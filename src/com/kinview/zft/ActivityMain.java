package com.kinview.zft;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import com.kinview.zft.R;
import com.kinview.config.Config;
import com.kinview.config.Dialog;
import com.kinview.config.Menus;
import com.kinview.config.Msg;
import com.kinview.config.print;
import com.kinview.thread.ThreadExit;
import com.kinview.util.Login;

public class ActivityMain extends ActivityGroup {
	public static final int ITEM0 = Menu.FIRST;
	public static final int ITEM1 = Menu.FIRST + 1;
	public static final int ITEM2 = Menu.FIRST + 2;
	public static final int ITEM3 = Menu.FIRST + 3;
	public static final int ITEM4 = Menu.FIRST + 4;
	public static final int ITEM5 = Menu.FIRST + 5;
	public static final int ITEM6 = Menu.FIRST + 6;
	public static TabHost tabHost;
	private final String[] tabName = { "违章查处", "案件办理", "历史记录", "渣土查询", "照片管理" };
	private final int[] tabPicOn = { R.drawable.main_tab1,
			R.drawable.main_tab2, R.drawable.main_tab3, R.drawable.main_tab4,
			R.drawable.main_tab5 };
	private final int[] tabPicOff = { R.drawable.main_tab1b,
			R.drawable.main_tab2b, R.drawable.main_tab3b,
			R.drawable.main_tab4b, R.drawable.main_tab5b };
	@SuppressWarnings("unchecked")
	private final Class[] tabClass = { ActivityWtcc.class, ActivityWtsb.class,
		ActivityWtsb.class, ActivityWtsb.class, ActivityWtsb.class };
	private ImageView[] tabImageView = new ImageView[5];
	private int select_position = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bottomtab);
		
		//测试网格
//		Config.dutyRgnCode="1105101";
		
//		检查屏幕参数,避免,出现错误时,再次加载,导致数据位空,界面出现差错!(分辨率)
		if(Config.windowType==0){
			DisplayMetrics dm = new DisplayMetrics();
		    getWindowManager().getDefaultDisplay().getMetrics(dm);
		    if(dm.heightPixels<=480){
		    	Config.windowType =1;
		    	Config.offLineLoad = true;	//程序出错后,参数丢失,那么就设置为离线登陆,不允许上报
		    	Config.boolean_thread_run = false;
		    }
		}
		initPanel();
		init();
	}
	
	public void init(){
		
	}


	private void initPanel() {
		tabHost = (TabHost) findViewById(R.id.edit_item_tab_host);
		tabHost.setup(this.getLocalActivityManager());

		LinearLayout ll = (LinearLayout) tabHost.getChildAt(0);
		TabWidget tw = (TabWidget) ll.getChildAt(1);

		for (int i = 0; i < tabName.length; i++) {
			RelativeLayout tabIndicator = (RelativeLayout) LayoutInflater.from(
					this).inflate(R.layout.tab_indicator, tw, false);
			ImageView iv = (ImageView) tabIndicator.getChildAt(0);
			if (i == 0)
				iv.setImageResource(tabPicOn[i]);
			else
				iv.setImageResource(tabPicOff[i]);
			tabImageView[i] = iv;
			TextView tv = (TextView) tabIndicator.getChildAt(1);
			tv.setText(tabName[i]);
			tabHost.addTab(tabHost.newTabSpec("TAB_" + i).setIndicator(
					tabIndicator).setContent(new Intent(this, tabClass[i])));
		}
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				tabImageView[select_position]
						.setImageResource(tabPicOff[select_position]);
				print.out("id==" + tabId);
				String s_position = tabId.split("_")[1];
				try {
					select_position = Integer.parseInt(s_position);
				} catch (Exception e) {
					e.printStackTrace();
				}
				tabImageView[select_position]
						.setImageResource(tabPicOn[select_position]);
			}
		});
	}

	public void showToast(String msg) {
		Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
		toast.show();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		Menus.initMenu(this, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Menus.actionMenu(this, mHandler, item.getItemId());
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//tab窗口的按键事件要再每一个窗口中截获
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Dialog.showDialog(Dialog.OKCANCEL, this, mHandler, Msg.ACTIVITY_EXIT, "退出程序", "确定要退出程序吗?",0,"");
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
//			case Msg.GETCASELIST:
//				showCaseList();
//				break;
//			case Msg.LISTVIEW_REFRESH:
////				refresh();
//				break;
			case Msg.ACTIVITY_CHANAGE:
				//保存切换
				Login login = new Login(ActivityMain.this);
				if(Config.windowStytle.equals("0")){
					login.chanageWindowStytle("1");
					Config.windowStytle = "1";
				}else{
					login.chanageWindowStytle("0");
					Config.windowStytle = "0";
				}
				//启动窗口
				Intent it = new Intent(ActivityMain.this,ActivityMainSutra.class);
				it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(it);
				finish();
				break;
			case Msg.ACTIVITY_EXIT:
//				print.out("检查到按键3");
				ThreadExit thread = new ThreadExit(ActivityMain.this);
				thread.showProcess();
				break;
			}
		}
	};
}
