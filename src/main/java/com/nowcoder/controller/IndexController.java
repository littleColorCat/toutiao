package com.nowcoder.controller;

import com.nowcoder.Service.ToutiaoService;
import com.nowcoder.aspect.LogAspect;
import com.nowcoder.model.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by admin on 2018/3/13.
 */
@Controller
public class IndexController {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(IndexController.class);
    //通过注解或配置文件的方式，实现对象的直接注入
    //自动将对应类的对象赋值到变量上
    @Autowired
    private ToutiaoService toutiaoService;
    //首页
    @RequestMapping(path = {"/", "/index"})
    @ResponseBody
    public String index(HttpSession session){
        logger.info("Index page");
        return "Hello NowCoder" + session.getAttribute("msg") + toutiaoService.say();
    }

    @RequestMapping(value = "/proporties/{groupId}/{userId}")
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

    @RequestMapping(value = {"/request"})
    @ResponseBody
    public String request(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session){
        StringBuilder sb = new StringBuilder();
        //获取request的头部信息
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            sb.append(name + ":" + request.getHeader(name) + "<br>");
        }
        //获取cookie信息
        for(Cookie cookie : request.getCookies()){
            sb.append("Cookie:");
            sb.append(cookie.getName());
            sb.append(":");
            sb.append(cookie.getValue());
            sb.append("<br>");
        }
        //请求的相关信息
        sb.append("getMethod: " + request.getMethod() + "<br>");
        sb.append("getPathInfo: " + request.getPathInfo() + "<br>");
        sb.append("getQueryString: " + request.getQueryString() + "<br>");
        sb.append("getRequestURI: " + request.getRequestURI() + "<br>");

        return sb.toString();
    }

    @RequestMapping(value = {"/responce"})
    @ResponseBody
    public String responce(@CookieValue(value = "nowcoder", defaultValue = "a") String nowcoder,
                            @RequestParam(value = "key", defaultValue = "key") String key,
                           @RequestParam(value = "value", defaultValue = "value") String value,
                           HttpServletResponse response){
        response.addCookie(new Cookie(key, value));
        response.addHeader(key, value);
        return "NowCoderId From Cookie: " + nowcoder;
    }
    /*重定向:方法一
    @RequestMapping(value = {"/redirect/{}code"})
    public RedirectView redirect(@PathVariable("code") int code){
        RedirectView red = new RedirectView("/",true);
        if(code == 301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }*/
    //重定向:方法二
    @RequestMapping(value = {"/redirect/{code}"})
    public String redirect(@PathVariable("code") int code,
                           HttpSession session){
        //跳转时，设置了session属性
        session.setAttribute("msg", "This is jumped from redirect");
        return "redirect:/";
    }

    @RequestMapping(value = {"/admin"})
    @ResponseBody
    public String admin(@RequestParam(value = "key", required = false) String key){
        if ("admin".equals(key)) {
            return "Hello admin";
        }
        throw new IllegalArgumentException("key 错误");
    }

    //Spring MVC外的Exception或Spring MVC没有处理的Exception--可以通过自定义的函数处理
    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e){
        return "ERROR" + e.getMessage();
    }


}
