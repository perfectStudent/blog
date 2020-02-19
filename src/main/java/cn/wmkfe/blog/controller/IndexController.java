package cn.wmkfe.blog.controller;

import cn.wmkfe.blog.model.*;
import cn.wmkfe.blog.service.*;
import cn.wmkfe.blog.util.ArchivesDate;
import cn.wmkfe.blog.util.ConstantValue;
import cn.wmkfe.blog.util.PageSupport;
import cn.wmkfe.blog.vo.ArticleGet;
import cn.wmkfe.blog.model.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private LinkService linkService;
    @Autowired
    private StatisticsService statisticsService;
    @RequestMapping(value = {"/index.html","/"})
    public String indexPage(@RequestParam(value = "p",required = false)String p,@RequestParam(value = "pjax",required = false)Boolean pjax,Model model){
        MacroCommonHead mCH=new MacroCommonHead();
        mCH.setTitle("测试标题");
        mCH.setDescription("测试描述");
        mCH.setUrl("http://localhost:8080/");
        mCH.setPreconnect("http://localhost:8080/");
        Statistics statistics=new Statistics();
        //文章数量
        statistics.setArticleCount(articleService.countArticle(null));
        //访问数量
        statistics.setViewCount(statisticsService.getCount());
        //更新浏览次数
        statisticsService.updateCount();
        Integer currentPage=1;
        if(p !=null && !"".equals(p)){
            currentPage=Integer.valueOf(p);
        }
        //获取总条数
        int count = articleService.countArticle(null);

        PageSupport pageSupport=new PageSupport();
        pageSupport.setPageSize(ConstantValue.ARTICLEPAGESIZE);
        pageSupport.setCurrentPageNo(currentPage);
        pageSupport.setTotal(count);

        if(currentPage<0){
            currentPage=1;
        }else if (currentPage>pageSupport.getPageCount()){
            currentPage=pageSupport.getPageCount();
        }
        pageSupport.setCurrentPageNo(currentPage);
        List<Article> articleList = articleService.getArticleList(currentPage, ConstantValue.ARTICLEPAGESIZE, null);

        //标签
        List<Tag> tagList = tagService.getTagList(1, ConstantValue.TAGSPAGESIZE, null);
        //分类
        List<Type> typeList = typeService.getTypeList(1, ConstantValue.TYPESIZE, null);
        //存档
        List<Archives> dateList = ArchivesDate.dateList();
        model.addAttribute("articleList",articleList);
        model.addAttribute("pageSupport",pageSupport);
        model.addAttribute("tagList",tagList);
        model.addAttribute("typeList",typeList);
        model.addAttribute("dateList",dateList);
        model.addAttribute("mCH",mCH);
        model.addAttribute("statistics",statistics);
        return pjax==null?  "index":"common-template/pjax-page";
    }


    //文章详情
    @RequestMapping(value = {"/articles/{id}.html"})
    public String articlesPage(@PathVariable String id, Model model){
        MacroCommonHead mCH=new MacroCommonHead();
        mCH.setTitle("测试标题");
        mCH.setDescription("测试描述");
        mCH.setUrl("http://localhost:8080/");
        mCH.setPreconnect("http://localhost:8080/");


        Statistics statistics=new Statistics();

        //文章数量
        statistics.setArticleCount(articleService.countArticle(null));
        //访问数量
        statistics.setViewCount(statisticsService.getCount());
        //更新浏览次数
        statisticsService.updateCount();
        Article article = articleService.getArticle(id);
        //标签
        List<Tag> tagList = tagService.getTagList(1, ConstantValue.TAGSPAGESIZE, null);
        //分类
        List<Type> typeList = typeService.getTypeList(1, ConstantValue.TYPESIZE, null);
        //存档
        List<Archives> dateList = ArchivesDate.dateList();
        //更新浏览次数
        articleService.updateArticleViewCount(id);

        model.addAttribute("article",article);
        model.addAttribute("tagList",tagList);
        model.addAttribute("typeList",typeList);
        model.addAttribute("dateList",dateList);
        model.addAttribute("mCH",mCH);
        model.addAttribute("statistics",statistics);
        return "articles";
    }
    //友情链接
    @RequestMapping(value = {"/links.html"})
    public String page1(Model model){
        MacroCommonHead mCH=new MacroCommonHead();
        mCH.setTitle("测试标题");
        mCH.setDescription("测试描述");
        mCH.setUrl("http://localhost:8080/");
        mCH.setPreconnect("http://localhost:8080/");


        Statistics statistics=new Statistics();

        //文章数量
        statistics.setArticleCount(articleService.countArticle(null));
        //访问数量
        statistics.setViewCount(statisticsService.getCount());
        //更新浏览次数
        statisticsService.updateCount();
        //友情链接
        List<Link> allLink = linkService.getAllLink();
        //标签
        List<Tag> tagList = tagService.getTagList(1, ConstantValue.TAGSPAGESIZE, null);
        //分类
        List<Type> typeList = typeService.getTypeList(1, ConstantValue.TYPESIZE, null);
        //存档
        List<Archives> dateList = ArchivesDate.dateList();
        model.addAttribute("mCH",mCH);
        model.addAttribute("allLink",allLink);
        model.addAttribute("tagList",tagList);
        model.addAttribute("typeList",typeList);
        model.addAttribute("dateList",dateList);
        model.addAttribute("statistics",statistics);
        return "links";
    }

    //归档
    @RequestMapping(value = {"/archives/{year}/{month}"})
    public String archivesPage(@PathVariable(value = "year")String year,@PathVariable(value = "month")String month,@RequestParam(value = "p",required = false)String p,@RequestParam(value = "pjax",required = false)Boolean pjax,Model model){
        MacroCommonHead mCH=new MacroCommonHead();
        mCH.setTitle("测试标题");
        mCH.setDescription("测试描述");
        mCH.setUrl("http://localhost:8080/");
        mCH.setPreconnect("http://localhost:8080/");
        ArticleGet articleGet=new ArticleGet();
        articleGet.setArchivesDate(year+"-"+month);
        Statistics statistics=new Statistics();
        //文章数量
        statistics.setArticleCount(articleService.countArticle(articleGet));
        //访问数量
        statistics.setViewCount(statisticsService.getCount());
        //更新浏览次数
        statisticsService.updateCount();

        Integer currentPage=1;
        if(p !=null && !"".equals(p)){
            currentPage=Integer.valueOf(p);
        }
        //获取总条数
        int count = articleService.countArticle(articleGet);

        PageSupport pageSupport=new PageSupport();
        pageSupport.setPageSize(ConstantValue.ARTICLEPAGESIZE);
        pageSupport.setCurrentPageNo(currentPage);
        pageSupport.setTotal(count);

        if(currentPage<0){
            currentPage=1;
        }else if (currentPage>pageSupport.getPageCount()){
            currentPage=pageSupport.getPageCount();
        }
        pageSupport.setCurrentPageNo(currentPage);
        //文章
        List<Article> articleList = articleService.getArticleList(currentPage, ConstantValue.ARTICLEPAGESIZE, articleGet);
        //标签
        List<Tag> tagList = tagService.getTagList(1, ConstantValue.TAGSPAGESIZE, null);
        //分类
        List<Type> typeList = typeService.getTypeList(1, ConstantValue.TYPESIZE, null);
        //存档
        List<Archives> dateList = ArchivesDate.dateList();
        model.addAttribute("mCH",mCH);
        model.addAttribute("articleList",articleList);
        model.addAttribute("tagList",tagList);
        model.addAttribute("typeList",typeList);
        model.addAttribute("dateList",dateList);
        model.addAttribute("pageSupport",pageSupport);
        model.addAttribute("statistics",statistics);
        return "archives";
    }

    //标签
    @RequestMapping(value = {"/tags.html"})
    public String tagsPage(Model model){
        MacroCommonHead mCH=new MacroCommonHead();
        mCH.setTitle("测试标题");
        mCH.setDescription("测试描述");
        mCH.setUrl("http://localhost:8080/");
        mCH.setPreconnect("http://localhost:8080/");

        Statistics statistics=new Statistics();

        //文章数量
        statistics.setArticleCount(articleService.countArticle(null));
        //访问数量
        statistics.setViewCount(statisticsService.getCount());
        //更新浏览次数
        statisticsService.updateCount();
        //标签
        List<Tag> tagList = tagService.getTagList(1, ConstantValue.TAGSPAGESIZE, null);
        //分类
        List<Type> typeList = typeService.getTypeList(1, ConstantValue.TYPESIZE, null);
        //存档
        List<Archives> dateList = ArchivesDate.dateList();
        model.addAttribute("mCH",mCH);
        model.addAttribute("tagList",tagList);
        model.addAttribute("typeList",typeList);
        model.addAttribute("dateList",dateList);
        model.addAttribute("statistics",statistics);
        return "tags";
    }
    //关于我
    @RequestMapping(value = {"/about.html"})
    public String aboutPage(Model model){
        MacroCommonHead mCH=new MacroCommonHead();
        mCH.setTitle("测试标题");
        mCH.setDescription("测试描述");
        mCH.setUrl("http://localhost:8080/");
        mCH.setPreconnect("http://localhost:8080/");

        Statistics statistics=new Statistics();

        //文章数量
        statistics.setArticleCount(articleService.countArticle(null));
        //访问数量
        statistics.setViewCount(statisticsService.getCount());
        //更新浏览次数
        statisticsService.updateCount();
        //关于我的文章
        Article article = articleService.aboutArticle();
        //标签
        List<Tag> tagList = tagService.getTagList(1, ConstantValue.TAGSPAGESIZE, null);
        //分类
        List<Type> typeList = typeService.getTypeList(1, ConstantValue.TYPESIZE, null);
        //存档
        List<Archives> dateList = ArchivesDate.dateList();
        model.addAttribute("mCH",mCH);
        model.addAttribute("article",article);
        model.addAttribute("tagList",tagList);
        model.addAttribute("typeList",typeList);
        model.addAttribute("dateList",dateList);
        model.addAttribute("statistics",statistics);
        return "about";
    }

    //按标签搜索
    @GetMapping(value = {"/tags/{tagId}"})
    public String tags(@PathVariable String tagId,@RequestParam(value = "p",required = false)String p,@RequestParam(value = "pjax",required = false)Boolean pjax,Model model){
        MacroCommonHead mCH=new MacroCommonHead();
        mCH.setTitle("测试标题");
        mCH.setDescription("测试描述");
        mCH.setUrl("http://localhost:8080/");
        mCH.setPreconnect("http://localhost:8080/");

        Statistics statistics=new Statistics();
        //文章数量
        statistics.setArticleCount(articleService.countArticle(null));
        //访问数量
        statistics.setViewCount(statisticsService.getCount());

        //更新浏览次数
        statisticsService.updateCount();
        ArticleGet articleGet=new ArticleGet();
        articleGet.setTagId(Integer.valueOf(tagId));
        Integer currentPage=1;
        if(p !=null && !"".equals(p)){
            currentPage=Integer.valueOf(p);
        }
        //获取总条数
        int count = articleService.countArticle(articleGet);

        PageSupport pageSupport=new PageSupport();
        pageSupport.setPageSize(ConstantValue.ARTICLEPAGESIZE);
        pageSupport.setCurrentPageNo(currentPage);
        pageSupport.setTotal(count);

        if(currentPage<0){
            currentPage=1;
        }else if (currentPage>pageSupport.getPageCount()){
            currentPage=pageSupport.getPageCount();
        }
        pageSupport.setCurrentPageNo(currentPage);
        //查询文章
        List<Article> articleList = articleService.getArticleList(currentPage, ConstantValue.ARTICLEPAGESIZE, articleGet);
        //标签
        List<Tag> tagList = tagService.getTagList(1, ConstantValue.TAGSPAGESIZE, null);
        //分类
        List<Type> typeList = typeService.getTypeList(1, ConstantValue.TYPESIZE, null);
        //存档
        List<Archives> dateList = ArchivesDate.dateList();
        model.addAttribute("articleList",articleList);
        model.addAttribute("pageSupport",pageSupport);
        model.addAttribute("tagList",tagList);
        model.addAttribute("typeList",typeList);
        model.addAttribute("dateList",dateList);
        model.addAttribute("mCH",mCH);
        model.addAttribute("statistics",statistics);
        return pjax==null? "index": "common-template/pjax-page";
    }
    //按分类搜索
    @GetMapping(value = {"/types/{typeId}"})
    public String types(@PathVariable String typeId,@RequestParam(value = "p",required = false)String p,@RequestParam(value = "pjax",required = false)Boolean pjax,Model model){
        MacroCommonHead mCH=new MacroCommonHead();
        mCH.setTitle("博客分类搜索");
        mCH.setDescription("博客分类搜索");
        mCH.setUrl("http://localhost:8080/");
        mCH.setPreconnect("http://localhost:8080/");

        Statistics statistics=new Statistics();

        //文章数量
        statistics.setArticleCount(articleService.countArticle(null));
        //访问数量
        statistics.setViewCount(statisticsService.getCount());
        //更新浏览次数
        statisticsService.updateCount();
        ArticleGet articleGet=new ArticleGet();
        articleGet.setTypeId(Integer.valueOf(typeId));
        Integer currentPage=1;
        if(p !=null && !"".equals(p)){
            currentPage=Integer.valueOf(p);
        }
        //获取总条数
        int count = articleService.countArticle(articleGet);

        PageSupport pageSupport=new PageSupport();
        pageSupport.setPageSize(ConstantValue.ARTICLEPAGESIZE);
        pageSupport.setCurrentPageNo(currentPage);
        pageSupport.setTotal(count);

        if(currentPage<0){
            currentPage=1;
        }else if (currentPage>pageSupport.getPageCount()){
            currentPage=pageSupport.getPageCount();
        }
        pageSupport.setCurrentPageNo(currentPage);
        //查询文章
        List<Article> articleList = articleService.getArticleList(currentPage, ConstantValue.ARTICLEPAGESIZE, articleGet);
        //标签
        List<Tag> tagList = tagService.getTagList(1, ConstantValue.TAGSPAGESIZE, null);
        //分类
        List<Type> typeList = typeService.getTypeList(1, ConstantValue.TYPESIZE, null);
        //存档
        List<Archives> dateList = ArchivesDate.dateList();
        model.addAttribute("articleList",articleList);
        model.addAttribute("pageSupport",pageSupport);
        model.addAttribute("tagList",tagList);
        model.addAttribute("typeList",typeList);
        model.addAttribute("dateList",dateList);
        model.addAttribute("mCH",mCH);
        model.addAttribute("statistics",statistics);
        return pjax==null?  "index":"common-template/pjax-page";
    }
    //文章搜索
    @GetMapping(value = {"/search.html"})
    public String searchPage(@RequestParam(value = "keyword",required = false) String keyword,@RequestParam(value = "p",required = false)String p,Model model){
        MacroCommonHead mCH=new MacroCommonHead();
        mCH.setTitle("博客分类搜索");
        mCH.setDescription("博客分类搜索");
        mCH.setUrl("http://localhost:8080/");
        mCH.setPreconnect("http://localhost:8080/");

        ArticleGet articleGet=new ArticleGet();
        articleGet.setTitle(keyword);

        //更新浏览次数
        statisticsService.updateCount();
        Integer currentPage=1;
        if(p !=null && !"".equals(p)){
            currentPage=Integer.valueOf(p);
        }
        //获取总条数
        int count = articleService.countArticle(articleGet);

        PageSupport pageSupport=new PageSupport();
        pageSupport.setPageSize(ConstantValue.ARTICLEPAGESIZE);
        pageSupport.setCurrentPageNo(currentPage);
        pageSupport.setTotal(count);

        if(currentPage<0){
            currentPage=1;
        }else if (currentPage>pageSupport.getPageCount()){
            currentPage=pageSupport.getPageCount();
        }
        pageSupport.setCurrentPageNo(currentPage);
        //查询文章
        List<Article> articleList = articleService.getArticleList(currentPage, ConstantValue.ARTICLEPAGESIZE, articleGet);
        model.addAttribute("articleList",articleList);
        model.addAttribute("pageSupport",pageSupport);
        model.addAttribute("mCH",mCH);
        return "search";
    }

    //404
    @RequestMapping(value = "/404.html")
    public String  notFountdPage(){
        return "error/404";
    }
}
