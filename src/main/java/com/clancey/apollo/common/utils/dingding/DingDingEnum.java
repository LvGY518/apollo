package com.clancey.apollo.common.utils.dingding;

public enum DingDingEnum {

    SENDMSG_81("81", "采购订单重新分配");

	private String value;

	private String description;

	private DingDingEnum(String value, String description) {

		this.value = value;

		this.description = description;

	}

	public String value() {
		return value;
	}

	public String description() {
		return description;
	}

	public static DingDingEnum valueOfs(String value) {
		if (value != null) {
			for (DingDingEnum type : DingDingEnum.values()) {
				if (type.value().equals(value)) {
					return type;
				}
			}
		}
		return null;
	}
}
