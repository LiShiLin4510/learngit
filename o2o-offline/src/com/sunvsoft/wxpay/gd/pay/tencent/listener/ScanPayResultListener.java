package com.sunvsoft.wxpay.gd.pay.tencent.listener;

import com.sunvsoft.wxpay.gd.pos.ExceptionConstant;
import com.sunvsoft.wxpay.gd.pos.PacketDef;
import com.sunvsoft.wxpay.tencent.business.ScanPayBusiness.ResultListener;
import com.sunvsoft.wxpay.tencent.protocol.pay_protocol.ScanPayResData;

public class ScanPayResultListener implements ResultListener {
	PacketDef result = null;

	public ScanPayResultListener(PacketDef result) {
		System.err.println(result);
		this.result = result;
	}

	private void trError(ScanPayResData scanPayResData) {
		result.isTrSuccess = "n";
		String err_code = scanPayResData.getErr_code();
		String err_code_des = scanPayResData.getErr_code_des();
		result.realAmount = 0L;
		if (err_code != null && !err_code.trim().equals("")) {
			result.returnCode = scanPayResData.getErr_code();
		} else {
			result.returnCode = ExceptionConstant.PAY_FAIL.getCode();
		}
		if (err_code_des != null && !err_code_des.trim().equals("")) {
			result.returnMsg = scanPayResData.getErr_code_des();
		} else {
			result.returnMsg = ExceptionConstant.PAY_FAIL.getText();
		}
	}

	public void onFail(ScanPayResData scanPayResData) {
		trError(scanPayResData);
	}

	public void onFailByAuthCodeExpire(ScanPayResData scanPayResData) {
		trError(scanPayResData);
	}

	public void onFailByAuthCodeInvalid(ScanPayResData scanPayResData) {
		trError(scanPayResData);
	}

	public void onFailByMoneyNotEnough(ScanPayResData scanPayResData) {
		trError(scanPayResData);
	}

	public void onFailByReturnCodeError(ScanPayResData scanPayResData) {
		trError(scanPayResData);
	}

	public void onFailByReturnCodeFail(ScanPayResData scanPayResData) {
		trError(scanPayResData);
	}

	public void onFailBySignInvalid(ScanPayResData scanPayResData) {
		trError(scanPayResData);
	}

	public void onSuccess(ScanPayResData scanPayResData) {
		if(ExceptionConstant.TRADE_HAS_REVOKE.getCode().equals(scanPayResData.getErr_code())){
			trError(scanPayResData);
		}else{
			result.returnCode = "00";
			result.returnMsg = "交易成功";
			result.thirdOrderNo = scanPayResData.getTransaction_id();
			result.isTrSuccess = "y";
			String time_end = scanPayResData.getTime_end();
			result.thirdTransDate = time_end.substring(0, 8);
			result.thirdTransTime = time_end.substring(8, 14);
			result.openId = scanPayResData.getOpenid();
			result.realAmount = Long.valueOf(scanPayResData.getTotal_fee());
			result.coupon = scanPayResData.getCoupon_fee();
		}
		
	}

}
