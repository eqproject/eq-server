package org.eq.basic.common.startCommand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import org.eq.basic.common.config.sysUtil.SysCacheUtil;

/**
 * @Author: JoinHan
 * @Date: Created in 12:03 2018/3/6
 * @Modified By：
 */
@Component
public class StartupRunner implements CommandLineRunner {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run(String... strings) throws Exception {

        this.logger.info(">>>>>>>>>>>>>>>服务启动执行，执行加载 数据缓存<<<<<<<<<<<<<");
        SysCacheUtil.getInstance().initCache();
    }
}
