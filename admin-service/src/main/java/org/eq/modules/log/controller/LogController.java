package org.eq.modules.log.controller;

import org.eq.basic.common.base.BaseController;
import org.eq.modules.order.entity.*;
import org.eq.modules.order.service.OrderAdLogService;
import org.eq.modules.trade.entity.*;
import org.eq.modules.trade.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/log")
public class LogController extends BaseController {
    @Autowired
    OrderTradeLogService orderTradeLogService;
    @Autowired
    OrderPaymentTradeLogService orderPaymentTradeLogService;
    @Autowired
    OrderRefundTradeLogService orderRefundTradeLogService;
    @Autowired
    OrderAdLogService orderAdLogService;
    @Autowired
    OrderAcceptLogService orderAcceptLogService;
    @Autowired
    OrderTransferLogService orderTransferLogService;

    /**
     * 获取订单交易日志
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/trade")
    public List<OrderTradeLog> getTradeLogList(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        OrderTradeLogExample example = new OrderTradeLogExample();
        example.or().andOrderTradeIdEqualTo(id);
        //时间排序
        example.setOrderByClause("create_date desc");
        List<OrderTradeLog> logList = orderTradeLogService.findListByExample(example);
        return logList;
    }

    /**
     * 获取订单支付日志
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/pay")
    public List<OrderPaymentTradeLog> getPayLogList(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        OrderPaymentTradeLogExample example = new OrderPaymentTradeLogExample();
        example.or().andOrderPayTradeIdEqualTo(id);
        //时间排序
        example.setOrderByClause("create_date desc");
        List<OrderPaymentTradeLog> logList = orderPaymentTradeLogService.findListByExample(example);
        return logList;
    }

    /**
     * 获取订单退款日志
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/refund")
    public List<OrderRefundTradeLog> getRefundList(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        OrderRefundTradeLogExample example = new OrderRefundTradeLogExample();
        example.or().andOrderRefundTradeIdEqualTo(id);
        //时间排序
        example.setOrderByClause("create_date desc");
        List<OrderRefundTradeLog> logList = orderRefundTradeLogService.findListByExample(example);
        return logList;
    }

    /**
     * 获取广告订单日志
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/ad")
    public List<OrderAdLog> getAdLogList(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        OrderAdLogExample example = new OrderAdLogExample();
        example.or().andOrderAdIdEqualTo(id);
        //时间排序
        example.setOrderByClause("create_date desc");
        List<OrderAdLog> logList = orderAdLogService.findListByExample(example);
        return logList;
    }

    /**
     * 获取承兑订单日志
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/accept")
    public List<OrderAcceptLog> getOrderAcceptLogList(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        OrderAcceptLogExample example = new OrderAcceptLogExample();
        example.or().andOrderAcceptIdEqualTo(id);
        //时间排序
        example.setOrderByClause("create_date desc");
        List<OrderAcceptLog> logList = orderAcceptLogService.findListByExample(example);
        return logList;
    }

    /**
     * 获取转让订单日志
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/transfer")
    public List<OrderTransferLog> getOrderTransferLogList(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        OrderTransferLogExample example = new OrderTransferLogExample();
        example.or().andOrderTransferIdEqualTo(id);
        //时间排序
        example.setOrderByClause("create_date desc");
        List<OrderTransferLog> logList = orderTransferLogService.findListByExample(example);
        return logList;
    }
}
