package com.fanggeek.msg.common.utils;

import com.fanggeek.msg.common.constants.Enums;
import com.fanggeek.msg.common.exception.BizException;

public class Exceptions {

    public static void throwss(boolean throwss, Enums enums) {
        Exceptions.throwss(throwss, enums.getCode(), enums.getDesc());
    }

    public static void throwss(boolean throwss, int code) {
        Exceptions.throwss(throwss, code, null);
    }

    public static void throwss(int code, String msg) {
        Exceptions.throwss(true, code, msg);
    }

    public static void throwss(Enums enums) {
        Exceptions.throwss(true, enums.getCode(), enums.getDesc());
    }

    public static void throwss(int code) {
        Exceptions.throwss(true, code, null);
    }

    public static void throwss(boolean throwss, int code, String msg) {
        if (throwss) {
            throw new BizException(code, msg);
        }
    }

}
