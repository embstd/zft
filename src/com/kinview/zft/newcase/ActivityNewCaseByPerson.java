package com.kinview.zft.newcase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.kobjects.base64.Base64;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.kinview.assistant.Assistant;
import com.kinview.camera.ActivityPhoto;
import com.kinview.config.Config;
import com.kinview.config.print;
import com.kinview.entity.NewCase;
import com.kinview.util.NewCaseService;
import com.kinview.zft.R;
import com.kinview.zft.casehandle.ActivityCaseHandle;

public class ActivityNewCaseByPerson extends Activity
{
	
	
	MyThread SubmitThread = null;
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
	public int  stateNum = 99;
	Context context = this;
	public EditText etTime;
	private CheckBox checkBoxPic = null;
	private CheckBox checkBoxMan = null;
	private CheckBox checkBoxWoman = null;
	private NewCase newcasebyperson = null;
	private EditText ncp_etName, ncp_etSex, ncp_etAge, ncp_etTell,
			ncp_etIDnumber, ncp_etAddress, ncp_etOpenAdd, ncp_etOpenTime,
			ncp_etintro;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newcasebyperson);
		load();

		spinner1.setOnItemSelectedListener(onItemSelectedListener1);
		spinner2.setOnItemSelectedListener(onItemSelectedListener2);
		spinner3.setOnItemSelectedListener(onItemSelectedListener3);
		checkBoxMan.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked)
			{
				// TODO Auto-generated method stub
				if (isChecked == true)
				{
					checkBoxWoman.setChecked(false);
				}

			}
		});
		checkBoxWoman.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked)
			{
				// TODO Auto-generated method stub
				if (isChecked == true)
				{
					checkBoxMan.setChecked(false);
				}
			}
		});
	}

	private void load()
	{

		// TODO Auto-generated method stub
		newcasebyperson = new NewCase();

		ncp_etName = (EditText) findViewById(R.id.ncp_etName);
		ncp_etAge = (EditText) findViewById(R.id.ncp_etAge);
		ncp_etTell = (EditText) findViewById(R.id.ncp_etTell);
		ncp_etIDnumber = (EditText) findViewById(R.id.ncp_etIDnumber);
		ncp_etAddress = (EditText) findViewById(R.id.ncp_etAddress);
		ncp_etOpenAdd = (EditText) findViewById(R.id.ncp_etOpenAdd);
		ncp_etOpenTime = (EditText) findViewById(R.id.ncp_etOpenTime);
		ncp_etintro = (EditText) findViewById(R.id.ncp_etintro);

		checkBoxMan = (CheckBox) findViewById(R.id.ncp_cbMan);
		checkBoxWoman = (CheckBox) findViewById(R.id.ncp_cbWoman);
		checkBoxPic = (CheckBox) findViewById(R.id.ncp_cbPic);
		etTime = (EditText) findViewById(R.id.ncp_etOpenTime);
		etTime.setText(Config.timebyYYYYmmDD());
		spinner1 = (Spinner) findViewById(R.id.Spinner1);
		spinner2 = (Spinner) findViewById(R.id.Spinner2);
		spinner3 = (Spinner) findViewById(R.id.Spinner3);
		spCasely = (Spinner)findViewById(R.id.ncp_spCaselaiyuan);
		ArrayList<String> list_smalltype = new ArrayList<String>();
		NewCaseService newCase = new NewCaseService(this);
		temp1 = newCase.CaseType1("0");
		// print.out(temp1.length);
		for (int i = 0; i < temp1[1].length; i++)
		{
			list_smalltype.add(temp1[1][i]);
		}
		// list_smalltype.add("市容环境");
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
			tempID1 = temp1[0][selectedItemid].toString();
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
			tempID2 = temp2[0][selectedItemid].toString();
			ArrayList<String> list_smalltype = new ArrayList<String>();
			NewCaseService newCase1 = new NewCaseService(context);
			temp3 = newCase1.CaseType1(tempID2);
			print.out("temp3.length=" + temp3[1].length);
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
			System.exit(0);
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
		if (context.getClass().equals(ActivityNewCaseByPerson.class))
		{
			menu.add(0, 0, 0, "照片").setIcon(R.drawable.newcase_tab5);
			// menu.add(0, 1, 0, "地图").setIcon(R.drawable.newcase_tab1);
			menu.add(0, 2, 0, "上报案件").setIcon(R.drawable.newcase_tab4);
			menu.add(0, 3, 0, "组织案件").setIcon(R.drawable.newcase_tab2);
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
		if (context.getClass().equals(ActivityNewCaseByPerson.class))
		{
			switch (menuId)
			{
			case 0: // 照片
				Intent itimg = new Intent(ActivityNewCaseByPerson.this,
						ActivityPhoto.class);
				Bundle b = new Bundle();
				b.putBoolean("stytle", true);
				b.putBoolean("op", false);
				b.putString("data", newcasebyperson.getPicList());
				b.putInt("count", newcasebyperson.getPicNum());
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
				Intent it = new Intent(ActivityNewCaseByPerson.this,
						ActivityNewCaseByOrganise.class);
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
						newcasebyperson.setPicNum(count);
						newcasebyperson.setPicList(data);
					}
					else
					{
						newcasebyperson.setPicNum(0);
						newcasebyperson.setPicList("");
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
		String IDnum = ncp_etIDnumber.getText().toString();
		if (ncp_etName.getText().length() == 0)
		{
			showToast("请填写姓名!");
			return;
		}
		if (checkBoxMan.isChecked() == false
				&& checkBoxWoman.isChecked() == false)
		{
			showToast("请选择性别!");
			return;
		}
		if (ncp_etOpenAdd.getText().length() == 0
				|| ncp_etOpenTime.getText().length() == 0)
		{
			showToast("案发地点和案发时间不能为空！");
			return;
		}
		if(IDnum.length()>0)
		{
			if(IDnum.length()<17)
			{
				showToast("身份证号码格式不正确！");
				return;
			}
			if(IDnum.length() == 17)
			{
				IDnum = IDnum +"X";
			}
		}
		newcasebyperson.setState(1);
		newcasebyperson.setFromtype(spCasely.getSelectedItemPosition()+1);
		newcasebyperson.setCaseType(1);
		newcasebyperson.setCreateTime(Config.timebyYYYYmmDD());
		newcasebyperson.setCreate(Config.user.getUserid());
		newcasebyperson.setCaseAjlb(tempID1);
		newcasebyperson.setCaseAnYou(tempID2);
		newcasebyperson.setCaseWeiFaXW(tempID3);
		newcasebyperson.setPerson(ncp_etName.getText().toString()); // 组织名称
		newcasebyperson.setSex(getSexByString());
		newcasebyperson.setAge(ncp_etAge.getText().toString());
		newcasebyperson.setPtel(ncp_etTell.getText().toString());
		newcasebyperson.setPadd(ncp_etAddress.getText().toString());
		newcasebyperson.setIdnumber(ncp_etIDnumber.getText().toString());
		newcasebyperson.setAddress(ncp_etOpenAdd.getText().toString());
		newcasebyperson.setThetime(ncp_etOpenTime.getText().toString());
		newcasebyperson.setIntro(ncp_etintro.getText().toString());
		newcasebyperson.setType(1);// type = 1 组织 type = 2 个人
		newcasebyperson.setAreaid(Integer.valueOf(Config.branch));
		newcasebyperson.setPicNum(getPicNum(newcasebyperson.getPicList()));
		newcasebyperson.setPicList(getPicList(newcasebyperson.getPicList()));
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

			submitProgressDialog = new ProgressDialog(ActivityNewCaseByPerson.this);
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
					ActivityNewCaseByPerson.this);
			builderError.setTitle("案件上报失败!");
			builderError.setMessage("案件上报失败,将返回界面!");
			
			builderError.setPositiveButton("确  定", windowClose);
			builderError.show();
		}
		else
		{
				NewCaseService.NewCaseId_Lsh = "";
				AlertDialog.Builder builder = new AlertDialog.Builder(
						ActivityNewCaseByPerson.this);
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
				ActivityNewCaseByPerson.this);
		builderError.setTitle(Title);
		builderError.setMessage(Message);
		builderError.setPositiveButton(PositiveButtonName, onClickListener);
		builderError.show();
	}
	

	private int HistorySubmit()
	{
		// TODO Auto-generated method stub
		NewCaseService service1 = new NewCaseService(this);
		int tmp = service1.HistorySubmitByPerson(newcasebyperson);
		print.out("返回值为："+tmp);
		stateNum = tmp;
		print.out("stateNum值为："+stateNum);
		return tmp;
	}

	private String getSexByString()
	{
		String sex = "";
		if (checkBoxMan.isChecked() && checkBoxWoman.isChecked() == false)
		{
			sex = "男";
		}
		else if (checkBoxWoman.isChecked() && checkBoxMan.isChecked() == false)
		{
			sex = "女";
		}
		else if (checkBoxMan.isChecked() == false
				&& checkBoxWoman.isChecked() == false)
		{
			sex = "";
		}
		return sex;
	}

	DialogInterface.OnClickListener windowClose = new DialogInterface.OnClickListener()
	{

		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			
			ActivityNewCaseByPerson.this.finish();
		}
	};

	DialogInterface.OnClickListener ByAJBL = new DialogInterface.OnClickListener()
	{

		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			Intent it = new Intent(ActivityNewCaseByPerson.this,
					ActivityCaseHandle.class);
			startActivity(it);
			
			finish();
		}
	};

	// / 照片信息获取
	private String getPicList(String PicList) throws IOException
	{
		if(PicList == null)
    	{
    		return "";
    	}
    	else
    	{
			String[] temp = PicList.split(",");
			if (temp == null)
				return null;
			newcasebyperson.setPicListStr(temp);
			int count = temp.length;
			String[] tmpArr = new String[count];
			for (int i = 0; i < count; i++)
			{
				String strBase64 = "";
				String fileName = temp[i];
				File file = new File(fileName);
				if (file.exists())
				{
					FileInputStream fs = new FileInputStream(fileName);
					byte[] buffer = new byte[(int) file.length()];
					int length = fs.read(buffer);
					try
					{
						strBase64 = Base64.encode(buffer);
					}
					catch (Exception e)
					{
						// TODO: handle exception
						print.out("*****" + e.getMessage() + "*****");
					}
				}
				tmpArr[i] = strBase64;
			}
			StringBuffer buffer = new StringBuffer();
			buffer
					.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<ResultSet>\r\n");
			buffer.append("   <item>\r\n\t\t");
			for (int i = 0; i < tmpArr.length; i++)
			{
				buffer.append("<PicListItem" + (i + 1) + ">" + tmpArr[i].toString()
						+ "</PicListItem" + (i + 1) + ">");
			}
			buffer.append("   </item>\r\n\t\t</ResultSet>");
			print.out("个人案件图片");
			print.out(String.valueOf(buffer));
			return buffer.toString();

    	}
	}

	public String encodingZipMedia(String PicList)
	{
		StringBuffer buffer = new StringBuffer();
		String[] temp = PicList.split(",");
		for (int i = 0; i < temp.length; i++)
		{
			buffer.append("<PicListItem" + (i + 1) + ">"
					+ Assistant.encodeZipMedia(temp[i]) + "</PicListItem"
					+ (i + 1) + ">");
		}
		return buffer.toString();
	}

	public int getPicNum(String PicList)
	{
		if (PicList == null || PicList == "")
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

		Toast toast = Toast.makeText(ActivityNewCaseByPerson.this, msg,
				Toast.LENGTH_LONG);
		toast.show();
	}

}
