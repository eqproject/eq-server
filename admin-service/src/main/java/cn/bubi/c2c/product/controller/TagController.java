/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.c2c.product.controller;

import cn.bubi.basic.common.base.BaseController;
import cn.bubi.basic.common.base.BaseOpMsg;
import cn.bubi.basic.common.base.BaseTableData;
import cn.bubi.basic.common.config.sysUtil.UserUtil;
import cn.bubi.basic.common.status.StatusCode;
import cn.bubi.basic.modules.sys.entity.SysUser;
import cn.bubi.basic.modules.sys.entity.SysUserInfo;
import cn.bubi.basic.modules.sys.service.SysUserInfoService;
import cn.bubi.c2c.enums.TagStateEnum;
import cn.bubi.c2c.product.entity.Tag;
import cn.bubi.c2c.product.service.TagService;
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
 * 标签管理Controller
 * @author kaka
 * @version 1.0.1
 */
@Controller
@RequestMapping(value = "/tag")
public class TagController extends BaseController {

	@Autowired
	private TagService tagService;

    @Autowired
    private SysUserInfoService sysUserInfoService;

	/**
	 * List页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"list", ""})
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/c2c/tag/list";
	}

	/**
	 * datatable 返回列表数据
	 * @param request
	 * @param response
	 * @param tag
	 * @return
	 */
	@SuppressWarnings("all")
	@ResponseBody
	@RequestMapping(value = {"dataList"})
	public BaseTableData dataList(HttpServletRequest request, HttpServletResponse response, Tag tagInfo) {
		Map<String,Object> params = new HashMap<>();
		getInfoFromRequest(request,params);
		baseTableData = tagService.findDataTableByRecordForPage(tagInfo,params);
        if(baseTableData!=null && baseTableData.getData()!=null){
            List<Tag> datas = baseTableData.getData();
            for(Tag temp : datas){
                SysUserInfo user = sysUserInfoService.selectByPrimaryKey(temp.getCreateBy());
                if(user!=null){
                    temp.setCreateName(user.getName());
                }else{
                    temp.setCreateName("查无此人");
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
     * @param tagId
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "selectTag")
    public BaseOpMsg<Tag> selectTag(Long tagId) {
        BaseOpMsg<Tag> result = new BaseOpMsg<>();
        if(tagId == null){
            result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
            result.setStatus("error");
            result.setMsg("查询数据失败！");
            return result;
        }
        Tag tag = tagService.selectByPrimaryKey(tagId);
        if(tag==null){
            result.setCode(StatusCode.CURD_SELECT_FAILURE);
            result.setStatus("error");
            result.setMsg("查询数据失败！");
            return  result;
        }
        result.setCode(StatusCode.CURD_SELECT_SUCCESS);
        result.setStatus("success");
        result.setMsg("查询数据成功！");
        result.setObject(tag);
        return result;

    }

    /**
     * 修改标签状态
     * @param id
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "opstate")
    public BaseOpMsg<Tag> opstate(long id) {
        BaseOpMsg<Tag> result = new BaseOpMsg<>();
        SysUser sysUser = getUser();
        if(id<=0 || sysUser==null ){
            result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
            result.setMsg("查询数据失败！");
            return result;
        }
        Tag tag = tagService.selectByPrimaryKey(id);
        if(tag==null){
            result.setCode(StatusCode.CURD_SELECT_FAILURE);
            result.setMsg("查询数据失败！");
            return  result;
        }
        tag.setUpdateBy(sysUser.getId());
        tag.setUpdateDate(new Date());
        if(tag.getStatus()==TagStateEnum.DEFAULT.getState()){
            tag.setStatus(TagStateEnum.DEL.getState());
        }else{
            tag.setStatus(TagStateEnum.DEFAULT.getState());
        }

        int updateResult = tagService.updateByPrimaryKey(tag);
        if(updateResult<=0){
            result.setCode(StatusCode.CURD_SELECT_FAILURE);
            result.setMsg("更新数据失败");
            return  result;
        }
        result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
        result.setStatus("success");
        result.setMsg("查询数据成功！");
        result.setObject(tag);
        return result;

    }

	/**
     * 保存or修改操作
     * @param request
     * @param tag
     * @param opType 操作类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"modify"})
    public BaseOpMsg modify(HttpServletRequest request, Tag tag, String opType) {
        SysUser user = UserUtil.getInstance().getUser();
        BaseOpMsg result = new BaseOpMsg();
        if(!"save".equals(opType) && !"update".equals(opType) ){
            result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
            result.setStatus("error");
            result.setMsg("操作违法！");
            return result;
        }
        if ("save".equals(opType)) {
            tag = initTag(tag, false, user);
            if (tagService.insertRecord(tag) > 0) {
                result.setCode(StatusCode.CURD_ADD_SUCCESS);
                result.setStatus("success");
                result.setMsg("保存数据成功！");
            } else {
                result.setCode(StatusCode.CURD_ADD_FAILURE);
                result.setStatus("error");
                result.setMsg("保存数据失败！");
            }
            return result;
        }
        tag = initTag(tag, true, user);
        if (tagService.updateByPrimaryKeySelective(tag) > 0) {
            result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
            result.setStatus("success");
            result.setMsg("修改数据成功！");
        } else {
            result.setCode(StatusCode.CURD_UPDATE_FAILURE);
            result.setStatus("error");
            result.setMsg("修改数据失败！");
        }
        return result;
    }

	private Tag initTag(Tag tag, boolean isUpdate, SysUser user) {
        if (!isUpdate) {
            tag.setCreateBy(user.getId());
            tag.setCreateDate(new Date());
            tag.setStatus(TagStateEnum.DEFAULT.getState());
        }
        tag.setUpdateBy(user.getId());
        tag.setUpdateDate(new Date());
        return tag;
    }

    /**
     * 根据ids 删除数据
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteTag")
    public BaseOpMsg deleteTag(String ids, String virtual) {
        BaseOpMsg result = new BaseOpMsg();
        if (ids != null && !"".equals(ids)) {
            int delNum = 0;
            if (virtual.equals("false")) {
                delNum = tagService.deleteByPrimaryKeys(ids);
            } else {
                delNum = tagService.deleteVirtualByPrimaryKeys(ids);
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
                    result.setDescription(tagService.getErrorInfo());
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
     * 获取所有标签数据
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "listTag")
    public BaseOpMsg listTag() {
        BaseOpMsg result = new BaseOpMsg();
        result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
        result.setStatus("success");
        result.setMsg("查询数据成功！");
        Tag tag = new Tag();
        tag.setStatus(TagStateEnum.DEFAULT.getState());
        List<Tag> tagList = tagService.findListByRecord(tag);
        result.setList(tagList);
        return result;
    }

}