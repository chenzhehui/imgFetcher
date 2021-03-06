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
     * 执行一个HTTP GET请求，返回请求响应的HTML 
     * 
     * @param url                 请求的URL地址 
     * @param queryString 请求的查询参数,可以为null 
     * @param charset         字符集 
     * @param pretty            是否美化 
     * @return 返回请求响应的HTML 
     */ 
    public static String doGet(String url, String queryString, String charset, boolean pretty) { 
            StringBuffer response = new StringBuffer(); 
            HttpClient client = new HttpClient(); 
            HttpMethod method = new GetMethod(url); 
            try { 
                    if (StringUtils.isNotBlank(queryString)) 
                            //对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串 
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
                    System.out.println("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！");
                    e.printStackTrace();
            } catch (IOException e) { 
                    System.out.println("执行HTTP Get请求" + url + "时，发生异常！");
                    e.printStackTrace();
            } finally { 
                    method.releaseConnection(); 
            } 
            return response.toString(); 
    } 

    /** 
     * 执行一个HTTP POST请求，返回请求响应的HTML 
     * 
     * @param url         请求的URL地址 
     * @param params    请求的查询参数,可以为null 
     * @param charset 字符集 
     * @param pretty    是否美化 
     * @return 返回请求响应的HTML 
     */ 
    public static String doPost(String url, Map<String, String> params, String charset, boolean pretty) { 
            StringBuffer response = new StringBuffer(); 
            HttpClient client = new HttpClient(); 
            HttpMethod method = new PostMethod(url); 
            //设置Http Post数据 
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
                    System.out.println("执行HTTP Post请求" + url + "时，发生异常！"+e.getStackTrace()); 
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
				m.put("action", "vote") ;
				m.put("picID", "41") ;
//				m.put("topicId", args[0]) ;
//				m.put("operate", args[1]) ;
				// TODO Auto-generated method stub
				int i=0 ;
				while(true){
					String ramdom = "asldkjl" +i+ "jlaksee"+(i+5);
					String y = doPost("http://42.159.196.120/luoding/games/luodingRote/luodingRote.php?from_user="+ramdom, m, "UTF-8", true); 
					String k = doPost("http://42.159.196.120/luoding/games/luodingRote/luodingRoteService.php", m, "UTF-8", true); 
					System.out.println(y);
					System.out.println(k);
					try{
						Thread.sleep(2000);
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
			}
    		
    	}).start();
            
    } 
}