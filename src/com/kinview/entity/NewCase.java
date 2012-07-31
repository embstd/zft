package com.kinview.entity;




public class NewCase
{
	
    public NewCase(){
		
	}
    
	/**组织**/
    private String  Organise;
    private String  Fzr;
    private String  Zw;
    private String  Otel;
    private String  Oadd;
    
    /**个人**/
    private String  Person;
    private String  Sex;
    private String  Age;
    private String  Idnumber;
    private String  Padd;
    private String  Ptel;
    
    /**共同点**/
    private String  CaseId;
    private String  CaseAjlb;        //请选择案件类别    上报案件中第一选项
    private String  CaseAnYou;        //请选择案由    上报案件中第二选项
    private String  CaseWeiFaXW;      //请选择违法行为  上报案件中第三选项
    private String  Thetime;/// 案发时间
    private String  Address; /// 案发地点
    private String  Intro; /// 简单描述
    private String  Create;    //创建人ID
    private String  Locker;     //同上必须传
    private String  CreateName;    //创建人名称
    private String  CreateTime;  /// 创建时间
    
   
    private int  CaseType ;/// 案件类型 手机默认值 为    1
    private String  CaseTypeString; 
    
    private int  Fromtype ;/// 案件来源  手机默认值 为    2
    private String  FromtypeString;
    
    private int  Areaid ; /// 责任区域ID
    private String  AreaidString ; /// 责任区域ID
    
    private int  State ; /// 案件状态
    private String  StateString ;
    
    private int  Rid ; /// 案件温馨告知类型// 1、弱势群体  2、初犯，情节轻微。  3、情节较轻，影响较小 字段
    private String  RidString ;
    
    private int  Cid ;///是否选择温馨告知  3为选择温馨执法  2为简易执法  1为一般案件
    private String  CidString ;
    
    private String  Lsh ;/// 案件流水号
    
    private String  CoordX = "0"; /// 坐标X
    private String  CoordY = "0";  /// 坐标Y
    private String  FirstUpTime ;/// 首次上报时间
    private String  Flag ;/// 该案件状态是否已上报成功Flag1已上报  Flag2上报成功  Flag3上报失败 
    
    private int    PicNum;	/// 该案件图片数量
    private String  PicNumString;
    private String   PicList;
    private String[]  PicListStr;
    
    private int   VideoNum;
    private String  VideoNumString;
    private String   VideoList;
    private String[]  VideoListStr;
    
    /**手机案件类型分个人案件和组织案件**/
    private int  Type;
    private String  TypeString;
    
    private String Lay;
    private String Rul;
    private String Yiju;
    private String Fazhe;
    
	public String getOrganise()
	{
		return Organise;
	}
	public void setOrganise(String organise)
	{
		Organise = organise;
	}
	public String getFzr()
	{
		return Fzr;
	}
	public void setFzr(String fzr)
	{
		Fzr = fzr;
	}
	public String getZw()
	{
		return Zw;
	}
	public void setZw(String zw)
	{
		Zw = zw;
	}
	public String getOtel()
	{
		return Otel;
	}
	public void setOtel(String otel)
	{
		Otel = otel;
	}
	public String getOadd()
	{
		return Oadd;
	}
	public void setOadd(String oadd)
	{
		Oadd = oadd;
	}
	public String getPerson()
	{
		return Person;
	}
	public void setPerson(String person)
	{
		Person = person;
	}
	public String getSex()
	{
		return Sex;
	}
	public void setSex(String sex)
	{
		Sex = sex;
	}
	public String getAge()
	{
		return Age;
	}
	public void setAge(String age)
	{
		Age = age;
	}
	public String getIdnumber()
	{
		return Idnumber;
	}
	public void setIdnumber(String idnumber)
	{
		Idnumber = idnumber;
	}
	public String getPadd()
	{
		return Padd;
	}
	public void setPadd(String padd)
	{
		Padd = padd;
	}
	public String getPtel()
	{
		return Ptel;
	}
	public void setPtel(String ptel)
	{
		Ptel = ptel;
	}
	public String getCaseAjlb()
	{
		return CaseAjlb;
	}
	public void setCaseAjlb(String caseAjlb)
	{
		CaseAjlb = caseAjlb;
	}
	public String getCaseAnYou()
	{
		return CaseAnYou;
	}
	public void setCaseAnYou(String caseAnYou)
	{
		CaseAnYou = caseAnYou;
	}
	public String getCaseWeiFaXW()
	{
		return CaseWeiFaXW;
	}
	public void setCaseWeiFaXW(String caseWeiFaXW)
	{
		CaseWeiFaXW = caseWeiFaXW;
	}
	public String getThetime()
	{
		return Thetime;
	}
	public void setThetime(String thetime)
	{
		Thetime = thetime;
	}
	public String getAddress()
	{
		return Address;
	}
	public void setAddress(String address)
	{
		Address = address;
	}
	public String getIntro()
	{
		return Intro;
	}
	public void setIntro(String intro)
	{
		Intro = intro;
	}
	public String getCreate()
	{
		return Create;
	}
	public void setCreate(String create)
	{
		Create = create;
	}
	public String getLocker()
	{
		return Locker;
	}
	public void setLocker(String locker)
	{
		Locker = locker;
	}
	public String getCreateName()
	{
		return CreateName;
	}
	public void setCreateName(String createName)
	{
		CreateName = createName;
	}
	public String getCreateTime()
	{
		return CreateTime;
	}
	public void setCreateTime(String createTime)
	{
		CreateTime = createTime;
	}
	
	//****************************getCaseType*********************************/
	public int getCaseType()
	{
		return CaseType;
	}
	public void setCaseType(int caseType)
	{
		CaseType = caseType;
	}
	public String getCaseTypeString()
	{
		CaseTypeString = String.valueOf(getCaseType());
		return CaseTypeString;
	}
	//****************************getCaseType*********************************/
	
	//****************************getFromtype*********************************/
	public int getFromtype()
	{
		return Fromtype;
	}
	public void setFromtype(int fromtype)
	{
		Fromtype = fromtype;
	}
	public String getFromtypeString()
	{
		FromtypeString = String.valueOf(getFromtype());
		return FromtypeString;
	}
	//****************************getFromtype*********************************/
	
	
	//****************************getAreaid*********************************/
	public int getAreaid()
	{
		return Areaid;
	}
	public void setAreaid(int areaid)
	{
		Areaid = areaid;
	}
	public String getAreaidString()
	{
		AreaidString = String.valueOf(getAreaid());
		return AreaidString;
	}
	//****************************getAreaid*********************************/
	
	//****************************getState*********************************/
	public int getState()
	{
		return State;
	}
	public void setState(int state)
	{
		State = state;
	}
	public String getStateString()
	{
		StateString = String.valueOf(getState());
		return StateString;
	}
	//****************************getState*********************************/
	
	//****************************getRid*********************************/
	public int getRid()
	{
		return Rid;
	}
	public void setRid(int rid)
	{
		Rid = rid;
	}
	public String getRidString()
	{
		RidString = String.valueOf(getRid());
		return RidString;
	}
	//****************************getRid*********************************/
	
	//****************************getCid*********************************/
	public int getCid()
	{
		return Cid;
	}
	public void setCid(int cid)
	{
		Cid = cid;
	}
	public String getCidString()
	{
		CidString = String.valueOf(getCid());
		return CidString;
	}
	//****************************getCid*********************************/
	
	//****************************getLsh*********************************/
	public String getLsh()
	{
		return Lsh;
	}
	public void setLsh(String lsh)
	{
		Lsh = lsh;
	}

	
	//****************************getLsh*********************************/
	
	public String getCoordX()
	{
		return CoordX;
	}
	public void setCoordX(String coordX)
	{
		CoordX = coordX;
	}
	public String getCoordY()
	{
		return CoordY;
	}
	public void setCoordY(String coordY)
	{
		CoordY = coordY;
	}
	public String getFirstUpTime()
	{
		return FirstUpTime;
	}
	public void setFirstUpTime(String firstUpTime)
	{
		FirstUpTime = firstUpTime;
	}
	public String getFlag()
	{
		return Flag;
	}
	public void setFlag(String flag)
	{
		Flag = flag;
	}
	
	//****************************getPicNum*********************************/
	public int getPicNum()
	{
		return PicNum;
	}
	public void setPicNum(int picNum)
	{
		PicNum = picNum;
	}
	public String getPicNumString()
	{
		PicNumString = String.valueOf(getPicNum());
		return PicNumString;
	}
	//****************************getPicNum*********************************/
	
	public String getPicList()
	{
		return PicList;
	}
	public void setPicList(String picList)
	{
		PicList = picList;
	}
	public String[] getPicListStr()
	{
		return PicListStr;
	}
	public void setPicListStr(String[] picListStr)
	{
		PicListStr = picListStr;
	}

	//*********************getVideoNum***********************************/
	public int getVideoNum()
	{
		return VideoNum;
	}
	public void setVideoNum(int videoNum)
	{
		VideoNum = videoNum;
	}
	public String getVideoNumString()
	{
		VideoNumString = String.valueOf(getVideoNum());
		return VideoNumString;
	}
//*********************getVideoNum***********************************/
	
	public String getVideoList()
	{
		return VideoList;
	}
	public void setVideoList(String videoList)
	{
		VideoList = videoList;
	}
	public String[] getVideoListStr()
	{
		return VideoListStr;
	}
	public void setVideoListStr(String[] videoListStr)
	{
		VideoListStr = videoListStr;
	}
	
  //*********************getType***********************************/
	public int getType()
	{
		return Type;
	}
	public void setType(int type)
	{
		Type = type;
	}
	
	public String getTypeString()
	{
		if(getType() == 1)
		{
			TypeString = "1";
		}
		else if(getType() == 2)
		{
			TypeString = "2";
		}
		return TypeString;
	}
	
	
	public String getCaseId()
	{
		return CaseId;
	}
	public void setCaseId(String caseId)
	{
		CaseId = caseId;
	}
	
	
	public String getLay()
	{
		return Lay;
	}
	public void setLay(String lay)
	{
		Lay = lay;
	}
	public String getRul()
	{
		return Rul;
	}
	public void setRul(String rul)
	{
		Rul = rul;
	}
	public String getYiju()
	{
		return Yiju;
	}
	public void setYiju(String yiju)
	{
		Yiju = yiju;
	}
	public String getFazhe()
	{
		return Fazhe;
	}
	public void setFazhe(String fazhe)
	{
		Fazhe = fazhe;
	}
	
	
	
   
    
	
	
   

	

}