package com.kinview.setting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

import com.kinview.util.SystemSetting;
import com.kinview.util.SystemSettingMgr;
import com.kinview.zft.R;

public class ActivitySystemSettingList extends Activity implements OnItemClickListener
{

  private SystemSettingMgr ssMgr;
  public static final int ITEM0 = Menu.FIRST;
  public static final int ITEM1 = Menu.FIRST + 1;
  private Button button1;
  private Button button2;
  private OnClickListener listener1;
  private OnClickListener listener2;
  private ListView itemlist;
  
  protected void onCreate(Bundle savedInstanceState)
  {
	  requestWindowFeature(Window.FEATURE_NO_TITLE);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.settingsystemlist);
//    setTitle("系统设置");
    init();
    showSystemSettingList();
  }

  private void init(){
    button1 = (Button)findViewById(R.id.systemsettinglist_button1);
    button2 = (Button)findViewById(R.id.systemsettinglist_button2);
    itemlist = (ListView) findViewById(R.id.systemsettinglist_listview1);
    
    addlisteners();
  }
  
  
  public void addlisteners(){
    listener1 = new OnClickListener(){
      public void onClick(View v)
      {
        showDialog();
      }
    };
    listener2 = new OnClickListener(){
      public void onClick(View v)
      {
        finish();
      }
    };
    button1.setOnClickListener(listener1);
    button2.setOnClickListener(listener2);
  }
  
  public void showSystemSettingList(){
    ssMgr = new SystemSettingMgr(this);
    ssMgr.loadSystemSetting();
    
    List<Map<String, Object>> list = ssMgr.getAllItemsForListView();
    
    if(list.size()==0){
      HashMap<String, Object> item = new HashMap<String, Object>();
      item.put("title", "没有配置项目!");
      item.put("info", "请点击菜单->添加,创建配置项目");
      list.add(item);
    }
    
    SimpleAdapter adapter = new SimpleAdapter(this, list,
        android.R.layout.simple_expandable_list_item_2, new String[] { "title","info" },
        new int[] { android.R.id.text1 , android.R.id.text2});
    
     itemlist.setAdapter(adapter);
     itemlist.setOnItemClickListener(this);  
     itemlist.setSelection(0);
  }
  
  public void onItemClick(AdapterView<?> arg0, View arg1, int position,
      long id)
  {
    Intent it = new Intent(this,ActivitySystemSetting.class);
    
    Bundle b = new Bundle();
    List<SystemSetting> list = ssMgr.loadSystemSetting();
    if(list.size()==0)return;   //如果数据库里没有,则不执行下去了
    b.putString("type", "modify");
    b.putString("id", ssMgr.getItem(position).getId());
    b.putString("description", ssMgr.getItem(position).getDescription());
    b.putString("url", ssMgr.getItem(position).getUrl());
    
    it.putExtras(b);
    startActivityForResult(it,0);
  }
  
  public boolean onCreateOptionsMenu(Menu menu) {
    //return super.onCreateOptionsMenu(menu);
    super.onCreateOptionsMenu(menu);
    menu.add(0,ITEM0,0,"添加").setIcon(R.drawable.alert_dialog_icon);
    menu.add(0,ITEM1,0,"返回");

    menu.findItem(ITEM1);
    return true;
  }
  
  public boolean onOptionsItemSelected(MenuItem item) {
    //return super.onOptionsItemSelected(item);
    switch(item.getItemId()){
    case ITEM0:
      actionClickMenuItem1();
      break;
    case ITEM1:
      actionClickMenuItem2();
      break;
    }
    return super.onOptionsItemSelected(item);
  }
  
  private void actionClickMenuItem1(){
    Bundle b = new Bundle();
    b.putString("type", "addnew");
    Intent it = new Intent(this,ActivitySystemSetting.class);
    it.putExtras(b);
    startActivityForResult(it,0);
  }
  
  private void actionClickMenuItem2(){
    finish();
  }
  
  
  protected void onActivityResult(int requestCode, int resultCode,
      Intent data)
  {
    showSystemSettingList();
    super.onActivityResult(requestCode, resultCode, data);
  }
  public void showDialog()
  {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setIcon(R.drawable.alert_dialog_icon);
    builder.setTitle("重置参数!");
    builder.setMessage("你确定要重置所有参数吗?");
    builder.setPositiveButton("确  定",
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
            SystemSettingMgr ssMgr = new SystemSettingMgr(ActivitySystemSettingList.this);
            ssMgr.replaceToDefault();
            showSystemSettingList();
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
