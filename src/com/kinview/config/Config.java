package com.kinview.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.telephony.TelephonyManager;

import com.kinview.entity.EvidenceInfo;
import com.kinview.entity.NewCase;
import com.kinview.entity.SongdaHuizheng;
import com.kinview.entity.TbType;
import com.kinview.thread.ThreadGetCaseInfo;
import com.kinview.thread.ThreadGetSongdaHuizheng;
import com.kinview.thread.ThreadSubmitBd;
import com.kinview.update.Apk;
import com.kinview.util.Case;
import com.kinview.util.EventMgr;
import com.kinview.util.Form;
import com.kinview.util.GetData;
import com.kinview.util.SystemSettingMgr;
import com.kinview.util.User;
import com.kinview.zft.R;

public class Config
{

	public static boolean debug = false;
//	 public static boolean debug = true;
	public static boolean  df = true;
	
	public static boolean offLineLoad = false; // 当前状态;
	public static boolean offLineOpen = false; // 打开离线登陆功能
	public static boolean gpsIsOpen = false; // 打开,关闭
	public static boolean boolean_thread_run = true; // 通知所有线程退出,程序马上关闭了
	public static float version = 0f; // 版本号
	public static int windowType = 0; // 0为默认大屏幕,1为320*478或者480，
	public static String windowStytle = "0"; // 窗口样式,经典,还是最新
	public static String appName = ""; // 程序名
	public static String packageName = ""; // 应用程序包名
	public static String statInfo = ""; // 获取手机id
	public static String phoneType = ""; // 手机型号
	public static String appPath = ""; // 文件缓存路径
	public static String appImgListPath = "";
//	public static String cityName = "常州市天宁区";
	public static final String DATABASE_NAME = "zft";
	public static int autologin_waiteseconds = 10; // 自动登录等待时间

	public static String password = "";
	public static final int webservice_timeout = 5;
	// public static Point point = new Point();
	// public static Point point4UploadPosition = new Point();
	public static long gps_timeFefresh = 0; // gps最后一次刷新时间
	public static String missionStat = ""; // 任务统计结果
	public static int isInArea = 2; // 是否在网格内 0不在网格,1在网格,2未知
	public static final long event_refresh_distence = 2 * 60 * 1000; // 2分钟刷新一次
	public static long event_refresh_start = 0;

	public static int  tb_typeLingNum = 0;  //tb_type表与服务器tb_type表对接
	
	// 3大线程
	// public static ThreadUploadReport threadUploadReport = null;
	// public static ThreadListener threadListener = null; ////监听线程
	// public static ThreadUpdatePosition threadUpdatePosition = null; ////监听线程

	// 城管通数据变量
	public static ArrayList<TbType> listtbtype = new ArrayList<TbType>();
	// public static ArrayList<Task> listTask = new ArrayList<Task>(); //下载的任务
	// public static ArrayList<History> listHistory = new ArrayList<History>();
	public static ArrayList<NewCase> listNewCases = new ArrayList<NewCase>();	//上报的任务
	// //上报的任务
	// public static ArrayList<Tip> listTip = new ArrayList<Tip>(); //今日提醒
	// public static ArrayList<Point> listPoint = new ArrayList<Point>(); //点

	// 消息句柄
	 public static Handler handlerTask = null;
	 public static Handler handlerTips = null;
	 public static Handler handlerHistory = null;

	 public static User user = new User();
	 public static String username = ""; // 用户登陆名
     public static String userid = ""; // 用户登陆名
	 public static String name = ""; // 记住用户的名字
	 public static String groupid = ""; //用户权限
	 public static String station = ""; //用户定位
	 public static String idiograph = ""; //用户签名存放路径
	 public static String zfzid = ""; //用户执法证号
	 public static String branch = ""; //用户所在部门id

	public static EventMgr eventMgr = null;

	public static GetData getData = null;

	// 基本配置参数
	public static String entrance_str = "";
	public static String serverip = ""; // 记录服务器地址
	public static String apk_url = "";
	public static String map_url = "";

	// 记住检查的版本号
	public static Apk apk = new Apk();
	
	//Add on 2012-04-28
	public static List<Case> currentCaseList = new ArrayList<Case>();
	public static List<Form> currentFormList = new ArrayList<Form>();
	public static List<NewCase> currentNewCaseList = new ArrayList<NewCase>();
	public static List<SongdaHuizheng> currentSongdaHuizhengList = new ArrayList<SongdaHuizheng>();
	
	public static ThreadGetCaseInfo threadGetCaseInfo;//获得案件详细信息
	public static ThreadSubmitBd threadSubmitBd;//上报表单线程
	public static ThreadGetSongdaHuizheng threadGetSongdaHuizheng = null;
	public static int caseId;
	public static int casePositionId;//案件 的位置id 可见的
	public static int bdPositionId;
	public static int bd_id_songda_huizheng = 119;//送达回证 表单id
	//pos打印用
	public static String otherTabBdContent = "";//两个tab Activity 时不可见的activity的里面的数据
	//上报用
	public static String otherTabBdContentSubmit = "";
	public static List<EvidenceInfo> listEvidenceInfo = new ArrayList<EvidenceInfo>();

	public static String cityName = "";//城市名称
	public static String cgUnitName = "";//城管单位名称
	public static String posFormTitle = "";//表单头名称  --字
	public static String cityTag = "";//城市名称表示
	public static String serverIpAddress = "";//服务器ip
	public static String cgUnitNameDaDui = "";//单位大队的名称
	public static String cgUnitNameZhiDui = "";//单位支队的名称
	
	public static void loadConfig(Context context)
	{
		// 记载系统配置,ip地址
		SystemSettingMgr ssMgr = new SystemSettingMgr(context);
		ssMgr.loadSystemSetting();
		serverip = ssMgr.getServerip_url();
		entrance_str = ssMgr.getEntrance_url();
		map_url = ssMgr.getMap_url();
		apk_url = ssMgr.getApk_url();

		// 如果配置信息没有,则加载默认
		if (serverip.equals(""))
		{
			ssMgr.replaceToDefault();
			ssMgr.loadSystemSetting();
			serverip = ssMgr.getServerip_url();
			entrance_str = ssMgr.getEntrance_url();
			map_url = ssMgr.getMap_url();
			apk_url = ssMgr.getApk_url();
		}

		// 加载程序包名,版本号
		PackageManager pm = context.getPackageManager();
		PackageInfo pi = null;
		try
		{
			pi = pm.getPackageInfo(context.getPackageName(), 0);
			packageName = pi.packageName;
			version = Float.parseFloat(pi.versionName.trim());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		// 加载程序名称
		Config.appName = context.getResources().getString(R.string.app_name);
		// Config.appName = Config.cityName + Config.appName;
		Config.appName = Config.cityName + "执法通";
		// appPath = "/sdcard/" + appName+"/";
		appPath = "/sdcard/" + "zft" + "/";
		appImgListPath = "/sdcard/" + "zft" + "/" + "imager" + "/";
		// 加载手机信息
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		packageName = packageName.substring(packageName.lastIndexOf(".") + 1,
				packageName.length());
		Config.statInfo = "simid=" + telephonyManager.getSimSerialNumber()
				+ "&" + "phoneid=" + telephonyManager.getDeviceId() + "&"
				+ "phonebrand=" + Build.MODEL + "&" + "app=" + packageName;
		Config.phoneType = Build.MODEL; // 获取手机型号
		// print.out("packageName="+packageName);
		// 初始化对象
		eventMgr = new EventMgr(context);

		getData = new GetData(context);
		
		

	}

	public static String getWebService_url()
	{

		String url = serverip + "/" + entrance_str;
		while (url.indexOf("\\") >= 0)
		{
			url = url.replace("\\", "/");
		}
		while (url.indexOf("//") >= 0)
		{
			url = url.replace("//", "/");
		}
		url = "http://" + url;

		// print.out(url);
		return url;
	}

	public static String getServerWebAppPath(String filename)
	{
		String url = entrance_str;
		print.out("entrance_str=" + entrance_str);
		while (url.indexOf("\\") >= 0)
		{
			url = url.replace("\\", "/");
		}
		while (url.indexOf("//") >= 0)
		{
			url = url.replace("//", "/");
		}
		while (url.indexOf("/") == 0)
		{
			url = url.substring(1, url.length());
		}
		url = url.substring(0, url.indexOf("/"));
		url = "/" + url + "/" + filename;
		while (url.indexOf("\\") >= 0)
		{
			url = url.replace("\\", "/");
		}
		while (url.indexOf("//") >= 0)
		{
			url = url.replace("//", "/");
		}

		url = "http://" + serverip + url;

		print.out("getServerWebAppPath=" + url);

		return url;
	}

	public static String getUpdate_url()
	{
		String url = getWebService_url();
		url = url.substring(0, url.lastIndexOf("/"));
		url = url.substring(0, url.lastIndexOf("/"));
		return url + "/" + "AnroidApk.xml";
	}

	public static String getApkXml_url()
	{
		String url = serverip + "/" + apk_url;
		while (url.indexOf("\\") >= 0)
		{
			url = url.replace("\\", "/");
		}
		while (url.indexOf("//") >= 0)
		{
			url = url.replace("//", "/");
		}
		url = "http://" + url;
		return url;
	}

	public static String getApk_url()
	{
		String url = getApkXml_url();
		url = url.substring(0, url.lastIndexOf("/") + 1);
		if (!apk.getCgt2zipUrl().equals(""))
		{
			url = url + Config.apk.getCgt2zipUrl();
		}
		else if (!apk.getZipUrl().equals(""))
		{
			url = url + Config.apk.getZipUrl();
		}
		else
		{
			url = url + Config.apk.getApkUrl();
		}
		return url;
	}

	public static String getMap_url()
	{
		String url = serverip + "/" + map_url + "/";
		while (url.indexOf("\\") >= 0)
		{
			url = url.replace("\\", "/");
		}
		while (url.indexOf("//") >= 0)
		{
			url = url.replace("//", "/");
		}
		url = "http://" + url;

		return url;
	}

	public static String timebyYMDHMS()
	{
		Calendar c = Calendar.getInstance();// 获得系统当前日期
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;// 系统日期从0开始算起
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);// 小时
		int minute = c.get(Calendar.MINUTE);// 分
        int second = c.get(Calendar.SECOND);// 秒
		return year + "年" + month + "月" + day + "日" + " " + hour + ":" + minute
				+ ":" + second;
	}
	
	public static String timebyYYYYmmDD()
	{
		Calendar c1 = Calendar.getInstance();// 获得系统当前日期
		int year = c1.get(Calendar.YEAR);
		int month = c1.get(Calendar.MONTH) + 1;// 系统日期从0开始算起
		int day = c1.get(Calendar.DAY_OF_MONTH);
		int hour = c1.get(Calendar.HOUR_OF_DAY);// 小时
		int minute = c1.get(Calendar.MINUTE);// 分
        int second = c1.get(Calendar.SECOND);// 秒
		return year + "/" + month + "/" + day + " " + hour + ":" + minute
				+ ":" + second;
	}
	
	//string to date to string
	public static String getXiandingTimeString(String startStr){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date;
		try {
			date = sdf.parse(startStr);
			Calendar rightNow = Calendar.getInstance();
			rightNow.setTime(date);
			int d = 3;
			int dayofweek = rightNow.get(Calendar.DAY_OF_WEEK);//要 -1 才是
//			System.out.println("dayofweek: " + dayofweek);
			
			//新
			int c = 0;
			int m = 0;
			if (dayofweek == 1){
				c = 0;
				m = d + (((c + d) -1)/5)*2;
			}else if (dayofweek == 7){
//				c = 6;//
				m = d + ((d -1)/5) * 2 + 1;
			}else {
				c = dayofweek  -1;
				m = d + (((c + d) -1)/5)*2;
			}
			rightNow.add(Calendar.DAY_OF_MONTH, m);
//			System.out.println("ppp:" +rightNow.get(Calendar.DAY_OF_MONTH));
//			System.out.println("ssss:" +sdf.format(rightNow.getTime()));
			return sdf.format(rightNow.getTime());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
//		return "";
	}
}
