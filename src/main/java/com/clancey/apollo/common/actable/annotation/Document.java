package com.clancey.apollo.common.actable.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zzy
 * 自动生成markDown文档形式
 *
 */
@Retention(RetentionPolicy.RUNTIME)
//字段上为字段描述,类上为表格描述
@Target({ElementType.FIELD,ElementType.TYPE})
@Inherited
public @interface Document {

	/**
	 * @return String
	 * @author ZhengZongYuan
	 *  描述
	 */
	public String description()default "";

	/**
	 * @return String
	 * @author ZhengZongYuan
	 * 生成字段类型
	 */
	public String type()default "String";

	/**
	 * @return String
	 * @author ZhengZongYuan
	 * 字段作为json键,请不要使用default作为键
	 */
	public String jsonKey()default "default";


	/**
	 * @return boolean
	 * @author ZhengZongYuan
	 * 是否必填,默认写该属性为必填
	 */
	public boolean required()default true;

	/**
	 * @return String
	 * @author ZhengZongYuan
	 * json数据中的示例值
	 */
	public String example()default "";
}
