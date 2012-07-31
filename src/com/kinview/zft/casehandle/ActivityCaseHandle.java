package com.kinview.zft.casehandle;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.kinview.assistant.CaseHandleAdapter;
import com.kinview.config.Config;
import com.kinview.config.Dialog;
import com.kinview.config.Msg;
import com.kinview.thread.ThreadExit;
import com.kinview.thread.ThreadGetCase;
import com.kinview.util.Case;
import com.kinview.zft.R;

public class ActivityCaseHandle extends Activity {

	private ListView casehandleList;
	public  ThreadGetCase threadGetCase;
//	public static List<Case> currentCaseList;
//	public static List<Case> currentCaseList;
	private String[] from = {"organizationName","litigant","date"};
	private int[] to = {R.id.organizationName,R.id.litigant,R.id.date};
	public SimpleAdapter caseHandleSimpleAdapter;
	public CaseHandleAdapter caseHandleAdapter;
	
	//分页
	private boolean footFlag = false;//是否是footview标志
	private int lastItem = 0;				
	private int totalPage = 0;
	private int pageNum = 0;
	private final static int PAGESIZE = 30;
	private LinearLayout footViewLayout = null;
	
	public static final String TAG="ZFT";
	
	public String timePeriod ="";//时间段
	
	public static final int  ITEM0 = 1;
	public static final int  ITEM1 = 2;
	public static final int  ITEM2 = 3;
	public static final int  ITEM3 = 4;
	public static final int  ITEM4 = 5;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.casehandle);
//		setTitle("案件办理");
		if (caseHandleAdapter == null){
//			caseHandleSimpleAdapter = new SimpleAdapter(this, getData(), R.layout.casehandlelist, from, to);
			caseHandleAdapter = new CaseHandleAdapter(this);
		}

		casehandleList = (ListView) this.findViewById(R.id.caseList);

		footViewLayout = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.list_page_load, null);
		casehandleList.addFooterView(footViewLayout);
		
		if (!footFlag && totalPage <= 1){
//			casehandleList.removeFooterView(footViewLayout);
//			footFlag = true;
		}
		
//		showCaseList();//测试
//		nextPage();
		//默认时间段为当周
		timePeriod = getTimePeriod(1);
		//启动读取案件的线程
		if (threadGetCase == null){
			threadGetCase = new ThreadGetCase();
			threadGetCase.showProcess(this, myHandler, 0, timePeriod);
		}
	}
	
	private String getTimePeriod(int periodType){
//		Calendar cal = Calendar.getInstance();
//		int year = cal.get(Calendar.YEAR);// 年
//		int month = cal.get(Calendar.MONTH) + 1;// 月
//		int week = cal.get(Calendar.WEEK_OF_MONTH);//周
//		int day = cal.get(Calendar.DAY_OF_MONTH);// 日
//		int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);//24小时制
//		int minute = cal.get(Calendar.MINUTE);// 分
//		int second = cal.get(Calendar.SECOND);// 秒
		
		String range = "";
		switch (periodType){
		case 1:
			range = "and datediff(week,c.createtime,getdate())=0";
			break;
		case 2:
			range = "and datediff(month,c.createtime,getdate())=0";
			break;
		case 3:
			range = "and datediff(qq,c.createtime,getdate())=0";
			break;
		case 4:
			range = "and datediff(year,c.createtime,getdate())=0";
			break;
		case 5:
			range = "";
			break;
		}
		return range;
	}
	
	
	
	private void getCaseList(){
//    	currentCaseList = new ArrayList<Case>();
//    	for(int i = 0;i < 120;i++){
//    		Case item = new Case();
//    		item.setOrganise("teset");
//    		item.setPerson("dddd");
//    		item.setCreatetime("20:30");
//    		currentCaseList.add(item);
//    	}
    	pageNum = 1;
    	int size = Config.currentCaseList.size();
    	if(size % PAGESIZE == 0){
    		totalPage = size / PAGESIZE;
    	}else{
    		totalPage = size / PAGESIZE + 1;
    	}
    	
    	Log.i(TAG, "size:" + String.valueOf(size) + "totalPage:" + totalPage);
    }
	
	//获取当前案件 对应的有哪些手机文书
	private void createView(int position){
		//应该是启动一个线程去读数据
		Intent it = new Intent(ActivityCaseHandle.this,ActivityFormList.class);
		Bundle b = new Bundle();
//		Config.casePositionId = position;
		b.putInt("position", position);
		b.putInt("caseId", Config.currentCaseList.get(position).getId());
		it.putExtras(b);
		startActivityForResult(it, 0);
//		finish();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try{
			Bundle b = data.getExtras();
			if(b!=null){
				int type = b.getInt("type");
				
				switch(type){
				case Msg.REQUEST_SUCCESS:
//					refresh();
					break;
				case Msg.REQUEST_REPEAT:
					refresh();
					Config.currentCaseList.clear();
					caseHandleAdapter.clearList();
					if (threadGetCase != null){
						threadGetCase = null;
					}
					if (threadGetCase == null){
						threadGetCase = new ThreadGetCase();
						threadGetCase.showProcess(this, myHandler, 0, timePeriod);
					}
					break;
				}
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*
	 * 开启或关闭进度条
	 */
	public void setTitleProgress(boolean flag){
		setProgressBarIndeterminateVisibility(flag);
	}

	private Handler myHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
			case Msg.GETCASELIST:
				showCaseList();
				nextPage();
				break;
//				case Msg.LISTVIEW_SHOW:
//				showCaseList();
//				break;
			case Msg.LISTVIEW_REFRESH:
				refresh();
				break;
			case Msg.ERROR_SERVER_CONNECT:
				Dialog.showDialog(Dialog.OKCANCEL, ActivityCaseHandle.this,
						myHandler, Msg.PROGRAM_EXIT, "连接", "服务器连接失败,请稍后重试", 0, "");
				break;
			case Msg.PROGRAM_EXIT:
//				ThreadExit thread = new ThreadExit(ActivityCaseHandle.this);
//				thread.showProcess();
				
				ActivityCaseHandle.this.onBackPressed();
				ActivityCaseHandle.this.finish();
				break;
			case Msg.TITLE_PROGRESS_START:
				setTitleProgress(true);
				break;
			case Msg.TITLE_PROGRESS_STOP:
				setTitleProgress(false);
				break;
				
			}
			
		}
		
	};
	
	private void refresh(){
//		print.out("refresh is call");
		if(caseHandleAdapter != null){
			caseHandleAdapter.notifyDataSetChanged();
		}
	}
	
	
	
	@Override
	protected void onResume() {
		refresh();
		super.onResume();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// do something on back.
//			Config.currentCaseList.clear();
//			caseHandleAdapter.clearList();
//			if (threadGetCase != null){
//				threadGetCase = null;
//			}
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showCaseList() {
		//测试
		getCaseList();//获得案件
		
		casehandleList.setAdapter(caseHandleAdapter);
		
		casehandleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				createView(arg2);
			}
			
		});
		
		casehandleList.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (lastItem == caseHandleAdapter.getCount() && scrollState == SCROLL_STATE_IDLE){
					nextPage();
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lastItem = firstVisibleItem + visibleItemCount - 1;
			}
		});
	}
	
	private void nextPage(){
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Case> list = new ArrayList<Case>();
		
		if (Config.currentCaseList.size() == 0){//没有数据
			casehandleList.removeFooterView(footViewLayout);
			footFlag = true;
			Toast.makeText(this, "暂时没有数据", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if (!footFlag && pageNum == totalPage){
			casehandleList.removeFooterView(footViewLayout);
			footFlag = true;
		}
		if (pageNum > totalPage){
			return;
		}
		
		if(pageNum == totalPage){
    		list = Config.currentCaseList.subList((pageNum - 1) * PAGESIZE, Config.currentCaseList.size());
    	}else{
    		list = Config.currentCaseList.subList((pageNum - 1) * PAGESIZE, pageNum * PAGESIZE);
    	}
		
		caseHandleAdapter.addItemList(list);
		caseHandleAdapter.notifyDataSetChanged();
//		pageNum++;
    	Toast.makeText(this, "第" + pageNum++ + "页，总共" + totalPage + "页", Toast.LENGTH_SHORT).show();
//    	taskController.setList(list);
//    	taskController.execute();
    	casehandleList.setSelection(lastItem);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, ITEM0, 0, "本周");
		menu.add(0, ITEM1, 0, "本月");
		menu.add(0, ITEM2, 0, "本季");
		menu.add(0, ITEM3, 0, "本年");
		menu.add(0, ITEM4, 0, "所有");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String timePeriodStr="";
		switch (item.getItemId()){
		case ITEM0:
			timePeriodStr = getTimePeriod(ITEM0);
			break;
		case ITEM1:
			timePeriodStr = getTimePeriod(ITEM1);
			break;
		case ITEM2:
			timePeriodStr = getTimePeriod(ITEM2);
			break;
		case ITEM3:
			timePeriodStr = getTimePeriod(ITEM3);
			break;
		case ITEM4:
			timePeriodStr = getTimePeriod(ITEM4);
			break;
		}
		if (threadGetCase == null){
			Config.currentCaseList.clear();
			caseHandleAdapter.clearList();
			threadGetCase = new ThreadGetCase();
			threadGetCase.showProcess(this, myHandler, 0, timePeriodStr);
		}else {
			showToast("请稍等一会再查询");
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	@Override
	protected void onDestroy() {
		Config.currentCaseList.clear();
		caseHandleAdapter.clearList();
		if (threadGetCase != null){
			threadGetCase = null;
		}
		super.onDestroy();
	}

	private void showToast(String content){
		Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
	}

}
