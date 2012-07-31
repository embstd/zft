package com.kinview.util;

public class Form {
	
	private int id;// 编号
	private String state  ="";//状态
	private String formName ="";//表单名称
	private String bd_module_id ="";//表单模版ID
	private String bd_id ="";//表单ID
	
	private String caseId ="";//案件ID
	private String hzId ="";//
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getBd_module_id() {
		return bd_module_id;
	}
	public void setBd_module_id(String bd_module_id) {
		this.bd_module_id = bd_module_id;
	}
	public String getBd_id() {
		return bd_id;
	}
	public void setBd_id(String bd_id) {
		this.bd_id = bd_id;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public String getHzId() {
		return hzId;
	}
	public void setHzId(String hzId) {
		this.hzId = hzId;
	}
	
	
}
