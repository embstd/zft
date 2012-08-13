package com.kinview.zft.ztc;

import java.text.SimpleDateFormat;

import com.kinview.config.print;
import com.kinview.entity.Ad;
import com.kinview.entity.ZtcList;
import com.kinview.util.AdService;
import com.kinview.util.ZtcService;
import com.kinview.zft.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ActivityZtcView extends Activity
{
	String idString  = "";
	private TextView  number,cut,master,driver,install,isdefault,fdjh;
	
	private Button buttonOK;
	String[][] Temp = null;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent it = this.getIntent();
		idString = it.getStringExtra("id");
		setContentView(R.layout.czs_ztcview);
		print.out("idString ="+ idString);
		load();
	}
	
	
	private void load()
	{
		// TODO Auto-generated method stub
		ZtcList ztc = new ZtcList();
		number = (TextView) findViewById(R.id.czs_ztc_tv_number);
		cut = (TextView) findViewById(R.id.czs_ztc_tv_cut);
		master = (TextView) findViewById(R.id.czs_ztc_tv_master);
		driver = (TextView) findViewById(R.id.czs_ztc_tv_driver);
		install = (TextView) findViewById(R.id.czs_ztc_tv_install);
		isdefault = (TextView) findViewById(R.id.czs_ztc_tv_isdefault);
		fdjh = (TextView) findViewById(R.id.czs_ztc_tv_fdjh);
		
        buttonOK = (Button)findViewById(R.id.czs_ztc_btnBack);
        buttonOK.setOnClickListener(btnClick);
        
		ZtcService  ztcService = new ZtcService();
		ztc = ztcService.getZtcInformation(idString);

		
		
		number.setText("车牌号："+ ztc.getNumber().toString());
		cut.setText("发动机号："+ ztc.getCut().toString());
		master.setText("车主姓名："+ ztc.getMaster().toString());
		driver.setText("驾驶员姓名："+ ztc.getDriver().toString());
		install.setText("GPS导航："+ ztc.getInstall().toString() );
		isdefault.setText("业务状态："+ ztc.getIsdefault().toString());
		fdjh.setText("有无违章："+ ztc.getFdjh().toString());
		
		
	}
	
	
	OnClickListener btnClick = new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			finish();
		}
	};
	
	
}
