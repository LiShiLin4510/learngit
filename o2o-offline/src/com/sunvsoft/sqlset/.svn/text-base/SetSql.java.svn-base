package com.sunvsoft.sqlset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sunvsoft.backup.PropertiesUtil;
import com.sunvsoft.entity.DatabaseInits;
import com.sunvsoft.entity.PropertiesInit;

public class SetSql {
	java.sql.PreparedStatement stmt;
	java.sql.Connection coo;
	JFrame jf = new JFrame();
	@Transactional(propagation=Propagation.REQUIRED)
	public ResultSet setSql(String sql) {
		String driver = PropertiesUtil.getJdbcDriver();
		String url = PropertiesUtil.getJdbcUrl();
		String user = PropertiesUtil.getJdbcUser();
		String passwd = PropertiesUtil.getJdbcPasswd();
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e1) {
			JOptionPane.showMessageDialog(jf, "配置文件不存在，请填写");
			new PropertiesInit().init();
			System.out.println("第一个catch");
//			e1.printStackTrace();
		}

		try {

			coo = DriverManager.getConnection(url, user, passwd);
			// ?useUnicode=true&characterEncoding=utf8_general_ci
			stmt = coo.prepareStatement(sql);

			// stmt.execute();

			/*
			 * while(rs.next()){
			 * System.out.println(rs.getInt(1)+"\t"+rs.getString(4)); }
			 */
		} catch (SQLException e) {
			// 配置文件问题
//			if(e.getErrorCode()==1045){
//				new PropertiesInit().init();
//			}
			//数据库名找不到，需新建数据库
			if(e.getErrorCode()==1049){
				JOptionPane.showMessageDialog(jf, "数据库不存在，请输入数据库用户名密码 新建数据库");
				new DatabaseInits().init();
			}else if(e.getErrorCode()==1045){
				JOptionPane.showMessageDialog(jf, "数据库用户名密码错误");
				new PropertiesInit().init();
			}
			System.out.println(e.getErrorCode());
			return null;
		}
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery();
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean setSqlNotReturn(String sql) {
		String driver = PropertiesUtil.getJdbcDriver();
		String url = PropertiesUtil.getJdbcUrl();
		String user = PropertiesUtil.getJdbcUser();
		String passwd = PropertiesUtil.getJdbcPasswd();
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			coo = DriverManager.getConnection(url, user, passwd);
			// ?useUnicode=true&characterEncoding=utf-8
			stmt = coo.prepareStatement(sql);

			stmt.execute();
			return true;
			/*
			 * while(rs.next()){
			 * System.out.println(rs.getInt(1)+"\t"+rs.getString(4)); }
			 */
		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return false;
		}

	}

	public static void main(String[] args) {
		new SetSql().setSqlNotReturn("22");
	}

	public Connection getConn() {
		String driver = PropertiesUtil.getJdbcDriver();
		String url = PropertiesUtil.getJdbcUrl();
		String user = PropertiesUtil.getJdbcUser();
		String passwd = PropertiesUtil.getJdbcPasswd();
		try {
			Class.forName(driver);
			coo = DriverManager.getConnection(url, user, passwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return coo;
	}

}
