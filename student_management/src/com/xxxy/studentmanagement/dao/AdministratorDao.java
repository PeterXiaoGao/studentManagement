package com.xxxy.studentmanagement.dao;

import com.xxxy.studentmanagement.bean.Administrator;
import com.xxxy.studentmanagement.bean.User;

public interface AdministratorDao {
    // 往数据库插入记录
    int insert(Administrator administrator);
    // 通过 用户名查询数据库
    int selectByUsername(String username);
    // 通过用户名和密码 查询数据库
    int selectByUnameAndPwd (String username, String password);
}
