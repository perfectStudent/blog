package cn.wmkfe.blog.controller.admin;

import cn.wmkfe.blog.annotation.Operation;
import cn.wmkfe.blog.model.User;
import cn.wmkfe.blog.service.UserService;
import cn.wmkfe.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/perfectStudent")
public class LoginController {

    @Autowired
    private UserService userService;

    @Operation("登录界面")
    @RequestMapping(value = "/login.html")
    public String loginPage(){
        return "admin/login";
    }


    @Operation("退出登录")
    @GetMapping("/loginOut")
    public String loginOut(HttpSession session){
        session.removeAttribute("user");
        return "admin/login";
    }

}
