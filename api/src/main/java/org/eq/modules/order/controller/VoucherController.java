/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.order.controller;

import org.eq.basic.common.base.BaseController;
import org.eq.modules.auth.entity.User;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.factory.ResponseFactory;
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
	private ProductService productService;

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


}