package com.kinview.zft.casehandle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.kinview.config.Config;
import com.kinview.config.Dialog;
import com.kinview.config.Msg;
import com.kinview.entity.NewCase;
import com.kinview.thread.ThreadExit;
import com.kinview.thread.ThreadGetCaseInfo;
import com.kinview.thread.ThreadSubmitBd;
import com.kinview.util.Form;
import com.kinview.zft.R;
import com.kinview.zft.R.id;
import com.kinview.zft.R.layout;

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

public class ActivityFormSiteRecordSituation extends Activity {
	private LayoutInflater inflater;
	private RelativeLayout currentFormLayout;
//	public int position = -1;
	private TextView reportCase;
	private TextView posPrint;
	private RelativeLayout bottomBarLayout;
	
	//����
	private TextView tv_form_title_all_2;
	private TextView tv_form_title_2;
	private TextView tv_record_conditions_1;
	private TextView tv_record_conditions_2;
	private TextView tv_record_conditions_3;
	private TextView tv_record_conditions_4;
	private TextView tv_record_conditions_5;
	private TextView tv_record_conditions_6;
	
	private EditText et_record_conditions_1;
	private EditText et_record_conditions_2;
	private EditText et_record_conditions_3;
	private EditText et_record_conditions_4;
	private EditText et_record_conditions_5;
	private EditText et_record_conditions_6;
	
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
		
		setContentView(R.layout.form_site_record_situation);
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
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormSiteRecordSituation.this, myHandler, Msg.PROMPT_PRINT,
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
		lockEditText(et_record_conditions_1);
		lockEditText(et_record_conditions_2);
		lockEditText(et_record_conditions_3);
		lockEditText(et_record_conditions_4);
		lockEditText(et_record_conditions_5);
		lockEditText(et_record_conditions_6);
	}
	//���ϱ����� ������Ϣ�����޸�
	private void lockEditText(EditText et){
		et.setEnabled(false);
		et.setFocusable(false);
	}
	
	//��ʾ������ϸ��Ϣ
	private void showCaseInfo(){
		Log.i("ZFTSP", String.valueOf(Config.currentNewCaseList.toString()));
		
		String dateStr = Config.currentNewCaseList.get(0).getThetime() ;
//		String dateStr = "2012/07/1 10:22:44";
		String[] ds = null;
		if (dateStr.indexOf("/") != -1){
			dateStr = dateStr.substring(0, dateStr.lastIndexOf("/")+3);
//			dateStr = dateStr.substring(0, dateStr.indexOf(" ")+1);
			ds = dateStr.split("/");
		}
		
		String year = "";
		String month = "";
		String day = "";
		if (ds != null){
			if (ds.length >2 ){
				year = ds[0];
				month = ds[1];
				day = ds[2];
			}
		}
		
		/**
		 * 1.2012��7��23�ձ���ִ����Ա�ֳ���֤(ִ��֤��:04001344��04008089)Ѳ��ʱ���ָõ������ڳ����б�����·����۪�ǹ��̽�����,���������������ִ�����ʻ,��ɱ�����·��·��Ⱦ. 
		 * 2.���ֳ�����,�õ����˵�·��Ⱦ���Ϊ50ƽ����. 
		 * 3ִ����Ա�ֳ�����ȡ֤�󷢳����ΪNO:2012000542�ĺ˲�֪ͨ��,�ֳ��������ֹͣ������Ϊ����������.
		 */
		et_record_conditions_1.setText("    1."+year + "��" + month + "��" + day + "��"
				+ "��ִ����Ա "+Config.user.getName()+"��"+Config.user.getU_UserName()
				+"��֤�ţ�"+Config.user.getZfzid()+"��"+Config.user.getU_ZfzID()+"��Ѳ����"
				+Config.currentNewCaseList.get(0).getAddress()+" ʱ�����ָõ�����"
				+Config.currentNewCaseList.get(0).getCaseWeiFaXW()+" ��\r\n" +"    2.ִ����Ա��֤��飬�������ֳ�δ���ṩ�κκϷ�����Ч������\r\n" +"    3.ִ����Ա����ȡ֤��");
		et_record_conditions_2.setText("��Ӱ������ʾ��ΪΥ�µص�");
		et_record_conditions_3.setText(Config.timebyYMDHMS());
		et_record_conditions_4.setText("����");
		et_record_conditions_5.setText("����ִ����Ա  "+Config.name);
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
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormSiteRecordSituation.this,
						null, Msg.PROGRAM_EXIT, "����", "����������ʧ��,���Ժ�����", 0, "");
				break;
			case Msg.PROGRAM_EXIT:
				ThreadExit thread = new ThreadExit(ActivityFormSiteRecordSituation.this);
				thread.showProcess();
				break;
			case Msg.TITLE_PROGRESS_START:
				setTitleProgress(true);
				break;
			case Msg.TITLE_PROGRESS_STOP:
				setTitleProgress(false);
				break;
			case Msg.ONBACK:
				it = new Intent(ActivityFormSiteRecordSituation.this,ActivityFormList.class);
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
				Dialog.showDialog(Dialog.OK, ActivityFormSiteRecordSituation.this,
				myHandler, Msg.ACTIVITY_EXIT, "��ʾ", "�ϱ����ɹ�", 0, "");
				break;
			case Msg.REQUEST_ERROR:
				Dialog.showDialog(Dialog.OK, ActivityFormSiteRecordSituation.this,
						null, 0, "��ʾ", "�ϱ�������", 0, "");
				break;
			case Msg.ACTIVITY_EXIT:
				it = new Intent(ActivityFormSiteRecordSituation.this,ActivityFormList.class);
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
	 * ������رս�����
	 */
	public void setTitleProgress(boolean flag){
		setProgressBarIndeterminateVisibility(flag);
	}
	
	private void init(){
		tv_form_title_all_2 = (TextView)this.findViewById(R.id.form_title_all_2_2);
		tv_form_title_2 = (TextView)this.findViewById(R.id.form_title_2_2);
		tv_record_conditions_1 = (TextView)this.findViewById(R.id.record_conditions_1_name);
		tv_record_conditions_2 = (TextView)this.findViewById(R.id.record_conditions_2_name);
		tv_record_conditions_3 = (TextView)this.findViewById(R.id.record_conditions_3_name);
		tv_record_conditions_4 = (TextView)this.findViewById(R.id.record_conditions_4_name);
		tv_record_conditions_5 = (TextView)this.findViewById(R.id.record_conditions_5_name);
		tv_record_conditions_6 = (TextView)this.findViewById(R.id.record_conditions_6_name);
		
		et_record_conditions_1 = (EditText)this.findViewById(R.id.record_conditions_1_name_txt);
		et_record_conditions_2 = (EditText)this.findViewById(R.id.record_conditions_2_name_txt);
		et_record_conditions_3 = (EditText)this.findViewById(R.id.record_conditions_3_name_txt);
		et_record_conditions_4 = (EditText)this.findViewById(R.id.record_conditions_4_name_txt);
		et_record_conditions_5 = (EditText)this.findViewById(R.id.record_conditions_5_name_txt);
		et_record_conditions_6 = (EditText)this.findViewById(R.id.record_conditions_6_name_txt);
		
		tv_form_title_all_2.setText(Config.cgUnitName);
	}
	
	private String getFormContent(){
		NewCase tempCase = Config.currentNewCaseList.get(0);
		Form tempform = Config.currentFormList.get(Config.bdPositionId);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		int type = 0;//0:��֯ 1�� ����
		if (tempCase.getOrganise() == null || tempCase.getOrganise().equals("")){
			if (tempCase.getPerson() != null){
				type = 1;
			}
		}else {
			type = 0;
		}
		StringBuffer content = new StringBuffer();
		content.append(tv_form_title_all_2.getText().toString().trim()).append("\r\n");
		content.append(tv_form_title_2.getText().toString().trim()).append("_");
//		content.append(tempform.getFormName()).append("_");
//		content.append("�����ִͨ��[").append(String.valueOf(year)).append("]  \r\nNo.")
//		.append(tempCase.getLsh()).append("_");
		content.append(" _");
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
		
		content.append("�ֳ�����ʾ��ͼ:\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");//15��
		
		content.append(tv_record_conditions_1.getText().toString().trim().replaceAll("_", "")).append("\r\n")
		.append("    "+et_record_conditions_1.getText().toString().trim().replaceAll("_", "")).append("\r\n")
		.append(tv_record_conditions_2.getText().toString().trim().replaceAll("_", ""))
		.append(et_record_conditions_2.getText().toString().trim().replaceAll("_", "")).append("\r\n")
		.append(tv_record_conditions_3.getText().toString().trim().replaceAll("_", ""))
		.append(et_record_conditions_3.getText().toString().trim().replaceAll("_", "")).append("\r\n")
		.append(tv_record_conditions_4.getText().toString().trim().replaceAll("_", ""))
		.append(et_record_conditions_4.getText().toString().trim().replaceAll("_", "")).append("\r\n")
		.append(tv_record_conditions_5.getText().toString().trim().replaceAll("_", ""))
		.append(et_record_conditions_5.getText().toString().trim().replaceAll("_", "")).append("\r\n")
		.append(tv_record_conditions_6.getText().toString().trim().replaceAll("_", ""))
		.append(et_record_conditions_6.getText().toString().trim().replaceAll("_", "")).append("\r\n");
		
		content.append("������ǩ��:").append("\r\n\r\n\r\n");
		content.append("��֤��ǩ��:").append("\r\n\r\n\r\n");
		content.append("�����Աǩ��:").append("_").append("\r\n\r\n\r\n");
		
		//������
		content.append("\r\n\r\n\r\n\r\n\r\n\r\n" + String.valueOf(year)).append(" �� ");
		content.append(String.valueOf(month)).append(" �� ");
		content.append(String.valueOf(day)).append(" ��");
		return content.toString();
//		return Html.fromHtml(content.toString()).toString();
	}
	
	private String getFormContentWhenPause(){
		StringBuffer content = new StringBuffer();
		content.append(tv_record_conditions_1.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_record_conditions_1.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_record_conditions_2.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_record_conditions_2.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_record_conditions_3.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_record_conditions_3.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_record_conditions_4.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_record_conditions_4.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_record_conditions_5.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_record_conditions_5.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(tv_record_conditions_6.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_record_conditions_6.getText().toString().trim().replaceAll("_", "")).append("_");
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
		
		int type = 0;//0:��֯ 1�� ����
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
		
//		content.append("�ֳ�����ʾ��ͼ:\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");//15��
		
		content
		.append(et_record_conditions_1.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_record_conditions_2.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_record_conditions_3.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_record_conditions_4.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_record_conditions_5.getText().toString().trim().replaceAll("_", "")).append("_")
		.append(et_record_conditions_6.getText().toString().trim().replaceAll("_", ""));
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
			Intent it  = new Intent(ActivityFormSiteRecordSituation.this, ActivityPosPrint.class);
			Bundle bundle = new Bundle();
			bundle.putInt("position", Config.bdPositionId);
			bundle.putString("content", getFormContent());
			it.putExtras(bundle);
			startActivityForResult(it, Msg.FORM_REQUEST_SUCCESS);
		}
	}
	
	
	
	private boolean checkInputLegal(){
//		if (et_record_conditions_1.getText().toString().trim().equals("")){
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
