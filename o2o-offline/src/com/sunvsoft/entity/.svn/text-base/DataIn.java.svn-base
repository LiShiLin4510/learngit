package com.sunvsoft.entity;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.sunvsoft.sqlset.SetSql;

public class DataIn {
	public void readExcel(String field, String excelName, String tableName)
			throws BiffException, IOException {
		// 创建一个list 用来存储读取的内容
		List<String[]> list = new ArrayList<String[]>();
		Workbook rwb = null;
		Cell cell = null;

		// 创建输入流
		InputStream stream = new FileInputStream(field + "\\" + excelName);
		// InputStream stream = new FileInputStream(field
		// + "\\sql/"+excelName);

		// 获取Excel文件对象
		rwb = Workbook.getWorkbook(stream);

		// 获取文件的指定工作表 默认的第一个
		Sheet sheet = rwb.getSheet(0);

		// 行数(表头的目录不需要，从1开始)
		for (int i = 1; i < sheet.getRows(); i++) {

			// 创建一个数组 用来存储每一列的值
			String[] str = new String[sheet.getColumns()];

			// 列数
			for (int j = 0; j < sheet.getColumns(); j++) {

				// 获取第i行，第j列的值
				cell = sheet.getCell(j, i);
				str[j] = cell.getContents();

			}
			// 把刚获取的列存入list
			list.add(str);
		}
		java.sql.PreparedStatement psta = null;
		new SetSql().setSqlNotReturn("delete from " + tableName);
		Connection conn = new SetSql().getConn();
		try {
			for (int i = 0; i < list.size(); i++) {
				StringBuffer sbBuffer = new StringBuffer();
				String[] str = (String[]) list.get(i);
				for (int j = 0; j < str.length - 1; j++) {
					// System.out.println(str[j]);//一行的数据str[0]--str[str.length]
					// if (str[j].length() == 0) {
					// str[j] = 0 + "";
					// }
					// String forAppend = "'" + str[j] + "',";
					// sbBuffer.append(forAppend);
					String forAppend = "?,";
					sbBuffer.append(forAppend);

				}
				// String forAppends = "'" + str[str.length - 1] + "'";
				String forAppends = "?";
				sbBuffer.append(forAppends);
				String strString = sbBuffer.toString();
				// System.out.println(strString);
				// java.sql.PreparedStatement psta = null;

				psta = conn.prepareStatement("insert into " + tableName
						+ " value(" + strString + ")");
				for (int j = 1; j <= str.length; j++) {
					if (str[j - 1].equals("")) {
						str[j - 1] = "0";
					}
					psta.setString(j, str[j - 1]);
				}
				psta.execute();

				//
				// execute();executeQuery()
				// new
				// SetSql().setSqlNotReturn("insert into "+tableName+" value("
				// + strString + ")");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {

				if (psta != null) {
					psta.close();
					psta = null;
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws BiffException, IOException {
		new DataIn().readExcel("E:\\", "excelname", "tablename");
	}
}