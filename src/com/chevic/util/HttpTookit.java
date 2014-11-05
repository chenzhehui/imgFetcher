package com.chevic.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;

public final class HttpTookit { 

    /** 
     * ִ��һ��HTTP GET���󣬷���������Ӧ��HTML 
     * 
     * @param url                 �����URL��ַ 
     * @param queryString ����Ĳ�ѯ����,����Ϊnull 
     * @param charset         �ַ��� 
     * @param pretty            �Ƿ����� 
     * @return ����������Ӧ��HTML 
     */ 
    public static String doGet(String url, String queryString, String charset, boolean pretty) { 
            StringBuffer response = new StringBuffer(); 
            HttpClient client = new HttpClient(); 
            HttpMethod method = new GetMethod(url); 
            try { 
                    if (StringUtils.isNotBlank(queryString)) 
                            //��get�����������http����Ĭ�ϱ��룬����û���κ����⣬���ֱ���󣬾ͳ�Ϊ%ʽ�����ַ��� 
                            method.setQueryString(URIUtil.encodeQuery(queryString)); 
                    client.executeMethod(method); 
                    if (method.getStatusCode() == HttpStatus.SC_OK) { 
                            BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset)); 
                            String line; 
                            while ((line = reader.readLine()) != null) { 
                                    if (pretty) 
                                            response.append(line).append(System.getProperty("line.separator")); 
                                    else 
                                            response.append(line); 
                            } 
                            reader.close(); 
                    } 
            } catch (URIException e) { 
                    System.out.println("ִ��HTTP Get����ʱ�������ѯ�ַ�����" + queryString + "�������쳣��");
                    e.printStackTrace();
            } catch (IOException e) { 
                    System.out.println("ִ��HTTP Get����" + url + "ʱ�������쳣��");
                    e.printStackTrace();
            } finally { 
                    method.releaseConnection(); 
            } 
            return response.toString(); 
    } 

    /** 
     * ִ��һ��HTTP POST���󣬷���������Ӧ��HTML 
     * 
     * @param url         �����URL��ַ 
     * @param params    ����Ĳ�ѯ����,����Ϊnull 
     * @param charset �ַ��� 
     * @param pretty    �Ƿ����� 
     * @return ����������Ӧ��HTML 
     */ 
    public static String doPost(String url, Map<String, String> params, String charset, boolean pretty) { 
            StringBuffer response = new StringBuffer(); 
            HttpClient client = new HttpClient(); 
            HttpMethod method = new PostMethod(url); 
            //����Http Post���� 
            if (params != null) { 
                    HttpMethodParams p = new HttpMethodParams(); 
                    NameValuePair[] data = new NameValuePair[params.size()] ;
                    int i=0 ;
                    for (Map.Entry<String, String> entry : params.entrySet()) { 
                    	data[i]=new NameValuePair(entry.getKey(), entry.getValue()); 
                    	i++ ;
                    } 
                    method.setQueryString(data);
            } 
            try { 
                    client.executeMethod(method); 
                    if (method.getStatusCode() == HttpStatus.SC_OK) { 
                            BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset)); 
                            String line; 
                            while ((line = reader.readLine()) != null) { 
                                    if (pretty) 
                                            response.append(line).append(System.getProperty("line.separator")); 
                                    else 
                                            response.append(line); 
                            } 
                            reader.close(); 
                    } 
            } catch (IOException e) { 
                    System.out.println("ִ��HTTP Post����" + url + "ʱ�������쳣��"+e.getStackTrace()); 
            } finally { 
                    method.releaseConnection(); 
            } 
            return response.toString(); 
    } 

    public static void main(final String[] args) { 
    	
    	new Thread(new Runnable(){

			@Override
			public void run(){
				Map m = new HashMap() ;
//				m.put("topicId", "82822") ;
//				m.put("operate", "0") ;
				m.put("topicId", args[0]) ;
				m.put("operate", args[1]) ;
				// TODO Auto-generated method stub
				while(true){
					
					String y = doPost("http://best.pconline.com.cn/action/topic/like_and_dislike.jsp", m, "UTF-8", true); 
					System.out.println(y);
					try{
						Thread.sleep(30000);
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
			}
    		
    	}).start();
            
    } 
}