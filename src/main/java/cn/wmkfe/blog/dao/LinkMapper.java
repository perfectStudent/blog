package cn.wmkfe.blog.dao;

import cn.wmkfe.blog.model.Link;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LinkMapper {
    int save(Link link);

    int remove(int[] ids);

    int update(Link link);

    Link getOne(int id);

    List<Link> getList(@Param("from")Integer from, @Param("pageSize") Integer pageSize,@Param("link") Link link);

    int count(Link link);

    Link getByName(String linkName);

    List<Link> getAll();
}
