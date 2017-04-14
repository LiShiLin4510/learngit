package com.sunvsoft.entity;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
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

import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sunvsoft.accessOnline.ConnectOnlineMethod;
import com.sunvsoft.backup.DoBackup;
import com.sunvsoft.backup.PropertiesUtil;
import com.sunvsoft.esc_pos.ClientDisplay;
import com.sunvsoft.esc_pos.HSCustomerShow;
import com.sunvsoft.scan.GenerateBarCode;
import com.sunvsoft.sqlset.SetSql;
import com.sunvsoft.szpos.PosinfSZ;
import com.sunvsoft.util.Sequence;

@SuppressWarnings("serial")
public class PosAndCashNoButton extends JFrame {

	private String refNo;

    private String cerNo;

    private double cardAmount;

    private boolean cardTradeFlag = false;

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

    static O2OMainMenu mm;
    
    JFrame jf = new JFrame(international.getInternational("cashier"));

    JPanel jPanel = new JPanel();

    JPanel jPanel1 = new JPanel();

    JPanel jPanel2 = new JPanel();

    JPanel jPanel3 = new JPanel();

    JPanel jPanel4 = new JPanel();
//    JLabel total = new JLabel("总额");
    JLabel total = new JLabel(international.getInternational("amount"));

    JLabel totalNum = new JLabel("", JLabel.CENTER);
//  paidamount应付金额    
    JLabel paidamount = new JLabel(international.getInternational("PaidAmount"));

    JLabel paidamountNum = new JLabel("", JLabel.CENTER);
//    JLabel cash = new JLabel("现金");
    JLabel cash = new JLabel(international.getInternational("cash"));

    JTextField cashNum = new JTextField("0", JTextField.CENTER);
//    JLabel card = new JLabel("刷卡");
    JLabel card = new JLabel(international.getInternational("card"));

    JTextField cardNum = new JTextField("0", JTextField.CENTER);
    
    JLabel amount = new JLabel(international.getInternational("total"));

    JLabel amountNum = new JLabel("", JLabel.CENTER);

    JLabel change = new JLabel(international.getInternational("giveChange"));

    JLabel changeNum = new JLabel("", JLabel.CENTER);
    
    JLabel allowance = new JLabel(international.getInternational("allowance"));

    JTextField allowanceNum = new JTextField("0", JTextField.CENTER);
    
    JLabel coupon = new JLabel(international.getInternational("coupon"));

    JTextField couponNum = new JTextField("0", JTextField.CENTER);
    
    //JButton cashButton = new JButton("现金F1");
    //JButton cardButton = new JButton("刷卡F2");
    //JButton changeButton = new JButton("混合支付F3");
    JButton sureButton = new JButton(international.getInternational("Receivables"));
    //JButton resetButton = new JButton("重置F5");
    JButton cancelButton = new JButton(international.getInternational("cancel"));
    private JButton weiButton = new JButton(international.getInternational("weChatPayment"));
    private JButton aliButton = new JButton(international.getInternational("alipayPay"));
    public void init() {
        //cashNum.requestFocus();
        //cashNum = new JTextField("0");
        amountNum.setText("0.00");
        changeNum.setText("0.00");
        cashNum.setDocument(new PlainDocument() {
            int MAX_LENGTH = 10;

            public void insertString(int offset, String s, AttributeSet attributeSet) throws BadLocationException {
                if (s == null || offset < 0) {
                    return;
                }
                for (int i = 0; i < s.length(); i++) {
                    if (getLength() > MAX_LENGTH - 1) {
                        break;
                    }
                    super.insertString(offset + i, s.substring(i, i + 1), attributeSet);
                }
                return;
            }
        });
        cardNum.setDocument(new PlainDocument() {
            int MAX_LENGTH = 10;

            public void insertString(int offset, String s, AttributeSet attributeSet) throws BadLocationException {
                if (s == null || offset < 0) {
                    return;
                }
                for (int i = 0; i < s.length(); i++) {
                    if (getLength() > MAX_LENGTH - 1) {
                        break;
                    }
                    super.insertString(offset + i, s.substring(i, i + 1), attributeSet);
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
        
        sureButton.addActionListener(new enterButtonListener());
        
        sureButton.setMnemonic(KeyEvent.VK_F1);
        
        cardNum.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {
				cardNum.setText(df.format(new BigDecimal(paidamountNum.getText())));
				
			}

			@Override
			public void focusLost(FocusEvent e) {
				//cardNum.setText(df.format(new BigDecimal(paidamountNum.getText())));
			}
        	
        });
        
        weiButton.addActionListener(new WeiButtonActionListener());
        weiButton.setMnemonic(KeyEvent.VK_3);
        
        aliButton.addActionListener(new AliButtonActionListener());
        aliButton.setMnemonic(KeyEvent.VK_4);
        jf.getRootPane().setDefaultButton(sureButton);
      
        //现金文本框cashNum数字变化监听器
        cashNum.getDocument().addDocumentListener(new cashNumDocumentListener());
        cashNum.addKeyListener(new cashNumKeyListener2());
        cardNum.getDocument().addDocumentListener(new cardNumDocumentListener());
        cardNum.addKeyListener(new cardNumKeyListener());
        allowanceNum.getDocument().addDocumentListener(new allowanceNumDocumentListener());
        allowanceNum.addKeyListener(new allowanceNumKeyListener());
        couponNum.getDocument().addDocumentListener(new couponNumDocumentListener());
        couponNum.addKeyListener(new couponNumKeyListener());
        double x= Double.parseDouble(O2OMainMenu.totalMoneyLabelChange.getText());
        String s1 = String.format("%.1f", x);
        String totalS1 = String.format("%.2f", Double.parseDouble(s1));
        totalNum.setText(totalS1);
        paidamountNum.setText(totalS1);
        /**
         * 设置位置大小
         */
        totalNum.setBounds(70, 10, 150, 35);
        paidamountNum.setBounds(70,50,150,35);
        cashNum.setBounds(155, 90, 130, 35);
        cardNum.setBounds(155, 130, 130, 35);
        amountNum.setBounds(145, 170, 180, 35);
        changeNum.setBounds(145, 190, 180, 70);
        allowanceNum.setBounds(250,10,80,35);
        couponNum.setBounds(410,10,80,35);
        
        total.setBounds(5, 10, 90, 35);
        paidamount.setBounds(5,50,150,35);
        cash.setBounds(95, 90, 50, 35);
        card.setBounds(95, 130, 50, 35);
        amount.setBounds(95, 170, 50, 35);
        change.setBounds(95, 210, 50, 35);
        allowance.setBounds(200, 10, 50, 35);
        coupon.setBounds(340,10,70,35);

        //      cashButton.setBounds(350, 30, 75, 35);
        //cashButton.setForeground(Color.red);
        //      cardButton.setBounds(350, 130,75, 35);
        //  changeButton.setBounds(350, 165, 75, 35);

        sureButton.setBounds(100, 250, 75, 35);
        //      resetButton.setBounds(140, 220, 75, 35);
        cancelButton.setBounds(340, 250, 75, 35);
        
        
        //微信支付宝
        weiButton.setBounds(330,90,120,35);
        aliButton.setBounds(330,130,120,35);
        
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
        //微信
        weiButton.setFont(new Font("黑体", Font.PLAIN, 20));
        weiButton.setForeground(Color.black);
        //支付宝
        aliButton.setFont(new Font("黑体", Font.PLAIN, 20));
        aliButton.setForeground(Color.black);

        total.setFont(new Font("黑体", Font.BOLD, 20));
        total.setForeground(Color.red);
        
        paidamount.setFont(new Font("黑体", Font.BOLD, 20));
        paidamount.setForeground(Color.red);

        change.setFont(new Font("黑体", Font.BOLD, 20));
        change.setForeground(Color.red);

        cash.setFont(new Font("黑体", Font.PLAIN, 20));
        cash.setHorizontalAlignment(JTextField.CENTER);// 居中显示

        card.setFont(new Font("黑体", Font.PLAIN, 20));
        card.setHorizontalAlignment(JTextField.CENTER);// 居中显示

        amount.setFont(new Font("黑体", Font.PLAIN, 20));
        amount.setHorizontalAlignment(JTextField.CENTER);// 居中显示

        change.setFont(new Font("黑体", Font.PLAIN, 20));
        change.setHorizontalAlignment(JTextField.CENTER);// 居中显示

        totalNum.setFont(new Font(null, Font.BOLD, 20));
        totalNum.setForeground(Color.red);
        
        paidamountNum.setFont(new Font(null,Font.BOLD,20));
        paidamountNum.setForeground(Color.red);
        
        allowance.setFont(new Font("黑体", Font.PLAIN, 20));
        allowance.setHorizontalAlignment(JTextField.CENTER);// 居中显示
        
        coupon.setFont(new Font("黑体", Font.PLAIN, 20));
        coupon.setHorizontalAlignment(JTextField.CENTER);
        
        cashNum.setFont(new Font(null, Font.BOLD, 20));
        cardNum.setFont(new Font(null, Font.PLAIN, 20));
        amountNum.setFont(new Font(null, Font.PLAIN, 20));
        changeNum.setFont(new Font(null, Font.PLAIN, 30));
	    allowanceNum.setFont(new Font(null,Font.BOLD,20));
	    couponNum.setFont(new Font(null,Font.BOLD,20));
        changeNum.setForeground(Color.red);

        /**
         * 布局
         */
        jPanel4.setLayout(null);
        jPanel4.add(totalNum);
        jPanel4.add(cashNum);
        jPanel4.add(amountNum);
        jPanel4.add(cardNum);
        jPanel4.add(changeNum);
        jPanel4.add(total);
        jPanel4.add(paidamountNum);
        jPanel4.add(paidamount);
        jPanel4.add(cash);
        jPanel4.add(amount);
        jPanel4.add(card);
        jPanel4.add(change);
        jPanel4.add(allowance);
        jPanel4.add(allowanceNum);
        jPanel4.add(coupon);
        jPanel4.add(couponNum);
        //      jPanel4.add(cashButton);
        //      jPanel4.add(cardButton);
        //      jPanel4.add(changeButton);
        jPanel4.add(sureButton);
        //      jPanel4.add(resetButton);
        jPanel4.add(cancelButton);
        jPanel4.add(weiButton);
        jPanel4.add(aliButton);
        //      cashNum.setMaximumSize(10);
        jPanel.setLayout(new BorderLayout());
        jPanel.add(jPanel4, BorderLayout.CENTER);
        jf.add(jPanel);
        jf.setSize(550, 350);
        jf.setLocationRelativeTo(null);// 窗口居中显示
        jf.setResizable(false);// 不可改变大小
        jf.setUndecorated(true); // 去掉窗口的装饰
        jf.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);// 采用指定的窗口装饰风格//简单对话框风格
        jf.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
        ImageIcon icon = new ImageIcon(PosAndCashNoButton.class.getClassLoader()
                .getResource("images/YZF.png"));
        jf.setIconImage(icon.getImage());
        //      cardNum.setText("0");
        //      cashNum.setText("0");
        //      
        allowanceNum.requestFocus();
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
                        if (cashNum.isFocusOwner()) {
                            cardNum.requestFocus();
                        } else {
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
    public class cashNumDocumentListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            String str = cashNum.getText();
            //          加入现金输入框的校验，验证小数点的合法性（.）
            //          如果金额以小数点开头，则不合法
            if (str.startsWith(".")) {
                JOptionPane.showMessageDialog(jf, "请输入正确的格式");
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        // goodsNumber.setText("");
                        cashNum.setText(cashNum.getText().substring(0, cashNum.getText().length() - 1));
                    }
                });
                return;
            }
            //          如果金额连续出现两个小数点，则不合法
            if (str.length() >= 2) {
                if (str.substring(str.length() - 2, str.length()).equals("..")) {
                    JOptionPane.showMessageDialog(jf, "请输入正确的格式");
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            // goodsNumber.setText("");
                            cashNum.setText(cashNum.getText().substring(0, cashNum.getText().length() - 1));
                        }
                    });
                    return;
                }
            }
            if (str.indexOf(".") != str.lastIndexOf(".")) {
                JOptionPane.showMessageDialog(jf, "请输入正确的格式");
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        // goodsNumber.setText("");
                        cashNum.setText(cashNum.getText().substring(0, cashNum.getText().length() - 1));
                    }
                });
                return;
            }

            if (!cashNum.getText().endsWith(".")) {
                String str2 = cardNum.getText();

                //收银输入框
                boolean isNum3 = str.matches("^[0-9]+(.[0-9]{1})?$");
                boolean isNum = str.matches("^[0-9]+(.[0-9]{2})?$");//正则表达式//只能输入有两位小数的正实数
                boolean isNum2 = str.matches("^[0-9]");//正则表达式//只能输入正整数
                if (isNum || isNum2 || isNum3) {
                    if (str.length() <= 8) {

                        if (cardNum.getText().length() == 0) {
                            str2 = "0";
                            //                    changeNum.setText("0.00");
                            //cardNum.setText("0.00");
                        }

                        amountNum.setText(df.format(new BigDecimal(cashNum.getText()).add(new BigDecimal(str2))));
                        //输入的现金大于总的金额，找零文本域显示为0
                        if (Double.parseDouble(str) > Double.parseDouble(paidamountNum.getText()+"")
                                || Double.parseDouble(amountNum.getText()+"") > Double.parseDouble(paidamountNum.getText()+"")) {
                            changeNum.setText(df.format(new BigDecimal(amountNum.getText()+"").subtract(new BigDecimal(
                                    paidamountNum.getText()+""))));
                        } else {
                            changeNum.setText("0.00");
                        }
                    } else {
                        JOptionPane.showMessageDialog(jf, "您输入的金额过大");
                    }
                } else {
                    JOptionPane.showMessageDialog(jf, "请输入正确的格式");
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            // goodsNumber.setText("");
                            cashNum.setText(cashNum.getText().substring(0, cashNum.getText().length() - 1));
                        }
                    });
                }
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (!cashNum.getText().endsWith(".")) {
                String str2 = cardNum.getText();
                if (cardNum.getText().length() == 0) {
                    str2 = "0";
                }
                if (cashNum.getText().length() != 0) {

                    amountNum.setText(df.format(new BigDecimal(cashNum.getText()).add(new BigDecimal(str2))));
                    if (Double.parseDouble(cashNum.getText()) > Double.parseDouble(paidamountNum.getText())) {
                        changeNum.setText(df.format(new BigDecimal(amountNum.getText()).subtract(new BigDecimal(
                                paidamountNum.getText()))));
                        //                cardNum.setText(df.format(new BigDecimal(cashNum.getText()).subtract(new BigDecimal(totalNum.getText()))));
                        cardNum.setText("0");
                    } else {
                        //                cardNum.setText(df.format(new BigDecimal(totalNum.getText()).subtract(new BigDecimal(cashNum.getText()))));
                        changeNum.setText("0.00");
                    }
                } else {
                    amountNum.setText(df.format(new BigDecimal(0).add(new BigDecimal(str2))));
                    //            changeNum.setText(df.format(new BigDecimal(amountNum.getText()).subtract(new BigDecimal(totalNum.getText()))));
                    changeNum.setText("0.00");
                }
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            
        }

    }
    
    //微信支付监听器
    private class WeiButtonActionListener implements ActionListener{  
        @Override
        public void actionPerformed(ActionEvent e) {  
             WeiPay pac = new WeiPay();
             WeiPay.allowance = allowanceNum.getText()+"";
             WeiPay.coupon = couponNum.getText()+"";
             WeiPay.paidamount = paidamountNum.getText()+"";
             WeiPay.discountAmount = df.format(new BigDecimal(Double.parseDouble(totalNum.getText())-Double.parseDouble(paidamountNum.getText())));
             pac.init(); 
            jf.dispose();
        }     
    }
    
   //支付宝
    private class AliButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            AliPay ali = new AliPay();
            AliPay.allowance = allowanceNum.getText()+"";
            AliPay.coupon = couponNum.getText()+"";
            AliPay.paidamount = paidamountNum.getText()+"";
            AliPay.discountAmount = df.format(new BigDecimal(Double.parseDouble(totalNum.getText())-Double.parseDouble(paidamountNum.getText())));
            ali.init();
            jf.dispose();
        }
    }
    
    
    
    /**
     * 
     * 刷卡文本框cardNum数字变化监听器
     *
     */
    public class cardNumDocumentListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            String str = cardNum.getText();
            //          加入现金输入框的校验，验证小数点的合法性（.）
            //          如果金额以小数点开头，则不合法
            if (str.startsWith(".")) {
                JOptionPane.showMessageDialog(jf, "请输入正确的格式");
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        // goodsNumber.setText("");
                        cashNum.setText("0.00");
                    }
                });
                return;
            }
            //          如果金额连续出现两个小数点，则不合法
            if (str.length() >= 2) {
                if (str.substring(str.length() - 2, str.length()).equals("..")) {
                    JOptionPane.showMessageDialog(jf, "请输入正确的格式");
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            // goodsNumber.setText("");
                            cashNum.setText("0.00");
                        }
                    });
                    return;
                }
            }
            if (!cardNum.getText().endsWith(".")) {
                String str2 = cashNum.getText();
                //if(cardNum.getText().length()==0){str="0";}
                boolean isNum3 = str.matches("^[0-9]+(.[0-9]{1})?$");
                boolean isNum = str.matches("^[0-9]+(.[0-9]{2})?$");//正则表达式//只能输入有两位小数的正实数
                boolean isNum2 = str.matches("^[0-9]");//正则表达式//只能输入正整数
                if (isNum || isNum2 || isNum3) {
                    if (Double.parseDouble(str) > Double.parseDouble(paidamountNum.getText())) {
                        //                        JOptionPane.showMessageDialog(jf, "您输入的刷卡金额过大");
                        SwingUtilities.invokeLater(new Runnable() { //必须要启动线程修改，否则会报错
                                    @Override
                                    public void run() {
                                        cardNum.setText(df.format(new BigDecimal(paidamountNum.getText())
                                                .subtract(new BigDecimal(cashNum.getText()))));
                                    }
                                });
                    }
                    if (cashNum.getText().length() == 0) {
                        str2 = "0";
                        //cashNum.setText("0.00");
                    }
                    amountNum.setText(df.format(new BigDecimal(str2).add(new BigDecimal(cardNum.getText()))));
                    changeNum.setText(df.format(new BigDecimal(paidamountNum.getText()).subtract(new BigDecimal(cardNum
                            .getText()))));

                } else {
                    JOptionPane.showMessageDialog(jf, "请输入正确的格式");
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            // goodsNumber.setText("");
                            cardNum.setText(cardNum.getText().substring(0, cardNum.getText().length() - 1));
                        }
                    });
                }
                if ((df.format(new BigDecimal(paidamountNum.getText()).subtract(new BigDecimal(cardNum.getText()))))
                        .contains("-")) {
                    cashNum.setText("0.00");
                } else {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            // goodsNumber.setText("");
                            //                            cardNum.setText(cardNum.getText().substring(0, cardNum.getText().length()-1));
                            cashNum.setText("0.00");
//                            cashNum.setText(df.format(new BigDecimal(paidamountNum.getText()).subtract(new BigDecimal(
//                                    cardNum.getText()))));
                        }
                    });
                }
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            
            if (!cardNum.getText().endsWith(".")) {
                String str2 = cashNum.getText();
                if (cashNum.getText().length() == 0) {
                    str2 = "0";
                }
                if (cardNum.getText().length() != 0) {

                    if (Double.parseDouble(amountNum.getText()) > Double.parseDouble(totalNum.getText())) {
                        //                        JOptionPane.showMessageDialog(jf, "您输入的刷卡金额过大");
                        SwingUtilities.invokeLater(new Runnable() { //必须要启动线程修改，否则会报错
                                    @Override
                                    public void run() {
                                    	
                                        cardNum.setText(df.format(new BigDecimal(paidamountNum.getText())
                                                .subtract(new BigDecimal(cashNum.getText()))));
                                        amountNum.setText(df.format(new BigDecimal(cashNum.getText())
                                                .add(new BigDecimal(cardNum.getText()))));
                                        changeNum.setText(df.format(new BigDecimal(paidamountNum.getText())
                                                .subtract(new BigDecimal(cardNum.getText()))));
                                    }
                                });
                    }
                } else {
                    amountNum.setText(df.format(new BigDecimal(str2).add(new BigDecimal(0))));
                    //                changeNum.setText(df.format(new BigDecimal(amountNum.getText()).subtract(new BigDecimal(totalNum.getText()))));
                    changeNum.setText("0.00");
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
    public class cancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            //JOptionPane.showMessageDialog(jf,"确定取消该交易？");
            jf.dispose();
            // 清空table数据

        }
    }

    /**
     *收款按钮监听器
     */
    public class enterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!cardNum.getText().endsWith(".") && !cashNum.getText().endsWith(".")) {
                
                sureButton.setEnabled(false);
                //收银调用方法
                cashInCheckMemberBefore();
                //清空双面收银机界面
                DoubleScreenView dsv=new DoubleScreenView();
                dsv.clearTable();
                //设置默认地址
                setDegaultAdress();
            } else {
                JOptionPane.showMessageDialog(jf, "请输入正确的格式");
            }
        }

        /**        
              
         * 设置默认地址  
         * @param   name    
         * @return String   
         * @Exception 异常对象       
        */
        private void setDegaultAdress() {
            if(O2OMainMenu.internetPass){
                try {
                    ConnectOnlineMethod connect = new ConnectOnlineMethod();
                    List<JSONObject> list = new ArrayList();
                    MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
                    queryParam.add("store_id", PropertiesUtil.getStoreId());
                    String url = PropertiesUtil.getIp() + 
                            "/api/shop/store!getaddress.do";
                    String outPut = connect.connectOnline(queryParam, 
                            url);
                    JSONArray array = connect.jsonConvertion(outPut);
                    list = connect.getJsonObjects(array);
                    String s = ((JSONObject)list.get(0)).get("store_address").toString();
                    
                    O2OMainMenu.addressLabel.setVisible(true);
                    O2OMainMenu.addressLabel.setText(PosAndCashNoButton.international.getInternational("harvestAddress") + s);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }else{
                
                O2OMainMenu.addressLabel.setVisible(true);
                O2OMainMenu.addressLabel.setText(PosAndCashNoButton.international.getInternational("harvestAddress") + PropertiesUtil.getStoreName().toString());
            }
        }
    }
    
    public class cashNumKeyListener2 implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            

        }

        @Override
        public void keyPressed(KeyEvent e) {
            
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                jf.dispose();
            }
            if (e.getKeyCode() == KeyEvent.VK_F1) {
                if (!cardNum.getText().endsWith(".") && !cashNum.getText().endsWith(".")) {
                    cashInCheckMemberBefore();
                    DoubleScreenView dsv=new DoubleScreenView();
                    dsv.clearTable();
                    try{
                      ConnectOnlineMethod connect = new ConnectOnlineMethod();
                      List<JSONObject> list = new ArrayList();
                      MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
                      queryParam.add("store_id", PropertiesUtil.getStoreId());
                      String url = PropertiesUtil.getIp() + 
                        "/api/shop/store!getaddress.do";
                      String outPut = connect.connectOnline(queryParam,url);
                      JSONArray array = connect.jsonConvertion(outPut);
                      list = connect.getJsonObjects(array);
                      String s = ((JSONObject)list.get(0)).get("store_address").toString();
                      
                      O2OMainMenu.addressLabel.setVisible(true);
                      O2OMainMenu.addressLabel.setText(PosAndCashNoButton.international.getInternational("harvestAddress") + s);
                    } catch (IOException e1) {
                      e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(jf, "请输入正确的格式");
                    //                    jf.dispose();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

    }

    public class cardNumKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            

        }

        @Override
        public void keyPressed(KeyEvent e) {
            
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                jf.dispose();
            }
            if (e.getKeyCode() == KeyEvent.VK_F1) {
                if (!cardNum.getText().endsWith(".") && !cashNum.getText().endsWith(".")) {
                    cashInCheckMemberBefore();
                } else {
                    JOptionPane.showMessageDialog(jf, "请输入正确的格式");
                    //                    jf.dispose();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            

        }
    }
    public class allowanceNumKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}	
    	
    }
    public class couponNumKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
    	
    }
    
    public class paidamountNumKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}	
    	
    }
    


    /**
     * 客显调用， O2OMainMenu中有同样的方法
     * @param dataStr  要显示的数字字符串
     * @param stateStr 亮灯状态数字字符串1:单价;   2:合计;
     *                 3:收款;   4:找零
     */
    public void sendToDisplay(String dataStr, String stateStr) {
        int cat = PropertiesUtil.getDisplayCat();
        if (cat == 1) {
            //普通客显调用函数
            String port = "COM" + PropertiesUtil.getnPort();
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
        } else {
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
      
     * 收银界面功能      
     * @param   name    
     * @return String   
     * @Exception 异常对象       
    */
    public void cashIn() {
        String cashNums;
        String cardNums;
        if (cashNum.getText().length() == 0) {
            cashNums = "0";
        } else {
            cashNums = cashNum.getText();
        }
        if (cardNum.getText().length() == 0) {
            cardNums = "0";
        } else {
            cardNums = cardNum.getText();
        }

        if (Double.parseDouble(changeNum.getText()) > 0 && Double.parseDouble(cardNums) > 0) {
            if (Double.parseDouble(cashNums) > Double.parseDouble(paidamountNum.getText())) {
                JOptionPane.showMessageDialog(jf, "您的现金金额足够，无需刷卡");
                cardNum.setText("0.00");
//            } else {
//                JOptionPane.showMessageDialog(jf, "您输入的刷卡金额过大");
//                BigDecimal cardDecimal = new BigDecimal(Double.parseDouble(totalNum.getText())
//                        - Double.parseDouble(cashNums));
//                cardNum.setText("" + df.format(cardDecimal));
            }
        } else {
            if (O2OMainMenu.rejectable) {
                JOptionPane.showMessageDialog(jf, "该订单已支付");
                jf.dispose();
            } else {
                //       if (false) {
//           if (O2OMainMenu.table.getRowCount() != 0) {
//               if (O2OMainMenu.memberNumber.getText().length() > 0) {
//                   String member = O2OMainMenu.memberNumber.getText();
//                   String sql = "select * from es_member where card_id like '" + member + "'";
                   if (!O2OMainMenu.internetPass) { //判断网络连接状态
//                       ResultSet rse = new SetSql().setSql(sql);
//                       try {
//                           mm.dataMember = new O2OMainMenu().getDataArrayMember(rse);
//                       } catch (SQLException e1) {
//                           e1.printStackTrace();
//                       }
                       // mm.table.getRowCount() != 0
//                       if (mm.dataMember.length > 0) {
                           if (cashNums.length() > 0//cardNum
                                   && (Double.parseDouble(cashNums) + Double.parseDouble(cardNums)) >= Double
                                           .parseDouble(totalNum.getText())) {
                               // 判断是否需要刷卡
                               if (Double.parseDouble(cardNums) > 0) {
                                   //                                  1、      调用农行之前的POS
                                   // 调用POS刷卡
                                   // refNo参考号 和 cerNo凭证号 cashAmount
                                   // cardAmount
                                   //                                          new SaleDSerialPort().write2Port(
                                   //                                                  cardPort, cardNums, jf,
                                   //                                                  CashNoButton.this);
                                   //                                  2、   调用深圳银联POS

                                 String[] resultStr = new PosinfSZ().doConsumption(getGMCString(cardNums));
//                                 if ("00".equals(resultStr[0])) {
                                 if(true){
                                       this.cardAmount = Double.parseDouble(paidamountNum.getText()+"") ;
                                       this.refNo = "00";
                                       this.cerNo = "00";
//                                       if ("保税".equals(O2OMainMenu.table.getValueAt(0, 4))||"直邮".equals(O2OMainMenu.table.getValueAt(0, 4))) {
//                                           doPayForTax(jf, PosAndCashNoButton.this);
//                                       } else {
                                           doPay(jf, PosAndCashNoButton.this);
//                                       }
                                   } /*else {
                                       JOptionPane.showMessageDialog(jf, "刷卡失败，请重试！");
                                       jf.dispose();
                                       return;
                                   }*/
                               } else {
//                                   if ("保税".equals(O2OMainMenu.table.getValueAt(0, 4))||"直邮".equals(O2OMainMenu.table.getValueAt(0, 4))) {
//                                       doPayForTax(jf, PosAndCashNoButton.this);
//                                   } else {
                                       doPay(jf, PosAndCashNoButton.this);
//                                  }
                               }
                                    //
                           } else {
                              JOptionPane.showMessageDialog(jf, "金额不足");
                              return;
                           }
//                            } else {
//                                JOptionPane.showMessageDialog(jf, "会员号无效");
//                                jf.dispose();
//                                return;
//                            }
                        } else { //这是在联网状态下
                            
//                            String phone = O2OMainMenu.operator.getText();
//                            MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
//                            queryParam.add("phone", phone);
//                            String url = PropertiesUtil.getIp() + "/api/shop/member!getCard.do";
//                            ConnectOnlineMethod connection = new ConnectOnlineMethod();
//                            try {
//                                String outPut = connection.connectOnline(queryParam, url);
//                                if (!outPut.equals("")) {
//                                    JSONArray array = connection.jsonConvertion(outPut);
//                                    JSONObject jsonObject = connection.getJsonObject(array);
//                                    mm.dataMember = new O2OMainMenu().getDataArrayMember(jsonObject);
//                                }
//
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            // mm.table.getRowCount() != 0
//                            if (mm.dataMember.length > 0) {
                                if (cashNums.length() > 0//cardNum
                                        && (Double.parseDouble(cashNums) + Double.parseDouble(cardNums)) >= Double
                                                .parseDouble(paidamountNum.getText())) {
                                    // 判断是否需要刷卡
                                    if (Double.parseDouble(cardNums) > 0) {
                                        //                          1、      调用农行之前的POS
                                        // 调用POS刷卡
                                        // refNo参考号 和 cerNo凭证号 cashAmount
                                        // cardAmount
                                        //                                  new SaleDSerialPort().write2Port(
                                        //                                          cardPort, cardNums, jf,
                                        //                                          CashNoButton.this);
                                        //                          2、   调用深圳银联POS

                                        String[] resultStr = new PosinfSZ().doConsumption(getGMCString(cardNums));
//                                        if ("00".equals(resultStr[0])) {
                                        if(true){
                                            System.out.println("this is pos return data---------begin");
                                            System.out.println(resultStr);
                                            System.out.println("this is pod return data---------end");
//                                            this.cardAmount = Double.parseDouble(resultStr[4]) / 100;
//                                            this.refNo = resultStr[11];
//                                            this.cerNo = resultStr[3];
                                            this.cardAmount = Double.parseDouble(paidamountNum.getText()+"");
                                            this.refNo = "00";
                                            this.cerNo = "00";
//                                            if ("保税".equals(O2OMainMenu.table.getValueAt(0, 4))||"直邮".equals(O2OMainMenu.table.getValueAt(0, 4))) {
//                                                doPayForTax(jf, PosAndCashNoButton.this);
//                                            } else {
                                                doPay(jf, PosAndCashNoButton.this);
//                                            }
                                        } /*else {
                                            JOptionPane.showMessageDialog(jf, "刷卡失败，请重试！");
                                            jf.dispose();
                                            return;
                                        }*/
                                    } else {
//                                        if ("保税".equals(O2OMainMenu.table.getValueAt(0, 4))||"直邮".equals(O2OMainMenu.table.getValueAt(0, 4))) {
//                                            doPayForTax(jf, PosAndCashNoButton.this);
//                                        } else {
                                            doPay(jf, PosAndCashNoButton.this);
//                                        }
                                    }
                                } else {
                                	sureButton.setEnabled(true);
                                    JOptionPane.showMessageDialog(jf, "金额不足");
                                    return;
                                }
//                            } else {
//                                JOptionPane.showMessageDialog(jf, "会员号无效");
//                                jf.dispose();
//                                return;
//                            }
                        }
//                    } else {
//                        JOptionPane.showMessageDialog(jf, "请输入会员号");
//                        jf.dispose();
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(jf, "无商品记录");
//                    jf.dispose();
//                }
               //清空会员地址
               O2OMainMenu.addrId.setText("0");
            }
        }
        O2OMainMenu.documentNumber.setText("");
    }

    /**        
            
     * 完税商品订单结算
     * @param   name    
     * @return String   
     * @Exception 异常对象       
    */
    @SuppressWarnings("static-access")
    public void doPay(JFrame jf, PosAndCashNoButton cashnb) {

        int rows = O2OMainMenu.table.getRowCount();
        String num = null;
        int product_id = 0;
        int goods_id = 0;
        String sn2 = null;
        String name = null;
        int tax_system = 100;
        String unit = null;
        String spec_value = null;
        Double price = (double) 0;
        String count = null;
        String cardNumber = O2OMainMenu.memberNumber.getText();
        long create_time = System.currentTimeMillis() / 1000;
        
        
        
        // long order_id = System.currentTimeMillis();
        //主订单号
        long order_id = 0;
        //完税订单编号
        long order_id_notax = 0;
        //保税订单编号
        long order_id_yestax = 0;
        //只有订单编号
        long order_id_posttax = 0;
        long order_ids = 0;
        Double goods_amount = 0.00;
        Double goods_amounts = 0.00;
        //商品价格总和
        for(int i = 0 ; i< O2OMainMenu.table.getRowCount(); i++){
            String tax1 =df.format(new BigDecimal(Double.parseDouble(O2OMainMenu.table.getValueAt(i, 7)+"")*Double.parseDouble(O2OMainMenu.table.getValueAt(i, 8)+"")));
            goods_amounts = Double.parseDouble(tax1);
            goods_amount = goods_amount + goods_amounts;
        }
//        Double goods_amount = Double.parseDouble(O2OMainMenu.totalMoneyLabelChange.getText());
        int goods_num = Integer.parseInt(O2OMainMenu.totalNumberLabelChange.getText());

        String user_name = mm.operator.getText();
        Double order_amount = Double.parseDouble(O2OMainMenu.totalMoneyLabelChange.getText());
        String currency = "CNY";
        String store_id = PropertiesUtil.getStoreId();
        String addr_id = O2OMainMenu.addrId.getText();
        if (addr_id.length() < 1) {
            addr_id = "0";
        }
        int status = OrderStatus.ORDER_COMPLETE;
        long timeNow = System.currentTimeMillis();
        String dateNow = new timeToDates().getTimeToDates(timeNow);
        
        //主订单编号
        String order_sn = new order_sn().soOrder();
        //完税订单编号
        String order_sn_notax = null;
        //保税订单编号
        String order_sn_yestax = null;
        //直邮订单编号
        String order_sn_posttax = null; 
        String sn3 = null;
        String sn4 = O2OMainMenu.goodsCode.getText();
        String thumbnail = null;
        double paymoney= Double.parseDouble(totalNum.getText());
        double allowance = 0;
        double coupon = 0;
        if(allowanceNum.getText().equals("")){
        	 allowance = 0;
        	
        }else{
        	 allowance = Double.parseDouble(allowanceNum.getText());
        	
        };
        if(couponNum.getText().equals("")){
        	 coupon = 0;
        }else{
        	 coupon = Double.parseDouble(couponNum.getText());
        };
        double discountAmount = Double.parseDouble(totalNum.getText())-Double.parseDouble(paidamountNum.getText());
        String discount1 = String.format("%.1f", discountAmount);
        String discount2 = String.format("%.2f", Double.parseDouble(discount1));
        discountAmount = Double.parseDouble(discount2);        		
        //给商品添加税金后将税金加到订单的taxes中去
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
        if (!O2OMainMenu.goodsCode.getText().startsWith("S")) {
            //if(snSts.equals("")){
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
            String payment_name = paymentName(cardAmount, cashAmount);
            String payment_type = paymentType(cardAmount, cashAmount);
            int paymentId = paymentId(cardAmount,cashAmount);
            //      实时持久化订单数据在线下数据库
            
//            StringBuffer sql = new StringBuffer("insert into es_order(" +
//                    "order_id,address_id,sn,cardNumber,user_name,status,create_time," +
//                    "goods_amount,order_amount,goods_num,store_id,currency,date," +
//                    "classes,balance_status,cardAmount,cashAmount,refNo,cerNo," +
//                    "payment_name,payment_id,payment_type,taxes,paymoney) values(");
//            sql.append(order_id);
            boolean a = new SetSql().setSqlNotReturn("insert into es_order(" +
                    "address_id,sn,cardNumber,user_name,status,create_time," +
                    "goods_amount,order_amount,goods_num,store_id,currency,date," +
                    "classes,balance_status,cardAmount,cashAmount,refNo,cerNo," +
                    "payment_name,payment_id,payment_type,taxes,paymoney,allowance,coupon,discountAmount) values("
                     + addr_id+ ",'"+ order_sn+ "','"+ cardNumber+ "','"
                     + user_name+ "',"+ status+ ","+ create_time+ ","+ goods_amount
                     + ","+ order_amount+ ","+ goods_num+ ",'"+ store_id+ "','"+ currency
                     + "','"+ dateNow+ "',"+ classes+ ",0,'"+ cardAmount+ "','"+ cashAmount
                     + "','"+ refNo+ "','"+ cerNo+ "','"+ payment_name+"',"+paymentId
                     +",'"+payment_type+"',"+taxess+","+paymoney+",'"+allowance+"','"+coupon+"','"+discountAmount+"')");
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
                             + "','"+ refNo+ "','"+ cerNo+ "','"+ payment_name+"',"+paymentId
                             +",'"+payment_type+"',"+taxess+","+paymoney+",'"+allowance+"','"+coupon+"','"+discountAmount+"')");
            try {           
            String sql1 = "select count(sn) from es_order where sn LIKE 'SO20%' and date = '"+dateNow+"' and payment_id = '6' or payment_id = '7'";
            ResultSet rss1 = new SetSql().setSql(sql1);
            rss1.next();
            count = rss1.getInt(1)+"";
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
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
                    map.add("paymentId", paymentId+"");
                    map.add("payment_type", payment_type+"");
                    map.add("taxesPrice", taxes+"");
                    map.add("paymoney", paymoney+"");
                    map.add("store_id",store_id+"");
                    map.add("allowance",allowance+"");
                    map.add("coupon",coupon+"");
                    map.add("discountAmount", discountAmount+"");
//                    map.add("parent_id", parent_id+"");
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
//                    boolean isCompleted = new OrderAutoCommit().httpUrlConnection(order, orderItem);
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
                    double cash = 0.00;
                    double card = 0.00;
                    if(cashAmount>0){
                        cash = goods_amount_notax;
                    }
                    if(cardAmount>0){
                        card = goods_amount_notax;
                    }
                    boolean a1 = new SetSql().setSqlNotReturn("insert into es_order(" +
                            "address_id,sn,cardNumber,user_name,status,create_time," +
                            "goods_amount,order_amount,goods_num,store_id,currency,date," +
                            "classes,balance_status,cardAmount,cashAmount,refNo,cerNo," +
                            "payment_name,payment_id,payment_type,taxes,paymoney,allowance,coupon,discountAmount,parent_id) values("
                             + addr_id+ ",'"+ order_sn_notax+ "','"+ cardNumber+ "','"
                             + user_name+ "',"+ status+ ","+ create_time+ ","+ goods_amount_notax
                             + ","+ goods_amount_notax+ ","+ num_notax+ ",'"+ store_id+ "','"+ currency
                             + "','"+ dateNow+ "',"+ classes+ ",0,'"+ card+ "','"+ cash
                             + "','"+ refNo+ "','"+ cerNo+ "','"+ payment_name+"',"+paymentId
                             +",'"+payment_type+"',"+notaxes+","+goods_amount_notax+",'"+allowance+"','"+coupon+"','"+discountAmount+"',"+parent_id+")");
                //  实时备份订单数据
                    new DoBackup()
                            .realTimeBackup("insert into es_order(" +
                                    "address_id,sn,cardNumber,user_name,status,create_time," +
                                    "goods_amount,order_amount,goods_num,store_id,currency,date," +
                                    "classes,balance_status,cardAmount,cashAmount,refNo,cerNo," +
                                    "payment_name,payment_id,payment_type,taxes,paymoney,allowance,coupon,discountAmount,parent_id) values("
                                     + addr_id+ ",'"+ order_sn_notax+ "','"+ cardNumber+ "','"
                                     + user_name+ "',"+ status+ ","+ create_time+ ","+ goods_amount_notax
                                     + ","+ goods_amount_notax+ ","+ num_notax+ ",'"+ store_id+ "','"+ currency
                                     + "','"+ dateNow+ "',"+ classes+ ",0,'"+ card+ "','"+ cash
                                     + "','"+ refNo+ "','"+ cerNo+ "','"+ payment_name+"',"+paymentId
                                     +",'"+payment_type+"',"+notaxes+","+goods_amount_notax+",'"+allowance+"','"+coupon+"','"+discountAmount+"',"+parent_id+")");
                    if(O2OMainMenu.internetPass){
                        MultivaluedMap<String, String> map = new MultivaluedMapImpl();
                        map.add("order_sn",order_sn);
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
                        if(cardAmount>0){
                            map.add("cardAmount", goods_amount_notax+"");
                        }else{
                            map.add("cardAmount", 0+"");
                        }
                        if(cashAmount>0){
                            map.add("cashAmount", goods_amount_notax+"");
                        }else{
                            map.add("cashAmount", 0+"");
                        }   
                        map.add("refNo", refNo+"");
                        map.add("cerNo", cerNo+"");
                        map.add("payment_name", payment_name);
                        map.add("paymentId", paymentId+"");
                        map.add("payment_type", payment_type+"");
                        map.add("taxesPrice", notaxes+"");
                        map.add("paymoney", goods_amount_notax+"");
                        map.add("store_id",store_id+"");
                        map.add("allowance",allowance+"");
                        map.add("coupon",coupon+"");
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
//                                boolean backUpItems = new SetSql()
//                                .setSqlNotReturn("update es_order_items set export_status = 1 where sn = '" + sn3 + "'");
//                                if (backUpItems) {
//                                    new DoBackup().realTimeBackup("update es_order_items set export_status = 1 where sn = '" + sn3
//                                            + "'");
//                                }
                            }else{
                                JOptionPane.showMessageDialog(jf, "订单上传线上失败");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    cash = 0.00;
                    card = 0.00;
                }
                if(tax_yes){
                    order_sn_yestax = new order_sn().soGets();
                    double cash = 0.00;
                    double card = 0.00;
                    if(cashAmount>0){
                        cash = goods_amount_yestax;
                    }
                    if(cardAmount>0){
                        card = goods_amount_yestax;
                    }
                    boolean a11 = new SetSql().setSqlNotReturn("insert into es_order(" +
                            "address_id,sn,cardNumber,user_name,status,create_time," +
                            "goods_amount,order_amount,goods_num,store_id,currency,date," +
                            "classes,balance_status,cardAmount,cashAmount,refNo,cerNo," +
                            "payment_name,payment_id,payment_type,taxes,paymoney,allowance,coupon,discountAmount,parent_id) values("
                             + addr_id+ ",'"+ order_sn_yestax+ "','"+ cardNumber+ "','"
                             + user_name+ "',"+ status+ ","+ create_time+ ","+ goods_amount_yestax
                             + ","+ goods_amount_yestax+ ","+ num_yestax+ ",'"+ store_id+ "','"+ currency
                             + "','"+ dateNow+ "',"+ classes+ ",0,'"+ card+ "','"+ cash
                             + "','"+ refNo+ "','"+ cerNo+ "','"+ payment_name+"',"+paymentId
                             +",'"+payment_type+"',"+yestaxes+","+goods_amount_yestax+",'"+allowance+"','"+coupon+"','"+discountAmount+"',"+parent_id+")");
                //  实时备份订单数据
                    new DoBackup()
                            .realTimeBackup("insert into es_order(" +
                                    "address_id,sn,cardNumber,user_name,status,create_time," +
                                    "goods_amount,order_amount,goods_num,store_id,currency,date," +
                                    "classes,balance_status,cardAmount,cashAmount,refNo,cerNo," +
                                    "payment_name,payment_id,payment_type,taxes,paymoney,allowance,coupon,discountAmount,parent_id) values("
                                     + addr_id+ ",'"+ order_sn_yestax+ "','"+ cardNumber+ "','"
                                     + user_name+ "',"+ status+ ","+ create_time+ ","+ goods_amount_yestax
                                     + ","+ goods_amount_yestax+ ","+ num_yestax+ ",'"+ store_id+ "','"+ currency
                                     + "','"+ dateNow+ "',"+ classes+ ",0,'"+ card+ "','"+ cash
                                     + "','"+ refNo+ "','"+ cerNo+ "','"+ payment_name+"',"+paymentId
                                     +",'"+payment_type+"',"+yestaxes+","+goods_amount_yestax+",'"+allowance+"','"+coupon+"','"+discountAmount+"',"+parent_id+")");
                    if(O2OMainMenu.internetPass){
                        MultivaluedMap<String, String> map = new MultivaluedMapImpl();
                        map.add("order_sn",order_sn);
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
                        if(cardAmount>0){
                            map.add("cardAmount", goods_amount_yestax+"");
                        }else{
                            map.add("cardAmount", 0+"");
                        }
                        if(cashAmount>0){
                            map.add("cashAmount", goods_amount_yestax+"");
                        }else{
                            map.add("cashAmount", 0+"");
                        }
                        map.add("refNo", refNo+"");
                        map.add("cerNo", cerNo+"");
                        map.add("payment_name", payment_name);
                        map.add("paymentId", paymentId+"");
                        map.add("payment_type", payment_type+"");
                        map.add("taxesPrice", yestaxes+"");
                        map.add("paymoney", goods_amount_yestax+"");
                        map.add("store_id",store_id+"");
                        map.add("allowance",allowance+"");
                        map.add("coupon",coupon+"");
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
//                                boolean backUpItems = new SetSql()
//                                .setSqlNotReturn("update es_order_items set export_status = 1 where sn = '" + sn3 + "'");
//                                if (backUpItems) {
//                                    new DoBackup().realTimeBackup("update es_order_items set export_status = 1 where sn = '" + sn3
//                                            + "'");
//                                }
                            }else{
                                JOptionPane.showMessageDialog(jf, "订单上传线上失败");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    cash = 0.00;
                    card = 0.00;
                }
                if(tax_post){
                    order_sn_posttax = new order_sn().soGetss();
                    double cash = 0.00;
                    double card = 0.00;
                    if(cashAmount>0){
                        cash = goods_amount_posttax;
                    }
                    if(cardAmount>0){
                        card = goods_amount_posttax;
                    }
                    boolean a111 = new SetSql().setSqlNotReturn("insert into es_order(" +
                            "address_id,sn,cardNumber,user_name,status,create_time," +
                            "goods_amount,order_amount,goods_num,store_id,currency,date," +
                            "classes,balance_status,cardAmount,cashAmount,refNo,cerNo," +
                            "payment_name,payment_id,payment_type,taxes,paymoney,allowance,coupon,discountAmount,parent_id) values("
                             + addr_id+ ",'"+ order_sn_posttax+ "','"+ cardNumber+ "','"
                             + user_name+ "',"+ status+ ","+ create_time+ ","+ goods_amount_posttax
                             + ","+ goods_amount_posttax+ ","+ num_posttax+ ",'"+ store_id+ "','"+ currency
                             + "','"+ dateNow+ "',"+ classes+ ",0,'"+ card+ "','"+ cash
                             + "','"+ refNo+ "','"+ cerNo+ "','"+ payment_name+"',"+paymentId
                             +",'"+payment_type+"',"+posttaxes+","+goods_amount_posttax+",'"+allowance+"','"+coupon+"','"+discountAmount+"',"+parent_id+")");
                //  实时备份订单数据
                    new DoBackup()
                            .realTimeBackup("insert into es_order(" +
                                    "address_id,sn,cardNumber,user_name,status,create_time," +
                                    "goods_amount,order_amount,goods_num,store_id,currency,date," +
                                    "classes,balance_status,cardAmount,cashAmount,refNo,cerNo," +
                                    "payment_name,payment_id,payment_type,taxes,paymoney,allowance,coupon,discountAmount,parent_id) values("
                                     + addr_id+ ",'"+ order_sn_posttax+ "','"+ cardNumber+ "','"
                                     + user_name+ "',"+ status+ ","+ create_time+ ","+ goods_amount_posttax
                                     + ","+ goods_amount_posttax+ ","+ num_posttax+ ",'"+ store_id+ "','"+ currency
                                     + "','"+ dateNow+ "',"+ classes+ ",0,'"+ card+ "','"+ cash
                                     + "','"+ refNo+ "','"+ cerNo+ "','"+ payment_name+"',"+paymentId
                                     +",'"+payment_type+"',"+posttaxes+","+goods_amount_posttax+",'"+allowance+"','"+coupon+"','"+discountAmount+"',"+parent_id+")");
                    if(O2OMainMenu.internetPass){
                        MultivaluedMap<String, String> map = new MultivaluedMapImpl();
                        map.add("order_sn",order_sn);
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
                        if(cardAmount>0){
                            map.add("cardAmount", goods_amount_posttax+"");
                        }else{
                            map.add("cardAmount", 0+"");
                        }
                        if(cashAmount>0){
                            map.add("cashAmount", goods_amount_posttax+"");
                        }else{
                            map.add("cashAmount", 0+"");
                        }
                        map.add("refNo", refNo+"");
                        map.add("cerNo", cerNo+"");
                        map.add("payment_name", payment_name);
                        map.add("paymentId", paymentId+"");
                        map.add("payment_type", payment_type+"");
                        map.add("taxesPrice", posttaxes+"");
                        map.add("paymoney", goods_amount_posttax+"");
                        map.add("store_id",store_id+"");
                        map.add("allowance",allowance+"");
                        map.add("coupon",coupon+"");
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
//                                boolean backUpItems = new SetSql()
//                                .setSqlNotReturn("update es_order_items set export_status = 1 where sn = '" + sn3 + "'");
//                                if (backUpItems) {
//                                    new DoBackup().realTimeBackup("update es_order_items set export_status = 1 where sn = '" + sn3
//                                            + "'");
//                                }
                            }else{
                                JOptionPane.showMessageDialog(jf, "订单上传线上失败");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    cash = 0.00;
                    card = 0.00;
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
                            thumbnail = snResultSet.getString("thumbnail");
                            spec_value = O2OMainMenu.table.getValueAt(i, 3).toString();
                            if(O2OMainMenu.table.getValueAt(i, 4).toString().equals("完税")){
                                tax_system = 101;
                            }else if(O2OMainMenu.table.getValueAt(i, 4).toString().equals("保税")){
                                tax_system = 100;
                            }else if(O2OMainMenu.table.getValueAt(i, 4).toString().equals("直邮")){
                                tax_system = 103;
                            }else if(O2OMainMenu.table.getValueAt(i, 4).toString().equals("全部")){
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
                    //根据不同的税制生成订单明细 101为完税订单 100为保税订单 103为直邮订单102为全部
                    if(tax_system==101 || tax_system == 102){
//                        sn3 = new order_sn().soGets();
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
//                        sn3 = new order_sn().soGets();
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
//                        sn3 = new order_sn().soGets();
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
                        if(O2OMainMenu.internetPass){
                            JOptionPane.showMessageDialog(jf, "订单明细上传线上失败");
                        }
                    }
                }
            }
//            sendToDisplay(changeNum.getText(), "4");
            PrientGoods.cashMoney = df.format(new BigDecimal(cashnb.cashNum.getText()));
            PrientGoods.totalMoneys = cashnb.amountNum.getText();
            PrientGoods.changeMoneys = cashnb.changeNum.getText();
            PrientGoods.bankMoney = cashnb.cardNum.getText();
            if("".equals(PrientGoods.bankMoney)){
                PrientGoods.bankMoney = "0.00";
            }
            PrientGoods.row = O2OMainMenu.table.getRowCount();
            PrientGoods.totalNumber = O2OMainMenu.totalNumberLabelChange.getText();
            PrientGoods.totalMoney = O2OMainMenu.totalMoneyLabelChange.getText();
            PrientGoods.table = O2OMainMenu.table;
            PrientGoods.sn = order_sn;
            GenerateBarCode.sn= order_sn;
            PrientGoods.tax = taxess;
            PrientGoods.paymoney=paymoney;
            PrientGoods.count = count;
            PrientGoods.discountAmount = discountAmount;
            PrientGoods.amount = Double.parseDouble(cashnb.paidamountNum.getText());;
            GenerateBarCode barcode = new GenerateBarCode();
            barcode.GenerateBarCode();
            PrientGoods pg = new PrientGoods();
            pg.start();
            JOptionPane.showMessageDialog(jf, "付款成功，找零：" + cashnb.changeNum.getText());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                
                e1.printStackTrace();
            }

            //这是通过改变订单状态的方式结算线下订单
        } else {
            //如果是订单的话拿到订单的订单状态，还有支付状态   @author lsl
            //start
            //            String orderStatus = (String) O2OMainMenu.table.getValueAt(0, 4);
            //            String pay_status = (String) O2OMainMenu.table.getValueAt(0, 5);
            double cashAmount;
            //          if(cardNum.getText().length()==0){cardAmount=0.00;}
            //          else{cardAmount = Double.parseDouble(cardNum.getText());}
            if (cashNum.getText().length() == 0) {
                cashAmount = 0.00;
            } else {
                cashAmount = Double.parseDouble(cashnb.totalNum.getText()) - cardAmount;
            }
            MultivaluedMap<String, String> querymap = new MultivaluedMapImpl();
            String sql = "UPDATE es_order SET `status` = 102 , pay_status = 102,payment_name = '"
                    + paymentName(cardAmount, cashAmount) + "' WHERE sn = '" + sn4 + "'";
            querymap.add("sql", sql);
            String url = PropertiesUtil.getIp() + "/api/shop/goods!updateOrderOffline.do";
            ConnectOnlineMethod connect = new ConnectOnlineMethod();
            try {
                connect.connectOnline(querymap, url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PrientGoods.cashMoney = df.format(new BigDecimal(cashnb.cashNum.getText()));
            PrientGoods.totalMoneys = cashnb.amountNum.getText();
            PrientGoods.changeMoneys = cashnb.changeNum.getText();
            PrientGoods.bankMoney = cashnb.cardNum.getText();
            if("".equals(PrientGoods.bankMoney)){
                PrientGoods.bankMoney = "0.00";
            }
            PrientGoods.row = O2OMainMenu.table.getRowCount();
            PrientGoods.totalNumber = O2OMainMenu.totalNumberLabelChange.getText();
            PrientGoods.totalMoney = O2OMainMenu.totalMoneyLabelChange.getText();
            PrientGoods.table = O2OMainMenu.table;
            //            PrientGoods.sn = sn3;
            PrientGoods.sn = sn4;
            GenerateBarCode.sn= sn4;
            PrientGoods.tax = taxess;
            PrientGoods.paymoney=paymoney;
            PrientGoods.discountAmount = discountAmount;
            PrientGoods.amount = Double.parseDouble(cashnb.paidamountNum.getText());;
            GenerateBarCode barcode = new GenerateBarCode();
            barcode.GenerateBarCode();
            PrientGoods pg = new PrientGoods();
            pg.start();
            JOptionPane.showMessageDialog(jf, "付款成功，找零：" + cashnb.changeNum.getText());
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
    }

    @SuppressWarnings("static-access")
    public void doPayForTax(JFrame jf, PosAndCashNoButton cashnb) {

        int rows = O2OMainMenu.table.getRowCount();
        String num = null;
        int product_id = 0;
        int goods_id = 0;
        String sn2 = null;
        String name = null;
        int tax_system = 100;
        String unit = null;
        String spec_value = null;
        Double price = (double) 0;

        String cardNumber = O2OMainMenu.memberNumber.getText();
        long create_time = System.currentTimeMillis() / 1000;
        // long order_id = System.currentTimeMillis();
        long order_id = Sequence.nextId();
        Double goods_amount = 0.00;
        Double goods_amounts = 0.00;
        for(int i = 0 ; i< O2OMainMenu.table.getRowCount(); i++){
            String tax1 =df.format(new BigDecimal(Double.parseDouble(O2OMainMenu.table.getValueAt(i, 7)+"")*Double.parseDouble(O2OMainMenu.table.getValueAt(i, 8)+"")));
            goods_amounts = Double.parseDouble(tax1);
            goods_amount = goods_amount + goods_amounts;
        }
        
//        Double goods_amount = Double.parseDouble(O2OMainMenu.totalMoneyLabelChange.getText());

        int goods_num = Integer.parseInt(O2OMainMenu.totalNumberLabelChange.getText());

        String user_name = mm.operator.getText();
        Double order_amount = Double.parseDouble(O2OMainMenu.totalMoneyLabelChange.getText());
        String currency = "CNY";
        String store_id = PropertiesUtil.getStoreId();
        String addr_id = O2OMainMenu.addrId.getText();
        if (addr_id.length() < 1) {
            addr_id = null;
        }
        int status = OrderStatus.ORDER_PAY_CONFIRM;
        long timeNow = System.currentTimeMillis();
        String dateNow = new timeToDates().getTimeToDates(timeNow);
        //订单编号
        String sn3 = new order_sn().soGets();
        String thumbnail = null;
        double paymoney=Double.parseDouble(totalNum.getText());
        double allowance = 0;
        double coupon = 0;
        if(allowanceNum.getText().equals("")){
        	 allowance = 0;
        	
        }else{
        	 allowance = Double.parseDouble(allowanceNum.getText());
        	
        };
        if(couponNum.getText().equals("")){
        	 coupon = 0;
        }else{
        	 coupon = Double.parseDouble(couponNum.getText());
        };
        double discountAmount = Double.parseDouble(totalNum.getText())-Double.parseDouble(paidamountNum.getText());
        String discount1 = String.format("%.1f", discountAmount);
        String discount2 = String.format("%.2f", Double.parseDouble(discount1));
        discountAmount = Double.parseDouble(discount2);
        String count = null;
        //给商品加了税金的字段
        Double taxes = 0.00;
        Double taxess = 0.00;
        Double tax = 0.00;
        for(int i = 0 ; i< O2OMainMenu.table.getRowCount(); i++){
            taxes = Double.parseDouble(O2OMainMenu.table.getValueAt(i, 6)+"");
            String tax1 =df.format(new BigDecimal(Double.parseDouble(O2OMainMenu.table.getValueAt(i, 6)+"")*Double.parseDouble(O2OMainMenu.table.getValueAt(i, 8)+"")));
            tax = Double.parseDouble(tax1);
            taxess = tax + taxess;
        }
        try {           
            String sql1 = "select count(*) from es_order as sn where sn LIKE 'SO20%' and date = '"+dateNow+"' and payment_id = '6'";
            ResultSet rss1 = new SetSql().setSql(sql1);
            rss1.next();
            count = rss1.getInt(1)+"";
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        
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
        String payment_name = paymentName(cardAmount, cashAmount);
        String payment_type = paymentType(cardAmount, cashAmount);
        int paymentId = paymentId(cardAmount,cashAmount);
        //      实时持久化订单数据在线下数据库
        boolean a = new SetSql().setSqlNotReturn("insert into es_order(order_id,address_id,sn," +
                "cardNumber,user_name,status,create_time,goods_amount,order_amount,goods_num," +
                "store_id,currency,date,classes,balance_status,cardAmount,cashAmount," +
                "refNo,cerNo,payment_name,payment_id,payment_type,taxes,paymoney,allowance,coupon) values("
                 + order_id+ ","+ addr_id+ ",'"+ sn3+ "','"+ cardNumber+ "','"+ user_name
                 + "',"+ status+ ","+ create_time+ ","+ goods_amount+ ","
                 + order_amount+ ","+ goods_num+ ",'"+ store_id + "','"+ currency+ "','"
                 + dateNow+ "',"+ classes+ ",0,'"+ cardAmount+ "','"+ cashAmount + "','"
                 + refNo + "','" + cerNo + "','" + payment_name + "',"+paymentId+",'"
                 +payment_type+"',"+taxess+","+paymoney+",'"+allowance+"','"+coupon+"','"+discountAmount+"')");
        if (a) {
          //判断是否联网，联网同步数据到线上，成功的话跟新导出状态为1，不导出数据，失败不更新导出状态，班结后导出数据
            if (O2OMainMenu.internetPass) {
                MultivaluedMap<String, String> map = new MultivaluedMapImpl();
                map.add("order_id",order_id+"");
                map.add("addr_id", addr_id);
                map.add("sn3", sn3);
                map.add("uname",O2OMainMenu.operator.getText().toString());
                map.add("card_id", cardNumber+"");
                map.add("user_name", user_name+"");
                map.add("status", 2+"");
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
                map.add("paymentId", paymentId+"");
                map.add("payment_type", payment_type+"");
                map.add("taxesPrice", taxes+"");
                map.add("paymoney", paymoney+"");
                map.add("store_id" , store_id+"");
                map.add("discountAmount", discountAmount+"");
                
                String url = PropertiesUtil.getIp() + "/api/shop/order!orderByOffline.do";
                ConnectOnlineMethod connect = new ConnectOnlineMethod();
                try {
                    String str  = connect.connectOnline(map, url);
                    if (str.length()==29) {
                        boolean backUpOrder = new SetSql()
                        .setSqlNotReturn("update es_order set export_status = 1 where sn = '" + sn3 + "'");
                        if (backUpOrder) {
                            new DoBackup().realTimeBackup("update es_order set export_status = 1 where sn = '" + sn3 + "'");
                        }
                    }else{
                        JOptionPane.showMessageDialog(jf, "订单上传线上失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (a) {
            //  实时备份订单数据
            new DoBackup()
                    .realTimeBackup("insert into es_order(order_id,address_id,sn," +
                            "cardNumber,user_name,status,create_time,goods_amount,order_amount,goods_num," +
                            "store_id,currency,date,classes,balance_status,cardAmount,cashAmount," +
                            "refNo,cerNo,payment_name,payment_id,payment_type,taxes,paymoney,allowance,coupon) values("
                             + order_id+ ","+ addr_id+ ",'"+ sn3+ "','"+ cardNumber+ "','"+ user_name
                             + "',"+ status+ ","+ create_time+ ","+ goods_amount+ ","
                             + order_amount+ ","+ goods_num+ ",'"+ store_id + "','"+ currency+ "','"
                             + dateNow+ "',"+ classes+ ",0,'"+ cardAmount+ "','"+ cashAmount + "','"
                             + refNo + "','" + cerNo + "','" + payment_name + "',"+paymentId+",'"
                             +payment_type+"',"+taxess+","+paymoney+",'"+allowance+"','"+coupon+"','"+discountAmount+"')");
            for (int i = 0; i < rows; i++) {
                num = String.valueOf(O2OMainMenu.table.getValueAt(i, 8));// num
                sn2 = String.valueOf(O2OMainMenu.table.getValueAt(i, 1));
//                String sql2 = "select * from es_product where bar_code = '" + sn2 + "' and product_id = '"
//                        + O2OMainMenu.table.getValueAt(i, 0) + "'";
                String sql2 = "select p.*,g.thumbnail from es_product p left join es_goods g on p.goods_id = g.goods_id where bar_code = '" + sn2 + "' and product_id = '"
                        + O2OMainMenu.table.getValueAt(i, 0) + "'";
                if (!O2OMainMenu.internetPass) {
                    ResultSet snResultSet = new SetSql().setSql(sql2);
                    try {
                        snResultSet.next();
                        goods_id = snResultSet.getInt("goods_id");
                        product_id = snResultSet.getInt("product_id");
                        name = snResultSet.getString("name");
                        price = Double.parseDouble(O2OMainMenu.table.getValueAt(i, 7) + "");
                        taxes = Double.parseDouble(O2OMainMenu.table.getValueAt(i, 6) + "");
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
                                price = Double.parseDouble(O2OMainMenu.table.getValueAt(i, 7) + "");
                                taxes = Double.parseDouble(O2OMainMenu.table.getValueAt(i, 6) + "");
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
                //          实时持久化订单明细在线下数据库
                boolean b = new SetSql().setSqlNotReturn("insert into es_order_items(order_id,goods_id," +
                        "product_id,num,bar_code,name,price,currency,sn,state,taxes,spec_value,tax_system,unit) values("
                         + order_id+ ","+ goods_id+ ","+ product_id+ ","+ num+ ",'"+ sn2+ "','"
                         + name+ "'," + price + ",'" + currency + "','" + sn3 + "',101,"+taxes+",'"+spec_value+"',"+tax_system+",'"+unit+"')");
                if (b) {
                    if(O2OMainMenu.internetPass){
                        MultivaluedMap<String, String> maps = new MultivaluedMapImpl();
                        maps.add("order_id",order_id+"");
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
                if (b) {
                    //              实时备份订单明细数据
                    new DoBackup()
                            .realTimeBackup("insert into es_order_items(order_id,goods_id," +
                                    "product_id,num,bar_code,name,price,currency,sn,state,taxes,spec_value,tax_system,unit) values("
                                    + order_id+ ","+ goods_id+ ","+ product_id+ ","+ num+ ",'"+ sn2+ "','"
                                    + name+ "'," + price + ",'" + currency + "','" + sn3 + "',101,"+taxes+",'"+spec_value+"',"+tax_system+",'"+unit+"')");
                }
            }
        }
//        sendToDisplay(changeNum.getText(), "4");

        PrientGoods.cashMoney = df.format(new BigDecimal(cashnb.cashNum.getText()));
        PrientGoods.totalMoneys = cashnb.amountNum.getText();
        PrientGoods.changeMoneys = cashnb.changeNum.getText();
        if(null==cashnb.cardNum.getText()){
            cashnb.cardNum.setText("0.00");
        }
        PrientGoods.bankMoney = cashnb.cardNum.getText();
        if("".equals(PrientGoods.bankMoney)){
            PrientGoods.bankMoney = "0.00";
        }
        PrientGoods.row = O2OMainMenu.table.getRowCount();
        PrientGoods.totalNumber = O2OMainMenu.totalNumberLabelChange.getText();
        PrientGoods.totalMoney = O2OMainMenu.totalMoneyLabelChange.getText();
        PrientGoods.table = O2OMainMenu.table;
        PrientGoods.sn = sn3;
        GenerateBarCode.sn= sn3;
        PrientGoods.tax = taxess;
        PrientGoods.paymoney = paymoney;
        PrientGoods.count = count;
        PrientGoods.discountAmount = discountAmount;
        PrientGoods.amount = Double.parseDouble(cashnb.paidamountNum.getText());
        GenerateBarCode barcode = new GenerateBarCode();
        barcode.GenerateBarCode();
        PrientGoods pg = new PrientGoods();
        pg.start();
        JOptionPane.showMessageDialog(jf, "付款成功，找零：" + cashnb.changeNum.getText());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            
            e1.printStackTrace();
        }

        jf.dispose();
        O2OMainMenu.goodsCode.setText("");
        O2OMainMenu.totalNumberLabelChange.setText("");
        O2OMainMenu.totalMoneyLabelChange.setText("");
        O2OMainMenu.addressLabel.setVisible(false);
    }

    /**        
        
     * 收银界面收银按钮执行方法    
     * @param   name    
     * @return String   
     * @Exception 异常对象       
    */
    public void cashInCheckMemberBefore() {
        //联网
//        if (O2OMainMenu.internetPass) {
//            String cardNumber = O2OMainMenu.operator.getText();
//            MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
//            queryParams.add("phone", cardNumber);
//            String cardurl = PropertiesUtil.getIp() + "/api/shop/member!getCard.do";
//            ConnectOnlineMethod connect = new ConnectOnlineMethod();
//            try {
//                String outPut = connect.connectOnline(queryParams, cardurl);
//                if (outPut.length()>29) {
////                    JSONArray jsonArray = connect.jsonConvertion(outPut);
////                    JSONObject jsonObject = connect.getJsonObject(jsonArray);
////                    String memberCard = (String) jsonObject.get("mobile");
//                    
                    cashIn();
//                }else{
//                    JOptionPane.showMessageDialog(jf, "该会员卡无效");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            cashIn();
//        }
    }

    /**
     * 判断支付类型
     */
    public String paymentName(double cardAmount, double cashAmount) {
        String payment_name = "";
        if (cardAmount > 0) {
            if (cashAmount > 0) {
                payment_name = "混合支付";
            } else {
                payment_name = "刷卡支付";
            }
        } else {
            payment_name = "现金支付";
        }
        return payment_name;
    }
    /**
     * 判断支付类型
     */
    public String paymentType(double cardAmount, double cashAmount) {
        String payment_type = "";
        if (cardAmount > 0) {
            if (cashAmount > 0) {
                payment_type = "混合支付";
            } else {
                payment_type = "刷卡支付";
            }
        } else {
            payment_type = "现金支付";
        }
        return payment_type;
    }
    
    /**
     * 判断支付类型
     */
    public int paymentId(double cardAmount,double cashAmount){
        int payment_id = 0;
        if(cardAmount>0){
            if(cashAmount>0){
                payment_id  = OrderStatus.PSOANDCASH_PAYMENT_ID;
            }else{
                payment_id = OrderStatus.POS_PAYMENT_ID;
            }
        }else{
            payment_id = OrderStatus.CASH_PAYMENT_ID;
        }
        return payment_id;
    }

    /**
     * 获取操作员id
     * @return String user_id
     */
    public String getUserId() {
        //      String user_name1 = "U1";
        String user_name1 = O2OMainMenu.operator.getText();
        ResultSet resultset1 = new SetSql().setSql("select userid from es_user where username = '" + user_name1 + "'");
        String user_id = "";
        try {
            resultset1.next();
            user_id = resultset1.getString("userid");
        } catch (SQLException e) {
            user_id = "";
            e.printStackTrace();
        }
        user_id = getSpaceString(user_id, 8);
        return user_id;
    }

    /**U1
     * 获取posNum
     * @return String posNum
     */
    public String getPosNum() {
        String posNum = getSpaceString(PropertiesUtil.getPosNum(), 8);
        return posNum;
    }

    /**
     * 获取指定长度的字符串不足长度右边不空格,str位“”时,返回制定长度的空格
     * @return 
     */
    public String getSpaceString(String str, int length) {
        String space = str;
        if (space.length() < length) {
            int spaceLength = length - space.length();
            for (int i = 0; i < spaceLength; i++) {
                space = space + "\u0020";
            }
        }
        return space;
    }

    /**
     * 获取指定刷卡金额格式
     * @return String posNum
     */
    public String cardNumFormat(String cardAmounts) {
        String cardAM = (int) (Double.parseDouble(cardAmounts) * 100) + "";
        if (cardAM.length() < 12) {
            int spaceLength = 12 - cardAM.length();
            for (int i = 0; i < spaceLength; i++) {
                cardAM = "0" + cardAM;
            }
        }
        return cardAM;
    }

    /**
     * 获取gmc界面接口参数
     * @return String posNum
     */
    public String getGMCString(String cardAmounts) {
        String str = "";
        String posNum = getPosNum();
        String user_id = getUserId();
        String dealTyep = "00";
        String cardAM = cardNumFormat(cardAmounts);
        String dates = getSpaceString("", 8);
        String ref = getSpaceString("", 12);
        String cer = getSpaceString("", 6);
        String rand3 = (int) (Math.random() * 900 + 100) + "";
        str = posNum + user_id + dealTyep + cardAM + dates + ref + cer + rand3;
        return str;
    }

    /**
     * 获取gmc界面接口参数(撤销命令)
     * @return String posNum
     */
    public String getCancelString(String cerNo) {
        String str = "";
        String posNum = getPosNum();
        String user_id = getUserId();
        String dealTyep = "01";
        String cardAM = "000000000000";
        String dates = getSpaceString("", 8);
        String ref = getSpaceString("", 12);
        String cer = getSpaceString(cerNo, 6);
        String rand3 = (int) (Math.random() * 900 + 100) + "";
        str = posNum + user_id + dealTyep + cardAM + dates + ref + cer + rand3;
        System.err.println(str);
        return str;
    }
    /**
     * 折扣文本框allowance数字变化监听器
     */
    public class allowanceNumDocumentListener implements DocumentListener {

    	@Override
    	public void insertUpdate(DocumentEvent e) {
               String str = allowanceNum.getText();
                //          加入现金输入框的校验，验证小数点的合法性（.）
                //          如果金额以小数点开头，则不合法
                if (str.startsWith(".")) {
                    JOptionPane.showMessageDialog(jf, "请输入正确的格式");
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            // goodsNumber.setText("");
                        	allowanceNum.setText(allowanceNum.getText().substring(0, allowanceNum.getText().length() - 1));
                        }
                    });
                    return;
                }
                //          如果金额连续出现两个小数点，则不合法
                if (str.length() >= 2) {
                    if (str.substring(str.length() - 2, str.length()).equals("..")) {
                        JOptionPane.showMessageDialog(jf, "请输入正确的格式");
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                // goodsNumber.setText("");
                            	allowanceNum.setText(allowanceNum.getText().substring(0, allowanceNum.getText().length() - 1));
                            }
                        });
                        return;
                    }
                }
                if (str.indexOf(".") != str.lastIndexOf(".")) {
                    JOptionPane.showMessageDialog(jf, "请输入正确的格式");
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            // goodsNumber.setText("");
                        	allowanceNum.setText(allowanceNum.getText().substring(0, allowanceNum.getText().length() - 1));
                        }
                    });
                    return;
                }

                if (!allowanceNum.getText().endsWith(".")) {
                    String str2 = allowanceNum.getText();

                    //收银输入框
                    boolean isNum = str.matches("^[1-9]([.]{1}[1-9])?$");//正则表达式//只能输入有一位小数的正实数
                    boolean isNum2 = str.matches("^([0-9]|9)?$");//正则表达式//只能输入1到9正整数
                    if (isNum || isNum2) {
                        if (str.length() <= 8) {

                            if (allowanceNum.getText().length() == 0) {
                                str2 = "0";
                                //                    changeNum.setText("0.00");
                                //cardNum.setText("0.00");
                            }else if(Double.parseDouble(allowanceNum.getText()+"") != 0){

                            //输入的现金大于总的金额，找零文本域显示为0
//                            if (Double.parseDouble(couponNum.getText()+"") > Double.parseDouble(totalNum.getText()+"")
//                                    || Double.parseDouble(amountNum.getText()+"") > Double.parseDouble(totalNum.getText()+"")) {
                                paidamountNum.setText(df.format(new BigDecimal(totalNum.getText()+"")
                            				 .multiply(new BigDecimal(allowanceNum.getText()+""))
                            				 .divide(new BigDecimal(10))));                               

                            }if (Double.parseDouble(couponNum.getText()) != 0 && Double.parseDouble(allowanceNum.getText()) !=0) {
                            	paidamountNum.setText(df.format(new BigDecimal(totalNum.getText()+"")
                            				 .multiply(new BigDecimal(allowanceNum.getText()+""))
                            				 .divide(new BigDecimal(10)).subtract(new BigDecimal(
                            				  couponNum.getText()+""))));
                            } else {
                                changeNum.setText("0.00");
                            }
                        } else {
                            JOptionPane.showMessageDialog(jf, "您输入的金额过大");
                        }
                    } else {
                        JOptionPane.showMessageDialog(jf, "请输入正确的格式");
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                // goodsNumber.setText("");
                            	allowanceNum.setText(allowanceNum.getText().substring(0, allowanceNum.getText().length() - 1));
                            }
                        });
                    }
                }
    	}

    	@Override
    	public void removeUpdate(DocumentEvent e) {
    		// TODO Auto-generated method stub
    		paidamountNum.setText(totalNum.getText());
    		
    	}

    	@Override
    	public void changedUpdate(DocumentEvent e) {
    		// TODO Auto-generated method stub
    		
    	}
    	
    }  
    
    

    /**
     * 抵金券文本框allowance数字变化监听器
     */
    public class couponNumDocumentListener implements DocumentListener {

		@Override
		public void insertUpdate(DocumentEvent e) {

            String str = couponNum.getText();
            //          加入现金输入框的校验，验证小数点的合法性（.）
            //          如果金额以小数点开头，则不合法
            if (str.startsWith(".")) {
                JOptionPane.showMessageDialog(jf, "请输入正确的格式");
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        // goodsNumber.setText("");
                    	couponNum.setText(couponNum.getText().substring(0, couponNum.getText().length() - 1));
                    }
                });
                return;
            }
            //          如果金额连续出现两个小数点，则不合法
            if (str.length() >= 2) {
                if (str.substring(str.length() - 2, str.length()).equals("..")) {
                    JOptionPane.showMessageDialog(jf, "请输入正确的格式");
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            // goodsNumber.setText("");
                        	couponNum.setText(couponNum.getText().substring(0, couponNum.getText().length() - 1));
                        }
                    });
                    return;
                }
            }
            if (str.indexOf(".") != str.lastIndexOf(".")) {
                JOptionPane.showMessageDialog(jf, "请输入正确的格式");
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        // goodsNumber.setText("");
                    	couponNum.setText(couponNum.getText().substring(0, couponNum.getText().length() - 1));
                    }
                });
                return;
            }

            if (!couponNum.getText().endsWith(".")) {
                String str2 = couponNum.getText();
                //收银输入框
                boolean isNum3 = str.matches("^[0-9]+(.[0-9]{1})?$");
                boolean isNum = str.matches("^[0-9]+(.[0-9]{2})?$");//正则表达式//只能输入有两位小数的正实数
                boolean isNum2 = str.matches("^[0-9]");//正则表达式//只能输入正整数
                if (isNum || isNum2 || isNum3) {
                    if (str.length() <= 8) {

                        if (couponNum.getText().length() == 0) {
                            str2 = "0";
                            //                    changeNum.setText("0.00");
                            //cardNum.setText("0.00");
                        }else if (Double.parseDouble(couponNum.getText()) != 0 && Double.parseDouble(allowanceNum.getText()) !=0) {
                            paidamountNum.setText(df.format(new BigDecimal(totalNum.getText()+"")
                            	.multiply(new BigDecimal(allowanceNum.getText()+""))
                            	.divide(new BigDecimal(10)).subtract(new BigDecimal(
                                    couponNum.getText()+""))));
                            if(Double.parseDouble(paidamountNum.getText()+"") < 0){
                           	 	paidamountNum.setText(totalNum.getText());
                           	 	JOptionPane.showMessageDialog(jf, "请输入正确抵金券金额");
                           	 	return;
                           }
                        } else if(Double.parseDouble(couponNum.getText()+"") != 0 && Double.parseDouble(allowanceNum.getText()+"") ==0){
                        	paidamountNum.setText(df.format(new BigDecimal(totalNum.getText()+"")
                        				 .subtract(new BigDecimal(couponNum.getText()+""))));
                            if(Double.parseDouble(paidamountNum.getText()) < 0){
                              	 paidamountNum.setText(totalNum.getText());
                              	 JOptionPane.showMessageDialog(jf, "请输入正确抵金券金额");
                              	return;
                              }
                        }else{  
                        	changeNum.setText("0.00");
                        }
                    } else {
                        JOptionPane.showMessageDialog(jf, "您输入的金额过大");
                    }
                } else {
                    JOptionPane.showMessageDialog(jf, "请输入正确的格式");
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            // goodsNumber.setText("");
                        	couponNum.setText(couponNum.getText().substring(0, couponNum.getText().length() - 1));
                        }
                    });
                }
            }
        
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			paidamountNum.setText(totalNum.getText());
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}
    	
    }

}
