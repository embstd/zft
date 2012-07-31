package com.	kinview.camera;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.AbsoluteLayout;
import android.widget.ImageView;

@SuppressWarnings("deprecation")
public class ActivityPictureView extends Activity implements OnTouchListener{

	private AbsoluteLayout absoluteLayout =null;
	private int window_x,window_y;
	private String url_image = "";
	private ImageView imageView = null;
	private float pos_x1,pos_y1,pos_x2,pos_y2;
	private double distence=0;
	private double moveDistence=0;
	int start_width,start_height;
	int width,height;
	int times=0;
	int state = 0;
	int flag_p1=0,flag_p2=0,flag_press=0;
	int img_x=0,img_y=0;
	private MyHandler mHandler = new MyHandler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		absoluteLayout = new AbsoluteLayout(this);
		getBundle();
		try{
			initPanel();
		}catch(Exception e){
			finish();
			return;
			//加载不正常的图片报错
		}
		setContentView(absoluteLayout);
		absoluteLayout.setOnTouchListener(this);
		init();
	}
	
	private void init(){

	}
	
	private void getBundle(){
		Bundle b = getIntent().getExtras();
		url_image = b.getString("url_image");
	}

	private void initPanel(){
		if(url_image==null&&url_image.equals(""))
			return;
		
		DisplayMetrics dm = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics(dm);
	    window_x =dm.widthPixels;	//除去左右间隔
	    window_y = dm.heightPixels;//除去任务栏高度,除去上下间隔
	    
//	    Bitmap bitmap = Config.bitmapMovie.get(url_image);
//	    Bitmap bitmap = Config_PictureMap.getBitmap(url_image);
	    //加载图片
	    FileInputStream fStream=null;
		try {
			fStream = new FileInputStream(url_image);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Bitmap bitmap = BitmapFactory.decodeStream(fStream);
	    
	    
	    width = bitmap.getWidth();
	    height = bitmap.getHeight();
	    
	    //计算缩放
	    if(width > window_x || height > window_y){
	    	if(width>window_x){
		    	float scale = ((float) window_x) / width; // 比例
		    	width = window_x;
		    	height = (int)(height *  scale);
	    	}
	    	if(height>window_y - 25 ){
	    		float scale = ((float) window_y -25) / height; // 比例
	    		width = (int)(width *  scale);
		    	height = window_y -25;
	    	}
	    }
	    
	    //计算位置
	    int x = (window_x-10 - width)/2 + 5;
	    int y = (window_y-25-10 - height)/2 + 5;
	    
		AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(width, height, x, y);
		start_width = width;
		start_height = height;
		imageView = new ImageView(this);
		imageView.setImageBitmap(bitmap);
		absoluteLayout.addView(imageView, layoutParams);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		int action = event.getAction();
		if(event.getPointerCount()==1){
			if (action == MotionEvent.ACTION_DOWN) {
				pos_x1 = event.getX();
				pos_y1 = event.getY();
			} else if (action == MotionEvent.ACTION_MOVE) {
				//获取移动的距离
				float move_x = event.getX() - pos_x1;
				float move_y = event.getY() - pos_y1;
				pos_x1 = event.getX();
				pos_y1 = event.getY();
				
				//获取原来位置
				AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) imageView
						.getLayoutParams();
				int x = layoutParams.x;
				int y = layoutParams.y;
				//计算新位置
				float new_x = x + move_x;
				float new_y = y + move_y;
				//计算边界
				int kong = 30;
				if (width < window_x) {
					if (new_x < 0 - kong) {
						new_x = 0 - kong;
					} else if (new_x > window_x - width + kong) {
						new_x = window_x - width + kong;
					}
				} else {
					if (new_x < window_x - width - kong) {
						new_x = window_x - width - kong;
					} else if (new_x > 0 + kong ) {
						new_x = kong;
					}
				}
				if (height < window_y -25) {
					if (new_y < 0 - kong) {
						new_y = 0 - kong;
					} else if (new_y > window_y - height + kong -25 ) {
						new_y = window_y - height + kong-25;
					}
				} else {
					if (new_y < window_y - height - kong-25) {
						new_y = window_y - height - kong-25;
					} else if (new_y > 0 + kong ) {
						new_y = kong;
					}
				}
				layoutParams = new AbsoluteLayout.LayoutParams(width, height,
						(int) new_x, (int) new_y);
				imageView.setLayoutParams(layoutParams);
			}
		}else if(event.getPointerCount()==2){
//			print.out("two button press down!");
//			print.out("press code is="+action);
			
			if(action == MotionEvent.ACTION_MOVE){
				if(distence==0){
					pos_x1 = event.getX(0);
					pos_y1 = event.getY(0);
					pos_x2 = event.getX(1);
					pos_y2 = event.getY(1);
					calculateDistence();
				}
			}
			
			if(action == MotionEvent.ACTION_POINTER_1_UP ||action == MotionEvent.ACTION_POINTER_2_UP){
				if(pos_x1==event.getX(0) && pos_y1 == event.getY(0)
						&& pos_x2==event.getX(1)&& pos_y2==event.getY(1)){
					flag_press =0;
					return true;
				}
				pos_x1 = event.getX(0);
				pos_y1 = event.getY(0);
				pos_x2 = event.getX(1);
				pos_y2 = event.getY(1);
				flag_p1 = 0;
				if(flag_p2==0 && flag_press == 1){
					calculateZoomDistence();
				}
			}
		}
		return true;
	}
	
	public void calculateDistence(){
		flag_press = 1;
		float x = pos_x1-pos_x2;
		float y = pos_y1-pos_y2;
		double tmp = x*x + y*y;
		distence = Math.sqrt(tmp);
	}
	
	//计算放大缩小范围
	public void calculateZoomDistence(){
		flag_press = 0;
		float x = pos_x1-pos_x2;
		float y = pos_y1-pos_y2;
		double tmp = x*x + y*y;
		moveDistence = Math.sqrt(tmp) - distence;
		startThread();
		distence = 0;
	}
	
	//放大
	public void ZoomOut(){
		width *= 1.3f;
		height *=1.3f;
		AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(
				width,
				height,
				img_x,
				img_y);
		imageView.setLayoutParams(layoutParams);
	}
	
	//缩小
	public void ZoomIn(){
		width /= 1.3f;
		height /=1.3f;
		AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(
				width,
				height,
				img_x,
				img_y);
		imageView.setLayoutParams(layoutParams);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// return super.onCreateOptionsMenu(menu);
		super.onCreateOptionsMenu(menu);
		// menu.add(0, ITEM0, 0, "设置").setIcon(R.drawable.icon_setting);
		menu.add(0, 1, 0, "放大");//.setIcon(R.drawable.menu_sb);
//		menu.add(0, ITEM1, 0, "今日提醒").setIcon(R.drawable.menu_tip);
		menu.add(0, 2, 0, "缩小");//.setIcon(R.drawable.menu_call);
		
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// return super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case 1:
			ZoomOut();
			break;
		case 2:
			ZoomIn();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void startThread(){
		AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) imageView
		.getLayoutParams();
		img_x = layoutParams.x;
		img_y = layoutParams.y;
		MyThread mThread = new MyThread();
		mThread.start();
	}
	
	
	class MyHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1: //显示进度
//					String rect = (String)msg.obj;
//					String[] rects = rect.split(",");
					AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(
							width,
							height,
							img_x,
							img_y);
					imageView.setLayoutParams(layoutParams);
					break;
			}
			super.handleMessage(msg);
		}
		
	}
	
	class MyThread extends Thread{
		
		public void run(){
			if(moveDistence==0)
				return;
			int fingerStep = 20;	//手指移动步子大小
			int fingerCount = 0;	//步数
			double moveStep = 0.1;//移动比例
			if(moveDistence<0){
				moveStep *=-1;
			}
			fingerCount = Math.abs((int)(moveDistence/fingerStep));
			if(fingerCount>50)
				fingerCount = 50;
			if(fingerCount<1)
				fingerCount=1;
			double percent = 1 + moveStep *fingerCount;
			if(percent <0.5)
				percent = 0.5;
			if(percent >10)
				percent = 10;
			int new_width = (int)(width * percent);
			int new_height = (int)(height *percent);
			int new_x = img_x - (new_width - width)/2;
			int new_y = img_y - (new_height - height)/2;
			
			int second = 300; //300毫秒内完成
			int wait = 15;	//等待10毫秒刷新一次;
			int count = second/wait;
			int moveStep_width = (new_width - width) /count;
			int moveStep_height = (new_height - height) /count;
			int moveSetp_x = (new_x - img_x)/count;
			int moveSetp_y = (new_y - img_y)/count;
			
			for(int i=0;i<count;i++){
				try {
					sleep(wait);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				width += moveStep_width;
				height += moveStep_height;
				img_x += moveSetp_x;
				img_y += moveSetp_y;
				
				mHandler.sendMessageDelayed(mHandler.obtainMessage(
						1, 0), 0);
			}
		}
	};
	
}
