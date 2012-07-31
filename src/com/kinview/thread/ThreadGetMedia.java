package com.kinview.thread;

import java.io.File;

import com.kinview.assistant.Assistant;
import com.kinview.config.Config;
import com.kinview.config.print;
import com.kinview.webservice.CommonResult;
import com.kinview.webservice.Server;

public class ThreadGetMedia extends Thread{

	private String filesName ="";
	private String filesType ="";
	private String path = "";
	
	public ThreadGetMedia(String filesName,String filesType){
		this.filesName = filesName;
		this.filesType = filesType;
		
		if(filesType.equals(".jpg")){
			path = Config.appPath + "pic/";
		}else{
			path = Config.appPath + "wav/";
		}
		print.out("ThreadGetMedia path="+path);
		
	}
	
	public void run(){
		String[] names = filesName.split(",");
		for(int i=0;i<names.length;i++){
			String fileName = names[i].trim();
			if(!fileName.equals("")){
				String mediaNum = fileName;
				fileName = path+fileName + filesType;
				File file = new File(fileName);
				if(!file.exists()){
					CommonResult cr = null;
					try {
//						cr = Server.downloadMedia(mediaNum);
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(cr!=null){
						print.out("getErrorCode="+cr.getErrorCode());
						print.out("getErrorDesc="+cr.getErrorDesc());
						String xml =cr.getResultStr();
						if(xml !=null ){
//							Assistant.saveZipMedia(filesType, fileName, xml);
							print.out("file write ok");
						}
					}
					
				}
			}
		}
	}
}
