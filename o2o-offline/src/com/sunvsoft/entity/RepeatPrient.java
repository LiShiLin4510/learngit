package com.sunvsoft.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.sunvsoft.scan.GenerateBarCode;
import com.sunvsoft.sqlset.SetSql;

public class RepeatPrient {
	 static Object[] columnNames = { "订单编号","商品名称", 
         "单价", "数量","总价","商品总数量","税金","税金总额","条形码","付款金额","日期"};
	static DefaultTableModel model;
	static JTable table;
    static String sn ="";
    static String paymoney = null;
    static Long time1 = null;

	Object[][] data=null; 
	@SuppressWarnings("serial")
    public void prient() throws SQLException{
			List<Object[]> list = new LinkedList<Object[]>();
			String sql="SELECT o.sn, t.name, t.price ,t.num,t.bar_code,o.order_amount,o.goods_amount,o.create_time,o.discountAmount, o.goods_num ,o.taxes,t.taxes AS taxess from es_order o LEFT JOIN es_order_items t ON o.order_id=t.order_id WHERE o.order_id=(SELECT order_id FROM es_order WHERE sn = '"+sn+"')";
			ResultSet rs = new SetSql().setSql(sql);
			while(rs.next()){
				String paymoney1;
				Object[] objects = new Object[] {
						rs.getString("sn"),
						rs.getString("name"),
						rs.getFloat("price"),
						rs.getInt("num"),
						rs.getFloat("order_amount"),
						rs.getInt("goods_num"),
						rs.getFloat("taxess"),
						rs.getFloat("taxes"),
						rs.getString("bar_code"),
						rs.getDouble("discountAmount"),
						paymoney1 = String.format("%.1f", rs.getDouble("order_amount")-rs.getDouble("discountAmount")),
						paymoney = String.format("%.2f", Double.parseDouble(paymoney1)),
						rs.getLong("create_time"),
						time1 = rs.getLong("create_time"),
						
				};
				list.add(objects);
			}
			if(list.size()==0){
                JOptionPane.showMessageDialog(O2OMainMenu.jf, "重新打印小票需输入订单编号");
            }else{
			data = list.toArray(new Object[0][0]);
			model = new DefaultTableModel(data, columnNames) {
				public boolean isCellEditable(int row, int column) {
					return false;
				};
			};
		    model.addRow(data);
		    table = new JTable(model);
		    table.setModel(model);
		    
		   GenerateBarCode.sn = sn;
		   RepeatPrientGoods.sn = sn;
		   RepeatPrientGoods.paymoney = paymoney;
	       GenerateBarCode barcode = new GenerateBarCode();
	       barcode.GenerateBarCode();
		   RepeatPrientGoods.row=list.size();
		   RepeatPrientGoods.table=table;
		   RepeatPrientGoods.time1 = time1;
		   RepeatPrientGoods pg = new RepeatPrientGoods();
           pg.start();
            }
	}
	public static void main(String[] args){
		RepeatPrient rp=new RepeatPrient();
		try {
			rp.prient();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
