/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.sys.controller;

import cn.bubi.basic.common.base.BaseController;
import cn.bubi.basic.common.base.BaseEntity;
import cn.bubi.basic.common.base.BaseOpMsg;
import cn.bubi.basic.common.base.BaseTableData;
import cn.bubi.basic.common.config.sysUtil.UserUtil;
import cn.bubi.basic.common.status.StatusCode;
import cn.bubi.basic.modules.sys.entity.SysUser;
import cn.bubi.basic.modules.sys.entity.SysUserOffice;
import cn.bubi.basic.modules.sys.service.SysUserOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户机构表Controller
 *
 * @author JoinHan
 * @version 2018-04-11
 */
@Controller
@RequestMapping(value = "/sys/sysUserOffice")
public class SysUserOfficeController extends BaseController {

    @Autowired
    private SysUserOfficeService sysUserOfficeService;

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
        return "modules/sys/sysUserOfficeList";
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
     * @param sysUserOffice
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"dataList" })
    public BaseTableData dataList(HttpServletRequest request, HttpServletResponse response,
                                  SysUserOffice sysUserOffice) {

        Map<String, Object> params = new HashMap<>();
        // 取出 request 的参数信息分页信息 排序信息
        this.getInfoFromRequest(request, params);
        // 数据的draw自增
        this.baseTableData = this.sysUserOfficeService.findDataTableByRecordForPage(sysUserOffice, params);
        int draw = Integer.parseInt(request.getParameter("draw") == null ? "0" : request.getParameter("draw"));
        this.baseTableData.setDraw(++draw);
        return this.baseTableData;
    }

    /**
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     *
     * @param sysUserOffice
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "selectSysUserOffice")
    public BaseOpMsg<SysUserOffice> selectSysUserOffice(SysUserOffice sysUserOffice) {

        BaseOpMsg<SysUserOffice> result = new BaseOpMsg<>();
        if (sysUserOffice != null) {
            List<SysUserOffice> sysUserOfficeList = this.sysUserOfficeService.findListByRecord(sysUserOffice);
            if (sysUserOfficeList != null && !( sysUserOfficeList.isEmpty())) {
                result.setCode(StatusCode.CURD_SELECT_SUCCESS);
                result.setStatus("success");
                result.setMsg("查询数据成功！");
                result.setList(sysUserOfficeList);// 返回查询数据
            } else {
                result.setCode(StatusCode.CURD_SELECT_FAILURE);
                result.setStatus("error");
                result.setMsg("查询数据失败！");
                if (this.logger.isDebugEnabled()) {
                    result.setDescription(this.sysUserOfficeService.getErrorInfo());
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
     * @param sysUserOffice
     * @param opType
     *            操作类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"modify" })
    public BaseOpMsg modify(HttpServletRequest request, SysUserOffice sysUserOffice, String opType) {

        SysUser user = UserUtil.getInstance().getUser();
        BaseOpMsg result = new BaseOpMsg();
        if (opType != null && !"".equals(opType)) {
            if ("save".equals(opType)) {// 新增保存
                sysUserOffice = this.initSysUserOffice(sysUserOffice, false, user);
                if (this.sysUserOfficeService.insertRecord(sysUserOffice) > 0) {
                    result.setCode(StatusCode.CURD_ADD_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("保存数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_ADD_FAILURE);
                    result.setStatus("error");
                    result.setMsg("保存数据失败！");
                    if (this.logger.isDebugEnabled()) {
                        result.setDescription(this.sysUserOfficeService.getErrorInfo());
                    }
                }
            } else if ("update".equals(opType)) {// 修改保存
                sysUserOffice = this.initSysUserOffice(sysUserOffice, true, user);
                if (this.sysUserOfficeService.updateByPrimaryKeySelective(sysUserOffice) > 0) {
                    result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("修改数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_UPDATE_FAILURE);
                    result.setStatus("error");
                    result.setMsg("修改数据失败！");
                    if (this.logger.isDebugEnabled()) {
                        result.setDescription(this.sysUserOfficeService.getErrorInfo());
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

    private SysUserOffice initSysUserOffice(SysUserOffice sysUserOffice, boolean ifUpdate, SysUser user) {

        if (!ifUpdate) {
            sysUserOffice.setCreateBy(user.getId());
            sysUserOffice.setCreateDate(new Date());
            sysUserOffice.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        }
        sysUserOffice.setUpdateBy(user.getId());
        sysUserOffice.setUpdateDate(new Date());
        return sysUserOffice;
    }

    /**
     * 根据ids 删除数据
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteSysUserOffice")
    public BaseOpMsg deleteSysUserOffice(String ids, String virtual) {

        BaseOpMsg result = new BaseOpMsg();
        if (ids != null && !"".equals(ids)) {
            int delNum = 0;
            if (virtual.equals("false")) {
                delNum = this.sysUserOfficeService.deleteByPrimaryKeys(ids);
            } else {
                delNum = this.sysUserOfficeService.deleteVirtualByPrimaryKeys(ids);
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
                    result.setDescription(this.sysUserOfficeService.getErrorInfo());
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

}