/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.controller;

import org.eq.basic.common.base.BaseController;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.factory.ResponseFactory;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.service.ProductService;
import com.alibaba.fastjson.JSONObject;
import org.eq.modules.product.vos.SearchProductVO;
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
@RequestMapping(value = "/api/product")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;

	@GetMapping("list")
	public ResponseData<String> index() {
	   return ResponseFactory.success("");
	}

	/**
	 * 获取平台有效商品信息
	 * @return
	 */
	@PostMapping("/platform/effect")
	public ResponseData<String> platformEffect(SearchProductVO searchProductVO) {



		return ResponseFactory.success("");
	}


}