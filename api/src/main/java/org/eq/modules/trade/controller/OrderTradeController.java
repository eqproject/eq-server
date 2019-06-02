/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanUtils;
import org.eq.basic.common.base.BaseController;
import org.eq.basic.common.exception.ProductNotExistsException;
import org.eq.basic.common.util.DateUtil;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.factory.ResponseFactory;
import org.eq.modules.trade.entity.OrderTrade;
import org.eq.modules.trade.service.OrderTradeService;
import org.eq.modules.trade.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/**
 * 订单交易Controller
 * @author yufei.sun
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/api/order/trade")
public class OrderTradeController extends BaseController {

	@Autowired
	private OrderTradeService orderTradeService;

    /**
     * 创建交易订单
     * @param orderTradeCreateReqVO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"create"})
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
        Date nowDate = DateUtil.getNowTime();
        orderTrade.setCreateDate(nowDate);
        orderTrade.setUpdateDate(nowDate);
        try {
            orderTradeService.createTradeOrder(orderTrade);
        } catch (ProductNotExistsException e) {
            logger.error("createTradeOrder 失败，原因是",e);
            return ResponseFactory.paramsError(e.getMessage());
        } catch (Exception e) {
            logger.error("createTradeOrder 失败，原因是",e);
            return ResponseFactory.systemError("系统错误");
        }
        OrderTradeCreateResVO orderTradeCreateResVO = new OrderTradeCreateResVO("");
        logger.info("createTradeOrder 响应内容:{}",JSON.toJSONString(orderTradeCreateResVO));
        return ResponseFactory.success(orderTradeCreateResVO);
    }

    /**
     * 取消交易订单
     * @param orderTradeCancelReqVO
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"cancel"})
    public ResponseData<OrderTradeCancelResVO> cancelTradeOrder(OrderTradeCancelReqVO orderTradeCancelReqVO) {
        if (orderTradeCancelReqVO == null) {
            logger.error("cancelTradeOrder 失败，原因是 orderTradeCreateReqVO is null");
            return ResponseFactory.paramsError("请求参数不能为空");
        }
        logger.info("cancelTradeOrder 请求参数:{}",JSON.toJSONString(orderTradeCancelReqVO));
        OrderTrade orderTrade =new OrderTrade();

        Date nowDate = DateUtil.getNowTime();
        orderTrade.setCreateDate(nowDate);
        orderTrade.setUpdateDate(nowDate);
        try {
            orderTradeService.cancelTradeOrder(orderTrade.getTradeNo());
        } catch (ProductNotExistsException e) {
            logger.error("cancelTradeOrder 失败，原因是",e);
            return ResponseFactory.paramsError(e.getMessage());
        } catch (Exception e) {
            logger.error("cancelTradeOrder 失败，原因是",e);
            return ResponseFactory.systemError("系统错误");
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
    @ResponseBody
    @RequestMapping(value = {"detail"})
    public ResponseData<OrderTradeDetailResVO> tradeOrderDetail(OrderTradeDetailReqVO orderTradeDetailReqVO) {
        if (orderTradeDetailReqVO == null) {
            logger.error("tradeOrderDetail 失败，原因是 orderTradeDetailReqVO is null");
            return ResponseFactory.paramsError("请求参数不能为空");
        }
        logger.info("tradeOrderDetail 请求参数:{}",JSON.toJSONString(orderTradeDetailReqVO));
        OrderTrade orderTrade =new OrderTrade();

        Date nowDate = DateUtil.getNowTime();
        orderTrade.setCreateDate(nowDate);
        orderTrade.setUpdateDate(nowDate);
        try {
            orderTradeService.cancelTradeOrder(orderTrade.getTradeNo());
        } catch (ProductNotExistsException e) {
            logger.error("tradeOrderDetail 失败，原因是",e);
            return ResponseFactory.paramsError(e.getMessage());
        } catch (Exception e) {
            logger.error("tradeOrderDetail 失败，原因是",e);
            return ResponseFactory.systemError("系统错误");
        }
        OrderTradeDetailResVO orderTradeCancelResVO = new OrderTradeDetailResVO();
        logger.info("tradeOrderDetail 响应内容:{}",JSON.toJSONString(orderTradeCancelResVO));
        return ResponseFactory.success(orderTradeCancelResVO);
    }

}