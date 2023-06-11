package com.xxxy.studentmanagement.bean;

public class Administrator {
    private Integer userId;                 // 用户唯一标识
    private String username;                // 用户账号
    private String password;                // 用户密码
    private String telephone;               // 用户手机号

    public Administrator(Integer userId, String username, String password, String telephone) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.telephone = telephone;
    }

    public Administrator() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}

