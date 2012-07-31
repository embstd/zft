package com.kinview.thread;

import java.util.ArrayList;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.kinview.assistant.Assistant;
import com.kinview.config.Config;
import com.kinview.config.Msg;
import com.kinview.entity.NewCase;
import com.kinview.util.Form;
import com.kinview.webservice.CommonResult;
import com.kinview.webservice.Server;

public class ThreadGetForm extends Thread{

	private Handler mHandler;
	private ProgressDialog progressDialog = null;
	private int flag;	//0,Ĭ��ˢ��,1�û�ˢ��,2��̨ˢ��
	private int caseId;
	
	public void showProcess(Context context,Handler mHandler,int flag, int caseId){
		this.flag = flag;
		this.mHandler = mHandler;
		this.caseId = caseId;
		try{
			if(flag!=2){
				progressDialog = new ProgressDialog(context);
				progressDialog.setMessage("���ڻ�ȡ����,���Ժ�...");
				progressDialog.setIndeterminate(true);
				progressDialog.show();
			}else{
				mHandler.sendMessage(mHandler.obtainMessage(Msg.TITLE_PROGRESS_START));
			}
			start();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void run(){
		CommonResult result =null;
		result = get(1);
		if(result!=null){
			if (result.getErrorCode().equals("1")){
				mHandler.sendMessage(mHandler.obtainMessage(Msg.GET_FORMLIST_NO_PERMISSION));
				try {
					progressDialog.dismiss();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}
			String xml = Assistant.upZipBaseString(result.getResultStr());
			//mHandler.sendMessage(mHandler.obtainMessage(Msg.ACTIVITY_SHOWTOAST,xml));
			ArrayList<Object> list = Assistant.parseXml(xml, Form.class);
			//Config.listEvent.clear();
//			int i=0;
			Config.currentFormList.clear();
			for(Object obj:list){
//				Event item = (Event)obj;
				Form item = (Form)obj;
				Config.currentFormList.add(item);
				//Config.listEvent.add(item);
				
//				print.out("i="+i);
//				print.out("getAddress="+item.getAddress());
//				print.out("getOrganise="+item.getOrganise());
//				print.out("getAge="+item.getAge());
//				print.out("getFh1="+item.getFh1());
//				i++;
			}
			//��¼���ˢ��ʱ��
			Config.event_refresh_start = (new Date()).getTime();
			if(flag==0){
				//ϵͳˢ��,��Ҫ��ʾlist
				mHandler.sendMessage(mHandler.obtainMessage(Msg.GETFORMLIST));
			}else if(flag==1){
				//�û�ˢ��,����Ҫ����ʾlist
				mHandler.sendMessage(mHandler.obtainMessage(Msg.LISTVIEW_REFRESH));
			}else if(flag==2){
				//��̨ˢ��,����Ҫ����ʾlist
				mHandler.sendMessage(mHandler.obtainMessage(Msg.LISTVIEW_REFRESH));
			}
			
			//������ʷ��¼
//			Config.eventMgr.save(Config.listEvent);
//			Config.eventMgr.load();
			//������ʷ��¼����
			
		}else{
			//��������ȡʧ��
			if(flag!=2){//��̨ˢ��,����ʾ����
				mHandler.sendMessage(mHandler.obtainMessage(Msg.ERROR_SERVER_CONNECT));
			}
		}
		if(flag!=2){
			try {
				progressDialog.dismiss();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			mHandler.sendMessage(mHandler.obtainMessage(Msg.TITLE_PROGRESS_STOP));
		}
		//Config.threadGetEvent = null;
	}
	
	public CommonResult get(int i){
//		User user = Config.user;
		CommonResult result = null;
		try {
			Log.i("ZFT", "caseId: " + caseId + " Config.user.getGroupid()="+Config.user.getGroupid());
//			if(Config.test==1){
				result = Server.getFormList(caseId, Config.user.getGroupid());
				Log.i("ZFT", result.getResultStr()+ " caseId: " + caseId);
//			}else{
//				result = test();
//				Config.test=1;
//			}
		}catch (Exception e) {
			e.printStackTrace();
			if(i==1){
				try {
					sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				result = get(2); 
			}
		}
		
		return result;
	}
	
	
	/*
	 * ����
	 */
//	public  CommonResult test(){
//		CommonResult cr = new CommonResult();
//		cr.setErrorCode("0");
//		cr.setErrorDesc("");
//		cr.setResultStr("<?xml version=\"1.0\" encoding=\"UTF-8\"?> <ResultSet> <item> <id>1080</id><typeName>�����չ����⣬ʩ����λҹ��������סլ��ʹ�ø�������е�豸����ʩ����ҵ</typeName><organise>Ǧ�ʳ�</organise><fzr></fzr><zw></zw><otel></otel><oadd></oadd><address>Ǧ��</address><thetime>2010-9-17 10:54:01</thetime><person>���</person><sex></sex><age></age><idnumber></idnumber><ptel></ptel><padd></padd><sjsp>4</sjsp><sp1>122|2010-09-17 10:58:46|dfd</sp1><sp2>117|2010-09-17 10:59:23|df</sp2><sp3>257|2010-09-17 11:00:25|dfdfddfsf</sp3><sp4>1|2010-09-17 11:00:59|dfaaaaaaaaaaaaaaaaa</sp4><qzsp>2</qzsp><q0>122|2010-09-19 10:35:53|</q0><q1></q1><q2></q2><q3></q3><zjsp>1</zjsp><zj0></zj0><zj1></zj1><zj2></zj2><zj3></zj3><fhsp>5</fhsp><fh0>122|2010-09-17 11:03:46|df</fh0><fh1>117|2010-09-17 11:04:02|dfd</fh1><fh2>257|2010-09-17 11:04:20|dfdf</fh2><fh3>1|2010-09-17 11:04:35|dfdf</fh3><type>2</type> </item> <item> <id>1290</id><typeName>��Ҫ�ֵ����ཨ������̨���Ŵ�����ҡ��ڷ�Ӱ���հ����Ʒ</typeName><organise>123</organise><fzr>123</fzr><zw>123</zw><otel>123</otel><oadd>12</oadd><address>123</address><thetime>2011-7-7 11:29:16</thetime><person></person><sex></sex><age></age><idnumber></idnumber><ptel></ptel><padd></padd><sjsp>3</sjsp><sp1>253|2011-7-28 16:18:56|ͬ��</sp1><sp2>253|2011-8-2 10:12:10|wr</sp2><sp3></sp3><sp4></sp4><qzsp>3</qzsp><q0>253|2011-7-28 16:23:54|ͬ��</q0><q1>253|2011-7-28 16:36:51|ͬ��</q1><q2></q2><q3></q3><zjsp>2</zjsp><zj0>253|2011-7-28 17:44:51|ͬ��</zj0><zj1></zj1><zj2></zj2><zj3></zj3><fhsp>2</fhsp><fh0>253|2011-7-28 17:46:34|ͬ��</fh0><fh1></fh1><fh2></fh2><fh3></fh3><type>3</type> </item> <item> <id>1293</id><typeName>��Ҫ�ֵ����ཨ������̨���Ŵ�����ҡ��ڷ�Ӱ���հ����Ʒ</typeName><organise>1111</organise><fzr>1111</fzr><zw>111</zw><otel>1111</otel><oadd>111</oadd><address>111</address><thetime>2011-7-21 9:26:57</thetime><person></person><sex></sex><age></age><idnumber></idnumber><ptel></ptel><padd></padd><sjsp>0</sjsp><sp1></sp1><sp2></sp2><sp3></sp3><sp4></sp4><qzsp>1</qzsp><q0></q0><q1></q1><q2></q2><q3></q3><zjsp>2</zjsp><zj0>253|2011-7-28 17:10:19|ͬ��</zj0><zj1></zj1><zj2></zj2><zj3></zj3><fhsp>1</fhsp><fh0></fh0><fh1></fh1><fh2></fh2><fh3></fh3><type>3</type> </item> <item> <id>1109</id><typeName>�������̵���ʩ</typeName><organise>������������</organise><fzr></fzr><zw></zw><otel></otel><oadd></oadd><address>������������</address><thetime>2010-9-19 11:10:08</thetime><person>������������</person><sex></sex><age></age><idnumber></idnumber><ptel></ptel><padd></padd><sjsp>4</sjsp><sp1>122|2010-09-19 11:15:17|</sp1><sp2></sp2><sp3></sp3><sp4></sp4><qzsp>0</qzsp><q0></q0><q1></q1><q2></q2><q3></q3><zjsp>0</zjsp><zj0></zj0><zj1></zj1><zj2></zj2><zj3></zj3><fhsp>2</fhsp><fh0>122|2010-09-19 11:33:45|</fh0><fh1></fh1><fh2></fh2><fh3></fh3><type>4</type> </item> <item> <id>1290</id><typeName>��Ҫ�ֵ����ཨ������̨���Ŵ�����ҡ��ڷ�Ӱ���հ����Ʒ</typeName><organise>123</organise><fzr>123</fzr><zw>123</zw><otel>123</otel><oadd>12</oadd><address>123</address><thetime>2011-7-7 11:29:16</thetime><person></person><sex></sex><age></age><idnumber></idnumber><ptel></ptel><padd></padd><sjsp>3</sjsp><sp1>253|2011-7-28 16:18:56|ͬ��</sp1><sp2>253|2011-8-2 10:12:10|wr</sp2><sp3></sp3><sp4></sp4><qzsp>3</qzsp><q0>253|2011-7-28 16:23:54|ͬ��</q0><q1>253|2011-7-28 16:36:51|ͬ��</q1><q2></q2><q3></q3><zjsp>2</zjsp><zj0>253|2011-7-28 17:44:51|ͬ��</zj0><zj1></zj1><zj2></zj2><zj3></zj3><fhsp>2</fhsp><fh0>253|2011-7-28 17:46:34|ͬ��</fh0><fh1></fh1><fh2></fh2><fh3></fh3><type>4</type> </item> </ResultSet>");
//		
//		Config.user.setGroupid("2");
//		Config.user.setUserName("jl");
//		
//		return cr;
//	}
}
