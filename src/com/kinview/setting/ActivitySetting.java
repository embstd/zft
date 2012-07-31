package com.kinview.setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kinview.zft.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class ActivitySetting extends Activity implements OnItemClickListener
{

	private Button button1;
	private ListView listview;

	protected void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		// setTitle("设置");
		init();
		showList();
	}

	private void init()
	{
		button1 = (Button) findViewById(R.id.setting_button1);
		button1.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				finish();
			}
		});
		listview = (ListView) findViewById(R.id.setting_listview1);
	}

	public void showList()
	{
		SimpleAdapter adapter = new SimpleAdapter(this, getMenu(),
				android.R.layout.simple_expandable_list_item_2, new String[]
				{ "menu", "info" }, new int[]
				{ android.R.id.text1, android.R.id.text2 });
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(this);
		listview.setSelection(0);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long id)
	{
		Intent it = null;
		switch (position)
		{
		case 0:
			it = new Intent(this, ActivitySystemSettingList.class);
			startActivity(it);
			break;
		case 1:
			it = new Intent(this, ActivitySettingPassword.class);
			startActivity(it);
			break;
		case 2:
			// it = new Intent(this, ActivitySync.class);
			// startActivity(it);
			break;
		}
	}

	public List<Map<String, Object>> getMenu()
	{
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		HashMap<String, Object> item;

		item = new HashMap<String, Object>();
		item.put("menu", "系统设置");
		item.put("info", "配置系统参数");
		data.add(item);

		item = new HashMap<String, Object>();
		item.put("menu", "密码修改");
		item.put("info", "管理用户账户安全");
		data.add(item);

		// item = new HashMap<String, Object>();
		// item.put("menu", "数据同步");
		// item.put("info", "同步列表和地图数据");
		// data.add(item);

		return data;
	}

}
