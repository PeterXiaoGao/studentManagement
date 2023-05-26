package com.xxxy.studentmanager.utils;

import java.util.Random;

/**
 * 生成验证码
 */
public class YzmUtil {

    // 不让创建对象
    private YzmUtil(){
    }

    private static Random rd = new Random();
    public static String getYzm(){
        char y1 = (char)(rd.nextInt(26) + 'A');
        int y2 = rd.nextInt(10);
        char y3 = (char)(rd.nextInt(26) + 'a');
        int y4 = rd.nextInt(10);
        String result = String.valueOf(y2) + String.valueOf(y1)+ String.valueOf(y3) + String.valueOf(y4);

        return result;
    }
}
