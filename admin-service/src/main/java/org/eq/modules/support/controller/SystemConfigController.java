/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.support.controller;

import org.eq.basic.common.base.BaseController;
import org.eq.basic.common.base.BaseOpMsg;
import org.eq.basic.common.base.BaseTableData;
import org.eq.modules.support.entity.SystemConfig;
import org.eq.modules.support.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统配置Controller
 * @author kaka
 * @version 1.0.1
 */
@SuppressWarnings("all")
@Controller
@RequestMapping(value = "/support/config")
public class SystemConfigController extends BaseController {

	@Autowired
	private SystemConfigService systemConfigService;

	/**
	 * List页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"list", ""})
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		//下拉选查询 自定义内容需要手动添加
		return "modules/c2c/support/configList";
	}

	/**
	 * @param request
	 * @param response
	 * @param systemConfig
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"dataList"})
	public BaseTableData dataList(HttpServletRequest request, HttpServletResponse response, SystemConfig systemConfig) {
		Map<String,Object> params = new HashMap<>();
		//取出 request 的参数信息分页信息 排序信息
		getInfoFromRequest(request,params);
		//数据的draw自增
		baseTableData = systemConfigService.findDataTableByRecordForPage(systemConfig,params);
		int draw = Integer.parseInt(request.getParameter("draw")==null?"0":request.getParameter("draw"));
		baseTableData.setDraw(++draw);
		return baseTableData;
	}

	/**
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     * @param systemConfig
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "getSystemConfig")
    public BaseOpMsg<SystemConfig> getSystemConfig(long id) {
        BaseOpMsg<SystemConfig> result = new BaseOpMsg<SystemConfig>();
        result.setStatus("error");
        if(id<=0){
            result.setMsg("查询条件为空");
            return result;
        }
        SystemConfig systemConfig = systemConfigService.selectByPrimaryKey(id);
        if(systemConfig==null){
            result.setMsg("查询数据失败！");
            return result;
        }
        result.setStatus("success");
        result.setObject(systemConfig);
        return result;
    }




}