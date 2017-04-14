package com.sunvsoft.order;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.sunvsoft.backup.PropertiesUtil;

public class OrderSellbackAutoCommit {

	@SuppressWarnings("unused")
    public boolean httpUrlConnection(String order,String orderItems) {
		try {
			StringBuffer reqValues = this.getRequestValues(onPay(order,orderItems));
			String pathUrl = PropertiesUtil.getSellbackCommitAutoIp();
			// 建立连接
			URL url = new URL(pathUrl);
			HttpURLConnection httpConn = (HttpURLConnection) url
					.openConnection();

			// //设置连接属性
			httpConn.setDoOutput(true);// 使用 URL 连接进行输出
			httpConn.setDoInput(true);// 使用 URL 连接进行输入
			httpConn.setUseCaches(false);// 忽略缓存
			httpConn.setRequestMethod("POST");// 设置URL请求方法
			String requestString = "客服端要以以流方式发送到服务端的数据...";

			// 设置请求属性
			// 获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致
			httpConn.setRequestProperty("Accept-Charset", "GBK");
			httpConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			httpConn.setRequestProperty("Content-Length",
					String.valueOf(reqValues.length()));
			// 设置请求参数
			// String name = URLEncoder.encode("黄武艺", "utf-8");
			// httpConn.setRequestProperty("NAME", name);

			// 建立输出流，并写入数据
			// OutputStream outputStream = httpConn.getOutputStream();
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					httpConn.getOutputStream());
			// outputStream.write(requestStringBytes);
			outputStreamWriter.write(reqValues.toString());
			outputStreamWriter.flush();
			// 获得响应状态
			int responseCode = httpConn.getResponseCode();

			if (HttpURLConnection.HTTP_OK == responseCode) {// 连接成功
				// 当正确响应时处理数据
				StringBuffer sb = new StringBuffer();
				String readLine;
				BufferedReader responseReader;
				// 处理响应流，必须与服务器响应流输出的编码一致
				responseReader = new BufferedReader(new InputStreamReader(
						httpConn.getInputStream(),"GBK"));
				while ((readLine = responseReader.readLine()) != null) {
					sb.append(readLine).append("\n");
				}
				responseReader.close();
				System.out.println(sb.toString());
				boolean isComplete = Boolean.parseBoolean(sb.toString().trim());
				System.out.println(isComplete);
				return isComplete;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
    public StringBuffer getRequestValues(Map<?, ?> parameterMap) {
		// Map parameterMap = new HashMap<String, String>();
		StringBuffer parameterBuffer = new StringBuffer();
		if (parameterMap != null) {
			Iterator iterator = parameterMap.keySet().iterator();
			String key = null;
			String value = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				if (parameterMap.get(key) != null) {
					value = (String) parameterMap.get(key);
				} else {
					value = "";
				}

				parameterBuffer.append(key).append("=").append(value);
				if (iterator.hasNext()) {
					parameterBuffer.append("&");
				}
			}
		}
		System.out.println("POST parameter : " + parameterBuffer.toString());
		return parameterBuffer;
	}

	public Map<String, String> onPay(String order,String orderItem) {
		// 设置支付参数
		// -----------------------------
		Map<String, String> sParaTemp = new HashMap<String, String>();
//		String order = "insert into es_order(sn,status,create_time,goods_amount,order_amount,goods_num,store_id,currency) values('SO210260201509041851192972','1',1441363879,202.0,202.0,3,10260,'CNY')#2323#SO210260201509041851192972";
//		String orderItem = "insert into es_order_items(goods_id,product_id,sn,num,name,price,currency,cat_id) values(13972,14919,'SO210260201509041851192972','1',' 健美生牌儿童专用鱼油软胶囊   水果味',148.0,'CNY',0)#insert into es_order_items(goods_id,product_id,sn,num,name,price,currency,cat_id) values(13973,14920,'SO210260201509041851192972','1',' 菲艾伦苏格兰威士忌',0.0,'CNY',0)#insert into es_order_items(goods_id,product_id,sn,num,name,price,currency,cat_id) values(13974,14921,'SO210260201509041851192972','1',' 花王洁霸抗菌超浓缩洗衣粉',54.0,'CNY',0)";
		sParaTemp.put("order", order);
		sParaTemp.put("orderItem", orderItem);
		System.out.println(sParaTemp.toString());
		return sParaTemp;
	}
//	public static void main(String[] args) {
//		new OrderAutoCommit().httpUrlConnection();
//	}

}
