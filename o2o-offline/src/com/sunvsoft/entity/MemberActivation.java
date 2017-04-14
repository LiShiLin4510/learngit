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

public class MemberActivation {
	private static MemberActivation mActivation = null;

//    private void MemberActivation() {}

	public static MemberActivation getInstance() {
		if (mActivation == null) {
			mActivation = new MemberActivation();
		}
		return mActivation;
	}
	static International international=International.getInstance();
	JFrame jFrameM = new JFrame(international.getInternational("memberActivation"));
	JPanel jPanel = new JPanel();
	JPanel jPanel2 = new JPanel();
	JPanel jPanel3 = new JPanel();
	JPanel jPanel4 = new JPanel();
	JPanel jPanel5 = new JPanel();
	JPanel jPanel6 = new JPanel();
	JPanel jPanel7 = new JPanel();
	JLabel jLabel = new JLabel(international.getInternational("memberActivation"));
	JLabel accountNumLable = new JLabel(international.getInternational("membership"));
	JTextField accountNumField;

	public MemberActivation() {
		super();
	}

	@SuppressWarnings("static-access")
    public void init() {
		accountNumField = new JTextField(10);
		jLabel.setFont(new Font("宋体", Font.PLAIN, 26));
		jLabel.setForeground(Color.red);
		JButton activationButton = new JButton(international.getInternational("activation"));
		/**
		 * 字体
		 */
		accountNumLable.setFont(new Font("黑体", Font.PLAIN, 16));
		accountNumLable.setForeground(Color.black);

		activationButton.setFont(new Font("黑体", Font.PLAIN, 16));
		activationButton.setForeground(Color.black);

		/**
		 * 位置
		 */
		jPanel4.setLayout(null);
		jLabel.setBounds(200, 30, 230, 30);
		accountNumLable.setBounds(100, 100, 230, 30);
		accountNumField.setBounds(170, 100, 230, 30);
		activationButton.setBounds(210, 200, 80, 30);
		/**
		 * 布局
		 */
		jPanel4.add(jLabel);
		jPanel4.add(accountNumLable);
		jPanel4.add(accountNumField);
		jPanel4.add(activationButton);

		jPanel.setLayout(new BorderLayout());
		jPanel.add(jPanel4, BorderLayout.CENTER);
		jFrameM.add(jPanel);

		jFrameM.setSize(500, 300);
		jFrameM.setLocationRelativeTo(null);// 窗口居中显示
		jFrameM.setResizable(false);// 不可改变大小
		jFrameM.setUndecorated(true); // 去掉窗口的装饰
		jFrameM.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);// 采用指定的窗口装饰风格//简单对话框风格
		jFrameM.setDefaultCloseOperation(jFrameM.DO_NOTHING_ON_CLOSE);
		jFrameM.setVisible(true);
		ImageIcon icon = new ImageIcon(MemberActivation.class.getClassLoader()
				.getResource("images/YZF.png"));
		jFrameM.setIconImage(icon.getImage());

		// 添加监听事件
		activationButton.addActionListener(new activationListener());
		accountNumField.addKeyListener(new keyListener());
		jFrameM.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				jFrameM.dispose();
			}
		});

	}

	class resetListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			accountNumField.setText("");
			accountNumField.requestFocus();
		}

	}

	public class activationListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			memberActivationMethod();
		}

	}

	public class keyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				jFrameM.dispose();
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				memberActivationMethod();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

	}

	// 会员激活监听器方法
	public void memberActivationMethod() {

		String cardNumber = accountNumField.getText();

		// O2OMainMenu.internetPass==true时代表联网状态
		if (!O2OMainMenu.internetPass) {
			ResultSet rs = new SetSql()
					.setSql("select * from es_member where card_id = '"
							+ cardNumber + "'");
			try {
				if (rs.next()) {
					JOptionPane.showMessageDialog(jFrameM, "该卡已激活");
					jFrameM.dispose();
					accountNumField.setText("");
				} else {
					long time = System.currentTimeMillis() / 1000;
					if (cardNumber.length() == 0) {
						JOptionPane.showMessageDialog(jFrameM, "激活失败，请换卡");
						accountNumField.requestFocus();
						accountNumField.setText("");
					} else {
						new SetSql()
								.setSqlNotReturn("insert into es_member (card_id,accepted_date) value('"
										+ cardNumber + "','" + time + "')");
						JOptionPane.showMessageDialog(jFrameM, "激活成功");
						jFrameM.dispose();
						accountNumField.setText("");

					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else {
			MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl(); // 通过用户输入的会员卡号在线上的数据库中查询
			queryParams.add("cardNumber", cardNumber);
			String cardurl = PropertiesUtil.getIp()
					+ "/api/shop/member!getCard.do";
			ConnectOnlineMethod connect = new ConnectOnlineMethod();
			try {
				String outPut = connect.connectOnline(queryParams, cardurl);
				if (!outPut.equals("")) {
					JSONArray jsonArray = connect.jsonConvertion(outPut);
					JSONObject jsonObject = connect.getJsonObject(jsonArray);
					// String memberPhone = (String) jsonObject.get("phone");
					if (jsonObject != null) { // 如果从线上查到了数据
						String memberCard = (String) jsonObject.get("card_id");
						if (memberCard != null) {
							JOptionPane.showMessageDialog(jFrameM, "该卡已激活");
							jFrameM.dispose();
						}
					} else {
						// long time = System.currentTimeMillis()/1000;
						if (cardNumber.length() == 0) {
							JOptionPane.showMessageDialog(jFrameM, "激活失败，请换卡");
						} else {
							MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
							// String accepted_date =
							// System.currentTimeMillis()/1000;
							queryParam.add("cardNumber", cardNumber);
							// queryParam.add("accepted_date", time);
							String urls = PropertiesUtil.getIp()
									+ "/api/shop/member!addMemberCardByOffline.do";
							connect.connectOnline(queryParam, urls);
							JOptionPane.showMessageDialog(jFrameM, "激活成功");
							jFrameM.dispose();
						}
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 因为是单例模式，所以要新建一个对象
		mActivation = null;
	}
}
