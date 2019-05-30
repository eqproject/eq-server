/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.trade.controller;

import org.eq.basic.common.base.BaseController;
import org.eq.basic.common.base.BaseOpMsg;
import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.config.sysUtil.UserUtil;
import org.eq.basic.common.status.StatusCode;
import org.eq.basic.modules.sys.entity.SysUser;
import org.eq.basic.modules.sys.entity.SysUserInfo;
import org.eq.basic.modules.sys.service.SysUserInfoService;
import org.eq.modules.enums.UserOrderStateEnum;
import org.eq.modules.trade.entity.UserOrder;
import org.eq.modules.trade.service.UserOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 管理用户个人发布的订单信息
 *  主要解决我的求购和我的售卖
 * @author kaka
 * @version 1.0.0
 */
@SuppressWarnings("all")
@Controller
@RequestMapping(value = "/order/userOrder")
public class UserOrderController extends BaseController {

	@Autowired
	private UserOrderService userOrderService;

    @Autowired
    private SysUserInfoService sysUserInfoService;

    private static SimpleDateFormat format  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
		return "modules/c2c/order/list";
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
	 * @param userOrder
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"dataList"})
	public BaseTableData dataList(HttpServletRequest request, HttpServletResponse response, UserOrder  userOrder) {
		Map<String,Object> params = new HashMap<>();
		//取出 request 的参数信息分页信息 排序信息
		getInfoFromRequest(request,params);
		//数据的draw自增
        try{
            Date startTime  =null;
            Date endTime = null;
            if(!StringUtils.isEmpty(request.getParameter("beginCreateDate"))){
                String temp = request.getParameter("beginCreateDate").trim() +" 00:00:00";
                startTime = format.parse(temp);
            }
            if(!StringUtils.isEmpty(request.getParameter("endCreateDate"))){
                String temp = request.getParameter("endCreateDate").trim()+" 23:59:59";
                endTime = format.parse(temp);

            }
            params.put("startTime",startTime);
            params.put("endTime",endTime);

        }catch (Exception e){
            logger.error("UserOrderController dataList 格式化时间异常",e);
        }


		baseTableData = userOrderService.findDataTableByRecordForPage(userOrder,params);
        if(baseTableData!=null && baseTableData.getData()!=null){
            List<UserOrder> datas = baseTableData.getData();

            for(UserOrder temp : datas){
                SysUserInfo user = sysUserInfoService.selectByPrimaryKey(temp.getUpdateBy());
                if(user!=null){
                    temp.setCreateByName(user.getName());
                }else{
                    temp.setCreateByName("查无此人");
                }
            }
        }
		int draw = Integer.parseInt(request.getParameter("draw")==null?"0":request.getParameter("draw"));
		baseTableData.setDraw(++draw);
		return baseTableData;
	}

	/**
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     * @param userOrder
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "selectUserOrder")
    public BaseOpMsg<UserOrder> selectUserOrder(UserOrder userOrder) {
        BaseOpMsg<UserOrder> result = new BaseOpMsg<UserOrder>();
        if (userOrder != null) {
            List<UserOrder> userOrderList = userOrderService.findListByRecord(userOrder);
            if (userOrderList != null && userOrderList.size() > 0) {
                result.setCode(StatusCode.CURD_SELECT_SUCCESS);
                result.setStatus("success");
                result.setMsg("查询数据成功！");
                result.setList(userOrderList);//返回查询数据
            } else {
                result.setCode(StatusCode.CURD_SELECT_FAILURE);
                result.setStatus("error");
                result.setMsg("查询数据失败！");
                if (logger.isDebugEnabled()) {
                    result.setDescription(userOrderService.getErrorInfo());
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
     * @param userOrder
     * @param opType 操作类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"modify"})
    public BaseOpMsg modify(HttpServletRequest request, UserOrder userOrder, String opType) {
        SysUser user = UserUtil.getInstance().getUser();
        BaseOpMsg result = new BaseOpMsg();
        if (opType != null && !"".equals(opType)) {
            if ("save".equals(opType)) {//新增保存
                userOrder = initUserOrder(userOrder, false, user);
                if (userOrderService.insertRecord(userOrder) > 0) {
                    result.setCode(StatusCode.CURD_ADD_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("保存数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_ADD_FAILURE);
                    result.setStatus("error");
                    result.setMsg("保存数据失败！");
                    if (logger.isDebugEnabled()) {
                        result.setDescription(userOrderService.getErrorInfo());
                    }
                }
            } else if ("update".equals(opType)) {//修改保存
            	userOrder = initUserOrder(userOrder, true, user);
                if (userOrderService.updateByPrimaryKeySelective(userOrder) > 0) {
                    result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("修改数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_UPDATE_FAILURE);
                    result.setStatus("error");
                    result.setMsg("修改数据失败！");
                    if (logger.isDebugEnabled()) {
                        result.setDescription(userOrderService.getErrorInfo());
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

	private UserOrder initUserOrder(UserOrder userOrder, boolean ifUpdate, SysUser user) {
        if (!ifUpdate) {
            userOrder.setCreateBy(user.getId());
            userOrder.setCreateDate(new Date());
        }
        userOrder.setUpdateBy(user.getId());
        userOrder.setUpdateDate(new Date());
        return userOrder;
    }

    /**
     * 根据ids 删除数据
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteUserOrder")
    public BaseOpMsg deleteUserOrder(String ids, String virtual) {
        BaseOpMsg result = new BaseOpMsg();
        if (ids != null && !"".equals(ids)) {
            int delNum = 0;
            if (virtual.equals("false")) {
                delNum = userOrderService.deleteByPrimaryKeys(ids);
            } else {
                delNum = userOrderService.deleteVirtualByPrimaryKeys(ids);
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
                    result.setDescription(userOrderService.getErrorInfo());
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


    /**
     * 下架商品订单
     *
     * @param product
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "downOrder")
    public BaseOpMsg<String> downOrder(long id) {
        BaseOpMsg<String> result = new BaseOpMsg<String>();
        SysUser sysUser = getUser();

        if(id <=0 || sysUser==null){
            result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
            result.setStatus("error");
            result.setMsg("数据有误或无权限操作");
            return result;
        }
        UserOrder userOrder = userOrderService.selectByPrimaryKey(id);
        if(userOrder==null){
            result.setCode(StatusCode.CURD_SELECT_FAILURE);
            result.setStatus("error");
            result.setMsg("数据查询失败");
            return result;
        }


        if(userOrder.getStatus() != UserOrderStateEnum.ORDER_STATE_CREATE.getState()){
            result.setCode(StatusCode.CURD_SELECT_FAILURE);
            result.setStatus("error");
            result.setMsg("订单状态只有在默认情况下才能下架");
            return result;
        }

        int oldState = userOrder.getStatus();
        result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
        result.setStatus("success");
        result.setMsg("下架成功");

        userOrder = new UserOrder();
        userOrder.setId(id);

        userOrder.setStatus(UserOrderStateEnum.ORDER_STATE_CONCEL.getState());
        userOrder.setUpdateBy(sysUser.getId());
        userOrder.setUpdateDate(new Date());
        userOrder.setCancelDesc("管理员下架");


        int opresult = userOrderService.updateUserOrderStateById(userOrder,oldState);
        if(opresult<=0){
            result.setCode(StatusCode.CURD_DELETE_FAILURE);
            result.setStatus("fale");
            result.setMsg("下架失败");
            return result;
        }
        return result;
    }

}