package com.kinview.camera;

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

public class PhotoAdapter4Download extends BaseAdapter {
	public ArrayList<Photo> listItem =null;
	Context context =null;
	private ArrayList<String> listSelect = new ArrayList<String>();
	private HashMap<String,CheckBox> mapCheckBox = new HashMap<String,CheckBox>();
	public static HashMap<String,Bitmap> mapBitmap = new HashMap<String,Bitmap>(); 
	private float pictureSize=ActivityPhoto.pictureSize;	//Õº∆¨¥Û–°100œÒÀÿ
	private String path="";
	
	public ArrayList<String> getListSelect() {
		return listSelect;
	}
	
	public PhotoAdapter4Download(Context context,String path,ArrayList<Photo> listItem){
		this.path= path;
		this.listItem = listItem;
		this.context = context;
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
		tv_date.setText("¥¥Ω® ±º‰:\n"+sdate);
//		tv_length.setText(" ±≥§:"+term.getLength());
		
		cb_select.setOnCheckedChangeListener(new MyOnCheckedChangeListener(term.getName()));
//		listCheckBox.add(cb_select);
		mapCheckBox.put(""+position, cb_select);
		if(listSelect.indexOf(term.getName())>=0){
			cb_select.setChecked(true);
		}
		
		//…Ë÷√‘§¿¿Õº∆¨
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
//		//º”‘ÿ‘§¿¿Õº∆¨
//		FileInputStream fStream=null;
//		try {
//			fStream = new FileInputStream(path+name);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		Bitmap bitmap = BitmapFactory.decodeStream(fStream);
//		bitmap = resizedBitmap(bitmap);
//		return bitmap;
		return Assistant.getResizeBitmapByFileName(path+name, pictureSize);
	}
	

	class MyOnCheckedChangeListener implements OnCheckedChangeListener{
		private String filename;
		public MyOnCheckedChangeListener(String filename){
			this.filename = filename;
		}
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
			if(isChecked){
				if(listSelect.indexOf(filename)<0){
					listSelect.add(filename);
				}
			}else{
				listSelect.remove(filename);
			}
		}
	};
	
	@SuppressWarnings("unchecked")
	public void setSelectAll(){
		Iterator it = mapCheckBox.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry map = (Map.Entry)it.next();
			CheckBox cb =(CheckBox)map.getValue();
			cb.setChecked(true);
		}
		listSelect.clear();
		for(int i=0;i<listItem.size();i++){
			listSelect.add(listItem.get(i).getName());
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
		//…æ≥˝Õº∆¨
		mapBitmap.remove(name);
		notifyDataSetChanged();
	}
	
//	public Bitmap resizedBitmap(Bitmap bitmap){
//		try{
//			Matrix matrix = new Matrix();
//			int width = bitmap.getWidth();
//			int height = bitmap.getHeight();
//			float scale = 1;
//			int temp=width>height?width:height;
//			scale = pictureSize/temp;
//			if(scale !=1){
//				
//					matrix.postScale(scale, scale);
//					Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
//							height, matrix, true);
//					bitmap.recycle();
//					return resizedBitmap;
//			}else{
//				return bitmap;
//			}
//		}catch(Exception e){
//			return null;
//		}
//	}
	
//	public void ReLoad(ArrayList<Record> listRecord){
//		this.listRecord = listRecord;
//		notifyDataSetChanged();
//	}
}
