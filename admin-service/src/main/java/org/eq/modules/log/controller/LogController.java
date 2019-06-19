package org.eq.modules.log.controller;

import org.eq.basic.common.base.BaseController;
import org.eq.modules.trade.entity.*;
import org.eq.modules.trade.service.OrderPaymentTradeLogService;
import org.eq.modules.trade.service.OrderRefundTradeLogService;
import org.eq.modules.trade.service.OrderTradeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/log")
public class LogController extends BaseController {
    @Autowired
    OrderTradeLogService orderTradeLogService;

    @Autowired
    OrderPaymentTradeLogService orderPaymentTradeLogService;

    @Autowired
    OrderRefundTradeLogService orderRefundTradeLogService;


    @ResponseBody
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

    @ResponseBody
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

    @ResponseBody
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
}
