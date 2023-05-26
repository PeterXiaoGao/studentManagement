package com.xxxy.studentmanager.dao;

import com.xxxy.studentmanager.bean.Student;

import java.util.List;

public interface StudentDao {

    /**
     * 查询数据库所有的学生，并且结果是，通过学生id升序查出
     * @return 存放所有学生的集合
     */
    List<Student> selectAll();

    /**
     *  查询数据库所有的学生，并且结果是，通过学生学号升序查出
     * @return 存放所有学生的集合
     */
    List<Student> selectAllByStuNoAsc();

    /**
     *  查询数据库所有的学生，并且结果是，通过学生学号降序查出
     * @return 存放所有学生的集合
     */
    List<Student> selectAllByStuNoDesc();

    /**
     * 通过学号查询数据库的学生
     * @param stuNo 学号
     * @return  学生对象  null表示不存在
     */
    Student selectByStuNo(String stuNo);

    /**
     * 根据学号修改数据库中的学生信息
     * @param stu
     * @return
     */
    int update(Student stu);

    /**
     * 向数据库插入学生
     * @param stu 学生
     * @return 1成功
     */
    int insert(Student stu);

    /**
     * 通过学号 删除数据库学生信息
     * @param stNo 学号
     * @return 1表示成功
     */
    int deleteByStuNo(String stNo);


}
