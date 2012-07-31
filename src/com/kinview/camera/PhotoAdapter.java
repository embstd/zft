package com.kinview.camera;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.kinview.assistant.Assistant;
import com.kinview.zft.R;
import com.kinview.config.print;

public class PhotoAdapter extends BaseAdapter {
	public ArrayList<Photo> listItem =null;
	Context context =null;
	private ArrayList<String> listSelect = new ArrayList<String>();
	private HashMap<String,CheckBox> mapCheckBox = new HashMap<String,CheckBox>();
	public static HashMap<String,Bitmap> mapBitmap = new HashMap<String,Bitmap>(); 
	private float pictureSize=ActivityPhoto.pictureSize;	//图片大小100像素
	private String path="";
	private Handler mHandler = null;
	
	public ArrayList<String> getListSelect() {
		return listSelect;
	}
	
	public PhotoAdapter(Context context,Handler mHandler,String path,ArrayList<Photo> listItem){
		this.path= path;
		this.listItem = listItem;
		this.context = context;
		this.mHandler = mHandler;
	}
	
	/*
	 * 查看,任务里的图片
	 */
	public PhotoAdapter(Context context,Handler mHandler,String path,ArrayList<Photo> listItem,String data){
		this.path= path;
		this.listItem = listItem;
		this.context = context;
		this.mHandler = mHandler;
		String[] s = data.split(",");
		for(int i=0;i<s.length;i++){
			String temp = s[i];
			//增加回传校验
			File file = new File(temp);
			if(file.exists()){
				temp = temp.substring(temp.lastIndexOf("/")+1,temp.length());
				listSelect.add(temp);
			}
			//
//			temp = temp.substring(temp.lastIndexOf("/")+1,temp.length());
//			listSelect.add(temp);
		}
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		System.out.println("recordList size="+listItem.size());
		return listItem.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return createView(position);
	}
	
	private View createView(int position){
		System.out.println("position=="+position);
		Photo term = listItem.get(position);
		
		LayoutInflater factory = LayoutInflater.from(context);
		RelativeLayout relativeLayout = new RelativeLayout(context);
		factory.inflate(R.layout.photo_list, relativeLayout,true); 
		
		TextView tv_name = (TextView)relativeLayout.findViewById(R.id.photo_list_textview1);
		TextView tv_date = (TextView)relativeLayout.findViewById(R.id.photo_list_textview2);
//		TextView tv_length = (TextView)relativeLayout.findViewById(R.id.photo_list_textview3);
		CheckBox cb_select = (CheckBox)relativeLayout.findViewById(R.id.photo_list_checkbox);
		ImageView iv_icon = (ImageView)relativeLayout.findViewById(R.id.photo_list_icon);
		
		tv_name.setText(term.getName());
		
		Date date = term.getDate();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss ");
		String sdate = formatter.format(date);
		tv_date.setText("创建时间:\n"+sdate);
//		tv_length.setText("时长:"+term.getLength());
		
		cb_select.setOnCheckedChangeListener(new MyOnCheckedChangeListener(cb_select,term.getName()));
//		listCheckBox.add(cb_select);
		mapCheckBox.put(""+position, cb_select);
//		for(int i=0;i<data.length;i++){
//			if(term.getName().equals(data[i])){
//				cb_select.setChecked(true);
//			}
//		}
		if(listSelect.indexOf(term.getName())>=0){
			cb_select.setChecked(true);
		}
		
		//设置预览图片
		Bitmap bitmap = mapBitmap.get(term.getName());
		if(bitmap!=null){
			iv_icon.setImageBitmap(bitmap);
		}else{
			bitmap = loadBitmap(term.getName());
			mapBitmap.put(term.getName(), bitmap);
			iv_icon.setImageBitmap(bitmap);
		}
		
		return relativeLayout;
	}
	
	private Bitmap loadBitmap(String name){
//		//加载预览图片
//		FileInputStream fStream=null;
//
//		File file = new File(path+name);
//		if(!file.exists()|| file.length()==0){
//			return null;
//		}
//		try {
//			fStream = new FileInputStream(file);
//		} catch (FileNotFoundException e1) {
////			e1.printStackTrace();
//			return null;
//		}
//
//		Bitmap bitmap = BitmapFactory.decodeStream(fStream);
//		return resizedBitmap(bitmap);
////		return bitmap;
		return Assistant.getResizeBitmapByFileName(path+name, pictureSize);
	}
	

	class MyOnCheckedChangeListener implements OnCheckedChangeListener
	{
		private String filename;
		private CheckBox checkbox;
		public MyOnCheckedChangeListener(CheckBox checkbox,String filename){
			this.checkbox = checkbox;
			this.filename = filename;
		}
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked)
			{
				File file = new File(ActivityPhoto.path + filename);
				Date d = new Date(file.lastModified());
				if(ActivityPhoto.stytle){
					//检查是不是上报模式
					if(checkDate(d)){
						if(listSelect.indexOf(filename)<0){
							listSelect.add(filename);
						}
					}else{
						checkbox.setChecked(false);
						listSelect.remove(filename);
//						mHandler.sendMessageDelayed(mHandler.obtainMessage(0,"此图片拍摄的时间超过指定的"+ActivityPhoto.timeLimit+"分钟\n不能上报,请拍摄最新的图片!"), 0);
						mHandler.sendMessageDelayed(mHandler.obtainMessage(0,"此图片拍摄的时间超过指定的24小时\n不能上报,请拍摄最新的图片!"), 0);
					}
				}else{
					//如果不是上报模式,可以选择
					if(listSelect.indexOf(filename)<0){
						listSelect.add(filename);
					}
				}
			}
			else{
				listSelect.remove(filename);
			}
		}
	};
	
	@SuppressWarnings("unchecked")
	public void setSelectAll(){
		Iterator it = mapCheckBox.entrySet().iterator();
		listSelect.clear();
		for(int i=0;i<listItem.size();i++){
			listSelect.add(listItem.get(i).getName());
		}
		while(it.hasNext()){
			Map.Entry map = (Map.Entry)it.next();
			print.out("key==="+map.getKey());
			CheckBox cb =(CheckBox)map.getValue();
			cb.setChecked(true);
		}
	}
	
	
	public void addItem(Photo item){
		listItem.add(0, item);
		notifyDataSetChanged();
	}
	
	public Photo getItemPhoto(int pos){
		return listItem.get(pos);
	}
	
	public void RemoveItem(int pos){
		String name = listItem.get(pos).getName();
		listItem.remove(pos);
		//删除图片
		mapBitmap.remove(name);
		
		notifyDataSetChanged();
		
		System.gc();	//回收删除的bitmap
	}
	
	/*
	public Bitmap resizedBitmap(Bitmap bitmap){
//		Matrix matrix = new Matrix();
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float scale = 1;
		
		int temp=width>height?width:height;
		
		scale = pictureSize/temp;
		
		Bitmap resizedBitmap = Assistant.resizeBitmap(bitmap, width, height, scale);
		if(resizedBitmap!=null){
			bitmap.recycle();
			bitmap=null;
			System.gc();
			return resizedBitmap;
		}else{
			return bitmap;
		}
		
		//
		if(scale !=1){
			try{
				matrix.postScale(scale, scale);
				Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
						height, matrix, true);
				bitmap.recycle();
				return resizedBitmap;
			}catch(Exception e){
				return null;
			}
		}else{
			return bitmap;
		}
		//
	}
	*/
	
	private boolean checkDate(Date date){
		Date now = new Date();
//		print.out("now.getTime()="+now.getTime());
//		print.out("date.getTime()="+date.getTime());
		if(Math.abs(now.getTime() - date.getTime()) > ActivityPhoto.timeLimit * 60000)//60秒 1000毫秒
		{	
			return false;
		}
		return true;
	}
	
	//ActivityPhoto.timeLimit = 60   60*6000 等于一小时
	
	private boolean checkDateH(Date date){
		Date now = new Date();
//		print.out("now.getTime()="+now.getTime());
//		print.out("date.getTime()="+date.getTime());
		if(Math.abs(now.getTime() - date.getTime()) > ActivityPhoto.timeLimit * 60000)//60秒 1000毫秒
		{	
			return false;
		}
		return true;
	}
	

	
//	public void ReLoad(ArrayList<Record> listRecord){
//		this.listRecord = listRecord;
//		notifyDataSetChanged();
//	}
}
