package cn.bubi.c2c.account.controller;

import cn.bubi.basic.modules.sys.entity.SysDict;
import cn.bubi.basic.modules.sys.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 配置开关验证绑定
 * @author : gb 2019/5/10
 */
@Controller
@RequestMapping(value = "/user/account/config")
public class ConfigSwitchContorller {
    private final static String TYPE = "user_account_config_switch";

    @Autowired
    private SysDictService sysDictService;

    /**
     * List页面
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = {"list", ""})
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("open",isOpen());
        return "modules/c2c/account/config";
    }


    @RequestMapping(value = "update")
    public String update(boolean open, Model model) {
        SysDict config = getConfig();
        config.setValue(open?"true":"false");
        config.setUpdateBy(1L);
        config.setUpdateDate(new Date());
        sysDictService.updateByPrimaryKey(config);
        return "modules/c2c/account/config";
    }

    private boolean isOpen(){
        return getConfig().getValue().equals("true");
    }

    private SysDict getConfig(){
        SysDict temp = new SysDict();
        temp.setType(TYPE);
        List<SysDict> list =  sysDictService.findListByRecord(temp);
        if(list !=null && !list.isEmpty()){
            return list.get(0);
        }

        temp.setLabel("open");
        temp.setDescription("配置开关验证绑定");
        temp.setValue("false");
        temp.setCreateBy(1L);
        temp.setCreateDate(new Date());
        sysDictService.insertRecord(temp);
        return temp;
    }
}
