package com.kinview.assistant;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kinview.util.Case;

public class CaseHandleAdapter extends BaseAdapter {

	private Context context =null;
	private List<Case> list = null;
	private LayoutInflater inflater = null;
	
	public CaseHandleAdapter(Context context) {
		this.context = context;
		list = new ArrayList<Case>();
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public void addItem(Case item){
		list.add(item);
	}
	
	public void addItemList(List<Case> subList){
		list.addAll(subList);
	}
	
	public void clearList(){
		list.clear();
	}

	public List<Case> getAdapterList(){
		return list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView == null){
			convertView = inflater.inflate(com.kinview.zft.R.layout.casehandlelist, parent, false);
			
			viewHolder = new ViewHolder();
			viewHolder.organizationName = (TextView)convertView.findViewById(com.kinview.zft.R.id.organizationName);
			viewHolder.litigant = (TextView)convertView.findViewById(com.kinview.zft.R.id.litigant);
			viewHolder.createtime = (TextView)convertView.findViewById(com.kinview.zft.R.id.date);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Case item = list.get(position);
		
//		Bitmap bitmap = dataStation.getBitmap(product.getProductID());
//		if(bitmap == null){
//			System.out.println(product.getProductID());
//			bitmap = dataStation.getDefaultBitmap();
//			OtherDownloadImageService.addImageRequer(new Product(product.getProductID(),(product.getProductImgPath())));
//		}
//		
//		viewHolder.productImg.setImageBitmap(bitmap);
		String organizationName = item.getOrganise();
		viewHolder.organizationName.setText(organizationName);
		String litigant = item.getPerson();
		viewHolder.litigant.setText(litigant);
		String createtime = item.getCreatetime();
		viewHolder.createtime.setText(createtime);
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView organizationName = null;
		TextView litigant = null;
		TextView createtime = null;
	}

}
