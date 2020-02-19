package cn.wmkfe.blog.controller.admin.api;

import cn.wmkfe.blog.annotation.Operation;
import cn.wmkfe.blog.model.Comment;
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

@RestController
@RequestMapping(value = "/admin")
public class CommentAPIController {
    @Autowired
    private CommentService commentService;

    //评论列表
    @Operation("获取评论列表")
    @GetMapping("/comments")
    public Map<String,Object> comments(@RequestParam("page")String page, @RequestParam("limit")String limit, Comment comment){
        Map<String,Object> map=new HashMap<>();
        Integer currentPage=1;
        Integer pageSize=10;
        if(page !=null && !"".equals(page)){
            currentPage=Integer.valueOf(page);
        }
        if(limit !=null && !"".equals(limit)){
            pageSize=Integer.valueOf(limit);
        }

        //获取总条数
        int count = commentService.countComment(comment);

        PageSupport pageSupport=new PageSupport();
        pageSupport.setPageSize(pageSize);
        pageSupport.setCurrentPageNo(currentPage);
        pageSupport.setTotal(count);

        if(currentPage<0){
            currentPage=1;
        }else if (currentPage>pageSupport.getPageCount()){
            currentPage=pageSupport.getPageCount();
        }
        pageSupport.setCurrentPageNo(currentPage);
        List<Comment> commentList = commentService.getCommentList(currentPage, pageSize, comment);
        map.put("code",200);
        map.put("count",count);
        map.put("msg", ConstantValue.SUCCESS);
        map.put("data",commentList);
        return map;
    }


    //添加评论
    @Operation("添加评论")
    @PostMapping("/comments")
    public Map<String,Object> addComments(@Valid Comment comment){
        Map<String,Object> map=new HashMap<>();
        commentService.saveComment(comment);
                map.put("code",200);
                map.put("msg",ConstantValue.SUCCESS);
                map.put("data",null);
        return map;
    }

    //删除评论
    @Operation("删除评论")
    @DeleteMapping("/comments/{ids}")
    public Map<String,Object> deleteComments(@PathVariable(value = "ids")String[] ids){
        Map<String,Object> map=new HashMap<>();
        int[] arr =new int [ids.length];
        for (int i=0;i<ids.length;i++) {
            if(ids[i]!=null&&!"".equals(ids[i])){
                arr[i]=Integer.valueOf(ids[i]);
            }
        }
        commentService.removeComment(arr);
            map.put("code",200);
            map.put("msg",ConstantValue.SUCCESS);
            map.put("data",null);
        return map;
    }


    //根据id查询评论
    @Operation("根据id查询评论")
    @GetMapping("/comments/{id}")
    public Map<String,Object> findById(@PathVariable(value = "id")String id){
        Map<String,Object> map=new HashMap<>();
        Comment comment=null;
        if(id!=null&!"".equals(id)){
             comment = commentService.getComment(Integer.valueOf(id));
        }
        map.put("code",202);
        map.put("msg","未找到相关数据");
        if (comment!=null){
            map.put("code",200);
            map.put("msg",ConstantValue.SUCCESS);
            map.put("data",comment);
        }
        return map;
    }


    //更新评论
    @Operation("更新评论")
    @PutMapping("/comments")
    public Map<String,Object> updateComments(@Valid Comment comment){
        Map<String,Object> map=new HashMap<>();
        commentService.updateComment(comment);
            map.put("code",200);
            map.put("msg",ConstantValue.SUCCESS);
        return map;
    }
}
