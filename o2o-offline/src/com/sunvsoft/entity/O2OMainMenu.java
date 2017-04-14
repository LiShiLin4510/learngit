package com.sunvsoft.entity;

import java.awt.AWTEvent;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xvolks.jnative.exceptions.NativeException;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sunvsoft.accessOnline.ConnectOnlineMethod;
import com.sunvsoft.backup.DoBackup;
import com.sunvsoft.backup.PropertiesUtil;
import com.sunvsoft.esc_pos.ClientDisplay;
import com.sunvsoft.esc_pos.HSCustomerShow;
import com.sunvsoft.listener.bnMemberListener;
import com.sunvsoft.sqlset.SetSql;
import com.sunvsoft.util.Sequence;

public class O2OMainMenu {
    static International international = International.getInstance();
    static boolean internetPass = false;
    String baudRate = null;
    String displayRate = null;
    @SuppressWarnings("unused")
    private Boolean a = true;
    @SuppressWarnings("unused")
    private Boolean b = true;
    static DecimalFormat df = new DecimalFormat("0.00");
    public static JFrame jf = new JFrame("O2O线下收银系统 —— 联机");
    private static JScrollPane jScrollPane;
    static DefaultTableModel model;
    static DefaultTableModel model2;
    private static JButton bnMember = new JButton(international.getInternational("activateMember"));
    private static JButton bnMemberInput = new JButton(international.getInternational("inputMembers"));
    private static JButton bnPendingOrder = new JButton(international.getInternational("chooseHang"));
    private static JButton bnPendingOrderQuery = new JButton(international.getInternational("queryHangOrder"));
    private static JButton bnPos = new JButton(international.getInternational("pay"));
    private static JButton orderCheck = new JButton(international.getInternational("queryAllOrder"));
    private static JButton rejected = new JButton(international.getInternational("chooseReturn"));
    private static JButton balanceOfWork = new JButton(international.getInternational("classPaid"));
    private static JButton balanceOfDay = new JButton(international.getInternational("dayPaid"));
    private static JButton dataImport = new JButton(international.getInternational("dataImport"));
    private static JButton dataExport = new JButton(international.getInternational("dataExport"));
    private static JButton bnHelper = new JButton(international.getInternational("systemHelp"));
    private static JButton bnDelete = new JButton(international.getInternational("delete"));
    private static JButton bnExit = new JButton(international.getInternational("esc"));
    private static JButton bnCashier = new JButton("现金结算 Alt+E");
    private static JButton dataRecovery = new JButton("数据恢复   ");
    private static JButton saleStatistics = new JButton("销售统计   ");
    private JButton createCode = new JButton("生成二维码");
    private JButton addAddress = new JButton("添加地址");
    private JButton detailAddress = new JButton("地址详情");
    private JLabel documentNumberLabel = new JLabel(international.getInternational("documentNumber"));
    private JLabel operatorLabel = new JLabel(international.getInternational("operator"));
    private JLabel datetimeLabel = new JLabel(international.getInternational("date"));
    private JLabel memberNumberLabel = new JLabel(international.getInternational("memberCard"));
    private JLabel totalNumberLabel = new JLabel(international.getInternational("totalQuantity"));
    public static JLabel totalNumberLabelChange = new JLabel("");
    private JLabel totalMoneyLabel = new JLabel(international.getInternational("totalAmount"));
    public static JLabel totalMoneyLabelChange = new JLabel("");
    private JLabel goodsCodeLabel = new JLabel(international.getInternational("inputGoods"));
    static JLabel addressLabel = new JLabel(international.getInternational("harvestAddress"));
    static JLabel addressDetail = new JLabel();
    static JTextField documentNumber;
    static JTextField operator;// 原：15
    private JTextField datetime;// 日期800*600:15
    static JTextField memberNumber;
    static JTextField addrId; // 存放地址id
    public static JTextField goodsCode;
    private static JTextField goodsNumber;
    
    static Object[] columnNames = { international.getInternational("choose"),international.getInternational("barCode"),
        international.getInternational("goodsName"), international.getInternational("goodsSpecifications"),
        international.getInternational("taxSystem"),international.getInternational("unit"),
        international.getInternational("tax"),international.getInternational("unitPirce"),
        international.getInternational("number"),international.getInternational("total")};
    static Object[] columnNames2 = { "多选","条形码", "商品名称", "商品规格", "税制",
            "计量单位","税金","单价", "数量", "总价"};
    static Object[][] data = null;
    Object[][] dataMember = null;
    Object[][] dataMember1 = null;
    Object[][] data2 = null;
    Object[][] data3 = null;
    static JTable table;
    int tableRow;
    public static boolean rejectable = false;
    public boolean ifAddGoods = false;
    public boolean ifAdded = false;
    static boolean utrue = false;// 班结导出判断
    public int goodsLength = 0;
    public static String salesman = null;
    static String panfu = null;
    int[] selections = new int[0];
    static int allCount = 0;
    static double allPrice = 0;
    public static boolean rejectedDay = false;
    // 班结变量
    static long timeNowDay = 0;
    static String dateNowDay = "";
    static int classesDay = 0;
    static Object[][] data4Day = null;

    /**
     * 初始化JFrame
     */
    public void init() {
        /**
         * 获取屏幕大小
         */
        if(internetPass){
            jf.setTitle("O2O线下收银系统 —— 联机");
        }else{
            jf.setTitle("O2O线下收银系统 —— 脱机");
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 得到屏幕的尺寸
        int x = screenSize.width; // 宽度
        int y = screenSize.height; // 高度
        System.out.println(x + "*" + y);
        documentNumber = new JTextField(30 * x / 1360);
        operator = new JTextField(10 * x / 1360);// 原：15
        datetime = new JTextField(15 * x / 1360);// 日期800*600:15
        memberNumber = new JTextField(20 * x / 1360);
        addrId = new JTextField(20 * x / 1360);
        goodsCode = new JTextField(25 * x / 1360);
        goodsNumber = new JTextField(15 * x / 1360);
        documentNumberLabel.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        documentNumber.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        operatorLabel.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        operator.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        datetimeLabel.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        datetime.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        memberNumberLabel.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        memberNumber.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        bnHelper.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        bnPos.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        bnCashier.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        bnMember.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        bnDelete.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        saleStatistics.setFont(new Font("宋体",Font.PLAIN,20*x/1360));
        dataRecovery.setFont(new Font("宋体",Font.PLAIN,20*x/1360));
        bnPendingOrder.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        bnPendingOrderQuery.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        balanceOfDay.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        balanceOfWork.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        dataImport.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        dataExport.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        orderCheck.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        rejected.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        bnExit.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        bnMemberInput.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        dataRecovery.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        totalNumberLabel.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        totalMoneyLabel.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        goodsCodeLabel.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        totalNumberLabelChange.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        addressLabel.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        addressDetail.setFont(new Font("宋体", Font.PLAIN, 20 * x / 1360));
        /**
         * 判断角色
         */
        if (!O2OMainMenu.internetPass) { // 在不联网状态下判断用户所属角色权限
            ResultSet rsForRole = new SetSql()
                    .setSql("select realname as rolename from es_user where username = '"
                            + salesman + "'");
            String role = null;
            try {
                rsForRole.next();
                role = (rsForRole.getString("rolename"));
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            switch (role) {
            case "admin":
                break;
            case "店长":
                bnPos.setEnabled(false);
                bnCashier.setEnabled(false);
                bnPendingOrder.setEnabled(false);
                bnPendingOrderQuery.setEnabled(false);
                balanceOfWork.setEnabled(false);
                dataRecovery.setEnabled(false);
                break;
            case "收银员":
                balanceOfDay.setEnabled(false);
                dataImport.setEnabled(false);
                dataExport.setEnabled(false);
                bnMember.setEnabled(false);
                dataRecovery.setEnabled(false);
                break;
            case "loader":
                bnPos.setEnabled(false);
                bnCashier.setEnabled(false);
                bnPendingOrder.setEnabled(false);
                bnPendingOrderQuery.setEnabled(false);
                balanceOfWork.setEnabled(false);
                break;
            case "operator":
                balanceOfDay.setEnabled(false);
                dataRecovery.setEnabled(false);
                dataImport.setEnabled(false);
                dataExport.setEnabled(false);
                break;
            case "init":
                bnPos.setEnabled(false);
                bnCashier.setEnabled(false);
                bnMember.setEnabled(false);
                bnDelete.setEnabled(false);
                saleStatistics.setEnabled(false);
                dataRecovery.setEnabled(false);
                bnPendingOrder.setEnabled(false);
                bnPendingOrderQuery.setEnabled(false);
                balanceOfDay.setEnabled(false);
                balanceOfWork.setEnabled(false);
                dataExport.setEnabled(false);
                orderCheck.setEnabled(false);
                rejected.setEnabled(false);
                bnMemberInput.setEnabled(false);
                dataRecovery.setEnabled(false);
                break;
            }
        } else { // 联网状态下角色权限
            bnMember.setEnabled(false);
            String role = null;
            MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
            queryParam.add("username", salesman);
            String url = PropertiesUtil.getIp() + "/api/shop/member!getRole.do";
            ConnectOnlineMethod connect = new ConnectOnlineMethod();
            try {
                String outPut = connect.connectOnline(queryParam, url);
                JSONArray array = connect.jsonConvertion(outPut);
                if (array.equals("")) {
                }
                JSONObject jsonObject = connect.getJsonObject(array);
                role = jsonObject.getString("role_name");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            switch (role) {
            case "admin":
                break;
            case "店长":
                bnPos.setEnabled(false);
                bnCashier.setEnabled(false);
                bnPendingOrder.setEnabled(false);
                bnPendingOrderQuery.setEnabled(false);
                balanceOfWork.setEnabled(false);
                break;
            case "收银员":
                balanceOfDay.setEnabled(false);
                dataImport.setEnabled(false);
                dataExport.setEnabled(false);
                bnMember.setEnabled(false);
                dataRecovery.setEnabled(false);
                break;
            case "init":
                bnPos.setEnabled(false);
                bnCashier.setEnabled(false);
                bnMember.setEnabled(false);
                bnDelete.setEnabled(false);
                saleStatistics.setEnabled(false);
                dataRecovery.setEnabled(false);
                bnPendingOrder.setEnabled(false);
                bnPendingOrderQuery.setEnabled(false);
                balanceOfDay.setEnabled(false);
                balanceOfWork.setEnabled(false);
                dataExport.setEnabled(false);
                orderCheck.setEnabled(false);
                rejected.setEnabled(false);
                bnMemberInput.setEnabled(false);
                dataRecovery.setEnabled(false);
                break;
            }
        }
        operator.setEditable(false);
        operator.setText(salesman);
        documentNumber.setEditable(false);
        addressLabel.setVisible(false);
        addressDetail.setVisible(false);
        /**
         * 显示日期
         */
        Date d = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        datetime.setText(df.format(d));
        datetime.setEditable(false);

        // O2O

        JLabel jLabel = new JLabel("  O 2 O  ");
        jLabel.setFont(new Font("宋体", Font.PLAIN, 66));
        jLabel.setForeground(Color.GREEN);

        totalMoneyLabelChange.setFont(new Font("宋体", Font.PLAIN, 40));
        // center
        JPanel top = new JPanel();
        top.setLayout(new FlowLayout(FlowLayout.CENTER, 35, 20));
        top.add(documentNumberLabel);
        top.add(documentNumber);
        top.add(operatorLabel);
        top.add(operator);
        top.add(datetimeLabel);
        top.add(datetime);
        top.add(memberNumberLabel);
        top.add(memberNumber);
        JPanel top1 = new JPanel();
        top1.setLayout(new FlowLayout(FlowLayout.LEFT,50,0));
        top1.add(addressLabel);
        top1.add(addressDetail);
        // top.add(addrId);
        memberNumber.setEditable(false);
        // south
        JPanel topSouth1 = new JPanel();
        topSouth1.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        topSouth1.add(totalNumberLabel);
        topSouth1.add(totalNumberLabelChange);
        JPanel topSouth2 = new JPanel();
        topSouth2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 1));
        topSouth2.add(goodsCodeLabel);
        goodsCode.setOpaque(false);// 设置控件透明
        topSouth2.add(goodsCode);
        topSouth2.add(goodsNumber);
        JPanel topSouth3 = new JPanel();
        topSouth3.setLayout(new FlowLayout(FlowLayout.CENTER, 31, 0));
        topSouth3.add(bnMember);
        topSouth3.add(bnMemberInput);
        // topSouth3.add(bnCashier);
        // topSouth3.add(bnDelete);
        topSouth3.add(bnPendingOrder);
        topSouth3.add(bnPendingOrderQuery);
        topSouth3.add(bnPos);
        topSouth3.add(orderCheck);
        topSouth3.add(rejected);
        topSouth3.add(dataRecovery);
        JPanel topSouth4 = new JPanel();
        topSouth4.setLayout(new FlowLayout(FlowLayout.CENTER, 31, 0));
        topSouth4.add(balanceOfWork);
        topSouth4.add(balanceOfDay);
        topSouth4.add(dataImport);
        topSouth4.add(dataExport);
        // topSouth4.add(dataRecovery);
        topSouth4.add(bnHelper);
        topSouth4.add(bnDelete);
        topSouth4.add(saleStatistics);
        // topSouth4.add(orderCheck);
        // topSouth4.add(rejected);
        // topSouth4.add(cardBack);
        topSouth4.add(bnExit);

        JPanel jp1 = new JPanel();
        jp1.setLayout(new BorderLayout());
        jp1.add(topSouth1, BorderLayout.CENTER);
        jp1.add(topSouth2, BorderLayout.SOUTH);

        JPanel jp2 = new JPanel();
        jp2.add(totalMoneyLabel);
        jp2.add(totalMoneyLabelChange);

        JPanel jp = new JPanel();
        jp.add(jp1);
        jp.add(jp2);
        // cardBack.setBounds(0, 0, 0, 0);
        // 限定长度
        goodsNumber.setDocument(new PlainDocument() {
            /**    
             * serialVersionUID:        
             */    
            
            private static final long serialVersionUID = 1L;
            int MAX_LENGTH = 8;

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
        memberNumber.setDocument(new PlainDocument() {
            /**    
             * serialVersionUID:        
             */    
            
            private static final long serialVersionUID = 1L;
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
        goodsCode.setDocument(new PlainDocument() {
            /**    
             * serialVersionUID:        
             */    
            
            private static final long serialVersionUID = 1L;
            int MAX_LENGTH = 50;

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
        dataRecovery.addActionListener(new dataRecoveryListener());
        bnMemberInput.addActionListener(new bnMemberInputListener());
        documentNumber.addMouseListener(new gainFocusListener());
        operator.addMouseListener(new gainFocusListener());
        datetime.addMouseListener(new gainFocusListener());
        memberNumber.addMouseListener(new gainFocusListener());
        orderCheck.setMnemonic(KeyEvent.VK_6);
        rejected.setMnemonic(KeyEvent.VK_7);
        dataExport.addActionListener(new dataOutListener());
        dataExport.setMnemonic(KeyEvent.VK_F11);
        dataImport.addActionListener(new dataInListener());
        dataImport.setMnemonic(KeyEvent.VK_F10);
        balanceOfWork.addActionListener(new operatorAccount());
        balanceOfWork.setMnemonic(KeyEvent.VK_8);
        balanceOfDay.addActionListener(new dayAccount());
        balanceOfDay.setMnemonic(KeyEvent.VK_F9);
        bnExit.addActionListener(new exitbn());
        bnExit.registerKeyboardAction(new exitbn(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, KeyEvent.CTRL_MASK),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
        bnExit.setMnemonic(KeyEvent.VK_0);
        bnHelper.setMnemonic(KeyEvent.VK_F12);
        bnHelper.addActionListener(new Helper());
        bnPos.addActionListener(new PosChashier());
        bnPos.setMnemonic(KeyEvent.VK_5);
        bnCashier.addActionListener(new chashier());
        bnCashier.setMnemonic(KeyEvent.VK_E);
        bnPendingOrder.addActionListener(new Bt2Listener());
        bnPendingOrder.setMnemonic(KeyEvent.VK_3);
        bnPendingOrderQuery.addActionListener(new Bt3Listener());
        bnPendingOrderQuery.setMnemonic(KeyEvent.VK_4);
        bnMember.addActionListener(new bnMemberListener());
        bnMember.setMnemonic(KeyEvent.VK_1);
        bnDelete.setMnemonic(KeyEvent.VK_D);
        saleStatistics.setMnemonic(KeyEvent.VK_S);
        dataRecovery.setMnemonic(KeyEvent.VK_R);
        bnMemberInput.setMnemonic(KeyEvent.VK_2);
        dataRecovery.setMnemonic(KeyEvent.VK_R);
        createCode.setMnemonic(KeyEvent.VK_C);
//      addAddress.setMnemonic(KeyEvent.VK_B);
        detailAddress.setMnemonic(KeyEvent.VK_F);
        detailAddress.addActionListener(new DetailAddressListener());
        addAddress.addActionListener(new AddAddress());
        orderCheck.addActionListener(new orderCheckListener());
        rejected.addActionListener(new rejectedListener());
        goodsCode.addKeyListener(new goodsCodeKeyListener());
        goodsNumber.getDocument()
                .addDocumentListener(new goodsNumberListener());
        goodsNumber.addKeyListener(new goodsNumberKeyListener());
        goodsNumber.setVisible(false);
        goodsCode.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Robot rb = null;
                try {
                    rb = new Robot();
                } catch (AWTException e1) {
                    e1.printStackTrace();
                }
                rb.keyRelease(KeyEvent.VK_CONTROL);
            }
        });
        goodsNumber.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Robot rb = null;
                try {
                    rb = new Robot();
                } catch (AWTException e1) {
                    e1.printStackTrace();
                }
                rb.keyRelease(KeyEvent.VK_CONTROL);
            }
        });
        JPanel p = new JPanel(new BorderLayout(0, 5));
        JPanel p1 = new JPanel(new GridLayout(2, 1, 30, 10));
        JPanel p2 = new JPanel();
        p.add(jp, BorderLayout.NORTH);
        p1.add(topSouth3);
        p1.add(topSouth4);
        p2.setBounds(0, 0, 0, 10);
        p.add(p1, BorderLayout.CENTER);
        p.add(p2, BorderLayout.SOUTH);

        model = new DefaultTableModel(data, columnNames) {
            /**    
             * serialVersionUID:        
             */    
            
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            };

        };
        table = new JTable(model);
        // table2 = new JTable(model2);
        jScrollPane = new JScrollPane(table,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        table.getColumnModel().getColumn(1).setPreferredWidth(130);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);
        table.getColumnModel().getColumn(4).setPreferredWidth(80);
        table.getColumnModel().getColumn(5).setPreferredWidth(80);
        table.getColumnModel().getColumn(6).setPreferredWidth(80);
        table.getColumnModel().getColumn(7).setPreferredWidth(80);
        table.getColumnModel().getColumn(8).setPreferredWidth(80);
        table.getColumnModel().getColumn(9).setPreferredWidth(80);
        // 删除按钮监听器
        bnDelete.addActionListener(new BtListener());
        saleStatistics.addActionListener(new SaleListener());
        /**
         * 创建复选框
         */
        table.getColumnModel().getColumn(0)
                .setCellRenderer(new TableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table, Object value, boolean isSelected,
                            boolean hasFocus, int row, int column) {
                        // 创建用于返回的渲染组件
                        JCheckBox ck = new JCheckBox();
                        // 使具有焦点的行对应复选框选中
                        ck.setSelected(isSelected);
                        // 使复选框在单元格内居中显示
                        ck.setHorizontalAlignment((int) 0.5f);
                        ck.setBackground(Color.white);
                        return ck;
                    }
                });
        table.setBackground(Color.white);
        /**
         * 禁用鼠标双击事件
         */

        /**
         * 使table数据居中显示
         */
        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, r);
        table.addMouseListener(new tableListener());

        ImageIcon icon = new ImageIcon(UserLogin.class.getClassLoader().getResource("images/YZF.png"));
        jf.setIconImage(icon.getImage());
        JPanel topCenter = new JPanel();
        topCenter.setLayout(new BoxLayout(topCenter, BoxLayout.PAGE_AXIS));
        topCenter.add(top, BorderLayout.CENTER);
        topCenter.add(top1,BorderLayout.CENTER);
        topCenter.add(jScrollPane, BorderLayout.CENTER);

        jf.add(topCenter, BorderLayout.CENTER);
        jf.add(p, BorderLayout.SOUTH);
        jf.pack();
        // jf.setSize(800, 600);
        jf.setSize(x, y);
        jf.addWindowFocusListener(new jfListener());
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jf.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);// 采用指定的窗口装饰风格//简单对话框风格
        jf.setVisible(true);
        // 键盘事件

        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            public void eventDispatched(AWTEvent event) {
                if (((KeyEvent) event).getID() == KeyEvent.KEY_PRESSED) {

                    switch (((KeyEvent) event).getKeyCode()) {
                    case KeyEvent.VK_F1:
                        Robot rb = null;
                        try {
                            rb = new Robot();
                        } catch (AWTException e1) {
                            e1.printStackTrace();
                        }
                        rb.keyRelease(KeyEvent.VK_CONTROL);
                        rb.keyPress(KeyEvent.VK_ALT);
                        rb.keyPress(KeyEvent.VK_1);
                        rb.keyRelease(KeyEvent.VK_ALT);
                        rb.keyRelease(KeyEvent.VK_1);
                        break;
                    case KeyEvent.VK_F2:
                        Robot rb2 = null;
                        try {
                            rb2 = new Robot();
                        } catch (AWTException e1) {
                            e1.printStackTrace();
                        }
                        rb2.keyRelease(KeyEvent.VK_CONTROL);
                        rb2.keyPress(KeyEvent.VK_ALT);
                        rb2.keyPress(KeyEvent.VK_2);
                        rb2.keyRelease(KeyEvent.VK_ALT);
                        rb2.keyRelease(KeyEvent.VK_2);
                        break;
                    case KeyEvent.VK_F3:
                        Robot rb3 = null;
                        try {
                            rb3 = new Robot();
                        } catch (AWTException e1) {
                            e1.printStackTrace();
                        }
                        rb3.keyRelease(KeyEvent.VK_CONTROL);
                        rb3.keyPress(KeyEvent.VK_ALT);
                        rb3.keyPress(KeyEvent.VK_3);
                        rb3.keyRelease(KeyEvent.VK_ALT);
                        rb3.keyRelease(KeyEvent.VK_3);
                        break;
                    case KeyEvent.VK_F4:
                        Robot rb4 = null;
                        try {
                            rb4 = new Robot();
                        } catch (AWTException e1) {
                            e1.printStackTrace();
                        }
                        rb4.keyRelease(KeyEvent.VK_CONTROL);
                        rb4.keyPress(KeyEvent.VK_ALT);
                        rb4.keyPress(KeyEvent.VK_4);
                        rb4.keyRelease(KeyEvent.VK_ALT);
                        rb4.keyRelease(KeyEvent.VK_4);
                        break;
                    case KeyEvent.VK_F5:
                        Robot rb5 = null;
                        try {
                            rb5 = new Robot();
                        } catch (AWTException e1) {
                            e1.printStackTrace();
                        }
                        rb5.keyRelease(KeyEvent.VK_CONTROL);
                        rb5.keyPress(KeyEvent.VK_ALT);
                        rb5.keyPress(KeyEvent.VK_5);
                        rb5.keyRelease(KeyEvent.VK_ALT);
                        rb5.keyRelease(KeyEvent.VK_5);
                        break;
                    case KeyEvent.VK_F6:
                        Robot rb6 = null;
                        try {
                            rb6 = new Robot();
                        } catch (AWTException e1) {
                            e1.printStackTrace();
                        }
                        rb6.keyRelease(KeyEvent.VK_CONTROL);
                        rb6.keyPress(KeyEvent.VK_ALT);
                        rb6.keyPress(KeyEvent.VK_6);
                        rb6.keyRelease(KeyEvent.VK_ALT);
                        rb6.keyRelease(KeyEvent.VK_6);

                        break;
                    case KeyEvent.VK_F7:
                        Robot rb7 = null;
                        try {
                            rb7 = new Robot();
                        } catch (AWTException e1) {
                            e1.printStackTrace();
                        }
                        rb7.keyRelease(KeyEvent.VK_CONTROL);
                        rb7.keyPress(KeyEvent.VK_ALT);
                        rb7.keyPress(KeyEvent.VK_7);
                        rb7.keyRelease(KeyEvent.VK_ALT);
                        rb7.keyRelease(KeyEvent.VK_7);
                        break;
                    case KeyEvent.VK_F8:
                        Robot rb8 = null;
                        try {
                            rb8 = new Robot();
                        } catch (AWTException e1) {
                            e1.printStackTrace();
                        }
                        rb8.keyRelease(KeyEvent.VK_CONTROL);
                        rb8.keyPress(KeyEvent.VK_ALT);
                        rb8.keyPress(KeyEvent.VK_8);
                        rb8.keyRelease(KeyEvent.VK_ALT);
                        rb8.keyRelease(KeyEvent.VK_8);
                        break;
                    case KeyEvent.VK_F9:
                        Robot rb9 = null;
                        try {
                            rb9 = new Robot();
                        } catch (AWTException e1) {
                            e1.printStackTrace();
                        }
                        rb9.keyRelease(KeyEvent.VK_CONTROL);
                        rb9.keyPress(KeyEvent.VK_ALT);
                        rb9.keyPress(KeyEvent.VK_F9);
                        rb9.keyRelease(KeyEvent.VK_ALT);
                        rb9.keyRelease(KeyEvent.VK_F9);
                        break;
                    case KeyEvent.VK_ESCAPE:
                        Robot rb0 = null;
                        try {
                            rb0 = new Robot();
                        } catch (AWTException e1) {
                            e1.printStackTrace();
                        }
                        rb0.keyRelease(KeyEvent.VK_CONTROL);
                        rb0.keyPress(KeyEvent.VK_ALT);
                        rb0.keyPress(KeyEvent.VK_0);
                        rb0.keyRelease(KeyEvent.VK_ALT);
                        rb0.keyRelease(KeyEvent.VK_0);
                        break;
                    case KeyEvent.VK_DELETE:
                        Robot rbd = null;
                        try {
                            rbd = new Robot();
                        } catch (AWTException e1) {
                            e1.printStackTrace();
                        }
                        rbd.keyRelease(KeyEvent.VK_CONTROL);
                        rbd.keyPress(KeyEvent.VK_ALT);
                        rbd.keyPress(KeyEvent.VK_D);
                        rbd.keyRelease(KeyEvent.VK_D);
                        rbd.keyRelease(KeyEvent.VK_ALT);
                        break;
                    case KeyEvent.VK_UP:
                        Robot rbu2 = null;
                        try {
                            rbu2 = new Robot();
                        } catch (AWTException e1) {
                            e1.printStackTrace();
                        }
                        rbu2.keyRelease(KeyEvent.VK_CONTROL);

                        int i = table.getSelectedRow();
                        if (table.getRowCount() == 0) {
                        } else {
                            if (table.getRowCount() == 1) {
                                table.setRowSelectionInterval(0, 0);
                            } else {
                                if (i == 0) {
                                    table.setRowSelectionInterval(
                                            table.getRowCount() - 1,
                                            table.getRowCount() - 1);
                                } else {
                                    table.setRowSelectionInterval(i - 1, i - 1);
                                }
                            }
                        }

                        break;

                    case KeyEvent.VK_DOWN:
                        Robot rbu = null;
                        try {
                            rbu = new Robot();
                        } catch (AWTException e1) {
                            e1.printStackTrace();
                        }
                        rbu.keyRelease(KeyEvent.VK_CONTROL);
                        int j = table.getSelectedRow();
                        if (table.getRowCount() == 0) {
                        } else {
                            if (table.getRowCount() == 1) {
                                table.setRowSelectionInterval(0, 0);
                            } else {
                                if (j == table.getRowCount() - 1) {
                                    table.setRowSelectionInterval(0, 0);
                                } else {
                                    table.setRowSelectionInterval(j + 1, j + 1);
                                }
                            }
                        }
                        break;
                    case KeyEvent.VK_F12:
                        Robot rbr = null;
                        try {
                            rbr = new Robot();
                        } catch (AWTException e1) {
                            e1.printStackTrace();
                        }
                        rbr.keyRelease(KeyEvent.VK_CONTROL);
                        if (ShowTips.visible) {
                            ShowTips.jf.setVisible(false);
                            ShowTips.visible = false;
                        } else {
                            ShowTips.jf.setVisible(true);
                            ShowTips.visible = true;
                        }
                        break;
                    case KeyEvent.VK_F10:
                        Robot rb10 = null;
                        try {
                            rb10 = new Robot();
                        } catch (AWTException e1) {
                            e1.printStackTrace();
                        }
                        rb10.keyRelease(KeyEvent.VK_CONTROL);
                        rb10.keyPress(KeyEvent.VK_ALT);
                        rb10.keyPress(KeyEvent.VK_F10);
                        rb10.keyRelease(KeyEvent.VK_ALT);
                        rb10.keyRelease(KeyEvent.VK_F10);
                        break;
                    case KeyEvent.VK_F11:
                        Robot rb11 = null;
                        try {
                            rb11 = new Robot();
                        } catch (AWTException e1) {
                            e1.printStackTrace();
                        }
                        rb11.keyRelease(KeyEvent.VK_CONTROL);
                        rb11.keyPress(KeyEvent.VK_ALT);
                        rb11.keyPress(KeyEvent.VK_F11);
                        rb11.keyRelease(KeyEvent.VK_ALT);
                        rb11.keyRelease(KeyEvent.VK_F11);
                        break;
                    case KeyEvent.VK_PAGE_DOWN:
                        Robot rb111 = null;
                        try {
                            rb111 = new Robot();
                        } catch (AWTException e1) {
                            e1.printStackTrace();
                        }
                        rb111.keyRelease(KeyEvent.VK_CONTROL);
                        new CancelForCardPay().init();
                        break;
//                  case KeyEvent.VK_L:
//                      try {
//                          Robot rbc = new Robot();
//                          rbc.keyPress(KeyEvent.VK_ALT);
//                          rbc.keyRelease(KeyEvent.VK_ALT);
//                      } catch (AWTException e) {
//                          e.printStackTrace();
//                      }
//                      O2OMainMenu.tableRefresh(null);
//                      O2OMainMenu.goodsCode.setText("");
//                      O2OMainMenu.goodsNumber.setText("");
//                      totalNumberLabelChange.setText("");
//                      totalMoneyLabelChange.setText("");
//                      O2OMainMenu.documentNumber.setText("");
//                      O2OMainMenu.memberNumber.setText("");
//                      O2OMainMenu.addrId.setText("");
//                      break;
                    default:
                        break;
                    }
                }
            }
        }, AWTEvent.KEY_EVENT_MASK);

        // 关闭窗口事件
        jf.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                // 加入动作
                Robot rb = null;
                try {
                    rb = new Robot();
                } catch (AWTException e1) {
                    e1.printStackTrace();
                }
                rb.keyRelease(KeyEvent.VK_CONTROL);

            }

        });

    }

    /**
     * 解析ResultSet
     */
    // for 商品 原价
    private Object[][] getDataArray(ResultSet rs) throws SQLException {
        List<Object[]> listGoods = new LinkedList<Object[]>();
        int row = O2OMainMenu.table.getRowCount();
        if (row == 0) {
            try {
                while (rs.next()) {
                    // 给table中添加数据
                    Object[] objects = new Object[] {
                            rs.getInt("product_id"),
                            rs.getString("bar_code"),
                            rs.getString("name"),
                            rs.getString("specs"),
                            OrderStatus.getTaxText(rs.getInt("tax_system")),
                            rs.getString("unit"),
                            rs.getDouble("goods_taxes"),
                            ""+ df.format(new BigDecimal(rs.getDouble("price")))};
                    listGoods.add(objects);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } else {
            try {
                while (rs.next()) {
//                    String cont = O2OMainMenu.table.getValueAt(row - 1, 4).toString();
//                    String tax_system = OrderStatus.getTaxText(rs
//                            .getInt("tax_system"));
//                    if (tax_system.equals("全部")) {
//                        String tax_sys = rs.getString("spec_value");
//                        if (tax_sys.contains("完税")) {
//                            tax_system = "完税";
//                        } else if(tax_sys.contains("保税")){
//                            tax_system = "保税";
//                        }else{
//                            tax_system = "直邮";
//                        }
//                    }
//                    if (cont.equals(tax_system)) {

                        // 改成你的列名
                        Object[] objects = new Object[] {
                                rs.getInt("product_id"),
                                rs.getString("bar_code"),
                                rs.getString("name"),
                                rs.getString("specs"),
                                OrderStatus.getTaxText(rs.getInt("tax_system")),
                                rs.getString("unit"),
                                rs.getDouble("goods_taxes"),
                                ""+ df.format(new BigDecimal(rs.getDouble("price"))) };
                        listGoods.add(objects);
//                    } else {
//                        listGoods.add(null);
////                        JOptionPane.showMessageDialog(jf, "您只能录入税制相同的产品");  
//                    }
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return listGoods.toArray(new Object[0][0]);
    }

    private Object[][] getDataArrayForSn(List<JSONObject> object) {
        List<Object[]> list = new LinkedList<Object[]>();
        if (object != null) {
            for (JSONObject json : object) {
                // 改成你的列名
                Object[] objects = new Object[] {
                        json.optString("product_id"),
                        json.optString("order_id"),
                        json.optString("sn"),
                        json.optString("name"),
                        json.opt("specs"),
                        OrderStatus.getTaxText(json.optInt("tax_system")),
                        json.opt("unit"),
                        json.optString("taxes"),
                        "" + df.format(new BigDecimal(json.getDouble("price"))),
                        json.getInt("num"),
                        ""+ df.format(new BigDecimal(((json.getDouble("price")+json.getDouble("taxes")) * (json.getInt("num"))))) };
                list.add(objects);
            }
        } else {
            Object[] objects = new Object[] {};
            list.add(objects);
        }
        return list.toArray(new Object[0][0]);
    }

    // for 会员
    Object[][] getDataArrayMember(ResultSet rs) throws SQLException {
        List<Object[]> list = new LinkedList<Object[]>();
        while (rs.next()) {
            // 改成你的列名
            Object[] objects = new Object[] { rs.getString("card_id"),
                    rs.getString("phone") };
            list.add(objects);
        }
        return list.toArray(new Object[0][0]);
    }

    Object[][] getDataArrayMember(JSONObject object) {
        List<Object[]> list = new LinkedList<Object[]>();
        if (object != null) {
            Object[] objects = new Object[] { object.get("mobile") };
            list.add(objects);
        } else {
            Object[] objects = new Object[] {};
            list.add(objects);
        }
        return list.toArray(new Object[0][0]);
    }

    /**
     * 商品数量监听器
     */
    public class goodsNumberListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {

            boolean isNum2 = goodsNumber.getText().matches(
                    "^[0-9]*[1-9][0-9]*$");// 正则表达式//只能输入正整数
            if (isNum2) {

                if (goodsNumber.isFocusOwner()) {
                    ifAddGoods = false;
                }
                if (rejectable) {
                    rejectable = false;

                    while (model.getRowCount() > 0) {
                        model.removeRow(model.getRowCount() - 1);
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            goodsNumber.setText("");
                        }
                    });
                }
                // 动态改变1
                else {
                    if (goodsNumber.getText().length() > 0) {
                        if (table.getRowCount() - 1 >= 0) {
                            selections = table.getSelectedRows();
                            if (/* !ifAddGoods && */selections.length == 1) {
                                String count = goodsNumber.getText();
                                table.setValueAt(count, selections[0], 8);
                                String value = (String) table.getValueAt(selections[0], 7);
                                Double tax =  Double.parseDouble(table.getValueAt(selections[0], 6)+"");
                                if (!count.equals("") && count != null) {
                                    double amount = ((Double.parseDouble(value))+tax)* Double.parseDouble(count);
                                    table.setValueAt(df.format(new BigDecimal(amount)),selections[0], 9);
                                }
                                totalRefresh();// 更新合计
                            } else if (ifAddGoods /* ||selections.length == 0 */) {
                                String count = goodsNumber.getText();
                                table.setValueAt(count,
                                        table.getRowCount() - 1, 7);
                                String value = (String) table.getValueAt(
                                        table.getRowCount() - 1, 5);
                                double amount = (Double.parseDouble(value))
                                        * Integer.parseInt(count);
                                table.setValueAt(
                                        df.format(new BigDecimal(amount)),
                                        table.getRowCount() - 1, 8);
                                totalRefresh();
                            } else {
                                JOptionPane
                                        .showMessageDialog(jf, "请选择一条商品改变数量");
                                SwingUtilities.invokeLater(new Runnable() {
                                    public void run() {
                                        goodsNumber.requestFocus();
                                    }
                                });
                            }
                        } else {
                            JOptionPane.showMessageDialog(jf, "请添加商品");
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    goodsNumber.setText("");
                                }
                            });
                        }
                    }

                }
            } else if (goodsNumber.getText().length() == 0) {
                JOptionPane.showMessageDialog(jf, "请输入数量");
            } else {
                JOptionPane.showMessageDialog(jf, "请输入正确的格式");
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        String str = goodsNumber.getText();
                        int len = goodsNumber.getText().length();
                        goodsNumber.setText(str.substring(0, len - 1));
                        goodsNumber.requestFocus();
                    }
                });

            }

        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (goodsNumber.isFocusOwner()) {
                ifAddGoods = false;
            }
            if (rejectable) {
                rejectable = false;

                while (model.getRowCount() > 0) {
                    model.removeRow(model.getRowCount() - 1);
                }
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        goodsNumber.setText("");
                    }
                });
            }
            // 动态改变1
            else {
                if (goodsNumber.getText().length() > 0) {
                    if (table.getRowCount() - 1 >= 0) {
                        selections = table.getSelectedRows();
                        if (!ifAddGoods && selections.length == 1) {
                            String count = goodsNumber.getText();
                            table.setValueAt(count, selections[0], 7);
                            String value = (String) table.getValueAt(
                                    selections[0], 5);
                            double amount = (Double.parseDouble(value))
                                    * Double.parseDouble(count);
                            table.setValueAt(df.format(new BigDecimal(amount)),
                                    selections[0], 8);
                            totalRefresh();
                        } else if (/* selections.length == 0 || */ifAddGoods) {
                            String count = goodsNumber.getText();
                            table.setValueAt(count, table.getRowCount() - 1, 7);
                            String value = (String) table.getValueAt(
                                    table.getRowCount() - 1, 5);
                            double amount = (Double.parseDouble(value))
                                    * Integer.parseInt(count);
                            table.setValueAt(df.format(new BigDecimal(amount)),
                                    table.getRowCount() - 1, 8);
                            totalRefresh();
                        } else {
                            JOptionPane.showMessageDialog(jf, "请选择一条商品改变数量");
                        }
                    } else {
                        JOptionPane.showMessageDialog(jf, "请添加商品");
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                goodsNumber.setText("");
                            }
                        });
                    }
                }
            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {

        }

    }

    /**
     * table监听
     */
    public class tableListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (table.getSelectedRowCount() == 1) {
                // more than one row
                tableRow = table.getSelectedRow();
            }
            try {
                Robot rb = new Robot();
                rb.keyPress(KeyEvent.VK_CONTROL);
            } catch (AWTException e1) {
                e1.printStackTrace();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            goodsCode.requestFocus();
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

    }

    /**
     * window焦点监听器
     */
    public class jfListener implements WindowFocusListener {

        @Override
        public void windowGainedFocus(WindowEvent e) {

            goodsCode.requestFocus();

        }

        @Override
        public void windowLostFocus(WindowEvent e) {

            try {
                Robot rb = new Robot();
                rb.keyRelease(KeyEvent.VK_CONTROL);
            } catch (AWTException e1) {
                e1.printStackTrace();
            }

        }

    }

    /**
     * 选中删除监听器
     */
    class BtListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int[] selections = table.getSelectedRows();
            // 获取model
            DefaultTableModel dtf = (DefaultTableModel) table.getModel();
            // 反向删除
            if (selections.length > 0) {
                if (selections.length == 1) {
                    Object[] options = { "确定" };
                    int response = JOptionPane.showOptionDialog(jf, "是否删除", "",
                            JOptionPane.YES_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options,
                            options[0]);
                    if (response == 0) {
                        dtf.removeRow(table.getSelectedRow());
                        totalRefresh();
                        if (table.getRowCount() > 0) {
                            table.setRowSelectionInterval(
                                    table.getRowCount() - 1,
                                    table.getRowCount() - 1);
                        }
                    } else {
                        // break;
                    }

                    goodsCode.requestFocus();
                    Robot rb = null;
                    try {
                        rb = new Robot();
                    } catch (AWTException e1) {
                        e1.printStackTrace();
                    }
                    rb.keyRelease(KeyEvent.VK_CONTROL);
                } else {
                    Object[] options = { "确定" };
                    int response = JOptionPane.showOptionDialog(jf, "是否删除", "",
                            JOptionPane.YES_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options,
                            options[0]);
                    if (response == 0) {
                        for (int i = selections.length; i > 0; i--) {
                            dtf.removeRow(table.getSelectedRow());
                            totalRefresh();
                        }
                    } else {
                        // break;
                    }

                    goodsCode.requestFocus();
                    Robot rb = null;
                    try {
                        rb = new Robot();
                    } catch (AWTException e1) {
                        e1.printStackTrace();
                    }
                    rb.keyRelease(KeyEvent.VK_CONTROL);
                }
                O2OMainMenu.documentNumber.setText("");
            } else {
                JOptionPane.showMessageDialog(jf, "未选择商品");
                goodsCode.requestFocus();
            }
        }
    }
    public class SaleListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            SaleStatistics s = new SaleStatistics();
            s.init();
        }
        
    }
    public class DataListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
        
    }
    /**
     * 商品输入键盘监听
     * */
    public class goodsCodeKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.isAltDown()){
                //release ctrl键
                Robot rb = null;
                try {
                    rb = new Robot();
                } catch (AWTException e1) {
                    e1.printStackTrace();
                }
                rb.keyRelease(KeyEvent.VK_CONTROL);
            }
            // 判断快捷键 alt
            if (e.isAltDown() && e.getKeyCode() == KeyEvent.VK_C) {
                try {
                    new RepeatPrient().prient();
                } catch (SQLException e1) {
                    
                    e1.printStackTrace();
                }
            }
            if (e.isAltDown() && e.getKeyCode() == KeyEvent.VK_B) {
                if (memberNumber.getText().length() != 0) {
                    new MemberAddress().init();
                } else {
                    JOptionPane.showMessageDialog(jf, "请输入会员号");
                }
            }
            if (e.isAltDown() && e.getKeyCode() == KeyEvent.VK_F) {
                if (memberNumber.getText().length() != 0) {
                    new DetailAddress().init();
                } else {
                    JOptionPane.showMessageDialog(jf, "请输入会员号");
                }
            }
            if (e.isAltDown() && e.getKeyCode() == KeyEvent.VK_L) {
                O2OMainMenu.tableRefresh(null);
                O2OMainMenu.goodsCode.setText("");
                O2OMainMenu.goodsNumber.setText("");
                totalNumberLabelChange.setText("");
                totalMoneyLabelChange.setText("");
                O2OMainMenu.documentNumber.setText("");
                O2OMainMenu.memberNumber.setText("");
                Robot rb = null;
                try {
                    rb = new Robot();
                } catch (AWTException e1) {
                    e1.printStackTrace();
                }
                rb.keyRelease(KeyEvent.VK_ALT);
            }
            if (e.isAltDown() && e.getKeyCode() == KeyEvent.VK_S) {
                new SaleStatistics().init();
            }
            if (e.isAltDown() && e.getKeyCode() == KeyEvent.VK_R) {
                JOptionPane.showMessageDialog(jf, "该功能已经被禁用");
                //数据恢复咱是不能使用 2017-03-27
//                new DoBackup().load();
            }
            ifAddGoods = true;

            // 添加数据
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if(O2OMainMenu.documentNumber.getText().length()>1){
                    JOptionPane.showMessageDialog(jf, "请处理当前未完成订单");
                    O2OMainMenu.goodsCode.setText("");
                    return;
                }
                // 如果rejectable为真，说明刚刚执行完订单查询，输入商品要清空订单查询记录
                if (rejectable || rejectedDay) {
                    rejectable = false;
                    rejectedDay = false;
                    documentNumber.setText("");
                    memberNumber.setText("");
                    while (model.getRowCount() > 0) {
                        model.removeRow(model.getRowCount() - 1);
                    }
                }
                data2 = null;
                data3 = null;
                a = true;

                String goodsString = goodsCode.getText().trim();
                String goods = goodsCode.getText().trim();
                int row = O2OMainMenu.table.getRowCount();
//                String reg = "^[0-9]*$";// 只能为数字的java正则表达式
                if (!goodsString.equals("") && !goods.equals("")) {// 输入不能为空
//                    if (goodsString.matches(reg) || goodsString.startsWith("S")) {
                    if (goodsString.startsWith("S")||!goods.equals("")) {
                        // 只能输入全部是数字或者以S开头的字符串
                        // 初始化参数
                        PropertiesUtil storeId = new PropertiesUtil();
                        @SuppressWarnings("static-access")
                        String store_num = storeId.getStoreId();
                        String sql = "";
                        sql = " select p.product_id,p. NAME,p.bar_code,p.specs,p.tax_system,sp.store_price AS price,g.unit,p.goods_taxes,sp.store_id  "
                                + " FROM es_store_pro sp "
                                + " LEFT JOIN es_product p ON p.product_id = sp.product_id "
                                + " LEFT JOIN es_goods g ON p.goods_id = g.goods_id " 
//                                + " INNER JOIN es_storage_count sc ON sc.store_id = sp.store_id " 
//                                + " AND sc.product_id = sp.product_id "
                                + " WHERE p.bar_code = '"
                                + goods
                                + "' AND sp.store_id = '" + store_num + "'";
                        // 一、 如果不联网
                        if (!O2OMainMenu.internetPass) { // 不联网状态的情况下，调用本地的数据库进行查询商品
                            ResultSet rs = new SetSql().setSql(sql);
                            try {
                                data2 = getDataArray(rs);
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                            if(data2.length!=0){
                            if(data2.length >= 1){
                                if(data2.length == 1&&null!=data2[0]){

                                model.addRow(data2[0]);
                                table.setModel(model);

                                jScrollPane.setViewportView(table);
                                if(table.getRowCount()==0){}
                                else{
                                    if(table.getRowCount()==1){
                                        table.setRowSelectionInterval(0,0);
                                        
                                    }else{
                                        table.setRowSelectionInterval(table.getRowCount()-1, table.getRowCount()-1);
                                    }
                                    
                                }
                                
                                goodsLength = goodsCode.getText().length();
                                if (table.getRowCount() == 1) {
                                    goodsNumber.setText("1");
                                } else {
                                    goodsNumber.setText("1");
                                    // 判断商品是否重复,重复则合并
                                    for (int i = 0; i < table.getRowCount() - 1; i++) {
                                        if (((String) (table.getValueAt(
                                                table.getRowCount() - 1, 2)))
                                                .equals((String) (table.getValueAt(i, 2)))) {

                                            String num1 = table.getValueAt(i, 8) + "";
                                            String num2 = table.getValueAt(
                                                    table.getRowCount() - 1, 8)
                                                    + "";
                                            int numI1 = Integer.parseInt(num1);
                                            int numI2 = Integer.parseInt(num2);
                                            String all = "" + (numI1 + numI2);

                                            model.removeRow(i);
                                            table.setModel(model);
                                            jScrollPane.setViewportView(table);
                                            goodsNumber.setText(all);
                                        }
                                    }

                                    goodsCode.requestFocusInWindow();
                                    
                                    
                                }
                                goodsCode.setText("");
                            }
                            if (data2.length >1) {
                                // sql = sql + "'" + goods + "'";
                                ResultSet rsGoods = new SetSql().setSql(sql);
                                List<Object[]> listGoods = new LinkedList<Object[]>();
                                try {
                                    while (rsGoods.next()) {
                                        Object[] objects = new Object[] {
                                            rsGoods.getInt("product_id"),
                                            rsGoods.getString("bar_code"),
                                            rsGoods.getString("name"),
                                            rsGoods.getString("specs"),
                                            OrderStatus.getTaxText(rsGoods.getInt("tax_system")),
                                            rsGoods.getString("unit"),  
                                            rsGoods.getDouble("goods_taxes"),
                                            "" + df.format(new BigDecimal(rsGoods.getDouble("price"))),
                                            
                                        };
                                        listGoods.add(objects);
                                    }
                                    GoodsMany.data = listGoods
                                            .toArray(new Object[0][0]);
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                }
                                new GoodsMany().init();
                                goodsCode.setText("");
                            }
                            goodsCode.setText("");
                            if(null==data2[0]&&data2.length==1){
                            JOptionPane.showMessageDialog(jf, "您只能录入税制相同的产品");
                            }
                            } else {
                                JOptionPane.showMessageDialog(jf, "商品不存在");
                                goodsCode.setText("");
                            }}else{
                                try {
                                    if(rs.next()){
                                        JOptionPane.showMessageDialog(jf, "您只能录入税制相同的产品");
                                    }else{
                                        JOptionPane.showMessageDialog(jf,"商品不存在");
                                    }
                                } catch (HeadlessException e1) {
                                    e1.printStackTrace();
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                }
                                goodsCode.setText("");
                                }
                            // 二、联网情况下
                        } else {
                            // （1）、线上的订单（编号以S开头），在线下支付
                            /*
                             * 触发监听事件,通过手机扫描商品信息生成的订单，然后在线下收银机上扫描进行支付，
                             * 在输入商品的输入框里输入订单号显示出订单的详细商品信息，然后进行支付。
                             */
                            if (goodsString.startsWith("S")) {

                                OrderListener orderListener = new OrderListener();
                                orderListener.orderScan();
                                // （2）、输入商品条形码
                            } else {
                                // 1
                                String sqlOfUpShelf = sql = " select p.product_id,p. NAME,p.bar_code,p.specs,p.tax_system,sp.store_price AS price,g.unit,p.goods_taxes,sp.store_id  "
                                        + " FROM es_store_pro sp "
                                        + " LEFT JOIN es_storage_count sc ON sp.product_id = sc.product_id " 
                                        + " AND sc.store_id = sp.store_id "
                                        + " LEFT JOIN es_product p ON p.product_id = sp.product_id " 
                                        + " LEFT JOIN es_goods g ON g.goods_id = p.goods_id "
                                        + " WHERE p.bar_code = '"
                                        + goods
                                        + "' AND sp.store_id = '" + store_num + "'";
                                List<Object[]> listGoods = new LinkedList<Object[]>();
                                MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
                                queryParam.add("sql", sqlOfUpShelf);
                                String url = PropertiesUtil.getIp()
                                        + "/api/shop/goods!getAllProduct.do";
                                ConnectOnlineMethod connect = new ConnectOnlineMethod();
                                List<JSONObject> list1 = new ArrayList<JSONObject>();
                                // List<Object[]> listGoods = new LinkedList();
                                String outPut;
                                try {
                                    outPut = connect.connectOnline(queryParam,
                                            url);
                                    JSONArray array = connect.jsonConvertion(outPut);
                                    list1 = connect.getJsonObjects(array);
                                    // int v = outPut.length();
                                    if (list1.size() >= 1 ) {
                                        if(list1.size() == 1){
                                            //不会弹出规格选择窗口
                                            if (row == 0) {
                                                for (JSONObject object : list1) {
                                                        Object[] objects = new Object[] {
                                                                object.get("product_id"),
                                                                object.get("bar_code"),
                                                                object.get("name"),
                                                                null==object.get("specs")||object.get("specs").equals(null)?"/":object.get("specs"),
                                                                OrderStatus.getTaxText(object.getInt("tax_system")),
                                                                object.get("unit"),
                                                                object.get("goods_taxes"),
                                                                "" + df.format(new BigDecimal(object.getDouble("price")))
                                                                
                                                        };
                                                        listGoods.add(objects);
                                                }
                                            } else {
                                                for (JSONObject object : list1) {
                                                    //如果是全部，需要进一步判断到底是保税还是完税商品
                                                    String cont = O2OMainMenu.table
                                                            .getValueAt(row - 1, 4)
                                                            .toString();
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
                                                    //判断当前录入商品的税制
                                                    String tax_system = OrderStatus
                                                            .getTaxText(object
                                                                    .getInt("tax_system"));
                                                    if (tax_system.equals("全部")) {
                                                        String tax_sys = object
                                                                .getString("spec_value");
                                                        if (tax_sys.contains("完税")) {
                                                            tax_system = "完税";
                                                        } else if(tax_sys.contains("保税")){
                                                            tax_system = "保税";
                                                        } else{
                                                            tax_system = "直邮";
                                                        }
                                                    }
  //                                                  if (cont.equals(tax_system)) {
                                                        Object[] objects = new Object[] {
                                                                object.get("product_id"),
                                                                object.get("bar_code"),
                                                                object.get("name"),
                                                                null==object.get("specs")||object.get("specs").equals(null)?"/":object.get("specs"),
                                                                OrderStatus.getTaxText(object.getInt("tax_system")),
                                                                object.get("unit"),
                                                                object.get("goods_taxes"),
                                                                ""+ df.format(new BigDecimal(object.getDouble("price")))
                                                                
                                                        };
                                                        listGoods.add(objects);
//                                                    } else {
//                                                        O2OMainMenu.totalRefresh();
//                                                        JOptionPane.showMessageDialog(jf,"您只能录入税制相同的产品");
//                                                        goodsCode.setText("");
//                                                        b = false;
//                                                    }
                                                }
                                            }
                                            
                                            //将list转化为数组    
                                            data2 = listGoods.toArray(new Object[0][0]);
                                            if(data2.length>0){
                                                //
                                                model.addRow(data2[0]);
                                            }
                                            table.setModel(model);
                                            jScrollPane.setViewportView(table);
                                            if (table.getRowCount() == 0) {

                                            } else {
                                                if (table.getRowCount() == 1) {
                                                    table.setRowSelectionInterval(0, 0);
                                                } else {
                                                    table.setRowSelectionInterval(
                                                            table.getRowCount() - 1,
                                                            table.getRowCount() - 1);
                                                }

                                            }

                                            goodsLength = goodsCode.getText().length();
                                            if (table.getRowCount() == 1) {
                                                goodsNumber.setText("1");
                                            } else {
                                                goodsNumber.setText("1");
                                                // 判断商品是否重复,重复则合并
                                                // 如果商品的条形码、商品名称、商品规格都相同，则合并主页面的商品
                                                for (int i = 0; i < table.getRowCount() - 1; i++) {
                                                    if (((String) (table.getValueAt(table.getRowCount() - 1, 1))).equals((String) (table.getValueAt(i, 1)))
                                                            && ((String) (table.getValueAt(table.getRowCount() - 1, 2))).equals((String) (table.getValueAt(i, 2)))
                                                            && ((String) (table.getValueAt(table.getRowCount() - 1, 4))).equals((String) (table.getValueAt(i, 4)))
                                                        ) {

                                                        String num1 = table.getValueAt(
                                                                i, 8) + "";
                                                        String num2 = table
                                                                .getValueAt(
                                                                        table.getRowCount() - 1,
                                                                        8)
                                                                + "";
                                                        int numI1 = Integer
                                                                .parseInt(num1);
                                                        int numI2 = Integer
                                                                .parseInt(num2);
                                                        String all = ""
                                                                + (numI1 + numI2);

                                                        model.removeRow(i);
                                                        table.setModel(model);
                                                        jScrollPane
                                                                .setViewportView(table);
                                                        goodsNumber.setText(all);
                                                    }
                                                }
                                                //调用客显
                                                sendToDisplay(totalMoneyLabelChange.getText(), "2");
                                                goodsCode.setText("");
                                                goodsCode.requestFocusInWindow();

                                            }
                                            //
                                        }
                                        //如果查询到多个商品，则弹出规格选择小窗口
                                        if(list1.size() > 1){
                                            for (JSONObject object : list1) {
                                                Object[] objects = new Object[] {
                                                        object.get("product_id")+ "",
                                                        object.get("bar_code"),
                                                        object.get("name"),
                                                        object.get("specs"),
                                                        OrderStatus.getTaxText(object.getInt("tax_system")),
                                                        object.get("unit"),
                                                        object.get("goods_taxes"),
                                                        ""+ df.format(new BigDecimal(object.getDouble("price")))
                                                        
                                                                        
                                                };
                                                listGoods.add(objects);
                                            }
//                                          GoodsMany.mainMenulist
                                            GoodsMany.data = listGoods.toArray(new Object[0][0]);
                                            new GoodsMany().init();
                                            goodsCode.setText("");
                                        }
                                        goodsCode.setText("");
                                    } else {
                                        JOptionPane.showMessageDialog(jf,"商品不存在");
                                        goodsCode.setText("");
                                    }
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                        
                    } else {
                        JOptionPane.showMessageDialog(jf, "输入格式不正确");
                        goodsCode.setText("");
                    }
                } else {
                    JOptionPane.showMessageDialog(jf, "请输入商品条形码或订单编号");
                    goodsCode.setText("");
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if(O2OMainMenu.documentNumber.getText().toString().length()==0){
//                PropertiesUtil storeId = new PropertiesUtil();
//                @SuppressWarnings("static-access")
//                String store_num = storeId.getStoreId();
//                String product_id = O2OMainMenu.table.getValueAt(table.getSelectedRow(), 0).toString();
//                String sql = " select p.product_id,p. NAME,p.bar_code,p.specs,p.tax_system,sp.store_price AS price,g.unit,sp.taxes,sp.store_id  "
//                        + " FROM es_product p "
//                        + " LEFT JOIN es_goods g ON p.goods_id = g.goods_id " 
//                        + " LEFT JOIN es_store_pro sp ON p.product_id = sp.product_id "
////                      + " LEFT JOIN es_store s ON sp.store_id = s.store_id "
//                        + " WHERE p.product_id = '"
//                        + product_id
//                        + "' AND sp.store_id = '" + store_num + "'";
                    /* if(O2OMainMenu.internetPass){
                MultivaluedMap queryParam = new MultivaluedMapImpl();
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
               String nums = goodsCode.getText();
               if(list1.size() >=1){
                   for(JSONObject object :list1){
                       useableCount = (Integer) object.get("useableCount");
                       storeCount = (Integer) object.get("storeCount");
                   }
               }
               if((useableCount + storeCount) < Integer.parseInt(nums)){
                   Integer counts = useableCount + storeCount;
                   JOptionPane.showMessageDialog(jf, "该商品当前库存为"+counts);
               }else{
                   goodsNumber.setText(goodsCode.getText());
                   String total = table.getValueAt(tableRow, 9).toString();
                   O2OMainMenu.totalMoneyLabelChange.setText(total);
               }}else{*/
                    goodsNumber.setText(goodsCode.getText());
                    String total = table.getValueAt(tableRow, 9).toString();
                    O2OMainMenu.totalMoneyLabelChange.setText(total);
//               }
                    // try {
                    // prop1.load(O2OMainMenu.class.getClassLoader().getResourceAsStream(
                    // "jdbc.properties"));
                    // } catch (IOException e2) {
                    // e2.printStackTrace();
                    // }
                    sendToDisplay(totalMoneyLabelChange.getText(), "2");
                    goodsCode.setText("");
                    totalRefresh();// 更新合计
                }else{
                    Integer nums = Integer.parseInt(goodsCode.getText().toString());
                    Integer nt = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 8)+"");
                    if(nums>nt){
                        JOptionPane.showMessageDialog(jf, "退货数量输入不正确");
                        return;
                    }
                    Double p = Double.parseDouble(table.getValueAt(table.getSelectedRow(), 7).toString());
                    table.setValueAt(nums, table.getSelectedRow(), 8);
                    table.setValueAt(nums*p, table.getSelectedRow(), 9);
                    totalRefresh();// 更新合计
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                documentNumber.setText(goodsCode.getText());
                goodsCode.setText("");
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

    }

    /**
     * 挂单处理
     */
    class Bt2Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (rejectable || rejectedDay) {
                JOptionPane.showMessageDialog(jf, "该订单已结算");
            } else {
                if (memberNumber.getText().length() > 0) {
                    String member = memberNumber.getText();
//                    String sql = "select * from es_member where card_id like '"
//                            + member + "'";
//                    if (!O2OMainMenu.internetPass) {
//                        ResultSet rse = new SetSql().setSql(sql);
//                        try {
//                            dataMember = getDataArrayMember(rse);
//                        } catch (SQLException e1) {
//                            e1.printStackTrace();
//                        }
//                    } else {
//                        MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
//                        queryParam.add("phone", member);
//                        String url = PropertiesUtil.getIp()
//                                + "/api/shop/member!getCard.do";
//                        ConnectOnlineMethod connection = new ConnectOnlineMethod();
//                        String outPut;
//                        try {
//                            outPut = connection.connectOnline(queryParam, url);
//                            if (!outPut.equals("")) {
//                                JSONArray array = connection
//                                        .jsonConvertion(outPut);
//                                JSONObject jsonObject = connection
//                                        .getJsonObject(array);
//                                dataMember = getDataArrayMember(jsonObject);
//                            }
//                        } catch (IOException e1) {
//                            e1.printStackTrace();
//                        }
//
//                    }

                    if (member.length() > 0) {

                        if (table.getRowCount() != 0) {
                            long timezero = 0;
                            try {
                                timezero = getTimes() / 1000;
                            } catch (ParseException e3) {
                                e3.printStackTrace();
                            }
                            String sqlg =  "select order_id from  es_order where cardNumber in ('"+memberNumber.getText()+"'," +
                                    "'select phone from es_member where cardNumber = "+memberNumber.getText()+"')"
                                    + " and status = "
                                    + OrderStatus.ORDER_NOT_PAY
                                    + " and create_time > " + timezero;
                            ResultSet rss = new SetSql()
                                    .setSql(sqlg);

                            boolean rsexist = true;
                            try {
                                rsexist = rss.next();
                            } catch (SQLException e2) {
                                e2.printStackTrace();
                            }

                            if (!rsexist) {
                                JOptionPane.showMessageDialog(jf, "挂单成功");
                                int rows = table.getRowCount();
                                String num = null;
                                int product_id = 0;
                                int goods_id = 0;
                                String sn2 = null;
                                String name = null;
                                Double price = (double) 0;
                                String spec_value = null;

                                String cardNumber = memberNumber.getText();
                                long create_time = System.currentTimeMillis() / 1000;
                                long order_id = Sequence.nextId();
                                Double goods_amount = Double
                                        .parseDouble(totalMoneyLabelChange
                                                .getText());
                                int goods_num = Integer
                                        .parseInt(totalNumberLabelChange
                                                .getText());
                                int status = OrderStatus.ORDER_NOT_PAY;
                                String user_name = operator.getText();
                                Double order_amount = goods_amount;
                                String currency = "CNY";
                                //给商品加了税金的字段
                                Double taxes = 0.00;
                                for(int i = 0 ; i< O2OMainMenu.table.getRowCount(); i++){
                                    taxes = Double.parseDouble(O2OMainMenu.table.getValueAt(i, 6)+"");
                                    taxes +=taxes;
                                }
                                // pos机和店铺id
                                String store_id = PropertiesUtil.getStoreId();
                                long timeNow = System.currentTimeMillis();
                                String dateNow = new timeToDates()
                                        .getTimeToDates(timeNow);
                                String sn3 = new order_sn().soGet();
                                int classes = 1;
                                ResultSet rsResultSet = new SetSql()
                                        .setSql("select * from es_classes");
                                try {
                                    rsResultSet.next();
                                    classes = rsResultSet.getInt("classes");
                                } catch (SQLException e2) {
                                    e2.printStackTrace();
                                }
                                boolean a = new SetSql()
                                        .setSqlNotReturn("insert into es_order(order_id,sn,cardNumber,user_name,status,create_time,goods_amount,order_amount,goods_num,store_id,currency,date,classes,balance_status,taxes) values("
                                                + order_id
                                                + ",'"
                                                + sn3
                                                + "','"
                                                + cardNumber
                                                + "','"
                                                + user_name
                                                + "',"
                                                + status
                                                + ","
                                                + create_time
                                                + ","
                                                + goods_amount
                                                + ","
                                                + order_amount
                                                + ","
                                                + goods_num
                                                + ",'"
                                                + store_id
                                                + "','"
                                                + currency
                                                + "','"
                                                + dateNow
                                                + "',"
                                                + classes
                                                + ",1,"+taxes+")");
                                if (a) {
                                    new DoBackup()
                                            .realTimeBackup("insert into es_order(order_id,sn,cardNumber,user_name,status,create_time,goods_amount,order_amount,goods_num,store_id,currency,date,classes,balance_status,taxes) values("
                                                    + order_id
                                                    + ",'"
                                                    + sn3
                                                    + "','"
                                                    + cardNumber
                                                    + "','"
                                                    + user_name
                                                    + "',"
                                                    + status
                                                    + ","
                                                    + create_time
                                                    + ","
                                                    + goods_amount
                                                    + ","
                                                    + order_amount
                                                    + ","
                                                    + goods_num
                                                    + ",'"
                                                    + store_id
                                                    + "','"
                                                    + currency
                                                    + "','"
                                                    + dateNow
                                                    + "',"
                                                    + classes
                                                    + ",1,"+taxes+");");
                                }
                                for (int i = 0; i < rows; i++) {
                                    num = String
                                            .valueOf(table.getValueAt(i, 8));// num
                                    sn2 = String
                                            .valueOf(table.getValueAt(i, 1));

                                    String sql2 = "select * from es_product where bar_code = '"
                                            + sn2
                                            + "' and product_id ='"
                                            + table.getValueAt(i, 0) + "'";
                                    spec_value = (O2OMainMenu.table.getValueAt(i, 3)+"").toString();
                                    if (!O2OMainMenu.internetPass) {
                                        ResultSet snResultSet = new SetSql()
                                                .setSql(sql2);
                                        try {
                                            snResultSet.next();
                                            goods_id = snResultSet
                                                    .getInt("goods_id");
                                            product_id = snResultSet
                                                    .getInt("product_id");
                                            name = snResultSet
                                                    .getString("name");
                                            price = Double.parseDouble(O2OMainMenu.table.getValueAt(i, 7) + "");
                                            taxes = Double.parseDouble(O2OMainMenu.table.getValueAt(i, 6) + "");                                            
                                        } catch (SQLException e1) {
                                            e1.printStackTrace();
                                        }
                                    } else {
                                        MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
                                        queryParam.add("sql", sql2);
                                        String url = PropertiesUtil.getIp()
                                                + "/api/shop/goods!getProductSpecValue.do";
                                        ConnectOnlineMethod connect = new ConnectOnlineMethod();
                                        JSONObject jObject = new JSONObject();
                                        try {
                                            String outPut = connect
                                                    .connectOnline(queryParam,
                                                            url);
                                            if (!outPut.equals("")) {
                                                JSONArray array = connect.jsonConvertion(outPut);
                                                jObject = connect.getJsonObject(array);
                                                goods_id = jObject.optInt("goods_id");
                                                product_id = jObject.optInt("product_id");
                                                name = jObject.getString("name");
                                                price = Double.parseDouble(O2OMainMenu.table.getValueAt(i,7)+ "");
                                                taxes = Double.parseDouble(O2OMainMenu.table.getValueAt(i, 6) + "");                                               
                                                        
                                            }
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                    }
                                    boolean b = new SetSql()
                                            .setSqlNotReturn("insert into es_order_items(order_id,goods_id,product_id,num,bar_code,name,price,currency,sn,taxes,spec_value) values("
                                                    + order_id
                                                    + ","
                                                    + goods_id
                                                    + ","
                                                    + product_id
                                                    + ","
                                                    + num
                                                    + ",'"
                                                    + sn2
                                                    + "','"
                                                    + name
                                                    + "',"
                                                    + price
                                                    + ",'"
                                                    + currency
                                                    + "','"
                                                    + sn3 + "',"
                                                    + taxes
                                                    + ",'"
                                                    + spec_value
                                                    +"')");
                                    if (b) {
                                        new DoBackup()
                                                .realTimeBackup("insert into es_order_items(order_id,goods_id,product_id,num,bar_code,name,price,currency,sn,taxes，spec_value) values("
                                                        + order_id
                                                        + ","
                                                        + goods_id
                                                        + ","
                                                        + product_id
                                                        + ","
                                                        + num
                                                        + ",'"
                                                        + sn2
                                                        + "','"
                                                        + name
                                                        + "',"
                                                        + price
                                                        + ",'"
                                                        + currency
                                                        + "','"
                                                        + sn3 + "',"
                                                        + taxes
                                                        + ",'"
                                                        + spec_value
                                                        +"')");
                                    }
                                }
                                // 清空table数据
                                memberNumber.setText("");
                                DefaultTableModel model = (DefaultTableModel) table
                                        .getModel();
                                while (model.getRowCount() > 0) {
                                    model.removeRow(model.getRowCount() - 1);
                                }
                                totalMoneyLabelChange.setText("");
                                totalNumberLabelChange.setText("");

                            } else {
                                JOptionPane.showMessageDialog(jf, "请先处理未完成交易");
                            }
                        } else {
                            JOptionPane.showMessageDialog(jf, "无商品记录");
                        }
                    } else {
                        JOptionPane.showMessageDialog(jf, "会员号无效，请重新输入！");
                    }

                    return;
                } else {
                    JOptionPane.showMessageDialog(jf, "请输入会员号");
                }
            }
        }
    }

    /**
     * 挂单查询处理
     */
    class Bt3Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
//            if (memberNumber.getText().length() > 0) {

                long timezero = 0;
                try {
                    timezero = getTimes() / 1000;
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
//                String ds = "select order_id from  es_order where cardNumber in ('"+memberNumber.getText()+"'," +
//                        "'select phone from es_member where cardNumber = "+memberNumber.getText()+"')"
//                        + " and status = "
//                        + OrderStatus.ORDER_NOT_PAY
//                        + " and create_time > " + timezero;
                String ds = "select order_id,sn from  es_order where status = "
                        + OrderStatus.ORDER_NOT_PAY
                        + " and create_time > " + timezero;
                ResultSet rs2 = new SetSql()
                        .setSql(ds);
                try {
                    if (rs2.next()) {
                        while (model.getRowCount() > 0) {
                            model.removeRow(model.getRowCount() - 1);
                        }
                        ResultSet rs = new SetSql()
                                .setSql(ds);
                        try {
                            List<Object[]> list = new LinkedList<Object[]>();
                            while (rs.next()) {
                                long order_ids = rs.getLong("order_id");
                                ResultSet rss = new SetSql()
                                        .setSql("select * from es_order_items where order_id = "
                                                + order_ids + "");
                                while (rss.next()) {
                                    String sqls = "select p.specs ,p.tax_system,unit from es_product p LEFT JOIN " +
                                            "es_goods g on p.goods_id = g.goods_id where p.bar_code = '"
                                            + rss.getString("bar_code")
                                            + "' and p.product_id = "
                                            + rss.getInt("product_id") + "";
                                    if (!O2OMainMenu.internetPass) {
                                        ResultSet rsss = new SetSql()
                                                .setSql(sqls);
                                        while (rsss.next()){
                                        // 改成你的列名
                                        Object[] objects = new Object[] {
                                                rss.getInt("product_id"),
                                                rss.getString("bar_code"),
                                                rss.getString("name"),
                                                rsss.getString("specs"),
                                                OrderStatus.getTaxText(rsss.getInt("tax_system")),
                                                rsss.getString("unit"),
                                                rss.getDouble("taxes"),
                                                rsss.getString("specs"),
                                                ""+ df.format(new BigDecimal(rss.getDouble("price"))),
                                                rss.getInt("num"),
                                                ""+ df.format(new BigDecimal(((rss.getDouble("price")+rss.getDouble("taxes")) * (rss.getInt("num"))))) };
                                        list.add(objects);}
                                    } else {
                                        MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
                                        queryParam.add("sql", sqls);
                                        String url = PropertiesUtil.getIp()
                                                + "/api/shop/goods!getProductSpecValue.do";
                                        ConnectOnlineMethod connect = new ConnectOnlineMethod();
                                        List<JSONObject> lists = new ArrayList<JSONObject>();
                                        try {
                                            String outPut = connect.connectOnline(queryParam,
                                                            url);
                                            if (outPut.length()>2) {
                                                JSONArray array = connect
                                                        .jsonConvertion(outPut);
                                                lists = connect
                                                        .getJsonObjects(array);
                                                for (JSONObject object : lists) {
//                                                    String spec_value = "";
//                                                    if(!(object.getString("spec_value").equals(""))){
//                                                        spec_value = object.getString("spec_value");
//                                                    }
                                                    Object[] objects = new Object[] {
                                                            rss.getInt("product_id"),
                                                            rss.getString("bar_code"),
                                                            rss.getString("name"),
                                                            rss.getString("spec_value"),
                                                            OrderStatus.getTaxText(object.getInt("tax_system")),
                                                            object.get("unit"),
                                                            rss.getDouble("taxes"),
                                                            ""+ rss.getDouble("price"),
                                                            rss.getInt("num"),
                                                            ((rss.getDouble("price")+rss.getDouble("taxes")) * (rss.getInt("num"))) };
                                                    list.add(objects);
                                                }
                                            }
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }

                                    }
                                    boolean c = new SetSql()
                                            .setSqlNotReturn("update es_order set status = "
                                                    + OrderStatus.ORDER_ALLOCATION_YES
                                                    + " where order_id = "
                                                    + order_ids);
                                    if (c) {
                                        new DoBackup()
                                                .realTimeBackup("update es_order set status = "
                                                        + OrderStatus.ORDER_ALLOCATION_YES
                                                        + " where order_id = "
                                                        + order_ids + ";");
                                    }
                                }

                            }
                            data = list.toArray(new Object[0][0]);
                        } catch (SQLException e2) {
                            e2.printStackTrace();
                        }
                        O2OMainMenu.documentNumber.setText(rs2.getString("sn"));
                        tableRefresh(data);
                        totalRefresh();

                        if (data.length > 0) {
                            JOptionPane.showMessageDialog(jf, "挂单查询成功");
                        } else {
                            JOptionPane.showMessageDialog(jf, "无记录");
                        }
                    } else {
                        JOptionPane.showMessageDialog(jf, "无新增挂单记录");
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

//            } else {
//                JOptionPane.showMessageDialog(jf, "请输入会员号");
//            }
        }
    }

    /**
     * 退货
     * */
    public class rejectedListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
 
                if (documentNumber.getText().length() > 0) {
                    if (memberNumber.getText().length() > 0) {
                        int t = table.getSelectedRowCount();
                        if(t>0){
                            Regected rg = new Regected();
                            rg.rr = O2OMainMenu.this;
                            rg.init();
                        }else{
                            JOptionPane.showMessageDialog(jf, "请选择要退货的商品");
                        }

                    } else {
                        JOptionPane.showMessageDialog(jf, "请输入会员号");
                    }
                } else {
                    JOptionPane.showMessageDialog(jf, "请输入订单编号");
                }
            }
          
        }


    /**
     * 订单查询
     * */
    public class orderCheckListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            QueryOrder.getInstance().init();
        }
    }

    /**
     * 收银监听
     */
    public class chashier implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (memberNumber.getText().length() == 0) {
                JOptionPane.showMessageDialog(jf, "请输入会员卡号");
            } else {
                if (table.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(jf, "请输入商品");
                } else {
//                  CashNoButton c = new CashNoButton();
//                  c.mm = O2OMainMenu.this;
//                  c.init();
                }

            }

        }

    }

    /**
     * 
     * 类描述： 创建人：Li Shilin 创建时间：2016-4-1 上午9:24:31 修改人：lsl 修改时间：2016-4-1
     * 上午9:24:31 修改备注：现金支付
     * 
     */
    public class PosChashier implements ActionListener {

        @SuppressWarnings("static-access")
        @Override
        public void actionPerformed(ActionEvent e) {
            // if (memberNumber.getText().length() == 0) {
            // JOptionPane.showMessageDialog(jf, "请输入会员卡号");
            // } else {
            String sn = O2OMainMenu.documentNumber.getText().toString();
            String sql = "select status from es_order where sn = '"+sn+"'";
            ResultSet rs = new SetSql().setSql(sql);
            Integer status = 0;
            try {
                if(rs.next()){
                    status = rs.getInt("status");
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            if(status == 107){
                JOptionPane.showMessageDialog(jf, "请选择退货");
                return;
            }
            if (table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(jf, "请输入商品");
//              jf.disp 
            }else{
                boolean a = false;
                for(int i=0 ;i<O2OMainMenu.table.getRowCount();i++){
                    String cont = O2OMainMenu.table.getValueAt(i, 4).toString();
                    if(cont.contains("保税")||cont.contains("直邮")){
                        a = true;
                    }
                }
                /*String cont = O2OMainMenu.table.getValueAt(0, 4).toString();
                if("全部".equals(cont)){
                    String cont_sys = O2OMainMenu.table.getValueAt(0, 3).toString();
                    if (cont_sys.contains("完税")) {
                        cont = "完税";
                    } else {
                        cont = "保税";
                    }
                }*/
                if (a) {
                    if ("".equals(O2OMainMenu.memberNumber.getText())||"88888888".equals(O2OMainMenu.memberNumber.getText())) {
                        JOptionPane.showMessageDialog(jf, "请输入会员号");
//                    jf.dispose();
                        return;
                    }
                    if (O2OMainMenu.addrId.getText().length() > 0) {
                        PosAndCashNoButton pac = new PosAndCashNoButton();
                        pac.mm = O2OMainMenu.this;
                        //
                        WeiPay wp = new WeiPay();
                        wp.www = O2OMainMenu.this;
                        pac.init();
                    } else {
                        JOptionPane.showMessageDialog(jf, "请选择收货地址");
                    }
                } else {
                    if(memberNumber.getText().equals("")){
                        if(O2OMainMenu.internetPass){
                            ConnectOnlineMethod connect = new ConnectOnlineMethod();
                            List<JSONObject> list = new ArrayList();
                            MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
                            queryParam.add("store_id", PropertiesUtil.getStoreId());
                            String url = PropertiesUtil.getIp() +"/api/shop/member!getCard.do";
                            String outPut = null;
                            try {
                                outPut = connect.connectOnline(queryParam, url);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            JSONArray array = connect.jsonConvertion(outPut);
                            if(array.length()>0){
                                list = connect.getJsonObjects(array);
                                String s = ((JSONObject)list.get(0)).get("card_id").toString();
                                memberNumber.setText(s);
                            }else{
                                memberNumber.setText("88888888");
                            }
                        }else{
                            memberNumber.setText("88888888");
                        }
                    }
                    PosAndCashNoButton pac = new PosAndCashNoButton();
                    pac.mm = O2OMainMenu.this;
                    //
                    WeiPay wp = new WeiPay();
                    wp.www = O2OMainMenu.this;
                    pac.init();
                }
            }
            
            
        }
    }

    /**
     * 
     * 类描述： 创建人：Li Shilin
     * 
     */
    public class AddAddress implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (memberNumber.getText().length() == 0) {
                JOptionPane.showMessageDialog(jf, "请输入会员卡号");
            } else {
                MemberAddress mAdd = MemberAddress.getInstance();
                mAdd.mm = O2OMainMenu.this;
                mAdd.init();

            }

        }

    }

    /**
     * 
     * 类描述： 创建人：Li Shilin
     * 
     */
    public class DetailAddressListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (memberNumber.getText().length() == 0) {
                JOptionPane.showMessageDialog(jf, "请输入会员卡号");
            } else {
                DetailAddress dAddress = DetailAddress.getInstance();
                dAddress.omm = O2OMainMenu.this;
                dAddress.init();

            }

        }

    }

    public class Helper implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (ShowTips.visible) {
                ShowTips.jf.setVisible(false);
                ShowTips.visible = false;
            } else {
                ShowTips.jf.setVisible(true);
                ShowTips.visible = true;
            }

        }

    }

    // 退出
    public class exitbn implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Robot rb = null;
            try {
                rb = new Robot();
            } catch (AWTException e1) {
                e1.printStackTrace();
            }
            rb.keyRelease(KeyEvent.VK_CONTROL);
            Object[] options = { "确定" };
            int response = JOptionPane.showOptionDialog(jf, "是否退出", "",
                    JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            if (response == 0) {
                System.exit(0);
            }

        }

    }

    /**
     * 班结按钮监听
     */
    public class operatorAccount implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object[][] data4 = null;
            long time0 = 0;
            String paymoney1 = null;
            try {
                time0 = getTimes();
                
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            String dateNow = new timeToDates().getTimeToDates(System
                    .currentTimeMillis());
            ResultSet rsResultSet = new SetSql()
                    .setSql("select * from es_classes");
            int classes = 1;
            try {
                rsResultSet.next();
                classes = rsResultSet.getInt("classes");
                //重点标明。下面这句是班结时删除挂单或者挂单查询成功的订单
                String gdsn = null; 
                ResultSet rsForDay1 = new SetSql()
                .setSql("select sn from es_order where status = 100 or status = 104 ");
                while (rsForDay1.next()){
                gdsn = rsForDay1.getString("sn"); 
                }
                boolean scgd = new SetSql().setSqlNotReturn("DELETE FROM es_order WHERE sn ='" + gdsn + "'" );
                boolean scmx = new SetSql().setSqlNotReturn("DELETE FROM es_order_items WHERE sn ='" + gdsn + "'" );

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            while (model.getRowCount() > 0) {
                model.removeRow(model.getRowCount() - 1);
            }
            try {
                //购买商品订单详情
                String sql = "select  o.bar_code,o.name, o.product_id," +
                        "o.spec_value,o.tax_system,o.unit,o.taxes," +
                        "o.price, sum(o.num) as num,sum(o.taxes * o.num) AS taxesall, sum(o.price * o.num) " +
                        "as priceall  from (SELECT * from es_order_items  " +
                        "where es_order_items.order_id = any" +
                        "(select order_id from es_order where es_order.create_time > "
                        + time0/ 1000
                        + " and user_name = '"
                        + operator.getText()
                        + "' and date = '"
                        + dateNow
                        + "' and classes = "
                        + classes
                        + " and sn not like 'SO20' )) as o  left join es_product p on p.bar_code = o.bar_code " +
                        "and p.product_id = o.product_id " +
                        "LEFT JOIN es_goods g ON p.goods_id = g.goods_id " +
                        " group by o.product_id ";
                ResultSet rss = new SetSql().setSql(sql);
                
                ResultSet rs4 = new SetSql()
                        .setSql("select  o.bar_code,o.name, o.spec_value,o.product_id," +
                                "o.tax_system,o.unit,o.taxes, o.price, sum(o.num) as num,sum(o.taxes * o.num) AS taxesall," +
                                " sum(o.price * o.num) + sum(o.taxes * o.num) as priceall  from " +
                                "(SELECT * from es_order_items where es_order_items.sn = any" +
                                "(select sn from es_order where es_order.create_time > "
                                + time0/ 1000
                                + " and user_name = '"
                                + operator.getText()
                                + "'and date = '"
                                + dateNow
                                + "' and classes = "
                                + classes
                                + ") and state = '103' ) as o  left join es_product p on p.bar_code = o.bar_code " +
                                "and p.product_id = o.product_id left join " +
                                "es_goods g on p.goods_id = g.goods_id " +
                                " group by o.product_id ");
                //退货金额
                ResultSet rs8 = new SetSql().setSql("SELECT sum(price * num) as paymoney from es_order_items where es_order_items.sn = any" +
                        "(select sn from es_order where es_order.create_time > "
                        + time0/ 1000
                        + " and user_name = '"
                        + operator.getText()
                        + "' and date = '"
                        + dateNow
                        + "' and classes = "
                        + classes
                        + ") and state = '103' ");
                
                ResultSet rs5 = new SetSql()
                        .setSql("select payment_id,sum(cardAmount) as cardAmounts, sum(cashAmount) as cashAmounts," +
                                "sum(paymoney) as paymoneys from es_order o where o.create_time > "
                                + time0
                                / 1000
                                + " and o.user_name = '"
                                + operator.getText()
                                + "'   and date = '"
                                + dateNow + "' and classes = " + classes + " and sn like 'SO20%'");  
              ResultSet rs6 = new SetSql()
                        .setSql("SELECT sum(price * num)+sum(taxes * num) as allprice from es_order_items where es_order_items.sn = any" +
                                "(select sn from es_order where es_order.create_time > "
                                + time0/ 1000
                                + " and user_name = '"
                                + operator.getText()
                                + "' and date = '"
                                + dateNow
                                + "' and classes = "
                                + classes
                                + ") and state not like '103' ");
              ResultSet rs9 = new SetSql()
              .setSql("select payment_id,sum(order_amount) as orderamount from es_order o where o.create_time > "
                      + time0
                      / 1000
                      + " and o.user_name = '"
                      + operator.getText()
                      + "'   and date = '"
                      + dateNow + "' and classes = " + classes + " and sn like 'SO20%'" 
                      + " GROUP BY payment_id"); 
                // normal
                List<Object[]> list = new LinkedList<Object[]>();
                //获取订单的总笔数
                String sql7 = "select count(sn), sum(goods_num) as nums, sum(order_amount) as orderAmount from es_order o where o.create_time >"+time0/ 1000+" and o.classes = "+classes+"  and sn like 'SO20%' and status <> 100";
                ResultSet rs7 = new SetSql().setSql(sql7);
                ResultSet rss8 = new SetSql()
                .setSql("select"
                	   +" sum(discountAmount) AS discountAmount,"
                	   +" sum(if((payment_id = 4),discountAmount,0)) AS weidiscount,"
                	   +" sum(if((payment_id = 5),discountAmount,0)) AS alidiscount,"
                	   +" sum(if((payment_id = 6),discountAmount,0)) AS cashdiscount,"
                	   +" sum(if((payment_id = 7),discountAmount,0)) AS carddiscount"
                	   +" FROM es_order o where o.create_time > "
                      + time0
                      / 1000
                      + " and o.user_name = '"
                      + operator.getText()
                      + "'   and date = '"
                      + dateNow + "' and classes = " + classes + " and sn like 'SO20%' and status = '107'");
                ResultSet rss9 = new SetSql()
                .setSql("select sum(order_amount-discountAmount) as paymoney1 from es_order o where o.create_time > "
                      + time0
                      / 1000
                      + " and o.user_name = '"
                      + operator.getText()
                      + "'   and date = '"
                      + dateNow + "' and classes = " + classes + " and sn like 'SO20%'" );
                while(rss9.next()){
                	paymoney1 = df.format(new BigDecimal(rss9.getDouble("paymoney1")));
                }
                //查询商品规格税制计量单位的sql
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
                            ""+ df.format(new BigDecimal(rss.getDouble("price"))),
                            rss.getInt("num"),
                            df.format(new BigDecimal(Double.parseDouble(rss.getDouble("priceall")+"")+Double.parseDouble(rss.getDouble("taxesall")+""))) };
                    list.add(objects);
                }

                data = list.toArray(new Object[0][0]);
                // regected 已退货退货
                List<Object[]> list4 = new LinkedList<Object[]>();
                double regectedCashAmount = 0;
                while (rs4.next()) {
                    // 改成你的列名
                    Object[] objects = new Object[] {
                            rs4.getInt("product_id"),
                            rs4.getString("bar_code"),
                            rs4.getString("name"),
                            rs4.getString("spec_value"),
                            OrderStatus.getTaxText(rs4.getInt("tax_system")),
                            rs4.getString("unit"),
                            rs4.getDouble("taxes"),
                            ""+ df.format(new BigDecimal(rs4.getDouble("price"))),
                            rs4.getInt("num"),
                            df.format(new BigDecimal(Double.parseDouble(rs4.getDouble("priceall")+""))) };
//                    df.format(new BigDecimal(Double.parseDouble(rs4.getDouble("priceall")+"")+Double.parseDouble(rs4.getDouble("taxesall")+"")))}; 
                    list4.add(objects);
                }

                data4 = list4.toArray(new Object[0][0]);
                // card and cash
                //添加微信和支付宝金额
                String weipayAmount = null;
                String alipayAmount = null;
                rss8.next();
                while (rs9.next()) {
                    if(rs9.getInt("payment_id") == 4){
                        weipayAmount = df.format(new BigDecimal(rs9.getDouble("orderamount"))
                        .subtract(new BigDecimal(rss8.getDouble("weidiscount"))));
                        Prient.weipayAmount = weipayAmount;
                    }
                    if(rs9.getInt("payment_id") == 5){
                        alipayAmount = df.format(new BigDecimal(rs9.getDouble("orderamount"))
                        .subtract(new BigDecimal(rss8.getDouble("alidiscount"))));
                        Prient.alipayAmount = alipayAmount;
                    }
                }
                while(rs8.next()){
                    regectedCashAmount += rs8.getDouble("paymoney");
                }
            rs5.next();
            rs6.next();
            rs7.next();
            String cardNumString = df.format(new BigDecimal(rs5
                    .getDouble("cardAmounts")).subtract(new BigDecimal(rss8.getDouble("carddiscount"))));
            String cashNumString = df.format(new BigDecimal(rs5
                    .getDouble("cashAmounts")).subtract(new BigDecimal(rss8.getDouble("cashdiscount"))));
            String paymoney =df.format(new BigDecimal(rs6
                    .getDouble("allprice")));
            String orderAmount = df.format(new BigDecimal(rs7.getDouble("orderAmount")));
            String discountAmount = df.format(new BigDecimal(rss8.getDouble("discountAmount")));
            int nums = rs7.getInt("nums");
            Prient.orderAmount = orderAmount;
            Prient.nums = nums;
            Prient.paymoney1 =df.format(Double.parseDouble(paymoney1)-regectedCashAmount);
            Prient.paymoney = paymoney;
            Prient.cardAmount = cardNumString;
            Prient.cashAmount = cashNumString;
            Prient.discountAmount = discountAmount;
  //              String cardNumString = df.format(new BigDecimal(rs5
 //                       .getDouble("cardAmounts")));
//                String cashNumString = df.format(new BigDecimal(rs5
//                        .getDouble("cashAmounts")));
//                String paymoney =df.format(new BigDecimal(rs5
//                        .getDouble("paymoneys")));
//                Prient.paymoney=paymoney;
                Prient.regectedCashAmount = df.format(new BigDecimal(regectedCashAmount));
//               Prient.cardAmount = cardNumString;
//               Prient.cashAmount = cashNumString;
                Prient.countOrder = rs7.getInt(1)+"";
                double cashActual = rs5.getDouble("cashAmounts");
                Prient.cashActual = df.format(new BigDecimal(cashActual));
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            tableRefresh(data);
            totalRefresh();
            long timeNow = System.currentTimeMillis() / 1000;
            new SetSql()
                    .setSqlNotReturn("insert into account_data(user_name,goods_amount,goods_num,loan_type,create_time,date) values('"
                            + operator.getText()
                            + "',"
                            + allPrice
                            + ","
                            + allCount
                            + ",2,"
                            + timeNow
                            + ",'"
                            + dateNow
                            + "')");
            timeNowDay = timeNow;
            dateNowDay = dateNow;
            classesDay = classes;
            data4Day = data4;
            new SwingWorker<Long, Void>() {
                @Override
                protected Long doInBackground() throws Exception {
                    
                    Gif2.show();
                    utrue = new DataOutAndIn().accountOutRun();
                    return null;
                }
                @Override
                protected void done() {
                    Gif2.close();
                    if (utrue) {
                        new SetSql()
                                .setSqlNotReturn("update es_shift set exit_time = "
                                        + timeNowDay
                                        + " , status = "
                                        + OrderStatus.ORDER_PAY
                                        + " where date = '"
                                        + dateNowDay
                                        + "' and classes = "
                                        + classesDay
                                        + " and user_code = '"
                                        + operator.getText() + "'");
                        Prient p = new Prient();
                        Prient.data4 = data4Day;
                        Prient.mm = O2OMainMenu.this;
                        p.start();
                        try {
                            jf.dispose();
                            Thread.sleep(3000);
                            System.exit(0);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }

                        JOptionPane.showMessageDialog(jf, "导出数据成功");
                    } else {
                        JOptionPane.showMessageDialog(jf, "导出数据失败");
                    }
                }
            }.execute();

        }

    }

    /**
     * 日结按钮监听
     */
    public class dayAccount implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object[][] data4 = null;
            String paymoney1 = null;
            double discountAmount = 0.00;
            /**
             * 查询未日结的日期
             * */
            ResultSet rsForDayForJudge = new SetSql()
                    .setSql("select create_time from es_order where balance_status = 0");
            try {
                if (rsForDayForJudge.next()) {// 判断是否有结算状态为0的商品
                    ResultSet rsForDay = new SetSql()
                            .setSql("select create_time from es_order where balance_status = 0");

                        //重点标明。下面这几句句是删除日结时挂单或者挂单查询成功的订单
                        String gdsn = null; 
                        ResultSet rsForDay1 = new SetSql()
                        .setSql("select sn from es_order where status = 100 or status = 104 ");
                        while (rsForDay1.next()){
                        gdsn = rsForDay1.getString("sn"); 
                        }
                        boolean scgd = new SetSql().setSqlNotReturn("DELETE FROM es_order WHERE sn ='" + gdsn + "'" );
                        boolean scmx = new SetSql().setSqlNotReturn("DELETE FROM es_order_items WHERE sn ='" + gdsn + "'" );
                    try {
                        List<Object> list = new LinkedList<Object>();
                        while (rsForDay.next()) {

                            // 改成你的列名
                            Object objects = rsForDay.getLong("create_time");
                            list.add(objects);
                        }

                        Object[] data3 = list.toArray();
                        String[] strs = new String[data3.length];
                        Long[] dataLongs = new Long[data3.length];
                        for (int i = 0; i < data3.length; i++) {
                            strs[i] = data3[i].toString();
                            dataLongs[i] = Long.parseLong(strs[i]);
                        }

                        String[] newDate = new timeToDates()
                                .dateNoSame(dataLongs);

                        for (int j = 0; j < newDate.length; j++) {
                            Long timeBegin = new timeToDates()
                                    .dateSToTimeLBegin(newDate[j]);
                            Long timeEnd = new timeToDates()
                                    .dateSToTimeLEnd(newDate[j]);

                            boolean c = new SetSql()
                                    .setSqlNotReturn("update es_order set balance_status = 1 where date = '"
                                            + newDate[j] + "'");
                            if (c) {
                                new DoBackup()
                                        .realTimeBackup("update es_order set balance_status = 1 where date = '"
                                                + newDate[j] + "';");
                            }

                            while (model.getRowCount() > 0) {
                                model.removeRow(model.getRowCount() - 1);
                            }
                            try {
                                ResultSet rss = new SetSql()
                                        .setSql(
                                                "select  o.bar_code,o.name, o.spec_value,o.tax_system,p.product_id,o.unit,o.taxes, o.price, " +
                                                "sum(o.num) as num,sum(o.taxes * o.num)as taxesall, sum(o.price * o.num) as priceall from " +
                                                "(SELECT * from es_order_items  where es_order_items.order_id =" +
                                                " any(select order_id from es_order o where o.create_time > "
                                                + timeBegin
                                                / 1000
                                                + " and o.create_time < "
                                                + timeEnd
                                                / 1000
                                                + " and sn not like 'SO20' )) as o  left join es_product p on p.bar_code = o.bar_code " +
                                                "and p.product_id = o.product_id left join es_goods g on p.goods_id = g.goods_id" +
                                                " LEFT JOIN es_order sp ON o.order_id = sp.order_id" +
                                                " group by o.product_id ");
                                ResultSet rs4 = new SetSql()
                                        .setSql("select  o.bar_code,o.name,o.product_id, o.spec_value,o.tax_system,o.unit,o.taxes," +
                                                " o.price, sum(o.num) as num,sum(o.taxes * o.num) as taxesall, sum(o.price * o.num)+sum(o.taxes * o.num) as priceall " +
                                                " from (SELECT * from es_order_items  where es_order_items.order_id =" +
                                                " any(select order_id from es_order  where es_order.create_time > "
                                                + timeBegin
                                                / 1000
                                                + " and es_order.create_time < "
                                                + timeEnd
                                                / 1000
                                                + ") and state like '103') as o  left join es_product p on p.bar_code = o.bar_code" +
                                                " and p.product_id = o.product_id left join es_goods g on p.goods_id = g.goods_id" +
                                                " LEFT JOIN es_order sp ON o.order_id = sp.order_id" +
                                                " group by o.product_id ");
                                //退货金额
                                ResultSet rs8 = new SetSql().setSql("SELECT sum(price * num) as paymoney from es_order_items  where es_order_items.sn =" +
                                                " any(select sn from es_order  where es_order.create_time > "
                                                + timeBegin
                                                / 1000
                                                + " and es_order.create_time < "
                                                + timeEnd
                                                / 1000
                                                + ") and state = '103'");
                                ResultSet rs5 = new SetSql()
                                        .setSql("select sum(cardAmount) as cardAmounts, sum(cashAmount) " +
                                                "as cashAmounts,sum(paymoney) as paymoneys from es_order o where o.create_time > "
                                                + timeBegin
                                                / 1000
                                                + " and o.create_time < "
                                                + timeEnd
                                                / 1000
                                                + " and sn not like 'SO20%' and o.`status` in ("+OrderStatus.ORDER_COMPLETE+"," +
                                       ""+OrderStatus.ORDER_PAY_CONFIRM+")");
                                
                                
                                
                                ResultSet rs6 = new SetSql()
                                .setSql("SELECT sum(es_order.order_amount) as totalAmount,es_order.payment_id from es_order where es_order.create_time > "
                                        + timeBegin
                                        / 1000
                                        + " and es_order.create_time < "
                                        + timeEnd
                                        / 1000
                                        + " and CASE WHEN sn REGEXP 'SO[4]' THEN `status` = 102 ELSE `status` = 107 END "
                                        + " and sn not like 'SO20%' GROUP BY es_order.payment_id ");
                                
                                ResultSet rs9 = new SetSql()
                                .setSql("SELECT sum(price * num)+sum(taxes * num) as paymoneys from es_order_items  where es_order_items.sn =" +
                                        " any(select sn from es_order  where es_order.create_time > "
                                        + timeBegin
                                        / 1000
                                        + " and es_order.create_time < "
                                        + timeEnd
                                        / 1000
                                        + ") and state not like '103'");
                                
                                String sql7 = "SELECT count(sn) from es_order where es_order.create_time > "+ timeBegin/1000+ " and es_order.create_time < "+timeEnd/1000+" and sn like 'SO20%'";
                                ResultSet rs7 = new SetSql().setSql(sql7);
                                ResultSet rss9 = new SetSql()
                                .setSql("select sum(order_amount-discountAmount) as paymoney1, sum(discountAmount) as discountAmount from es_order  where es_order.create_time > "
                                        + timeBegin
                                        / 1000
                                        + " and es_order.create_time < "
                                        + timeEnd
                                        / 1000
                                        + " and sn like 'SO20%'");
                                ResultSet rss8 = new SetSql()
                                .setSql("select"
                                	   +" sum(if((payment_id = 4),discountAmount,0)) AS weidiscount,"
                                	   +" sum(if((payment_id = 5),discountAmount,0)) AS alidiscount,"
                                	   +" sum(if((payment_id = 6),discountAmount,0)) AS cashdiscount,"
                                	   +" sum(if((payment_id = 7),discountAmount,0)) AS carddiscount"
                                	   +" FROM es_order where es_order.create_time > "
                                        + timeBegin
                                        / 1000
                                        + " and es_order.create_time < "
                                        + timeEnd
                                        / 1000
                                        + " and sn like 'SO20%' and status = '107'");
                                while(rss9.next()){
                                	paymoney1 = df.format(new BigDecimal(rss9.getDouble("paymoney1")));
                                	discountAmount = rss9.getDouble("discountAmount");
                                }
                                // normal
                                List<Object[]> list2 = new LinkedList<Object[]>();
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
                                            ""+ df.format(new BigDecimal(rss.getDouble("price"))),
                                            rss.getInt("num"),
//                                            df.format(new BigDecimal(rss.getDouble("priceall")+rss.getDouble("taxesall"))) };
                                            df.format(new BigDecimal(Double.parseDouble(rss.getDouble("priceall")+"")+Double.parseDouble(rss.getDouble("taxesall")+""))) };
                                    list2.add(objects);

                                }

                                data = list2.toArray(new Object[0][0]);
                                // regected
                                List<Object[]> list4 = new LinkedList<Object[]>();
                                double regectedCashAmount = 0;
                                while (rs4.next()) {

//                                    regectedCashAmount += rs4.getDouble("priceall");
                                    // 改成你的列名
                                    Object[] objects = new Object[] {
                                            rs4.getInt("product_id"),
                                            rs4.getString("bar_code"),
                                            rs4.getString("name"),
                                            rs4.getString("spec_value"),
                                            OrderStatus.getTaxText(rs4.getInt("tax_system")),
                                            rs4.getString("unit"),
                                            rs4.getDouble("taxes"),
                                            ""+ df.format(new BigDecimal(rs4.getDouble("price"))),
                                            rs4.getInt("num"),
                                            df.format(new BigDecimal(Double.parseDouble(rs4.getDouble("priceall")+""))) };
//                                            df.format(new BigDecimal(Double.parseDouble(rs4.getDouble("priceall")+"")+Double.parseDouble(rs4.getDouble("taxesall")+""))) };
                                    list4.add(objects);

                                }

                                data4 = list4.toArray(new Object[0][0]);
                                // card and cash
                                rs5.next();
                                rss8.next();
                                rs9.next();
                                PrientDay.data4 = data4;
                                String cardNumString = df
                                        .format(new BigDecimal(rs5
                                                .getDouble("cardAmounts")).subtract(new BigDecimal(rss8.getDouble("carddiscount"))));
                                String cashNumString = df
                                        .format(new BigDecimal(rs5
                                                .getDouble("cashAmounts")).subtract(new BigDecimal(rss8.getDouble("cashdiscount"))));
                                String paymoney = df.format(new BigDecimal(rs9
                                        .getDouble("paymoneys")));
                                PrientDay.cardAmount = cardNumString;
                                PrientDay.cashAmount = cashNumString;
                                PrientDay.paymoney = paymoney;
                                PrientDay.discountAmount = discountAmount;
                                rs7.next();
                                PrientDay.countOrder = rs7.getInt(1)+"";
                                //添加退款金额
                                while(rs8.next()){
                                     regectedCashAmount += rs8.getDouble("paymoney");
                                }
                                PrientDay.regectedCashAmount = df.format(new BigDecimal(regectedCashAmount));
                                PrientDay.paymoney1 = df.format(Double.parseDouble(paymoney1)-regectedCashAmount);
                                double cashActual = rs5.getDouble("cashAmounts")- regectedCashAmount;
                                PrientDay.cashActual = df
                                        .format(new BigDecimal(cashActual));
                                //添加微信和支付宝金额
                                String weipayAmount = null;
                                String alipayAmount = null;
                                while (rs6.next()) {
                                    if(rs6.getInt("payment_id") == 4){
                                            weipayAmount = df.format(new BigDecimal(rs6.getDouble("totalAmount"))
                                            .subtract(new BigDecimal(rss8.getDouble("weidiscount"))));
                                            PrientDay.weipayAmount = weipayAmount;
                                    }
                                    if(rs6.getInt("payment_id") == 5){
                                            alipayAmount = df.format(new BigDecimal(rs6.getDouble("totalAmount"))
                                            .subtract(new BigDecimal(rss8.getDouble("alidiscount"))));
                                            PrientDay.alipayAmount = alipayAmount;
                                            
                                    }
                                }
                            } catch (SQLException e2) {
                                e2.printStackTrace();
                            }
                            tableRefresh(data);
                            totalRefresh();
                            long timeNow = System.currentTimeMillis();
                            String dataNow = new timeToDates()
                                    .getTimeToDates(timeNow);
                            // 判断是否进行过日结，有则删掉
                            ResultSet rsJudge = new SetSql()
                                    .setSql("select * from es_shift_day where date = '"
                                            + dataNow + "'");
                            if (rsJudge.next()) {
                                new SetSql()
                                        .setSqlNotReturn("delete from account_data where date ='"
                                                + dataNow
                                                + "' and loan_type = 1");
                                new SetSql()
                                        .setSqlNotReturn("delete from es_shift_day where date ='"
                                                + dataNow + "'");
                            }
                            new SetSql()
                                    .setSqlNotReturn("insert into account_data(user_name,goods_amount,goods_num,loan_type,create_time,date) values('dayAccount',"
                                            + allPrice
                                            + ","
                                            + allCount
                                            + ",1,"
                                            + timeNow
                                            / 1000
                                            + ",'"
                                            + dataNow
                                            + "')");
                            new SetSql()
                                    .setSqlNotReturn("insert into es_shift_day(user_code,date,create_time) values('"
                                            + operator.getText()
                                            + "','"
                                            + dataNow
                                            + "',"
                                            + timeNow
                                            / 1000
                                            + ")");
                            PrientDay p = new PrientDay();
                            p.setData(data);
                            PrientDay.dateNow = newDate[j];
                            PrientDay.mm = O2OMainMenu.this;
                            p.start();
                            JOptionPane.showMessageDialog(jf, "日结完成");
                            O2OMainMenu.goodsCode.setText("");
                            O2OMainMenu.totalNumberLabelChange.setText("");
                            O2OMainMenu.totalMoneyLabelChange.setText("");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                            while (model.getRowCount() > 0) {
                                model.removeRow(model.getRowCount() - 1);
                            }
                        }// for()
                    } catch (SQLException e3) {
                        e3.printStackTrace();
                    }
                } else {// 无结算状态为0的订单记录
                    long time = System.currentTimeMillis();
                    String dateNewString = new timeToDates()
                            .getTimeToDates(time);
                    Long timeBegin = new timeToDates()
                            .dateSToTimeLBegin(dateNewString);
                    Long timeEnd = new timeToDates()
                            .dateSToTimeLEnd(dateNewString);

                    boolean c = new SetSql()
                            .setSqlNotReturn("update es_order set balance_status = 1 where date = '"
                                    + dateNewString + "'");
                    if (c) {
                        new DoBackup()
                                .realTimeBackup("update es_order set balance_status = 1 where date = '"
                                        + dateNewString + "';");
                    }
                    while (model.getRowCount() > 0) {
                        model.removeRow(model.getRowCount() - 1);
                    }
                    try {
                        ResultSet rss = new SetSql().setSql("select  o.bar_code,o.name, o.spec_value,o.tax_system,p.product_id,o.unit,o.taxes, o.price, " +
                                "sum(o.num) as num,sum(o.taxes * o.num)as taxesall, sum(o.price * o.num) as priceall from " +
                                "(SELECT * from es_order_items  where es_order_items.order_id =" +
                                " any(select order_id from es_order o where o.create_time > "
                                + timeBegin
                                / 1000
                                + " and o.create_time < "
                                + timeEnd
                                / 1000
                                + " and sn not like 'SO20' )) as o  left join es_product p on p.bar_code = o.bar_code " +
                                "and p.product_id = o.product_id left join es_goods g on p.goods_id = g.goods_id" +
                                " LEFT JOIN es_order sp ON o.order_id = sp.order_id" +
                                " group by o.product_id ");

                        List<Object[]> list2 = new LinkedList<Object[]>();
                        ResultSet rs4 = new SetSql()
                                .setSql("select  o.bar_code,o.name, p.spec_value,p.product_id,sum(o.taxes * o.num)as taxesall," +
                                        "p.tax_system,g.unit,o.taxes, o.price, sum(o.num) as num, sum(o.price * o.num)+sum(o.taxes * o.num) " +
                                        "as priceall  from (SELECT * from es_order_items  where es_order_items.order_id =" +
                                                " any(select order_id from es_order  where es_order.create_time > "
                                                + timeBegin
                                                / 1000
                                                + " and es_order.create_time < "
                                                + timeEnd
                                                / 1000
                                                + ") and state like '103') as o  left join es_product p on p.bar_code = o.bar_code " +
                                        "and p.product_id = o.product_id left join es_goods g on p.goods_id = g.goods_id" +
                                        " group by o.product_id ");
                        ResultSet rs8 = new SetSql().setSql("SELECT sum(price * num) as paymoney from es_order_items  where es_order_items.sn =" +
                                " any(select sn from es_order  where es_order.create_time > "
                                + timeBegin
                                / 1000
                                + " and es_order.create_time < "
                                + timeEnd
                                / 1000
                                + ") and state = '103'");
                        ResultSet rs5 = new SetSql()
                                .setSql("select sum(cardAmount) as cardAmounts, sum(cashAmount) " +
                                        "as cashAmounts,sum(paymoney) as paymoneys from es_order o where o.create_time > "
                                        + timeBegin
                                        / 1000
                                        + " and o.create_time < "
                                        + timeEnd
                                        / 1000
                                        + " and sn not like 'SO20%' and o.`status` in ("+OrderStatus.ORDER_COMPLETE+"," +
                               ""+OrderStatus.ORDER_PAY_CONFIRM+")");
                        ResultSet rs6 = new SetSql()
                        .setSql("SELECT sum(es_order.order_amount) as totalAmount,es_order.payment_id from es_order where sn not like 'SO20%' and es_order.create_time > "
                                + timeBegin
                                / 1000
                                + " and es_order.create_time < "
                                + timeEnd
                                / 1000
                                + " and CASE WHEN sn REGEXP 'SO[4]' THEN `status` = 102 ELSE `status` = 107 END "
                                + " GROUP BY es_order.payment_id ");
                        
                        ResultSet rs9 = new SetSql()
                        .setSql("select sum(order_amount) as paymoneys from es_order  where es_order.create_time > "
                                + timeBegin
                                / 1000
                                + " and es_order.create_time < "
                                + timeEnd
                                / 1000
                                + " and sn like 'SO20%' and status <> 100");
                        // normal
                        String sql7 = "SELECT count(sn) from es_order where es_order.create_time > "+ timeBegin/1000+ " and es_order.create_time < "+timeEnd/1000+" and sn like 'SO20%'";
                        ResultSet rs7 = new SetSql().setSql(sql7);
                        ResultSet rss8 = new SetSql()
                        .setSql("select"
                        	   +" sum(if((payment_id = 4),discountAmount,0)) AS weidiscount,"
                        	   +" sum(if((payment_id = 5),discountAmount,0)) AS alidiscount,"
                        	   +" sum(if((payment_id = 6),discountAmount,0)) AS cashdiscount,"
                        	   +" sum(if((payment_id = 7),discountAmount,0)) AS carddiscount"
                        	   +" FROM es_order where es_order.create_time > "
                                + timeBegin
                                / 1000
                                + " and es_order.create_time < "
                                + timeEnd
                                / 1000
                                + " and sn like 'SO20%' and status = '107'");
                        ResultSet rss9 = new SetSql()
                        .setSql("select sum(order_amount-discountAmount) as paymoney1, sum(discountAmount) as discountAmount from es_order  where es_order.create_time > "
                                + timeBegin
                                / 1000
                                + " and es_order.create_time < "
                                + timeEnd
                                / 1000 
                                + " and sn like 'SO20%'");
                        while(rss9.next()){
                        	paymoney1 = df.format(new BigDecimal(rss9.getDouble("paymoney1")));
                        	discountAmount = rss9.getDouble("discountAmount");
                        }
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
                                    ""+ df.format(new BigDecimal(rss.getDouble("price"))),
                                    rss.getInt("num"),
                                    df.format(new BigDecimal(rss.getDouble("priceall")+rss.getDouble("taxesall"))) };
                            list2.add(objects);
                        }

                        data = list2.toArray(new Object[0][0]);
                        // regected
                        List<Object[]> list4 = new LinkedList<Object[]>();
                        double regectedCashAmount = 0;
                        while (rs4.next()) {
                            // 改成你的列名
                            Object[] objects = new Object[] {
                                    rs4.getInt("product_id"),
                                    rs4.getString("bar_code"),
                                    rs4.getString("name"),
                                    rs4.getString("spec_value"),
                                    OrderStatus.getTaxText(rs4.getInt("tax_system")),
                                    rs4.getString("unit"),
                                    rs4.getDouble("taxes"),
                                    ""+ df.format(new BigDecimal(rs4.getDouble("price"))),
                                    rs4.getInt("num"),
                                    df.format(new BigDecimal(rs4.getDouble("priceall"))) };
//                                  df.format(new BigDecimal(rs4.getDouble("priceall")+rs4.getDouble("taxesall"))) };
                            list4.add(objects);

                        }

                        data4 = list4.toArray(new Object[0][0]);
                        while(rs8.next()){
                            regectedCashAmount += rs8.getDouble("paymoney");
                        }
                        // card and cash
                        rs5.next();
                        rss8.next();
                        rs9.next();
                        String cardNumString = df.format(new BigDecimal(rs5
                                .getDouble("cardAmounts")).subtract(new BigDecimal(rss8.getDouble("carddiscount"))));
                        String cashNumString = df.format(new BigDecimal(rs5
                                .getDouble("cashAmounts")).subtract(new BigDecimal(rss8.getDouble("cashdiscount"))));
                        String paymoney =df.format(new BigDecimal(rs9
                                .getDouble("paymoneys")));
                        PrientDay.data4 = data4;
                        rs7.next();
                        PrientDay.countOrder = rs7.getInt(1)+"";
                        PrientDay.cardAmount = cardNumString;
                        PrientDay.cashAmount = cashNumString;
                        PrientDay.paymoney = paymoney;
                        PrientDay.discountAmount = discountAmount;
                        PrientDay.regectedCashAmount = df.format(new BigDecimal(regectedCashAmount));
                        PrientDay.paymoney1 = df.format(Double.parseDouble(paymoney1)-regectedCashAmount);
                        double cashActual = rs5.getDouble("cashAmounts")
                                - regectedCashAmount;
                        PrientDay.cashActual = df.format(new BigDecimal(
                                cashActual));
                        //添加微信和支付宝金额
                        String weipayAmount = null;
                        String alipayAmount = null;
                        while (rs6.next()) {
                            if(rs6.getInt("payment_id") == 4){
                                    weipayAmount = df.format(new BigDecimal(rs6.getDouble("totalAmount"))
                                    .subtract(new BigDecimal(rss8.getDouble("weidiscount"))));
                                    PrientDay.weipayAmount = weipayAmount;
                            }
                            if(rs6.getInt("payment_id") == 5){
                                    alipayAmount = df.format(new BigDecimal(rs6.getDouble("totalAmount"))
                                    .subtract(new BigDecimal(rss8.getDouble("alidiscount"))));
                                    PrientDay.alipayAmount = alipayAmount;
                            }
                        }
                    } catch (SQLException e2) {
                        e2.printStackTrace();
                    }
                    tableRefresh(data);
                    totalRefresh();
                    long timeNow = System.currentTimeMillis();
                    String dataNow = new timeToDates().getTimeToDates(timeNow);
                    // 判断是否进行过日结，有则删掉
                    ResultSet rsJudge = new SetSql()
                            .setSql("select * from es_shift_day where date = '"
                                    + dataNow + "'");
                    if (rsJudge.next()) {
                        new SetSql()
                                .setSqlNotReturn("delete from account_data where date ='"
                                        + dataNow + "' and loan_type = 1");
                        new SetSql()
                                .setSqlNotReturn("delete from es_shift_day where date ='"
                                        + dataNow + "'");
                    }
                    new SetSql()
                            .setSqlNotReturn("insert into account_data(user_name,goods_amount,goods_num,loan_type,create_time,date) values('dayAccount',"
                                    + allPrice
                                    + ","
                                    + allCount
                                    + ",1,"
                                    + timeNow / 1000 + ",'" + dataNow + "')");
                    new SetSql()
                            .setSqlNotReturn("insert into es_shift_day(user_code,date,create_time) values('"
                                    + operator.getText()
                                    + "','"
                                    + dataNow
                                    + "'," + timeNow / 1000 + ")");
                    PrientDay p = new PrientDay();
                    p.setData(data);
                    PrientDay.dateNow = dataNow;
                    PrientDay.mm = O2OMainMenu.this;
                    p.start();
                    JOptionPane.showMessageDialog(jf, "日结完成");
                    O2OMainMenu.goodsCode.setText("");
                    O2OMainMenu.totalNumberLabelChange.setText("");
                    O2OMainMenu.totalMoneyLabelChange.setText("");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    while (model.getRowCount() > 0) {
                        model.removeRow(model.getRowCount() - 1);
                    }
                }

            } catch (SQLException e4) {
                e4.printStackTrace();
            }

        }

    }

    /**
     * 数量键盘监听
     * */
    public class goodsNumberKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_F12) {
                memberNumber.requestFocus();

            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                goodsCode.requestFocus();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

    }

    // 获取零点秒数
    public long getTimes() throws ParseException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String aString = sdf.format(date);
        Date date2 = sdf.parse(aString);
        long time0 = date2.getTime();

        return time0;
    }

    /*
     * 数据导出处理
     */
    class dataOutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new SwingWorker<Long, Void>() {
                boolean a = false;

                @Override
                protected Long doInBackground() throws Exception {
                    
                    Gif2.show();
                    DataOutAndIn d = new DataOutAndIn();
                    a = d.DataOutRunItems();
                    a = d.DataOutRun();
                    return null;
                }

                @Override
                protected void done() {
                    Gif2.close();
                    if (a) {
                        JOptionPane.showMessageDialog(jf, "导出成功");
                    } else {
                        JOptionPane.showMessageDialog(jf, "导出失败");
                    }
                }
            }.execute();

        }
    }

    /*
     * 数据导入处理
     */
    class dataInListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //停止定时器
            TimerTest.stopTimer();
            
            new SwingWorker<Long, Void>() {
                boolean a = false;

                @Override
                protected Long doInBackground() throws Exception {
                    
                    Gif2.show();
                    DataOutAndIn d = new DataOutAndIn();
                    a = d.DataInRun();
                    return null;
                }

                @Override
                protected void done() {
                    Gif2.close();
                    if (a) {
                        JOptionPane.showMessageDialog(jf, "导入成功");
                    } else {
                        JOptionPane.showMessageDialog(jf, "导入失败");
                    }
                }
            }.execute();

        }
    }

    public class bnMemberInputListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // MemberInputFrame mif = new MemberInputFrame();
            MemberInputFrame mif = MemberInputFrame.getInstance();
            // mif.mm = O2OMainMenu.this; //家这句话的意思是将主界面的数据带入到新实力化的窗口中
            mif.init();
        }

    }
    //点击上方输入框，条码输入框不失去焦点
    public class gainFocusListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            goodsCode.requestFocus();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        
    }
    // 数据恢复
    public class dataRecoveryListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new DoBackup().load();
        }

    }

    // table刷新
    public static void tableRefresh(Object[][] data) {
        model = new DefaultTableModel(data, columnNames) {
            /**    
             * serialVersionUID:        
             */    
            
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            };

        };
        table.setModel(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(130);
        table.getColumnModel().getColumn(1).setPreferredWidth(130);
        table.getColumnModel().getColumn(2).setPreferredWidth(130);
        table.getColumnModel().getColumn(3).setPreferredWidth(130);
        table.getColumnModel().getColumn(4).setPreferredWidth(130);
        table.getColumnModel().getColumn(5).setPreferredWidth(80);
        table.getColumnModel().getColumn(6).setPreferredWidth(80);
        table.getColumnModel().getColumn(7).setPreferredWidth(80);
        table.getColumnModel().getColumn(8).setPreferredWidth(80);
        table.getColumnModel().getColumn(9).setPreferredWidth(80);
        jScrollPane.setViewportView(table);

        table.getColumnModel().getColumn(0)
                .setCellRenderer(new TableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table, Object value, boolean isSelected,
                            boolean hasFocus, int row, int column) {
                        // 创建用于返回的渲染组件
                        JCheckBox ck = new JCheckBox();
                        // 使具有焦点的行对应复选框选中
                        ck.setSelected(isSelected);
                        // 使复选框在单元格内居中显示
                        ck.setHorizontalAlignment((int) 0.5f);
                        ck.setBackground(Color.white);
                        return ck;
                    }
                });
    }

    // 订单扫描刷新商品信息表格
    public static void tableRefreshs(Object[][] data) {
        model = new DefaultTableModel(data, columnNames2) {
            /**    
             * serialVersionUID:        
             */    
            
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            };

        };
        table.setModel(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(130);
        table.getColumnModel().getColumn(1).setPreferredWidth(130);
        table.getColumnModel().getColumn(2).setPreferredWidth(130);
        table.getColumnModel().getColumn(3).setPreferredWidth(130);
        table.getColumnModel().getColumn(4).setPreferredWidth(130);
        table.getColumnModel().getColumn(5).setPreferredWidth(80);
        table.getColumnModel().getColumn(6).setPreferredWidth(80);
        table.getColumnModel().getColumn(7).setPreferredWidth(80);
        table.getColumnModel().getColumn(8).setPreferredWidth(80);
        jScrollPane.setViewportView(table);

        table.getColumnModel().getColumn(0)
                .setCellRenderer(new TableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(
                            JTable table, Object value, boolean isSelected,
                            boolean hasFocus, int row, int column) {
                        // 创建用于返回的渲染组件
                        JCheckBox ck = new JCheckBox();
                        // 使具有焦点的行对应复选框选中
                        ck.setSelected(isSelected);
                        // 使复选框在单元格内居中显示
                        ck.setHorizontalAlignment((int) 0.5f);
                        ck.setBackground(Color.white);
                        return ck;
                    }
                });
    }

    // 更新合计数量和合计金额
    public static void totalRefresh() {
        allCount = 0;
        allPrice = 0;
        for (int i = 0; i < table.getRowCount(); i++) {
            String valueChange = table.getValueAt(i, 8) + "";
            String valueChange2 = table.getValueAt(i, 9) + "";

            allCount += Integer.parseInt(valueChange);
            allPrice += Double.parseDouble(valueChange2);
        }
        String a = "" + allCount;
        String b = df.format(new BigDecimal(allPrice));
        totalNumberLabelChange.setText(a);
        totalMoneyLabelChange.setText(b);
        
        
        //刷新双面收银机另一面的显示
        int rows = O2OMainMenu.table.getRowCount();
        int columns = O2OMainMenu.table.getColumnCount();
        
        Object[][] doubledata = new Object[rows][columns];
        for(int i = 0 ; i < rows ; i++){
            for(int j = 0 ; j < columns ; j++){
                doubledata[i][j] = O2OMainMenu.table.getValueAt(i, j);
            }
        }
        System.out.println(doubledata.toString());
        
        DefaultTableModel doubleModel = new DefaultTableModel(doubledata, DoubleScreenView.columnNames) {
            /**    
             * serialVersionUID:        
             */    
            
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        
        // 当我们改了 ArrayList 数据的数据时 JTable 并不知道需要刷新我们通过 fireTableDataChanged() 事件来通知 JTable 数据变了请刷新表格的显示。
        doubleModel.fireTableDataChanged(); 
        
        JTable doubletable = new JTable(doubleModel);
        DoubleScreenView.djScrollPane.setViewportView(doubletable);
        
        //更新双面机另一面的总数与总价
        DoubleScreenView.totalMoneyLabelChange.setText(b);
        DoubleScreenView.totalNumberLabelChange.setText(a);
    }

    /**
     * 客显调用， CashNoButton中有同样的方法
     * 
     * @param dataStr
     *            要显示的数字字符串
     * @param stateStr
     *            亮灯状态数字字符串1:单价; 2:合计; 3:收款; 4:找零
     */
    public void sendToDisplay(String dataStr, String stateStr) {
        int cat = PropertiesUtil.getDisplayCat();
        if (cat == 1) {
            // 普通客显调用函数
            String port = "COM" + PropertiesUtil.getnPort();
            Map<String, String> map = new HashMap<String, String>();
            map.put(ClientDisplay.PARAM_PORT_STR, port);
            map.put(ClientDisplay.PARAM_BAUD_RATE_STR, baudRate);
            map.put(ClientDisplay.PARAM_DISPLAY_RATE_STR, displayRate);
            map.put(ClientDisplay.PARAM_DATA_STR, dataStr);
            map.put(ClientDisplay.PARAM_STATE_STR, stateStr);
            try {
                ClientDisplay.sendDisplay(map);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else {
            // 海信客显调用函数
            HSCustomerShow hs = new HSCustomerShow();
            int pSrc = Integer.parseInt(stateStr);
            String pTar = dataStr;
            int MaxCount = 11;
            try {
                // hs.HSOpenDComm(PropertiesUtil.getnPort(),0);
                // hs.VC110_Init();
                hs.VC110_Display(pSrc, pTar, MaxCount);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NativeException e) {
                e.printStackTrace();
            }
        }
    }

    // 添加商品去重复for goodsMany 按照规格去重（条码已经相同）
    public static void dropRepeat() {
        goodsNumber.setText("1");
        for (int i = 0; i < table.getRowCount() - 1; i++) {
            // 判断商品是否重复,重复则合并
            // 如果商品的条形码、商品名称、商品规格都相同，则合并主页面的商品
            if (((String) (table.getValueAt(table.getRowCount() - 1, 1))).equals((String) (table.getValueAt(i, 1)))
                    && ((String) (table.getValueAt(table.getRowCount() - 1, 2))).equals((String) (table.getValueAt(i, 2)))
                    && ((String) (table.getValueAt(table.getRowCount() - 1, 3))).equals((String) (table.getValueAt(i, 3)))
                ) {

                String num1 = table.getValueAt(i, 8) + "";
                String num2 = table.getValueAt(table.getRowCount() - 1, 8) + "";
                int numI1 = Integer.parseInt(num1);
                int numI2 = Integer.parseInt(num2);
                String all = "" + (numI1 + numI2);

                model.removeRow(i);
                table.setModel(model);
                //让table显示在桌面上
                jScrollPane.setViewportView(table);
                goodsNumber.setText(all);
            }
        }
    }

    // table添加行
    public static void addTableRow(Object[][] data) {
        for(int i = 0;i< data.length;i++){
            model.addRow(data[i]);
        }
        table.setModel(model);

        jScrollPane.setViewportView(table);
        if (table.getRowCount() == 0) {
            
        } else {
            if (table.getRowCount() == 1) {
                table.setRowSelectionInterval(0, 0);

            } else {
                table.setRowSelectionInterval(table.getRowCount() - 1,
                        table.getRowCount() - 1);
            }

        }

    }

    public class cardInit implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            
            new CancelForCardPay().init();
        }

    }

    /**
     * 订单查询table显示
     */
    public static void orderCheckShow(Object[][] data2) {
        data = null;
        tableRefresh(data);
        O2OMainMenu.addTableRow(data2);
        totalRefresh();
    }

    /**
     * 扫描手机生成订单二维码
     */
    class OrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (memberNumber.getText().length() > 0) {
                MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
                // String goodsString = goodsCode.getText();
                String goods = goodsCode.getText();
                // queryParam.add("sql", sql);
                queryParam.add("sn", goods);
                // queryParam.add("product_id", product_id);
                // queryParam.add("goods_id", goods);
                String url = PropertiesUtil.getIp()
                        + "/api/shop/goods!getAllProduct.do";
                ConnectOnlineMethod connect = new ConnectOnlineMethod();
                try {
                    String outPut = connect.connectOnline(queryParam, url);
                    JSONArray array = connect.jsonConvertion(outPut);
                    // JSONObject jsonObject = null;
                    List<JSONObject> jsonObjects = null;
                    if (array.length() != 0) {
                        jsonObjects = connect.getJsonObjects(array);
                        data3 = getDataArrayForSn(jsonObjects);
                        tableRefresh(data3);
                        totalRefresh();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            } else {
                JOptionPane.showMessageDialog(jf, "请输入会员号");
            }
        }

        public void orderScan() {
            if (memberNumber.getText().length() > 0) {
                String goods = goodsCode.getText();
                MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
                String sql = "SELECT * FROM es_product WHERE bar_code = '"
                        + goods + "'";
                queryParam.add("sql", sql);
                queryParam.add("sn", goods);
                String url = PropertiesUtil.getIp()
                        + "/api/shop/goods!getAllProduct.do";
                ConnectOnlineMethod connect = new ConnectOnlineMethod();
                try {
                    String outPut = connect.connectOnline(queryParam, url);
                    if (!outPut.equals("")) {

                        JSONArray array = connect.jsonConvertion(outPut);

                        List<JSONObject> jsonObjects = null;
                        if (array.length() != 0) {
                            jsonObjects = connect.getJsonObjects(array);

                            // 这儿需要将数据order_items表中的num值取出来
                            data3 = getDataArrayForSn(jsonObjects);
                            tableRefreshs(data3);
                            String so = goodsCode.getText();
                            O2OMainMenu.documentNumber.setText(so);
                            totalRefresh();
                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            } else {
                JOptionPane.showMessageDialog(jf, "请输入会员号");
                a = false;

            }
        }
    }
    public static void changePerssion(){
        /**
         * 判断角色
         */
        if (!O2OMainMenu.internetPass) { // 在不联网状态下判断用户所属角色权限
            ResultSet rsForRole = new SetSql()
                    .setSql("select rolename from es_user where username = '"
                            + salesman + "'");
            String role = null;
            try {
                rsForRole.next();
                role = (rsForRole.getString("rolename"));
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            switch (role) {
            case "admin":
                break;
            case "店长":
                bnPos.setEnabled(false);
                bnCashier.setEnabled(false);
                bnPendingOrder.setEnabled(false);
                bnPendingOrderQuery.setEnabled(false);
                balanceOfWork.setEnabled(false);
                break;
            case "收银员":
                balanceOfDay.setEnabled(false);
                dataImport.setEnabled(false);
                dataExport.setEnabled(false);
                bnMember.setEnabled(false);
                break;
            case "init":
                bnPos.setEnabled(false);
                bnCashier.setEnabled(false);
                bnMember.setEnabled(false);
                bnDelete.setEnabled(false);
                saleStatistics.setEnabled(false);
                bnPendingOrder.setEnabled(false);
                bnPendingOrderQuery.setEnabled(false);
                balanceOfDay.setEnabled(false);
                balanceOfWork.setEnabled(false);
                dataExport.setEnabled(false);
                orderCheck.setEnabled(false);
                rejected.setEnabled(false);
                bnMemberInput.setEnabled(false);
                dataRecovery.setEnabled(false);
                break;
            }
        } else { // 联网状态下角色权限
            bnMember.setEnabled(false);
            String role = null;
            MultivaluedMap<String, String> queryParam = new MultivaluedMapImpl();
            queryParam.add("username", salesman);
            String url = PropertiesUtil.getIp() + "/api/shop/member!getRole.do";
            ConnectOnlineMethod connect = new ConnectOnlineMethod();
            try {
                String outPut = connect.connectOnline(queryParam, url);
                JSONArray array = connect.jsonConvertion(outPut);
                if (array.equals("")) {
                }
                JSONObject jsonObject = connect.getJsonObject(array);
                role = jsonObject.getString("role_name");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            switch (role) {
            case "admin":
                break;
            case "店长":
                bnPos.setEnabled(false);
                bnCashier.setEnabled(false);
                bnPendingOrder.setEnabled(false);
                bnPendingOrderQuery.setEnabled(false);
                balanceOfWork.setEnabled(false);
                break;
            case "收银员":
                balanceOfDay.setEnabled(false);
                dataImport.setEnabled(false);
                dataExport.setEnabled(false);
                bnMember.setEnabled(false);
                break;
            case "init":
                bnPos.setEnabled(false);
                bnCashier.setEnabled(false);
                bnMember.setEnabled(false);
                bnDelete.setEnabled(false);
                saleStatistics.setEnabled(false);
                bnPendingOrder.setEnabled(false);
                bnPendingOrderQuery.setEnabled(false);
                balanceOfDay.setEnabled(false);
                balanceOfWork.setEnabled(false);
                dataExport.setEnabled(false);
                orderCheck.setEnabled(false);
                rejected.setEnabled(false);
                bnMemberInput.setEnabled(false);
                dataRecovery.setEnabled(false);
                break;
            }
        }
    }
}
