package com.clancey.apollo.common.constant;

public enum FileTypeEnum {

	AI("AI","AI文件"),PDF("PDF","PDF文件"),IMG("IMG","图片"),TEXT("TEXT","文本"),NORMAL("NORMAL","文件");

    // 定义私有变量

    private String code;

	private String description;

    // 构造函数，枚举类型只能为私有

	public String code() {
		return code;
	}

	public String description() {
		return description;
	}

    private FileTypeEnum(String _nCode,String desc) {

        this.code = _nCode;
        this.description = desc;

    }


}
