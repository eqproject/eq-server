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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import org.eq.basic.common.config.sysUtil.UserUtil;
import org.eq.basic.common.status.StatusCode;
import org.eq.basic.gen.entity.page.PageCurdModal;
import org.eq.basic.modules.gen.entity.GenDB;
import org.eq.basic.modules.gen.entity.GenPlan;
import org.eq.basic.modules.gen.entity.GenTableModal;
import org.eq.basic.modules.gen.service.GenDBService;
import org.eq.basic.modules.gen.service.GenPlanService;
import org.eq.basic.modules.gen.service.GenTableModalService;
import org.eq.basic.modules.sys.entity.SysUser;
import org.eq.basic.modules.sys.service.SysDictService;

/**
 * 代码生成表配置Controller
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Controller
@RequestMapping(value = "/gen/genTableModal")
public class GenTableModalController extends BaseController {

    @Autowired
    private GenTableModalService genTableModalService;

    @Autowired
    private GenPlanService genPlanService;

    @Autowired
    private GenDBService genDBService;

    @Autowired
    private SysDictService sysDictService;

    /**
     * List页面
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"list", "" })
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, GenPlan genPlan) {

        // 下拉选查询 自定义内容需要手动添加
        List<GenDB> genDBList = this.genDBService.findListByRecord(new GenDB());
        // 系统中所有的数据字典
        List<Map> dictTypeList = this.sysDictService.findDictType();
        model.addAttribute("dictTypeList", dictTypeList);
        model.addAttribute("genDBList", genDBList);
        model.addAttribute("genPlan", genPlan);
        return "modules/gen/codeConfig/tableModalList";
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
     * @param genTableModal
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"dataList" })
    public BaseTableData dataList(HttpServletRequest request, HttpServletResponse response,
                                  GenTableModal genTableModal) {

        Map<String, Object> params = new HashMap<>();
        // 取出 request 的参数信息分页信息 排序信息
        this.getInfoFromRequest(request, params);
        // 数据的draw自增
        this.baseTableData = this.genTableModalService.findTableModalForPage(genTableModal, params);
        int draw = Integer.parseInt(request.getParameter("draw") == null ? "0" : request.getParameter("draw"));
        this.baseTableData.setDraw(++draw);
        return this.baseTableData;
    }

    /**
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     *
     * @param genTableModal
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "selectGenTableModal")
    public BaseOpMsg<GenTableModal> selectGenTableModal(GenTableModal genTableModal) {

        BaseOpMsg<GenTableModal> result = new BaseOpMsg<>();
        if (genTableModal != null) {
            List<GenTableModal> genTableModalList = this.genTableModalService.findListByRecord(genTableModal);
            if (genTableModalList != null && genTableModalList.size() > 0) {
                result.setCode(StatusCode.CURD_SELECT_SUCCESS);
                result.setStatus("success");
                result.setMsg("查询数据成功！");
                result.setList(genTableModalList);// 返回查询数据
            } else {
                result.setCode(StatusCode.CURD_SELECT_FAILURE);
                result.setStatus("error");
                result.setMsg("查询数据失败！");
                if (this.logger.isDebugEnabled()) {
                    result.setDescription(this.genTableModalService.getErrorInfo());
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
     * @param genTableModal
     * @param opType
     *            操作类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"modify" })
    public BaseOpMsg modify(HttpServletRequest request, GenTableModal genTableModal, String opType,
            PageCurdModal pageCurdModal) {

        SysUser user = UserUtil.getInstance().getUser();
        BaseOpMsg result = new BaseOpMsg();
        if (opType != null && !"".equals(opType)) {
            Gson gson = new Gson();
            genTableModal.setPageSetting(gson.toJson(pageCurdModal));
            if ("save".equals(opType)) {// 新增保存
                genTableModal = this.initGenTableModal(genTableModal, false, user);
                if (this.genTableModalService.insertRecord(genTableModal) > 0) {
                    result.setCode(StatusCode.CURD_ADD_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("保存数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_ADD_FAILURE);
                    result.setStatus("error");
                    result.setMsg("保存数据失败！");
                    if (this.logger.isDebugEnabled()) {
                        result.setDescription(this.genTableModalService.getErrorInfo());
                    }
                }
            } else if ("update".equals(opType)) {// 修改保存
                if (this.genTableModalService.updateByPrimaryKeySelective(genTableModal) > 0) {
                    result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("修改数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_UPDATE_FAILURE);
                    result.setStatus("error");
                    result.setMsg("修改数据失败！");
                    if (this.logger.isDebugEnabled()) {
                        result.setDescription(this.genTableModalService.getErrorInfo());
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

    private GenTableModal initGenTableModal(GenTableModal genTableModal, boolean ifUpdate, SysUser user) {

        if (!ifUpdate) {
            genTableModal.setCreateBy(user.getId());
            genTableModal.setCreateDate(new Date());
            genTableModal.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        }
        genTableModal.setUpdateBy(user.getId());
        genTableModal.setUpdateDate(new Date());
        return genTableModal;
    }

    /**
     * 根据ids 删除数据
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteGenTableModal")
    public BaseOpMsg deleteGenTableModal(String ids, String virtual) {

        BaseOpMsg result = new BaseOpMsg();
        if (ids != null && !"".equals(ids)) {
            int delNum = 0;
            if (virtual.equals("false")) {
                delNum = this.genTableModalService.deleteByPrimaryKeys(ids);
            } else {
                delNum = this.genTableModalService.deleteVirtualByPrimaryKeys(ids);
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
                    result.setDescription(this.genTableModalService.getErrorInfo());
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
     * 方案的代码生成
     */
    @ResponseBody
    @RequestMapping(value = "codeMake")
    public BaseOpMsg codeMake(GenTableModal genTableModal) {

        BaseOpMsg result = new BaseOpMsg();
        if (this.genTableModalService.codeMake(genTableModal, null)) {
            result.setCode(StatusCode.CURD_DELETE_SUCCESS);
            result.setStatus("success");
            result.setMsg("代码生成成功！");
        } else {
            result.setCode(StatusCode.CURD_DELETE_FAILURE);
            result.setStatus("error");
            result.setMsg("代码生成失败！");
        }
        return result;
    }
}