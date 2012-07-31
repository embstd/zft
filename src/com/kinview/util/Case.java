package com.kinview.util;

import java.io.Serializable;

public class Case implements Serializable{
	
	private int id;
	private String organise ="";
	private String person ="";
	private String createtime ="";
	
//	private String organizationName;
//	private String litigant;
	
//	public String getOrganizationName() {
//		return organizationName;
//	}
//	public void setOrganizationName(String organizationName) {
//		this.organizationName = organizationName;
//	}
//	public String getLitigant() {
//		return litigant;
//	}
//	public void setLitigant(String litigant) {
//		this.litigant = litigant;
//	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getOrganise() {
		return organise;
	}
	public void setOrganise(String organise) {
		this.organise = organise;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	
	
	
}
