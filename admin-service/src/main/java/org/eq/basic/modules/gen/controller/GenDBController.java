/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.gen.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eq.basic.common.base.BaseController;
import org.eq.basic.common.base.BaseEntity;
import org.eq.basic.common.base.BaseOpMsg;
import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.util.StringLowUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.eq.basic.common.status.StatusCode;
import org.eq.basic.gen.bean.DBFactory;
import org.eq.basic.gen.config.WebManger;
import org.eq.basic.gen.entity.Table;
import org.eq.basic.gen.entity.TableColumn;
import org.eq.basic.modules.gen.entity.GenDB;
import org.eq.basic.modules.gen.entity.GenTableModal;
import org.eq.basic.modules.gen.service.GenDBService;
import org.eq.basic.modules.sys.entity.SysUser;

/**
 * 代码生成数据库Controller
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Controller
@RequestMapping(value = "/gen/genDB")
public class GenDBController extends BaseController {

    @Autowired
    private GenDBService genDBService;

    /**
     * List页面
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"list", "" })
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {

        // 下拉选查询 自定义内容需要手动添加
        return "modules/gen/db/dbList";
    }

    /**
     * 数据库详情页面
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "detail")
    public String detail(HttpServletRequest request, HttpServletResponse response, Model model, GenDB genDB) {

        // 下拉选查询 自定义内容需要手动添加
        GenDB selectOne = this.genDBService.selectByRecord(genDB);
        model.addAttribute("dbMangerName", selectOne.getName());
        return "modules/gen/db/detailList";
    }

    /**
     * 数据库验证
     *
     * @param request
     * @param response
     * @param genDB
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "validDB")
    public BaseOpMsg validDB(HttpServletRequest request, HttpServletResponse response, GenDB genDB) {

        BaseOpMsg result = new BaseOpMsg();
        GenDB selectOne = this.genDBService.selectByRecord(genDB);
        WebManger db = new WebManger(selectOne);
        // 验证可用 可用加到bean池里
        if (db.valid()) {
            DBFactory.addBean(new WebManger(selectOne));
            result.setCode(StatusCode.CURD_SELECT_SUCCESS);
            result.setStatus("success");
            result.setMsg("数据库连接成功！");
        } else {
            // 销毁连接池
            db.getDbManger().getDruidDataSource().close();
            db = null;
            result.setCode(StatusCode.CURD_SELECT_FAILURE);
            result.setStatus("error");
            result.setMsg("数据库连接失败！");
        }
        return result;
    }

    /**
     * datatable 返回列表数据
     * {
     * "draw": 4,
     * "recordsTotal": 57,
     * "recordsFiltered": 57,
     * "data": [
     * [
     * "Charde",
     * "Marshall",
     * "Regional Director",
     * "San Francisco",
     * "16th Oct 08",
     * "$470,600"
     * ],[]...
     * ]
     * }
     *
     *
     * @param request
     * @param response
     * @param genDB
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"dataList" })
    public BaseTableData dataList(HttpServletRequest request, HttpServletResponse response, GenDB genDB) {

        Map<String, Object> params = new HashMap<>();
        // 取出 request 的参数信息分页信息 排序信息
        this.getInfoFromRequest(request, params);
        // 数据的draw自增
        this.baseTableData = this.genDBService.findDataTableByRecordForPage(genDB, params);
        int draw = Integer.parseInt(request.getParameter("draw") == null ? "0" : request.getParameter("draw"));
        this.baseTableData.setDraw(++draw);
        return this.baseTableData;
    }

    /**
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     *
     * @param genDB
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "selectGenDB")
    public BaseOpMsg<GenDB> selectGenDB(GenDB genDB) {

        BaseOpMsg<GenDB> result = new BaseOpMsg<>();
        if (genDB != null) {
            List<GenDB> genDBList = this.genDBService.findListByRecord(genDB);
            if (genDBList != null && !( genDBList.isEmpty())) {
                result.setCode(StatusCode.CURD_SELECT_SUCCESS);
                result.setStatus("success");
                result.setMsg("查询数据成功！");
                result.setList(genDBList);// 返回查询数据
            } else {
                result.setCode(StatusCode.CURD_SELECT_FAILURE);
                result.setStatus("error");
                result.setMsg("查询数据失败！");
                if (this.logger.isDebugEnabled()) {
                    result.setDescription(this.genDBService.getErrorInfo());
                }
            }
        } else {
            result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
            result.setStatus("error");
            result.setMsg("查询数据失败！");
            if (this.logger.isDebugEnabled()) {
                result.setDescription("查询条件为空");
            }
        }
        return result;
    }

    /**
     * 保存or修改操作
     *
     * @param request
     * @param genDB
     * @param opType
     *            操作类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"modify" })
    public BaseOpMsg modify(HttpServletRequest request, GenDB genDB, String opType) {

        // SysUser user = UserUtil.getInstance().getUser();
        SysUser user = new SysUser();
        user.setId(1L);
        BaseOpMsg result = new BaseOpMsg();
        if (opType != null && !"".equals(opType)) {
            if ("save".equals(opType)) {// 新增保存
                genDB = this.initGenDB(genDB, false, user);
                if (this.genDBService.insertRecord(genDB) > 0) {
                    result.setCode(StatusCode.CURD_ADD_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("保存数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_ADD_FAILURE);
                    result.setStatus("error");
                    result.setMsg("保存数据失败！");
                    if (this.logger.isDebugEnabled()) {
                        result.setDescription(this.genDBService.getErrorInfo());
                    }
                }
            } else if ("update".equals(opType)) {// 修改保存
                if (this.genDBService.updateByPrimaryKeySelective(genDB) > 0) {
                    result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("修改数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_UPDATE_FAILURE);
                    result.setStatus("error");
                    result.setMsg("修改数据失败！");
                    if (this.logger.isDebugEnabled()) {
                        result.setDescription(this.genDBService.getErrorInfo());
                    }
                }
            } else {
                result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
                result.setStatus("error");
                result.setMsg("操作违法！");
                if (this.logger.isDebugEnabled()) {
                    result.setDescription("opType 违法");
                }
            }
        } else {
            result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
            result.setStatus("error");
            result.setMsg("操作违法！");
            if (this.logger.isDebugEnabled()) {
                result.setDescription("opType 违法 或 null");
            }
        }
        return result;
    }

    private GenDB initGenDB(GenDB genDB, boolean ifUpdate, SysUser user) {

        if (!ifUpdate) {
            genDB.setCreateBy(user.getId());
            genDB.setCreateDate(new Date());
            genDB.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        }
        genDB.setUpdateBy(user.getId());
        genDB.setUpdateDate(new Date());
        return genDB;
    }

    /**
     * 根据ids 删除数据
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteGenDB")
    public BaseOpMsg deleteGenDB(String ids, String virtual) {

        BaseOpMsg result = new BaseOpMsg();
        if (ids != null && !"".equals(ids)) {
            int delNum = 0;
            if (virtual.equals("false")) {
                delNum = this.genDBService.deleteByPrimaryKeys(ids);
            } else {
                delNum = this.genDBService.deleteVirtualByPrimaryKeys(ids);
            }
            if (delNum > 0) {
                result.setCode(StatusCode.CURD_DELETE_SUCCESS);
                result.setStatus("success");
                result.setMsg("删除数据成功！");
            } else {
                result.setCode(StatusCode.CURD_DELETE_FAILURE);
                result.setStatus("error");
                result.setMsg("删除数据失败！");
                if (this.logger.isDebugEnabled()) {
                    result.setDescription(this.genDBService.getErrorInfo());
                }
            }
        } else {
            result.setCode(StatusCode.CURD_DELETE_FAILURE);
            result.setStatus("error");
            result.setMsg("删除数据失败！");
            if (this.logger.isDebugEnabled()) {
                result.setDescription("数据ids为空");
            }
        }
        return result;
    }

    /**
     * datatable 返回数据库table列表数据
     * {
     * "draw": 4,
     * "recordsTotal": 57,
     * "recordsFiltered": 57,
     * "data": [
     * [
     * "Charde",
     * "Marshall",
     * "Regional Director",
     * "San Francisco",
     * "16th Oct 08",
     * "$470,600"
     * ],[]...
     * ]
     * }
     *
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"tableList" })
    public BaseTableData tableList(HttpServletRequest request, HttpServletResponse response, String dbMangerName) {

        Map<String, Object> params = new HashMap<>();
        // 取出 request 的参数信息分页信息 排序信息
        this.getInfoFromRequest(request, params);
        // 数据的draw自增
        this.baseTableData = this.genDBService.findDBTableForPage(params, dbMangerName);
        int draw = Integer.parseInt(request.getParameter("draw") == null ? "0" : request.getParameter("draw"));
        this.baseTableData.setDraw(++draw);
        return this.baseTableData;
    }

    /**
     * 根据数据库连接名 查询库的所有表
     *
     * @param genDB
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "tableAll")
    public BaseOpMsg tableAll(HttpServletRequest request, HttpServletResponse response, GenDB genDB) {

        BaseOpMsg result = new BaseOpMsg();
        WebManger webManger = this.genDBService.getDBConfig(genDB);
        List<Table> tableList = this.genDBService.findDBTable(genDB);
        result.setCode(StatusCode.CURD_SELECT_SUCCESS);
        result.setList(tableList);
        result.setObject(webManger.getGenConfig().getDictionary());
        result.setStatus("success");
        result.setMsg("查询数据成功！");
        return result;
    }

    /**
     * datatable 返回数据库table 字段 列表数据
     * {
     * "draw": 4,
     * "recordsTotal": 57,
     * "recordsFiltered": 57,
     * "data": [
     * [
     * "Charde",
     * "Marshall",
     * "Regional Director",
     * "San Francisco",
     * "16th Oct 08",
     * "$470,600"
     * ],[]...
     * ]
     * }
     *
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"tableColumnList" })
    public BaseTableData tableColumnList(HttpServletRequest request, HttpServletResponse response,
            String dbMangerName) {

        Map<String, Object> params = new HashMap<>();
        // 取出 request 的参数信息分页信息 排序信息
        this.getInfoFromRequest(request, params);
        // 数据的draw自增
        this.baseTableData = this.genDBService.findDBTableColumnForPage(params, dbMangerName);
        int draw = Integer.parseInt(request.getParameter("draw") == null ? "0" : request.getParameter("draw"));
        this.baseTableData.setDraw(++draw);
        return this.baseTableData;
    }

    /**
     * 根据数据库连接名 和表名 查询库的所有表
     *
     * @param tableModal
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "tableColumnAll")
    public BaseOpMsg tableColumnAll(HttpServletRequest request, HttpServletResponse response,
            GenTableModal tableModal) {

        BaseOpMsg result = new BaseOpMsg();
        List<TableColumn> tableColumnList = this.genDBService.findDBTableColumn(tableModal);
        result.setCode(StatusCode.CURD_SELECT_SUCCESS);
        result.setList(tableColumnList);
        result.setStatus("success");
        result.setMsg("查询数据成功！");
        return result;
    }

    /**
     * dataTables请求头处理
     *
     * @param request
     * @param params
     */
    @Override
    protected void getInfoFromRequest(HttpServletRequest request, Map<String, Object> params) {

        super.getInfoFromRequest(request, params);
        // 表名
        String tableName = request.getParameter("tableName");
        if (StringLowUtils.isNotBlank(tableName)) {
            params.put("tableName", tableName);
        } else {
            params.put("tableName", "");
        }
    }
}