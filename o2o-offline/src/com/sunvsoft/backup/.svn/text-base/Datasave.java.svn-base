package com.sunvsoft.backup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import org.apache.log4j.Logger;

import com.sunvsoft.sqlset.SetSql;

public class Datasave {
	protected final Logger logger = Logger.getLogger(getClass());
	private final String username = PropertiesUtil.getJdbcUser();
	private final String password = PropertiesUtil.getJdbcPasswd();
	private final String dbname = PropertiesUtil.getJdbcDbname();
	private final String dbtablename = PropertiesUtil.getJdbcTableName();
	private final String diskString = PropertiesUtil.getDiskString();
	private List<File> flielist = new ArrayList<File>();

	// public static void main(String[] args) {
	// // backup();
	// load();
	// //
	// realTimeBackup("insert into eop_app (appid,app_name) values ('bbcc','ddddd');");
	// }

	/**
	 * 备份检验一个sql文件是否可以做导入文件用的一个判断方法：把该sql文件分别用记事本和ultra
	 * edit打开，如果看到的中文均正常没有乱码，则可以用来做导入的源文件（不管sql文件的编码格式如何，也不管db的编码格式如何）
	 */
	public void backup(String diskString) {
		InputStream in = null;
		InputStreamReader xx = null;
		BufferedReader br = null;
		FileOutputStream fout = null;
		OutputStreamWriter writer = null;
		Process child = null;
		try {
			Runtime rt = Runtime.getRuntime();
			if (dbtablename != null && !"".equals(dbtablename.trim())) {
				child = rt.exec("mysqldump -u" + username + " -p" + password
						+ " --set-charset=utf8 " + dbname + " " + dbtablename);// 设置导出编码为utf8。这里必须是utf8
			} else {
				// 调用 mysql 的 cmd:
				child = rt.exec("mysqldump -u" + username + " -p" + password
						+ " --set-charset=utf8 " + dbname);// 设置导出编码为utf8。这里必须是utf8
			}

			// 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
			in = child.getInputStream();// 控制台的输出信息作为输入流

			xx = new InputStreamReader(in, "utf8");// 设置输出流编码为utf8。这里必须是utf8，否则从流中读入的是乱码

			String inStr;
			StringBuffer sb = new StringBuffer("");
			String outStr;
			// 组合控制台输出信息字符串
			br = new BufferedReader(xx);
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");
			}
			outStr = sb.toString();

			// 要用来做导入用的sql目标文件：
			fout = new FileOutputStream(newTodayFile(diskString));
			// 第二个参数为true时，不会覆盖上次的数据
			// fout = new FileOutputStream(newTodayFile(diskString),true);
			writer = new OutputStreamWriter(fout, "utf8");
//			在sql文件的开始写入编码，防止导入数据库时中文乱码
			writer.write("set names 'utf8';");
			writer.write(outStr);
			// writer.write("abcdfewfewkfoewpfkopewkfirjigfrhjidejfiewfjjfalafja");
			// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
			writer.flush();

			// 别忘记关闭输入输出流
			logger.info(diskString + "备份成功！");

		} catch (Exception e) {
			logger.error(diskString + "备份失败！");
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (xx != null) {
					xx.close();
				}
				if (br != null) {
					br.close();
				}
				if (writer != null) {
					writer.close();
				}
				if (fout != null) {
					fout.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	/**
	 * 实时写入sql语句到SQL文件中
	 * 
	 */
	public void realTimeBackup(String sql, String diskString) {
		FileOutputStream fout = null;
		OutputStreamWriter writer = null;
		try {
			// 要用来做导入用的sql目标文件：
			fout = new FileOutputStream(newTodayFile(diskString), true);
			writer = new OutputStreamWriter(fout, "utf8");
			writer.write(sql);
			writer.write("\r\n");
			// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
			writer.flush();
			logger.info("sql:" + sql + "备份成功！磁盘：" + diskString);

		} catch (Exception e) {
			logger.error("sql:" + sql + "备份失败！磁盘：" + diskString);
			e.printStackTrace();
		} finally {
			// 别忘记关闭输入输出流
			try {
				if (writer != null) {
					writer.close();
				}
				if (fout != null) {
					fout.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	/**
	 * 导入 导入的时候需要数据库已经建好。
	 */
	public void load(File file) {
		OutputStream out = null;
		BufferedReader br = null;
		OutputStreamWriter writer = null;
		try {
			// String fPath = "d:/demo.sql";
			Runtime rt = Runtime.getRuntime();
			// 调用 mysql 的 cmd:
			// 为什么设置编码会报错？用mysqldump为什么也会报错？
			Process child = rt.exec("mysql -u" + username + " -p" + password
					+ " " + dbname);// 设置导出编码为utf8。这里必须是utf8
			// 调用 mysql 的 cmd:
			// rt.exec("create database demo");
			// Process child = rt.exec("mysql -uroot -pabc123456 tradeload");
			out = child.getOutputStream();// 控制台的输入信息作为输出流
			String inStr;
			StringBuffer sb = new StringBuffer("");
			String outStr;
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), "utf8"));
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");
			}
			outStr = sb.toString();
			writer = new OutputStreamWriter(out, "utf8");
			writer.write(outStr);
			// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
			writer.flush();
			// 别忘记关闭输入输出流
			logger.info("文件:" + file.getName() + "导入成功！数据库名：" + dbname);
		} catch (Exception e) {
			logger.info("文件:" + file.getName() + "导入失败！数据库名：" + dbname);
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if (br != null) {
					br.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	// 每天新建一个文件，sql语句写入

	/**
	 * 获取文件名
	 * 
	 * @return
	 */
	private String getFileName(String prefixString) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 0);
		String fileName = new SimpleDateFormat("yyyyMMdd")
				.format(cal.getTime()) + ".sql";
		return prefixString + fileName;
	}

	/**
	 * 新建一个文件（以今天的日期命名） diskString 磁盘符
	 * 
	 * @return
	 */
	private File newTodayFile(String diskString) {
		FileOutputStream fout = null;
		OutputStreamWriter writer = null;
		File todayFile = new File(diskString + getFileName("order"));
		if (!todayFile.exists()) {
			try {
				// 当且仅当不存在具有此抽象路径名指定名称的文件时，不可分地创建一个新的空文件
				todayFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
//		新建一个sql文件时，在文件的开始写入编码
		try {
			fout = new FileOutputStream(todayFile,true);
			writer = new OutputStreamWriter(fout, "utf8");
			writer.write("set names 'utf8';");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if (fout != null) {
					fout.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return todayFile;
	}

	/**
	 * 循环遍历指定磁盘中所有文件和目录文件 （包括文件夹下的文件）
	 * 
	 * @param diskString
	 *            硬盘符
	 * @return
	 */
	@SuppressWarnings("unused")
    private List<File> iteratorDirFile(String diskString) {
		// List<File> flielist = new ArrayList<File>();
		File diskFile = new File(diskString);
		File[] listFile = diskFile.listFiles();
		for (File myflie : listFile) {
			if (myflie != null && !myflie.isHidden() && myflie.isDirectory()) {
				iteratorFile(myflie.getAbsolutePath());
			}
			if (myflie.isFile() && !myflie.isHidden()) {
				flielist.add(myflie);
			}
		}
		return flielist;
	}

	/**
	 * 循环遍历指定磁盘中所有文件 （不包括文件夹下的文件）
	 * 
	 * @param diskString
	 *            硬盘符
	 * @return
	 */
	public List<File> iteratorFile(String diskString) {
		List<File> onlyFilelist = new ArrayList<File>();
		File diskFile = new File(diskString);
		File[] listFile = diskFile.listFiles();
		for (File myflie : listFile) {
			if (myflie.isFile()
					&& myflie.getName().trim().toLowerCase().endsWith(".sql")
					&& !myflie.isHidden()) {
				onlyFilelist.add(myflie);
				logger.info("文件：" + myflie.getName());
			}
		}
		return onlyFilelist;
	}

	/**
	 * 获取指定的系统盘符(从配置文件中读取)
	 * 
	 */
	public String getDiskString() {
		if (diskString == null)
			return "E:/";
		return diskString;
	}

	/**
	 * 获取指定系统盘名字的系统盘符
	 * 
	 */
	public String getDiskStringByName(String diskname) {
		FileSystemView sys = FileSystemView.getFileSystemView();
		File[] files = File.listRoots();
		String diskString = null;
		for (int i = 0; i < files.length; i++) {
			// 得到文字命名形式的盘符系统 (C:)、软件 (D:)、公司文档 (E:)、测试U盘 (H:)
			if (sys.getSystemDisplayName(files[i]).contains(diskname)) {
				diskString = files[i].getPath();
			}
		}
		if (diskString == null) {
			JOptionPane.showMessageDialog(new JFrame(), "没有找到指定名字为：" + diskname
					+ "的硬盘！");
		}
		return diskString;
	}

	/**
	 * 读取初始化备份日志，只在第一次初始化的时候备份数据库
	 * 
	 * diskname 磁盘名
	 */
	public Boolean hasBackuped(String diskname) {
		String sql2 = " select * from information_schema.TABLES where table_name = 'backup_log' AND TABLE_SCHEMA = '"
				+ dbname + "' ";
		ResultSet rs2 = new SetSql().setSql(sql2);
		try {
			if (!rs2.next()) {
				// 创建数据库
				String sql3 = "create table backup_log("
						+ "id int(8) auto_increment PRIMARY KEY not null,"
						+ "diskName varchar(20)," + "hasBackuped int(1))";
				new SetSql().setSqlNotReturn(sql3);
			}
		} catch (SQLException e1) {
			logger.error("创建数据库表 backup_log 失败！");
			e1.printStackTrace();
		}
		String sql = "select b.hasBackuped from backup_log b where b.diskname = '"
				+ diskname + "'";
		ResultSet rs = new SetSql().setSql(sql);
		Boolean flag = false;
		try {
			while (rs.next()) {
				int hasBackuped = rs.getInt("hasBackuped");
				if (hasBackuped == 2)
					flag = true;
				System.out.println(hasBackuped);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 写入初始化备份日志，只在第一次初始化的时候
	 * 
	 * diskname 磁盘名
	 */
	public void writeBackupLog(String diskname) {
		// String sql2 =
		// " select * from information_schema.TABLES where table_name = 'backup_log' AND TABLE_SCHEMA = '"+dbname+"' ";
		// ResultSet rs2 = new SetSql().setSql(sql2);
		try {
			// if(rs2.next()){
			String sql4 = "select * from backup_log where diskname = '"
					+ diskname + "' ";
			ResultSet rs3 = new SetSql().setSql(sql4);
			if (rs3.next()) {
				new SetSql()
						.setSqlNotReturn("update backup_log set hasBackuped = 2 where diskname = '"
								+ diskname + "' ");
			} else {
				String sql = "insert into backup_log (diskname,hasBackuped) values ('"
						+ diskname + "',2)";
				new SetSql().setSqlNotReturn(sql);
			}

			// }else{
			// String sql3 = "create table backup_log(" +
			// "id int(8) auto_increment PRIMARY KEY not null," +
			// "diskName varchar(20)," +
			// "hasBackuped int(1))";
			// new SetSql().setSqlNotReturn(sql3);
			// String sql =
			// "insert into backup_log (diskname,hasBackuped) values ('"
			// + diskname + "',2)";
			// new SetSql().setSqlNotReturn(sql);
			// }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
