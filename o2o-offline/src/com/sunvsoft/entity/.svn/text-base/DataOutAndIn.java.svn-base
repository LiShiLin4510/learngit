package com.sunvsoft.entity;

import java.io.File;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import jxl.read.biff.BiffException;

import com.sunvsoft.backup.PropertiesUtil;

public class DataOutAndIn {
//	private Properties prop1 = new Properties();
	
	// 获取系统盘符
	FileSystemView sys = FileSystemView.getFileSystemView();
	File[] files = File.listRoots();
	File file = null;
	JFrame jFrame = new JFrame("");
/**
 * 导出订单和退货单
 * 
 */
	public boolean DataOutRun() {
		for (int i = 0; i < files.length; i++) {
			// 得到文字命名形式的盘符系统 (C:)、软件 (D:)、公司文档 (E:)、测试U盘 (H:)
			if (sys.getSystemDisplayName(files[i]).contains(PropertiesUtil.getUKeyName())) {
				file = files[i];
			}
		}
		if (file == null) {
			JOptionPane.showMessageDialog(jFrame, "未插入U盘");
			return false;
		} else {
			//导出sql
//			try {
//				String cmd = "cmd /c mysqldump -uroot -p12345678 O2Ooffline es_order es_order_items>"+ file+ "\\sql/data.sql";
//				try {
//					Runtime.getRuntime().exec(cmd).waitFor();
//					JOptionPane.showMessageDialog(jFrame, "数据导出成功");
//				} catch (InterruptedException e) {
//					
//					JOptionPane.showMessageDialog(jFrame, "数据导出失败");
//					e.printStackTrace();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
			try {
				new outOrderToExcel().exportExcel(file+"");
				new outsellbackToExcel().exportExcel(file+"");
//				JOptionPane.showMessageDialog(jFrame, "导出成功");
			} catch (SQLException e) {
				
//				JOptionPane.showMessageDialog(jFrame, "导出失败");
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}
	/**
	 * 导出明细，包含退货明细
	 */
	public boolean DataOutRunItems() {
		for (int i = 0; i < files.length; i++) {
			// 得到文字命名形式的盘符系统 (C:)、软件 (D:)、公司文档 (E:)、测试U盘 (H:)
			if (sys.getSystemDisplayName(files[i]).contains(PropertiesUtil.getUKeyName())) {
				file = files[i];
			}
		}
		if (file == null) {
			return false;
		} else {
			//导出sql
//			try {
//				String cmd = "cmd /c mysqldump -uroot -p12345678 O2Ooffline es_order es_order_items>"+ file+ "\\sql/data.sql";
//				try {
//					Runtime.getRuntime().exec(cmd).waitFor();
//					JOptionPane.showMessageDialog(jFrame, "数据导出成功");
//				} catch (InterruptedException e) {
//					
//					JOptionPane.showMessageDialog(jFrame, "数据导出失败");
//					e.printStackTrace();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			try {
				
				new outOrderItemsToExcel().exportExcel(file+"");
//				JOptionPane.showMessageDialog(jFrame, "成功");
			} catch (SQLException e) {
				
//				JOptionPane.showMessageDialog(jFrame, "失败");
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}
	/**
	 * 导出班结统计和订单，订单明细，退货明细，退货单
	 * @return 导出状态 true 成功
	 */
	public boolean accountOutRun() {
		for (int i = 0; i < files.length; i++) {
			// 得到文字命名形式的盘符系统 (C:)、软件 (D:)、公司文档 (E:)、测试U盘 (H:)
			if (sys.getSystemDisplayName(files[i]).contains(PropertiesUtil.getUKeyName())) {
				file = files[i];
			}
		}
		if (file == null) {
			JOptionPane.showMessageDialog(jFrame, "未插入U盘");
			return false;
		} else {
			//导出sql
//			try {
//				String cmd = "cmd /c mysqldump -uroot -p12345678 o2ooffline account_data>"
//						+ file + "\\sql/data_account.sql";
//				try {
//					Runtime.getRuntime().exec(cmd).waitFor();
//					JOptionPane.showMessageDialog(jFrame, "数据导出成功");
//				} catch (InterruptedException e) {
//					
//					JOptionPane.showMessageDialog(jFrame, "数据导出失败");
//					e.printStackTrace();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			try {
//				new outAccountDataToExcel().exportExcel(file+"");
				new outOrderItemsToExcel().exportExcel(file+"");
				new outsellbackToExcel().exportExcel(file+"");
				new outOrderToExcel().exportExcel(file+"");
				new AddressToExcel().exportExcel(file+"");
				return true;
//				JOptionPane.showMessageDialog(jFrame, "成功");
			} catch (SQLException e) {
				
//				JOptionPane.showMessageDialog(jFrame, "失败");
				e.printStackTrace();
				return false;
			}
		}
	}
	/**
	 * 数据导入
	 */
	public boolean DataInRun() {
		for (int i = 0; i < files.length; i++) {
			// 得到文字命名形式的盘符系统 (C:)、软件 (D:)、公司文档 (E:)、测试U盘 (H:)
			if (sys.getSystemDisplayName(files[i]).contains(PropertiesUtil.getUKeyName())) {
				file = files[i];
			}
		}
		if (file == null) {
			JOptionPane.showMessageDialog(jFrame, "未插入U盘");
			return false;
		} else {
			//					Runtime.getRuntime()
//							.exec(new String[] {
//									"cmd",
//									"/c",
//									"mysql -uroot -p123456 O2Ooffline <" + file
//											+ "\\sql\\test.sql" },
//									null,
//									new File(
//											"C:\\Program Files\\MySQL\\MySQL Server 5.0\\bin"));
//					Thread.sleep(7000);// 主线程挂起7秒
			try {
				new DataIn().readExcel(file+"","es_goods.xls","es_goods");
				//new DataIn().readExcel(file+"","es_goods_spec.xls","es_goods_spec");
				new DataIn().readExcel(file+"","es_product.xls","es_product");
				new DataIn().readExcel(file+"","es_user.xls","es_user");
				new DataIn().readExcel(file+"","es_member_card.xls","es_member");
				new DataIn().readExcel(file+"","es_store_pro.xls","es_store_pro");
				//new DataIn().readExcel(file+"","es_spec_values.xls","es_spec_values");
				
			} catch (BiffException e) {
				
				e.printStackTrace();
				return false;
			} catch (Exception e) {
				
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
	}
//	public String getProperties(String str){
//		try {
//			prop1.load(DataOutAndIn.class.getClassLoader().getResourceAsStream(
//					"dbconfig.properties"));
//		} catch (IOException e2) {
//			e2.printStackTrace();
//		}
//		return prop1.getProperty(str);
//	}
}
