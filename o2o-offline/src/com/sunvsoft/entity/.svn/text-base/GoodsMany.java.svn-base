package com.sunvsoft.entity;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sunvsoft.accessOnline.ConnectOnlineMethod;
import com.sunvsoft.backup.PropertiesUtil;

public class GoodsMany {
	static International international=International.getInstance();
	static DecimalFormat df = new DecimalFormat("0.00");
	private JFrame jf = new JFrame();
	private JScrollPane jScrollPane;
	static DefaultTableModel model;
	private JTable table;
	@SuppressWarnings("unused")
    private static List<Object[]> mainMenulist = new LinkedList<Object[]>();
//	Object[] columnNames = {"id","商品名称", "商品规格","税制", "单价"};
	//要使用从主页面传过来的数据，所以列要保持和主页面相同
//	static Object[] columnNames = { "序号","商品条码","商品名称", "商品规格", "税制",
//		"计量单位","税金","单价", "数量", "总价"};
static Object[] columnNames = { international.getInternational("choose"),international.getInternational("barCode"),
    international.getInternational("goodsName"), international.getInternational("goodsSpecifications"),
    international.getInternational("taxSystem"),international.getInternational("unit"),
    international.getInternational("tax"),international.getInternational("unitPirce"),
    international.getInternational("number"),international.getInternational("total")};
	static Object[][] data = null;
//	static Object[][] smallData = null; 
	public void init(){
		
		model = new DefaultTableModel(data, columnNames) {
			/**    
             * serialVersionUID:        
             */    
            
            private static final long serialVersionUID = 9072602171542652563L;

            public boolean isCellEditable(int row, int column) {
				return false;
			};

		};
		
		table = new JTable(model);
		//
//		table.getColumnModel().getColumn(0).setMinWidth(0);
//		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setPreferredWidth(60);
//		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setMinWidth(0);
		table.getColumnModel().getColumn(1).setMaxWidth(0);
		table.getColumnModel().getColumn(2).setPreferredWidth(80);
		table.getColumnModel().getColumn(3).setPreferredWidth(80);
		table.getColumnModel().getColumn(4).setPreferredWidth(80);
		table.getColumnModel().getColumn(5).setPreferredWidth(80);
		table.getColumnModel().getColumn(6).setPreferredWidth(80);
		table.getColumnModel().getColumn(7).setPreferredWidth(80);
		table.getColumnModel().getColumn(8).setMinWidth(0);
		table.getColumnModel().getColumn(8).setMaxWidth(0);
		table.getColumnModel().getColumn(9).setMinWidth(0);
		table.getColumnModel().getColumn(9).setMaxWidth(0);
		// table.setCellSelectionEnabled(true);
		// table.setEnabled(false);
		
		
		jScrollPane = new JScrollPane(table,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jf.add(jScrollPane);
		jf.setSize(500, 300);
		jf.setLocationRelativeTo(null);// 窗口居中显示
		jf.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);// 采用指定的窗口装饰风格//简单对话框风格
		ImageIcon icon = new ImageIcon(GoodsMany.class.getClassLoader()
                .getResource("images/YZF.png"));
        jf.setIconImage(icon.getImage());
		jf.setVisible(true);
		table.setRowSelectionInterval(0,0);
//		jf.requestFocus();
		table.addKeyListener(new KeyAdapter() {
			 @SuppressWarnings("static-access")
            public void keyPressed(KeyEvent e) {
				 if(e.getKeyCode()==KeyEvent.VK_SPACE){
				     List<Object[]> listGoods = new LinkedList<Object[]>();
				     int row = O2OMainMenu.table.getRowCount();
				     int selectRow = table.getSelectedRow();
				     Object[] selectData = data[selectRow];
				     PropertiesUtil storeId = new PropertiesUtil();
				     String store_num = storeId.getStoreId();
				     String product_id = table.getValueAt(table.getSelectedRow(), 0).toString();
				     String sql = " select p.product_id,p. NAME,p.bar_code,p.specs,p.tax_system,sp.store_price AS price,g.unit,p.goods_taxes,sp.store_id  "
                             + " FROM es_store_pro sp "
                             + " LEFT JOIN es_product p ON p.product_id = sp.product_id "
                             + " LEFT JOIN es_goods g ON p.goods_id = g.goods_id " 
//                           + " LEFT JOIN es_store s ON sp.store_id = s.store_id "
                             + " WHERE p.product_id = '"
                             + product_id
                             + "' AND sp.store_id = '" + store_num + "'";
				     if(O2OMainMenu.internetPass){
                     MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
                     queryParam.add("sql", sql);
                     String url = PropertiesUtil.getIp()
                             + "/api/shop/goods!getAllProduct.do";
                     ConnectOnlineMethod connect = new ConnectOnlineMethod();
                     List<JSONObject> list1 = new ArrayList<JSONObject>();
                     // List<Object[]> listGoods = new LinkedList();
                     String outPut = null;
                     try {
                         outPut = connect.connectOnline(queryParam,url);
                         JSONArray array = connect.jsonConvertion(outPut);
                         list1 = connect.getJsonObjects(array);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    Integer useableCount = 0;
                    Integer storeCount = 0;
                    if(list1.size() >=1){
                        for(JSONObject object :list1){
                            useableCount = (Integer) object.get("useableCount");
                            storeCount = (Integer) object.get("storeCount");
                        }
                    }
                    if(useableCount<=0 && storeCount<=0){
                        JOptionPane.showMessageDialog(jf, "该商品无库存");
                    }else{
                        //如果主页面有商品，则判断将要添加的小窗口的商品的税制和主页面的税制是否相同
                        if(row > 0){
                            String cont =O2OMainMenu.table.getValueAt(row-1, 4).toString() ;
                            if("全部".equals(cont)){
                                String cont_sys = O2OMainMenu.table.getValueAt(row - 1, 3).toString();
                                if (cont_sys.contains("完税")) {
                                    cont = "完税";
                                } else if(cont_sys.contains("保税")){
                                    cont = "保税";
                                } else{
                                    cont = "直邮";
                                }
                            }
                            String tax_system = (String)selectData[4];
                            //判断当前录入商品的税制
                            if (tax_system.equals("全部")) {
                                String tax_sys = (String)selectData[3];
                                if (tax_sys.contains("完税")) {
                                    tax_system = "完税";
                                } else if(tax_sys.contains("保税")){
                                    tax_system = "保税";
                                } else{
                                    tax_system = "直邮";
                                }
                            }
                            //如果税制相同，则可以添加
                            if(cont.equals(tax_system)){
                                listGoods.add(selectData);
                            }else{
                                O2OMainMenu.totalRefresh();
                                JOptionPane.showMessageDialog(jf,"您只能录入税制相同的产品");
                                return;
                            }
                        }else{
                            listGoods.add(selectData);
                        }
                        }
                    }else{

                        //如果主页面有商品，则判断将要添加的小窗口的商品的税制和主页面的税制是否相同
                        if(row > 0){
                            String cont =O2OMainMenu.table.getValueAt(row-1, 4).toString() ;
                            if("全部".equals(cont)){
                                String cont_sys = O2OMainMenu.table.getValueAt(row - 1, 3).toString();
                                if (cont_sys.contains("完税")) {
                                    cont = "完税";
                                } else if(cont_sys.contains("保税")){
                                    cont = "保税";
                                } else{
                                    cont = "直邮";
                                }
                            }
                            String tax_system = (String)selectData[4];
                            //判断当前录入商品的税制
                            if (tax_system.equals("全部")) {
                                String tax_sys = (String)selectData[3];
                                if (tax_sys.contains("完税")) {
                                    tax_system = "完税";
                                } else if(tax_sys.contains("保税")){
                                    tax_system = "保税";
                                } else{
                                    tax_system = "直邮";
                                }
                            }
                            //如果税制相同，则可以添加
                            if(cont.equals(tax_system)){
                                listGoods.add(selectData);
                            }else{
                                O2OMainMenu.totalRefresh();
                                JOptionPane.showMessageDialog(jf,"您只能录入税制相同的产品");
                                return;
                            }
                        }else{
                            listGoods.add(selectData);
                        }
                        
                    }
                        O2OMainMenu.addTableRow(listGoods.toArray(new Object[0][0]));
                        O2OMainMenu.dropRepeat();
                        O2OMainMenu.totalRefresh();
                        O2OMainMenu.goodsCode.setText("");
                        jf.dispose();
                    }
				 
			if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
				jf.dispose();
			}
			
			}
		});
	}
	public static void main(String[] args){
		new GoodsMany().init();
	}
	
}
