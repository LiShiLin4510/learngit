package com.sunvsoft.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单状态
 * 
 * @author apexking
 * 
 */
public abstract class OrderStatus {

	/**
	 * ------------------------------------------------------------- 订单状态
	 * -------------------------------------------------------------
	 */
	public static final int ORDER_CHANGED = 116;// 已换货
	public static final int ORDER_CHANGE_APPLY = 115;// 申请换货
	public static final int ORDER_RETURN_APPLY = 114; // 申请退货
	public static final int ORDER_CANCEL_SHIP = 113; // 退货
	public static final int ORDER_CANCEL_PAY = 112; // 退款
	public static final int ORDER_NOT_PAY = 100; // 未付款/新订单 修改为已确认
	public static final int ORDER_PAY = 101; // 已支付待确认
	public static final int ORDER_PAY_CONFIRM = 102; // 已确认支付
	public static final int ORDER_SHIP = 105; // 已发货
	public static final int ORDER_ROG = 106; // 已收货
	public static final int ORDER_COMPLETE = 107; // 已完成
	public static final int ORDER_CANCELLATION = 108; // 作废
	public static final int ORDER_HAS_PAY = 111; // 已放款
	public static final int ORDER_DISPUTE = 110; // 纠纷订单

	// 以下为暂停使用的订单状态
	public static final int ORDER_ALLOCATION_YES = 104; // 配货完成，待发货
	public static final int ORDER_NOT_CONFIRM = 109; // 订单已生效
	public static final int ORDER_ALLOCATION = 103; // 配货中
	public static final int ORDER_CHANGE_REFUSE = 117;// 换货被拒绝
	public static final int ORDER_RETURN_REFUSE = 118;// 退货被拒绝

	/**
	 * ------------------------------------------------------------- 付款状态
	 * -------------------------------------------------------------
	 */
	public static final int PAY_NO = 100; // 未付款
	public static final int PAY_YES = 101; // 已付款待确认
	public static final int PAY_CONFIRM = 102; // 已确认付款
	public static final int PAY_CANCEL = 103; // 已退款
	public static final int PAY_PARTIAL_REFUND = 104; // 部分退款
	public static final int PAY_PARTIAL_PAYED = 105;// 部分付款

	/**
	 * ------------------------------------------------------------- 货运状态
	 * -------------------------------------------------------------
	 */
	public static final int SHIP_ALLOCATION_NO = 100; // 0未配货
	public static final int SHIP_ALLOCATION_YES = 101; // 1配货中
	public static final int SHIP_NO = 102; // 0未发货 （配货已完成）
	public static final int SHIP_YES = 103;// 1已发货
	public static final int SHIP_CANCEL = 104;// 2.已退货
	public static final int SHIP_PARTIAL_SHIPED = 105; // 4 部分发货
	public static final int SHIP_PARTIAL_CANCEL = 106;// 3 部分退货
	public static final int SHIP_PARTIAL_CHANGE = 107;// 5部分换货
	public static final int SHIP_CHANED = 108;// 6已换货
	public static final int SHIP_ROG = 109;// 9已收货
	public static final int TAX_SYSTEM = 100; // 保税
	public static final int TAX_SYSTEM_COM = 101; //完税
	public static final int TAX_SYSTEM_POST = 103; //直邮
	public static final int TAX_SYSTEM_ALL = 102; //全部
	public static final int WEI_PAYMENT_ID = 4; //微信支付
	public static final int ALI_PAYMENT_ID = 5; //支付宝支付
	public static final int CASH_PAYMENT_ID = 6; //现金
	public static final int POS_PAYMENT_ID = 7; //刷卡
	public static final int PSOANDCASH_PAYMENT_ID = 8; //混合
	

	/**
	 * 获取订单状态值 map key为状态变量名字串 value为状态值
	 * 
	 * @return
	 */
	public static Map<String, Object> getOrderStatusMap() {
		Map<String, Object> map = new HashMap<String, Object>(17);

		// 订单状态
		map.put("ORDER_CHANGED", OrderStatus.ORDER_CHANGED);
		map.put("ORDER_CHANGE_REFUSE", OrderStatus.ORDER_CHANGE_REFUSE);
		map.put("ORDER_RETURN_REFUSE", OrderStatus.ORDER_RETURN_REFUSE);
		map.put("ORDER_CHANGE_APPLY", OrderStatus.ORDER_CHANGE_APPLY);
		map.put("ORDER_RETURN_APPLY", OrderStatus.ORDER_RETURN_APPLY);
		map.put("ORDER_CANCEL_SHIP", OrderStatus.ORDER_CANCEL_SHIP);
		map.put("ORDER_CANCEL_PAY", OrderStatus.ORDER_CANCEL_PAY);
		map.put("ORDER_NOT_PAY", OrderStatus.ORDER_NOT_PAY);
		map.put("ORDER_PAY", OrderStatus.ORDER_PAY);
		map.put("ORDER_PAY_CONFIRM", OrderStatus.ORDER_PAY_CONFIRM);
		map.put("ORDER_ALLOCATION", OrderStatus.ORDER_ALLOCATION);
		map.put("ORDER_ALLOCATION_YES", OrderStatus.ORDER_ALLOCATION_YES);
		map.put("ORDER_SHIP", OrderStatus.ORDER_SHIP);
		map.put("ORDER_ROG", OrderStatus.ORDER_ROG);
		map.put("ORDER_COMPLETE", OrderStatus.ORDER_COMPLETE);
		map.put("ORDER_CANCELLATION", OrderStatus.ORDER_CANCELLATION);
		map.put("ORDER_NOT_CONFIRM", OrderStatus.ORDER_NOT_CONFIRM);
		map.put("ORDER_HAS_PAY", OrderStatus.ORDER_HAS_PAY);
		map.put("ORDER_DISPUTE", OrderStatus.ORDER_DISPUTE);

		// 付款状态
		map.put("PAY_NO", PAY_NO);
		map.put("PAY_YES", PAY_YES);
		map.put("PAY_CONFIRM", PAY_CONFIRM);
		map.put("PAY_CANCEL", PAY_CANCEL);
		map.put("PAY_PARTIAL_REFUND", PAY_PARTIAL_REFUND);
		map.put("PAY_PARTIAL_PAYED", PAY_PARTIAL_PAYED);

		// 货运状态
		map.put("SHIP_ALLOCATION_NO", SHIP_ALLOCATION_NO);
		map.put("SHIP_ALLOCATION_YES", SHIP_ALLOCATION_YES);
		map.put("SHIP_NO", SHIP_NO);
		map.put("SHIP_YES", SHIP_YES);
		map.put("SHIP_CANCEL", SHIP_CANCEL);
		map.put("SHIP_PARTIAL_SHIPED", SHIP_PARTIAL_SHIPED);
		map.put("SHIP_PARTIAL_CANCEL", SHIP_YES);
		map.put("SHIP_PARTIAL_CHANGE", SHIP_CANCEL);
		map.put("SHIP_CHANED", SHIP_CHANED);
		map.put("SHIP_ROG", SHIP_ROG);
		map.put("TAX_SYSTEM", TAX_SYSTEM);
		map.put("TAX_SYSYTEM_COM", TAX_SYSTEM_COM);

		return map;
	}

	/**
	 * 获取订单状态
	 * 
	 * @param status
	 * @return
	 */
	public static String getOrderStatusText(int status) {
		String text = "";

		switch (status) {
		case ORDER_CHANGED:
			text = "已换货";
			break;
		case ORDER_CHANGE_REFUSE:
			text = "换货被拒绝";
			break;

		case ORDER_RETURN_REFUSE:
			text = "退货被拒绝";
			break;

		case ORDER_CHANGE_APPLY:
			text = "申请换货";
			break;

		case ORDER_RETURN_APPLY:
			text = "申请退货";
			break;
		case ORDER_CANCEL_SHIP:
			text = "退货";
			break;
		case ORDER_CANCEL_PAY:
			text = "退款";
			break;

		case ORDER_NOT_PAY:
			text = "待付款";
			// text = "已确认";
			break;

		case ORDER_NOT_CONFIRM:
			text = "订单已生效";
			break;

		case ORDER_PAY:
			text = "已付款待确认";
			break;

		case ORDER_PAY_CONFIRM:
			text = "已付款";
			break;

		case ORDER_ALLOCATION:
			text = "配货中";
			break;

		case ORDER_ALLOCATION_YES:
			text = "未发货";
			break;

		case ORDER_SHIP:
			text = "已发货";
			break;

		case ORDER_COMPLETE:
			text = "已完成";
			break;

		case ORDER_ROG:
			text = "已收货";
			break;

		case ORDER_CANCELLATION:
			text = "已取消";
			break;
			
		case ORDER_HAS_PAY:
			text = "已放款";
			break;
			
		case ORDER_DISPUTE:
			text = "纠纷订单";
			break;

		default:
			text = "错误状态";
			break;
		}
		return text;

	}

	/**
	 * 获取付款状态
	 * 
	 * @param status
	 * @return
	 */
	public static String getPayStatusText(int status) {
		String text = "";

		switch (status) {
		case PAY_NO:
			text = "未付款";
			break;
		case PAY_YES:
			text = "已付款待确认";
			break;
		case PAY_CONFIRM:
			text = "已确认付款";
			break;

		case PAY_CANCEL:
			text = "已经退款";
			break;
		case PAY_PARTIAL_REFUND:
			text = "部分退款";
			break;
		case PAY_PARTIAL_PAYED:
			text = "部分付款";
			break;
		default:
			text = "错误状态";
			break;
		}
		return text;
	}

	/**
	 * 获取货运状态
	 * 
	 * @param status
	 * @return
	 */
	public static String getShipStatusText(int status) {
		String text = "";

		switch (status) {
		case SHIP_ALLOCATION_NO:
			text = "未配货";
			break;
		case SHIP_ALLOCATION_YES:
			text = "配货中 ";
			break;

		case SHIP_NO:
			text = "未发货";
			break;

		case SHIP_YES:
			text = "已发货";
			break;

		case SHIP_CANCEL:
			text = "已退货";
			break;
		case SHIP_PARTIAL_SHIPED:
			text = "部分发货";
			break;
		case SHIP_PARTIAL_CANCEL:
			text = "部分退货";
			break;

		case SHIP_PARTIAL_CHANGE:
			text = "部分换货";
			break;

		case SHIP_CHANED:
			text = " 已换货";
			break;

		case SHIP_ROG:
			text = " 已收货";
			break;
			
		default:
			text = "错误状态";
			break;
		}

		return text;

	}
	
	/**        
	 * TODO       
	 * 获取商品税制
	 * @param   name    
	 * @return String   
	 * @Exception 异常对象       
	*/
	public static String getTaxText(int sts){
	    String text = "";
	    switch (sts) {
        case 100:
            text = "保税";
            break;
            
        case 101:
            text = "完税";
            break;

        case 102:
            text = "全部";
            break;
            
        case 103:
            text = "直邮";
            break;
        default:
            text = "全部";
            break;
        }
	    return text;
	}

}
