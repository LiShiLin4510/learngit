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

public class outOrderItemsToExcel {

//	// for es_order
Object[][] data = null;
	
	@SuppressWarnings("rawtypes")
	private Object[][] getDataArray(ResultSet rs) throws SQLException {
		@SuppressWarnings("unchecked")
		List<Object[]> list = new LinkedList();
		Object[] ob = new Object[]{"item_id","order_id","goods_id","product_id","cat_id","num","name","price","state"
									,"currency","sn","taxes"};
			list.add(ob);
		while (rs.next()) {
			//空的state转化为1
			String state =null;
			if(rs.getInt("state")==0){
				 state = OrderStatus.ORDER_PAY+"";
			}else{
				 state = rs.getInt("state")+"";
			}
			Object[] objects = new Object[] {     	rs.getInt("item_id")+"", 
					 rs.getLong("order_id")+"",	   	rs.getInt("goods_id")+"",
					 rs.getInt("product_id")+"",  	rs.getInt("cat_id")+"",  		rs.getInt("num")+"",
					 rs.getString("name"),  		rs.getDouble("price")+"", 		state,
					 rs.getString("currency"),		rs.getString("sn"),             rs.getDouble("taxes")+""
					 };
			list.add(objects);
		}
		return list.toArray(new Object[0][0]);
	}
	public void exportExcel(String file) throws SQLException {
	    String sql = "select * from es_order_items left join es_order on es_order_items.order_id = es_order.order_id where isnull(es_order_items.export_status) and es_order.status IN ("+ OrderStatus.ORDER_COMPLETE +","+ OrderStatus.ORDER_PAY_CONFIRM +","+OrderStatus.ORDER_CANCEL_PAY+")";
		ResultSet rs = new SetSql().setSql("select * from es_order_items left join es_order on es_order_items.order_id = es_order.order_id where isnull(es_order_items.export_status) and es_order.status IN ("+ OrderStatus.ORDER_COMPLETE +","+ OrderStatus.ORDER_PAY_CONFIRM +","+OrderStatus.ORDER_CANCEL_PAY+")");
//	    ResultSet rs = new SetSql().setSql("select * from es_order_items left join es_order on es_order_items.order_id = es_order.order_id where not isnull(es_order_items.export_status) and es_order.status = 7");
		data=getDataArray(rs);
        WritableWorkbook wwb;
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file+"\\es_order_items.xls");
//            fos = new FileOutputStream(file+"\\sql/es_order_items.xls");
            wwb = Workbook.createWorkbook(fos);
            WritableSheet ws = wwb.createSheet("es_order_items", 10);        // 创建一个工作表

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
            new SetSql().setSqlNotReturn("update es_order_items  set export_status = '1' where isnull(export_status)");
        } catch (IOException e){
        } catch (RowsExceededException e){
        } catch (WriteException e){}
    }
	public static void main(String[] args) throws SQLException{
		//new outOrderItemsToExcel().exportExcel();
		System.out.println("成功导出数据到Excel文件了!!!");
	}
}
