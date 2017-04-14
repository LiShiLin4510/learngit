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
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xvolks.jnative.exceptions.NativeException;

import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sunvsoft.accessOnline.ConnectOnlineMethod;
import com.sunvsoft.alipay.Main;
import com.sunvsoft.backup.DoBackup;
import com.sunvsoft.backup.PropertiesUtil;
import com.sunvsoft.esc_pos.ClientDisplay;
import com.sunvsoft.esc_pos.HSCustomerShow;
import com.sunvsoft.scan.GenerateBarCode;
import com.sunvsoft.sqlset.SetSql;
import com.sunvsoft.util.SAXParser;
import com.sunvsoft.util.Sequence;
import com.sunvsoft.util.WechatAndAlipayFromYizhifu;
import com.sunvsoft.util.XmlUtil;

@SuppressWarnings("serial")
public class AliPay extends JFrame{
    // yp start
    // refNo参考号 和 cerNo凭证号 cashAmount cardAmount
    @SuppressWarnings("unused")
    private final String cardPort = PropertiesUtil.cardPort();
    private String refNo;
    private String cerNo;
    private double cardAmount;
    private boolean cardTradeFlag = false;
    private static AlipayTradeService tradeService;
    static String paidamount;
	static String allowance;
	static String coupon;
    static String discountAmount;

    public static AlipayTradeService getTradeService() {
        return tradeService;
    }

    public static void setTradeService(AlipayTradeService tradeService) {
        AliPay.tradeService = tradeService;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getCerNo() {
        return cerNo;
    }

    public void setCerNo(String cerNo) {
        this.cerNo = cerNo;
    }

    public double getCardAmount() {
        return cardAmount;
    }

    public void setCardAmount(double cardAmount) {
        this.cardAmount = cardAmount;
    }

    public boolean isCardTradeFlag() {
        return cardTradeFlag;
    }

    public void setCardTradeFlag(boolean cardTradeFlag) {
        this.cardTradeFlag = cardTradeFlag;
    }

    public JFrame getJf() {
        return jf;
    }

    public void setJf(JFrame jf) {
        this.jf = jf;
    }

    // end
//  private Properties prop1 = new Properties();
    static International international=International.getInstance();
    String baudRate = null;  
    String displayRate = null;
    DecimalFormat df = new DecimalFormat("0.00");
    static O2OMainMenu www;
    Object[][] dataMember = null;
    JFrame jf = new JFrame(international.getInternational("cashier"));
    JPanel jPanel = new JPanel();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel3 = new JPanel();
    JPanel jPanel4 = new JPanel();

    JLabel total = new JLabel(international.getInternational("total"));
    JLabel totalNum = new JLabel("",JLabel.CENTER);
    JLabel cash = new JLabel(international.getInternational("cash"));
    JTextField cashNum = new JTextField("0",JTextField.CENTER);
    JLabel ali = new JLabel(international.getInternational("alipayCode"));
    JTextField aliNum = new JTextField("0",JTextField.CENTER);
    JLabel paidamount1 = new JLabel("实收金额");
    JLabel paidamountNum = new JLabel("",JLabel.CENTER);
    //JLabel amount = new JLabel("合计");
    //JLabel amountNum = new JLabel("",JLabel.CENTER);
    //JLabel change = new JLabel("找零");
    //JLabel changeNum = new JLabel("",JLabel.CENTER);
    //JButton cashButton = new JButton("现金F1");
    //JButton cardButton = new JButton("刷卡F2");
    //JButton changeButton = new JButton("混合支付F3");
    JButton sureButton = new JButton(international.getInternational("Receivables"));
    //JButton resetButton = new JButton("重置F5");
    JButton cancelButton = new JButton(international.getInternational("cancel"));

    public void init() {
        //cashNum.requestFocus();
        //cashNum = new JTextField("0");
        //amountNum.setText("0.00");
        //changeNum.setText("0.00");
        cashNum.setDocument(new PlainDocument() {
            int MAX_LENGTH = 10;
            public void insertString(int offset, String s,
                    AttributeSet attributeSet) throws BadLocationException {
                if (s == null || offset < 0) {
                    return;
                }
                for (int i = 0; i < s.length(); i++) {
                    if (getLength() > MAX_LENGTH - 1) {
                        break;
                    }
                    super.insertString(offset + i, s.substring(i, i + 1),
                            attributeSet);
                }
                return;
            }
        });
        aliNum.setDocument(new PlainDocument() {
            int MAX_LENGTH = 18;
            public void insertString(int offset, String s,
                    AttributeSet attributeSet) throws BadLocationException {
                if (s == null || offset < 0) {
                    return;
                }
                for (int i = 0; i < s.length(); i++) {
                    if (getLength() > MAX_LENGTH - 1) {
                        break;
                    }
                    super.insertString(offset + i, s.substring(i, i + 1),
                            attributeSet);
                }
                return;
            }
        });
        
        /**
         * 注册监听器
         */
        //取消按钮监听器
        cancelButton.addActionListener(new cancelButtonListener());
        cancelButton.setMnemonic(KeyEvent.VK_2);
        sureButton.addActionListener(new sureButtonListener());
        sureButton.setMnemonic(KeyEvent.VK_1);
        //现金文本框cashNum数字变化监听器
        cashNum.getDocument().addDocumentListener(new cashNumDocumentListener());
        cashNum.addKeyListener(new cashNumKeyListener2());
        aliNum.getDocument().addDocumentListener(new cardNumDocumentListener());
        aliNum.addKeyListener(new cardNumKeyListener());
        totalNum.setText(O2OMainMenu.totalMoneyLabelChange.getText());

        /**
         * 设置位置大小
         */
//        totalNum.setBounds(155, 15, 180, 35);
//        cashNum.setBounds(165, 60, 130, 35);
        paidamountNum.setBounds(160, 15, 180, 35);
        paidamount1.setBounds(70, 15, 100, 35);
        aliNum.setBounds(190,100, 245, 35);
        //amountNum.setBounds(155, 140, 180, 35);
        //changeNum.setBounds(155, 160, 180, 70);

//        total.setBounds(110, 15, 50, 35);
//        cash.setBounds(105, 60, 50, 35);
        ali.setBounds(70, 100, 100, 35);
        //amount.setBounds(105, 140, 50, 35);
        //change.setBounds(105, 180, 50, 35);

//      cashButton.setBounds(350, 30, 75, 35);
        //cashButton.setForeground(Color.red);
//      cardButton.setBounds(350, 130,75, 35);
    //  changeButton.setBounds(350, 165, 75, 35);
        
        sureButton.setBounds(80, 190, 75, 35);
//      resetButton.setBounds(140, 220, 75, 35);
        cancelButton.setBounds(320, 190, 75, 35);
        
        /**
         * 字体
         */
//      cashButton.setFont(new Font("黑体", Font.PLAIN, 16));
//      cashButton.setForeground(Color.red);

//      cardButton.setFont(new Font("黑体", Font.PLAIN, 16));
//      cardButton.setForeground(Color.black);

//      changeButton.setFont(new Font("黑体", Font.PLAIN, 13));
//      changeButton.setForeground(Color.black);
        
        sureButton.setFont(new Font("黑体", Font.PLAIN, 16));
        sureButton.setForeground(Color.black);
        
//      resetButton.setFont(new Font("黑体", Font.PLAIN, 16));
//      resetButton.setForeground(Color.black);
        
        cancelButton.setFont(new Font("黑体", Font.PLAIN, 16));
        cancelButton.setForeground(Color.black);

//        total.setFont(new Font("黑体", Font.BOLD, 20));
//        total.setForeground(Color.red);
        
        //change.setFont(new Font("黑体", Font.BOLD, 20));
        //change.setForeground(Color.red);

//        cash.setFont(new Font("黑体", Font.PLAIN, 20));
//        cash.setHorizontalAlignment(JTextField.CENTER);// 居中显示

        ali.setFont(new Font("黑体", Font.PLAIN, 20));
        ali.setHorizontalAlignment(JTextField.CENTER);// 居中显示

        //amount.setFont(new Font("黑体", Font.PLAIN, 20));
        //amount.setHorizontalAlignment(JTextField.CENTER);// 居中显示

        //change.setFont(new Font("黑体", Font.PLAIN, 20));
        //change.setHorizontalAlignment(JTextField.CENTER);// 居中显示

//        totalNum.setFont(new Font(null, Font.BOLD, 25));
//        totalNum.setForeground(Color.red);
        

//        cashNum.setFont(new Font(null, Font.BOLD, 20));
        aliNum.setFont(new Font(null, Font.PLAIN, 20));
        
        paidamount1.setFont(new Font("黑体", Font.BOLD, 20));
        paidamount1.setForeground(Color.red);
        
        paidamountNum.setFont(new Font(null, Font.BOLD, 25));
        paidamountNum.setForeground(Color.red);
        //amountNum.setFont(new Font(null, Font.PLAIN, 20));
        //changeNum.setFont(new Font(null, Font.PLAIN, 30));
        //changeNum.setForeground(Color.red);
        
        /*JLabel line1 = new JLabel(".   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .");
        line1.setBounds(5, 170, 308, 10);
        JPanel jp = new JPanel();
        jp.setLayout(null);
        jp.setBounds(310, 0, 5, 180);
        
        //JLabel line2 = new JLabel("。");
        //line2.setBounds(0, 0, 5, 100);
        //jp.add(line2);
        for(int i=0;i<250;i++){
            JLabel line2 = new JLabel(".");
            line2.setBounds(0, i, 5, 10);
            jp.add(line2);
            i=i+20;
        }*/

        /**
         * 布局
         */
        jPanel4.setLayout(null);
        jPanel4.add(totalNum);
//        jPanel4.add(cashNum);
        //jPanel4.add(amountNum);
        jPanel4.add(aliNum);
        //jPanel4.add(changeNum);
        jPanel4.add(total);
//        jPanel4.add(cash);
        //jPanel4.add(amount);
        jPanel4.add(ali);
        //jPanel4.add(change);
//      jPanel4.add(cashButton);
//      jPanel4.add(cardButton);
//      jPanel4.add(changeButton);
        jPanel4.add(sureButton);
//      jPanel4.add(resetButton);
        jPanel4.add(cancelButton);
        jPanel4.add(paidamount1);
        jPanel4.add(paidamountNum);
//      cashNum.setMaximumSize(10);
        jPanel.setLayout(new BorderLayout());
        jPanel.add(jPanel4, BorderLayout.CENTER);
        jf.add(jPanel);
        jf.setSize(500, 300);
        jf.setLocationRelativeTo(null);// 窗口居中显示
        jf.setResizable(false);// 不可改变大小
        jf.setUndecorated(true); // 去掉窗口的装饰
        jf.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);// 采用指定的窗口装饰风格//简单对话框风格
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
        jf.getRootPane().setDefaultButton(sureButton);
        ImageIcon icon = new ImageIcon(AliPay.class.getClassLoader()
                .getResource("images/YZF.png"));
        jf.setIconImage(icon.getImage());
        paidamountNum.setText(paidamount);
//      cardNum.setText("0");
//      cashNum.setText("0");
//      
        cashNum.requestFocus();
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
                        if(cashNum.isFocusOwner()){
                            aliNum.requestFocus();
                        }else{
                            cashNum.requestFocus();
                        }
                        
                        break;
                    
                    default:
                        break;
                    }
                }
            }
        }, AWTEvent.KEY_EVENT_MASK);
    }
    
    /**
     *现金文本框cashNum数字变化监听器
     */
    public class cashNumDocumentListener implements DocumentListener{
        
        @SuppressWarnings("unused")
        @Override
        public void insertUpdate(DocumentEvent e) {
            if(!cashNum.getText().endsWith(".")){
            
            String str=cashNum.getText();
            String str2=aliNum.getText();
            boolean isNum3 = str.matches("^[0-9]+(.[0-9]{1})?$");
            boolean isNum = str.matches("^[0-9]+(.[0-9]{2})?$");//正则表达式//只能输入有两位小数的正实数
            boolean isNum2=str.matches("^[0-9]");//正则表达式//只能输入正整数
            if(isNum||isNum2||isNum3){
                
                if(aliNum.getText().length()==0){
                    str2="0";
                    //cardNum.setText("0.00");
                }
                //amountNum.setText(df.format(new BigDecimal(cashNum.getText()).add(new BigDecimal(str2))));
                //changeNum.setText(df.format(new BigDecimal(amountNum.getText()).subtract(new BigDecimal(totalNum.getText()))));
            }else{
                JOptionPane.showMessageDialog(jf,"请输入正确的格式");
                SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    // goodsNumber.setText("");
                    cashNum.setText(cashNum.getText().substring(0, cashNum.getText().length()-1));
                }
            });
                
            }
            }
        }

        @SuppressWarnings("unused")
        @Override
        public void removeUpdate(DocumentEvent e) {
            
            if(!cashNum.getText().endsWith(".")){
            String str2=aliNum.getText();
            if(aliNum.getText().length()==0){str2="0";}
            if(cashNum.getText().length()!=0){
                
            //amountNum.setText(df.format(new BigDecimal(cashNum.getText()).add(new BigDecimal(str2))));
            //changeNum.setText(df.format(new BigDecimal(amountNum.getText()).subtract(new BigDecimal(totalNum.getText()))));
            }
            else{
            //amountNum.setText(df.format(new BigDecimal(0).add(new BigDecimal(str2))));
            //changeNum.setText(df.format(new BigDecimal(amountNum.getText()).subtract(new BigDecimal(totalNum.getText()))));
            }
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            
            
        }

        
    }
    /**
     * 
     * 刷卡文本框cardNum数字变化监听器
     *
     */
    public class cardNumDocumentListener implements DocumentListener{

        @SuppressWarnings("unused")
        @Override
        public void insertUpdate(DocumentEvent e) {
            if(!aliNum.getText().endsWith(".")){
            
            String str2=cashNum.getText();
            String str=aliNum.getText();
            //if(cardNum.getText().length()==0){str="0";}
            boolean isNum3 = str.matches("^[0-9]+(.[0-9]{1})?$");
            boolean isNum = str.matches("^[0-9]+(.[0-9]{2})?$");//正则表达式//只能输入有两位小数的正实数
            boolean isNum2=str.matches("^[0-9]");//正则表达式//只能输入正整数
            if(isNum||isNum2||isNum3){
                
                if(cashNum.getText().length()==0){
                    str2="0";
                    //cashNum.setText("0.00");
                }
                //amountNum.setText(df.format(new BigDecimal(str2).add(new BigDecimal(cardNum.getText()))));
                //changeNum.setText(df.format(new BigDecimal(amountNum.getText()).subtract(new BigDecimal(totalNum.getText()))));
            }else{
                JOptionPane.showMessageDialog(jf,"请输入正确的格式");
                SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    // goodsNumber.setText("");
                    aliNum.setText(aliNum.getText().substring(0, aliNum.getText().length()-1));
                }
            });
            }
            }
        }

        @SuppressWarnings("unused")
        @Override
        public void removeUpdate(DocumentEvent e) {
            
            if(!aliNum.getText().endsWith(".")){
            String str2=cashNum.getText();
            if(cashNum.getText().length()==0){str2="0";}
            if(aliNum.getText().length()!=0){
                
            //amountNum.setText(df.format(new BigDecimal(str2).add(new BigDecimal(cardNum.getText()))));
            //changeNum.setText(df.format(new BigDecimal(amountNum.getText()).subtract(new BigDecimal(totalNum.getText()))));
                }else{
                    //amountNum.setText(df.format(new BigDecimal(str2).add(new BigDecimal(0))));
                    //changeNum.setText(df.format(new BigDecimal(amountNum.getText()).subtract(new BigDecimal(totalNum.getText()))));
                }
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            
            
        }
    }
    /**
     *取消按钮监听器
     */
    public class cancelButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            
            //JOptionPane.showMessageDialog(jf,"确定取消该交易？");
            PosAndCashNoButton pac=new PosAndCashNoButton();
            pac.init();
            jf.dispose();
            // 清空table数据
            

        }
    }
    
    /**
     *收款按钮监听器
     */
    public class sureButtonListener implements ActionListener{
        @SuppressWarnings("unused")
        @Override
        public void actionPerformed(ActionEvent e) {
            cashInCheckMemberBefore();
            DoubleScreenView dsv=new DoubleScreenView();
            dsv.clearTable();
            String sql = "select  * from es_member_address where member_id = '88888888' ";
            ResultSet rs = new SetSql().setSql(sql);
                try {
                    if(rs.next()){
                        String addr_id = rs.getInt("addr_id")+"";
                        String addDetail = rs.getString("addr");
                        String province = rs.getString("province");
                        String city = rs.getString("city");
                        String street = rs.getString("region");
                        O2OMainMenu.addrId.setText("0");
                        O2OMainMenu.addressLabel.setVisible(true);
                        O2OMainMenu.addressLabel.setText("收货地址：  "+province+city+street+addDetail);
                    }else{
                        O2OMainMenu.addrId.setText("0");
                        O2OMainMenu.addressLabel.setVisible(true);
                        O2OMainMenu.addressLabel.setText("收货地址：跨客城小店  ");
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
        }
    }
    public class cashNumKeyListener2 implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
            
            
        }

        @SuppressWarnings("unused")
        @Override
        public void keyPressed(KeyEvent e) {
            
            if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
                jf.dispose();
            }
            if(e.getKeyCode()==KeyEvent.VK_Z){
                cashInCheckMemberBefore();
                DoubleScreenView dsv=new DoubleScreenView();
                dsv.clearTable();
                String sql = "select  * from es_member_address where member_id = '88888888' ";
                ResultSet rs = new SetSql().setSql(sql);
                    try {
                        if(rs.next()){
                            String addr_id = rs.getInt("addr_id")+"";
                            String addDetail = rs.getString("addr");
                            String province = rs.getString("province");;
                            String city = rs.getString("city");
                            String street = rs.getString("region");;
                            O2OMainMenu.addrId.setText("0");
                            O2OMainMenu.addressLabel.setVisible(true);
                            O2OMainMenu.addressLabel.setText("收货地址：  "+province+city+street+addDetail);
                        }else{
                            O2OMainMenu.addrId.setText("0");
                            O2OMainMenu.addressLabel.setVisible(true);
                            O2OMainMenu.addressLabel.setText("收货地址：跨客城小店  ");
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            
            
        }
        
    }
    public class cardNumKeyListener implements KeyListener{


        @Override
        public void keyTyped(KeyEvent e) {
            
            
        }

        @Override
        public void keyPressed(KeyEvent e) {
            
            if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
                jf.dispose();
            }
            if(e.getKeyCode()==KeyEvent.VK_Z){
                cashInCheckMemberBefore();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            
            
        }
    }
    /**
     * 客显调用， O2OMainMenu中有同样的方法
     * @param dataStr  要显示的数字字符串
     * @param stateStr 亮灯状态数字字符串1:单价;   2:合计;
     *                 3:收款;   4:找零
     */
    public void sendToDisplay(String dataStr,String stateStr){
        int cat = PropertiesUtil.getDisplayCat();
        if(cat==1){
            //普通客显调用函数
            String port = "COM"+PropertiesUtil.getnPort();
            Map<String, String> map = new HashMap<String, String>();  
            map.put(ClientDisplay.PARAM_PORT_STR, port);  
            map.put(ClientDisplay.PARAM_BAUD_RATE_STR, baudRate);  
            map.put(ClientDisplay.PARAM_DISPLAY_RATE_STR, displayRate);  
            map.put(ClientDisplay.PARAM_DATA_STR, dataStr);  
            map.put(ClientDisplay.PARAM_STATE_STR, stateStr);
            try {
                ClientDisplay.sendDisplay(map);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }else{
            //海信客显调用函数
            HSCustomerShow hs = new HSCustomerShow();
            int pSrc = Integer.parseInt(stateStr);
            String pTar = dataStr;
            int MaxCount = 11;
            try {
    //          hs.HSOpenDComm(PropertiesUtil.getnPort(),0);
    //          hs.VC110_Init();
                hs.VC110_Display(pSrc, pTar, MaxCount);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NativeException e) {
                e.printStackTrace();
            }
        }
    }
       
    /**
     * 收银功能
     */
    public void cashIn() {
        if (O2OMainMenu.rejectable) {
            JOptionPane.showMessageDialog(jf, "该订单已支付");
            jf.dispose();
        } else {
//            if ("保税".equals(O2OMainMenu.table.getValueAt(0, 4))||"直邮".equals(O2OMainMenu.table.getValueAt(0, 4))) {
//                doPayForTax(jf, AliPay.this);
//            } else {
                doPay(jf, AliPay.this);
//            }
        }
    }
    /**        
            
     * 完税订单交易流程
     * @param   name    
     * @return String   
     * @Exception 异常对象       
    */
    @SuppressWarnings("static-access")
    public void doPay(JFrame jf, AliPay cashnb){

        int rows = O2OMainMenu.table.getRowCount();
        String num = null;
        int product_id = 0;
        int goods_id = 0;
        String sn2 = null;
        String name = "zhifubaozhifu";
        int tax_system = 100;
        String unit = null;
        String spec_value = null;
        Double price = (double) 0;
        String count2 = null;
        String cardNumber = O2OMainMenu.memberNumber.getText();
        long create_time = System.currentTimeMillis() / 1000;
        // long order_id = System.currentTimeMillis();
      //主订单号
        long order_id = Sequence.nextId();
        //完税订单编号
        long order_id_notax = 0;
        //包水电订单编号
        long order_id_yestax = 0;
        //只有订单编号
        long order_id_posttax = 0;
        long order_ids = 0;
        Double goods_amount = 0.00;
        Double goods_amounts = 0.00;
        for(int i = 0 ; i< O2OMainMenu.table.getRowCount(); i++){
            String tax1 =df.format(new BigDecimal(Double.parseDouble(O2OMainMenu.table.getValueAt(i, 7)+"")*Double.parseDouble(O2OMainMenu.table.getValueAt(i, 8)+"")));
            goods_amounts = Double.parseDouble(tax1);
            goods_amount = goods_amount + goods_amounts;
        }
        
//        Double goods_amount = Double.parseDouble(O2OMainMenu.totalMoneyLabelChange.getText());
        int goods_num = Integer.parseInt(O2OMainMenu.totalNumberLabelChange.getText());
        String user_name = www.operator.getText();
        Double order_amount = Double.parseDouble(O2OMainMenu.totalMoneyLabelChange.getText());
        String currency = "CNY";
        String snSts = (String) O2OMainMenu.table.getValueAt(0, 1);
        String store_id = PropertiesUtil.getStoreId();
        int status = OrderStatus.ORDER_COMPLETE;
        long timeNow = System.currentTimeMillis();
        String dateNow = new timeToDates().getTimeToDates(timeNow);
        //主订单编号
        String order_sn = new order_sn().soOrder();
        //完税订单编号
        String order_sn_notax = new order_sn().soGet();
        //保税订单编号
        String order_sn_yestax = new order_sn().soGets();
        //直邮订单编号
        String order_sn_posttax = new order_sn().soGetss();
        String sn3 = new order_sn().soGet();
        String thumbnail = null;
        String addr_id = O2OMainMenu.addrId.getText();
        if (addr_id.length() < 1) {
            addr_id = "0";
        }
      //给商品加了税金的字段
        Double taxes = 0.00;
        Double taxess = 0.00;
        Double tax = 0.00;
        Integer parent_id = 0;
        for(int i = 0 ; i< O2OMainMenu.table.getRowCount(); i++){
            taxes = Double.parseDouble(O2OMainMenu.table.getValueAt(i, 6)+"");
            String tax1 =df.format(new BigDecimal(Double.parseDouble(O2OMainMenu.table.getValueAt(i, 6)+"")*Double.parseDouble(O2OMainMenu.table.getValueAt(i, 8)+"")));
            tax = Double.parseDouble(tax1);
            taxess = tax + taxess;
        }
        
      //首信易支付微信支付宝接口
        String code = aliNum.getText().toString();
        WechatAndAlipayFromYizhifu w = new WechatAndAlipayFromYizhifu();
        String results = null;
        try {
            results = w.scanCodePayStandard(sn3, sn3, paidamount, name, "13000000000", name, "271", code);
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        
        String text = null;
        String nodeName = null;
        Map map1 = new HashMap();
        List<Map<String,String>> lists = XmlUtil.readXML(null, results);
        for (Map<String, String> map : lists) {
            for(String key: map.keySet()){
                text = map.get("text");
                nodeName = map.get("nodeName");
                map1.put(nodeName, text);
            }
        }
    //判断是否支付成功
//        if("0".equals(map1.get("status"))&&"1".equals(map1.get("pstatus"))){
            
        if(true){
        if(!O2OMainMenu.goodsCode.getText().startsWith("S")){
        ResultSet rsResultSet = new SetSql().setSql("select * from es_classes");
        int classes = 1;
//      double cardAmount;
        double cashAmount;
//      if(cardNum.getText().length()==0){cardAmount=0.00;}
//      else{cardAmount = Double.parseDouble(cardNum.getText());}
        if (cashNum.getText().length() == 0) {
            cashAmount = 0.00;
        } else {
            cashAmount = Double.parseDouble(cashnb.totalNum.getText()) - cardAmount;
        }
        try {
            rsResultSet.next();
            classes = rsResultSet.getInt("classes");
            
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
//        String payment_name = paymentName(cardAmount,cashAmount);
       
        
        String payment_name = "支付宝支付";
        double paymoney=Double.parseDouble(totalNum.getText());
        boolean a = new SetSql().setSqlNotReturn("insert into es_order(" +
                "address_id,sn,cardNumber,user_name,status,create_time," +
                "goods_amount,order_amount,goods_num,store_id,currency,date," +
                "classes,balance_status,cardAmount,cashAmount,refNo,cerNo," +
                "payment_name,payment_id,payment_type,taxes,paymoney,allowance,coupon,discountAmount) values("
                 + addr_id+ ",'"+ order_sn+ "','"+ cardNumber+ "','"
                 + user_name+ "',"+ status+ ","+ create_time+ ","+ goods_amount
                 + ","+ order_amount+ ","+ goods_num+ ",'"+ store_id+ "','"+ currency
                 + "','"+ dateNow+ "',"+ classes+ ",0,'"+ cardAmount+ "','"+ cashAmount
                 + "','"+ refNo+ "','"+ cerNo+ "','"+ payment_name+"',"+OrderStatus.ALI_PAYMENT_ID
                 +",'"+payment_name+"',"+taxess+","+paymoney+",'"+allowance+"','"+coupon+"','"+ discountAmount+"')");
    //  实时备份订单数据
        new DoBackup()
                .realTimeBackup("insert into es_order(" +
                        "address_id,sn,cardNumber,user_name,status,create_time," +
                        "goods_amount,order_amount,goods_num,store_id,currency,date," +
                        "classes,balance_status,cardAmount,cashAmount,refNo,cerNo," +
                        "payment_name,payment_id,payment_type,taxes,paymoney,allowance,coupon,discountAmount) values("
                         + addr_id+ ",'"+ order_sn+ "','"+ cardNumber+ "','"
                         + user_name+ "',"+ status+ ","+ create_time+ ","+ goods_amount
                         + ","+ order_amount+ ","+ goods_num+ ",'"+ store_id+ "','"+ currency
                         + "','"+ dateNow+ "',"+ classes+ ",0,'"+ cardAmount+ "','"+ cashAmount
                         + "','"+ refNo+ "','"+ cerNo+ "','"+ payment_name+"',"+OrderStatus.ALI_PAYMENT_ID
                         +",'"+payment_name+"',"+taxess+","+paymoney+",'"+allowance+"','"+coupon+"','"+ discountAmount+"')");
        String sss = "select order_id from es_order where sn = '"+order_sn+"'";
        ResultSet ssss = new SetSql().setSql(sss);
        try {
            if (ssss.next()){
                parent_id = ssss.getInt("order_id");
            }
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        if (a) {
            //判断是否联网，联网同步数据到线上，成功的话跟新导出状态为1，不导出数据，失败不更新导出状态，班结后导出数据
            String user = O2OMainMenu.operator.getText().toString();
            System.out.println(user+"------------------------------------------------------");
            if (O2OMainMenu.internetPass) {
                MultivaluedMap<String, String> map = new MultivaluedMapImpl();
                map.add("addr_id", addr_id);
                map.add("uname", O2OMainMenu.operator.getText().toString());
                map.add("sn3", order_sn);
                map.add("card_id", cardNumber+"");
                map.add("user_name", user_name+"");
                map.add("status", 7+"");
                map.add("create_time", create_time+"");
                map.add("goods_amount", goods_amount+"");
                map.add("order_amount", order_amount+"");
                map.add("goods_num", goods_num+"");
                map.add("currency", currency+"");
                map.add("date", dateNow);
                map.add("classes", classes+"");
                map.add("balance_status", 0+"");
                map.add("cardAmount", cardAmount+"");
                map.add("cashAmount", cashAmount+"");
                map.add("refNo", refNo+"");
                map.add("cerNo", cerNo+"");
                map.add("payment_name", payment_name);
                map.add("paymentId", OrderStatus.ALI_PAYMENT_ID+"");
                map.add("payment_type", payment_name+"");
                map.add("taxesPrice", taxes+"");
                map.add("paymoney", paymoney+"");
                map.add("store_id",store_id+"");
                map.add("allowance", allowance+"");
                map.add("coupon", coupon+"");
                map.add("discountAmount", discountAmount+"");
                
                String url = PropertiesUtil.getIp() + "/api/shop/order!orderByOffline.do";
                ConnectOnlineMethod connect = new ConnectOnlineMethod();
                try {
                    String str  = connect.connectOnline(map, url);
                    if (str.length()==29) {
                        boolean backUpOrder = new SetSql()
                        .setSqlNotReturn("update es_order set export_status = 1 where sn = '" + order_sn + "'");
                        if (backUpOrder) {
                            new DoBackup().realTimeBackup("update es_order set export_status = 1 where sn = '" + order_sn + "'");
                        }
//                        boolean backUpItems = new SetSql()
//                        .setSqlNotReturn("update es_order_items set export_status = 1 where sn = '" + sn3 + "'");
//                        if (backUpItems) {
//                            new DoBackup().realTimeBackup("update es_order_items set export_status = 1 where sn = '" + sn3
//                                    + "'");
//                        }
                    }else{
                        JOptionPane.showMessageDialog(jf, "订单上传线上失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                boolean isCompleted = new OrderAutoCommit().httpUrlConnection(order, orderItem);
            }
        }
        if (a) {
            
            //tax_no 判断为完税商品时设为true， tax_yes 为保税，tax_post 为直邮 
            boolean tax_no = false;
            boolean tax_yes = false;
            boolean tax_post = false;
            //以下分别是完税、保税、直邮订单的订单价格以及税金的总金额 ，
            double goods_amount_notax = 0.00;
            double notaxes = 0.00;
            int num_notax = 0;
            double goods_amount_yestax = 0.00; 
            double yestaxes = 0.00;
            int num_yestax = 0;
            double goods_amount_posttax = 0.00;
            double posttaxes = 0.00; 
            int num_posttax = 0;
            //判断录入商品的table中是否有三种税制的商品，如果有的话获得他们的总价以及
            for(int j = 0 ; j < rows; j++){
                String tax_sys = O2OMainMenu.table.getValueAt(j, 4).toString();
                if("完税".equals(tax_sys)||"全部".equals(tax_sys)){
                    tax_no = true;
                    double goods_amount_notaxs = Double.parseDouble(df.format(new BigDecimal(Double.parseDouble(O2OMainMenu.table.getValueAt(j, 9)+""))));
                    goods_amount_notax = goods_amount_notaxs + goods_amount_notax;
                    String tax1 =df.format(new BigDecimal(Double.parseDouble(O2OMainMenu.table.getValueAt(j, 6)+"")*Double.parseDouble(O2OMainMenu.table.getValueAt(j, 8)+"")));
                    double taxss = Double.parseDouble(tax1);
                    notaxes =taxss + notaxes;
                    int num_totaltaxs = Integer.parseInt(O2OMainMenu.table.getValueAt(j, 8)+"");
                    num_notax = num_totaltaxs + num_notax;
                }else if("保税".equals(tax_sys)){
                    tax_yes = true;
                    double goods_amount_yestaxs = Double.parseDouble(df.format(new BigDecimal(Double.parseDouble(O2OMainMenu.table.getValueAt(j, 9)+""))));
                    goods_amount_yestax = goods_amount_yestaxs + goods_amount_yestax;
                    String tax1 =df.format(new BigDecimal(Double.parseDouble(O2OMainMenu.table.getValueAt(j, 6)+"")*Double.parseDouble(O2OMainMenu.table.getValueAt(j, 8)+"")));
                    double taxss = Double.parseDouble(tax1);
                    yestaxes = taxss + yestaxes;
                    int num_yestaxs = Integer.parseInt(O2OMainMenu.table.getValueAt(j, 8)+"");
                    num_yestax = num_yestaxs + num_yestax;
                }else if("直邮".equals(tax_sys)){
                    tax_post = true;
                    double goods_amount_posttaxs = Double.parseDouble(df.format(new BigDecimal(Double.parseDouble(O2OMainMenu.table.getValueAt(j, 9)+""))));
                    goods_amount_posttax = goods_amount_posttaxs + goods_amount_posttax;
                    String tax1 =df.format(new BigDecimal(Double.parseDouble(O2OMainMenu.table.getValueAt(j, 6)+"")*Double.parseDouble(O2OMainMenu.table.getValueAt(j, 8)+"")));
                    double taxss = Double.parseDouble(tax1);
                    posttaxes = taxss + posttaxes;
                    int num_posttaxs = Integer.parseInt(O2OMainMenu.table.getValueAt(j, 8)+"");
                    num_posttax = num_posttaxs + num_posttax;
                }
            }
            if(tax_no){
                order_sn_notax = new order_sn().soGet();
                boolean a1 = new SetSql().setSqlNotReturn("insert into es_order(" +
                        "address_id,sn,cardNumber,user_name,status,create_time," +
                        "goods_amount,order_amount,goods_num,store_id,currency,date," +
                        "classes,balance_status,cardAmount,cashAmount,refNo,cerNo," +
                        "payment_name,payment_id,payment_type,taxes,paymoney,parent_id,allowance,coupon,discountAmount) values("
                         + addr_id+ ",'"+ order_sn_notax+ "','"+ cardNumber+ "','"
                         + user_name+ "',"+ status+ ","+ create_time+ ","+ goods_amount_notax
                         + ","+ goods_amount_notax+ ","+ num_notax+ ",'"+ store_id+ "','"+ currency
                         + "','"+ dateNow+ "',"+ classes+ ",0,'"+ cardAmount+ "','"+ cashAmount
                         + "','"+ refNo+ "','"+ cerNo+ "','"+ payment_name+"',"+OrderStatus.ALI_PAYMENT_ID
                         +",'"+payment_name+"',"+notaxes+","+goods_amount_notax+","+parent_id+",'"+allowance+"','"+coupon+"','"+ discountAmount+"')");
            //  实时备份订单数据
                new DoBackup()
                        .realTimeBackup("insert into es_order(" +
                                "address_id,sn,cardNumber,user_name,status,create_time," +
                                "goods_amount,order_amount,goods_num,store_id,currency,date," +
                                "classes,balance_status,cardAmount,cashAmount,refNo,cerNo," +
                                "payment_name,payment_id,payment_type,taxes,paymoney,parent_id,allowance,coupon,discountAmount) values("
                                 + addr_id+ ",'"+ order_sn_notax+ "','"+ cardNumber+ "','"
                                 + user_name+ "',"+ status+ ","+ create_time+ ","+ goods_amount_notax
                                 + ","+ goods_amount_notax+ ","+ num_notax+ ",'"+ store_id+ "','"+ currency
                                 + "','"+ dateNow+ "',"+ classes+ ",0,'"+ cardAmount+ "','"+ cashAmount
                                 + "','"+ refNo+ "','"+ cerNo+ "','"+ payment_name+"',"+OrderStatus.ALI_PAYMENT_ID
                                 +",'"+payment_name+"',"+notaxes+","+goods_amount_notax+","+parent_id+",'"+allowance+"','"+coupon+"','"+ discountAmount+"')");
                if(O2OMainMenu.internetPass){
                    MultivaluedMap<String, String> map = new MultivaluedMapImpl();
                    map.add("order_sn",order_sn+"");
                    map.add("addr_id", addr_id);
                    map.add("uname", O2OMainMenu.operator.getText().toString());
                    map.add("sn3", order_sn_notax);
                    map.add("card_id", cardNumber+"");
                    map.add("user_name", user_name+"");
                    map.add("status", 7+"");
                    map.add("create_time", create_time+"");
                    map.add("goods_amount", goods_amount_notax+"");
                    map.add("order_amount", goods_amount_notax+"");
                    map.add("goods_num", num_notax+"");
                    map.add("currency", currency+"");
                    map.add("date", dateNow);
                    map.add("classes", classes+"");
                    map.add("balance_status", 0+"");
                    map.add("cardAmount", cardAmount+"");
                    map.add("cashAmount", cashAmount+"");
                    map.add("refNo", refNo+"");
                    map.add("cerNo", cerNo+"");
                    map.add("payment_name", payment_name);
                    map.add("paymentId", OrderStatus.ALI_PAYMENT_ID+"");
                    map.add("payment_type", payment_name+"");
                    map.add("taxesPrice", notaxes+"");
                    map.add("paymoney", goods_amount_notax+"");
                    map.add("store_id",store_id+"");
                    map.add("allowance", allowance+"");
                    map.add("coupon", coupon+"");
                    map.add("discountAmount", discountAmount+"");
                    
                    String url = PropertiesUtil.getIp() + "/api/shop/order!orderByOffline.do";
                    ConnectOnlineMethod connect = new ConnectOnlineMethod();
                    try {
                        String str  = connect.connectOnline(map, url);
                        if (str.length()==29) {
                            boolean backUpOrder = new SetSql()
                            .setSqlNotReturn("update es_order set export_status = 1 where sn = '" + order_sn_notax + "'");
                            if (backUpOrder) {
                                new DoBackup().realTimeBackup("update es_order set export_status = 1 where sn = '" + order_sn_notax + "'");
                            }
//                            boolean backUpItems = new SetSql()
//                            .setSqlNotReturn("update es_order_items set export_status = 1 where sn = '" + sn3 + "'");
//                            if (backUpItems) {
//                                new DoBackup().realTimeBackup("update es_order_items set export_status = 1 where sn = '" + sn3
//                                        + "'");
//                            }
                        }else{
                            JOptionPane.showMessageDialog(jf, "订单上传线上失败");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(tax_yes){
                order_sn_yestax = new order_sn().soGets();
                boolean a11 = new SetSql().setSqlNotReturn("insert into es_order(" +
                        "address_id,sn,cardNumber,user_name,status,create_time," +
                        "goods_amount,order_amount,goods_num,store_id,currency,date," +
                        "classes,balance_status,cardAmount,cashAmount,refNo,cerNo," +
                        "payment_name,payment_id,payment_type,taxes,paymoney,parent_id,allowance,coupon,discountAmount) values("
                         + addr_id+ ",'"+ order_sn_yestax+ "','"+ cardNumber+ "','"
                         + user_name+ "',"+ status+ ","+ create_time+ ","+ goods_amount_yestax
                         + ","+ goods_amount_yestax+ ","+ num_yestax+ ",'"+ store_id+ "','"+ currency
                         + "','"+ dateNow+ "',"+ classes+ ",0,'"+ cardAmount+ "','"+ cashAmount
                         + "','"+ refNo+ "','"+ cerNo+ "','"+ payment_name+"',"+OrderStatus.ALI_PAYMENT_ID
                         +",'"+payment_name+"',"+yestaxes+","+goods_amount_yestax+","+parent_id+",'"+allowance+"','"+coupon+"','"+ discountAmount+"')");
            //  实时备份订单数据
                new DoBackup()
                        .realTimeBackup("insert into es_order(" +
                                "address_id,sn,cardNumber,user_name,status,create_time," +
                                "goods_amount,order_amount,goods_num,store_id,currency,date," +
                                "classes,balance_status,cardAmount,cashAmount,refNo,cerNo," +
                                "payment_name,payment_id,payment_type,taxes,paymoney,parent_id,allowance,coupon,discountAmount) values("
                                 + addr_id+ ",'"+ order_sn_yestax+ "','"+ cardNumber+ "','"
                                 + user_name+ "',"+ status+ ","+ create_time+ ","+ goods_amount_yestax
                                 + ","+ goods_amount_yestax+ ","+ num_yestax+ ",'"+ store_id+ "','"+ currency
                                 + "','"+ dateNow+ "',"+ classes+ ",0,'"+ cardAmount+ "','"+ cashAmount
                                 + "','"+ refNo+ "','"+ cerNo+ "','"+ payment_name+"',"+OrderStatus.ALI_PAYMENT_ID
                                 +",'"+payment_name+"',"+yestaxes+","+goods_amount_yestax+","+parent_id+",'"+allowance+"','"+coupon+"','"+ discountAmount+"')");
                if(O2OMainMenu.internetPass){
                    MultivaluedMap<String, String> map = new MultivaluedMapImpl();
                    map.add("order_sn", order_sn+"");
                    map.add("addr_id", addr_id);
                    map.add("uname", O2OMainMenu.operator.getText().toString());
                    map.add("sn3", order_sn_yestax);
                    map.add("card_id", cardNumber+"");
                    map.add("user_name", user_name+"");
                    map.add("status", 7+"");
                    map.add("create_time", create_time+"");
                    map.add("goods_amount", goods_amount_yestax+"");
                    map.add("order_amount", goods_amount_yestax+"");
                    map.add("goods_num", num_yestax+"");
                    map.add("currency", currency+"");
                    map.add("date", dateNow);
                    map.add("classes", classes+"");
                    map.add("balance_status", 0+"");
                    map.add("cardAmount", cardAmount+"");
                    map.add("cashAmount", cashAmount+"");
                    map.add("refNo", refNo+"");
                    map.add("cerNo", cerNo+"");
                    map.add("payment_name", payment_name);
                    map.add("paymentId", OrderStatus.ALI_PAYMENT_ID+"");
                    map.add("payment_type", payment_name+"");
                    map.add("taxesPrice", yestaxes+"");
                    map.add("paymoney", goods_amount_yestax+"");
                    map.add("store_id",store_id+"");
                    map.add("allowance", allowance+"");
                    map.add("coupon", coupon+"");
                    map.add("discountAmount", discountAmount+"");
                    
                    String url = PropertiesUtil.getIp() + "/api/shop/order!orderByOffline.do";
                    ConnectOnlineMethod connect = new ConnectOnlineMethod();
                    try {
                        String str  = connect.connectOnline(map, url);
                        if (str.length()==29) {
                            boolean backUpOrder = new SetSql()
                            .setSqlNotReturn("update es_order set export_status = 1 where sn = '" + order_sn_yestax + "'");
                            if (backUpOrder) {
                                new DoBackup().realTimeBackup("update es_order set export_status = 1 where sn = '" + order_sn_yestax + "'");
                            }
//                            boolean backUpItems = new SetSql()
//                            .setSqlNotReturn("update es_order_items set export_status = 1 where sn = '" + sn3 + "'");
//                            if (backUpItems) {
//                                new DoBackup().realTimeBackup("update es_order_items set export_status = 1 where sn = '" + sn3
//                                        + "'");
//                            }
                        }else{
                            JOptionPane.showMessageDialog(jf, "订单上传线上失败");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(tax_post){
                order_sn_posttax = new order_sn().soGetss();
                boolean a111 = new SetSql().setSqlNotReturn("insert into es_order(" +
                        "address_id,sn,cardNumber,user_name,status,create_time," +
                        "goods_amount,order_amount,goods_num,store_id,currency,date," +
                        "classes,balance_status,cardAmount,cashAmount,refNo,cerNo," +
                        "payment_name,payment_id,payment_type,taxes,paymoney,parent_id,allowance,coupon,discountAmount) values("
                         + addr_id+ ",'"+ order_sn_posttax+ "','"+ cardNumber+ "','"
                         + user_name+ "',"+ status+ ","+ create_time+ ","+ goods_amount_posttax
                         + ","+ goods_amount_posttax+ ","+ num_posttax+ ",'"+ store_id+ "','"+ currency
                         + "','"+ dateNow+ "',"+ classes+ ",0,'"+ cardAmount+ "','"+ cashAmount
                         + "','"+ refNo+ "','"+ cerNo+ "','"+ payment_name+"',"+OrderStatus.ALI_PAYMENT_ID
                         +",'"+payment_name+"',"+posttaxes+","+goods_amount_posttax+","+parent_id+",'"+allowance+"','"+coupon+"','"+ discountAmount+"')");
            //  实时备份订单数据
                new DoBackup()
                        .realTimeBackup("insert into es_order(" +
                                "address_id,sn,cardNumber,user_name,status,create_time," +
                                "goods_amount,order_amount,goods_num,store_id,currency,date," +
                                "classes,balance_status,cardAmount,cashAmount,refNo,cerNo," +
                                "payment_name,payment_id,payment_type,taxes,paymoney,parent_id,allowance,coupon,discountAmount) values("
                                 + addr_id+ ",'"+ order_sn_posttax+ "','"+ cardNumber+ "','"
                                 + user_name+ "',"+ status+ ","+ create_time+ ","+ goods_amount_posttax
                                 + ","+ goods_amount_posttax+ ","+ num_posttax+ ",'"+ store_id+ "','"+ currency
                                 + "','"+ dateNow+ "',"+ classes+ ",0,'"+ cardAmount+ "','"+ cashAmount
                                 + "','"+ refNo+ "','"+ cerNo+ "','"+ payment_name+"',"+OrderStatus.ALI_PAYMENT_ID
                                 +",'"+payment_name+"',"+posttaxes+","+goods_amount_posttax+","+parent_id+",'"+allowance+"','"+coupon+"','"+ discountAmount+"')");
                if(O2OMainMenu.internetPass){
                    MultivaluedMap<String, String> map = new MultivaluedMapImpl();
                    map.add("order_sn",order_sn+"");
                    map.add("addr_id", addr_id);
                    map.add("uname", O2OMainMenu.operator.getText().toString());
                    map.add("sn3", order_sn_posttax);
                    map.add("card_id", cardNumber+"");
                    map.add("user_name", user_name+"");
                    map.add("status", 7+"");
                    map.add("create_time", create_time+"");
                    map.add("goods_amount", goods_amount_posttax+"");
                    map.add("order_amount", goods_amount_posttax+"");
                    map.add("goods_num", num_posttax+"");
                    map.add("currency", currency+"");
                    map.add("date", dateNow);
                    map.add("classes", classes+"");
                    map.add("balance_status", 0+"");
                    map.add("cardAmount", cardAmount+"");
                    map.add("cashAmount", cashAmount+"");
                    map.add("refNo", refNo+"");
                    map.add("cerNo", cerNo+"");
                    map.add("payment_name", payment_name);
                    map.add("paymentId", OrderStatus.ALI_PAYMENT_ID+"");
                    map.add("payment_type", payment_name+"");
                    map.add("taxesPrice", posttaxes+"");
                    map.add("paymoney", goods_amount_posttax+"");
                    map.add("store_id",store_id+"");
                    map.add("allowance", allowance+"");
                    map.add("coupon", coupon+"");
                    map.add("discountAmount", discountAmount+"");
                    
                    String url = PropertiesUtil.getIp() + "/api/shop/order!orderByOffline.do";
                    ConnectOnlineMethod connect = new ConnectOnlineMethod();
                    try {
                        String str  = connect.connectOnline(map, url);
                        if (str.length()==29) {
                            boolean backUpOrder = new SetSql()
                            .setSqlNotReturn("update es_order set export_status = 1 where sn = '" + order_sn_posttax + "'");
                            if (backUpOrder) {
                                new DoBackup().realTimeBackup("update es_order set export_status = 1 where sn = '" + order_sn_posttax + "'");
                            }
//                            boolean backUpItems = new SetSql()
//                            .setSqlNotReturn("update es_order_items set export_status = 1 where sn = '" + sn3 + "'");
//                            if (backUpItems) {
//                                new DoBackup().realTimeBackup("update es_order_items set export_status = 1 where sn = '" + sn3
//                                        + "'");
//                            }
                        }else{
                            JOptionPane.showMessageDialog(jf, "订单上传线上失败");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            //获取订单明细，持久化到线下数据库中并且上传线上
            for (int i = 0; i < rows; i++) {
                num = String.valueOf(O2OMainMenu.table.getValueAt(i, 8));// num
                sn2 = String.valueOf(O2OMainMenu.table.getValueAt(i, 1));
                String sql2 = "select p.*,g.thumbnail from es_product p left join es_goods g on p.goods_id = g.goods_id where bar_code = '" + sn2 + "' and product_id = '"
                        + O2OMainMenu.table.getValueAt(i, 0) + "'";
                if (!O2OMainMenu.internetPass) {
                    ResultSet snResultSet = new SetSql().setSql(sql2);
                    try {
                        snResultSet.next();
                        goods_id = snResultSet.getInt("goods_id");
                        product_id = snResultSet.getInt("product_id");
                        name = snResultSet.getString("name");
                        price = Double.parseDouble(O2OMainMenu.table.getValueAt(i, 7)+"");
                        taxes = Double.parseDouble(O2OMainMenu.table.getValueAt(i, 6)+"");
                        spec_value = O2OMainMenu.table.getValueAt(i, 3).toString();
                        if(O2OMainMenu.table.getValueAt(i, 4).toString().equals("完税")){
                            tax_system = 101;
                        }else if(O2OMainMenu.table.getValueAt(i, 4).toString().equals("保税")){
                            tax_system = 100;
                        }else if(O2OMainMenu.table.getValueAt(i, 4).toString().equals("直邮")){
                            tax_system = 103;
                        }else{
                            tax_system = 102;
                        }
                        unit = O2OMainMenu.table.getValueAt(i, 5).toString();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    MultivaluedMap<String, String> queryParam3 = new MultivaluedMapImpl();
                    queryParam3.add("sql", sql2);
                    String url = PropertiesUtil.getIp() + "/api/shop/goods!getProductSpecValue.do";
                    ConnectOnlineMethod connect = new ConnectOnlineMethod();
                    List<JSONObject> list = new ArrayList<JSONObject>();
                    try {
                        String outPuts = connect.connectOnline(queryParam3, url);
                        if (outPuts != null) {
                            JSONArray array = connect.jsonConvertion(outPuts);
                            list = connect.getJsonObjects(array);
                            for (JSONObject object : list) {
                                goods_id = object.getInt("goods_id");
                                product_id = object.getInt("product_id");
                                name = object.getString("name");
                                price = Double.parseDouble(O2OMainMenu.table.getValueAt(i, 7)+"");
                                taxes = Double.parseDouble(O2OMainMenu.table.getValueAt(i, 6)+"");
                                thumbnail = object.get("thumbnail").toString();
                                spec_value = O2OMainMenu.table.getValueAt(i, 3).toString();
                                if(O2OMainMenu.table.getValueAt(i, 4).toString().equals("完税")){
                                    tax_system = 101;
                                }else if(O2OMainMenu.table.getValueAt(i, 4).toString().equals("保税")){
                                    tax_system = 100;
                                }else if(O2OMainMenu.table.getValueAt(i, 4).toString().equals("直邮")){
                                    tax_system = 103;
                                }else{
                                    tax_system = 102;
                                }
                                unit = O2OMainMenu.table.getValueAt(i, 5).toString();
                            }
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                //根据不同的税制生成订单明细 101为完税订单 100为保税订单 102为直邮订单
                if(tax_system==101||tax_system==102){
//                    sn3 = new order_sn().soGets();
                    ResultSet rs = new SetSql().setSql("select order_id from es_order where sn ='"+order_sn_notax+"'");
                    try {
                        rs.next();
                        order_id_notax = rs.getInt("order_id");
                        order_ids = order_id_notax;
                        sn3 = order_sn_notax;
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    boolean b = new SetSql().setSqlNotReturn("insert into es_order_items(order_id,goods_id,product_id," +
                            "num,bar_code,name,price,currency,sn,state,taxes,spec_value,tax_system,unit) values("
                            + order_id_notax+ ","+ goods_id+ ","+ product_id+ ","+ num+ ",'"
                            + sn2+ "','"+ name + "'," + price + ",'" + currency + "','"
                            + order_sn_notax + "',101,"+taxes+",'"+spec_value+"',"+tax_system+",'"+unit+"')");
                    
                    if (b) {
                        //              实时备份订单明细数据
                        new DoBackup()
                        .realTimeBackup("insert into es_order_items(order_id,goods_id,product_id," +
                                "num,bar_code,name,price,currency,sn,state,taxes,spec_value,tax_system,unit) values("
                                + order_id_notax+ ","+ goods_id+ ","+ product_id+ ","+ num+ ",'"
                                + sn2+ "','"+ name + "'," + price + ",'" + currency + "','"
                                + order_sn_notax + "',101,"+taxes+",'"+spec_value+"',"+tax_system+",'"+unit+"')");
                    }
                }else if(tax_system==100){
                    //保税订单明细
//                    sn3 = new order_sn().soGets();
                    ResultSet rs = new SetSql().setSql("select order_id from es_order where sn ='"+order_sn_yestax+"'");
                    try {
                        rs.next();
                        order_id_yestax = rs.getInt("order_id");
                        order_ids = order_id_yestax;
                        sn3 = order_sn_yestax;
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    boolean b = new SetSql()
                    .setSqlNotReturn("insert into es_order_items(order_id,goods_id,product_id," +
                            "num,bar_code,name,price,currency,sn,state,taxes,spec_value,tax_system,unit) values("
                            + order_id_yestax+ ","+ goods_id+ ","+ product_id+ ","+ num+ ",'"
                            + sn2+ "','"+ name + "'," + price + ",'" + currency + "','"
                            + order_sn_yestax + "',101,"+taxes+",'"+spec_value+"',"+tax_system+",'"+unit+"')");
                    
                    if (b) {
                        //              实时备份订单明细数据
                        new DoBackup()
                        .realTimeBackup("insert into es_order_items(order_id,goods_id,product_id," +
                                "num,bar_code,name,price,currency,sn,state,taxes,spec_value,tax_system,unit) values("
                                + order_id_yestax+ ","+ goods_id+ ","+ product_id+ ","+ num+ ",'"
                                + sn2+ "','"+ name + "'," + price + ",'" + currency + "','"
                                + order_sn_yestax + "',101,"+taxes+",'"+spec_value+"',"+tax_system+",'"+unit+"')");
                    }
                
                }else if(tax_system==103){
                    //直邮订单明细
//                    sn3 = new order_sn().soGets();
                    ResultSet rs = new SetSql().setSql("select order_id from es_order where sn ='"+order_sn_posttax+"'");
                    try {
                        rs.next();
                        order_id_posttax = rs.getInt("order_id");
                        order_ids = order_id_posttax;
                        sn3 = order_sn_posttax;
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    boolean b = new SetSql()
                    .setSqlNotReturn("insert into es_order_items(order_id,goods_id,product_id," +
                            "num,bar_code,name,price,currency,sn,state,taxes,spec_value,tax_system,unit) values("
                            + order_id_posttax+ ","+ goods_id+ ","+ product_id+ ","+ num+ ",'"
                            + sn2+ "','"+ name + "'," + price + ",'" + currency + "','"
                            + order_sn_posttax + "',101,"+taxes+",'"+spec_value+"',"+tax_system+",'"+unit+"')");
                    
                    if (b) {
                        //              实时备份订单明细数据
                        new DoBackup()
                        .realTimeBackup("insert into es_order_items(order_id,goods_id,product_id," +
                                "num,bar_code,name,price,currency,sn,state,taxes,spec_value,tax_system,unit) values("
                                + order_id_posttax+ ","+ goods_id+ ","+ product_id+ ","+ num+ ",'"
                                + sn2+ "','"+ name + "'," + price + ",'" + currency + "','"
                                + order_id_posttax + "',101,"+taxes+",'"+spec_value+"',"+tax_system+",'"+unit+"')");
                    }
                
                }
                if(O2OMainMenu.internetPass){
                    MultivaluedMap<String, String> maps = new MultivaluedMapImpl();
                    maps.add("order_id",order_ids+"");
                    maps.add("store_id", store_id+"");
                    maps.add("goods_id", goods_id+"");
                    maps.add("product_id",product_id+"");
                    maps.add("num",num+"");
                    maps.add("bar_code", sn2+"");
                    maps.add("name",name);
                    maps.add("price", price+"");
                    maps.add("currency", currency+"");
                    maps.add("sn3", sn3);
                    maps.add("cat_id", 0+"");
                    maps.add("state", 0+"");
                    maps.add("taxes", taxes+"");
                    maps.add("thumbnail",thumbnail);
                    String url = PropertiesUtil.getIp() + "/api/shop/order!orderItemByOffline.do";
                    ConnectOnlineMethod connect = new ConnectOnlineMethod();
                    try {
                        String str  = connect.connectOnline(maps, url);
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
                    JOptionPane.showMessageDialog(jf, "订单明细上传线上失败");
                }
            }
        }
        try {           
        String sql1 = "select count(sn) from es_order where sn LIKE 'SO20%' and date = '"+dateNow+"' and payment_id = '5'";
        ResultSet rss1 = new SetSql().setSql(sql1);
		rss1.next();
		count2 = rss1.getInt(1)+"";
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
        //sendToDisplay(changeNum.getText(), "4");
        GenerateBarCode.sn= sn3;
        GenerateBarCode barcode = new GenerateBarCode();
        barcode.GenerateBarCode();
        AliGoods.count2 = count2;
        AliGoods.row = O2OMainMenu.table.getRowCount();
        AliGoods.totalNumber = O2OMainMenu.totalNumberLabelChange.getText();
        AliGoods.totalMoney = paidamount;
        AliGoods.discountAmount = discountAmount;
        AliGoods.table = O2OMainMenu.table;
        AliGoods.sn = sn3;
        AliGoods.tax = taxess;
        AliGoods pg = new AliGoods();
        pg.start();
        JOptionPane.showMessageDialog(jf,"付款成功");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
     }else{
          //如果是订单的话拿到订单的订单状态，还有支付状态   @author lsl
            //start
//            String orderStatus = (String) O2OMainMenu.table.getValueAt(0, 4);
//            String pay_status = (String) O2OMainMenu.table.getValueAt(0, 5);
            MultivaluedMap<String, String> querymap = new MultivaluedMapImpl();
            String sql = "UPDATE es_order SET `status` = '107' , pay_status = '102' WHERE order_id = '"+snSts+"'";
            querymap.add("sql", sql);
            String url = PropertiesUtil.getIp()+ "/api/shop/goods!updateOrderOffline.do";
            ConnectOnlineMethod connect = new ConnectOnlineMethod();
            try {
                connect.connectOnline(querymap, url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            GenerateBarCode.sn= sn3;
            GenerateBarCode barcode = new GenerateBarCode();
            barcode.GenerateBarCode();
            AliGoods.count2 = count2;
            AliGoods.row = O2OMainMenu.table.getRowCount();
            AliGoods.totalNumber = O2OMainMenu.totalNumberLabelChange.getText();
            AliGoods.totalMoney = paidamount;
            AliGoods.discountAmount = discountAmount;
            AliGoods.table = O2OMainMenu.table;
            AliGoods.sn = sn3;
            AliGoods.tax = taxess;
            AliGoods pg = new AliGoods();
            pg.start();
            JOptionPane.showMessageDialog(jf,"付款成功");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                
                e1.printStackTrace();
            }
        }
        jf.dispose();
        O2OMainMenu.goodsCode.setText("");
        O2OMainMenu.totalNumberLabelChange.setText("");
        O2OMainMenu.totalMoneyLabelChange.setText("");
        O2OMainMenu.addressLabel.setVisible(false);
        }else{
            JOptionPane.showMessageDialog(jf, "支付宝支付失败");
              jf.dispose();
              PosAndCashNoButton pac=new PosAndCashNoButton();
              pac.init();
        }
    
    }
    
    @SuppressWarnings("static-access")
//    public void doPayForTax(JFrame jf, AliPay cashnb) {
//
//        int rows = O2OMainMenu.table.getRowCount();
//        String num = null;
//        int product_id = 0;
//        int goods_id = 0;
//        String sn2 = null;
//        String name = null;
//        int tax_system = 100;
//        String unit = null;
//        String spec_value = null;
//        Double price = (double) 0;
//
//        String cardNumber = O2OMainMenu.memberNumber.getText();
//        long create_time = System.currentTimeMillis() / 1000;
//        // long order_id = System.currentTimeMillis();
//        long order_id = Sequence.nextId();
//        Double goods_amount = 0.00;
//        Double goods_amounts = 0.00;
//        for(int i = 0 ; i< O2OMainMenu.table.getRowCount(); i++){
//            String tax1 =df.format(new BigDecimal(Double.parseDouble(O2OMainMenu.table.getValueAt(i, 7)+"")*Double.parseDouble(O2OMainMenu.table.getValueAt(i, 8)+"")));
//            goods_amounts = Double.parseDouble(tax1);
//            goods_amount = goods_amount + goods_amounts;
//        }
////        Double goods_amount = Double.parseDouble(O2OMainMenu.totalMoneyLabelChange.getText());
//        int goods_num = Integer.parseInt(O2OMainMenu.totalNumberLabelChange.getText());
//        String user_name = www.operator.getText();
//        Double order_amount = Double.parseDouble(O2OMainMenu.totalMoneyLabelChange.getText());
//        String currency = "CNY";
//        String store_id = PropertiesUtil.getStoreId();
//        String addr_id = O2OMainMenu.addrId.getText();
//        if (addr_id.length() < 1) {
//            addr_id = null;
//        }
//        int status = OrderStatus.ORDER_PAY_CONFIRM;
//        long timeNow = System.currentTimeMillis();
//        String dateNow = new timeToDates().getTimeToDates(timeNow);
//        String sn3 = new order_sn().soGets();;;
//        String thumbnail = null;
//        //给商品加了税金的字段
//        Double taxes = 0.00;
//        Double taxess = 0.00;
//        Double tax = 0.00;
//        for(int i = 0 ; i< O2OMainMenu.table.getRowCount(); i++){
//            taxes = Double.parseDouble(O2OMainMenu.table.getValueAt(i, 6)+"");
//            String tax1 =df.format(new BigDecimal(Double.parseDouble(O2OMainMenu.table.getValueAt(i, 6)+"")*Double.parseDouble(O2OMainMenu.table.getValueAt(i, 8)+"")));
//            tax = Double.parseDouble(tax1);
//            taxess = tax + taxess;
//        }
//      //添加商品明细到支付宝
//        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
//        for (int i = 0; i < rows; i++) {
//            num = String.valueOf(O2OMainMenu.table.getValueAt(i, 8));// num
//            sn2 = String.valueOf(O2OMainMenu.table.getValueAt(i, 1));
//            String sql2 = "select * from es_product where bar_code = '" + sn2 + "' and product_id = '"
//                    + O2OMainMenu.table.getValueAt(i, 0) + "'";
//            if (!O2OMainMenu.internetPass) {
//                ResultSet snResultSet = new SetSql().setSql(sql2);
//                try {
//                    snResultSet.next();
//                    goods_id = snResultSet.getInt("goods_id");
//                    product_id = snResultSet.getInt("product_id");
//                    name = snResultSet.getString("name");
//                    price = Double.parseDouble(O2OMainMenu.table.getValueAt(i, 7) + "");
//                    // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
//                    GoodsDetail goods1 = GoodsDetail.newInstance(String.valueOf(product_id), name, new Double(price*100).longValue(), Integer.parseInt(num));
//                    // 创建好一个商品后添加至商品明细列表
//                    goodsDetailList.add(goods1);
//                } catch (SQLException e1) {
//                    e1.printStackTrace();
//                }
//            } else {
//                MultivaluedMap<String, String> queryParam3 = new MultivaluedMapImpl();
//                queryParam3.add("sql", sql2);
//                String url = PropertiesUtil.getIp() + "/api/shop/goods!getProductSpecValue.do";
//                ConnectOnlineMethod connect = new ConnectOnlineMethod();
//                List<JSONObject> list = new ArrayList<JSONObject>();
//                try {
//                    String outPuts = connect.connectOnline(queryParam3, url);
//                    if (outPuts != null) {
//                        JSONArray array = connect.jsonConvertion(outPuts);
//                        list = connect.getJsonObjects(array);
//                        for (JSONObject object : list) {
//                            goods_id = object.getInt("goods_id");
//                            product_id = object.getInt("product_id");
//                            name = object.getString("name");
//                            price = Double.parseDouble(O2OMainMenu.table.getValueAt(i, 7) + "");
////                            taxes = Double.parseDouble(O2OMainMenu.table.getValueAt(i, 6)+"");
//                            // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
//                            GoodsDetail goods1 = GoodsDetail.newInstance(String.valueOf(product_id), name, new Double(price*100).longValue(), Integer.parseInt(num));
//                            // 创建好一个商品后添加至商品明细列表
//                            goodsDetailList.add(goods1);
//                        }
//                    }
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        }
//        
//        //支付宝支付
//        String outTradeNo = sn3;
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("aliPay", aliNum.getText().toString());
//        map.put("totalMoney", order_amount);
//        map.put("goodsDetailList", goodsDetailList);
//        map.put("outTradeNo", outTradeNo);
//        Main main=new Main();
//        String result=main.trade_pay(map);
//        //判断是否支付成功
//    if("SUCCESS".equals(result)){
//        ResultSet rsResultSet = new SetSql().setSql("select * from es_classes");
//        int classes = 1;
//        //      double cardAmount;
//        double cashAmount;
//        //      if(cardNum.getText().length()==0){cardAmount=0.00;}
//        //      else{cardAmount = Double.parseDouble(cardNum.getText());}
//        if (cashNum.getText().length() == 0) {
//            cashAmount = 0.00;
//        } else {
//            cashAmount = Double.parseDouble(cashnb.totalNum.getText()) - cardAmount;
//        }
//
//        try {
//            rsResultSet.next();
//            classes = rsResultSet.getInt("classes");
//
//        } catch (SQLException e2) {
//            
//            e2.printStackTrace();
//        }
////        String payment_name = paymentName(cardAmount, cashAmount);
//        String payment_name = "支付宝支付";
//        //      实时持久化订单数据在线下数据库
//        boolean a = new SetSql().setSqlNotReturn("insert into es_order" +
//        		"(order_id,address_id,sn,cardNumber,user_name,status," +
//        		"create_time,goods_amount,order_amount,goods_num,store_id," +
//        		"currency,date,classes,balance_status,cardAmount,cashAmount," +
//        		"refNo,cerNo,payment_name,payment_id,taxes,paymoney) values("
//                 + order_id+ ","+ addr_id+ ",'"+ sn3 + "','"+ cardNumber+ "','"
//                 + user_name+ "',"+ status+ ","+ create_time+ ","+ goods_amount
//                 + ","+ order_amount+ ","+ goods_num+ ",'"+ store_id+ "','"
//                 + currency+ "','"+ dateNow+ "',"+ classes+ ",0,'"+ cardAmount
//                 + "','"+ cashAmount + "','" + refNo + "','" + cerNo + "','" 
//                 + payment_name + "',"+OrderStatus.ALI_PAYMENT_ID+","+taxess+","+order_amount+")");
//        if (a) {
//            //  实时同步订单数据到线上平台                                        // refNo参考号 和 cerNo凭证号 cashAmount cardAmount
//            if (O2OMainMenu.internetPass) {
//                MultivaluedMap<String, String> maps = new MultivaluedMapImpl();
//                maps.add("order_id",order_id+"");
//                maps.add("addr_id", addr_id);
//                maps.add("sn3", sn3);
//                maps.add("mobile", cardNumber+"");
//                maps.add("user_name", user_name+"");
//                maps.add("status", 2+"");
//                maps.add("create_time", create_time+"");
//                maps.add("goods_amount", goods_amount+"");
//                maps.add("order_amount", order_amount+"");
//                maps.add("goods_num", goods_num+"");
//                maps.add("currency", currency+"");
//                maps.add("date", dateNow);
//                maps.add("classes", classes+"");
//                maps.add("balance_status", 0+"");
//                maps.add("cardAmount", cardAmount+"");
//                maps.add("cashAmount", cashAmount+"");
//                maps.add("refNo", refNo+"");
//                maps.add("cerNo", cerNo+"");
//                maps.add("uname",O2OMainMenu.operator.getText().toString());
//                maps.add("payment_name", payment_name);
//                maps.add("paymentId", 5+"");
////                map.add("payment_type", payment_type+"");
//                maps.add("taxesPrice", taxes+"");
////                map.add("paymoney", paymoney+"");
//                maps.add("store_id" , store_id+"");
//                
//                String url = PropertiesUtil.getIp() + "/api/shop/order!OrderByOffline.do";
//                ConnectOnlineMethod connect = new ConnectOnlineMethod();
//                try {
//                    String str  = connect.connectOnline(maps, url);
//                    if (str.length()==29) {
//                        boolean backUpOrder = new SetSql()
//                        .setSqlNotReturn("update es_order set export_status = 1 where sn = '" + sn3 + "'");
//                        if (backUpOrder) {
//                            new DoBackup().realTimeBackup("update es_order set export_status = 1 where sn = '" + sn3 + "'");
//                        }
//                    }else{
//                        JOptionPane.showMessageDialog(jf, "订单上传线上失败");
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        if (a) {
//            //  实时备份订单数据
//            new DoBackup().realTimeBackup("insert into es_order" +
//                    "(order_id,address_id,sn,cardNumber,user_name,status," +
//                    "create_time,goods_amount,order_amount,goods_num,store_id," +
//                    "currency,date,classes,balance_status,cardAmount,cashAmount," +
//                    "refNo,cerNo,payment_name,payment_id,taxes,paymoney) values("
//                     + order_id+ ","+ addr_id+ ",'"+ sn3 + "','"+ cardNumber+ "','"
//                     + user_name+ "',"+ status+ ","+ create_time+ ","+ goods_amount
//                     + ","+ order_amount+ ","+ goods_num+ ",'"+ store_id+ "','"
//                     + currency+ "','"+ dateNow+ "',"+ classes+ ",0,'"+ cardAmount
//                     + "','"+ cashAmount + "','" + refNo + "','" + cerNo + "','" 
//                     + payment_name + "',"+OrderStatus.ALI_PAYMENT_ID+","+taxess+","+order_amount+")");
//            for (int i = 0; i < rows; i++) {
//                num = String.valueOf(O2OMainMenu.table.getValueAt(i, 8));// num
//                sn2 = String.valueOf(O2OMainMenu.table.getValueAt(i, 1));
////                String sql2 = "select * from es_product where bar_code = '" + sn2 + "' and product_id = '"
////                        + O2OMainMenu.table.getValueAt(i, 0) + "'";
//                String sql2 = "select p.*,g.thumbnail from es_product p left join es_goods g on p.goods_id = g.goods_id where bar_code = '" + sn2 + "' and product_id = '"
//                        + O2OMainMenu.table.getValueAt(i, 0) + "'";
//                if (!O2OMainMenu.internetPass) {
//                    ResultSet snResultSet = new SetSql().setSql(sql2);
//                    try {
//                        snResultSet.next();
//                        goods_id = snResultSet.getInt("goods_id");
//                        product_id = snResultSet.getInt("product_id");
//                        name = snResultSet.getString("name");
//                        price = Double.parseDouble(O2OMainMenu.table.getValueAt(i, 7) + "");
//                        taxes = Double.parseDouble(O2OMainMenu.table.getValueAt(i, 6)+"");
//                        spec_value = O2OMainMenu.table.getValueAt(i, 3).toString();
//                        if(O2OMainMenu.table.getValueAt(i, 4).toString().equals("完税")){
//                            tax_system = 101;
//                        }else if(O2OMainMenu.table.getValueAt(i, 4).toString().equals("保税")){
//                            tax_system = 100;
//                        }else if(O2OMainMenu.table.getValueAt(i, 4).toString().equals("直邮")){
//                            tax_system = 103;
//                        }else{
//                            tax_system = 102;
//                        }
//                        unit = O2OMainMenu.table.getValueAt(i, 5).toString();
//                    } catch (SQLException e1) {
//                        e1.printStackTrace();
//                    }
//                } else {
//                    MultivaluedMap<String, String> queryParam3 = new MultivaluedMapImpl();
//                    queryParam3.add("sql", sql2);
//                    String url = PropertiesUtil.getIp() + "/api/shop/goods!getProductSpecValue.do";
//                    ConnectOnlineMethod connect = new ConnectOnlineMethod();
//                    List<JSONObject> list = new ArrayList<JSONObject>();
//                    try {
//                        String outPuts = connect.connectOnline(queryParam3, url);
//                        if (outPuts != null) {
//                            JSONArray array = connect.jsonConvertion(outPuts);
//                            list = connect.getJsonObjects(array);
//                            for (JSONObject object : list) {
//                                goods_id = object.getInt("goods_id");
//                                product_id = object.getInt("product_id");
//                                name = object.getString("name");
//                                price = Double.parseDouble(O2OMainMenu.table.getValueAt(i, 7) + "");
//                                taxes = Double.parseDouble(O2OMainMenu.table.getValueAt(i, 6) + "");
//                                thumbnail = object.get("thumbnail").toString();
//                                spec_value = O2OMainMenu.table.getValueAt(i, 3).toString();
//                                if(O2OMainMenu.table.getValueAt(i, 4).toString().equals("完税")){
//                                    tax_system = 101;
//                                }else if(O2OMainMenu.table.getValueAt(i, 4).toString().equals("保税")){
//                                    tax_system = 100;
//                                }else if(O2OMainMenu.table.getValueAt(i, 4).toString().equals("直邮")){
//                                    tax_system = 103;
//                                }else{
//                                    tax_system = 102;
//                                }
//                                unit = O2OMainMenu.table.getValueAt(i, 5).toString();
//                            }
//                        }
//                    } catch (IOException e1) {
//                        e1.printStackTrace();
//                    }
//                }
//                //          实时持久化订单明细在线下数据库
//                boolean b = new SetSql().setSqlNotReturn("insert into es_order_items" +
//                		"(order_id,goods_id,product_id,num,bar_code,name,price,currency," +
//                		"sn,state,taxes,spec_value,tax_system,unit) values("+ order_id+ ","+ goods_id+ ","+ product_id
//                         + ","+ num+ ",'"+ sn2+ "','"+ name+ "'," + price + ",'" + currency 
//                         + "','" + sn3 + "',0,"+taxes+",'"+spec_value+"',"+tax_system+",'"+unit+"')");
//                if (b) {
//                    //              实时同步订单明细数据到线上平台
//                    if(O2OMainMenu.internetPass){
//                        MultivaluedMap<String, String> maps = new MultivaluedMapImpl();
//                        maps.add("order_id",order_id+"");
//                        maps.add("goods_id", goods_id+"");
//                        maps.add("store_id", store_id+"");
//                        maps.add("product_id",product_id+"");
//                        maps.add("num",num+"");
//                        maps.add("bar_code", sn2+"");
//                        maps.add("name",name);
//                        maps.add("price", price+"");
//                        maps.add("currency", currency+"");
//                        maps.add("sn3", sn3);
//                        maps.add("cat_id", 0+"");
//                        maps.add("state", 101+"");
//                        maps.add("taxes", taxes+"");
//                        maps.add("thumbnail",thumbnail);
//                        String url = PropertiesUtil.getIp() + "/api/shop/order!OrderItemByOffline.do";
//                        ConnectOnlineMethod connect = new ConnectOnlineMethod();
//                        try {
//                            String str  = connect.connectOnline(maps, url);
//                            if(str.length()!=29){
//                                JOptionPane.showMessageDialog(jf, "订单明细上传线上失败");
//                            }
//                            boolean backUpItems = new SetSql()
//                            .setSqlNotReturn("update es_order_items set export_status = 1 where sn = '" + sn3 + "'");
//                            if (backUpItems) {
//                                new DoBackup().realTimeBackup("update es_order_items set export_status = 1 where sn = '" + sn3
//                                        + "'");
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }else{
//                        JOptionPane.showMessageDialog(jf, "订单明细上传线上失败");
//                    }
//                }
//                if (b) {
//                    //              实时备份订单明细数据
//                    new DoBackup()
//                            .realTimeBackup("insert into es_order_items" +
//                                    "(order_id,goods_id,product_id,num,bar_code,name,price,currency," +
//                                    "sn,state,taxes,spec_value,tax_system,unit) values("+ order_id+ ","+ goods_id+ ","+ product_id
//                                     + ","+ num+ ",'"+ sn2+ "','"+ name+ "'," + price + ",'" + currency 
//                                     + "','" + sn3 + "',0,"+taxes+",'"+spec_value+"',"+tax_system+",'"+unit+"')");
//                }
//            }
//        }
//        //客显显示内容
////        sendToDisplay(changeNum.getText(), "4");
//        AliGoods.row = O2OMainMenu.table.getRowCount();
//        AliGoods.totalNumber = O2OMainMenu.totalNumberLabelChange.getText();
//        AliGoods.totalMoney = O2OMainMenu.totalMoneyLabelChange.getText();
//        AliGoods.table = O2OMainMenu.table;
//        AliGoods.sn = sn3;
//        AliGoods.tax = taxess;
//        AliGoods pg = new AliGoods();
//        pg.start();
//        JOptionPane.showMessageDialog(jf, "付款成功");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e1) {
//            e1.printStackTrace();
//        }
//        jf.dispose();
//        O2OMainMenu.goodsCode.setText("");
//        O2OMainMenu.totalNumberLabelChange.setText("");
//        O2OMainMenu.totalMoneyLabelChange.setText("");
//        O2OMainMenu.addressLabel.setVisible(false);
//        }else{
//            JOptionPane.showMessageDialog(jf, "支付宝支付失败");
//             jf.dispose();
//          PosAndCashNoButton pac=new PosAndCashNoButton();
//              pac.init();
//        }
//    }
    public void cashInCheckMemberBefore(){
        cashIn();
    }
    /**
     * 判断支付类型
     */
    public String paymentName(double cardAmount,double cashAmount){
        String payment_name = "";
        if(cardAmount>0){
            if(cashAmount>0){
                payment_name  = "混合支付";
            }else{
                payment_name = "刷卡支付";
            }
        }else{
            payment_name = "现金支付";
        }
        return payment_name;
    }
    /**
     * 获取操作员id
     * @return String user_id
     */
    public String getUserId(){
//      String user_name1 = "U1";
        String user_name1 = O2OMainMenu.operator.getText();
        ResultSet resultset1 = new SetSql().setSql("select userid from es_user where username = '"+user_name1+"'");
        String user_id="";
        try {
            resultset1.next();
             user_id = resultset1.getString("userid");
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        user_id = getSpaceString(user_id,8);
        return user_id;
    }
    /**U1
     * 获取posNum
     * @return String posNum
     */
    public String getPosNum(){
        String posNum = getSpaceString(PropertiesUtil.getPosNum(),8);
        return posNum;
    }
    /**
     * 获取指定长度的字符串不足长度右边不空格,str位“”时,返回制定长度的空格
     * @return 
     */
    public String getSpaceString(String str,int length){
        String space=str;
        if(space.length()<length){
            int spaceLength = length-space.length();
            for(int i=0;i<spaceLength;i++){
                space = space + "\u0020";
            }
        }
        return space;
    }
    /**
     * 获取指定刷卡金额格式
     * @return String posNum
     */
    public String cardNumFormat(String cardAmounts){
        String cardAM = (int) (Double.parseDouble(cardAmounts)*100)+"";
        if(cardAM.length()<12){
            int spaceLength = 12-cardAM.length();
            for(int i=0;i<spaceLength;i++){
                cardAM = "0"+cardAM ;
            }
        } 
        return cardAM;
    }
    
    /**
     * 获取gmc界面接口参数
     * @return String posNum
     */
    public String getGMCString(String cardAmounts){
        String str = "";
        String posNum = getPosNum(); 
        String user_id =getUserId();
        String dealTyep = "00";
        String cardAM = cardNumFormat(cardAmounts);
        String dates = getSpaceString("",8);
        String ref = getSpaceString("",12);
        String cer = getSpaceString("",6);
        String rand3 = (int)(Math.random() * 900 + 100)+"";
        str = posNum + user_id + dealTyep + cardAM + dates + ref + cer + rand3;
        System.err.println(str);
        return str;
    }
    /**
     * 获取gmc界面接口参数(撤销命令)
     * @return String posNum
     */
    public String getCancelString(String cerNo){
        String str = "";
        String posNum = getPosNum(); 
        String user_id =getUserId();
        String dealTyep = "01";
        String cardAM = "000000000000";
        String dates = getSpaceString("",8);
        String ref = getSpaceString("",12);
        String cer = getSpaceString(cerNo,6);
        String rand3 = (int)(Math.random() * 900 + 100)+"";
        str = posNum + user_id + dealTyep + cardAM + dates + ref + cer + rand3;
        System.err.println(str);
        return str;
    }
//  public static void main(String[] args){
//      System.out.println(new CashNoButton().getCancelString("0.01","001449"));
//  }
    public static void main(String[] args) {
        AliPay pac = new AliPay();
         pac.init();
    }
}
