package cn.wmkfe.blog.dao;

import cn.wmkfe.blog.model.Article;
import cn.wmkfe.blog.vo.ArticleGet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {
    int save(Article article);

    int remove(String[] ids);

    int update(Article article);

    Article getOne(String id);

    List<Article> getList(@Param("from")Integer from, @Param("pageSize") Integer pageSize,@Param("articleGet") ArticleGet articleGet);

    int count(@Param("articleGet") ArticleGet articleGet);

    int updateViewCount(String id);

    int updatePutTop(@Param("id")String id, @Param("putTop")boolean putTop);

    Article about();
}