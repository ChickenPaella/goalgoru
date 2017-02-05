package com.gorugoru.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetUtil {
	public static String getHostName(){
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String getHostAddr(){
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
