package com.sunvsoft.entity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingWorker;
import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sunvsoft.accessOnline.ConnectOnlineMethod;
import com.sunvsoft.backup.PropertiesUtil;
import com.sunvsoft.sqlset.SetSql;

/**    
 *            
 * 类描述：    
 * 创建人：Li Shilin    
 * 创建时间：2016-12-12 上午11:04:11    
 * 修改人：lsl    
 * 修改时间：2016-12-12 上午11:04:11    
 * 修改备注： 根据不同的角色的（店长，收银员）销售统计
 *     
 */
public class SaleStatistics {

    JFrame jFrameM = new JFrame("销售统计");
    JPanel jPanel = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JPanel jPanel5 = new JPanel();
    JPanel jPanel6 = new JPanel();
    JPanel jPanel7 = new JPanel();
    JPanel jPanel8 = new JPanel();
    JPanel jPanel9 = new JPanel();
    JPanel jPanel10 = new JPanel();
    JLabel jLabel = new JLabel("收银员:");
    JLabel jLabel1 = new JLabel("日期:");
        
    
    JLabel saleNumLable = new JLabel("卖货笔数:");
       
    JLabel totalLable = new JLabel("金额:");
    JLabel discountAmount = new JLabel("优惠金额:");
    //          JTextField cityField ;
    JLabel payLabel = new JLabel("结算方式");
    //          JTextField streetField ;
    JLabel cashLable = new JLabel("现金:");
    
    JLabel cardLabel = new JLabel("刷卡:");
    JLabel aliLabel = new JLabel("支付宝:");
    JLabel weiLabel = new JLabel("微信:");
    O2OMainMenu mm;
    static int num;
    static double total;
    static double cash;
    static double card;
    static double alipay;
    static double weipay;
    static double discount;
    @SuppressWarnings("static-access")
    public void init() {
        
    jLabel.setFont(new Font("宋体", Font.PLAIN, 26));
    jLabel.setForeground(Color.black);
    jLabel1.setFont(new Font("宋体", Font.PLAIN, 26));
    jLabel1.setForeground(Color.black);
    JButton addButton = new JButton("添加");
    JButton cancelButton = new JButton("取消");
    /**
     *  字体  
     */
    saleNumLable.setFont(new Font("黑体", Font.PLAIN, 16));
    saleNumLable.setForeground(Color.black);
    totalLable.setFont(new Font("黑体", Font.PLAIN, 16));
    totalLable.setForeground(Color.black);
    payLabel.setFont(new Font("黑体", Font.PLAIN, 16));
    payLabel.setForeground(Color.black);
    cashLable.setFont(new Font("黑体", Font.PLAIN, 16));
    cashLable.setForeground(Color.black);
    cardLabel.setFont(new Font("黑体", Font.PLAIN, 16));
    cardLabel.setForeground(Color.black);
    aliLabel.setFont(new Font("黑体", Font.PLAIN, 16));
    aliLabel.setForeground(Color.black);
    weiLabel.setFont(new Font("黑体", Font.PLAIN, 16));
    weiLabel.setForeground(Color.black);
    discountAmount.setFont(new Font("黑体", Font.PLAIN, 16));
    discountAmount.setForeground(Color.black);
    
    addButton.setFont(new Font("黑体", Font.PLAIN, 16));
    addButton.setForeground(Color.black);
    cancelButton.setFont(new Font("黑体", Font.PLAIN, 16));
    cancelButton.setForeground(Color.black);
    
    /**
     * 位置
     */
    jPanel4.setLayout(null);
    jLabel.setBounds(50, 30, 230, 30);
    jLabel1.setBounds(250, 30, 230, 30);
    discountAmount.setBounds(60,165,120,30);
    saleNumLable.setBounds(60, 90, 120, 30);
    totalLable.setBounds(60, 130, 120, 30);
    payLabel.setBounds(60, 200, 120, 30);
    cashLable.setBounds(60, 240, 120, 30);
    cardLabel.setBounds(60, 280, 120, 30);
    aliLabel.setBounds(60, 320, 120, 30);
    weiLabel.setBounds(60, 360, 120, 30);
    addButton.setBounds(80, 460, 80, 30);
    cancelButton.setBounds(250,460,80,30);
    /**
     * 布局
     */
    jPanel4.add(jLabel);
    jPanel4.add(jLabel1);
    jPanel4.add(saleNumLable);
    jPanel4.add(totalLable);
    jPanel4.add(discountAmount);
    jPanel4.add(payLabel);
    jPanel4.add(cashLable);
    jPanel4.add(cardLabel);
    jPanel4.add(aliLabel);
    jPanel4.add(weiLabel);
    jPanel4.add(addButton);
    jPanel4.add(cancelButton);
    
    jPanel.setLayout(new BorderLayout());
    jPanel.add(jPanel4, BorderLayout.CENTER);
    jFrameM.add(jPanel);
    
    jFrameM.setSize(500, 550);
    jFrameM.setLocationRelativeTo(null);// 窗口居中显示
    jFrameM.setResizable(false);// 不可改变大小
    jFrameM.setUndecorated(true); // 去掉窗口的装饰
    jFrameM.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);//采用指定的窗口装饰风格//简单对话框风格
    jFrameM.setDefaultCloseOperation(jFrameM.DO_NOTHING_ON_CLOSE);
    jFrameM.setVisible(true);
    ImageIcon icon = new ImageIcon(MemberAddress.class.getClassLoader()
            .getResource("images/YZF.png"));
    jFrameM.setIconImage(icon.getImage());
    addButton.addActionListener(new activationListener());
    cancelButton.addActionListener(new cancelActionListener());
    jFrameM.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
            jFrameM.dispose();
        }
    });
        
    String salesman = O2OMainMenu.operator.getText().toString();
    String role = null;
//    String role = "店长";
    if(!O2OMainMenu.internetPass){
        ResultSet rsForRole = new SetSql()
        .setSql("select realname as rolename from es_user where username = '"
                + salesman + "'");
        try {
            rsForRole.next();
            role = (rsForRole.getString("rolename"));
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
    }else{
        MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
        queryParam.add("username", salesman);
        String url = PropertiesUtil.getIp() + "/api/shop/member!getRole.do";
        ConnectOnlineMethod connect = new ConnectOnlineMethod();
        try {
            String outPut = connect.connectOnline(queryParam, url);
            JSONArray array = connect.jsonConvertion(outPut);
            if (array.equals("")) {
            }
            JSONObject jsonObject = connect.getJsonObject(array);
            role = jsonObject.getString("role_name");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    if(role.equals("店长")){
        dayStatistics();
    }else{
        classStatistics();
    }
    }
        
    public class activationListener implements ActionListener {
    
        @Override
        public void actionPerformed(ActionEvent e) {
             jFrameM.dispose();
        }
    
    }
    public class cancelActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
             jFrameM.dispose();
        }
        
    }
        /**        
         * @param   name    
         * @return String   
         * @Exception 异常对象       
         * 收银员角色统计
        */
        public void classStatistics(){
            
            //获取该收银员的班号
            ResultSet rs = new SetSql().setSql("select LAST_INSERT_ID(classes) as classes from es_classes");
            try {
                rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int classes = 0;
            try {
                classes = rs.getInt("classes");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            long timeNow = System.currentTimeMillis();
            String dateNow = new timeToDates().getTimeToDates(timeNow);
            //获取订单统计 
            String sql = "select count(sn) as counts ,sum(cardAmount) as card, sum(discountAmount) as discount,sum(round(cashAmount,1)) as cash,(select sum(paymoney) from es_order where payment_type ='微信支付' and sn like 'SO20%' and date = '"+dateNow+ "' and classes ="+classes+") as weipay,(select sum(paymoney) from es_order where payment_type = '支付宝支付' and sn like 'SO20%' and date = '"+dateNow+ "' and classes ="+classes+") as alipay from es_order where classes="+classes+"" + " AND `status` = '107' and date = '"+dateNow+ "' and sn like 'SO20%'"; 
            ResultSet s = new SetSql().setSql(sql); 
            ResultSet rss= new SetSql()
            .setSql("select"
            	   +" sum(discountAmount) AS discountAmount,"
            	   +" sum(if((payment_id = 4),discountAmount,0)) AS weidiscount,"
            	   +" sum(if((payment_id = 5),discountAmount,0)) AS alidiscount,"
            	   +" sum(if((payment_id = 6),discountAmount,0)) AS cashdiscount,"
            	   +" sum(if((payment_id = 7),discountAmount,0)) AS carddiscount"
            	   +" FROM es_order o where classes="+classes+"" + " AND "
            	   +" `status` = '107' and date = '"+dateNow+ "' and sn like 'SO20%'");
            try {
                while(s.next() && rss.next()){
                    num = s.getInt("counts");
                    card = s.getDouble("card")-rss.getDouble("carddiscount");
                    cash = s.getDouble("cash")-rss.getDouble("cashdiscount");
                    weipay = s.getDouble("weipay")-rss.getDouble("weidiscount");
                    alipay = s.getDouble("alipay")-rss.getDouble("alidiscount");
                    discount = s.getDouble("discount");
                    
                }
                total = cash+card+weipay+alipay;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String total1 = String.format("%.1f", total);
            String total2 = String.format("%.2f", Double.parseDouble(total1));
            jLabel.setText("收银员:"+O2OMainMenu.operator.getText());
            jLabel1.setText("日期:"+dateNow);
            saleNumLable.setText("买货笔数:"+num);
            totalLable.setText("金额:"+total2);
            discountAmount.setText("优惠金额:"+String.format("%.2f", discount));
            cashLable.setText("现金:"+String.format("%.2f", cash));
            cardLabel.setText("刷卡:"+String.format("%.2f", card));
            aliLabel.setText("支付宝:"+String.format("%.2f", alipay));
            weiLabel.setText("微信:"+String.format("%.2f", weipay));
        }
        /**        
         * @param   name    
         * @return String   
         * @Exception 异常对象       
         * 日结统计
        */
        public void dayStatistics(){

            
            long timeNow = System.currentTimeMillis();
            String dateNow = new timeToDates().getTimeToDates(timeNow);
            String time = dateNow+" 00:00:00";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = null;
            try {
                d = sdf.parse(time);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            long times = d.getTime()/1000;
            //获取订单统计 
            String sql = "select count(sn) as counts ,sum(cardAmount) as card,sum(discountAmount) as discount, sum(round(cashAmount,1)) as cash,(select sum(paymoney) from es_order where payment_type ='微信支付' and sn like 'SO20%' and create_time >="+times+") as weipay,(select sum(paymoney) from es_order where payment_type = '支付宝支付' and sn not like 'SO20%' and create_time >="+times+") as alipay from es_order where create_time>"+times+""+" AND `status` = '107' and date = '"+dateNow+ "' and sn like 'SO20%'"; 
            ResultSet s = new SetSql().setSql(sql);
            ResultSet rss= new SetSql()
            .setSql("select"
            	   +" sum(discountAmount) AS discountAmount,"
            	   +" sum(if((payment_id = 4),discountAmount,0)) AS weidiscount,"
            	   +" sum(if((payment_id = 5),discountAmount,0)) AS alidiscount,"
            	   +" sum(if((payment_id = 6),discountAmount,0)) AS cashdiscount,"
            	   +" sum(if((payment_id = 7),discountAmount,0)) AS carddiscount"
            	   +" FROM es_order o  where create_time>"+times+""+" "
            	   +" AND `status` = '107' and date = '"+dateNow+ "' and sn like 'SO20%'");
            try {
                while(s.next() && rss.next()){
                    
                    num = s.getInt("counts");
                    card = s.getDouble("card")-rss.getDouble("carddiscount");
                    cash = s.getDouble("cash")-rss.getDouble("cashdiscount");
                    weipay = s.getDouble("weipay")-rss.getDouble("weidiscount");
                    alipay = s.getDouble("alipay")-rss.getDouble("alidiscount");
                    discount = s.getDouble("discount");
                }
                total = cash+card+weipay+alipay;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            String total1 = String.format("%.1f", total);
            String total2 = String.format("%.2f", Double.parseDouble(total1));
            jLabel.setText("店长:"+O2OMainMenu.operator.getText());
            jLabel1.setText("日期:"+dateNow);
            saleNumLable.setText("买货笔数:"+num);
            totalLable.setText("金额:"+total2);
            discountAmount.setText("优惠金额:"+String.format("%.2f", discount));
            cashLable.setText("现金:"+String.format("%.2f", cash));
            cardLabel.setText("刷卡:"+String.format("%.2f", card));
            aliLabel.setText("支付宝:"+String.format("%.2f", alipay));
            weiLabel.setText("微信:"+String.format("%.2f", weipay));
        }
    //添加地址监听器方法
//    public static void main(String[] args) {
////        SaleStatistics a = new SaleStatistics();
//    //                  System.out.println(a.getRegions());
////        a.init();
//        long timeNow = System.currentTimeMillis();
//        String dateNow = new timeToDates().getTimeToDates(timeNow);
//        System.out.println(dateNow);
//    }
}

