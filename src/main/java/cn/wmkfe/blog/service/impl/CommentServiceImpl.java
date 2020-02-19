package cn.wmkfe.blog.service.impl;

import cn.wmkfe.blog.dao.CommentMapper;
import cn.wmkfe.blog.model.Comment;
import cn.wmkfe.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Transactional
    @Override
    public int saveComment(Comment comment) {
        return commentMapper.save(comment);
    }

    @Transactional
    @Override
    public int removeComment(int[] ids) {
        return commentMapper.remove(ids);
    }

    @Transactional
    @Override
    public int updateComment(Comment comment) {
        return commentMapper.update(comment);
    }

    @Override
    public Comment getComment(int id) {
        return commentMapper.getOne(id);
    }

    @Override
    public List<Comment> getCommentList(Integer currentPage, Integer pageSize, Comment comment) {
        return commentMapper.getList((currentPage-1)*pageSize,pageSize,comment);
    }

    @Override
    public int countComment(Comment comment) {
        return commentMapper.count(comment);
    }

    @Override
    public List<Comment> getByArticleIdList(Integer currentPage, Integer pageSize, String articleId) {

        return commentMapper.getByArticleId((currentPage-1)*pageSize,pageSize,articleId);
    }
}
