/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.base.BaseController;
import org.eq.basic.common.base.BaseOpMsg;
import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.config.sysUtil.UserUtil;
import org.eq.basic.common.status.StatusCode;
import org.eq.basic.common.util.DateUtil;
import org.eq.basic.modules.sys.entity.SysUser;
import org.eq.modules.enums.OrderTradeStateEnum;
import org.eq.modules.trade.entity.OrderTradeAppeal;
import org.eq.modules.trade.entity.OrderTradeAppealExample;
import org.eq.modules.trade.service.OrderTradeAppealService;
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
 * 投诉管理Controller
 * @author kaka
 * @version 1.0.1
 */
@Controller
@RequestMapping(value = "/orderTradeAppeal")
public class OrderTradeAppealController extends BaseController {

	@Autowired
	private OrderTradeAppealService orderTradeAppealService;

	@RequestMapping(value = {"list", ""})
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/c2c/trade/appealList";
	}


	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = {"dataList"})
	public BaseTableData dataList(HttpServletRequest request, HttpServletResponse response, OrderTradeAppeal orderTradeAppeal) {
		Map<String,Object> params = new HashMap<>();
        getInfoFromRequest(request,params);
        String orderDir = request.getParameter("orderDir");
        String orderName = request.getParameter("orderName");
        params.put("orderDir",orderDir);
        params.put("orderName",orderName);
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
            logger.error("OrderTradeAppealController dataList 格式化时间异常", e);
        }

		baseTableData = orderTradeAppealService.findDataTableByRecordForPage(orderTradeAppeal,params);
		int draw = Integer.parseInt(request.getParameter("draw")==null?"0":request.getParameter("draw"));
		baseTableData.setDraw(++draw);
		return baseTableData;
	}

	/**
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     * @param orderId
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "selectOrderTrade")
    public BaseOpMsg<OrderTradeAppeal> selectOrderTradeAppeal(HttpServletRequest request, HttpServletResponse response,  long orderId) {
        BaseOpMsg<OrderTradeAppeal> result = new BaseOpMsg();
        OrderTradeAppealExample example = new OrderTradeAppealExample();
        OrderTradeAppealExample.Criteria ca = example.or();

        OrderTradeAppeal orderTradeAppeal =null;
        try {
            if (orderId > 0) {
                ca.andIdEqualToForAll(orderId);
                List<OrderTradeAppeal> list  = orderTradeAppealService.findListByExample(example);
                if(!CollectionUtils.isEmpty(list)){
                    orderTradeAppeal = list.get(0);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(orderTradeAppeal!=null){
            result.setStatus("success");
            result.setCode(StatusCode.CURD_SELECT_SUCCESS);
        }else{
            result.setStatus("faile");
            result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
        }
        result.setMsg("查询数据成功！");
        result.setObject(orderTradeAppeal);
        return result;
    }

	/**
     * 审核
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "audit")
    public BaseOpMsg audit( long orderId,String remark) {
        BaseOpMsg<String> result = new BaseOpMsg();
        OrderTradeAppealExample example = new OrderTradeAppealExample();
        OrderTradeAppealExample.Criteria ca = example.or();
        OrderTradeAppeal orderTradeAppeal =null;
        try {
            if (orderId > 0) {
                ca.andIdEqualToForAll(orderId);
                List<OrderTradeAppeal> list  = orderTradeAppealService.findListByExample(example);
                if(!CollectionUtils.isEmpty(list)){
                    orderTradeAppeal = list.get(0);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(orderTradeAppeal==null){
            result.setMsg("数据为空");
            return result;
        }
        orderTradeAppeal.setRemark(remark);
        orderTradeAppeal.setStatus(1);
        orderTradeAppeal.setUpdateTime(new Date());
        String msg = "处理成功";
        try{
            orderTradeAppealService.updateByPrimaryKeySelective(orderTradeAppeal);
        }catch (Exception e){
            e.printStackTrace();
            msg="处理失败";
        }
        result.setMsg(msg);
        return result;
    }


    /**
     * 根据ids 删除数据
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteOrderTradeAppeal")
    public BaseOpMsg deleteOrderTradeAppeal(String ids, String virtual) {
        BaseOpMsg result = new BaseOpMsg();
        if (ids != null && !"".equals(ids)) {
            int delNum = 0;
            if (virtual.equals("false")) {
                delNum = orderTradeAppealService.deleteByPrimaryKeys(ids);
            } else {
                delNum = orderTradeAppealService.deleteVirtualByPrimaryKeys(ids);
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
                    result.setDescription(orderTradeAppealService.getErrorInfo());
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