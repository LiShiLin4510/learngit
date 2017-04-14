package com.sunvsoft.entity;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xvolks.jnative.exceptions.NativeException;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sunvsoft.accessOnline.ConnectOnlineMethod;
import com.sunvsoft.backup.DoBackup;
import com.sunvsoft.backup.PropertiesUtil;
import com.sunvsoft.esc_pos.HSCustomerShow;
import com.sunvsoft.sqlset.SetSql;
import com.sunvsoft.util.StringUtil;

public class UserLogin {
    private static UserLogin userLogin;
    private UserLogin (){};
    public UserLogin getInstance(){
        if(userLogin==null){
            userLogin = new UserLogin();
        }
        return userLogin;
    }
    International international = International.getInstance();
	JFrame jFrame = new JFrame();
	JPanel jPanel = new JPanel();
	JPanel jPanel1 = new JPanel();
	JPanel jPanel2 = new JPanel();
	JPanel jPanel3 = new JPanel();
	JPanel jPanel4 = new JPanel();
	JRadioButton jrb = new JRadioButton(international.getInternational("netWorking"));
	JTextField accountNumField;
	JLabel accountNumLabel = new JLabel(international.getInternational("username"));
	JPasswordField passwordField;
	JLabel passwordLabel = new JLabel(international.getInternational("password"));
	Connection coo;
	static int index = 0;
	static int index1 = 0;
	@SuppressWarnings("rawtypes")
    static JComboBox changeSkin = new JComboBox();

	private String[] skinName = {international.getInternational("changeTheme"), 
	        international.getInternational("green"), international.getInternational("golden"),
	        international.getInternational("blue"), international.getInternational("gray")};
	private Object[] getSkinName() {
		Object[] os = new Object[skinName.length];
		for (int i = 0; i < skinName.length; i++) {
			os[i] = skinName[i];
		}
		return os;
	}
	
	//国际化下拉选项
//	static JComboBox changeLaguage = new JComboBox();

//    private String[] laguages = {"中文","英语"};
//    private Object[] getLaguage() {
//        Object[] os = new Object[laguages.length];
//        for (int i = 0; i < laguages.length; i++) {
//            os[i] = laguages[i];
//        }
//        return os;
//    }
	@SuppressWarnings("static-access")
    public void init() {
		/**
		 * 初始化客显端口for海信
		 */
//		int cat = PropertiesUtil.getDisplayCat();
//		if(cat==2){
//				try {
//					 HSCustomerShow hsc = new HSCustomerShow();
//					 hsc.HSOpenDComm(PropertiesUtil.getnPort(),0);
//					 hsc.VC110_Init();
////					 hsc.VC110_Display(2, "45.67", 10);
//				} catch (IllegalAccessException e) {
//					System.out.println("初始化失败");
//					e.printStackTrace();
//				} catch (NativeException e) {
//					System.out.println("初始化失败");
//					e.printStackTrace();
//				}
//		}
		/**
		 * 默认选中联网
		 */
		jrb.setSelected(true);
		/**
		 * JComboBox固定长度
		 */
		changeSkin.setMaximumSize(new Dimension(80,30));
		changeSkin.setMinimumSize(new Dimension(80,30));
		changeSkin.setPreferredSize(new Dimension(80,30));
		//选择国际化的语言
//		changeLaguage.setMaximumSize(new Dimension(60,30));
//		changeLaguage.setMinimumSize(new Dimension(60,30));
//		changeLaguage.setPreferredSize(new Dimension(60,30));
		/**
		 * 操作员登录
		 */
		JLabel jLabel = new JLabel("操作员登录");
		jPanel4.add(jLabel);
		jLabel.setFont(new Font("黑体", Font.PLAIN, 26));
		jLabel.setForeground(Color.RED);
		jLabel.setBounds(175, 30, 230, 30);
		/**
		 * Button 设置大小位置
		 */
		JButton loginButton = new JButton(international.getInternational("login"));
		JButton resetButton = new JButton(international.getInternational("reset"));
		Dimension preferredSize = new Dimension(80, 30);// 设置尺寸
		loginButton.setPreferredSize(preferredSize);
		resetButton.setPreferredSize(preferredSize);
		jPanel3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 25));
		jPanel3.add(loginButton);
		jPanel3.add(resetButton);
		jPanel3.add(changeSkin);
//		jPanel3.add(changeLaguage);
		jPanel3.add(jrb);
		/**
		 * JTextField 设置位置大小
		 */
		accountNumField = new JTextField();
		passwordField = new JPasswordField();
		jPanel4.setLayout(null);
		accountNumField.setBounds(161, 100, 200, 30);
		passwordField.setBounds(161, 140, 200, 30);


		accountNumLabel.setBounds(110, 100, 200, 30);
		passwordLabel.setBounds(110, 140, 200, 30);

		accountNumLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		accountNumLabel.setForeground(Color.black);
		passwordLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		passwordLabel.setForeground(Color.black);
		jPanel4.add(accountNumField);
		jPanel4.add(passwordField);
		jPanel4.add(accountNumLabel);
		jPanel4.add(passwordLabel);
//		jPanel4.add(changeSkin1);
//		jPanel4.add(changeSkin2);
		/**
		 * jPanel 布局
		 */
		jPanel.setLayout(new BorderLayout());
		jPanel.add(jPanel4, BorderLayout.CENTER);
		jPanel.add(jPanel3, BorderLayout.SOUTH);

		jFrame.add(jPanel);
		jFrame.setSize(500, 300);
		jFrame.setLocationRelativeTo(null);// 窗口居中显示
		jFrame.setResizable(false);// 不可改变大小
		jFrame.setUndecorated(true); // 去掉窗口的装饰
		jFrame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);// 采用指定的窗口装饰风格//简单对话框风格
		// 替换标题栏图标为透明图片，不能去掉，只能替换
		// ImageIcon icon = new ImageIcon("D:/icon.png");
		ImageIcon icon = new ImageIcon(UserLogin.class.getClassLoader()
				.getResource("images/YZF.png"));
		jFrame.setIconImage(icon.getImage());

		jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);
		passwordField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginMethod();
				}
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
						if(accountNumField.isFocusOwner()){
							passwordField.requestFocus();
						}else{
							accountNumField.requestFocus();
						}
						
						break;
					
					default:
						break;
					}
				}
			}
		}, AWTEvent.KEY_EVENT_MASK);
		loginButton.addActionListener(new BtListener());
		resetButton.addActionListener(new Bt2Listener());
	}

	class BtListener implements ActionListener {
		// @Override

		public void actionPerformed(ActionEvent e) {
			loginMethod();
		}
	}

	class Bt2Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			accountNumField.setText("");
			passwordField.setText("");
		}
	}

	public Connection getConn() {
		String driver = PropertiesUtil.getJdbcDriver();
		String url = PropertiesUtil.getJdbcUrl();
		String user = PropertiesUtil.getJdbcUser();
		String passwd = PropertiesUtil.getJdbcPasswd();
		try {
			Class.forName(driver);
			coo = DriverManager.getConnection(url,user,passwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return coo;
	}

	/**
	 * 换肤
	 */
	class changeSkinListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// changeSkin1=new JComboBox(jcb.a());

		}

	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		final UserLogin ul = new UserLogin();
		// 使标题栏的风格也跟着一起改变
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		// 必须要启动这个线程，不然无法达到换肤效果
		SwingUtilities.invokeLater(new Runnable() {
			@SuppressWarnings("rawtypes")
            public void run() {
				try {
					changeSkin = new JComboBox(ul.getSkinName());
					changeSkin.addActionListener(new ActionListener() {
						@SuppressWarnings("unused")
                        public void actionPerformed(ActionEvent e) {
							index = changeSkin.getSelectedIndex();
							Object selected = changeSkin.getSelectedItem();
							try {
								switch (index) {
								case 0:
									// 翡翠绿
									UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceGreenMagicLookAndFeel");
									break;
								case 1:
									// 翡翠绿
									UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceGreenMagicLookAndFeel");
									break;
								case 2:
									// 土豪金
									UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceFieldOfWheatLookAndFeel");
									break;
								case 3:
									// 蓝色
									UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceOfficeBlue2007LookAndFeel");
									break;
								default:
									// 灰色
									UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceRavenGraphiteLookAndFeel");
									break;
									
									
								}
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					});
					changeSkin.setSelectedIndex(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
//				changeLaguage = new JComboBox(ul.getLaguage());
//				changeLaguage.addActionListener(new ActionListener() {
//                    
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        index1 = changeLaguage.getSelectedIndex();
//                    }
//                });
//				changeLaguage.setSelectedIndex(0);
				ResultSet rs = new SetSql().setSql("select * from es_user where username = 'adminOff'");
				if(rs!=null && !rs.equals("")){
					System.out.println("rs="+rs);
					ul.init();
					try {
						if(rs.next()){}
						else{
							new SetSql().setSqlNotReturn("insert into es_user(userid,username,password,realname) values('-1','adminOff','e10adc3949ba59abbe56e057f20f883e','admin')");
							new SetSql().setSqlNotReturn("insert into es_user(userid,username,password,realname) values('-2','init','e10adc3949ba59abbe56e057f20f883e','init')");
						}
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
				}
			}
		});
//		reStart();
	}
	@SuppressWarnings("unused")
    public static void reStart(){

		ResultSet rs = new SetSql().setSql("select * from es_user where username = 'adminOff'");
		try {
			if(rs.next()){}
			else{
				new SetSql().setSqlNotReturn("insert into es_user(userid,username,password,realname) values('-1','adminOff','e10adc3949ba59abbe56e057f20f883e','admin')");
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		final UserLogin ul = new UserLogin();
		// 使标题栏的风格也跟着一起改变
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		// 必须要启动这个线程，不然无法达到换肤效果
	
	}
	@SuppressWarnings("deprecation")
    public void loginMethod(){
		/**
		 * 初始化客显端口for海信
		 */
		int cat = PropertiesUtil.getDisplayCat();
		if(cat==2){
				try {
					 HSCustomerShow hsc = new HSCustomerShow();
					 hsc.HSOpenDComm(PropertiesUtil.getnPort(),0);
					 hsc.VC110_Init();
//					 hsc.VC110_Display(2, "45.67", 10);
				} catch (IllegalAccessException e) {
					System.out.println("初始化失败");
					e.printStackTrace();
				} catch (NativeException e) {
					System.out.println("初始化失败");
					e.printStackTrace();
				}
		}
		new DoBackup().firstBackup();//首次备份到硬盘
		String username = String.valueOf(accountNumField.getText());
		String password = String.valueOf(passwordField.getText());
		String store_num  = PropertiesUtil.getStoreId();
		password = StringUtil.md5(password);//加密

		if (username.equals("") || password.equals("")) {
			JOptionPane.showMessageDialog(jFrame, "登录密码或账户不能为空!",
					"警告,提醒!", JOptionPane.INFORMATION_MESSAGE);
		} else if(!jrb.isSelected()){
			Connection conn = getConn();
			String sql = "select username,password from es_user where username=?";
			PreparedStatement psta = null;
			ResultSet rs = null;
			try {
				psta = conn.prepareStatement(sql);
				psta.setString(1, username);
				rs = psta.executeQuery();
				if (rs.next()) {
					if (password.equals(rs.getString(2))) {
					    
					    creatClasses();           //调用创建班结记录的方法
					} else {
						JOptionPane.showMessageDialog(jFrame,
								"密码输入错误!!!", "提醒!!警告!!!",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(jFrame,
							"用户名不存在,请重新输入或注册一个账户！！！", "提醒!!!",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
						rs = null;
					}
					if (psta != null) {
						psta.close();
						psta = null;
					}
					if (conn != null) {
						conn.close();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}else{
		    MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
		    queryParam.add("username", username);
 		    queryParam.add("store_id",store_num);
		    String url = PropertiesUtil.getIp() + "/api/shop/member!loginOffline.do";
		    ConnectOnlineMethod connection = new ConnectOnlineMethod();
		    try {
                String outPut = connection.connectOnline(queryParam, url);
                if(!outPut.equals("")){
                    JSONArray array = connection.jsonConvertion(outPut);
                    JSONObject jsonObject = connection.getJsonObject(array);
                    if(jsonObject != null){
	                    String passwords = (String) jsonObject.get("password");
	                    if(password.equals(passwords)){
	                        creatClasses();
	                    }else{
	                    	JOptionPane.showMessageDialog(jFrame,
		                            "密码输入错误!!!", "提醒!!警告!!!",
		                            JOptionPane.ERROR_MESSAGE);
	                    }
	                }else{
	                    JOptionPane.showMessageDialog(jFrame,
	                            "用户名不存在,请重新输入或注册一个账户!!!", "提醒!!警告!!!",
	                            JOptionPane.ERROR_MESSAGE);
	                }
                }
            } catch (Exception e) {
            	JOptionPane.showMessageDialog(jFrame,
                        "未检测到网络，请在脱机模式下登录！！！", "提醒!!警告!!!",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
		    
		}
		
	
	}
	/**
	 * 
	 *   创建班结记录，每登陆一次系统就会生成一个新的班结号
	 * @param   name    
	 * @return String   
	 * @Exception 异常对象
	 * @author lsl
	 */
	@SuppressWarnings({ "static-access", "unused" })
    public void creatClasses(){

        ResultSet rsClasses = new SetSql()
                .setSql("select * from es_classes");
        try {
        if (!rsClasses.next()) {
            Date dateNow = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(
                    "yyyy-MM-dd");
            String aString = sdf.format(dateNow);
//          System.out.println(aString);
            new SetSql()
                    .setSqlNotReturn("insert into es_classes(date,classes) values('2015-08-11',1)");
        }
        Date dateNow = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyy-MM-dd");
        String aString = sdf.format(dateNow);
        ResultSet rsCurrent = new SetSql()
                .setSql("select * from es_classes");
      
            rsCurrent.next();
        String dateClasses;
            dateClasses = rsCurrent
                    .getString("date");
       
        int classesOld;
            classesOld = rsCurrent
                    .getInt("classes");
       
        long time = System.currentTimeMillis() / 1000;
        // int classes = 1;
        if (dateClasses.equals(aString)) {
            classesOld = classesOld + 1;
            new SetSql()
                    .setSqlNotReturn("update es_classes set classes = "
                            + classesOld);
        } else {
            classesOld = 1;
            new SetSql()
                    .setSqlNotReturn("update es_classes set classes = "
                            + classesOld);
            new SetSql()
                    .setSqlNotReturn("update es_classes set date = '"
                            + aString + "'");
        }
        new SetSql()
                .setSqlNotReturn("insert into es_shift(user_code,begin_time,classes,status,date) values('"
                        + accountNumField.getText()
                        + "',"
                        + time
                        + ","
                        + classesOld
                        + ",0,'"
                        + aString + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        O2OMainMenu o2o = new O2OMainMenu();
        if(jrb.isSelected()){
            O2OMainMenu.internetPass = true;
        }
        //检测联网断网，并自动切换,每秒扫描一次
//        TimerTest.queryInternetPass(0,10000);
        //订单实时传输线上的定时任务
        
        o2o.salesman = accountNumField.getText();
        o2o.init();
        if(O2OMainMenu.internetPass){
            CommitDataClass.upLoadData(0, 30*60*1000);
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
                O2OMainMenu.addressLabel.setText(this.international.getInternational("harvestAddress") + s);            
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            O2OMainMenu.addressLabel.setVisible(true);
            O2OMainMenu.addressLabel.setText(this.international.getInternational("harvestAddress") + PropertiesUtil.getStoreName().toString());            
        }
        new ShowTips().init();
        ShowTips.jf.setVisible(false);
        jFrame.dispose();
        
        //初始化收银系统的另一面
        DoubleScreenView od = new DoubleScreenView();
        od.init();
	}
}

