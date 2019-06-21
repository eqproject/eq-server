/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.base.BaseController;
import org.eq.basic.common.base.BaseOpMsg;
import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.config.sysUtil.UserUtil;
import org.eq.basic.common.status.StatusCode;
import org.eq.basic.common.util.DateUtil;
import org.eq.basic.modules.sys.entity.SysUser;
import org.eq.basic.modules.sys.service.SysUserInfoService;
import org.eq.modules.enums.OrderAdStateEnum;
import org.eq.modules.order.entity.OrderAd;
import org.eq.modules.order.entity.OrderAdExample;
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
import java.util.List;
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
		return "modules/c2c/order/list";
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

        try{
            Date startTime  =null;
            Date endTime = null;
            if(!StringUtils.isEmpty(request.getParameter("beginCreateDate"))){
                String temp = request.getParameter("beginCreateDate").trim() +" 00:00:00";
                startTime = DateUtil.paseTimeStr(temp);
            }
            if(!StringUtils.isEmpty(request.getParameter("endCreateDate"))){
                String temp = request.getParameter("endCreateDate").trim()+" 23:59:59";
                endTime = DateUtil.paseTimeStr(temp);

            }
            params.put("startTime",startTime);
            params.put("endTime",endTime);

        }catch (Exception e){
            logger.error("UserOrderController dataList 格式化时间异常",e);
        }

		baseTableData = orderAdService.findDataTableByRecordForPage(orderAd,params);
		if(baseTableData!=null && CollectionUtils.isEmpty(baseTableData.getData())){
		    List<OrderAd> datalist = baseTableData.getData();

        }
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
     * 查询订单数据
     * @param product
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "selectOrder")
    public BaseOpMsg<OrderAd> selectOrder(long orderId) {
        BaseOpMsg<OrderAd> result = new BaseOpMsg();
        OrderAdExample example = new OrderAdExample();
        OrderAdExample.Criteria ca = example.or();

        OrderAd orderAd =null;
        try {
            if (orderId > 0) {
                ca.andIdEqualToForAll(orderId);
                List<OrderAd> list  = orderAdService.findListByExample(example);
                if(!CollectionUtils.isEmpty(list)){
                    orderAd = list.get(0);
                }
            }
        }catch (Exception e){
           e.printStackTrace();
        }
        if(orderAd!=null){
            result.setCode(StatusCode.CURD_SELECT_SUCCESS);
        }else{
            result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
        }
        result.setStatus("success");
        result.setMsg("查询数据成功！");
        result.setObject(orderAd);
        return result;
    }


    /**
     * 关闭订单
     * @param product
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "close")
    public BaseOpMsg<OrderAd> close(long orderId) {
        BaseOpMsg<OrderAd> result = new BaseOpMsg();

        OrderAd orderAd =null;
        try {
            if (orderId > 0) {
                orderAd = orderAdService.selectByPrimaryKey(orderId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(orderAd==null){
            result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
            result.setMsg("订单不存在");
            return result;
        }
        boolean closeResult = orderAdService.cacelOrderAd(orderId);
        if(closeResult){
            result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
        }else{
            result.setCode(StatusCode.CURD_DELETE_FAILURE);
        }
        result.setStatus("success");
        result.setMsg("操作成功！");
        result.setObject(orderAd);
        return result;
    }


    /**
     * 查询订单数据
     * @param product
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "audit")
    public BaseOpMsg<OrderAd> audit(long orderId,int op) {
        BaseOpMsg<OrderAd> result = new BaseOpMsg();
        OrderAd orderAd =null;
        try {
            if (orderId > 0) {
                orderAd = orderAdService.selectByPrimaryKey(orderId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(orderAd==null){
            result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
            result.setMsg("订单不存在");
            return result;
        }
        boolean ispass = false;
        if(op>0){
            ispass = true;
        }
        boolean closeResult = orderAdService.auditOrder(orderId,ispass);
        if(closeResult){
            result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
        }else{
            result.setCode(StatusCode.CURD_DELETE_FAILURE);
        }
        result.setStatus("success");
        result.setMsg("查询数据成功！");
        result.setObject(orderAd);
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