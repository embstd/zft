package com.kinview.assistant;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kinview.util.Form;
import com.kinview.zft.R;

public class FormListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<Form> formList= null;
	public FormListAdapter(Context context){
		this.inflater = LayoutInflater.from(context);
		formList = new ArrayList<Form>();
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return formList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return formList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public void addItem(Form form){
		formList.add(form);
	}
	
	public void addItemList(List<Form> sublist){
		formList.addAll(sublist);
	}
	
	public void clearList(){
		formList.clear();
	}
	
	public Form getForm(int position){
		return formList.get(position);
	}
	
	public List<Form> getFormList(){
		return formList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null){
			convertView = inflater.inflate(R.layout.formlistlayout, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.state = (TextView)convertView.findViewById(R.id.state);
			viewHolder.formName = (TextView)convertView.findViewById(R.id.formName);
			convertView.setTag(viewHolder);
		}else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Form form = formList.get(position);
		if (!form.getBd_module_id().equals("")){
			form.setState("“—…œ±®");
		}
		viewHolder.state.setText(form.getState());
		viewHolder.formName.setText(form.getFormName());
		return convertView;
	}
	
	static class ViewHolder{
		TextView state = null;
		TextView formName = null;
	}

}
