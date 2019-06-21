/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.controller;

import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.base.BaseController;
import org.eq.modules.auth.entity.User;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.factory.ResponseFactory;
import org.eq.modules.order.service.OrderAcceptService;
import org.eq.modules.order.service.OrderTransferService;
import org.eq.modules.order.vo.*;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.product.service.UserProductStockService;
import org.eq.modules.product.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 券包Controller
 * @author kaka
 * @version 2019.05.08
 */
@SuppressWarnings("all")
@RestController
@RequestMapping(value = "/api/voucher")
public class VoucherController extends BaseController {

	@Autowired
	private OrderTransferService orderTransferService;

	@Autowired
	private OrderAcceptService orderAcceptService;

	@Autowired
	private UserProductStockService userProductStockService;


	/**
	 * 券包中可用券
	 * @return
	 */
	@PostMapping("/user/effectList")
	public ResponseData<PageResultData> userEffect(SearchPageProductVO searchPageProductVO) {
		if(searchPageProductVO ==null){
			return ResponseFactory.paramsError("参数为空或者用户ID为空");
		}
		User user = getUserInfo(searchPageProductVO.getUserId());
		if(user==null){
			return ResponseFactory.signError("用户不存在");
		}
		PageResultData<VoucherProductBaseVO> pageResultData =  userProductStockService.pageVoucherProduct(searchPageProductVO,user);
		return ResponseFactory.success(pageResultData);
	}


	/**
	 * 转出
	 * @return
	 */
	@PostMapping("/user/turnout")
	public ResponseData<OrderTransVO> turnout(SearchTransOrderVO searchTransOrderVO) {
		if(searchTransOrderVO ==null){
			return ResponseFactory.paramsError("参数为空或者用户ID为空");
		}
		User user = getUserInfo(searchTransOrderVO.getUserId());
		if(user==null){
			return ResponseFactory.signError("用户不存在");
		}
		if(StringUtils.isEmpty(searchTransOrderVO.getAddress())){
			return ResponseFactory.signError("转出地址为空");
		}

		ServieReturn<OrderTransVO>  resOrderAdVO =  orderTransferService.createTransOrderVO(searchTransOrderVO,user);
		if(!StringUtils.isEmpty(resOrderAdVO.getErrMsg())){
			return ResponseFactory.signError(resOrderAdVO.getErrMsg());
		}
		return ResponseFactory.success(resOrderAdVO.getData());
	}


	/**
	 * 承兑
	 * @return
	 */
	@PostMapping("/user/accept")
	public ResponseData<OrderAcceptVO> accept(SearchAcceptOrderVO searchAcceptOrderVO) {
		if(searchAcceptOrderVO ==null){
			return ResponseFactory.paramsError("参数为空或者用户ID为空");
		}
		User user = getUserInfo(searchAcceptOrderVO.getUserId());
		if(user==null){
			return ResponseFactory.signError("用户不存在");
		}
		ServieReturn<OrderAcceptVO>  resOrderAdVO =  orderAcceptService.createAcceptOrderVO(searchAcceptOrderVO,user);
		if(!StringUtils.isEmpty(resOrderAdVO.getErrMsg())){
			return ResponseFactory.signError(resOrderAdVO.getErrMsg());
		}
		return ResponseFactory.success(resOrderAdVO.getData());
	}


	/**
	 * 承兑列表
	 * @return
	 */
	@PostMapping("/user/acceptList")
	public ResponseData<PageResultData> pageAcceptList(SearchPageAcceptVO searchPageAcceptVO) {
		if(searchPageAcceptVO ==null){
			return ResponseFactory.paramsError("参数为空或者用户ID为空");
		}
		User user = getUserInfo(searchPageAcceptVO.getUserId());
		if(user==null){
			return ResponseFactory.signError("用户不存在");
		}
		PageResultData<OrderAcceptVO> pageResultData =  orderAcceptService.pageAcceptOrder(searchPageAcceptVO,user);
		if(pageResultData==null){
			logger.error("获取承兑订单异常");
			return ResponseFactory.signError("查询异常");
		}
		return ResponseFactory.success(pageResultData);
	}



	/**
	 * 转让列表
	 * @return
	 */
	@PostMapping("/user/turnoutList")
	public ResponseData<PageResultData> pageTurnoutList(SearchPageTransVO searchPageTransVO) {
		if(searchPageTransVO ==null){
			return ResponseFactory.paramsError("参数为空或者用户ID为空");
		}
		User user = getUserInfo(searchPageTransVO.getUserId());
		if(user==null){
			return ResponseFactory.signError("用户不存在");
		}
		PageResultData<OrderTransVO> pageResultData =  orderTransferService.pageTransOrder(searchPageTransVO,user);
		if(pageResultData==null){
			logger.error("获取转让订单异常");
			return ResponseFactory.signError("查询异常");
		}
		return ResponseFactory.success(pageResultData);
	}


	/**
	 * 失效列表
	 * @return
	 */
	@PostMapping("/user/overdueList")
	public ResponseData<PageResultData> pageOverdueList(SearchPageAcceptVO searchPageAcceptVO) {
		if(searchPageAcceptVO ==null){
			return ResponseFactory.paramsError("参数为空或者用户ID为空");
		}
		User user = getUserInfo(searchPageAcceptVO.getUserId());
		if(user==null){
			return ResponseFactory.signError("用户不存在");
		}
		PageResultData<OverdueVO> pageResultData = orderAcceptService.pageOverdueOrder(searchPageAcceptVO,user);
		if(pageResultData==null){
			logger.error("失效列表订单异常");
			return ResponseFactory.signError("查询异常");
		}
		return ResponseFactory.success(pageResultData);
	}


	/**
	 * 承兑详情
	 * @return
	 */
	@PostMapping("/user/acceptDetail")
	public ResponseData<OrderAcceptVO> acceptDetail(SearchAcceptOrderVO searchAcceptOrderVO) {
		if(searchAcceptOrderVO ==null){
			return ResponseFactory.paramsError("参数为空或者用户ID为空");
		}
		User user = getUserInfo(searchAcceptOrderVO.getUserId());
		if(user==null){
			return ResponseFactory.signError("用户不存在");
		}
		ServieReturn<OrderAcceptVO>   servieReturn = orderAcceptService.searchOrderAccept(searchAcceptOrderVO,user);
		if(!StringUtils.isEmpty(servieReturn.getErrMsg())){
			return ResponseFactory.signError(servieReturn.getErrMsg());
		}
		return ResponseFactory.success(servieReturn.getData());
	}



}