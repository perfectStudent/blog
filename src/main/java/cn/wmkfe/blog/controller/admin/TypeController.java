package cn.wmkfe.blog.controller.admin;

import cn.wmkfe.blog.annotation.Operation;
import cn.wmkfe.blog.model.Type;
import cn.wmkfe.blog.service.TypeService;
import cn.wmkfe.blog.util.ConstantValue;
import cn.wmkfe.blog.util.PageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping(value = "/admin")
public class TypeController {

    //后台
    @Autowired
    private TypeService typeService;

    @Operation("分类页面")
    @GetMapping("/type.html")
    public String typePage(){
        return "admin/type/typeList";
    }

}
