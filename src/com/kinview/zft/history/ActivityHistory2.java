package com.kinview.zft.history;



import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.kinview.util.HistoryService;
import com.kinview.zft.R;
import com.kinview.config.Config;
import com.kinview.config.Msg;
import com.kinview.config.print;

public class ActivityHistory2 extends Activity implements OnItemClickListener{
	
	private Spinner sp_type;
	private ListView listview;
	private Button btn_clear;
	ArrayList<String> list_type = new ArrayList<String>();
	private ArrayList<String> dialogMenu_error = new ArrayList<String>();
	private ArrayList<String> dialogMenu_success = new ArrayList<String>();
	private int menu_Index=0;	//1表示选择的是dialogMenu_error菜单,2表示选择了dialogMenu_success菜单;
//	HistoryMgr historyMgr = null;
	private int selectPosition = -1;
	int showState=0;	//显示全部,失败,成功
//	private HistoryAdapter  adapter = null;	//任务适配器
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try{
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.history);
			Config.handlerHistory = this.mHandler;
//			historyMgr = new HistoryMgr(this);
			init();
			initMenu();
		}catch(Exception e){
//			MyLog.logDate();
//			MyLog.log("ActivityHistory2 onCreate():");
//			MyLog.log(e.getMessage());
		}
		
	}

	private void init() {
		listview = (ListView)findViewById(R.id.history_listview);
		sp_type = (Spinner)findViewById(R.id.history_spinner1);
		btn_clear = (Button)findViewById(R.id.history_clear);
		
		sp_type.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> arg0, View v,
					int pos, long arg3) {
				refreshShowState();
//				print.out("choose pos="+pos);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
		});
		
		Button btn_back = (Button)findViewById(R.id.back);
		btn_back.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Config.handlerHistory = null;
				finish();
			}
		});
		
		btn_clear.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				showDialog();
			}
		});
		
		showSpinner();
		showList();
		
	}
	
	@SuppressWarnings("unchecked")
	private void showSpinner(){
		list_type.add("全部");
		list_type.add("上报成功");
		list_type.add("上报失败");
		list_type.add("正在上报");
		ArrayAdapter<String> adapter_smalltype = new ArrayAdapter<String>(this,
		        android.R.layout.simple_spinner_item, list_type);
		adapter_smalltype.setDropDownViewResource
         (android.R.layout.simple_spinner_dropdown_item);
		ArrayAdapter<String> oldAdapter =null;
		if(sp_type.getAdapter()!=null){
			oldAdapter = (ArrayAdapter<String>)sp_type.getAdapter();
		}
		sp_type.setAdapter(adapter_smalltype);
		if(oldAdapter!=null){
			oldAdapter.clear();
			oldAdapter= null;
		}
	}
	
	private void getShowState(){
		String s = sp_type.getSelectedItem().toString();
		if(s.equals("全部")){
			showState=0;
		}else if(s.equals("上报成功")){
			showState=1;
		}else if(s.equals("上报失败")){
			showState=4;
		}else if(s.equals("正在上报")){
			showState=3;
		}
	}
	
	private void showList() {
//		try{
//			getShowState();
//		   HistoryService service   = new HistoryService(this,showState);
//		   SimpleAdapter adapter = new SimpleAdapter(this, service.getItemsForList(showState), 
//				   R.layout.history_list, new String[]{"xu", "id","date" ,"color","eventDesc"} , 
//				   new int[] { R.id.history_list_xu,R.id.history_list_id,
//							R.id.history_list_date,
//							R.id.history_list_color,R.id.history_list_eventDesc});
//		   listview.setAdapter(adapter);
//		   listview.setSelection(0);
////			adapter = new HistoryAdapter(this,showState);
////			
////			listview.setOnItemClickListener(this);
////			
//		}catch (Exception e){
////			MyLog.log("ActivityHistory showList()");
////			MyLog.log(e.getMessage());
//		}
	}
	
	private void refreshShowState(){
		try{
			getShowState();
//			if(adapter!=null){
//				adapter.setShowState(showState);
//			}
		}catch (Exception e){
//			MyLog.log("ActivityHistory refreshShowState()");
//			MyLog.log(e.getMessage());
		}
//		MySimpleAdapter adapter = new MySimpleAdapter(this, historyMgr.getItemsForList(showState),
//				R.layout.history_list, new String[] {
//						"xu", "id","date" ,"color","eventDesc"}
//		, new int[] { R.id.history_list_xu,R.id.history_list_id,
//			R.id.history_list_date,
//			R.id.history_list_color,R.id.history_list_eventDesc});
//		listview.setAdapter(adapter);
	}
	
	private void refreshData(){
		try{
//			if(adapter!=null){
////				adapter.initData();
////				adapter.notifyDataSetChanged();
//			}
		}catch (Exception e){
//			MyLog.logDate();
//			MyLog.log("ActivityHistory refreshData()");
//			MyLog.log(e.getMessage());
		}
	}
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Msg.REFREASH:
				//刷新
				print.out("HISTORY_REFREASH is do!");
				//数据发生变化
				refreshData();
				break;
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// do something on back.
//			DIALOG.showDialog(this,"1");
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long id) {
//		print.out("onItemClick is call");
		selectPosition = position;
//		int state = Config.listHistory.get(position).getState();
//		if(state/10!=1 || state %10!=1 || state/10 !=3 || state%10!=3|| state/10 !=5 ){
//		if(state/10==4 || state%10==4){
//			Dialog dialog = onCreateDialog(1);
//			dialog.show();
//		}else if(state/10==1 && state%10==1){
//			Dialog dialog = onCreateDialog(2);
//			dialog.show();
//		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		refreshData();
		print.out("history onResume is call");
		super.onResume();
	}
	
	
	 private void initMenu(){
	    	dialogMenu_error.add("删除任务");
	    	dialogMenu_error.add("重新上报");
	    	dialogMenu_error.add("查看任务");
	    	
	    	
	    	dialogMenu_success.add("删除任务");
	    	dialogMenu_success.add("查看任务");
//	    	if(type){
//	    		dialogMenu.add("添加到附件");
//	    	}
	    	
	    }
	
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		//id=1未正常上报,id=2已经正常上报
		try{
	    	switch(id){
	    	case 1:
	    		//上报失败菜单
	    		menu_Index=1;
	    		int size = dialogMenu_error.size();
	    		String[] str = new String[size]; 
	    		for(int i=0 ;i<size;i++){
	    			str[i] = (String)dialogMenu_error.get(i);
	    		}
	    		return new AlertDialog.Builder(this)
	            .setTitle("菜单") // 设置标题
	            .setIcon(R.drawable.record_dialogtitle) // 设置标题图片
	            .setItems(str, new DialogInterface.OnClickListener() { // 弹框所显示的内容来自一个数组。数组中的数据会一行一行地依次排列
	              public void onClick(DialogInterface dialog, int position) {
	//                ActivityMain.this.setTitle("点击了项目"+ which);
//	            	  History history = Config.listHistory.get(selectPosition);
//	            	  if(history.getState()/10 !=4 || history.getState()%10!=4){
//	            		  ActivityHistory2.this.menuAction(position,history);
//	            	  }
	              }
	            })
	            .create();
	    	case 2:
	    		//上报成功菜单
	    		menu_Index=2;
	    		int size2 = dialogMenu_success.size();
	    		String[] str2 = new String[size2]; 
	    		for(int i=0 ;i<size2;i++){
	    			str2[i] = (String)dialogMenu_success.get(i);
	    		}
	    		return new AlertDialog.Builder(this)
	            .setTitle("菜单") // 设置标题
	            .setIcon(R.drawable.record_dialogtitle) // 设置标题图片
	            .setItems(str2, new DialogInterface.OnClickListener() { // 弹框所显示的内容来自一个数组。数组中的数据会一行一行地依次排列
	              public void onClick(DialogInterface dialog, int position) {
//                ActivityMain.this.setTitle("点击了项目"+ which);
//	            	  History history = Config.listHistory.get(selectPosition);
//	            	  if(history.getState()/10 !=4 || history.getState()%10!=4){
//	            		  ActivityHistory2.this.menuAction(position,history);
//	            	  }
	              }
	            })
	            .create();
			default:
				return null;
	    	}
		}catch(Exception e){
//			MyLog.logDate();
//			MyLog.log("ActivityHistory2 menuAction():");
//			MyLog.log(e.getMessage());
			return null;
		}
	    	
	}
    
//    public void menuAction(int position ,History history){
    	 public void menuAction(int position ){
    	try{
	    	switch(position){
	    	//删除
	    	case 0:
	    		//删除的是否为成功任务
//	    		if(history.getState()==11){
//	    			如果是成功任务,直接删除
//	    			historyMgr.delete(history);
//		    		Config.listHistory.remove(history);
		    		//清楚任务记录里的隐藏任务
		    		ArrayList<String> taskIDs = new ArrayList<String>();
//		    		taskIDs.add(history.getTaskNum().split(",")[0]);
//		    		TaskMgr taskMgr = new TaskMgr(ActivityHistory2.this);
//					taskMgr.clearSuccessMission(taskIDs);
//	    		}else{
		    		//恢复任务
//	    			print.out("恢复任务被调用!");
//		    		Task task = new Task();
//		    		task.setCardID(Config.username);
//		    		task.setTaskID(history.getTaskNum());
//		    		task.setState(0);	//设置为初始状态
//		    		TaskMgr taskMgr  = new TaskMgr(this);
//		    		taskMgr.chanageState(task);
		    		//发送消息,不用发送消息,因为onresume会自动刷新
		    		
//		    		historyMgr.delete(history);
//		    		if(adapter!=null){
//		    			adapter.removeItem(history);
//		    		}
	//	    		Config.listHistory.remove(history);
//	    		}
	    		refreshData();
	    		break;
	    	case 1:
	    		if(menu_Index==1){
	    			//重新上报
//		    		int state = history.getState();
//		    		if(state/10==4){
//		    			state = 50 + state%10;	//修改为重新上报
//		    		}
//		    		if(state%10==4){
//		    			state = state/10 *10 + 5;	//改为重新上报
//		    		}
//		    		history.setState(state);
//		    		historyMgr.chanageState(history);
//		    		//打开上报开关
//		    		if(Config.threadUploadReport!=null){
//		    			Config.threadUploadReport.newReport = true;
//		    		}
		    		refreshData();
	    		}else if(menu_Index==2){
	    			//查看任务
//	    			Intent it = new Intent(this,ActivityHistoryView.class);
//	        		Bundle b = new Bundle();
//	        		b.putSerializable("history", history);
//	        		it.putExtras(b);
//	        		startActivity(it);
	    		}
	    		break;
	    	case 2:
	    		//查看任务
//	    		Intent it = new Intent(this,ActivityHistoryView.class);
//	    		Bundle b = new Bundle();
//	    		b.putSerializable("history", history);
//	    		it.putExtras(b);
//	    		startActivity(it);
	    		break;
	    	}
    	}catch(Exception e){
//			MyLog.logDate();
//			MyLog.log("ActivityHistory2 menuAction():");
//			MyLog.log(e.getMessage());
		}
    }

    private void showDialog() {
		try{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
	//		builder.setIcon(R.drawable.alert_dialog_icon);
			builder.setTitle("清除历史记录");
			builder.setMessage("您确定要清除上传成功的任务吗?");
			builder.setPositiveButton("确  定",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							ArrayList<String> taskIDs = new ArrayList<String>();
//							for(int i=0;i<Config.listHistory.size();i++){
//								History item = Config.listHistory.get(i);
//								if(item.getState()==11){
//									taskIDs.add(item.getTaskNum().split(",")[0]);
//								}
//							}
//							historyMgr.clearSuccessMission();
//							TaskMgr taskMgr = new TaskMgr(ActivityHistory2.this);
//							taskMgr.clearSuccessMission(taskIDs);
//							historyMgr.load();
//							if(adapter!=null){
//								adapter.initData();
//							}
//							refreshData();
//							if(Config.listHistory.size()==0){
//								//如果记录为0,那么清除未删除的隐藏任务
//								taskMgr.clearHideMission();
//							}
						}
					});
			builder.setNegativeButton("取  消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
						}
					});
			builder.show();
	    }catch(Exception e){
//			MyLog.logDate();
//			MyLog.log("ActivityHistory2 onCreate():");
//			MyLog.log(e.getMessage());
		}
	}
}