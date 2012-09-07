package com.kinview.zft.casehandle;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.Spanned;
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

public class ActivityFormProvide extends Activity {

	private LayoutInflater inflater;
	private RelativeLayout currentFormLayout;
//	public int position = -1;
	private TextView reportCase;
	private TextView posPrint;
	private RelativeLayout bottomBarLayout;
	//��һ
	private EditText et_notification_nuit;
	private EditText et_illegal_location;
	private EditText et_illegal_behavior;
	private EditText et_limited_date;
	
//	private TextView tv_form_title_all;
	private TextView tv_form_title;
	private TextView tv_notification_unit;
	private TextView tv_illegal_location;
	private TextView tv_illegal_behavior;
	private TextView tv_limited_date;
	
	private TextView tv_form_title_all;//�ܵ� ����
	
//	private ThreadGetCaseInfo threadGetCaseInfo;
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
		
		setContentView(R.layout.form_provide_legal_procedures);
		
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
				
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormProvide.this, myHandler, Msg.PROMPT_PRINT,
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
		lockEditText(et_notification_nuit);
		lockEditText(et_illegal_location);
		lockEditText(et_illegal_behavior);
		lockEditText(et_limited_date);
	}
	//���ϱ����� ������Ϣ�����޸�
	private void lockEditText(EditText et){
		et.setEnabled(false);
		et.setFocusable(false);
//		et.setFilters(new InputFilter[]{new InputFilter() {
//			
//			@Override
//			public CharSequence filter(CharSequence source, int start, int end,
//					Spanned dest, int dstart, int dend) {
//				
//				return source.length() < 1 ? source.subSequence(dstart, dend): "";
//			}
//		}});

	}
	
	//��ʾ������ϸ��Ϣ
	private void showCaseInfo(){
		//�ϱ�����
//		if (!Config.currentFormList.get(Config.bdPositionId).getState().equals("")){
//			reportCase.setVisibility(View.GONE);
//			setEditTextLock();
//			return;
//		}
//		Log.i("ZFTSP", "Config.casePositionId: " + Config.casePositionId + " Config.casePositionId: " + Config.casePositionId);
		if (Config.currentNewCaseList.get(0).getOrganise().equals("") ||
				Config.currentNewCaseList.get(0).getOrganise() == null){
			et_notification_nuit.setText(Config.currentNewCaseList.get(0).getPerson());
		}else {
			et_notification_nuit.setText(Config.currentNewCaseList.get(0).getOrganise());
		}
		       
		et_illegal_location.setText(Config.currentNewCaseList.get(0).getAddress());
		et_illegal_behavior.setText(Config.currentNewCaseList.get(0).getCaseWeiFaXW());
//		et_limited_date.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		et_limited_date.setText("3");
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
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormProvide.this,
						null, Msg.PROGRAM_EXIT, "����", "����������ʧ��,���Ժ�����", 0, "");
				break;
			case Msg.PROGRAM_EXIT:
				ThreadExit thread = new ThreadExit(ActivityFormProvide.this);
				thread.showProcess();
				break;
			case Msg.TITLE_PROGRESS_START:
				setTitleProgress(true);
				break;
			case Msg.TITLE_PROGRESS_STOP:
				setTitleProgress(false);
				break;
			case Msg.ONBACK:
				it = new Intent(ActivityFormProvide.this,ActivityFormList.class);
				b = new Bundle ();
				b.putInt("type",Msg.REQUEST_REPEAT);
				it.putExtras(b);
				setResult(0, it);
				finish();
				break;
			case Msg.REQUEST_SUCCESS:
				Dialog.showDialog(Dialog.OK, ActivityFormProvide.this,
				myHandler, Msg.ACTIVITY_EXIT, "��ʾ", "�ϱ����ɹ�", 0, "");
				break;
			case Msg.REQUEST_ERROR:
				Dialog.showDialog(Dialog.OK, ActivityFormProvide.this,
						null, 0, "��ʾ", "�ϱ�������", 0, "");
				break;
			case Msg.ACTIVITY_EXIT:
				it = new Intent(ActivityFormProvide.this,ActivityFormList.class);
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
		tv_form_title_all = (TextView)this.findViewById(R.id.form_title_all);
		tv_form_title = (TextView)this.findViewById(R.id.form_title);
		tv_notification_unit = (TextView)this.findViewById(R.id.notification_unit);
		tv_illegal_location = (TextView)this.findViewById(R.id.illegal_location);
		tv_illegal_behavior = (TextView)this.findViewById(R.id.illegal_behavior);
		tv_limited_date = (TextView)this.findViewById(R.id.limited_date);
		
		et_notification_nuit = (EditText)this.findViewById(R.id.notification_unit_txt);
		et_illegal_location = (EditText)this.findViewById(R.id.illegal_location_txt);
		et_illegal_behavior = (EditText)this.findViewById(R.id.illegal_behavior_txt);
		et_limited_date = (EditText)this.findViewById(R.id.limited_date_txt);
		
		tv_form_title_all.setText(Config.cgUnitName);
		
//		tv_form_title.setText(getResources().getString(R.string.form_title_1_name));
	}
	
	
	private String getFormContent(){
		NewCase tempCase = Config.currentNewCaseList.get(0);
		Form tempform = Config.currentFormList.get(Config.bdPositionId);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		//ʱ���ʽ��
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");
//		String dateStr = "";
//		try {
//			Date date = sdf.parse(tempCase.getThetime());
//			dateStr = sdf.format(date);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		int type = 0;//0:��֯ 1�� ����
		if (tempCase.getOrganise() == null || tempCase.getOrganise().equals("")){
			if (tempCase.getPerson() != null){
				type = 1;
			}
		}else {
			type = 0;
		}
		String str[] =null;
		if (Config.cityTag.equals("czs")){
			str = tempCase.getThetime().split("-");
		}else {
			str = tempCase.getThetime().split("/");
		}
		String year1 = "";
		String month1 = "";
		String day1 = "";
		String theTime = "";
		if (str.length > 2){
			year1 = str[0];
			month1 = str[1];
			day1 = str[2].substring(0, 2);
			theTime = year1 + "��"+month1 + "��"+day1 + "��";
		}else {
			theTime = tempCase.getThetime();
		}
		
		StringBuffer content = new StringBuffer();
		if (Config.cityTag.equals("czjts")){//��̳��
			content.append(tv_form_title_all.getText().toString().trim()).append("\r\n");
			content.append(tv_form_title.getText().toString().trim()).append("_");
//			content.append(tempform.getFormName()).append("_");
			content.append(Config.posFormTitle +"ִͨ��[").append(String.valueOf(year)).append("]  \r\nNo.")
			.append(tempCase.getLsh()).append("_");
			content.append(et_notification_nuit.getText().toString().trim()).append(":\r\n\r\n");
			content.append("    ���飬�㣨��λ����  ").append(theTime)
			.append("��").append(et_illegal_location.getText().toString().trim())
			.append("������").append(et_illegal_behavior.getText().toString().trim())
			.append("����Ϊ�����㣨��λ���ֳ�δ���ṩ��غϷ�������������Υ��")
			.append(tempCase.getFazhe().replaceAll(" ", "")).append("���йع涨���������㣨��λ������ֹͣ������Ϊ�������յ���֪֮ͨ����3������");
			
			if (Config.cityTag.equals("cztnq") || Config.cityTag.equals("czxbq") || Config.cityTag.equals("czwjq")
					|| Config.cityTag.equals("czzlq") || Config.cityTag.equals("czqsyq")){
				content.append(Config.cgUnitNameZhiDui);//����
			}else {
				if (Config.cityTag.equals("czs")){
					content.append(Config.cgUnitName);//����
				}else if (Config.cityTag.equals("czjts")){
					content.append("��̳�г��й�������ִ�����" + Config.user.getBranch_name());//����
				}else {
					content.append(Config.user.getBranch_name());//����
				}
				
			}
			content.append("����ַ��");
			content.append(Config.user.getBranch_address());
			content.append("����ϵ�绰��");
			content.append(Config.user.getBranch_phone());
			content.append("���ṩ������Ϊ����غϷ����������ڲ��ṩ�ģ��Ҿֽ������鴦��").append("_");
			//ִ����Ա
			content.append("������ǩ��:\r\n\r\n\r\n");
			content.append("�绰:\r\n\r\n\r\n");
			content.append("ִ����Աǩ��:\r\n\r\n\r\n");
			content.append("��֤��ǩ��:\r\n\r\n\r\n").append("_");
			
//			content.append("ִ����Ա:  ").append("_");
			
			//������
			content.append("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" + String.valueOf(year)).append(" �� ");
			content.append(String.valueOf(month)).append(" �� ");
			content.append(String.valueOf(day)).append(" ��");
		}else {
			content.append(tv_form_title_all.getText().toString().trim()).append("\r\n");
			content.append(tv_form_title.getText().toString().trim()).append("_");
//			content.append(tempform.getFormName()).append("_");
			content.append(Config.posFormTitle +"ִͨ��[").append(String.valueOf(year)).append("]  \r\nNo.")
			.append(tempCase.getLsh()).append("_");
			content.append(et_notification_nuit.getText().toString().trim()).append(":\r\n\r\n");
			content.append("    ���飬�㣨��λ����  ").append(theTime)
			.append("��").append(et_illegal_location.getText().toString().trim())
			.append("����").append(et_illegal_behavior.getText().toString().trim())
			.append("����Ϊ���ֳ�δ���ṩ��غϷ�������������Υ��")
			.append(tempCase.getFazhe().replaceAll(" ", "").replaceAll("\n", "").trim()).append("���йع涨���������㣨��λ������ֹͣ������Ϊ�������յ���֪֮ͨ����3������");
			
			if (Config.cityTag.equals("cztnq") || Config.cityTag.equals("czxbq") || Config.cityTag.equals("czwjq")
					|| Config.cityTag.equals("czzlq") || Config.cityTag.equals("czqsyq")){
				content.append(Config.cgUnitNameZhiDui);//����
			}else if (Config.cityTag.equals("czs")){
				content.append(Config.cgUnitName);//����
			}else {
				content.append(Config.user.getBranch_name());//����
			}
			content.append("����ַ��");
			content.append(Config.user.getBranch_address());
			content.append("����ϵ�绰��");
			content.append(Config.user.getBranch_phone());
			content.append("���ṩ������Ϊ����غϷ����������ڲ��ṩ�ģ������޺Ϸ��������Ҿֽ������鴦��").append("_");
			//ִ����Ա
			content.append("������ǩ��:\r\n\r\n\r\n");
			content.append("�绰:\r\n\r\n\r\n");
			content.append("ִ����Աǩ��:\r\n\r\n\r\n");
			content.append("��֤��ǩ��:\r\n\r\n\r\n").append("_");
			
//			content.append("ִ����Ա:  ").append("_");
			
			//������
			content.append("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" + String.valueOf(year)).append(" �� ");
			content.append(String.valueOf(month)).append(" �� ");
			content.append(String.valueOf(day)).append(" ��");
		}
		
		return content.toString();
//		return Html.fromHtml(content.toString()).toString();
	}
	
	private String getFormContent2(){
		NewCase tempCase = Config.currentNewCaseList.get(0);
		Form tempfrom = Config.currentFormList.get(Config.bdPositionId);
//		Case tempC = Config.currentCaseList.get(position);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		StringBuffer content = new StringBuffer();
		
		content.append(String.valueOf(year)).append("_");//��
		if (tempCase.getLsh() == null || tempCase.getLsh().equals("")){
			content.append(" ").append("_");
		}else {
			content.append(tempCase.getLsh()).append("_");//��ˮ��
		}
		//��֯�� ����
		String danwei = "";
		int type = 0;//0:��֯ 1�� ����
		if (tempCase.getOrganise() == null || tempCase.getOrganise().equals("")){
			if (tempCase.getPerson() != null){
//				danwei = tempCase.getPerson();
				type = 1;
			}
		}else {
//			danwei = tempCase.getOrganise();
			type = 0;
		}
//		content.append(danwei).append("_");
		
		content.append(et_notification_nuit.getText().toString().trim()).append("_");
		//����ʱ��
		if (tempCase.getThetime() == null || tempCase.getThetime().equals("")){
			content.append(" ").append("_");
		}else {
			content.append(tempCase.getThetime()).append("_");//
		}
		
		content.append(et_illegal_location.getText().toString().trim()).append("_")
		.append(et_illegal_behavior.getText().toString().trim()).append("_");
//		.append(et_limited_date.getText().toString().trim());
		//�йع涨
		if (tempCase.getFazhe() == null || tempCase.getFazhe().equals("")){
			content.append(" ").append("_");
		}else {
			content.append(tempCase.getFazhe()).append("_");//
		}
//		//��ַ
		content.append(Config.user.getBranch_address()).append("_");
//		if (type == 0){
//			if (tempCase.getOadd() == null || tempCase.getOadd().equals("")){
//				content.append(tempCase.getOadd()).append("_");
//			}else {
//				content.append(" ").append("_");
//			}
//		}else if (type == 1){
//			if (tempCase.getPadd() == null || tempCase.getPadd().equals("")){
//				content.append(tempCase.getPadd()).append("_");
//			}else {
//				content.append(" ").append("_");
//			}
//		}
		
		//��ϵ�绰
		content.append(Config.user.getBranch_phone()).append("_");
//		if (type == 0){
//			if (tempCase.getOtel() == null || tempCase.getOtel().equals("")){
//				content.append(tempCase.getOtel()).append("_");
//			}else {
//				content.append(" ").append("_");
//			}
//		}else if (type == 1){
//			if (tempCase.getPtel() == null || tempCase.getPtel().equals("")){
//				content.append(tempCase.getPtel()).append("_");
//			}else {
//				content.append(" ").append("_");
//			}
//		}
		
		//ִ����Ա
		content.append(Config.user.getName()).append("_");
		//������
		content.append(String.valueOf(year)).append("_");
		content.append(String.valueOf(month)).append("_");
		content.append(String.valueOf(day));
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
			Intent it  = new Intent(ActivityFormProvide.this, ActivityPosPrint.class);
			Bundle bundle = new Bundle();
			bundle.putInt("position", Config.bdPositionId);
			bundle.putString("content", getFormContent());
			it.putExtras(bundle);
			startActivityForResult(it, Msg.FORM_REQUEST_SUCCESS);
		}
	}
	
	private boolean checkInputLegal(){
		if (et_notification_nuit.getText().toString().trim().equals("")){
			Dialog.showDialog(Dialog.OK, this, null, 0,
					"��ʾ", "֪ͨ��λ ����Ϊ��!", 0, "");
			return false;
		}else if (et_illegal_location.getText().toString().trim().equals("")){
			Dialog.showDialog(Dialog.OK, this, null, 0,
					"��ʾ", "Υ���ص� ����Ϊ��!", 0, "");
			return false;
		}else if (et_illegal_behavior.getText().toString().trim().equals("")){
			Dialog.showDialog(Dialog.OK, this, null, 0,
					"��ʾ", "Υ����Ϊ ����Ϊ��!", 0, "");
			return false;
		}else if (et_limited_date.getText().toString().trim().equals("")){
			Dialog.showDialog(Dialog.OK, this, null, 0,
					"��ʾ", "�޶����� ����Ϊ��!", 0, "");
			return false;
		}else {
			return true;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//����ϱ��ɹ�ˢ���б�  �ȴ������� ��kill ��ǰactivity
	}
}
