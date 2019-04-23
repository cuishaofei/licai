package com.feifei.licai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author cuishaofei
 * @date 2019/4/23
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private HttpServletRequest request;

    @Value("${user.username}")
    private String username;

    @Value("${user.password}")
    private String password;

    @RequestMapping("/login")
    public String login(HttpSession session) throws Exception {
        String str = "";
        String username= request.getParameter("username");
        String password= request.getParameter("password");
        if(username.equals(this.username) && password.equals(this.password)) {
            session.setAttribute("user",session.getId());
            str = "redirect:/index.html";
        }else {
            str = "redirect:/login.html";
        }
        return str;
    }
}