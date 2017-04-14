package com.sunvsoft.entity;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sunvsoft.accessOnline.ConnectOnlineMethod;
import com.sunvsoft.backup.PropertiesUtil;
import com.sunvsoft.sqlset.SetSql;

public class DetailAddress {

    private static DetailAddress detailAddress = null;
    public DetailAddress(){}
    public static DetailAddress getInstance(){
        if(detailAddress == null){
            detailAddress = new DetailAddress();
        }
        return detailAddress;
    }
    International international = International.getInstance();
    JFrame jf = new JFrame();
//    Object[] columnNames = { "选择","收货人","省份", "城市", "地区", "详细地址"};
    Object[] columnNames = { international.getInternational("choose"),international.getInternational("consignee"),
				    		international.getInternational("province"), international.getInternational("city"), 
				    		international.getInternational("region"), international.getInternational("detailedAddress")};
    DefaultTableModel model;
    JTable table;
    JScrollPane jScrollPane;
    JLabel jl = new JLabel(international.getInternational("choose1"));
    JPanel jp = new JPanel();
    JPanel jp1 = new JPanel();
    JPanel jp2 = new JPanel();
    Object[][] data = null;
    public O2OMainMenu omm;
    public void init() {
        jl.setFont(new Font("黑体", Font.PLAIN, 20));
        jl.setHorizontalAlignment(JTextField.CENTER);// 居中显示
        
        data = getData();
        model = new DefaultTableModel(data, columnNames) {
            /**    
             * serialVersionUID:        
             */    
            
            private static final long serialVersionUID = 7406921551847070098L;

            public boolean isCellEditable(int row,
                    int column) {
                return false;
            };

        };
        
        table = new JTable(model);

        // table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        table.getColumnModel().getColumn(0).setMinWidth(0);
//        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
//        table.getColumnModel().getColumn(0).setResizable(false);
        table.getColumnModel().getColumn(1).setPreferredWidth(20);
        table.getColumnModel().getColumn(2).setPreferredWidth(20);
        table.getColumnModel().getColumn(3).setPreferredWidth(20);
        table.getColumnModel().getColumn(4).setPreferredWidth(20);
        table.getColumnModel().getColumn(5).setPreferredWidth(170);
        
        /**
         * 使table数据居中显示
         */
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, r);
        // table键盘监听
        table.addKeyListener(new ModelChoice());
        


        jScrollPane = new JScrollPane(table,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        table.getColumnModel().getColumn(0)
        .setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value,
                    boolean isSelected,
                    boolean hasFocus, int row,
                    int column) {
                // 创建用于返回的渲染组件
                JCheckBox ck = new JCheckBox();
                // 使具有焦点的行对应复选框选中
                ck.setSelected(isSelected);
                // 使复选框在单元格内居中显示
                ck.setHorizontalAlignment((int) 0.5f);
//                ck.setBackground(Color.white);
                return ck;
            }
        });
        jp1.setBounds(0, 0, 0, 10);
        jp2.setBounds(0, 0, 0, 10);
        jp.setLayout(new BorderLayout());
        jp.add(jp1, BorderLayout.NORTH);
        jp.add(jl, BorderLayout.CENTER);
        jp.add(jp2, BorderLayout.SOUTH);

        jf.setLayout(new BorderLayout());
        jf.add(jp, BorderLayout.NORTH);
        jf.add(jScrollPane, BorderLayout.CENTER);

        jf.setSize(800, 450);
        jf.setLocationRelativeTo(null);// 窗口居中显示
        ImageIcon icon = new ImageIcon(orderCheckWindow.class.getClassLoader()
                .getResource("images/YZF.png"));
        jf.setIconImage(icon.getImage());
        jf.setResizable(false);// 不可改变大小
        //加上这个修饰的话，点击订单查询时窗口会消失才
//      jf.setUndecorated(true); // 去掉窗口的装饰 
        jf.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);// 采用指定的窗口装饰风格//简单对话框风格
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
        
        
    }
    private Object[][] getData(){
        List<Object[]> listArray = new ArrayList<Object[]>();
        String sqls = "SELECT addr_id ,name,province , city , region , addr FROM es_member_address" +
                " WHERE member_id ='"+O2OMainMenu.memberNumber.getText()+"'";
        String sql = "SELECT addr_id ,name,province , city , region , addr FROM es_member_address " +
        		"WHERE member_id = (select member_id from es_member_card where card_id ='"+O2OMainMenu.memberNumber.getText()+"')";  
        if(!O2OMainMenu.internetPass){
            ResultSet rs = new SetSql().setSql(sqls);
            try {
                while(rs.next()){
                    Object[] objects = new Object[] { 
                            rs.getInt("addr_id"),
                            rs.getString("name"),
                            rs.getString("province"),
                            rs.getString("city"),
                            rs.getString("region"),
                            rs.getString("addr"),};
                    listArray.add(objects);
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }}else{
        MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
        queryParam.add("card_id",O2OMainMenu.memberNumber.getText().toString());
        queryParam.add("mobile",O2OMainMenu.memberNumber.getText().toString());
        queryParam.add("sql", sql);
        String url = PropertiesUtil.getIp() + "/api/shop/memberAddress!getAddressByOffline.do";
        ConnectOnlineMethod connect = new ConnectOnlineMethod();
        List<JSONObject> lists = new ArrayList<JSONObject>();
            String outPut;
            try {
                outPut = connect.connectOnline(queryParam, url);
//                int a = outPut.length();
                if(outPut.length()>37){
                    JSONArray array = connect.jsonConvertion(outPut);
                    lists = connect.getJsonObjects(array);
                    for(JSONObject object : lists){
                        Object[] objects = new Object[] {
                                object.getInt("addr_id"),
                                object.get("name"),
                                object.getString("province"),
                                object.getString("city"),
                                object.getString("region"),
                                object.getString("addr"),};
                        listArray.add(objects);
                        }
                    }
            } catch (IOException e1) {
                e1.printStackTrace();
            }}
            data = listArray.toArray(new Object[0][0]);
            return data;
    }
    public class ModelChoice implements KeyListener {
        public void keyTyped(KeyEvent e) {
            
        }

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                  String addr_id = table.getValueAt(table.getSelectedRow(), 0)+"";
                  String addDetail = table.getValueAt(table.getSelectedRow(), 5)+"";
                  String province = table.getValueAt(table.getSelectedRow(), 2)+"";
                  String city = table.getValueAt(table.getSelectedRow(), 3)+"";
                  String street = table.getValueAt(table.getSelectedRow(), 4)+"";
                  O2OMainMenu.addrId.setText(addr_id);
                  jf.dispose();
                  O2OMainMenu.addressLabel.setVisible(true);
                  O2OMainMenu.addressLabel.setText(international.getInternational("harvestAddress")+province+city+street+addDetail);
            }
            if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
                jf.dispose();
            }
        }

        public void keyReleased(KeyEvent e) {
            
        }
    }
}
