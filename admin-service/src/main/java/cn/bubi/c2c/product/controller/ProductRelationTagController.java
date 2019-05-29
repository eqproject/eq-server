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
import cn.bubi.c2c.product.entity.ProductRelationTag;
import cn.bubi.c2c.product.service.ProductRelationTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品标签对应关系Controller
 * @author kaka
 * @version 1.0.1
 */
@Controller
@RequestMapping(value = "/product/tag")
public class ProductRelationTagController extends BaseController {

	@Autowired
	private ProductRelationTagService productRelationTagService;

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
		return "modules/product/productRelationTagList";
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
	 * @param productRelationTag
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"dataList"})
	public BaseTableData dataList(HttpServletRequest request, HttpServletResponse response, ProductRelationTag productRelationTag) {
		Map<String,Object> params = new HashMap<>();
		//取出 request 的参数信息分页信息 排序信息
		getInfoFromRequest(request,params);
		//数据的draw自增
		baseTableData = productRelationTagService.findDataTableByRecordForPage(productRelationTag,params);
		int draw = Integer.parseInt(request.getParameter("draw")==null?"0":request.getParameter("draw"));
		baseTableData.setDraw(++draw);
		return baseTableData;
	}

	/**
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     * @param productRelationTag
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "selectProductRelationTag")
    public BaseOpMsg<ProductRelationTag> selectProductRelationTag(ProductRelationTag productRelationTag) {
        BaseOpMsg<ProductRelationTag> result = new BaseOpMsg<ProductRelationTag>();
        if (productRelationTag != null) {
            List<ProductRelationTag> productRelationTagList = productRelationTagService.findListByRecord(productRelationTag);
            if (productRelationTagList != null && productRelationTagList.size() > 0) {
                result.setCode(StatusCode.CURD_SELECT_SUCCESS);
                result.setStatus("success");
                result.setMsg("查询数据成功！");
                result.setList(productRelationTagList);//返回查询数据
            } else {
                result.setCode(StatusCode.CURD_SELECT_FAILURE);
                result.setStatus("error");
                result.setMsg("查询数据失败！");
                if (logger.isDebugEnabled()) {
                    result.setDescription(productRelationTagService.getErrorInfo());
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
     * @param productRelationTag
     * @param opType 操作类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"modify"})
    public BaseOpMsg modify(HttpServletRequest request, ProductRelationTag productRelationTag, String opType) {
        SysUser user = UserUtil.getInstance().getUser();
        BaseOpMsg result = new BaseOpMsg();
        if (opType != null && !"".equals(opType)) {
            if ("save".equals(opType)) {//新增保存
                if (productRelationTagService.insertRecord(productRelationTag) > 0) {
                    result.setCode(StatusCode.CURD_ADD_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("保存数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_ADD_FAILURE);
                    result.setStatus("error");
                    result.setMsg("保存数据失败！");
                    if (logger.isDebugEnabled()) {
                        result.setDescription(productRelationTagService.getErrorInfo());
                    }
                }
            } else if ("update".equals(opType)) {//修改保存
                if (productRelationTagService.updateByPrimaryKeySelective(productRelationTag) > 0) {
                    result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("修改数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_UPDATE_FAILURE);
                    result.setStatus("error");
                    result.setMsg("修改数据失败！");
                    if (logger.isDebugEnabled()) {
                        result.setDescription(productRelationTagService.getErrorInfo());
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

    /**
     * 根据ids 删除数据
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteProductRelationTag")
    public BaseOpMsg deleteProductRelationTag(String ids, String virtual) {
        BaseOpMsg result = new BaseOpMsg();
        if (ids != null && !"".equals(ids)) {
            int delNum = 0;
            if (virtual.equals("false")) {
                delNum = productRelationTagService.deleteByPrimaryKeys(ids);
            } else {
                delNum = productRelationTagService.deleteVirtualByPrimaryKeys(ids);
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
                    result.setDescription(productRelationTagService.getErrorInfo());
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