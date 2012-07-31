package com.kinview.zft.casehandle;

import com.kinview.config.Dialog;
import com.kinview.config.Msg;
import com.kinview.zft.R;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ActivityFormEvidence extends TabActivity {
	private LayoutInflater inflater;
	private RelativeLayout currentFormLayout;
	public int position = -1;
	public int optionId = 0;
	private RelativeLayout bottomBarLayout;
	private TextView showOptions;
	private TabHost tabHost;
	private RadioGroup radioGroup;
	public static final String FIRST = "基础信息";
	public static final String SECOND = "证据信息";
	
	private String[] tabCategory = {FIRST, SECOND};
	private Class[] tabClass = {ActivityFormEvidenceReg.class, ActivityFormEvidenceInfoListView.class};

	private TextView reportCase;
	private TextView posPrint;
	
	private FrameLayout frameLayout;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form_tab);
		inflater = LayoutInflater.from(this);
		Intent intent = this.getIntent();
		position = intent.getIntExtra("position", 0);

		tabHost = this.getTabHost();
		tabHost.setup();
//		frameLayout = tabHost.getTabContentView();
		for (int i=0; i<tabCategory.length; i++){
			tabHost.addTab(tabHost.newTabSpec(tabCategory[i]).setIndicator(tabCategory[i])
					.setContent(new Intent(this, tabClass[i])));
		}
		frameLayout =  tabHost.getTabContentView();
//		tabHost.addTab(tabHost.newTabSpec("0").setIndicator(FIRST)
//				.setContent(new Intent(this, ActivityFormSiteRecordInfo.class)));
//		tabHost.addTab(tabHost.newTabSpec("1").setIndicator(SECOND)
//				.setContent(new Intent(this, ActivityFormSiteRecordSituation.class)));
		radioGroup = (RadioGroup) this.findViewById(R.id.rg_main_btns);
		
		for (int j = 0; j<radioGroup.getChildCount(); j++){
			RadioButton rb = (RadioButton) radioGroup.getChildAt(j);
			rb.setText(tabCategory[j]);
			if (j == 0){
				rb.setChecked(true);
			}
		}
		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
//				int radioId = -1;
				switch (checkedId){
				case R.id.first:
					optionId = 0;
//					init();
//					tabHost.setCurrentTabByTag(tabCategory[0]);
					break;
				case R.id.second:
					optionId = 1;
//					init();
//					tabHost.setCurrentTabByTag(tabCategory[1]);
					break;
				}
				tabHost.setCurrentTabByTag(tabCategory[optionId]);
				RadioButton rb = (RadioButton) radioGroup.getChildAt(optionId);
				rb.setChecked(true);
//				radioGroup.setVisibility(View.GONE);
//				showOptions();
			}
		});
		
//		radioGroup.setVisibility(View.GONE);
//		bottomBarLayout = (RelativeLayout) this.findViewById(R.id.form_bottom);
//		showOptions = (TextView) bottomBarLayout.findViewById(R.id.show_options);
//		showOptions.setVisibility(View.VISIBLE);
//		showOptions.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				showOptions();
//			}
//		});
//		
//		if (bottomBarLayout == null){
//			return;
//		}
	}
	
	public void showOptions(){
		if (radioGroup.getVisibility() == View.GONE){
			radioGroup.setVisibility(View.VISIBLE);
			showOptions.setText("隐藏选项");
		}else {
			radioGroup.setVisibility(View.GONE);
			showOptions.setText("显示选项");
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 如果上报成功刷新列表 先传回数据 后kill 当前activity
	}
}
