package com.sunvsoft.entity;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONObject;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sunvsoft.accessOnline.ConnectOnlineMethod;
import com.sunvsoft.backup.DoBackup;
import com.sunvsoft.backup.PropertiesUtil;
import com.sunvsoft.sqlset.SetSql;

/**
 * 会员中心-接收地址
 * 
 * @author lsl
 */
@SuppressWarnings("serial")
public class MemberAddress implements Serializable {
	
	    private static MemberAddress mAddInputFrame = null;
	    @SuppressWarnings("unused")
        private void MemberAdress(){}
	    public static MemberAddress getInstance(){
	        if(mAddInputFrame == null){
	            mAddInputFrame = new MemberAddress();
	        }
	        return mAddInputFrame;
	    }
	    JFrame jFrameM = new JFrame("添加地址");
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
	    JLabel jLabel = new JLabel("添加地址");
	    //组合框显示的内容
	    String[] provinceItems = getRegions();
	    String[] cityItem = getDefaultCity("北京");
	    String[] streetItem = getDefaultStreet("北京");
	    String provinces = null ;
	    String citys = null;
	    String streets = null;
	        
	    
//	    //组合框
//	    JComboBox jcb = new JComboBox(items);
//	    //列表框
//	    JList jl = new JList<>();
	    JLabel provinceLable = new JLabel("<HTML><font color=red>*</font> 所在省");
//	    JTextField provinceField ;
	   
	    @SuppressWarnings({ "rawtypes", "unchecked" })
        JComboBox provinceField = new JComboBox(provinceItems);
	    
	    
	    JLabel cityLable = new JLabel("<HTML><font color=red>*</font> 所在市/县");
//	    JTextField cityField ;
	    JComboBox<String> cityField = new JComboBox<String>() ;
	    JLabel streetLabel = new JLabel("<HTML><font color=red>*</font> 所在区/街");
//	    JTextField streetField ;
	    JComboBox<String> streetField = new JComboBox<String>();
	    JLabel detailAddLable = new JLabel("<HTML><font color=red>*</font> 详细地址");
	    JTextField detailAddField ;
	    JLabel memberLabel = new JLabel("<HTML><font color=red>*</font> 收货人");
	    JTextField memberFeild ;
	    JLabel mobileLabel = new JLabel("<HTML><font color=red>*</font> 电话");
	    JTextField mobileField ;
	    JLabel zipLabel = new JLabel("<HTML><font color=red>*</font> 邮政编码");
	    JTextField zipField ;
	    O2OMainMenu mm;
	    @SuppressWarnings("static-access")
        public void init() {
	        
	        detailAddField = new JTextField(10);
	        memberFeild = new JTextField(10);
	        mobileField = new JTextField(10);
	        zipField = new JTextField(10);
	        jLabel.setFont(new Font("宋体", Font.PLAIN, 26));
	        jLabel.setForeground(Color.red);
	        JButton addButton = new JButton("添加");
	        /**
	         *  字体  
	         */
	        provinceLable.setFont(new Font("黑体", Font.PLAIN, 16));
	        provinceLable.setForeground(Color.black);
	        cityLable.setFont(new Font("黑体", Font.PLAIN, 16));
            cityLable.setForeground(Color.black);
            streetLabel.setFont(new Font("黑体", Font.PLAIN, 16));
            streetLabel.setForeground(Color.black);
	        detailAddLable.setFont(new Font("黑体", Font.PLAIN, 16));
	        detailAddLable.setForeground(Color.black);
	        memberLabel.setFont(new Font("黑体", Font.PLAIN, 16));
            memberLabel.setForeground(Color.black);
	        mobileLabel.setFont(new Font("黑体", Font.PLAIN, 16));
	        mobileLabel.setForeground(Color.black);
	        zipLabel.setFont(new Font("黑体", Font.PLAIN, 16));
	        zipLabel.setForeground(Color.black);
	        
	        addButton.setFont(new Font("黑体", Font.PLAIN, 16));
	        addButton.setForeground(Color.black);
	        
	        /**
	         * 位置
	         */
	        jPanel4.setLayout(null);
	        jLabel.setBounds(200, 30, 230, 30);
	        provinceLable.setBounds(60, 150, 100, 30);
	        provinceField.setBounds(160, 150, 230, 30);
	        cityLable.setBounds(60, 200, 100, 30);
            cityField.setBounds(160, 200, 230, 30);
            streetLabel.setBounds(60, 250, 100, 30);
            streetField.setBounds(160, 250, 230, 30);
	        detailAddLable.setBounds(60, 300, 100, 30);
	        detailAddField.setBounds(160, 300, 230, 30);
	        memberLabel.setBounds(60, 100, 100, 30);
            memberFeild.setBounds(160, 100, 230, 30);
	        mobileLabel.setBounds(60, 350, 100, 30);
	        mobileField.setBounds(160, 350, 230, 30);
	        zipLabel.setBounds(60, 400, 100, 30);
	        zipField.setBounds(160, 400, 230, 30);
	        addButton.setBounds(210, 450, 80, 30);
	        /**
	         * 布局
	         */
	        jPanel4.add(jLabel);
//	        jPanel4.add(jcb);
//	        jPanel4.add(jl);
	        jPanel4.add(provinceLable);
	        jPanel4.add(provinceField);
	        jPanel4.add(cityLable);
            jPanel4.add(cityField);
            jPanel4.add(streetLabel);
            jPanel4.add(streetField);
            jPanel4.add(detailAddLable);
            jPanel4.add(detailAddField);
            jPanel4.add(memberLabel);
            jPanel4.add(memberFeild);
	        jPanel4.add(mobileLabel);
	        jPanel4.add(mobileField);
	        jPanel4.add(zipLabel);
	        jPanel4.add(zipField);
	        jPanel4.add(addButton);
	        
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
	        
	        for (int i=0;i<streetItem.length;i++){
                streetField.addItem(streetItem[i]);
            }
	        for (int i=0;i<cityItem.length;i++){
	        	cityField.addItem(cityItem[i]);
	        }
	        
	        @SuppressWarnings("unused")
            class streetFieldListener implements ItemListener {
	        	
	        	@Override
	        	public void itemStateChanged(ItemEvent e) {
	        		if(e.getStateChange() == ItemEvent.SELECTED)
	        		{
	        			streets=streetField.getSelectedItem().toString();
	        		}
	        	}
	        }
	        
	        class cityFiledListener implements ItemListener{

				@Override
				public void itemStateChanged(ItemEvent e) {
				   if(e.getStateChange() == ItemEvent.SELECTED){
                        citys=cityField.getSelectedItem().toString();
                        String[] streetItem = getStreet();
                        streetField.removeAllItems();
//                        streetField.removeItemListener(new streetFieldListener());
                        for (int i=0;i<streetItem.length;i++){
                            streetField.addItem(streetItem[i]);
                        }
//                        streetField.addItemListener(new streetFieldListener());
                    }
				}
	        	
	        }
	        
	        // 添加监听事件
	        provinceField.addItemListener(new ItemListener() {
                
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if(e.getStateChange() == ItemEvent.SELECTED)
                    {
                        provinces = provinceField.getSelectedItem().toString();
                        String[] cityItem = getCity();
                        cityField.removeAllItems();
                        cityField.removeItemListener(new cityFiledListener());
                        for (int i=0;i<cityItem.length;i++){
                            cityField.addItem(cityItem[i]);
                        }
                        cityField.addItemListener(new cityFiledListener());
                        /*SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });*/
                            
                    }
                }
            });
	        
	       
//	        cityField.addItemListener(new ItemListener() {
//                
//                @Override
//                public void itemStateChanged(ItemEvent e) {
//                    if(e.getStateChange() == ItemEvent.SELECTED)
//                    {
//                        citys=cityField.getSelectedItem().toString();
//                        String[] streetItem = getStreet();
//                        streetField.removeAllItems();
//                        for (int i=0;i<streetItem.length;i++){
//                            streetField.addItem(streetItem[i]);
//                        }
//                        /*SwingUtilities.invokeLater(new Runnable() {
//                            @Override
//                            public void run() {
//                            }
//                        });*/
//                    }
//                }
//            });
//	        streetField.addItemListener(new ItemListener() {
//                
//                @Override
//                public void itemStateChanged(ItemEvent e) {
//                    if(e.getStateChange() == ItemEvent.SELECTED)
//                    {
//                        streets=streetField.getSelectedItem().toString();
//                    }
//                }
//            });
	        
	        
	        addButton.addActionListener(new activationListener());
	        provinceField.addKeyListener(new keyListener());
	        jFrameM.addWindowListener(new WindowAdapter() {
	            public void windowClosing(WindowEvent e) {
	                jFrameM.dispose();
	            }
	        });
	        
	    }
	        
	        class resetListener implements ActionListener{

	            @Override
	            public void actionPerformed(ActionEvent e) {
	                memberLabel.setText("");
	                memberLabel.requestFocus();
	            }
	            
	        }
	        public class activationListener implements ActionListener {

	            @Override
	            public void actionPerformed(ActionEvent e) {
	                memberAddAddressMethod();
	            }

	        }
	        public class keyListener implements KeyListener{

	            @Override
	            public void keyTyped(KeyEvent e) {
	                
	            }

	            @Override
	            public void keyPressed(KeyEvent e) {
	                if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
	                     jFrameM.dispose();
	                 }
	                if(e.getKeyCode()==KeyEvent.VK_ENTER){
	                    memberAddAddressMethod();
	                }
	            }

	            @Override
	            public void keyReleased(KeyEvent e) {
	                
	            }
	            
	        }
	        //添加地址监听器方法
	        @SuppressWarnings("unused")
            public void memberAddAddressMethod(){

//	            String province = provinceField.getText();
//	            String city = cityField.getText();    
//	            String region = streetField.getText();  
	            String province = (String)provinceField.getSelectedItem();
	            String city = (String)cityField.getSelectedItem();
	            String region = (String)streetField.getSelectedItem();
	            String addr = detailAddField.getText();   
	            String zip = zipField.getText();
	            String shipName = memberFeild.getText();
	            String mobile = mobileField.getText();
	            String member_id = O2OMainMenu.memberNumber.getText();
	            String country = "中国";
	            int province_id = 0;
	            int city_id = 0;
	            int region_id = 0;
	            String sqls = "select region_id from es_regions where region_grade = 1 and local_name = '"+province+"'";
	            ResultSet rs1 = new SetSql().setSql("select region_id from es_regions where region_grade = 1 and local_name = '"+province+"'");
	            try {
                    rs1.next();
                    province_id = rs1.getInt("region_id");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
	            ResultSet rs2 = new SetSql().setSql("select region_id from es_regions where region_grade = 2 and local_name = '"+city+"'");
                try {
                    rs2.next();
                    city_id = rs2.getInt("region_id");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                ResultSet rs3 = new SetSql().setSql("select region_id from es_regions where region_grade = 3 and local_name = '"+region+"'");
                try {
                    rs3.next();
                    region_id = rs3.getInt("region_id");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
	            //O2OMainMenu.internetPass==true时代表联网状态
	            if(!O2OMainMenu.internetPass){
	                ResultSet rs = new SetSql().setSql("select * from es_member where card_id = " +
                    		"'"+O2OMainMenu.memberNumber.getText()+"'");
//	                member_id = rs.getInt("member_id");
                    if(province.length() >0 && city.length() > 0 
                            && addr.length() > 0 && zip.length() > 0 && shipName.length()>0 && mobile.length() > 0){
                            String sql = "insert into es_member_address (member_id,province,city,region,addr,zip,name,mobile,country,province_id,city_id,region_id,export_status) " +
                            		"value('"+member_id+"','"+province+"','"+city+"','"+region+"','"+addr+"','"+zip+"','"+shipName+"','"+mobile+"','"+country+"',"+province_id+","+city_id+","+region_id+",1)";
                            if(new SetSql().setSqlNotReturn(sql)){
                            	//如果新增的地址信息插入到了收银机的数据库中，则同时备份到硬盘的sql文件中
                            	new DoBackup().realTimeBackup(sql);
                            }
                        JOptionPane.showMessageDialog(jFrameM, "地址添加成功");
                        jFrameM.dispose();
                    }else{
                    JOptionPane.showMessageDialog(jFrameM, "请补充完整要填的项");
                    provinceField.requestFocus();
                    
                    }
	        }else{
	            if(province.length() > 0 && city.length() > 0 
	                    && addr.length() > 0 && zip.length() > 0 &&shipName.length()>0 && mobile.length() > 0){
	                if(null==region){
	                    region = "";
	                }
	                String sql = "insert into es_member_address (member_id,province,city,region,addr,zip,name,mobile,country,province_id,city_id,region_id,export_status) " +
                            "value('"+member_id+"','"+province+"','"+city+"','"+region+"','"+addr+"','"+zip+"','"+shipName+"','"+mobile+"','"+country+"',"+province_id+","+city_id+","+region_id+",0)";
                    if(new SetSql().setSqlNotReturn(sql)){
                        //如果新增的地址信息插入到了收银机的数据库中，则同时备份到硬盘的sql文件中
                        new DoBackup().realTimeBackup(sql);
                    }
//	                String sql1 = "insert into es_member_address (province,city,region,addr,zip,name,mobile,country,province_id,city_id,region_id,member_id) " +
//                            "value('"+province+"','"+city+"','"+region+"','"+addr+"','"+zip+"','"+shipName+"','"+mobile+"','"+country+"',"+province_id+","+city_id+","+region_id+",";
	            MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
	            queryParam.add("province", province);
	            queryParam.add("city",city);
	            queryParam.add("region",region);
	            queryParam.add("addr",addr);
	            queryParam.add("zip",zip);
	            queryParam.add("mobile", mobile);
	            queryParam.add("card_id", member_id);
	            queryParam.add("name",shipName);
	            queryParam.add("country", country);
	            queryParam.add("province_id", province_id+"");
	            queryParam.add("city_id", city_id+"");
	            queryParam.add("region_id", region_id+"");
	            queryParam.add("phone", mobile);
	            String url = PropertiesUtil.getIp() + "/api/shop/memberAddress!addOfflineAddress.do";
	            ConnectOnlineMethod connect = new ConnectOnlineMethod();
	            List<JSONObject> lists = new ArrayList<JSONObject>();
	            String outPut = "";
                   try {
                        outPut = connect.connectOnline(queryParam, url);
                        if("false".equals(outPut)||"".equals(outPut)){
                        	JOptionPane.showMessageDialog(jFrameM, "地址添加失败");
                        	return;
                        }
                        JOptionPane.showMessageDialog(jFrameM, "地址添加成功");
        	            jFrameM.dispose();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
	            }else{ 
	                 JOptionPane.showMessageDialog(jFrameM, "请补充完整要填的项");
	                 provinceField.requestFocus();
	                 }
	            }
	            }
	        public String[] getRegions(){
	            String[] items = new String[34];
	            String regions = null;
	            int a ;
	            for(a = 2;a<=35;a++){
	                String sql = "select local_name from es_regions where region_id ="+a+" ";
	                ResultSet rs = new SetSql().setSql(sql);
	                try {
                        if(rs.next()){
                            regions = rs.getString("local_name");
                            items[a-2] = regions;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
	            }
	            return items;
	        }
	        public String[] getCity(){
	            String[] items = null;
	            int count = 0;
	            String sql1 = "select count(local_name) from es_regions where p_region_id = (select region_id from es_regions where p_region_id = 1 and local_name='"+provinces+"')";
	            ResultSet rss = new SetSql().setSql(sql1);
	            try {
                    if(rss.next()){
                        count = rss.getInt(1);
                        items = new String[count];
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                String regions = null;
                        String sql = "select local_name from es_regions where p_region_id = (select region_id from es_regions where p_region_id = 1 and local_name='"+provinces+"')";
                        ResultSet rs = new SetSql().setSql(sql);
                        
                        for(int a = 0;a<count;a++){
                        try {
                            if(rs.next()){
                                regions = rs.getString("local_name");
                                items[a] = regions;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        }
                    return items;
            }
	        public String[] getDefaultCity(String defaultProvinces ){
	        	String[] items = null;
	        	int count = 0;
	        	String sql1 = "select count(local_name) from es_regions where p_region_id = (select region_id from es_regions where p_region_id = 1 and local_name='"+defaultProvinces+"')";
	        	ResultSet rss = new SetSql().setSql(sql1);
	        	try {
	        		if(rss.next()){
	        			count = rss.getInt(1);
	        			items = new String[count];
	        		}
	        	} catch (SQLException e1) {
	        		e1.printStackTrace();
	        	}
	        	String regions = null;
	        	String sql = "select local_name from es_regions where p_region_id = (select region_id from es_regions where p_region_id = 1 and local_name='"+defaultProvinces+"')";
	        	ResultSet rs = new SetSql().setSql(sql);
	        	
	        	for(int a = 0;a<count;a++){
	        		try {
	        			if(rs.next()){
	        				regions = rs.getString("local_name");
	        				items[a] = regions;
	        			}
	        		} catch (SQLException e) {
	        			e.printStackTrace();
	        		}
	        	}
	        	return items;
	        }
	        public String[] getStreet(){
                String[] items = null;
                int count = 0;
                String sql2 = "select count(local_name) from es_regions where p_region_id = (select region_id from es_regions where region_grade = 2 and local_name='"+citys+"')";
                ResultSet rss = new SetSql().setSql(sql2);
                try {
                    if(rss.next()){
                        count = rss.getInt(1);
                        items = new String[count];
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                String regions = null;
                        String sql = "select local_name from es_regions where p_region_id = (select region_id from es_regions where region_grade = 2 and local_name='"+citys+"')";
                        ResultSet rs = new SetSql().setSql(sql);
                        
                        for(int a = 0;a<count;a++){
                        try {
                            if(rs.next()){
                                regions = rs.getString("local_name");
                                items[a] = regions;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        }
                    return items;
            }
	        public String[] getDefaultStreet(String defaultCitys){
	        	String[] items = null;
	        	int count = 0;
	        	String sql2 = "select count(local_name) from es_regions where p_region_id = (select region_id from es_regions where region_grade = 2 and local_name='"+defaultCitys+"')";
	        	ResultSet rss = new SetSql().setSql(sql2);
	        	try {
	        		if(rss.next()){
	        			count = rss.getInt(1);
	        			items = new String[count];
	        		}
	        	} catch (SQLException e1) {
	        		e1.printStackTrace();
	        	}
	        	String regions = null;
	        	String sql = "select local_name from es_regions where p_region_id = (select region_id from es_regions where region_grade = 2 and local_name='"+defaultCitys+"')";
	        	ResultSet rs = new SetSql().setSql(sql);
	        	
	        	for(int a = 0;a<count;a++){
	        		try {
	        			if(rs.next()){
	        				regions = rs.getString("local_name");
	        				items[a] = regions;
	        			}
	        		} catch (SQLException e) {
	        			e.printStackTrace();
	        		}
	        	}
	        	return items;
	        }
	        public static void main(String[] args) {
	            MemberAddress a = new MemberAddress();
//	            System.out.println(a.getRegions());
	            a.init();
	        }
}
