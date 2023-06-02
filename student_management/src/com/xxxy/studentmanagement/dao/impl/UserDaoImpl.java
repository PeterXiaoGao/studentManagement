package com.xxxy.studentmanagement.dao.impl;

import com.xxxy.studentmanagement.bean.User;
import com.xxxy.studentmanagement.dao.UserDao;
import com.xxxy.studentmanagement.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImpl implements UserDao {

    /**
     * 数据库插入的方法封装
     * @param user
     * @return 1代表插入成功  0失败
     */
    @Override
    public int insert(User user) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into user (username, password, telephone) values (?, ?, ?);";
            ps = conn.prepareStatement(sql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getTelephone());
            count = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn, ps, null);
        }
        return count;
    }

    /**
     * 通过用户名查询数据库
     * @param username 用户名
     * @return 0没有存在， 1存在
     */
    @Override
    public int selectByUsername(String username) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select username from user where username = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if(rs.next()){
                count = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn, ps, rs);
        }

        return count;
    }

    /**
     * 通过用户名和密码查询数据库
     * @param username 用户名
     * @param password 密码
     * @return 1存在 0不存在
     */
    @Override
    public int selectByUnameAndPwd(String username, String password) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select username,password from user where username = ? and password = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if(rs.next()){
                count = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn, ps, rs);
        }
        return count;
    }


}
