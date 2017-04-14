package com.sunvsoft.entity;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;


public class DatabaseInits {
	static International international=International.getInstance();
	JFrame jf = new JFrame();
	JPanel jp1 = new JPanel();
	JPanel jp2 = new JPanel();
	JPanel jp3 = new JPanel();
	JPanel jp4 = new JPanel();
	JPanel jp = new JPanel();
	JLabel jl = new JLabel(international.getInternational("dataBase"));
	JLabel jl2 = new JLabel(international.getInternational("username"));
	JLabel jl3 = new JLabel(international.getInternational("password"));
	JTextField jtf1 = new JTextField(18);
	JTextField jtf2 = new JTextField(18);
	JButton jb1 = new JButton(international.getInternational("sureButton"));
	JButton jb2 = new JButton(international.getInternational("cancel1"));
	public void init(){
		jp1.add(jl); 
		
		jp2.add(jl2);
		jp2.add(jtf1);
		
		jp3.add(jl3);
		jp3.add(jtf2);
		
		jp4.add(jb1);
		jp4.add(jb2);
		jp.setLayout(new GridLayout(4,1,10,10));
		jp.add(jp1);
		jp.add(jp2);
		jp.add(jp3);
		jp.add(jp4);
		
		jf.add(jp);
		
		jf.setSize(500, 300);
		jf.setLocationRelativeTo(null);// 窗口居中显示
		jf.setResizable(false);// 不可改变大小
		jf.setUndecorated(true); // 去掉窗口的装饰
		jf.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);// 采用指定的窗口装饰风格//简单对话框风格
		jf.setVisible(true);
		ImageIcon icon = new ImageIcon(DatabaseInits.class.getClassLoader()
                .getResource("images/YZF.png"));
        jf.setIconImage(icon.getImage());

		
		jb1.addActionListener(new sureListener());
		jb2.addActionListener(new cancelListener());
		jtf1.addKeyListener(new sureKeyListener());
		jtf2.addKeyListener(new sureKeyListener());
		
	}
	public class sureListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			 new SwingWorker<Long, Void>() {
	                @SuppressWarnings("unused")
                    boolean a = false;

	                @Override
	                protected Long doInBackground() throws Exception {
	                    
	                    Gif2.show();
	        			dbCreate(jtf1.getText(),jtf2.getText());
	        			load(jtf1.getText(),jtf2.getText());
	        			loadData(jtf1.getText(),jtf2.getText());
//	        			JOptionPane.showMessageDialog(jf, "请重新启动程序");
//	        			jf.dispose();
	                    return null;
	                }

	                @Override
	                protected void done() {
	                    Gif2.close();
	                    JOptionPane.showMessageDialog(jf, international.getInternational("programReset"));
	        			jf.dispose();
	                }
	            }.execute();
			
//			dbCreate(jtf1.getText(),jtf2.getText());
//			load(jtf1.getText(),jtf2.getText());
//			loadData(jtf1.getText(),jtf2.getText());
//			jf.dispose();
		}
		
	}
	public class cancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			jf.dispose();
		}
		
	}
	public class sureKeyListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent arg0) {
			if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
				dbCreate(jtf1.getText(),jtf2.getText());
				load(jtf1.getText(),jtf2.getText());
				
				jf.dispose();
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			
		}
		
	}
	public void dbCreate(String user,String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			
			e1.printStackTrace();
		}
	      String url = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf-8";
	      Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
			
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}

	      PreparedStatement statement;
		try {
			statement = connection.prepareStatement("create database o2ooffline character set utf8");
			statement.execute();
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
//		Runtime rt = Runtime.getRuntime();
//		try {
//			rt.exec("cmd /c mysqladmin -uroot -p12345678 create o2ooffline");
////			rt.exec("mysql use mysql");
////			rt.exec("mysql create database o2ooffline");
//			System.out.println("sdfsdf");
////			rt.exec("mysql -uroot -p12345678 >create database if not exists 'o2ooffline' DEFAULT CHARSET=utf8");//CREATE DATABASE if not exists `数据库名` DEFAULT CHARSET=gbk;
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		}
		/*Runtime rt2 = Runtime.getRuntime();
		try {
			rt2.exec("cmd /c mysqladmin -uroot -p12345678 create o2ooffline if not exists 'o2ooffline' DEFAULT CHARSET=utf8");//CREATE DATABASE if not exists `数据库名` DEFAULT CHARSET=gbk;
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}*/
	}
	/**
	 * 导入 导入的时候需要数据库已经建好。
	 */
	public void load(String user,String password) {
		OutputStream out = null;
		BufferedReader br = null;
		OutputStreamWriter writer = null;
		try {
			// String fPath = "d:/demo.sql";
			Runtime rt2 = Runtime.getRuntime();
			// 调用 mysql 的 cmd:
			// 为什么设置编码会报错？用mysqldump为什么也会报错？
			Process child = rt2.exec("mysql -u"+user+" -p"+password+" o2ooffline");// 设置导出编码为utf8。这里必须是utf8
			// 调用 mysql 的 cmd:
			// rt.exec("create database demo");
			// Process child = rt.exec("mysql -uroot -pabc123456 tradeload");
			out = child.getOutputStream();// 控制台的输入信息作为输出流
			String inStr;
			StringBuffer sb = new StringBuffer("");
			String outStr;
//			String file = DatabaseInit.class.getClassLoader().getResource("abc/o2ooffline.sql").toString();
//			new FileInputStream
			//InputStream is=this.getClass().getResourceAsStream("/resource/res.txt"); 
//			br = new BufferedReader(new InputStreamReader(new FileInputStream("src/o2oofflineNull.sql"), "utf8"));
			br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("o2oofflineNull.sql"),"utf8"));
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");
			}
			outStr = sb.toString();
			writer = new OutputStreamWriter(out, "utf8");
			writer.write(outStr);
//			br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("o2oofflineData.sql"),"utf8"));
//			while ((inStr = br.readLine()) != null) {
//				sb.append(inStr + "\r\n");
//			}
//			outStr = sb.toString();
//			writer = new OutputStreamWriter(out, "utf8");
//			writer.write(outStr);
			// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
//			writer.flush();
			// 别忘记关闭输入输出流
			//logger.info("文件:" + file.getName() + "导入成功！数据库名：" + dbname);
		} catch (Exception e) {
			//logger.info("文件:" + file.getName() + "导入失败！数据库名：" + dbname);
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if (br != null) {
					br.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
//		Runtime rt2 = Runtime.getRuntime();
//		try {
//			rt2.exec("cmd /c mysqladmin -uroot -p12345678 alter o2ooffline DEFAULT CHARSET utf8");
//			
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		}
//		JOptionPane.showMessageDialog(jf, "请重新启动程序");
	}
	/**
	 * 导入 初始化数据
	 */
	public void loadData(String user,String password) {
		OutputStream out = null;
		BufferedReader br = null;
		OutputStreamWriter writer = null;
		try {
			// String fPath = "d:/demo.sql";
			Runtime rt2 = Runtime.getRuntime();
			// 调用 mysql 的 cmd:
			// 为什么设置编码会报错？用mysqldump为什么也会报错？
			Process child = rt2.exec("mysql -u"+user+" -p"+password+" o2ooffline");// 设置导出编码为utf8。这里必须是utf8
			// 调用 mysql 的 cmd:
			// rt.exec("create database demo");
			// Process child = rt.exec("mysql -uroot -pabc123456 tradeload");
			out = child.getOutputStream();// 控制台的输入信息作为输出流
			String inStr;
			StringBuffer sb = new StringBuffer("");
			String outStr;
//			String file = DatabaseInit.class.getClassLoader().getResource("abc/o2ooffline.sql").toString();
//			new FileInputStream
			//InputStream is=this.getClass().getResourceAsStream("/resource/res.txt"); 
//			br = new BufferedReader(new InputStreamReader(new FileInputStream("src/o2oofflineNull.sql"), "utf8"));
			br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream("o2oofflineData.sql"),"utf8"));
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");
			}
			outStr = sb.toString();
			writer = new OutputStreamWriter(out, "utf8");
			writer.write(outStr);
			// 注：这里如果用缓冲方式写入文件的话，会导致中文乱码，用flush()方法则可以避免
//			writer.flush();
			// 别忘记关闭输入输出流
			//logger.info("文件:" + file.getName() + "导入成功！数据库名：" + dbname);
		} catch (Exception e) {
			//logger.info("文件:" + file.getName() + "导入失败！数据库名：" + dbname);
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if (br != null) {
					br.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
//		Runtime rt2 = Runtime.getRuntime();
//		try {
//			rt2.exec("cmd /c mysqladmin -uroot -p12345678 alter o2ooffline DEFAULT CHARSET utf8");
//			
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		}
//		JOptionPane.showMessageDialog(jf, "请重新启动程序");
	}
//public static void main(String[] args){
//	new DatabaseInits().dbCreate();
////	try {
////		Thread.sleep(1000);
////	} catch (InterruptedException e) {
////		
////		e.printStackTrace();
////	}
//	new DatabaseInits().load();
//}
}
