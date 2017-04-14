package com.sunvsoft.entity;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sunvsoft.accessOnline.ConnectOnlineMethod;
import com.sunvsoft.backup.PropertiesUtil;
import com.sunvsoft.sqlset.SetSql;

public class MemberInputFrame {
    private static MemberInputFrame mInputFrame = null;
    private MemberInputFrame(){}
    public static MemberInputFrame getInstance(){
        if(mInputFrame == null){
            mInputFrame = new MemberInputFrame();
        }
        return mInputFrame;
    }
    International international = International.getInstance1();
    JFrame jFrameM = new JFrame();
	JPanel jPanel = new JPanel();
	JPanel jPanel2 = new JPanel();
	JPanel jPanel3 = new JPanel();
	JPanel jPanel4 = new JPanel();
	JPanel jPanel5 = new JPanel();
	JPanel jPanel6 = new JPanel();
	JPanel jPanel7 = new JPanel();
	JLabel jLabel = new JLabel(international.getInternational("inputMember"));
	JLabel phoneLabel = new JLabel(international.getInternational("phone"));
	JTextField phone ;
	JLabel accountNumLable = new JLabel(international.getInternational("memberNum"));
	JTextField accountNumField ;
	public O2OMainMenu mm;
	
	
	@SuppressWarnings({ "serial", "static-access" })
    public void init() {
		
		accountNumField = new JTextField();
		phone = new JTextField();
//		限定长度
		accountNumField.setDocument(new PlainDocument() {
			int MAX_LENGTH = 24;
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
		phone.setDocument(new PlainDocument() {
			int MAX_LENGTH = 24;
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
		jLabel.setFont(new Font("宋体", Font.PLAIN, 26));
		jLabel.setForeground(Color.red);
		JButton activationButton = new JButton(international.getInternational("sureButton"));
		/**
		 * 	字体	
		 */
		accountNumLable.setFont(new Font("黑体", Font.PLAIN, 16));
		accountNumLable.setForeground(Color.black);
		phoneLabel.setFont(new Font("黑体", Font.PLAIN, 16));
		phoneLabel.setForeground(Color.black);
		
		activationButton.setFont(new Font("黑体", Font.PLAIN, 16));
		activationButton.setForeground(Color.black);
		
		/**
		 * 位置
		 */
		jPanel4.setLayout(null);
		jLabel.setBounds(200, 30, 230, 30);
		accountNumLable.setBounds(90, 100, 230, 30);
		accountNumField.setBounds(170, 100, 230, 30);
		phoneLabel.setBounds(90, 150, 230, 30);
		phone.setBounds(170, 150, 230, 30);
		activationButton.setBounds(210, 200, 80, 30);
		/**
		 * 布局
		 */
		jPanel4.add(jLabel);
		jPanel4.add(accountNumLable);
		jPanel4.add(accountNumField);
		jPanel4.add(phoneLabel);
		jPanel4.add(phone);
		jPanel4.add(activationButton);
		
		jPanel.setLayout(new BorderLayout());
		jPanel.add(jPanel4, BorderLayout.CENTER);
		jFrameM.add(jPanel);
		
		jFrameM.setSize(500, 300);
		jFrameM.setLocationRelativeTo(null);// 窗口居中显示
		jFrameM.setResizable(false);// 不可改变大小
		
		jFrameM.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);// 采用指定的窗口装饰风格//简单对话框风格
		
		jFrameM.setDefaultCloseOperation(jFrameM.DO_NOTHING_ON_CLOSE);
		ImageIcon icon = new ImageIcon(MemberInputFrame.class.getClassLoader()
                .getResource("images/YZF.png"));
        jFrameM.setIconImage(icon.getImage());
		jFrameM.setVisible(true);
		// 添加监听事件
				activationButton.addActionListener(new activationListener());
				accountNumField.addKeyListener(new keyListener());
				phone.addKeyListener(new keyListener());
		jFrameM.addWindowListener(new WindowAdapter() {
			 public void windowClosing(WindowEvent e) {
				 jFrameM.dispose();
			 }
		});
		
		//切换账号数据刷新
//		O2OMainMenu.totalNumberLabelChange.setText("");
//		O2OMainMenu.totalMoneyLabelChange.setText("");
//		O2OMainMenu.tableRefresh(null);
}
	public class activationListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			memberFounction();
		}
		
	}
	public void memberFounction(){
		String member = accountNumField.getText();
		String phoneNum = phone.getText();
		if(!O2OMainMenu.internetPass){       //未联网状态
			ResultSet rs = new SetSql()
					.setSql("select * from es_member where card_id = '"
							+ member + "'");
			ResultSet rs1 = new SetSql()
			.setSql("select * from es_member where phone = '"
					+ phoneNum + "'");
			try {
				if (rs1.next()) {
					int s = O2OMainMenu.memberNumber.getText().toString().length();
					if(s>0){
                        // 清空table数据
                           DefaultTableModel model = (DefaultTableModel) O2OMainMenu.table
                                   .getModel();
                           while (model.getRowCount() > 0) {
                               model.removeRow(model.getRowCount() - 1);
                           }
                       }
					O2OMainMenu.memberNumber.setText(rs1.getString("card_id"));
//					System.out.println("手机号大0");
					jFrameM.dispose();
				} else if(rs.next()){
					if(O2OMainMenu.memberNumber.getText().toString().length()>0){
                        // 清空table数据
                           DefaultTableModel model = (DefaultTableModel) O2OMainMenu.table
                                   .getModel();
                           while (model.getRowCount() > 0) {
                               model.removeRow(model.getRowCount() - 1);
                           }
                       }
					O2OMainMenu.memberNumber.setText(member);
//					System.out.println("账号大0");
					jFrameM.dispose();
				}else {
					JOptionPane.showMessageDialog(jFrameM, "会员号无效，请重新输入！");
					jFrameM.dispose();
					accountNumField.setText("");
					phone.setText("");
					accountNumField.requestFocus();
				}
			} catch (HeadlessException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{                //联网状态
		    MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
		    String store_id = PropertiesUtil.getStoreId();
		    queryParam.add("store_id",store_id);
		    queryParam.add("cardNumber",member);
		    queryParam.add("phone",phoneNum);
		    String url = PropertiesUtil.getIp() + "/api/shop/member!getCard.do";
		    ConnectOnlineMethod connect = new ConnectOnlineMethod();
		    try {
                String outPut = connect.connectOnline(queryParam, url);
                if(!outPut.equals("")){
	                JSONArray array = connect.jsonConvertion(outPut);
	                JSONObject object = connect.getJsonObject(array);
	                if(object !=null){
	                    String phone = (String) object.get("phone");
                        String card_id = object.getString("card_id");
//                        String address_id =object.getInt("addr_id")+"";
                        String address = object.getString("address");
                        if(phone.equals(phoneNum)||member.equals(card_id)){
                            if(O2OMainMenu.memberNumber.getText().toString().length()>0){
                             // 清空table数据
                                DefaultTableModel model = (DefaultTableModel) O2OMainMenu.table
                                        .getModel();
                                while (model.getRowCount() > 0) {
                                    model.removeRow(model.getRowCount() - 1);
                                }
                            }
                            O2OMainMenu.addressLabel.setText("收货地址:"+address);
                            O2OMainMenu.memberNumber.setText(card_id);
//                            O2OMainMenu.addrId.setText(address_id);
                            O2OMainMenu.addrId.setText("0");
                            jFrameM.dispose();
                        }
	                }else{
	                    JOptionPane.showMessageDialog(jFrameM, "会员号无效，请重新输入！");
	                    jFrameM.dispose();
						accountNumField.setText("");
						phone.setText("");
	                    accountNumField.requestFocus();
	                }
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
		    
		}
		//因为是单例模式，所以要新建一个对象
		mInputFrame = null;
	}
	public class keyListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				memberFounction();
				mInputFrame = null;
			}
			if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
				jFrameM.dispose();
			}
				
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}
		
	}
}
