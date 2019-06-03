package org.eq.modules.support.controller;

import org.eq.basic.common.base.BaseController;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.support.service.SupportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文案相关Controller
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

}