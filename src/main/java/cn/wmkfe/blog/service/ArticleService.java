package cn.wmkfe.blog.service;

import cn.wmkfe.blog.model.Article;
import cn.wmkfe.blog.vo.ArticleGet;

import java.util.List;

public interface ArticleService {

    int saveArticle(Article article,List<Integer> tagList);

    int removeArticle(String[] ids);

    int updateArticle(Article article,List<Integer> tagList);

    Article getArticle(String id);

    List<Article> getArticleList(Integer currentPage, Integer pageSize, ArticleGet articleGet);

    int countArticle(ArticleGet articleGet);

    int updateArticleViewCount(String id);

    int updateArticlePutTop(String id,boolean putTop);

    Article aboutArticle();

}
