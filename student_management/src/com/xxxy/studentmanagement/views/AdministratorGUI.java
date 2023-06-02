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

public class AdministratorGUI {
    private final StudentDao studentDao = new StudentDaoImpl();
    private final JTextField selectStuNoFiled, addStuNoFiled, addStuNameFiled,
            addStuSexFiled,addStuCollageFiled, addStuClassFiled,
            deleteStuNoFiled, modifyStuNoFiled, modifyStuNameFiled,
            modifyStuSexFiled,modifyStuCollageFiled, modifyStuClassFiled;
    private final JButton stuNoButton, addStuButton, deleteStuButton,
            showByStuNoAscBtn, showByStuNoDescBtn, showRevertBtn, modifyStuButton;
    private final JFrame jframe;
    private final JLabel loginNameLabel, welcomeLabel, addStuNoLabel,
            addStuNameLabel, addStuSexLabel, addStuCollageLabel,
            addStuClassLabel, modifyStuNoLabel, modifyStuNameLabel,
            modifyStuSexLabel, modifyStuCollageLabel,modifyStuClassLabel;
    private final JPanel showStuPanel, selectStuPanel, addStuPanel, deleteStuPanel, modifyStuPanel;
    private final JTable stuInfoTable, selectInfoTable;
    private final DefaultTableModel stuInfoModel, selectInfoModel;

    private boolean selectIsFirstClick = true;   //是否为第一次点击
    private boolean deleteIsFirstClick = true;   //是否为第一次点击
    public AdministratorGUI(){
        // 创建窗体组件
        jframe = new JFrame("管理员页面");
        // 窗体布局
        jframe.setLayout(null);
        // 窗体大小
        jframe.setSize(700, 800);
        // 窗体居中显示
        jframe.setLocationRelativeTo(null);

        // 设置欢迎你 标签
        welcomeLabel = new JLabel("欢迎您,");
        welcomeLabel.setBounds(270, 10, 70, 28);
        welcomeLabel.setFont(new Font(null, Font.BOLD, 18));
        jframe.add(welcomeLabel);

        // 获取登录的用户名
        String name = "用户";
        if(LoginGUI.currentAdministrator != null){
            name = LoginGUI.currentAdministrator.getUsername();
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

        //  创建增加功能面板  增加功能
        addStuPanel = new JPanel();
        addStuPanel.setLayout(null);
        addStuPanel.setBorder(new TitledBorder(null, "增加学生", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
        addStuPanel.setBounds(23, 490, 651, 80);

        // 学号标签
        addStuNoLabel = new JLabel("学号:");
        addStuNoLabel.setBounds(80, 15, 35, 20);
        addStuNoLabel.setFont(new Font(null, Font.BOLD, 13));
        addStuPanel.add(addStuNoLabel);
        // 学号文本框
        addStuNoFiled = new JTextField(20);
        addStuNoFiled.setBounds(120, 15, 120, 20);
        addStuPanel.add(addStuNoFiled);

        // 姓名标签
        addStuNameLabel = new JLabel("姓名:");
        addStuNameLabel.setBounds(270, 15,35,20);
        addStuNameLabel.setFont(new Font(null, Font.BOLD, 13));
        addStuPanel.add(addStuNameLabel);
        // 姓名文本框
        addStuNameFiled = new JTextField(20);
        addStuNameFiled.setBounds(310, 15, 120, 20);
        addStuPanel.add(addStuNameFiled);

        // 性别标签
        addStuSexLabel = new JLabel("性别:");
        addStuSexLabel.setBounds(460, 15, 35, 20);
        addStuSexLabel.setFont(new Font(null, Font.BOLD, 13));
        addStuPanel.add(addStuSexLabel);
        // 性别文本框
        addStuSexFiled = new JTextField(20);
        addStuSexFiled.setBounds(500, 15, 120, 20);
        addStuPanel.add(addStuSexFiled);

        // 学院标签
        addStuCollageLabel = new JLabel("学院:");
        addStuCollageLabel.setBounds(80, 45, 35, 20);
        addStuCollageLabel.setFont(new Font(null, Font.BOLD, 13));
        addStuPanel.add(addStuCollageLabel);
        // 学院文本框
        addStuCollageFiled = new JTextField(20);
        addStuCollageFiled.setBounds(120, 45, 120, 20);
        addStuPanel.add(addStuCollageFiled);

        // 班级标签
        addStuClassLabel = new JLabel("班级:");
        addStuClassLabel.setBounds(270, 45, 35, 20);
        addStuClassLabel.setFont(new Font(null, Font.BOLD, 13));
        addStuPanel.add(addStuClassLabel);
        // 班级文本框
        addStuClassFiled = new JTextField(20);
        addStuClassFiled.setBounds(310, 45, 120, 20);
        addStuPanel.add(addStuClassFiled);

        // 增加学生按钮
        addStuButton = new JButton("增加");
        addStuButton.setBounds(520, 43, 80, 23);
        addStuPanel.add(addStuButton);
        // 将增加功能面板添加到窗体中
        jframe.add(addStuPanel);

        addStuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 点击增加按钮后处理的逻辑
                insertByAddBtn();
            }
        });

        //  创建删除功能面板  删除功能
        deleteStuPanel = new JPanel();
        deleteStuPanel.setLayout(null);
        deleteStuPanel.setBorder(new TitledBorder(null, "删除学生", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
        deleteStuPanel.setBounds(23, 580, 651, 65);

        // 删除文本框
        deleteStuNoFiled = new JTextField("请输入学号删除");
        deleteStuNoFiled.setFocusable(false);
        deleteStuNoFiled.setFont(new Font(null, Font.CENTER_BASELINE, 10));
        deleteStuNoFiled.setForeground(Color.GRAY);
        deleteStuNoFiled.setBounds(200, 25, 180, 24);
        deleteStuPanel.add(deleteStuNoFiled);

        // 根据学号删除文本框点击事件
        deleteStuNoFiled.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(deleteIsFirstClick){
                    //第一次单击
                    deleteStuNoFiled.setFocusable(true);
                    deleteStuNoFiled.setFont(new Font(null, Font.BOLD, 10));
                    deleteStuNoFiled.setForeground(Color.BLACK);
                    deleteStuNoFiled.setText("");
                    deleteIsFirstClick = false; // 修改标志位
                }

            }
        });

        // 删除学号文本框获取焦点事件
        deleteStuNoFiled.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // 获得焦点
                if(!deleteIsFirstClick && "请输入学号删除".equals(deleteStuNoFiled.getText())){
                    // 不是第一次获取焦点 并且 里面内容是 "请输入学号删除" 此时修改文本框内容
                    deleteStuNoFiled.setText("");
                    deleteStuNoFiled.setFont(new Font(null, Font.CENTER_BASELINE, 10));
                    deleteStuNoFiled.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // 失去焦点
                if(ToolUtil.isEmpty(deleteStuNoFiled.getText())){
                    // 根据学号删除文本框为空
                    deleteStuNoFiled.setText("请输入学号删除");
                    deleteStuNoFiled.setFont(new Font(null, Font.CENTER_BASELINE, 10));
                    deleteStuNoFiled.setForeground(Color.GRAY);
                }
            }
        });


        // 删除按钮
        deleteStuButton = new JButton("删除");
        deleteStuButton.setBounds(400, 25, 80, 23);
        deleteStuPanel.add(deleteStuButton);
        // 将删除功能面板添加到 窗体中
        jframe.add(deleteStuPanel);

        deleteStuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 点击删除按钮之后的逻辑 传入学号
                deleteData(deleteStuNoFiled.getText());
            }
        });

        //  创建修改功能面板  修改功能
        modifyStuPanel = new JPanel();
        modifyStuPanel.setLayout(null);
        modifyStuPanel.setBorder(new TitledBorder(null, "修改学生", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
        modifyStuPanel.setBounds(23, 655, 651, 85);

        // 学号标签
        modifyStuNoLabel = new JLabel("学号:");
        modifyStuNoLabel.setBounds(80, 15, 35, 20);
        modifyStuNoLabel.setFont(new Font(null, Font.BOLD, 13));
        modifyStuPanel.add(modifyStuNoLabel);
        // 学号文本框
        modifyStuNoFiled = new JTextField(20);
        modifyStuNoFiled.setBounds(120, 15, 120, 20);
        modifyStuPanel.add(modifyStuNoFiled);

        // 姓名标签
        modifyStuNameLabel = new JLabel("姓名:");
        modifyStuNameLabel.setBounds(270, 15,35,20);
        modifyStuNameLabel.setFont(new Font(null, Font.BOLD, 13));
        modifyStuPanel.add(modifyStuNameLabel);
        // 姓名文本框
        modifyStuNameFiled = new JTextField(20);
        modifyStuNameFiled.setBounds(310, 15, 120, 20);
        modifyStuPanel.add(modifyStuNameFiled);

        // 性别标签
        modifyStuSexLabel = new JLabel("性别:");
        modifyStuSexLabel.setBounds(460, 15, 35, 20);
        modifyStuSexLabel.setFont(new Font(null, Font.BOLD, 13));
        modifyStuPanel.add(modifyStuSexLabel);
        // 性别文本框
        modifyStuSexFiled = new JTextField(20);
        modifyStuSexFiled.setBounds(500, 15, 120, 20);
        modifyStuPanel.add(modifyStuSexFiled);

        // 学院标签
        modifyStuCollageLabel = new JLabel("学院:");
        modifyStuCollageLabel.setBounds(80, 45, 35, 20);
        modifyStuCollageLabel.setFont(new Font(null, Font.BOLD, 13));
        modifyStuPanel.add(modifyStuCollageLabel);
        // 学院文本框
        modifyStuCollageFiled = new JTextField(20);
        modifyStuCollageFiled.setBounds(120, 45, 120, 20);
        modifyStuPanel.add(modifyStuCollageFiled);

        // 班级标签
        modifyStuClassLabel = new JLabel("班级:");
        modifyStuClassLabel.setBounds(270, 45, 35, 20);
        modifyStuClassLabel.setFont(new Font(null, Font.BOLD, 13));
        modifyStuPanel.add(modifyStuClassLabel);
        // 班级文本框
        modifyStuClassFiled = new JTextField(20);
        modifyStuClassFiled.setBounds(310, 45, 120, 20);
        modifyStuPanel.add(modifyStuClassFiled);

        // 修改学生按钮
        modifyStuButton = new JButton("修改");
        modifyStuButton.setBounds(520, 43, 80, 23);
        modifyStuPanel.add(modifyStuButton);

        // 修改按钮点击事件
        modifyStuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 点击修改按钮后的逻辑
                updateByModifyBtn();
            }
        });

        // 通过学号修改标签
        JLabel modifyStuLabel = new JLabel("<html>通过学号<br>修改信息<br>(学号不可改)</html>");
        modifyStuLabel.setFont(new Font(null, Font.BOLD, 10));
        modifyStuLabel.setForeground(Color.RED);
        modifyStuLabel.setBounds(10, 23, 80, 40);
        modifyStuPanel.add(modifyStuLabel);
        // 将修改功能面板添加到 窗体中
        jframe.add(modifyStuPanel);

        // 窗体显示
        jframe.setVisible(true);
        // 窗体关闭按钮
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 不可改变窗体大小
        jframe.setResizable(false);

    }

    /**
     * 点击修改按钮之后的逻辑
     */
    private void updateByModifyBtn() {
        // 获取5个文本框输入的内容，同时去除所有空格
        String stuNoText = modifyStuNoFiled.getText();
        String stuNameText = modifyStuNameFiled.getText();
        String stuSexText = modifyStuSexFiled.getText();
        String stuCollageText = modifyStuCollageFiled.getText();
        String stuClassText = modifyStuClassFiled.getText();
        if(ToolUtil.hasSpace(stuNoText) || stuNoText.length() != 11 || !ToolUtil.isNumber(stuNoText) || ToolUtil.isEmpty(stuNoText)){
            // 学号含有空格  或者长度不为11 或者不是数字 或者为空字符
            JOptionPane.showMessageDialog(null, "学号不能为空字符或者有空格，且要为11位数字");
        }else if(ToolUtil.hasSpace(stuNameText) || ToolUtil.isEmpty(stuNameText)){
            // 名字含有空格 或者为空字符
            JOptionPane.showMessageDialog(null, "名字不能为空字符或者有空格");
        }else if(ToolUtil.hasSpace(stuSexText) || (!"男".equals(stuSexText) && !"女".equals(stuSexText)) || ToolUtil.isEmpty(stuSexText)){
            // 性别有空格 或者为空字符 或者 不为男并且不为女
            JOptionPane.showMessageDialog(null, "性别不能为空字符或者有空格，且要为男或者女");
        }else if(ToolUtil.hasSpace(stuCollageText) ||  ToolUtil.isEmpty(stuCollageText)){
            // 学院有空格 或者学院为空字符
            JOptionPane.showMessageDialog(null, "学院不能为空字符或者有空格");
        }else if(ToolUtil.hasSpace(stuClassText) ||  ToolUtil.isEmpty(stuClassText)){
            // 班级有空格  或者为空字符
            JOptionPane.showMessageDialog(null, "班级不能为空字符或者有空格");
        }else {
            //根据学号判断 数据中是否已经存在此学号信息 学号是唯一的
            Student stu = studentDao.selectByStuNo(stuNoText);

            // 判断数据库中是否存在此人信息
            if(stu != null){
                // 学号输入正确 且数据库中存在此学生信息
                // 封装学生信息
                Student updateStu = new Student(null, stuNoText, stuNameText, stuSexText, stuCollageText, stuClassText);
                // 根据学号进行修改 调用dao包中的方法进行修改
                int count = studentDao.update(updateStu);
                if(count == 1){
                    // 更新成功
                    JOptionPane.showMessageDialog(null, "更新成功");
                    // 删除更改前的展示信息面板上的 学生信息
                    deleteShowPanelStuInfo(stuNoText);
                    // 更新展示学生信息的面板 (添加刚刚更改的学生信息)
                    putData(2, stuNoText);

                }else{
                    // 更新失败
                    JOptionPane.showMessageDialog(null, "更新失败,请联系管理员");
                }

            }else {
                JOptionPane.showMessageDialog(null, "请输入正确的学号");
            }

        }
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
     * 处理点击删除按钮之后的逻辑
     */
    private void deleteData(String stuNo) {
        // 连接数据库删除此信息
        stuNo = stuNo.trim();
        if(!ToolUtil.isEmpty(stuNo) && !"请输入学号删除".equals(stuNo)){
            // 不为空 删除数据库信息
            int option = JOptionPane.showOptionDialog(null, "请选择一个选项", "确定删除框", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"确定", "取消"}, "确定");

            if (option == JOptionPane.YES_OPTION) {
                // 确定按钮被点击
                int count = studentDao.deleteByStuNo(stuNo);
                if(count == 1){
                    // 删除成功
                    JOptionPane.showMessageDialog(null, "删除成功");

                    // 删除 展示信息面板上面的一条记录
                    deleteShowPanelStuInfo(stuNo);

                }else{
                    JOptionPane.showMessageDialog(null, "请输入正确的学号");
                }
            } else if (option == JOptionPane.NO_OPTION) {
                // 删除按钮被点击了
                // 不做处理
            } else {
                // 对话框其他按钮被点击了
                // 不做处理
            }


        }else{
            // 学号为空
            JOptionPane.showMessageDialog(null, "请输入正确的学号");
        }
    }

    /**
     * 根据学号删除展示信息面板上的一条记录
     * @param stuNo 学号
     */
    private void deleteShowPanelStuInfo(String stuNo) {
        // 获取当前展示信息模板上的 行数
        int row = stuInfoModel.getRowCount();
        // 获取当前展示信息模板上的 列数 因为学号都在 第二列 所以为 下标为1
        int col = 1;
        int flag = 0;
        for(int i = 0; i < row; i++){
            if(stuNo.equals(stuInfoModel.getValueAt(i,col))){
                // 记录学号存在当前的行数
                flag = i;
            }
        }

        // 删除展示面板上的信息
        stuInfoModel.removeRow(flag);
    }


    /**
     * 处理点击增加按钮之后的逻辑
     */
    private void insertByAddBtn() {
        // 获取5个文本框输入的内容，同时去除所有空格
        String stuNoText = addStuNoFiled.getText();
        String stuNameText = addStuNameFiled.getText();
        String stuSexText = addStuSexFiled.getText();
        String stuCollageText = addStuCollageFiled.getText();
        String stuClassText = addStuClassFiled.getText();
        if(ToolUtil.hasSpace(stuNoText) || stuNoText.length() != 11 || !ToolUtil.isNumber(stuNoText) || ToolUtil.isEmpty(stuNoText)){
            // 学号含有空格  或者长度不为11 或者不是数字 或者为空字符
            JOptionPane.showMessageDialog(null, "学号不能为空字符或者有空格，且要为11位数字");
        }else if(ToolUtil.hasSpace(stuNameText) || ToolUtil.isEmpty(stuNameText)){
            // 名字含有空格 或者为空字符
            JOptionPane.showMessageDialog(null, "名字不能为空字符或者有空格");
        }else if(ToolUtil.hasSpace(stuSexText) || (!"男".equals(stuSexText) && !"女".equals(stuSexText)) || ToolUtil.isEmpty(stuSexText)){
            // 性别有空格 或者为空字符 或者 不为男并且不为女
            JOptionPane.showMessageDialog(null, "性别不能为空字符或者有空格，且要为男或者女");
        }else if(ToolUtil.hasSpace(stuCollageText) ||  ToolUtil.isEmpty(stuCollageText)){
            // 学院有空格 或者学院为空字符
            JOptionPane.showMessageDialog(null, "学院不能为空字符或者有空格");
        }else if(ToolUtil.hasSpace(stuClassText) ||  ToolUtil.isEmpty(stuClassText)){
            // 班级有空格  或者为空字符
            JOptionPane.showMessageDialog(null, "班级不能为空字符或者有空格");
        }else {
            //根据学号判断 数据中是否已经存在此学号信息 学号是唯一的
            Student stu = studentDao.selectByStuNo(stuNoText);
            if(stu == null){
                // 数据库中不存在此学号信息 可以插入学生信息
                // 封装学生对象
                Student student = new Student();
                student.setStuNo(stuNoText);
                student.setStuName(stuNameText);
                student.setStuSex(stuSexText);
                student.setStuCollage(stuCollageText);
                student.setStuClass(stuClassText);
                int count = studentDao.insert(student);

                if(count != 1){
                    // 插入失败
                    JOptionPane.showMessageDialog(null, "添加学生信息失败, 请联系管理员");
                }else{
                    // 插入成功
                    JOptionPane.showMessageDialog(null, "添加学生信息成功");

                    // 更新数据
                    putData(2, student.getStuNo());
                }
            }else{
                // 数据中已存在此学号信息 禁止插入
                JOptionPane.showMessageDialog(null, "学号已存在请重新输入");
            }
        }

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
        new AdministratorGUI();
    }

}