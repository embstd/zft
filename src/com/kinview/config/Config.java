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
	
	public static boolean offLineLoad = false; // ��ǰ״̬;
	public static boolean offLineOpen = false; // �����ߵ�½����
	public static boolean gpsIsOpen = false; // ��,�ر�
	public static boolean boolean_thread_run = true; // ֪ͨ�����߳��˳�,�������Ϲر���
	public static float version = 0f; // �汾��
	public static int windowType = 0; // 0ΪĬ�ϴ���Ļ,1Ϊ320*478����480��
	public static String windowStytle = "0"; // ������ʽ,����,��������
	public static String appName = ""; // ������
	public static String packageName = ""; // Ӧ�ó������
	public static String statInfo = ""; // ��ȡ�ֻ�id
	public static String phoneType = ""; // �ֻ��ͺ�
	public static String appPath = ""; // �ļ�����·��
	public static String appImgListPath = "";
//	public static String cityName = "������������";
	public static final String DATABASE_NAME = "zft";
	public static int autologin_waiteseconds = 10; // �Զ���¼�ȴ�ʱ��

	public static String password = "";
	public static final int webservice_timeout = 5;
	// public static Point point = new Point();
	// public static Point point4UploadPosition = new Point();
	public static long gps_timeFefresh = 0; // gps���һ��ˢ��ʱ��
	public static String missionStat = ""; // ����ͳ�ƽ��
	public static int isInArea = 2; // �Ƿ��������� 0��������,1������,2δ֪
	public static final long event_refresh_distence = 2 * 60 * 1000; // 2����ˢ��һ��
	public static long event_refresh_start = 0;

	public static int  tb_typeLingNum = 0;  //tb_type���������tb_type��Խ�
	
	// 3���߳�
	// public static ThreadUploadReport threadUploadReport = null;
	// public static ThreadListener threadListener = null; ////�����߳�
	// public static ThreadUpdatePosition threadUpdatePosition = null; ////�����߳�

	// �ǹ�ͨ���ݱ���
	public static ArrayList<TbType> listtbtype = new ArrayList<TbType>();
	// public static ArrayList<Task> listTask = new ArrayList<Task>(); //���ص�����
	// public static ArrayList<History> listHistory = new ArrayList<History>();
	public static ArrayList<NewCase> listNewCases = new ArrayList<NewCase>();	//�ϱ�������
	// //�ϱ�������
	// public static ArrayList<Tip> listTip = new ArrayList<Tip>(); //��������
	// public static ArrayList<Point> listPoint = new ArrayList<Point>(); //��

	// ��Ϣ���
	 public static Handler handlerTask = null;
	 public static Handler handlerTips = null;
	 public static Handler handlerHistory = null;

	 public static User user = new User();
	 public static String username = ""; // �û���½��
     public static String userid = ""; // �û���½��
	 public static String name = ""; // ��ס�û�������
	 public static String groupid = ""; //�û�Ȩ��
	 public static String station = ""; //�û���λ
	 public static String idiograph = ""; //�û�ǩ�����·��
	 public static String zfzid = ""; //�û�ִ��֤��
	 public static String branch = ""; //�û����ڲ���id

	public static EventMgr eventMgr = null;

	public static GetData getData = null;

	// �������ò���
	public static String entrance_str = "";
	public static String serverip = ""; // ��¼��������ַ
	public static String apk_url = "";
	public static String map_url = "";

	// ��ס���İ汾��
	public static Apk apk = new Apk();
	
	//Add on 2012-04-28
	public static List<Case> currentCaseList = new ArrayList<Case>();
	public static List<Form> currentFormList = new ArrayList<Form>();
	public static List<NewCase> currentNewCaseList = new ArrayList<NewCase>();
	public static List<SongdaHuizheng> currentSongdaHuizhengList = new ArrayList<SongdaHuizheng>();
	
	public static ThreadGetCaseInfo threadGetCaseInfo;//��ð�����ϸ��Ϣ
	public static ThreadSubmitBd threadSubmitBd;//�ϱ����߳�
	public static ThreadGetSongdaHuizheng threadGetSongdaHuizheng = null;
	public static int caseId;
	public static int casePositionId;//���� ��λ��id �ɼ���
	public static int bdPositionId;
	public static int bd_id_songda_huizheng = 119;//�ʹ��֤ ��id
	//pos��ӡ��
	public static String otherTabBdContent = "";//����tab Activity ʱ���ɼ���activity�����������
	//�ϱ���
	public static String otherTabBdContentSubmit = "";
	public static List<EvidenceInfo> listEvidenceInfo = new ArrayList<EvidenceInfo>();

	public static String cityName = "";//��������
	public static String cgUnitName = "";//�ǹܵ�λ����
	public static String posFormTitle = "";//��ͷ����  --��
	public static String cityTag = "";//�������Ʊ�ʾ
	public static String serverIpAddress = "";//������ip
	public static String cgUnitNameDaDui = "";//��λ��ӵ�����
	public static String cgUnitNameZhiDui = "";//��λ֧�ӵ�����
	
	public static void loadConfig(Context context)
	{
		// ����ϵͳ����,ip��ַ
		SystemSettingMgr ssMgr = new SystemSettingMgr(context);
		ssMgr.loadSystemSetting();
		serverip = ssMgr.getServerip_url();
		entrance_str = ssMgr.getEntrance_url();
		map_url = ssMgr.getMap_url();
		apk_url = ssMgr.getApk_url();

		// ���������Ϣû��,�����Ĭ��
		if (serverip.equals(""))
		{
			ssMgr.replaceToDefault();
			ssMgr.loadSystemSetting();
			serverip = ssMgr.getServerip_url();
			entrance_str = ssMgr.getEntrance_url();
			map_url = ssMgr.getMap_url();
			apk_url = ssMgr.getApk_url();
		}

		// ���س������,�汾��
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

		// ���س�������
		Config.appName = context.getResources().getString(R.string.app_name);
		// Config.appName = Config.cityName + Config.appName;
		Config.appName = Config.cityName + "ִ��ͨ";
		// appPath = "/sdcard/" + appName+"/";
		appPath = "/sdcard/" + "zft" + "/";
		appImgListPath = "/sdcard/" + "zft" + "/" + "imager" + "/";
		// �����ֻ���Ϣ
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		packageName = packageName.substring(packageName.lastIndexOf(".") + 1,
				packageName.length());
		Config.statInfo = "simid=" + telephonyManager.getSimSerialNumber()
				+ "&" + "phoneid=" + telephonyManager.getDeviceId() + "&"
				+ "phonebrand=" + Build.MODEL + "&" + "app=" + packageName;
		Config.phoneType = Build.MODEL; // ��ȡ�ֻ��ͺ�
		// print.out("packageName="+packageName);
		// ��ʼ������
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
		Calendar c = Calendar.getInstance();// ���ϵͳ��ǰ����
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;// ϵͳ���ڴ�0��ʼ����
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);// Сʱ
		int minute = c.get(Calendar.MINUTE);// ��
        int second = c.get(Calendar.SECOND);// ��
		return year + "��" + month + "��" + day + "��" + " " + hour + ":" + minute
				+ ":" + second;
	}
	
	public static String timebyYYYYmmDD()
	{
		Calendar c1 = Calendar.getInstance();// ���ϵͳ��ǰ����
		int year = c1.get(Calendar.YEAR);
		int month = c1.get(Calendar.MONTH) + 1;// ϵͳ���ڴ�0��ʼ����
		int day = c1.get(Calendar.DAY_OF_MONTH);
		int hour = c1.get(Calendar.HOUR_OF_DAY);// Сʱ
		int minute = c1.get(Calendar.MINUTE);// ��
        int second = c1.get(Calendar.SECOND);// ��
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
			int dayofweek = rightNow.get(Calendar.DAY_OF_WEEK);//Ҫ -1 ����
//			System.out.println("dayofweek: " + dayofweek);
			
			//��
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
