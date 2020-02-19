package cn.wmkfe.blog.controller.admin;

import cn.wmkfe.blog.annotation.Operation;
import cn.wmkfe.blog.model.Comment;
import cn.wmkfe.blog.model.Type;
import cn.wmkfe.blog.service.CommentService;
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
@RequestMapping("/admin")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Operation("评论页面")
    @GetMapping("/comment.html")
    public String commentPage(){
        return "admin/comment/commentList";
    }

    @Operation("更新评论页面")
    @GetMapping("/commentUpdate.html")
    public String commentAddPage(){
        return "admin/comment/commentUpdate";
    }

}
