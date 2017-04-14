package com.sunvsoft.entity;

import java.util.Locale;
import java.util.ResourceBundle;

public class International {

    private static International international = null;
    private International(){}
    public static International getInstance(){
        if(international==null){
            international = new International();
        }
        return international;
        
    }
    static Locale locale ;
    ResourceBundle bundle;
    public String getInternational(String key){
        
        //取得系统默认的国家/语言环境
        
         locale = Locale.getDefault();
        //根据指定国家/语言环境加载资源文件
        if(UserLogin.index1==0){
            bundle = ResourceBundle.getBundle("com.sunvsoft.entity.message_CN",locale);
        }else{
            bundle = ResourceBundle.getBundle("com.sunvsoft.entity.message_UN",locale);
        }
        
        //打印从资源文件中取得的消息
//        
//        System.out.println(bundle.getString("inputMember"));
        return bundle.getString(key);
    }
    /*public static void main(String[] args) {
        International international = new International();
        international.getInternational("inputMember");
        }
*/
    public static International getInstance1() {
        return International.getInstance();
    }
}
