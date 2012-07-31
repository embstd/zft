package com.kinview.zft.casehandle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kinview.assistant.EvidenceInfoAdapter;
import com.kinview.config.Config;
import com.kinview.config.Dialog;
import com.kinview.config.Msg;
import com.kinview.entity.EvidenceInfo;
import com.kinview.entity.NewCase;
import com.kinview.thread.ThreadExit;
import com.kinview.thread.ThreadSubmitBd;
import com.kinview.util.Form;
import com.kinview.zft.R;

public class ActivityFormDetainDeterminInfoListView extends Activity {

	private ListView listView;
	public static List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();;
	
	private TextView reportCase;
	private TextView posPrint;
	private RelativeLayout bottomBarLayout;
	
	private EvidenceInfoAdapter eadapter;
	private static int currentPosition;
	public static int evidenceNum;//证据条数
//	private List<EvidenceInfo> eList = new ArrayList<EvidenceInfo>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.form_evidence_registration_info_listview);
		listView = (ListView) this.findViewById(R.id.form_evidence_registration_info_listview);
		
		
		eadapter = new EvidenceInfoAdapter(this);
		getData();
//		getData();
//		sadapter = new SimpleAdapter(this, listData, R.layout.form_evidence_registration_info_listview_single,
//				new String[]{"id","name"}, new int[]{R.id.xuhao, R.id.name});
		
//		sadapter.setViewBinder(new ViewBinder() {
//			
//			@Override
//			public boolean setViewValue(View view, Object data,
//					String textRepresentation) {
//				if (view instanceof TextView && data instanceof String) {
//					nameView = (TextView) view;
//					nameView.setText(data.toString());
//					return true;
//				} else
//					return false;
//			}
//		});
		listView.setAdapter(eadapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				createView(arg2);
			}
		});
		
		bottomBarLayout = (RelativeLayout)this.findViewById(R.id.form_bottom);
		if (bottomBarLayout == null){
			return;
		}
		reportCase = (TextView)bottomBarLayout.findViewById(R.id.report_case);
		posPrint = (TextView)bottomBarLayout.findViewById(R.id.pos_print);
		reportCase.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormDetainDeterminInfoListView.this, myHandler, Msg.PROMPT_PRINT,
						"提示", "如需打印先打印，上报后将无法打印,是否继续上报？", 0, "");

//				reportCaseProcess();
//				Toast.makeText(ActivityForm.this, "hhhhhh", Toast.LENGTH_SHORT);
			}
		});
		
		if (Config.currentFormList.get(Config.bdPositionId).getState() != null){
			if (!Config.currentFormList.get(Config.bdPositionId).getState().equals("")){
				reportCase.setVisibility(View.GONE);
				setListViewLock();
			}
		}

		posPrint.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				posPrintProcess();
			}
		});
	}
	

	private void setListViewLock() {
		listView.setEnabled(false);
		listView.setFocusable(false);
	}
	private void setEditTextLock(){
		
	}
	//已上报案件 所有信息不可修改
	private void lockEditText(EditText et){
		et.setEnabled(false);
		et.setFocusable(false);
	}
	
	private void getData(){
		Config.listEvidenceInfo.clear();
		for (int i=0; i<6; i++){
			EvidenceInfo vi = new EvidenceInfo();
			vi.setBianhao(String.valueOf(i+1));
			vi.setName("未添加证据信息");
			Config.listEvidenceInfo.add(vi);
		}
		eadapter.addItemList(Config.listEvidenceInfo);
//		if (listData.size() != 0){
//			listData.clear();
//		}
//		for (int i=0; i<6; i++){
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("id", String.valueOf(i+1) + "： ");
//			if (Config.listEvidenceInfo.size() == 0){
//				map.put("name", "证据"+i);//待改正
//			}else {
//				map.put("name", Config.listEvidenceInfo.get(i).getName());//待改正
//			}
////			map.put("name", Config.listEvidenceInfo.get(i).getName());//待改正
//			
//			listData.add(map);
//		}
	}
	
	private void createView(int position){
//		if (!Config.listEvidenceInfo.get(position).getName().equals("")){//填过的不可以修改
//			return ;
//		}
		if (position >evidenceNum){
			return;
		}
		Intent it = null;
		//表单id 要为特定值
//		int bd_id = Config.currentFormList.get(position).getId();
		currentPosition = position;
		it = new Intent(ActivityFormDetainDeterminInfoListView.this, ActivityFormDetainDeterminInfo.class);
		Bundle b = new Bundle();
		b.putInt("position", position);
//		b.putInt("caseId", Config.caseId);
		it.putExtras(b);
		startActivityForResult(it, 0);
	}
	
	private void showInfoList(){
		
	}
	
	private void refresh(){
//		getData();
		
//		Config.listEvidenceInfo.get(currentPosition).setName(Config.listEvidenceInfo.get(currentPosition).getName());
		eadapter.notifyDataSetChanged();
		evidenceNum++;
	}
	
	private Handler myHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			Intent it = null;
			Bundle b = null;
			switch (msg.what){
			case Msg.GETFORMLIST:
				showInfoList();
				break;
			case Msg.LISTVIEW_REFRESH:
//				refresh();
				break;
			case Msg.ERROR_SERVER_CONNECT:
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormDetainDeterminInfoListView.this,
						null, Msg.PROGRAM_EXIT, "连接", "服务器连接失败,请稍后重试", 0, "");
				break;
			case Msg.PROGRAM_EXIT:
				ThreadExit thread = new ThreadExit(ActivityFormDetainDeterminInfoListView.this);
				thread.showProcess();
				break;
			case Msg.TITLE_PROGRESS_START:
				setTitleProgress(true);
				break;
			case Msg.TITLE_PROGRESS_STOP:
				setTitleProgress(false);
				break;
			case Msg.ONBACK:
				it = new Intent(ActivityFormDetainDeterminInfoListView.this,ActivityFormList.class);
				b = new Bundle ();
				b.putInt("type",Msg.REQUEST_REPEAT);
				it.putExtras(b);
				if (getParent() == null){
					setResult(0, it);
				}else {
					getParent().setResult(0, it);
				}
				finish();
				break;
			case Msg.REQUEST_SUCCESS:
				Dialog.showDialog(Dialog.OK, ActivityFormDetainDeterminInfoListView.this,
				myHandler, Msg.ACTIVITY_EXIT, "提示", "上报表单成功", 0, "");
				break;
			case Msg.REQUEST_ERROR:
				Dialog.showDialog(Dialog.OK, ActivityFormDetainDeterminInfoListView.this,
						null, 0, "提示", "上报表单错误", 0, "");
				break;
			case Msg.ACTIVITY_EXIT:
				it = new Intent(ActivityFormDetainDeterminInfoListView.this,ActivityFormList.class);
				b = new Bundle ();
				b.putInt("type",Msg.SUBMIT_BD_SUCCESS);
				it.putExtras(b);
				if (getParent() == null){
					setResult(0, it);
				}else {
					getParent().setResult(0, it);
				}
//				setResult(0, it);
				finish();
				break;
			case Msg.PROMPT_PRINT:
				reportCaseProcess();
				break;
			}
		}
		
	};
	
	@Override
	protected void onPause() {
		super.onPause();
//		Config.otherTabBdContent = getFormContentWhenPause();
		Config.otherTabBdContentSubmit = getFormContentWhenPause();
	}
	
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
	
	private String getFormContent(){
		NewCase tempCase = Config.currentNewCaseList.get(0);
		Form tempform = Config.currentFormList.get(Config.bdPositionId);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		int type = 0;//0:组织 1： 个人
		if (tempCase.getOrganise() == null || tempCase.getOrganise().equals("")){
			if (tempCase.getPerson() != null){
				type = 1;
			}
		}else {
			type = 0;
		}
		StringBuffer content = new StringBuffer();
		content.append(Config.otherTabBdContent);
		
		content.append("_");
		content.append("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" + String.valueOf(year)).append(" 年 ");
		content.append(String.valueOf(month)).append(" 月 ");
		content.append(String.valueOf(day)).append(" 日");
//		content.append(tv_form_title_all_4.getText().toString().trim()).append("\r\n");
//		content.append(tempform.getFormName()).append("_");
//		content.append("常天城执登存字[").append(String.valueOf(year)).append("]  \r\nNo.")
//		.append(tempCase.getLsh()).append("_");
//		content.append(" _");
//		
//		if (type == 0){
//			content.append(et_evidence_registration_info_1.getText().toString().trim())
//			.append(et_evidence_registration_info_1.getText().toString().trim()).append("\r\n")
//			.append(et_evidence_registration_info_1.getText().toString().trim())
//			.append(et_evidence_registration_info_1.getText().toString().trim()).append("\r\n")
//			.append(et_evidence_registration_info_1.getText().toString().trim())
//			.append(et_evidence_registration_info_1.getText().toString().trim()).append("\r\n");
//		}else {
//			content.append(et_evidence_registration_info_1.getText().toString().trim())
//			.append(et_evidence_registration_info_1.getText().toString().trim()).append("\r\n")
//			.append(et_evidence_registration_info_1.getText().toString().trim())
//			.append(et_evidence_registration_info_1.getText().toString().trim()).append("\r\n")
//			.append(et_evidence_registration_info_1.getText().toString().trim())
//			.append(et_evidence_registration_info_1.getText().toString().trim()).append("\r\n");
//		}
//		content.append("现场勘察示意图:\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");//15行
//		String[] otherStr = Config.otherTabBdContent.split("_");
//		for (int i =0; i<otherStr.length; i++){
//			content.append(otherStr[i]);
//			if (i%2 != 0){
//				content.append("\r\n");
//			}
//		}
//		
//		
//		content.append(tv_form_title_all_4.getText().toString().trim()).append("_")
//		.append(tv_form_title_4.getText().toString().trim()).append("_")
//		.append(tv_evidence_registration_info_1.getText().toString().trim()).append("_")
//		.append(et_evidence_registration_info_1.getText().toString().trim()).append("_")
//		.append(tv_evidence_registration_info_2.getText().toString().trim()).append("_")
//		.append(et_evidence_registration_info_2.getText().toString().trim()).append("_")
//		.append(tv_evidence_registration_info_3.getText().toString().trim()).append("_")
//		.append(et_evidence_registration_info_3.getText().toString().trim()).append("_")
//		.append(tv_evidence_registration_info_4.getText().toString().trim()).append("_")
//		.append(et_evidence_registration_info_4.getText().toString().trim()).append("_")
//		.append(tv_evidence_registration_info_5.getText().toString().trim()).append("_")
//		.append(et_evidence_registration_info_5.getText().toString().trim()).append("_")
//		.append(tv_evidence_registration_info_6.getText().toString().trim()).append("_")
//		.append(et_evidence_registration_info_6.getText().toString().trim());
		return content.toString();
////		return Html.fromHtml(content.toString()).toString();
	}
//	
	private String getFormContentWhenPause(){
		StringBuffer content = new StringBuffer();
		for (int i=0; i<Config.listEvidenceInfo.size(); i++){
			content.append(Config.listEvidenceInfo.get(i).getName()).append("_")
			.append(Config.listEvidenceInfo.get(i).getGuige()).append("_")
			.append(Config.listEvidenceInfo.get(i).getDanwei()).append("_")
			.append(Config.listEvidenceInfo.get(i).getShuliang()).append("_")
			.append(Config.listEvidenceInfo.get(i).getBeizhu()).append("_");
		}
//		content
//		.append(tv_evidence_registration_info_1.getText().toString().trim()).append("_")
//		.append(et_evidence_registration_info_1.getText().toString().trim()).append("_")
//		.append(tv_evidence_registration_info_2.getText().toString().trim()).append("_")
//		.append(et_evidence_registration_info_2.getText().toString().trim()).append("_")
//		.append(tv_evidence_registration_info_3.getText().toString().trim()).append("_")
//		.append(et_evidence_registration_info_3.getText().toString().trim()).append("_")
//		.append(tv_evidence_registration_info_4.getText().toString().trim()).append("_")
//		.append(et_evidence_registration_info_4.getText().toString().trim()).append("_")
//		.append(tv_evidence_registration_info_5.getText().toString().trim()).append("_")
//		.append(et_evidence_registration_info_5.getText().toString().trim()).append("_")
//		.append(tv_evidence_registration_info_6.getText().toString().trim()).append("_")
//		.append(et_evidence_registration_info_6.getText().toString().trim());
		
		return content.toString();
//		return Html.fromHtml(content.toString()).toString();
	}
	
	private String getFormContent2(){
		NewCase tempCase = Config.currentNewCaseList.get(0);
		Form tempform = Config.currentFormList.get(Config.bdPositionId);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		int type = 0;//0:组织 1： 个人
		if (tempCase.getOrganise() == null || tempCase.getOrganise().equals("")){
			if (tempCase.getPerson() != null){
				type = 1;
			}
		}else {
			type = 0;
		}
		StringBuffer content = new StringBuffer();
		//待改
		if (!Config.otherTabBdContentSubmit.equals("")){
			content.append(Config.otherTabBdContentSubmit);//这个要解析 按照 最终的表单样子
		}
		content.append(getFormContentWhenPause());
		
		if (type == 0){
//			content
//			.append(et_evidence_registration_info_1.getText().toString().trim()).append("_")
//			.append(et_evidence_registration_info_2.getText().toString().trim()).append("_")
//			.append(et_evidence_registration_info_3.getText().toString().trim()).append("_")
//			.append(et_evidence_registration_info_4.getText().toString().trim()).append("_")
//			.append(et_evidence_registration_info_5.getText().toString().trim()).append("_");
//		}else {
//			content
//			.append(et_evidence_registration_info_1.getText().toString().trim()).append("_")
//			.append(et_evidence_registration_info_2.getText().toString().trim()).append("_")
//			.append(et_evidence_registration_info_3.getText().toString().trim()).append("_")
//			.append(et_evidence_registration_info_4.getText().toString().trim()).append("_")
//			.append(et_evidence_registration_info_5.getText().toString().trim()).append("_");
		}
//		content.append("现场勘察示意图:\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");//15行
//		String[] otherStr = Config.otherTabBdContent.split("_");
//		for (int i =0; i<otherStr.length; i++){
//			if (i%2 != 0){
//				content.append(otherStr[i]);
//				content.append("_");
//			}
//		}
		return content.toString();
//		return Html.fromHtml(content.toString()).toString();
	}
	
	private void reportCaseProcess(){
		if (checkInputLegal()){
			int aj_id =0;
			String bd_id = "";
			String bd_name = "";
			String param = "";
			if (Config.currentFormList.size() != 0){
				aj_id = Integer.valueOf(Config.currentFormList.get(0).getCaseId());
				bd_id = String.valueOf(Config.currentFormList.get(Config.bdPositionId).getId());//注意
				bd_name = Config.currentFormList.get(Config.bdPositionId).getFormName();
				param = getFormContent2();
			}
			
			Log.i("ZFTSP", "otherTabBdContent: " + Config.otherTabBdContent + "aj_id: " + aj_id +" position: " + Config.bdPositionId + " bd_id: " + bd_id + " bd_name: " + bd_name + " param: " + param);
				
			if (Config.threadSubmitBd == null){
				Config.threadSubmitBd = new ThreadSubmitBd();
				Config.threadSubmitBd.showProcess(this, myHandler, aj_id, bd_id, bd_name, param);
			}
		}
	}
	
	private void posPrintProcess() {
		if (checkInputLegal()) {
			Intent it  = new Intent(ActivityFormDetainDeterminInfoListView.this, ActivityPosPrint.class);
			Bundle bundle = new Bundle();
			bundle.putInt("position", Config.bdPositionId);
			bundle.putString("content", getFormContent());
			it.putExtras(bundle);
			startActivityForResult(it, Msg.FORM_REQUEST_SUCCESS);
		}
	}
	
	
	
	private boolean checkInputLegal(){
//		if (et_evidence_registration_info_1.getText().toString().trim().equals("")){
//			Dialog.showDialog(Dialog.OK, this, null, 0,
//					"提示", "不能为空!", 0, "");
//			return false;
//		}else {
//			return true;
//		}
		return true;
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
//				case Msg.REQUEST_SUCCESS:
//					refresh();
//					break;
				case Msg.REQUEST_REPEAT:
//					refresh();
//					Config.currentFormList.clear();
//					formListAdapter.clearList();
//					if (threadGetForm != null){
//						threadGetForm = null;
//					}
//					if (threadGetForm == null){
//						threadGetForm = new ThreadGetForm();
//						threadGetForm.showProcess(this, myHandler, 0, Config.caseId);
//					}
					break;
				case Msg.OPERATE_INFO_SUCCESS:
					refresh();
					break;
					
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
