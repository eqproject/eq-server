/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eq.basic.common.base.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import org.eq.basic.common.config.sysUtil.SysCacheUtil;
import org.eq.basic.common.config.sysUtil.UserUtil;
import org.eq.basic.common.status.StatusCode;
import org.eq.basic.modules.sys.entity.SysDict;
import org.eq.basic.modules.sys.entity.SysFile;
import org.eq.basic.modules.sys.entity.SysUser;
import org.eq.basic.modules.sys.service.SysFileService;

/**
 * 附件表Controller
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Controller
@RequestMapping(value = "/sys/sysFile")
public class SysFileController extends BaseController {

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
        List<SysDict> compareTypeList = SysCacheUtil.getInstance().getDictMapCache().get("compareType");
        List<SysDict> sizeTypeList = SysCacheUtil.getInstance().getDictMapCache().get("sizeType");
        model.addAttribute("sizeTypeList", sizeTypeList);
        model.addAttribute("compareTypeList", compareTypeList);
        return "modules/sys/sysFileList";
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
     * @param sysFile
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"dataList" })
    public BaseTableData dataList(HttpServletRequest request, HttpServletResponse response, SysFile sysFile) {

        Map<String, Object> params = new HashMap<>();
        // 取出 request 的参数信息分页信息 排序信息
        this.getInfoFromRequest(request, params);
        // 数据的draw自增
        this.baseTableData = this.sysFileService.findDataTableByRecordForPage(sysFile, params);
        int draw = Integer.parseInt(request.getParameter("draw") == null ? "0" : request.getParameter("draw"));
        this.baseTableData.setDraw(++draw);
        return this.baseTableData;
    }

    @Override
    protected void getInfoFromRequest(HttpServletRequest request, Map<String, Object> params) {

        super.getInfoFromRequest(request, params);
        String compare = request.getParameter("compare");
        params.put("compare", compare);
    }

    /**
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     *
     * @param sysFile
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "selectSysFile")
    public BaseOpMsg<SysFile> selectSysFile(SysFile sysFile) {

        BaseOpMsg<SysFile> result = new BaseOpMsg<>();
        if (sysFile != null) {
            List<SysFile> sysFileList = this.sysFileService.findListByRecord(sysFile);
            if (sysFileList != null && !( sysFileList.isEmpty())) {
                result.setCode(StatusCode.CURD_SELECT_SUCCESS);
                result.setStatus("success");
                result.setMsg("查询数据成功！");
                result.setList(sysFileList);// 返回查询数据
            } else {
                result.setCode(StatusCode.CURD_SELECT_FAILURE);
                result.setStatus("error");
                result.setMsg("查询数据失败！");
                if (this.logger.isDebugEnabled()) {
                    result.setDescription(this.sysFileService.getErrorInfo());
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
     * @param sysFile
     * @param opType
     *            操作类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"modify" })
    public BaseOpMsg modify(HttpServletRequest request, SysFile sysFile, String opType, MultipartFile file) {

        SysUser user = UserUtil.getInstance().getUser();
        BaseOpMsg result = new BaseOpMsg();
        if (opType != null && !"".equals(opType)) {
            if ("save".equals(opType)) {// 新增保存
                sysFile = this.initSysFile(sysFile, false, user);
                if (this.sysFileService.saveFile(sysFile, null, file) != null) {
                    result.setCode(StatusCode.CURD_ADD_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("保存数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_ADD_FAILURE);
                    result.setStatus("error");
                    result.setMsg("保存数据失败！");
                    if (this.logger.isDebugEnabled()) {
                        result.setDescription(this.sysFileService.getErrorInfo());
                    }
                }
            } else if ("update".equals(opType)) {// 修改保存
                sysFile = this.initSysFile(sysFile, true, user);
                if (this.sysFileService.updateFile(sysFile, null, file) != null) {
                    result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("修改数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_UPDATE_FAILURE);
                    result.setStatus("error");
                    result.setMsg("修改数据失败！");
                    if (this.logger.isDebugEnabled()) {
                        result.setDescription(this.sysFileService.getErrorInfo());
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

    private SysFile initSysFile(SysFile sysFile, boolean ifUpdate, SysUser user) {

        if (!ifUpdate) {
            sysFile.setCreateBy(user.getId());
            sysFile.setCreateDate(new Date());
            sysFile.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        }
        sysFile.setUpdateBy(user.getId());
        sysFile.setUpdateDate(new Date());
        return sysFile;
    }

    /**
     * 根据ids 删除数据
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteSysFile")
    public BaseOpMsg deleteSysFile(String ids, String virtual) {

        BaseOpMsg result = new BaseOpMsg();
        if (ids != null && !"".equals(ids)) {
            int delNum = 0;
            if (virtual.equals("false")) {
                delNum = this.sysFileService.deleteByPrimaryKeys(ids);
            } else {
                delNum = this.sysFileService.deleteVirtualByPrimaryKeys(ids);
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
                    result.setDescription(this.sysFileService.getErrorInfo());
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

    @RequestMapping(value = "/download/**")
    public void download(HttpServletRequest request, HttpServletResponse response) {

        this.logger.debug("file download start...");
        String path = request.getRequestURI();
        // Url与文件位置转换
        BaseFile downloadFile = this.sysFileService.downloadFile(path);
        byte[] buff = new byte[1024];
        FileInputStream bis = null;
        try (OutputStream os = response.getOutputStream()) {
            response.setContentType("application/octet-stream");
            String name = downloadFile.getSysFile().getFileName();
            if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > -1) {
                name = URLEncoder.encode(name, "UTF-8");
            } else {
                name = new String(name.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setHeader("content-disposition", "attachment;filename=\"" + name + "\"");

            bis = new FileInputStream(downloadFile.getFile());
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, i);
                os.flush();
                i = bis.read(buff);
            }
            response.setHeader("Content-Length", String.valueOf(bis.getChannel().size()));
        } catch(IOException e) {
            logger.debug(e.getMessage());
        }finally{
            if(bis!=null){
                try {
                    bis.close();
                } catch(IOException e) {
                    logger.debug(e.getMessage());
                }
            }
        }
    }

    @RequestMapping(value = "/downloadInside/**")
    public void downloadInside(HttpServletRequest request, HttpServletResponse response) {

        this.logger.debug("file download start...");
        String path = request.getRequestURI();
        // Url与文件位置转换
        InputStream inputStream = this.sysFileService.downloadJarFile(path);
        byte[] buff = new byte[1024];
        try (OutputStream os = response.getOutputStream()) {
            response.setContentType("application/octet-stream");
            String name = path.substring(path.lastIndexOf("/") + 1, path.length());
            if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > -1) {
                name = URLEncoder.encode(name, "UTF-8");
            } else {
                name = new String(name.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setHeader("content-disposition", "attachment;filename=\"" + name + "\"");

            int i = inputStream.read(buff);
            while (i != -1) {
                os.write(buff, 0, i);
                os.flush();
                i = inputStream.read(buff);
            }
        } catch(IOException e) {
            logger.debug(e.getMessage());
            ;
        }
    }
}