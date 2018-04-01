package com.hbasesoft.manager.utils;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 微信素材工具类
 * 
 * @author YS-004
 *
 */
public class Materialutil {

	public static JSONObject getMaterial(String accessToken,String type,Integer offset,Integer count) {
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=" + accessToken;
		JSONObject j = new JSONObject();  
	       try {  
	           j.put("type",type); 
	           j.put("offset",offset);
	           j.put("count",count);
	        } catch (JSONException e) {  
	           e.printStackTrace();  
	       } 
	       System.out.println(j);
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", j.toString());
		System.out.println(jsonObject);
		return jsonObject;
  }
	
	public static void main(String[] args) {
		String accessToken = "Lzz4XKBq1xyJXGSqUAlWBjZcCp-qn6JnHbohaqVOeLa_jeI2RoHh5khcOq2eJmKfI96TdG"
				+ "71TdOZs3b3HCLYCmWEuoECQ3BJXQoskDBVPBst5wr1vW87qeuJpKH9xbxdZYTgACAMLG";
		getMaterial(accessToken,"news",4,1);
	}
}
