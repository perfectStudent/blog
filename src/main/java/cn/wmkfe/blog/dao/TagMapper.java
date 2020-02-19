package cn.wmkfe.blog.dao;

import cn.wmkfe.blog.model.Tag;
import cn.wmkfe.blog.model.Type;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapper {
    int save(Tag tag);

    int remove(int[] ids);

    int update(Tag tag);

    Tag getOne(int id);

    List<Tag> getList(@Param("from")Integer from, @Param("pageSize") Integer pageSize, @Param("tag") Tag tag);

    int count(Tag tag);

    Tag getByName(String tagName);

    List<Tag> getAll();
}