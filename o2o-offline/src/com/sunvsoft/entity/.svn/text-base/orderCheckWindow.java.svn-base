package com.sunvsoft.entity;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sunvsoft.accessOnline.ConnectOnlineMethod;
import com.sunvsoft.backup.PropertiesUtil;
import com.sunvsoft.sqlset.SetSql;

public class orderCheckWindow {
    /*private static orderCheckWindow oCheckWindow = null;
    private orderCheckWindow(){}
//    private static final orderCheckWindow oCheckWindow = new orderCheckWindow();
    public static orderCheckWindow getInstance(){
        if(oCheckWindow == null){
            oCheckWindow = new orderCheckWindow();
        }
        return oCheckWindow;
    }*/
    JFrame jf = new JFrame();
    Object[] columnNames = { "订单编号", "操作员", "生成时间", "产品总数", "日期"};
    DefaultTableModel model;
    JTable table;
    JScrollPane jScrollPane;
    JLabel jl = new JLabel("请按“↑”或“↓”键选择订单，按空格键确认");
    JPanel jp = new JPanel();
    JPanel jp1 = new JPanel();
    JPanel jp2 = new JPanel();
    Object[][] data = null;
    public O2OMainMenu omm;
	@SuppressWarnings("static-access")
    public void init(Object[][] data) {
	    
	    jf.setAlwaysOnTop(true);
		jl.setFont(new Font("黑体", Font.PLAIN, 20));
		jl.setHorizontalAlignment(JTextField.CENTER);// 居中显示
		model = new DefaultTableModel(data, columnNames) {
			/**    
             * serialVersionUID:        
             */    
            
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row,
					int column) {
				return false;
			};

		};
		
		table = new JTable(model);

		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(170);
		table.getColumnModel().getColumn(1).setPreferredWidth(110);
		table.getColumnModel().getColumn(2).setPreferredWidth(110);
		table.getColumnModel().getColumn(3).setPreferredWidth(110);
		table.getColumnModel().getColumn(4).setPreferredWidth(100);
		/**
		 * 使table数据居中显示
		 */
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, r);
		// table键盘监听
		table.addKeyListener(new ModelChoice());

		jScrollPane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		jp1.setBounds(0, 0, 0, 10);
		jp2.setBounds(0, 0, 0, 10);
		jp.setLayout(new BorderLayout());
		jp.add(jp1, BorderLayout.NORTH);
		jp.add(jl, BorderLayout.CENTER);
		jp.add(jp2, BorderLayout.SOUTH);

		jf.setLayout(new BorderLayout());
		jf.add(jp, BorderLayout.NORTH);
		jf.add(jScrollPane, BorderLayout.CENTER);

		jf.setSize(800, 450);
		jf.setLocationRelativeTo(null);// 窗口居中显示
		ImageIcon icon = new ImageIcon(orderCheckWindow.class.getClassLoader()
                .getResource("images/YZF.png"));
        jf.setIconImage(icon.getImage());
		jf.setResizable(false);// 不可改变大小
		//加上这个修饰的话，点击订单查询时窗口会消失才
//		jf.setUndecorated(true); // 去掉窗口的装饰 
		jf.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);// 采用指定的窗口装饰风格//简单对话框风格
		jf.setDefaultCloseOperation(jf.DISPOSE_ON_CLOSE);
		jf.setVisible(true);
	}

	public class ModelChoice implements KeyListener {
		public void keyTyped(KeyEvent e) {
		}

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				O2OMainMenu.documentNumber.setText(""+table.getValueAt(table.getSelectedRow(), 0));
				String sql  = "select cardNumber,order_id from  es_order o where o.`status` in ("+OrderStatus.ORDER_COMPLETE+","+OrderStatus.ORDER_PAY_CONFIRM+","+OrderStatus.SHIP_PARTIAL_CANCEL+") and o.sn = '"
				        + table.getValueAt(table.getSelectedRow(), 0)
				        + "'";
				ResultSet rs2 = new SetSql()
						.setSql(sql);
				ResultSet rs = new SetSql().setSql(sql);
				String sn = O2OMainMenu.documentNumber.getText().toString();
				try {
					if (rs2.next()) {
						try {
							List<Object[]> list = new LinkedList<Object[]>();
							while (rs.next()) {
								long order_ids = rs.getLong("order_id");
								O2OMainMenu.memberNumber.setText(rs.getString("cardNumber"));
								String s = "select o.*,(o.num*o.price) as price_amount from es_order_items o where o.order_id = "
                                        + order_ids
                                        + " and o.state = 101  ";
								ResultSet rss = new SetSql()
			 							.setSql("select o.*,(o.num*o.price) as price_amount from es_order_items o where o.order_id = "
		                                        + order_ids
		                                        + " and o.state = 101  ");
								while (rss.next()) {
									// 改成你的列名
									Object[] objects = new Object[] {
											rss.getInt("product_id"),
											rss.getString("bar_code"),
											rss.getString("name"),
											rss.getString("spec_value"),
											OrderStatus.getTaxText(rss.getInt("tax_system")),
											rss.getString("unit"),
											rss.getDouble("taxes"),
											"" + rss.getDouble("price"),
											rss.getInt("num"),
											rss.getDouble("price_amount")+rss.getDouble("taxes")*rss.getInt("num"),
									};
									list.add(objects);
								}
							}
							data = list.toArray(new Object[0][0]);
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
						
						O2OMainMenu.orderCheckShow(data);
						jf.dispose();
						RepeatPrient.table = table;
		    		    RepeatPrient.sn = sn;
						String dataNow = new timeToDates().getTimeToDates(System.currentTimeMillis());
						String dataOrder = table.getValueAt(table.getSelectedRow(), 4)+"";
						O2OMainMenu.rejectable = true;
						if(dataNow.equals(dataOrder)){
							O2OMainMenu.rejectedDay = true;
						}
						JOptionPane.showMessageDialog(jf, "订单查询成功");
					} else {
						JOptionPane.showMessageDialog(jf, "无记录，请核对订单号和会员号");
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}

		public void keyReleased(KeyEvent e) {
		}
	}
}
