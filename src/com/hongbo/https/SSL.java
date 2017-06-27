/**
 * 
 */
package com.hongbo.https;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

/**
 * @author HB
 *
 * 2017��6��26��
 */
public class SSL {
	/** 
	 * �ƹ���֤ 
	 *   
	 * @return 
	 * @throws NoSuchAlgorithmException  
	 * @throws KeyManagementException  
	 */  
	public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {  
	    SSLContext sc = SSLContext.getInstance("SSLv3");  
	  
	    // ʵ��һ��X509TrustManager�ӿڣ������ƹ���֤�������޸�����ķ���  
	    X509TrustManager trustManager = new X509TrustManager() {  
	        @Override  
	        public void checkClientTrusted(  
	                java.security.cert.X509Certificate[] paramArrayOfX509Certificate,  
	                String paramString) throws CertificateException {  
	        }  
	  
	        @Override  
	        public void checkServerTrusted(  
	                java.security.cert.X509Certificate[] paramArrayOfX509Certificate,  
	                String paramString) throws CertificateException {  
	        }  
	  
	        @Override  
	        public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
	            return null;  
	        }  
	    };  
	  
	    sc.init(null, new TrustManager[] { trustManager }, null);  
	    return sc;  
	}  
	
	/** 
	 * ����������ǩ��֤�� 
	 *   
	 * @param keyStorePath      ��Կ��·�� 
	 * @param keyStorepass      ��Կ������ 
	 * @return 
	 */  
	public static SSLContext custom(String keyStorePath, String keyStorepass){  
	    SSLContext sc = null;  
	    FileInputStream instream = null;  
	    KeyStore trustStore = null;  
	    try {  
	        trustStore = KeyStore.getInstance(KeyStore.getDefaultType());  
	        instream = new FileInputStream(new File(keyStorePath));  
	        trustStore.load(instream, keyStorepass.toCharArray());  
	        // �����Լ���CA��������ǩ����֤��  
	        sc = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();  
	    } catch (KeyStoreException | NoSuchAlgorithmException| CertificateException | IOException | KeyManagementException e) {  
	        e.printStackTrace();  
	    } finally {  
	        try {  
	            instream.close();  
	        } catch (IOException e) {  
	        }  
	    }  
	    return sc;  
	}  
	
	public static CloseableHttpClient getClient() throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {  
	    //�����ƹ���֤�ķ�ʽ����https����  
	    SSLContext sslcontext = createIgnoreVerifySSL();  
	      
	       // ����Э��http��https��Ӧ�Ĵ���socket���ӹ����Ķ���  
	       Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()  
	           .register("http", PlainConnectionSocketFactory.INSTANCE)  
	           .register("https", new SSLConnectionSocketFactory(sslcontext))  
	           .build();  
	       PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
	       HttpClients.custom().setConnectionManager(connManager);  
	  
	       //�����Զ����httpclient����  
	       CloseableHttpClient client = HttpClients.custom().setConnectionManager(connManager).build();  
	     //  CloseableHttpClient client = HttpClients.createDefault();  
	       
	       return client;
	}  
}
