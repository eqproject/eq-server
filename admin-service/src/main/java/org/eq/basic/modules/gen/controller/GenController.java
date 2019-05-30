/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.gen.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eq.basic.common.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 代码生成 主Controller
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Controller
@RequestMapping(value = "/gen/")
public class GenController extends BaseController {

    /**
     * List页面
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"list", "" })
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {

        // 下拉选查询 自定义内容需要手动添加
        return "modules/gen/genHome";
    }

    /**
     * List页面 代码配置页面
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "codeConfig")
    public String codeConfig(HttpServletRequest request, HttpServletResponse response, Model model) {

        // 下拉选查询 自定义内容需要手动添加
        return "modules/gen/codeConfig/codeConfigList";
    }

}