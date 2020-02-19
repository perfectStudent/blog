package cn.wmkfe.blog.dao;

import cn.wmkfe.blog.model.Type;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TypeMapper {

   int save(Type type);

   int remove(int[] ids);

   int update(Type type);

   Type getOne(int id);

   List<Type> getList(@Param("from")Integer from,@Param("pageSize") Integer pageSize,@Param("type") Type type);

   int count(Type type);

   Type getByName(String typeName);

   List<Type> getAll();
}