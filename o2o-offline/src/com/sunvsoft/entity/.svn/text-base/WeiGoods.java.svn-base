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
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.sunvsoft.backup.PropertiesUtil;
  
public class WeiGoods extends Thread implements Printable{  
     //O2OMainMenu mm = null;
//  private Properties prop1 = new Properties();
    static DecimalFormat df = new DecimalFormat("0.00");
     static int row =0;
     static String sn ="";
     static double tax = 0.00;
     static String totalNumber = null;
     static String totalMoney = null;
    static JTable table = null;
    static String count1 = null;
    static String discountAmount;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String aString = sdf.format(date);
    
    @SuppressWarnings("static-access")
    @Override  
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException { 
//      try {
//          prop1.load(DataOutAndIn.class.getClassLoader().getResourceAsStream(
//                  "jdbc.properties"));
//      } catch (IOException e2) {
//          e2.printStackTrace();
//      }
        String posNumString = PropertiesUtil.getPosNum();
        String lsh = PropertiesUtil.getPosNum()+0+0+count1;
    	ImageIcon image = new ImageIcon(Prient.class.getClassLoader()
				.getResource("images/icon.png"));
    	Image image2 = image.getImage(); 
        Graphics2D g2d = (Graphics2D) g; 
        g2d.setFont(new Font("Default", Font.PLAIN, 8)); 
        g2d.drawImage(image2,5+Prient.transX,5,105,35,null);
        g2d.drawString("店铺名称："+new PropertiesUtil().getStoreName(),0+Prient.transX,70);        
        g2d.drawString("终端编号:"+posNumString,0+Prient.transX, 80);
        g2d.drawString("流水号:"+lsh,0+Prient.transX,90);
        g2d.drawString("日期:"+aString, 0+Prient.transX, 100);
        g2d.setFont(new Font("Default", Font.PLAIN, 6));
        ImageIcon image5 = new ImageIcon("D:\\lmtbarcode\\"+sn+".png");
        Image image6 = image5.getImage();
        g2d.drawImage(image6,5,110,140,40,null);
        g2d.setFont(new Font("Default", Font.PLAIN, 8)); 
        g2d.drawString("商品名", 0+Prient.transX, 160);
        g2d.drawString("数量*单价", 30+Prient.transX, 160);
        g2d.drawString("税金", 70+Prient.transX, 160);
       // g2d.drawString("单价", 55, 80);
        g2d.drawString("金额", 95+Prient.transX, 160);
        g2d.drawString("-----------------------------------------------", 0+Prient.transX, 170);
        int i =0;
        for(i=0;i<row;i++){
            String goodsNames = O2OMainMenu.table.getValueAt(i, 2)+"";
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
//               goodsName = goodsNames.substring(0, 17);
//               goodsName1 = goodsNames.substring(17,goodsLength);
            	 g2d.setFont(new Font("Default", Font.PLAIN, 5));
                 g2d.drawString(goodsName, 0+Prient.transX, 180+i*30);
                 g2d.drawString(goodsName1, 0+Prient.transX, 190+i*30);
                 g2d.drawString(table.getValueAt(i, 8)+"*"+table.getValueAt(i, 7), 30+Prient.transX, 200+i*30);
                 String tax1 =df.format(new BigDecimal(Double.parseDouble(table.getValueAt(i, 6)+"")*Double.parseDouble(table.getValueAt(i, 8)+"")));
                 g2d.drawString(tax1+"", 70+Prient.transX, 200+i*30);
                 //g2d.drawString(table.getValueAt(i, 5)+"", 50, 90+i*20);
                 g2d.drawString(table.getValueAt(i, 9)+"", 90+Prient.transX, 200+i*30);
        }
        g2d.setFont(new Font("Default", Font.PLAIN, 8));
        g2d.drawString("-----------------------------------------------",0+Prient.transX, 210+i*30);
        g2d.drawString("购买件数: " + totalNumber, 0+Prient.transX, 220+i*30);  
        g2d.drawString("微信支付: " + totalMoney, 0+Prient.transX, 230+i*30);
        g2d.drawString("优惠合计: " + discountAmount, 0+Prient.transX, 240+i*30);
        g2d.drawString("-----------------------------------------------", 0+Prient.transX, 250+i*30);  
        //g2d.drawString("时间: " + aString , 0, 120+i*20);
        g2d.drawString("欢迎您再次光临！", 0+Prient.transX, 260+i*30);
        g2d.drawString("-----------------------------------------------",0+Prient.transX, 270+i*30);
        return PAGE_EXISTS; 
//        Graphics2D g2d = (Graphics2D) g; 
//      
//        g2d.setFont(new Font("Default", Font.PLAIN, 10));  
//        g2d.drawString("小票",0, 30);  
//        g2d.drawString("--------------------------------", 0, 45); 
//        int i =0;
//        for(i=0;i<row;i++){
//          g2d.drawString(i+1+"."+table.getValueAt(i, 3)+"--"+table.getValueAt(i, 5)+"--"+table.getValueAt(i, 6), 0, 55+i*30);
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
        book.append(new WeiGoods(), pf);  
  
        // 获取打印服务对象  
        PrinterJob job = PrinterJob.getPrinterJob();  
        job.setPageable(book);  
        PrinterJob job1 = PrinterJob.getPrinterJob();  
        job1.setPageable(book);
        try {  
            job.print();
            job1.print();
        } catch (PrinterException e) {
            e.printStackTrace();
            System.out.println("================打印出现异常");  
        }  finally {
            // 清空table数据
            O2OMainMenu.memberNumber.setText("");
            DefaultTableModel model = (DefaultTableModel) O2OMainMenu.table
                    .getModel();
            while (model.getRowCount() > 0) {
                model.removeRow(model.getRowCount() - 1);
            }
            
        }
  
    }  
    public static void main(String[] args) {
        WeiGoods wg=new WeiGoods();
        wg.start();
    }
  
}
