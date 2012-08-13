package com.kinview.zft.ztc;

import java.util.ArrayList;

import com.kinview.config.print;
import com.kinview.util.AdService;
import com.kinview.util.ZtcService;
import com.kinview.zft.R;
import com.kinview.zft.ad.ActivityAd;
import com.kinview.zft.ad.ActivityAdList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ActivityZtc extends Activity
{
	private Button btnok,btnback;
	private Spinner spinner;
	private EditText carnumber_et,cutnumber_et,mastername_et,drivername_et;
	String temp[][] = null;
	ZtcService ztcService = new ZtcService(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.czs_ztcsearch);
		load();
	}

	private void load()
	{
		// TODO Auto-generated method stub
		btnok = (Button)findViewById(R.id.zfc_search_btnOk);
		btnback = (Button)findViewById(R.id.ztc_search_btnBacks);
		spinner = (Spinner)findViewById(R.id.ztc_isdefault_sp);
		carnumber_et= (EditText)findViewById(R.id.ztc_carnumber_et);
		cutnumber_et= (EditText)findViewById(R.id.ztc_cutnumber_et);
		mastername_et= (EditText)findViewById(R.id.ztc_mastername_et);
		drivername_et= (EditText)findViewById(R.id.ztc_drivername_et);
		btnback.setOnClickListener(btnbackClick);
		btnok.setOnClickListener(btnokClick);
		
		
		ArrayList<String> defauttype = new ArrayList<String>();
		String[] ss = new String[]{"未办理","办理中","未审核","审核中","已审核","已退订"};
		for (int is = 0; is < ss.length; is++)
		{
			defauttype.add(ss[is]);
		}
		ArrayAdapter<String> adapter_casetype = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, defauttype);
		adapter_casetype
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter_casetype);
	}
	
	OnClickListener btnbackClick = new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			finish();
		}
	};
	
	OnClickListener btnokClick = new OnClickListener()
	{
		
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			String Number = "";
			String Cut = "";
			String Master = "";
			String Driver = "";
            if (carnumber_et.getText().length()>0)
            {
                 Number = carnumber_et.getText().toString();
            }
            else
            {
                 Number = "";
            }
            if (cutnumber_et.getText().length()>0)
            {
            	Cut = cutnumber_et.getText().toString();
            }
            else
            {
            	Cut = "";
            }
            if (mastername_et.getText().length()>0)
            {
                 Master = mastername_et.getText().toString();
            }
            else
            {
                 Master = "";
            }
            if (drivername_et.getText().length()>0)
            {
                 Driver = drivername_et.getText().toString();
            }
            else
            {
                 Driver = "";
            }
            String State = String.valueOf(spinner.getSelectedItemId());
            print.out("渣土车选择项目所选的ID="+State);
            Intent it = new Intent(ActivityZtc.this, ActivityZtcList.class);
			Bundle bundle = new Bundle();
			bundle.putString("Number",Number);
			bundle.putString("Cut",Cut);
			bundle.putString("Master",Master);
			bundle.putString("Driver",Driver);
			bundle.putString("State",State);
		    it.putExtras(bundle);
		    startActivity(it);
        }
	};

}
