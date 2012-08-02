package com.kinview.entity;

public class SongdaHuizheng {
	private String typeName = "";//案由
	private String organise = "";//组织名称
	private String oadd = "";//组织地址
	private String person = "";//个人名称
	private String padd = "";//个人地址
	private String lsh  = "";//流水号
	
	private String  CaseAnYou;        //请选择案由    上报案件中第二选项
	private String  Address; /// 案发地点
	
	
	public String getCaseAnYou() {
		return CaseAnYou;
	}
	public void setCaseAnYou(String caseAnYou) {
		CaseAnYou = caseAnYou;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getOrganise() {
		return organise;
	}
	public void setOrganise(String organise) {
		this.organise = organise;
	}
	public String getOadd() {
		return oadd;
	}
	public void setOadd(String oadd) {
		this.oadd = oadd;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getPadd() {
		return padd;
	}
	public void setPadd(String padd) {
		this.padd = padd;
	}
	public String getLsh() {
		return lsh;
	}
	public void setLsh(String lsh) {
		this.lsh = lsh;
	}
	
	
}
