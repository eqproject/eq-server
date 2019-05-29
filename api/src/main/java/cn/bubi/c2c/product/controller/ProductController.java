/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.c2c.product.controller;

import cn.bubi.basic.common.base.BaseController;
import cn.bubi.c2c.product.entity.Product;
import cn.bubi.c2c.product.service.ProductService;
import com.alibaba.fastjson.JSONObject;
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
@RequestMapping(value = "/api/product")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;

	@GetMapping("list")
	public String list() {
	    JSONObject json = success();
        List<Product> list = productService.findListByExample(null);
		json.put("list",list);
		return json.toJSONString();
	}

}