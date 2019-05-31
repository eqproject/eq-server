package org.eq.modules.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.eq.modules.enums.ProductStateEnum;
import org.eq.modules.product.entity.Product;
import org.eq.modules.product.entity.ProductExample;
import org.eq.modules.product.vo.ProductExtend;
import org.eq.modules.product.vo.ProductVO;
import org.eq.modules.product.vo.SearchProductVO;

/**
 * 商品工具类
 * @author  kaka
 * @date  2019-05-27
 */
public class ProductUtil {


    /**
     * 转化对象实体
     * @param product
     * @return
     */
    public static ProductVO transObj(Product product){
        if(product==null){
            return null;
        }
        ProductVO result = new ProductVO();
        result.setId(product.getId());
        result.setProductName(product.getName());
        result.setUnitPrice(product.getUnitPrice());
        result.setImg(product.getProductImg());
        result.setDesc(product.getRemarks());
        ProductExtend productExtend = formatExtend(product.getExtendInfo());
        result.setReceive(productExtend.getReceive());
        result.setExpirationStart(product.getExpirationStart());
        result.setExpirationEnd(product.getExpirationEnd());
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
        return productExtend;
    }

    /**
     * 根据查询条件 封装Example
     * @param searchProductVO 查询条件
     * @return
     */
    public static ProductExample createPlatformSearchExample(SearchProductVO searchProductVO) {
        ProductExample example = new ProductExample();
        ProductExample.Criteria ca = example.or();
        example.setOrderByClause(" sort desc ");
        if(searchProductVO.getProductId()>0){
            ca.andIdEqualTo(searchProductVO.getProductId());
        }
        if(searchProductVO.getState()!=null){
            ca.andStatusEqualTo(searchProductVO.getState().intValue());
        }else{
            ca.andStatusEqualTo(ProductStateEnum.ONLINE.getState());
        }
        if(searchProductVO.isOver()){
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


}
