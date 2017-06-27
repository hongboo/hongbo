
package com.hongbo.https;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * @author HB
 *
 * 2017年6月26日
 */
public class HttpsTest {
	
	// /2013-12-26/Accounts/{accountSid}/SMS/TemplateSMS?sig={SigParameter}
	static String baseUrl = "https://appsms.cloopen.com:8883/2013-12-26";
	static String ACCOUNT_SID = "aaf98f894c5a7f75014c699487900794";
	static String AUTH_TOKEN = "4a717227f52e48df8d0e17a2713507b5";
	static String business = "/SMS/TemplateSMS";
	static String accountLevel = "/Accounts/";
	static String date = YTXURLUtils.convertDate(new Date());
	static String SigParameter = YTXURLUtils.MD5(ACCOUNT_SID + AUTH_TOKEN + date);
	static String Authorization = YTXURLUtils.base64(ACCOUNT_SID + ":" + date);
	
	public static void main(String[] args) {
		System.out.println(date);
		System.out.println(Authorization);
		String url = baseUrl + accountLevel + ACCOUNT_SID + business + "?sig=" + SigParameter;
		System.out.println(url);
		try {
			CloseableHttpClient client = SSL.getClient();
			
			HttpPost post = new HttpPost(url);
			//post.setHeader("content-length", "139");
			post.setHeader("Accept", "application/json");
			post.setHeader("Content-Type", "application/json;charset=utf-8");
			post.setHeader("Authorization", Authorization);
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("to", "17612149986");
			jsonObj.put("appId", "aaf98f894c5a7f75014c699795420795");//上海同道
			jsonObj.put("templateId", "15818");
			jsonObj.put("datas", new String[]{"121212","3"});
			
			//设置参数到请求对象中  
			StringEntity entity = new StringEntity(jsonObj.toString(),"utf-8");
			post.setEntity(entity);
		    //执行请求操作，并拿到结果（同步阻塞）  
		    CloseableHttpResponse response = client.execute(post);  
		    //获取结果实体  
		    HttpEntity responseEntity = response.getEntity();  
		    if (entity != null) {  
		        //按指定编码转换结果实体为String类型  
		       String body = EntityUtils.toString(responseEntity, "utf-8");  
		       System.out.println(body);
		    }  
		    EntityUtils.consume(entity);  
		    //释放链接  
		    response.close();  
			
			
		} catch (KeyManagementException | NoSuchAlgorithmException| IOException e) {
			e.printStackTrace();
		}
	}
}
