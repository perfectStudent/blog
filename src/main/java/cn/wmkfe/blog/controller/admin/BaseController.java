package cn.wmkfe.blog.controller.admin;

import cn.wmkfe.blog.annotation.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class BaseController {
    @Operation("后台首页")
    @GetMapping(value = "/index.html")
    public String indexPage(){
        return "admin/index";
    }
    @Operation("后台首页")
    @GetMapping(value = "/main.html")
    public String mainPage(){
        return "admin/main";
    }
}
