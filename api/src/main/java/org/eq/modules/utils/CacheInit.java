package org.eq.modules.utils;

import org.apache.commons.collections.CollectionUtils;
import org.eq.modules.common.cache.BaseCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class CacheInit {

    @Autowired
    private List<BaseCache> cacheset;


    @PostConstruct
    private void startCache(){
        if(!CollectionUtils.isEmpty(cacheset)){
            for(BaseCache temp: cacheset){
                temp.init();
            }
        }else{
            System.out.println("空的");
        }
    }

}
