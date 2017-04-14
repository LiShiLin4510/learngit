package com.sunvsoft.backup;

import java.io.File;
import java.util.List;
import org.apache.log4j.Logger;

public class DoBackup {
	private final String diskname = PropertiesUtil.getDiskName();
	private Datasave da = new Datasave();
	protected final Logger logger = Logger.getLogger(getClass());

	// MyLog log = MyLog.getLoger();
	//@Test
	public void firstBackup() {
		// public String firstBackup(String diskname){
		// String diskname = "hello";
//		Datasave da = new Datasave();
		String diskString = da.getDiskStringByName(diskname);
		if (!da.hasBackuped(diskname)) {
			da.backup(diskString);
			// 第一次备份后，在表中写入记录，下次不会再次备份
			da.writeBackupLog(diskname);
			// return "初始化备份成功！";
		}
//		System.out.println("********************************8");
		logger.info(diskname + "第一次备份成功！");
	}

	//@Test
	//public void realTimeBackup() {
		 public void realTimeBackup(String sql){
		//String sql = "insert into es_order values ('bbcc','ddddd');";
//		String diskname = "hello";
//		Datasave da = new Datasave();
		String diskString = da.getDiskStringByName(diskname);
		da.realTimeBackup(sql, diskString);
		logger.info(sql + "实时写入成功！");
	}

	//@Test
	public void load() {
		// public String load(String diskname){
//		Datasave da = new Datasave();
//		String diskname = "hello";
		String diskString = da.getDiskStringByName(diskname);
		List<File> list = da.iteratorFile(diskString);
		for (File file : list) {
			System.out.println(file.getName());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				logger.error(diskname + "备份导入失败！");
				e.printStackTrace();
			}
			da.load(file);
		}
		logger.info(diskname + "备份导入成功！");
	}
}
