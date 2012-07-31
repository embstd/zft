package com.kinview.config;

public class Msg
{
	
	public static final int LOGIN_USRERROR = 1;
	public static final int LOGIN_SERVERERROR = 2;
	public static final int LOGIN_LOCKPANEL = 3;
	public static final int LOGIN_UNLOCKPANEL = 4;
	public static final int LOGIN_RUNLOGINTHREAD = 5;
	public static final int LOGIN_OFFLINELOADERROR = 6;
//	public static final int LOGIN_LOADSPINNER = 7;
	public static final int LOGIN_CONTINUELOAD = 8;	//�������¼�������
	public static final int LOGIN_FORCEUPDATE = 9;	//�������¼�������
	public static final int LOGIN_NEEDUPDATE = 10;	//�������¼�������
	public static final int LOGIN_CANCELAUTOLOGIN = 11;	//ȡ���Զ���½
	public static final int LOGIN_OFFLINELOAD = 12;//��ʼ���ߵ�½
	
	public static final int MODIFYPWD_SUCCESS = 21;
	public static final int MODIFYPWD_ERROR = 22;
	
	public static final int UPDATE_EVENT_SUCCESS=31;
	public static final int UPDATE_ERROR=32;
	public static final int UPDATE_GETMAP_SUCCESS=33;
	public static final int UPDATE_GETMAPFILE_SUCCESS=34;
	public static final int UPDATE_UNZIPGETMAPFILE_SUCCESS=35;
	public static final int UPDATE_UNZIPGETMAPFILE_ERROR=36;
	
	public static final int REFREASH=100;
	
	public static final int TASK_UPDATE=41;
	
	
	public static final int LISTVIEW_SHOW=51;
	public static final int LISTVIEW_REFRESH=52;
	
	public static final int ERROR_SERVER_CONNECT=61;
	
	
	public static final int PROGRAM_EXIT=91;
	public static final int ACTIVITY_EXIT=92;
	public static final int ACTIVITY_EXIT_REFRESH_PARENT=93;	//�˳�,ͬ��ˢ�¸�����
	public static final int ACTIVITY_CHANAGE=94;
	public static final int ACTIVITY_NEEDUPDATE=95;	//����
	public static final int ACTIVITY_FORCEUPDATE=96;	//ǿ������
	
//	public static final int DIALOG_OK=71;
//	public static final int DIALOG_OKCANCEL=72;
	
	public static final int PASSWORD_MODIFIY_SUCCESS = 81;
	
	public static final int REQUEST_SUCCESS=101;//���سɹ�
	public static final int REQUEST_ERROR=102;//���سɹ�
	public static final int REQUEST_REPEAT=103;//�����ظ��ϱ�,�����ѱ�������
	
	public static final int OPERTOR_SUBMIT=111;	//�ύ
	
	public static final int TITLE_PROGRESS_START=121;
	public static final int TITLE_PROGRESS_STOP=122;
	
	public static final int GETCASELIST = 131;//��ȡҪ����İ���
	public static final int GETFORMLIST = 132;//��ȡĳһ������Ӧ�ı�����
	public static final int GETCASEINFO = 133;//��ȡҪ����İ�������ϸ��Ϣ
	
	public static final int FORM_REQUEST_SUCCESS = 141;//���ϱ��ɹ�����
	public static final int FORM_REQUEST_ERROR = 142;//���ϱ�ʧ�ܷ���
	public static final int ONBACK = 143;//�˻ص���һ����
	public static final int SUBMIT_BD_SUCCESS = 144;//�ϱ����ɹ�
	public static final int OPERATE_INFO_SUCCESS = 145;//����֤����Ϣ�ɹ�
	
	public static final int PROMPT_PRINT = 146;//��ʾ��ӡ����
	public static final int SEND_SONGDA_HUIZHENG = 147;//�Ƿ����ʹ��֤
	
	public static final int GET_SONGDA_HUIZHENG_SUCCESS = 148;//��ȡ�ʹ��֤�ɹ�
	public static final int GET_FORMLIST_NO_PERMISSION = 149;//��Ȩ�� ��ȡ��
	
}
