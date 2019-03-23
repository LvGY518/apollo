package com.clancey.apollo.common.exception;

public class DingDingApprovalException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -5522964149013408998L;


	private String msg;

	public DingDingApprovalException() {
		super();
	}

	public DingDingApprovalException(String msg) {
		super();
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
