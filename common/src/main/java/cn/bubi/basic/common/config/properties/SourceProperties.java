package cn.bubi.basic.common.config.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 配置类文件读取
 * Created by JoinHan on 2017/4/14.
 */
@Component
// @ConfigurationProperties(locations = "classpath:config/my-web.properties", prefix = "web")
@ConfigurationProperties(prefix = "app")
public class SourceProperties {

    @Autowired
    private Environment env;

    // 文件上传路径
    private String uploadPath;

    // 文件上传默认文件夹
    private String uploadPre;

    // 站点
    private String webSite;

    public Environment getEnv() {

        return this.env;
    }

    public void setEnv(Environment env) {

        this.env = env;
    }

    public String getUploadPath() {

        return this.uploadPath;
    }

    public void setUploadPath(String uploadPath) {

        this.uploadPath = uploadPath;
    }

    public String getUploadPre() {

        return this.uploadPre;
    }

    public void setUploadPre(String uploadPre) {

        this.uploadPre = uploadPre;
    }

    public String getWebSite() {

        return this.webSite;
    }

    public void setWebSite(String webSite) {

        this.webSite = webSite;
    }
}