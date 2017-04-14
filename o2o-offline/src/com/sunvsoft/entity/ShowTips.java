package com.sunvsoft.entity;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;

public class ShowTips {
	static JFrame jf = new JFrame();
//	JLabel jl1 = new JLabel("Tips:",JLabel.CENTER);
	JLabel jl2 = new JLabel("    更改商品数量：在输入框输入相应数量之后按“→”键");
	JLabel jl3 = new JLabel("    清空页面：Alt+L");
	JLabel jl4 = new JLabel("    会员激活：F1");
    JLabel jl5 = new JLabel("    会员录入：F2");
    JLabel jl6 = new JLabel("    选择挂单：F3");
    JLabel jl7 = new JLabel("    挂单查询：F4");
    JLabel jl8 = new JLabel("    收银结算：F5");
    JLabel jl9 = new JLabel("    订单查询：F6");
    JLabel jl10 = new JLabel("    退货：F7");
    JLabel jl11 = new JLabel("    班      结：F8");
    JLabel jl12 = new JLabel("    日      结：F9");
    JLabel jl13 = new JLabel("    数据导入：F10");
    JLabel jl14 = new JLabel("    数据导出：F11");
    JLabel jl15 = new JLabel("    帮       助：F12");
    JLabel jl16 = new JLabel("    删       除：Delete");
    JLabel jl17 = new JLabel("    退      出：Esc");
    JLabel jl18 = new JLabel("    确      认：Enter");
    JLabel jl19 = new JLabel("    现金结算：Alt+E");
    JLabel jl20 = new JLabel("    数据恢复：Alt+R");
    JLabel jl21 = new JLabel("    重新打印小票：Alt+C");
    JLabel jl22 = new JLabel("    选择地址：Alt+F");
    JLabel jl23 = new JLabel("    添加地址：Alt+B");
	static boolean visible = false;
	public void init(){
		JPanel jp = new JPanel(new GridLayout(19, 2, 0, 0));
//		jp.add(jl1);
		jp.add(jl4);
		jp.add(jl5);
		jp.add(jl6);
		jp.add(jl7);
		jp.add(jl8);
		jp.add(jl9);
		jp.add(jl10);
		jp.add(jl11);
		jp.add(jl12);
		jp.add(jl13);
		jp.add(jl14);
		jp.add(jl15);
		jp.add(jl16);
		jp.add(jl17);
		jp.add(jl18);
//		jp.add(jl19);
		jp.add(jl20);
		jp.add(jl21);
		jp.add(jl22);
		jp.add(jl23);
		jp.add(jl3);
		jp.add(jl2);
		jf.add(jp);
		
		jf.setUndecorated(true);//必须这样设置不然 setOpacity 抛出异常
		jf.setOpacity(0.9F);//如果不设置 setUndecorated(true) 将会抛出异常
		
		jf.setSize(650,500);
		jf.setLocationRelativeTo(null);// 窗口居中显示
		jf.setResizable(false);// 不可改变大小
//		jf.setUndecorated(true); // 去掉窗口的装饰
		jf.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);// 采用指定的窗口装饰风格//简单对话框风格
		jf.setBackground(Color.BLUE);
//		jf.setDefaultCloseOperation(jf.DISPOSE_ON_CLOSE);
		ImageIcon icon = new ImageIcon(ShowTips.class.getClassLoader()
                .getResource("images/YZF.png"));
        jf.setIconImage(icon.getImage());

		jf.setVisible(true);
//		jf.addKeyListener(new KeyAdapter() {
//			public void keyPressed(KeyEvent e) {
//				if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
//					jf.dispose();
//				}
//			}
//		});
	}
	public static void main(String[] args){
		new ShowTips().init();
	}
	public void exits(){
		jf.dispose();
	}

}
