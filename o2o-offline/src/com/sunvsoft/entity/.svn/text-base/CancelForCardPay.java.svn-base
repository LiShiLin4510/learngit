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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import com.sunvsoft.szpos.PosinfSZ;

public class CancelForCardPay {
	@SuppressWarnings("unused")
    private JFrame jf;
	JFrame jFrameM = new JFrame();
	JPanel jPanel = new JPanel();
	JPanel jPanel2 = new JPanel();
	JPanel jPanel3 = new JPanel();
	JPanel jPanel4 = new JPanel();
	JPanel jPanel5 = new JPanel();
	JPanel jPanel6 = new JPanel();
	JPanel jPanel7 = new JPanel();
	JLabel jLabel = new JLabel("刷卡撤销");
	JLabel accountNumLable = new JLabel("凭证号");
	JTextField accountNumField ;
	public O2OMainMenu mm;
	
	
	@SuppressWarnings("serial")
    public void init() {
		
		accountNumField = new JTextField();
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
		jLabel.setFont(new Font("宋体", Font.PLAIN, 26));
		jLabel.setForeground(Color.red);
		JButton activationButton = new JButton("确定");
		JButton activationButtonCancel = new JButton("取消");
		/**
		 * 	字体	
		 */
		accountNumLable.setFont(new Font("黑体", Font.PLAIN, 16));
		accountNumLable.setForeground(Color.black);
		
		activationButton.setFont(new Font("黑体", Font.PLAIN, 16));
		activationButton.setForeground(Color.black);
		activationButtonCancel.setFont(new Font("黑体", Font.PLAIN, 16));
		activationButtonCancel.setForeground(Color.black);
		
		/**
		 * 位置
		 */
		jPanel4.setLayout(null);
		jLabel.setBounds(200, 30, 230, 30);
		accountNumLable.setBounds(80, 100, 230, 30);
		accountNumField.setBounds(140, 100, 230, 30);
		activationButton.setBounds(140, 200, 80, 30);
		activationButtonCancel.setBounds(275, 200, 80, 30);
		/**
		 * 布局
		 */
		jPanel4.add(jLabel);
		jPanel4.add(accountNumLable);
		jPanel4.add(accountNumField);
		jPanel4.add(activationButton);
		jPanel4.add(activationButtonCancel);
		
		jPanel.setLayout(new BorderLayout());
		jPanel.add(jPanel4, BorderLayout.CENTER);
		jFrameM.add(jPanel);
		
		jFrameM.setSize(500, 300);
		jFrameM.setLocationRelativeTo(null);// 窗口居中显示
		jFrameM.setResizable(false);// 不可改变大小
		jFrameM.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		jFrameM.setVisible(true);
		// 添加监听事件
				activationButton.addActionListener(new activationListener());
				activationButtonCancel.addActionListener(new activationCancelListener());
				accountNumField.addKeyListener(new keyListener());
		jFrameM.addWindowListener(new WindowAdapter() {
			 public void windowClosing(WindowEvent e) {
				 jFrameM.dispose(); 
			 }
		});
}
	public class activationListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			memberFounction();
			
		}
		
	}
	
	public class activationCancelListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			jFrameM.dispose();
		}
		
	}
	public class keyListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				memberFounction();
			}
			if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
				jFrameM.dispose();
			}
				
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}
		
	}
	public void memberFounction(){
		String[] resultStr = new PosinfSZ().doConsumption(new PosAndCashNoButton().getCancelString(accountNumField.getText()));
		if("00".equals(resultStr[0])){
//			this.cardAmount = Double.parseDouble(resultStr[4])/100;
//			this.refNo = resultStr[11];
//			this.cerNo = resultStr[3];
//			doPay(jf, CashNoButton.this);
			jFrameM.dispose();
		}else{
			JOptionPane.showMessageDialog(jFrameM, "刷卡失败，请重试！");
			jFrameM.dispose();
		}
	}
	public static void main(String[] args){
		new CancelForCardPay().init();
	}
}
