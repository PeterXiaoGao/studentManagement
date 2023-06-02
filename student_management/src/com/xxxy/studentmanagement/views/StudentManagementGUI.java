package com.xxxy.studentmanagement.views;

import com.xxxy.studentmanagement.bean.Student;
import com.xxxy.studentmanagement.dao.StudentDao;
import com.xxxy.studentmanagement.dao.impl.StudentDaoImpl;
import com.xxxy.studentmanagement.utils.ToolUtil;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Vector;

public class StudentManagementGUI {
    private final StudentDao studentDao = new StudentDaoImpl();
    private final JTextField selectStuNoFiled;
    private final JButton stuNoButton, showByStuNoAscBtn, showByStuNoDescBtn, showRevertBtn;
    private final JFrame jframe;
    private final JLabel loginNameLabel, welcomeLabel;
    private final JPanel showStuPanel, selectStuPanel;
    private final JTable stuInfoTable, selectInfoTable;
    private final DefaultTableModel stuInfoModel, selectInfoModel;

    private boolean selectIsFirstClick = true;   //是否为第一次点击
    private boolean deleteIsFirstClick = true;   //是否为第一次点击
    public StudentManagementGUI(){
        // 创建窗体组件
        jframe = new JFrame("学生管理系统");
        // 窗体布局
        jframe.setLayout(null);
        // 窗体大小
        jframe.setSize(700, 550);
        // 窗体居中显示
        jframe.setLocationRelativeTo(null);

        // 设置欢迎你 标签
        welcomeLabel = new JLabel("欢迎您,");
        welcomeLabel.setBounds(270, 10, 70, 28);
        welcomeLabel.setFont(new Font(null, Font.BOLD, 18));
        jframe.add(welcomeLabel);

        // 获取登录的用户名
        String name = "用户";
        if(LoginGUI.currentUser != null){
            name = LoginGUI.currentUser.getUsername();
        }
        loginNameLabel = new JLabel(name);
        loginNameLabel.setForeground(Color.RED);
        loginNameLabel.setFont(new Font(null, Font.BOLD, 18));
        loginNameLabel.setBounds(340, 10, 300, 28);
        jframe.add(loginNameLabel);

        // 展示学生信息的JPanel  展示功能
        showStuPanel = new JPanel();
        showStuPanel.setLayout(null);
        showStuPanel.setBorder(new TitledBorder(null, "学生信息", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
        showStuPanel.setBounds(23, 40, 651, 280);

        // 展示学生信息  通过表格组件进行展示，表格需要一个表头，表头数据的组成是一个数组
        String[] title_1 = {"编号", "学号", "姓名", "性别", "学院", "班级"};
        String[][] data_1 = {};

        // 初始化表格组件以及数据模型组件
        stuInfoModel = new DefaultTableModel(data_1, title_1);
        stuInfoTable = new JTable();
        stuInfoTable.setModel(stuInfoModel);
        //设置为不可编辑表格的数据
        stuInfoTable.setEnabled(false);
        // 查询数据库放数据 学生所有信息
        putData(1, null);

        // 将表格组件添加到JScrollPane中, 并且将JScrollPane添加到showJStuPanel 面板中  在将showJStuPanel添加到JFrame中
        JScrollPane jScrollPane_1 = new JScrollPane();
        jScrollPane_1.setBounds(25, 22, 607, 208);
        jScrollPane_1.setViewportView(stuInfoTable);
        // 滚动面板加到展示面板上
        showStuPanel.add(jScrollPane_1);

        // 通过学号排序按钮
        showByStuNoAscBtn = new JButton("学号升序");
        showByStuNoAscBtn.setBounds(170, 240, 90, 23);
        showStuPanel.add(showByStuNoAscBtn);

        // 点击学号升序按钮之后的处理事件
        showByStuNoAscBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 处理点击学号升序之后的按钮
                stuNoByAsc();
            }
        });

        showByStuNoDescBtn = new JButton("学号降序");
        showByStuNoDescBtn.setBounds(310, 240, 90, 23);
        showStuPanel.add(showByStuNoDescBtn);


        // 点击学号降序按钮之后的的事件
        showByStuNoDescBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 处理点击学号降序之后的按钮
                stuNobyDesc();
            }
        });

        // 还原按钮
        showRevertBtn = new JButton("还原");
        showRevertBtn.setBounds(450, 240, 90, 23);
        showStuPanel.add(showRevertBtn);
        // 加到窗体中
        jframe.add(showStuPanel);

        // 点击还原按钮事件
        showRevertBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 点击还原按钮事件之后的逻辑
                putData(1, null);
            }
        });


        // 查询功能面板   查询功能
        selectStuPanel = new JPanel();
        selectStuPanel.setLayout(null);
        selectStuPanel.setBorder(new TitledBorder(null, "查询学生", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
        selectStuPanel.setBounds(23, 330, 651, 150);

        //准备表头  和 数据
        String[] title_2 = {"编号", "学号", "姓名", "性别", "学院", "班级"};
        String[][] data_2 = {};

        //  初始化表格组件以及数据模型组件
        selectInfoModel = new DefaultTableModel(data_2, title_2);
        selectInfoTable = new JTable();
        selectInfoTable.setModel(selectInfoModel);
        //设置为不可编辑表格的数据
        selectInfoTable.setEnabled(false);

        // 创建查询按钮
        stuNoButton = new JButton("查询");
        stuNoButton.setBounds(400, 20, 80, 23);
        selectStuPanel.add(stuNoButton);
        // 创建输入学号的文本框
        selectStuNoFiled = new JTextField("请输入学号查询");
        selectStuNoFiled.setFocusable(false);
        selectStuNoFiled.setFont(new Font(null, Font.CENTER_BASELINE, 10));
        selectStuNoFiled.setForeground(Color.GRAY);
        selectStuNoFiled.setBounds(200, 20, 180, 24);
        selectStuPanel.add(selectStuNoFiled);
        // 查询学号文本框点击事件
        selectStuNoFiled.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(selectIsFirstClick){
                    //第一次单击
                    selectStuNoFiled.setFocusable(true);
                    selectStuNoFiled.setFont(new Font(null, Font.BOLD, 10));
                    selectStuNoFiled.setForeground(Color.BLACK);
                    selectStuNoFiled.setText("");
                    selectIsFirstClick = false; // 修改标志位
                }

            }
        });

        selectStuNoFiled.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // 查询学号获得焦点
                // 如果不是第一次获得焦点 并且里面是 "请输入学号查询" 才清空文本框
                if(!selectIsFirstClick && "请输入学号查询".equals(selectStuNoFiled.getText())){
                    selectStuNoFiled.setText("");
                    selectStuNoFiled.setFont(new Font(null, Font.BOLD, 10));
                    selectStuNoFiled.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // 查询学号失去焦点
                if(ToolUtil.isEmpty(selectStuNoFiled.getText())){
                    selectStuNoFiled.setText("请输入学号查询");
                    selectStuNoFiled.setFont(new Font(null, Font.CENTER_BASELINE, 10));
                    selectStuNoFiled.setForeground(Color.GRAY);
                }
            }
        });

        // 点击查询按钮事件
        stuNoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectData(selectStuNoFiled.getText());
            }
        });

        // 将表格组件添加到JScrollPane中, 并且将JScrollPane添加到showJStuPanel 面板中  在将showJStuPanel添加到JFrame中
        // 滚动面板
        JScrollPane jScrollPane_2 = new JScrollPane();
        jScrollPane_2.setBounds(25, 60, 607, 70);
        jScrollPane_2.setViewportView(selectInfoTable);
        // 滚动面板加到展示面板上
        selectStuPanel.add(jScrollPane_2);
        // 加到窗体中
        jframe.add(selectStuPanel);



        // 窗体显示
        jframe.setVisible(true);
        // 窗体关闭按钮
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 不可改变窗体大小
        jframe.setResizable(false);

    }


    /**
     * 让展示面板 按照学号降序展示
     */
    private void stuNobyDesc() {
        List<Student> stuList = studentDao.selectAllByStuNoDesc();
        putData(stuList);
    }

    /**
     * 让展示面板 按照学号升序展示
     */
    private void stuNoByAsc() {
        List<Student> stuList = studentDao.selectAllByStuNoAsc();
        putData(stuList);
    }



    /**
     * 根据传入的学生集合 对展示面板重新展示
     * @param stuList 学生集合
     */
    private void putData(List<Student> stuList){
        DefaultTableModel model = (DefaultTableModel) stuInfoTable.getModel();
        model.setRowCount(0);
        // 遍历集合
        for(Student stu : stuList){
            Vector vector1 = new Vector();
            vector1.add(stu.getUserId());
            vector1.add(stu.getStuNo());
            vector1.add(stu.getStuName());
            vector1.add(stu.getStuSex());
            vector1.add(stu.getStuCollage());
            vector1.add(stu.getStuClass());
            model.addRow(vector1);
        }
    }

    /**
     * 第一次查询所有的信息并且插入到展示面板上， 不是第一次根据学号查询数据库 只插入单条数据
     * @param first 存放第几次调用
     * @param stuNo 学号
     */
    private void putData(int first, String stuNo) {
        DefaultTableModel model = (DefaultTableModel) stuInfoTable.getModel();
        if(first == 1){
            model.setRowCount(0);
            // 第一次调用
            List<Student> list = studentDao.selectAll();
            // 遍历集合
            for(Student stu : list){
                Vector vector1 = new Vector();
                vector1.add(stu.getUserId());
                vector1.add(stu.getStuNo());
                vector1.add(stu.getStuName());
                vector1.add(stu.getStuSex());
                vector1.add(stu.getStuCollage());
                vector1.add(stu.getStuClass());
                model.addRow(vector1);
            }
        }else{
            // 其他次调用 缓解数据库压力 查询的压力 不用每次更新都查询全部
            // 根据学号查询数据库
            Student student = studentDao.selectByStuNo(stuNo);
            Vector vector2 = new Vector();
            vector2.add(student.getUserId());
            vector2.add(student.getStuNo());
            vector2.add(student.getStuName());
            vector2.add(student.getStuSex());
            vector2.add(student.getStuCollage());
            vector2.add(student.getStuClass());
            model.addRow(vector2);
        }

    }

    /**
     * 处理查询按钮点击之后的逻辑 查询学生信息 并且放在查询面板上
     */
    private void selectData(String stuNo){
        // 编写点击查询按钮之后的逻辑
        // 根据学号连接数据库 查询学生信息
        stuNo = stuNo.trim();
        if(!ToolUtil.isEmpty(stuNo) && !"请输入学号查询".equals(stuNo)){
            // 学号文本框不为空
            // 查询数据库
            Student stu = studentDao.selectByStuNo(stuNo);
            if(stu != null){
                // 表示数据库查询成功
                selectInfoModel.setRowCount(0);
                Vector vector = new Vector();
                vector.add(stu.getUserId());
                vector.add(stu.getStuNo());
                vector.add(stu.getStuName());
                vector.add(stu.getStuSex());
                vector.add(stu.getStuCollage());
                vector.add(stu.getStuClass());
                selectInfoModel.addRow(vector);

            }else{
                // 数据库中不存在此人信息
                JOptionPane.showMessageDialog(null, "请输入正确的学号");
            }

        }else{
            // 学号为空
            JOptionPane.showMessageDialog(null, "请输入正确的学号");
        }
    }

    public static void main(String[] args) {
        new StudentManagementGUI();
    }

}