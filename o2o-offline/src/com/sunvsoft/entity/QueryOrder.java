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
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javax.swing.JTextField;
import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sunvsoft.accessOnline.ConnectOnlineMethod;
import com.sunvsoft.backup.PropertiesUtil;
import com.sunvsoft.sqlset.SetSql;

public class QueryOrder {
    private static QueryOrder queryOrder = null;
    private QueryOrder(){}
    public static QueryOrder getInstance(){
        if(queryOrder==null){
            queryOrder = new QueryOrder();
        }
        return queryOrder;
    }
    public O2OMainMenu o2oMainMenu = new O2OMainMenu();
    static 
    Object[][] data3 = null;
    JFrame jf = new JFrame();
    JPanel jp = new JPanel();
    JPanel jp1 = new JPanel();
    private static JLabel orderLabel;
    private static JLabel memberNumber;
    private static JLabel orderNumber;
    private static JTextField memberNum;
    private static JTextField orderNum;
    private static JButton sureButton;
    @SuppressWarnings("static-access")
    public void init(){
        //初始化参数的相关信息
        orderLabel = new JLabel("订单查询");
        memberNumber = new JLabel("会员号");
        orderNumber = new JLabel("订单号");
        memberNum = new JTextField();
        orderNum = new JTextField();
        sureButton = new JButton("确定");
        orderLabel.setFont(new Font("宋体", Font.PLAIN, 26));
        orderLabel.setForeground(Color.red);
        memberNumber.setFont(new Font("黑体", Font.PLAIN, 16));
        memberNumber.setForeground(Color.black);
        orderNumber.setFont(new Font("黑体", Font.PLAIN, 16));
        orderNumber.setForeground(Color.black);
        sureButton.setFont(new Font("黑体",Font.PLAIN, 16));
        sureButton.setForeground(Color.black);
        
        /**
         * 组建的位置
         */
        jp1.setLayout(null);
        orderLabel.setBounds(200, 30, 230, 30);
        memberNumber.setBounds(90, 100, 230, 30);
        memberNum.setBounds(170, 100, 230, 30);
        orderNumber.setBounds(90, 150, 100, 30);
        orderNum.setBounds(170, 150, 230, 30);
        sureButton.setBounds(210, 200, 80, 30);
        
        //设置布局
        jp.add(orderLabel);
        jp1.add(memberNumber);
        jp1.add(memberNum);
        jp1.add(orderNumber);
        jp1.add(orderNum);
        jp1.add(sureButton);
        
        jp.setLayout(new BorderLayout());
        jp.add(jp1,BorderLayout.CENTER);
        jf.add(jp);
        
        jf.setSize(500, 300);
        jf.setLocationRelativeTo(null);
        jf.setResizable(false);
        jf.setUndecorated(true);
        jf.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        jf.setDefaultCloseOperation(jf.DO_NOTHING_ON_CLOSE);
        jf.setVisible(true);
        ImageIcon icon = new ImageIcon(MemberAddress.class.getClassLoader()
                .getResource("images/YZF.png"));
        jf.setIconImage(icon.getImage());
        
        sureButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
               checkOrder(); 
            }
        });
        memberNum.addKeyListener(new keyListener());
        orderNum.addKeyListener(new keyListener());
        jf.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                jf.dispose();
            }
        });
    }
    public void checkOrder(){

        if (memberNum.getText().length() > 0 || orderNum.getText().length() >0) {
                
                while (O2OMainMenu.model.getRowCount() > 0) {
                    O2OMainMenu.model.removeRow(O2OMainMenu.model.getRowCount() - 1);
                }
                StringBuffer sql2 = new StringBuffer("select * from  es_order o where");             
				if(memberNum.getText().length()>0){
				//联网状态
//				MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
//				String store_id = PropertiesUtil.getStoreId();
//				queryParam.add("store_id",store_id);
//				queryParam.add("cardNumber",memberNum.getText().toString());
//				queryParam.add("phone",memberNum.getText().toString());
//				String url = PropertiesUtil.getIp() + "/api/shop/member!getCard.do";
//				String phone = null;
//				ConnectOnlineMethod connect = new ConnectOnlineMethod();
//				String outPut;
//				try {
//				    outPut = connect.connectOnline(queryParam, url);
//				    if(!outPut.equals("")){
//				        JSONArray array = connect.jsonConvertion(outPut);
//				        JSONObject object = connect.getJsonObject(array);
//				        if(object !=null){
//				            phone = (String) object.get("phone");
//				        }
//				    }
//				} catch (IOException e) {
//				    e.printStackTrace();
//				}
				sql2.append(" o.cardNumber = '"+memberNum.getText().toString()+"'");
            }else if(orderNum.getText().length()>0){
				sql2.append(" (o.sn ='"+orderNum.getText()+"' or parent_id = (select order_id from es_order where sn ='"+orderNum.getText()+"'))");
            }
            sql2.append(" and sn not like 'SO20%' and o.`status`in ("+OrderStatus.ORDER_COMPLETE+","+OrderStatus.ORDER_PAY_CONFIRM+") ");
            ResultSet rs2 = new SetSql().setSql(sql2.toString());
            ResultSet rs = new SetSql().setSql(sql2.toString());
            try {
				if (rs2.next()) {
				    try {
				        List<Object[]> list = new LinkedList<Object[]>();
				        while (rs.next()) {
				            // 改成你的列名
				            Long time = rs.getLong("create_time");
				            String times = new timeToDates().getTimes(time * 1000);
				            Object[] objects = new Object[] {
				                    rs.getString("sn"),
				                    rs.getString("user_name"), times,
				                    rs.getInt("goods_num"),
				                    rs.getString("date") };
				            list.add(objects);
				        }
				        data3 = list.toArray(new Object[0][0]);
				    } catch (SQLException e2) {
				        e2.printStackTrace();
				    }
				    jf.dispose();
				    orderCheckWindow oCheckWindow = new orderCheckWindow();
				    // 这是一个比较深的问题，暂时我先理解到这个层面
				    oCheckWindow.init(data3);
				} else {
				    JOptionPane.showMessageDialog(jf, "无记录，请核对会员号或者订单号");
				}

            } catch (SQLException e1) {
				e1.printStackTrace();
            }
        } else {
        	String role = null;
        	if(O2OMainMenu.internetPass){
        	    MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
        	    queryParam.add("username", O2OMainMenu.salesman);
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
        	}else{
        	    ResultSet rsForRole = new SetSql()
                .setSql("select realname as rolename from es_user where username = '"
                        + O2OMainMenu.operator.getText().toString() + "'");
        	    try {
        	        rsForRole.next();
        	        role = (rsForRole.getString("rolename"));
        	    } catch (SQLException e2) {
        	        e2.printStackTrace();
        	    }
        	}
        	if(role.equals("店长")){ 
        	Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
            String datetime = sdf.format(date);
        	String sql3 = new String("select o.sn,o.user_name,o.date,o.goods_num from  es_order o where date= '"+datetime+"'"
        			+" and o.`status`in ("+OrderStatus.ORDER_COMPLETE+","+OrderStatus.ORDER_PAY_CONFIRM+","+OrderStatus.SHIP_PARTIAL_CANCEL+") and sn not like 'SO20%'");
            ResultSet rs = new SetSql().setSql(sql3);
		    try {
		        List<Object[]> list = new LinkedList<Object[]>();
		            // 改成你的列名
		        while (rs.next()) {
		            Object[] objects = new Object[] {
		                    rs.getString("sn"),
		                    rs.getString("user_name"), datetime,
		                    rs.getInt("goods_num"),
		                    rs.getString("date") };
		            list.add(objects);
		        }
		        data3 = list.toArray(new Object[0][0]);
		    } catch (SQLException e2) {
		        e2.printStackTrace();
		    }
		    jf.dispose();
		    orderCheckWindow oCheckWindow = new orderCheckWindow();
		    oCheckWindow.init(data3);
           }else{

               String dateNow = new timeToDates().getTimeToDates(System
                       .currentTimeMillis());
               ResultSet rsResultSet = new SetSql()
                       .setSql("select * from es_classes");
               int classes = 1;
               try {
                   rsResultSet.next();
                   classes = rsResultSet.getInt("classes");
               } catch (SQLException e1) {
                   e1.printStackTrace();
               }
              	Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
                String datetime = sdf.format(date);
            	String sql5 = new String("select o.sn,o.user_name,o.date,o.goods_num from  es_order o where date= '"+datetime+"'"
            			+"and classes = '"+classes+"'"+" and o.`status`in ("+OrderStatus.ORDER_COMPLETE+","+OrderStatus.ORDER_PAY_CONFIRM+","+OrderStatus.SHIP_PARTIAL_CANCEL+") and sn not like 'SO20%'");
                ResultSet rs3 = new SetSql().setSql(sql5);
    		    try {
    		        List<Object[]> list3 = new LinkedList<Object[]>();
    		            // 改成你的列名
    		        while (rs3.next()) {
    		            Object[] objects = new Object[] {
    		                    rs3.getString("sn"),
    		                    rs3.getString("user_name"), datetime,
    		                    rs3.getInt("goods_num"),
    		                    rs3.getString("date") };
    		            list3.add(objects);
    		        }
    		        data3 = list3.toArray(new Object[0][0]);
    		    } catch (SQLException e2) {
    		        e2.printStackTrace();
    		    }
    		    jf.dispose();
    		    orderCheckWindow oCheckWindow = new orderCheckWindow();
    		    oCheckWindow.init(data3);
               
               
           }
			
        }
        queryOrder = null;
        
    }
    public class keyListener implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
            
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
                 jf.dispose();
             }
            if(e.getKeyCode()==KeyEvent.VK_ENTER){
                checkOrder();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            
        }
    }
    
}
