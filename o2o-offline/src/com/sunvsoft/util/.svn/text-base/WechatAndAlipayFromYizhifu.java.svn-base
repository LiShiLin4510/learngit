package com.sunvsoft.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * 易智付反向扫码支付直接提交接口(微信、支付宝标准版)V1.1
 * 
 * @author liuhy 
 * @date 2016年12月19日 下午3:20:27
 */
public class WechatAndAlipayFromYizhifu {

	private static final String V_MID = "8819"; // 商户编号
	private static final String MD5_KEY = "test"; // md5 key
	
	// 反向扫码支付直接提交接口(微信、支付宝标准版)V1.1
//	private static final String OFFLINE_ORDER_URL = "https://pay.yizhifubj.com/customer/otherpay/reverseScanCodePay.jsp";
	private static final String OFFLINE_ORDER_URL =  "https://pay.yizhifubj.com/customer/otherpay/reverseScanCodePay.jsp";
	
	
	/**
	 * 反向扫码支付直接提交接口(微信、支付宝标准版)
	 * 
	 * @param order_sn 订单sn
	 * @param order_id 订单编号
	 * @param needPayMoney 订单总金额
	 * @param address 收货地址
	 * @param mobile 收货人电话
	 * @param goodsName 商品名称
	 * @param payType 支付方式编号,254 微信扫码;271 支付宝扫码
	 * @param payCode 支付宝/微信付款码,商户通过扫码枪获取持卡人的付款码信息
	 * @return 易智付响应结果
	 * @throws IOException
	 */
	public String scanCodePayStandard(String order_sn, String order_id,
			String needPayMoney, String address, String mobile, String goodsName, String payType, String payCode) throws IOException {
		Client client = Client.create();
		WebResource webResource = client.resource(OFFLINE_ORDER_URL);
		MultivaluedMap<String, String> queryParams = getParamsMap(order_sn, order_id, needPayMoney, address, mobile, goodsName, payType, payCode);
		String result = webResource.queryParams(queryParams).get(String.class);
		return result;
	}

	/******************************************************************************/
	
	/**
	 * 生成请求参数map
	 * 
	 * @param order
	 *            订单实体
	 * @return MultivaluedMap
	 * @throws IOException
	 */
	private static MultivaluedMap<String, String> getParamsMap(String order_sn, String order_id,
			String needPayMoney, String address, String mobile, String goodsName, String payType, String payCode) throws IOException {
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		
		SimpleDateFormat df1 = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
		String dateId1 = df1.format(new Date());// 获取当前日期

		// 具体参数参考接口文档
		String v_mid = V_MID; // 商户编号
		String v_oid = dateId1 + "-" + v_mid + "-" + order_sn; // 订单编号
		String v_rcvname = order_id; // 收货人姓名,传商户订单号
		String v_amount = needPayMoney; // 订单总金额
		String v_ymd = dateId1; // 订单产生日期
		String v_moneytype = "0"; // 支付币种, 0为人民币
		String v_url = "http://www.beijing.com.cn"; // 返回商户页面地址
													// 注意：因此接口非页面跳转方式，而是同步方式实时得到响应结果，因此不存在调用返回商
													// 户页面地址返回支付结果信息。此参数值不会实际使用，商户可置为固定值。
		String v_md5info = v_moneytype+v_ymd+v_amount+v_rcvname+v_oid+v_mid+v_url; // 订单数字指纹
		MD5Util md5 = new MD5Util("");
		md5.hmac_Md5(v_md5info, MD5_KEY); // 第一个参数是加密参数，第二个参数是加密密钥
		byte b[] = md5.getDigest();
		String digestString = MD5Util.stringify(b);
		
		queryParams.add("v_mid", v_mid);
		queryParams.add("v_oid", v_oid);
		queryParams.add("v_rcvname", v_rcvname);
		queryParams.add("v_rcvaddr", address); // 收货人地址
		queryParams.add("v_rcvtel", mobile); // 收货人电话
		queryParams.add("v_rcvpost", v_mid); // 收货人邮编,统一用商户编号的值代替
		queryParams.add("v_amount", v_amount);
		queryParams.add("v_ymd", v_ymd);
		queryParams.add("v_orderstatus", "1"); // 商户配货状态, 0为未配齐，1为已配齐， 统一配置为 1
		queryParams.add("v_ordername", v_mid); // 订货人姓名,统一用商户编号的值代替
		queryParams.add("v_moneytype", v_moneytype);
		queryParams.add("v_url", v_url);
		queryParams.add("v_md5info", digestString);
		queryParams.add("v_ordip", "127.0.0.1");
		queryParams.add("v_merdata5", URLEncoder.encode(goodsName, "UTF-8")); // 商品名称
		queryParams.add("v_pmode", payType); // 支付方式，可选值包括:254 微信扫码,271 支付宝扫码
		queryParams.add("v_paycode", payCode); // 支付宝/微信付款码,商户通过扫码枪获取持卡人的付款码信息
		return queryParams;
	}

}
