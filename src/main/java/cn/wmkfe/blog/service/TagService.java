package cn.wmkfe.blog.service;

import cn.wmkfe.blog.model.Tag;

import java.util.List;

public interface TagService {
    int saveTag(Tag tag);

    int removeTag(int[] ids);

    int updateTag(Tag tag);

    Tag getTag(int id);

    List<Tag> getTagList(Integer currentPage, Integer pageSize, Tag tag);

    int countTag(Tag Tag);

    Tag getTagByName(String tagName);

    List<Tag> getAllTag();
}
