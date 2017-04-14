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

public class PrientDay extends Thread implements Printable {
    static O2OMainMenu mm = null;
    static String dateNow = null;
    static String cardAmount;
    static String cashAmount;
    static String paymoney;
    static String paymoney1;
    static String weipayAmount = "0.00";
    static String alipayAmount = "0.00";
    static String countOrder ;
    static double discountAmount;
    double paymoneyshl = Double.parseDouble(paymoney)+Double.parseDouble(regectedCashAmount);
    String cashAmount1 = String.format("%.1f", Double.parseDouble(cashAmount));
    double cashAmount2s = Double.parseDouble(String.format("%.2f", Double.parseDouble(cashAmount1)));
    String cashAmount2 = String.format("%.2f", cashAmount2s);
    public static Object[][] data4;
    private Object[][] data;
//  private JTable table;
    public static String regectedCashAmount;
    public static String cashActual;
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String aString = sdf.format(date);
    double orderamount = Double.parseDouble(paymoney)-Double.parseDouble(regectedCashAmount);
    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

//  public JTable getTable() {
//      return table;
//  }
//
//  public void setTable(JTable table) {
//      this.table = table;
//  }


    @SuppressWarnings({ "static-access", "unused" })
    @Override
    public int print(Graphics g, PageFormat pf, int page)
            throws PrinterException {

        // if (page > 0) {
        // return NO_SUCH_PAGE;
        // }

        ImageIcon image = new ImageIcon(Prient.class.getClassLoader()
                .getResource("images/icon.png"));
        Image image2 = image.getImage();
        // Toolkit tool = this.getToolkit();
        // Image image = new Image
        Graphics2D g2d = (Graphics2D) g;

        g2d.setFont(new Font("Default", Font.PLAIN, 8));
        g2d.drawImage(image2, 5+Prient.transX,5,105,35, null);
        g2d.drawString("店铺名称："+new PropertiesUtil().getStoreName(),0+Prient.transX,90);
        g2d.drawString("日结:" + dateNow, 0+Prient.transX, 100);
        // g2d.drawString(dateNow, 30, 50);
        g2d.drawString("-------------------------------------------", 0+Prient.transX, 120);
//        g2d.drawString("商品名", 0+Prient.transX, 100);
//        g2d.drawString("数量", 30+Prient.transX, 100);
//        g2d.drawString("单价", 55+Prient.transX, 100);
//        g2d.drawString("小计", 80+Prient.transX, 100);
        int i = 0;
//        for (i = 0; i < data.length; i++) {
//            String goodsNames = data[i][2] + "";
//            String goodsName = "";
//            String goodsName1 = "";
//            int goodsLength = goodsNames.length();
////            if(goodsNames.length()<17){
////                goodsName = goodsNames;
////                g2d.drawString(goodsName, 0+Prient.transX, 110+i*20);
////                g2d.drawString(mm.data[i][7]+"*"+mm.data[i][6], 30+Prient.transX, 130+i*20);
////                g2d.drawString(mm.data[i][8]+"", 80+Prient.transX, 130+i*20);
////            }else{
////                int goodsLength = goodsNames.length();
////                goodsName = goodsNames.substring(0, 17);
////                if(goodsLength>34){
////                    goodsName1 = goodsNames.substring(17,34);
////                }else{
////                    goodsName1 = goodsNames.substring(17, goodsLength);
////                }
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
//            g2d.drawString(goodsName, 0+Prient.transX, 110+i*30);
//            g2d.drawString(goodsName1,0+Prient.transX, 120+i*30);
//            g2d.drawString(mm.data[i][7]+"*"+mm.data[i][6], 30+Prient.transX, 130+i*30);
//            g2d.drawString(mm.data[i][8]+"", 80+Prient.transX, 130+i*30);
//        }
        Double weipayAmountDouble = Double.parseDouble(weipayAmount);
        Double alipayAmountDouble = Double.parseDouble(alipayAmount);
        Double cashActualDouble = Double.parseDouble(cashActual);
        Double cardAmountDouble = Double.parseDouble(cardAmount);
        Double sum = weipayAmountDouble+alipayAmountDouble+cashActualDouble+cardAmountDouble;
        DecimalFormat df = new DecimalFormat("######0.00");
        String sums = df.format(sum);
        g2d.drawString("商品数量: " + mm.totalNumberLabelChange.getText(), 0+Prient.transX,
                140 + i * 30);
        g2d.drawString("合计: " + paymoney, 50+Prient.transX,
                140 + i * 30);
        g2d.drawString("交易单数: " + countOrder, 0+Prient.transX, 150+i*30);
        g2d.drawString("刷卡: " + cardAmount, 0+Prient.transX, 160+i*30);
        g2d.drawString("现金: " + cashAmount2, 0+Prient.transX, 170+i*30);
        g2d.drawString("微信: " + weipayAmount, 0+Prient.transX, 180+i*30);
        g2d.drawString("支付宝: " + alipayAmount, 0+Prient.transX, 190+i*30);
        g2d.drawString("-------------------------------------------", 0+Prient.transX,
                200 + i * 30);
         int j = 0;
           
            if(data4.length>0){
                g2d.drawString("退货明细", 0+Prient.transX, 210+i*30);
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
                    g2d.drawString(goodsName, 0+Prient.transX, 220+j*30+i*30);
                    g2d.drawString(goodsName1, 0+Prient.transX, 230+j*30+i*30);
                    g2d.drawString(data4[j][8]+"*"+data4[j][7], 30+Prient.transX, 240+j*30+i*30);
                    g2d.drawString(data4[j][9]+"", 80+Prient.transX, 240+j*30+i*30);
                    }
               g2d.drawString("退货总额:"+regectedCashAmount, 0+Prient.transX, 250+j*30+i*30);
               g2d.drawString("-------------------------------------------",0+Prient.transX, 260+i*30+j*30);
               g2d.drawString("总计金额: " + orderamount, 0+Prient.transX, 270+i*30+j*30);
               g2d.drawString("实收金额: " + paymoney1, 0+Prient.transX, 280+i*30+j*30);
               g2d.drawString("优惠合计: " + discountAmount, 0+Prient.transX, 290+i*30+j*30);
               g2d.drawString("打印时间: " + aString , 0+Prient.transX, 300+i*30+j*30);
               
               g2d.drawString("-------------------------------------------",0+Prient.transX, 310+i*30+j*30);
           }else{
            g2d.drawString("总计金额: " + orderamount, 0+Prient.transX, 220+i*30);
            g2d.drawString("实收金额: " + paymoney1, 0+Prient.transX, 230+i*30+j*30);
            g2d.drawString("优惠合计: " + discountAmount, 0+Prient.transX, 240+i*30+j*30);
            g2d.drawString("打印时间: " + aString , 0+Prient.transX, 250+i*30); 
            g2d.drawString("-------------------------------------------",0+Prient.transX, 260+i*30);
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
        p.setSize(160, 1000);
        p.setImageableArea(0, 0, 160, 1000); // (,上边距,,)
        pf.setPaper(p);

        // 把 PageFormat 和 Printable 添加到书中，组成一个页面
        book.append(this, pf);

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
