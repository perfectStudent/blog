package cn.wmkfe.blog.service;

import cn.wmkfe.blog.model.User;

public interface UserService {
    User checkUser(String username, String password);
}
