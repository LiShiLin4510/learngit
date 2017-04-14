package com.sunvsoft.szpos;

import java.io.UnsupportedEncodingException;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class PosinfSZ {

	public interface IBank extends Library {
		IBank INSTACE = (IBank) Native
				.loadLibrary("C:/gmc/posinf", IBank.class);

		int bankall(String str1, byte[] str2);
	}

	public String[] doConsumption(String str) {
		// String str = "123456782345678900000000000001";
		byte[] str1 = new byte[1024];
		IBank.INSTACE.bankall(str, str1);
		System.setProperty("jna.encoding", "GBK");
		String result;
		try {
			result = new String(str1, "GBK");
			System.out.println("result" + result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return getResultStr(str1);
	}

	public static void main(String[] args) {
//		String str = "000000012345678903000000000001                    000011123";//查余
//		String str = "01      69      01000000000000                    000039715";//撤销
//		String str = "12345678234567890000000000000120090601111111111111000011000";//消费
		String str = "12345678234567890200000000000120151104151414299085000011000";//退货
		byte[] str1 = new byte[1024];

		IBank.INSTACE.bankall(str, str1);
		System.setProperty("jna.encoding", "GBK");
		try {
			String result = new String(str1, "GBK");
			System.out.println("result" + result);
			//
			 String[] resultStr = getResultStr(str1);
			 for (int j = 0; j < resultStr.length; j++) {
				 System.out.println(resultStr[j]);
			 }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Pos返回的字符串结果
	 * 
	 * @param str1
	 * @return
	 */
	private static String[] getResultStr(byte[] str1) {
		String[] result = new String[15];
		byte[][] s = new byte[15][40];
		int[] l = new int[] { 2, 4, 20, 6, 12, 40, 15, 8, 6, 4, 6, 12, 6, 4, 3 };
		int count = 0;
		for (int i = 0; i < 15; i++) {
			for (int k = 0; k < l[i]; k++) {
				s[i][k] = str1[count];
				count++;
			}
		}
		for (int i = 0; i < 15; i++) {
			try {
				result[i] = new String(s[i], "GBK").trim();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
//1.消费返回结果
	// 定长的
	// result|（00 表示成功，其它表示失败2）00|（银行行号4）9088|（卡号20）6222020504009875577
	// |（凭证号6）001425|（金额12）000000000001|（错误说明40）交易成功
	// |（商户号15）001584058129995|（终端号8）11480003|（批次号6）000156|（交易日期4）1014|（交易时间6）184115|（交易参考号12）184115529146|（授权号6）
	// |（清算日期4） 1014|（LRC校验3）
	// result0090886222020504009875577 001426000000000001交易成功
	// 001584058129995114800030001561014191541191541550128 1014
//2.	撤销返回结果，原凭证号：000039
//	result0001026222020504009875577 000048000000000001交易成功                                8984403737500020002HM0500000111041506031506032940870123451104715
//	00
//	0102
//	6222020504009875577
//	000048
//	000000000001
//	交易成功
//	898440373750002
//	0002HM05
//	000001
//	1104
//	150603
//	150603294087
//	012345
//	1104
//	715
}
