package com.chevic.util;

import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SiteUrlFetcher {
	
	public static Set<String> innerPages= new HashSet<String>() ;
	
	public static Set<String> getInnerPages(String url) throws Exception{
		Document doc = Jsoup.connect(url).get();
		Elements imgs = doc.select("img");
		return innerPages ;
	}
	
	//tid:82822
	//url:http://best.pconline.com.cn/action/topic/like_and_dislike.jsp?operate=0&tid:82822
	

}
