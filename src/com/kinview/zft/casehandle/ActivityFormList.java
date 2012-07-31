package com.kinview.zft.casehandle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.kinview.assistant.FormListAdapter;
import com.kinview.config.Config;
import com.kinview.config.Dialog;
import com.kinview.config.Msg;
import com.kinview.thread.ThreadExit;
import com.kinview.thread.ThreadGetCase;
import com.kinview.thread.ThreadGetForm;
import com.kinview.util.Form;
import com.kinview.zft.R;
import com.kinview.zft.R.id;
import com.kinview.zft.R.layout;

public class ActivityFormList extends Activity {

	private ListView formList;
	private ThreadGetForm threadGetForm;
	private List<Form> listForm;
	private LinearLayout footViewLayout = null;
	private FormListAdapter formListAdapter;
	private boolean footFlag = false;
	
	private int pageNum = 0;
	private int totalPage = 0;
	private static final int PAGESIZE=30;
	private int lastItem = 0;//总共的最后一项序号
	
//	private int caseId;
	private int position;
	
	private List<Form> formDataList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form);
//		setTitle("表单处理");
		formListAdapter = new FormListAdapter(this);
		formList = (ListView) this.findViewById(R.id.formList);
		footViewLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.list_page_load, null);
		formList.addFooterView(footViewLayout);
		
//		showFormList();
//		nextPage();
		
		Intent it = this.getIntent();
		position = it.getIntExtra("position", 0);
//		caseId = it.getIntExtra("caseId", 0);
		Config.caseId = it.getIntExtra("caseId", 0);
		//启动读取表单列表的线程
		if (threadGetForm == null){
			threadGetForm = new ThreadGetForm();
			threadGetForm.showProcess(this, myHandler, 0, Config.caseId);
		}
	}
	
//	private int position ;//当前表单id
	//获取当前手机文书的具体文书
	private void createView(int position){
		//如果表单状态发生变化后（上报成功后）就不可以再修改了
		if (Config.currentFormList.size() != 0) {
			if (Config.currentFormList.get(position) != null) {
				if (Config.currentFormList.get(position).getState() != null) {
					if (!Config.currentFormList.get(position).getState()
							.equals("")) {
//						Dialog.showDialog(Dialog.OK, ActivityFormList.this,
//								null, 0, "提示", "该表单已上报，无需操作!", 0, "");
						this.position = position;
						Config.bdPositionId = 1;
						Dialog.showDialog(Dialog.OKCANCEL, ActivityFormList.this,
								myHandler, Msg.SEND_SONGDA_HUIZHENG, "提示", "该表单已上报，是否发送送达回证？", 0, "");
						return;
					}

				}
			}
		}
		
		Intent it = null;
		//表单id 要为特定值
		int bd_id = Config.currentFormList.get(position).getId();
		switch (bd_id){
		case 96://限期提供合法手续通知书
			it = new Intent(ActivityFormList.this, ActivityFormProvide.class);
			break;
		case 98://现场检查笔录
			it = new Intent(ActivityFormList.this, ActivityFormSiteRecord.class);
			break;
		case 100://核查通知书
			it = new Intent(ActivityFormList.this, ActivityFormVerification.class);
			break;
		case 101://证据先行登记保存通知书
			it = new Intent(ActivityFormList.this, ActivityFormEvidence.class);
			break;
		case 108://责令改正通知书
			it = new Intent(ActivityFormList.this, ActivityFormOrderCorrect.class);
			break;
		case 97://责令停止建设通知书
			it = new Intent(ActivityFormList.this, ActivityFormOrderStop.class);
			break;
		case 104://停止违法(章)行为通知书(手机)
			it = new Intent(ActivityFormList.this, ActivityFormStopIllegal.class);
			break;
		case 111://行政处罚事先告知书(手机)
			it = new Intent(ActivityFormList.this, ActivityFormPublishBeforNotice.class);
			break;
		case 109://行政处罚听证告知书(手机)
			it = new Intent(ActivityFormList.this, ActivityFormPublishHearingNotice.class);
			break;
		case 99://解除停止建设通知书(手机)
			it = new Intent(ActivityFormList.this, ActivityFormRemoveOrderStop.class);
			break;
		case 102://调查询问笔录(手机)
			it = new Intent(ActivityFormList.this, ActivitySurveyAskRecord.class);
			break;
		case 103://证人证言笔录(手机)
			it = new Intent(ActivityFormList.this, ActivityFormWitnessRecored.class);
			break;
		case 106://当场处罚决定(手机)
			it = new Intent(ActivityFormList.this, ActivityFormSitePublishDetermin.class);
			break;
		case 107://行政处罚决定(手机)
			it = new Intent(ActivityFormList.this, ActivityFormPublishDetermin.class);
			break;
		case 184://证据先行登记保存决定书(手机)(目前和 通知书 一样， 在 程序中判断 （金坛的）)
			it = new Intent(ActivityFormList.this, ActivityFormEvidence.class);
			break;
		case 185://证据先行登记保存现场笔录(手机)
			it = new Intent(ActivityFormList.this, ActivityFormEvidenceRegSiteRecord.class);
			break;
		case 183://扣押决定书(手机)
			it = new Intent(ActivityFormList.this, ActivityFormDetainDetermin.class);
			break;
		case 186://扣押现场笔录(手机)
			it = new Intent(ActivityFormList.this, ActivityFormDetainSiteRecord.class);
			break;
		}
//		switch (position){
//		case 0:
//			it = new Intent(ActivityFormList.this, ActivityFormProvide.class);
//			break;
//		case 1:
//			it = new Intent(ActivityFormList.this, ActivityFormSiteRecord.class);
//			break;
//		case 2:
//			it = new Intent(ActivityFormList.this, ActivityFormVerification.class);
//			break;
//		case 3:
//			it = new Intent(ActivityFormList.this, ActivityFormEvidence.class);
//			break;
//		case 4:
//			it = new Intent(ActivityFormList.this, ActivityFormOrderCorrect.class);
//			break;
//		}
		if (it == null){
			return;
		}
		
		Bundle b = new Bundle();
		b.putInt("position", position);
//		b.putInt("caseId", Config.caseId);
		Config.bdPositionId = position;
		it.putExtras(b);
		startActivityForResult(it, 0);
	}
	
	private Handler myHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			Intent it = null;
			Bundle b = null;
			switch (msg.what){
			case Msg.GETFORMLIST:
				showFormList();
				nextPage();
				break;
			case Msg.GET_FORMLIST_NO_PERMISSION:
				Dialog.showDialog(Dialog.OK, ActivityFormList.this,
						myHandler, Msg.PROGRAM_EXIT, "提示", "该用户无读取手机表单的权限", 0, "");
				break;
			case Msg.LISTVIEW_REFRESH:
//				refresh();
				break;
			case Msg.ERROR_SERVER_CONNECT:
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormList.this,
						myHandler, Msg.PROGRAM_EXIT, "连接", "服务器连接失败,请稍后重试", 0, "");
				break;
			case Msg.PROGRAM_EXIT:
//				ThreadExit thread = new ThreadExit(ActivityFormList.this);
//				thread.showProcess();
				ActivityFormList.this.onBackPressed();
				ActivityFormList.this.finish();
				break;
			case Msg.TITLE_PROGRESS_START:
				setTitleProgress(true);
				break;
			case Msg.TITLE_PROGRESS_STOP:
				setTitleProgress(false);
				break;
			case Msg.ONBACK:
				it = new Intent(ActivityFormList.this,ActivityCaseHandle.class);
				b = new Bundle ();
				b.putInt("type",Msg.REQUEST_REPEAT);
				it.putExtras(b);
				setResult(0, it);
				finish();
				break;
			case Msg.SEND_SONGDA_HUIZHENG:
				Intent it1 = new Intent(ActivityFormList.this,ActivitySongdaHuizheng.class);
				Bundle b1 = new Bundle ();
				b1.putInt("position",position);
				it1.putExtras(b1);
				startActivity(it1);
				break;
			}
			
		}
		
	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// do something on back.
			myHandler.sendEmptyMessage(Msg.ONBACK);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/*
	 * 开启或关闭进度条
	 */
	public void setTitleProgress(boolean flag){
		setProgressBarIndeterminateVisibility(flag);
	}
	
	private void getFormListData(){
		//测试
//		formDataList = new ArrayList<Form>();
//		for (int i=0; i< 5; i++){
//			Form form = new Form();
//			form.setFormName(getFormName(i));
//			form.setState("state"+i);
//			Config.currentFormList.add(form);
//		}
		//测试结束
		pageNum = 1;
		int size = Config.currentFormList.size();
		Log.i("ZFT", String.valueOf(size));
		if (size % PAGESIZE == 0){
			totalPage = size/PAGESIZE;
		}else {
			totalPage = size/PAGESIZE +1;
		}
	}
	
	//测试
	private String getFormName(int index){
		switch (index){
		case 0:
			return "限期提供合法手续通知书(手机)";
		case 1:
			return "现场检查笔录(手机)";
		case 2:
			return "停工(核查)通知书(手机)";
		case 3:
			return "证据先行登记保存通知书(手机)";
		case 4:
			return "责令改正通知书(手机)";
		}
		return "";
	}
	
	private void showFormList(){
		getFormListData();
//		SimpleAdapter sa = new SimpleAdapter(this, getData(), R.layout.formlistlayout,
//				new String[]{"organizationName","litigant","date"}, new int[]{R.id.organizationName,R.id.litigant,R.id.date});
		
		formList.setAdapter(formListAdapter);
		
		formList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					createView(arg2);
				}
			
		});
		formList.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (lastItem == formListAdapter.getCount() && scrollState == SCROLL_STATE_IDLE){
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
		List<Form> list = new ArrayList<Form>();
		if (!footFlag && pageNum == totalPage){
			formList.removeFooterView(footViewLayout);
			footFlag = true;
		}
		
		if (pageNum > totalPage){
			return;
		}
		if (pageNum == totalPage){
			list = Config.currentFormList.subList((pageNum-1) * PAGESIZE, Config.currentFormList.size());
		}else {
			list = Config.currentFormList.subList((pageNum-1) * PAGESIZE, pageNum * PAGESIZE);
		}
		formListAdapter.addItemList(list);
		formListAdapter.notifyDataSetChanged();
		formList.setSelection(lastItem);
		Toast.makeText(this, "第" +pageNum++ +"页/共" +totalPage +"页", Toast.LENGTH_SHORT).show();
		
	}
	
	//上报表单成功后显示 刷新
	private void refresh() {
		Config.currentFormList.get(Config.bdPositionId).setState("已上报");
		formListAdapter.notifyDataSetChanged();
	}
	
	@Override
	protected void onDestroy() {
		Config.currentFormList.clear();
		formListAdapter.clearList();
		if (threadGetForm != null){
			threadGetForm = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//如果上报成功刷新列表
		
		try{
			Bundle b = data.getExtras();
			if(b!=null){
				int type = b.getInt("type");
				
				switch(type){
				case Msg.REQUEST_SUCCESS:
//					refresh();
					break;
				case Msg.REQUEST_REPEAT:
//					refresh();
					Config.otherTabBdContent = "";
					Config.otherTabBdContentSubmit = "";
					Config.currentFormList.clear();
					formListAdapter.clearList();
					if (threadGetForm != null){
						threadGetForm = null;
					}
					if (threadGetForm == null){
						threadGetForm = new ThreadGetForm();
						threadGetForm.showProcess(this, myHandler, 0, Config.caseId);
					}
					break;
				case Msg.SUBMIT_BD_SUCCESS:
					refresh();
					break;
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

}
