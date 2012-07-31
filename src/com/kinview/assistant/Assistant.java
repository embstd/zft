package com.kinview.assistant;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.kobjects.base64.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.kinview.config.Config;
import com.kinview.config.print;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.preference.EditTextPreference;

public class Assistant
{

	// @SuppressWarnings("unchecked")
	public static ArrayList<Object> parseXml(String xml, Class objectClass)
	{
//		print.out("PARSEXML = " + xml);
		ArrayList<Object> list = new ArrayList<Object>();
		// ��ȡ����
		Field[] fields = objectClass.getDeclaredFields();
		ArrayList<String> paramList = new ArrayList<String>();
		for (int j = 0; j < fields.length; j++)
		{
			paramList.add(fields[j].getName());
		}
        DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
        try
		{
			DocumentBuilder dombuilder = domfac.newDocumentBuilder();
			ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
//			print.out("bais >>>>>>>>>>>>>>>>>>>>>" + xml.getBytes().toString());
			Document doc = dombuilder.parse(bais);
//			print.out("doc>>>>>>>>" + doc.getDocumentElement());
			// ��ȡ��
			Element resultSet = doc.getDocumentElement();
//			print.out("resultSet >>>>>>>  " + resultSet.toString());
			NodeList resultSetList = resultSet.getChildNodes(); // ��
			// ������
			if (resultSetList != null
					&& resultSet.getNodeName().toLowerCase().equals(
							"ResultSet".toLowerCase()))
			{
				// ����ҵ��ڵ�����ô ��ʼѭ��row;
				for (int i = 0; i < resultSetList.getLength(); i++)
				{
					Node rowNode = resultSetList.item(i);
					if (rowNode.getNodeName().equals("item"))
					{
						Object obj = objectClass.newInstance();
						// ��ȡxml
						NodeList cols = rowNode.getChildNodes();
						// ѭ��xml��
						for (int j = 0; j < cols.getLength(); j++)
						{
							Node col = cols.item(j);
							// ѭ�������ֶ�
							for (int k = 0; k < paramList.size(); k++)
							{
								if (col.getNodeName().toLowerCase().equals(
										paramList.get(k).toLowerCase()))
								{
									String name = paramList.get(k);
									String funcString = "set"
											+ name.substring(0, 1)
													.toUpperCase()
											+ name.substring(1, name.length());
									try
									{
										Field field = obj.getClass()
												.getDeclaredField(name);
										Type type = field.getType();
										Method m = null;
										if (type.equals(String.class))
										{
											m = obj.getClass()
													.getDeclaredMethod(
															funcString,
															String.class);
											m.invoke(obj, getNodeValue(col));
										}
										else if (type.equals(int.class))
										{
											m = obj.getClass()
													.getDeclaredMethod(
															funcString,
															int.class);
											m
													.invoke(
															obj,
															Integer
																	.parseInt(getNodeValue(col)));
										}
										else if (type.equals(double.class))
										{
											m = obj.getClass()
													.getDeclaredMethod(
															funcString,
															double.class);
											m
													.invoke(
															obj,
															Double
																	.parseDouble(getNodeValue(col)));
										}
										else if (type.equals(float.class))
										{
											m = obj.getClass()
													.getDeclaredMethod(
															funcString,
															float.class);
											m
													.invoke(
															obj,
															Float
																	.parseFloat(getNodeValue(col)));
										}
										break;
									}
									catch (Exception e)
									{
										e.printStackTrace();
									}
								}
							}
						}
						list.add(obj);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
//			print.out("Exception e" + e.getMessage());
		}
		return list;
	}

	private static String getNodeValue(Node node)
	{
		if (node.getChildNodes().item(0) == null)
		{
			return "";
		}
		String s = node.getChildNodes().item(0).getNodeValue().trim();
		if (s.equals("null"))
		{
			return "";
		}
		else
		{
			return s;
		}
	}

	/*
	 * ��String->zip->String
	 */
	public static String upZipBaseString(String data)
	{
		try
		{
			ByteArrayInputStream bais = new ByteArrayInputStream(Base64
					.decode(data));
			ZipInputStream zis = new ZipInputStream(bais);

			ZipEntry entry = null;
			byte[] buf = new byte[4096];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			while ((entry = zis.getNextEntry()) != null)
			{
				if (entry.isDirectory())
				{
					continue;
				}
				else
				{
					int count = 0;
					while ((count = zis.read(buf)) > 0)
					{
						baos.write(buf, 0, count);
					}
					baos.flush();
					// ֻ����һ���ļ�
					break;
				}
			}
			zis.close();
			bais.close();
			String s = baos.toString("gbk");
//			String s = baos.toString();
			// String s = new String(baos.toByteArray(),"gbk");
			baos.close();
			return s;

		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}

	}
	
	public static void main(String[] args) {
		String s = upZipBaseString("UEsDBBQAAAAIAMhTp0Dww3xUOAAAAEEAAAAEAAAAZGF0YbOxr8jNUShLLSrOzM+zVTLUM1BSSM1Lzk/JzEu3VQoNcdO1ULK34+WyCUotLs0pCU4tAXH0ETwAUEsBAhQAFAAAAAgAyFOnQPDDfFQ4AAAAQQAAAAQAAAAAAAAAAAAAAAAAAAAAAGRhdGFQSwUGAAAAAAEAAQAyAAAAWgAAAAAA");
		System.out.println(s);
	}
	

	public static void parseCursor(Object obj, Cursor cur) throws Exception
	{
		Field[] fields = obj.getClass().getDeclaredFields();
		// for(int i=0;i<fields.length;i++){
		// print.out("getName="+fields[i].getName());
		// }

		// ���ݽ��������ֵ
		int colCount = cur.getColumnCount();
		for (int i = 0; i < colCount; i++)
		{
			String name = cur.getColumnName(i);
			Field field = obj.getClass().getDeclaredField(name);
			Type type = field.getType();
			String funcString = "set"
					+ field.getName().substring(0, 1).toUpperCase()
					+ field.getName().substring(1, field.getName().length());
			if (type.equals(int.class))
			{
				Method m = obj.getClass().getDeclaredMethod(funcString,
						int.class);
				m.invoke(obj, cur.getInt(i));
			}
			else if (type.equals(String.class))
			{
				Method m = obj.getClass().getDeclaredMethod(funcString,
						String.class);
				m.invoke(obj, (cur.getString(i)));
				// m.invoke(obj,(new
				// String(cur.getString(i).getBytes("ISO-8859-1"),"gbk")));//�������ݿ�����Ҫת����
			}
			else if (type.equals(Long.class))
			{
				Method m = obj.getClass().getDeclaredMethod(funcString,
						Long.class);
				m.invoke(obj, cur.getLong(i));
			}
			else if (type.equals(Float.class))
			{
				Method m = obj.getClass().getDeclaredMethod(funcString,
						Float.class);
				m.invoke(obj, cur.getFloat(i));
			}
			else if (type.equals(Double.class))
			{
				Method m = obj.getClass().getDeclaredMethod(funcString,
						Double.class);
				m.invoke(obj, cur.getDouble(i));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Object> parseXml2(String xml, Class objectClass)
	{
		// print.out("Assistant parseXml="+xml);
		// print.out("objectClass="+objectClass);
		ArrayList<Object> list = new ArrayList<Object>();
		// xml = xml.replace("gb2312", "utf-8");
		// print.out("apk xml ="+xml);
		// ��ȡ����
		Field[] fields = objectClass.getDeclaredFields();
		ArrayList<String> paramList = new ArrayList<String>();
		for (int j = 0; j < fields.length; j++)
		{
			// print.out("name="+fields[j].getName());
			paramList.add(fields[j].getName());
		}

		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder dombuilder = domfac.newDocumentBuilder();
			ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
			// InputStream is = new FileInputStream("d:\\AnroidApk.xml");
			Document doc = dombuilder.parse(bais);
			// ��ȡ��
			Element resultSet = doc.getDocumentElement();
			NodeList resultSetList = resultSet.getChildNodes();

			// ������
			if (resultSetList != null
					&& resultSet.getNodeName().toLowerCase().equals(
							"ResultSet".toLowerCase()))
			{
				// ����ҵ��ڵ�����ô ��ʼѭ��row;
				for (int i = 0; i < resultSetList.getLength(); i++)
				{
					Node rowNode = resultSetList.item(i);
					if (rowNode.getNodeName().equals("item"))
					{
						Object obj = objectClass.newInstance();
						// ��ȡxml
						NodeList cols = rowNode.getChildNodes();
						// ѭ��xml��
						for (int j = 0; j < cols.getLength(); j++)
						{
							Node col = cols.item(j);
							// ѭ�������ֶ�
							for (int k = 0; k < paramList.size(); k++)
							{
								if (col.getNodeName().toLowerCase().equals(
										paramList.get(k).toLowerCase()))
								{
									String name = paramList.get(k);
									String funcString = "set"
											+ name.substring(0, 1)
													.toUpperCase()
											+ name.substring(1, name.length());
									try
									{
										Field field = obj.getClass()
												.getDeclaredField(name);
										Type type = field.getType();
										Method m = null;
										if (type.equals(String.class))
										{
											m = obj.getClass()
													.getDeclaredMethod(
															funcString,
															String.class);
											m.invoke(obj, getNodeValue(col));
										}
										else if (type.equals(int.class))
										{
											m = obj.getClass()
													.getDeclaredMethod(
															funcString,
															int.class);
											m
													.invoke(
															obj,
															Integer
																	.parseInt(getNodeValue(col)));
										}
										else if (type.equals(double.class))
										{
											m = obj.getClass()
													.getDeclaredMethod(
															funcString,
															double.class);
											m
													.invoke(
															obj,
															Double
																	.parseDouble(getNodeValue(col)));
										}
										else if (type.equals(float.class))
										{
											m = obj.getClass()
													.getDeclaredMethod(
															funcString,
															float.class);
											m
													.invoke(
															obj,
															Float
																	.parseFloat(getNodeValue(col)));
										}
										break;
									}
									catch (Exception e)
									{
										e.printStackTrace();
									}
								}
							}
						}
						list.add(obj);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	
	public static Bitmap getResizeBitmapByFileName(String fileName,float pictureSize){
		File file = new File(fileName);
		if(!file.exists()|| file.length()==0){
			//�ļ�������
			return null;
		}
		FileInputStream fStream=null;
		try {
			fStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
			//�ļ�δ�ҵ�
			return null;
		}
		Bitmap bitmap =null;
		try{
			bitmap = BitmapFactory.decodeStream(fStream);
		}catch(Exception e){
			//���س���
			return null;
		}
		if(bitmap==null){
			return null;
		}
		float scale=1;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int temp=width>height?width:height;
		scale = pictureSize/temp;
		if(scale!=1){
			return resizeBitmap(bitmap,width,height,scale,0);
		}else{
			return bitmap;
		}
	}
	
	/*
	 * ת��ͼƬ
	 */
	public static Bitmap resizeBitmap(Bitmap bitmap,int width,int height ,float scale,float rotate){
		try{
			Matrix matrix = new Matrix();
			matrix.postScale(scale, scale);
			if(rotate!=0){
				matrix.postRotate(rotate);
			}
			Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
					height, matrix, true);
			bitmap.recycle();
			bitmap=null;
			System.gc();
			return resizedBitmap;
		}catch(Exception e){
			return bitmap;
		}
	}
	
//	public static boolean findPicReference(String name){
//		boolean b = false;
//		for(int i=0;i<Config.listHistory.size();i++){
//			History history = Config.listHistory.get(i);
//			if(history.getPicNum()>0 && history.getState()!=11){
//				if(history.getPicList().indexOf(name)!=-1)
//					b = true;
//			}
//		}
//		return b;
//	}
	
	// /*
	// * ���ر������ɵ�����Ψһ��ʶ
	// */
	// public static String getUniqueID(){
	// Date date = new Date();
	// Format format = new SimpleDateFormat("yyMMddHHmmss");
	// return format.format(date);
	// }
	//	
	// /*
	// * �����ص���base64��������,����Ϊ�����ļ�
	// * ��������,���ص�ʱ����ѹ����
	// */
	// public static void saveZipMedia(String type,String fileName,String data){
	// print.out("Assistant type="+type);
	// print.out("Assistant fileName="+fileName);
	//		
	// if(data.equals("")|| type.equals("")|| fileName.equals("")){
	// return;
	// }
	// //ת��
	// MyBase64 mb = new MyBase64();
	// byte[] bytes = mb.decode(data);
	//		
	// //��ѹ��
	// ByteArrayInputStream dis = new ByteArrayInputStream(bytes);
	// MyZip mz = new MyZip();
	// mz.unZip(type, fileName, dis);
	// }
	//	
	// /*
	// * �����ض�ý���ļ�����,�����ַ���
	// * δѹ��,�ϴ���ý���ļ�ʱ,��ѹ��
	// */
	// public static String encodeMedia(String fileName){
	//		
	// File file = new File(fileName);
	// byte[] data = new byte[(int)file.length()];
	// try {
	// FileInputStream fis = new FileInputStream(file);
	// fis.read(data);
	// } catch (FileNotFoundException e1) {
	// e1.printStackTrace();
	// } catch (IOException e1) {
	// e1.printStackTrace();
	// }
	//		
	// MyBase64 mb = new MyBase64();
	// try {
	// return "<![CDATA[" + mb.encode(data).replace("\r\n", "") +"]]>";
	// } catch (Exception e) {
	//			
	// e.printStackTrace();
	// return "";
	// }
	// }
	//	
	
	/*
	 * ѹ������
	 * �˹������ϱ�ʱ,��ʱδ��
	 */
	 public static String encodeZipMedia(String fileName){
	 //ѹ���ļ�
	 MyZip mz = new MyZip();
	 byte[] data = mz.zip(fileName);
			
	 //ת��base64����
	 MyBase64 mb = new MyBase64();
	 try {
	 return mb.encode(data);
	 } catch (Exception e) {
	 e.printStackTrace();
	 return "";
	 }
	 }
	//	
	// /*
	// * ��ȡ��ǰ�ֻ�ʱ��
	// */
	// public static String getDate(){
	// String s = "";
	// Date d = new Date();
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// s = sdf.format(d);
	// // print.out("date="+s);
	// return s;
	//		
	// }
	//	
	// /*
	// * ���ڱ�����ʷ������ʾʱ,ת������ʾ����ʱ,��ʾ����ɫ
	// */
	// public static int getMissionColor(int state){
	// int red = Color.parseColor("#FF0000");
	// int green = Color.parseColor("#00FF00");
	// int black = Color.parseColor("#333333");
	// if(state==11){
	// return black;
	// }
	// int shi = state/10;
	// int ge = state%10;
	// if(shi==4||ge==4){
	// return red;
	// }
	// if(shi==2|| ge==2 || shi==3|| ge==3){
	// return green;
	// }
	//		
	// return red;
	// }

}
