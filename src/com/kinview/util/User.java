package com.kinview.util;

public class User
{
	/**
	 * �û�id
	 */
	private String userid;          
	/**
	 * �û���½��
	 */
	private String userName;        //��½��
	/**
	 * �û���ʵ����
	 */
	private String name; 	        //����
	/**
	 * �û�Ȩ��
	 */
	private String groupid;	        //�û�Ȩ��
	/**
	 * �û���λ
	 */
	private String station;	        //�û���λ
	/**
	 * �û�ǩ�����·��
	 */
	private String idiograph;  	    //ǩ�����·��
	/**
	 * �û�ִ��֤��
	 */
	private String zfzid;	        //ִ��֤��
	/**
	 * �û����ڲ���id
	 */
	private String branch;	        //����
	/********************************************/
	
	/**
	 * �û����ڲ�������
	 */
	private  String branch_name ;   // �û����ڲ�������
	
	/**
	 * �û����ڲ��Ż��ش���
	 */
	private  String branch_jgdm ;   // �û����ڲ��Ż��ش���
	
	/**
	 * �û����ڲ��ŵ绰
	 */
	private  String branch_phone ;  // �û����ڲ��ŵ绰
	
	/**
	 *  �û����ڲ��ŵ�ַ
	 */
	private  String branch_address ; // �û����ڲ��ŵ�ַ
	/********************************************/
	
	/**
	 * ͬ���û�ID
	 */
	private int  u_UserID ;/// ͬ���û�ID
	
	/**
	 * ͬ���û�����
	 */
	private  String u_UserName ;/// ͬ���û�����
	
	/**
	 * ͬ���û�ִ��֤��
	 */
	private String u_ZfzID ; /// ͬ���û�ִ��֤��
	
	/**
	 * ͬ���û�ǩ�����·��
	 */
	private String u_Idiograph ;/// ͬ���û�ǩ�����·��
	/********************************************/
	
	/**
	 * �û�id
	 */
	public String getUserid()
	{
		return userid;
	}
	/**
	 * �û�id
	 */
	public void setUserid(String userid)
	{
		this.userid = userid;
	}
	
	/**
	 * �û���½��
	 */
	public String getUserName()
	{
		return userName;
	}
	
	/**
	 * �û���½��
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	/**
	 * �û���ʵ����
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * �û���ʵ����
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * �û�Ȩ��
	 */
	public String getGroupid()
	{
		return groupid;
	}
	
	/**
	 * �û�Ȩ��
	 */
	public void setGroupid(String groupid)
	{
		this.groupid = groupid;
	}
	
	/**
	 * �û���λ
	 */
	public String getStation()
	{
		return station;
	}
	
	/**
	 * �û���λ
	 */
	public void setStation(String station)
	{
		this.station = station;
	}
	
	/**
	 * �û�ǩ�����·��
	 */
	public String getIdiograph()
	{
		return idiograph;
	}
	
	/**
	 * �û�ǩ�����·��
	 */
	public void setIdiograph(String idiograph)
	{
		this.idiograph = idiograph;
	}
	
	/**
	 * �û�ִ��֤��
	 */
	public String getZfzid()
	{
		return zfzid;
	}
	
	/**
	 * �û�ִ��֤��
	 */
	public void setZfzid(String zfzid)
	{
		this.zfzid = zfzid;
	}
	
	/**
	 * �û����ڲ���id
	 */
	public String getBranch()
	{
		return branch;
	}
	
	/**
	 * �û����ڲ���id
	 */
	public void setBranch(String branch)
	{
		this.branch = branch;
	}
	
	/**
	 * �û����ڲ�������
	 */
	public String getBranch_name()
	{
		return branch_name;
	}
	
	/**
	 * �û����ڲ�������
	 */
	public void setBranch_name(String branchName)
	{
		branch_name = branchName;
	}
	
	/**
	 * �û����ڲ��Ż��ش���
	 */
	public String getBranch_jgdm()
	{
		return branch_jgdm;
	}
	
	/**
	 * �û����ڲ��Ż��ش���
	 */
	public void setBranch_jgdm(String branchJgdm)
	{
		branch_jgdm = branchJgdm;
	}
	
	/**
	 * �û����ڲ��ŵ绰
	 */
	public String getBranch_phone()
	{
		return branch_phone;
	}
	
	/**
	 * �û����ڲ��ŵ绰
	 */
	public void setBranch_phone(String branchPhone)
	{
		branch_phone = branchPhone;
	}
	
	/**
	 *  �û����ڲ��ŵ�ַ
	 */
	public String getBranch_address()
	{
		return branch_address;
	}
	
	/**
	 *  �û����ڲ��ŵ�ַ
	 */
	public void setBranch_address(String branchAddress)
	{
		branch_address = branchAddress;
	}
	
	/**
	 * ͬ���û�ID
	 */
	public int getU_UserID()
	{
		return u_UserID;
	}
	
	/**
	 * ͬ���û�ID
	 */
	public void setU_UserID(int uUserID)
	{
		u_UserID = uUserID;
	}
	
	/**
	 * ͬ���û�����
	 */
	public String getU_UserName()
	{
		return u_UserName;
	}
	
	/**
	 * ͬ���û�����
	 */
	public void setU_UserName(String uUserName)
	{
		u_UserName = uUserName;
	}
	
	/**
	 * ͬ���û�ִ��֤��
	 */
	public String getU_ZfzID()
	{
		return u_ZfzID;
	}
	
	/**
	 * ͬ���û�ִ��֤��
	 */
	public void setU_ZfzID(String uZfzID)
	{
		u_ZfzID = uZfzID;
	}
	
	/**
	 * ͬ���û�ǩ�����·��
	 */
	public String getU_Idiograph()
	{
		return u_Idiograph;
	}
	
	/**
	 * ͬ���û�ǩ�����·��
	 */
	public void setU_Idiograph(String uIdiograph)
	{
		u_Idiograph = uIdiograph;
	}
	
	
}
