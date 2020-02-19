package cn.wmkfe.blog.service;

import cn.wmkfe.blog.model.Comment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentService {
    int saveComment(Comment comment);

    int removeComment(int[] ids);

    int updateComment(Comment comment);

    Comment getComment(int id);

    List<Comment> getCommentList(Integer currentPage, Integer pageSize, Comment comment);

    int countComment(Comment comment);

    List<Comment> getByArticleIdList(Integer currentPage,Integer pageSize, String articleId);
}
