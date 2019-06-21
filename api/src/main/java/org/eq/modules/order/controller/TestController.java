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
import org.eq.modules.order.vo.*;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.service.ProductLoadService;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.product.vo.TicketProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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


	/**
	 * 用户商品加载
	 * @return
	 */
	@PostMapping("/up")
	public String up(String address) {

		Map<String,TicketProductVO> result =  productLoadService.getTicketUserProduct(address);

		return JSONObject.toJSONString(result);
	}


}