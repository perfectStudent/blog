package cn.wmkfe.blog.service.impl;

import cn.wmkfe.blog.dao.UserMapper;
import cn.wmkfe.blog.model.User;
import cn.wmkfe.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User checkUser(String username, String password) {
        User user = userMapper.findByusernameAndPassword(username, password);
        return user;
    }
}
