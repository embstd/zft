package com.kinview.camera;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.kinview.assistant.Assistant;
import com.kinview.zft.R;
import com.kinview.config.Config;
import com.kinview.config.print;
import com.kinview.thread.ThreadGetMedia;

public class ActivityPhoto4Download extends Activity {
	/** Called when the activity is first created. */
	public static final int ITEM0 = Menu.FIRST;
	public static final int ITEM1 = Menu.FIRST + 1;
	private Button btn_new,btn_select,btn_del,btn_cancel,btn_finish;
	private ListView listview;
//	public static String path = "/sdcard/";
	public static String path = Config.appPath + "pic/";
	public static String fileType = ".jpg";
	private PhotoAdapter4Download adapter = null;
//	private ArrayList<String> dialogMenu = new ArrayList<String>();
	private int selectPosition = 0;
	public static float pictureSize = 50f;//Õº∆¨¥Û–°100œÒÀÿ
	public String filesName ="";
	PhotoMgr pm = new PhotoMgr();
	ArrayList<Photo> listPhoto =null;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.photo);
        init();
        
        Bundle b = getIntent().getExtras();
        if(b!=null){
        	filesName = b.getString("filesname");
        }
        print.out("filesName="+filesName);
        
        //º”‘ÿÕº∆¨
//        if(PhotoAdapter4Download.mapBitmap.size()==0){
//        	MyThread thread = new MyThread();
//        	thread.showProcess();
//        }else{
//        	showList();
//        }
        MyThread thread = new MyThread();
    	thread.showProcess();
    }
    
    private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				showList();
				break;
			}
		}
	};
    
    class MyThread extends Thread{
    	ProgressDialog progressDialog =null;
		
		private void showProcess(){
			progressDialog = new ProgressDialog(ActivityPhoto4Download.this);
			progressDialog.setMessage("’˝‘⁄º”‘ÿ’’∆¨ø‚,«Î…‘∫Ú...");
			progressDialog.setIndeterminate(true);
			progressDialog.show();
			start();
		}
		
		public void run(){
			//œ¬‘ÿÕº∆¨
			if(!filesName.equals("")){
				ThreadGetMedia threadMedia = new ThreadGetMedia(filesName,".jpg");
				threadMedia.start();
				try {
					threadMedia.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			listPhoto = pm.getItemsList(filesName);
			
			for(int i=0;i<listPhoto.size();i++){
				String name = listPhoto.get(i).getName();
				if(PhotoAdapter4Download.mapBitmap.get(name)==null){
					File file = new File(name);
					if(file.exists())
						PhotoAdapter4Download.mapBitmap.put(name, loadBitmap(name));
				}
			}
			mHandler.sendMessageDelayed(mHandler.obtainMessage(1,0), 0);
			progressDialog.dismiss();
		}
    }
    
    private Bitmap loadBitmap(String name){
		//º”‘ÿ‘§¿¿Õº∆¨
//    	print.out("name="+name);
//    	
//		FileInputStream fStream=null;
//		try {
//			fStream = new FileInputStream(path+name);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		Bitmap bitmap = BitmapFactory.decodeStream(fStream);
//		bitmap = resizedBitmap(bitmap);
//		return bitmap;
    	return Assistant.getResizeBitmapByFileName(path+name, pictureSize);
	}
    
//    public Bitmap resizedBitmap(Bitmap bitmap){
//		Matrix matrix = new Matrix();
//		int width = bitmap.getWidth();
//		int height = bitmap.getHeight();
//		float scale = 1;
//		
//		int temp=width>height?width:height;
//		
//		scale = pictureSize/temp;
//		
//		if(scale !=1){
//			try{
//				matrix.postScale(scale, scale);
//				Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
//						height, matrix, true);
//				bitmap.recycle();
//				return resizedBitmap;
//			}catch(Exception e){
//				return null;
//			}
//		}else{
//			return bitmap;
//		}
//	}
    
    private void init(){
    	btn_new = (Button)findViewById(R.id.photo_button1);
    	btn_select = (Button)findViewById(R.id.photo_button2);
    	btn_del = (Button)findViewById(R.id.photo_button3);
    	btn_cancel = (Button)findViewById(R.id.photo_button4);
    	listview = (ListView)findViewById(R.id.photo_listview1);
    	btn_finish = (Button)findViewById(R.id.photo_button5);
    	
    	btn_cancel.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				finish();
			}
    	});
    	
    	btn_new.setVisibility(View.INVISIBLE);
		btn_select.setVisibility(View.INVISIBLE);
		btn_del.setVisibility(View.INVISIBLE);
		btn_finish.setVisibility(View.INVISIBLE);
		btn_cancel.setText("∑µªÿ");
    	
    }
    

    
    private void showList() {
    	
    	adapter = new PhotoAdapter4Download(this,path,listPhoto);
    	listview.setAdapter(adapter);
    	listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				selectPosition = pos;
				Intent intent = new Intent(ActivityPhoto4Download.this,ActivityPictureView.class);
	    		Bundle b = new Bundle();
	    		String url_image = path + adapter.getItemPhoto(selectPosition).getName();
	    		b.putString("url_image", url_image);
	    		intent.putExtras(b);
	    		startActivity(intent);
			}
		});
    	
    }
    
    @SuppressWarnings("unused")
	private String getMIMEType(File f)
    {
      String end = f.getName().substring(
          f.getName().lastIndexOf(".") + 1, f.getName().length())
          .toLowerCase();
      String type = "";
      if (end.equals("mp3") || end.equals("aac") || end.equals("aac")
          || end.equals("amr") || end.equals("mpeg")
          || end.equals("mp4"))
      {
        type = "audio";
      }
      else if (end.equals("jpg") || end.equals("gif")
          || end.equals("png") || end.equals("jpeg"))
      {
        type = "image";
      }
      else
      {
        type = "*";
      }
      type += "/*";
      return type;
    }
    
    
}