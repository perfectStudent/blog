package cn.wmkfe.blog.service.impl;

import cn.wmkfe.blog.dao.LinkMapper;
import cn.wmkfe.blog.model.Link;
import cn.wmkfe.blog.service.LinkService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class LinkServiceImpl implements LinkService {
    @Autowired
    private LinkMapper linkMapper;

    @Transactional
    @Override
    public int saveLink(Link link) {
        return linkMapper.save(link);
    }

    @Transactional
    @Override
    public int removeLink(int[] ids) {
        return linkMapper.remove(ids);
    }

    @Transactional
    @Override
    public int updateLink(Link link) {
        return linkMapper.update(link);
    }

    @Override
    public Link getOneLink(int id) {
        return linkMapper.getOne(id);
    }

    @Override
    public List<Link> getListLink(Integer currentPage, Integer pageSize, Link link) {
        return linkMapper.getList((currentPage-1)*pageSize,pageSize,link);
    }

    @Override
    public int countLink(Link link) {
        return linkMapper.count(link);
    }

    @Override
    public Link getByNameLink(String linkName) {
        return linkMapper.getByName(linkName);
    }

    @Override
    public List<Link> getAllLink() {
        return linkMapper.getAll();
    }
}
