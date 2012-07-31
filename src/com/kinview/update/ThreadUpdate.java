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
			
			//�������Ǽ��汾   �߳�
			try {
				Config.apk.findUpdate();	//������
			} catch (Exception e1) {
//				e1.printStackTrace();
				
				//������粿����,�ٵ�һ��
				try {
					sleep(2000);
				} catch (InterruptedException e) {
				}
				try {
					Config.apk.findUpdate();	//������
				} catch (Exception e2) {
//					e2.printStackTrace();
					
					//������Ǹ��²���,�ͷ�����Ϣ,��������
//					mHandler.sendMessageDelayed(mHandler.obtainMessage(
//							Msg.LOGIN_CONTINUELOAD, 100), 0);
//					return;
				}
			}
			
//			��������
//			Config.apk.setVersion(1.0f);
//			Config.apk.setType("0");
			
			//ǿ������
			if(Config.version<Config.apk.getVersion() && Config.apk.getType().equals("1")){
				mHandler.sendMessageDelayed(mHandler.obtainMessage(
						Msg.LOGIN_FORCEUPDATE,0), 0);
			}else if(Config.version<Config.apk.getVersion() && Config.apk.getType().equals("0")){
				mHandler.sendMessageDelayed(mHandler.obtainMessage(
						Msg.LOGIN_NEEDUPDATE,0), 0);
			}else{
			//���û���°汾,��ô������Ϣ,�������س�������
				mHandler.sendMessageDelayed(mHandler.obtainMessage(
						Msg.LOGIN_CONTINUELOAD,0), 0);
			}
			
		}else if(type.equals("update")){
			//�������Ǹ����߳�
			String url = Config.getApk_url();
			print.out("url="+url);
			//���Կ�ʼ
//			print.out("url="+url);
//			url = "http://192.168.16.1/cgt1.2.zip";
			
			//���Խ���
			try{
				download(url);
			}catch(FileNotFoundException e){
				//�����ļ������ڴ���
				mHandler.sendMessageDelayed(mHandler.obtainMessage(
						3, "�ļ�������!"), 0);
			}catch(MyException e){
				if(e.getType()==0){
					//type=0����Ĭ�ϴ���
					e.printStackTrace();
				}else if(e.getType()==1){
					//type=1,˵����ִ�гɹ��׳����쳣,���سɹ���Ϣ
					mHandler.sendMessageDelayed(mHandler.obtainMessage(
							2, e.getMessage()), 0);
				}
			}catch(ConnectException e){
				//���ط����������ڴ���
				mHandler.sendMessageDelayed(mHandler.obtainMessage(
						3, "������������!"), 0);
			}catch (Exception e) {
				//��ӡ��������
				e.printStackTrace();
			}
		}
		
	}

	private void download(String url) throws Exception {
		if (!URLUtil.isNetworkUrl(url)) {
			throw new MyException("URL��ʽ����ȷ");
		} else {
			// ��ȡ�ļ�������չ��
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

			/* ������ʱ�ļ� */
//			File myTempFile = File.createTempFile(fileNa, "." + fileEx);
			File myTempFile = File.createTempFile(Config.packageName, "." + fileEx);
			/* ȡ��վ���̰�·�� */
			String currentTempFilePath = myTempFile.getAbsolutePath();
			/* ���ļ�д���ݴ��� */
			FileOutputStream fos = new FileOutputStream(myTempFile);
			byte buf[] = new byte[128];
			thread.start(); //ˢ�½�����
			do {
				int numread = is.read(buf);
				if (numread <= 0) {
					break;
				}
				fileread +=128;
				fos.write(buf, 0, numread);
			} while (true);
			is.close();
			/* ���ļ����а�װ */
			//�ж��ļ��Ƿ�Ϊѹ���ļ�
			if(fileEx.equals("zip")){
				mHandler.sendMessageDelayed(mHandler.obtainMessage(
						4,""), 0);
				//��ô��ѹ��
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
