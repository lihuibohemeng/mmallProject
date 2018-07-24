package com.mmallshop.common;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created with IntelliJ IDEA.
 * User: 刘莉慧
 * Date: 2018/7/24
 * Time: 15:06
 * To change this template use File | Settings | File Templates.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public enum ResponseCode {

    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    NEED_LOGIN(10,"NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");


    private final int code;
    private final String desc;

    ResponseCode(int code,String desc){
        this.desc = desc;
        this.code = code;
    }

    public int getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }
}
