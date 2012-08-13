package com.kinview.zft.ztc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kinview.config.print;
import com.kinview.util.AdService;
import com.kinview.util.ZtcService;
import com.kinview.zft.R;

import android.app.Activity;
import android.content.Context;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class ZtcAdapter extends Activity
{
	Context context = null;
	private String number = "";
	private String cut = "";
	private String master = "";
	private String driver = "";
	private String state = "";
	
	SimpleAdapter adapter = null;
	List<Map<String, Object>> list;
	
	public ZtcAdapter(Context context1, String number1,String cut1, String master1, String driver1, String state1)
	{
		// TODO Auto-generated constructor stub
		this.context = context1;
		this.number = number1;
		this.cut = cut1;
		this.master = master1;
		this.driver= driver1; 
		this.state= state1; 
	}
	
	public ListAdapter getListAdapter()
	{

		adapter = null;
	    adapter = new SimpleAdapter(context,getItemsForList(list),R.layout.czs_ztciteminlist,
				new String[]{"ztcnumber","ztcmaster","ztcdriver","ztcid"},
				new int[]{R.id.czs_ztc_listitme_number,R.id.czs_ztc_listitme_master,R.id.czs_ztc_listitme_driver,R.id.czs_ztc_listitme_id});
	    																	
		return adapter;
	}
	
	public List<Map<String, Object>> getItemsForList( List<Map<String, Object>>list )
	{
		String[][] temp = null;
		if(list==null){
			list = new ArrayList<Map<String, Object>>();
		}else{
			list.clear();
		}
		Map<String, Object> map = null;
		ZtcService service = new ZtcService(context);
		temp = service.getSearch(number, cut, master, driver, state);
		if (temp == null)
		{
			print.out("temp == null");
		}
		for (int i = 0; i < temp.length; i++)
		{
			for (int j = 0; j < temp[0].length; j++)
			{
				print.out("temp[" + i + "]" + "[" + j + "]"
						+ temp[i][j].toString());
                map = new HashMap<String, Object>();
				if (temp[i][0].length()>0)
				{
					map.put("ztcid", temp[i][0].toString());
				}
				if (temp[i][1].length()>0)
				{
					map.put("ztcnumber",temp[i][1].toString());					
				}
				else if (temp[i][2].length()>0)
				{
					map.put("ztcmaster",temp[i][3].toString());					
				}
				else if (temp[i][3].length()>0)
				{
					map.put("ztcdriver",temp[i][4].toString());					
				}	
			}
			list.add(map);
		}	
		
		print.out("getItemsForList返回List列表行数="+ list.size());
		return list;
	}
	

}
