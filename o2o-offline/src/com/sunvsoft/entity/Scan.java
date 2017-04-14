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
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;

import com.google.zxing.BarcodeFormat;
import com.sunvsoft.scan.PrientCode;
import com.sunvsoft.scan.QRCode;

public class Scan {
	JFrame jFrameM = new JFrame("扫描生成二维码");
	JPanel jPanel = new JPanel();
	JPanel jPanel2 = new JPanel();
	JPanel jPanel3 = new JPanel();
	JPanel jPanel4 = new JPanel();
	JPanel jPanel5 = new JPanel();
	JPanel jPanel6 = new JPanel();
	JPanel jPanel7 = new JPanel();
	JLabel jLabel = new JLabel("扫描生成二维码");
	JLabel barCodeLabel = new JLabel("条码");
	JTextField barCodeField ;
	public Scan() {
		super();
	}
	@SuppressWarnings("static-access")
    public void init() {
	    barCodeField = new JTextField(10);
		jLabel.setFont(new Font("宋体", Font.PLAIN, 26));
		jLabel.setForeground(Color.red);
		JButton activationButton = new JButton("确定");
		/**
		 * 	字体	
		 */
		barCodeLabel.setFont(new Font("黑体", Font.PLAIN, 16));
		barCodeLabel.setForeground(Color.black);
		
		activationButton.setFont(new Font("黑体", Font.PLAIN, 16));
		activationButton.setForeground(Color.black);
		
		/**
		 * 位置
		 */
		jPanel4.setLayout(null);
		jLabel.setBounds(150, 30, 230, 30);
		barCodeLabel.setBounds(100, 100, 230, 30);
		barCodeField.setBounds(140, 100, 230, 30);
		activationButton.setBounds(210, 200, 80, 30);
		/**
		 * 布局
		 */
		jPanel4.add(jLabel);
		jPanel4.add(barCodeLabel);
		jPanel4.add(barCodeField);
		jPanel4.add(activationButton);
		
		jPanel.setLayout(new BorderLayout());
		jPanel.add(jPanel4, BorderLayout.CENTER);
		jFrameM.add(jPanel);
		
		jFrameM.setSize(500, 300);
		jFrameM.setLocationRelativeTo(null);// 窗口居中显示
		jFrameM.setResizable(false);// 不可改变大小
		jFrameM.setUndecorated(true); // 去掉窗口的装饰
		jFrameM.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);//采用指定的窗口装饰风格//简单对话框风格
		jFrameM.setDefaultCloseOperation(jFrameM.DO_NOTHING_ON_CLOSE);
		ImageIcon icon = new ImageIcon(Scan.class.getClassLoader()
                .getResource("images/YZF.png"));
        jFrameM.setIconImage(icon.getImage());
		jFrameM.setVisible(true);
		// 添加监听事件
				activationButton.addActionListener(new activationListener());
				barCodeField.addKeyListener(new keyListener());
		jFrameM.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				jFrameM.dispose();
			}
		});
		
	}

	
	class resetListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
		    barCodeField.setText("");
		    barCodeField.requestFocus();
		}
		
	}
	public class activationListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
		    doScan();
		}

	}
	public class keyListener implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
				 jFrameM.dispose();
			 }
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
			    String barCode = barCodeField.getText();
//				String barCode = "438.8";
			    String reg = "^[0-9]*$";//只能为数字的java正则表达式
			    if(barCode.equals("") || barCode.matches(reg)){
			        JOptionPane.showMessageDialog(jFrameM, "输入格式不正确");
                    barCodeField.setText("");
			    }
			    doScan();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}
		
	}
	public void doScan(){

//		String code = barCodeField.getText();
		String code = "438.2";
//		if(!O2OMainMenu.internetPass){
//		    
//		ResultSet rs = new SetSql().setSql("select product_id,goods_id,name,whprice_ru from es_product where bar_code like '"
//                + code + "'");
//		try {
//			if(!rs.next()){
//				JOptionPane.showMessageDialog(jFrameM, "商品不存在");
//				jFrameM.dispose();
//			}
//			else{
//			    String goods_id = rs.getString("goods_id");
//			    String product_id = rs.getString("product_id");
//			    QRCode test = new QRCode();
//			    String filePostfix="png";
//		        File file = new File("Code."+filePostfix);
////		        test.encode(PropertiesUtil.getIp() +"/goods-"+goods_id+".html?"+product_id, file,filePostfix, BarcodeFormat.QR_CODE, 200, 200, null);
//		        test.encode(code, file,filePostfix, BarcodeFormat.QR_CODE, 200, 200, null);
//		        PrientCode p = new PrientCode();
//		        p.goodsName = rs.getString("name");
//		        p.goodsPrice = rs.getString("whprice_ru");
//		        p.start();
//		        jFrameM.dispose();
//		        O2OMainMenu.goodsCode.setText("");
//			}
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//	
//	}else{
//	    //通过线上检索商品，获取商品信息，生成二维码
//	    MultivaluedMap queryParam = new MultivaluedMapImpl();
//	    String sql = "select * from es_product where bar_code like '"
//                + code + "'";
////	    queryParam.add("bar_code",code);
//	    queryParam.add("sql",sql);
//	    String url = PropertiesUtil.getIp() + "/api/shop/goods!getAllProduct.do";
//	    ConnectOnlineMethod connect = new ConnectOnlineMethod();
//	    try {
//            String outPut = connect.connectOnline(queryParam, url);
//            if(!outPut.equals("") && outPut!= null){
//                JSONArray array = connect.jsonConvertion(outPut);
//                if(!array.equals("")){
//                JSONObject object = connect.getJsonObject(array);
//                String bar_code =  object.getString("bar_code");
//                Integer goods_id = (Integer) object.get("goods_id");
//                Integer product_id =  (Integer) object.get("product_id");
//                if(bar_code.equals(code)){
//                    QRCode test = new QRCode();
//                    String filePostfix="png";
//                    File file = new File("Code."+filePostfix);
////                    test.encode(PropertiesUtil.getIp() +"/goods-"+goods_id+".html?"+product_id, file,filePostfix, BarcodeFormat.QR_CODE, 200, 200, null);
//                    test.encode(code, file,filePostfix, BarcodeFormat.QR_CODE, 200, 200, null);
//                    PrientCode p = new PrientCode();
//                    p.goodsName = (String) object.get("name");
//                    p.goodsPrice =  object.get("whprice_ru").toString();
//                    p.start();
//                    jFrameM.dispose();
//                }
//            }else{
//                JOptionPane.showMessageDialog(jFrameM, "商品不存在");
//                jFrameM.dispose();
//            }}
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//	    
//	}
		QRCode test = new QRCode();
        String filePostfix="png";
        File file = new File("Code."+filePostfix);
//      test.encode(PropertiesUtil.getIp() +"/goods-"+goods_id+".html?"+product_id, file,filePostfix, BarcodeFormat.QR_CODE, 200, 200, null);
	    test.encode(code, file,filePostfix, BarcodeFormat.QR_CODE, 200, 200, null);
	    PrientCode p = new PrientCode();
//      p.goodsName = (String) object.get("name");
//      p.goodsPrice =  object.get("whprice_ru").toString();
        p.start();
	}
	public static void main(String[] args){
	    new Scan().init();
	}
}
