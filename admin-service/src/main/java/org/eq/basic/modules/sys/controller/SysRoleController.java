/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.controller;

import java.util.ArrayList;
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
import org.eq.basic.common.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.eq.basic.common.config.sysUtil.UserUtil;
import org.eq.basic.common.status.StatusCode;
import org.eq.basic.modules.sys.entity.SysDict;
import org.eq.basic.modules.sys.entity.SysRole;
import org.eq.basic.modules.sys.entity.SysRoleMenu;
import org.eq.basic.modules.sys.entity.SysUser;
import org.eq.basic.modules.sys.service.SysRoleMenuService;
import org.eq.basic.modules.sys.service.SysRoleService;

/**
 * 角色表Controller
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Controller
@RequestMapping(value = "/sys/sysRole")
public class SysRoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

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
        List<SysDict> roleTypeList = this.sysRoleService.getRoleTypePermission(null);
        model.addAttribute("roleTypeList", roleTypeList);
        return "modules/sys/sysRoleList";
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
     * @param sysRole
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"dataList" })
    public BaseTableData dataList(HttpServletRequest request, HttpServletResponse response, SysRole sysRole) {

        Map<String, Object> params = new HashMap<>();
        // 取出 request 的参数信息分页信息 排序信息
        this.getInfoFromRequest(request, params);
        // 数据的draw自增
        // 角色类型权限下的角色信息
        this.baseTableData = this.sysRoleService.findDataTableByRecordForPagePermission(sysRole, params);
        int draw = Integer.parseInt(request.getParameter("draw") == null ? "0" : request.getParameter("draw"));
        this.baseTableData.setDraw(++draw);
        return this.baseTableData;
    }

    /**
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     *
     * @param sysRole
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "selectSysRole")
    public BaseOpMsg<SysRole> selectSysRole(SysRole sysRole) {

        BaseOpMsg<SysRole> result = new BaseOpMsg<>();
        if (sysRole != null) {
            List<SysRole> sysRoleList = this.sysRoleService.findListByRecord(sysRole);
            if (sysRoleList != null && !( sysRoleList.isEmpty())) {

                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(sysRole.getId());
                List<SysRoleMenu> sysRoleMenuList = this.sysRoleMenuService.findListByRecord(sysRoleMenu);
                sysRoleList.get(0).setSysRoleMenuList(sysRoleMenuList);

                result.setCode(StatusCode.CURD_SELECT_SUCCESS);
                result.setStatus("success");
                result.setMsg("查询数据成功！");
                result.setList(sysRoleList);// 返回查询数据
            } else {
                result.setCode(StatusCode.CURD_SELECT_FAILURE);
                result.setStatus("error");
                result.setMsg("查询数据失败！");
                if (this.logger.isDebugEnabled()) {
                    result.setDescription(this.sysRoleService.getErrorInfo());
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
     * @param sysRole
     * @param opType
     *            操作类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"modify" })
    public BaseOpMsg modify(HttpServletRequest request, SysRole sysRole, String opType) {

        SysUser user = UserUtil.getInstance().getUser();
        BaseOpMsg result = new BaseOpMsg();
        if (opType != null && !"".equals(opType)) {
            this.getRoleMenuFromRequest(request, sysRole, false, user);
            if ("save".equals(opType)) {// 新增保存
                sysRole = this.initSysRole(sysRole, false, user);
                if (this.sysRoleService.insertRole(sysRole)) {
                    result.setCode(StatusCode.CURD_ADD_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("保存数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_ADD_FAILURE);
                    result.setStatus("error");
                    result.setMsg("保存数据失败！");
                    if (this.logger.isDebugEnabled()) {
                        result.setDescription(this.sysRoleService.getErrorInfo());
                    }
                }
            } else if ("update".equals(opType)) {// 修改保存
                sysRole = this.initSysRole(sysRole, true, user);
                if (this.sysRoleService.updateRole(sysRole)) {
                    result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("修改数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_UPDATE_FAILURE);
                    result.setStatus("error");
                    result.setMsg("修改数据失败！");
                    if (this.logger.isDebugEnabled()) {
                        result.setDescription(this.sysRoleService.getErrorInfo());
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

    private void getRoleMenuFromRequest(HttpServletRequest request, SysRole sysRole, boolean ifUpdate, SysUser user) {

        List<SysRoleMenu> result = new ArrayList<>();
        String menuIds = request.getParameter("menuIds");
        if (menuIds != null && !"".equals(menuIds)) {
            String[] menuIdArray = menuIds.split(",");
            for (int i = 0; i < menuIdArray.length; i++) {
                // 解析子表数据
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setMenuId(ParseUtil.getLong(menuIdArray[i]));
                this.initSysRoleMenu(sysRoleMenu, ifUpdate, user);
                result.add(sysRoleMenu);
            }
        }
        sysRole.setSysRoleMenuList(result);
    }

    private SysRoleMenu initSysRoleMenu(SysRoleMenu sysRoleMenu, boolean ifUpdate, SysUser user) {

        if (!ifUpdate) {
            sysRoleMenu.setCreateBy(user.getId());
            sysRoleMenu.setCreateDate(new Date());
            sysRoleMenu.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        }
        sysRoleMenu.setUpdateBy(user.getId());
        sysRoleMenu.setUpdateDate(new Date());
        return sysRoleMenu;
    }

    private SysRole initSysRole(SysRole sysRole, boolean ifUpdate, SysUser user) {

        if (!ifUpdate) {
            sysRole.setCreateBy(user.getId());
            sysRole.setCreateDate(new Date());
            sysRole.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        }
        sysRole.setUpdateBy(user.getId());
        sysRole.setUpdateDate(new Date());
        return sysRole;
    }

    /**
     * 根据ids 删除数据
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteSysRole")
    public BaseOpMsg deleteSysRole(String ids, String virtual) {

        BaseOpMsg result = new BaseOpMsg();
        if (ids != null && !"".equals(ids)) {
            int delNum = 0;
            delNum = this.sysRoleService.deleteRole(ids, virtual);
            if (delNum > 0) {
                result.setCode(StatusCode.CURD_DELETE_SUCCESS);
                result.setStatus("success");
                result.setMsg("删除数据成功！");
            } else {
                result.setCode(StatusCode.CURD_DELETE_FAILURE);
                result.setStatus("error");
                result.setMsg("删除数据失败！");
                if (this.logger.isDebugEnabled()) {
                    result.setDescription(this.sysRoleService.getErrorInfo());
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