package com.sunvsoft.entity;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sunvsoft.accessOnline.ConnectOnlineMethod;
import com.sunvsoft.backup.DoBackup;
import com.sunvsoft.backup.PropertiesUtil;
import com.sunvsoft.sqlset.SetSql;

public class CommitDataClass extends TimerTask {

    private static Timer timer = new Timer();
    
    public void commit(){
        
        this.upLoadOrderData();
        
        this.upLoadSellbackData();
        
    }

    @Override
    public void run() {
       commit();
    }
    
    
    
    /**        
     * TODO       
     * @param   name    
     * @return String   
     * @Exception 异常对象      
     * 上传线下未上传线上的订单交易以及订单明细数据 
    */
    public void upLoadOrderData(){
        String sql = "SELECT * FROM es_order WHERE ISNULL(export_status) and status <> 100";
        ResultSet rs = new SetSql().setSql(sql);
        @SuppressWarnings("rawtypes")
        List<Map> list = new LinkedList<Map>();
        try {
            while(rs.next()){
                Map<String,String> m = new HashMap<String,String>();
                if(!rs.getString("sn").startsWith("SO20")){
                    m.put("sn3", rs.getString("sn"));
                    list.add(m);
                }
                MultivaluedMap<String, String> map = new MultivaluedMapImpl();
                map.add("addr_id", rs.getInt("address_id")+"");
                map.add("uname", rs.getString("user_name"));
                map.add("sn3",rs.getString("sn"));
                map.add("card_id", rs.getString("cardNumber")+"");
                map.add("user_name", rs.getString("user_name")+"");
                map.add("status", rs.getInt("status")+"");
                map.add("create_time", rs.getInt("create_time")+"");
                map.add("goods_amount", rs.getDouble("goods_amount")+"");
                map.add("order_amount", rs.getDouble("order_amount")+"");
                map.add("goods_num", rs.getInt("goods_num")+"");
                map.add("currency", rs.getString("currency")+"");
                map.add("date", rs.getDate("date")+"");
                map.add("classes", rs.getInt("classes")+"");
                map.add("balance_status",rs.getInt("balance_status")+"");
                map.add("cashAmount", rs.getDouble("cashAmount")+"");
                map.add("cardAmount", rs.getDouble("cardAmount")+"");
                map.add("refNo", rs.getString("refNo")+"");
                map.add("cerNo", rs.getString("cerNo")+"");
                map.add("payment_name", rs.getString("payment_name"));
                map.add("paymentId", rs.getInt("payment_id")+"");
                map.add("payment_type", rs.getString("payment_type")+"");
                map.add("taxesPrice", rs.getDouble("taxes")+"");
                map.add("paymoney", rs.getDouble("paymoney")+"");
                map.add("store_id",rs.getInt("store_id")+"");
                map.add("allowance", rs.getString("allowance"));
                map.add("coupon", rs.getString("coupon"));
                String url = PropertiesUtil.getIp() + "/api/shop/order!orderByOffline.do";
                ConnectOnlineMethod connect = new ConnectOnlineMethod();
                try {
                    String str  = connect.connectOnline(map, url);
                    if (str.length()==29) {
                        boolean backUpOrder = new SetSql()
                        .setSqlNotReturn("update es_order set export_status = 1 where sn = '" + rs.getString("sn") + "'");
                        if (backUpOrder) {
                            new DoBackup().realTimeBackup("update es_order set export_status = 1 where sn = '" + rs.getString("sn") + "'");
                        }
                        System.out.println("线下未上传订单上传成功"+new Date().getTime());
                    }else{
                        System.out.println("线下未上传订单上传失败"+new Date().getTime());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Map<String, String> map : list) {
            String sql1 = "SELECT * FROM es_order_items WHERE sn = '"+map.get("sn3")+"'";
            ResultSet rs1 = new SetSql().setSql(sql1);
            try {
                while(rs1.next()){
                    MultivaluedMap<String, String> maps = new MultivaluedMapImpl();
                    maps.add("order_id",rs1.getInt("order_id")+"");
                    maps.add("store_id", PropertiesUtil.getStoreId()+"");
                    maps.add("goods_id", rs1.getInt("goods_id")+"");
                    maps.add("product_id",rs1.getInt("product_id")+"");
                    maps.add("num",rs1.getInt("num")+"");
                    maps.add("bar_code", rs1.getString("bar_code")+"");
                    maps.add("name",rs1.getString("name"));
                    maps.add("price", rs1.getDouble("price")+"");
                    maps.add("currency", rs1.getString("currency")+"");
                    maps.add("sn3", rs1.getString("sn"));
                    maps.add("cat_id", rs1.getInt("cat_id")+"");
                    maps.add("state", rs1.getInt("state")+"");
                    maps.add("taxes", rs1.getDouble("taxes")+"");
//                    maps.add("thumbnail",rs1.getString(""));
                    String url = PropertiesUtil.getIp() + "/api/shop/order!orderItemByOffline.do";
                    ConnectOnlineMethod connect = new ConnectOnlineMethod();
                    try {
                        String str  = connect.connectOnline(maps, url);
                        if(str.length()==29){
                            boolean backUpItems = new SetSql()
                            .setSqlNotReturn("update es_order_items set export_status = 1 where sn = '" + rs1.getString("sn") + "'");
                            if (backUpItems) {
                                new DoBackup().realTimeBackup("update es_order_items set export_status = 1 where sn = '" + rs1.getString("sn")
                                        + "'");
                                System.out.println("线下未上传订单明细上传成功"+new Date().getTime());
                            }
                        }else{
                            System.out.println("线下未上传订单明细上传失败"+new Date().getTime());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /**        
     * TODO       
     * @param   name    
     * @return String   
     * @Exception 异常对象       
     * 上传线下未上传的退货单交易以及退货单明细数据
    */
    public void upLoadSellbackData(){
        String sellbackSql = "SELECT * FROM es_sellback_list WHERE ISNULL(export_status) ";
        ResultSet sellbackRs = new SetSql().setSql(sellbackSql);
        ConnectOnlineMethod connect = new ConnectOnlineMethod();
        try {
            while(sellbackRs.next()){
                MultivaluedMap<String, String> sellMap = new MultivaluedMapImpl();
                sellMap.add("id",sellbackRs.getInt("id")+"");
                sellMap.add("tradeno",sellbackRs.getString("tradeno"));
                sellMap.add("ordersn",sellbackRs.getString("ordersn"));
                sellMap.add("regtime",sellbackRs.getString("regtime"));
                sellMap.add("alltotal_pay",sellbackRs.getDouble("alltotal_pay")+"");
                sellMap.add("member_id",sellbackRs.getString("member_id"));
                sellMap.add("total", sellbackRs.getInt("total")+"");
                sellMap.add("store_id",PropertiesUtil.getStoreId()+"");
                String url = PropertiesUtil.getIp() + "/api/shop/sellBack!addSellbackLists.do";
                try {
                    String str  = connect.connectOnline(sellMap, url);
                    if(str.length()==29){
                        boolean backUpItems = new SetSql()
                        .setSqlNotReturn("update es_sellback_list set export_status = 1 where tradeno = '" + sellbackRs.getString("tradeno") + "'");
                        if (backUpItems) {
//                            new DoBackup().realTimeBackup("update es_order_items set export_status = 1 where sn = '" + sell.getString("sn")
//                                    + "'");
                            System.out.println("线下未上传退货单上传成功"+new Date().getTime());
                            String sellbackGoodsSql = "SELECT * FROM es_sellback_goodslist WHERE ISNULL(export_status) and recid = "+sellbackRs.getInt("id")+" ";
                            ResultSet sellbackGoodsRs = new SetSql().setSql(sellbackGoodsSql);
                            try {
                                while(sellbackGoodsRs.next()){
                                    MultivaluedMap<String, String> sellGoodsMap = new MultivaluedMapImpl();
                                    sellGoodsMap.add("tradeno",sellbackRs.getString("tradeno"));
                                    sellGoodsMap.add("product_id",sellbackGoodsRs.getInt("product_id")+"");
                                    sellGoodsMap.add("goods_id",sellbackGoodsRs.getInt("goods_id")+"");
                                    sellGoodsMap.add("return_num",sellbackGoodsRs.getInt("return_num")+"");
                                    sellGoodsMap.add("prices",sellbackGoodsRs.getDouble("price")+"");
                                    String urls = PropertiesUtil.getIp() + "/api/shop/sellBack!BackListGoodsFromOffline.do";
//                                    ConnectOnlineMethod connect = new ConnectOnlineMethod();
                                    try {
                                        String strs  = connect.connectOnline(sellGoodsMap, urls);
                                        if(strs.length()==29){
                                                System.out.println("线下未上传退货单上传成功"+new Date().getTime());
                                        }else{
                                            System.out.println("线下未上传退货单上传失败"+new Date().getTime());
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }else{
                        System.out.println("线下未上传退货单上传失败"+new Date().getTime());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    public static void upLoadData(long delay,long period){
        timer.schedule(new CommitDataClass(), delay,period);
    }
}
