package com.kinview.update;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.kinview.config.Config;
import com.kinview.config.print;

public class Apk {

	private float version = 0;
	private String apkUrl ="";
	private String type ="";
	private String zipUrl = "";
	private String cgt2zipUrl="";
	
	
	public String getCgt2zipUrl() {
		return cgt2zipUrl;
	}
	public void setCgt2zipUrl(String cgt2zipUrl) {
		this.cgt2zipUrl = cgt2zipUrl;
	}
	public String getZipUrl() {
		return zipUrl;
	}
	public void setZipUrl(String zip) {
		this.zipUrl = zip;
	}
	public float getVersion() {
		return version;
	}
	public void setVersion(float version) {
		this.version = version;
	}
	public String getApkUrl() {
		return apkUrl;
	}
	public void setApkUrl(String url) {
		this.apkUrl = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public boolean findUpdate() throws Exception{
//		Apk term = new Apk();
		MessageXml msgxml = Action.getXml(Config.getApkXml_url());
		if (msgxml.getType() != 0) {
			throw new Exception("服务器访问错误!");
		}
		String xml = msgxml.getXml();
		
		if(xml==null || xml.equals("")){
			return false;
		}
		xml = xml.replace("gb2312", "utf-8");
		
		print.out("apk xml ="+xml);
		
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dombuilder = domfac.newDocumentBuilder();
			ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
//			InputStream is = new FileInputStream("d:\\AnroidApk.xml");
			Document doc = dombuilder.parse(bais);
			// 获取根
			Element resultSet = doc.getDocumentElement();
			NodeList resultSetList = resultSet.getChildNodes();

			// 读数据
			if (resultSetList != null && resultSet.getNodeName().equals("ResultSet")) {
				//如果找到节点了那么 开始循环row;
				for(int i=0;i<resultSetList.getLength();i++){
					Node rowNode = resultSetList.item(i);
					if(rowNode.getNodeName().equals("row")){
						NodeList cols = rowNode.getChildNodes();
						for(int j=0;j<cols.getLength();j++){
							Node col = cols.item(j);
							if(col.getNodeName().toLowerCase().equals(("APKVERSION").toLowerCase())){
								Config.apk.setVersion(Float.parseFloat(getNodeValue(col)));
							}else if(col.getNodeName().toLowerCase().equals(("FORCEUPDATE").toLowerCase())){
								Config.apk.setType(getNodeValue(col));
							}else if(col.getNodeName().toLowerCase().equals(("APKURL").toLowerCase())){
								Config.apk.setApkUrl(getNodeValue(col));
							}else if(col.getNodeName().toLowerCase().equals(("ZIPURL").toLowerCase())){
								Config.apk.setZipUrl(getNodeValue(col));
							}else if(col.getNodeName().toLowerCase().equals(("CGT2ZIPURL").toLowerCase())){
								Config.apk.setCgt2zipUrl(getNodeValue(col));
							}
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private String getNodeValue(Node node){
		if(node.getChildNodes().item(0)==null){
			return "";
		}
		String s = node.getChildNodes().item(0).getNodeValue().trim();
		if(s.equals("null")){
			return "";
		}else{
			return s;
		}
	}
}
