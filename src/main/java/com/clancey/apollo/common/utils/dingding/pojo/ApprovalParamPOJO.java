package com.clancey.apollo.common.utils.dingding.pojo;

import java.util.ArrayList;

public class ApprovalParamPOJO {

	/**
	 * 审批实例唯一编码
	 */
	private String approvalProcessCode;

	/**
	 * 发起审批人用户id
	 */
	private String sendUserId;

	/**
	 * 发起审批人部门id
	 */
	private String sendDeptId;

	/**
	 * 接收人集合
	 */
	private ArrayList<String> recUserId;

	/**
	 * 是否限制审批次数 false不限制 true限制
	 */
	private boolean limitCount;

	/**
	 * 限制次数因子
	 */
	private int capacity;

	/**
	 * 抄送人
	 */
	private ArrayList<String> ccList;

	/**
	 * 抄送时间
	 */
	private String CcPosition;

	/**
	 * 是否限制重复提交(提交过于频繁) false限制 true不限制
	 */
	private boolean resubmit;

	public ArrayList<String> getCcList() {
		return ccList;
	}

	public void setCcList(ArrayList<String> ccList) {
		this.ccList = ccList;
	}

	public void setRecUserId(ArrayList<String> recUserId) {
		this.recUserId = recUserId;
	}

	public String getCcPosition() {
		return CcPosition;
	}

	public void setCcPosition(String ccPosition) {
		CcPosition = ccPosition;
	}

	public String getApprovalProcessCode() {
		return approvalProcessCode;
	}

	public void setApprovalProcessCode(String approvalProcessCode) {
		this.approvalProcessCode = approvalProcessCode;
	}

	public boolean isLimitCount() {
		return limitCount;
	}

	public void setLimitCount(boolean limitCount) {
		this.limitCount = limitCount;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public boolean isResubmit() {
		return resubmit;
	}

	public void setResubmit(boolean resubmit) {
		this.resubmit = resubmit;
	}

	public String getSendUserId() {
		return sendUserId;
	}

	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	public String getSendDeptId() {
		return sendDeptId;
	}

	public void setSendDeptId(String sendDeptId) {
		this.sendDeptId = sendDeptId;
	}

	public ArrayList<String> getRecUserId() {
		return recUserId;
	}

}
