package com.sunvsoft.backup;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * 读取配置文件工具类
 * 
 * @author yanpeng
 * 
 */
public class PropertiesUtil {
//	private static ResourceBundle bundle = ResourceBundle.getBundle("F:\\dbconfig");
//	InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("dbconfig.properties");
	static Properties properties = new Properties();
	static String jarFilePath = null;
	static{
		//使用类路径
//		 jarFilePath = PropertiesUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();
		//使用jvm路径 
//		jarFilePath = System.getProperty("java.class.path");
		//获取
		jarFilePath = new File(".").getAbsolutePath();
		 int firstIndex = 0;
		 int lastIndex = jarFilePath.lastIndexOf(File.separator) + 1;
//		 System.out.println(File.separator);
		 System.out.println(lastIndex);
		 System.out.println(jarFilePath);
		 jarFilePath = jarFilePath.substring(firstIndex, lastIndex);
		 jarFilePath = jarFilePath+"dbconfig.properties";
		// URL Decoding  
		 System.out.println(jarFilePath);
//		try {
////			jarFilePath = java.net.URLDecoder.decode(jarFilePath, "UTF-8");
//			System.out.println(jarFilePath);
//		} catch (UnsupportedEncodingException e3) {
//			e3.printStackTrace();
//		}  
		File file = new File(jarFilePath);  
//		File file = new File("F:\\dbconfig.properties");  
        if (!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		 try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{  
            try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
        }   
	}
	/**
	 * url
	 * 
	 * @return
	 */
	public static String getJdbcUrl() {
		String value = properties.getProperty("jdbc.url");
		if (value == null)
			return "";
		return value;
	}

	/**
	 * user
	 * 
	 * @return
	 */
	public static String getJdbcUser() {
		String value = properties.getProperty("jdbc.user");
		if (value == null)
			return "";
		return value;
	}

	/**
	 * password
	 * 
	 * @return
	 */
	public static String getJdbcPasswd() {
		String value = properties.getProperty("jdbc.passwd");
		if (value == null)
			return "";
		return value;
	}
	/**
	 * dbDriver
	 * 
	 * @return
	 */
	public static String getJdbcDriver() {
		String value = properties.getProperty("jdbc.driverClassName");
		if (value == null)
			return "";
		return value;
	}

	/**
	 * jdbc.dbname
	 * 
	 * @return
	 */
	public static String getJdbcDbname() {
		String value = properties.getProperty("jdbc.dbname");
		if (value == null)
			return "o2ooffline";
		return value;
	}

	/**
	 * jdbc.tableName
	 * 
	 * @return
	 */
	public static String getJdbcTableName() {
		String value = properties.getProperty("jdbc.tableName");
		if (value == null)
			return "o2ooffline";
		return value;
	}
	/**
	 * 系统磁盘符
	 * 
	 * @return
	 */
	public static String getDiskString() {
		String value = properties.getProperty("diskString");
		if (value == null)
			return "E:/";
		return value;
	}

	public static String getDiskName() {
		String value = properties.getProperty("diskName");
		if (value == null)
			return "O2OOffLine";
		return value;
	}
	/**
	 * 客显端口
	 * 
	 * @return
	 */
	public static String getDisplayPort() {
		String value = properties.getProperty("port");
		if (value == null)
			return "COM1";
		return value;
	}
	/**
	 * 收银机编号
	 * 
	 * @return
	 */
	public static String getPosNum() {
		String value = properties.getProperty("posnum");
		if (value == null)
			return "1";
		return value;
	}
	/**
	 * UKey名称
	 * 
	 * @return
	 */
	public static String getUKeyName() {
		String value = properties.getProperty("uDiskName");
		if (value == null)
			return "O2O-OFFLINE";
		return value;
	}
	/**
	 * POS端口
	 * 
	 * @return
	 */
	public static String cardPort() {
		String value = properties.getProperty("cardPort");
		if (value == null)
			return "COM9";
		return value;
	}
	/**
	 * storeId varchar类型
	 * 
	 * @return
	 */
	public static String getStoreId() {
		String value = properties.getProperty("storeId");
		if (value == null)
			return "000000";
		return value;
	}
	/**
     * storeName varchar类型
     * 
     * @return
     */
    public static String getStoreName() {
        String value = properties.getProperty("storeName");
        if (value == null)
            return "环球购俄罗斯生活馆";
        return value;
    }
	/**
	 * 订单同步地址
	 * 
	 * @return
	 */
	public static String getCommitAutoIp() {
		String value = properties.getProperty("ip");
		if (value == null)
			return "http://192.168.1.21:8080" +
					"/api/b2b2c/addOrderoffline!addOrderofflineSql.do";
		return value + "/api/b2b2c/addOrderoffline!addOrderofflineSql.do";
	}
	/**
	 * 退货单同步地址
	 * 
	 * @return
	 */
	public static String getSellbackCommitAutoIp() {
		String value = properties.getProperty("ip");
//		value = value.replace("\\", "");
		if (value == null)
			return "http://192.168.1.21:8080/api/b2b2c/addOrderSellback!addOrderSellbackSql.do";
		return value + "/api/b2b2c/addOrderSellback!addOrderSellbackSql.do";
	}
	/**
	 * 会员号同步地址
	 * 
	 * @return
	 */
	public static String getMemberCommitAutoIp() {
		String value = properties.getProperty("ip");
		if (value == null)
			return "http://192.168.1.21:8080/api/b2b2c/addOrderSellback!memberIsValid.do";
		return value + "/api/b2b2c/addOrderSellback!memberIsValid.do";
	}
	public static String getIp(){
	    String value = properties.getProperty("ip");
	    if(value == null)
	        return "http://192.168.0.152:8080";
	    return value ;
	}
	/**
	 * 获取端口for HS
	 * 
	 * @return
	 */
	public static int  getnPort() {
		String value = properties.getProperty("nPort");
		if (value == null)
			return 1;
		int valueInt = Integer.parseInt(value);
		return valueInt;
	}
	/**
	 * 获取打印偏移量  为空返回 20
	 * 
	 * @return
	 */
	public static int  getXTrans() {
		String value = properties.getProperty("xtrans");
		if (value == null)
			return 20;
		int valueInt = Integer.parseInt(value);
		return valueInt;
	}
	/**
	 * 获取客显种类 1:普通客显  2:海信客显  为空返回1
	 * 
	 * @return
	 */
	public static int  getDisplayCat() {
		String value = properties.getProperty("displayCat");
		if (value == null)
			return 1;
		int valueInt = Integer.parseInt(value);
		return valueInt;
	}
	/**
	 * 设置配置文件
	 * @param maps
	 * @throws IOException
	 */
	 public void setValue(Map<?, ?> maps) throws IOException {
	        Properties prop = new Properties();
//	        String configPath = jarFilePath;
	        File file = new File(jarFilePath);  
	        if (!file.exists())  
	            file.createNewFile(); 
//	        	file.mkdirs();
	        InputStream fis = new FileInputStream(jarFilePath);
	        // 从输入流中读取属性列表（键和元素对）
	        prop.load(fis);
	        // 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
	        // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
	        OutputStream fos = new FileOutputStream(jarFilePath);
//	       	 遍历map，放到Properties中
	        if (maps != null) {
				Iterator<?> iterator = maps.keySet().iterator();
				String key = null;
				String value = null;
				while (iterator.hasNext()) {
					key = (String) iterator.next();
					value =  (String) maps.get(key);
					prop.setProperty(key, value);
				}
			}
	        // 以适合使用 load 方法加载到 Properties 表中的格式，
	        // 将此 Properties 表中的属性列表（键和元素对）写入输出流
	        prop.store(fos,"last update");
	        fos.close();
	    }
	public static void main(String[] args){
//		System.out.println(getMemberCommitAutoIp());
//		String a = "";
//		a = getMemberCommitAutoIp().replace("\\", "");
//		System.out.println(a);
	}
}
