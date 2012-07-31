package com.kinview.setting;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.kinview.util.SystemSetting;
import com.kinview.util.SystemSettingMgr;
import com.kinview.zft.R;

public class ActivitySystemSetting extends Activity
{

  private String type = "";
  private String id = "";
  private String description = "";
  private String url = "";
  
  private Button button1;
  private Button button2;
  private Button button3;
  private EditText edittext1;
  private EditText edittext2;
  private EditText edittext3;
  

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
	  requestWindowFeature(Window.FEATURE_NO_TITLE);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.settingsystem);
//    setTitle("系统设置");

    init();
  }

  public void init()
  {
    getBundle();
    button1 = (Button)findViewById(R.id.systemsetting_button1);
    button2 = (Button)findViewById(R.id.systemsetting_button2);
    button3 = (Button)findViewById(R.id.systemsetting_button3);
    edittext1 = (EditText)findViewById(R.id.systemsetting_edittext1);
    edittext2 = (EditText)findViewById(R.id.systemsetting_edittext2);
    edittext3 = (EditText)findViewById(R.id.systemsetting_edittext3);
    
    
    if (type.equals("modify")){
      edittext1.setText(id);
      edittext2.setText(description);
      edittext3.setText(url);
      edittext1.setEnabled(false);
      button2.setVisibility(View.VISIBLE);
    }
    
    addListenters();

  }

  public void getBundle()
  {
    Bundle b = this.getIntent().getExtras();
    type = b.getString("type");
    if (type.equals("modify"))
    {
      id=b.getString("id");
      description = b.getString("description");
      url = b.getString("url");
    }
  }

  public void addListenters()
  {
    button1.setOnClickListener(new OnClickListener(){
      @Override
      public void onClick(View v)
      {
        if(edittext1.getText().toString().equals(""))return;
        if(edittext2.getText().toString().equals(""))return;
        if(edittext3.getText().toString().equals(""))return;
        SystemSetting ss = new SystemSetting();
        ss.setId(edittext1.getText().toString());
        ss.setDescription(edittext2.getText().toString());
        ss.setUrl(edittext3.getText().toString());
        SystemSettingMgr ssMgr = new SystemSettingMgr(ActivitySystemSetting.this);
        ssMgr.save(ss);
        finish();
      }
    });
    
    button2.setOnClickListener(new OnClickListener(){
      public void onClick(View v)
      {
        showDialog();
      }
    });
    
    button3.setOnClickListener(new OnClickListener(){
      public void onClick(View v)
      {
        finish();
      }
    });
    
  }


  
  public void showDialog()
  {
    AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySystemSetting.this);
    builder.setIcon(R.drawable.alert_dialog_icon);
    builder.setTitle("删除配置!");
    builder.setMessage("你确定要删除这条配置信息吗?");
    builder.setPositiveButton("确  定",
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
            SystemSettingMgr ssMgr = new SystemSettingMgr(ActivitySystemSetting.this);
            ssMgr.delete(edittext1.getText().toString());
            finish();
          }
        });
    builder.setNegativeButton("取  消",
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
          }
        });
    builder.show();
  }
}
