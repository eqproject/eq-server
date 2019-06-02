package org.eq.modules.support.controller;

import org.eq.basic.common.base.BaseController;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.factory.ResponseFactory;
import org.eq.modules.enums.DocTypeEnum;
import org.eq.modules.support.entity.Support;
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
        Support support = new Support();
        support.setType(DocTypeEnum.TERMS.getState());
        Support data = supportService.selectByRecord(support);

        if (data != null) {
            return ResponseFactory.success(data);
        } else {
            return ResponseFactory.error("获取失败", "1");
        }
    }

    @RequestMapping(value = "/legal")
    public ResponseData getLegal() {
        Support support = new Support();
        support.setType(DocTypeEnum.LEGAL.getState());
        Support data = supportService.selectByRecord(support);

        if (data != null) {
            return ResponseFactory.success(data);
        } else {
            return ResponseFactory.error("获取失败", "1");
        }
    }

    @RequestMapping(value = "/buydoc")
    public ResponseData getBuydoc() {
        Support support = new Support();
        support.setType(DocTypeEnum.BUY_DOC.getState());
        Support data = supportService.selectByRecord(support);

        if (data != null) {
            return ResponseFactory.success(data);
        } else {
            return ResponseFactory.error("获取失败", "1");
        }
    }

}