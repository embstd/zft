package com.kinview.zft.ad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kinview.config.Config;
import com.kinview.config.print;
import com.kinview.util.AdService;
import com.kinview.util.HistoryMgr;
import com.kinview.zft.R;

import android.content.Context;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class AdAdapter
{
	Context context = null;
	String address = "";
	String style = "";
	String type = "";
	String text = "";
	
	SimpleAdapter adapter = null;
	List<Map<String, Object>> list;
	
	public AdAdapter(Context context1,String text1,String address1,String style1,String type1)
	{
		this.context = context1;
		this.address = address1;
		this.style = style1;
		this.type = type1;
		this.text= text1; 
	}
	
	public ListAdapter getListAdapter()
	{

		adapter = null;
	    adapter = new SimpleAdapter(context,getItemsForList(list),R.layout.czs_iteminlist,
				new String[]{"adcode","adname","adtell"},
				new int[]{R.id.czs_ad_listitme_id,R.id.czs_ad_listitme_name,R.id.czs_ad_listitme_tell});
	    																	
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
		AdService service = new AdService(context);
		temp = service.getSearch(text,address, style, type);
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
					map.put("adcode", temp[i][0].toString());
				}
				if (temp[i][1].length()>0)
				{
					map.put("adname",temp[i][1].toString());					
				}
				else if (temp[i][2].length()>0)
				{
					map.put("adtell",temp[i][2].toString());					
				}				
			}
			list.add(map);
		}	
		return list;
	}
	
}
