package com.sunvsoft.entity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.sunvsoft.sqlset.SetSql;

public class outOrderToExcel{

//	// for es_order
Object[][] data = null;
	
	@SuppressWarnings("rawtypes")
	private Object[][] getDataArray(ResultSet rs) throws SQLException {
		@SuppressWarnings("unchecked")
		List<Object[]> list = new LinkedList();
		Object[] ob = new Object[]{"order_id","sn","cardNumber","status","payment_id","payment_name"
									,"payment_type","paymoney","goods","create_time","goods_amount","order_amount","goods_num","remark"
									,"disabled","complete_time","store_id","parent_id","currency","price_zh","classes","userid","refNo"
									,"cerNo","cashAmount","cardAmount","address_id","ship_status","shipping_id","shipping_type","weight"
									,"shipping_amount","discount","taxes","discountAmount","allowance","coupon"};
			list.add(ob);
		while (rs.next()) {
			// 改成你的列名
			Object[] objects = new Object[] {      		rs.getLong("order_id")+"", 
					 rs.getString("sn"),				rs.getString("cardNumber"),
					 
					 rs.getInt("status")+"",  			rs.getInt("payment_id")+"", 
					 rs.getString("payment_name"), 		rs.getString("payment_type"),  			rs.getDouble("paymoney")+"",
					 rs.getString("goods"),  			rs.getLong("create_time")+"",  			rs.getDouble("goods_amount")+"", 
					 rs.getDouble("order_amount")+"", 	rs.getInt("goods_num")+"",  			rs.getString("remark"),  
					 rs.getString("disabled") ,  		rs.getInt("create_time")+"",  			rs.getString("store_id"),  
					 rs.getInt("parent_id")+"",  	    rs.getString("currency"),  				rs.getDouble("price_zh")+"",
					 rs.getInt("classes")+"",			rs.getInt("userid")+"",					rs.getString("refNo"),
					 rs.getString("cerNo"),				rs.getDouble("cashAmount")+"",			rs.getDouble("cardAmount")+"",
					 rs.getInt("address_id")+"","102","0","物流配送","0.00","0.00","0.00",rs.getDouble("taxes")+"",rs.getDouble("discountAmount")+"",rs.getDouble("allowance")+"",rs.getDouble("coupon")+""};
			list.add(objects);
		}
		return list.toArray(new Object[0][0]);
	}
	public void exportExcel(String file) throws SQLException {
//	    String sql =  "select * from es_order left join es_user on es_order.user_name = es_user.username where es_order.status = "+ OrderStatus.ORDER_COMPLETE +" and isnull(es_order.export_status)";
	    String sql =  "select * from es_order left join es_user on es_order.user_name = es_user.username where es_order.status IN ("+ OrderStatus.ORDER_COMPLETE +","+ OrderStatus.ORDER_CANCEL_PAY +","+ OrderStatus.SHIP_PARTIAL_CANCEL +" ) and isnull(es_order.export_status)";
		ResultSet rs = new SetSql().setSql(sql);
//		ResultSet rs = new SetSql().setSql("select * from es_order left join es_user on es_order.user_name = es_user.username where es_order.status = 7 and not isnull(es_order.export_status)");
		data=getDataArray(rs);
        WritableWorkbook wwb;
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file+"\\es_order.xls");
//            fos = new FileOutputStream(file+"\\sql/es_order.xls");
            wwb = Workbook.createWorkbook(fos);
            WritableSheet ws = wwb.createSheet("es_order", 10);        // 创建一个工作表

            //    设置单元格的文字格式
            WritableFont wf = new WritableFont(WritableFont.ARIAL,12,WritableFont.NO_BOLD,false,
                    UnderlineStyle.NO_UNDERLINE,Colour.BLUE);
            WritableCellFormat wcf = new WritableCellFormat(wf);
            wcf.setVerticalAlignment(VerticalAlignment.CENTRE); 
            wcf.setAlignment(Alignment.CENTRE); 
            ws.setRowView(1, 500);

            //    填充数据的内容
           
            for(int i = 0;i<data.length;i++){
            	for(int j=0; j<data[0].length;j++){
            		ws.addCell(new Label(j, i + 1,  (String) data[i][j], wcf));
            	}
                
                if(i == 0)
                    wcf = new WritableCellFormat();
            }

            wwb.write();
            wwb.close();
            //改变订单表的导出状态，1已导出，null未导出
            new SetSql().setSqlNotReturn("update es_order set export_status = '1' where isnull(export_status)");
        } catch (IOException e){
        } catch (RowsExceededException e){
        } catch (WriteException e){}
    }
	public static void main(String[] args) throws SQLException{
		new outOrderToExcel().exportExcel("D:/abc.xls");
		System.out.println("成功导出数据到Excel文件了!!!");
	}
}
