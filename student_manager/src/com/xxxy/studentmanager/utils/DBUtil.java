package com.xxxy.studentmanager.utils;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * JDBC工具类 连接数据库
 *
 */
public class DBUtil {

    private static ResourceBundle bundle = ResourceBundle.getBundle("resource/jdbc");
    private static String driver = bundle.getString("driver");
    private static String url = bundle.getString("url");
    private static String user = bundle.getString("user");
    private static String password = bundle.getString("password");

    // 不让创建对象 所以这里私有
    private DBUtil(){
    }

    // 静态加载 当调用这个类的方法会进行类加载，此时静态代码块执行
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取数据库连接对象
     * @return 连接对象
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    /**
     * 关闭资源
     * @param conn 数据库连接对象
     * @param ps    操作数据库对象
     * @param rs    结果集对象
     */
    public static void close(Connection conn, Statement ps, ResultSet rs){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(ps != null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(rs != null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
