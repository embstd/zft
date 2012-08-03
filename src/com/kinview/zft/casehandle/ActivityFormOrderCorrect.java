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

public class ActivityFormOrderCorrect extends Activity {
	private LayoutInflater inflater;
	private RelativeLayout currentFormLayout;
//	public int position = -1;
	private TextView reportCase;
	private TextView posPrint;
	private RelativeLayout bottomBarLayout;
	
	//表单
	private TextView tv_form_title_all_5;
	private TextView tv_form_title_5;
	private TextView tv_order_correct_1;
	private TextView tv_order_correct_2;
	private TextView tv_order_correct_3;
	private TextView tv_order_correct_4;
	private TextView tv_order_correct_5;
	private TextView tv_order_correct_6;
	private TextView tv_order_correct_7;
	
	private EditText et_order_correct_1;
	private EditText et_order_correct_2;
	private EditText et_order_correct_3;
	private EditText et_order_correct_4;
	private EditText et_order_correct_5;
	private EditText et_order_correct_6;
	private EditText et_order_correct_7;
	private EditText et_order_correct_8;
	private EditText et_order_correct_9;
	private EditText et_order_correct_10;
	
//	private String formContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		inflater = LayoutInflater.from(this);
		Intent intent = this.getIntent();
//		position = intent.getIntExtra("position", 0);
		
		setContentView(R.layout.form_order_correct);
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
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormOrderCorrect.this, myHandler, Msg.PROMPT_PRINT,
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
		lockEditText(et_order_correct_1);
		lockEditText(et_order_correct_2);
		lockEditText(et_order_correct_3);
		lockEditText(et_order_correct_4);
		lockEditText(et_order_correct_5);
		lockEditText(et_order_correct_6);
//		lockEditText(et_order_correct_7);
		lockEditText(et_order_correct_8);
		lockEditText(et_order_correct_9);
		lockEditText(et_order_correct_10);
		
	}
	//已上报案件 所有信息不可修改
	private void lockEditText(EditText et){
		et.setEnabled(false);
		et.setFocusable(false);
	}
	
	//显示案件详细信息
	private void showCaseInfo(){
		Log.i("ZFTSP", String.valueOf(Config.currentNewCaseList.toString()));
		if (Config.currentNewCaseList.get(0).getOrganise().equals("") ||
				Config.currentNewCaseList.get(0).getOrganise() == null){
			et_order_correct_1.setText(Config.currentNewCaseList.get(0).getPerson());
		}else {
			et_order_correct_1.setText(Config.currentNewCaseList.get(0).getOrganise());
		}
//		et_order_correct_1.setText(Config.currentNewCaseList.get(0).getOrganise());
		et_order_correct_2.setText(Config.currentNewCaseList.get(0).getThetime());
		et_order_correct_3.setText(Config.currentNewCaseList.get(0).getAddress());
		et_order_correct_4.setText(Config.currentNewCaseList.get(0).getCaseWeiFaXW());
		et_order_correct_5.setText(Config.currentNewCaseList.get(0).getYiju());
//		et_order_correct_6.setText(Config.currentNewCaseList.get(0).getOrganise());//限定时间
//		et_order_correct_1.setText(Config.currentNewCaseList.get(0).getOrganise());//部门
//		et_order_correct_1.setText(Config.currentNewCaseList.get(0).getOrganise());//地址
//		et_order_correct_1.setText(Config.currentNewCaseList.get(0).getOrganise());//电话
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
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormOrderCorrect.this,
						null, Msg.PROGRAM_EXIT, "连接", "服务器连接失败,请稍后重试", 0, "");
				break;
			case Msg.PROGRAM_EXIT:
				ThreadExit thread = new ThreadExit(ActivityFormOrderCorrect.this);
				thread.showProcess();
				break;
			case Msg.TITLE_PROGRESS_START:
				setTitleProgress(true);
				break;
			case Msg.TITLE_PROGRESS_STOP:
				setTitleProgress(false);
				break;
			case Msg.ONBACK:
				it = new Intent(ActivityFormOrderCorrect.this,ActivityFormList.class);
				b = new Bundle ();
				b.putInt("type",Msg.REQUEST_REPEAT);
				it.putExtras(b);
				setResult(0, it);
				finish();
				break;
			case Msg.REQUEST_SUCCESS:
				Dialog.showDialog(Dialog.OK, ActivityFormOrderCorrect.this,
				myHandler, Msg.ACTIVITY_EXIT, "提示", "上报表单成功", 0, "");
				break;
			case Msg.REQUEST_ERROR:
				Dialog.showDialog(Dialog.OK, ActivityFormOrderCorrect.this,
						null, 0, "提示", "上报表单错误", 0, "");
				break;
			case Msg.ACTIVITY_EXIT:
				it = new Intent(ActivityFormOrderCorrect.this,ActivityFormList.class);
				b = new Bundle ();
				b.putInt("type",Msg.SUBMIT_BD_SUCCESS);
				it.putExtras(b);
				setResult(0, it);
				finish();
				break;
			case Msg.PROMPT_PRINT:
				reportCaseProcess();
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
	
	private void init(){
		tv_form_title_all_5 = (TextView)this.findViewById(R.id.form_title_all_5);
		tv_form_title_5 = (TextView)this.findViewById(R.id.form_title_5);
		tv_order_correct_1 = (TextView)this.findViewById(R.id.order_correct_1_name);
		tv_order_correct_2 = (TextView)this.findViewById(R.id.order_correct_2_name);
		tv_order_correct_3 = (TextView)this.findViewById(R.id.order_correct_3_name);
		tv_order_correct_4 = (TextView)this.findViewById(R.id.order_correct_4_name);
		tv_order_correct_5 = (TextView)this.findViewById(R.id.order_correct_5_name);
		tv_order_correct_6 = (TextView)this.findViewById(R.id.order_correct_6_name);
		tv_order_correct_7 = (TextView)this.findViewById(R.id.order_correct_7_name);
		
		et_order_correct_1 = (EditText)this.findViewById(R.id.order_correct_1_txt);
		et_order_correct_2 = (EditText)this.findViewById(R.id.order_correct_2_txt);
		et_order_correct_3 = (EditText)this.findViewById(R.id.order_correct_3_txt);
		et_order_correct_4 = (EditText)this.findViewById(R.id.order_correct_4_txt);
		et_order_correct_5 = (EditText)this.findViewById(R.id.order_correct_5_txt);
		et_order_correct_6 = (EditText)this.findViewById(R.id.order_correct_6_txt);
		et_order_correct_7 = (EditText)this.findViewById(R.id.order_correct_7_txt);
		et_order_correct_8 = (EditText)this.findViewById(R.id.order_correct_8_txt);
		et_order_correct_9 = (EditText)this.findViewById(R.id.order_correct_9_txt);
		et_order_correct_10 = (EditText)this.findViewById(R.id.order_correct_10_txt);
		
		tv_form_title_all_5.setText(Config.cgUnitName);
	}
	
	private String getFormContent(){
		NewCase tempCase = Config.currentNewCaseList.get(0);
		Form tempform = Config.currentFormList.get(Config.bdPositionId);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		
		StringBuffer content = new StringBuffer();
		content.append(tv_form_title_all_5.getText().toString().trim()).append("\r\n");
		content.append(tv_form_title_5.getText().toString().trim()).append("_");
//		content.append(tempform.getFormName()).append("_");
		content.append(Config.posFormTitle +"执改字[").append(String.valueOf(year)).append("]  \r\nNo.")
		.append(tempCase.getLsh()).append("_");
		content.append(et_order_correct_1.getText().toString().trim()).append(":\r\n\r\n");
		content.append("    经查，你（单位）于  ").append(et_order_correct_2.getText().toString().trim())
		.append("  ，在  ").append(et_order_correct_3.getText().toString().trim())
		.append("  ，进行  ").append(et_order_correct_4.getText().toString().trim())
		.append("  的行为，已违反  ")
		.append(tempCase.getYiju());
		content.append("  的规定，现责令你（单位）立即自收到本通知书之日起按下列要求改正：\r\n");
		
		if (!et_order_correct_7.getText().toString().trim().equals("")){
			content.append("    1：").append(et_order_correct_7.getText().toString().trim()).append("\r\n\r\n");
		}
		if (!et_order_correct_8.getText().toString().trim().equals("")){
			content.append("    2：").append(et_order_correct_8.getText().toString().trim()).append("\r\n\r\n");
		}
		if (!et_order_correct_9.getText().toString().trim().equals("")) {
			content.append("    3：").append(et_order_correct_9.getText().toString().trim()).append("\r\n\r\n");
		}
		if (!et_order_correct_10.getText().toString().trim().equals("")) {
			content.append("    4：").append(et_order_correct_10.getText().toString().trim()).append("\r\n\r\n");
		}
		content.append("逾期未改正的，本机关将依法予以行政处罚。\r\n特此通知。\r\n\r\n\r\n")
		.append("当事人签字:\r\n\r\n\r\n").append("见证人签字:\r\n\r\n\r\n");;
		content.append("执法人员签字:\r\n\r\n\r\n");
		content.append("_");
		content.append("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" + String.valueOf(year)).append(" 年 ");
		content.append(String.valueOf(month)).append(" 月 ");
		content.append(String.valueOf(day)).append(" 日");
		
		//测试

		return content.toString();
//		return Html.fromHtml(content.toString()).toString();
	}
	
	private String getFormContent2(){
		NewCase tempCase = Config.currentNewCaseList.get(0);
		Form tempfrom = Config.currentFormList.get(Config.bdPositionId);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		StringBuffer content = new StringBuffer();
		content.append(String.valueOf(year)).append("_");//年
		if (tempCase.getLsh() == null || tempCase.getLsh().equals("")){
			content.append(" ").append("_");
		}else {
			content.append(tempCase.getLsh()).append("_");//流水号
		}
		
		int type = 0;//0:组织 1： 个人
		if (tempCase.getOrganise() == null || tempCase.getOrganise().equals("")){
			if (tempCase.getPerson() != null){
				type = 1;
			}
		}else {
			type = 0;
		}
		
		content
		.append(et_order_correct_1.getText().toString().trim()).append("_")
		.append(et_order_correct_2.getText().toString().trim()).append("_")
		.append(et_order_correct_3.getText().toString().trim()).append("_")
		.append(et_order_correct_4.getText().toString().trim()).append("_")
		.append(et_order_correct_5.getText().toString().trim());
//		if (!et_order_correct_7.getText().toString().trim().equals("")){
			content.append(et_order_correct_7.getText().toString().trim()).append("_");
//		}
//		if (!et_order_correct_8.getText().toString().trim().equals("")){
			content.append(et_order_correct_8.getText().toString().trim()).append("_");
//		}
//		if (!et_order_correct_9.getText().toString().trim().equals("")) {
			content.append(et_order_correct_9.getText().toString().trim()).append("_");
//		}
//		if (!et_order_correct_10.getText().toString().trim().equals("")) {
			content.append(et_order_correct_10.getText().toString().trim()).append("_");
//		}
			content.append(year).append("_");	
			content.append(month).append("_");
			content.append(day);
//		.append(et_order_correct_7.getText().toString().trim()).append("_")
//		.append(et_order_correct_8.getText().toString().trim()).append("_")
//		.append(et_order_correct_9.getText().toString().trim()).append("_")
//		.append(et_order_correct_10.getText().toString().trim());
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
			
			Log.i("ZFTSP", "aj_id: " + aj_id +" position: " + Config.bdPositionId + " bd_id: " + bd_id + " bd_name: " + bd_name + " param: " + param);
				
			if (Config.threadSubmitBd == null){
				Config.threadSubmitBd = new ThreadSubmitBd();
				Config.threadSubmitBd.showProcess(this, myHandler, aj_id, bd_id, bd_name, param);
			}
		}
	}
	
	private void posPrintProcess() {
		if (checkInputLegal()) {
			Intent it  = new Intent(ActivityFormOrderCorrect.this, ActivityPosPrint.class);
			Bundle bundle = new Bundle();
//			bundle.putInt("position", position);
			bundle.putString("content", getFormContent());
			it.putExtras(bundle);
			startActivityForResult(it, Msg.FORM_REQUEST_SUCCESS);
		}
	}
	
	
	private boolean checkInputLegal(){
//		if (et_order_correct_1.getText().toString().trim().equals("")){
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
		//如果上报成功刷新列表  先传回数据 后kill 当前activity
	}
}
