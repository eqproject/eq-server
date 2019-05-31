package org.eq.modules.common.cache;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eq.modules.common.utils.DateUtil;
import org.eq.modules.enums.ProductStateEnum;
import org.eq.modules.product.entity.ProductAll;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.product.vo.SearchProductVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 商品缓存
 */
@Component
public class ProductCache implements BaseCache {

    private Logger loger = LoggerFactory.getLogger(ProductCache.class);


    @Autowired
    private ProductService productService;


    /**
     * 商品缓存Map
     */
    private static Map<String,ProductAll> productMap = new ConcurrentHashMap<>();



    public ProductAll getProduct(String productId){
        if(StringUtils.isEmpty(productId)){
            return null;
        }
        if(productMap.containsKey(productId)){
            return productMap.get(productId);
        }
        reloadProduct(productId);
        return productMap.get(productId);
    }

    /**
     * 重新加载商品库
     * @param productId
     */
    private  void reloadProduct(String productId){
        SearchProductVO searchProductVO = new  SearchProductVO();
        if(!StringUtils.isEmpty(productId)){
            searchProductVO.setProductId(Long.valueOf(productId));
        }
        searchProductVO.setOver(false);
        searchProductVO.setState(ProductStateEnum.ONLINE.getState());
        List<ProductAll> result =  productService.listProductAll(searchProductVO);
        if(CollectionUtils.isEmpty(result)){
            return ;
        }
        for(ProductAll temp:  result){
            productMap.put(String.valueOf(temp.getId()),temp);
        }


    }

    @Override
    public void init() {
        reloadProduct(null);
        Thread thread = new Thread(() -> {
            try{
                System.out.println("开始执行商品加载");
                loger.info("定时加载商品信息 任务开始加载 {}", DateUtil.getNowTimeStr());
                reloadProduct(null);
                loger.info("定时加载商品信息 任务加载完毕 {}", DateUtil.getNowTimeStr());

                Thread.sleep(1000 * 60);

            }catch (Exception e){
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
