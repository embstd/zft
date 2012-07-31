package com.kinview.zft.casehandle;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.kinview.config.Config;
import com.kinview.config.Dialog;
import com.kinview.util.PosPrintUtil;
import com.kinview.util.ESC;
import com.kinview.zft.R;
import com.kinview.zft.R.id;
import com.kinview.zft.R.layout;

public class ActivityPosPrint extends Activity {
	public static final String SPP_UUID = "00001101-0000-1000-8000-00805F9B34FB";
	public BluetoothAdapter btAdapt;
	public static final int REQUEST_ENABLE_BT = 8807;
	private Button btnSearch;
	private Button btnPrint;
	private Spinner deviceSpinner;
	private ArrayAdapter<String> deviceAdapter;
	List<String> SpinnerDevices = new ArrayList<String>();
//	private BluetoothDevice currentDevice;//����ѡ�е��豸
	private SearchThread searchThread;
	private String mac_address;
	
	public static BluetoothSocket btSocket;
	private OutputStream outStream;
	private ESC esc=new ESC(1024);
	
	private ConnectThread connectThread;
	public String formContent;
//	public int position;
	
	public static final int  ITEM0 = 1;
	public static final int  ITEM1 = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bluetooth_print);
		initView();
		
		Intent it = this.getIntent();
//		position = it.getIntExtra("position", 0);
		formContent = it.getStringExtra("content");
		
		// ��̬ע��Receiver����ȡ�����豸��صĽ��  
		IntentFilter intent = new IntentFilter();
		intent.addAction(BluetoothDevice.ACTION_FOUND);// ��BroadcastReceiver��ȡ���������
		intent.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
		intent.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
		intent.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		registerReceiver(searchDevices, intent);
		
		//�ж�������������Ƿ�����
		btAdapt = BluetoothAdapter.getDefaultAdapter();
		if (btAdapt == null){
			Dialog.showDialog(0, this, null, 0, "��ʾ", "�Ҳ��������豸", 0, "");
		}else {
			if (!btAdapt.isEnabled()){//û�п����Ϳ���
				Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			}else {
				searchDevices();
			}
		}
		
	}
	
	private void initView(){
		
		btnSearch = (Button)this.findViewById(R.id.btnSearch);
		btnPrint = (Button)this.findViewById(R.id.btnPrint);
		btnPrint.setEnabled(false);
		deviceSpinner = (Spinner)this.findViewById(R.id.device_spinner);
		deviceAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, SpinnerDevices);
		deviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		deviceSpinner.setAdapter(deviceAdapter);
		deviceSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String address = deviceAdapter.getItem(arg2);
	        	String []values = address.split("\\|");
	        	mac_address = values[2];
	        	
				//��õ�ǰѡ���ƥ����豸
//	        	Set<BluetoothDevice> pairedDevices = btAdapt.getBondedDevices();
//	    		if (pairedDevices.size() > 0){
//	    			Iterator it = pairedDevices.iterator();
//	    			for(;it.hasNext();){
//	    				BluetoothDevice device = (BluetoothDevice)it.next();
//	    				if (device.getAddress().equals(mac_address)){
//	    					currentDevice = device;
//	    				}
//	    			}
//	    		}
	        	
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		//�����豸
		btnSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				searchDevices();
				if (!btAdapt.isEnabled()){//û�п����Ϳ���
					Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
				}
				
				btAdapt.cancelDiscovery();
				btAdapt.startDiscovery();//��������б�û������Ե��豸���ſ�ʼ����
				if (searchThread == null){
					searchThread = new SearchThread();
					searchThread.showProcess();
				}
				
			}
		});
		
		btnPrint.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mac_address.equals("")){
					return;
				}
				if (!btAdapt.isEnabled()){//û�п����Ϳ���
					Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
				}
				
				btnPrint.setEnabled(false);
				BluetoothDevice btDev = btAdapt.getRemoteDevice(mac_address);
				connectThread = new ConnectThread(btDev);
				connectThread.start();
//				connectPrinter();
//				posPrintProcess();
			}
		});
	}
	
	private BroadcastReceiver searchDevices = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			
			//�����豸ʱ��ȡ���豸��MAC��ַ
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				
				BluetoothDevice device1 = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

				String str= "ĩ���"+"|"+device1.getName() + "|" + device1.getAddress();
				for (int i=0; i<SpinnerDevices.size(); i++){
					String temp = SpinnerDevices.get(i).split("|")[2];
					if (!str.split("|")[2].equals(temp)){//����ͬһmac��ַ�������
						SpinnerDevices.add(str);
					}
				}
//				if (SpinnerDevices.indexOf(str) == -1)
//					SpinnerDevices.add(str);
				deviceAdapter.notifyDataSetChanged();
			}
		}
	};
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0,ITEM0,0,"�ر�����");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case ITEM0:
			closeBluetooth();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void closeBluetooth(){
		if(btAdapt.isEnabled())
		{
			btAdapt.disable();
		}
	}

	private void searchDevices(){
		if (btAdapt.getState() == BluetoothAdapter.STATE_OFF) {// ���������û����
			Toast.makeText(ActivityPosPrint.this, "���ȴ�����", 1000).show();
			return;
		}
		setTitle("����������ַ:" + btAdapt.getAddress());
		SpinnerDevices.clear();
		
		Set<BluetoothDevice> pairedDevices = btAdapt.getBondedDevices();
		if (pairedDevices.size() > 0){
//			btnConnect.setEnabled(true);
			Iterator it = pairedDevices.iterator();
			for(;it.hasNext();){
				BluetoothDevice device = (BluetoothDevice)it.next();
				String str= "�����"+"|"+device.getName() + "|" + device.getAddress();
				if (SpinnerDevices.indexOf(str) == -1)
					SpinnerDevices.add(str);
				deviceAdapter.notifyDataSetChanged();
				btnPrint.setEnabled(true);
			}
		}else{
			btAdapt.startDiscovery();//��������б�û������Ե��豸���ſ�ʼ����
			if (searchThread == null){
				searchThread = new SearchThread();
			}
			searchThread.showProcess();
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == this.REQUEST_ENABLE_BT){
			searchDevices();
		}
	}

	protected void onDestroy() {
	    this.unregisterReceiver(searchDevices);
	    if (connectThread != null){
	    	connectThread.cancel();
	    }
		super.onDestroy();
		
//		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	//���ķ�ʽ���͸���ӡ��
	private void printMessage(byte[] b,int len) {
		try {
			if (outStream != null)
				outStream.write(b, 0, len);
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
	
	public void WakeUpPritner() 
	{
		byte[] b={'\0','\0','\0'};
		printMessage(b,3);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		byte[] b1={27,64};
		printMessage(b1,2);	
	}
	
	class SearchThread extends Thread{
		ProgressDialog progressDialog = null;

		private void showProcess()
		{
			progressDialog = new ProgressDialog(ActivityPosPrint.this);
			progressDialog.setMessage("��������,���Ժ�...");
			progressDialog.setIndeterminate(true);
			try
			{
				progressDialog.show();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			start();
		}
		public void run(){
			while (!Thread.interrupted()){
				if (!btAdapt.isDiscovering()){
					progressDialog.dismiss();
					break;
				}
			}
			progressDialog.dismiss();
			searchThread = null;
		}
	}
	
	private class ConnectThread extends Thread{
		private final BluetoothSocket mmSocket;
		private final BluetoothDevice mmDevice;
		public ConnectThread(BluetoothDevice device){
			BluetoothSocket tmp = null;
			mmDevice = device;
			UUID uuid = UUID.fromString(SPP_UUID);
			try {
				tmp = device.createRfcommSocketToServiceRecord(uuid);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mmSocket = tmp;
		}
		
		public void run(){
			btAdapt.cancelDiscovery();
			
			try {
				mmSocket.connect();
				//�õ������
//				outStream=mmSocket.getOutputStream();
				PosPrintUtil.outStream = mmSocket.getOutputStream();
				readyPosPrint();
				boolean isPrintSuccess = PosPrintUtil.posPrintProcess();
//				boolean isPrintSuccess = posPrintProcess();
				if (isPrintSuccess){
					myHandler.sendMessage(myHandler.obtainMessage(PRINT_OK));
				}else {
					myHandler.sendMessage(myHandler.obtainMessage(PRINT_ERROR));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					mmSocket.close();
				} catch (IOException e1) {
					return;
				}
			}
//			manageConnectedSocket(mmSocket);
		}
		
		public void cancel(){
			try {
				mmSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//ƴ�� ESC 
	private void readyPosPrint(){
		//Ӧ�ø��ݱ�id��ӡ
//		Config.currentFormList.get(Config.bdPositionId).getId();
		String[] s = formContent.split("_");
		if (s.length == 2){
			posPrint3();
		}else if (s.length == 3){
			posPrint1();
		}else if (s.length == 4){
			posPrint2();
		}else if (s.length == 5){
			posPrint0();
		}else {
			return;
		}
//		switch (Config.bdPositionId){
//		case 0:
//			posPrint0();
//			break;
//		case 1:
//			posPrint1();
//			break;
//		case 2:
//			posPrint1();
//			break;
//		case 3:
//			posPrint1();
//			break;
//		case 4:
//			posPrint2();
//			break;
//		}
		
	}
	
	private void posPrint3() {
		String[] s = formContent.split("_");
		Log.i("zft", formContent);
		try {
			PosPrintUtil.WakeUpPritner();
//			PosPrintUtil.esc.printBarCode("788788878");
			PosPrintUtil.esc.setInit();
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.setCenter();
			PosPrintUtil.esc.setBold();
			PosPrintUtil.esc.setHorizonalZoom();
			PosPrintUtil.esc.TextOut(s[0].toString());
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.resetBoldtoNormal();
			PosPrintUtil.esc.setLeft();
			PosPrintUtil.esc.TextOut(s[1].toString());
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.Enter();
//			PosPrintUtil.esc.TextOut(s[3].toString());
//			PosPrintUtil.esc.Enter();
//			PosPrintUtil.esc.Enter();
////			PosPrintUtil.esc.setRight();
//			PosPrintUtil.esc.TextOut(s[4].toString());
//			PosPrintUtil.esc.Enter();
//			PosPrintUtil.esc.Enter();
			
			//��������ֽ
			for (int j=0; j<5; j++){
				PosPrintUtil.esc.TextOut("\r\n");
			}
//			PosPrintUtil.esc.setInit();
//			PosPrintUtil.esc.setZouZhi();//��ֽ
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void posPrint0(){
		String[] s = formContent.split("_");
		Log.i("zft", formContent);
		try {
			PosPrintUtil.WakeUpPritner();
//			PosPrintUtil.esc.printBarCode("788788878");
			PosPrintUtil.esc.setInit();
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.setCenter();
			PosPrintUtil.esc.setBold();
			PosPrintUtil.esc.setHorizonalZoom();
			PosPrintUtil.esc.TextOut(s[0].toString());
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.resetBoldtoNormal();
			PosPrintUtil.esc.TextOut(s[1].toString());
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.setLeft();
			PosPrintUtil.esc.TextOut(s[2].toString());
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.TextOut(s[3].toString());
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.Enter();
//			PosPrintUtil.esc.setRight();
			PosPrintUtil.esc.setCenter();
			PosPrintUtil.esc.TextOut(s[4].toString());
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.Enter();
			
//			for (int i=2; i<s.length; i++){
//				PosPrintUtil.esc.TextOut(s[i].toString());
//				if (i%2 != 0){
//					PosPrintUtil.esc.Enter();
//				}
//			}
			//��������ֽ
			for (int j=0; j<5; j++){
				PosPrintUtil.esc.TextOut("\r\n");
			}
//			PosPrintUtil.esc.setInit();
//			PosPrintUtil.esc.setZouZhi();//��ֽ
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void posPrint1() {
		String[] s = formContent.split("_");
		Log.i("zft", formContent);
		try {
			PosPrintUtil.WakeUpPritner();
//			PosPrintUtil.esc.printBarCode("788788878");
			PosPrintUtil.esc.setInit();
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.setCenter();
			PosPrintUtil.esc.setBold();
			PosPrintUtil.esc.setHorizonalZoom();
			PosPrintUtil.esc.TextOut(s[0].toString());
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.resetBoldtoNormal();
			PosPrintUtil.esc.setLeft();
			PosPrintUtil.esc.TextOut(s[1].toString());
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.Enter();
			
			PosPrintUtil.esc.setCenter();
			PosPrintUtil.esc.TextOut(s[2].toString());
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.Enter();
//			PosPrintUtil.esc.TextOut(s[3].toString());
//			PosPrintUtil.esc.Enter();
//			PosPrintUtil.esc.Enter();
////			PosPrintUtil.esc.setRight();
//			PosPrintUtil.esc.TextOut(s[4].toString());
//			PosPrintUtil.esc.Enter();
//			PosPrintUtil.esc.Enter();
			
			//��������ֽ
			for (int j=0; j<5; j++){
				PosPrintUtil.esc.TextOut("\r\n");
			}
//			PosPrintUtil.esc.setInit();
//			PosPrintUtil.esc.setZouZhi();//��ֽ
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void posPrint2() {
		String[] s = formContent.split("_");
		Log.i("zft", formContent);
		try {
			PosPrintUtil.WakeUpPritner();
//			PosPrintUtil.esc.printBarCode("788788878");
			PosPrintUtil.esc.setInit();
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.setCenter();
			PosPrintUtil.esc.setBold();
			PosPrintUtil.esc.setHorizonalZoom();
			PosPrintUtil.esc.TextOut(s[0].toString());
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.resetBoldtoNormal();
			PosPrintUtil.esc.TextOut(s[1].toString());
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.setLeft();
			PosPrintUtil.esc.TextOut(s[2].toString());
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.setCenter();
			PosPrintUtil.esc.TextOut(s[3].toString());
			PosPrintUtil.esc.Enter();
			PosPrintUtil.esc.Enter();
////			PosPrintUtil.esc.setRight();
//			PosPrintUtil.esc.TextOut(s[4].toString());
//			PosPrintUtil.esc.Enter();
//			PosPrintUtil.esc.Enter();
			
			//��������ֽ
			for (int j=0; j<5; j++){
				PosPrintUtil.esc.TextOut("\r\n");
			}
//			PosPrintUtil.esc.setInit();
//			PosPrintUtil.esc.setZouZhi();//��ֽ
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static final  int PRINT_OK = 0;
	public static final  int PRINT_ERROR = 1;
	private Handler myHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
			case PRINT_OK:
				btnPrint.setEnabled(false);
				ActivityPosPrint.this.onBackPressed();
				ActivityPosPrint.this.finish();
				break;
			case PRINT_ERROR:
				Dialog.showDialog(Dialog.OK, ActivityPosPrint.this, null, 0,
						"��ʾ", "��ӡʧ��!", 0, "");
				ActivityPosPrint.this.onBackPressed();
				ActivityPosPrint.this.finish();
				break;
			}
		}
		
	};
	
//	private void connectPrinter(){
////btnDisConnect.setEnabled(true);
//btAdapt.cancelDiscovery();
//UUID uuid = UUID.fromString(SPP_UUID);
//BluetoothDevice btDev = btAdapt.getRemoteDevice(mac_address);//����������ַ��ȡԶ�������豸
//try {
//	btSocket = btDev.createRfcommSocketToServiceRecord(uuid);//����UUID����������һ��BluetoothSocket ����BluetoothSocket
//	btSocket.connect();
//	outStream=btSocket.getOutputStream();
//} catch (IOException e) {
//	e.printStackTrace();
//}
////btnPrint.setEnabled(true);
////spinner.setEnabled(false);
////btnConnect.setEnabled(false);
//}
//
//private boolean posPrintProcess(){
//try {	 
//	WakeUpPritner();
//	for(int i =0;i < 1;i++){
//		
//		esc.Code39("121ABC");
//		esc.Enter(); //�س�����
//		
////		esc.TextOut("���Լ�ǿ��ӡ��ABCabc123\r\n");
//		esc.TextOut(formContent+"\r\n");
//	}	
//	printMessage(esc.buf,esc.index);
//	esc.index=0;
//	return true;
//} catch (IOException e) {
//	e.printStackTrace();
//}
//return false;
//}
	
}