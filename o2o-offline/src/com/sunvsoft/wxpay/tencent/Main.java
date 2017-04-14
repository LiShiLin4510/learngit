package com.sunvsoft.wxpay.tencent;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.sunvsoft.backup.PropertiesUtil;
import com.sunvsoft.wxpay.gd.pay.tencent.listener.ScanPayResultListener;
import com.sunvsoft.wxpay.gd.pos.PacketDef;
import com.sunvsoft.wxpay.tencent.common.Util;
import com.sunvsoft.wxpay.tencent.protocol.pay_protocol.ScanPayReqData;

public class Main {

	static SimpleDateFormat sld = new SimpleDateFormat("yyyyMMddHHmmss");

	public static void main(String[] args) {

		try {

			// --------------------------------------------------------------------
			// 温馨提示，第一次使用该SDK时请到com.tencent.common.Configure类里面进行配置
			// --------------------------------------------------------------------

			// --------------------------------------------------------------------
			// PART One:基础组件测试
			// --------------------------------------------------------------------

			// 1）https请求可用性测试
			// HTTPSPostRquestWithCert.test();

			// 2）测试项目用到的XStream组件，本项目利用这个组件将Java对象转换成XML数据Post给API
			// XStreamTest.test();

			// --------------------------------------------------------------------
			// PART Two:基础服务测试
			// --------------------------------------------------------------------

			// 1）测试被扫支付API
			// PayServiceTest.test();
			String authCode = "130167555752175478";
			int amount = 1;
			String deviceInfo = PropertiesUtil.getStoreId();
			String goodsInfo = "微信测试支付";
//			PacketDef result = scanPay(authCode, amount, goodsInfo, deviceInfo);
			String result = scanPay(authCode, amount, goodsInfo, deviceInfo);
			System.out.println("result.returnCode ======== " + result);
//			System.out.println("result.returnMsg ======== " + result.returnMsg);

			// 2）测试被扫订单查询API
			// PayQueryServiceTest.test();

			// 3）测试撤销API
			// 温馨提示，测试支付API成功扣到钱之后，可以通过调用PayQueryServiceTest.test()，将支付成功返回的transaction_id和out_trade_no数据贴进去，完成撤销工作，把钱退回来
			// ^_^v
			// ReverseServiceTest.test();

			// 4）测试退款申请API
			// RefundServiceTest.test();

			// 5）测试退款查询API
			// RefundQueryServiceTest.test();

			// 6）测试对账单API
			// DownloadBillServiceTest.test();

			// 本地通过xml进行API数据模拟的时候，先按需手动修改xml各个节点的值，然后通过以下方法对这个新的xml数据进行签名得到一串合法的签名，最后把这串签名放到这个xml里面的sign字段里，这样进行模拟的时候就可以通过签名验证了
			// Util.log(Signature.getSignFromResponseString(Util.getLocalXMLString("/test/com/tencent/business/refundqueryserviceresponsedata/refundquerysuccess2.xml")));

			// Util.log(new Date().getTime());
			// Util.log(System.currentTimeMillis());

		} catch (Exception e) {
			Util.log(e.getMessage());
		}

	}
	
	
	public static String scanPay(String authCode, int amount, String goodsInfo,
			String deviceInfo) {
		PacketDef er = new PacketDef();
		try {
			// 得到IP
			InetAddress ia = InetAddress.getLocalHost();
			String attach = "";
			String spBillCreateIP = ia.getHostAddress().toString();

			Calendar cal = Calendar.getInstance();
			sld = new SimpleDateFormat("yyyyMMddHHmmss");
			String timeStart = sld.format(cal.getTime());
			cal.add(Calendar.HOUR_OF_DAY, 2);
			String timeExpire = sld.format(cal.getTime());
			String goodsTag = "";

//			er.epsOrderNo = getOderNum(deviceInfo);
			er.epsOrderNo = deviceInfo;

			ScanPayReqData data = new ScanPayReqData(authCode, goodsInfo,
					attach, er.epsOrderNo, amount, deviceInfo, spBillCreateIP,
					timeStart, timeExpire, goodsTag);

			ScanPayResultListener listener = new ScanPayResultListener(er);
			WXPay.doScanPayBusiness(data, listener);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(er.isTrSuccess);
		return er.isTrSuccess;

	}

	/**
	 * 系统参考号
	 */
	@SuppressWarnings("unused")
    private static String getOderNum(String deviceInfo) {
		String outTradeNo = sld.format(new Date())
				+ ((long) (Math.random() * 1000) + 1000);
		return deviceInfo + outTradeNo;
	}

}
