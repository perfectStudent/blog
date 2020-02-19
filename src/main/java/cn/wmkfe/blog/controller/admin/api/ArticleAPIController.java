package cn.wmkfe.blog.controller.admin.api;

import cn.wmkfe.blog.annotation.Operation;
import cn.wmkfe.blog.model.Article;
import cn.wmkfe.blog.model.User;
import cn.wmkfe.blog.service.ArticleService;
import cn.wmkfe.blog.util.ConstantValue;
import cn.wmkfe.blog.util.PageSupport;
import cn.wmkfe.blog.vo.ArticleGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin")
public class ArticleAPIController {
    @Autowired
    private ArticleService articleService;


    //文章列表
    @Operation("获取文章列表")
    @GetMapping("/articles")
    public Map<String,Object> articles(@RequestParam(value = "page",required = false)String page, @RequestParam(value = "limit",required = false)String limit, ArticleGet articleGet){
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
        int count = articleService.countArticle(articleGet);

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
        List<Article> articleList = articleService.getArticleList(currentPage, pageSize, articleGet);
        map.put("code",200);
        map.put("count",count);
        map.put("msg", ConstantValue.SUCCESS);
        map.put("data",articleList);
        return map;
    }


    //添加文章
    @Operation("添加文章")
    @PostMapping("/articles")
    public Map<String,Object> addArticles(@Valid  Article article, @RequestParam("tagArr[]")List<Integer> tagArr, HttpSession session){
        Map<String,Object> map=new HashMap<>();
        User user = (User) session.getAttribute("user");
        article.setId(Long.toString(new Date().getTime()));
        article.setCreateTime(new Date());
        article.setUpdateTime(new Date());
        article.setViewCount(0);
        article.setUserId(user.getId());
        articleService.saveArticle(article,tagArr);
        map.put("code",200);
        map.put("msg",ConstantValue.SUCCESS);
        return map;
    }

    //删除文章
    @Operation("删除文章")
    @DeleteMapping("/articles/{ids}")
    public Map<String,Object> deleteArticles(@PathVariable(value = "ids")String[] ids){
        Map<String,Object> map=new HashMap<>();
        articleService.removeArticle(ids);
        map.put("code",200);
        map.put("msg",ConstantValue.SUCCESS);
        map.put("data",null);
        return map;
    }


    //根据id查询文章
    @Operation("根据id查询文章")
    @GetMapping("/articles/{id}")
    public Map<String,Object> findById(@PathVariable(value = "id")String id){
        Map<String,Object> map=new HashMap<>();
        Article article=null;
        article = articleService.getArticle(id);
        map.put("code",202);
        map.put("msg","未找到相关数据");
        if (article!=null){
            map.put("code",200);
            map.put("msg",ConstantValue.SUCCESS);
            map.put("data",article);
        }
        return map;
    }

    //更新文章
    @Operation("更新文章")
    @PutMapping("/articles")
    public Map<String,Object> updateArticles(@Valid Article article,@RequestParam("tagArr[]")List<Integer> tagArr){
        Map<String,Object> map=new HashMap<>();
        articleService.updateArticle(article,tagArr);
        map.put("code",200);
        map.put("msg",ConstantValue.SUCCESS);
        return map;
    }

    //更新文章
    @Operation("更新文章")
    @PutMapping("/articles/putTop")
    public Map<String,Object> updatePutTop(String id,boolean putTop){
        Map<String,Object> map=new HashMap<>();
        articleService.updateArticlePutTop(id,putTop);
        map.put("code",200);
        map.put("msg",ConstantValue.SUCCESS);
        return map;
    }

}
