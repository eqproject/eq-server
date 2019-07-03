package org.eq.modules.auth.controller;

import org.eq.basic.common.base.BaseController;
import org.eq.basic.common.base.BaseEntity;
import org.eq.basic.common.base.BaseOpMsg;
import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.config.sysUtil.UserUtil;
import org.eq.basic.common.status.StatusCode;
import org.eq.basic.modules.sys.entity.SysUser;
import org.eq.modules.auth.entity.User;
import org.eq.modules.auth.entity.UserExample;
import org.eq.modules.auth.service.UserService;
import org.eq.modules.enums.ClientTypeEnum;
import org.eq.modules.enums.UserWhiteListStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import org.eq.modules.auth.entity.UserClientWhitelist;
import org.eq.modules.auth.service.UserClientWhitelistService;

/**
 * 用户白名单Controller
 * @author hobe
 * @version 2019-07-03
 */
@Controller
@RequestMapping(value = "/user/auth/whitelist")
public class UserClientWhitelistController extends BaseController {

	@Autowired
	private UserClientWhitelistService userClientWhitelistService;

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
		return "modules/c2c/auth/whiteList";
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
	 * @param userClientWhitelist
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"dataList"})
	public BaseTableData dataList(HttpServletRequest request, HttpServletResponse response, UserClientWhitelist userClientWhitelist) {
		Map<String,Object> params = new HashMap<>();
		//取出 request 的参数信息分页信息 排序信息
		getInfoFromRequest(request,params);
		//数据的draw自增
		baseTableData = userClientWhitelistService.findDataTableByRecordForPage(userClientWhitelist,params);
		int draw = Integer.parseInt(request.getParameter("draw")==null?"0":request.getParameter("draw"));
		baseTableData.setDraw(++draw);
		return baseTableData;
	}

	/**
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     * @param userClientWhitelist
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "selectUserClientWhitelist")
    public BaseOpMsg<UserClientWhitelist> selectUserClientWhitelist(UserClientWhitelist userClientWhitelist) {
        BaseOpMsg<UserClientWhitelist> result = new BaseOpMsg<UserClientWhitelist>();
        if (userClientWhitelist != null) {
            List<UserClientWhitelist> userClientWhitelistList = userClientWhitelistService.findListByRecord(userClientWhitelist);
            if (userClientWhitelistList != null && userClientWhitelistList.size() > 0) {
                result.setCode(StatusCode.CURD_SELECT_SUCCESS);
                result.setStatus("success");
                result.setMsg("查询数据成功！");
                result.setList(userClientWhitelistList);//返回查询数据
            } else {
                result.setCode(StatusCode.CURD_SELECT_FAILURE);
                result.setStatus("error");
                result.setMsg("查询数据失败！");
                if (logger.isDebugEnabled()) {
                    result.setDescription(userClientWhitelistService.getErrorInfo());
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
     * @param userClientWhitelist
     * @param opType 操作类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"modify"})
    public BaseOpMsg modify(HttpServletRequest request, UserClientWhitelist userClientWhitelist, String opType) {
        // 判断手机号是否已注册
        User user = new User();
        user.setMobile(userClientWhitelist.getMobile());
        User checkUser = userService.selectByRecord(user);
        if (checkUser != null) {
            //更新用户表client_type
            checkUser.setClientType(ClientTypeEnum.B.getState());
            userService.updateByPrimaryKeySelective(checkUser);
            //设置已关联状态
            userClientWhitelist.setStatus(UserWhiteListStatusEnum.YES.getState());
        }else{
            //设置未关联状态
            userClientWhitelist.setStatus(UserWhiteListStatusEnum.NO.getState());
        }

        SysUser sysUser = UserUtil.getInstance().getUser();
        BaseOpMsg result = new BaseOpMsg();
        if (opType != null && !"".equals(opType)) {
            if ("save".equals(opType)) {//新增保存
                userClientWhitelist = initUserClientWhitelist(userClientWhitelist, false, sysUser);
                if (userClientWhitelistService.insertRecord(userClientWhitelist) > 0) {
                    result.setCode(StatusCode.CURD_ADD_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("保存数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_ADD_FAILURE);
                    result.setStatus("error");
                    result.setMsg("保存数据失败！");
                    if (logger.isDebugEnabled()) {
                        result.setDescription(userClientWhitelistService.getErrorInfo());
                    }
                }
            } else if ("update".equals(opType)) {//修改保存
            	userClientWhitelist = initUserClientWhitelist(userClientWhitelist, true, sysUser);
                if (userClientWhitelistService.updateByPrimaryKeySelective(userClientWhitelist) > 0) {
                    result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("修改数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_UPDATE_FAILURE);
                    result.setStatus("error");
                    result.setMsg("修改数据失败！");
                    if (logger.isDebugEnabled()) {
                        result.setDescription(userClientWhitelistService.getErrorInfo());
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

	private UserClientWhitelist initUserClientWhitelist(UserClientWhitelist userClientWhitelist, boolean ifUpdate, SysUser user) {
        if (!ifUpdate) {
            userClientWhitelist.setCreateBy(user.getId());
            userClientWhitelist.setCreateDate(new Date());
            userClientWhitelist.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        }
        userClientWhitelist.setUpdateBy(user.getId());
        userClientWhitelist.setUpdateDate(new Date());
        return userClientWhitelist;
    }

    /**
     * 根据ids 删除数据
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "delete")
    public BaseOpMsg deleteUserClientWhitelist(String ids, String virtual) {
        BaseOpMsg result = new BaseOpMsg();
        try {
            UserClientWhitelist ucwl = userClientWhitelistService.selectByPrimaryKey(Long.parseLong(ids));
            User user = new User();
            UserExample userExample = new UserExample();
            if (ucwl != null) {
                user.setClientType(ClientTypeEnum.C.getState());
                userExample.or().andMobileEqualTo(ucwl.getMobile());
                userService.updateByExampleSelective(user, userExample);
            }
        } catch (Exception e) {
            result.setCode(StatusCode.CURD_DELETE_FAILURE);
            result.setStatus("error");
            result.setMsg("修改用户客户端类型失败！");
            return result;
        }

        if (ids != null && !"".equals(ids)) {
            int delNum = 0;
            if (virtual.equals("false")) {
                delNum = userClientWhitelistService.deleteByPrimaryKeys(ids);
            } else {
                delNum = userClientWhitelistService.deleteVirtualByPrimaryKeys(ids);
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
                    result.setDescription(userClientWhitelistService.getErrorInfo());
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