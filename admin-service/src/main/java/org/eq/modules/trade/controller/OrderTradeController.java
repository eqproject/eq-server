package org.eq.modules.trade.controller;

import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.base.BaseController;
import org.eq.basic.common.base.BaseOpMsg;
import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.config.sysUtil.UserUtil;
import org.eq.basic.common.status.StatusCode;
import org.eq.basic.common.util.DateUtil;
import org.eq.basic.modules.sys.entity.SysUser;
import org.eq.modules.order.entity.OrderAd;
import org.eq.modules.trade.entity.OrderTrade;
import org.eq.modules.trade.service.OrderTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单交易Controller
 * @author yufei.sun
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/trade/order")
public class OrderTradeController extends BaseController {

    @Autowired
    private OrderTradeService orderTradeService;

    /**
     * List页面
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"list", ""})
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
        //下拉选查询 自定义内容需要手动添加
        return "modules/c2c/trade/order/list";
    }

    /**
     * datatable 返回列表数据
     {
     "draw": 4,
     "recordsTotal": 57,
     "recordsFiltered": 57,
     "data": [
     [
     "Charde",
     "Marshall",
     "Regional Director",
     "San Francisco",
     "16th Oct 08",
     "$470,600"
     ],[]...
     ]
     }
     *
     *
     * @param request
     * @param response
     * @param orderTrade
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"dataList"})
    public BaseTableData dataList(HttpServletRequest request, HttpServletResponse response, OrderTrade orderTrade) {
        Map<String, Object> params = new HashMap<>();
        //取出 request 的参数信息分页信息 排序信息
        getInfoFromRequest(request, params);

        try {
            Date beginCreateDate = null;
            Date endCreateDate = null;
            if (!StringUtils.isEmpty(request.getParameter("beginCreateDate"))) {
                String temp = request.getParameter("beginCreateDate").trim() + " 00:00:00";
                beginCreateDate = DateUtil.paseTimeStr(temp);
            }
            if (!StringUtils.isEmpty(request.getParameter("endCreateDate"))) {
                String temp = request.getParameter("endCreateDate").trim() + " 23:59:59";
                endCreateDate = DateUtil.paseTimeStr(temp);

            }
            params.put("beginCreateDate", beginCreateDate);
            params.put("endCreateDate", endCreateDate);

        } catch (Exception e) {
            logger.error("OrderTradeController dataList 格式化时间异常", e);
        }
        //数据的draw自增
        baseTableData = orderTradeService.findDataTableByRecordForPage(orderTrade, params);
        int draw = Integer.parseInt(request.getParameter("draw") == null ? "0" : request.getParameter("draw"));
        baseTableData.setDraw(++draw);
        return baseTableData;
    }

    /**
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     * @param orderTrade
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "selectOrderTrade")
    public BaseOpMsg<OrderTrade> selectOrderTrade(OrderTrade orderTrade) {
        BaseOpMsg<OrderTrade> result = new BaseOpMsg<OrderTrade>();
        if (orderTrade != null) {
            List<OrderTrade> orderTradeList = orderTradeService.findListByRecord(orderTrade);
            if (orderTradeList != null && orderTradeList.size() > 0) {
                result.setCode(StatusCode.CURD_SELECT_SUCCESS);
                result.setStatus("success");
                result.setMsg("查询数据成功！");
                result.setList(orderTradeList);//返回查询数据
            } else {
                result.setCode(StatusCode.CURD_SELECT_FAILURE);
                result.setStatus("error");
                result.setMsg("查询数据失败！");
                if (logger.isDebugEnabled()) {
                    result.setDescription(orderTradeService.getErrorInfo());
                }
            }
        } else {
            result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
            result.setStatus("error");
            result.setMsg("查询数据失败！");
            if (logger.isDebugEnabled()) {
                result.setDescription("查询条件为空");
            }
        }
        return result;
    }
    /**
     * 充新放券
     * @param tradeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "reVoucher")
    public BaseOpMsg reVoucher(long tradeId) {
        BaseOpMsg<OrderTrade> result = new BaseOpMsg();
        SysUser user = UserUtil.getInstance().getUser();
        result.setCode(StatusCode.CURD_UPDATE_FAILURE);
        if(user==null){
            result.setMsg("用户不存在");
            return result;
        }
        OrderTrade orderTrade =null;
        try {
            if (tradeId > 0) {
                orderTrade = orderTradeService.selectByPrimaryKey(tradeId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(orderTrade==null){
            result.setMsg("单号不存在");
            return result;
        }
        boolean voucher = false;
        try{
            voucher = orderTradeService.voucherTrade(tradeId,user);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(voucher){
            result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
        }
        return result;
    }


    /**
     * 保存or修改操作
     * @param request
     * @param orderTrade
     * @param opType 操作类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"modify"})
    public BaseOpMsg modify(HttpServletRequest request, OrderTrade orderTrade, String opType) {
        SysUser user = UserUtil.getInstance().getUser();
        BaseOpMsg result = new BaseOpMsg();
        if (opType != null && !"".equals(opType)) {
            if ("save".equals(opType)) {//新增保存
                orderTrade = initOrderTrade(orderTrade, false, user);
                if (orderTradeService.insertRecord(orderTrade) > 0) {
                    result.setCode(StatusCode.CURD_ADD_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("保存数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_ADD_FAILURE);
                    result.setStatus("error");
                    result.setMsg("保存数据失败！");
                    if (logger.isDebugEnabled()) {
                        result.setDescription(orderTradeService.getErrorInfo());
                    }
                }
            } else if ("update".equals(opType)) {//修改保存
                orderTrade = initOrderTrade(orderTrade, true, user);
                if (orderTradeService.updateByPrimaryKeySelective(orderTrade) > 0) {
                    result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("修改数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_UPDATE_FAILURE);
                    result.setStatus("error");
                    result.setMsg("修改数据失败！");
                    if (logger.isDebugEnabled()) {
                        result.setDescription(orderTradeService.getErrorInfo());
                    }
                }
            } else {
                result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
                result.setStatus("error");
                result.setMsg("操作违法！");
                if (logger.isDebugEnabled()) {
                    result.setDescription("opType 违法");
                }
            }
        } else {
            result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
            result.setStatus("error");
            result.setMsg("操作违法！");
            if (logger.isDebugEnabled()) {
                result.setDescription("opType 违法 或 null");
            }
        }
        return result;
    }

    private OrderTrade initOrderTrade(OrderTrade orderTrade, boolean ifUpdate, SysUser user) {
        if (!ifUpdate) {
            orderTrade.setCreateDate(new Date());
        }
        orderTrade.setUpdateDate(new Date());
        return orderTrade;
    }

    /**
     * 根据ids 删除数据
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteOrderTrade")
    public BaseOpMsg deleteOrderTrade(String ids, String virtual) {
        BaseOpMsg result = new BaseOpMsg();
        if (ids != null && !"".equals(ids)) {
            int delNum = 0;
            if (virtual.equals("false")) {
                delNum = orderTradeService.deleteByPrimaryKeys(ids);
            } else {
                delNum = orderTradeService.deleteVirtualByPrimaryKeys(ids);
            }
            if (delNum > 0) {
                result.setCode(StatusCode.CURD_DELETE_SUCCESS);
                result.setStatus("success");
                result.setMsg("删除数据成功！");
            } else {
                result.setCode(StatusCode.CURD_DELETE_FAILURE);
                result.setStatus("error");
                result.setMsg("删除数据失败！");
                if (logger.isDebugEnabled()) {
                    result.setDescription(orderTradeService.getErrorInfo());
                }
            }
        } else {
            result.setCode(StatusCode.CURD_DELETE_FAILURE);
            result.setStatus("error");
            result.setMsg("删除数据失败！");
            if (logger.isDebugEnabled()) {
                result.setDescription("数据ids为空");
            }
        }
        return result;
    }

}