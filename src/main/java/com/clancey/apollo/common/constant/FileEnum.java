package com.clancey.apollo.common.constant;


public enum FileEnum {
	IDENTITY_CARD("2","IDENTITY CARD"), // 身份证
	CREDIT_INVESTIGATION("3","CREDIT_INVESTIGATION"), // 征信报告
    BRAND_AUTH("4","BRAND_AUTH"), // 商标授权书,自动生成
    BUSSINESS_LICENCE("5","BUSSINESS_LICENCE"), //营业执照扫描件
    CLIENT_COMPACT("6","CLIENT_COMPACT"), // 合同扫描件,上传
    CLIENT_COMPACT_AUTO("7","CLIENT_COMPACT_AUTO"), // 自动生成的合同
    TEXT_PAYER("8","TEXT_PAYER"),// 纳税人资质证明
	CUSTOMER_COMAPCT_UPLOAD("9","CUSTOMER_COMAPCT_UPLOAD"),//单笔采购合同客户上传文件
	STRUCT_SALE_PDF("10","STRUCT_SALE_PDF"),//销售结构设计上传PDF
	STRUCT_SALE_AI("11","STRUCT_SALE_AI"),//销售结构设计上传AI
	STRUCT_SALE_NORMAL("12","STRUCT_SALE_NORMAL"),//销售结构设计上传文件
	SALE_PROOFING_PDF("13","SALE_PROOFING_PDF"),//销售上传打样PDF
	SALE_PROOFING_NORMAL("14","SALE_PROOFING_NORMAL"),//销售上传的正常文件如图片
	SALE_PROOFING_AI("15","SALE_PROOFING_AI"),//销售上传的AI文件
	PROOFING_UPLOAD_PDF("16","PROOFING_UPLOAD_PDF"),//打样部门上传的PDF
	PROOFING_UPLOAD_AI("17","PROOFING_UPLOAD_AI"),//打样部门上传的AI
	PROOFING_UPLOAD_NORMAL("18","PROOFING_UPLOAD_NORMAL"),//打样部门上传的正常文件如图片
	VISION_SALE_PDF("19","VISION_SALE_PDF"),//销售视觉PDF
	VISION_SALE_AI("20","VISION_SALE_AI"),//销售视觉AI
	VISION_SALE_NORMAL("21","VISION_SALE_NORMAL"),//销售视觉正常文件
	VISION_NORMAL("22","VISION_NORMAL"),//视觉部门上传图片
	VISION_AI("23","VISION_AI"),//视觉部门上传AI图片
	VISION_PDF("24","VISION_PDF"),//视觉部门上传PDF图片
	COST_AUDIT_PDF("25","COST_AUDIT_PDF"),//费用审核PDF
	COST_AUDIT_AI("26","COST_AUDIT_AI"),//费用审核AI
	COST_AUDIT_NORMAL("27","COST_AUDIT_NORMAL"),//费用审核正常文件如图片
	QUALITY_STANDARD("28","QUALITY_STANDARD"),//质量标准上传
	DELIVERY_VOUCHER("29","DELIVERY_VOUCHER"),//送货单---送货凭证
	SINGLE_CONTRACT("30","SINGLE_CONTRACT"),//单笔采购合同 文件
	STANDARD_CONTRACT("31","STANDARD_CONTRACT"),//标准合同 文件
	PACK_SERVE_ORDER_STRUCT_DRAW("32","PACK_SERVE_ORDER_STRUCT_DRAW"),//包装服务单结构图
	PACK_SERVE_ORDER_STRUCT_SAMPLE("33","PACK_SERVE_ORDER_STRUCT_SAMPLE"),//包装服务单结构样
	PACK_SERVE_ORDER_GRAPHIC_DESIGN("34","PACK_SERVE_ORDER_GRAPHIC_DESIGN"),//包装服务单平面设计
	PACK_SERVE_ORDER_COLOR_PRINTING("35","PACK_SERVE_ORDER_COLOR_PRINTING"),//包装服务单彩印图
	PACK_SERVE_ORDER_NUMERICAL("36","PACK_SERVE_ORDER_NUMERICAL"),//包装服务单数码样
	PACK_SERVE_ORDER_FACTORY_DRAW("37","PACK_SERVE_ORDER_FACTORY_DRAW"),//包装服务单工厂图
	PACK_SERVE_ORDER_FACTORY_SAMPLE("38","PACK_SERVE_ORDER_FACTORY_SAMPLE"),//包装服务单工厂样
	PACK_SERVE_ORDER_TEXT_VIDEO("39","PACK_SERVE_ORDER_FACTORY_SAMPLE"),//包装服务测试视频
	PACK_SERVE_ORDER_SOP_VIDEO("40","PACK_SERVE_ORDER_FACTORY_SAMPLE"),//包装服务sop视频
	PACK_SERVE_ORDER_HEAP_VIDEO("41","PACK_SERVE_ORDER_FACTORY_SAMPLE"),//包装服务单堆码视频
	PACKAGING_FILE("42","PACKAGING_FILE"),//包装解决方案的文件
	PACKAGING_QUALITY_STANDARD("43","PACKAGING_QUALITY_STANDARD"),//包装质量标准PDF
	PACKAGING_QUALITY_STANDARD_PNG("44","PACKAGING_QUALITY_STANDARD_PNG"),//包装质量标准PNG
    SUPPLIER_01("45","SUPPLIER_01"),//供应商营业执照
    SUPPLIER_02("46","SUPPLIER_02"),//供应商税务登记证
    SUPPLIER_03("47","SUPPLIER_03"),//组织机构代码证
    SUPPLIER_04("48","SUPPLIER_04"),//征信报告预览
    SUPPLIER_05("49","SUPPLIER_05"),//开户行信息
    SUPPLIER_06("50","SUPPLIER_06"),//印刷许可证
    SUPPLIER_07("51","SUPPLIER_07");//环境影响报告

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

    private FileEnum(String _nCode,String desc) {

        this.code = _nCode;
        this.description = desc;

    }

    public static String valuesOf(FileEnum ele){
    	return ele.description;
    }

    public static String keysOf(FileEnum ele){
    	return ele.code;
    }

    public static void main(String[] args) {
		System.out.println(FileEnum.valuesOf(BRAND_AUTH));
		System.out.println(FileEnum.keysOf(BRAND_AUTH));
	}
}
