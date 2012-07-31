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
	
	// ��ѹָ��zip�ļ�
	public void unZip(String type,String fileName,ByteArrayInputStream dis) {// unZipfileName��Ҫ��ѹ��zip�ļ���
		
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
						//�������Ŀ¼
						File file = new File(fileName);
						//������Ŀ¼
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
	 * ��ѹ���ص�ѹ����װ��
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
						//�������Ŀ¼
						File file = new File(purpose);
						//������Ŀ¼
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
						
						//ɾ��ԭ�ļ�
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
	
	
	// ��ѹ����zip�ļ�
	public void unZipFiles(FileInputStream fis) {// unZipfileName��Ҫ��ѹ��zip�ļ���
		//ɾ���ɵĵ�ͼ����
		String p = Config.appPath + "map/" ;//+ Config.dutyRgnCode +"/";
		File temp = new File(p);
		if(temp.exists()){
			delFile(temp);
		}
		
		//��ѹ��ͼ
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
						//�������Ŀ¼
						File file = new File(path+zipEntry.getName());	//�����ļ�
						//������Ŀ¼
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
	 * ѹ��ָ���ļ������ļ�
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
