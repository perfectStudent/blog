package cn.wmkfe.blog.dao;

import cn.wmkfe.blog.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User findByusernameAndPassword(@Param("username") String username,@Param("password") String password);
}