/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.controller;

import org.eq.basic.common.base.*;
import org.eq.basic.common.config.sysUtil.SysCacheUtil;
import org.eq.basic.common.config.sysUtil.UserUtil;
import org.eq.basic.common.status.StatusCode;
import org.eq.basic.common.util.ParseUtil;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.basic.common.util.excel.ExcelUtil;
import org.eq.basic.modules.sys.entity.SysDict;
import org.eq.basic.modules.sys.entity.SysOffice;
import org.eq.basic.modules.sys.entity.SysOfficeExample;
import org.eq.basic.modules.sys.entity.SysUser;
import org.eq.basic.modules.sys.service.SysFileService;
import org.eq.basic.modules.sys.service.SysOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 机构表Controller
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Controller
@RequestMapping(value = "/sys/sysOffice")
public class SysOfficeController extends BaseController {

    @Autowired
    private SysOfficeService sysOfficeService;

    @Autowired
    private SysFileService sysFileService;

    private int order = 0;// 机构排序变量

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
        List<SysDict> officeTypeList = SysCacheUtil.getInstance().getDictMapCache().get("officeType");
        model.addAttribute("officeTypeList", officeTypeList);
        return "modules/sys/sysOfficeList";
    }

    @ResponseBody
    @RequestMapping(value = {"dataList" })
    public BaseOpMsg dataList(HttpServletRequest request, HttpServletResponse response, Model model,
            SysOffice sysOffice) {

        List<SysOffice> sysOfficeList = new ArrayList<>();
        if (UserUtil.getInstance().isAdmin(null)) {// 管理员
            sysOfficeList = this.sysOfficeService.findListByRecord(sysOffice);
        } else {
            // 登录人 所有角色所对应的菜单id 根据id 查出所有的菜单
            sysOfficeList = this.sysOfficeService.findListPermission(UserUtil.getInstance().getUserInfo(), sysOffice);
        }

        BaseOpMsg result = new BaseOpMsg();
        result.setCode(StatusCode.CURD_SELECT_SUCCESS);
        result.setStatus("success");
        result.setList(sysOfficeList);
        result.setMsg("查询数据成功！");
        return result;
    }

    /**
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     *
     * @param sysOffice
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "selectSysOffice")
    public BaseOpMsg<SysOffice> selectSysOffice(SysOffice sysOffice) {

        BaseOpMsg<SysOffice> result = new BaseOpMsg<>();
        if (sysOffice != null) {
            List<SysOffice> sysOfficeList = this.sysOfficeService.findListByRecord(sysOffice);
            if (sysOfficeList != null && !( sysOfficeList.isEmpty())) {
                result.setCode(StatusCode.CURD_SELECT_SUCCESS);
                result.setStatus("success");
                result.setMsg("查询数据成功！");
                result.setList(sysOfficeList);// 返回查询数据
            } else {
                result.setCode(StatusCode.CURD_SELECT_FAILURE);
                result.setStatus("error");
                result.setMsg("查询数据失败！");
                if (this.logger.isDebugEnabled()) {
                    result.setDescription(this.sysOfficeService.getErrorInfo());
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
     * @param sysOffice
     * @param opType
     *            操作类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"modify" })
    public BaseOpMsg modify(HttpServletRequest request, SysOffice sysOffice, String opType) {

        SysUser user = UserUtil.getInstance().getUser();
        BaseOpMsg result = new BaseOpMsg();
        if (opType != null && !"".equals(opType)) {
            if ("save".equals(opType)) {// 新增保存
                sysOffice = this.initSysOffice(sysOffice, false, user);
                if (this.sysOfficeService.insertRecord(sysOffice) > 0) {
                    result.setCode(StatusCode.CURD_ADD_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("保存数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_ADD_FAILURE);
                    result.setStatus("error");
                    result.setMsg("保存数据失败！");
                    if (this.logger.isDebugEnabled()) {
                        result.setDescription(this.sysOfficeService.getErrorInfo());
                    }
                }
            } else if ("update".equals(opType)) {// 修改保存
                sysOffice = this.initSysOffice(sysOffice, true, user);
                if (this.sysOfficeService.updateByPrimaryKeySelective(sysOffice) > 0) {
                    result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("修改数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_UPDATE_FAILURE);
                    result.setStatus("error");
                    result.setMsg("修改数据失败！");
                    if (this.logger.isDebugEnabled()) {
                        result.setDescription(this.sysOfficeService.getErrorInfo());
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

    private SysOffice initSysOffice(SysOffice sysOffice, boolean ifUpdate, SysUser user) {

        SysOffice old = this.sysOfficeService.selectByPrimaryKey(sysOffice.getParentId());
        sysOffice.setParentIds(old.getParentIds() + old.getId() + ",");
        if (!ifUpdate) {
            sysOffice.setCreateBy(user.getId());
            sysOffice.setCreateDate(new Date());
            sysOffice.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        }
        sysOffice.setUpdateBy(user.getId());
        sysOffice.setUpdateDate(new Date());
        return sysOffice;
    }

    /**
     * 根据ids 删除数据
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteSysOffice")
    public BaseOpMsg deleteSysOffice(String ids, String virtual) {

        BaseOpMsg result = new BaseOpMsg();
        if (ids != null && !"".equals(ids)) {
            int delNum = 0;
            if (virtual.equals("false")) {
                delNum = this.sysOfficeService.deleteByPrimaryKeys(ids);
            } else {
                delNum = this.sysOfficeService.deleteVirtualByPrimaryKeys(ids);
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
                    result.setDescription(this.sysOfficeService.getErrorInfo());
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
    @RequestMapping(value = "treeSysOffice")
    public BaseOpMsg treeSysOffice(HttpServletRequest request, HttpServletResponse response, Model model,
            SysOffice sysOffice) {

        List<Map<String, Object>> sysOfficeListMap = new ArrayList<>();

        List<SysOffice> sysOfficeList = new ArrayList<>();
        if (UserUtil.getInstance().isAdmin(null)) {// 管理员
            sysOfficeList = this.sysOfficeService.findListByRecord(sysOffice);
        } else {
            // 登录人 所有角色所对应的菜单id 根据id 查出所有的菜单
            sysOfficeList = this.sysOfficeService.findListPermission(UserUtil.getInstance().getUserInfo(), sysOffice);
        }

        // 机构根节点处理
        StringBuilder ids = new StringBuilder(",");
        StringBuilder rootIds = new StringBuilder(",");
        for (int i = 0; i < sysOfficeList.size(); i++) {
            ids.append(sysOfficeList.get(i).getId() + ",");
        }
        for (int i = 0; i < sysOfficeList.size(); i++) {
            if (ids.indexOf("," + sysOfficeList.get(i).getParentId() + ",") == -1) {
                if (rootIds.indexOf("," + sysOfficeList.get(i).getParentId() + ",") == -1) {
                    rootIds.append(sysOfficeList.get(i).getParentId() + ",");
                }
            }
        }
        String[] idStr = rootIds.toString().split(",");
        this.order = 0;
        for (int j = 1; j < idStr.length; j++) {
            sysOfficeListMap.addAll(this.addTree(ParseUtil.getLong(idStr[j]), sysOfficeList));
        }
        BaseOpMsg result = new BaseOpMsg();
        if (sysOfficeListMap != null && !( sysOfficeListMap.isEmpty())) {
            result.setCode(StatusCode.TREE_SUCCESS);
            result.setStatus("success");
            result.setList(sysOfficeListMap);
            result.setMsg("树查询数据成功！");
        } else {
            result.setCode(StatusCode.TREE_FAILURE);
            result.setStatus("error");
            result.setMsg("树查询数据失败！");
            if (this.logger.isDebugEnabled()) {
                result.setDescription("");
            }
        }
        return result;
    }

    private List<Map<String, Object>> addTree(Long id, List<SysOffice> sysOfficeList) {

        Map<String, Object> map = null;
        List<Map<String, Object>> mapList = null;
        // 根据code排序
        List<SysOffice> childOffices = new ArrayList<>();
        for (int i = 0; i < sysOfficeList.size(); i++) {
            if (sysOfficeList.get(i).getParentId().equals(id)) {
                childOffices.add(sysOfficeList.get(i));
            }
        }
        Collections.sort(childOffices, new Comparator<SysOffice>() {

            @Override
            public int compare(SysOffice o1, SysOffice o2) {

                return o1.getCode().compareTo(o2.getCode());
            }
        });

        for (int i = 0; i < childOffices.size(); i++) {
            if (mapList == null) {
                mapList = new ArrayList<>();
            }
            map = new HashMap<>();
            map.put("text", childOffices.get(i).getName());
            map.put("value", childOffices.get(i).getId());
            map.put("code", childOffices.get(i).getCode());
            map.put("parentId", childOffices.get(i).getParentId());
            map.put("order", this.order);
            map.put("areaId", childOffices.get(i).getAreaId());
            map.put("type", childOffices.get(i).getType());
            this.order++;
            List<Map<String, Object>> childMap = this.addTree(childOffices.get(i).getId(), sysOfficeList);
            if (childMap != null) {
                map.put("nodes", childMap);
            }
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 验证code 唯一性
     *
     * @param sysOffice
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "codeValid")
    public BaseValidatorMsg codeValid(SysOffice sysOffice) {

        BaseValidatorMsg result = new BaseValidatorMsg();
        if (StringLowUtils.isNotBlank(sysOffice.getCode())) {
            if (sysOffice.getId() == null) {
                SysOfficeExample example = new SysOfficeExample();
                example.or().andCodeEqualTo(sysOffice.getCode()).andDelFlagEqualTo(BaseEntity.DEL_FLAG_NORMAL);
                List<SysOffice> list = this.sysOfficeService.findListByExample(example);
                if (list == null || list.size() <= 0) {
                    result.setValid(true);
                }
            } else {
                SysOfficeExample example = new SysOfficeExample();
                example.or().andCodeEqualTo(sysOffice.getCode()).andIdNotEqualTo(sysOffice.getId())
                        .andDelFlagEqualTo(BaseEntity.DEL_FLAG_NORMAL);
                List<SysOffice> list = this.sysOfficeService.findListByExample(example);
                if (list == null || list.size() <= 0) {
                    result.setValid(true);
                }
            }
        } else {
            result.setValid(false);
        }
        return result;
    }

    /**
     * 根据查询条件 导出查询所得数据
     *
     * @param
     * @return
     */
    @RequestMapping(value = "export")
    public void exportSysOffice(HttpServletRequest request, HttpServletResponse response, SysOffice sysOffice) {

        List<SysOffice> sysOfficeList = this.sysOfficeService.findListByRecord(sysOffice);
        try {
            this.downHeadSetting(request, response, "机构.xls");
            ExcelUtil.exportList2ExcelByClass(response.getOutputStream(), sysOfficeList, SysOffice.class);
        } catch(Exception e) {
            logger.debug(e.getMessage());
            ;
        }
    }

    /**
     * 导入数据
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "import")
    public BaseOpMsg importSysOffice(HttpServletRequest request, HttpServletResponse response,
            List<MultipartFile> fileUploadInput) {

        BaseOpMsg result = new BaseOpMsg();
        SysUser sysUser = UserUtil.getInstance().getUser();
        boolean ifSuccess = true;
        for (MultipartFile file : fileUploadInput) {
            BaseFile bf = this.sysFileService.saveFile(sysUser, "excel", file);
            if (bf == null) {
                ifSuccess = false;
                result.setCode(StatusCode.UPLOAD_FILE_FAILURE);
                result.setStatus("error");
                result.setMsg("文件上传失败！");
            } else {
                // 读取处理文件 由于机构有父级先后顺序 只能边读边写
                result = ExcelUtil.importExcel2ListByClass(bf.getFile(), SysOffice.class, 0, 0, null, true,
                        "sysOfficeController");
                if ("error".equals(result.getStatus())) {
                    return result;
                }
            }

        }
        if (ifSuccess) {
            // 刷新系统缓存
            SysCacheUtil.getInstance().refreshOffice();
            result.setCode(StatusCode.UPLOAD_FILE_SUCCESS);
            result.setStatus("success");
            result.setMsg("文件上传成功！");
        } else {
            if (!StringLowUtils.isNotBlank(result.getMsg())) {
                result.setMsg("文件上传失败,未知错误！");
            }
            result.setCode(StatusCode.UPLOAD_FILE_FAILURE);
            result.setStatus("error");
        }
        return result;
    }

    @Override
    public String validateObject(Object object, SysUser sysUser) {

        SysOffice sysOffice = (SysOffice) object;
        // 机构名称不能重复，机构代码不能重复，机构类型必须存在，地区必需存在
        if (!this.codeValid(sysOffice).getValid()) {
            return "机构代码不能重复！";
        }
        ;
        if (sysOffice.getType() == null) {
            return "机构类型未找到或为空且！";

        }
        if (sysOffice.getParentId() == null) {
            return "父级机构未找到！";
        }
        if (sysOffice.getAreaId() == null) {
            return "地区未找到！";
        }
        this.initSysOffice(sysOffice, false, sysUser);
        this.sysOfficeService.insertRecord(sysOffice);
        return null;
    }
}