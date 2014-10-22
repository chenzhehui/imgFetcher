package com.chevic.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ImgFetcher {
	
	public static Set<String> getImgs(String url) throws Exception{
		 Set<String> imgSet= new HashSet<String>() ;
		 
		Document doc = Jsoup.connect(url).get();
		Elements imgs = doc.select("img");
		for(int i=0 ;i<imgs.size() ;i++){
			imgSet.add(imgs.get(i).attr("src")) ;
		}
		
		
		return imgSet;
	}
	
	public static void saveImg(String directory,String imgSrc,int number) throws Exception{
		URL url = new URL(imgSrc);
		File dir = new File(System.getProperty("user.dir")+"/img/"+directory) ;
		if(!dir.exists()){
			dir.mkdir() ;
		}
		File outFile = new File(System.getProperty("user.dir")+"/img/"+directory+"/"+number+"."+imgSrc.substring(imgSrc.lastIndexOf(".")+1,imgSrc.length()));
		OutputStream os = new FileOutputStream(outFile);
		InputStream is = url.openStream();
		byte[] buff = new byte[1024];
		while(true) {
			int readed = is.read(buff);
			if(readed == -1) {
				break;
			}
			byte[] temp = new byte[readed];
			System.arraycopy(buff, 0, temp, 0, readed);
			os.write(temp);
		}
		is.close(); 
        os.close();
	}
	
	public static String getDirectory(String siteUrl){
		return siteUrl.substring(siteUrl.indexOf("://")+3, siteUrl.indexOf("://")+3+(siteUrl.substring(8,siteUrl.length()).indexOf("/")<0?siteUrl.substring(8,siteUrl.length()).length():siteUrl.substring(8,siteUrl.length()).indexOf("/"))+1) ;
	}
	
	public static void main(String[] args){
		System.out.println(ImgFetcher.getDirectory("http://www.baidu.com/bppf_bth/bppf_bth/tags")) ;
	}
}
