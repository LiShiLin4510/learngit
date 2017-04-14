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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;

import com.sunvsoft.backup.PropertiesUtil;
  
public class Prient extends Thread implements Printable{  
    static O2OMainMenu mm = null;
    static String cardAmount;
    static String cashAmount;
    static String paymoney;
    static String paymoney1;
    static String price;
    static int nums;
    static String orderAmount;
    static String weipayAmount = "0.00";
    static String alipayAmount = "0.00";
    static String discountAmount = "0.00";
    public static Object[][] data4;
    public static String regectedCashAmount;
    public static String cashActual;
    public static String countOrder;
    public static int transX = PropertiesUtil.getXTrans();
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    String aString = sdf.format(date);
    
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    String aString1 = sdf.format(date);
    @SuppressWarnings({ "static-access", "unused" })
    @Override  
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {  
//  
//        if (page > 0) {  
//            return NO_SUCH_PAGE;  
//        }  
        ImageIcon image = new ImageIcon(Prient.class.getClassLoader()
                .getResource("images/icon.png"));
        Image image2 = image.getImage();  
        //Toolkit tool = this.getToolkit();
        //Image image = new Image
        Graphics2D g2d = (Graphics2D) g; 
        g2d.setFont(new Font("Default", Font.PLAIN, 9)); 
//        g2d.drawImage(image2,5+transX,15,135,55,null);
        g2d.drawImage(image2,5+Prient.transX,5,105,35,null);
        g2d.drawString("店铺名称："+new PropertiesUtil().getStoreName(),0+transX,90);
        g2d.drawString("收银员:"+ mm.operator.getText(),0+transX, 100);  
        g2d.drawString("系统班结:"+aString1,0+transX, 110); 
        g2d.drawString("--------------------------------------", 0+transX, 120);
//        g2d.drawString("商品名", 0+transX, 100);
//        g2d.drawString("数量", 30+transX, 100);
//        g2d.drawString("单价", 55+transX, 100);
//        g2d.drawString("小计", 80+transX, 100);
        int i =0;
//        for(i=0;i<mm.table.getRowCount();i++){
//            String goodsNames = mm.data[i][2]+"";
//            String goodsName = "";
//            String goodsName1 = "";
//            int goodsLength = goodsNames.length();
//            if(goodsLength<17){
//                goodsName = goodsNames.substring(0, goodsLength);
//                goodsName1 = "";
//            }else{
//                goodsName = goodsNames.substring(0, 17);
//                if(goodsLength>34){
//                    goodsName1 = goodsNames.substring(17, 34);
//                }else{
//                    goodsName1 = goodsNames.substring(17, goodsLength);
//                }
//            }
//                g2d.drawString(goodsName, 0+transX, 110+i*30);
//                g2d.drawString(goodsName1,0+transX, 120+i*30);
//                g2d.drawString(mm.data[i][7]+"*"+mm.data[i][6], 30+transX, 130+i*30);
//                g2d.drawString(mm.data[i][8]+"", 80+transX, 130+i*30);
//        }
        
        Double weipayAmountDouble = Double.parseDouble(weipayAmount);
        Double alipayAmountDouble = Double.parseDouble(alipayAmount);
        Double cashActualDouble = Double.parseDouble(cashActual);
        Double cardAmountDouble = Double.parseDouble(cardAmount);
        Double sum = weipayAmountDouble+alipayAmountDouble+cashActualDouble+cardAmountDouble;
        DecimalFormat df = new DecimalFormat("######0.00");
        String sums = df.format(sum);

        g2d.drawString("商品数量: " + nums, 0+transX, 150+i*30);  
        g2d.drawString("合计: " + orderAmount, 60+transX, 150+i*30);
        g2d.drawString("交易单数: " + countOrder, 0+transX, 160+i*30);
        g2d.drawString("刷卡: " + cardAmount, 0+transX, 170+i*30);
        g2d.drawString("现金: " + cashAmount, 0+transX, 180+i*30);
        g2d.drawString("微信: " + weipayAmount, 0+transX, 190+i*30);
        g2d.drawString("支付宝: " + alipayAmount, 0+transX, 200+i*30);
        g2d.drawString("--------------------------------------", 0+transX, 210+i*30);
        int j = 0;
       
        if(data4.length>0){
            g2d.drawString("退货明细", 0+transX, 220+i*30);
            for(j=0;j<data4.length;j++){
                String goodsNames = data4[j][2]+"";
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
                g2d.drawString(goodsName, 0+transX, 230+j*30+i*30);
                g2d.drawString(goodsName1, 0+transX, 240+j*30+i*30);
                g2d.drawString(data4[j][8]+"*"+data4[j][7], 30+transX, 250+j*30+i*30);
                g2d.drawString(data4[j][9]+"", 80+transX, 250+j*30+i*30);
            
           }
           g2d.drawString("退货总额:"+regectedCashAmount, 0+transX, 250+j*30+i*30);
           g2d.drawString("--------------------------------------",0+transX, 260+i*30+j*30);
           g2d.drawString("总计金额: " + paymoney, 0+transX, 270+i*30+j*30);
           g2d.drawString("实收金额: " + paymoney1, 0+Prient.transX, 280+i*30+j*30);
           g2d.drawString("优惠金额: " + discountAmount, 0+Prient.transX, 290+i*30+j*30);
           g2d.drawString("打印时间: " + aString , 0+transX, 300+i*30+j*30); 
           g2d.drawString("--------------------------------------",0+transX, 310+i*30+j*30);
       }else{
        g2d.drawString("总计金额: " + paymoney, 0+transX, 220+i*30);
        g2d.drawString("实收金额: " + paymoney1, 0+Prient.transX, 230+i*30+j*30);
        g2d.drawString("优惠金额: " + discountAmount, 0+Prient.transX, 240+i*30+j*30);
        g2d.drawString("打印时间: " + aString , 0+transX, 250+i*30); 
        g2d.drawString("--------------------------------------",0+transX, 260+i*30);
       }
        return PAGE_EXISTS;  
    }  
  
    public void run() { 
  
       
  
        // 通俗理解就是书、文档  
        Book book = new Book();  
  
        // 打印格式  
        PageFormat pf = new PageFormat();  
        pf.setOrientation(PageFormat.PORTRAIT);  
  
        // 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。  
        Paper p = new Paper();  
        p.setSize(160,1000);  
        p.setImageableArea(0, 0, 160, 1000);  
        pf.setPaper(p);  
  //打印选择调整
//       PageFormat page = PrinterJob.getPrinterJob().pageDialog((PrintRequestAttributeSet) null);
//        page .getPaper();
        
        // 把 PageFormat 和 Printable 添加到书中，组成一个页面  
        book.append(new Prient(), pf);  
  
        // 获取打印服务对象  
        PrinterJob job = PrinterJob.getPrinterJob();
      
        job.setPageable(book);  
        try {  
            job.print();  
        } catch (PrinterException e) {  
            System.out.println("================打印出现异常");  
        }  
  
    }  
  
}
