package com.kinview.update;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import com.kinview.config.print;

public class Action {
	
	public static MessageXml getXml(String url) {
		try {
			HttpURLConnection urlc = (HttpURLConnection) ((new URL(url))
					.openConnection());
			urlc.setRequestProperty("User-Agent", "Android Application:");
			urlc.setRequestProperty("Connection", "close");
			urlc.setConnectTimeout(1000 * 5); // mTimeout is in seconds
			try{
				urlc.connect();
			}catch(SocketTimeoutException e){
				//cannot open socket
				e.printStackTrace();
				return new MessageXml(3);
			}catch(Exception e){
				//连接错误 ，服务器无响应
				e.printStackTrace();
				return new MessageXml(4);
			}
			
			if (urlc.getResponseCode() == 200) {
				InputStream is = urlc.getInputStream();
				print.out("ccccccccc="+urlc.getContentLength());
				byte b[] = new byte[1024*1024*2];
				int i = 0;
				int temp = 0;
				while ((temp = is.read()) != -1) { // 当没有读取完时，继续读取
					b[i] = (byte) temp;
					i++;
				}
				is.close();
				
				return new MessageXml(0, new String(b, 0, i));
//				return new MessageXml(0, new String(b));
			}
		} catch (MalformedURLException e1) {
			return new MessageXml(3);
		} catch (IOException e) {
			return new MessageXml(4);
		}
		print.out("action-----success,but sevice is not conrect");
		return new MessageXml(4);

	}

}
