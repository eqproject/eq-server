/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.controller;

import org.eq.basic.common.base.BaseController;
import org.eq.modules.auth.entity.User;
import org.eq.modules.common.entitys.PageResultBase;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.factory.ResponseFactory;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.product.vo.PageProductVO;
import org.eq.modules.product.vo.ProductVO;
import org.eq.modules.product.vo.SearchProductVO;
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

	@GetMapping("list")
	public ResponseData<String> index() {
	   return ResponseFactory.success("");
	}

	/**
	 * 获取平台有效商品信息
	 * @return
	 */
	@PostMapping("/platform/effect")
	public ResponseData<PageProductVO> platformEffect(SearchProductVO searchProductVO) {
		if(searchProductVO==null){
			return ResponseFactory.paramsError("参数为空或者用户ID为空");
		}
		User user = getUserInfo(searchProductVO.getUserId());
		if(user==null){
			return ResponseFactory.signError("用户不存在");
		}
		PageProductVO result = new PageProductVO();
		PageResultBase<ProductVO>  pageResultData =  productService.pageSimpeProduct(searchProductVO);
		result.setPageNum(searchProductVO.getPageNum());
		result.setProductDatas(pageResultData.getData());
		result.setTotalNum(pageResultData.getRecordsTotal());
		return ResponseFactory.success(result);
	}


	/**
	 * 获取平台有效商品信息
	 * @return
	 */

	@PostMapping("/user/effect")
	public ResponseData<PageProductVO> userEffect(SearchProductVO searchProductVO) {
		if(searchProductVO==null){
			return ResponseFactory.paramsError("参数为空或者用户ID为空");
		}
		User user = getUserInfo(searchProductVO.getUserId());
		if(user==null){
			return ResponseFactory.signError("用户不存在");
		}

		PageProductVO result = new PageProductVO();
		PageResultBase<ProductVO>  pageResultData =  productService.pageSimpeProduct(searchProductVO);
		result.setPageNum(searchProductVO.getPageNum());
		result.setProductDatas(pageResultData.getData());
		result.setTotalNum(pageResultData.getRecordsTotal());
		return ResponseFactory.success(result);
	}


}