package com.sunvsoft.wxpay.gd.pos;

/**
 * 内部系统间调用参数及结果类
 * @author ThinkPad
 *
 */
public class PacketDef {

	public String transCode;//交易码
	public String workDate;//营业日期
	public String shiftNum;//班次
	public String posId;//posId
	public String traceNo;//pos流水号
	public String isTrSuccess = "";//交易是否成功 Y:交易成功 N:交易失败
	public String returnCode = "";//响应码
	public String returnMsg = "";//响应码说明
	public String cardNum = "";//银行卡号/微信授权码
	public String termNum = "  ";//终端号
	public String shopNum = "";//商户号
	public String epsOrderNo = "";//EPS订单号
	public String rrn;//系统参考号
	public String settlementDate = "";//清算日期
	public String cardBankCode = "";//发卡行代码
	public String cardBankName = "";//发卡行名称
	public String thirdOrderNo = "";//第三方订单号(微信、支付 宝)
	public String transDate = "";//local交易日期
	public String transTime = "";//local交易时间	
	public String thirdTransDate = "";//third交易日期
	public String thirdTransTime = "";//third交易时间		
	public String workspace = "";//支付对接公司（1-银联）、交易平台
	public String cardType = "";//卡类型(7 银联)
	public String bppcCode = "";//加油站编号
	public Long amount;//交易金额,以分为单位
	public Long realAmount;//交易实际金额,以分为单位
	public String mac = "";
	public String transName = "";
	
	public Long realAmount1;//交易实际金额,以分为单位
	
	public Integer totalNum;//总笔数
	public Long totalAmt;//总金额
	public Integer diffTotalNum;//差异总笔数
	public Long diffTotalAmt;//差异总金额
	
	public String openId;		//用户标识
	public String transRoute;	//交易路由	
	public String goods;		//商品
	public String buyerLogonId;
	public String coupon;		//代金券或立减优惠金额
	

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	public String getShiftNum() {
		return shiftNum;
	}

	public void setShiftNum(String shiftNum) {
		this.shiftNum = shiftNum;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public String getTraceNo() {
		return traceNo;
	}

	public void setTraceNo(String traceNo) {
		this.traceNo = traceNo;
	}

	public String getIsTrSuccess() {
		return isTrSuccess;
	}

	public void setIsTrSuccess(String isTrSuccess) {
		this.isTrSuccess = isTrSuccess;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getTermNum() {
		return termNum;
	}

	public void setTermNum(String termNum) {
		this.termNum = termNum;
	}

	public String getShopNum() {
		return shopNum;
	}

	public void setShopNum(String shopNum) {
		this.shopNum = shopNum;
	}

	public String getEpsOrderNo() {
		return epsOrderNo;
	}

	public void setEpsOrderNo(String epsOrderNo) {
		this.epsOrderNo = epsOrderNo;
	}

	public String getRrn() {
		return rrn;
	}

	public void setRrn(String rrn) {
		this.rrn = rrn;
	}

	public String getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getCardBankCode() {
		return cardBankCode;
	}

	public void setCardBankCode(String cardBankCode) {
		this.cardBankCode = cardBankCode;
	}

	public String getCardBankName() {
		return cardBankName;
	}

	public void setCardBankName(String cardBankName) {
		this.cardBankName = cardBankName;
	}

	public String getThirdOrderNo() {
		return thirdOrderNo;
	}

	public void setThirdOrderNo(String thirdOrderNo) {
		this.thirdOrderNo = thirdOrderNo;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getWorkspace() {
		return workspace;
	}

	public void setWorkspace(String workspace) {
		this.workspace = workspace;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getBppcCode() {
		return bppcCode;
	}

	public void setBppcCode(String bppcCode) {
		this.bppcCode = bppcCode;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Long getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(Long totalAmt) {
		this.totalAmt = totalAmt;
	}

	public Integer getDiffTotalNum() {
		return diffTotalNum;
	}

	public void setDiffTotalNum(Integer diffTotalNum) {
		this.diffTotalNum = diffTotalNum;
	}

	public Long getDiffTotalAmt() {
		return diffTotalAmt;
	}

	public void setDiffTotalAmt(Long diffTotalAmt) {
		this.diffTotalAmt = diffTotalAmt;
	}

	public String getThirdTransDate() {
		return thirdTransDate;
	}

	public void setThirdTransDate(String thirdTransDate) {
		this.thirdTransDate = thirdTransDate;
	}

	public String getThirdTransTime() {
		return thirdTransTime;
	}

	public void setThirdTransTime(String thirdTransTime) {
		this.thirdTransTime = thirdTransTime;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getTransName() {
		return transName;
	}

	public void setTransName(String transName) {
		this.transName = transName;
	}

	public String getTransRoute() {
		return transRoute;
	}

	public void setTransRoute(String transRoute) {
		this.transRoute = transRoute;
	}

	public String getGoods() {
		return goods;
	}

	public void setGoods(String goods) {
		this.goods = goods;
	}

	public Long getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(Long realAmount) {
		this.realAmount = realAmount;
	}

	public Long getRealAmount1() {
		return realAmount1;
	}

	public void setRealAmount1(Long realAmount1) {
		this.realAmount1 = realAmount1;
	}

	public String getBuyerLogonId() {
		return buyerLogonId;
	}

	public void setBuyerLogonId(String buyerLogonId) {
		this.buyerLogonId = buyerLogonId;
	}
	

	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	@Override
	public String toString() {
		return "PacketDef [transCode=" + transCode + ", workDate=" + workDate
				+ ", shiftNum=" + shiftNum + ", posId=" + posId + ", traceNo="
				+ traceNo + ", isTrSuccess=" + isTrSuccess + ", returnCode="
				+ returnCode + ", returnMsg=" + returnMsg + ", cardNum="
				+ cardNum + ", termNum=" + termNum + ", shopNum=" + shopNum
				+ ", epsOrderNo=" + epsOrderNo + ", rrn=" + rrn
				+ ", settlementDate=" + settlementDate + ", cardBankCode="
				+ cardBankCode + ", cardBankName=" + cardBankName
				+ ", thirdOrderNo=" + thirdOrderNo + ", transDate=" + transDate
				+ ", transTime=" + transTime + ", thirdTransDate="
				+ thirdTransDate + ", thirdTransTime=" + thirdTransTime
				+ ", workspace=" + workspace + ", cardType=" + cardType
				+ ", bppcCode=" + bppcCode + ", amount=" + amount + ", mac="
				+ mac + ", transName=" + transName + ", totalNum=" + totalNum
				+ ", totalAmt=" + totalAmt + ", diffTotalNum=" + diffTotalNum
				+ ", diffTotalAmt=" + diffTotalAmt + ", transSettleDefs="
				+ ", openId=" + openId + ", transRoute="
				+ transRoute + "]";
	}
	
	
}
