package cn.wmkfe.blog.controller.admin;

import cn.wmkfe.blog.annotation.Operation;
import cn.wmkfe.blog.model.Tag;
import cn.wmkfe.blog.service.TagService;
import cn.wmkfe.blog.util.ConstantValue;
import cn.wmkfe.blog.util.PageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin")
public class TagController {

    //后台
    @Autowired
    private TagService tagService;

    @Operation("标签页面")
    @GetMapping("/tag.html")
    public String tagPage(){
        return "admin/tag/tagList";
    }


}
