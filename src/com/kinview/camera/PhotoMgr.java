package com.kinview.camera;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Environment;

import com.kinview.config.print;

public class PhotoMgr {

	private ArrayList<Photo> listPhoto = new ArrayList<Photo>();
	
	private void loadItemList() {
		listPhoto.clear();
		
		File fileRoot = new File(ActivityPhoto.path);
		if (checkSDKard()) {
			File files[] = fileRoot.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].getName().indexOf(".") >= 0) {
						String name = files[i].getName().substring(
								files[i].getName().indexOf("."));
						if (name.toLowerCase().equals(ActivityPhoto.fileType)) {
							Date date = new Date(files[i].lastModified());
//							if(checkDate(date)){
								Photo term = new Photo();
								//获取名称
								term.setName(files[i].getName());
								//获取时间
								term.setDate(date);
								listPhoto.add(term);
//							}
						}
					}
				}
			}
		}
		sort();
	}
	
//	检查时间是否超时
//	private boolean checkDate(Date date){
//		Date now = new Date();
//		if(now.getTime() - date.getTime() > ActivityPhoto.timeLimit * 60000){	//60秒 1000毫秒
//			return false;
//		}
//		return true;
//	}
	
	private void sort(){
		Collections.sort(listPhoto,new Comparator<Photo>(){    
	           public int compare(Photo r1, Photo r2) {    
	               return (int)(r2.getDate().getTime()-r1.getDate().getTime());    
	            }    
	        });   
	}
	
	public ArrayList<Photo> getItemsList(){
		loadItemList();
		return listPhoto;
	}
	
	
	public List<Map<String, Object>> getItemsForList(){
		//加载列表
		loadItemList();
		
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		int size = listPhoto.size();
		if(size==0){
			return data;
		}
		if(size>10)
			size=10;
		HashMap<String, Object> item;
		for(int i=0;i<size;i++){
			Photo term = listPhoto.get(i);
			item = new HashMap<String, Object>();
			item.put("name", term.getName());
			Date date = term.getDate();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss ");
			String sdate = formatter.format(date);
			item.put("date", "创建时间:\n"+sdate);
			data.add(item);
		}
		return data;
	}
	
	public ArrayList<Photo> getItemsList(String filesName){
		loadItemList(filesName);
		return listPhoto;
	}
	
	public List<Map<String, Object>> getItemsForList(String filesName){
		//加载列表
		loadItemList(filesName);
		
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		int size = listPhoto.size();
		if(size==0){
			return data;
		}
		if(size>10)
			size=10;
		HashMap<String, Object> item;
		for(int i=0;i<size;i++){
			Photo term = listPhoto.get(i);
			item = new HashMap<String, Object>();
			item.put("name", term.getName());
			Date date = term.getDate();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss ");
			String sdate = formatter.format(date);
			item.put("date", "创建时间:\n"+sdate);
			data.add(item);
		}
		return data;
	}
	
	private void loadItemList(String filesName) {
		listPhoto.clear();
		if(filesName.equals("")){
			return;
		}
		String[] fileNameStrings = filesName.split(",");
//		ArrayList<File> fileList = new ArrayList<File>();
		for(int i=0;i<fileNameStrings.length;i++){
			String name = fileNameStrings[i].trim();
			if(!name.equals("")){
				name = ActivityPhoto4Download.path + name + ActivityPhoto4Download.fileType;
				print.out("name="+name);
				File file = new File(name);
				//如果文件存在
				print.out("bool ="+file.exists());
				if(file.exists()){
					print.out("name= is cun zai");
					Photo p = new Photo();
					p.setName(name.substring(name.lastIndexOf("/")+1,name.length()));
					p.setDate(new Date(file.lastModified()));
					listPhoto.add(p);
				}else{
					//如果不存在
					print.out("download picture file not found");
				}
			}
		}
		
		sort();
	}
	
	public Photo loadItemByFileName(String fileName){
		if(fileName.equals("")){
			return null;
		}
		File file = new File(ActivityPhoto.path + fileName);
		if(!file.exists()){
			return null;
		}
		Photo item = new Photo();
		item.setName(fileName);
		item.setDate(new Date(file.lastModified()));
		return item;
	}
	
	public boolean checkSDKard(){
		return Environment.getExternalStorageState().equals(
		        android.os.Environment.MEDIA_MOUNTED);
	}
}
