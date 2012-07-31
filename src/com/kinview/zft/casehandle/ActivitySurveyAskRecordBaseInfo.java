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
	
	//����
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
	
	private String xiandingTimeStr = "";//�޶�ʱ���string
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
						"��ʾ", "�����ӡ�ȴ�ӡ���ϱ����޷���ӡ,�Ƿ�����ϱ���", 0, "");
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
	//���ϱ����� ������Ϣ�����޸�
	private void lockEditText(EditText et){
		et.setEnabled(false);
		et.setFocusable(false);
	}
	

	//��ʾ������ϸ��Ϣ
	private void showCaseInfo(){
		Log.i("ZFTSP", String.valueOf(Config.currentNewCaseList.toString()));
		NewCase tempCase = Config.currentNewCaseList.get(0);
		Form tempform = Config.currentFormList.get(Config.bdPositionId);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��ss��");
		et_survey_ask_base_info_1.setText(sdf.format(date));
//		et_survey_ask_base_info_2.setText(tempCase.getThetime());
//		et_survey_ask_base_info_3.setText(tempCase.getAddress());
		et_survey_ask_base_info_4.setText(Config.name);
//		
//		et_survey_ask_base_info_5.setText(xiandingTimeStr);
//		et_survey_ask_base_info_6.setText(Config.timebyYMDHMS());
//		et_survey_ask_base_info_7.setText(Config.user.getBranch_name());
//		et_survey_ask_base_info_8.setText(Config.user.getBranch_address());
//		et_survey_ask_base_info_9.setText(Config.user.getBranch_phone());
//		et_survey_ask_base_info_10.setText("");
//		et_survey_ask_base_info_11.setText("");
//		et_survey_ask_base_info_12.setText("");
//		et_survey_ask_base_info_13.setText("");
//		et_survey_ask_base_info_14.setText("");
		
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
						null, Msg.PROGRAM_EXIT, "����", "����������ʧ��,���Ժ�����", 0, "");
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
				myHandler, Msg.ACTIVITY_EXIT, "��ʾ", "�ϱ������ɹ�", 0, "");
				break;
			case Msg.REQUEST_ERROR:
				Dialog.showDialog(Dialog.OK, ActivitySurveyAskRecordBaseInfo.this,
						null, 0, "��ʾ", "�ϱ���������", 0, "");
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
		Config.otherTabBdContentSubmit = getFormContentWhenPause();//�ϱ���
		Config.otherTabBdContent = getFormContent();//pos��ӡ
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
	 * ������رս�����
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
		content.append(tempform.getFormName()).append("_");
//		content.append(Config.posFormTitle +"ִ�Ǵ���[").append(String.valueOf(year)).append("]  \r\nNo.")
//		.append(tempCase.getLsh()).append("_");
		
		content
		.append("���ɣ� ").append(et_survey_ask_base_info_1.getText().toString().trim())
		.append("ʱ�䣺 ").append(et_survey_ask_base_info_2.getText().toString().trim())
		.append("�ص㣺 ").append(et_survey_ask_base_info_3.getText().toString().trim())
		.append("�����ˣ�").append(et_survey_ask_base_info_4.getText().toString().trim())
		.append("��¼�ˣ�").append(et_survey_ask_base_info_5.getText().toString().trim())
		.append("�������ˣ� ").append(et_survey_ask_base_info_6.getText().toString().trim())
		.append("�Ա� ").append(et_survey_ask_base_info_7.getText().toString().trim())
		.append("�������£� ").append(et_survey_ask_base_info_8.getText().toString().trim())
		.append("����֤���� �� ").append(et_survey_ask_base_info_9.getText().toString().trim())
		.append("ס���� ").append(et_survey_ask_base_info_10.getText().toString().trim())
		.append("�绰�� ").append(et_survey_ask_base_info_11.getText().toString().trim())
		.append("������λ �� ").append(et_survey_ask_base_info_12.getText().toString().trim())
		.append("ְ�� ").append(et_survey_ask_base_info_13.getText().toString().trim())
		.append("�ʱࣺ ").append(et_survey_ask_base_info_14.getText().toString().trim());
		
		content.append("��֪�� �����ǳ����г��й�������ִ���ֵ�ִ����Ա���������ǵġ�����ִ��֤�� "
				+ " ����ʾ֤������ִ��֤��Ϊ              ��          ��������ǰ "
				+ "�����е��顣���ݡ��л����񹲺͹��������������ȷ��ɹ涨����ִ����Ա�� "
				+ "�����˻�ִ��֤�������ݲ���������Ȩ�ܾ����飬��ִ����Ա�밸����ֱ���� "
				+ "����ϵ����Ҳ��Ȩ����ִ����Ա�رܡ�ͬʱ��Ӧ��ʵ�ṩ�й����ϡ��ش�ѯ�ʣ�  "
				+ "������ٳ�����ܾ������ӵ��飬������׷���������Ρ�����������ǵ���ѯ  "
				+ " �ʡ����Ƿ�������ˣ�\r\n �� ���������\r\n ");
		content.append("�ʣ�\r\n");
		if (!Config.otherTabBdContent.equals("")){
			content.append(Config.otherTabBdContent).append("\r\n");
		}
		content.append( "�������ˣ�");
		
		content.append("_");
		content.append("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" + String.valueOf(year)).append(" �� ");
		content.append(String.valueOf(month)).append(" �� ");
		content.append(String.valueOf(day)).append(" ��");
		
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
		.append(et_survey_ask_base_info_13.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_14.getText().toString().trim());
		
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
		.append(et_survey_ask_base_info_13.getText().toString().trim()).append("_")
		.append(et_survey_ask_base_info_14.getText().toString().trim());
		
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
				bd_id = String.valueOf(Config.currentFormList.get(Config.bdPositionId).getId());//ע��
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
//					"��ʾ", "����Ϊ��!", 0, "");
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
		//����ϱ��ɹ�ˢ���б�  �ȴ������� ��kill ��ǰactivity
	}
}