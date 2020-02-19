package cn.wmkfe.blog.service;

import cn.wmkfe.blog.model.Link;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LinkService {
    int saveLink(Link link);

    int removeLink(int[] ids);

    int updateLink(Link link);

    Link getOneLink(int id);

    List<Link> getListLink(Integer currentPage,Integer pageSize, Link link);

    int countLink(Link link);

    Link getByNameLink(String linkName);

    List<Link> getAllLink();
}
