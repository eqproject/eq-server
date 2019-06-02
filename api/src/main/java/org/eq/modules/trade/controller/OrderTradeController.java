/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanUtils;
import org.eq.basic.common.base.BaseController;
import org.eq.basic.common.base.BaseServiceException;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.factory.ResponseFactory;
import org.eq.modules.trade.entity.OrderPaymentTrade;
import org.eq.modules.trade.entity.OrderTrade;
import org.eq.modules.trade.service.OrderTradeService;
import org.eq.modules.trade.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;

/**
 * 订单交易Controller
 * @author yufei.sun
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/api/order/trade")
public class OrderTradeController extends BaseController {

	@Autowired
	private OrderTradeService orderTradeService;

    /**
     * 创建交易订单
     * @param orderTradeCreateReqVO
     * @return
     */
    @PostMapping("/create")
    public ResponseData<OrderTradeCreateResVO> createTradeOrder(OrderTradeCreateReqVO orderTradeCreateReqVO) {
        if (orderTradeCreateReqVO == null) {
            logger.error("createTradeOrder 失败，原因是 orderTradeCreateReqVO is null");
            return ResponseFactory.paramsError("请求参数不能为空");
        }
        logger.info("createTradeOrder 请求参数:{}",JSON.toJSONString(orderTradeCreateReqVO));
        OrderTrade orderTrade =new OrderTrade();
        try {
            BeanUtils.copyProperties(orderTrade, orderTradeCreateReqVO);
        } catch (IllegalAccessException e) {
            logger.error("createTradeOrder 失败，原因是",e);
            return ResponseFactory.paramsError("请求参数错误");
        } catch (InvocationTargetException e) {
            logger.error("createTradeOrder 失败，原因是",e);
            return ResponseFactory.paramsError("请求参数错误");
        }
        try {
            orderTrade = orderTradeService.createTradeOrder(orderTrade);
        } catch (BaseServiceException e) {
            logger.error("createTradeOrder 失败，原因是:{}",e.getMessage());
            return ResponseFactory.paramsError(e.getMessage());
        } catch (Exception e) {
            logger.error("createTradeOrder 失败，原因是",e);
            return ResponseFactory.systemError(SYSTEM_ERROR_MSG);
        }
        OrderTradeCreateResVO orderTradeCreateResVO = new OrderTradeCreateResVO(orderTrade.getTradeNo());
        logger.info("createTradeOrder 响应内容:{}",JSON.toJSONString(orderTradeCreateResVO));
        return ResponseFactory.success(orderTradeCreateResVO);
    }

    /**
     * 取消交易订单
     * @param orderTradeCancelReqVO
     * @return
     */
    @GetMapping("/cancel")
    public ResponseData<OrderTradeCancelResVO> cancelTradeOrder(OrderTradeCancelReqVO orderTradeCancelReqVO) {
        if (orderTradeCancelReqVO == null) {
            logger.error("cancelTradeOrder 失败，原因是 orderTradeCancelReqVO is null");
            return ResponseFactory.paramsError("请求参数不能为空");
        }
        logger.info("cancelTradeOrder 请求参数:{}",JSON.toJSONString(orderTradeCancelReqVO));
        try {
            orderTradeService.cancelTradeOrder(orderTradeCancelReqVO.getTradeNo());
        } catch (BaseServiceException e) {
            logger.error("cancelTradeOrder 失败，原因是:{}",e.getMessage());
            return ResponseFactory.paramsError(e.getMessage());
        } catch (Exception e) {
            logger.error("cancelTradeOrder 失败，原因是",e);
            return ResponseFactory.systemError(SYSTEM_ERROR_MSG);
        }
        OrderTradeCancelResVO orderTradeCancelResVO = new OrderTradeCancelResVO(orderTradeCancelReqVO.getTradeNo());
        logger.info("cancelTradeOrder 响应内容:{}",JSON.toJSONString(orderTradeCancelResVO));
        return ResponseFactory.success(orderTradeCancelResVO);
    }


    /**
     * 查询交易订单详情
     * @param orderTradeDetailReqVO
     * @return
     */
    @GetMapping("/detail")
    public ResponseData<OrderTradeDetailResVO> tradeOrderDetail(OrderTradeDetailReqVO orderTradeDetailReqVO) {
        if (orderTradeDetailReqVO == null) {
            logger.error("tradeOrderDetail 失败，原因是 orderTradeDetailReqVO is null");
            return ResponseFactory.paramsError("请求参数不能为空");
        }
        logger.info("tradeOrderDetail 请求参数:{}",JSON.toJSONString(orderTradeDetailReqVO));
        OrderTradeDetailResVO orderTradeCancelResVO;
        try {
            orderTradeCancelResVO = orderTradeService.tradeOrderDetail(orderTradeDetailReqVO.getTradeNo());
        } catch (BaseServiceException e) {
            logger.error("tradeOrderDetail 失败，原因是:{}",e.getMessage());
            return ResponseFactory.paramsError(e.getMessage());
        } catch (Exception e) {
            logger.error("tradeOrderDetail 失败，原因是",e);
            return ResponseFactory.systemError(SYSTEM_ERROR_MSG);
        }
        logger.info("tradeOrderDetail 响应内容:{}",JSON.toJSONString(orderTradeCancelResVO));
        return ResponseFactory.success(orderTradeCancelResVO);
    }

    @PostMapping("/pay/notify")
    public ResponseData<OrderTradePaymentResVO> orderPaymentTradeNotify(OrderTradePaymentReqVO orderTradePaymentReqVO) {
        if (orderTradePaymentReqVO == null) {
            logger.error("createOrderTradePayment 失败，原因是 orderTradePaymentReqVO is null");
            return ResponseFactory.paramsError("请求参数不能为空");
        }
        logger.info("createOrderTradePayment 请求参数:{}",JSON.toJSONString(orderTradePaymentReqVO));
        OrderPaymentTrade orderPaymentTrade = new OrderPaymentTrade();
        try {
            BeanUtils.copyProperties(orderPaymentTrade, orderTradePaymentReqVO);
            orderPaymentTrade.setAmount(orderTradePaymentReqVO.getPayAmout());
            orderPaymentTrade.setStatus(orderTradePaymentReqVO.getPayStatus());
        } catch (IllegalAccessException e) {
            logger.error("createOrderTradePayment 失败，原因是",e);
            return ResponseFactory.paramsError("请求参数错误");
        } catch (InvocationTargetException e) {
            logger.error("createOrderTradePayment 失败，原因是",e);
            return ResponseFactory.paramsError("请求参数错误");
        }
        OrderTradePaymentResVO orderTradePaymentResVO;
        try {
            orderTradePaymentResVO = orderTradeService.orderPaymentTradeNotify(orderPaymentTrade);
        } catch (BaseServiceException e) {
            logger.error("createOrderTradePayment 失败，原因是:{}",e.getMessage());
            return ResponseFactory.paramsError(e.getMessage());
        } catch (Exception e) {
            logger.error("createOrderTradePayment 失败，原因是",e);
            return ResponseFactory.systemError(SYSTEM_ERROR_MSG);
        }
        logger.info("createOrderTradePayment 响应内容:{}",JSON.toJSONString(orderTradePaymentResVO));
        return ResponseFactory.success(orderTradePaymentResVO);
    }

}