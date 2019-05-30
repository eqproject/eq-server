/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.controller;

import com.alibaba.fastjson.JSONObject;
import org.eq.basic.common.base.BaseController;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
public class OrderController extends BaseController {

	@Autowired
	private ProductService productService;

	@GetMapping("/list")
	public String list() {
	    JSONObject json = success();
        List<Product> list = productService.findListByExample(null);
		json.put("list",list);
		return json.toJSONString();
	}

}