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

public class AddressToExcel{

//	// for es_order
Object[][] data = null;
	
	@SuppressWarnings("rawtypes")
	private Object[][] getDataArray(ResultSet rs) throws SQLException {
		@SuppressWarnings("unchecked")
		List<Object[]> list = new LinkedList();
		Object[] ob = new Object[]{"addr_id","member_id","name","province_id","city_id"
									,"region_id","region","city","province","addr","zip","tel","mobile"
									,"type","def_addr","country"};
			list.add(ob);
		while (rs.next()) {
			// 改成你的列名
			Object[] objects = new Object[] {
			         rs.getString("addr_id")+"", 
					 rs.getString("member_id"),	
					 rs.getString("name"),
		  			 rs.getString("province_id")+"", 
					 rs.getString("city_id"), 		
					 rs.getString("region_id"),  			
					 rs.getString("region")+"",
					 rs.getString("city"),  			
					 rs.getString("province")+"",  			
					 rs.getString("addr")+"", 
					 rs.getString("zip")+"", 	
					 rs.getString("tel")+"",  			
					 rs.getString("mobile"),  
					 rs.getString("type") ,  		
					 rs.getString("def_addr")+"",
					 rs.getString("country")};
			list.add(objects);
		}
		return list.toArray(new Object[0][0]);
	}
	public void exportExcel(String file) throws SQLException {
		ResultSet rs = new SetSql().setSql("select * from es_member_address where export_status=1 ");
//		ResultSet rs = new SetSql().setSql("select * from es_order left join es_user on es_order.user_name = es_user.username where es_order.status = 7 and not isnull(es_order.export_status)");
		data=getDataArray(rs);
        WritableWorkbook wwb;
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file+"\\es_member_address.xls");
//            fos = new FileOutputStream(file+"\\sql/es_order.xls");
            wwb = Workbook.createWorkbook(fos);
            WritableSheet ws = wwb.createSheet("es_member_address", 10);        // 创建一个工作表

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
            String sql="update es_member_address set export_status=0";
            new SetSql().setSqlNotReturn(sql);
            wwb.write();
            wwb.close();
            //改变订单表的导出状态，1已导出，null未导出
//            new SetSql().setSqlNotReturn("update es_order set export_status = '1' where isnull(export_status)");
        } catch (IOException e){
        } catch (RowsExceededException e){
        } catch (WriteException e){}
    }
	/*public static void main(String[] args) throws SQLException{
		new AddressToExcel().exportExcel("D:/abc.xls");
		System.out.println("成功导出数据到Excel文件了!!!");
	}*/
}
