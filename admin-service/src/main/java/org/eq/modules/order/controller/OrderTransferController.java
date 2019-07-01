/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.controller;

import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.base.BaseController;
import org.eq.basic.common.base.BaseOpMsg;
import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.status.StatusCode;
import org.eq.basic.common.util.DateUtil;
import org.eq.modules.auth.entity.User;
import org.eq.modules.auth.service.UserService;
import org.eq.modules.order.entity.OrderTransfer;
import org.eq.modules.order.service.OrderTransferService;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.service.ProductService;
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
 * 转让Controller
 * @author kaka
 * @version 1.0.01
 */
@SuppressWarnings("all")
@Controller
@RequestMapping(value = "/orderTransfer")
public class OrderTransferController extends BaseController {

	@Autowired
	private OrderTransferService orderTransferService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

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
		return "modules/c2c/order/transferList";
	}

	@ResponseBody
	@RequestMapping(value = {"dataList"})
	public BaseTableData dataList(HttpServletRequest request, HttpServletResponse response, OrderTransfer orderTransfer) {
		Map<String,Object> params = new HashMap<>();
		//取出 request 的参数信息分页信息 排序信息
		getInfoFromRequest(request,params);
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
		//数据的draw自增
		baseTableData = orderTransferService.findDataTableByRecordForPage(orderTransfer,params);
		int draw = Integer.parseInt(request.getParameter("draw")==null?"0":request.getParameter("draw"));
		baseTableData.setDraw(++draw);
		return baseTableData;
	}

	/**
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     * @param orderTransfer
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "selectOrder")
    public BaseOpMsg<OrderTransfer> selectOrderTransfer(OrderTransfer orderTransfer) {
        BaseOpMsg<OrderTransfer> result = new BaseOpMsg<OrderTransfer>();
        if(orderTransfer ==null || orderTransfer.getId() ==null || orderTransfer.getId()<=0){
            result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
            result.setMsg("查询数据失败！");
            return result;
        }
        OrderTransfer orderTransferResult = orderTransferService.selectByPrimaryKey(orderTransfer.getId());
        if(orderTransferResult==null){
            result.setCode(StatusCode.CURD_SELECT_FAILURE);
            result.setMsg("查询数据失败！");
            return result;
        }
        Product product = productService.selectByPrimaryKey(orderTransferResult.getProductId());
        if(product!=null){
            orderTransferResult.setProductName(product.getName());
        }
        User user = userService.selectByPrimaryKey(orderTransferResult.getUserId());
        if(user!=null){
            orderTransferResult.setUserNickName(user.getNickname());
        }
        result.setCode(StatusCode.CURD_SELECT_SUCCESS);
        result.setStatus("success");
        result.setObject(orderTransferResult);
        return result;
    }
}