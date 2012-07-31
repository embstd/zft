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
	
	//�����ַ�����
	public void setCenter(){
		buf[index++]= 0x1B;
		buf[index++]= 0x61;
		buf[index++]= 0x01;
	}
	//�����ַ�����(�ָ�����)
	public void setLeft(){
		buf[index++] = 0x1B;
		buf[index++] = 0x61;
		buf[index++] = 0x00;
	}
	//�����ַ�����(������)
	public void setRight(){
		buf[index++] = 0x1B;
		buf[index++] = 0x20;
	}
	//�����ַ�����(�ָ�����)
	public void reSetRight(){
		buf[index++] = 0x1B;
		buf[index++] = 0x20;
		buf[index++] = 0x00;
	}
	//�����ַ�����
	public void setBold(){
		buf[index++] = 0x1B;
		buf[index++] = 0x45;
		buf[index++] = 0x01;
	}
	
	//ȡ���ַ�����
	public void resetBoldtoNormal(){
		buf[index++] = 0x1B;
		buf[index++] = 0x45;
		buf[index++] = 0x00;
	}
	//����Ŵ�
	public void setHorizonalZoom(){
		buf[index++] = 0x1c;
		buf[index++] = 0x21;
	}
	
	//����Ŵ�
	public void setVerticalZoom(){
		buf[index++] = 0x1c;
		buf[index++] = 0x21;
	}
	
	//����,����Ŵ�
	public void setZoom(){
		buf[index++] = 0x1c;
		buf[index++] = 0x21;
	}
	
	public void setInit(){
		//��ʼ��ָ��1B 40
		buf[index++] = 0x1B;
        buf[index++] = 0x40;
	}
	
	public void setZouZhi(){
		//��ӡ����ָֽ��
		buf[index++] = 0x0C;
	}
	
	
	//��Unicode�ַ���ת��GBK�ַ���������
	public void TextOut(String data) throws UnsupportedEncodingException
	{
		byte []bs=data.getBytes("GBK");
		int len = bs.length;
		for (int i=0;i<len;i++)
			buf[index++]=bs[i];		
	}
	/**
	 * ��ӡ������
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
	 * ESCָ��ʵ�ִ�ӡ������
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
