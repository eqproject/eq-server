/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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

import org.eq.basic.common.config.sysUtil.SysCacheUtil;
import org.eq.basic.common.config.sysUtil.UserUtil;
import org.eq.basic.common.status.StatusCode;
import org.eq.basic.modules.sys.entity.SysArea;
import org.eq.basic.modules.sys.entity.SysDict;
import org.eq.basic.modules.sys.entity.SysOffice;
import org.eq.basic.modules.sys.entity.SysUser;
import org.eq.basic.modules.sys.service.SysDictService;

/**
 * 数据字典表Controller
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Controller
@RequestMapping(value = "/sys/sysDict")
public class SysDictController extends BaseController {

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
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {

        // 下拉选查询 自定义内容需要手动添加
        List<Map> typeList = this.sysDictService.findDictType();
        model.addAttribute("typeList", typeList);
        return "modules/sys/sysDictList";
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
     * @param sysDict
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"dataList" })
    public BaseTableData dataList(HttpServletRequest request, HttpServletResponse response, SysDict sysDict) {

        Map<String, Object> params = new HashMap<>();
        // 取出 request 的参数信息分页信息 排序信息
        this.getInfoFromRequest(request, params);
        // 数据的draw自增
        this.baseTableData = this.sysDictService.findDataTableByRecordForPage(sysDict, params);
        int draw = Integer.parseInt(request.getParameter("draw") == null ? "0" : request.getParameter("draw"));
        this.baseTableData.setDraw(++draw);
        return this.baseTableData;
    }

    /**
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     *
     * @param sysDict
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "selectSysDict")
    public BaseOpMsg<SysDict> selectSysDict(SysDict sysDict) {

        BaseOpMsg<SysDict> result = new BaseOpMsg<>();
        if (sysDict != null) {
            List<SysDict> sysDictList = this.sysDictService.findListByRecord(sysDict);
            if (sysDictList != null && !( sysDictList.isEmpty())) {
                result.setCode(StatusCode.CURD_SELECT_SUCCESS);
                result.setStatus("success");
                result.setMsg("查询数据成功！");
                result.setList(sysDictList);// 返回查询数据
            } else {
                result.setCode(StatusCode.CURD_SELECT_FAILURE);
                result.setStatus("error");
                result.setMsg("查询数据失败！");
                if (this.logger.isDebugEnabled()) {
                    result.setDescription(this.sysDictService.getErrorInfo());
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
     * @param sysDict
     * @param opType
     *            操作类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"modify" })
    public BaseOpMsg modify(HttpServletRequest request, SysDict sysDict, String opType) {

        SysUser user = UserUtil.getInstance().getUser();
        BaseOpMsg result = new BaseOpMsg();
        if (opType != null && !"".equals(opType)) {
            if ("save".equals(opType)) {// 新增保存
                sysDict = this.initSysDict(sysDict, false, user);
                if (this.sysDictService.insertRecord(sysDict) > 0) {
                    result.setCode(StatusCode.CURD_ADD_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("保存数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_ADD_FAILURE);
                    result.setStatus("error");
                    result.setMsg("保存数据失败！");
                    if (this.logger.isDebugEnabled()) {
                        result.setDescription(this.sysDictService.getErrorInfo());
                    }
                }
            } else if ("update".equals(opType)) {// 修改保存
                sysDict = this.initSysDict(sysDict, true, user);
                if (this.sysDictService.updateByPrimaryKeySelective(sysDict) > 0) {
                    result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("修改数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_UPDATE_FAILURE);
                    result.setStatus("error");
                    result.setMsg("修改数据失败！");
                    if (this.logger.isDebugEnabled()) {
                        result.setDescription(this.sysDictService.getErrorInfo());
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
            SysCacheUtil.getInstance().refreshDict();
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

    private SysDict initSysDict(SysDict sysDict, boolean ifUpdate, SysUser user) {

        if (!ifUpdate) {
            sysDict.setCreateBy(user.getId());
            sysDict.setCreateDate(new Date());
            sysDict.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        }
        sysDict.setUpdateBy(user.getId());
        sysDict.setUpdateDate(new Date());
        return sysDict;
    }

    /**
     * 根据ids 删除数据
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteSysDict")
    public BaseOpMsg deleteSysDict(String ids, String virtual) {

        BaseOpMsg result = new BaseOpMsg();
        if (ids != null && !"".equals(ids)) {
            int delNum = 0;
            if (virtual.equals("false")) {
                delNum = this.sysDictService.deleteByPrimaryKeys(ids);
            } else {
                delNum = this.sysDictService.deleteVirtualByPrimaryKeys(ids);
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
                    result.setDescription(this.sysDictService.getErrorInfo());
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
     * 用于页面展示字典数据，机构数据，地区数据等
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"getDictionary" })
    public Map<String, Map<String, String>> getDictionary(HttpServletRequest request) {

        Map<String, Map<String, String>> dictData = new HashMap<>();
        // 系统数据字典
        List<SysDict> dictList = SysCacheUtil.getInstance().getDictListCache();
        new HashSet<String>();
        for (SysDict dicts : dictList) {
            if (dicts.getType() != null && !"".equals(dicts.getType())) {
                String type = dicts.getType();
                if (dictData.get(type) == null) {
                    Map list = new HashMap<String, String>();
                    list.put(dicts.getValue(), dicts.getLabel());
                    dictData.put(type, list);
                } else {
                    Map list = dictData.get(type);
                    list.put(dicts.getValue(), dicts.getLabel());
                    dictData.put(type, list);
                }
            }
        }
        // 机构字典
        List<SysOffice> officeList = SysCacheUtil.getInstance().getOfficeListCache();
        Map officeMap = new HashMap<String, String>();
        for (SysOffice sysOffice : officeList) {
            officeMap.put(sysOffice.getId() + "", sysOffice.getName());
            dictData.put("office", officeMap);
        }
        // 地区字典
        List<SysArea> areaList = SysCacheUtil.getInstance().getAreaListCache();
        Map areaMap = new HashMap<String, String>();
        for (SysArea sysArea : areaList) {
            areaMap.put(sysArea.getId() + "", sysArea.getName());
            dictData.put("area", areaMap);
        }
        return dictData;
    }
}