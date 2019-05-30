/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.controller;

import org.eq.basic.common.base.BaseController;
import org.eq.basic.common.base.BaseOpMsg;
import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.config.sysUtil.UserUtil;
import org.eq.basic.common.status.StatusCode;
import org.eq.basic.modules.sys.entity.SysUser;
import org.eq.basic.modules.sys.entity.SysUserInfo;
import org.eq.basic.modules.sys.service.SysUserInfoService;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.service.ProductService;
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
 * 商品信息Controller
 * @author kaka
 * @version 2019.05.08
 */
@SuppressWarnings("all")
@Controller
@RequestMapping(value = "/product")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;

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
		//下拉选查询 自定义内容需要手动添加
		return "modules/c2c/product/list";
	}

	/**
	 * @param request
	 * @param response
	 * @param product
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = {"dataList"})
	public BaseTableData dataList(HttpServletRequest request, HttpServletResponse response, Product product) {
		Map<String,Object> params = new HashMap<>();
		getInfoFromRequest(request,params);
		String orderDir = request.getParameter("orderDir");
		String orderName = request.getParameter("orderName");
        params.put("orderDir",orderDir);
        params.put("orderName",orderName);

		baseTableData = productService.findDataTableByRecordForPage(product,params);
		if(baseTableData!=null && baseTableData.getData()!=null){
            List<Product> datas = baseTableData.getData();
            for(Product temp : datas){
                SysUserInfo user = sysUserInfoService.selectByPrimaryKey(temp.getUpdateBy());
                if(user!=null){
                    temp.setCreateUserName(user.getName());
                }else{
                    temp.setCreateUserName("查无此人");
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
     * @param product
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "selectProduct")
    public BaseOpMsg<Product> selectProduct(long id) {
        BaseOpMsg<Product> result = new BaseOpMsg<Product>();
        if(id<=0){
            result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
            result.setStatus("error");
            result.setMsg("查询数据失败！");
            return  result;
        }
        Product product = productService.selectByPrimaryKey(id);
        if(product==null){
            result.setCode(StatusCode.CURD_SELECT_FAILURE);
            result.setStatus("error");
            result.setMsg("查询数据失败！");
            return  result;
        }
        result.setCode(StatusCode.CURD_SELECT_SUCCESS);
        result.setStatus("success");
        result.setMsg("查询数据成功！");
        result.setObject(product);
        return result;
    }


    /**
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     * @param product
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "updataSort")
    public BaseOpMsg<String> updataSort(long id,int command) {
        BaseOpMsg<String> result = new BaseOpMsg<String>();
        SysUser sysUser = getUser();

        if(id <=0 || sysUser==null){
            result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
            result.setStatus("error");
            result.setMsg("数据有误或无权限操作");
            return result;
        }
        Product product = productService.selectByPrimaryKey(id);
        if(product==null){
            result.setCode(StatusCode.CURD_SELECT_FAILURE);
            result.setStatus("error");
            result.setMsg("数据查询失败");
            return result;
        }
        boolean isup = false;
        if(command>0){
            isup = true;
        }
        Product target = productService.getProductBetweenScore(product.getSort(),isup);
        result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
        result.setStatus("success");
        result.setMsg("数据调整成功");
        if(target==null){
            return result;
        }
        product = new Product();
        product.setId(id);
        if(isup){
            product.setSort(target.getSort()+1);
        }else{
            product.setSort(target.getSort()-1);
        }
        product.setUpdateBy(sysUser.getId());
        product.setUpdateDate(new Date());

        int opresult = productService.updateByPrimaryKeySelective(product);
        if(opresult<=0){
            result.setCode(StatusCode.CURD_DELETE_FAILURE);
            result.setStatus("fale");
            result.setMsg("数据调整失败");
            return result;
        }
        return result;
    }

	/**
     * 保存or修改操作
     * @param request
     * @param product
     * @param opType 操作类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"modify"})
    public BaseOpMsg modify(HttpServletRequest request, Product product, String opType) {
        SysUser user = UserUtil.getInstance().getUser();
        BaseOpMsg result = new BaseOpMsg();
        if (opType != null && !"".equals(opType)) {
            if ("save".equals(opType)) {//新增保存
                product = initProduct(product, false, user);
                if (productService.insertRecord(product) > 0) {
                    result.setCode(StatusCode.CURD_ADD_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("保存数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_ADD_FAILURE);
                    result.setStatus("error");
                    result.setMsg("保存数据失败！");
                    if (logger.isDebugEnabled()) {
                        result.setDescription(productService.getErrorInfo());
                    }
                }
            } else if ("update".equals(opType)) {//修改保存
            	product = initProduct(product, true, user);
                if (productService.updateByPrimaryKeySelective(product) > 0) {
                    result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
                    result.setStatus("success");
                    result.setMsg("修改数据成功！");
                } else {
                    result.setCode(StatusCode.CURD_UPDATE_FAILURE);
                    result.setStatus("error");
                    result.setMsg("修改数据失败！");
                    if (logger.isDebugEnabled()) {
                        result.setDescription(productService.getErrorInfo());
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

	private Product initProduct(Product product, boolean ifUpdate, SysUser user) {
        if (!ifUpdate) {
            product.setCreateBy(user.getId());
            product.setCreateDate(new Date());
        }
        product.setUpdateBy(user.getId());
        product.setUpdateDate(new Date());
        return product;
    }

    /**
     * 根据ids 删除数据
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteProduct")
    public BaseOpMsg deleteProduct(String ids, String virtual) {
        BaseOpMsg result = new BaseOpMsg();
        if (ids != null && !"".equals(ids)) {
            int delNum = 0;
            if (virtual.equals("false")) {
                delNum = productService.deleteByPrimaryKeys(ids);
            } else {
                delNum = productService.deleteVirtualByPrimaryKeys(ids);
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
                    result.setDescription(productService.getErrorInfo());
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