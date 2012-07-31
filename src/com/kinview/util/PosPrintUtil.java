package com.kinview.util;

import java.io.IOException;
import java.io.OutputStream;

import android.bluetooth.BluetoothAdapter;

public class PosPrintUtil {
	
	public  static OutputStream outStream;
	public static ESC esc=new ESC(2048);
	
//	public BluetoothUtil(OutputStream outStream){
//		this.outStream = outStream;
//	}
	
	public static BluetoothAdapter getBluetoothAdapter(){
		BluetoothAdapter badapter = BluetoothAdapter.getDefaultAdapter();
		return badapter;
	}
	
	public  static  boolean posPrintProcess(){
		try {	 
//			WakeUpPritner();
			for(int i =0;i < 1;i++){
//				esc.setCenter();
//				esc.setBold();
//				esc.Code39("121ABC");
//				esc.Enter(); //回车换行
//				esc.TextOut("测试济强打印机ABCabc123\r\n");
//				esc.TextOut(formContent+"\r\n");
			}	
			printMessage(esc.buf,esc.index);
			esc.index=0;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//流的方式发送给打印机
	private static void printMessage(byte[] b,int len) {
		try {
			if (outStream != null)
				outStream.write(b, 0, len);
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
	
	public static void WakeUpPritner() 
	{
		byte[] b={'\0','\0','\0'};
		printMessage(b,3);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		byte[] b1={27,64};
		printMessage(b1,2);	
	}
	
}
