/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.controller;

import org.eq.basic.common.base.BaseController;
import org.eq.basic.common.base.BaseEntity;
import org.eq.basic.common.base.BaseOpMsg;
import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.config.sysUtil.UserUtil;
import org.eq.basic.common.status.StatusCode;
import org.eq.basic.modules.sys.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import org.eq.modules.trade.entity.OrderRefundTradeLog;
import org.eq.modules.trade.service.OrderRefundTradeLogService;

/**
 * 订单退款交易日志Controller
 * @author yufei.sun
 * @version 0.0.1
 */
@Controller
@RequestMapping(value = "/trade/orderRefundTradeLog")
public class OrderRefundTradeLogController extends BaseController {

	@Autowired
	private OrderRefundTradeLogService orderRefundTradeLogService;

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
		return "modules/trade/orderRefundTradeLogList";
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
	 * @param orderRefundTradeLog
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"dataList"})
	public BaseTableData dataList(HttpServletRequest request, HttpServletResponse response, OrderRefundTradeLog orderRefundTradeLog) {
		Map<String,Object> params = new HashMap<>();
		//取出 request 的参数信息分页信息 排序信息
		getInfoFromRequest(request,params);
		//数据的draw自增
		baseTableData = orderRefundTradeLogService.findDataTableByRecordForPage(orderRefundTradeLog,params);
		int draw = Integer.parseInt(request.getParameter("draw")==null?"0":request.getParameter("draw"));
		baseTableData.setDraw(++draw);
		return baseTableData;
	}

	/**
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     * @param orderRefundTradeLog
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "selectOrderRefundTradeLog")
    public BaseOpMsg<OrderRefundTradeLog> selectOrderRefundTradeLog(OrderRefundTradeLog orderRefundTradeLog) {
        BaseOpMsg<OrderRefundTradeLog> result = new BaseOpMsg<OrderRefundTradeLog>();
        if (orderRefundTradeLog != null) {
            List<OrderRefundTradeLog> orderRefundTradeLogList = orderRefundTradeLogService.findListByRecord(orderRefundTradeLog);
            if (orderRefundTradeLogList != null && orderRefundTradeLogList.size() > 0) {
                result.setCode(StatusCode.CURD_SELECT_SUCCESS);
                result.setStatus("success");
                result.setMsg("查询数据成功！");
                result.setList(orderRefundTradeLogList);//返回查询数据
            } else {
                result.setCode(StatusCode.CURD_SELECT_FAILURE);
                result.setStatus("error");
                result.setMsg("查询数据失败！");
                if (logger.isDebugEnabled()) {
                    result.setDescription(orderRefundTradeLogService.getErrorInfo());
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
     * 保存or修改操作
     * @param request
     * @param orderRefundTradeLog
     * @param opType 操作类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"modify"})
    public BaseOpMsg modify(HttpServletRequest request, OrderRefundTradeLog orderRefundTradeLog, String opType) {
        SysUser user = UserUtil.getInstance().getUser();
        BaseOpMsg result = new BaseOpMsg();
        if (opType != null && !"".equals(opType)) {
            if ("save".equals(opType)) {//新增保存
                orderRefundTradeLog = initOrderRefundTradeLog(orderRefundTradeLog, false, user);
                if (orderRefundTradeLogService.insertRecord(orderRefundTradeLog) > 0) {
                    result.setCode(StatusCode.CURD_ADD_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("保存数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_ADD_FAILURE);
                    result.setStatus("error");
                    result.setMsg("保存数据失败！");
                    if (logger.isDebugEnabled()) {
                        result.setDescription(orderRefundTradeLogService.getErrorInfo());
                    }
                }
            } else if ("update".equals(opType)) {//修改保存
            	orderRefundTradeLog = initOrderRefundTradeLog(orderRefundTradeLog, true, user);
                if (orderRefundTradeLogService.updateByPrimaryKeySelective(orderRefundTradeLog) > 0) {
                    result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("修改数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_UPDATE_FAILURE);
                    result.setStatus("error");
                    result.setMsg("修改数据失败！");
                    if (logger.isDebugEnabled()) {
                        result.setDescription(orderRefundTradeLogService.getErrorInfo());
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

	private OrderRefundTradeLog initOrderRefundTradeLog(OrderRefundTradeLog orderRefundTradeLog, boolean ifUpdate, SysUser user) {
        if (!ifUpdate) {
            orderRefundTradeLog.setCreateDate(new Date());
        }
        return orderRefundTradeLog;
    }

    /**
     * 根据ids 删除数据
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteOrderRefundTradeLog")
    public BaseOpMsg deleteOrderRefundTradeLog(String ids, String virtual) {
        BaseOpMsg result = new BaseOpMsg();
        if (ids != null && !"".equals(ids)) {
            int delNum = 0;
            if (virtual.equals("false")) {
                delNum = orderRefundTradeLogService.deleteByPrimaryKeys(ids);
            } else {
                delNum = orderRefundTradeLogService.deleteVirtualByPrimaryKeys(ids);
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
                    result.setDescription(orderRefundTradeLogService.getErrorInfo());
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