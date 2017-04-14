package com.sunvsoft.entity;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DoubleScreenView {
    static International international = International.getInstance();
    static int isNeedBottom = 0;
    public JFrame jf = new JFrame("O2O线下收银系统");
    public Object[][] data = null;
    static JTable table;
    private JLabel totalNumberLabel = new JLabel(international.getInternational("totalQuantity"));
    public static JLabel totalNumberLabelChange = new JLabel("");
    private JLabel totalMoneyLabel = new JLabel(international.getInternational("totalAmount"));
    public static JLabel totalMoneyLabelChange = new JLabel("");

//    static Object[] columnNames = { "序号", "条形码", "商品名称", "商品规格", "税制", "计量单位","税金",
//            "单价", "数量", "总价" };
    static Object[] columnNames = { international.getInternational("serialnumber"),international.getInternational("barCode"),
        international.getInternational("goodsName"), international.getInternational("goodsSpecifications"),
        international.getInternational("taxSystem"),international.getInternational("unit"),
        international.getInternational("tax"),international.getInternational("unitPirce"),
        international.getInternational("number"),international.getInternational("total")};
    static JScrollPane djScrollPane = null;

    public DoubleScreenView() {
    }

    /**
     * 初始化JFrame
     */
    @SuppressWarnings("unused")
    public void init() {
        /**
         * 获取屏幕大小
         */
        ImageIcon icon = new ImageIcon(DoubleScreenView.class.getClassLoader()
                .getResource("images/YZF.png"));
        jf.setIconImage(icon.getImage());
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 得到屏幕的尺寸
        int x = screenSize.width; // 宽度
        int y = screenSize.height; // 高度
        JPanel p = new JPanel(new BorderLayout(0, 5));

        totalNumberLabel.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1024));
        totalMoneyLabel.setFont(new Font("宋体", Font.PLAIN, 20* x / 1024));
        totalNumberLabelChange.setFont(new Font("宋体", Font.PLAIN, 20* x / 1024));
        totalMoneyLabelChange.setFont(new Font("宋体", Font.PLAIN, 20* x / 1024));
        
        
        JPanel jpTotal = new JPanel();
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        jp1.add(totalNumberLabel);
        jp1.add(totalNumberLabelChange);
        jp1.add(totalMoneyLabel);
        jp1.add(totalMoneyLabelChange);
        jp1.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        jpTotal.add(jp1);
        jpTotal.setLayout(new FlowLayout(FlowLayout.CENTER, 260, 20));
        

        // JPanel jp3= new JPanel();
        // jp3.add(jp1);

        

        JPanel topCenter = new JPanel();
//      JPanel bigTopCenter = new JPanel();
        topCenter.setLayout(new FlowLayout(FlowLayout.CENTER, 250, 30));
        topCenter.setLayout(new BoxLayout(topCenter, BoxLayout.Y_AXIS));

        // 图片显示
        JPanel jpImage = new JPanel();
//      jpImage.setBounds(200,250,100,150);
        jpImage.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        ImageIcon image = new ImageIcon(DoubleScreenView.class.getClassLoader()
                .getResource("images/YZF.png"));
        JLabel imageLabel = new JLabel(image);
        jpImage.add(imageLabel);
        
        
        // topCenter.add(imageLabel,BorderLayout.SOUTH);

        if (O2OMainMenu.table != null) {
            int rows = O2OMainMenu.table.getRowCount();
            int columns = O2OMainMenu.table.getColumnCount();

            data = new Object[rows][columns];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    data[i][j] = O2OMainMenu.table.getValueAt(i, j);
                }
                // int row = O2OMainMenu.table.getRowCount();
                // int selectRow = table.getSelectedRow();
                // Object[] selectData = data[i];
                // listGoods.add(selectData);
            }
            System.out.println(data.toString());
        }
        @SuppressWarnings("serial")
        DefaultTableModel doubleModel = new DefaultTableModel(data, DoubleScreenView.columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };

        // 当我们改了 ArrayList 数据的数据时 JTable 并不知道需要刷新我们通过 fireTableDataChanged()
        // 事件来通知 JTable 数据变了请刷新表格的显示。
        doubleModel.fireTableDataChanged();
        
        table = new JTable(doubleModel);
        djScrollPane = new JScrollPane(table,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//      Rectangle aRect = new Rectangle();
//      table.scrollRectToVisible(aRect);
        
        
        djScrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent evt) { 
                 if(evt.getAdjustmentType() == AdjustmentEvent.TRACK && isNeedBottom <= 3) {          
                     djScrollPane.getVerticalScrollBar().setValue(djScrollPane.getVerticalScrollBar().getModel().getMaximum() - djScrollPane.getVerticalScrollBar().getModel().getExtent()); 
                     isNeedBottom++; 
                 }
             } 
        });
        topCenter.add(djScrollPane, BorderLayout.CENTER);
        // jf.add(jp1);
//      bigTopCenter.add(topCenter, BorderLayout.NORTH);
        topCenter.add(jpTotal, BorderLayout.CENTER);
        // topCenter.add(jp1);
        topCenter.add(jpImage,BorderLayout.CENTER);
        jf.add(topCenter, BorderLayout.CENTER);
//      jf.add(imageLabel, BorderLayout.SOUTH);
        // jf.add(p, BorderLayout.SOUTH);
        jf.setLocation(1366, 20);
//      jf.setExtendedState(JFrame.MAXIMIZED_HORIZ);
//      jf.setExtendedState(JFrame.MAXIMIZED_VERT);
        jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jf.setSize(x,y);
        jf.pack();
        // jf.setLocationRelativeTo(null);
        // jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // jf.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);//
        // 采用指定的窗口装饰风格//简单对话框风格
        jf.setVisible(true);
        // 键盘事件
    }

    @SuppressWarnings("serial")
    public void clearTable(){
        //清空table
        DefaultTableModel doubleModel = new DefaultTableModel(null, DoubleScreenView.columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        JTable doubletable = new JTable(doubleModel);
        DoubleScreenView.djScrollPane.setViewportView(doubletable);
        
        //清空总金额和总数量
        DoubleScreenView.totalMoneyLabelChange.setText("");
        DoubleScreenView.totalNumberLabelChange.setText("");
    }
    public static void main(String[] args) {
        new DoubleScreenView().init();
    }

}