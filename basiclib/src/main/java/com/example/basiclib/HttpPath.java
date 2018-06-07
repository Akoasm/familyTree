package com.example.basiclib;

/**
 * create by: wxc.
 * date:On 2018/6/5
 */
public class HttpPath {

    public static final String BASEHOST="http://192.168.3.226:3000/";


    /**用户注册*/
    public static final String REGISTER=BASEHOST+"users/register";
    /**密码登录*/
    public static final String LOGIN_PWD=BASEHOST+"users/login";
    /**验证码登录*/
    public static final String LOGIN_CODE=BASEHOST+"users/codelogin";
    /**获取验证码*/
    public static final String GET_CODE=BASEHOST+"users/code";
    /**忘记密码*/
    public static final String FORGET_PASSWORD=BASEHOST+"users/retrieve";
    /**修改用户信息*/
    public static final String UPDATE_PERSONALDATA=BASEHOST+"users/updateinformation";
}
