package com.kinview.assistant;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.kobjects.base64.Base64;

public class MyBase64 {
	
	/*
	 * 将base64编码字符串,解码为byte数组返回
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
	 * 转换字符(base64编码)为字节流,并按指定好的文件名称,路径保存为文件
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
					return false;	//新建文件失败
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;	//新建文件失败
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
	 * 根据文件名转换base64编码
	 */
	public String encode(String fileName) throws Exception{
		File file = new File(fileName);
		return encode(file);
	}
	
	/*
	 * 从文件转换成base64编码
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
	 * 将字节流转成base64编码
	 */
	public String encode(byte[] buffer)  throws Exception{
		if(buffer!=null){
			return Base64.encode(buffer);
		}else{
			return "";
		}
		
	}
	
	
}
