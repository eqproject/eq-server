/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.auth.controller;

import org.eq.basic.common.base.BaseController;
import org.eq.basic.common.base.BaseEntity;
import org.eq.basic.common.base.BaseOpMsg;
import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.config.sysUtil.UserUtil;
import org.eq.basic.common.status.StatusCode;
import org.eq.basic.modules.sys.entity.SysUser;
import org.eq.modules.auth.entity.User;
import org.eq.modules.auth.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * 用户管理Controller
 * @author kaka
 * @version 1.0.
 */
@SuppressWarnings("all")
@Controller
@RequestMapping(value = "/user/auth/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

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
		return "modules/c2c/auth/userList";
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
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"dataList"})
	public BaseTableData dataList(HttpServletRequest request, HttpServletResponse response, User user) {
		Map<String,Object> params = new HashMap<>();
		//取出 request 的参数信息分页信息 排序信息
		getInfoFromRequest(request,params);
		//数据的draw自增
		baseTableData = userService.findDataTableByRecordForPage(user,params);
		int draw = Integer.parseInt(request.getParameter("draw")==null?"0":request.getParameter("draw"));
		baseTableData.setDraw(++draw);
		return baseTableData;
	}

	/**
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     * @param user
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "selectUser")
    public BaseOpMsg selectUser(long userId) {
        BaseOpMsg result = new BaseOpMsg();
        result.setCode(StatusCode.CURD_SELECT_FAILURE);
        result.setStatus("error");
        if(userId<=0){
            result.setMsg("参数有误");
            return result;
        }
        User user = userService.selectByPrimaryKey(userId);
        if(user==null){
            result.setMsg("用户不存在");
            return result;
        }
        result.setCode(StatusCode.CURD_SELECT_SUCCESS);
        result.setStatus("success");
        result.setMsg("查询数据成功！");
        List<User> userList = new ArrayList<User>();
        userList.add(user);
        result.setList(userList);
        return result;
    }

	/**
     * 保存or修改操作
     * @param request
     * @param user
     * @param opType 操作类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"modify"})
    public BaseOpMsg modify(HttpServletRequest request, User user, String opType) {
        SysUser sysUser = UserUtil.getInstance().getUser();
        BaseOpMsg result = new BaseOpMsg();
        if (opType != null && !"".equals(opType)) {
            if ("save".equals(opType)) {//新增保存
                user = initUser(user, false, sysUser);
                if (userService.insertRecord(user) > 0) {
                    result.setCode(StatusCode.CURD_ADD_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("保存数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_ADD_FAILURE);
                    result.setStatus("error");
                    result.setMsg("保存数据失败！");
                    if (logger.isDebugEnabled()) {
                        result.setDescription(userService.getErrorInfo());
                    }
                }
            } else if ("update".equals(opType)) {//修改保存
            	user = initUser(user, true, sysUser);
                if (userService.updateByPrimaryKeySelective(user) > 0) {
                    result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("修改数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_UPDATE_FAILURE);
                    result.setStatus("error");
                    result.setMsg("修改数据失败！");
                    if (logger.isDebugEnabled()) {
                        result.setDescription(userService.getErrorInfo());
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

	private User initUser(User user, boolean ifUpdate, SysUser sysUser) {
        if (!ifUpdate) {
            user.setCreateDate(new Date());
        }
        user.setUpdateDate(new Date());
        return user;
    }

    /**
     * 根据ids 删除数据
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteUser")
    public BaseOpMsg deleteUser(String ids, String virtual) {
        BaseOpMsg result = new BaseOpMsg();
        if (ids != null && !"".equals(ids)) {
            int delNum = 0;
            if (virtual.equals("false")) {
                delNum = userService.deleteByPrimaryKeys(ids);
            } else {
                delNum = userService.deleteVirtualByPrimaryKeys(ids);
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
                    result.setDescription(userService.getErrorInfo());
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
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     * @param product
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "opDelFlag")
    public BaseOpMsg<String> opDelFlag(long id,String commond) {
        BaseOpMsg<String> result = new BaseOpMsg<String>();
        SysUser sysUser = getUser();
        result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
        if(id <=0 || sysUser==null || StringUtils.isEmpty(commond)){
            result.setMsg("数据有误或无权限操作");
            return result;
        }
        Integer newDelFlag;
        if(!BaseEntity.DEL_FLAG_NORMAL.equals(commond)  && !BaseEntity.DEL_FLAG_DELETE.equals(commond)){
            result.setMsg("数据有误");
            return result;
        }
        User user = userService.selectByPrimaryKey(id);
        if(user==null){
            result.setMsg("数据查询失败");
            return result;
        }
        int opresult = userService.updateUserDelFlagById(user.getId(),Integer.valueOf(commond),user.getDelFlag());
        if(opresult<=0){
            result.setMsg("请稍后重试");
            return result;
        }
        result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
        result.setMsg("数据处理成功");
        return result;
    }

}