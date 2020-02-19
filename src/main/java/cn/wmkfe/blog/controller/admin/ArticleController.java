package cn.wmkfe.blog.controller.admin;

import cn.wmkfe.blog.annotation.Operation;
import cn.wmkfe.blog.model.*;
import cn.wmkfe.blog.service.ArticleService;
import cn.wmkfe.blog.service.TagService;
import cn.wmkfe.blog.service.TypeService;
import cn.wmkfe.blog.util.ConstantValue;
import cn.wmkfe.blog.util.PageSupport;
import cn.wmkfe.blog.vo.ArticleGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TypeService typeService;

    @Operation("访问文章页面")
    @GetMapping("/article.html")
    public String articlePage(){
        return "admin/article/articleList";
    }

    @Operation("添加文章页面")
    @GetMapping("/articleAdd.html")
    public String articleAddPage(Model model){
        List<Tag> tagList = tagService.getAllTag();
        List<Type> typeList = typeService.getAllType();
        model.addAttribute("tagList",tagList);
        model.addAttribute("typeList",typeList);
        return "admin/article/articleAdd";
    }

    @Operation("更新文章页面")
    @GetMapping("/articleUpdate.html")
    public String articleUpdatePage(Model model){
        List<Tag> tagList = tagService.getAllTag();
        List<Type> typeList = typeService.getAllType();
        model.addAttribute("tagList",tagList);
        model.addAttribute("typeList",typeList);
        return "admin/article/articleUpdate";
    }
}
