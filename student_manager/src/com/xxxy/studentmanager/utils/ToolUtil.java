package com.xxxy.studentmanager.utils;

public class ToolUtil {

    // 不让创建对象
    private ToolUtil(){
    }

    /**
     * 判断字符串是否为空串
     * @param str 字符串
     * @return true为空串 false 不是空串
     */
    public static boolean isEmpty(String str){
        if(str != null && !"".equals(str)){
            return false;
        }
        return true;
    }

    /**
     * 判断字符串是否为数字
     * @param str 字符串
     * @return true是  false不是
     */
    public static boolean isNumber(String str){
        boolean result = true;
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) > '9' || str.charAt(i) < '0'){
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 去除字符串的所有空格
     * @param str 字符串
     * @return 去除所有空白字符的字符串
     */
    public static String deleteSpace(String str){
        String result = str.replaceAll("[ ]+", "");

        return result;
    }

    /**
     * 判断字符串是否含有空格
     * @param str 字符串
     * @return 含有空格返回true  不含空格false
     */
    public static boolean hasSpace(String str){
        boolean result = str.contains(" ");
        return result;
    }

}
