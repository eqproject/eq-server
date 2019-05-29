/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.sys.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bubi.basic.common.base.*;
import cn.bubi.basic.common.util.StringLowUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.bubi.basic.common.config.sysUtil.UserUtil;
import cn.bubi.basic.common.status.StatusCode;
import cn.bubi.basic.common.util.excel.ExcelUtil;
import cn.bubi.basic.modules.sys.entity.SysDepart;
import cn.bubi.basic.modules.sys.entity.SysUser;
import cn.bubi.basic.modules.sys.security.UserInfo;
import cn.bubi.basic.modules.sys.service.SysDepartService;
import cn.bubi.basic.modules.sys.service.SysFileService;

/**
 * 部门表Controller
 *
 * @author JoinHan
 * @version 2018-03-30
 */
@Controller
@RequestMapping(value = "/sys/sysDepart")
public class SysDepartController extends BaseController {

    @Autowired
    private SysDepartService sysDepartService;

    @Autowired
    private SysFileService sysFileService;

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
        return "modules/sys/sysDepartList";
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
     * @param sysDepart
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"dataList" })
    public BaseTableData dataList(HttpServletRequest request, HttpServletResponse response, SysDepart sysDepart) {

        Map<String, Object> params = new HashMap<>();
        // 取出 request 的参数信息分页信息 排序信息
        this.getInfoFromRequest(request, params);
        if (UserUtil.getInstance().isAdmin(null)) {// 管理员
            this.baseTableData = this.sysDepartService.findDataTableByRecordForPage(sysDepart, params);
        } else {
            // 登录人所属机构下的部门
            UserInfo userInfo = UserUtil.getInstance().getUserInfo();
            this.baseTableData = this.sysDepartService.findDataTableByRecordPermission(userInfo, sysDepart, params);
        }
        int draw = Integer.parseInt(request.getParameter("draw") == null ? "0" : request.getParameter("draw"));
        this.baseTableData.setDraw(++draw);
        return this.baseTableData;
    }

    /**
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     *
     * @param sysDepart
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "selectSysDepart")
    public BaseOpMsg<SysDepart> selectSysDepart(SysDepart sysDepart) {

        BaseOpMsg<SysDepart> result = new BaseOpMsg<>();
        if (sysDepart != null) {
            List<SysDepart> sysDepartList = this.sysDepartService.findListByRecord(sysDepart);
            if (sysDepartList != null && !( sysDepartList.isEmpty())) {
                result.setCode(StatusCode.CURD_SELECT_SUCCESS);
                result.setStatus("success");
                result.setMsg("查询数据成功！");
                result.setList(sysDepartList);// 返回查询数据
            } else {
                result.setCode(StatusCode.CURD_SELECT_FAILURE);
                result.setStatus("error");
                result.setMsg("查询数据失败！");
                if (this.logger.isDebugEnabled()) {
                    result.setDescription(this.sysDepartService.getErrorInfo());
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
     * @param sysDepart
     * @param opType
     *            操作类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"modify" })
    public BaseOpMsg modify(HttpServletRequest request, SysDepart sysDepart, String opType) {

        // SysUser user = UserUtil.getInstance().getUser();
        SysUser user = new SysUser();
        user.setId(1L);
        BaseOpMsg result = new BaseOpMsg();
        if (opType != null && !"".equals(opType)) {
            if ("save".equals(opType)) {// 新增保存
                sysDepart = this.initSysDepart(sysDepart, false, user);
                if (this.sysDepartService.insertRecord(sysDepart) > 0) {
                    result.setCode(StatusCode.CURD_ADD_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("保存数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_ADD_FAILURE);
                    result.setStatus("error");
                    result.setMsg("保存数据失败！");
                    if (this.logger.isDebugEnabled()) {
                        result.setDescription(this.sysDepartService.getErrorInfo());
                    }
                }
            } else if ("update".equals(opType)) {// 修改保存
                sysDepart = this.initSysDepart(sysDepart, true, user);
                if (this.sysDepartService.updateByPrimaryKeySelective(sysDepart) > 0) {
                    result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("修改数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_UPDATE_FAILURE);
                    result.setStatus("error");
                    result.setMsg("修改数据失败！");
                    if (this.logger.isDebugEnabled()) {
                        result.setDescription(this.sysDepartService.getErrorInfo());
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

    private SysDepart initSysDepart(SysDepart sysDepart, boolean ifUpdate, SysUser user) {

        if (!ifUpdate) {
            sysDepart.setCreateBy(user.getId());
            sysDepart.setCreateDate(new Date());
            sysDepart.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        }
        sysDepart.setUpdateBy(user.getId());
        sysDepart.setUpdateDate(new Date());
        return sysDepart;
    }

    /**
     * 根据ids 删除数据
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteSysDepart")
    public BaseOpMsg deleteSysDepart(String ids, String virtual) {

        BaseOpMsg result = new BaseOpMsg();
        if (ids != null && !"".equals(ids)) {
            int delNum = 0;
            if (virtual.equals("false")) {
                delNum = this.sysDepartService.deleteByPrimaryKeys(ids);
            } else {
                delNum = this.sysDepartService.deleteVirtualByPrimaryKeys(ids);
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
                    result.setDescription(this.sysDepartService.getErrorInfo());
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
     * 根据查询条件 导出查询所得数据
     *
     * @param
     * @return
     */
    @RequestMapping(value = "export")
    public void exportSysDepart(HttpServletRequest request, HttpServletResponse response, SysDepart sysDepart) {

        List<SysDepart> sysOfficeList = this.sysDepartService.findListByRecord(sysDepart);
        try {
            this.downHeadSetting(request, response, "部门.xls");
            ExcelUtil.exportList2ExcelByClass(response.getOutputStream(), sysOfficeList, SysDepart.class);
        } catch(Exception e) {
            logger.debug(e.getMessage());
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
    public BaseOpMsg importSysDepart(HttpServletRequest request, HttpServletResponse response,
            List<MultipartFile> fileUploadInput) {

        BaseOpMsg result = new BaseOpMsg();
        SysUser sysUser = UserUtil.getInstance().getUser();
        boolean ifSuccess = true;
        for (MultipartFile file : fileUploadInput) {
            BaseFile bf = this.sysFileService.saveFile(sysUser, "excel", file);
            if (bf == null) {
                result.setCode(StatusCode.UPLOAD_FILE_FAILURE);
                result.setStatus("error");
                result.setMsg("文件上传失败！");
                return result;
            }
            // 读取处理文件 读取后验证 再存储
            result = ExcelUtil.importExcel2ListByClass(bf.getFile(), SysDepart.class, 0, 0, null, false,
                    "sysDepartController");
            for (int i = 0; i < result.getList().size(); i++) {
                SysDepart sysDepart = (SysDepart) result.getList().get(i);
                // 部门名称不能重复，部门代码不能重复，所属机构必须存在
                if (sysDepart.getOfficeId() == null) {
                    ifSuccess = false;
                    result.setMsg("第" + ( i + 1) + "行，所属机构未找到！");
                    break;
                }
                this.initSysDepart(sysDepart, false, sysUser);
                this.sysDepartService.insertRecord(sysDepart);
            }
        }
        if (ifSuccess) {
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
}