package com.nowcoder.controller;

import com.nowcoder.model.User;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/3/13.
 */
@Controller
public class IndexController {
    @RequestMapping(path = {"/", "/index"})
    @ResponseBody
    public String index(){
        return "Hello NowCoder";
    }

    @RequestMapping(value = {"/proporties/{groupId}/{userId}"})
    @ResponseBody
    public String proporties(@PathVariable("groupId") String groupId,
                             @PathVariable("userId") int userId,
                             @RequestParam(value = "type", defaultValue = "1") int type,
                             @RequestParam(value = "key", defaultValue = "nowcoder") String key){

        return String.format("GID{%s}, UID{%d}, Type{%d}, Key{%s}", groupId, userId, type, key);
    }

    //指定输出的是news文件---templates文件夹下
    @RequestMapping(value = {"/vm"})
    public String news(Model model){//model用于数据存储
        model.addAttribute("value","vvl");
        List<String> colors = Arrays.asList(new String[]{"RED","GREEN","BLUE"});
        Map<String, String> map = new HashMap<>();
        for(int i = 0; i < 4; i ++){
            map.put(String.valueOf(i), String.valueOf(i * i));
        }
        model.addAttribute("colors",colors);//参数：标签，变量名或变量
        model.addAttribute("map",map);

        model.addAttribute("user", new User("lily"));//传入的是自定义的类对象
        return "news";
    }
}
