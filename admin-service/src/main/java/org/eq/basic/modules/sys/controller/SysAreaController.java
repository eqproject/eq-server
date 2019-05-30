/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.controller;

import org.eq.basic.common.base.BaseController;
import org.eq.basic.common.base.BaseEntity;
import org.eq.basic.common.base.BaseOpMsg;
import org.eq.basic.common.base.BaseValidatorMsg;
import org.eq.basic.common.config.sysUtil.SysCacheUtil;
import org.eq.basic.common.config.sysUtil.UserUtil;
import org.eq.basic.common.status.StatusCode;
import org.eq.basic.common.util.ParseUtil;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.basic.modules.sys.entity.SysArea;
import org.eq.basic.modules.sys.entity.SysAreaExample;
import org.eq.basic.modules.sys.entity.SysDict;
import org.eq.basic.modules.sys.entity.SysUser;
import org.eq.basic.modules.sys.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 地区表Controller
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Controller
@RequestMapping(value = "/sys/sysArea")
public class SysAreaController extends BaseController {

    @Autowired
    private SysAreaService sysAreaService;

    private int order = 0;// 下拉树排序变量

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
        List<SysDict> areaTypeList = this.sysAreaService.getAreaTypePermission(null);
        model.addAttribute("areaTypeList", areaTypeList);
        return "modules/sys/sysAreaList";
    }

    @ResponseBody
    @RequestMapping(value = {"dataList" })
    public BaseOpMsg dataList(HttpServletRequest request, HttpServletResponse response, SysArea sysArea) {

        List<SysArea> sysAreaList = new ArrayList<>();
        if (UserUtil.getInstance().isAdmin(null)) {// 管理员
            sysAreaList = this.sysAreaService.findListByRecord(sysArea);
        } else {
            // 登录人 所有角色所对应的菜单id 根据id 查出所有的菜单
            sysAreaList = this.sysAreaService.findListPermission(UserUtil.getInstance().getUserInfo(), sysArea);
        }
        BaseOpMsg result = new BaseOpMsg();
        result.setCode(StatusCode.CURD_SELECT_SUCCESS);
        result.setStatus("success");
        result.setList(sysAreaList);
        result.setMsg("查询数据成功！");
        return result;
    }

    /**
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     *
     * @param sysArea
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "selectSysArea")
    public BaseOpMsg<SysArea> selectSysArea(SysArea sysArea) {

        BaseOpMsg<SysArea> result = new BaseOpMsg<>();
        if (sysArea != null) {
            List<SysArea> sysAreaList = this.sysAreaService.findListByRecord(sysArea);
            if (sysAreaList != null && !( sysAreaList.isEmpty())) {
                result.setCode(StatusCode.CURD_SELECT_SUCCESS);
                result.setStatus("success");
                result.setMsg("查询数据成功！");
                result.setList(sysAreaList);// 返回查询数据
            } else {
                result.setCode(StatusCode.CURD_SELECT_FAILURE);
                result.setStatus("error");
                result.setMsg("查询数据失败！");
                if (this.logger.isDebugEnabled()) {
                    result.setDescription(this.sysAreaService.getErrorInfo());
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
     * @param sysArea
     * @param opType
     *            操作类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"modify" })
    public BaseOpMsg modify(HttpServletRequest request, SysArea sysArea, String opType) {

        SysUser user = UserUtil.getInstance().getUser();
        BaseOpMsg result = new BaseOpMsg();
        if (opType != null && !"".equals(opType)) {
            if ("save".equals(opType)) {// 新增保存
                sysArea = this.initSysArea(sysArea, false, user);
                if (this.sysAreaService.insertRecord(sysArea) > 0) {
                    result.setCode(StatusCode.CURD_ADD_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("保存数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_ADD_FAILURE);
                    result.setStatus("error");
                    result.setMsg("保存数据失败！");
                    if (this.logger.isDebugEnabled()) {
                        result.setDescription(this.sysAreaService.getErrorInfo());
                    }
                }
            } else if ("update".equals(opType)) {// 修改保存
                sysArea = this.initSysArea(sysArea, true, user);
                if (this.sysAreaService.updateByPrimaryKeySelective(sysArea) > 0) {
                    result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("修改数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_UPDATE_FAILURE);
                    result.setStatus("error");
                    result.setMsg("修改数据失败！");
                    if (this.logger.isDebugEnabled()) {
                        result.setDescription(this.sysAreaService.getErrorInfo());
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
            // 刷新数据字典缓存
            SysCacheUtil.getInstance().refreshArea();
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

    private SysArea initSysArea(SysArea sysArea, boolean ifUpdate, SysUser user) {

        SysArea old = this.sysAreaService.selectByPrimaryKey(sysArea.getParentId());
        sysArea.setParentIds(old.getParentIds() + old.getId() + ",");
        if (!ifUpdate) {
            sysArea.setCreateBy(user.getId());
            sysArea.setCreateDate(new Date());
            sysArea.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        }
        sysArea.setUpdateBy(user.getId());
        sysArea.setUpdateDate(new Date());
        return sysArea;
    }

    /**
     * 根据ids 删除数据
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteSysArea")
    public BaseOpMsg deleteSysArea(String ids, String virtual) {

        BaseOpMsg result = new BaseOpMsg();
        if (ids != null && !"".equals(ids)) {
            int delNum = 0;
            if (virtual.equals("false")) {
                delNum = this.sysAreaService.deleteByPrimaryKeys(ids);
            } else {
                delNum = this.sysAreaService.deleteVirtualByPrimaryKeys(ids);
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
                    result.setDescription(this.sysAreaService.getErrorInfo());
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
    @RequestMapping(value = "treeSysArea")
    public BaseOpMsg treeSysArea(HttpServletRequest request, HttpServletResponse response, Model model,
            SysArea sysArea) {

        List<Map<String, Object>> sysAreaListMap = new ArrayList<>();
        List<SysArea> sysAreaList = new ArrayList<>();
        if (UserUtil.getInstance().isAdmin(null)) {// 管理员
            sysAreaList = this.sysAreaService.findListByRecord(sysArea);
        } else {
            // 登录人 所有角色所对应的菜单id 根据id 查出所有的菜单
            sysAreaList = this.sysAreaService.findListPermission(UserUtil.getInstance().getUserInfo(), sysArea);
        }

        StringBuilder ids = new StringBuilder(",");
        StringBuilder rootIds = new StringBuilder(",");
        for (int i = 0; i < sysAreaList.size(); i++) {
            ids.append(sysAreaList.get(i).getId() + ",");
        }
        for (int i = 0; i < sysAreaList.size(); i++) {
            if (ids.indexOf("," + sysAreaList.get(i).getParentId() + ",") == -1) {
                if (rootIds.indexOf("," + sysAreaList.get(i).getParentId() + ",") == -1) {
                    rootIds.append(sysAreaList.get(i).getParentId() + ",");
                }
            }
        }
        new ArrayList<>();
        String[] idStr = rootIds.toString().split(",");
        this.order = 0;
        for (int j = 1; j < idStr.length; j++) {
            sysAreaListMap.addAll(this.addTree(ParseUtil.getLong(idStr[j]), sysAreaList));
        }
        BaseOpMsg result = new BaseOpMsg();
        if (sysAreaListMap != null && !( sysAreaListMap.isEmpty())) {
            result.setCode(StatusCode.TREE_SUCCESS);
            result.setStatus("success");
            result.setList(sysAreaListMap);
            result.setMsg("树查询数据成功！");
        } else {
            result.setCode(StatusCode.TREE_FAILURE);
            result.setStatus("success");
            result.setMsg("树查询数据失败！");
            if (this.logger.isDebugEnabled()) {
                result.setDescription("");
            }
        }
        return result;
    }

    private List<Map<String, Object>> addTree(Long id, List<SysArea> sysAreaList) {

        Map<String, Object> map = null;
        List<Map<String, Object>> mapList = null;
        for (int i = 0; i < sysAreaList.size(); i++) {
            if (sysAreaList.get(i).getParentId().equals(id)) {
                if (mapList == null) {
                    mapList = new ArrayList<>();
                }
                map = new HashMap<>();
                map.put("text", sysAreaList.get(i).getName());
                map.put("value", sysAreaList.get(i).getId());
                map.put("parentValue", sysAreaList.get(i).getParentId());
                map.put("order", this.order);
                this.order++;
                List<Map<String, Object>> childMap = this.addTree(sysAreaList.get(i).getId(), sysAreaList);
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
     * @param sysArea
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "codeValid")
    public BaseValidatorMsg codeValid(SysArea sysArea) {

        BaseValidatorMsg result = new BaseValidatorMsg();
        if (StringLowUtils.isNotBlank(sysArea.getCode())) {
            if (sysArea.getId() == null) {
                SysAreaExample example = new SysAreaExample();
                example.or().andCodeEqualTo(sysArea.getCode()).andDelFlagEqualTo(BaseEntity.DEL_FLAG_NORMAL);
                List<SysArea> list = this.sysAreaService.findListByExample(example);
                if (list == null || list.size() <= 0) {
                    result.setValid(true);
                }
            } else {
                SysAreaExample example = new SysAreaExample();
                example.or().andCodeEqualTo(sysArea.getCode()).andIdNotEqualTo(sysArea.getId())
                        .andDelFlagEqualTo(BaseEntity.DEL_FLAG_NORMAL);
                List<SysArea> list = this.sysAreaService.findListByExample(example);
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