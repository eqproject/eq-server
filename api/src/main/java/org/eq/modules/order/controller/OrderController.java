/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.base.BaseController;
import org.eq.modules.auth.entity.User;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.entitys.StaticEntity;
import org.eq.modules.common.factory.ResponseFactory;
import org.eq.modules.enums.OrderAdTypeEnum;
import org.eq.modules.order.service.OrderAdService;
import org.eq.modules.order.vo.*;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.product.vo.ProductBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品信息Controller
 * @author kaka
 * @version 2019.05.08
 */
@RestController
@RequestMapping(value = "/api/adOrder")
public class OrderController extends BaseController {

	@Autowired
	private ProductService productService;


	@Autowired
	private OrderAdService orderAdService;




	/**
	 * 获取平台有效商品信息
	 * @return
	 */
	@PostMapping("/user/create")
	public ResponseData<ResOrderAdVO> createOrderAdinfo(SearchAdOrderVO searchAdOrderVO) {
		String volidResult = VolidOrderInfo.volidSearchOrderAd(searchAdOrderVO);
		if(!StringUtils.isEmpty(volidResult)){
			return ResponseFactory.paramsError(volidResult);
		}
		User user = getUserInfo(searchAdOrderVO.getUserId());
		if(user==null){
			return ResponseFactory.signError("用户不存在");
		}
		ServieReturn<ResOrderAdVO>  resOrderAdVO =  orderAdService.createResOrderAdVO(searchAdOrderVO,user);
		if(!StringUtils.isEmpty(resOrderAdVO.getErrMsg())){
			return ResponseFactory.signError(resOrderAdVO.getErrMsg());
		}
		return ResponseFactory.success(resOrderAdVO.getData());
	}


	/**
	 * 取消订单
	 * @return
	 */
	@PostMapping("/user/cancel")
	public ResponseData<ResOrderAdVO> cacelOrderAdinfo(SearchAdOrderVO searchAdOrderVO) {
		if(searchAdOrderVO==null || StringUtils.isEmpty(searchAdOrderVO.getOrderCode())){
			return ResponseFactory.signError("订单号为空，无法取消");
		}
		User user = getUserInfo(searchAdOrderVO.getUserId());
		if(user==null){
			return ResponseFactory.signError("用户不存在");
		}
		ServieReturn<ResOrderAdVO>  resOrderAdVO =  orderAdService.cacelResOrderAdVO(searchAdOrderVO,user);
		if(!StringUtils.isEmpty(resOrderAdVO.getErrMsg())){
			return ResponseFactory.signError(resOrderAdVO.getErrMsg());
		}
		return ResponseFactory.success(resOrderAdVO.getData());
	}



	/**
	 * 获取平台订单
	 * @return
	 */
	@PostMapping("/plat/details")
	public ResponseData<OrderAdSimpleVO> orderDetail(SearchAdOrderVO searchAdOrderVO) {
		if(searchAdOrderVO==null || StringUtils.isEmpty(searchAdOrderVO.getOrderCode())){
			return ResponseFactory.signError("订单号为空");
		}
		ServieReturn<OrderAdSimpleVO>  resOrderAdVO =  orderAdService.getResOrderAdVO(searchAdOrderVO);
		if(!StringUtils.isEmpty(resOrderAdVO.getErrMsg())){
			return ResponseFactory.signError(resOrderAdVO.getErrMsg());
		}
		return ResponseFactory.success(resOrderAdVO.getData());
	}



	/**
	 * 获取我的订单
	 * @return
	 */
	@PostMapping("/user/list")
	public ResponseData<PageResultData> orderAdUserList(SearchPageAdOrderVO searchPageAdOrderVO) {
		if(searchPageAdOrderVO==null){
			return ResponseFactory.signError("查询条件为空");
		}
		User user = getUserInfo(searchPageAdOrderVO.getUserId());
		if(user==null){
			return ResponseFactory.signError("用户不存在");
		}
		PageResultData<OrderAdSimpleVO>  pageResult =  orderAdService.pageUserOrderAd(searchPageAdOrderVO,user);
		if(pageResult==null){
			logger.error("集市获取订单接口异常");
			return ResponseFactory.signError("查询异常");
		}
		return ResponseFactory.success(pageResult);
	}




	/**
	 * 集市订单查询
	 * @return
	 */
	@PostMapping("/plat/list")
	public ResponseData<PageResultData> orderAdinfoList(SearchPageAdOrderVO searchPageAdOrderVO) {
		if(searchPageAdOrderVO==null){
			return ResponseFactory.signError("查询条件为空");
		}
		//获取平台求购订单不需要用户
		User user = getUserInfo(searchPageAdOrderVO.getUserId());
		if(searchPageAdOrderVO.getOrderType()!=1 && searchPageAdOrderVO.getOrderType()!=2){
			return ResponseFactory.signError("订单类型出错");
		}

		PageResultData<OrderAdSimpleVO>  pageResult =  orderAdService.pagePlatOrderAd(searchPageAdOrderVO,user);
		if(pageResult==null){
			logger.error("集市获取订单接口异常");
			return ResponseFactory.signError("查询异常");
		}
		return ResponseFactory.success(pageResult);
	}

}