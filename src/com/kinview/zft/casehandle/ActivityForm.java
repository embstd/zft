package com.kinview.zft.casehandle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kinview.config.Dialog;
import com.kinview.config.Msg;
import com.kinview.util.ESC;
import com.kinview.util.PosPrintUtil;
import com.kinview.zft.R;
import com.kinview.zft.R.id;
import com.kinview.zft.R.layout;

public class ActivityForm extends Activity {

	private LayoutInflater inflater;
	private RelativeLayout currentFormLayout;
	public int position = -1;
	private TextView reportCase;
	private TextView posPrint;
	private RelativeLayout bottomBarLayout;
	//表单一
	private EditText et_notification_nuit;
	private EditText et_illegal_location;
	private EditText et_illegal_behavior;
	private EditText et_limited_date;
	
	private TextView tv_form_title_all;
	private TextView tv_form_title;
	private TextView tv_notification_unit;
	private TextView tv_illegal_location;
	private TextView tv_illegal_behavior;
	private TextView tv_limited_date;
	
	//表单二
	private TextView tv_form_title_all_2;
	private TextView tv_form_title_2;
	private TextView tv_orgnization;
	private TextView tv_competent;
	private TextView tv_competent_post;
	private TextView tv_competent_address;
	private TextView tv_competent_phone;
	private TextView tv_citizen;
	private TextView tv_citizen_sex;
	private TextView tv_citizen_age;
	private TextView tv_citizen_identity_card;
	private TextView tv_citizen_address;
	private TextView tv_citizen_phone;
	
	private EditText et_form_title_all_2;
	private EditText et_form_title_2;
	private EditText et_orgnization;
	private EditText et_competent;
	private EditText et_competent_post;
	private EditText et_competent_address;
	private EditText et_competent_phone;
	private EditText et_citizen;
	private EditText et_citizen_sex;
	private EditText et_citizen_age;
	private EditText et_citizen_identity_card;
	private EditText et_citizen_address;
	private EditText et_citizen_phone;
	
//	private String formContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		inflater = LayoutInflater.from(this);
		Intent intent = this.getIntent();
		position = intent.getIntExtra("position", 0);
		
		initViewByPosition(position);
		setContentView(currentFormLayout);
		
//		init();
		bottomBarLayout = (RelativeLayout)currentFormLayout.findViewById(R.id.form_bottom);
		if (bottomBarLayout == null){
			return;
		}
		reportCase = (TextView)bottomBarLayout.findViewById(R.id.report_case);
		posPrint = (TextView)bottomBarLayout.findViewById(R.id.pos_print);
		reportCase.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				reportCaseProcess();
//				Toast.makeText(ActivityForm.this, "hhhhhh", Toast.LENGTH_SHORT);
			}
		});

		posPrint.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				posPrintProcess();
			}
		});
	}
	
	private void initViewByPosition(int position){
		switch (position){
		case 0:
			currentFormLayout = (RelativeLayout) inflater.inflate(R.layout.form_provide_legal_procedures, null);
			init0();
			break;
		case 1:
			currentFormLayout = (RelativeLayout) inflater.inflate(R.layout.form_site_record_info, null);
			init1();
			break;
		case 2:
			currentFormLayout = (RelativeLayout) inflater.inflate(R.layout.form_provide_legal_procedures, null);
//			init();
			break;
		case 3:
			currentFormLayout = (RelativeLayout) inflater.inflate(R.layout.form_provide_legal_procedures, null);
//			init();
			break;
		case 4:
			currentFormLayout = (RelativeLayout) inflater.inflate(R.layout.form_provide_legal_procedures, null);
//			init();
			break;
		}
	}
	
	private void init0(){
		tv_form_title_all = (TextView)currentFormLayout.findViewById(R.id.form_title_all);
		tv_form_title = (TextView)currentFormLayout.findViewById(R.id.form_title);
		tv_notification_unit = (TextView)currentFormLayout.findViewById(R.id.notification_unit);
		tv_illegal_location = (TextView)currentFormLayout.findViewById(R.id.illegal_location);
		tv_illegal_behavior = (TextView)currentFormLayout.findViewById(R.id.illegal_behavior);
		tv_limited_date = (TextView)currentFormLayout.findViewById(R.id.limited_date);
		
		et_notification_nuit = (EditText)currentFormLayout.findViewById(R.id.notification_unit_txt);
		et_illegal_location = (EditText)currentFormLayout.findViewById(R.id.illegal_location_txt);
		et_illegal_behavior = (EditText)currentFormLayout.findViewById(R.id.illegal_behavior_txt);
		et_limited_date = (EditText)currentFormLayout.findViewById(R.id.limited_date_txt);
	}
	
	private void init1(){
		tv_form_title_all_2 = (TextView)currentFormLayout.findViewById(R.id.form_title_all_2);
		tv_form_title_2 = (TextView)currentFormLayout.findViewById(R.id.form_title_2);
		tv_orgnization = (TextView)currentFormLayout.findViewById(R.id.form_title_2);
		tv_competent = (TextView)currentFormLayout.findViewById(R.id.form_title_2);
		tv_competent_post = (TextView)currentFormLayout.findViewById(R.id.form_title_2);
		tv_competent_address = (TextView)currentFormLayout.findViewById(R.id.form_title_2);
		tv_competent_phone = (TextView)currentFormLayout.findViewById(R.id.form_title_2);
		tv_citizen = (TextView)currentFormLayout.findViewById(R.id.form_title_2);
		tv_citizen_sex = (TextView)currentFormLayout.findViewById(R.id.form_title_2);
		tv_citizen_age = (TextView)currentFormLayout.findViewById(R.id.form_title_2);
		tv_citizen_identity_card = (TextView)currentFormLayout.findViewById(R.id.form_title_2);
		tv_citizen_address = (TextView)currentFormLayout.findViewById(R.id.form_title_2);
		tv_citizen_phone = (TextView)currentFormLayout.findViewById(R.id.form_title_2);
		
		et_form_title_all_2 = (EditText)currentFormLayout.findViewById(R.id.limited_date_txt);;
		et_form_title_2 = (EditText)currentFormLayout.findViewById(R.id.limited_date_txt);;
		et_orgnization = (EditText)currentFormLayout.findViewById(R.id.limited_date_txt);;
		et_competent = (EditText)currentFormLayout.findViewById(R.id.limited_date_txt);;
		et_competent_post = (EditText)currentFormLayout.findViewById(R.id.limited_date_txt);;
		et_competent_address = (EditText)currentFormLayout.findViewById(R.id.limited_date_txt);;
		et_competent_phone = (EditText)currentFormLayout.findViewById(R.id.limited_date_txt);;
		et_citizen = (EditText)currentFormLayout.findViewById(R.id.limited_date_txt);;
		et_citizen_sex = (EditText)currentFormLayout.findViewById(R.id.limited_date_txt);;
		et_citizen_age = (EditText)currentFormLayout.findViewById(R.id.limited_date_txt);;
		et_citizen_identity_card = (EditText)currentFormLayout.findViewById(R.id.limited_date_txt);;
		et_citizen_address = (EditText)currentFormLayout.findViewById(R.id.limited_date_txt);;
		et_citizen_phone = (EditText)currentFormLayout.findViewById(R.id.limited_date_txt);;
	}
	
	private String getFormContent(){
//		ESC esc = PosPrintUtil.esc;
		StringBuffer content = new StringBuffer();
		switch (position){
		case 0:
//			esc.setCenter();
			content.append(tv_form_title_all.getText().toString().trim()).append("_")
			.append(tv_form_title.getText().toString().trim()).append("_")
			.append(tv_notification_unit.getText().toString().trim()).append("_")
			.append(et_notification_nuit.getText().toString().trim()).append("_")
			.append(tv_illegal_location.getText().toString().trim()).append("_")
			.append(et_illegal_location.getText().toString().trim()).append("_")
			.append(tv_illegal_behavior.getText().toString().trim()).append("_")
			.append(et_illegal_behavior.getText().toString().trim()).append("_")
			.append(tv_limited_date.getText().toString().trim()).append("_")
			.append(et_limited_date.getText().toString().trim());
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		}
		return content.toString();
//		return Html.fromHtml(content.toString()).toString();
	}
	
	private void reportCaseProcess(){
		if (checkInputLegal()){
			
		}
	}
	
	private void posPrintProcess() {
		if (checkInputLegal()) {
			Intent it  = new Intent(ActivityForm.this, ActivityPosPrint.class);
			Bundle bundle = new Bundle();
			bundle.putString("content", getFormContent());
			it.putExtras(bundle);
			startActivityForResult(it, Msg.FORM_REQUEST_SUCCESS);
		}
	}
	
	private boolean checkInputLegal(){
		switch (position){
		case 0:
			return checkInput0();
		case 1:
			return checkInput1();
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		}
		return false;
	}
	
	private boolean checkInput0(){
		if (et_notification_nuit.getText().toString().trim().equals("")){
			Dialog.showDialog(Dialog.OK, this, null, 0,
					"提示", "通知单位 不能为空!", 0, "");
			return false;
		}else if (et_illegal_location.getText().toString().trim().equals("")){
			Dialog.showDialog(Dialog.OK, this, null, 0,
					"提示", "违法地点 不能为空!", 0, "");
			return false;
		}else if (et_illegal_behavior.getText().toString().trim().equals("")){
			Dialog.showDialog(Dialog.OK, this, null, 0,
					"提示", "违法行为 不能为空!", 0, "");
			return false;
		}else if (et_limited_date.getText().toString().trim().equals("")){
			Dialog.showDialog(Dialog.OK, this, null, 0,
					"提示", "限定日期 不能为空!", 0, "");
			return false;
		}else {
			return true;
		}
	}
	
	private boolean checkInput1(){
		if (et_limited_date.getText().toString().trim().equals("")){
			Dialog.showDialog(Dialog.OK, this, null, 0,
					"提示", "限定日期 不能为空!", 0, "");
			return false;
		}else {
			return true;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//如果上报成功刷新列表  先传回数据 后kill 当前activity
	}
	
	
}
