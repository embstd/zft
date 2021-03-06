package com.kinview.zft.casehandle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kinview.config.Config;
import com.kinview.config.Dialog;
import com.kinview.config.Msg;
import com.kinview.entity.NewCase;
import com.kinview.thread.ThreadExit;
import com.kinview.thread.ThreadGetCaseInfo;
import com.kinview.thread.ThreadSubmitBd;
import com.kinview.util.Form;
import com.kinview.zft.R;



public class ActivitySurveyAskRecordBaseInfo extends Activity {
	private LayoutInflater inflater;
	private RelativeLayout currentFormLayout;
//	public int position = -1;
	private TextView reportCase;
	private TextView posPrint;
	private RelativeLayout bottomBarLayout;
	
	//表单
	private TextView tv_form_title_all_4;
	private TextView tv_form_title_4;
	private TextView tv_survey_ask_base_info_1;
	private TextView tv_survey_ask_base_info_2;
	private TextView tv_survey_ask_base_info_3;
	private TextView tv_survey_ask_base_info_4;
	private TextView tv_survey_ask_base_info_5;
	private TextView tv_survey_ask_base_info_6;
	private TextView tv_survey_ask_base_info_7;
	private TextView tv_survey_ask_base_info_8;
	private TextView tv_survey_ask_base_info_9;
	private TextView tv_survey_ask_base_info_10;
	private TextView tv_survey_ask_base_info_11;
	private TextView tv_survey_ask_base_info_12;
	private TextView tv_survey_ask_base_info_13;
	private TextView tv_survey_ask_base_info_14;
	
	private EditText et_survey_ask_base_info_1;
	private EditText et_survey_ask_base_info_2;
	private EditText et_survey_ask_base_info_3;
	private EditText et_survey_ask_base_info_4;
	private EditText et_survey_ask_base_info_5;
	private EditText et_survey_ask_base_info_6;
	private EditText et_survey_ask_base_info_7;
	private EditText et_survey_ask_base_info_8;
	private EditText et_survey_ask_base_info_9;
	private EditText et_survey_ask_base_info_10;
	private EditText et_survey_ask_base_info_11;
	private EditText et_survey_ask_base_info_12;
	private EditText et_survey_ask_base_info_13;
	private EditText et_survey_ask_base_info_14;
	
	private String xiandingTimeStr = "";//限定时间的string
//	private String formContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		inflater = LayoutInflater.from(this);
		Intent intent = this.getIntent();
//		position = intent.getIntExtra("position", 0);
		
		setContentView(R.layout.form_survey_ask_record_base_info);
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
				Dialog.showDialog(Dialog.OKCANCEL, ActivitySurveyAskRecordBaseInfo.this, myHandler, Msg.PROMPT_PRINT,
						"提示", "如需打印先打印，上报后将无法打印,是否继续上报？", 0, "");
//	
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
		lockEditText(et_survey_ask_base_info_1);
		lockEditText(et_survey_ask_base_info_2);
		lockEditText(et_survey_ask_base_info_3);
		lockEditText(et_survey_ask_base_info_4);
		lockEditText(et_survey_ask_base_info_5);
		lockEditText(et_survey_ask_base_info_6);
		lockEditText(et_survey_ask_base_info_7);
		lockEditText(et_survey_ask_base_info_8);
		lockEditText(et_survey_ask_base_info_9);
		lockEditText(et_survey_ask_base_info_10);
		lockEditText(et_survey_ask_base_info_11);
		lockEditText(et_survey_ask_base_info_12);
		lockEditText(et_survey_ask_base_info_13);
		lockEditText(et_survey_ask_base_info_14);
		
	}
	//已上报案件 所有信息不可修改
	private void lockEditText(EditText et){
		et.setEnabled(false);
		et.setFocusable(false);
	}
	

	//显示案件详细信息
	private void showCaseInfo(){
		Log.i("ZFTSP", String.valueOf(Config.currentNewCaseList.toString()));
		NewCase tempCase = Config.currentNewCaseList.get(0);
		Form tempform = Config.currentFormList.get(Config.bdPositionId);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		et_survey_ask_base_info_1.setText(sdf.format(date));
		et_survey_ask_base_info_2.setText(tempCase.getAddress());
		et_survey_ask_base_info_3.setText(Config.name);
		et_survey_ask_base_info_4.setText(Config.name);
		
		int type = 0;//0:组织 1： 个人
		if (tempCase.getOrganise() == null || tempCase.getOrganise().equals("")){
			if (tempCase.getPerson() != null){
				type = 1;
			}
		}else {
			type = 0;
		}
		if (type == 1){
			et_survey_ask_base_info_5.setText(tempCase.getPerson());
			et_survey_ask_base_info_6.setText(tempCase.getSex());
			et_survey_ask_base_info_7.setText(tempCase.getIdnumber());
			et_survey_ask_base_info_8.setText(tempCase.getPtel());
//			et_survey_ask_base_info_9.setText("");//出生年月
			et_survey_ask_base_info_10.setText(tempCase.getPadd());
//			et_survey_ask_base_info_11.setText("");//工作单位
			et_survey_ask_base_info_12.setText(tempCase.getZw());//职位
//			et_survey_ask_base_info_13.setText("");//邮编
		}else {
			et_survey_ask_base_info_5.setText(tempCase.getFzr());
			et_survey_ask_base_info_6.setText(tempCase.getSex());
			et_survey_ask_base_info_7.setText(tempCase.getIdnumber());
			et_survey_ask_base_info_8.setText(tempCase.getOtel());
//			et_survey_ask_base_info_9.setText("");
			et_survey_ask_base_info_10.setText(tempCase.getOadd());
			et_survey_ask_base_info_11.setText(tempCase.getOrganise());//工作单位
			et_survey_ask_base_info_12.setText(tempCase.getZw());
//			et_survey_ask_base_info_13.setText("");//邮编
		}
		
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
				Dialog.showDialog(Dialog.OKCANCEL, ActivitySurveyAskRecordBaseInfo.this,
						null, Msg.PROGRAM_EXIT, "连接", "服务器连接失败,请稍后重试", 0, "");
				break;
			case Msg.PROGRAM_EXIT:
				ThreadExit thread = new ThreadExit(ActivitySurveyAskRecordBaseInfo.this);
				thread.showProcess();
				break;
			case Msg.TITLE_PROGRESS_START:
				setTitleProgress(true);
				break;
			case Msg.TITLE_PROGRESS_STOP:
				setTitleProgress(false);
				break;
			case Msg.ONBACK:
				it = new Intent(ActivitySurveyAskRecordBaseInfo.this,ActivityFormList.class);
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
				Dialog.showDialog(Dialog.OK, ActivitySurveyAskRecordBaseInfo.this,
				myHandler, Msg.ACTIVITY_EXIT, "提示", "上报表单成功", 0, "");
				break;
			case Msg.REQUEST_ERROR:
				Dialog.showDialog(Dialog.OK, ActivitySurveyAskRecordBaseInfo.this,
						null, 0, "提示", "上报表单错误", 0, "");
				break;
			case Msg.ACTIVITY_EXIT:
				it = new Intent(ActivitySurveyAskRecordBaseInfo.this,ActivityFormList.class);
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
		Config.otherTabBdContentSubmit = getFormContentWhenPause();//上报用
		Config.otherTabBdContent = getFormContent();//pos打印
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
		tv_form_title_all_4 = (TextView)this.findViewById(R.id.form_title_all_11);
		tv_form_title_4 = (TextView)this.findViewById(R.id.form_title_11);
		tv_survey_ask_base_info_1 = (TextView)this.findViewById(R.id.form_survey_ask_base_info_1_name);
		tv_survey_ask_base_info_2 = (TextView)this.findViewById(R.id.form_survey_ask_base_info_2_name);
		tv_survey_ask_base_info_3 = (TextView)this.findViewById(R.id.form_survey_ask_base_info_3_name);
		tv_survey_ask_base_info_4 = (TextView)this.findViewById(R.id.form_survey_ask_base_info_4_name);
		tv_survey_ask_base_info_5 = (TextView)this.findViewById(R.id.form_survey_ask_base_info_5_name);
		tv_survey_ask_base_info_6 = (TextView)this.findViewById(R.id.form_survey_ask_base_info_6_name);
		tv_survey_ask_base_info_7 = (TextView)this.findViewById(R.id.form_survey_ask_base_info_7_name);
		tv_survey_ask_base_info_8 = (TextView)this.findViewById(R.id.form_survey_ask_base_info_8_name);
		tv_survey_ask_base_info_9 = (TextView)this.findViewById(R.id.form_survey_ask_base_info_9_name);
		tv_survey_ask_base_info_10 = (TextView)this.findViewById(R.id.form_survey_ask_base_info_10_name);
		tv_survey_ask_base_info_11 = (TextView)this.findViewById(R.id.form_survey_ask_base_info_11_name);
		tv_survey_ask_base_info_12 = (TextView)this.findViewById(R.id.form_survey_ask_base_info_12_name);
		tv_survey_ask_base_info_13 = (TextView)this.findViewById(R.id.form_survey_ask_base_info_13_name);
		tv_survey_ask_base_info_14 = (TextView)this.findViewById(R.id.form_survey_ask_base_info_14_name);
		
		et_survey_ask_base_info_1 = (EditText)this.findViewById(R.id.form_survey_ask_base_info_1_name_txt);
		et_survey_ask_base_info_2 = (EditText)this.findViewById(R.id.form_survey_ask_base_info_2_name_txt);
		et_survey_ask_base_info_3 = (EditText)this.findViewById(R.id.form_survey_ask_base_info_3_name_txt);
		et_survey_ask_base_info_4 = (EditText)this.findViewById(R.id.form_survey_ask_base_info_4_name_txt);
		et_survey_ask_base_info_5 = (EditText)this.findViewById(R.id.form_survey_ask_base_info_5_name_txt);
		et_survey_ask_base_info_6 = (EditText)this.findViewById(R.id.form_survey_ask_base_info_6_name_txt);
		et_survey_ask_base_info_7 = (EditText)this.findViewById(R.id.form_survey_ask_base_info_7_name_txt);
		et_survey_ask_base_info_8 = (EditText)this.findViewById(R.id.form_survey_ask_base_info_8_name_txt);
		et_survey_ask_base_info_9 = (EditText)this.findViewById(R.id.form_survey_ask_base_info_9_name_txt);
		et_survey_ask_base_info_10 = (EditText)this.findViewById(R.id.form_survey_ask_base_info_10_name_txt);
		et_survey_ask_base_info_11 = (EditText)this.findViewById(R.id.form_survey_ask_base_info_11_name_txt);
		et_survey_ask_base_info_12 = (EditText)this.findViewById(R.id.form_survey_ask_base_info_12_name_txt);
		et_survey_ask_base_info_13 = (EditText)this.findViewById(R.id.form_survey_ask_base_info_13_name_txt);
		et_survey_ask_base_info_14 = (EditText)this.findViewById(R.id.form_survey_ask_base_info_14_name_txt);
		
		tv_form_title_all_4.setText(Config.cgUnitName);
	}
	
	private String getFormContent(){
		NewCase tempCase = Config.currentNewCaseList.get(0);
		Form tempform = Config.currentFormList.get(Config.bdPositionId);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		StringBuffer content = new StringBuffer();
		content.append(tv_form_title_all_4.getText().toString().trim()).append("\r\n");
		content.append(tv_form_title_4.getText().toString().trim()).append("\r\n").append("_");
//		content.append(tempform.getFormName()).append("_");
//		content.append(Config.posFormTitle +"执登存字[").append(String.valueOf(year)).append("]  \r\nNo.")
//		.append(tempCase.getLsh()).append("_");
		
		content
		.append("案由： ").append(tempCase.getCaseWeiFaXW().toString().trim()).append("\r\n")
		.append("时间： ").append(et_survey_ask_base_info_1.getText().toString().trim()).append("\r\n")
		.append("地点： ").append(et_survey_ask_base_info_2.getText().toString().trim()).append("\r\n")
		.append("调查人：").append(et_survey_ask_base_info_3.getText().toString().trim()).append("\r\n")
		.append("记录人：").append(et_survey_ask_base_info_4.getText().toString().trim()).append("\r\n")
		.append("被调查人： ").append(et_survey_ask_base_info_5.getText().toString().trim()).append("\r\n")
		.append("性别： ").append(et_survey_ask_base_info_6.getText().toString().trim()).append("\r\n")
		.append("出生年月： ").append(et_survey_ask_base_info_7.getText().toString().trim()).append("\r\n")
		.append("身份证号码 ： ").append(et_survey_ask_base_info_8.getText().toString().trim()).append("\r\n")
		.append("住所： ").append(et_survey_ask_base_info_9.getText().toString().trim()).append("\r\n")
		.append("电话： ").append(et_survey_ask_base_info_10.getText().toString().trim()).append("\r\n")
		.append("工作单位 ： ").append(et_survey_ask_base_info_11.getText().toString().trim()).append("\r\n")
		.append("职务： ").append(et_survey_ask_base_info_12.getText().toString().trim()).append("\r\n")
		.append("邮编： ").append(et_survey_ask_base_info_13.getText().toString().trim()).append("\r\n");
		
		content.append("告知： 我们是常州市城市管理行政执法局的执法人员，这是我们的《行政执法证》 "
				+ "（出示证件），执法证号为 "+Config.user.getZfzid()+"，"+Config.user.getU_ZfzID()+"，现依法前 "
				+ "来进行调查。根据《中华人民共和国行政处罚法》等法律规定，如执法人员少 "
				+ "于两人或执法证件与身份不符，你有权拒绝调查，如执法人员与案件有直接利 "
				+ "害关系，你也有权申请执法人员回避。同时你应如实提供有关资料、回答询问， "
				+ "如作虚假陈述或拒绝、阻挠调查，将依法追究法律责任。请你配合我们调查询"
				+ "问。\r\n问：你是否听清楚了？\r\n答： 我听清楚了\r\n");
		content.append("笔录内容：\r\n");
		if (!Config.otherTabBdContent.equals("")){
			content.append(Config.otherTabBdContent).append("\r\n");
		}
//		content.append( "被调查人：");
		
		content.append("_");
		content.append("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" + String.valueOf(year)).append(" 年 ");
		content.append(String.valueOf(month)).append(" 月 ");
		content.append(String.valueOf(day)).append(" 日");
		
		return content.toString();
//		return Html.fromHtml(content.toString()).toString();
	}
	
	private String getFormContentWhenPause(){
		StringBuffer content = new StringBuffer();
		content
		.append(et_survey_ask_base_info_1.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_2.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_3.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_4.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_5.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_6.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_7.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_8.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_9.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_10.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_11.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_12.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_13.getText().toString().trim()).append("_");
		
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
		
		StringBuffer content = new StringBuffer();
		content
		.append(et_survey_ask_base_info_1.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_2.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_3.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_4.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_5.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_6.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_7.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_8.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_9.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_10.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_11.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_12.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_13.getText().toString().trim());
		
//		if (!Config.otherTabBdContentSubmit.equals("")){
//			content.append("_").append(Config.otherTabBdContentSubmit).append("_");
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
			Intent it  = new Intent(ActivitySurveyAskRecordBaseInfo.this, ActivityPosPrint.class);
			Bundle bundle = new Bundle();
			bundle.putInt("position", Config.bdPositionId);
			bundle.putString("content", getFormContent());
			it.putExtras(bundle);
			startActivityForResult(it, Msg.FORM_REQUEST_SUCCESS);
		}
	}
	
	
	
	private boolean checkInputLegal(){
//		if (et_survey_ask_base_info_1.getText().toString().trim().equals("")){
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
