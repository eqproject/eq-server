/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.base.BaseController;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.factory.ResponseFactory;
import org.eq.modules.product.service.ProductLoadService;
import org.eq.modules.product.vo.TicketProductVO;
import org.eq.modules.trade.service.OrderTradeService;
import org.eq.modules.trade.vo.OrderTradeLoan;
import org.eq.modules.trade.vo.OrderTradeLoanReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 *  测试类
 * @author kaka
 * @version 2019.05.08
 */
@RestController
@RequestMapping(value = "/api/test")
public class TestController extends BaseController {


	@Autowired
	private ProductLoadService productLoadService;


	@Autowired
	private OrderTradeService orderTradeService;


	/**
	 * 用户商品加载
	 * @return
	 */
	@PostMapping("/up")
	public String up(String address) {

		Map<String,TicketProductVO> result =  productLoadService.getTicketUserProduct(address);

		return JSONObject.toJSONString(result);
	}


	/**
	 * 放款接口
	 * @return
	 */
	@PostMapping("/loan")
	public ResponseData<String> loan(OrderTradeLoanReqVO orderTradeLoanReqVO) {
		if(orderTradeLoanReqVO==null || StringUtils.isEmpty(orderTradeLoanReqVO.getTradeNo())){
			return ResponseFactory.paramsError("请求参数不能为空");
		}
		if(orderTradeLoanReqVO.getState()==null){
			return ResponseFactory.paramsError("放款状态有误");
		}
		OrderTradeLoan orderTradeLoanVO = new OrderTradeLoan();
		boolean isSucces = false;
		if(orderTradeLoanReqVO.getState().intValue()==1){
			isSucces = true;
		}
		orderTradeLoanVO.setTradeNo(orderTradeLoanReqVO.getTradeNo());
		boolean result  =  orderTradeService.loanTrade(orderTradeLoanVO,isSucces);
		if(result){
			return ResponseFactory.success("通知成功");
		}
		return ResponseFactory.paramsError("通知失败");
	}


}