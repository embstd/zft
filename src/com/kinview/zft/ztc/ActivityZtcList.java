package com.kinview.zft.ztc;

import com.kinview.zft.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class ActivityZtcList extends Activity
{
	private String Number = "";
	private String Cut = "";
	private String Master = "";
	private String Driver = "";
	private String State = "";
	private ListView listview; // ап╠М
	private Button buttonBack;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.czs_ztclist);
		Intent it = this.getIntent();
		Number = it.getStringExtra("Number");
		Cut = it.getStringExtra("Cut");
		Master = it.getStringExtra("Master");
		Driver = it.getStringExtra("Driver");
		State = it.getStringExtra("State");
		
		listview = (ListView)findViewById(R.id.ztc_listview);
		buttonBack = (Button)findViewById(R.id.ztc_lists_back);
		buttonBack.setOnClickListener(buttonListener);
		showList();
		
	}
	
	
	private void showList()
	{
		// TODO Auto-generated method stub
		ZtcAdapter adapter = new ZtcAdapter(this,Number,Cut,Master,Driver,State);
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
			 TextView tv_ztcid=(TextView) arg1.findViewById(R.id.czs_ztc_listitme_id);
			 Intent iv = new Intent(ActivityZtcList.this,ActivityZtcView.class);
			 Bundle bundle = new Bundle();
		     bundle.putString("id", tv_ztcid.getText().toString());
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
