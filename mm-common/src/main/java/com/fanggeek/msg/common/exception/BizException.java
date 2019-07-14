package com.fanggeek.msg.common.exception;

import com.fanggeek.msg.common.constants.Enums;

public class BizException extends RuntimeException{
	
	private int		code;
	private String	msg;

	public BizException(int code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public BizException(int code) {
		super();
		this.code = code;
	}
	
	public BizException(Enums enumCode) {
		this(enumCode.getCode(), enumCode.getDesc());
	}


	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
