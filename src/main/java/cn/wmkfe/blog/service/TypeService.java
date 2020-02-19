package cn.wmkfe.blog.service;

import cn.wmkfe.blog.model.Type;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TypeService {
    int saveType(Type type);

    int removeType(int[] ids);

    int updateType(Type type);

    Type getType(int id);

    List<Type> getTypeList(Integer currentPage,Integer pageSize,Type type);

    int countType(Type type);

    Type getTypeByName(String typeName);

    List<Type> getAllType();
}
