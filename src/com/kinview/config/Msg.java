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
	public static final int LOGIN_CONTINUELOAD = 8;	//检查完更新继续加载
	public static final int LOGIN_FORCEUPDATE = 9;	//检查完更新继续加载
	public static final int LOGIN_NEEDUPDATE = 10;	//检查完更新继续加载
	public static final int LOGIN_CANCELAUTOLOGIN = 11;	//取消自动登陆
	public static final int LOGIN_OFFLINELOAD = 12;//开始离线登陆
	
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
	public static final int ACTIVITY_EXIT_REFRESH_PARENT=93;	//退出,同事刷新父窗口
	public static final int ACTIVITY_CHANAGE=94;
	public static final int ACTIVITY_NEEDUPDATE=95;	//升级
	public static final int ACTIVITY_FORCEUPDATE=96;	//强制升级
	
//	public static final int DIALOG_OK=71;
//	public static final int DIALOG_OKCANCEL=72;
	
	public static final int PASSWORD_MODIFIY_SUCCESS = 81;
	
	public static final int REQUEST_SUCCESS=101;//返回成功
	public static final int REQUEST_ERROR=102;//返回成功
	public static final int REQUEST_REPEAT=103;//返回重复上报,案件已被审批过
	
	public static final int OPERTOR_SUBMIT=111;	//提交
	
	public static final int TITLE_PROGRESS_START=121;
	public static final int TITLE_PROGRESS_STOP=122;
	
	public static final int GETCASELIST = 131;//获取要处理的案件
	public static final int GETFORMLIST = 132;//获取某一案件对应的表单文书
	public static final int GETCASEINFO = 133;//获取要处理的案件的详细信息
	
	public static final int FORM_REQUEST_SUCCESS = 141;//表单上报成功返回
	public static final int FORM_REQUEST_ERROR = 142;//表单上报失败返回
	public static final int ONBACK = 143;//退回到上一界面
	public static final int SUBMIT_BD_SUCCESS = 144;//上报表单成功
	public static final int OPERATE_INFO_SUCCESS = 145;//保存证据信息成功
	
	public static final int PROMPT_PRINT = 146;//提示打印窗口
	public static final int SEND_SONGDA_HUIZHENG = 147;//是否发送送达回证
	
	public static final int GET_SONGDA_HUIZHENG_SUCCESS = 148;//获取送达回证成功
	public static final int GET_FORMLIST_NO_PERMISSION = 149;//无权限 获取表单
	
}
