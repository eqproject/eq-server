package org.eq.modules.account.controller;

import org.eq.basic.modules.sys.entity.SysDict;
import org.eq.basic.modules.sys.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user/account/")
public class UserAccountController {
    private final static String type="userAccountSwitch";

    @Autowired
    private SysDictService sysDictService;

    @RequestMapping(value = "switch")
    public String switchs(HttpServletRequest request, HttpServletResponse response, Model model) {

        SysDict dict = new SysDict();

        //下拉选查询 自定义内容需要手动添加
        return "modules/c2c/auth/list";
    }
}
