package com.kinview.zft.history;

import java.util.ArrayList;
import com.kinview.config.Config;
import com.kinview.config.print;
import com.kinview.util.HistoryMgr;
import com.kinview.util.HistoryService;
import com.kinview.zft.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class ActivityHistoryList extends Activity
{
	ArrayList<String> list_type = new ArrayList<String>();
	int showState = 0;
	private Button btn_back; // ����
	private Spinner sp_type; // ״̬ѡ��˵�
	private ListView listview; // �б�
	private Button btn_clear; // ��ť�����¼
	int sp_state = 0; 
	Context  contextsContext = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try
		{
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			if(Config.cityTag.equals("cztnq"))
			{
				//������
				setContentView(R.layout.tianning_history);
			}
			else if (Config.cityTag.equals("czjts")) 
			{
				//��̳��
				setContentView(R.layout.jintan_history);
			}
			else if (Config.cityTag.equals("czlys")) 
			{
				//������
				setContentView(R.layout.liyang_history);
			}
			else {
				//Ĭ��
				setContentView(R.layout.default_history);
			}
			load();
			// initMenu();
		}
		catch (Exception e)
		{

		}
	}

	private void load()
	{
		print.out("load()����");
		// TODO Auto-generated method stub
		btn_back = (Button) findViewById(R.id.back);
		listview = (ListView) findViewById(R.id.history_listview);
		sp_type = (Spinner) findViewById(R.id.history_spinner1);
		btn_clear = (Button) findViewById(R.id.history_clear);

		btn_back.setOnClickListener(onClickListenerback);
		btn_clear.setOnClickListener(onClickListenerclear);
		sp_type.setOnItemSelectedListener(onItemSelectedListener);

		showSpinner();
		showList();
	
	}

	Button.OnClickListener onClickListenerback = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			finish();
		}
	};

	Button.OnClickListener onClickListenerclear = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			showDialog();
		}
	};

	protected void showDialog()
	{
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("�����ʷ��¼");
		builder.setMessage("��ȷ��Ҫ������е���ʷ��¼��?");
		builder.setPositiveButton("ȷ  ��", onClickListenerOK);
		builder.setNegativeButton("ȡ  ��", onClickListenerCancel);
		builder.show();
	}

	DialogInterface.OnClickListener onClickListenerOK = new DialogInterface.OnClickListener()
	{
        @Override
		public void onClick(DialogInterface dialog, int which)
		{
			// TODO Auto-generated method stub
			clear();
//			finish();
			HistoryService service2 = new HistoryService(contextsContext, sp_state);
//			//jiazai  shu ju
			service2.getItemsForList(HistoryService.list,sp_state);
			listview.setAdapter(service2.getListAdapter());
	        listview.setOnItemClickListener(listitemClickListener);
			listview.setSelection(0);
		}

		
	};

	DialogInterface.OnClickListener onClickListenerCancel = new DialogInterface.OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			// TODO Auto-generated method stub

		}
	};

	ListView.OnItemSelectedListener onItemSelectedListener = new ListView.OnItemSelectedListener()
	{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			
			// TODO Auto-generated method stub
			String s = sp_type.getSelectedItem().toString();
			if (s.equals("ȫ��"))
			{
				sp_state = 0;
			}
			else if (s.equals("�ϱ��ɹ�"))
			{
				sp_state = 1;
			}
			else if (s.equals("�ϱ�ʧ��"))
			{
				sp_state = 2;
			}
			
			ReList(sp_state);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0)
		{
			// TODO Auto-generated method stub

		}

	};

	@SuppressWarnings("unchecked")
	private void showSpinner()
	{
		print.out("showSpinner()");
		list_type.add("ȫ��");
		list_type.add("�ϱ��ɹ�");
		list_type.add("�ϱ�ʧ��");
		ArrayAdapter<String> adapter_smalltype = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list_type);
		adapter_smalltype
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ArrayAdapter<String> oldAdapter = null;
		if (sp_type.getAdapter() != null)
		{
			oldAdapter = (ArrayAdapter<String>) sp_type.getAdapter();
		}
		sp_type.setAdapter(adapter_smalltype);
		if (oldAdapter != null)
		{
			oldAdapter.clear();
			oldAdapter = null;
		}
	}

	

	private void showList()
	{
		try
		{
			print.out("showList()����");
			getShowState();
			HistoryService service = new HistoryService(this,sp_state);
			listview.setAdapter(service.getListAdapter());
	        listview.setOnItemClickListener(listitemClickListener);
			listview.setSelection(0);
		}
		catch (Exception e)
		{
			// MyLog.log("ActivityHistory showList()");
			// MyLog.log(e.getMessage());
		}
	}
	
	private void ReList(int state)
	{
		try
		{
			
			
			print.out("ReList��state="+sp_state);
			HistoryService service1 = new HistoryService(this,state);
			//jiazai  shu ju
			service1.getItemsForList(HistoryService.list,state);
			
			listview.setAdapter(service1.getListAdapter());
	        listview.setOnItemClickListener(listitemClickListener);
			listview.setSelection(0);
		}
		catch (Exception e)
		{
			// MyLog.log("ActivityHistory showList()");
			// MyLog.log(e.getMessage());
		}
	}
	
	
	
	private void getShowState()
	{
		String s = sp_type.getSelectedItem().toString();
		if (s.equals("ȫ��"))
		{
			sp_state = 0;
		}
		else if (s.equals("�ϱ��ɹ�"))
		{
			sp_state = 1;
		}
		else if (s.equals("�ϱ�ʧ��"))
		{
			sp_state = 2;
		}
	}

	ListView.OnItemClickListener listitemClickListener = new ListView.OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			// TODO Auto-generated method stub
			 TextView tv_caseid=(TextView) arg1.findViewById(R.id.caseid);
			 TextView tv_casetype=(TextView) arg1.findViewById(R.id.casetype);
			 Intent iv = new Intent(ActivityHistoryList.this,ActivityHistorySelectItemView.class);
			 Bundle bundle = new Bundle();
		     bundle.putString("caseid", tv_caseid.getText().toString());
		     bundle.putString("casetype", tv_casetype.getText().toString());
		     iv.putExtras(bundle);
		     startActivity(iv);
		}
	};
	
	private void clear()
	{
		// TODO Auto-generated method stub
		HistoryMgr  mgr = new HistoryMgr(this);
		mgr.cleardata();
	}
}
