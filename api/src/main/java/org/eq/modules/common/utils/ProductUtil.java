package org.eq.modules.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.eq.modules.business.ProductBusines;
import org.eq.modules.enums.ProductStateEnum;
import org.eq.basic.common.util.DateUtil;
import org.eq.modules.product.entity.*;
import org.eq.modules.product.vo.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品工具类
 * @author  kaka
 * @date  2019-05-27
 */
@SuppressWarnings("all")
public class ProductUtil  extends ProductBusines {


    /**
     * 转化对象实体
     * @param product
     * @return
     */
    public static ProductBaseVO transObj(Product product){
        if(product==null){
            return null;
        }
        ProductBaseVO result = new ProductBaseVO();
        result.setId(product.getId());
        result.setProductName(product.getName());
        result.setUnitPrice(product.getUnitPrice());
        result.setImg(product.getProductImg());
        result.setExpirationStart(product.getExpirationStart());
        result.setExpirationEnd(product.getExpirationEnd());
        result.setSort(product.getSort());
        ProductExtend productExtend = formatExtend(product.getExtendInfo());
        result.setReceive(productExtend.getReceive());
        result.setDesc(productExtend.getTicketDesc());
        return result;
    }


    /**
     * 转化对象实体
     * @param product
     * @return
     */
    public static ProductDetailVO transObjTOProductDetail(ProductAll productAll){
        if(productAll==null){
            return null;
        }
        ProductDetailVO result = transObjTOUserProductDetail(productAll);
        return result;
    }

    /**
     * 转化对象实体
     * @param product
     * @return
     */
    public static UserProductDetailVO transObjTOUserProductDetail(ProductAll productAll){
        if(productAll==null || productAll.getId()==null ){
            return null;
        }
        UserProductDetailVO result = new UserProductDetailVO();
        result.setId(productAll.getId());
        result.setProductName(productAll.getName());
        result.setUnitPrice(productAll.getUnitPrice()==null?0:productAll.getUnitPrice());
        result.setImg(productAll.getProductImg());
        result.setExpirationStart(productAll.getExpirationStart());
        result.setExpirationEnd(productAll.getExpirationEnd());
        result.setSort(productAll.getSort()==null?0:productAll.getSort());
        ProductExtend productExtend = formatExtend(productAll.getExtendInfo());
        result.setReceive(productExtend.getReceive());
        result.setDesc(productExtend.getTicketDesc());
        result.setAcceptName(productAll.getAcceptName());
        result.setAcceptImg(productAll.getAcceptIcon());
        result.setAcceptAddress(productAll.getAcceptAddress());
        result.setAcceptIntro(productAll.getAcceptIntro());
        result.setIssuerName(productAll.getIssuerName());
        result.setIssuerImg(productAll.getIssuerIcon());
        result.setIssuerAddress(productAll.getIssuerAddress());
        result.setIssuerIntro(productAll.getIssuerIntro());
        result.setNumber(productAll.getNumber());
        result.setLockedNum(productAll.getLockNumber());
        return result;
    }


    /**
     * 格式化商品扩展信息
     * TODO 完善商品扩展信息
     * @param extendInfo
     * @return
     */
    public static ProductExtend  formatExtend(String extendInfo){
        ProductExtend productExtend = new ProductExtend();
        if(StringUtils.isEmpty(extendInfo)){
            return  productExtend;
        }
        JSONObject obj = JSONObject.parseObject(extendInfo);
        productExtend.setReceive(obj.getString("receive"));
        productExtend.setTicketDesc(obj.getString("ticketDesc"));
        return productExtend;
    }

    /**
     * 根据查询条件 封装Example
     * @param searchBSearchProductVO 查询条件
     * @return
     */
    public static ProductExample createPlatformSearchExample(BSearchProduct bsearchProduct,boolean isall) {
        ProductExample example = new ProductExample();
        ProductExample.Criteria ca = example.or();
        example.setOrderByClause(" sort desc ");
        if(bsearchProduct.getProductId()>0){
            if(isall){
                ca.andIdAllEqualTo(bsearchProduct.getProductId());
            }else{
                ca.andIdEqualTo(bsearchProduct.getProductId());
            }

        }
        if(bsearchProduct.getState()!=null){
            ca.andStatusEqualTo(bsearchProduct.getState().intValue());
        }else{
            ca.andStatusEqualTo(ProductStateEnum.ONLINE.getState());
        }
        if(bsearchProduct.isOver()){
            ca.andExpirationEndLessThanOrEqualTo(DateUtil.getNowTimeStr());
        }else{
            ca.andExpirationEndGreaterThan(DateUtil.getNowTimeStr());
        }
        return example;
    }





    /**
     * 获取基本有效查询条件
     * @return
     */
    public static ProductExample getBaseEffectExample() {
        ProductExample example = new ProductExample();
        ProductExample.Criteria ca = example.or();
        example.setOrderByClause(" sort desc ");
        ca.andStatusEqualTo(ProductStateEnum.ONLINE.getState());
        ca.andExpirationEndGreaterThan(DateUtil.getNowTimeStr());
        return example;
    }


    /**
     * 获取基本有效查询条件
     * @return
     */
    public static UserProductStockExample getUserBaseEffectExample() {
        UserProductStockExample example = new UserProductStockExample();
        UserProductStockExample.Criteria ca = example.or();
        example.setOrderByClause(" p.sort desc ");
        ca.andProductStateEqualTo(ProductStateEnum.ONLINE.getState());
        ca.andProductExpirationEndGreaterThan(DateUtil.getNowTimeStr());
        return example;
    }


    /**
     * 获取区块链平台商品信息
     * @return
     * TODO 实现区块链同步商品接口
     */
    public List<TicketProductVO> listTicketProduct(){
        List<TicketProductVO> result = new ArrayList<>();
        TicketProductVO ticketProductVO = new TicketProductVO();
        ticketProductVO.setTicketId("TICKET01");
        ticketProductVO.setTicketName("区块链商品名称");
        ticketProductVO.setTicketFaceValue("1000");
        ticketProductVO.setTicketDesc("区块链商品简介");
        result.add(ticketProductVO);
        return result;
    }


    /**
     * 获取区块链平台商品信息
     * @return
     * TODO 实现区块链同步商品接口
     */

    public static Map<String,TicketProductVO> getTicketUserProduct(String address){
        Map<String,TicketProductVO> result = new HashMap<>();
        if("KAKA".equals(address)){
            TicketProductVO ticketProductVO = new TicketProductVO();
            ticketProductVO.setTicketId("TICKET01");
            ticketProductVO.setTrancheId("TRANCEID01");
            ticketProductVO.setTicketName("区块链商品名称");
            ticketProductVO.setTicketFaceValue("1000");
            ticketProductVO.setBalance("1000");
            ticketProductVO.setTicketDesc("区块链商品简介");
            String key = ticketProductVO.getTicketId()+"_"+ticketProductVO.getTrancheId();
            result.put(key,ticketProductVO);

            ticketProductVO = new TicketProductVO();
            ticketProductVO.setTicketId("TICKET02");
            ticketProductVO.setTrancheId("TRANCEID02");
            ticketProductVO.setTicketName("区块链商品名称");
            ticketProductVO.setTicketFaceValue("1000");
            ticketProductVO.setBalance("1000");
            ticketProductVO.setTicketDesc("区块链商品简介");
            key = ticketProductVO.getTicketId()+"_"+ticketProductVO.getTrancheId();
            result.put(key,ticketProductVO);

            ticketProductVO = new TicketProductVO();
            ticketProductVO.setTicketId("TICKET03");
            ticketProductVO.setTrancheId("TRANCEID03");
            ticketProductVO.setTicketName("区块链商品名称");
            ticketProductVO.setTicketFaceValue("1000");
            ticketProductVO.setBalance("1000");
            ticketProductVO.setTicketDesc("区块链商品简介");
            key = ticketProductVO.getTicketId()+"_"+ticketProductVO.getTrancheId();
            result.put(key,ticketProductVO);
        }
        return result;
    }

    public static void main(String[] args) {
        ProductAll productAll = new ProductAll();
        productAll.setId(1L);
        productAll.setAcceptAddress("111");
        productAll.setLockNumber(10);
        ProductDetailVO result = transObjTOProductDetail(productAll);
        System.out.println(result.getId()+"===");
    }


}
