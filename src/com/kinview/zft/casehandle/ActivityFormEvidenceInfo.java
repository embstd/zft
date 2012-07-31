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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kinview.config.Config;
import com.kinview.config.Dialog;
import com.kinview.config.Msg;
import com.kinview.entity.EvidenceInfo;
import com.kinview.entity.NewCase;
import com.kinview.thread.ThreadExit;
import com.kinview.thread.ThreadSubmitBd;
import com.kinview.util.Form;
import com.kinview.zft.R;

public class ActivityFormEvidenceInfo extends Activity {
	private LayoutInflater inflater;
	private RelativeLayout currentFormLayout;
	public int position = -1;
	private TextView reportCase;
	private TextView posPrint;
	private RelativeLayout bottomBarLayout;
	
	//表单
	private TextView tv_form_title_all_4;
	private TextView tv_form_title_4;
	private TextView tv_evidence_registration_info_1;
	private TextView tv_evidence_registration_info_2;
	private TextView tv_evidence_registration_info_3;
	private TextView tv_evidence_registration_info_4;
	private TextView tv_evidence_registration_info_5;
	private TextView tv_evidence_registration_info_6;
	
	private EditText et_evidence_registration_info_1;
	private EditText et_evidence_registration_info_2;
	private EditText et_evidence_registration_info_3;
	private EditText et_evidence_registration_info_4;
	private EditText et_evidence_registration_info_5;
	private EditText et_evidence_registration_info_6;
	
	private Button saveButton;
	private Button deleteButton;
//	private String formContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		inflater = LayoutInflater.from(this);
		Intent intent = this.getIntent();
		position = intent.getIntExtra("position", 0);
		
		setContentView(R.layout.form_evidence_registration_info);
		init();
		bottomBarLayout = (RelativeLayout)this.findViewById(R.id.form_bottom);
		bottomBarLayout.setVisibility(View.GONE);//目前 先隐藏 
		if (bottomBarLayout == null){
			return;
		}
		reportCase = (TextView)bottomBarLayout.findViewById(R.id.report_case);
		posPrint = (TextView)bottomBarLayout.findViewById(R.id.pos_print);
		reportCase.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormEvidenceInfo.this, myHandler, Msg.PROMPT_PRINT,
						"提示", "如需打印先打印，上报后将无法打印,是否继续上报？", 0, "");

//				reportCaseProcess();
//				Toast.makeText(ActivityForm.this, "hhhhhh", Toast.LENGTH_SHORT);
			}
		});

		posPrint.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				posPrintProcess();
			}
		});
		
//		if (Config.threadGetCaseInfo == null){
//			Config.threadGetCaseInfo = new ThreadGetCaseInfo();
//			Config.threadGetCaseInfo.showProcess(this, myHandler, 0, Config.caseId);
//		}
		
		saveButton = (Button) this.findViewById(R.id.save_form_evidence_info);
		deleteButton = (Button) this.findViewById(R.id.delete_form_evidence_info);
		deleteButton.setVisibility(View.GONE);
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Config.listEvidenceInfo.get(position).setBianhao(String.valueOf(position));
				Config.listEvidenceInfo.get(position).setBianhao(et_evidence_registration_info_1.getText().toString().trim());
				Config.listEvidenceInfo.get(position).setDanwei(et_evidence_registration_info_2.getText().toString().trim());
				Config.listEvidenceInfo.get(position).setShuliang(et_evidence_registration_info_3.getText().toString().trim());
				Config.listEvidenceInfo.get(position).setName(et_evidence_registration_info_4.getText().toString().trim());
				Config.listEvidenceInfo.get(position).setGuige(et_evidence_registration_info_5.getText().toString().trim());
				Config.listEvidenceInfo.get(position).setBeizhu(et_evidence_registration_info_6.getText().toString().trim());
				
				Dialog.showDialog(Dialog.OK, ActivityFormEvidenceInfo.this,
						myHandler, Msg.ACTIVITY_EXIT, "提示", "保存成功", 0, "");
			}
		});
		
		deleteButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Config.listEvidenceInfo.size() != 0){
					if (Config.listEvidenceInfo.get(position) != null){
						Config.listEvidenceInfo.remove(position);
						Dialog.showDialog(Dialog.OK, ActivityFormEvidenceInfo.this,
								myHandler, Msg.ACTIVITY_EXIT, "提示", "删除成功", 0, "");
					}
				}
				
			}
		});
		
		if (Config.listEvidenceInfo.size() != 0){
			if (Config.listEvidenceInfo.get(position) != null){
				deleteButton.setVisibility(View.VISIBLE);
				showCaseInfo();
			}
		}
	}
	

	//显示案件详细信息
	private void showCaseInfo(){
		Log.i("ZFTSP", String.valueOf(Config.currentNewCaseList.toString()));
		et_evidence_registration_info_1.setText(Config.listEvidenceInfo.get(position).getBianhao().toString());
		et_evidence_registration_info_2.setText(Config.listEvidenceInfo.get(position).getDanwei().toString()); 
		et_evidence_registration_info_3.setText(Config.listEvidenceInfo.get(position).getShuliang().toString());
		et_evidence_registration_info_4.setText(Config.listEvidenceInfo.get(position).getName().toString());
		et_evidence_registration_info_5.setText(Config.listEvidenceInfo.get(position).getGuige().toString());
		et_evidence_registration_info_6.setText(Config.listEvidenceInfo.get(position).getBeizhu().toString());
//		et_orgnization.setText(Config.currentNewCaseList.get(0).getOrganise());
//		et_competent.setText(Config.currentNewCaseList.get(0).getFzr());
//		et_competent_post.setText(Config.currentNewCaseList.get(0).getZw());
//		et_competent_address.setText(Config.currentNewCaseList.get(0).getOadd());
//		et_competent_phone.setText(Config.currentNewCaseList.get(0).getOtel());
//		et_citizen.setText(Config.currentNewCaseList.get(0).getPerson());
//		et_citizen_sex.setText(Config.currentNewCaseList.get(0).getSex());
//		et_citizen_age.setText(Config.currentNewCaseList.get(0).getAge());
//		et_citizen_identity_card.setText(Config.currentNewCaseList.get(0).getIdnumber());
//		et_citizen_address.setText(Config.currentNewCaseList.get(0).getPadd());
//		et_citizen_phone.setText(Config.currentNewCaseList.get(0).getPtel());
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
				Dialog.showDialog(Dialog.OKCANCEL, ActivityFormEvidenceInfo.this,
						null, Msg.PROGRAM_EXIT, "连接", "服务器连接失败,请稍后重试", 0, "");
				break;
			case Msg.PROGRAM_EXIT:
				ThreadExit thread = new ThreadExit(ActivityFormEvidenceInfo.this);
				thread.showProcess();
				break;
			case Msg.TITLE_PROGRESS_START:
				setTitleProgress(true);
				break;
			case Msg.TITLE_PROGRESS_STOP:
				setTitleProgress(false);
				break;
			case Msg.ONBACK:
//				it = new Intent(ActivityFormEvidenceInfo.this,ActivityFormEvidenceInfoListView.class);
//				b = new Bundle ();
//				b.putInt("type",Msg.REQUEST_REPEAT);
//				it.putExtras(b);
//				if (getParent() == null){
//					setResult(0, it);
//				}else {
//					getParent().setResult(0, it);
//				}
				finish();
				break;
//			case Msg.REQUEST_SUCCESS:
//				Dialog.showDialog(Dialog.OK, ActivityFormEvidenceInfo.this,
//				myHandler, Msg.ACTIVITY_EXIT, "提示", "上报表单成功", 0, "");
//				break;
//			case Msg.REQUEST_ERROR:
//				Dialog.showDialog(Dialog.OK, ActivityFormEvidenceInfo.this,
//						null, 0, "提示", "上报表单错误", 0, "");
//				break;
			case Msg.ACTIVITY_EXIT:
				it = new Intent(ActivityFormEvidenceInfo.this,ActivityFormEvidenceInfoListView.class);
				b = new Bundle ();
				b.putInt("type",Msg.OPERATE_INFO_SUCCESS);
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
	protected void onPause() {
		super.onPause();
		//暂时用不到
//		Config.otherTabBdContent = getFormContentWhenPause();
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
		tv_form_title_all_4 = (TextView)this.findViewById(R.id.form_title_all_4_2);
		tv_form_title_4 = (TextView)this.findViewById(R.id.form_title_4_2);
		tv_evidence_registration_info_1 = (TextView)this.findViewById(R.id.evidence_registration_info_1_name);
		tv_evidence_registration_info_2 = (TextView)this.findViewById(R.id.evidence_registration_info_2_name);
		tv_evidence_registration_info_3 = (TextView)this.findViewById(R.id.evidence_registration_info_3_name);
		tv_evidence_registration_info_4 = (TextView)this.findViewById(R.id.evidence_registration_info_4_name);
		tv_evidence_registration_info_5 = (TextView)this.findViewById(R.id.evidence_registration_info_5_name);
		tv_evidence_registration_info_6 = (TextView)this.findViewById(R.id.evidence_registration_info_6_name);
		
		et_evidence_registration_info_1 = (EditText)this.findViewById(R.id.evidence_registration_info_1_txt);
		et_evidence_registration_info_2 = (EditText)this.findViewById(R.id.evidence_registration_info_2_txt);
		et_evidence_registration_info_3 = (EditText)this.findViewById(R.id.evidence_registration_info_3_txt);
		et_evidence_registration_info_4 = (EditText)this.findViewById(R.id.evidence_registration_info_4_txt);
		et_evidence_registration_info_5 = (EditText)this.findViewById(R.id.evidence_registration_info_5_txt);
		et_evidence_registration_info_6 = (EditText)this.findViewById(R.id.evidence_registration_info_6_txt);
		
		tv_form_title_all_4.setText(Config.cgUnitName);
		if (Config.cityTag.equals("czjts")){
			tv_form_title_4.setText(getResources().getString(R.string.form_title_18_name));
		}
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
		content.append(tv_form_title_all_4.getText().toString().trim()).append("\r\n");
		content.append(tempform.getFormName()).append("_");
		content.append(Config.posFormTitle +"执登存字[").append(String.valueOf(year)).append("]  \r\nNo.")
		.append(tempCase.getLsh()).append("_");
		content.append(" _");
		
		if (type == 0){
			content.append(et_evidence_registration_info_1.getText().toString().trim())
			.append(et_evidence_registration_info_1.getText().toString().trim()).append("\r\n")
			.append(et_evidence_registration_info_1.getText().toString().trim())
			.append(et_evidence_registration_info_1.getText().toString().trim()).append("\r\n")
			.append(et_evidence_registration_info_1.getText().toString().trim())
			.append(et_evidence_registration_info_1.getText().toString().trim()).append("\r\n");
		}else {
			content.append(et_evidence_registration_info_1.getText().toString().trim())
			.append(et_evidence_registration_info_1.getText().toString().trim()).append("\r\n")
			.append(et_evidence_registration_info_1.getText().toString().trim())
			.append(et_evidence_registration_info_1.getText().toString().trim()).append("\r\n")
			.append(et_evidence_registration_info_1.getText().toString().trim())
			.append(et_evidence_registration_info_1.getText().toString().trim()).append("\r\n");
		}
		content.append("现场勘察示意图:\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");//15行
		String[] otherStr = Config.otherTabBdContent.split("_");
		for (int i =0; i<otherStr.length; i++){
			content.append(otherStr[i]);
			if (i%2 != 0){
				content.append("\r\n");
			}
		}
		
		
		content.append(tv_form_title_all_4.getText().toString().trim()).append("_")
		.append(tv_form_title_4.getText().toString().trim()).append("_")
		.append(tv_evidence_registration_info_1.getText().toString().trim()).append("_")
		.append(et_evidence_registration_info_1.getText().toString().trim()).append("_")
		.append(tv_evidence_registration_info_2.getText().toString().trim()).append("_")
		.append(et_evidence_registration_info_2.getText().toString().trim()).append("_")
		.append(tv_evidence_registration_info_3.getText().toString().trim()).append("_")
		.append(et_evidence_registration_info_3.getText().toString().trim()).append("_")
		.append(tv_evidence_registration_info_4.getText().toString().trim()).append("_")
		.append(et_evidence_registration_info_4.getText().toString().trim()).append("_")
		.append(tv_evidence_registration_info_5.getText().toString().trim()).append("_")
		.append(et_evidence_registration_info_5.getText().toString().trim()).append("_")
		.append(tv_evidence_registration_info_6.getText().toString().trim()).append("_")
		.append(et_evidence_registration_info_6.getText().toString().trim());
		return content.toString();
//		return Html.fromHtml(content.toString()).toString();
	}
	
	private String getFormContentWhenPause(){
		StringBuffer content = new StringBuffer();
		content
		.append(tv_evidence_registration_info_1.getText().toString().trim()).append("_")
		.append(et_evidence_registration_info_1.getText().toString().trim()).append("_")
		.append(tv_evidence_registration_info_2.getText().toString().trim()).append("_")
		.append(et_evidence_registration_info_2.getText().toString().trim()).append("_")
		.append(tv_evidence_registration_info_3.getText().toString().trim()).append("_")
		.append(et_evidence_registration_info_3.getText().toString().trim()).append("_")
		.append(tv_evidence_registration_info_4.getText().toString().trim()).append("_")
		.append(et_evidence_registration_info_4.getText().toString().trim()).append("_")
		.append(tv_evidence_registration_info_5.getText().toString().trim()).append("_")
		.append(et_evidence_registration_info_5.getText().toString().trim()).append("_")
		.append(tv_evidence_registration_info_6.getText().toString().trim()).append("_")
		.append(et_evidence_registration_info_6.getText().toString().trim());
		
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
		
		if (type == 0){
			content
			.append(et_evidence_registration_info_1.getText().toString().trim()).append("_")
			.append(et_evidence_registration_info_2.getText().toString().trim()).append("_")
			.append(et_evidence_registration_info_3.getText().toString().trim()).append("_")
			.append(et_evidence_registration_info_4.getText().toString().trim()).append("_")
			.append(et_evidence_registration_info_5.getText().toString().trim()).append("_");
		}else {
			content
			.append(et_evidence_registration_info_1.getText().toString().trim()).append("_")
			.append(et_evidence_registration_info_2.getText().toString().trim()).append("_")
			.append(et_evidence_registration_info_3.getText().toString().trim()).append("_")
			.append(et_evidence_registration_info_4.getText().toString().trim()).append("_")
			.append(et_evidence_registration_info_5.getText().toString().trim()).append("_");
		}
//		content.append("现场勘察示意图:\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");//15行
		String[] otherStr = Config.otherTabBdContent.split("_");
		for (int i =0; i<otherStr.length; i++){
			if (i%2 != 0){
				content.append(otherStr[i]);
				content.append("_");
			}
		}
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
			Intent it  = new Intent(ActivityFormEvidenceInfo.this, ActivityPosPrint.class);
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
