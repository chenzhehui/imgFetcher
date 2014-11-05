package com.chevic.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class VpnTool {
	
	private static String executeCmd(String cmd) throws Exception{
		Process process = Runtime.getRuntime().exec("cmd /c "+cmd) ;
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())) ;
		StringBuilder sb = new StringBuilder() ;
		String line = "" ;
		while((line = reader.readLine())!=null){
			sb.append(line+"\r\n") ;
		}
		return sb.toString() ;
	}
	
	public static boolean disconnectVpn(String vpnName) throws Exception{
		boolean flag = true ;
		String cmd = "rasdial "+vpnName+" /disconnect";
		String result = executeCmd(cmd) ;
		if(result==null||!result.contains("没用连接")){
			flag = false ;
		}
		return flag ;
	}
	
	public static boolean connectVpn(String vpnName, String userName, String password) throws Exception{
		boolean flag = true ;
		String cmd = "rasdial "+vpnName+" "+userName+" "+ password ;
		String result = executeCmd(cmd) ;
		if(result==null||!result.contains("")){
			flag = false ;
		}
		return flag ;
	}
	
	
	public static void main(String[] args) throws Exception{
		System.out.println(connectVpn("baige","baigevpn.cn","10fenzhong")) ;
	}

}
