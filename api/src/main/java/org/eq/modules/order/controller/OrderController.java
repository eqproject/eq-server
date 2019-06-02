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
	public ResponseData<ResOrderAdVO> platformEffect(SearchAdOrderVO searchAdOrderVO) {
		String volidResult = volidSearchOrderAd(searchAdOrderVO);
		if(!StringUtils.isEmpty(volidResult)){
			return ResponseFactory.paramsError(volidResult);
		}
		User user = getUserInfo(searchAdOrderVO.getUserId());
		if(user==null){
			return ResponseFactory.signError("用户不存在");
		}
		ResOrderAdVO resOrderAdVO =  orderAdService.createResOrderAdVO(searchAdOrderVO);
		return ResponseFactory.success(resOrderAdVO);
	}




	@GetMapping("/list")
	public String list() {
	    JSONObject json = success();
        List<Product> list = productService.findListByExample(null);
		json.put("list",list);
		return json.toJSONString();
	}

	/**
	 * 验证广告订单
	 * @param searchAdOrderVO
	 * @return
	 */
	private static String volidSearchOrderAd(SearchAdOrderVO searchAdOrderVO){
		if(searchAdOrderVO==null){
			return  "请求参数为空";
		}
		if(searchAdOrderVO.getUserId()<=0 ){
			return "用户为空";
		}
		if(searchAdOrderVO.getProductId()<=0 || searchAdOrderVO.getNumber()<=0){
			return "商品为空或数量为空";
		}
		if(StringUtils.isEmpty(searchAdOrderVO.getAdTitle()) || searchAdOrderVO.getAdTitle().length()> StaticEntity.ORDER_AD_TITLE_LENGTH){
			return "广告标题长度为空或长度超过最大限制";
		}
		String result = OrderAdTypeEnum.getRemarkByType(searchAdOrderVO.getOrderType());
		if(StringUtils.isEmpty(result)){
			return "广告类型非法";
		}
		return null;

	}

}