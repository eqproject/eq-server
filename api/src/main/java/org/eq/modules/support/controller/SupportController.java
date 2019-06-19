package org.eq.modules.support.controller;

import org.eq.basic.common.base.BaseController;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.factory.ResponseFactory;
import org.eq.modules.support.service.SupportService;
import org.eq.modules.support.vo.ConfigVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统支持相关
 * @author hobe
 * @version 2019-06-02
 */
@RestController
@RequestMapping(value = "/api/support")
public class SupportController extends BaseController {

    @Autowired
    private SupportService supportService;

    @RequestMapping(value = "/terms")
    public ResponseData getTerms() {
        return supportService.getTerms();
    }

    @RequestMapping(value = "/legal")
    public ResponseData getLegal() {
        return supportService.getLegal();
    }

    @RequestMapping(value = "/buydoc")
    public ResponseData getBuydoc() {
        return supportService.getBuydoc();
    }

    /**
     *
     * 获取平台配置项
     * @return
     */
    @PostMapping(value = "/getConfig")
    public ResponseData<ConfigVO> getConfig() {
        ConfigVO configVO = supportService.getConfigVo();
        return ResponseFactory.success(configVO);
    }


}