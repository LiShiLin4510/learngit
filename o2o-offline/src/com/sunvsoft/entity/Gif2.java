package com.sunvsoft.entity;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Gif2 {
	static JFrame jf = new JFrame();
	static International international=International.getInstance();

	@SuppressWarnings("static-access")
    public static void show() {
		JLabel jLabel = new JLabel();
		JLabel jLabel1 = new JLabel(international.getInternational("executes"),jLabel.CENTER);
//		jLabel.setSize(100, 100);
//		ImageIcon image = new ImageIcon("D:\\loading2.gif");
		ImageIcon image = new ImageIcon(UserLogin.class.getClassLoader()
				.getResource("images/loading2.gif"));
		jLabel.setIcon(image);//定义此组件将要显示的图标
		jLabel.setIconTextGap(6);//如果同时设置了图标和文本属性，则此属性定义它们之间的间隔
		
		jLabel1.setFont(new Font("黑体", Font.PLAIN, 16));
		jLabel.setBounds(25, 50, 215, 20);
		jLabel1.setBounds(25, 20, 215, 20);
		
		jf.setLayout(null);
		jf.add(jLabel);
		jf.add(jLabel1);

		jf.setSize(270, 123);
		jf.setLocationRelativeTo(null);// 窗口居中显示
		jf.setResizable(false);// 不可改变大小
		jf.setUndecorated(true); // 去掉窗口的装饰
		jf.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);// 采用指定的窗口装饰风格//简单对话框风格
		jf.setDefaultCloseOperation(jf.DISPOSE_ON_CLOSE);
		jf.setVisible(true);
	}

	public static void close() {
		jf.dispose();
	}

	public static void main(String[] args) {
		// 使标题栏的风格也跟着一起改变
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		// 必须要启动这个线程，不然无法达到换肤效果
		SwingUtilities.invokeLater(new Runnable() {
			@SuppressWarnings("static-access")
            public void run() {
				try {
					UIManager
							.setLookAndFeel("org.jvnet.substance.skin.SubstanceGreenMagicLookAndFeel");

				} catch (Exception e1) {
					e1.printStackTrace();
				}
				Gif2 g = new Gif2();
				g.show();
//				try {
//					Thread.sleep(50000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				g.close();
			}
		});
	}
}
