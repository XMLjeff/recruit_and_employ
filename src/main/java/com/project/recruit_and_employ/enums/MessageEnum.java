package com.project.recruit_and_employ.enums;


import com.project.recruit_and_employ.constant.Constant;
import com.project.recruit_and_employ.constant.UserConstant;

/**
 * @author ：xmljeff
 * @date ：Created in 2022/1/16 17:16
 * @description：返回的提示内容
 * @modified By：
 * @version: $
 */
public enum MessageEnum {

    OK(1000, "成功"),
    ACCOUNT_PASSWORD_WRONG(1001, "账号或密码错误"),
    USER_EXIST(1002, "用户已存在"),
    USER_NOT_EXIST(1003, "用户不存在，请先注册"),
    PASSWORD_CANT_NULL(1008, "密码不能为空"),
    USERNAME_NOT_EXIST(1009, "用户不存在"),
    DEFAULT_PASSWORD(1010, "重置成功,重置之后的密码是" + UserConstant.DEFAULT_PASSWORD),
    FILE_FAIL(1011, "文件上传失败"),
    ROLE_ERROR(1012, "角色错误"),
    WRITE_INFO(1013, "请先填写求职信息，并上传简历"),


    ERROR(2000, "内部服务器错误。");

    private final String msg;
    private final Integer code;

    MessageEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public Integer getCode() {
        return this.code;
    }
}
