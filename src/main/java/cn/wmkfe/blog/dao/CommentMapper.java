package cn.wmkfe.blog.dao;

import cn.wmkfe.blog.model.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    int save(Comment comment);

    int remove(int[] ids);

    int update(Comment comment);

    Comment getOne(int id);

    List<Comment> getList(@Param("from")Integer from, @Param("pageSize") Integer pageSize, @Param("comment") Comment comment);

    int count(Comment comment);

    List<Comment> getByArticleId(@Param("from")Integer from, @Param("pageSize") Integer pageSize,@Param("articleId") String articleId);

    int removeArticleIds(String[] articleIdList);
}