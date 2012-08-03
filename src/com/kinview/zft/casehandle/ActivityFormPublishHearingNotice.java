package com.kinview.zft.casehandle;

import java.util.Calendar;

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

public class ActivityFormPublishHearingNotice extends Activity{
	private LayoutInflater inflater;
	private RelativeLayout currentFormLayout;
//	public int position = -1;
	private TextView reportCase;
	private TextView posPrint;
	private RelativeLayout bottomBarLayout;
	
	//表单
	private TextView tv_form_title_all_3;
	private TextView tv_form_title_3;
	private TextView tv_publish_before_notice_1;
	private TextView tv_publish_before_notice_2;
	private TextView tv_publish_before_notice_3;
	private TextView tv_publish_before_notice_4;
	private TextView tv_publish_before_notice_5;
	private TextView tv_publish_before_notice_6;
	private TextView tv_publish_before_notice_7;
	private TextView tv_publish_before_notice_8;
	private TextView tv_publish_before_notice_9;
	private TextView tv_publish_before_notice_10;
	private TextView tv_publish_before_notice_11;
	
	private EditText et_publish_before_notice_1;
	private EditText et_publish_before_notice_2;
	private EditText et_publish_before_notice_3;
	private EditText et_publish_before_notice_4;
	private EditText et_publish_before_notice_5;
	private EditText et_publish_before_notice_6;
	private EditText et_publish_before_notice_7;
//	private EditText et_publish_before_notice_8;
	private EditText et_publish_before_notice_9;
	private EditText et_publish_before_notice_10;
	private EditText et_publish_before_notice_11;
	
	private String xiandingTimeStr = "";//限定时间的string
	
//	private String formContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		inflater = LayoutInflater.from(this);
		Intent intent = this.getIntent();
//		position = intent.getIntExtra("position", 0);
		
		setContentView(R.layout.form_publish_before_notice);
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
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormPublishHearingNotice.this, myHandler, Msg.PROMPT_PRINT,
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
		lockEditText(et_publish_before_notice_1);
		lockEditText(et_publish_before_notice_2);
		lockEditText(et_publish_before_notice_3);
		lockEditText(et_publish_before_notice_4);
		lockEditText(et_publish_before_notice_5);
		lockEditText(et_publish_before_notice_6);
		lockEditText(et_publish_before_notice_7);
//		lockEditText(et_publish_before_notice_8);
		lockEditText(et_publish_before_notice_9);
		lockEditText(et_publish_before_notice_10);
		lockEditText(et_publish_before_notice_11);
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
			et_publish_before_notice_1.setText(Config.currentNewCaseList.get(0).getPerson());
		}else {
			et_publish_before_notice_1.setText(Config.currentNewCaseList.get(0).getOrganise());
		}
//		et_publish_before_notice_1.setText(Config.currentNewCaseList.get(0).getOrganise());
		et_publish_before_notice_2.setText(Config.currentNewCaseList.get(0).getThetime());
		et_publish_before_notice_3.setText(Config.currentNewCaseList.get(0).getAddress());
		et_publish_before_notice_4.setText(Config.currentNewCaseList.get(0).getCaseWeiFaXW());
		et_publish_before_notice_5.setText(Config.currentNewCaseList.get(0).getFazhe());
		et_publish_before_notice_6.setText(Config.currentNewCaseList.get(0).getYiju());
//		xiandingTimeStr = Config.getXiandingTimeString(Config.currentNewCaseList.get(0).getThetime());
//		et_publish_before_notice_7.setText(xiandingTimeStr);// 最终 什么样的处罚 结果 （自己填）
//		et_publish_before_notice_9.setText(Config.user.getBranch_name());//部门
		if (Config.cityTag.equals("cztnq") || Config.cityTag.equals("czxbq") || Config.cityTag.equals("czwjq")
				|| Config.cityTag.equals("czzlq") || Config.cityTag.equals("czqsyq")){
			et_publish_before_notice_9.setText(Config.cgUnitNameZhiDui);//部门
		}else {
			et_publish_before_notice_9.setText(Config.user.getBranch_name());//部门
		}
//		et_publish_before_notice_9.setText(Config.cgUnitName + Config.cgUnitNameDaDui);//部门
		et_publish_before_notice_10.setText(Config.user.getBranch_address());//地址
		et_publish_before_notice_11.setText(Config.user.getBranch_phone());//电话
//		et_publish_before_notice_11.setText(Config.currentNewCaseList.get(0).getOrganise());
//		et_publish_before_notice_12.setText(Config.currentNewCaseList.get(0).getOrganise());
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
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormPublishHearingNotice.this,
						null, Msg.PROGRAM_EXIT, "连接", "服务器连接失败,请稍后重试", 0, "");
				break;
			case Msg.PROGRAM_EXIT:
				ThreadExit thread = new ThreadExit(ActivityFormPublishHearingNotice.this);
				thread.showProcess();
				break;
			case Msg.TITLE_PROGRESS_START:
				setTitleProgress(true);
				break;
			case Msg.TITLE_PROGRESS_STOP:
				setTitleProgress(false);
				break;
			case Msg.ONBACK:
				it = new Intent(ActivityFormPublishHearingNotice.this,ActivityFormList.class);
				b = new Bundle ();
				b.putInt("type",Msg.REQUEST_REPEAT);
				it.putExtras(b);
				setResult(0, it);
				finish();
				break;
			case Msg.REQUEST_SUCCESS:
				Dialog.showDialog(Dialog.OK, ActivityFormPublishHearingNotice.this,
				myHandler, Msg.ACTIVITY_EXIT, "提示", "上报表单成功", 0, "");
				break;
			case Msg.REQUEST_ERROR:
				Dialog.showDialog(Dialog.OK, ActivityFormPublishHearingNotice.this,
						null, 0, "提示", "上报表单错误", 0, "");
				break;
			case Msg.ACTIVITY_EXIT:
				it = new Intent(ActivityFormPublishHearingNotice.this,ActivityFormList.class);
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
		tv_form_title_all_3 = (TextView)this.findViewById(R.id.form_title_all_9);
		tv_form_title_3 = (TextView)this.findViewById(R.id.form_title_9);
		tv_publish_before_notice_1 = (TextView)this.findViewById(R.id.publish_before_notice_1_name);
		tv_publish_before_notice_2 = (TextView)this.findViewById(R.id.publish_before_notice_2_name);
		tv_publish_before_notice_3 = (TextView)this.findViewById(R.id.publish_before_notice_3_name);
		tv_publish_before_notice_4 = (TextView)this.findViewById(R.id.publish_before_notice_4_name);
		tv_publish_before_notice_5 = (TextView)this.findViewById(R.id.publish_before_notice_5_name);
		tv_publish_before_notice_6 = (TextView)this.findViewById(R.id.publish_before_notice_6_name);
		tv_publish_before_notice_7 = (TextView)this.findViewById(R.id.publish_before_notice_7_name);
		tv_publish_before_notice_8 = (TextView)this.findViewById(R.id.publish_before_notice_8_name);
		tv_publish_before_notice_9 = (TextView)this.findViewById(R.id.publish_before_notice_9_name);
		tv_publish_before_notice_10 = (TextView)this.findViewById(R.id.publish_before_notice_10_name);
		tv_publish_before_notice_11 = (TextView)this.findViewById(R.id.publish_before_notice_11_name);
		
		et_publish_before_notice_1 = (EditText)this.findViewById(R.id.publish_before_notice_1_name_txt);
		et_publish_before_notice_2 = (EditText)this.findViewById(R.id.publish_before_notice_2_name_txt);
		et_publish_before_notice_3 = (EditText)this.findViewById(R.id.publish_before_notice_3_name_txt);
		et_publish_before_notice_4 = (EditText)this.findViewById(R.id.publish_before_notice_4_name_txt);
		et_publish_before_notice_5 = (EditText)this.findViewById(R.id.publish_before_notice_5_name_txt);
		et_publish_before_notice_6 = (EditText)this.findViewById(R.id.publish_before_notice_6_name_txt);
		et_publish_before_notice_7 = (EditText)this.findViewById(R.id.publish_before_notice_7_name_txt);
//		et_publish_before_notice_8 = (EditText)this.findViewById(R.id.publish_before_notice_8_name_txt);
		et_publish_before_notice_9 = (EditText)this.findViewById(R.id.publish_before_notice_9_name_txt);
		et_publish_before_notice_10 = (EditText)this.findViewById(R.id.publish_before_notice_10_name_txt);
		et_publish_before_notice_11 = (EditText)this.findViewById(R.id.publish_before_notice_11_name_txt);
		
		tv_form_title_3.setText(getResources().getString(R.string.form_title_9_1_name));
		tv_form_title_all_3.setText(Config.cgUnitName);
	}
	
	private String getFormContent(){
		NewCase tempCase = Config.currentNewCaseList.get(0);
		Form tempform = Config.currentFormList.get(Config.bdPositionId);
		
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		
		StringBuffer content = new StringBuffer();
		content.append(tv_form_title_all_3.getText().toString().trim()).append("\r\n");
		content.append(tv_form_title_3.getText().toString().trim()).append("\r\n").append("_");
		
//		content.append(tempform.getFormName()).append("_");
		content.append(Config.posFormTitle +"执告字[").append(String.valueOf(year)).append("]  \r\nNo.")
		.append(tempCase.getLsh()).append("_");
		content.append(et_publish_before_notice_1.getText().toString().trim()).append(":\r\n\r\n");
		content.append("    经查，你（单位）于  ").append(et_publish_before_notice_2.getText().toString().trim())
		.append("，在  ").append(et_publish_before_notice_3.getText().toString().trim())
		.append("，进行  ").append(et_publish_before_notice_4.getText().toString().trim())
		.append("的行为，已违反了  ")
		.append(et_publish_before_notice_5.getText().toString().trim()).append("的规定。根据");//分段
		content.append(et_publish_before_notice_6.getText().toString().trim()).append("的规定。本机关拟对你（单位）作出");//分段
		content.append(et_publish_before_notice_7.getText().toString().trim()).append("的行政处罚。 \r\n    ");//分段
		content.append("根据《中华人民共和国行政处罚法》第三十一条、第三十二条的规定，你（单 位）有权进行陈述和申辩。请你（单位）在收到本通知书之日起三日内到 ")
		.append(et_publish_before_notice_9.getText().toString().trim())
		.append("（地址：")
		.append(et_publish_before_notice_10.getText().toString().trim())
		.append("）进行陈述和申辩。联系电话：" )
		.append(et_publish_before_notice_11.getText().toString().trim())
		.append("。\r\n    逾期视为放弃上述权利。\r\n\r\n");
		
		content.append("_");
		content.append("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" + String.valueOf(year)).append(" 年 ");
		content.append(String.valueOf(month)).append(" 月 ");
		content.append(String.valueOf(day)).append(" 日");
		
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
		
		content
		.append(et_publish_before_notice_1.getText().toString().trim()).append("_")
		.append(et_publish_before_notice_2.getText().toString().trim()).append("_")
		.append(et_publish_before_notice_3.getText().toString().trim()).append("_")
		.append(et_publish_before_notice_4.getText().toString().trim()).append("_")
		.append(et_publish_before_notice_5.getText().toString().trim()).append("_")
		.append(et_publish_before_notice_6.getText().toString().trim()).append("_")
		.append(et_publish_before_notice_7.getText().toString().trim()).append("_")
		.append(et_publish_before_notice_9.getText().toString().trim()).append("_")
		.append(et_publish_before_notice_10.getText().toString().trim()).append("_")
		.append(et_publish_before_notice_11.getText().toString().trim()).append("_")
		
		.append(String.valueOf(year)).append("_")
		.append(String.valueOf(month)).append("_")
		.append(String.valueOf(day));
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
			Intent it  = new Intent(ActivityFormPublishHearingNotice.this, ActivityPosPrint.class);
			Bundle bundle = new Bundle();
			bundle.putInt("position", Config.bdPositionId);
			bundle.putString("content", getFormContent());
			it.putExtras(bundle);
			startActivityForResult(it, Msg.FORM_REQUEST_SUCCESS);
		}
	}
	
	
	
	private boolean checkInputLegal(){
//		if (xiandingTimeStr.equals("")){
//			Dialog.showDialog(Dialog.OK, this, null, 0,
//					"提示", "时间不能为空!", 0, "");
//			return false;
//		}else {
			return true;
//		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//如果上报成功刷新列表  先传回数据 后kill 当前activity
	}
}
