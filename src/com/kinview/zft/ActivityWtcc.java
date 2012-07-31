package com.kinview.zft;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ActivityWtcc extends Activity {

	private Spinner spinner1 = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wtcc);
		
		init();
	}
	
	private void init(){
		spinner1  = (Spinner)findViewById(R.id.Spinner1);
		ArrayList<String> list_smalltype = new ArrayList<String>();
		list_smalltype.add("ÊÐÈÝ»·¾³");
		ArrayAdapter<String> adapter_smalltype = new ArrayAdapter<String>(this,
		        android.R.layout.simple_spinner_item, list_smalltype);
		adapter_smalltype.setDropDownViewResource
         (android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter_smalltype);
		
		
	}
}
