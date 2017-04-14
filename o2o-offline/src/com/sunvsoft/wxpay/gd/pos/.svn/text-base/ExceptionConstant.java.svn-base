package com.sunvsoft.wxpay.gd.pos;

public enum ExceptionConstant {

	SUCCESS("00", "00交易成功"),
	TIME_OUT("E0", "E0链接超时"), 
	TERMINAL_NOT_EXIST("01","01无此终端"), 
	SHOPCLOCK_NOT_EXIST("02", "02无班次信息"), 
	NOTENOUGH("03", "03余额不足"), 
	TRADE_NOT_EXIST("04", "04无此交易"), 
	AUTH_CODE_ERROR("05", "05授权码参数错误(每个二维码仅限使用一次，请刷新再试)"), 
	AUTH_CODE_INVALID("06", "06授权码检验错误(请扫描微信支付被扫条码/二维码)"), 
	CHECKING_EQ("EQ","EQ账平"),
	CHECKING_NQ("NQ","NQ账不平"),
	EPS_NOT_EXIST("07","07EPS未注册"),
	AUTHCODEEXPIRE("08","08被扫条码/二维码过期"),
	INVALID_TRANSACTIONID("09","09非法的订单号"),
	AUTHCODE_NOT_EXISt("10","10授权码为空"),
	TERMUM_NOT_EXISt("11","11终端编号为空"),
	TRACENO_NOT_EXISt("12","12流水号为空"),
	AMOUNT_NOT_EXISt("13","13金额为空"),
	ORGANIZATION_NOT_EXISt("14","14加油站信息错误"),
	AUTHORIZATIONCODE_ERRO("15","15EPS授权码信息有误"),
	TRADE_HAS_REVOKE("16","16支付超时，订单已撤销"),
	WX_NETWORK_ERRO("E1","E1微信网络异常"),
	PPS_NETWORK_ERRO("E2","E2PPS网络异常"),
	EPS_NETWORK_ERRO("E3","E3EPS网络异常"),
	WX_CANCEL_ERRO("E4","E4冲正失败"),
	WX_CANCEL_SUCCESS("E5","支付超时订单已撤销或冲正成功(请注意查看顾客微信支付凭证)"),
	WX_DATA_FORMAT_ERROR("E5","E5数据格式错误"),
	WX_HAS_CANCELLED("E6","E6交易已被冲正过"),
	WX_SYSTEMERROR("E7","E7你的操作过于频繁，请稍后再试"),
	SYSTEM_ERROR("99", "99系统错（未知错）"),
	WX_PPS_REVOKE("17","交易异常已被系统撤销"),
	NETWORK_ERRO("E9","网络异常"),
	TIMEOUT_QUERY_FAIL("E8","超时查询失败"),
	PAY_FAIL("FL","支付失败");

	ExceptionConstant(String code, String text) {
		this.code = code;
		this.text = text;
	}

	private String code;
	private String text;

	public String getCode() {
		return code;
	}

	public String getText() {
		return text;
	}

	public static String getText(String code) {
		for (ExceptionConstant c : ExceptionConstant.values()) {
			if (code.equals(c.getCode())) {
				return c.getText();
			}
		}
		return null;
	}

}
