package com.sunvsoft.entity;

/**    
 * 类描述： 项目中除订单状态，支付状态，配送状态外的各种常量
 * 创建人：limengnan
 * 创建时间：2016-3-4 下午2:33:20    
 * 修改人：sks    
 * 修改时间：2016-3-4 下午2:33:20    
 * 修改备注：       
 */
public abstract class DictStatus {
    //创建常量复制下面代码，自行更改数据类型，常量名和值,并加上注释
    //public static final int ORDER_CHANGED = 116;
    
    
    //退货状态
    public static final int CHECK_PENDING = 100;//待审核
    public static final int PASSED = 101;//已通过
    public static final int HAVE_RETURN = 102;//已退货
    public static final int REFUNDED = 103;//已退款
    public static final int EXAMINATION_NOTPASSED = 104;//审核不通过
    //支付方式
    public static final int PAYMENT_NAME = 100;//首信易支付
    
    //商品翻译
    public static final int TRANSLATION_NO = 100;//未翻译
    public static final int TRANSLATION_YES = 101;//已翻译
    
    //仓库状态
    public static final int WAREHOUSE_DISPLAY = 100;//正常显示
    public static final int WAREHOUSE_HIDE = 101;//回收站
    //仓库类型
    public static final int WAREHOUSE_PHYSICALSTORE = 101;//实体店仓库
    public static final int WAREHOUSE_LINE = 102;//线上发货仓
    public static final int WAREHOUSE_RETURN = 105;//退货仓库
    //仓库启用状态
    public static final int WAREHOUSE_USEABLED =  100 ;//未启用
    public static final int WAREHOUSE_OPEN = 101 ;//启用
    
    //会员角色
    public static final int DOMESTIC_CONSUMER = 100;//普通用户
    public static final int GUIDE = 200;//导游
    
    //审核状态
    public static final int OPEN = 100;//开启
    public static final int CLOSE = 101;//关闭
    public static final int PENDING = 102;//待审核
    public static final int NOT_PASS = 103;//审核未通过
    
    //店铺归属
    public static final int INDEPENDENT_SHOPS = 100;//独立店铺
    public static final int QINGYUN_MALL = 101;//青云商城
    //库存类型
    public static final int STORAGE_LIBRARY = 101;//出库
    public static final int STORAGE_STORAGE = 102;//入库
    public static final int STORAGE_FROZEN = 103;//冻结
    public static final int STORAGE_THAW = 104;//解冻
    public static final int STORAGE_ONLINEDELIVERY = 105;//线上发货
    public static final int STORAGE_WAREHOUSING = 106;//退货入库
    public static final int STORAGE_ONTHESHELVES = 107;//上架出库
    public static final int STORAGE_SHELFSTORAGE = 108;//下架入库
    public static final int STORAGE_SHELVESSTORAGE = 109;//上架入库
    public static final int STORAGE_OFFLINEOUTLIBRARY = 110;//下架出库
    public static final int STORAGE_PHYSICALSALES = 111;//实体店销售
    public static final int STORAGE_PHYSICALRETURN = 112;//实体店退货
    //会员卡
    public static final int MEMBERCARD_STATUS_NOMAL = 100;//会员卡状态正常
    public static final int MEMBERCARD_STATUS_NOT = 101;//会员卡未绑定
    public static final int MEMBERCARD_STATUS_GUASHI = 102;//会员卡挂失
    public static final int MEMBERCARD_TYPESID = 100;//身份证
    public static final int MEMBERCARD_TYPEGID = 101;//港台身份证
    public static final int MEMBERCARD_TYPEHID = 102;//护照
    public static final int MEMBERCARD_TYPEOTHER = 103;//其他
    
    //供应订单状态
    public static final int SUPORDER_PENDINGCONFIRMATION = 100;//待确认
    public static final int SUPORDER_PENDINGPAYMENT  = 110;//待付款
    public static final int SUPORDER_PAIDDEPOSIT = 120;//已付定金
    public static final int SUPORDER_COMBINEDSINGLE = 130;//已合单
    public static final int SUPORDER_END = 140;//已完成
    
    
    //供应订单出库状态
    public static final int SUPORDER_NOTPUTINSTORAGE = 100;//未出库
    public static final int SUPORDER_PARTIALSTORAGE = 101;//部分出库
    public static final int SUPORDER_ALLWAREHOUSING = 102;//全部出库
    //中央仓库type属性
    public static final int CENTERWAREHOUSE_STORE = 101;//实体店仓库
    public static final int CENTERWAREHOUSE_ONLINE = 102;//线上发货仓
    public static final int CENTERWAREHOUSE_RETURN = 105;//退货仓库
    public static final int CENTERWAREHOUSE_BONDED = 106;//保税仓
    //所有仓库属性
    public static final int STORE_CENTERWAREHOUSE = 101;//中央实体店仓库
    public static final int ONLINE_CENTERWAREHOUSE = 102;//中央线上发货仓
    public static final int STORE_WAREHOUSE = 103;//实体店仓库非中央
    public static final int RETURN_CENTERWAREHOUSE = 105;//中央退货仓库
    public static final int BONDED_CENTERWAREHOUSE = 106;//中央保税仓
    
    //出库订单状态
    public static final int PUTOUT_WAREHOUSE=100;//已出库
    public static final int PUTIN_WAREHOUSE=101;//已入库
    public static final int NOTPUTOUT_WAREHOUSE=102;//未出库
    //出库订单类型
    public static final int SUPPLY_INVEBTORY =100;//供应单出入库
    public static final int STOCK_ALLOCATION =101;//库存调拨
}
