package cn.wmkfe.blog.controller.admin.api;

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

@RestController
@RequestMapping(value = "/perfectStudent")
public class LoginAPIController {

    @Autowired
    private UserService userService;

    @Operation("账号登录验证")
    @PostMapping("/checkLogin")
    public Map<String,Object> login(@RequestParam String username
                        , @RequestParam String password
                        , HttpSession session){
        Map<String, Object> map=new HashMap<>();
        User user = userService.checkUser(username, MD5Utils.MD5Lower(username, password));
        if(user!=null){
            user.setPassword(null);
            map.put("code",200);
            session.setAttribute("user",user);
            map.put("msg","success");
            map.put("data",null);
        }else {
            map.put("code",202);
            map.put("msg","用户名或密码错误");
            map.put("data",null);
        }
        return map;
    }
}
