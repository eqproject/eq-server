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
import org.eq.modules.order.vo.ResOrderAdVO;
import org.eq.modules.order.vo.SearchAdOrderVO;
import org.eq.modules.order.vo.ServieReturn;
import org.eq.modules.order.vo.VolidOrderInfo;
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
	 * 获取平台有效商品信息
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





	@GetMapping("/list")
	public String list() {
	    JSONObject json = success();
        List<Product> list = productService.findListByExample(null);
		json.put("list",list);
		return json.toJSONString();
	}


}