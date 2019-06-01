/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.controller;

import org.eq.basic.common.base.BaseController;
import org.eq.modules.auth.entity.User;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.factory.ResponseFactory;
import org.eq.modules.product.entity.ProductAll;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.product.service.UserProductStockService;
import org.eq.modules.product.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品信息Controller
 * @author kaka
 * @version 2019.05.08
 */
@SuppressWarnings("all")
@RestController
@RequestMapping(value = "/api/product")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;

	@Autowired
	private UserProductStockService userProductStockService;

	@GetMapping("list")
	public ResponseData<String> index() {
	   return ResponseFactory.success("");
	}

	/**
	 * 获取平台有效商品信息
	 * @return
	 */
	@PostMapping("/platform/effect")
	public ResponseData<PageResultData> platformEffect(SearchPageProductVO searchPageProductVO) {
		if(searchPageProductVO ==null){
			return ResponseFactory.paramsError("参数为空或者用户ID为空");
		}
		User user = getUserInfo(searchPageProductVO.getUserId());
		if(user==null){
			return ResponseFactory.signError("用户不存在");
		}
		PageResultData<ProductBaseVO> pageResultData =  productService.pageSimpeProduct(searchPageProductVO);
		return ResponseFactory.success(pageResultData);
	}


	/**
	 * 获取平台有效商品信息
	 * @return
	 */

	@PostMapping("/user/effect")
	public ResponseData<PageResultData> userEffect(SearchPageProductVO searchPageProductVO) {
		if(searchPageProductVO ==null){
			return ResponseFactory.paramsError("参数为空或者用户ID为空");
		}
		User user = getUserInfo(searchPageProductVO.getUserId());
		if(user==null){
			return ResponseFactory.signError("用户不存在");
		}

		PageResultData<ProductBaseVO> pageResultData =  userProductStockService.pageSimpeProduct(searchPageProductVO,user);
		return ResponseFactory.success(pageResultData);
	}


	@PostMapping("/platform/details")
	public ResponseData platformDetails(SearchProductVO searchProductVO) {
		if(searchProductVO ==null || searchProductVO.getUserId()<=0 || searchProductVO.getId()<=0){
			return ResponseFactory.paramsError("参数为空或者用户ID为空");
		}
		User user = getUserInfo(searchProductVO.getUserId());
		if(user==null){
			return ResponseFactory.signError("用户不存在");
		}

		BSearchProduct bsearchProduct = new BSearchProduct();
		bsearchProduct.setProductId(searchProductVO.getId());
		ProductDetailVO productDetailVO =  productService.getProductAll(bsearchProduct);
		if(productDetailVO==null){
			productDetailVO = new ProductDetailVO();
		}

		return ResponseFactory.success(productDetailVO);
	}


}