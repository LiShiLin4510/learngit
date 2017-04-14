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
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JTable;

import com.sunvsoft.backup.PropertiesUtil;
import com.sunvsoft.util.StringUtil;
  
public class RepeatPrientGoods extends Thread implements Printable{  
	 //O2OMainMenu mm = null;
//	private Properties prop1 = new Properties();
    static DecimalFormat df = new DecimalFormat("0.00");
     static int row =0;
     double tax = 0.00;
     public static String sn;
     String totalNumber = null;
     String totalMoney = null;
     static Long time1 = null;
     static JTable table = null;
     static String paymoney;
     DecimalFormat df1 = new DecimalFormat("######0.00");
     String taxs = df.format(tax);

 //    Date date = new Date();
 //    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 //    String aString = sdf.format(date);
	
    @SuppressWarnings("static-access")
    @Override  
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
//    	sn=table.getValueAt(0, 0)+"";
//    	totalNumber=table.getValueAt(0, 0)+"";
//    	totalMoney=table.getValueAt(0, 0)+"";
//    	tax=Double.parseDouble(table.getValueAt(0, 2)+"");
//    	try {
//			prop1.load(DataOutAndIn.class.getClassLoader().getResourceAsStream(
//					"jdbc.properties"));
//		} catch (IOException e2) {
//			e2.printStackTrace();
//		}
     	String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(time1 * 1000));
     			
        String posNumString = PropertiesUtil.getPosNum();  
    	ImageIcon image = new ImageIcon(Prient.class.getClassLoader()
				.getResource("images/icon.png"));
    	Image image2 = image.getImage();
        Graphics2D g2d = (Graphics2D) g; 
        g2d.setFont(new Font("Default", Font.PLAIN, 8)); 
        g2d.drawImage(image2,5+Prient.transX,5,105,35,null);
        g2d.drawString("店铺名称："+new PropertiesUtil().getStoreName(),0+Prient.transX,70);
        g2d.drawString("终端编号:"+posNumString,0+Prient.transX, 80); 
        g2d.drawString("日期:"+date, 0+Prient.transX, 90);
        g2d.setFont(new Font("Default", Font.PLAIN, 6));
    	ImageIcon image3 = new ImageIcon("D://lmtbarcode//"+sn+".png");
    	Image image4 = image3.getImage();
        g2d.drawImage(image4,5,100,140,40,null);
        g2d.setFont(new Font("Default", Font.PLAIN, 8)); 
        g2d.drawString("商品名", 0+Prient.transX, 150);
        g2d.drawString("数量*单价", 40+Prient.transX, 150);
        g2d.drawString("税金", 80+Prient.transX, 150);
       // g2d.drawString("单价", 55, 80);
        g2d.drawString("金额",105+Prient.transX, 150);
        g2d.drawString("-------------------------------------------", 0+Prient.transX, 160);
        int i = 0;
        
        for(i=0;i<row;i++){
        	String goodsNames = table.getValueAt(i, 1)+"";
        	String goodsName = "";
        	String goodsName1 = "";
        	int goodsLength = goodsNames.length();
        	if(goodsLength<17){
        	    goodsName = goodsNames.substring(0, goodsLength);
        	    goodsName1 = "";
        	}else{
        	    goodsName = goodsNames.substring(0, 17);
        	    if(goodsLength>34){
        	        goodsName1 = goodsNames.substring(17, 34);
        	    }else{
        	        goodsName1 = goodsNames.substring(17, goodsLength);
        	    }
        	}
             g2d.setFont(new Font("Default", Font.PLAIN, 5)); 
    		 g2d.drawString(goodsName, 0+Prient.transX, 170+i*30);
    		 g2d.drawString(goodsName1, 0+Prient.transX, 180+i*30);
    		 g2d.drawString( table.getValueAt(i, 8).toString(),Prient.transX,190+i*30);
             g2d.drawString(table.getValueAt(i, 3)+"*"+table.getValueAt(i, 2), 40+Prient.transX, 190+i*30);
             String tax1 =df.format(new BigDecimal(Double.parseDouble(table.getValueAt(i, 6)+"")*Double.parseDouble(table.getValueAt(i, 3)+"")));
             g2d.drawString(tax1+"", 80+Prient.transX, 190+i*30);
             g2d.drawString(table.getValueAt(i, 4)+"", 105+Prient.transX, 190+i*30);
        }
        g2d.setFont(new Font("Default", Font.PLAIN, 8)); 
        g2d.drawString("-------------------------------------------",0+Prient.transX, 200+i*30);
        g2d.drawString("购买件数: " + table.getValueAt(0,5), 0+Prient.transX, 210+i*30);  
        g2d.drawString("付款金额: " + paymoney, 0+Prient.transX, 220+i*30);
        g2d.drawString("税金: " + table.getValueAt(0,7), 0+Prient.transX, 230+i*30);
        g2d.drawString("-------------------------------------------", 0+Prient.transX, 240+i*30);  
        //g2d.drawString("时间: " + aString , 0, 120+i*20);
        g2d.drawString("欢迎您再次光临！", 0+Prient.transX, 250+i*30);
        g2d.drawString("-------------------------------------------",0+Prient.transX, 260+i*30);
        StringUtil.delete("D://lmtbarcode");
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
        book.append(new RepeatPrientGoods(), pf);  
  
        // 获取打印服务对象  
        PrinterJob job = PrinterJob.getPrinterJob();  
        job.setPageable(book);  
        PrinterJob job1 = PrinterJob.getPrinterJob();  
        job1.setPageable(book);
        try {  
            job.print();
            job1.print();
         // 清空table数据
//    		O2OMainMenu.memberNumber.setText("");
//    		DefaultTableModel model = (DefaultTableModel) O2OMainMenu.table
//    				.getModel();
//    		while (model.getRowCount() > 0) {
//    			model.removeRow(model.getRowCount() - 1);
//    		}
        } catch (PrinterException e) {
        	e.printStackTrace();
            System.out.println("================打印出现异常");  
        }  
  
    }  
  
}
