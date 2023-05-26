package com.xxxy.studentmanager.views;

import com.xxxy.studentmanager.bean.User;
import com.xxxy.studentmanager.dao.UserDao;
import com.xxxy.studentmanager.dao.impl.UserDaoImpl;
import com.xxxy.studentmanager.utils.ToolUtil;
import com.xxxy.studentmanager.utils.YzmUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class LoginGUI implements ActionListener {
    // 存放登录成功的用户名
    public static User currentUser;
    // 声明组件
    private JFrame frame;
    private JLabel userLabel, passwordLabel, messageLabel, yzmLabel, yzmMes;
    private JTextField userText, yzmText;
    private JPasswordField passwordText;
    private JButton loginButton, registerButton;
    // 创建连接数据库的封装类
    private UserDao userDao = new UserDaoImpl();
    //存放验证码
    String yzmResult = "";
    public LoginGUI() {
        // 创建 JFrame 对象并设置标题
        frame = new JFrame("登录页面");
        // 设置窗口大小
        frame.setBounds(250,100,260,200);
        // 设置窗口在屏幕中间显示
        frame.setLocationRelativeTo(null);
        // 禁用窗口的默认关闭操作
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 创建面板对象
        JPanel panel = new JPanel();
        //设置面板布局
        panel.setLayout(new FlowLayout());
        // 创建用户标签
        userLabel = new JLabel("账号:");
        // 创建用户名输入框
        userText = new JTextField(20);
        // 创建密码标签
        passwordLabel = new JLabel("密码:");
        // 创建密码输入框
        passwordText = new JPasswordField(20);
        // 创建验证码标签
        yzmLabel = new JLabel("验证码:");
        // 创建验证码输入框
        yzmText = new JTextField(15);
        // 创建存放验证码的标签
        yzmResult = YzmUtil.getYzm();
        yzmMes = new JLabel(yzmResult);
        // 创建登录按钮
        loginButton = new JButton("登录");
        // 创建注册按钮
        registerButton = new JButton("注册");
        // 创建消息显示标签
        messageLabel = new JLabel();
        // 添加组件到面板中
        panel.add(userLabel);
        panel.add(userText);
        panel.add(passwordLabel);
        panel.add(passwordText);
        panel.add(yzmLabel);
        panel.add(yzmText);
        panel.add(yzmMes);
        panel.add(loginButton);
        panel.add(registerButton);
        panel.add(messageLabel);
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
            boolean result = LoginCheckResult(name, password, yzm);
            if (result) {
                // 登录成功
                // 保存当前登录的用户名
                currentUser = new User();
                currentUser.setUsername(name);
                // 关闭当前页面
                frame.dispose();
                // 跳到学生管理页面
                new StudentManagementGUI();
            } else {
                // 登录失败
                JOptionPane.showMessageDialog(null, "账号或密码或验证码错误");
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
    private boolean LoginCheckResult(String name, String password, String yzm) {
        // 这里可以通过数据库查询等方式进行验证
        boolean flag = false;
        // 这里只是简单的判断用户名和密码是否都为 admin
        if(ToolUtil.isEmpty(name) || ToolUtil.isEmpty(password) || ToolUtil.isEmpty(yzm)){
            // 登录失败
            flag = false;
        }else{
            // 账号 密码 验证码不为空
            // 判断验证码是否错误
            if(yzmMes.getText().equals(yzmText.getText())){
                // 输入的验证码正确
                //连接数据库 判断用户和密码
                int count = userDao.selectByUnameAndPwd(name, password);
                if(count == 1){
                    // 数据库中存在，登录成功
                    flag = true;
                }
            }
        }
        return flag;
    }
    public static void main(String[] args) {
        new LoginGUI();
    }
}