package com.kinview.zft.ad;

import java.security.PrivateKey;

import com.kinview.util.AdService;
import com.kinview.util.HistoryService;
import com.kinview.zft.R;
import com.kinview.zft.history.ActivityHistoryList;
import com.kinview.zft.history.ActivityHistorySelectItemView;
import com.sun.org.apache.bcel.internal.generic.LoadClass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityAdList extends Activity
{
	
	private String address = "";
	private String style = "";
	private String type = "";
	private String text = "";
	private ListView listview; // �б�
	private Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent it = this.getIntent();
		address = it.getStringExtra("adaddress");
		style = it.getStringExtra("adstyle");
		type = it.getStringExtra("adtype");
		text = it.getStringExtra("test");
		setContentView(R.layout.czs_adlist);
		listview = (ListView)findViewById(R.id.ad_listview);
		button = (Button)findViewById(R.id.ad_list_back);
		
		showList();
		button.setOnClickListener(buttonListener);
	}
	
	private void showList()
	{
		// TODO Auto-generated method stub
		AdAdapter adapter = new AdAdapter(this,text,address,style,type);
		listview.setAdapter(adapter.getListAdapter());
        listview.setOnItemClickListener(listitemClickListener);
		listview.setSelection(0);
	}
	
	
	ListView.OnItemClickListener listitemClickListener = new ListView.OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			// TODO Auto-generated method stub
			 TextView tv_codeid=(TextView) arg1.findViewById(R.id.czs_ad_listitme_id);
			 Intent iv = new Intent(ActivityAdList.this,ActivityAdView.class);
			 Bundle bundle = new Bundle();
		     bundle.putString("codeid", tv_codeid.getText().toString());
		     iv.putExtras(bundle);
		     startActivity(iv);
		}
	};
	
	OnClickListener buttonListener = new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			listview = null;
			finish();
		}
	};
	
   
}
