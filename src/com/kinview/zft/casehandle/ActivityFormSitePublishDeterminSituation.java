package com.kinview.zft.casehandle;

import java.util.Calendar;

import com.kinview.config.Config;
import com.kinview.config.Dialog;
import com.kinview.config.Msg;
import com.kinview.entity.NewCase;
import com.kinview.thread.ThreadExit;
import com.kinview.thread.ThreadGetCaseInfo;
import com.kinview.thread.ThreadSubmitBd;
import com.kinview.util.Form;
import com.kinview.zft.R;

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
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivityFormSitePublishDeterminSituation extends Activity {
	private LayoutInflater inflater;
	private RelativeLayout currentFormLayout;
//	public int position = -1;
	private TextView reportCase;
	private TextView posPrint;
	private RelativeLayout bottomBarLayout;
	
	//表单二
	private TextView tv_form_title_all_2;
	private TextView tv_form_title_2;
	private TextView tv_site_publish_determin_1;
	private TextView tv_site_publish_determin_2;
	private TextView tv_site_publish_determin_3;
	private TextView tv_site_publish_determin_4;
	private TextView tv_site_publish_determin_5;
	private TextView tv_site_publish_determin_6;
	private TextView tv_site_publish_determin_7;
	private TextView tv_site_publish_determin_8;
	private TextView tv_site_publish_determin_9;
	private TextView tv_site_publish_determin_10;
	private TextView tv_site_publish_determin_11;
	
	private EditText et_site_publish_determin_1;
	private EditText et_site_publish_determin_2;
	private EditText et_site_publish_determin_3;
	private EditText et_site_publish_determin_4;
	private EditText et_site_publish_determin_5;
	private EditText et_site_publish_determin_6;
	private EditText et_site_publish_determin_7;
	private EditText et_site_publish_determin_8;
	private EditText et_site_publish_determin_9;
	private EditText et_site_publish_determin_10;
	private EditText et_site_publish_determin_11;
	
//	private int caseId;
//	private String formContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		inflater = LayoutInflater.from(this);
		Intent intent = this.getIntent();
//		position = intent.getIntExtra("position", 0);
//		caseId = intent.getIntExtra("caseId", 0);
		
		setContentView(R.layout.form_site_publish_determin_situation);
		init();
		bottomBarLayout = (RelativeLayout)this.findViewById(R.id.form_bottom);
		if (bottomBarLayout == null){
			return;
		}
		reportCase = (TextView)bottomBarLayout.findViewById(R.id.report_case);
		posPrint = (TextView)bottomBarLayout.findViewById(R.id.pos_print);
		reportCase.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormSitePublishDeterminSituation.this, myHandler, Msg.PROMPT_PRINT,
						"提示", "如需打印先打印，上报后将无法打印,是否继续上报？", 0, "");
//				reportCaseProcess();
//				Toast.makeText(ActivityForm.this, "hhhhhh", Toast.LENGTH_SHORT);
			}
		});
		if (Config.currentFormList.get(Config.bdPositionId).getState() != null){
			if (!Config.currentFormList.get(Config.bdPositionId).getState().equals("")){
				reportCase.setVisibility(View.GONE);
				setEditTextLock();
			}
		}

		
		posPrint.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				posPrintProcess();
			}
		});
		
		if (Config.threadGetCaseInfo == null){
			Config.threadGetCaseInfo = new ThreadGetCaseInfo();
			Config.threadGetCaseInfo.showProcess(this, myHandler, 0, Config.caseId);
		}
	}
	
	private void setEditTextLock(){
		lockEditText(et_site_publish_determin_1);
		lockEditText(et_site_publish_determin_2);
		lockEditText(et_site_publish_determin_3);
		lockEditText(et_site_publish_determin_4);
		lockEditText(et_site_publish_determin_5);
		lockEditText(et_site_publish_determin_6);
		lockEditText(et_site_publish_determin_7);
		lockEditText(et_site_publish_determin_8);
		lockEditText(et_site_publish_determin_9);
		lockEditText(et_site_publish_determin_10);
		lockEditText(et_site_publish_determin_11);
		
	}
	//已上报案件 所有信息不可修改
	private void lockEditText(EditText et){
		et.setEnabled(false);
		et.setFocusable(false);
	}
	
	//显示案件详细信息
	private void showCaseInfo(){
		Log.i("ZFTSP", String.valueOf(Config.currentNewCaseList.toString()));
		et_site_publish_determin_1.setText(Config.currentNewCaseList.get(0).getThetime());
		et_site_publish_determin_2.setText(Config.currentNewCaseList.get(0).getAddress());
		et_site_publish_determin_3.setText(Config.currentNewCaseList.get(0).getCaseWeiFaXW());
		et_site_publish_determin_4.setText(Config.currentNewCaseList.get(0).getFazhe());
		et_site_publish_determin_5.setText(Config.currentNewCaseList.get(0).getYiju());
//		et_site_publish_determin_6.setText(Config.currentNewCaseList.get(0).getThetime());//责令改正
//		et_site_publish_determin_7.setText(Config.currentNewCaseList.get(0).getThetime());//行政处罚
//		et_site_publish_determin_8.setText(Config.currentNewCaseList.get(0).getThetime());//行政
//		et_site_publish_determin_9.setText(Config.currentNewCaseList.get(0).getThetime());//处罚地
//		et_site_publish_determin_10.setText(Config.currentNewCaseList.get(0).getThetime());//处罚票号
//		et_site_publish_determin_11.setText(Config.currentNewCaseList.get(0).getThetime());//司法起诉
	}
	
	private Handler myHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			Intent it = null;
			Bundle b = null;
			switch (msg.what){
			case Msg.GETCASEINFO:
				showCaseInfo();
//				nextPage();
				break;
			case Msg.LISTVIEW_REFRESH:
//				refresh();
				break;
			case Msg.ERROR_SERVER_CONNECT:
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormSitePublishDeterminSituation.this,
						null, Msg.PROGRAM_EXIT, "连接", "服务器连接失败,请稍后重试", 0, "");
				break;
			case Msg.PROGRAM_EXIT:
				ThreadExit thread = new ThreadExit(ActivityFormSitePublishDeterminSituation.this);
				thread.showProcess();
				break;
			case Msg.TITLE_PROGRESS_START:
				setTitleProgress(true);
				break;
			case Msg.TITLE_PROGRESS_STOP:
				setTitleProgress(false);
				break;
			case Msg.ONBACK:
				it = new Intent(ActivityFormSitePublishDeterminSituation.this,ActivityFormList.class);
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
				Dialog.showDialog(Dialog.OK, ActivityFormSitePublishDeterminSituation.this,
				myHandler, Msg.ACTIVITY_EXIT, "提示", "上报表单成功", 0, "");
				break;
			case Msg.REQUEST_ERROR:
				Dialog.showDialog(Dialog.OK, ActivityFormSitePublishDeterminSituation.this,
						null, 0, "提示", "上报表单错误", 0, "");
				break;
			case Msg.ACTIVITY_EXIT:
				it = new Intent(ActivityFormSitePublishDeterminSituation.this,ActivityFormList.class);
				b = new Bundle ();
				b.putInt("type",Msg.SUBMIT_BD_SUCCESS);
				it.putExtras(b);
				if (getParent() == null){
					setResult(0, it);
				}else {
					getParent().setResult(0, it);
				}
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
		Config.otherTabBdContent = getFormContentWhenPause();
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
	
	private void init(){
		tv_form_title_all_2 = (TextView)this.findViewById(R.id.form_title_all_13_2);
		tv_form_title_2 = (TextView)this.findViewById(R.id.form_title_13_2);
		tv_site_publish_determin_1 = (TextView)this.findViewById(R.id.site_publish_determin_1_name);
		tv_site_publish_determin_2 = (TextView)this.findViewById(R.id.site_publish_determin_2_name);
		tv_site_publish_determin_3 = (TextView)this.findViewById(R.id.site_publish_determin_3_name);
		tv_site_publish_determin_4 = (TextView)this.findViewById(R.id.site_publish_determin_4_name);
		tv_site_publish_determin_5 = (TextView)this.findViewById(R.id.site_publish_determin_5_name);
		tv_site_publish_determin_6 = (TextView)this.findViewById(R.id.site_publish_determin_6_name);
		tv_site_publish_determin_7 = (TextView)this.findViewById(R.id.site_publish_determin_7_name);
		tv_site_publish_determin_8 = (TextView)this.findViewById(R.id.site_publish_determin_8_name);
		tv_site_publish_determin_9 = (TextView)this.findViewById(R.id.site_publish_determin_9_name);
		tv_site_publish_determin_10 = (TextView)this.findViewById(R.id.site_publish_determin_10_name);
		tv_site_publish_determin_11 = (TextView)this.findViewById(R.id.site_publish_determin_11_name);
		
		et_site_publish_determin_1 = (EditText)this.findViewById(R.id.site_publish_determin_1_name_txt);
		et_site_publish_determin_2 = (EditText)this.findViewById(R.id.site_publish_determin_2_name_txt);
		et_site_publish_determin_3 = (EditText)this.findViewById(R.id.site_publish_determin_3_name_txt);
		et_site_publish_determin_4 = (EditText)this.findViewById(R.id.site_publish_determin_4_name_txt);
		et_site_publish_determin_5 = (EditText)this.findViewById(R.id.site_publish_determin_5_name_txt);
		et_site_publish_determin_6 = (EditText)this.findViewById(R.id.site_publish_determin_6_name_txt);
		et_site_publish_determin_7 = (EditText)this.findViewById(R.id.site_publish_determin_7_name_txt);
		et_site_publish_determin_8 = (EditText)this.findViewById(R.id.site_publish_determin_8_name_txt);
		et_site_publish_determin_9 = (EditText)this.findViewById(R.id.site_publish_determin_9_name_txt);
		et_site_publish_determin_10 = (EditText)this.findViewById(R.id.site_publish_determin_10_name_txt);
		et_site_publish_determin_11 = (EditText)this.findViewById(R.id.site_publish_determin_11_name_txt);
		
		tv_form_title_2.setText(getResources().getString(R.string.form_title_13_name));
		tv_form_title_all_2.setText(Config.cgUnitName);
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
		content.append(tv_form_title_all_2.getText().toString().trim()).append("\r\n");
		content.append(tempform.getFormName()).append("_");
		content.append(Config.posFormTitle+"执当罚字[").append(String.valueOf(year)).append("]  \r\nNo.")
		.append(tempCase.getLsh()).append("_");
		String[] otherStr = Config.otherTabBdContent.split("_");
		for (int i =0; i<otherStr.length; i++){
			if (type == 0){
				if (i > 9){
					break;
				}
			}else {
				if (i <= 9){
					continue;
				}
			}
			content.append(otherStr[i]);
			if (i%2 != 0){
				content.append("\r\n");
			}
		}
		
//		content.append("现场勘察示意图:\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");//15行
//		
		content.append(tv_site_publish_determin_1.getText().toString().trim().replaceAll("_", ""))
		.append(et_site_publish_determin_1.getText().toString().trim().replaceAll("_", "")).append("\r\n")
		.append(tv_site_publish_determin_2.getText().toString().trim().replaceAll("_", ""))
		.append(et_site_publish_determin_2.getText().toString().trim().replaceAll("_", "")).append("\r\n")
		.append(tv_site_publish_determin_3.getText().toString().trim().replaceAll("_", ""))
		.append(et_site_publish_determin_3.getText().toString().trim().replaceAll("_", "")).append("\r\n")
		.append(tv_site_publish_determin_4.getText().toString().trim().replaceAll("_", ""))
		.append(et_site_publish_determin_4.getText().toString().trim().replaceAll("_", "")).append("\r\n")
		.append(tv_site_publish_determin_5.getText().toString().trim().replaceAll("_", ""))
		.append(et_site_publish_determin_5.getText().toString().trim().replaceAll("_", "")).append("\r\n")
		.append(tv_site_publish_determin_6.getText().toString().trim().replaceAll("_", ""))
		.append(et_site_publish_determin_6.getText().toString().trim().replaceAll("_", "")).append("\r\n")
		.append(tv_site_publish_determin_7.getText().toString().trim().replaceAll("_", ""))
		.append(et_site_publish_determin_7.getText().toString().trim().replaceAll("_", "")).append("\r\n")
		.append(tv_site_publish_determin_8.getText().toString().trim().replaceAll("_", ""))
		.append(et_site_publish_determin_8.getText().toString().trim().replaceAll("_", "")).append("\r\n")
		.append(tv_site_publish_determin_9.getText().toString().trim().replaceAll("_", ""))
		.append(et_site_publish_determin_9.getText().toString().trim().replaceAll("_", "")).append("\r\n")
		.append(tv_site_publish_determin_10.getText().toString().trim().replaceAll("_", ""))
		.append(et_site_publish_determin_10.getText().toString().trim().replaceAll("_", "")).append("\r\n")
		.append(tv_site_publish_determin_11.getText().toString().trim().replaceAll("_", ""))
		.append(et_site_publish_determin_11.getText().toString().trim().replaceAll("_", "")).append("\r\n");
		
//		content.append("当事人签名:").append("\r\n\r\n\r\n");
//		content.append("检查人员签名:").append("\r\n\r\n\r\n");
//		content.append("见证人签名:").append("\r\n\r\n\r\n");
		
		content.append("_");
		content.append("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" + String.valueOf(year)).append(" 年 ");
		content.append(String.valueOf(month)).append(" 月 ");
		content.append(String.valueOf(day)).append(" 日");
		return content.toString();
//		return Html.fromHtml(content.toString()).toString();
	}
	
	private String getFormContentWhenPause(){
		StringBuffer content = new StringBuffer();
		content.append(tv_site_publish_determin_1.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_site_publish_determin_1.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_site_publish_determin_2.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_site_publish_determin_2.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_site_publish_determin_3.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_site_publish_determin_3.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_site_publish_determin_4.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_site_publish_determin_4.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_site_publish_determin_5.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_site_publish_determin_5.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_site_publish_determin_6.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_site_publish_determin_6.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_site_publish_determin_7.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_site_publish_determin_7.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_site_publish_determin_8.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_site_publish_determin_8.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_site_publish_determin_9.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_site_publish_determin_9.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_site_publish_determin_10.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_site_publish_determin_10.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_site_publish_determin_11.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_site_publish_determin_11.getText().toString().trim().replaceAll("_", "")).append("_");
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
		String[] otherStr = Config.otherTabBdContent.split("_");
		for (int i =0; i<otherStr.length; i++){
			if (i %2 == 0){
				continue;
			}
			if (type == 0){
				if (i > 9){
					break;
				}
			}else {
				if (i <= 9){
					continue;
				}
			}
			
			if (i%2 != 0){
				content.append(otherStr[i]);
				content.append("_");
			}
		}
		
//		content.append("现场勘察示意图:\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");//15行
		
		content
		.append(et_site_publish_determin_1.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_site_publish_determin_2.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_site_publish_determin_3.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_site_publish_determin_4.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_site_publish_determin_5.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_site_publish_determin_6.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_site_publish_determin_7.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_site_publish_determin_8.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_site_publish_determin_9.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_site_publish_determin_10.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_site_publish_determin_11.getText().toString().trim().replaceAll("_", ""));
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
			Intent it  = new Intent(ActivityFormSitePublishDeterminSituation.this, ActivityPosPrint.class);
			Bundle bundle = new Bundle();
			bundle.putInt("position", Config.bdPositionId);
			bundle.putString("content", getFormContent());
			it.putExtras(bundle);
			startActivityForResult(it, Msg.FORM_REQUEST_SUCCESS);
		}
	}
	
	
	
	private boolean checkInputLegal(){
//		if (et_site_publish_determin_1.getText().toString().trim().equals("")){
//			Dialog.showDialog(Dialog.OK, this, null, 0,
//					"提示", "不能为空!", 0, "");
//			return false;
//		}else {
//			return true;
//		}
		return true;
	}
	
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		Config.otherTabBdContent = "";
//		Config.otherTabBdContentSubmit = "";
//	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//如果上报成功刷新列表  先传回数据 后kill 当前activity
	}
}
