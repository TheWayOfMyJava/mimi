package com.company.controller;

import com.company.pojo.Admin;
import com.company.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminAction{
    //在所有界面层，一定会有业务逻辑层的对象
    @Autowired
    AdminService adminService;
    //实现登录判断，并进行相应的跳转
    @RequestMapping("/login")
    public String login(String name, String pwd, HttpServletRequest request){
        Admin admin = adminService.login(name,pwd);
        if (admin != null){
            //表明登录成功
            request.setAttribute("admin",admin);
            return "main";
        }
        request.setAttribute("errmsg","用户名或密码错误");
        return "login";
    }
}
