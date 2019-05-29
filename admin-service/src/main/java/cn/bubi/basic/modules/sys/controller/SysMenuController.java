/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.sys.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bubi.basic.common.base.BaseController;
import cn.bubi.basic.common.base.BaseEntity;
import cn.bubi.basic.common.base.BaseOpMsg;
import cn.bubi.basic.common.base.BaseValidatorMsg;
import cn.bubi.basic.common.util.ParseUtil;
import cn.bubi.basic.common.util.StringLowUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.bubi.basic.common.config.sysUtil.UserUtil;
import cn.bubi.basic.common.status.StatusCode;
import cn.bubi.basic.modules.sys.entity.SysMenu;
import cn.bubi.basic.modules.sys.entity.SysMenuExample;
import cn.bubi.basic.modules.sys.entity.SysUser;
import cn.bubi.basic.modules.sys.service.SysMenuService;

/**
 * 菜单表Controller
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Controller
@RequestMapping(value = "/sys/sysMenu")
public class SysMenuController extends BaseController {

    @Autowired
    private SysMenuService sysMenuService;

    private int order = 0;// 菜单排序变量

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
        return "modules/sys/sysMenuList";
    }

    @ResponseBody
    @RequestMapping(value = {"dataList" })
    public BaseOpMsg dataList(HttpServletRequest request, HttpServletResponse response, Model model, SysMenu sysMenu) {

        Map<String, Object> params = new HashMap<>();
        this.getInfoFromRequest(request, params);
        List<SysMenu> sysMenuList = new ArrayList<>();
        if (UserUtil.getInstance().isAdmin(null)) {// 管理员
            sysMenuList = this.sysMenuService.findListByRecord(sysMenu);
        } else {
            // 登录人 所有角色所对应的菜单id 根据id 查出所有的菜单
            sysMenuList = this.sysMenuService.findListPermission(UserUtil.getInstance().getUserInfo(), sysMenu);
        }
        BaseOpMsg result = new BaseOpMsg();
        result.setCode(StatusCode.CURD_SELECT_SUCCESS);
        result.setStatus("success");
        result.setList(sysMenuList);
        result.setMsg("查询数据成功！");
        return result;
    }

    /**
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     *
     * @param sysMenu
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "selectSysMenu")
    public BaseOpMsg<SysMenu> selectSysMenu(SysMenu sysMenu) {

        BaseOpMsg<SysMenu> result = new BaseOpMsg<>();
        if (sysMenu != null) {
            List<SysMenu> sysMenuList = this.sysMenuService.findListByRecord(sysMenu);
            if (sysMenuList != null && !( sysMenuList.isEmpty())) {
                result.setCode(StatusCode.CURD_SELECT_SUCCESS);
                result.setStatus("success");
                result.setMsg("查询数据成功！");
                result.setList(sysMenuList);// 返回查询数据
            } else {
                result.setCode(StatusCode.CURD_SELECT_FAILURE);
                result.setStatus("error");
                result.setMsg("查询数据失败！");
                if (this.logger.isDebugEnabled()) {
                    result.setDescription(this.sysMenuService.getErrorInfo());
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
     * @param sysMenu
     * @param opType
     *            操作类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"modify" })
    public BaseOpMsg modify(HttpServletRequest request, SysMenu sysMenu, String opType) {

        SysUser user = UserUtil.getInstance().getUser();
        BaseOpMsg result = new BaseOpMsg();
        if (opType != null && !"".equals(opType)) {
            if ("save".equals(opType)) {// 新增保存
                sysMenu = this.initSysMenu(sysMenu, false, user);
                if (this.sysMenuService.insertRecord(sysMenu) > 0) {
                    result.setCode(StatusCode.CURD_ADD_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("保存数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_ADD_FAILURE);
                    result.setStatus("error");
                    result.setMsg("保存数据失败！");
                    if (this.logger.isDebugEnabled()) {
                        result.setDescription(this.sysMenuService.getErrorInfo());
                    }
                }
            } else if ("update".equals(opType)) {// 修改保存
                sysMenu = this.initSysMenu(sysMenu, true, user);
                if (this.sysMenuService.updateByPrimaryKeySelective(sysMenu) > 0) {
                    result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("修改数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_UPDATE_FAILURE);
                    result.setStatus("error");
                    result.setMsg("修改数据失败！");
                    if (this.logger.isDebugEnabled()) {
                        result.setDescription(this.sysMenuService.getErrorInfo());
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

    private SysMenu initSysMenu(SysMenu sysMenu, boolean ifUpdate, SysUser user) {

        SysMenu old = this.sysMenuService.selectByPrimaryKey(sysMenu.getParentId());
        sysMenu.setParentIds(old.getParentIds() + old.getId() + ",");
        if (!ifUpdate) {
            sysMenu.setCreateBy(user.getId());
            sysMenu.setCreateDate(new Date());
            sysMenu.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        }
        sysMenu.setUpdateBy(user.getId());
        sysMenu.setUpdateDate(new Date());
        return sysMenu;
    }

    /**
     * 根据ids 删除数据
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteSysMenu")
    public BaseOpMsg deleteSysMenu(String ids, String virtual) {

        BaseOpMsg result = new BaseOpMsg();
        // TODO 删除菜单应先判断 该菜单是否使用
        if (ids != null && !"".equals(ids)) {
            int delNum = 0;
            if (virtual.equals("false")) {
                delNum = this.sysMenuService.deleteByPrimaryKeys(ids);
            } else {
                delNum = this.sysMenuService.deleteVirtualByPrimaryKeys(ids);
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
                    result.setDescription(this.sysMenuService.getErrorInfo());
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

    @ResponseBody
    @RequestMapping(value = "treeSysMenu")
    public BaseOpMsg treeSysMenu(HttpServletRequest request, HttpServletResponse response, Model model,
            SysMenu sysMenu) {

        List<Map<String, Object>> sysMenuListMap = new ArrayList<>();
        List<SysMenu> sysMenuList = new ArrayList<>();
        if (UserUtil.getInstance().isAdmin(null)) {// 管理员
            sysMenuList = this.sysMenuService.findListByRecord(sysMenu);
        } else {
            // 登录人 所有角色所对应的菜单id 根据id 查出所有的菜单
            sysMenuList = this.sysMenuService.findListPermission(UserUtil.getInstance().getUserInfo(), sysMenu);
        }

        StringBuilder ids = new StringBuilder(",");
        StringBuilder rootIds = new StringBuilder(",");
        for (int i = 0; i < sysMenuList.size(); i++) {
            ids.append(sysMenuList.get(i).getId() + ",");
        }
        for (int i = 0; i < sysMenuList.size(); i++) {
            if (ids.indexOf("," + sysMenuList.get(i).getParentId() + ",") == -1) {
                if (rootIds.indexOf("," + sysMenuList.get(i).getParentId() + ",") == -1) {
                    rootIds.append(sysMenuList.get(i).getParentId() + ",");
                }
            }
        }
        new ArrayList<>();
        String[] idStr = rootIds.toString().split(",");
        this.order = 0;
        for (int j = 1; j < idStr.length; j++) {
            sysMenuListMap.addAll(this.addTree(ParseUtil.getLong(idStr[j]), sysMenuList));
        }
        BaseOpMsg result = new BaseOpMsg();
        if (sysMenuListMap != null && !( sysMenuListMap.isEmpty())) {
            result.setCode(StatusCode.TREE_SUCCESS);
            result.setStatus("success");
            result.setList(sysMenuListMap);
            result.setMsg("树查询数据成功！");
        } else {
            result.setCode(StatusCode.TREE_FAILURE);
            result.setStatus("success");
            result.setMsg("树查询数据失败！");
        }
        return result;
    }

    private List<Map<String, Object>> addTree(Long id, List<SysMenu> sysMenuList) {

        Map<String, Object> map = null;
        List<Map<String, Object>> mapList = null;
        for (int i = 0; i < sysMenuList.size(); i++) {
            if (sysMenuList.get(i).getParentId().equals(id)) {
                if (mapList == null) {
                    mapList = new ArrayList<>();
                }
                map = new HashMap<>();
                map.put("text", sysMenuList.get(i).getName());
                map.put("value", sysMenuList.get(i).getId());
                map.put("order", this.order);
                this.order++;
                List<Map<String, Object>> childMap = this.addTree(sysMenuList.get(i).getId(), sysMenuList);
                if (childMap != null) {
                    map.put("nodes", childMap);
                }
                mapList.add(map);
            }
        }
        return mapList;
    }

    /**
     * 验证code 唯一性
     *
     * @param sysMenu
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "codeValid")
    public BaseValidatorMsg codeValid(SysMenu sysMenu) {

        BaseValidatorMsg result = new BaseValidatorMsg();
        if (StringLowUtils.isNotBlank(sysMenu.getCode())) {
            if (sysMenu.getId() == null) {
                SysMenuExample example = new SysMenuExample();
                example.or().andCodeEqualTo(sysMenu.getCode()).andDelFlagEqualTo(BaseEntity.DEL_FLAG_NORMAL);
                List<SysMenu> list = this.sysMenuService.findListByExample(example);
                if (list == null || list.size() <= 0) {
                    result.setValid(true);
                }
            } else {
                SysMenuExample example = new SysMenuExample();
                example.or().andCodeEqualTo(sysMenu.getCode()).andIdNotEqualTo(sysMenu.getId())
                        .andDelFlagEqualTo(BaseEntity.DEL_FLAG_NORMAL);
                List<SysMenu> list = this.sysMenuService.findListByExample(example);
                if (list == null || list.size() <= 0) {
                    result.setValid(true);
                }
            }
        } else {
            result.setValid(false);
        }
        return result;
    }

}