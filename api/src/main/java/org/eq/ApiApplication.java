package org.eq;

import org.apache.commons.collections.CollectionUtils;
import org.eq.modules.common.cache.BaseCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

/**
 * @author : gb 2019/5/20
 */

@SpringBootApplication
public class ApiApplication {

    public static void main(String[] args) {

        SpringApplication.run(ApiApplication.class, args);
    }





}
