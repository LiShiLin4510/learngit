package com.sunvsoft.entity;
 
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JTable;

import com.sunvsoft.backup.PropertiesUtil;
import com.sunvsoft.sqlset.SetSql;
  
public class PrientGoodsForCheck extends Thread implements Printable{  
//	 O2OMainMenu mm = null;
//	private Properties prop1 = new Properties();
	  int row = O2OMainMenu.table.getRowCount();
	  String bankMoney = null;
	  String cashMoney = null;
	  String sn =O2OMainMenu.documentNumber.getText();
	  String totalNumber = O2OMainMenu.totalNumberLabelChange.getText();
	  String totalMoney = O2OMainMenu.totalMoneyLabelChange.getText();
	  JTable table = O2OMainMenu.table;
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String aString = sdf.format(date);
	
    @Override  
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException { 
    	 ResultSet rs = new SetSql().setSql("select * from es_order where sn = '"+sn+"'");
         try {
 			rs.next();
 			bankMoney = rs.getDouble("cardAmount")+"";
 			cashMoney = rs.getDouble("cashAmount")+"";
 		} catch (SQLException e1) {
 			e1.printStackTrace();
 		}
//    	try {
//			prop1.load(DataOutAndIn.class.getClassLoader().getResourceAsStream(
//					"jdbc.properties"));
//		} catch (IOException e2) {
//			e2.printStackTrace();
//		}
    	String posNumString = PropertiesUtil.getPosNum();
  
    	ImageIcon image = new ImageIcon(Prient.class.getClassLoader()
				.getResource("images/logo3.png"));
    	Image image2 = image.getImage();  
        Graphics2D g2d = (Graphics2D) g; 
        g2d.setFont(new Font("Default", Font.PLAIN, 8)); 
        g2d.drawImage(image2,5+Prient.transX,5,105,35,null);
        g2d.drawString("终端编号:"+posNumString,0+Prient.transX, 80); 
        g2d.drawString("日期:"+aString, 0+Prient.transX, 90);
        g2d.setFont(new Font("Default", Font.PLAIN, 6)); 
        g2d.drawString("单号:"+sn, 0+Prient.transX, 100);
        g2d.setFont(new Font("Default", Font.PLAIN, 8)); 
        g2d.drawString("货号", 0+Prient.transX, 110);
        g2d.drawString("数量*单价", 30+Prient.transX, 110);
       // g2d.drawString("单价", 55, 80);
        g2d.drawString("金额", 80+Prient.transX, 110);
        g2d.drawString("-----------------------------------------------", 0+Prient.transX, 120);
        int i =0;
        for(i=0;i<row;i++){
        	String goodsNames = table.getValueAt(i, 3)+"";
        	String goodsName = "";
        	if(goodsNames.length()>15){
        		 goodsName = goodsNames.substring(0, 15);
        	}else{
        		goodsName = goodsNames;
        	}
        	g2d.drawString(goodsName, 0+Prient.transX, 130+i*20);
        	g2d.drawString(table.getValueAt(i, 6)+"*"+table.getValueAt(i, 5), 30+Prient.transX, 140+i*20);
        	//g2d.drawString(table.getValueAt(i, 5)+"", 50, 90+i*20);
        	g2d.drawString(table.getValueAt(i, 7)+"", 80+Prient.transX, 140+i*20);
        }
        g2d.drawString("-----------------------------------------------",0+Prient.transX, 140+i*20);
        g2d.drawString("购买件数: " + totalNumber, 0+Prient.transX, 150+i*20);  
        g2d.drawString("应付: " + totalMoney, 0+Prient.transX, 160+i*20);
        g2d.drawString("原单刷卡金额:" + bankMoney , 0+Prient.transX, 170+i*20);
        g2d.drawString("原单现金金额:" + cashMoney , 0+Prient.transX, 180+i*20);
        g2d.drawString("-----------------------------------------------", 0+Prient.transX, 190+i*20);  
        //g2d.drawString("时间: " + aString , 0, 120+i*20);
        g2d.drawString("欢迎您再次光临！", 0+Prient.transX, 200+i*20);
        g2d.drawString("-----------------------------------------------",0+Prient.transX, 220+i*20);
        return PAGE_EXISTS; 
//        Graphics2D g2d = (Graphics2D) g; 
//      
//        g2d.setFont(new Font("Default", Font.PLAIN, 10));  
//        g2d.drawString("小票",0, 30);  
//        g2d.drawString("--------------------------------", 0, 45); 
//        int i =0;
//        for(i=0;i<row;i++){
//        	g2d.drawString(i+1+"."+table.getValueAt(i, 3)+"--"+table.getValueAt(i, 5)+"--"+table.getValueAt(i, 6), 0, 55+i*30);
//        }
//        g2d.drawString("合计数量:" + totalNumber, 0, 75+i*30);  
//        g2d.drawString("合计金额:" + totalMoney, 0, 105+i*30);  
//        g2d.drawString("--------------------------------", 0, 140+i*30);  
//        g2d.drawString("订单编号:" + sn , 0, 170+i*30);
//        g2d.drawString("打印时间:" + aString , 0, 190+i*30);
//        return PAGE_EXISTS;  
    }  
  
    public void run() { 
  
       
  
        // 通俗理解就是书、文档  
        Book book = new Book();  
  
        // 打印格式  
        PageFormat pf = new PageFormat();  
        pf.setOrientation(PageFormat.PORTRAIT);  
  
        // 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。  
        Paper p = new Paper();  
        p.setSize(160, 1000);  
        p.setImageableArea(0, 0, 160, 1000);  
        pf.setPaper(p);  
  
        // 把 PageFormat 和 Printable 添加到书中，组成一个页面  
        book.append(new PrientGoodsForCheck(), pf);  
  
        // 获取打印服务对象  
        PrinterJob job = PrinterJob.getPrinterJob();  
        job.setPageable(book);  
//        ResultSet rs = new SetSql().setSql("select * from es_order where sn = '"+sn+"'");
//        try {
//			rs.next();
//			bankMoney = rs.getDouble("cardAmount")+"";
//			cashMoney = rs.getDouble("cashAmount")+"";
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
        
        try {  
            job.print();  
        } catch (PrinterException e) {  
            System.out.println("================打印出现异常");  
        }  
  
    }  
  
}
