/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.base.BaseController;
import org.eq.basic.common.base.BaseServiceException;
import org.eq.modules.auth.entity.User;
import org.eq.modules.common.entitys.PageResultData;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.factory.ResponseFactory;
import org.eq.modules.enums.OrderTradeStateEnum;
import org.eq.modules.order.vo.ServieReturn;
import org.eq.modules.trade.entity.OrderTrade;
import org.eq.modules.trade.service.OrderTradeService;
import org.eq.modules.trade.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 交易Controller
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
    public ResponseData<OrderTradeBaseResVO> createTradeOrder(OrderTradeCreateReqVO orderTradeCreateReqVO) {
        String errMsg = VolidOrderTradeInfo.volidCreate(orderTradeCreateReqVO);
        if(!StringUtils.isEmpty(errMsg)){
            return ResponseFactory.paramsError(errMsg);
        }
        OrderTradeBaseResVO result  = new OrderTradeBaseResVO();
        User user = getUserInfo(orderTradeCreateReqVO.getUserId());
        if(user==null){
            return ResponseFactory.signError("用户不存在");
        }
        logger.info("createTradeOrder 请求参数:{}",JSON.toJSONString(orderTradeCreateReqVO));
        ServieReturn<OrderTrade> inserResult =  null;
        try {
            inserResult = orderTradeService.createTradeOrder(orderTradeCreateReqVO,user);
        } catch (Exception e) {
            logger.error("createTradeOrder 失败，原因是",e);
            return ResponseFactory.systemError(SYSTEM_ERROR_MSG);
        }
        if(inserResult!=null && !StringUtils.isEmpty(inserResult.getErrMsg())){
            return ResponseFactory.systemError(inserResult.getErrMsg());
        }
        if(inserResult ==null || inserResult.getData()==null){
            return ResponseFactory.systemError(SYSTEM_ERROR_MSG);
        }
        result.setTradeNo(inserResult.getData().getTradeNo());
        logger.info("createTradeOrder 响应内容:{}",JSON.toJSONString(result));
        return ResponseFactory.success(result);
    }



    /**
     * 支付前回调
     * @param orderTradeSearchVO
     * @return
     */
    @PostMapping("/prePay")
    public ResponseData<OrderTradeBaseResVO> prePay(OrderTradeSearchVO orderTradeSearchVO) {
        String errMsg = VolidOrderTradeInfo.volidSearch(orderTradeSearchVO);
        if(!StringUtils.isEmpty(errMsg)){
            return ResponseFactory.paramsError(errMsg);
        }
        OrderTradeBaseResVO result = new OrderTradeBaseResVO();

        User user = getUserInfo(orderTradeSearchVO.getUserId());
        if(user==null){
            return ResponseFactory.signError("用户不存在");
        }
        logger.info("prePay 请求参数:{}",orderTradeSearchVO.toString());
        OrderTrade orderTrade = null;
        try {
            ServieReturn<OrderTrade> inserResult = orderTradeService.prePayTradeOrder(orderTradeSearchVO,user);
            if(inserResult!=null && !StringUtils.isEmpty(inserResult.getErrMsg())){
                return ResponseFactory.systemError(inserResult.getErrMsg());
            }

            if(inserResult==null || inserResult.getData()==null){
                return ResponseFactory.systemError(SYSTEM_ERROR_MSG);
            }
            orderTrade = inserResult.getData();
        } catch (Exception e) {
            logger.error("prePay 失败，原因是",e);
        }
        if(orderTrade ==null){
            return ResponseFactory.systemError("数据查询异常");
        }
        result.setTradeNo(orderTrade.getTradeNo());
        logger.info("prePay 响应内容:{}",JSON.toJSONString(result));
        return ResponseFactory.success(result);
    }

    /**
     * 取消交易订单
     * @param orderTradeSearchVO
     * @return
     */
    @PostMapping("/cancel")
    public ResponseData<OrderTradeBaseResVO> cancelTradeOrder(OrderTradeSearchVO orderTradeSearchVO) {

        String errMsg = VolidOrderTradeInfo.volidConcel(orderTradeSearchVO);
        if(!StringUtils.isEmpty(errMsg)){
            return ResponseFactory.paramsError(errMsg);
        }
        OrderTradeBaseResVO result = new OrderTradeBaseResVO();
        logger.info("cancelTradeOrder 请求参数:{}",JSON.toJSONString(orderTradeSearchVO));
        try {
            orderTradeService.cancelTradeOrder(orderTradeSearchVO.getTradeNo());
        } catch (BaseServiceException e) {
            logger.error("cancelTradeOrder 失败，原因是:{}",e.getMessage());
            return ResponseFactory.paramsError(e.getMessage());
        } catch (Exception e) {
            logger.error("cancelTradeOrder 失败，原因是",e);
            return ResponseFactory.systemError(SYSTEM_ERROR_MSG);
        }
        result.setTradeNo(orderTradeSearchVO.getTradeNo());
        logger.info("cancelTradeOrder 响应内容:{}",JSON.toJSONString(result));
        return ResponseFactory.success(result);
    }


    /**
     * 查询交易订单详情
     * @param orderTradeSearchVO
     * @return
     */
    @PostMapping("/detail")
    public ResponseData<OrderTradeDetailResVO> tradeOrderDetail(OrderTradeSearchVO orderTradeSearchVO) {
        if (orderTradeSearchVO == null) {
            logger.error("tradeOrderDetail 失败，原因是 orderTradeDetailReqVO is null");
            return ResponseFactory.paramsError("请求参数不能为空");
        }
        logger.info("tradeOrderDetail 请求参数:{}",JSON.toJSONString(orderTradeSearchVO));
        OrderTradeDetailResVO orderTradeCancelResVO;
        try {
            orderTradeCancelResVO = orderTradeService.tradeOrderDetail(orderTradeSearchVO.getTradeNo());
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

    /**
     * 获取交易数量
     * @param orderTradeSearchVO
     * @return
     */
    @PostMapping("/poolInfo")
    public ResponseData<OrderTradePoolInfoVO> poolInfo(OrderTradeSearchVO orderTradeSearchVO) {
        String errMsg = VolidOrderTradeInfo.volidPoolInfo(orderTradeSearchVO);
        if(!StringUtils.isEmpty(errMsg)){
            return ResponseFactory.paramsError(errMsg);
        }
        OrderTradePoolInfoVO result = null;
        logger.info("poolInfo 请求参数:{}",JSON.toJSONString(orderTradeSearchVO));
        try {
            result = orderTradeService.poolInfolTradeOrder(orderTradeSearchVO.getUserId());
        } catch (BaseServiceException e) {
            logger.error("poolInfo 失败，原因是:{}",e.getMessage());
            return ResponseFactory.paramsError(e.getMessage());
        } catch (Exception e) {
            logger.error("poolInfo 失败，原因是",e);
            return ResponseFactory.systemError(SYSTEM_ERROR_MSG);
        }
        if(result==null){
            result = new OrderTradePoolInfoVO();
        }
        logger.info("cancelTradeOrder 响应内容:{}",JSON.toJSONString(result));
        return ResponseFactory.success(result);
    }


    /**
     * 支付结果回调
     * @param orderTradePaymentReqVO
     * @return
     */
    @PostMapping("/pay/notify")
    public ResponseData<OrderTradePaymentResVO> orderPaymentTradeNotify(OrderTradePaymentReqVO orderTradePaymentReqVO) {
        String errMsg = VolidOrderTradeInfo.volidPayNotify(orderTradePaymentReqVO);
        if(!StringUtils.isEmpty(errMsg)){
            return ResponseFactory.paramsError(errMsg);
        }

        logger.info("createOrderTradePayment 请求参数:{}",JSON.toJSONString(orderTradePaymentReqVO));

        OrderTradePaymentResVO orderTradePaymentResVO;
        try {
            orderTradePaymentResVO = orderTradeService.orderPaymentTradeNotify(orderTradePaymentReqVO);
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

    /**
     * 查询待付款交易订单列表接口
     * @param orderTradeListReqVO
     * @return
     */
    @PostMapping("/paying/list")
    public ResponseData<PageResultData> payingOrderTradeList(OrderTradeListReqVO orderTradeListReqVO) {

        if (orderTradeListReqVO == null || orderTradeListReqVO.getUserId()<=0) {
            return ResponseFactory.paramsError("请求参数不能为空");
        }

        logger.info("payingOrderTradeList 请求参数:{}",JSON.toJSONString(orderTradeListReqVO));
        PageResultData<OrderTradeSimpleResVO> orderTradeListResVOPageResultData;
        try {

            orderTradeListResVOPageResultData = orderTradeService.pageWaitPayList(orderTradeListReqVO);
        } catch (BaseServiceException e) {
            logger.error("payingOrderTradeList 失败，原因是:{}",e.getMessage());
            return ResponseFactory.paramsError(e.getMessage());
        } catch (Exception e) {
            logger.error("payingOrderTradeList 失败，原因是",e);
            return ResponseFactory.systemError(SYSTEM_ERROR_MSG);
        }
        logger.info("payingOrderTradeList 响应内容:{}",JSON.toJSONString(orderTradeListResVOPageResultData));
        return ResponseFactory.success(orderTradeListResVOPageResultData);
    }

    /**
     * 查询进行中交易订单列表接口
     * @param orderTradeListReqVO
     * @return
     */
    @PostMapping("/porcessing/list")
    public ResponseData<PageResultData> porcessingOrderTradeList(OrderTradeListReqVO orderTradeListReqVO) {
        if (orderTradeListReqVO == null) {
            logger.error("porcessingOrderTradeList 失败，原因是 orderTradeListReqVO is null");
            return ResponseFactory.paramsError("请求参数不能为空");
        }
        logger.info("porcessingOrderTradeList 请求参数:{}",JSON.toJSONString(orderTradeListReqVO));
        PageResultData<OrderTradeSimpleResVO> orderTradeListResVOPageResultData;
        try {
            orderTradeListResVOPageResultData = orderTradeService.pageTradeOrderList(orderTradeListReqVO);
        } catch (BaseServiceException e) {
            logger.error("porcessingOrderTradeList 失败，原因是:{}",e.getMessage());
            return ResponseFactory.paramsError(e.getMessage());
        } catch (Exception e) {
            logger.error("porcessingOrderTradeList 失败，原因是",e);
            return ResponseFactory.systemError(SYSTEM_ERROR_MSG);
        }
        logger.info("porcessingOrderTradeList 响应内容:{}",JSON.toJSONString(orderTradeListResVOPageResultData));
        return ResponseFactory.success(orderTradeListResVOPageResultData);
    }

}