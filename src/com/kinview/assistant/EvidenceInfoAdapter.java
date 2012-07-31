package com.kinview.assistant;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kinview.config.Config;
import com.kinview.entity.EvidenceInfo;
import com.kinview.util.Case;
import com.kinview.zft.R;

public class EvidenceInfoAdapter extends BaseAdapter {

	private Context context =null;
	private List<EvidenceInfo> list = null;
	private LayoutInflater inflater = null;
	
	public EvidenceInfoAdapter(Context context) {
		this.context = context;
		list = new ArrayList<EvidenceInfo>();
//		list = list;
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
	
	public void addItem(EvidenceInfo item){
		list.add(item);
	}
	
	public void addItemList(List<EvidenceInfo> subList){
		list.addAll(subList);
	}
	
	public void clearList(){
		list.clear();
	}

	public List<EvidenceInfo> getAdapterList(){
		return list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView == null){
			convertView = inflater.inflate(com.kinview.zft.R.layout.form_evidence_registration_info_listview_single, parent, false);
			
			viewHolder = new ViewHolder();
			viewHolder.xuhao = (TextView)convertView.findViewById(R.id.xuhao);
			viewHolder.name = (TextView)convertView.findViewById(R.id.name);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
//		if (list.size() == 0) {
//			viewHolder.xuhao.setText(String.valueOf(position)+": ");
//			viewHolder.name.setText("未添加证据信息");
//			return convertView;
//		}
		EvidenceInfo item = list.get(position);
		
//		Bitmap bitmap = dataStation.getBitmap(product.getProductID());
//		if(bitmap == null){
//			System.out.println(product.getProductID());
//			bitmap = dataStation.getDefaultBitmap();
//			OtherDownloadImageService.addImageRequer(new Product(product.getProductID(),(product.getProductImgPath())));
//		}
		
//		viewHolder.productImg.setImageBitmap(bitmap);
		String xuhao = item.getBianhao();
		viewHolder.xuhao.setText(String.valueOf(position+1)+ ": ");
		String name = item.getName();
		viewHolder.name.setText(name);
		
		return convertView;
	}
	
	static class ViewHolder{
		TextView xuhao = null;
		TextView name = null;
	}

}
