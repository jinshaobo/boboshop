package com.jinshaobo.controller;

import com.jinshaobo.pojo.Admin;
import com.jinshaobo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminAction {

    //controller对象会调用service对象
    @Autowired
    AdminService adminService;

    //接收用户参数，判断是否登录成功
    @RequestMapping("/login")
    public String login(String name, String pwd, HttpServletRequest request){
        Admin admin = adminService.login(name,pwd);
        //如果admin不为空。则登录成功
        if (admin != null) {
            //登录成功
            request.setAttribute("admin",admin);
            return "main";
        }else {
            request.setAttribute("errmsg","用户名或密码不正确！");
            return "login";
        }
    }
}
