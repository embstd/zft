package com.kinview.camera;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.kinview.assistant.Assistant;

import com.kinview.zft.R;
import com.kinview.zft.newcase.ActivityNewCaseByOrganise;
import com.kinview.zft.newcase.ActivityNewCaseByPerson;
import com.kinview.config.Config;
import com.kinview.config.print;
import com.kinview.entity.NewCase;

public class ActivityPhoto extends Activity {
	
	public static String path = Config.appPath;
	public static String fileType = ".jpg";
	public static int timeLimit = 1440; //����,ͼƬʹ��ʱ������
	public static int windowType=0;		//0ΪĬ�ϴ���Ļ,1Ϊ320*478����480��
//	public static int windowType=1;		//0ΪĬ�ϴ���Ļ,1Ϊ320*478����480��
	public static final int ITEM0 = Menu.FIRST;
	public static final int ITEM1 = Menu.FIRST + 1;
	private Button btn_new,btn_select,btn_del,btn_cancel,btn_finish;
	private ListView listview;
	private PhotoAdapter adapter = null;
	private ArrayList<String> dialogMenu = new ArrayList<String>();
	private int selectPosition = 0;
	public static float pictureSize = 50f;//ͼƬ��С100����,Ԥ��ͼƬ
	
	PhotoMgr pm = new PhotoMgr();
	ArrayList<Photo> listPhoto =null;
	
	public static boolean stytle = false;	//�ж��Ǵ�false���������,��true�Ǵ������ϱ�����
	public static boolean op = false; //�ж��Ǵ�Person�����,��Organ�Ǵ������ϱ�����
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.photo);
        windowType = Config.windowType;
        getBundle();
        getBundle2();
        init();
        initMenu();
        //�жϴ����ַ
        int count = 0;
        String data ="";
        if(stytle){
    		Intent it = getIntent();
        	Bundle b = it.getExtras();
    		data = b.getString("data");
    		print.out("ActivityPhoto get data from bunle="+data);
    		count = b.getInt("count");
    		if(count>0){
//    			adapter.setSelect(data);
    		}
    	}
    
        //���ͼƬû�м���,���ȼ���ͼƬ
        listPhoto = pm.getItemsList();
        if(count>0){
        	adapter = new PhotoAdapter(this,mHandler,path,listPhoto,data);
        }else{
        	adapter = new PhotoAdapter(this,mHandler,path,listPhoto);
        }
        if(PhotoAdapter.mapBitmap.size()==0){
        	MyThread thread = new MyThread();
        	thread.showProcess();
        }else{
        	showList();
        }
//        setSelect();//��ʾ��ѡ���ͼƬ
    }
    
    private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				String s = (String)msg.obj;
				showToast(s);
				break;
			case 1:
				showList();
				break;
			}
		}
	};
    
    class MyThread extends Thread{
    	ProgressDialog progressDialog =null;
		
		private void showProcess(){
			progressDialog = new ProgressDialog(ActivityPhoto.this);
			progressDialog.setMessage("���ڼ�����Ƭ��,���Ժ�...");
			progressDialog.setIndeterminate(true);
			progressDialog.show();
			start();
		}
		
		public void run(){
			for(int i=0;i<listPhoto.size();i++){
				String name = listPhoto.get(i).getName();
				PhotoAdapter.mapBitmap.put(name, loadBitmap(name));
			}
			mHandler.sendMessageDelayed(mHandler.obtainMessage(1,0), 0);
			progressDialog.dismiss();
		}
    }
    

    
    private void getBundle(){
    	Intent it = getIntent();
    	Bundle b = it.getExtras();
    	if(b!=null){
    		stytle = b.getBoolean("stytle");
    	}
    }
    
    private void getBundle2(){
    	Intent it = getIntent();
    	Bundle b = it.getExtras();
    	if(b!=null){
    		op = b.getBoolean("op");
    	}
    }
    
    private Bitmap loadBitmap(String name){
		//����Ԥ��ͼƬ
//		FileInputStream fStream=null;
//		try {
//			fStream = new FileInputStream(path+name);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		Bitmap bitmap = BitmapFactory.decodeStream(fStream);
//		try {
//			bitmap = resizedBitmap(bitmap);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return bitmap;
    	return Assistant.getResizeBitmapByFileName(path+name, pictureSize);
	}
    
//    public Bitmap resizedBitmap(Bitmap bitmap)throws Exception{
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
//			matrix.postScale(scale, scale);
//			Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,
//					height, matrix, true);
//			bitmap.recycle();
//			bitmap = null;
//			System.gc();
//			return resizedBitmap;
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
    	
    	btn_new.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				Intent it = new Intent(ActivityPhoto.this,ActivityCamera.class);
				startActivityForResult(it,0);
			}
    	});
    	
    	btn_del.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				if(adapter.getListSelect().size()>0)
					showDialog();
			}
    	});
    	
    	btn_select.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				adapter.setSelectAll();
			}
    	});
    	
    	btn_cancel.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				finish();
			}
    	});
    	
    	btn_finish.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				Intent it = null;
				if(op)
				{
					it = new Intent(ActivityPhoto.this,ActivityNewCaseByOrganise.class);//�޸�
				}
				else 
				{
					it = new Intent(ActivityPhoto.this,ActivityNewCaseByPerson.class);//�޸�
				}
//				Intent it = new Intent(ActivityPhoto.this,ActivityNewCaseByOrganise.class);//�޸�
				Bundle b = new Bundle();
				List<String> listSelect = adapter.getListSelect();
				int count = listSelect.size();
				String data = "";
				for(int i=0;i<count;i++){
					data += path + listSelect.get(i) +",";
				}
				b.putString("type", "pic");
				b.putString("data", data);
				b.putInt("count", count);
				it.putExtras(b);
				setResult(RESULT_OK, it);
//				startActivity(it);
				finish();
			}
    	});
    	
    	if(!stytle){
    		btn_select.setVisibility(View.VISIBLE);
    		btn_del.setVisibility(View.VISIBLE);
    		btn_finish.setVisibility(View.INVISIBLE);
    	}else{
    		btn_select.setVisibility(View.INVISIBLE);
    		btn_del.setVisibility(View.INVISIBLE);
    		btn_finish.setVisibility(View.VISIBLE);
    	}
    }
    
    private void initMenu(){
    	dialogMenu.add("���");
//    	if(type){
//    		dialogMenu.add("��ӵ�����");
//    	}
    	
    }
    
    private void deleteItem(){
    	List<String> listSelect = adapter.getListSelect();
    	ArrayList<String> delList = new ArrayList<String>();
    	
		for(int i= 0;i<listSelect.size();i++){
			String filename = listSelect.get(i);
			print.out("delete name="+filename);
			File file = new File(path + filename);
			if(file.exists()){
//				if(!Assistant.findPicReference(filename)){
				if(!findPicReference(filename)){
					//���û�з����������õ�ͼƬ
					print.out("�ļ�"+filename+"����,����ɾ��");
					for(int j=0;j<adapter.listItem.size();j++){
						if(adapter.listItem.get(j).getName().equals(filename)){
							adapter.RemoveItem(j);
							break;
						}
					}
					file.delete();
					delList.add(filename);
//					listSelect.remove(filename);
				}else{
					showToast("ͼƬ"+filename +"����ʹ��,��ʱ����ɾ��!");
				}
			}
		}
		
		for(int i=0;i<delList.size();i++){
			listSelect.remove(delList.get(i));
		}
		listSelect.clear();
    }
    
    private void showList() {
    	
//    	System.out.println("----photoMgr is ok----");
    	
//    	System.out.println("----CheckBoxAdapter is ok----");
    	listview.setAdapter(adapter);
    	listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				selectPosition = pos;
				Dialog dialog = onCreateDialog(1);
				dialog.show();
			}
		});
    	
    }
    
    public void showToast(String msg) {
		Toast toast = Toast
				.makeText(this, msg, Toast.LENGTH_SHORT);
		toast.show();
	}
    
    private void showDialog() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setIcon(R.drawable.alert_dialog_icon);
		builder.setTitle("ɾ��ͼƬ");
		builder.setMessage("��ȷ��Ҫɾ��ѡ����Ŀ��?");
		builder.setPositiveButton("ȷ  ��",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						deleteItem();
					}
				});
		builder.setNegativeButton("ȡ  ��",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});
		builder.show();

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
    
    @Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
    	switch(id){
    	case 1:
    		int size = dialogMenu.size();
    		String[] str = new String[size]; 
    		for(int i=0 ;i<size;i++){
    			str[i] = (String)dialogMenu.get(i);
    		}
    		return new AlertDialog.Builder(this)
            .setTitle("�˵�") // ���ñ���
            .setIcon(R.drawable.record_dialogtitle) // ���ñ���ͼƬ
            .setItems(str, new DialogInterface.OnClickListener() { // ��������ʾ����������һ�����顣�����е����ݻ�һ��һ�е���������
              public void onClick(DialogInterface dialog, int position) {
//                ActivityMain.this.setTitle("�������Ŀ"+ which);
                menuAction(position);
              }
            })
            .create();
		default:
			return null;
    	}
	}
    
    public void menuAction(int position){
    	switch(position){
    	//��һλ����ѡ��,���뵽����
    	case 0:
    		Intent intent = new Intent(this,ActivityPictureView.class);
    		Bundle b = new Bundle();
    		String url_image = path + adapter.getItemPhoto(selectPosition).getName();
    		b.putString("url_image", url_image);
    		intent.putExtras(b);
    		startActivity(intent);
    		break;
    	//�ڶ���λ���ǲ����ļ�
    	case 1:
//			photo term = adapter.listphoto.get(selectPosition);
//			String filepath = path + term.getName();
//			File f = new File(filepath);
//			Intent intent = new Intent();
//		    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		    intent.setAction(android.content.Intent.ACTION_VIEW);
//		    String type = getMIMEType(f);
//		    intent.setDataAndType(Uri.fromFile(f), type);
//		    startActivity(intent);
    		break;
    	}
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		
		try{
			Bundle b = intent.getExtras();
			String result = b.getString("result");
			if(result!=null){
				if(result.equals("finish")){
					String fileName = b.getString("filename");
					PhotoMgr rm = new PhotoMgr();
	//				print.out("filename="+fileName);
					Photo photo = rm.loadItemByFileName(fileName);
					if(photo!=null)
						adapter.addItem(photo);
				}
			}
		}catch(Exception e){
			
		}

	}
    
    public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, ITEM0, 0, "ɾ������");//.setIcon(R.drawable.menu_setting);
		menu.add(0, ITEM1, 0, "ɾ��ѡ��");//.setIcon(R.drawable.menu_help);
		menu.findItem(ITEM0);
		return true;
	}
    
    public boolean onOptionsItemSelected(MenuItem item) {
		// return super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
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
    	showDialog2();
    }
    
    private void actionClickMenuItem2(){
    	if(adapter.getListSelect().size()>0)
			showDialog();
    }
    
    private void showDialog2() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setIcon(R.drawable.alert_dialog_icon);
		builder.setTitle("ɾ��ͼƬ");
		builder.setMessage("��ȷ��Ҫɾ��������Ŀ��?");
		builder.setPositiveButton("ȷ  ��",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						deleteItem2();
					}
				});
		builder.setNegativeButton("ȡ  ��",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					}
				});
		builder.show();

	}

	private void deleteItem2(){
		
		ArrayList<String> delList = new ArrayList<String>();
		List<String> listSelect = adapter.getListSelect();
		
		File[] files = (new File(Config.appPath)).listFiles();
		
		for(int i=0;i<files.length;i++){
			File file = files[i];
			String filename = file.getName();
			if(!file.isDirectory() && filename.endsWith(".jpg")){
//				if(!Assistant.findPicReference(filename)){
				if(!findPicReference(filename)){
					//���û�з����������õ�ͼƬ
					for(int j=0;j<adapter.listItem.size();j++){
						if(adapter.listItem.get(j).getName().equals(filename)){
							adapter.RemoveItem(j);
//							print.out("find item del item!");
							break;
						}
					}
					file.delete();
					delList.add(filename);
				}else{
					showToast("ͼƬ"+filename +"����ʹ��,��ʱ����ɾ��!");
				}
			}
		}
		
		for(int i=0;i<delList.size();i++){
			listSelect.remove(delList.get(i));
		}
		adapter.notifyDataSetChanged();
	}
    
	
	public  boolean findPicReference(String name){
		boolean b = false;
		for(int i=0;i<Config.listNewCases.size();i++){
			NewCase history = Config.listNewCases.get(i);
			if(history.getPicNum()>0 && history.getState()!=11){
				if(history.getPicList().indexOf(name)!=-1)
					b = true;
			}
		}
		return b;
	}
}