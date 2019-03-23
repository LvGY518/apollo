package com.clancey.apollo.common.utils.dingding;

public class ApprovalEvent {



	/**可以监听的所有事件
	 * user_add_org : 通讯录用户增加
	user_modify_org : 通讯录用户更改
	user_leave_org : 通讯录用户离职
	org_admin_add ：通讯录用户被设为管理员
	org_admin_remove ：通讯录用户被取消设置管理员
	org_dept_create ： 通讯录企业部门创建
	org_dept_modify ： 通讯录企业部门修改
	org_dept_remove ： 通讯录企业部门删除
	org_remove ： 企业被解散
	org_change ： 企业信息发生变更
	label_user_change ： 员工角色信息发生变更
	label_conf_add：增加角色或者角色组
	label_conf_del：删除角色或者角色组
	label_conf_modify：修改角色或者角色组
	bpms_task_change 审批任务状态改变
	bpms_instance_change 审批实例状态改变
	*
	**/



	 public static String USER_ADD_ORG = "user_add_org";
	 public static String USER_MODIFY_ORG = "user_modify_org";
	 public static String USER_LEAVE_ORG = "user_leave_org";
	 public static String ORG_DEPT_CREATE = "org_dept_create";
	 public static String ORG_DEPT_MODIFY = "org_dept_modify";
	 public static String ORG_DEPT_REMOVE = "org_dept_remove";
	 public static String LABEL_USER_CHANGE = "label_user_change";
	 public static String LABEL_CONF_ADD = "label_conf_add";
	 public static String LABEL_CONF_DEL = "label_conf_del";
	 public static String LABEL_CONF_MODIFY = "label_conf_modify";
	 public static String ORG_ADMIN_ADD = "org_admin_add";
	 public static String ORG_ADMIN_REMOVE = "org_admin_remove";
	 public static String ORG_REMOVE = "org_remove";
	 public static String ORG_CHANGE = "org_change";
	 public static String BPMS_TASK_CHANGE = "bpms_task_change";
	 public static String BPMS_INSTANCE_CHANGE = "bpms_instance_change";

}
