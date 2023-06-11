package com.xxxy.studentmanagement.views;

import com.xxxy.studentmanagement.bean.Administrator;
import com.xxxy.studentmanagement.bean.User;
import com.xxxy.studentmanagement.dao.AdministratorDao;
import com.xxxy.studentmanagement.dao.UserDao;
import com.xxxy.studentmanagement.dao.exception.LoginException;
import com.xxxy.studentmanagement.dao.impl.AdministratorDaoImpl;
import com.xxxy.studentmanagement.dao.impl.UserDaoImpl;
import com.xxxy.studentmanagement.utils.ToolUtil;
import com.xxxy.studentmanagement.utils.YzmUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class LoginGUI implements ActionListener {
    // 存放登录成功的用户名
    public static User currentUser;
    public static Administrator currentAdministrator;
    // 声明组件
    private JFrame frame;
    private JLabel userLabel, passwordLabel, yzmLabel, yzmMes, powerLabel;
    private JTextField userText, yzmText;
    private JPasswordField passwordText;
    private JButton loginButton, registerButton;
    private JRadioButton userBtn, adminBtn;
    // 创建连接数据库的封装类
    private UserDao userDao = new UserDaoImpl();
    private AdministratorDao administratorDao = new AdministratorDaoImpl();
    //存放验证码
    String yzmResult = "";
    public LoginGUI() {
        // 创建 JFrame 对象并设置标题
        frame = new JFrame("登录页面");
        // 设置窗口大小
        frame.setSize(450,300);
        // 设置窗口在屏幕中间显示
        frame.setLocationRelativeTo(null);
        // 禁用窗口的默认关闭操作
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 创建面板对象
        JPanel panel = new JPanel();
        //设置面板布局
        panel.setLayout(null);
        // 创建用户标签
        userLabel = new JLabel("账号:");
        userLabel.setBounds(50, 30, 80, 30);

        // 创建用户名输入框
        userText = new JTextField(20);
        // 用户名文本框
        userText.setBounds(150, 30, 170, 30);

        // 创建密码标签
        passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(50, 70, 80, 30);
        // 创建密码输入框
        passwordText = new JPasswordField(20);
        passwordText.setBounds(150, 70, 170, 30);

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

        // 创建验证码标签
        yzmLabel = new JLabel("验证码:");
        yzmLabel.setBounds(50, 140, 80, 30);
        // 创建验证码输入框
        yzmText = new JTextField(15);
        yzmText.setBounds(150, 140, 120, 30);
        // 创建存放验证码的标签
        yzmResult = YzmUtil.getYzm();
        yzmMes = new JLabel(yzmResult);
        yzmMes.setBounds(280, 140, 120, 30);

        // 创建登录按钮
        loginButton = new JButton("登录");
        loginButton.setBounds(140, 200, 80, 20);
        // 创建注册按钮
        registerButton = new JButton("注册");
        registerButton.setBounds(260, 200, 80, 20);
        // 添加组件到面板中
        panel.add(userBtn);
        panel.add(adminBtn);
        panel.add(powerLabel);
        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(yzmLabel);
        panel.add(yzmText);
        panel.add(yzmMes);
        panel.add(loginButton);
        panel.add(registerButton);
        // 为登录按钮注册事件监听器
        loginButton.addActionListener(this);
        // 为注册按钮注册事件监听器
        registerButton.addActionListener(this);
        // 将面板添加到窗口中
        frame.add(panel);
        // 设置窗口可见
        frame.setVisible(true);
        //设置窗口不可变更大小
        frame.setResizable(false);
    }

    /**
     * 处理点击登录按钮之后的逻辑
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) { // 如果是登录按钮被点击了
            // 获取登录的用户名 清除前后空格
            String name = userText.getText().trim();
            // 获取登录的密码
            String password = String.valueOf(passwordText.getPassword());
            // 获取验证码 清除前后空格
            String yzm = yzmText.getText().trim();
            // 根据用户名和密码,验证码进行登录验证
            boolean result = false;
            try {
                result = LoginCheckResult(name, password, yzm);
            } catch (LoginException ex) {
                ex.printStackTrace();
            }
            if (result) {
                // 登录成功
                if(userBtn.isSelected()){
                    // 用户权限被选择
                    // 保存当前登录的用户名
                    currentUser = new User();
                    currentUser.setUsername(name);
                    // 关闭当前页面
                    frame.dispose();
                    // 跳到学生管理页面
                    new StudentManagementGUI();
                }
                if(adminBtn.isSelected()){
                    // 管理员权限被选择
                    currentAdministrator = new Administrator();
                    currentAdministrator.setUsername(name);
                    // 关闭当前页面
                    frame.dispose();
                    // 跳到管理员页面
                    new AdministratorGUI();
                }

            } else {
                // 登录失败
                if (!userBtn.isSelected() && !adminBtn.isSelected()){
                    JOptionPane.showMessageDialog(null, "请选择权限");
                }
                if(!yzmMes.getText().equals(yzmText.getText())){
                    // 验证码错误
                    JOptionPane.showMessageDialog(null, "验证码错误");
                }
                yzmMes.setText(YzmUtil.getYzm());
            }
        } else if (e.getSource() == registerButton) { // 如果是注册按钮被点击了
            frame.dispose();
            // 弹出注册窗口
            new RegisterGUI();
        }
    }
    /**
     * 根据用户名和密码进行登录验证
     *
     * @param name     用户名
     * @param password 密码
     * @return true 表示验证成功，false 表示验证失败
     */
    private boolean LoginCheckResult(String name, String password, String yzm) throws LoginException {
        // 这里可以通过数据库查询等方式进行验证
        boolean flag = false;
        // 这里只是简单的判断用户名和密码是否都为 空
        if(ToolUtil.isEmpty(name) || ToolUtil.isEmpty(password) || ToolUtil.isEmpty(yzm) || (!userBtn.isSelected() && !adminBtn.isSelected())){
            // 登录失败
            flag = false;
        }else{
            // 账号 密码 验证码不为空
            // 判断验证码是否错误
            if(yzmMes.getText().equals(yzmText.getText())){
                // 输入的验证码正确

                // 用户单选按钮选择
                if(userBtn.isSelected()){
                    // 当用户按钮被选择
                    //连接数据库 判断用户和密码
                    int count = userDao.selectByUnameAndPwd(name, password);
                    if(count == 1){
                        // 数据库中存在，登录成功
                        flag = true;
                    }else{
                        // 登录失败 数据库中不存在此人
                        JOptionPane.showMessageDialog(null, "账号或密码错误");
                        throw new LoginException("登录失败，不存在此用户");
                    }
                }

                // 管理员单选按钮选择
                if(adminBtn.isSelected()){
                    int count = administratorDao.selectByUnameAndPwd(name, password);
                    if(count == 1){
                        // 数据库中存在，登录成功
                        flag = true;
                    }else{
                        // 登录失败 数据库中不存在此人
                        JOptionPane.showMessageDialog(null, "账号或密码错误");
                        throw new LoginException("登录失败，不存在此用户");
                    }
                }

            }
        }
        return flag;
    }
    public static void main(String[] args) {
        new LoginGUI();
    }
}