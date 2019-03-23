package com.clancey.apollo.common.utils.dingding;

public class ApprovalRecordUtil {

    public static final String APPROVALSTATE_CHANGE_PRICE  = "CHANGE_PRICE";//价格变更审批
    public static final String APPROVALSTATE_ADD_PRODUCT  = "ADD_PRODUCT";//新增商品价格审批
    public static final String APPROVALSTATE_ONLINE_PRICE  = "ONLINE_PRICE";//在线报价审批
    public static final String APPROVALSTATE_CLIENT_COMPACT  = "CLIENT_COMPACT";//客户合同审批
    public static final String APPROVALSTATE_CREDIT_INVESTIGATION  = "CREDIT_INVESTIGATION";//客户征信调查审批
    public static final String SUPPLIER_INSPECTION  = "SUPPLIER_INSPECTION";//供应商验厂
    public static final String PURCHASE_BAG_QC  = "PURCHASE_BAG_QC";//采购单质检

    // 打样 记录类型
    public static final String  PROOFING_MANAGE_ALLOCATE_ZERO = "PROOFING_MANAGE_ALLOCATE_ZERO"; //打样操作端设计师接单操作  接单 待抢单-打样中

    public static final String  PROOFING_MANAGE_ALLOCATE_ONE = "PROOFING_MANAGE_ALLOCATE_ONE"; //打样管理端分配操作  分配 待抢单-待领取

    public static final String  PROOFING_MANAGE_ALLOCATE_TWO = "PROOFING_MANAGE_ALLOCATE_TWO"; //打样管理端分配操作  拒绝 待抢单-已驳回

    public static final String  PROOFING_MANAGE_ALLOCATE_THREE = "PROOFING_MANAGE_ALLOCATE_THREE"; //打样管理端分配操作  移交 待抢单-已移交

    public static final String  PROOFING_MANAGE_APPROVE_ONE = "PROOFING_MANAGE_APPROVE_ONE"; //打样管理端审批确认操作  确认 待审批-同意退回

    public static final String  PROOFING_MANAGE_APPROVE_TWO = "PROOFING_MANAGE_APPROVE_TWO"; //打样管理端审批拒绝操作  拒绝 待审批-待领取

    public static final String  PROOFING_MANAGE_APPROVE_THREE = "PROOFING_MANAGE_APPROVE_THREE"; //打样操作端设计师领取操作  领取 待领取-打样中

    public static final String  PROOFING_OPTION_RECEIVE_ONE = "PROOFING_OPTION_RECEIVE_ONE"; //打样操作端设计师确认操作  确认 已拒绝-打样中

    public static final String  PROOFING_OPTION_RECEIVE_TWO = "PROOFING_OPTION_RECEIVE_TWO";  //打样操作端设计师拒单操作  拒绝 待抢单-待审批

    public static final String  PROOFING_OPTION_PROOFING_ONE = "PROOFING_OPTION_PROOFING_ONE"; //上传结构图,结构样,印刷样  打样中-待确认

    public static final String  PROOFING_OPTION_PROOFING_TWO = "PROOFING_OPTION_PROOFING_TWO"; //上传工厂图,工厂样 打样中-待审核

    public static final String  PROOFING_OPTION_FACTORY_AUDIT_ONE = "PROOFING_OPTION_FACTORY_AUDIT_ONE"; //蔡蕾  工厂图 审批操作 通过 待审核-待确认

    public static final String  PROOFING_OPTION_FACTORY_AUDIT_THREE = "PROOFING_OPTION_FACTORY_AUDIT_THREE"; //蔡蕾  工厂样  审批操作 通过 待审核-已审核

    public static final String  PROOFING_OPTION_FACTORY_AUDIT_TWO = "PROOFING_OPTION_FACTORY_AUDIT_TWO";  //蔡蕾  工厂样 图 审批操作  拒绝 待审核-已拒绝

    public static final String  PROOFING_OPTION_FACTORY_AUDIT_FOUR = "PROOFING_OPTION_FACTORY_AUDIT_FOUR"; //蔡蕾  工厂样  审批操作 确认上传 已审核-待确认

    public static final String  PROOFING_OPTION_FACTORY_AUDIT_FIVE = "PROOFING_OPTION_FACTORY_AUDIT_FIVE"; //打样管理端最终确认操作 待确认-已完成

    public static final String  PROOFING_OPTION_FACTORY_AUDIT_SIX = "PROOFING_OPTION_FACTORY_AUDIT_SIX"; //打样管理端最终驳回操作 待确认-已拒绝



    //合同类型
}
