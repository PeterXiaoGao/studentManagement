package com.xxxy.studentmanager.dao;

import com.xxxy.studentmanager.bean.User;

public interface UserDao {
    // 往数据库插入记录
    int insert(User user);
    // 通过 用户名查询数据库
    int selectByUsername(String username);
    // 通过用户名和密码 查询数据库
    int selectByUnameAndPwd (String username, String password);
}
