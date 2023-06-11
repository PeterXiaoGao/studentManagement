package com.xxxy.studentmanagement.views;

import com.xxxy.studentmanagement.bean.Administrator;
import com.xxxy.studentmanagement.bean.User;
import com.xxxy.studentmanagement.dao.AdministratorDao;
import com.xxxy.studentmanagement.dao.UserDao;
import com.xxxy.studentmanagement.dao.exception.RegisterException;
import com.xxxy.studentmanagement.dao.impl.AdministratorDaoImpl;
import com.xxxy.studentmanagement.dao.impl.UserDaoImpl;
import com.xxxy.studentmanagement.utils.ToolUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

// 手机号正则：/^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
// 密码正则：^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$        // 由数字和字母组成，并且要同时含有数字和字母，且长度要在8-16位之间。
public class RegisterGUI extends JFrame {
    private UserDao userDao = new UserDaoImpl();
    private AdministratorDao administratorDao = new AdministratorDaoImpl();
    private JLabel nameLabel, passwordLabel, phoneLabel, nameMes, passwordMes, phoneMes, powerLabel;
    private JTextField nameField, phoneField;
    private JPasswordField passwordField;
    private JButton registerButton, loginButton;
    private JRadioButton userBtn,adminBtn;
    public RegisterGUI() {
        super("注册页面");
        // 设置窗口大小和关闭操作
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 创建组件
        // 用户提示框
        nameMes = new JLabel();
        // 密码提示框
        passwordMes = new JLabel();
        // 手机号提示框
        phoneMes = new JLabel();
        nameLabel = new JLabel("账号:");
        passwordLabel = new JLabel("密码:");
        phoneLabel = new JLabel("手机号:");
        nameField = new JTextField(20);
        // 添加账号文本框失去焦点和获得焦点的事件
        nameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // 获得焦点
                // 更新账号提示框
                nameMes.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                // 失去焦点
                String username = nameField.getText();
                // 判断是否含有空格 且是否为空
                if(ToolUtil.isEmpty(username)){
                    nameMes.setText("用户名不能为空");
                    nameMes.setFont(new Font(null, Font.BOLD,9));
                    nameMes.setForeground(Color.RED);
                }else{
                    // 先判断用户名中是否含有空格
                    if(ToolUtil.hasSpace(username)){
                        nameMes.setText("用户名不能含有空格");
                        nameMes.setFont(new Font(null, Font.BOLD,9));
                        nameMes.setForeground(Color.RED);
                    }else{
                        // 失去焦点后
                        if(userBtn.isSelected()){
                            // 用户单选按钮被选择
                            // 先去判断数据库用户表中用户名是否存在 如果存在则给提示 不存在 √
                            // 获得存在还是不存在信息
                            int count = userDao.selectByUsername(nameField.getText());
                            if(count == 1){
                                // 存在
                                nameMes.setText("");
                                nameMes.setText("用户名已存在");
                                nameMes.setFont(new Font(null, Font.BOLD,9));
                                nameMes.setForeground(Color.RED);
                            }else {
                                // 不存在 √
                                nameMes.setText("√");
                                nameMes.setFont(new Font(null, Font.BOLD,20));
                                nameMes.setForeground(Color.GREEN);
                            }
                        }
                        // 失去焦点后
                        if(adminBtn.isSelected()){
                            // 管理员单选按钮被选择
                            // 先去判断数据库管理员表中用户名是否存在 如果存在则给提示 不存在 √
                            // 获得存在还是不存在信息
                            int count = administratorDao.selectByUsername(nameField.getText());
                            if(count == 1){
                                // 存在
                                nameMes.setText("");
                                nameMes.setText("用户名已存在");
                                nameMes.setFont(new Font(null, Font.BOLD,9));
                                nameMes.setForeground(Color.RED);
                            }else {
                                // 不存在 √
                                nameMes.setText("√");
                                nameMes.setFont(new Font(null, Font.BOLD,20));
                                nameMes.setForeground(Color.GREEN);
                            }
                        }
                        // 监听用户单选按钮
                        userBtn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(userBtn.isSelected()){
                                    // 用户单选按钮被选择
                                    // 先去判断数据库用户表中用户名是否存在 如果存在则给提示 不存在 √
                                    // 获得存在还是不存在信息
                                    int count = userDao.selectByUsername(nameField.getText());
                                    if(count == 1){
                                        // 存在
                                        nameMes.setText("");
                                        nameMes.setText("用户名已存在");
                                        nameMes.setFont(new Font(null, Font.BOLD,9));
                                        nameMes.setForeground(Color.RED);
                                    }else {
                                        // 不存在 √
                                        nameMes.setText("√");
                                        nameMes.setFont(new Font(null, Font.BOLD,20));
                                        nameMes.setForeground(Color.GREEN);
                                    }
                                }
                            }
                        });

                        // 监听管理员单选按钮
                        adminBtn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(adminBtn.isSelected()){
                                    // 管理员单选按钮被选择
                                    // 先去判断数据库管理员表中用户名是否存在 如果存在则给提示 不存在 √
                                    // 获得存在还是不存在信息
                                    int count = administratorDao.selectByUsername(nameField.getText());
                                    if(count == 1){
                                        // 存在
                                        nameMes.setText("");
                                        nameMes.setText("用户名已存在");
                                        nameMes.setFont(new Font(null, Font.BOLD,9));
                                        nameMes.setForeground(Color.RED);
                                    }else {
                                        // 不存在 √
                                        nameMes.setText("√");
                                        nameMes.setFont(new Font(null, Font.BOLD,20));
                                        nameMes.setForeground(Color.GREEN);
                                    }
                                }
                            }
                        });


                    }

                }
            }
        });
        phoneField = new JTextField(20);
        // 添加手机文本框获取和失去焦点事件
        phoneField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // 获取焦点
                phoneMes.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                // 失去焦点
                // 获得手机号框内容
                String phoneText = phoneField.getText();
                if(ToolUtil.isEmpty(phoneText)){
                    // 为空
                    phoneMes.setText("手机号不能为空");
                    phoneMes.setFont(new Font(null, Font.BOLD,9));
                    phoneMes.setForeground(Color.RED);
                }else{
                    //不为空
                    // 判断是否符合正则表达式
                    boolean phoneResult = phoneText.matches("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$");
                    if(phoneResult){
                        // 符合手机号的正则表达式
                        phoneMes.setText("√");
                        phoneMes.setFont(new Font(null, Font.BOLD,20));
                        phoneMes.setForeground(Color.GREEN);
                    }else{
                        // 不符合手机号的正则表达式
                        phoneMes.setText("手机号不正确");
                        phoneMes.setFont(new Font(null, Font.BOLD,9));
                        phoneMes.setForeground(Color.RED);
                    }
                }
            }
        });
        passwordField = new JPasswordField(20);
        //添加密码失去焦点和获得焦点的事件
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // 获得焦点
                passwordMes.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                // 失去焦点
                // 得到输入的密码
                String password = String.valueOf(passwordField.getPassword());
                if(ToolUtil.isEmpty(password)){
                    passwordMes.setText("密码不能为空");
                    passwordMes.setFont(new Font(null, Font.BOLD,9));
                    passwordMes.setForeground(Color.RED);
                }else{
                    // 密码不为空
                    // 正则表达式  由数字和字母组成，并且要同时含有数字和字母，且长度要在8-16位之间。
                    boolean pdResult = password.matches("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$");
                    if(pdResult){
                        // 符合要求
                        passwordMes.setText("√");
                        passwordMes.setFont(new Font(null, Font.BOLD,20));
                        passwordMes.setForeground(Color.GREEN);
                    }else {
                        // 不符合要求
                        passwordMes.setText("由数字和字母组成[8-16]位");
                        passwordMes.setFont(new Font(null, Font.BOLD,9));
                        passwordMes.setForeground(Color.RED);
                    }

                }
            }
        });
        registerButton = new JButton("注册");
        //点击注册按钮事件
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                // 编写点击注册按钮后的逻辑
                try {
                    registerBtn();
                } catch (RegisterException ex) {
                    ex.printStackTrace();
                }


            }
        });

        loginButton = new JButton("登录");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 编写点击登录按钮后的逻辑
                // 关闭此窗口
                RegisterGUI.super.dispose();
                // 去登录页面
                new LoginGUI();
            }
        });
        // 自定义布局
        setLayout(null);

        // 账号
        nameLabel.setBounds(50, 30, 80, 30);
        // 账号文本框
        nameField.setBounds(150, 30, 170, 30);
        // 账号框提示信息
        nameMes.setBounds(325,30, 100,30);
        // 密码
        passwordLabel.setBounds(50, 70, 80, 30);
        // 密码框
        passwordField.setBounds(150, 70, 170, 30);
        // 密码框提示信息
        passwordMes.setBounds(325, 70,110,30);
        powerLabel = new JLabel("权限:");
        powerLabel.setBounds(50, 110,80,20);
        // 用户 管理员 单选框
        userBtn = new JRadioButton("用户");
        adminBtn = new JRadioButton("管理员");
        userBtn.setBounds(160, 110, 60, 20);
        adminBtn.setBounds(230, 110, 80, 20);
        ButtonGroup group = new ButtonGroup();
        group.add(userBtn);
        group.add(adminBtn);

        // 手机号
        phoneLabel.setBounds(50, 140, 80, 30);
        // 手机号框
        phoneField.setBounds(150, 140, 170, 30);
        // 手机号框提示信息
        phoneMes.setBounds(325, 140,110,30);
        //注册按钮
        registerButton.setBounds(140, 200, 80, 20);
        // 取消按钮
        loginButton.setBounds(260, 200, 80, 20);

        // 添加组件
        add(powerLabel);
        add(userBtn);
        add(adminBtn);
        add(nameLabel);
        add(nameField);
        add(passwordLabel);
        add(passwordField);
        add(phoneLabel);
        add(phoneField);
        add(registerButton);
        add(loginButton);
        add(nameMes);
        add(passwordMes);
        add(phoneMes);
        // 显示窗口
        setVisible(true);
        setLocationRelativeTo(null);
    }
    public static void main(String[] args) {
        new RegisterGUI();
    }

    private void registerBtn() throws RegisterException{
        // 编写点击注册按钮后的逻辑
        // 判断 密码 账号 手机号是否符合规范
        if("√".equals(nameMes.getText()) && "√".equals(passwordMes.getText()) && "√".equals(phoneMes.getText())){
            // 获得账户内容
            String username = nameField.getText();
            // 获得密码框内容
            String password = String.valueOf(passwordField.getPassword());
            // 获取手机号框内容
            String telephone = phoneField.getText();

            // 选中的单选是用户 则插入数据库用户表
            if(userBtn.isSelected()){
                // 选择的是用户单选按钮
                // 创建用户对象
                User user = new User();
                // 设置账户
                user.setUsername(username);
                // 设置密码
                user.setPassword(password);
                // 设置手机号
                user.setTelephone(telephone);

                // 插入数据库 user表中
                int count = userDao.insert(user);
                if(count == 1){
                    // 弹框注册完成
                    JOptionPane.showMessageDialog(null, "注册成功");
                }else{
                    // 注册失败抛异常
                    throw new RegisterException("注册失败，请稍后再试");
                }
            }

            // 选中的单选是管理员 则插入数据库管理员表
            if(adminBtn.isSelected()){
                // 选择的是管理员单选按钮
                // 创建管理员对象
                Administrator administrator = new Administrator();
                // 设置账户
                administrator.setUsername(username);
                // 设置密码
                administrator.setPassword(password);
                // 设置手机号
                administrator.setTelephone(telephone);

                // 插入数据库 管理员表中
                int count = administratorDao.insert(administrator);
                if(count == 1){
                    // 弹框注册完成
                    JOptionPane.showMessageDialog(null, "注册成功");
                }else{
                    // 注册失败抛异常
                    throw new RegisterException("注册失败，请稍后再试");
                }

            }

            if(!adminBtn.isSelected() && !userBtn.isSelected()){
                // 两个单选框都没有选择
                JOptionPane.showMessageDialog(null, "权限没有选择");
            }

        }else{
            // 不符合
            if(!adminBtn.isSelected() && !userBtn.isSelected()){
                // 两个单选框都没有选择
                JOptionPane.showMessageDialog(null, "权限没有选择");
            }else {
                // 弹窗错误
                JOptionPane.showMessageDialog(null, "账号或者密码或者手机号错误");
                throw new RegisterException("注册失败，请稍后再试");
            }
        }
    }


}