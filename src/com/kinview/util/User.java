package com.kinview.util;

public class User
{
	/**
	 * 用户id
	 */
	private String userid;          
	/**
	 * 用户登陆名
	 */
	private String userName;        //登陆名
	/**
	 * 用户真实名字
	 */
	private String name; 	        //名字
	/**
	 * 用户权限
	 */
	private String groupid;	        //用户权限
	/**
	 * 用户定位
	 */
	private String station;	        //用户定位
	/**
	 * 用户签名存放路径
	 */
	private String idiograph;  	    //签名存放路径
	/**
	 * 用户执法证号
	 */
	private String zfzid;	        //执法证号
	/**
	 * 用户所在部门id
	 */
	private String branch;	        //部门
	/********************************************/
	
	/**
	 * 用户所在部门名称
	 */
	private  String branch_name ;   // 用户所在部门名称
	
	/**
	 * 用户所在部门机关代码
	 */
	private  String branch_jgdm ;   // 用户所在部门机关代码
	
	/**
	 * 用户所在部门电话
	 */
	private  String branch_phone ;  // 用户所在部门电话
	
	/**
	 *  用户所在部门地址
	 */
	private  String branch_address ; // 用户所在部门地址
	/********************************************/
	
	/**
	 * 同组用户ID
	 */
	private int  u_UserID ;/// 同组用户ID
	
	/**
	 * 同组用户名称
	 */
	private  String u_UserName ;/// 同组用户名称
	
	/**
	 * 同组用户执法证号
	 */
	private String u_ZfzID ; /// 同组用户执法证号
	
	/**
	 * 同组用户签名存放路径
	 */
	private String u_Idiograph ;/// 同组用户签名存放路径
	/********************************************/
	
	/**
	 * 用户id
	 */
	public String getUserid()
	{
		return userid;
	}
	/**
	 * 用户id
	 */
	public void setUserid(String userid)
	{
		this.userid = userid;
	}
	
	/**
	 * 用户登陆名
	 */
	public String getUserName()
	{
		return userName;
	}
	
	/**
	 * 用户登陆名
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	/**
	 * 用户真实姓名
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * 用户真实姓名
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
	/**
	 * 用户权限
	 */
	public String getGroupid()
	{
		return groupid;
	}
	
	/**
	 * 用户权限
	 */
	public void setGroupid(String groupid)
	{
		this.groupid = groupid;
	}
	
	/**
	 * 用户定位
	 */
	public String getStation()
	{
		return station;
	}
	
	/**
	 * 用户定位
	 */
	public void setStation(String station)
	{
		this.station = station;
	}
	
	/**
	 * 用户签名存放路径
	 */
	public String getIdiograph()
	{
		return idiograph;
	}
	
	/**
	 * 用户签名存放路径
	 */
	public void setIdiograph(String idiograph)
	{
		this.idiograph = idiograph;
	}
	
	/**
	 * 用户执法证号
	 */
	public String getZfzid()
	{
		return zfzid;
	}
	
	/**
	 * 用户执法证号
	 */
	public void setZfzid(String zfzid)
	{
		this.zfzid = zfzid;
	}
	
	/**
	 * 用户所在部门id
	 */
	public String getBranch()
	{
		return branch;
	}
	
	/**
	 * 用户所在部门id
	 */
	public void setBranch(String branch)
	{
		this.branch = branch;
	}
	
	/**
	 * 用户所在部门名称
	 */
	public String getBranch_name()
	{
		return branch_name;
	}
	
	/**
	 * 用户所在部门名称
	 */
	public void setBranch_name(String branchName)
	{
		branch_name = branchName;
	}
	
	/**
	 * 用户所在部门机关代码
	 */
	public String getBranch_jgdm()
	{
		return branch_jgdm;
	}
	
	/**
	 * 用户所在部门机关代码
	 */
	public void setBranch_jgdm(String branchJgdm)
	{
		branch_jgdm = branchJgdm;
	}
	
	/**
	 * 用户所在部门电话
	 */
	public String getBranch_phone()
	{
		return branch_phone;
	}
	
	/**
	 * 用户所在部门电话
	 */
	public void setBranch_phone(String branchPhone)
	{
		branch_phone = branchPhone;
	}
	
	/**
	 *  用户所在部门地址
	 */
	public String getBranch_address()
	{
		return branch_address;
	}
	
	/**
	 *  用户所在部门地址
	 */
	public void setBranch_address(String branchAddress)
	{
		branch_address = branchAddress;
	}
	
	/**
	 * 同组用户ID
	 */
	public int getU_UserID()
	{
		return u_UserID;
	}
	
	/**
	 * 同组用户ID
	 */
	public void setU_UserID(int uUserID)
	{
		u_UserID = uUserID;
	}
	
	/**
	 * 同组用户名称
	 */
	public String getU_UserName()
	{
		return u_UserName;
	}
	
	/**
	 * 同组用户名称
	 */
	public void setU_UserName(String uUserName)
	{
		u_UserName = uUserName;
	}
	
	/**
	 * 同组用户执法证号
	 */
	public String getU_ZfzID()
	{
		return u_ZfzID;
	}
	
	/**
	 * 同组用户执法证号
	 */
	public void setU_ZfzID(String uZfzID)
	{
		u_ZfzID = uZfzID;
	}
	
	/**
	 * 同组用户签名存放路径
	 */
	public String getU_Idiograph()
	{
		return u_Idiograph;
	}
	
	/**
	 * 同组用户签名存放路径
	 */
	public void setU_Idiograph(String uIdiograph)
	{
		u_Idiograph = uIdiograph;
	}
	
	
}
