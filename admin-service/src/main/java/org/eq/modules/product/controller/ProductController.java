/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.product.controller;

import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.base.BaseController;
import org.eq.basic.common.base.BaseOpMsg;
import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.config.sysUtil.UserUtil;
import org.eq.basic.common.status.StatusCode;
import org.eq.basic.modules.sys.entity.SysUser;
import org.eq.basic.modules.sys.service.SysUserInfoService;
import org.eq.modules.enums.ProductStateEnum;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.ProductAll;
import org.eq.modules.product.entity.ProductExample;
import org.eq.modules.product.entity.Tag;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.product.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
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
        ProductAll productAll = productService.getProductAll(id);
        if(productAll==null){
            result.setCode(StatusCode.CURD_SELECT_FAILURE);
            result.setStatus("error");
            result.setMsg("查询数据失败！");
            return  result;
        }

        result.setCode(StatusCode.CURD_SELECT_SUCCESS);
        result.setStatus("success");
        result.setMsg("查询数据成功！");
        result.setObject(productAll);
        return result;
    }
    /**
     * 同步商品
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "loadProduct")
    public BaseOpMsg<Integer> loadProduct() {
        BaseOpMsg<Integer> result = new BaseOpMsg<Integer>();
        int num = productService.loadProduct();
        result.setCode(StatusCode.CURD_SELECT_SUCCESS);
        result.setStatus("success");
        result.setMsg("查询数据成功！");
        result.setObject(num);
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
     * 根据条件 查询数据 返回页面json数据
     * 条件为空时 查询失败
     * @param product
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "updataTag")
    public BaseOpMsg<String> updataTag(long id,String tagIds) {
        BaseOpMsg<String> result = new BaseOpMsg<String>();
        result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
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
        if(StringUtils.isEmpty(tagIds)){
            tagIds = "";
        }
        String[] tagIdArray = tagIds.split(",");
        StringBuffer tagNamesBuffer = new StringBuffer("");
        StringBuffer tagIdBuffer = new StringBuffer("");
        for(int i=0;i<tagIdArray.length;i++){
            String tid = tagIdArray[i];
            Tag tag = tagService.selectByPrimaryKey(Long.valueOf(tid));
            if(tag!=null){
                tagIdBuffer.append(tag.getId());
                tagNamesBuffer.append(tag.getName());
            }
            if(i!=tagIdArray.length-1){
                tagIdBuffer.append(",");
                tagNamesBuffer.append(",");
            }
        }
        Product update = new Product();
        update.setTagIds(tagIdBuffer.toString());
        update.setTagNames(tagNamesBuffer.toString());
        update.setUpdateDate(new Date());
        boolean isupdate = false;
        try{
            productService.updateByExampleSelective(update,getExample(product.getId()));
            isupdate = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!isupdate){
            result.setCode(StatusCode.CURD_DELETE_FAILURE);
            result.setMsg("更新失败");
            return result;
        }
        return result;
    }



    /**
     * 商品ID
     * 条件为空时 查询失败
     * @param productId
     * @param command 上下架操作指令
     * @return BaseOpMsg
     */
    @ResponseBody
    @RequestMapping(value = "opRacks")
    public BaseOpMsg<String> opRacks(long productId,String command) {
        BaseOpMsg<String> result = new BaseOpMsg<String>();
        SysUser sysUser = getUser();
        result.setCode(StatusCode.CURD_UPDATE_SUCCESS);
        if(productId <=0 || sysUser==null){
            result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
            result.setMsg("数据有误或无权限操作");
            return result;
        }
        if(!"up".equals(command) && !"down".equals(command)){
            result.setCode(StatusCode.REQUEST_CONTENT_ERROR);
            result.setMsg("指令有误");
            return result;
        }

        Product product = productService.selectByPrimaryKey(productId);
        if(product==null){
            result.setCode(StatusCode.CURD_SELECT_FAILURE);
            result.setMsg("商品信息不存在");
            return result;
        }
        int targetState = -1;
        int nowState = product.getStatus();
        if("up".equals(command)){
            targetState  = ProductStateEnum.ONLINE.getState();
            if(nowState!=ProductStateEnum.DEFAULT.getState()){
                result.setCode(StatusCode.CURD_SELECT_FAILURE);
                result.setMsg("商品为待上架状态才能进行上架操作");
                return result;
            }
        }else{
            targetState  = ProductStateEnum.OFFLINE.getState();
        }
        if(targetState==nowState){
            return result;
        }
        int opresult =  productService.updateProductState(targetState,product.getStatus(),productId);
        if(opresult<=0){
            result.setCode(StatusCode.CURD_DELETE_FAILURE);
            result.setMsg("上下架失败");
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
            product.setCreateDate(new Date());
        }
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




    private static ProductExample getExample(long pid){
        ProductExample example = new ProductExample();
        ProductExample.Criteria ca = example.or();
        ca.andIdEqualTo(pid);
        return example;

    }

}