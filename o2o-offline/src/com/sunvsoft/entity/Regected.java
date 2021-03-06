package com.sunvsoft.entity;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sunvsoft.accessOnline.ConnectOnlineMethod;
import com.sunvsoft.backup.DoBackup;
import com.sunvsoft.backup.PropertiesUtil;
import com.sunvsoft.order.OrderSellbackAutoCommit;
import com.sunvsoft.scan.GenerateBarCode;
import com.sunvsoft.sqlset.SetSql;
import com.sunvsoft.util.Sequence;
import com.sunvsoft.util.StringUtil;

public class Regected {
    DefaultTableModel model;
    O2OMainMenu rr = null;
    JFrame jf = new JFrame("退货");
    JLabel jl = new JLabel("密  码：");
    JLabel j2 = new JLabel("用户名：");
    JTextField jtf = new JTextField();
    JPasswordField jpf = new JPasswordField();
    JButton jb = new JButton("确定");
    JPanel jPanel = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JLabel jLabel = new JLabel("退   货");
    DecimalFormat df = new DecimalFormat("0.00");
    static O2OMainMenu o2o;
    
    @SuppressWarnings("static-access")
    public void init(){
        jLabel.setFont(new Font("宋体", Font.PLAIN, 26));
        jLabel.setForeground(Color.red);
        /**
         *  字体  
         */
        jl.setFont(new Font("黑体", Font.PLAIN, 16));
        jl.setForeground(Color.black);
        j2.setFont(new Font("黑体", Font.PLAIN, 16));
        j2.setForeground(Color.black);
        
        jb.setFont(new Font("黑体", Font.PLAIN, 16));
        jb.setForeground(Color.black);
        
        /**
         * 位置
         */
        jPanel4.setLayout(null);
        jLabel.setBounds(210, 30, 230, 30);
        j2.setBounds(130, 80, 230, 30);
        jtf.setBounds(190, 80, 180, 30);
        jl.setBounds(130, 150, 230, 30);
        jpf.setBounds(190, 150, 180, 30);
        jb.setBounds(220, 210, 80, 30);
        /**
         * 布局
         */
        jPanel4.add(jLabel);
        jPanel4.add(j2);
        jPanel4.add(jtf);
        jPanel4.add(jl);
        jPanel4.add(jpf);
        jPanel4.add(jb);
        
        jPanel.setLayout(new BorderLayout());
        jPanel.add(jPanel4, BorderLayout.CENTER);
        jf.add(jPanel);
        
        jf.setSize(500, 300);
        jf.setLocationRelativeTo(null);// 窗口居中显示
        jf.setResizable(false);// 不可改变大小
        jf.setUndecorated(true); // 去掉窗口的装饰
        jf.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);//采用指定的窗口装饰风格//简单对话框风格
        jf.setDefaultCloseOperation(jf.DO_NOTHING_ON_CLOSE);
        ImageIcon icon = new ImageIcon(Regected.class.getClassLoader()
                .getResource("images/YZF.png"));
        jf.setIconImage(icon.getImage());
        jf.setVisible(true);
        
        jb.addActionListener(new jbListener());
        jpf.addKeyListener(new jtpfListener());
        jtf.addKeyListener(new jtpfListener());
        jf.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                jf.dispose();

            }

        });
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            public void eventDispatched(AWTEvent event) {
                if (((KeyEvent) event).getID() == KeyEvent.KEY_PRESSED) {

                    // 放入自己的键盘监听事件KEY_PRESSED
                    // ((KeyEvent) event).getKeyCode();// 获取按键的code
                    // ((KeyEvent) event).getKeyChar();// 获取按键的字符
                    // if (((KeyEvent) event).getID() == KeyEvent.VK_F1){
                    // System.out.println("sss");
                    // }
                    switch (((KeyEvent) event).getKeyCode()) {
                    case KeyEvent.VK_F3:
                        if(jtf.isFocusOwner()){
                            jpf.requestFocus();
                        }else{
                            jtf.requestFocus();
                        }
                        
                        break;
                    
                    default:
                        break;
                    }
                }
            }
        }, AWTEvent.KEY_EVENT_MASK);
    }
    public class jbListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            regectedMethod();
        }
        
    }
    public class jtpfListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
            
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
                 jf.dispose();
             }
            if(e.getKeyCode()==KeyEvent.VK_ENTER){
                regectedMethod();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            
        }
        
    }
    @SuppressWarnings({ "rawtypes", "unchecked", "static-access", "unused" })
    public void regectedMethod(){
        String sellBackOrders = "";//同步的退货订单#会员#sn
        String itemsProduct_ids = "";//同步的对货明细产品id，#分隔
        String username = jtf.getText();
        @SuppressWarnings("deprecation")
        String passwords = jpf.getText();
        int rows = rr.table.getRowCount();
        int rowss = rr.table.getSelectedRowCount();
        String num = null;
        int product_id = 0;
        int goods_id = 0;
        String sn2 = "";
        String name = null;
        Double price = (double) 0;

        String cardNumber = rr.memberNumber
                .getText();
        long create_time = System.currentTimeMillis() / 1000;
        // long order_id = System.currentTimeMillis();
        long order_id = Sequence.nextId();
        Double goods_amount = 0.00;
        int goods_num = 0;
        int rrss[] = o2o.table.getSelectedRows();
        for(int i =0;i<o2o.table.getSelectedRowCount();i++){
            goods_num = goods_num + Integer.parseInt(o2o.table.getValueAt(rrss[i], 8).toString());
            goods_amount = goods_amount + Double.parseDouble(o2o.table.getValueAt(rrss[i], 9).toString());
        }
        int status = OrderStatus.ORDER_CANCEL_PAY;
        String user_name = rr.operator.getText();
        Double order_amount = goods_amount;
        String currency = "CNY";
        String snSt = (String) rr.table.getValueAt(0, 2);
        ResultSet orderParent = new SetSql().setSql("select order_id from es_order where sn = '"+rr.documentNumber.getText()+"'");
        int parent_id = 0;
        try {
             orderParent.next();
             parent_id = orderParent.getInt("order_id");
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        //pos机和店铺id
        String store_id = PropertiesUtil.getStoreId();
        int classes = 1;
        ResultSet rsResultSet = new SetSql().setSql("select * from es_classes");
        try {
            rsResultSet.next();
            classes = rsResultSet.getInt("classes");
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        int balance_status = 1;
        long timeNow = System.currentTimeMillis();
        String dateNow = new timeToDates().getTimeToDates(timeNow);
        String sr3 = new order_sn().srGet();
        String sn3 = null;
        //判断联网状态，internetpass=true为联网状态
      //生成退货记录之后，再将退货的商品添加到订单列表中
        
//        sn3 = doPayForReject();
        //脱机模式下订单退货流程
        if(!O2OMainMenu.internetPass){
            if(passwords.length()>0){
                
                ResultSet rs = new SetSql().setSql("select password,realname as rolename from es_user where username = '"+username+"'");
                String password = "";
                try {
                    if(rs.next()){
                    password = rs.getString("password");
                    String role = rs.getString("rolename");
                    
                    String getPassword = new String(passwords);
                    getPassword = StringUtil.md5(getPassword);//加密
                    if(role.equals("店长")){
                        if(getPassword.equals(password)){

                            /**
                             * 退款
                             * */
            
                            if (rr.table.getRowCount() != 0) {
                                
//                              sn3 = rr.documentNumber.getText();
                                boolean a1 = new SetSql().setSqlNotReturn("insert into es_sellback_list(id,tradeno,tradestatus,ordersn,regtime,alltotal_pay,member_id,total) values('"+order_id+"','"+sr3+"','100','"+rr.documentNumber.getText()+"','"+create_time+"','"+goods_amount+"','"+cardNumber+"','"+goods_num+"')");
                                String sellBackOrder = "insert into es_sellback_list(tradeno,tradestatus,ordersn,regtime,alltotal_pay,total,store_id) values("+"UPPER('"+sn3+"')"+",'101',UPPER('"+rr.documentNumber.getText()+"'),'"+create_time+"','"+goods_amount+"','"+goods_num+"',"+PropertiesUtil.getStoreId()+")#"+cardNumber+"#"+rr.documentNumber.getText();
                                sellBackOrders = sellBackOrder;
                                if(a1){
                                    new DoBackup().realTimeBackup("insert into es_sellback_list(id,tradeno,tradestatus,ordersn,regtime,alltotal_pay,member_id,total) values('"+order_id+"','"+sr3+"','100','"+rr.documentNumber.getText()+"','"+create_time+"','"+goods_amount+"','"+cardNumber+"','"+goods_num+"');");
            
                                }
                                //获取订单
                                String sn3s = rr.documentNumber.getText();
                                boolean c = false;
                                boolean c1 = false;
                                boolean aa = false;
                                int trc1 = o2o.table.getSelectedRowCount();
                                int trc2 = o2o.table.getRowCount();
                                if(trc1<trc2){
                                    int rrs[] = o2o.table.getSelectedRows();
                                    for(int i =0;i<o2o.table.getSelectedRowCount();i++){
//                                        goods_num = goods_num + Integer.parseInt(o2o.table.getValueAt(rrs[i], 8).toString());
                                        String sql = "select num from es_order_items where sn = '"+sn3s+"' and product_id = "+o2o.table.getValueAt(rrs[i], 0)+"";
                                        ResultSet rs1 = new SetSql().setSql(sql);
                                        //原订单明细的数量
                                        int goods_num1 = 0;
                                        try {
                                            rs1.next();
                                            goods_num1 = rs.getInt("num");
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        //nums为退货的数量，goods_nums1-nums 的值为未退货的商品数量
                                        int nums = Integer.parseInt(o2o.table.getValueAt(rrs[i], 8)+"");
                                        //判断原订单 的订单明细的数量和要退货的商品明细的数量是否相等，如果相等直接更新订单的状态为全部退货（112）明细的状态为103，否则新增一条订单明细为103 的状态，订单明细的数量为用户选择的数量（nums） 其中goods_nums1为订单明细的原数量，
                                        if(nums==goods_num1){//订单明细的数量不变的情况下 ，只更新订单明细的状态
                                            c1 = new SetSql().setSqlNotReturn("update es_order_items set state = 103  where  sn = '"+sn3s+"' and product_id = "+o2o.table.getValueAt(rrs[i], 0)+"");
//                                            MultivaluedMap<String, String> mapss = new MultivaluedMapImpl();
//                                            mapss.add("sn3",sn3s+"");
//                                            mapss.add("product_id",o2o.table.getValueAt(rrs[i], 0)+"");
//                                            String urlss = PropertiesUtil.getIp() + "/api/shop/order!updateOrderStatus.do";
//                                            ConnectOnlineMethod connect = new ConnectOnlineMethod();
//                                                String strs = null ;
//                                                try {
//                                                    strs = connect.connectOnline(mapss, urlss);
//                                                } catch (IOException e) {
//                                                    e.printStackTrace();
//                                                }
//                                                if(strs.length()!=29){
//                                                    JOptionPane.showMessageDialog(jf, "订单明细更新失败");
//                                                }
                                        }else{//订单明细的数量发生改变，会把原来的订单明细拆分为两个明细，其中一个明细为选择退货后的退货明细，状态为103，
                                            String sqlss = "select * from es_order_items where sn = '"+sn3s+"' and product_id = "+o2o.table.getValueAt(rrs[i], 0)+"";
                                            ResultSet rss = new SetSql().setSql(sqlss);
                                            try {
                                                rss.next();
                                                //把原来的订单明细拆分为两个明细，一下代码为新增已退货的订单明细，状态103，数量为扣除退货明细后的数量
                                                new SetSql().setSqlNotReturn("insert into es_order_items(order_id,goods_id,product_id," +
                                                        "num,bar_code,name,price,currency,sn,state,taxes,spec_value,tax_system,unit) values("
                                                        + rss.getInt("order_id")+ ","+ rss.getInt("goods_id")+ ","+ rss.getInt("product_id")+ ","+ nums+ ",'"
                                                        + rss.getString("bar_code")+ "','"+ rss.getString("name") + "'," + rss.getDouble("price") + ",'" + rss.getString("currency") + "','"
                                                        + rss.getString("sn") + "',103,"+rss.getDouble("taxes")+",'"+rss.getString("spec_value")+"',"+rss.getInt("tax_system")+",'"+rss.getString("unit")+"')");
                                                if(O2OMainMenu.internetPass){
                                                    MultivaluedMap<String, String> maps = new MultivaluedMapImpl();
                                                    maps.add("order_id",rss.getInt("order_id")+"");
                                                    maps.add("store_id", store_id+"");
                                                    maps.add("goods_id", rss.getInt("goods_id")+"");
                                                    maps.add("product_id",rss.getInt("product_id")+"");
                                                    maps.add("num",nums+"");
                                                    maps.add("bar_code", rss.getString("bar_code")+"");
                                                    maps.add("name",rss.getString("name")+"");
                                                    maps.add("price", rss.getDouble("price")+"");
                                                    maps.add("currency", rss.getString("currency")+"");
                                                    maps.add("sn3", rss.getString("sn"));
                                                    maps.add("cat_id", 0+"");
                                                    maps.add("state", 103+"");
                                                    maps.add("taxes", rss.getDouble("taxes")+"");
                                                    maps.add("thumbnail","");
                                                    String urlss = PropertiesUtil.getIp() + "/api/shop/order!orderItemByOffline.do";
                                                    ConnectOnlineMethod connect = new ConnectOnlineMethod();
                                                    try {
                                                        String str  = connect.connectOnline(maps, urlss);
                                                        if(str.length()!=29){
                                                            JOptionPane.showMessageDialog(jf, "订单明细上传线上失败");
                                                        }
                                                        boolean backUpItems = new SetSql()
                                                        .setSqlNotReturn("update es_order_items set export_status = 1 where sn = '" + sn3 + "'");
                                                        if (backUpItems) {
                                                            new DoBackup().realTimeBackup("update es_order_items set export_status = 1 where sn = '" + sn3
                                                                    + "'");
                                                        }
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }else{
                                                    if(O2OMainMenu.internetPass){
                                                        JOptionPane.showMessageDialog(jf, "订单明细上传线上失败");
                                                    }
                                                }
                                                //把原订单明细经过退货拆分后剩余的订单明细，此时将这部分明细的数量更新未退货数量 （101为已完成状态）
                                                aa = new SetSql().setSqlNotReturn("update es_order_items set num="+(goods_num1-nums)+"  where  sn = '"+sn3s+"' and product_id = "+o2o.table.getValueAt(rrs[i], 0)+" and state = 101");
                                                MultivaluedMap<String, String> mapss = new MultivaluedMapImpl();
//                                                mapss.add("sn3",sn3s+"");
//                                                mapss.add("product_id",o2o.table.getValueAt(rrs[i], 0)+"");
//                                                mapss.add("num",(goods_num1-nums)+"");
//                                                String urlss = PropertiesUtil.getIp() + "/api/shop/order!updateOrderStatus.do";
//                                                ConnectOnlineMethod connect = new ConnectOnlineMethod();
//                                                    String strs = null ;
//                                                    try {
//                                                        strs = connect.connectOnline(mapss, urlss);
//                                                    } catch (IOException e) {
//                                                        e.printStackTrace();
//                                                    }
//                                                    if(strs.length()!=29){
//                                                        JOptionPane.showMessageDialog(jf, "订单明细上传线上失败");
//                                                    }
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                            
                                        }
                                    }
                                }else if (trc1 == trc2){
                                    int rrs[] = o2o.table.getSelectedRows();
                                    for(int i =0;i<o2o.table.getSelectedRowCount();i++){
//                                        goods_num = goods_num + Integer.parseInt(o2o.table.getValueAt(rrs[i], 8).toString());
                                        String sql = "select num from es_order_items where sn = '"+sn3s+"' and product_id = "+o2o.table.getValueAt(rrs[i], 0)+"";
                                        ResultSet rs1 = new SetSql().setSql(sql);
                                        int goods_num1 = 0;
                                        try {
                                            rs1.next();
                                            goods_num1 = rs1.getInt("num");
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        int nums = Integer.parseInt(o2o.table.getValueAt(rrs[i], 8)+"");
                                        if(nums==goods_num1){
                                            c1 = new SetSql().setSqlNotReturn("update es_order_items set state = 103  where  sn = '"+sn3s+"' and product_id = "+o2o.table.getValueAt(rrs[i], 0)+"");
//                                            MultivaluedMap<String, String> mapss = new MultivaluedMapImpl();
//                                            mapss.add("sn3",sn3s+"");
//                                            mapss.add("product_id",o2o.table.getValueAt(rrs[i], 0)+"");
//                                            String urlss = PropertiesUtil.getIp() + "/api/shop/order!updateOrderStatus.do";
//                                            ConnectOnlineMethod connect = new ConnectOnlineMethod();
//                                                String strs = null ;
//                                                try {
//                                                    strs = connect.connectOnline(mapss, urlss);
//                                                } catch (IOException e) {
//                                                    e.printStackTrace();
//                                                }
//                                                if(strs.length()!=29){
//                                                    JOptionPane.showMessageDialog(jf, "订单明细上传线上失败");
//                                                }
                                        }else{
                                            String sqlss = "select * from es_order_items where sn = '"+sn3s+"' and product_id = "+o2o.table.getValueAt(rrs[i], 0)+"";
                                            ResultSet rss = new SetSql().setSql(sqlss);
                                            try {
                                                rss.next();
                                                new SetSql().setSqlNotReturn("insert into es_order_items(order_id,goods_id,product_id," +
                                                        "num,bar_code,name,price,currency,sn,state,taxes,spec_value,tax_system,unit) values("
                                                        + rss.getInt("order_id")+ ","+ rss.getInt("goods_id")+ ","+ rss.getInt("product_id")+ ","+ nums+ ",'"
                                                        + rss.getString("bar_code")+ "','"+ rss.getString("name") + "'," + rss.getDouble("price") + ",'" + rss.getString("currency") + "','"
                                                        + rss.getString("sn") + "',103,"+rss.getDouble("taxes")+",'"+rss.getString("spec_value")+"',"+rss.getInt("tax_system")+",'"+rss.getString("unit")+"')");
                                                if(O2OMainMenu.internetPass){
                                                    MultivaluedMap<String, String> maps = new MultivaluedMapImpl();
                                                    maps.add("order_id",rss.getInt("order_id")+"");
                                                    maps.add("store_id", store_id+"");
                                                    maps.add("goods_id", rss.getInt("goods_id")+"");
                                                    maps.add("product_id",rss.getInt("product_id")+"");
                                                    maps.add("num",goods_num1-nums+"");
                                                    maps.add("bar_code", rss.getString("bar_code")+"");
                                                    maps.add("name",rss.getString("name")+"");
                                                    maps.add("price", rss.getDouble("price")+"");
                                                    maps.add("currency", rss.getString("currency")+"");
                                                    maps.add("sn3", rss.getString("sn"));
                                                    maps.add("cat_id", 0+"");
                                                    maps.add("state", 101+"");
                                                    maps.add("taxes", rss.getDouble("taxes")+"");
                                                    maps.add("thumbnail","");
                                                    String urlss = PropertiesUtil.getIp() + "/api/shop/order!orderItemByOffline.do";
                                                    ConnectOnlineMethod connect = new ConnectOnlineMethod();
                                                    try {
                                                        String str  = connect.connectOnline(maps, urlss);
                                                        if(str.length()!=29){
                                                            JOptionPane.showMessageDialog(jf, "订单明细上传线上失败");
                                                        }
                                                        boolean backUpItems = new SetSql()
                                                        .setSqlNotReturn("update es_order_items set export_status = 1 where sn = '" + sn3 + "'");
                                                        if (backUpItems) {
                                                            new DoBackup().realTimeBackup("update es_order_items set export_status = 1 where sn = '" + sn3
                                                                    + "'");
                                                        }
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                    }
                                                }else{
                                                    if(O2OMainMenu.internetPass){
                                                        JOptionPane.showMessageDialog(jf, "订单明细上传线上失败");
                                                    }
                                                }
                                            } catch (SQLException e) {
                                                e.printStackTrace();
                                            }
                                            aa = new SetSql().setSqlNotReturn("update es_order_items set num="+(goods_num1-nums)+"  where  sn = '"+sn3s+"' and product_id = "+o2o.table.getValueAt(rrs[i], 0)+" and state = 101");
//                                            MultivaluedMap<String, String> mapss = new MultivaluedMapImpl();
//                                            mapss.add("sn3",sn3s+"");
//                                            mapss.add("num",(goods_num1-nums)+"");
//                                            mapss.add("product_id",o2o.table.getValueAt(rrs[i], 0)+"");
//                                            String urlss = PropertiesUtil.getIp() + "/api/shop/order!updateOrderStatus.do";
//                                            ConnectOnlineMethod connect = new ConnectOnlineMethod();
//                                                String strs = null ;
//                                                try {
//                                                    strs = connect.connectOnline(mapss, urlss);
//                                                } catch (IOException e) {
//                                                    e.printStackTrace();
//                                                }
//                                                if(strs.length()!=29){
//                                                    JOptionPane.showMessageDialog(jf, "订单明细更新失败");
//                                                }
                                        }
                                    }
                                }
                                boolean d = false;
                                if(c1){
                                    d = new SetSql().setSqlNotReturn("update es_order set status = "+OrderStatus.SHIP_PARTIAL_CANCEL+" where sn = '"+sn3s+"'");
                                }else{
                                    d = new SetSql().setSqlNotReturn("update es_order set status = "+DictStatus.STORAGE_PHYSICALRETURN+" where sn = '"+sn3s+"'");
                                }
                                if(c||c1){
                                    new DoBackup().realTimeBackup("update es_order_items set state = 103 where sn = '"+sn3+"';");
                                }
                                if(d){
                                    new DoBackup().realTimeBackup("update es_order set status = "+DictStatus.STORAGE_PHYSICALRETURN+" where sn = '"+sn3+"'");
                                }
//                                    boolean sellback = new SetSql().setSqlNotReturn("update es_sellback_list set export_status = 1 where tradeno = '"+sr3+"'");
//                                    if(sellback){
//                                        new DoBackup().realTimeBackup("update es_sellback_list set export_status = 1 where tradeno = '"+sr3+"'");
//                                    }
//                                    boolean items = false;
//                                    if(items){
//                                     items =new SetSql().setSqlNotReturn("update es_order_items set export_status = 1 where sn = '"+sn3s+"' and product_id = '");
//                                         new DoBackup().realTimeBackup("update es_order_items set export_status = 1 where sn = '"+sn3s+"' ab");
//                                     }
//                                }
                                
                                //上传退货订单明细
                                String itemsProduct = "";
                                int rw = rr.table.getSelectedRowCount();
                                int row[] = rr.table.getSelectedRows();
                                for (int i = 0; i < rw; i++) {
                                    int r = row[i];
                                    num = String.valueOf(rr.table.getValueAt(r, 8));// num
                                    sn2 = String.valueOf(rr.table
                                            .getValueAt(r, 1));
            
                                    ResultSet snResultSet = new SetSql()
                                            .setSql("select * from es_order_items where bar_code = '"
                                                    + sn2 + "' and product_id = '"+rr.table.getValueAt(r,0)+"' and sn ='"+rr.documentNumber.getText()+"'");
                                    try {
                                        snResultSet.next();
                                        goods_id = snResultSet.getInt("goods_id");
                                        product_id = snResultSet
                                                .getInt("product_id");
                                        name = snResultSet.getString("name");
                                        price = Double.parseDouble(rr.table.getValueAt(r, 7)+"");
                                    } catch (SQLException e1) {
                                        e1.printStackTrace();
                                    }
//                                    itemsProduct_ids = itemsProduct;
//                                        boolean e = new SetSql().setSqlNotReturn("update es_order_items set state = "+DictStatus.REFUNDED+" , export_status = 1 where order_id = '"+parent_id+"' and product_id = '"+product_id+"'");
//                                        boolean f = new SetSql().setSqlNotReturn("update es_order set status = "+DictStatus.STORAGE_PHYSICALRETURN+",export_status = 1 where order_id = '"+parent_id+"'");
//                                        if(e){
//                                            new DoBackup().realTimeBackup("update es_order_items set state = "+DictStatus.REFUNDED+" , export_status = null where order_id = '"+parent_id+"' and product_id = '"+product_id+"';");
//                                        }
//                                        if(f){
//                                            new DoBackup().realTimeBackup("update es_order set status = "+DictStatus.STORAGE_PHYSICALRETURN+",export_status = null where order_id = '"+parent_id+"'");
//                                        }
//                                    }
                                }
                                
                                JOptionPane.showMessageDialog(jf, "退货成功");
                                    
                                // 清空table数据
                                rr.memberNumber.setText("");
                                rr.documentNumber.setText("");
                                rr.totalMoneyLabelChange.setText("");
                                rr.totalNumberLabelChange.setText("");
                                DefaultTableModel model = (DefaultTableModel) rr.table
                                        .getModel();
                                while (model.getRowCount() > 0) {
                                    model.removeRow(model.getRowCount() - 1);
                                }
                            } else {
                                JOptionPane.showMessageDialog(jf, "无商品记录");
                            }
                            jf.dispose();
                        
                      }else{
                        JOptionPane.showMessageDialog(jf, "密码错误");
                    }
                    
                    }else{
                        JOptionPane.showMessageDialog(jf, "请输入店长账号");
                    }   
                            
                    }
                    else{
                        JOptionPane.showMessageDialog(jf, "用户名不存在");
                    }
                } catch (SQLException e3) {
                    e3.printStackTrace();
                }
            }else{JOptionPane.showMessageDialog(jf, "请输入密码！");
            }
            
          //联机状态下订单退货流程
        }else{  
    
            if(passwords.length()>0){
            //从线上获取用户名等信息
            MultivaluedMap queryParam = new MultivaluedMapImpl();
            String getPassword = StringUtil.md5(passwords);
            queryParam.add("username", username);
            queryParam.add("password", getPassword);
            String url = PropertiesUtil.getIp() + "/api/shop/member!getRole.do";
            ConnectOnlineMethod connection = new ConnectOnlineMethod();
            
            String outPut = null;
            try {
                outPut = connection.connectOnline(queryParam, url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!outPut.equals("") && outPut != null){
                JSONArray array = connection.jsonConvertion(outPut);
                JSONObject jsonObject = connection.getJsonObject(array);
                if(jsonObject !=null){
                String role = jsonObject.getString("role_name");
                
                if(role.equals("店长")){
                    /**
                     * 退款
                     * */
    
                    if (rr.table.getRowCount() != 0) {
                        
//                    	sn3 = rr.documentNumber.getText();
                        boolean a1 = new SetSql().setSqlNotReturn("insert into es_sellback_list(id,tradeno,tradestatus,ordersn,regtime,alltotal_pay,member_id,total) values('"+order_id+"','"+sr3+"','100','"+rr.documentNumber.getText()+"','"+create_time+"','"+goods_amount+"','"+cardNumber+"','"+goods_num+"')");
                        String sellBackOrder = "insert into es_sellback_list(tradeno,tradestatus,ordersn,regtime,alltotal_pay,total,store_id) values("+"UPPER('"+sr3+"')"+",'101',UPPER('"+rr.documentNumber.getText()+"'),'"+create_time+"','"+goods_amount+"','"+goods_num+"',"+PropertiesUtil.getStoreId()+")#"+cardNumber+"#"+rr.documentNumber.getText();
                        sellBackOrders = sellBackOrder;
                        if(a1){
                            new DoBackup().realTimeBackup("insert into es_sellback_list(id,tradeno,tradestatus,ordersn,regtime,alltotal_pay,member_id,total) values('"+order_id+"','"+sr3+"','100','"+rr.documentNumber.getText()+"','"+create_time+"','"+goods_amount+"','"+cardNumber+"','"+goods_num+"');");
    
                        }
                        //获取订单
                        String sn3s = rr.documentNumber.getText();
                        boolean c = false;
                        boolean c1 = false;
                        boolean aa = false;
                        int trc1 = o2o.table.getSelectedRowCount();
                        int trc2 = o2o.table.getRowCount();
                        if(trc1<trc2){
                            int rrs[] = o2o.table.getSelectedRows();
                            for(int i =0;i<o2o.table.getSelectedRowCount();i++){
//                                goods_num = goods_num + Integer.parseInt(o2o.table.getValueAt(rrs[i], 8).toString());
                                String sql = "select num from es_order_items where sn = '"+sn3s+"' and product_id = "+o2o.table.getValueAt(rrs[i], 0)+"";
                                ResultSet rs = new SetSql().setSql(sql);
                                //原订单明细的数量
                                int goods_num1 = 0;
                                try {
                                    rs.next();
                                    goods_num1 = rs.getInt("num");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                //nums为退货的数量，goods_nums1-nums 的值为未退货的商品数量
                                int nums = Integer.parseInt(o2o.table.getValueAt(rrs[i], 8)+"");
                                //判断原订单 的订单明细的数量和要退货的商品明细的数量是否相等，如果相等直接更新订单的状态为全部退货（112）明细的状态为103，否则新增一条订单明细为103 的状态，订单明细的数量为用户选择的数量（nums） 其中goods_nums1为订单明细的原数量，
                                if(nums==goods_num1){//订单明细的数量不变的情况下 ，只更新订单明细的状态
                                    c1 = new SetSql().setSqlNotReturn("update es_order_items set state = 103  where  sn = '"+sn3s+"' and product_id = "+o2o.table.getValueAt(rrs[i], 0)+"");
                                    MultivaluedMap<String, String> mapss = new MultivaluedMapImpl();
                                    mapss.add("sn3",sn3s+"");
                                    mapss.add("product_id",o2o.table.getValueAt(rrs[i], 0)+"");
                                    String urlss = PropertiesUtil.getIp() + "/api/shop/order!updateOrderStatus.do";
                                    ConnectOnlineMethod connect = new ConnectOnlineMethod();
                                        String strs = null ;
                                        try {
                                            strs = connect.connectOnline(mapss, urlss);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        if(strs.length()!=29){
                                            JOptionPane.showMessageDialog(jf, "订单明细更新失败");
                                        }
                                }else{//订单明细的数量发生改变，会把原来的订单明细拆分为两个明细，其中一个明细为选择退货后的退货明细，状态为103，
                                    String sqlss = "select * from es_order_items where sn = '"+sn3s+"' and product_id = "+o2o.table.getValueAt(rrs[i], 0)+"";
                                    ResultSet rss = new SetSql().setSql(sqlss);
                                    try {
                                        rss.next();
                                        //把原来的订单明细拆分为两个明细，一下代码为新增已退货的订单明细，状态103，数量为扣除退货明细后的数量
                                        new SetSql().setSqlNotReturn("insert into es_order_items(order_id,goods_id,product_id," +
                                                "num,bar_code,name,price,currency,sn,state,taxes,spec_value,tax_system,unit) values("
                                                + rss.getInt("order_id")+ ","+ rss.getInt("goods_id")+ ","+ rss.getInt("product_id")+ ","+ nums+ ",'"
                                                + rss.getString("bar_code")+ "','"+ rss.getString("name") + "'," + rss.getDouble("price") + ",'" + rss.getString("currency") + "','"
                                                + rss.getString("sn") + "',103,"+rss.getDouble("taxes")+",'"+rss.getString("spec_value")+"',"+rss.getInt("tax_system")+",'"+rss.getString("unit")+"')");
                                        if(O2OMainMenu.internetPass){
                                            MultivaluedMap<String, String> maps = new MultivaluedMapImpl();
                                            maps.add("order_id",rss.getInt("order_id")+"");
                                            maps.add("store_id", store_id+"");
                                            maps.add("goods_id", rss.getInt("goods_id")+"");
                                            maps.add("product_id",rss.getInt("product_id")+"");
                                            maps.add("num",nums+"");
                                            maps.add("bar_code", rss.getString("bar_code")+"");
                                            maps.add("name",rss.getString("name")+"");
                                            maps.add("price", rss.getDouble("price")+"");
                                            maps.add("currency", rss.getString("currency")+"");
                                            maps.add("sn3", rss.getString("sn"));
                                            maps.add("cat_id", 0+"");
                                            maps.add("state", 103+"");
                                            maps.add("taxes", rss.getDouble("taxes")+"");
                                            maps.add("thumbnail","");
                                            maps.add("rejected","1");
                                            String urlss = PropertiesUtil.getIp() + "/api/shop/order!orderItemByOffline.do";
                                            ConnectOnlineMethod connect = new ConnectOnlineMethod();
                                            try {
                                                String str  = connect.connectOnline(maps, urlss);
                                                if(str.length()!=29){
                                                    JOptionPane.showMessageDialog(jf, "订单明细上传线上失败");
                                                }
                                                boolean backUpItems = new SetSql()
                                                .setSqlNotReturn("update es_order_items set export_status = 1 where sn = '" + sn3 + "'");
                                                if (backUpItems) {
                                                    new DoBackup().realTimeBackup("update es_order_items set export_status = 1 where sn = '" + sn3
                                                            + "'");
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }else{
                                            if(O2OMainMenu.internetPass){
                                                JOptionPane.showMessageDialog(jf, "订单明细上传线上失败");
                                            }
                                        }
                                        //把原订单明细经过退货拆分后剩余的订单明细，此时将这部分明细的数量更新未退货数量 （101为已完成状态）
                                        aa = new SetSql().setSqlNotReturn("update es_order_items set num="+(goods_num1-nums)+"  where  sn = '"+sn3s+"' and product_id = "+o2o.table.getValueAt(rrs[i], 0)+" and state = 101");
                                        MultivaluedMap<String, String> mapss = new MultivaluedMapImpl();
                                        mapss.add("sn3",sn3s+"");
                                        mapss.add("product_id",o2o.table.getValueAt(rrs[i], 0)+"");
                                        mapss.add("num",(goods_num1-nums)+"");
                                        mapss.add("store_id",PropertiesUtil.getStoreId()+"");
                                        String urlss = PropertiesUtil.getIp() + "/api/shop/order!updateOrderStatus.do";
                                        ConnectOnlineMethod connect = new ConnectOnlineMethod();
                                            String strs = null ;
                                            try {
                                                strs = connect.connectOnline(mapss, urlss);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            if(strs.length()!=29){
                                                JOptionPane.showMessageDialog(jf, "订单明细上传线上失败");
                                                boolean backUpItems = new SetSql()
                                                .setSqlNotReturn("update es_order_items set export_status = 1 where sn = '" + sn3 + "'");
                                                if (backUpItems) {
                                                    new DoBackup().realTimeBackup("update es_order_items set export_status = 1 where sn = '" + sn3
                                                            + "'");
                                                }
                                            }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    
                                }
                            }
                        }else if(trc1==trc2){
                            int rrs[] = o2o.table.getSelectedRows();
                            for(int i =0;i<o2o.table.getSelectedRowCount();i++){
//                                goods_num = goods_num + Integer.parseInt(o2o.table.getValueAt(rrs[i], 8).toString());
                                String sql = "select num from es_order_items where sn = '"+sn3s+"' and product_id = "+o2o.table.getValueAt(rrs[i], 0)+"";
                                ResultSet rs = new SetSql().setSql(sql);
                                int goods_num1 = 0;
                                try {
                                    rs.next();
                                    goods_num1 = rs.getInt("num");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                int nums = Integer.parseInt(o2o.table.getValueAt(rrs[i], 8)+"");
                                if(nums==goods_num1){
                                    c1 = new SetSql().setSqlNotReturn("update es_order_items set state = 103  where  sn = '"+sn3s+"' and product_id = "+o2o.table.getValueAt(rrs[i], 0)+"");
                                    MultivaluedMap<String, String> mapss = new MultivaluedMapImpl();
                                    mapss.add("sn3",sn3s+"");
                                    mapss.add("product_id",o2o.table.getValueAt(rrs[i], 0)+"");
                                    mapss.add("store_id",PropertiesUtil.getStoreId()+"");
                                    String urlss = PropertiesUtil.getIp() + "/api/shop/order!updateOrderStatus.do";
                                    ConnectOnlineMethod connect = new ConnectOnlineMethod();
                                        String strs = null ;
                                        try {
                                            strs = connect.connectOnline(mapss, urlss);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        if(strs.length()!=29){
                                            JOptionPane.showMessageDialog(jf, "订单明细上传线上失败");
                                            boolean backUpItems = new SetSql()
                                            .setSqlNotReturn("update es_order_items set export_status = 1 where sn = '" + sn3 + "'");
                                            if (backUpItems) {
                                                new DoBackup().realTimeBackup("update es_order_items set export_status = 1 where sn = '" + sn3
                                                        + "'");
                                            }
                                        }
                                }else{
                                    String sqlss = "select * from es_order_items where sn = '"+sn3s+"' and product_id = "+o2o.table.getValueAt(rrs[i], 0)+"";
                                    ResultSet rss = new SetSql().setSql(sqlss);
                                    try {
                                        rss.next();
                                        new SetSql().setSqlNotReturn("insert into es_order_items(order_id,goods_id,product_id," +
                                                "num,bar_code,name,price,currency,sn,state,taxes,spec_value,tax_system,unit) values("
                                                + rss.getInt("order_id")+ ","+ rss.getInt("goods_id")+ ","+ rss.getInt("product_id")+ ","+ nums+ ",'"
                                                + rss.getString("bar_code")+ "','"+ rss.getString("name") + "'," + rss.getDouble("price") + ",'" + rss.getString("currency") + "','"
                                                + rss.getString("sn") + "',103,"+rss.getDouble("taxes")+",'"+rss.getString("spec_value")+"',"+rss.getInt("tax_system")+",'"+rss.getString("unit")+"')");
                                        if(O2OMainMenu.internetPass){
                                            MultivaluedMap<String, String> maps = new MultivaluedMapImpl();
                                            maps.add("order_id",rss.getInt("order_id")+"");
                                            maps.add("store_id", store_id+"");
                                            maps.add("goods_id", rss.getInt("goods_id")+"");
                                            maps.add("product_id",rss.getInt("product_id")+"");
                                            maps.add("num",goods_num1-nums+"");
                                            maps.add("bar_code", rss.getString("bar_code")+"");
                                            maps.add("name",rss.getString("name")+"");
                                            maps.add("price", rss.getDouble("price")+"");
                                            maps.add("currency", rss.getString("currency")+"");
                                            maps.add("sn3", rss.getString("sn"));
                                            maps.add("cat_id", 0+"");
                                            maps.add("state", 101+"");
                                            maps.add("taxes", rss.getDouble("taxes")+"");
                                            maps.add("thumbnail","");
                                            maps.add("rejected","1");
                                            String urlss = PropertiesUtil.getIp() + "/api/shop/order!orderItemByOffline.do";
                                            ConnectOnlineMethod connect = new ConnectOnlineMethod();
                                            try {
                                                String str  = connect.connectOnline(maps, urlss);
                                                if(str.length()!=29){
                                                    JOptionPane.showMessageDialog(jf, "订单明细上传线上失败");
                                                }
                                                boolean backUpItems = new SetSql()
                                                .setSqlNotReturn("update es_order_items set export_status = 1 where sn = '" + sn3s + "'");
                                                if (backUpItems) {
                                                    new DoBackup().realTimeBackup("update es_order_items set export_status = 1 where sn = '" + sn3s
                                                            + "'");
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }else{
                                            if(O2OMainMenu.internetPass){
                                                JOptionPane.showMessageDialog(jf, "订单明细上传线上失败");
                                            }
                                        }
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    aa = new SetSql().setSqlNotReturn("update es_order_items set num="+(goods_num1-nums)+"  where  sn = '"+sn3s+"' and product_id = "+o2o.table.getValueAt(rrs[i], 0)+" and state = 101");
                                    MultivaluedMap<String, String> mapss = new MultivaluedMapImpl();
                                    mapss.add("sn3",sn3s+"");
                                    mapss.add("num",(goods_num1-nums)+"");
                                    mapss.add("product_id",o2o.table.getValueAt(rrs[i], 0)+"");
                                    mapss.add("num1", nums+"");
                                    mapss.add("store_id", PropertiesUtil.getStoreId()+"");
                                    String urlss = PropertiesUtil.getIp() + "/api/shop/order!updateOrderStatus.do";
                                    ConnectOnlineMethod connect = new ConnectOnlineMethod();
                                        String strs = null ;
                                        try {
                                            strs = connect.connectOnline(mapss, urlss);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        if(strs.length()!=29){
                                            JOptionPane.showMessageDialog(jf, "订单明细更新失败");
                                            boolean sellback = new SetSql().setSqlNotReturn("update es_sellback_list set export_status = 1 where tradeno = '"+sr3+"'");
                                            if(sellback){
                                                new DoBackup().realTimeBackup("update es_sellback_list set export_status = 1 where tradeno = '"+sr3+"'");
                                            }
                                        }
                                }
                            }
                        }
                        boolean d = false;
                        if(aa){
                            d = new SetSql().setSqlNotReturn("update es_order set status = "+OrderStatus.SHIP_PARTIAL_CANCEL+" where sn = '"+sn3s+"'");
                        }else{
                            d = new SetSql().setSqlNotReturn("update es_order set status = "+DictStatus.STORAGE_PHYSICALRETURN+" where sn = '"+sn3s+"'");
                        }
                        if(aa||c1){
                            new DoBackup().realTimeBackup("update es_order_items set state = 103 where sn = '"+sn3s+"';");
                        }
                        if(d){
                            new DoBackup().realTimeBackup("update es_order set status = "+DictStatus.STORAGE_PHYSICALRETURN+" where sn = '"+sn3s+"'");
                        }
                        
                        String sql = "select payment_name from es_order where sn = '"+sn3s+"'";
                        String refund_way = "";
                        ResultSet rss = new SetSql().setSql(sql);
                        try {
                            if(rss.next()){
                                refund_way = rss.getString("payment_name");
                            }
                        } catch (SQLException e2) {
                            e2.printStackTrace();
                        }
                        MultivaluedMap map =  new MultivaluedMapImpl();
    //                                            map.add("sn3",sn3);
                        map.add("id",order_id+"");
                        map.add("tradeno", sr3);
                        map.add("tradestatus",3+"");
                        map.add("refund_way",refund_way);
                        map.add("ordersn",sn3s);
                        map.add("regtime",create_time+"");
                        map.add("alltotal_pay",goods_amount+"");
                        map.add("member_id",cardNumber);
                        map.add("total",goods_num+"");
                        map.add("store_id",PropertiesUtil.getStoreId()+"");
                        String url1 = PropertiesUtil.getIp() + "/api/shop/sellBack!addSellbackLists.do";
                        ConnectOnlineMethod connection1 = new ConnectOnlineMethod();
                       
                        String outPut1 = null;
                        try {
                            outPut1 = connection1.connectOnline(map, url1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(outPut1.length()==29){
                            boolean sellback = new SetSql().setSqlNotReturn("update es_sellback_list set export_status = 1 where tradeno = '"+sr3+"'");
                            if(sellback){
                                new DoBackup().realTimeBackup("update es_sellback_list set export_status = 1 where tradeno = '"+sr3+"'");
                            }
                        }
                        
                        //上传退货订单明细
                        String itemsProduct = "";
                        int rw = rr.table.getSelectedRowCount();
                        int row[] = rr.table.getSelectedRows();
                        for (int i = 0; i < rw; i++) {
                            int r = row[i];
                            num = String.valueOf(rr.table.getValueAt(r, 8));// num
                            sn2 = String.valueOf(rr.table
                                    .getValueAt(r, 1));
    
                            ResultSet snResultSet = new SetSql()
                                    .setSql("select * from es_order_items where bar_code = '"
                                            + sn2 + "' and product_id = '"+rr.table.getValueAt(r,0)+"' and sn ='"+rr.documentNumber.getText()+"'");
                            try {
                                snResultSet.next();
                                goods_id = snResultSet.getInt("goods_id");
                                product_id = snResultSet
                                        .getInt("product_id");
                                name = snResultSet.getString("name");
                                price = Double.parseDouble(rr.table.getValueAt(r, 7)+"");
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            itemsProduct_ids = itemsProduct;
                            String sellbackGoodsList = "insert into es_sellback_goodslist (product_id,goods_id,return_num,price)values("+product_id+","+goods_id+","+num+",'"+price+"')";
                            boolean sell = new SetSql().setSqlNotReturn(sellbackGoodsList);
                            if(sell){
                                MultivaluedMap maps =  new MultivaluedMapImpl();
                                maps.add("tradeno", sr3);
                                maps.add("product_id",product_id+"");
                                maps.add("goods_id",goods_id+"");
                                maps.add("return_num",num+"");
                                maps.add("prices",price+"");
                                String url1s = PropertiesUtil.getIp() + "/api/shop/sellBack!BackListGoodsFromOffline.do";
                                ConnectOnlineMethod connection1s = new ConnectOnlineMethod();
                                
                                String outPut2 = null;
                                try {
                                    outPut2 = connection1s.connectOnline(maps, url1s);
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                if(outPut2.length()>29){
                                    boolean e = new SetSql().setSqlNotReturn("update es_order_items set state = "+DictStatus.REFUNDED+" , export_status = 1 where order_id = '"+parent_id+"' and product_id = '"+product_id+"'");
                                    boolean f = new SetSql().setSqlNotReturn("update es_order set status = "+DictStatus.STORAGE_PHYSICALRETURN+",export_status = 1 where order_id = '"+parent_id+"'");
                                    boolean k = new SetSql().setSqlNotReturn("update es_sellback_goodslist set export_status = 1 where id=(select  id from es_sellback_goodslist  order by id desc LIMIT 1)");
                                    if(e){
                                        new DoBackup().realTimeBackup("update es_order_items set state = "+DictStatus.REFUNDED+" , export_status = null where order_id = '"+parent_id+"' and product_id = '"+product_id+"';");
                                    }
                                    if(f){
                                        new DoBackup().realTimeBackup("update es_order set status = "+DictStatus.STORAGE_PHYSICALRETURN+",export_status = null where order_id = '"+parent_id+"'");
                                    }
                                    if(k){
                                        new DoBackup().realTimeBackup("update es_sellback_goodslist set export_status = 1 where id=(select  id from es_sellback_goodslist  order by id desc LIMIT 1)");
                                    }
                                }
                            }
                        }
                        
                        JOptionPane.showMessageDialog(jf, "退货成功");
                            
                        // 清空table数据
                        rr.memberNumber.setText("");
                        rr.documentNumber.setText("");
                        rr.totalMoneyLabelChange.setText("");
                        rr.totalNumberLabelChange.setText("");
                        DefaultTableModel model = (DefaultTableModel) rr.table
                                .getModel();
                        while (model.getRowCount() > 0) {
                            model.removeRow(model.getRowCount() - 1);
                        }
                    } else {
                        JOptionPane.showMessageDialog(jf, "无商品记录");
                    }
                    jf.dispose();
                }else{
                    JOptionPane.showMessageDialog(jf, "密码错误");
                }
                
                }else{
                    JOptionPane.showMessageDialog(jf, "请输入店长账号");
                }
                }
                else{
                    JOptionPane.showMessageDialog(jf, "用户名不存在");
                }
        }else{JOptionPane.showMessageDialog(jf, "请输入密码！");}
                
                
       }
       o2o.goodsCode.setText("");
    }
    /**        
     * @author lsl
     * @param   name    
     * @return String   
     * @Exception 异常对象    
     * 选中退货的商品之后，生成新的订单记录   
    */
}
