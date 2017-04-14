package com.sunvsoft.accessOnline;


    import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;


    public class ConnectOnlineMethod { 
        /**
         * 
         * TODO   该方法实现链接线上方法 ，得到线上传过来的json  
         * @param   name    
         * @return String   
         * @Exception 异常对象
         * @author lishilin
         */
        public String connectOnline(MultivaluedMap<String, String> queryParams,String url) throws IOException {
            
            //创建一个客户端连接对象
            Client cc = Client.create();    
            
            //初始化资源url
            WebResource rr = cc.resource(url);
            
            //需要的参数map集合，将本地的参数放到集合当中，然后之间链接到url终端    
//            MultivaluedMap queryParam = new MultivaluedMapImpl();
            /*queryParams.add("v_mid", "6565");  
            queryParams.add("v_oid", oid);
            queryParams.add("v_refamount", "0.01");
            queryParams.add("v_refreason", URLEncoder.encode("可中文-退款原因", "gb2312"));
            queryParams.add("v_refoprt", "27827");
          
            Md5 md5 = new Md5 ( "" ) ;
            md5.hmac_Md5("6565"+oid+"0.0127827","test");
            byte b[]= md5.getDigest();
            String digestString = md5.stringify(b) ;
            System.out.println (digestString) ;
            queryParams.add("v_mac", digestString);
                                 方法一：*/
//            ClientResponse response = rr.accept("application/json")
//                    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
//                    .post(ClientResponse.class,queryParams);
            
            //响应状态码判断响应状态
//            if (response.getStatus() != 200) {     
//                  throw new RuntimeException("Failed : HTTP error code " + response.getStatus());
//             }
            
            //获取从线上得到的字符串
//            String outPut = response.getEntity(String.class);   
//          方法二：
            String outPut = rr.queryParams(queryParams).post(String.class);
            return outPut;
    }   
    /**
     *     
     * TODO   得到json对象数组    
     * @param   name    
     * @return String   
     * @Exception 异常对象
     * @author lishilin
     */
    public JSONArray jsonConvertion(String string){
        //这是从网上找到的解决方案，主要是解析这种特殊格式的json字符串
        JSONObject jsonObject = new JSONObject(string);   
        
        //这里用set能够成功接受，使用map\list在我的多次尝试下未能成功
        Set<String> set = jsonObject.keySet();     
        
        //申明jsonarray来接受set遍历出来的集合
        JSONArray array = null;
        for (String str : set) {  
            //这里的判断是将json中result对象剪切掉，剩下的字符串会变成另一种格式的JSON
            if(!str.equals("result")){  
            //这一步就会将上一步中特殊格式的json转成jsonarray
            array= new JSONArray(jsonObject.get(str).toString());  
            }
        } 
        return array;
    }
    /**
     * 
     * TODO  得到jsonarray中的对象的值 
     * @param   name    
     * @return String   
     * @Exception 异常对象
     * @author lishilin
     */
    public JSONObject getJsonObject(JSONArray array){
      //声明jsonobject对象
        JSONObject jsons = null;
        
        if(array != null){
        for (int i = 0; i < array.length(); i++) {  
           jsons = array.getJSONObject(0);
            //得到其中的每一个json对象
        }
        }
        return jsons;
    }
    /**        
     * TODO       
     * @param   name    
     * @return String   
     * @Exception 异常对象       
    */
    public List<JSONObject> getJsonObjects(JSONArray array){
        List<JSONObject> list = new ArrayList<JSONObject>();
          
          if(array != null){
          for (int i = 0; i < array.length(); i++) {  
              list.add(array.getJSONObject(i));
          }
      }
          return list;
      }
}
