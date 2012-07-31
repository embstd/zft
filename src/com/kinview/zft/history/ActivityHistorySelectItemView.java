package com.kinview.zft.history;

import com.kinview.config.print;
import com.kinview.entity.NewCase;
import com.kinview.util.HistoryMgr;
import com.kinview.util.HistoryService;
import com.kinview.zft.R;
import com.kinview.zft.newcase.ActivityNewCaseByOrganise;
import com.sun.org.apache.bcel.internal.generic.LoadClass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityHistorySelectItemView extends Activity
{
	String caseid;
	String casetype;
	int t = 0;                 
	private Button btn_OpenPic,btn_OpenPicp,btn_BackO,btn_BackP;
	private EditText nco_etName, nco_etFzr, nco_etZw, nco_etTell,
	nco_etAddress, nco_etOpenAdd, nco_etOpenTime, nco_etintro,nco_etAnyou;
	private NewCase HistoryCase = null;
	private EditText ncp_etName, ncp_etSex, ncp_etAge, ncp_etTell,
	ncp_etIDnumber, ncp_etAddress, ncp_etOpenAdd, ncp_etOpenTime,
	ncp_etintro,ncp_etAnyou;
	

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub  1组织  2个人
		super.onCreate(savedInstanceState);
		Intent it = this.getIntent();
		caseid = it.getStringExtra("caseid");
		casetype = it.getStringExtra("casetype");
		if(casetype.equals("1"))
		{
			
			setContentView(R.layout.historybyorganise);
			
			t = 1;
		} else if (casetype.equals("2")) {
			setContentView(R.layout.historybyperson);
			t = 2;
		}
		
		Load(t);
	}
	
	
	
		
	    private Button.OnClickListener l = new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				finish();
			}
		};
		
		  


	private void Load(int ist)
	{
		HistoryMgr mgr = new HistoryMgr(this);
		String anyouString = "";
		// TODO Auto-generated method stub 1组织  2个人
		if(ist==1)
		{
			HistoryCase = mgr.getCaseInfoByOrganise(caseid);
			nco_etName = (EditText) findViewById(R.id.nco_etName);
			nco_etFzr = (EditText) findViewById(R.id.nco_etFzr);
			nco_etZw = (EditText) findViewById(R.id.nco_etZw);
			nco_etTell = (EditText) findViewById(R.id.nco_etTell);
			nco_etAddress = (EditText) findViewById(R.id.nco_etAddress);
			nco_etOpenAdd = (EditText) findViewById(R.id.nco_etOpenAdd);
			nco_etOpenTime = (EditText) findViewById(R.id.nco_etOpenTime);
			nco_etintro = (EditText) findViewById(R.id.nco_etintro);
			nco_etAnyou = (EditText) findViewById(R.id.nco_etAnyou);
			btn_OpenPic = (Button) findViewById(R.id.nco_btnPic);
			btn_BackO = (Button) findViewById(R.id.nco_btnBack);
			
			print.out("getType="+String.valueOf(HistoryCase.getType()));
			anyouString =  mgr.getAnyou(String.valueOf(HistoryCase.getType()));
			print.out("anyouString="+anyouString);
			nco_etAnyou.setText(anyouString);
			nco_etName.setText(HistoryCase.getOrganise());
			nco_etFzr.setText(HistoryCase.getFzr());
			nco_etZw.setText(HistoryCase.getZw());
			nco_etTell.setText(HistoryCase.getOtel());
			nco_etAddress.setText(HistoryCase.getOadd());
			nco_etOpenAdd.setText(HistoryCase.getAddress());
			nco_etOpenTime.setText(HistoryCase.getThetime());
			nco_etintro.setText(HistoryCase.getIntro());
			btn_OpenPic.setText("查看上报的照片("+HistoryCase.getPicNumString()+")");
			btn_OpenPic.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					if(HistoryCase.getPicNum() == 0)
					{
						showToast("该案件未上传照片信息!");
					}
					else if(HistoryCase.getPicNum() > 0)
					{
						//caseid
					}
				}
			});
			btn_BackO.setOnClickListener(l);
		}
		else if (ist == 2) {
			
			HistoryCase = mgr.getCaseInfoByPerson(caseid);
			ncp_etName = (EditText) findViewById(R.id.ncp_etName);
			ncp_etAge = (EditText) findViewById(R.id.ncp_etAge);
		    ncp_etTell = (EditText) findViewById(R.id.ncp_etTell);
			ncp_etIDnumber = (EditText) findViewById(R.id.ncp_etIDnumber);
			ncp_etAddress = (EditText) findViewById(R.id.ncp_etAddress);
		    ncp_etOpenAdd = (EditText) findViewById(R.id.ncp_etOpenAdd);
		    ncp_etOpenTime = (EditText) findViewById(R.id.ncp_etOpenTime);
		    ncp_etintro = (EditText) findViewById(R.id.ncp_etintro);
	        ncp_etSex = (EditText) findViewById(R.id.ncp_etSex);
	        btn_OpenPicp = (Button)findViewById(R.id.ncp_btnPic);
			btn_BackP = (Button) findViewById(R.id.ncp_btnBack);
			ncp_etAnyou = (EditText) findViewById(R.id.ncp_etAnyou);
			
			print.out("getType="+String.valueOf(HistoryCase.getType()));
			anyouString =  mgr.getAnyou(String.valueOf(HistoryCase.getType()));
			print.out("anyouString="+anyouString);
			ncp_etAnyou.setText(anyouString);
	        ncp_etName.setText(HistoryCase.getPerson());
			ncp_etAge.setText(HistoryCase.getAge());
			ncp_etIDnumber.setText(HistoryCase.getIdnumber());
			ncp_etAddress.setText(HistoryCase.getPadd());
			ncp_etTell.setText(HistoryCase.getPtel());
			ncp_etOpenAdd.setText(HistoryCase.getAddress());
			ncp_etOpenTime.setText(HistoryCase.getThetime());
			ncp_etintro.setText(HistoryCase.getIntro());
			ncp_etSex.setText(HistoryCase.getSex());
			btn_OpenPicp.setText("查看上报的照片("+HistoryCase.getPicNumString()+")");
			btn_OpenPicp.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					// TODO Auto-generated method stub
					if(HistoryCase.getPicNum() == 0)
					{
						showToast("该案件未上传照片信息!");
					}
					else if(HistoryCase.getPicNum() > 0)
					{
						//caseid
						
					}
				}
			});
			btn_BackP.setOnClickListener(l);
			
		}
	}
	
	public void showToast(String msg)
	{
		
		Toast toast = Toast
				.makeText(ActivityHistorySelectItemView.this, msg, Toast.LENGTH_LONG);
		toast.show();
	}
	
}
