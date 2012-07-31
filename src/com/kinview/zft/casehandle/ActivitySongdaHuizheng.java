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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.kinview.config.Config;
import com.kinview.config.Dialog;
import com.kinview.config.Msg;
import com.kinview.entity.NewCase;
import com.kinview.entity.SongdaHuizheng;
import com.kinview.thread.ThreadExit;
import com.kinview.thread.ThreadGetSongdaHuizheng;
import com.kinview.thread.ThreadSubmitBd;
import com.kinview.util.Form;
import com.kinview.zft.R;

public class ActivitySongdaHuizheng extends Activity {
	private LayoutInflater inflater;
	private RelativeLayout currentFormLayout;
//	public int position = -1;
	private TextView reportCase;
	private TextView posPrint;
	private RelativeLayout bottomBarLayout;
	//��һ
	private EditText et_songda_huizheng_1;
	private EditText et_songda_huizheng_2;
	private EditText et_songda_huizheng_3;
	private EditText et_songda_huizheng_4;
	private EditText et_songda_huizheng_5;
	private Spinner sp_songda_huizheng_6;
	private EditText et_songda_huizheng_7;
	
//	private TextView tv_form_title_all;
	private TextView tv_songda_huizheng_1;
	private TextView tv_songda_huizheng_2;
	private TextView tv_songda_huizheng_3;
	private TextView tv_songda_huizheng_4;
	private TextView tv_songda_huizheng_5;
	private TextView tv_songda_huizheng_6;
	private TextView tv_songda_huizheng_7;
	
	private TextView tv_form_title_all;//�ܵ� ����
	private TextView tv_form_title;
	
	private String currentSongDaFangshi = "";//��ǰѡ����ʹ﷽ʽ
	public static String[] songDaFangshiStr =  {"ֱ��","����","�ʼ�","ί��","����","ת��"};
	private ArrayAdapter<String> songdafangshiAdapter = null;
//	private ThreadGetCaseInfo threadGetCaseInfo;
//	private int caseId;
	
//	private String formContent;
	private int position;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		inflater = LayoutInflater.from(this);
		Intent intent = this.getIntent();
		position = intent.getIntExtra("position", 0);
//		caseId = intent.getIntExtra("caseId", 0);
		
		setContentView(R.layout.form_songda_huizheng);
		
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
				
				Dialog.showDialog(Dialog.OKCANCEL, ActivitySongdaHuizheng.this, myHandler, Msg.PROMPT_PRINT,
						"��ʾ", "�Ƿ�ȷ���ϱ���", 0, "");
//				reportCaseProcess();
//				Toast.makeText(ActivityForm.this, "hhhhhh", Toast.LENGTH_SHORT);
			}
		});
		
//		if (Config.currentFormList.get(Config.bdPositionId).getState() != null){
//			if (!Config.currentFormList.get(Config.bdPositionId).getState().equals("")){
//				reportCase.setVisibility(View.GONE);
//				setEditTextLock();
//			}
//		}
		

		posPrint.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				posPrintProcess();
			}
		});
		
		if (Config.threadGetSongdaHuizheng == null){
			Config.threadGetSongdaHuizheng = new ThreadGetSongdaHuizheng();
			Config.threadGetSongdaHuizheng.showProcess(this, myHandler, 0, Config.caseId);
		}
	}
	
	private void setEditTextLock(){
		lockEditText(et_songda_huizheng_1);
		lockEditText(et_songda_huizheng_2);
		lockEditText(et_songda_huizheng_3);
		lockEditText(et_songda_huizheng_4);
		lockEditText(et_songda_huizheng_5);
//		lockEditText(et_songda_huizheng_6);
		lockEditText(et_songda_huizheng_7);
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
		et_songda_huizheng_1.setText(Config.currentSongdaHuizhengList.get(0).getTypeName());
		if (Config.currentSongdaHuizhengList.get(0).getOrganise().equals("") ||
				Config.currentSongdaHuizhengList.get(0).getOrganise() == null){
			et_songda_huizheng_2.setText(Config.currentSongdaHuizhengList.get(0).getPerson());
			et_songda_huizheng_3.setText(Config.currentSongdaHuizhengList.get(0).getPadd());
		}else {
			et_songda_huizheng_2.setText(Config.currentSongdaHuizhengList.get(0).getOrganise());
			et_songda_huizheng_3.setText(Config.currentSongdaHuizhengList.get(0).getOadd());
		}
		
		et_songda_huizheng_4.setText(getWenShuName());
		et_songda_huizheng_5.setText(Config.currentSongdaHuizhengList.get(0).getLsh());
//		et_songda_huizheng_6.setText(Config.currentSongdaHuizhengList.get(0).getOadd());
//		et_songda_huizheng_7.setText(Config.currentSongdaHuizhengList.get(0).getOadd());
//		et_illegal_location.setText(Config.currentNewCaseList.get(0).getAddress());
//		et_illegal_behavior.setText(Config.currentNewCaseList.get(0).getCaseWeiFaXW());
//		et_limited_date.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//		et_limited_date.setText("3");
	}
	private String getCurrentTitle(){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		StringBuffer sb = new  StringBuffer();
		String name = "";
		int bd_id = Config.currentFormList.get(position).getId();
		switch (bd_id){
		case 96:
//			name = "�����ṩ�Ϸ�����֪ͨ��(�ֻ�)";
			sb.append(Config.posFormTitle +"ִ"+"ͨ"+"��[").append(String.valueOf(year)).append("]");
			name = sb.toString();
			break;
		case 98:
//			name = "�ֳ�����¼(�ֻ�)";
			sb.append(Config.posFormTitle +"ִ"+"��"+"��[").append(String.valueOf(year)).append("]");
			name = sb.toString();
			break;
		case 100:
//			name = "�˲�֪ͨ��(�ֻ�)";
			sb.append(Config.posFormTitle +"ִ"+"ͨ"+"��[").append(String.valueOf(year)).append("]");
			name = sb.toString();
			break;
		case 101:
//			name = "֤�����еǼǱ���֪ͨ��(�ֻ�)";
			sb.append(Config.posFormTitle +"ִ"+"֤"+"��[").append(String.valueOf(year)).append("]");
			name = sb.toString();
			break;
		case 108:
//			name = "�������֪ͨ��(�ֻ�)";
			sb.append(Config.posFormTitle +"ִ"+"��"+"��[").append(String.valueOf(year)).append("]");
			name = sb.toString();
			break;
		}
		return name;
	}
	
	private String getWenShuName(){
		
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		StringBuffer sb = new  StringBuffer();
		String name = "";
		name = Config.currentFormList.get(position).getFormName();
//		int bd_id = Config.currentFormList.get(position).getId();
//		switch (bd_id){
//		case 96:
//			name = "�����ṩ�Ϸ�����֪ͨ��(�ֻ�)";
//			break;
//		case 98:
//			name = "�ֳ�����¼(�ֻ�)";
//			break;
//		case 100:
//			name = "�˲�֪ͨ��(�ֻ�)";
//			break;
//		case 101:
//			name = "֤�����еǼǱ���֪ͨ��(�ֻ�)";
//			break;
//		case 108:
//			name = "�������֪ͨ��(�ֻ�)";
//			break;
//		}
		return name;
	}
	
	private Handler myHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			Intent it = null;
			Bundle b = null;
			switch (msg.what){
			case Msg.GET_SONGDA_HUIZHENG_SUCCESS:
				showCaseInfo();
//				nextPage();
				break;
			case Msg.LISTVIEW_REFRESH:
//				refresh();
				break;
			case Msg.ERROR_SERVER_CONNECT:
				Dialog.showDialog(Dialog.OKCANCEL, ActivitySongdaHuizheng.this,
						null, Msg.PROGRAM_EXIT, "����", "����������ʧ��,���Ժ�����", 0, "");
				break;
			case Msg.PROGRAM_EXIT:
				ThreadExit thread = new ThreadExit(ActivitySongdaHuizheng.this);
				thread.showProcess();
				break;
			case Msg.TITLE_PROGRESS_START:
				setTitleProgress(true);
				break;
			case Msg.TITLE_PROGRESS_STOP:
				setTitleProgress(false);
				break;
			case Msg.ONBACK:
				it = new Intent(ActivitySongdaHuizheng.this,ActivityFormList.class);
				b = new Bundle ();
				b.putInt("type",Msg.REQUEST_REPEAT);
				it.putExtras(b);
				setResult(0, it);
				finish();
				break;
			case Msg.REQUEST_SUCCESS:
				Dialog.showDialog(Dialog.OK, ActivitySongdaHuizheng.this,
				myHandler, Msg.ACTIVITY_EXIT, "��ʾ", "�ϱ����ɹ�", 0, "");
				break;
			case Msg.REQUEST_ERROR:
				Dialog.showDialog(Dialog.OK, ActivitySongdaHuizheng.this,
						null, 0, "��ʾ", "�ϱ�������", 0, "");
				break;
			case Msg.ACTIVITY_EXIT:
				it = new Intent(ActivitySongdaHuizheng.this,ActivityFormList.class);
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
		tv_form_title_all = (TextView)this.findViewById(R.id.form_title_all_6);
		tv_form_title = (TextView)this.findViewById(R.id.form_title_6);
		tv_songda_huizheng_1 = (TextView)this.findViewById(R.id.songda_huizheng_1_name);
		tv_songda_huizheng_2 = (TextView)this.findViewById(R.id.songda_huizheng_2_name);
		tv_songda_huizheng_3 = (TextView)this.findViewById(R.id.songda_huizheng_3_name);
		tv_songda_huizheng_4 = (TextView)this.findViewById(R.id.songda_huizheng_4_name);
		tv_songda_huizheng_5 = (TextView)this.findViewById(R.id.songda_huizheng_5_name);
		tv_songda_huizheng_6 = (TextView)this.findViewById(R.id.songda_huizheng_6_name);
		tv_songda_huizheng_7 = (TextView)this.findViewById(R.id.songda_huizheng_7_name);
		
		et_songda_huizheng_1 = (EditText)this.findViewById(R.id.songda_huizheng_1_txt);
		et_songda_huizheng_2 = (EditText)this.findViewById(R.id.songda_huizheng_2_txt);
		et_songda_huizheng_3 = (EditText)this.findViewById(R.id.songda_huizheng_3_txt);
		et_songda_huizheng_4 = (EditText)this.findViewById(R.id.songda_huizheng_4_txt);
		et_songda_huizheng_5 = (EditText)this.findViewById(R.id.songda_huizheng_5_txt);
		sp_songda_huizheng_6 = (Spinner)this.findViewById(R.id.songda_huizheng_6_sp);
		et_songda_huizheng_7 = (EditText)this.findViewById(R.id.songda_huizheng_7_txt);
		
		tv_form_title_all.setText(Config.cgUnitName);
		
		songdafangshiAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, songDaFangshiStr);
		songdafangshiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		sp_songda_huizheng_6.setAdapter(songdafangshiAdapter);
		sp_songda_huizheng_6.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				currentSongDaFangshi = songDaFangshiStr[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		currentSongDaFangshi = songDaFangshiStr[0];
	}
	
	
	private String getFormContent(){
		SongdaHuizheng tempSD = Config.currentSongdaHuizhengList.get(0);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		
		StringBuffer content = new StringBuffer();
		content.append(tv_form_title_all.getText().toString().trim()).append("\r\n");
		content.append(tv_form_title.getText().toString()).append("_");
		
		content.append(getCurrentTitle()).append("  \r\nNo.")
//		content.append(Config.posFormTitle +"ִͨ��[").append(String.valueOf(year)).append("]  \r\nNo.")
		.append(tempSD.getLsh()).append("_");
		
		content.append("����:"+et_songda_huizheng_1.getText().toString()+"\r\n");
		content.append("���ʹ���:" + et_songda_huizheng_2.getText().toString() + "\r\n\r\n");
		content.append("�ʹ�ص�:" + et_songda_huizheng_3.getText().toString() + "\r\n\r\n");
		content.append("�ʹ�����:" + et_songda_huizheng_4.getText().toString() + "\r\n");
		content.append("�ʹ��ĺ�:" + et_songda_huizheng_5.getText().toString() + "\r\n");
		content.append("�ʹ﷽ʽ:" + currentSongDaFangshi + "\r\n");
		content.append("�ռ���ǩ�������:\r\n\r\n");
		content.append("�յ�ʱ��:\r\n         ��   ��   ��   ʱ   ��\r\n");
		content.append("�����˼�����������: \r\n\r\n");
		content.append("��ע:\r\n");
		content.append("    " + et_songda_huizheng_7.getText().toString() + "\r\n\r\n");
//		content.append("ǩ����:\r\n\r\n");
		content.append("ǩ��ʱ��:       ��     ��     ��  \r\n");
		content.append("�ʹ���:\r\n\r\n");
		content.append("֤����:\r\n\r\n");
		content.append("ע�����ʹ��˻�������ͬס��������ܾ���������ģ��ʹ��˿������йػ��� "
				+ "��֯�������ڵ�λ�Ĵ�������˵����������ʹ��֤�ϼ����������ɺ����ڣ���"
				+ "�ʹ��ˡ���֤��ǩ������£��������������ʹ��˵�ס��������Ϊ�ʹ");
		
		
		return content.toString();
//		return Html.fromHtml(content.toString()).toString();
	}
	
	private String getFormContent2(){
		SongdaHuizheng tempSD = Config.currentSongdaHuizhengList.get(0);
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);	
		
		StringBuffer content = new StringBuffer();
//		content.append(tv_form_title_all.getText().toString().trim()).append("\r\n");
//		content.append(tv_form_title.getText().toString()).append("_");
//		content.append(Config.posFormTitle +"ִͨ��[").append(String.valueOf(year)).append("]  \r\nNo.")
//		.append(tempSD.getLsh()).append("_");
		
		content.append(et_songda_huizheng_1.getText().toString()+"_");
		content.append(et_songda_huizheng_2.getText().toString() + "_");
		content.append(et_songda_huizheng_3.getText().toString() + "_");
		content.append(et_songda_huizheng_4.getText().toString() + " ");//�������������� ��������ĺŷ���һ����
		content.append(et_songda_huizheng_5.getText().toString() + "_");
		content.append(currentSongDaFangshi + "_");
//		content.append("�ռ���ǩ�������:\r\n\r\n");
//		content.append("�յ�ʱ��:\r\n         ��   ��   ��   ʱ   ��\r\n");
//		content.append("�����˼�����������: \r\n\r\n");
//		content.append("��ע:\r\n");
		content.append(et_songda_huizheng_7.getText().toString() + "_");
//		content.append("ǩ����:\r\n\r\n");
//		content.append("ǩ��ʱ��:       ��     ��     ��  \r\n");
//		content.append(Config.user.getUserName() + "_");
//		content.append("֤����:\r\n\r\n");
		
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
//				aj_id = Integer.valueOf(Config.currentFormList.get(0).getCaseId());
//				bd_id = String.valueOf(Config.currentFormList.get(Config.bdPositionId).getId());//ע��
				bd_name = Config.currentFormList.get(Config.bdPositionId).getFormName();
				aj_id = Config.caseId;
				bd_name = "�ʹ��֤";
				bd_id = String.valueOf(Config.bd_id_songda_huizheng);
//				bd_id = String.valueOf(Config.currentFormList.get(position).getId());
//				bd_name = Config.currentFormList.get(Config.bdPositionId).getFormName();
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
			Intent it  = new Intent(ActivitySongdaHuizheng.this, ActivityPosPrint.class);
			Bundle bundle = new Bundle();
			bundle.putInt("position", Config.bdPositionId);
			bundle.putString("content", getFormContent());
			it.putExtras(bundle);
			startActivityForResult(it, Msg.FORM_REQUEST_SUCCESS);
		}
	}
	
	private boolean checkInputLegal(){
		if (et_songda_huizheng_1.getText().toString().trim().equals("")){
			Dialog.showDialog(Dialog.OK, this, null, 0,
					"��ʾ", tv_songda_huizheng_1.getText().toString() +"����Ϊ��!", 0, "");
			return false;
		}else if (et_songda_huizheng_2.getText().toString().trim().equals("")){
			Dialog.showDialog(Dialog.OK, this, null, 0,
					"��ʾ", tv_songda_huizheng_2.getText().toString() +"����Ϊ��!", 0, "");
			return false;
		}else if (et_songda_huizheng_3.getText().toString().trim().equals("")){
			Dialog.showDialog(Dialog.OK, this, null, 0,
					"��ʾ", tv_songda_huizheng_3.getText().toString() +"����Ϊ��!", 0, "");
			return false;
		}else if (et_songda_huizheng_4.getText().toString().trim().equals("")){
			Dialog.showDialog(Dialog.OK, this, null, 0,
					"��ʾ", tv_songda_huizheng_4.getText().toString() +"����Ϊ��!", 0, "");
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
