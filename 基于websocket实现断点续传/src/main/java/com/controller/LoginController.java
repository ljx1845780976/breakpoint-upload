package com.controller;/**
 *
 **/

import com.utils.GetCurrentUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @introduction
 * @author ljz
 * @date 2022年06月19日 14:24
 */
@Controller
public class LoginController {

    @RequestMapping("/userLogin")
    public String userLogin() {
        return "login";
    }

    @RequestMapping("/")
    public String login() {
        return "login";
    }
    @RequestMapping("/main")
    public String main(HttpSession httpSession) {
        httpSession.setAttribute("username", GetCurrentUserUtil.getCurrentUserName());
        return "main";
    }
}
