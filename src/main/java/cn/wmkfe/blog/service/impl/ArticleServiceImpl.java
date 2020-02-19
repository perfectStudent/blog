package cn.wmkfe.blog.service.impl;

import cn.wmkfe.blog.NotFoundException;
import cn.wmkfe.blog.annotation.Operation;
import cn.wmkfe.blog.dao.ArticleMapper;
import cn.wmkfe.blog.dao.CommentMapper;
import cn.wmkfe.blog.dao.TagMapMapper;
import cn.wmkfe.blog.model.Article;
import cn.wmkfe.blog.service.ArticleService;
import cn.wmkfe.blog.vo.ArticleGet;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.List;
@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagMapMapper tagMapMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Transactional
    @Override
    public int saveArticle(Article article,List<Integer> tagList) {
        int saveArticle = articleMapper.save(article);
        int saveTagMap = tagMapMapper.save(article.getId(), tagList);
        return saveArticle+saveTagMap;
    }


    @Transactional
    @Override
    public int removeArticle(String[] ids) {
        int remove = commentMapper.removeArticleIds(ids);
        int remove2 = tagMapMapper.removeArticleIds(ids);
        int remove3 = articleMapper.remove(ids);
        return remove+remove2+remove3;
    }

    @Transactional
    @Override
    public int updateArticle(Article article,List<Integer> tagList) {
        List<Integer> getTagList = tagMapMapper.getTagIdList(article.getId());
        int remove=0;
        if(tagMapMapper.getTagIdList(article.getId())!=null&& !tagMapMapper.getTagIdList(article.getId()).isEmpty()){
            remove=tagMapMapper.remove(article.getId(), getTagList);
        }
        int save = tagMapMapper.save(article.getId(), tagList);
        int update = articleMapper.update(article);
        return remove+save+update;
    }

    @Override
    public Article getArticle(String id) {
        Article article = articleMapper.getOne(id);
        if (article==null){
            throw  new  NotFoundException("未找到相关文章");
        }
        return article;
    }

    @Override
    public List<Article> getArticleList(Integer currentPage, Integer pageSize, ArticleGet articleGet) {
        List<Article> list = articleMapper.getList((currentPage - 1) * pageSize, pageSize, articleGet);
        if (list.isEmpty()){
            throw  new  NotFoundException("未找到相关文章");
        }
        return list;
    }

    @Override
    public int countArticle(ArticleGet articleGet) {
        return articleMapper.count(articleGet);
    }

    @Transactional
    @Override
    public int updateArticleViewCount(String id) {
        return articleMapper.updateViewCount(id);
    }

    @Transactional
    @Override
    public int updateArticlePutTop( String id, boolean putTop) {
        return articleMapper.updatePutTop(id,putTop);
    }

    @Override
    public Article aboutArticle() {
        return articleMapper.about();
    }
}
