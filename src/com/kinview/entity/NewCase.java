package com.kinview.entity;




public class NewCase
{
	
    public NewCase(){
		
	}
    
	/**��֯**/
    private String  Organise;
    private String  Fzr;
    private String  Zw;
    private String  Otel;
    private String  Oadd;
    
    /**����**/
    private String  Person;
    private String  Sex;
    private String  Age;
    private String  Idnumber;
    private String  Padd;
    private String  Ptel;
    
    /**��ͬ��**/
    private String  CaseId;
    private String  CaseAjlb;        //��ѡ�񰸼����    �ϱ������е�һѡ��
    private String  CaseAnYou;        //��ѡ����    �ϱ������еڶ�ѡ��
    private String  CaseWeiFaXW;      //��ѡ��Υ����Ϊ  �ϱ������е���ѡ��
    private String  Thetime;/// ����ʱ��
    private String  Address; /// �����ص�
    private String  Intro; /// ������
    private String  Create;    //������ID
    private String  Locker;     //ͬ�ϱ��봫
    private String  CreateName;    //����������
    private String  CreateTime;  /// ����ʱ��
    
   
    private int  CaseType ;/// �������� �ֻ�Ĭ��ֵ Ϊ    1
    private String  CaseTypeString; 
    
    private int  Fromtype ;/// ������Դ  �ֻ�Ĭ��ֵ Ϊ    2
    private String  FromtypeString;
    
    private int  Areaid ; /// ��������ID
    private String  AreaidString ; /// ��������ID
    
    private int  State ; /// ����״̬
    private String  StateString ;
    
    private int  Rid ; /// ������ܰ��֪����// 1������Ⱥ��  2�������������΢��  3����ڽ��ᣬӰ���С �ֶ�
    private String  RidString ;
    
    private int  Cid ;///�Ƿ�ѡ����ܰ��֪  3Ϊѡ����ִܰ��  2Ϊ����ִ��  1Ϊһ�㰸��
    private String  CidString ;
    
    private String  Lsh ;/// ������ˮ��
    
    private String  CoordX = "0"; /// ����X
    private String  CoordY = "0";  /// ����Y
    private String  FirstUpTime ;/// �״��ϱ�ʱ��
    private String  Flag ;/// �ð���״̬�Ƿ����ϱ��ɹ�Flag1���ϱ�  Flag2�ϱ��ɹ�  Flag3�ϱ�ʧ�� 
    
    private int    PicNum;	/// �ð���ͼƬ����
    private String  PicNumString;
    private String   PicList;
    private String[]  PicListStr;
    
    private int   VideoNum;
    private String  VideoNumString;
    private String   VideoList;
    private String[]  VideoListStr;
    
    /**�ֻ��������ͷָ��˰�������֯����**/
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