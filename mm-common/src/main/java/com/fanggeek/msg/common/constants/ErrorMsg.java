package com.fanggeek.msg.common.constants;

/**
 * 错误信息
 * @Author: AndrewYan
 * @Date: 2019/4/29 14:58
 */
public enum ErrorMsg implements Enums {
    MSG_SUCCESS(200, "操作成功"),
    MSG_ERROR_REQ_PARAMS(10001, "提交数据格式错误, 请确认后重试!"),





    MSG_UNKNOWN_ERR(100000,   "服务器错误, 请稍候重试!");

    private int     code;
    private String  errmsg;

    private ErrorMsg(int code, String errmsg) {
        this.code = code;
        this.errmsg = errmsg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return errmsg;
    }

    /**
     * 根据枚举码获取枚举
     * @param code 枚举码
     * @return 枚举
     */
    public static final ErrorMsg getByCode(int code) {
        for (ErrorMsg item : ErrorMsg.values()) {
            if (item.getCode() == code) {
                return item;
            }
        }
        return MSG_UNKNOWN_ERR;
    }
}
