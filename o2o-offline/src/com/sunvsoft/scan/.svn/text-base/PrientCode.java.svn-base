package com.sunvsoft.scan;

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
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;

import com.sunvsoft.backup.PropertiesUtil;
  
public class PrientCode extends Thread implements Printable{  
	public static String goodsName;
	public static String goodsPrice;
	public static int transX = PropertiesUtil.getXTrans();
	 
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String aString = sdf.format(date);
	
    @Override  
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {  
//  
    	ImageIcon image = new ImageIcon("Code.png");
    	Image image2 = image.getImage();  
        Graphics2D g2d = (Graphics2D) g; 
      
        g2d.setFont(new Font("Default", Font.PLAIN, 8)); 
        if(goodsName.length()>15){
            goodsName = goodsName.substring(0,15);
        }
        g2d.drawString("商品名:"+goodsName, 0+transX, 20);
        g2d.drawString("价   格："+goodsPrice, 0+transX, 40);
        g2d.drawString("--------------------------------------------", 0+transX, 60);
        g2d.drawImage(image2,5+transX,80,100,100,null);
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
        book.append(new PrientCode(), pf);  
  
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
