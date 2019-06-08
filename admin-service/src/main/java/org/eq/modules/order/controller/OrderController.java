/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.controller;

import org.eq.basic.common.base.BaseController;
import org.eq.basic.common.base.BaseOpMsg;
import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.config.sysUtil.UserUtil;
import org.eq.basic.common.status.StatusCode;
import org.eq.basic.modules.sys.entity.SysUser;
import org.eq.basic.modules.sys.service.SysUserInfoService;
import org.eq.modules.enums.OrderAdStateEnum;
import org.eq.modules.order.entity.OrderAd;
import org.eq.modules.order.service.OrderAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 商品信息Controller
 * @author kaka
 * @version 2019.05.08
 */
@SuppressWarnings("all")
@Controller
@RequestMapping(value = "/order")
public class OrderController extends BaseController {

	@Autowired
	private OrderAdService orderAdService;

    @Autowired
    private SysUserInfoService sysUserInfoService;

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
		return "modules/c2c/product/list";
	}

	/**
	 * @param request
	 * @param response
	 * @param product
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"dataList"})
	public BaseTableData dataList(HttpServletRequest request, HttpServletResponse response, OrderAd orderAd) {
		Map<String,Object> params = new HashMap<>();
		getInfoFromRequest(request,params);
		String orderDir = request.getParameter("orderDir");
		String orderName = request.getParameter("orderName");
        params.put("orderDir",orderDir);
        params.put("orderName",orderName);

		baseTableData = orderAdService.findDataTableByRecordForPage(orderAd,params);
		int draw = Integer.parseInt(request.getParameter("draw")==null?"0":request.getParameter("draw"));
		baseTableData.setDraw(++draw);
		return baseTableData;
	}

	/**
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     * @param product
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "countOrder")
    public BaseOpMsg<Integer> countOrder(long productId) {
        BaseOpMsg<Integer> result = new BaseOpMsg();
        if(productId<=0){
            result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
            result.setStatus("error");
            result.setMsg("查询数据失败！");
            return  result;
        }
        OrderAd  countAd = new OrderAd();
        countAd.setProductId(productId);
        countAd.setStatus(OrderAdStateEnum.ORDER_TRADEING.getState());
        int ocunt = 0 ;
        try{
            ocunt = orderAdService.countByRecord(countAd);
        }catch (Exception e){
            ocunt  =-1;
        }
        if(ocunt>=0){
            result.setCode(StatusCode.CURD_SELECT_SUCCESS);
        }else{
            result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
        }
        result.setStatus("success");
        result.setMsg("查询数据成功！");
        result.setObject(ocunt);
        return result;
    }



	/**
     * 保存or修改操作
     * @param request
     * @param product
     * @param opType 操作类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"modify"})
    public BaseOpMsg modify(HttpServletRequest request, OrderAd orderAd, String opType) {
        SysUser user = UserUtil.getInstance().getUser();
        BaseOpMsg result = new BaseOpMsg();
        if (opType != null && !"".equals(opType)) {
            if ("save".equals(opType)) {//新增保存
                orderAd = initProduct(orderAd, false, user);
                if (orderAdService.insertRecord(orderAd) > 0) {
                    result.setCode(StatusCode.CURD_ADD_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("保存数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_ADD_FAILURE);
                    result.setStatus("error");
                    result.setMsg("保存数据失败！");
                    if (logger.isDebugEnabled()) {
                        result.setDescription(orderAdService.getErrorInfo());
                    }
                }
            } else if ("update".equals(opType)) {//修改保存
                orderAd = initProduct(orderAd, true, user);
                if (orderAdService.updateByPrimaryKeySelective(orderAd) > 0) {
                    result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("修改数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_UPDATE_FAILURE);
                    result.setStatus("error");
                    result.setMsg("修改数据失败！");
                    if (logger.isDebugEnabled()) {
                        result.setDescription(orderAdService.getErrorInfo());
                    }
                }
            } else {
                result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
                result.setStatus("error");
                result.setMsg("操作违法！");
                if (logger.isDebugEnabled()) {
                    result.setDescription("opType 违法");
                }
            }
        } else {
            result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
            result.setStatus("error");
            result.setMsg("操作违法！");
            if (logger.isDebugEnabled()) {
                result.setDescription("opType 违法 或 null");
            }
        }
        return result;
    }

	private OrderAd initProduct(OrderAd orderAd, boolean ifUpdate, SysUser user) {
        if (!ifUpdate) {
            orderAd.setCreateDate(new Date());
        }
        orderAd.setUpdateDate(new Date());
        return orderAd;
    }

    /**
     * 根据ids 删除数据
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteProduct")
    public BaseOpMsg deleteProduct(String ids, String virtual) {
        BaseOpMsg result = new BaseOpMsg();
        if (ids != null && !"".equals(ids)) {
            int delNum = 0;
            if (virtual.equals("false")) {
                delNum = orderAdService.deleteByPrimaryKeys(ids);
            } else {
                delNum = orderAdService.deleteVirtualByPrimaryKeys(ids);
            }
            if (delNum > 0) {
                result.setCode(StatusCode.CURD_DELETE_SUCCESS);
                result.setStatus("success");
                result.setMsg("删除数据成功！");
            } else {
                result.setCode(StatusCode.CURD_DELETE_FAILURE);
                result.setStatus("error");
                result.setMsg("删除数据失败！");
                if (logger.isDebugEnabled()) {
                    result.setDescription(orderAdService.getErrorInfo());
                }
            }
        } else {
            result.setCode(StatusCode.CURD_DELETE_FAILURE);
            result.setStatus("error");
            result.setMsg("删除数据失败！");
            if (logger.isDebugEnabled()) {
                result.setDescription("数据ids为空");
            }
        }
        return result;
    }

}