package com.kinview.zft.newcase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.Format.Field;
import java.util.ArrayList;
import org.kobjects.base64.Base64;

import com.kinview.assistant.Assistant;
import com.kinview.camera.ActivityCamera;
import com.kinview.camera.ActivityPhoto;
import com.kinview.config.Config;
import com.kinview.config.Dialog;
import com.kinview.config.print;
import com.kinview.entity.NewCase;
import com.kinview.util.NewCaseService;
import com.kinview.zft.ActivityLogin;
import com.kinview.zft.R;
import com.kinview.zft.casehandle.ActivityCaseHandle;
import com.kinview.zft.newcase.ActivityNewCaseByPerson.MyThread;

import android.util.Log;  
import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Xml.Encoding;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ActivityNewCaseByOrganise extends Activity
{
	MyThread SubmitThread = null;
	public int  stateNum = 99;
	private String id_lsh ="";
	private Spinner spinner1 = null;
	private Spinner spinner2 = null;
	private Spinner spinner3 = null;
	private Spinner spCasely = null;
	public String[][] temp1;
	public String[][] temp2;
	public String[][] temp3;
	String tempID1 = ""; // 已选择项的ID，记录要传输的ID，该ID为获取选择项的必要条件
	String tempID2 = ""; // 案由
	String tempID3 = ""; // 违法行为
	Context context = this;
	public EditText etTime;
	private NewCase newcasebyorganise = null;
	private CheckBox checkBoxPic = null;
	private EditText nco_etName, nco_etFzr, nco_etZw, nco_etTell,
			nco_etAddress, nco_etOpenAdd, nco_etOpenTime, nco_etintro;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newcasebyorganise);
		load();

		spinner1.setOnItemSelectedListener(onItemSelectedListener1);
		spinner2.setOnItemSelectedListener(onItemSelectedListener2);
		spinner3.setOnItemSelectedListener(onItemSelectedListener3);
	
	}

	private void load()
	{
		newcasebyorganise = new NewCase();
		// TODO Auto-generated method stub
		nco_etName = (EditText) findViewById(R.id.nco_etName);
		nco_etFzr = (EditText) findViewById(R.id.nco_etFzr);
		nco_etZw = (EditText) findViewById(R.id.nco_etZw);
		nco_etTell = (EditText) findViewById(R.id.nco_etTell);
		nco_etAddress = (EditText) findViewById(R.id.nco_etAddress);
		nco_etOpenAdd = (EditText) findViewById(R.id.nco_etOpenAdd);
		nco_etOpenTime = (EditText) findViewById(R.id.nco_etOpenTime);
		nco_etintro = (EditText) findViewById(R.id.nco_etintro);

		checkBoxPic = (CheckBox) findViewById(R.id.nco_cbPic);
		etTime = (EditText) findViewById(R.id.nco_etOpenTime);
		etTime.setText(Config.timebyYYYYmmDD());
		spinner1 = (Spinner) findViewById(R.id.Spinner1);
		spinner2 = (Spinner) findViewById(R.id.Spinner2);
		spinner3 = (Spinner) findViewById(R.id.Spinner3);
		spCasely = (Spinner)findViewById(R.id.nco_spCaselaiyuan);
		ArrayList<String> list_smalltype = new ArrayList<String>();
		NewCaseService newCase = new NewCaseService(this);
		temp1 = newCase.CaseType1("0");
		for (int i = 0; i < temp1[1].length; i++)
		{
			list_smalltype.add(temp1[1][i]);
		}
		ArrayAdapter<String> adapter_smalltype = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list_smalltype);
		adapter_smalltype
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter_smalltype);
		
		ArrayList<String> caselisttype = new ArrayList<String>();
		String[] ss = new String[]{"投诉、举报","巡查发现","交办","移送","媒体曝光","12319","其他"};
		for (int is = 0; is < ss.length; is++)
		{
			caselisttype.add(ss[is]);
		}
		ArrayAdapter<String> adapter_casetype = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, caselisttype);
		adapter_casetype
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spCasely.setAdapter(adapter_casetype);
		
	}
	
	
	

	private Spinner.OnItemSelectedListener onItemSelectedListener1 = new Spinner.OnItemSelectedListener()
	{
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			int selectedItemid = spinner1.getSelectedItemPosition();
			// print.out("selectedItemid ="+ selectedItemid);
			tempID1 = temp1[0][selectedItemid].toString();
			// print.out("tempID1="+tempID1);
			ArrayList<String> list_smalltype = new ArrayList<String>();
			NewCaseService newCase1 = new NewCaseService(context);
			temp2 = newCase1.CaseType1(tempID1);
			for (int i = 0; i < temp2[1].length; i++)
			{
				list_smalltype.add(temp2[1][i]);
			}
			ArrayAdapter<String> adapter_smalltype = new ArrayAdapter<String>(
					context, android.R.layout.simple_spinner_item,
					list_smalltype);
			adapter_smalltype
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner2.setAdapter(adapter_smalltype);
		}

		public void onNothingSelected(AdapterView<?> arg0)
		{
			// TODO Auto-generated method stub
		}
	};

	private Spinner.OnItemSelectedListener onItemSelectedListener2 = new Spinner.OnItemSelectedListener()
	{
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			int selectedItemid = spinner2.getSelectedItemPosition();
			// print.out("selectedItemid ="+ selectedItemid);
			tempID2 = temp2[0][selectedItemid].toString();
			// print.out("tempID1="+tempID1);
			ArrayList<String> list_smalltype = new ArrayList<String>();
			NewCaseService newCase1 = new NewCaseService(context);
			temp3 = newCase1.CaseType1(tempID2);
			for (int i = 0; i < temp3[1].length; i++)
			{
				list_smalltype.add(temp3[1][i]);
			}
			ArrayAdapter<String> adapter_smalltype = new ArrayAdapter<String>(
					context, android.R.layout.simple_spinner_item,
					list_smalltype);
			adapter_smalltype
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner3.setAdapter(adapter_smalltype);
		}

		public void onNothingSelected(AdapterView<?> arg0)
		{
			// TODO Auto-generated method stub
		}
	};

	private Spinner.OnItemSelectedListener onItemSelectedListener3 = new Spinner.OnItemSelectedListener()
	{
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3)
		{
			int selectedItemid = spinner3.getSelectedItemPosition();
			// print.out("selectedItemid ="+ selectedItemid);
			tempID3 = temp3[0][selectedItemid].toString();
		}

		public void onNothingSelected(AdapterView<?> arg0)
		{
			// TODO Auto-generated method stub
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		print.out("onKeyDown()");
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			
//			System.exit(0);
			this.finish();
			return true;
		}
		else if (keyCode == KeyEvent.KEYCODE_MENU)
		{
			print.out("2");
		}
		return super.onKeyUp(keyCode, event);
	}

	/**
	 * 设置菜单
	 */
	public boolean onCreateOptionsMenu(Menu menu)
	{
		
		super.onCreateOptionsMenu(menu);
		// Menus.initMenu(this, menu);
		initMenu(this, menu);
		return true;
	}

	public static void initMenu(Context context, Menu menu)
	{
		
		if (context.getClass().equals(ActivityNewCaseByOrganise.class))
		{
			menu.add(0, 0, 0, "照片").setIcon(R.drawable.newcase_tab5);
			// menu.add(0, 1, 0, "地图").setIcon(R.drawable.newcase_tab1);
			menu.add(0, 2, 0, "上报案件").setIcon(R.drawable.newcase_tab4);
			menu.add(0, 3, 0, "个人案件").setIcon(R.drawable.newcase_tab1);
			menu.add(0, 4, 0, "退出").setIcon(R.drawable.newcase_tab3);
		}
	}

	/**
	 * 菜单处理
	 */
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Menus.actionMenu(this, null, item.getItemId());
		actionMenu(this, null, item.getItemId());
		return super.onOptionsItemSelected(item);
	}

	public void actionMenu(Context context, Handler mHandler, int menuId)
	{
		if (context.getClass().equals(ActivityNewCaseByOrganise.class))
		{
			switch (menuId)
			{
			case 0: // 照片
				Intent itimg = new Intent(ActivityNewCaseByOrganise.this,
						ActivityPhoto.class);
				Bundle b = new Bundle();
				b.putBoolean("stytle", true);
				b.putBoolean("op", true);
				b.putString("data", newcasebyorganise.getPicList());
				b.putInt("count", newcasebyorganise.getPicNum());
				itimg.putExtras(b);
				startActivityForResult(itimg, 0);
				break;
			case 1: // 保留地图
				break;
			case 2: // 上报案件
				try
				{
					Submit();
				}
				catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 3:
				
				finish();
				Intent it = new Intent(ActivityNewCaseByOrganise.this,
						ActivityNewCaseByPerson.class);
				startActivity(it);
				break;
			case 4:
				
				finish();
				break;
			}

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent)
	{
		super.onActivityResult(requestCode, resultCode, intent);

		try
		{
		
			Bundle b = intent.getExtras();
			if (b != null)
			{
				String type = b.getString("type");
				if (type.equals("pic"))
				{
					String data = b.getString("data");
				    int count = b.getInt("count");
				    if (count > 0)
					{
						setCheck(2, true);
						newcasebyorganise.setPicNum(count);
						newcasebyorganise.setPicList(data);
					}
					else
					{
						newcasebyorganise.setPicNum(0);
						newcasebyorganise.setPicList("");
						setCheck(2, false);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private void setCheck(int id, boolean visible)
	{
		switch (id)
		{
         case 2:
			if (visible)
			{
				checkBoxPic.setText("照片已选");
                checkBoxPic.setChecked(visible);
				// b_pic=true;
			}
			else
			{
				checkBoxPic.setText("照片选择");
                checkBoxPic.setChecked(visible);
				// b_pic=false;
			}
			break;

		}

	}

	private void Submit() throws Exception
	{
		// TODO Auto-generated method stub
		if(nco_etName.getText().length() == 0)
		{
			showToast("请填写组织名称!");
			return;
		}
		if(nco_etOpenAdd.getText().length() == 0 || nco_etOpenTime.getText().length() == 0)
		{
			showToast("案发地点和案发时间不能为空！");
			return;
		}
		
//		print.out("selectedItemid=")
		newcasebyorganise.setState(1);
		newcasebyorganise.setFromtype(spCasely.getSelectedItemPosition()+1);
		newcasebyorganise.setCaseType (1);
		newcasebyorganise.setCreateTime(Config.timebyYYYYmmDD());
		newcasebyorganise.setCreate (Config.user.getUserid());
		newcasebyorganise.setCaseAjlb (tempID1);
		newcasebyorganise.setCaseAnYou (tempID2);
		newcasebyorganise.setCaseWeiFaXW (tempID3);
		newcasebyorganise.setOrganise (nco_etName.getText().toString()); // 组织名称
		newcasebyorganise.setFzr (nco_etFzr.getText().toString());
		newcasebyorganise.setZw ( nco_etZw.getText().toString());
		newcasebyorganise.setOtel ( nco_etTell.getText().toString());
		newcasebyorganise.setOadd (nco_etAddress.getText().toString());
		newcasebyorganise.setAddress ( nco_etOpenAdd.getText().toString());
		newcasebyorganise.setThetime (nco_etOpenTime.getText().toString());
		newcasebyorganise.setIntro ( nco_etintro.getText().toString());
		newcasebyorganise.setType(1);// type = 1 组织 type = 2 个人
		newcasebyorganise.setAreaid (Integer.valueOf(Config.branch));
		newcasebyorganise.setPicNum(getPicNum(newcasebyorganise.getPicList()));
		newcasebyorganise.setPicList(getPicList(newcasebyorganise.getPicList()));
		SubmitThread  = new MyThread();
		SubmitThread.showProcess();
		
	}
	
	
	
	public Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg)
		{
			// TODO Auto-generated method stub
			switch(msg.what){
			case  0:
				print.out("t获取stateNum值后得出"+stateNum);
				switch (stateNum)
				{
					case 10000:
						Alert("案件上报失败","错误代码10000！确定后将退出！","确定",windowClose);
						break;
					case 10001:
						Alert("案件上报失败","图片保存失败确定后将退出！","确定",windowClose);
						break;
					case 20000:
						Alert("案件上报失败","与服务器连接超时！","确定",windowClose);
						break;
					case 20001:
						Alert("案件上报成功本地保存失败","错误代码20001！","确定",windowClose);
						break;
					case 0:
						go();
						break;
					case 99:
						Alert("案件上报失败","错误代码99！确定后将退出！","确定",windowClose);
						break;
					default:
						break;
				}
				break;
			}
		}
		
	};
	
	class MyThread extends Thread
	{
		ProgressDialog submitProgressDialog = null;
        private void showProcess()
		{

			submitProgressDialog = new ProgressDialog(ActivityNewCaseByOrganise.this);
			submitProgressDialog.setMessage("正在上报,请稍候...");
			submitProgressDialog.setIndeterminate(true);
			try
			{
				submitProgressDialog.show();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			start();
		}

		public void run()
		{

			try
			{
				stateNum = HistorySubmit();
				print.out("stateNum值为："+stateNum);
				mHandler.sendEmptyMessage(0);
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			submitProgressDialog.dismiss();
			SubmitThread = null; // 将自己设置为空,以便下次调用登陆
		}
	};
	
	
	
	
	private void go()
	{
		// TODO Auto-generated method stub
		id_lsh = NewCaseService.NewCaseId_Lsh;
        if (id_lsh.equals(""))
			{
				AlertDialog.Builder builderError = new AlertDialog.Builder(
						ActivityNewCaseByOrganise.this);
				builderError.setTitle("案件上报失败!");
				builderError.setMessage("案件上报失败,将返回界面!");
				builderError.setPositiveButton("确  定", windowClose);
				builderError.show();
			}
			else
			{
				NewCaseService.NewCaseId_Lsh = "";
				AlertDialog.Builder builder = new AlertDialog.Builder(
						ActivityNewCaseByOrganise.this);
				builder.setTitle("案件上报成功!");
				builder.setMessage("案件要转到处理吗?否则将返回界面!");
				builder.setPositiveButton("确  定", ByAJBL).setNegativeButton(
						"取消", windowClose).show();

			}
	}
	
	/**
	 * 
	 * @param Title  弹出窗体名称
	 * @param Message  弹出窗体内容
	 * @param PositiveButtonName  弹出窗体按钮名称
	 * @param onClickListener   弹出窗体按钮事件
	 */
	public void Alert(String Title,String Message,String  PositiveButtonName,DialogInterface.OnClickListener   onClickListener)
	{
		AlertDialog.Builder builderError = new AlertDialog.Builder(
				ActivityNewCaseByOrganise.this);
		builderError.setTitle(Title);
		builderError.setMessage(Message);
		builderError.setPositiveButton(PositiveButtonName, onClickListener);
		builderError.show();
	}
	
	
	

	private int HistorySubmit()
	{
		int  tmp = 0;
		// TODO Auto-generated method stub
		NewCaseService  service1 = new NewCaseService(this);
		try
		{
		    tmp = service1.HistorySubmitByOrganise(newcasebyorganise);
		}
		catch (Exception e)
		{
			// TODO: handle exception
		}
		
		return tmp;
	}

	DialogInterface.OnClickListener  windowClose = new DialogInterface.OnClickListener()
	{
		
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			ActivityNewCaseByOrganise.this.finish();
		}
	};
	
	DialogInterface.OnClickListener  ByAJBL = new DialogInterface.OnClickListener()
	{
		
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			
			Intent it = new Intent(ActivityNewCaseByOrganise.this,
					ActivityCaseHandle.class);
			startActivity(it);
			finish();
		
		}
	};
	
	
	
//	private String[] getPicList(String listStr)
//	{
//		String[] temp = listStr.split(",");
////		print.out(temp.length);
////		for(int i = 0; i<temp.length;i++)
////		{
////			print.out(temp[i].toString());
////		}
//		return temp;
//	}
	
	
    /// 照片信息获取 
    private String getPicList(String PicList) throws IOException
    {
    	print.out("PicList="+PicList);
    	if(PicList == null)
    	{
    		return "";
    	}
    	else
    	{
    		String[] temp = PicList.split(",");
        	if (temp == null)
                return null;
        	newcasebyorganise.setPicListStr(temp);
        	int count = temp.length;
            String[] tmpArr = new String[count];
            for (int i = 0; i < count; i++)
            {
            	print.out("*****"+temp[i]+"*****");
                String strBase64 = "";
                String fileName = temp[i];
                File file = new File(fileName);
                if (file.exists())
                {
                     FileInputStream fs = new FileInputStream(fileName);
                     byte[] buffer = new byte[(int) file.length()];  
                     int length = fs.read(buffer);  
                     print.out("length========"+length);
                     try
    				{
                    	 strBase64 = Base64.encode(buffer);
    				}
    				catch (Exception e)
    				{
    					// TODO: handle exception
    					 print.out("*****"+e.getMessage()+"*****");
    				}
                }
                tmpArr[i] = strBase64;
            }
            StringBuffer  buffer  = new StringBuffer();
            buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<ResultSet>\r\n");
    		buffer.append("   <item>\r\n\t\t");
            for(int i = 0;i<tmpArr.length;i++)
            {
            	buffer.append("<PicListItem"+(i+1)+">"+tmpArr[i].toString()+"</PicListItem"+(i+1)+">");
            }
            buffer.append("   </item>\r\n\t\t</ResultSet>");
            return buffer.toString();
		}
    	
    }
    
    public String encodingZipMedia(String  PicList)
	{
    	StringBuffer  buffer  = new StringBuffer();
    	String[] temp = PicList.split(",");
    	 for (int i = 0; i < temp.length; i++)
         {
    		 buffer.append("<PicListItem"+(i+1)+">"+Assistant.encodeZipMedia(temp[i])+"</PicListItem"+(i+1)+">");
         }
    	 print.out(buffer.toString());
    	 return buffer.toString();
	}
    
    public int  getPicNum(String PicList)
    {
    	if(PicList == null ||PicList == "")
    	{
    		return 0;
    	}
    	else 
    	{
    		String[] temp = PicList.split(",");
			return temp.length;
    		
		}
    }
    
    public void showToast(String msg)
	{
		
		Toast toast = Toast
				.makeText(ActivityNewCaseByOrganise.this, msg, Toast.LENGTH_LONG);
		toast.show();
	}
    

}
