package com.kinview.assistant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.kobjects.base64.Base64;

public class MyBase64 {
	
	/*
	 * ��base64�����ַ���,����Ϊbyte���鷵��
	 */
	public byte[] decode(String data){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			Base64.decode(data,baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
	
	/*
	 * ת���ַ�(base64����)Ϊ�ֽ���,����ָ���õ��ļ�����,·������Ϊ�ļ�
	 */
	public boolean decode(String fileName,String data){
		if(data.length()==0){
			return false;
		}
		File file = new File(fileName);
		if(!file.exists()){
			file.getParentFile().mkdirs();
			try {
				if(!file.createNewFile()){
					return false;	//�½��ļ�ʧ��
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;	//�½��ļ�ʧ��
			}
		}
		FileOutputStream fos=null;
		try {
			fos = new FileOutputStream(file);
			Base64.decode(data,fos);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * �����ļ���ת��base64����
	 */
	public String encode(String fileName) throws Exception{
		File file = new File(fileName);
		return encode(file);
	}
	
	/*
	 * ���ļ�ת����base64����
	 */
	public String encode(File file)  throws Exception{
		if(!file.exists()){
			throw new Exception("file='"+file.getAbsolutePath()+"' is not exists");
		}
		byte[] buffer = new byte[(int)file.length()];
		FileInputStream fis = null;
		fis = new FileInputStream(file);
		fis.read(buffer);
		return Base64.encode(buffer);
	}
	
	/*
	 * ���ֽ���ת��base64����
	 */
	public String encode(byte[] buffer)  throws Exception{
		if(buffer!=null){
			return Base64.encode(buffer);
		}else{
			return "";
		}
		
	}
	
	
}
