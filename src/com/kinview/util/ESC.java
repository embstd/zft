package com.kinview.util;

import java.io.UnsupportedEncodingException;

public class ESC {
	public byte[] buf;
	public int index;
	public ESC(int length)
	{
		buf=new byte[length];
		index=0;
	}
	
	public final byte GS=0x1D;
	
	public void Enter(){
		
		buf[index++]=0x0D;
		buf[index++]=0x0A;
	}
	
	//设置字符居中
	public void setCenter(){
		buf[index++]= 0x1B;
		buf[index++]= 0x61;
		buf[index++]= 0x01;
	}
	//设置字符居左(恢复居中)
	public void setLeft(){
		buf[index++] = 0x1B;
		buf[index++] = 0x61;
		buf[index++] = 0x00;
	}
	//设置字符居右(不可用)
	public void setRight(){
		buf[index++] = 0x1B;
		buf[index++] = 0x20;
	}
	//设置字符居左(恢复居右)
	public void reSetRight(){
		buf[index++] = 0x1B;
		buf[index++] = 0x20;
		buf[index++] = 0x00;
	}
	//设置字符粗体
	public void setBold(){
		buf[index++] = 0x1B;
		buf[index++] = 0x45;
		buf[index++] = 0x01;
	}
	
	//取消字符粗体
	public void resetBoldtoNormal(){
		buf[index++] = 0x1B;
		buf[index++] = 0x45;
		buf[index++] = 0x00;
	}
	//横向放大
	public void setHorizonalZoom(){
		buf[index++] = 0x1c;
		buf[index++] = 0x21;
	}
	
	//纵向放大
	public void setVerticalZoom(){
		buf[index++] = 0x1c;
		buf[index++] = 0x21;
	}
	
	//横向,纵向放大
	public void setZoom(){
		buf[index++] = 0x1c;
		buf[index++] = 0x21;
	}
	
	public void setInit(){
		//初始化指令1B 40
		buf[index++] = 0x1B;
        buf[index++] = 0x40;
	}
	
	public void setZouZhi(){
		//打印并走纸指令
		buf[index++] = 0x0C;
	}
	
	
	//把Unicode字符串转成GBK字符串并发送
	public void TextOut(String data) throws UnsupportedEncodingException
	{
		byte []bs=data.getBytes("GBK");
		int len = bs.length;
		for (int i=0;i<len;i++)
			buf[index++]=bs[i];		
	}
	/**
	 * 打印条形码
	 */
	public void Code39(String data)
	{
		int m=69;//0x45
		int n=data.length();
		buf[index++]=GS;
		buf[index++]='k';
		buf[index++]=(byte)m;
		buf[index++]=(byte)(n+2);
		buf[index++]='*';
		for (int i=0;i<n;i++)
			buf[index++]=(byte)data.charAt(i);
		buf[index++]='*';
	}	
	
	/**
	 * ESC指令实现打印条形码
	 */
	
	public void printBarCode(String data){
		buf[index++] = 0x1d; 
		buf[index++] = 0x68; 
		buf[index++] = 120; 
		buf[index++] = 0x1d; 
		buf[index++] = 0x48; 
		buf[index++] = 0x01; 
		buf[index++] = 0x1d; 
		buf[index++] = 0x6B; 
		buf[index++] = 0x02;
		int n = data.length();
		for (int i =0; i<n ; i++){
			buf[index++] = (byte)data.charAt(i);
		}
		buf[index++] = 0x00;
	}

}
