package com.kinview.update;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;

import android.os.Handler;
import android.webkit.URLUtil;

import com.kinview.assistant.MyZip;
import com.kinview.config.Config;
import com.kinview.config.Msg;
import com.kinview.config.print;

public class ThreadUpdate extends Thread{
	private Handler mHandler = null;
	private int filesize=0;
	private int fileread=0;
	private boolean run = true;
	private String type;

	public ThreadUpdate(Handler mHandler,String type){
		this.mHandler = mHandler;
		this.type = type;
	}

	@Override
	public void run() {
		if(type.equals("check")){
			
			//启动的是检查版本   线程
			try {
				Config.apk.findUpdate();	//检查更新
			} catch (Exception e1) {
//				e1.printStackTrace();
				
				//如果网络部正常,再等一下
				try {
					sleep(2000);
				} catch (InterruptedException e) {
				}
				try {
					Config.apk.findUpdate();	//检查更新
				} catch (Exception e2) {
//					e2.printStackTrace();
					
					//如果还是更新不到,就发送消息,继续加载
//					mHandler.sendMessageDelayed(mHandler.obtainMessage(
//							Msg.LOGIN_CONTINUELOAD, 100), 0);
//					return;
				}
			}
			
//			升级测试
//			Config.apk.setVersion(1.0f);
//			Config.apk.setType("0");
			
			//强制升级
			if(Config.version<Config.apk.getVersion() && Config.apk.getType().equals("1")){
				mHandler.sendMessageDelayed(mHandler.obtainMessage(
						Msg.LOGIN_FORCEUPDATE,0), 0);
			}else if(Config.version<Config.apk.getVersion() && Config.apk.getType().equals("0")){
				mHandler.sendMessageDelayed(mHandler.obtainMessage(
						Msg.LOGIN_NEEDUPDATE,0), 0);
			}else{
			//如果没有新版本,那么发送消息,继续加载程序数据
				mHandler.sendMessageDelayed(mHandler.obtainMessage(
						Msg.LOGIN_CONTINUELOAD,0), 0);
			}
			
		}else if(type.equals("update")){
			//启动的是更新线程
			String url = Config.getApk_url();
			print.out("url="+url);
			//测试开始
//			print.out("url="+url);
//			url = "http://192.168.16.1/cgt1.2.zip";
			
			//测试结束
			try{
				download(url);
			}catch(FileNotFoundException e){
				//返回文件不存在错误
				mHandler.sendMessageDelayed(mHandler.obtainMessage(
						3, "文件不存在!"), 0);
			}catch(MyException e){
				if(e.getType()==0){
					//type=0，是默认错误
					e.printStackTrace();
				}else if(e.getType()==1){
					//type=1,说明是执行成功抛出的异常,返回成功信息
					mHandler.sendMessageDelayed(mHandler.obtainMessage(
							2, e.getMessage()), 0);
				}
			}catch(ConnectException e){
				//返回服务器不存在错误
				mHandler.sendMessageDelayed(mHandler.obtainMessage(
						3, "服务器不存在!"), 0);
			}catch (Exception e) {
				//打印其他错误
				e.printStackTrace();
			}
		}
		
	}

	private void download(String url) throws Exception {
		if (!URLUtil.isNetworkUrl(url)) {
			throw new MyException("URL格式不正确");
		} else {
			// 获取文件名和扩展名
			String fileEx = url.substring(url.lastIndexOf(".") + 1,
					url.length()).toLowerCase();
//			String fileNa = url.substring(url.lastIndexOf("/") + 1, url
//					.lastIndexOf("."));

			URL myURL = new URL(url);
			URLConnection conn = myURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			if (is == null) {
				throw new MyException("stream is null");
			}
			filesize = conn.getContentLength();
			System.out.println("length = " + filesize);

			/* 创建临时文件 */
//			File myTempFile = File.createTempFile(fileNa, "." + fileEx);
			File myTempFile = File.createTempFile(Config.packageName, "." + fileEx);
			/* 取得站存盘案路径 */
			String currentTempFilePath = myTempFile.getAbsolutePath();
			/* 将文件写入暂存盘 */
			FileOutputStream fos = new FileOutputStream(myTempFile);
			byte buf[] = new byte[128];
			thread.start(); //刷新进度条
			do {
				int numread = is.read(buf);
				if (numread <= 0) {
					break;
				}
				fileread +=128;
				fos.write(buf, 0, numread);
			} while (true);
			is.close();
			/* 打开文件进行安装 */
			//判断文件是否为压缩文件
			if(fileEx.equals("zip")){
				mHandler.sendMessageDelayed(mHandler.obtainMessage(
						4,""), 0);
				//那么解压缩
				MyZip mz = new MyZip();
				String purpose = currentTempFilePath.replace(".zip", ".apk");
				mz.unZipApk(currentTempFilePath, purpose);
				currentTempFilePath = purpose; 
			}
			sleep(100);
			throw new MyException(1, currentTempFilePath);
		}
	}
	
	Thread thread = new Thread(){
		
		public void run(){
			while(run && fileread<filesize){
				int progress = fileread*100/filesize;
				mHandler.sendMessageDelayed(mHandler.obtainMessage(
						1, progress), 0);
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(run){
				mHandler.sendMessageDelayed(mHandler.obtainMessage(
					1, 100), 0);
			}
		}
	};

}
