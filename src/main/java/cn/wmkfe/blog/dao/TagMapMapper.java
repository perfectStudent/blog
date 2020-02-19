package cn.wmkfe.blog.dao;

import cn.wmkfe.blog.vo.ArticleGet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapMapper {

    int save(@Param("articleId") String  articleId,@Param("tagIdList") List<Integer> tagIdList);

    int remove(@Param("articleId") String  articleId,@Param("tagIdList") List<Integer> tagIdList);

    List<Integer> getTagIdList(String  articleId);

    int removeArticleIds(String[] articleIdList );

}