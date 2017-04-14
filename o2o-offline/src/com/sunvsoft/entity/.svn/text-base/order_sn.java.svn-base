package com.sunvsoft.entity;

import com.sunvsoft.backup.PropertiesUtil;

public class order_sn {
    
    /**
     * 
     * 主订单编号
     * @param 
     * @return
     */
    public String soOrder() {
        String store_id = PropertiesUtil.getStoreId();
        long timeNow = System.currentTimeMillis();
        String dateNow = new timeToDates().getTimeToDateForSn(timeNow);
        String sn = "SO20" + store_id + "" + dateNow + "" + (int) (Math.random() * 9000 + 1000);// 订单编号
        return sn;

    }
    /**
     * 
     * 完税订单编号
     * @param 
     * @return
     */
    public String soGet() {
        String store_id = PropertiesUtil.getStoreId();
        long timeNow = System.currentTimeMillis();
        String dateNow = new timeToDates().getTimeToDateForSn(timeNow);
        String sn = "SO21" + store_id + "" + dateNow + "" + (int) (Math.random() * 9000 + 1000);// 订单编号
        return sn;

    }

    /**        
     * TODO    
     * 保税订单编号   
     * @param   name    
     * @return String   
     * @Exception 异常对象       
    */
    public String soGets() {
        String store_id = PropertiesUtil.getStoreId();
        long timeNow = System.currentTimeMillis();
        String dateNow = new timeToDates().getTimeToDateForSn(timeNow);
        String sn = "SO22" + store_id + "" + dateNow + "" + (int) (Math.random() * 9000 + 1000);// 订单编号
        return sn;

    }

    /**        
     * TODO    
     * 直邮订单编号   
     * @param   name    
     * @return String   
     * @Exception 异常对象       
    */
    public String soGetss() {
        String store_id = PropertiesUtil.getStoreId();
        long timeNow = System.currentTimeMillis();
        String dateNow = new timeToDates().getTimeToDateForSn(timeNow);
        String sn = "SO23" + store_id + "" + dateNow + "" + (int) (Math.random() * 9000 + 1000);// 订单编号
        return sn;

    }
    /**
     * 
     * 退货订单编号
     * @param 
     * @return
     */
    public String srGet() {
        String store_id = PropertiesUtil.getStoreId();
        long timeNow = System.currentTimeMillis();
        String dateNow = new timeToDates().getTimeToDateForSn(timeNow);
        String sn = "SR2" + store_id + "" + dateNow + "" + (int) (Math.random() * 9000 + 1000);// 订单编号
        return sn;
    }

    public static void main(String[] args) {
        System.out.println((int) (Math.random() * 9000 + 1000));
    }
}
