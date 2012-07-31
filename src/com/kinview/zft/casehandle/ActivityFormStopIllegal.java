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

public class ActivityFormStopIllegal extends Activity {
	private LayoutInflater inflater;
	private RelativeLayout currentFormLayout;
//	public int position = -1;
	private TextView reportCase;
	private TextView posPrint;
	private RelativeLayout bottomBarLayout;
	
	//��
	private TextView tv_form_title_all_5;
	private TextView tv_form_title_5;
	private TextView tv_stop_illeage_1;
	private TextView tv_stop_illeage_2;
	private TextView tv_stop_illeage_3;
	private TextView tv_stop_illeage_4;
	private TextView tv_stop_illeage_5;
//	private TextView tv_stop_illeage_6;
	
	private EditText et_stop_illeage_1;
	private EditText et_stop_illeage_2;
	private EditText et_stop_illeage_3;
	private EditText et_stop_illeage_4;
	private EditText et_stop_illeage_5;
//	private EditText et_stop_illeage_6;
	
//	private String formContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		inflater = LayoutInflater.from(this);
		Intent intent = this.getIntent();
//		position = intent.getIntExtra("position", 0);
		
		setContentView(R.layout.form_stop_illeage);
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
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormStopIllegal.this, myHandler, Msg.PROMPT_PRINT,
						"��ʾ", "�����ӡ�ȴ�ӡ���ϱ����޷���ӡ,�Ƿ�����ϱ���", 0, "");
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
		lockEditText(et_stop_illeage_1);
		lockEditText(et_stop_illeage_2);
		lockEditText(et_stop_illeage_3);
		lockEditText(et_stop_illeage_4);
		lockEditText(et_stop_illeage_5);
//		lockEditText(et_stop_illeage_6);
		
	}
	//���ϱ����� ������Ϣ�����޸�
	private void lockEditText(EditText et){
		et.setEnabled(false);
		et.setFocusable(false);
	}
	private String xiandingTimeStr = "";//�޶�ʱ���string
	//��ʾ������ϸ��Ϣ
	private void showCaseInfo(){
		Log.i("ZFTSP", String.valueOf(Config.currentNewCaseList.toString()));
		if (Config.currentNewCaseList.get(0).getOrganise().equals("") ||
				Config.currentNewCaseList.get(0).getOrganise() == null){
			et_stop_illeage_1.setText(Config.currentNewCaseList.get(0).getPerson());
		}else {
			et_stop_illeage_1.setText(Config.currentNewCaseList.get(0).getOrganise());
		}
//		et_stop_illeage_1.setText(Config.currentNewCaseList.get(0).getOrganise());
		et_stop_illeage_2.setText(Config.currentNewCaseList.get(0).getAddress());
		et_stop_illeage_3.setText(Config.currentNewCaseList.get(0).getIntro());//Υ������
		xiandingTimeStr = Config.getXiandingTimeString(Config.currentNewCaseList.get(0).getThetime());
		et_stop_illeage_4.setText(Config.currentNewCaseList.get(0).getYiju());//�涨
//		et_stop_illeage_5.setText(Config.name);//Я��֤��
//		et_stop_illeage_6.setText(Config.user.getBranch_phone());//(����)�绰
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
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormStopIllegal.this,
						null, Msg.PROGRAM_EXIT, "����", "����������ʧ��,���Ժ�����", 0, "");
				break;
			case Msg.PROGRAM_EXIT:
				ThreadExit thread = new ThreadExit(ActivityFormStopIllegal.this);
				thread.showProcess();
				break;
			case Msg.TITLE_PROGRESS_START:
				setTitleProgress(true);
				break;
			case Msg.TITLE_PROGRESS_STOP:
				setTitleProgress(false);
				break;
			case Msg.ONBACK:
				it = new Intent(ActivityFormStopIllegal.this,ActivityFormList.class);
				b = new Bundle ();
				b.putInt("type",Msg.REQUEST_REPEAT);
				it.putExtras(b);
				setResult(0, it);
				finish();
				break;
			case Msg.REQUEST_SUCCESS:
				Dialog.showDialog(Dialog.OK, ActivityFormStopIllegal.this,
				myHandler, Msg.ACTIVITY_EXIT, "��ʾ", "�ϱ����ɹ�", 0, "");
				break;
			case Msg.REQUEST_ERROR:
				Dialog.showDialog(Dialog.OK, ActivityFormStopIllegal.this,
						null, 0, "��ʾ", "�ϱ�������", 0, "");
				break;
			case Msg.ACTIVITY_EXIT:
				it = new Intent(ActivityFormStopIllegal.this,ActivityFormList.class);
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
	 * ������رս�����
	 */
	public void setTitleProgress(boolean flag){
		setProgressBarIndeterminateVisibility(flag);
	}
	
	private void init(){
		tv_form_title_all_5 = (TextView)this.findViewById(R.id.form_title_all_8);
		tv_form_title_5 = (TextView)this.findViewById(R.id.form_title_8);
		tv_stop_illeage_1 = (TextView)this.findViewById(R.id.stop_illeage_1_name);
		tv_stop_illeage_2 = (TextView)this.findViewById(R.id.stop_illeage_2_name);
		tv_stop_illeage_3 = (TextView)this.findViewById(R.id.stop_illeage_3_name);
		tv_stop_illeage_4 = (TextView)this.findViewById(R.id.stop_illeage_4_name);
		tv_stop_illeage_5 = (TextView)this.findViewById(R.id.stop_illeage_5_name);
//		tv_stop_illeage_6 = (TextView)this.findViewById(R.id.stop_illeage_6_name);
		
		et_stop_illeage_1 = (EditText)this.findViewById(R.id.stop_illeage_1_txt);
		et_stop_illeage_2 = (EditText)this.findViewById(R.id.stop_illeage_2_txt);
		et_stop_illeage_3 = (EditText)this.findViewById(R.id.stop_illeage_3_txt);
		et_stop_illeage_4 = (EditText)this.findViewById(R.id.stop_illeage_4_txt);
		et_stop_illeage_5 = (EditText)this.findViewById(R.id.stop_illeage_5_txt);
//		et_stop_illeage_6 = (EditText)this.findViewById(R.id.stop_illeage_6_txt);
		
		tv_form_title_all_5.setText(Config.cgUnitName);
	}
	
	private String getFormContent(){
		NewCase tempCase = Config.currentNewCaseList.get(0);
		Form tempform = Config.currentFormList.get(Config.bdPositionId);
		Calendar calendar = Calendar.getInstance();
		int year1 = calendar.get(Calendar.YEAR);
		int month1 = calendar.get(Calendar.MONTH) + 1;
		int day1 = calendar.get(Calendar.DAY_OF_MONTH);
		
		String str[] = xiandingTimeStr.split("/");
		String year = "";
		String month = "";
		String day = "";
		if (str.length > 2){
			year = str[0];
			month = str[1];
			day = str[2].substring(0, 2);
		}else {
			
		}
		
		StringBuffer content = new StringBuffer();
		content.append(tv_form_title_all_5.getText().toString().trim()).append("\r\n");
		content.append(tempform.getFormName()).append("_");
		content.append(Config.posFormTitle +"ִͣͨ��[").append(String.valueOf(year1)).append("]  \r\nNo.")
		.append(tempCase.getLsh()).append("_");
		content.append("Υ�����£���λ���ˣ���").append(et_stop_illeage_1.getText().toString().trim()).append("\r\n");
		content.append("Υ�����£��ص㣺").append(et_stop_illeage_2.getText().toString().trim()).append("\r\n");
		content.append("Υ�����£����ݣ�").append(et_stop_illeage_3.getText().toString().trim()).append("\r\n");
		content.append("���ݣ�").append(et_stop_illeage_4.getText().toString().trim());
		content.append(" �Ĺ涨�����㣨��λ���ӱ�֪ͨ�������ֹͣΥ�����£���Ϊ����Я��")
		.append(et_stop_illeage_5.getText().toString().trim()).append("\r\n");
		
		content.append("����  "+year +" ��"+month+" ��"+day+" ��ǰ���� ");
//		content.append(Config.user.getBranch_name())
		if (Config.cityTag.equals("cztnq") || Config.cityTag.equals("czxbq") || Config.cityTag.equals("czwjq")
				|| Config.cityTag.equals("czzlq") || Config.cityTag.equals("czqsyq")){
			content.append(Config.cgUnitNameZhiDui);//����
		}else {
			content.append(Config.user.getBranch_name());//����
		}
//		content.append(Config.cgUnitName + Config.cgUnitNameDaDui)
		content.append("(��ַ��"+Config.user.getBranch_address()+"   ��ϵ�绰�� "+Config.user.getBranch_phone()+"  )���ܴ���\r\n\r\n\r\n ");
		
		content.append("_");
		content.append("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" + String.valueOf(year1)).append(" �� ");
		content.append(String.valueOf(month1)).append(" �� ");
		content.append(String.valueOf(day1)).append(" ��");
		
		
		
		//����

		return content.toString();
//		return Html.fromHtml(content.toString()).toString();
	}
	
	private String getFormContent2(){
		NewCase tempCase = Config.currentNewCaseList.get(0);
		Form tempfrom = Config.currentFormList.get(Config.bdPositionId);
		Calendar calendar = Calendar.getInstance();
		int year1 = calendar.get(Calendar.YEAR);
		int month1 = calendar.get(Calendar.MONTH) + 1;
		int day1 = calendar.get(Calendar.DAY_OF_MONTH);
		
		String str[] = xiandingTimeStr.split("/");
		String year = "";
		String month = "";
		String day = "";
		if (str.length > 2){
			year = str[0];
			month = str[1];
			day = str[2].substring(0, 2);
		}else {
			
		}
		
		StringBuffer content = new StringBuffer();
		content.append(String.valueOf(year1)).append("_");//��
		if (tempCase.getLsh() == null || tempCase.getLsh().equals("")){
			content.append(" ").append("_");
		}else {
			content.append(tempCase.getLsh()).append("_");//��ˮ��
		}
		
		
		content
		.append(et_stop_illeage_1.getText().toString().trim()).append("_")
		.append(et_stop_illeage_2.getText().toString().trim()).append("_")
		.append(et_stop_illeage_3.getText().toString().trim()).append("_")
		.append(et_stop_illeage_4.getText().toString().trim()).append("_")
		.append(et_stop_illeage_5.getText().toString().trim()).append("_");
		
		content.append(year).append("_");	
		content.append(month).append("_");
		content.append(day).append("_");
		
//		content.append(Config.user.getBranch_name())
		if (Config.cityTag.equals("cztnq") || Config.cityTag.equals("czxbq") || Config.cityTag.equals("czwjq")
				|| Config.cityTag.equals("czzlq") || Config.cityTag.equals("czqsyq")){
			content.append(Config.cgUnitNameZhiDui);//����
		}else {
			content.append(Config.user.getBranch_name());//����
		}
//		content.append(Config.cgUnitName + Config.cgUnitNameDaDui)
		content.append("_"+Config.user.getBranch_address())
		.append("_"+Config.user.getBranch_phone()).append("_");
		
		content.append(year1).append("_");	
		content.append(month1).append("_");
		content.append(day);
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
			
			Log.i("ZFTSP", "aj_id: " + aj_id +" position: " + Config.bdPositionId + " bd_id: " + bd_id + " bd_name: " + bd_name + " param: " + param);
				
			if (Config.threadSubmitBd == null){
				Config.threadSubmitBd = new ThreadSubmitBd();
				Config.threadSubmitBd.showProcess(this, myHandler, aj_id, bd_id, bd_name, param);
			}
		}
	}
	
	private void posPrintProcess() {
		if (checkInputLegal()) {
			Intent it  = new Intent(ActivityFormStopIllegal.this, ActivityPosPrint.class);
			Bundle bundle = new Bundle();
//			bundle.putInt("position", position);
			bundle.putString("content", getFormContent());
			it.putExtras(bundle);
			startActivityForResult(it, Msg.FORM_REQUEST_SUCCESS);
		}
	}
	
	
	private boolean checkInputLegal(){
//		if (et_stop_illeage_1.getText().toString().trim().equals("")){
//			Dialog.showDialog(Dialog.OK, this, null, 0,
//					"��ʾ", "����Ϊ��!", 0, "");
//			return false;
//		}else {
//			return true;
//		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//����ϱ��ɹ�ˢ���б�  �ȴ������� ��kill ��ǰactivity
	}
}
