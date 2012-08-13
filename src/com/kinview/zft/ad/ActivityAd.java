package com.kinview.zft.ad;

import java.util.ArrayList;

import com.kinview.config.print;
import com.kinview.util.AdService;
import com.kinview.util.NewCaseService;
import com.kinview.zft.R;
import com.kinview.zft.newcase.ActivityNewCaseByOrganise;
import com.sun.org.apache.bcel.internal.generic.LoadClass;
import com.sun.org.apache.bcel.internal.generic.NEW;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ActivityAd extends Activity
{
	public Button  button,buttonBack;
	private TextView text;
	private EditText editText;
	private Spinner spinner1, spinner2, spinner3;
	public String[][] tempadd;
	public String[][] tempstyle;
	public String[][] temptype;
	public String txt = "";
	public String strdd = "";
	public String strxs = "";
	public String strtype = "";
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.czs_adsearch);
		load();
	}

	private void load()
	{
		// TODO Auto-generated method stub
		editText = (EditText) findViewById(R.id.ad_search_et);
		spinner1 = (Spinner) findViewById(R.id.ad_search_spSetAdd); // 设置地段
		spinner2 = (Spinner) findViewById(R.id.ad_search_spSetAdStyle); // 广告形式
		spinner3 = (Spinner) findViewById(R.id.ad_search_spSetAdType); // 广告类别
		button = (Button) findViewById(R.id.ad_search_btnok); 
		buttonBack = (Button) findViewById(R.id.ad_search_btnBack); 
		
		spinnerload();

		spinner1.setOnItemSelectedListener(l1);
		spinner2.setOnItemSelectedListener(l2);
		spinner3.setOnItemSelectedListener(l3);
		button.setOnClickListener(buttonClick);
		buttonBack.setOnClickListener(buttonBackClick);
	}

	private void spinnerload()
	{
		// TODO Auto-generated method stub
		tempadd = null;
		ArrayList<String> list_Address = new ArrayList<String>();
		AdService ad = new AdService(this);
		tempadd = ad.getAdType("5");
		for (int i = 0; i < tempadd.length; i++)
		{
			list_Address.add(tempadd[i][1]);
		}
		ArrayAdapter<String> adapter_Address = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list_Address);
		adapter_Address
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter_Address);

		tempstyle = null;
		ArrayList<String> list_Style = new ArrayList<String>();
		tempstyle = ad.getAdType("3");
		for (int i = 0; i < tempstyle.length; i++)
		{
			list_Style.add(tempstyle[i][1]);
		}
		ArrayAdapter<String> adapter_Style = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list_Style);
		adapter_Style
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(adapter_Style);

		temptype = null;
		ArrayList<String> list_Type = new ArrayList<String>();
		temptype = ad.getAdType("1");
		for (int i = 0; i < temptype.length; i++)
		{
			list_Type.add(temptype[i][1]);
		}
		ArrayAdapter<String> adapter_smalltype = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list_Type);
		adapter_smalltype
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner3.setAdapter(adapter_smalltype);
	}

	private Spinner.OnItemSelectedListener l1 = new OnItemSelectedListener()
	{
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			if (String.valueOf(spinner1.getSelectedItemId()).equals("0"))
			{
				strdd = "";
			}
			else
			{
//				strdd = spinner1.getSelectedItem().toString();
				strdd = tempadd[spinner1.getSelectedItemPosition()][0];
				
				
			}

		}

		public void onNothingSelected(AdapterView<?> arg0)
		{
			// TODO Auto-generated method stub
		}
	};

	private Spinner.OnItemSelectedListener l2 = new OnItemSelectedListener()
	{
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			if (String.valueOf(spinner2.getSelectedItemId()).equals("0"))
			{
				strxs = "";
			}
			else
			{
//				strxs = spinner2.getSelectedItem().toString();
				strxs = tempstyle[spinner2.getSelectedItemPosition()][0];
			}
		}

		public void onNothingSelected(AdapterView<?> arg0)
		{
			// TODO Auto-generated method stub
		}
	};

	private Spinner.OnItemSelectedListener l3 = new OnItemSelectedListener()
	{
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			if (String.valueOf(spinner3.getSelectedItemId()).equals("0"))
			{
				strtype = "";
			}
			else
			{
//				strtype = spinner3.getSelectedItem().toString();
				strtype = temptype[spinner2.getSelectedItemPosition()][0];
			}
		}

		public void onNothingSelected(AdapterView<?> arg0)
		{
			// TODO Auto-generated method stub
		}
	};
	
	
	OnClickListener buttonClick = new OnClickListener()
	{
		String  sp1Vules = "";
		String  sp2Vules = "";
		String  sp3Vules = "";
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			if(editText.getText().length() == 0)
			{
				showToast("请填写查询条件!");
				return;
			}
			if (!strdd.equals(""))
			{
				sp1Vules = " and region = " + strdd;
			}
			if (!strxs.equals(""))
			{
				sp2Vules = " and form = " + strxs;
			}
			if (!strtype.equals(""))
			{
				sp3Vules = " and type = " + strtype;
			}
			Intent it = new Intent(ActivityAd.this, ActivityAdList.class);
			 Bundle bundle = new Bundle();
			 bundle.putString("test",editText.getText().toString());
		     bundle.putString("adaddress", sp1Vules);
		     bundle.putString("adstyle", sp2Vules);
		     bundle.putString("adtype", sp3Vules);
		     it.putExtras(bundle);
		     startActivity(it);
	
		}
	};
	
	OnClickListener buttonBackClick = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			finish();
		}
	};
	
	public void showToast(String msg)
	{
		
		Toast toast = Toast
				.makeText(ActivityAd.this, msg, Toast.LENGTH_LONG);
		toast.show();
	}
	
}
