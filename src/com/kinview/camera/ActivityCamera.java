package com.kinview.camera;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.kinview.assistant.Assistant;
import com.kinview.zft.R;
import com.kinview.config.Config;
import com.kinview.config.print;

public class ActivityCamera extends Activity
{
	private CameraPreView preView = null;
	private Bitmap bitmap = null;
	public String flashMode = "auto"; // "on" "off"
	public TextView tv_reset, tv_finish, tv_cancel, tv_flash, tv_matrix;
	public ImageView iv_reset, iv_finish, iv_cancel, iv_flash, iv_matrix;
	public int type = 0;
	public int panel_type = 0; // 0为纵向排版,1为横向排版
	private int state_take = 0;// state_fbl=0; //标识按钮状态,拍摄状态/重拍,闪光

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		// 去掉标题,全屏
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ***********************20120514*****************************//
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		preView = new CameraPreView(this);
		preView.setVerticalFadingEdgeEnabled(true);

		if (panel_type == 0)
		{
			// 纵向排版
			setContentView(R.layout.camera_v);
			init0();
		}
		else if (panel_type == 1)
		{
			// 横向排版
			switch (ActivityPhoto.windowType)
			{
			case 0:
				setContentView(R.layout.camera);
				break;
			case 1:
				setContentView(R.layout.low_camera);
				break;
			}
			init1();
		}

	}

	public void init0()
	{
		OnTouchListener otl = new OnTouchListener()
		{
			public boolean onTouch(View v, MotionEvent event)
			{
				int action = event.getAction();
				ImageView iv = (ImageView) v;
				if (action == MotionEvent.ACTION_DOWN)
				{
					iv.setBackgroundResource(R.drawable.camera_down2);
				}
				else if (action == MotionEvent.ACTION_CANCEL
						|| action == MotionEvent.ACTION_UP)
				{
					iv.setBackgroundResource(R.drawable.camera_up2);
				}
				return false;
			}
		};
		iv_cancel = (ImageView) findViewById(R.id.camera_ImageView1);
		iv_reset = (ImageView) findViewById(R.id.camera_ImageView2);
		iv_flash = (ImageView) findViewById(R.id.camera_ImageView3);
		iv_matrix = (ImageView) findViewById(R.id.camera_ImageView4);
		iv_finish = (ImageView) findViewById(R.id.camera_ImageView5);

		iv_cancel.setImageResource(R.drawable.camera_cancel);
		iv_reset.setImageResource(R.drawable.camera_take);
		iv_flash.setImageResource(R.drawable.camera_flash_auto);
		iv_matrix.setImageResource(R.drawable.camera_fbl_640);
		iv_finish.setImageResource(R.drawable.camera_finish);

		iv_cancel.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				cancelCamera();
			}
		});

		iv_reset.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{

				if (state_take == 0)
				{
					print.out("state_take==0执行takePicture();");
					takePicture();
				}
				else if (state_take == 1)
				{
					print.out("state_take==1执行resetCamera();");
					resetCamera();
				}
			}
		});

		iv_flash.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				if (flashMode.equals("auto"))
				{
					flashMode = "on";
					iv_flash.setImageResource(R.drawable.camera_flash_open);
				}
				else if (flashMode.equals("on"))
				{
					flashMode = "off";
					iv_flash.setImageResource(R.drawable.camera_flash_close);
				}
				else if (flashMode.equals("off"))
				{
					flashMode = "auto";
					iv_flash.setImageResource(R.drawable.camera_flash_auto);
				}
			}
		});

		iv_matrix.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				if (type == 4)
				{
					type = 0;

				}
				else
				{
					type += 1;
				}
				// if(type ==0 || type ==3){
				// tv_matrix.setText("分辨率\n800×600");
				// }else if(type==1){
				// tv_matrix.setText("分辨率\n320×240");
				// }else if(type==2){
				// tv_matrix.setText("分辨率\n640×480");
				// }else if(type==4){
				// tv_matrix.setText("分辨率\n1024×768");
				// }
				if (type == 0 || type == 3)
				{
					// tv_matrix.setText("分辨率\n640×480");
					iv_matrix.setImageResource(R.drawable.camera_fbl_640);
				}
				else if (type == 1)
				{
					// tv_matrix.setText("分辨率\n800×600");
					iv_matrix.setImageResource(R.drawable.camera_fbl_800);
				}
				else if (type == 2)
				{
					// tv_matrix.setText("分辨率\n320×240");
					iv_matrix.setImageResource(R.drawable.camera_fbl_320);
				}
			}
		});

		iv_finish.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				finishCamera();
			}
		});
		iv_cancel.setOnTouchListener(otl);
		iv_reset.setOnTouchListener(otl);
		iv_flash.setOnTouchListener(otl);
		iv_matrix.setOnTouchListener(otl);
		iv_finish.setOnTouchListener(otl);
		FrameLayout fl = ((FrameLayout) findViewById(R.id.preview));
		fl.addView(preView);

	}

	private void init1()
	{
		OnTouchListener touchListener = new OnTouchListener()
		{
			public boolean onTouch(View v, MotionEvent event)
			{
				int action = event.getAction();
				TextView tv = (TextView) v;
				if (action == MotionEvent.ACTION_DOWN)
				{
					tv.setBackgroundResource(R.drawable.camera_down);
				}
				else if (action == MotionEvent.ACTION_UP)
				{
					tv.setBackgroundResource(R.drawable.camera_up);
				}
				return false;
			}
		};

		tv_cancel = (TextView) findViewById(R.id.camera_textview1);
		tv_reset = (TextView) findViewById(R.id.camera_textview2);
		tv_flash = (TextView) findViewById(R.id.camera_textview3);
		tv_matrix = (TextView) findViewById(R.id.camera_textview4);
		tv_finish = (TextView) findViewById(R.id.camera_textview5);

		tv_cancel.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				cancelCamera();
			}
		});

		tv_reset.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				String s = tv_reset.getText().toString();
				if (s.equals("拍摄"))
				{
					takePicture();
				}
				else if (s.equals("重拍"))
				{
					resetCamera();
				}
			}
		});

		tv_flash.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				if (flashMode.equals("auto"))
				{
					flashMode = "on";
					tv_flash.setText("闪光\n开");
				}
				else if (flashMode.equals("on"))
				{
					flashMode = "off";
					tv_flash.setText("闪光\n关");
				}
				else if (flashMode.equals("off"))
				{
					flashMode = "auto";
					tv_flash.setText("闪光\n自动");
				}
			}
		});

		tv_matrix.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				if (type == 4)
				{
					type = 0;
				}
				else
				{
					type += 1;
				}
				// if(type ==0 || type ==3){
				// tv_matrix.setText("分辨率\n800×600");
				// }else if(type==1){
				// tv_matrix.setText("分辨率\n320×240");
				// }else if(type==2){
				// tv_matrix.setText("分辨率\n640×480");
				// }else if(type==4){
				// tv_matrix.setText("分辨率\n1024×768");
				// }
				if (type == 0 || type == 3)
				{
					tv_matrix.setText("分辨率\n640×480");
				}
				else if (type == 1)
				{
					tv_matrix.setText("分辨率\n800×600");
				}
				else if (type == 2)
				{
					tv_matrix.setText("分辨率\n320×240");
				}
			}
		});

		tv_finish.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				finishCamera();
			}
		});
		tv_cancel.setOnTouchListener(touchListener);
		tv_reset.setOnTouchListener(touchListener);
		tv_flash.setOnTouchListener(touchListener);
		tv_matrix.setOnTouchListener(touchListener);
		tv_finish.setOnTouchListener(touchListener);
		FrameLayout fl = ((FrameLayout) findViewById(R.id.preview));
		fl.addView(preView);

	}

	private void takePicture()
	{
		state_take = 1;
		preView.setFlashMode(flashMode);
		preView.takePicture();
		if (panel_type == 0)
		{
			iv_reset.setImageResource(R.drawable.camera_reset);
		}
		else if (panel_type == 1)
		{
			tv_reset.setText("重拍");
		}
	}

	class MyThread extends Thread
	{
		ProgressDialog progressDialog = null;
		public boolean flag = true;

		private void showProcess()
		{
			progressDialog = new ProgressDialog(ActivityCamera.this);
			if (panel_type == 0)
			{
				// progressDialog.setMessage("");
			}
			else if (panel_type == 1)
			{
				progressDialog.setMessage("请稍候...");
			}
			progressDialog.setIndeterminate(true);
			if (panel_type == 0)
			{
				// progressDialog.show();
			}
			else if (panel_type == 1)
			{
				progressDialog.show();
			}
			start();
		}

		public void run()
		{
			while (flag)
			{
				try
				{
					sleep(100);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			try
			{
				if (Config.windowType == 1)
				{
					sleep(500);
				}
				else if (Config.windowType == 0)
				{
					sleep(6000);
				}
				else
				{
					sleep(4000);
				}
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			progressDialog.dismiss();
		}
	}

	private void resetCamera()
	{
		state_take = 0; // 修改拍照按钮标识
		if (panel_type == 0)
		{
			iv_reset.setImageResource(R.drawable.camera_take);
		}
		else if (panel_type == 1)
		{
			tv_reset.setText("拍摄");
		}
		preView.restart();
		// 释放刚去的的图片
		if (bitmap != null)
		{
			Bitmap tmp = bitmap;
			bitmap = null;
			tmp.recycle();
		}
	}

	// 完成
	private void finishCamera()
	{
		if (panel_type == 0)
		{
			if (state_take == 0 || bitmap == null)
			{
				return;
			}
		}
		else if (panel_type == 1)
		{
			String s = tv_reset.getText().toString();
			if (!s.equals("重拍") || bitmap == null)
				return;
		}

		preView = null;

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssms");
		String fileName = sdf.format(date) + ActivityPhoto.fileType;

		// 先保存

		try
		{
			File file = new File(ActivityPhoto.path + fileName);
			file.getParentFile().mkdirs();
			// file.createNewFile();
			// FileOutputStream fOut = new FileOutputStream(file);
			// //保存bitmap
			// bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fOut);
			// fOut.flush();
			// fOut.close();
			// fOut = null;
			// bitmap.recycle();
			// bitmap = null;
			// System.gc();
			//	    	
			// //再读出来
			// bitmap = BitmapFactory.decodeFile(ActivityPhoto.path+fileName);
			bitmap = resizedBitmap(bitmap);
			print.out("getWidth=" + bitmap.getWidth());
			print.out("getHeight=" + bitmap.getHeight());
			print.out(file.toString());
			FileOutputStream fOut = new FileOutputStream(file);

			bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fOut);
			fOut.flush();
			fOut.close();
			bitmap.recycle();
			bitmap = null;
			System.gc();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		Intent intent = new Intent(this, ActivityPhoto.class);
		Bundle b = new Bundle();
		b.putString("result", "finish");
		b.putString("filename", fileName);
		intent.putExtras(b);
		setResult(RESULT_OK, intent);
		finish();
	}

	private void cancelCamera()
	{
		preView = null;
		finish();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// print.out("onKeyDown is call-------------");
		// print.out("keycode ="+keyCode);
		// //80 轻按下，预览
		// //27 拍摄
		if (state_take == 0)
		{
			if (keyCode == 27 || keyCode == 23)
			{ // 27照相机键,17滚珠按下
				print.out("按下了,按键");
				takePicture();
			}
			else
			{
				print.out("按键码是" + keyCode);
			}
		}
		// 21上,22下

		// print.out("按键码是"+keyCode);
		// if(keyCode==21){
		// preView.zoomIn();
		// return true;
		// }else if(keyCode==22){
		// preView.zoomOut();
		// return true;
		// }else
		if (keyCode == 4)
		{
			cancelCamera();
		}
		return super.onKeyDown(keyCode, event);
	}

	public Bitmap resizedBitmap(Bitmap bitmap) throws Exception
	{
		// print.out("resizedBitmap is call");
		 Matrix matrix = new Matrix();
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float scale = 1;
		// if(type==0 || type==3){
		// //800*600
		// scale = 800f /width;
		// }else if(type==1){
		// //320*240
		// scale = 320f /width;
		// }else if(type ==2){
		// //640*480
		// scale = 640f /width;
		// }else if(type==4){
		// //1024*768
		// scale = 1024f /width;
		// }else if(type==5){
		// //2048*1536
		// }
		if (type == 0 || type == 3)
		{
			// 640*480
			scale = 640f / width;
		}
		else if (type == 1)
		{
			// 800*600
			scale = 800f / width;
		}
		else if (type == 2)
		{
			// 320*240
			scale = 320f / width;
		}
		// 不管怎么样,都要重绘,因为,变换了方向
		// print.out("scale="+scale);

		
//		  matrix.postScale(scale, scale); 
//		  matrix.postRotate(90);//方向变换 Bitmap
//		  resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
//		  matrix, true); 
//		  bitmap.recycle(); 
//		  bitmap = null; 
//		  System.gc(); //提醒系统回收
		  
//		  return resizedBitmap;
		 
		return Assistant.resizeBitmap(bitmap, width, height, scale, 90);
	}

	public void setPicture(Bitmap tmp)
	{
		bitmap = tmp;
	}

}