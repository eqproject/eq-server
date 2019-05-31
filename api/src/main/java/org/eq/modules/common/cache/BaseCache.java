package org.eq.modules.common.cache;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eq.modules.enums.ProductStateEnum;
import org.eq.modules.product.entity.ProductAll;
import org.eq.modules.product.service.ProductService;
import org.eq.modules.product.vo.SearchProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 商品缓存
 */

public interface BaseCache {

    void init();
}
