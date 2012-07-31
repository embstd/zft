package com.kinview.update;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class DatabaseCopy {

	public void copy2Sdcard(){
		try{
//			拷贝数据库文件
			File fIn = new File("/data/data/com.kinview.zft/databases/zft");
			File fOut = new File("/sdcard/zft.db");
			docopy(fIn,fOut);
			
			//拷贝登陆文件
			fIn = new File("/data/data/com.kinview.zft/shared_prefs/login.xml");
			fOut = new File("/sdcard/login.xml");
			docopy(fIn,fOut);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void copy2SytemData(){
		try{
//			拷贝数据库文件
			File fOut = new File("/data/data/com.kinview.zft/databases/zft");
			File fIn= new File("/sdcard/zft.db");
			docopy(fIn,fOut);
			
			//拷贝登陆文件
			fOut = new File("/data/data/com.kinview.zft/shared_prefs/login.xml");
			fIn = new File("/sdcard/login.xml");
			docopy(fIn,fOut);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void docopy(File fIn,File fOut){
		try{
			fOut.getParentFile().mkdirs();
			FileInputStream fis = new FileInputStream(fIn);
			FileOutputStream fos = new FileOutputStream(fOut);
			byte[] buf = new byte[1024*1024];
			while(true){
				int len=0;
				len = fis.read(buf);
				if(len>-1){
					fos.write(buf, 0, len);
				}else{
					break;
				}
			}
			fos.close();
			fis.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean checkApkExist(Context context, String packageName) {
		if (packageName == null || "".equals(packageName))
			return false;
		try {
			ApplicationInfo info = context.getPackageManager()
					.getApplicationInfo(packageName,
							PackageManager.GET_UNINSTALLED_PACKAGES);

			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}
}
