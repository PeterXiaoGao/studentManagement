package com.xxxy.studentmanagement.dao.impl;

import com.xxxy.studentmanagement.bean.Student;
import com.xxxy.studentmanagement.dao.StudentDao;
import com.xxxy.studentmanagement.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    /**
     * 查询数据库中所有的Student 并且是以学生id为升序查出
     * @return 存放所有的Student的List集合
     */
    @Override
    public List<Student> selectAll() {
        List<Student> list = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select id,stu_no,stu_name,stu_sex,stu_collage,stu_class from student order by id asc";
            ps = conn.prepareStatement(sql);
            // 查出数据
            rs = ps.executeQuery();
            while (rs.next()) {
                // 取出数据
               Integer id = rs.getInt("id");
               String stu_no = rs.getString("stu_no");
               String stu_name = rs.getString("stu_name");
               String stu_sex = rs.getString("stu_sex");
               String stu_collage = rs.getString("stu_collage");
               String stu_class = rs.getString("stu_class");
               Student stu = new Student(id, stu_no, stu_name, stu_sex, stu_collage, stu_class);
               list.add(stu);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    /**
     * 查询数据库中所有的Student 并且是以学生学号为升序查出
     * @return 存放所有的Student的List集合
     */
    @Override
    public List<Student> selectAllByStuNoAsc() {
        List<Student> list = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select id,stu_no,stu_name,stu_sex,stu_collage,stu_class from student order by stu_no asc";
            ps = conn.prepareStatement(sql);
            // 查出数据
            rs = ps.executeQuery();
            while (rs.next()) {
                // 取出数据
                Integer id = rs.getInt("id");
                String stu_no = rs.getString("stu_no");
                String stu_name = rs.getString("stu_name");
                String stu_sex = rs.getString("stu_sex");
                String stu_collage = rs.getString("stu_collage");
                String stu_class = rs.getString("stu_class");
                Student stu = new Student(id, stu_no, stu_name, stu_sex, stu_collage, stu_class);
                list.add(stu);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    @Override
    public List<Student> selectAllByStuNoDesc() {
        List<Student> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select id,stu_no,stu_name,stu_sex,stu_collage,stu_class from student order by stu_no desc";
            ps = conn.prepareStatement(sql);
            // 查出数据
            rs = ps.executeQuery();
            while (rs.next()) {
                // 取出数据
                Integer id = rs.getInt("id");
                String stu_no = rs.getString("stu_no");
                String stu_name = rs.getString("stu_name");
                String stu_sex = rs.getString("stu_sex");
                String stu_collage = rs.getString("stu_collage");
                String stu_class = rs.getString("stu_class");
                Student stu = new Student(id, stu_no, stu_name, stu_sex, stu_collage, stu_class);
                list.add(stu);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn, ps, rs);
        }
        return list;
    }

    /**
     * 根据学生学号查询数据库
     * @param stuNo 学生学号
     * @return 学生对象
     */
    @Override
    public Student selectByStuNo(String stuNo) {
        Student stu = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select id,stu_no,stu_name,stu_sex,stu_collage,stu_class from student where stu_no = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, stuNo);
            rs = ps.executeQuery();
            if(rs.next()){
                // 数据库中存在此人
                Integer id = rs.getInt("id");
                String stu_no = rs.getString("stu_no");
                String stu_name = rs.getString("stu_name");
                String stu_sex = rs.getString("stu_sex");
                String stu_collage = rs.getString("stu_collage");
                String stu_class = rs.getString("stu_class");
                // 封装到学生对象中
                stu = new Student(id, stu_no, stu_name, stu_sex, stu_collage, stu_class);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn, ps, rs);
        }
        return stu;
    }

    /**
     * 通过学生学号修改数据中学生的信息
     * @param stu
     * @return 1表示修改成功
     */
    @Override
    public int update(Student stu) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update student set stu_name = ?, stu_sex = ?, stu_collage = ?, stu_class = ? where stu_no = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, stu.getStuName());
            ps.setString(2, stu.getStuSex());
            ps.setString(3, stu.getStuCollage());
            ps.setString(4, stu.getStuClass());
            ps.setString(5, stu.getStuNo());
            count = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn, ps, null);
        }

        return count;
    }

    /**
     * 插入学生信息
     * @param stu 学生
     * @return 1表示成功
     */
    @Override
    public int insert(Student stu) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into student (stu_no, stu_name, stu_sex, stu_collage, stu_class) values (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, stu.getStuNo());
            ps.setString(2, stu.getStuName());
            ps.setString(3, stu.getStuSex());
            ps.setString(4, stu.getStuCollage());
            ps.setString(5, stu.getStuClass());
            count = ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn, ps, null);
        }
        return count;
    }

    /**
     * 根据学号删除信息
     * @param stuNo 学号
     * @return 1表示成功
     */
    @Override
    public int deleteByStuNo(String stuNo) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from student where stu_no = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, stuNo);
            count = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn, ps, null);
        }
        return count;
    }


}
