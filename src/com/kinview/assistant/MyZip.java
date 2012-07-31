package com.kinview.assistant;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.kinview.config.Config;
import com.kinview.config.print;

public class MyZip {
	
	// 解压指定zip文件
	public void unZip(String type,String fileName,ByteArrayInputStream dis) {// unZipfileName需要解压的zip文件名
		
		try {
			ZipInputStream zipIn = new ZipInputStream(dis);
			ZipEntry zipEntry = null;
			int readedBytes =0;
			byte[] buf = new byte[512];
			print.out("zip is call");
			while ((zipEntry = zipIn.getNextEntry()) != null) {
				print.out("zipEntry.getName()="+zipEntry.getName());
				print.out("!zipEntry.isDirectory()="+!zipEntry.isDirectory());
				print.out("zipEntry.getName().indexOf('type')>1="+(zipEntry.getName().indexOf("type")>1));
					if(!zipEntry.isDirectory()&& zipEntry.getName().endsWith(type)){
						//如果不是目录
						File file = new File(fileName);
						//创建父目录
						File parent = file.getParentFile();
						if (!parent.exists()) {
							parent.mkdirs();
						}
						file.createNewFile();
						FileOutputStream fileOut = new FileOutputStream(file);
						while ((readedBytes = zipIn.read(buf)) > 0) {
							fileOut.write(buf, 0, readedBytes);
						}
						print.out("zip file write");
						fileOut.close();
					}
//				}
			}
			zipIn.closeEntry();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/*
	 * 解压下载的压缩安装包
	 */
	public boolean unZipApk(String source,String purpose){
		try {
			File file_source = new File(source);
			ZipInputStream zipIn = new ZipInputStream(new FileInputStream(file_source));
			ZipEntry zipEntry = null;
			int readedBytes =0;
			byte[] buf = new byte[512];
			while ((zipEntry = zipIn.getNextEntry()) != null) {
					if(!zipEntry.isDirectory()&& zipEntry.getName().endsWith(".apk")){
						//如果不是目录
						File file = new File(purpose);
						//创建父目录
						File parent = file.getParentFile();
						if (!parent.exists()) {
							parent.mkdirs();
						}
						file.createNewFile();
						FileOutputStream fileOut = new FileOutputStream(file);
						while ((readedBytes = zipIn.read(buf)) > 0) {
							fileOut.write(buf, 0, readedBytes);
						}
						print.out("zip file write");
						fileOut.close();
						
						//删除原文件
						if(file_source.exists())
							file_source.delete();
						break;
					}
//				}
			}
			zipIn.closeEntry();
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	// 解压整个zip文件
	public void unZipFiles(FileInputStream fis) {// unZipfileName需要解压的zip文件名
		//删除旧的地图数据
		String p = Config.appPath + "map/" ;//+ Config.dutyRgnCode +"/";
		File temp = new File(p);
		if(temp.exists()){
			delFile(temp);
		}
		
		//解压地图
		String path = Config.appPath + "map/" ;
		File directory = new File(path);
		if(!directory.exists()){
			directory.mkdirs();
		}
		try {
			ZipInputStream zipIn = new ZipInputStream(fis);
			ZipEntry zipEntry = null;
			int readedBytes =0;
			byte[] buf = new byte[512];
			print.out("zip is call");
			while ((zipEntry = zipIn.getNextEntry()) != null) {
				print.out("zipEntry.getName()="+zipEntry.getName());
				print.out("!zipEntry.isDirectory()="+!zipEntry.isDirectory());
				print.out("zipEntry.getName().indexOf('type')>1="+(zipEntry.getName().indexOf("type")>1));
					if(!zipEntry.isDirectory()){
						//如果不是目录
						File file = new File(path+zipEntry.getName());	//创建文件
						//创建父目录
						File parent = file.getParentFile();
						if (!parent.exists()) {
							parent.mkdirs();
						}
						file.createNewFile();
						FileOutputStream fileOut = new FileOutputStream(file);
						while ((readedBytes = zipIn.read(buf)) > 0) {
							fileOut.write(buf, 0, readedBytes);
						}
						print.out("zip file write");
						fileOut.close();
					}
//				}
			}
			zipIn.closeEntry();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/*
	 * 压缩指定文件名的文件
	 */
	public byte[] zip(String fileName){
		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream zos = new ZipOutputStream(baos);
			
			int readedBytes=0;
			byte[] buf= new byte[512];
			ZipEntry ze = new ZipEntry(fileName.substring(fileName.lastIndexOf("/")+1,fileName.length()));
			ze.setSize(file.length());   
			ze.setTime(file.lastModified());  
			zos.putNextEntry(ze);
			while ((readedBytes = fis.read(buf)) > 0) {
				
				zos.write(buf, 0, readedBytes);
			}
			zos.closeEntry();
			zos.flush();
			zos.close();
			fis.close();
			return baos.toByteArray();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	private void delFile(File file){
		File[] list = file.listFiles();
		for(int i=0;i<list.length;i++){
			File f = list[i];
			if(f.isDirectory()){
				delFile(f);
			}
			f.delete();			
		}
	}
}
