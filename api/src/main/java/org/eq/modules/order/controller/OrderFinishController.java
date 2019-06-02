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
import org.eq.modules.common.factory.ResponseFactory;
import org.eq.modules.order.service.OrderAdService;
import org.eq.modules.order.service.OrderFinishSnapshootService;
import org.eq.modules.order.vo.*;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.service.ProductService;
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
@RequestMapping(value = "/api/order")
public class OrderFinishController extends BaseController {

	@Autowired
	private OrderFinishSnapshootService orderFinishSnapshootService;


	/**
	 * 订单查询
	 * @return
	 */
	@PostMapping("/finish/list")
	public ResponseData<PageResultData> orderAdinfoList(SearchPageOrderFinishVO searchPageOrderFinishVO) {
		if(searchPageOrderFinishVO==null){
			return ResponseFactory.signError("查询条件为空");
		}
		User user = getUserInfo(searchPageOrderFinishVO.getUserId());
		if(user==null){
			return ResponseFactory.signError("用户不存在");
		}

		PageResultData<OrderFinishSnapshootSimpleVO>  pageResult =  orderFinishSnapshootService.pageFinishPlatOrder(searchPageOrderFinishVO,user);
		if(pageResult==null){
			logger.error("获取已完成订单接口异常");
			return ResponseFactory.signError("查询异常");
		}
		return ResponseFactory.success(pageResult);
	}


}