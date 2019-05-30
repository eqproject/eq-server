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

import org.eq.basic.common.base.*;
import org.eq.basic.common.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import org.eq.basic.common.config.sysUtil.UserUtil;
import org.eq.basic.common.status.StatusCode;
import org.eq.basic.modules.sys.entity.SysRole;
import org.eq.basic.modules.sys.entity.SysUser;
import org.eq.basic.modules.sys.entity.SysUserInfo;
import org.eq.basic.modules.sys.entity.SysUserOffice;
import org.eq.basic.modules.sys.entity.SysUserRole;
import org.eq.basic.modules.sys.service.SysRoleService;
import org.eq.basic.modules.sys.service.SysUserOfficeService;
import org.eq.basic.modules.sys.service.SysUserRoleService;
import org.eq.basic.modules.sys.service.SysUserService;

/**
 * 用户表Controller
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Controller
@RequestMapping(value = "/sys/sysUser")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

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

        List<SysRole> roleList = this.sysRoleService.findRolePermission(0);
        model.addAttribute("roleList", roleList);
        return "modules/sys/sysUserList";
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
     * @param sysUser
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"dataList" })
    public BaseTableData dataList(HttpServletRequest request, HttpServletResponse response, SysUser sysUser,
                                  String association) {

        Map<String, Object> params = new HashMap<>();
        // 取出 request 的参数信息分页信息 排序信息
        this.getInfoFromRequest(request, params);
        params.put("association", association);
        // 数据的draw自增
        this.baseTableData = this.sysUserService.findDataTableByRecordForPagePermission(sysUser, params);
        int draw = Integer.parseInt(request.getParameter("draw") == null ? "0" : request.getParameter("draw"));
        this.baseTableData.setDraw(++draw);
        return this.baseTableData;
    }

    /**
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     *
     * @param sysUser
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "selectSysUser")
    public BaseOpMsg<SysUser> selectSysUser(SysUser sysUser) {

        BaseOpMsg<SysUser> result = new BaseOpMsg<>();
        if (sysUser != null) {
            List<SysUser> sysUserList = this.sysUserService.selectUser(sysUser);
            if (sysUserList != null && !( sysUserList.isEmpty())) {
                // 用户角色信息
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(sysUser.getId());
                List<SysUserRole> sysUserRoleList = this.sysUserRoleService.findListByRecord(sysUserRole);
                sysUserList.get(0).setSysUserRoleList(sysUserRoleList);
                // 用户机构信息
                SysUserOffice sysUserOffice = new SysUserOffice();
                sysUserOffice.setUserId(sysUser.getId());
                List<SysUserOffice> sysUserOfficeList = this.sysUserOfficeService.findListByRecord(sysUserOffice);
                sysUserList.get(0).setSysUserOfficeList(sysUserOfficeList);

                result.setCode(StatusCode.CURD_SELECT_SUCCESS);
                result.setStatus("success");
                result.setMsg("查询数据成功！");
                result.setList(sysUserList);// 返回查询数据
            } else {
                result.setCode(StatusCode.CURD_SELECT_FAILURE);
                result.setStatus("error");
                result.setMsg("查询数据失败！");
                if (this.logger.isDebugEnabled()) {
                    result.setDescription(this.sysUserService.getErrorInfo());
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
     * @param sysUser
     * @param opType
     *            操作类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"modify" })
    public BaseOpMsg modify(HttpServletRequest request, SysUser sysUser, String opType) {

        SysUser user = UserUtil.getInstance().getUser();
        BaseOpMsg result = new BaseOpMsg();
        if (opType != null && !"".equals(opType)) {
            this.getUserRoleAndOfficeFromRequest(request, sysUser, false, user);
            if ("save".equals(opType)) {// 新增保存
                sysUser = this.initSysUser(sysUser, false, user);
                if (this.sysUserService.insertUser(sysUser)) {
                    result.setCode(StatusCode.CURD_ADD_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("保存数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_ADD_FAILURE);
                    result.setStatus("error");
                    result.setMsg("保存数据失败！");
                    if (this.logger.isDebugEnabled()) {
                        result.setDescription(this.sysUserService.getErrorInfo());
                    }
                }
            } else if ("update".equals(opType)) {// 修改保存
                sysUser = this.initSysUser(sysUser, true, user);
                if (this.sysUserService.updateUser(sysUser)) {
                    result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("修改数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_UPDATE_FAILURE);
                    result.setStatus("error");
                    result.setMsg("修改数据失败！");
                    if (this.logger.isDebugEnabled()) {
                        result.setDescription(this.sysUserService.getErrorInfo());
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

    private SysUser initSysUser(SysUser sysUser, boolean ifUpdate, SysUser user) {

        if (!"******".equals(sysUser.getPw())) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
            sysUser.setPw(encoder.encode(sysUser.getPw().trim()));
        } else {
            sysUser.setPw(null);
        }
        this.initSysUserInfo(sysUser.getSysUserInfo(), ifUpdate, user);
        if (!ifUpdate) {
            sysUser.setCreateBy(user.getId());
            sysUser.setCreateDate(new Date());
            sysUser.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        }
        sysUser.setUpdateBy(user.getId());
        sysUser.setUpdateDate(new Date());
        return sysUser;
    }

    private SysUserInfo initSysUserInfo(SysUserInfo sysUserInfo, boolean ifUpdate, SysUser user) {

        if (!ifUpdate) {
            sysUserInfo.setCreateBy(user.getId());
            sysUserInfo.setCreateDate(new Date());
            sysUserInfo.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        }
        sysUserInfo.setUpdateBy(user.getId());
        sysUserInfo.setUpdateDate(new Date());
        return sysUserInfo;
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

    private SysUserRole initSysUserRole(SysUserRole sysUserRole, boolean ifUpdate, SysUser user) {

        if (!ifUpdate) {
            sysUserRole.setCreateBy(user.getId());
            sysUserRole.setCreateDate(new Date());
            sysUserRole.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        }
        sysUserRole.setUpdateBy(user.getId());
        sysUserRole.setUpdateDate(new Date());
        return sysUserRole;
    }

    private void getUserRoleAndOfficeFromRequest(HttpServletRequest request, SysUser sysUser, boolean ifUpdate,
            SysUser user) {

        List<SysUserRole> result = new ArrayList<>();
        String[] roleIds = request.getParameterValues("roleIds");
        if (roleIds != null) {
            for (int i = 0; i < roleIds.length; i++) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setRoleId(ParseUtil.getLong(roleIds[i]));
                this.initSysUserRole(sysUserRole, ifUpdate, user);
                result.add(sysUserRole);
            }
        }
        sysUser.setSysUserRoleList(result);

        String[] officeIds = sysUser.getOfficeidsStr().split(",");
        List<SysUserOffice> sysUserOfficeList = new ArrayList<>();
        for (String id : officeIds) {
            SysUserOffice suf = new SysUserOffice();
            suf.setOfficeId(ParseUtil.getLong(id));
            this.initSysUserOffice(suf, ifUpdate, user);
            sysUserOfficeList.add(suf);
        }
        sysUser.setSysUserOfficeList(sysUserOfficeList);
    }

    /**
     * 根据ids 删除数据
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteSysUser")
    public BaseOpMsg deleteSysUser(String ids, String virtual) {

        BaseOpMsg result = new BaseOpMsg();
        if (ids != null && !"".equals(ids)) {
            int delNum = this.sysUserService.deleteUser(ids, virtual);
            if (delNum > 0) {
                result.setCode(StatusCode.CURD_DELETE_SUCCESS);
                result.setStatus("success");
                result.setMsg("删除数据成功！");
            } else {
                result.setCode(StatusCode.CURD_DELETE_FAILURE);
                result.setStatus("error");
                result.setMsg("删除数据失败！");
                if (this.logger.isDebugEnabled()) {
                    result.setDescription(this.sysUserService.getErrorInfo());
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
     * 重置密码前验证原始密码正确性
     *
     * @param sysUser
     * @param oldPwd
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "validPwd")
    public BaseValidatorMsg validPwd(SysUser sysUser, String oldPwd) {

        BaseValidatorMsg baseValidatorMsg = new BaseValidatorMsg();
        SysUser oldUser = this.sysUserService.selectByRecord(sysUser);
        boolean checkpw = BCrypt.checkpw(oldPwd, oldUser.getPw());
        baseValidatorMsg.setValid(checkpw);
        return baseValidatorMsg;
    }

    @ResponseBody
    @RequestMapping(value = "saveNewPwd")
    public BaseOpMsg saveNewPwd(SysUser sysUser, String userId) {

        SysUser user = UserUtil.getInstance().getUser();
        BaseOpMsg result = new BaseOpMsg();
        sysUser = this.initSysUser(sysUser, true, user);
        sysUser.setId(ParseUtil.getLong(userId));
        if (this.sysUserService.updateByPrimaryKeySelective(sysUser) > 0) {
            result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
            result.setStatus("success");
            result.setMsg("修改数据成功！");
        } else {
            result.setCode(StatusCode.CURD_UPDATE_FAILURE);
            result.setStatus("error");
            result.setMsg("修改数据失败！");
            if (this.logger.isDebugEnabled()) {
                result.setDescription(this.sysUserService.getErrorInfo());
            }
        }
        return result;
    }

}