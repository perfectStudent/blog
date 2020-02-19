package cn.wmkfe.blog.service.impl;

import cn.wmkfe.blog.NotFoundException;
import cn.wmkfe.blog.dao.TagMapper;
import cn.wmkfe.blog.model.Tag;
import cn.wmkfe.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Transactional
    @Override
    public int saveTag(Tag tag) {
        return tagMapper.save(tag);
    }

    @Transactional
    @Override
    public int removeTag(int[] ids) {
        return tagMapper.remove(ids);
    }

    @Transactional
    @Override
    public int updateTag(Tag tag) {
        return tagMapper.update(tag);
    }

    @Override
    public Tag getTag(int id) {
        Tag one = tagMapper.getOne(id);
        if (one==null){
            throw new NotFoundException("未找到改信息");
        }
        return one;
    }

    @Override
    public List<Tag> getTagList(Integer currentPage, Integer pageSize, Tag tag) {
        List<Tag> list = tagMapper.getList((currentPage - 1) * pageSize, pageSize, tag);
        if (list==null){
            throw new NotFoundException("未找到改信息");
        }
        return list;
    }

    @Override
    public int countTag(Tag tag) {
        return tagMapper.count(tag);
    }

    @Override
    public Tag getTagByName(String tagName) {
        return tagMapper.getByName(tagName);
    }

    @Override
    public List<Tag> getAllTag() {
        return tagMapper.getAll();
    }
}
