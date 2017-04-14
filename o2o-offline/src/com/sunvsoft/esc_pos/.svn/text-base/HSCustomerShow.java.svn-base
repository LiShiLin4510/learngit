package com.sunvsoft.esc_pos;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;

public class HSCustomerShow {
	static JNative DComm = null;
	static JNative Display = null;
	static JNative Init = null;
	static JNative Clr = null;

	/**
	 * 指定端口
	 * nPort:     1:COM1;  2:COM2;  3:COM3; 4:COM4 ...
	 * nParity:   0: Even; 1: Mark; 2:NO;   3:Odd;   4:Space
	 */
	@SuppressWarnings("deprecation")
	public void HSOpenDComm (int nPort,int nParity)
			throws NativeException, IllegalAccessException {
		
		try {
			if (DComm == null) {
				DComm = new JNative("HSDisplay","HSOpenDComm");
				DComm.setRetVal(Type.VOID);
			}
			int i = 0;
			DComm.setParameter(i++, nPort);
			DComm.setParameter(i++, nParity);
			DComm.invoke();// 调用方法
		}finally {
			if (DComm != null) {
				DComm.dispose();// 释放
			}
		}
	}
	/**
	 * 初始化
	 */
	@SuppressWarnings("deprecation")
	public String VC110_Init()
			throws NativeException, IllegalAccessException {
		try {
			if (Init == null) {
				Init = new JNative("HSDisplay","VC110_Init");
			}
//			System.out.println("调用的DLL文件名为：" + Something2.getDLLName());
//			System.out.println("调用的方法名为：" + Something2.getFunctionName());
			Init.invoke();// 调用方法
			return Init.getRetVal();
		} finally {
			if (Init != null) {
				Init.dispose();// 释放
			}
		}
	}
	
	/**
	 * 显示数字字符串和简单交易用语
	 * 参数:nTransType:1:单价(多谢惠顾明亮);   2:合计(多谢惠顾明亮);
	 * 				  3:收款(多谢惠顾明亮);   4:找零(多谢惠顾闪烁)
	 * pTar:要显示的字符
	 * pAmount:将要显示的数字字符串
	 * nAmountLen:数字字符串长度(包含所有小数点0<nAmountLen<=20)
	 */
	@SuppressWarnings("deprecation")
	public void VC110_Display(int pSrc, String pTar, int MaxCount)
			throws NativeException, IllegalAccessException {
		try {
			if (Display == null) {
				Display = new JNative("HSDisplay","VC110_Display");
			}
			int i = 0;
			Display.setParameter(i++, pSrc);
			Display.setParameter(i++, pTar);
			Display.setParameter(i++, MaxCount);
			Display.invoke();// 调用方法
//			return Init.getRetVal();
		} finally {
			if (Display != null) {
				Display.dispose();// 释放
			}
		}
	}
	@SuppressWarnings("deprecation")
	public void VC110_Cls() throws NativeException, IllegalAccessException{
		try {
			if (Clr == null) {
				Clr = new JNative("HSDisplay","VC110_Cls");
			}
			Clr.invoke();// 调用方法
		} finally {
			if (Clr != null) {
				Clr.dispose();// 释放
			}
		}
	}

	public static void main(String[] args) throws NativeException,
			IllegalAccessException {
		HSCustomerShow uc = new HSCustomerShow();
		uc.HSOpenDComm(2, 0);
		uc.VC110_Init();
		uc.VC110_Display(2, "45.67", 10);
	}
}
