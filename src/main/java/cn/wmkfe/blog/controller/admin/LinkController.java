package cn.wmkfe.blog.controller.admin;

import cn.wmkfe.blog.annotation.Operation;
import cn.wmkfe.blog.model.Article;
import cn.wmkfe.blog.model.Link;
import cn.wmkfe.blog.model.Link;
import cn.wmkfe.blog.service.LinkService;
import cn.wmkfe.blog.util.ConstantValue;
import cn.wmkfe.blog.util.PageSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/admin")
@Controller
public class LinkController {
    @Autowired
    private LinkService linkService;

    @Operation("链接页面")
    @GetMapping("/linkList.html")
    public String linkPage(){
        return "admin/systemSetting/linkList";
    }

    @Operation("添加链接页面")
    @GetMapping("/linkAdd.html")
    public String linkAddPage(){
        return "admin/systemSetting/linksAdd";
    }

    @Operation("更新链接页面")
    @GetMapping("/linkUpdate.html")
    public String linksUpdatePage(){
        return "admin/systemSetting/linksUpdate";
    }

}
