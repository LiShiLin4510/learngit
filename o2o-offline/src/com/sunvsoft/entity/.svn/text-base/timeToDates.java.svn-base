package com.sunvsoft.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class timeToDates {
	public String getTimeToDates(long time) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		// System.out.println(now + " = " +
		// formatter.format(calendar.getTime()));
		return formatter.format(calendar.getTime());

	}
	public String getTimeToDateForSn(long time) {
		DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		// System.out.println(now + " = " +
		// formatter.format(calendar.getTime()));
		return formatter.format(calendar.getTime());

	}

	public String[] dateNoSame(Long[] time) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		String[] timeS = new String[time.length];
		for (int i = 0; i < time.length; i++) {
			calendar.setTimeInMillis(time[i]*1000);
			timeS[i] = formatter.format(calendar.getTime());
		}
		System.out.println(timeS[0]);
		// 去重
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < timeS.length; i++) {
			if (!list.contains(timeS[i])) {// 如果数组 list 不包含当前项，则增加该项到数组中
				list.add(timeS[i]);
			}
		}
		System.out.println(list);
		Object[] newdata = list.toArray();
		System.out.println(newdata.length);
		String[] newStr = new String[newdata.length];
		for(int i=0;i<newdata.length;i++){
			newStr[i]=newdata[i].toString();
			}
		return newStr;
	}
	public Long dateSToTimeLBegin(String dateS){
		dateS = dateS+" 00:00:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			//String dateString = sdf.format(dateS);
			date = sdf.parse(dateS);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		long timeBegin = date.getTime();

		return timeBegin;
	} 
	public Long dateSToTimeLEnd(String dateS){
		dateS = dateS+" 23:59:59";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			//String dateString = sdf.format(dateS);
			date = sdf.parse(dateS);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		long timeEnd = date.getTime();

		return timeEnd;
	} 
	public String getTimes(long time) {
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		// System.out.println(now + " = " +
		// formatter.format(calendar.getTime()));
		return formatter.format(calendar.getTime());

	}
//	public static void main(String[] args){
//		Long i = new timeToDates().dateSToTimeLBegin("2015-08-08");
//		System.out.println(i);
//		Long j = new timeToDates().dateSToTimeLEnd("2015-08-08");
//		System.out.println(j);
//	}
}