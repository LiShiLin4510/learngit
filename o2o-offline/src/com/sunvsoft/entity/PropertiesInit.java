package com.sunvsoft.entity;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;

import com.sunvsoft.backup.PropertiesUtil;

public class PropertiesInit {
    private int index = 0;
    public static Integer customerPort = 1;     //客显种类选择 1：普通 2：海信
    public static String posPort = null;        //pos机端口    
    public static String HSPort = null;         //海信客显端口
    public String userName = null;              //数据库用户名
    public String password = null;              //数据库密码
    public String diskName = null;              //备份硬盘名
    public String uDiskName = null;             //UK名
    public String posNum = null;                //POS机编号
    public String storeId = null;               //店铺编号
    public String storeName = null;             //实体店名称
    public String ip = null;                    //联网地址
    public String xtrans = null;                //小票打印平移量
    
    JFrame jf = new JFrame();
    JPanel jp1 = new JPanel();
    JPanel jp2 = new JPanel();
    JPanel jp3 = new JPanel();
    JPanel jp4 = new JPanel();
    JPanel jp5 = new JPanel();
    JPanel jp6 = new JPanel();
    JPanel jp7 = new JPanel();
    JPanel jp8 = new JPanel();
    JPanel jp13 = new JPanel();
    JPanel jp9 = new JPanel();
    JPanel jp10 = new JPanel();
    JPanel jp11 = new JPanel();
    JPanel jp12 = new JPanel();
    
    JPanel jp = new JPanel();
    
    JLabel jl = new JLabel("配置信息");
    JLabel jl1 = new JLabel("数据库用户名：");
    JLabel jl2 = new JLabel("数据库密码：");
    JLabel jl3 = new JLabel("备份磁盘名：");
    JLabel jl4 = new JLabel("U盘名：");
    JLabel jl5 = new JLabel("客显种类：");
    JLabel jl6 = new JLabel("POS机编号：");
    JLabel jl7 = new JLabel("POS机端口：");
    JLabel jl8 = new JLabel("店铺编号：");
    JLabel jl13 = new JLabel("实体店名称：");
    JLabel jl9 = new JLabel("IP：");
    JLabel jl10 = new JLabel("客显端口：");
    JLabel jl11 = new JLabel("打印水平偏移量：");
    JTextField jtf1 = new JTextField(18);
    JTextField jtf2 = new JTextField(18);
    JTextField jtf3 = new JTextField(18);
    JTextField jtf4 = new JTextField(18);
    @SuppressWarnings("rawtypes")
    JComboBox jcb5 = new JComboBox();
    JTextField jtf6 = new JTextField(18);
    @SuppressWarnings("rawtypes")
    JComboBox jcb7 = new JComboBox();
    JTextField jtf8 = new JTextField(18);
    JTextField jtf13 = new JTextField(18);
    JTextField jtf9 = new JTextField(18);
    @SuppressWarnings("rawtypes")
    JComboBox jcb10 = new JComboBox();
    JTextField jtf11 = new JTextField(18);
    JButton jb1 = new JButton("确定");
    JButton jb2 = new JButton("取消");
    
    private String[] comName = {"COM1", "COM2", "COM3", "COM4", "COM5"};
    private Object[] getComName() {
        Object[] os = new Object[comName.length];
        for (int i = 0; i < comName.length; i++) {
            os[i] = comName[i];
        }
        return os;
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void init(){
        jb1.addActionListener(new sureAction());
        jb2.addActionListener(new cancelAction());
        String[] comNames = {"普通客显", "海信客显"};
        jcb5 = new JComboBox(comNames);
        jcb5.addActionListener(new ActionListener() {
        @SuppressWarnings("unused")
        public void actionPerformed(ActionEvent e) {
            index = jcb5.getSelectedIndex();
            Object selected = jcb5.getSelectedItem();
            try {
                switch (index) {
                case 0:
                    customerPort = 1;
                    break;
                case 1:
                    customerPort = 2;
                    break;
                default:
                    customerPort = 1;
                    break;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    });
        jcb5.setSelectedIndex(0);
        
        jcb7 = new JComboBox(new PropertiesInit().getComName());
        jcb7.addActionListener(new ActionListener() {
        @SuppressWarnings("unused")
        public void actionPerformed(ActionEvent e) {
            index = jcb7.getSelectedIndex();
            Object selected = jcb7.getSelectedItem();
            try {
                switch (index) {
                case 0:
                    posPort = "COM1";
                    break;
                case 1:
                    posPort = "COM2";
                    break;
                case 2:
                    posPort = "COM3";
                    break;
                case 3:
                    posPort = "COM4";
                    break;
                case 4:
                    posPort = "COM5";
                    break;
                default:
                    posPort = "COM1";
                    break;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    });
        jcb7.setSelectedIndex(0);
        
        jcb10 = new JComboBox(new PropertiesInit().getComName());
        jcb10.addActionListener(new ActionListener() {
        @SuppressWarnings("unused")
        public void actionPerformed(ActionEvent e) {
            index = jcb10.getSelectedIndex();
            Object selected = jcb10.getSelectedItem();
            try {
                switch (index) {
                case 0:
                    HSPort = "1";
                case 1:
                    HSPort = "2";
                    break;
                case 2:
                    HSPort = "3";
                    break;
                case 3:
                    HSPort = "4";
                    break;
                case 4:
                    HSPort = "5";
                    break;
                default:
                    HSPort = "1";
                    break;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    });
        jcb10.setSelectedIndex(0);
        jp1.add(jl1);
        jp1.add(jtf1);
        
        jp2.add(jl2);
        jp2.add(jtf2);
        
        jp3.add(jl3);
        jp3.add(jtf3);
        
        jp4.add(jl4);
        jp4.add(jtf4);
        
        jp5.add(jl5);
        jp5.add(jcb5);
        
        jp6.add(jl6);
        jp6.add(jtf6);
        
        jp7.add(jl7);
        jp7.add(jcb7);
        
        jp8.add(jl8);
        jp8.add(jtf8);
        
        jp13.add(jl13);
        jp13.add(jtf13);
        
        jp9.add(jl9);
        jp9.add(jtf9);
        
        jp10.add(jl10);
        jp10.add(jcb10);
        
        jp11.add(jl11);
        jp11.add(jtf11);
        jp12.setLayout(new FlowLayout(FlowLayout.CENTER,50,0));
        jp12.add(jb1); 
        jp12.add(jb2);
        
//      jp.setLayout(new GridLayout(12,1,0,0));
        jp.setLayout(null);
        jp.add(jl);
        jp.add(jp1);
        jp.add(jp2);
        jp.add(jp3);
        jp.add(jp4);
        jp.add(jp5);
        jp.add(jp6);
        jp.add(jp7);
        jp.add(jp8);
        jp.add(jp13);
        jp.add(jp9);
        jp.add(jp10);
        jp.add(jp11);
        jp.add(jp12);
        
        /**
         * JComboBox固定长度
         */
        jcb5.setMaximumSize(new Dimension(207,25));
        jcb5.setMinimumSize(new Dimension(207,25));
        jcb5.setPreferredSize(new Dimension(207,25));
        
        jcb7.setMaximumSize(new Dimension(207,25));
        jcb7.setMinimumSize(new Dimension(207,25));
        jcb7.setPreferredSize(new Dimension(207,25));
        
        jcb10.setMaximumSize(new Dimension(207,25));
        jcb10.setMinimumSize(new Dimension(207,25));
        jcb10.setPreferredSize(new Dimension(207,25));
        

        
        jl.setFont(new Font("黑体", Font.PLAIN, 20));
        
        jl.setBounds(210, 10, 300, 20);
        jp1.setBounds(40, 40, 400, 30);
        jp2.setBounds(46, 70, 400, 30);
        jp3.setBounds(46, 100, 400, 30);
        jp4.setBounds(59, 130, 400, 30);
        jp5.setBounds(52, 160, 400, 30);
        jp6.setBounds(46, 190, 400, 30);
        jp7.setBounds(46, 220, 400, 30);
        jp8.setBounds(52, 250, 400, 30);
        jp13.setBounds(42, 280, 400, 30);
        jp9.setBounds(70, 310, 400, 30);
        jp10.setBounds(40, 340, 400, 30);
        jp11.setBounds(35, 370, 400, 30);
        jp12.setBounds(50, 400, 400, 30);
        
        jf.add(jp);
        
        jf.setSize(530, 500);
        jf.setLocationRelativeTo(null);// 窗口居中显示
        jf.setResizable(false);// 不可改变大小
        jf.setUndecorated(true); // 去掉窗口的装饰
        jf.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);// 采用指定的窗口装饰风格//简单对话框风格
        jf.setVisible(true);
        ImageIcon icon = new ImageIcon(PropertiesInit.class.getClassLoader()
                .getResource("images/YZF.png"));
        jf.setIconImage(icon.getImage());

    }
    public class sureAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent arg0) {
            userName = jtf1.getText();              //数据库用户名
            password = jtf2.getText();              //数据库密码
            diskName = jtf3.getText();              //备份硬盘名
            uDiskName = jtf4.getText();             //UK名
            posNum = jtf6.getText();                //POS机编号
            storeId = jtf8.getText();               //店铺编号
            ip = jtf9.getText();                    //联网地址
            storeName = jtf13.getText();            //实体店名称
            xtrans = jtf11.getText();                   //联网地址
            Map<String, String> propertiesMap = new HashMap<String, String>();
            propertiesMap.put("jdbc.user", userName);
            propertiesMap.put("jdbc.passwd", password);
            propertiesMap.put("jdbc.url", "jdbc:mysql://localhost:3306/o2ooffline?useUnicode=true&characterEncoding=utf-8");
            propertiesMap.put("jdbc.dbname", "o2ooffline");
            propertiesMap.put("jdbc.tableName", "es_order es_order_items es_sellback_list");
            propertiesMap.put("jdbc.driverClassName", "com.mysql.jdbc.Driver");
            propertiesMap.put("diskString", "K:/");
            propertiesMap.put("diskName", diskName);
            propertiesMap.put("uDiskName", uDiskName);
            propertiesMap.put("displayCat", customerPort+"");
            propertiesMap.put("posnum", posNum);
            propertiesMap.put("cardPort", posPort);
            propertiesMap.put("storeId", storeId);
            propertiesMap.put("ip", ip);
            propertiesMap.put("storeName", storeName);
            propertiesMap.put("nPort", HSPort);
            propertiesMap.put("xtrans", xtrans);
            try {
                new PropertiesUtil().setValue(propertiesMap);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(jf, "配置文件设置失败");
                System.exit(0);
            }
            JOptionPane.showMessageDialog(jf, "配置文件设置成功，请重新启动程序");
            System.exit(0);
        }
        
    }
    public class cancelAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent arg0) {
            System.exit(0);
        }
        
    }
    public static void main(String[] args){
        
//      JFrame.setDefaultLookAndFeelDecorated(true);
//      JDialog.setDefaultLookAndFeelDecorated(true);
//      // 必须要启动这个线程，不然无法达到换肤效果
//      SwingUtilities.invokeLater(new Runnable() {
//          public void run() {
//              try {
//                                  UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceFieldOfWheatLookAndFeel");
//                          } catch (Exception e1) {
//                              e1.printStackTrace();
//                          }
//                      }
//                  });
        PropertiesInit a = new PropertiesInit();
        a.init();
    }
}
