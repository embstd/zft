package com.kinview.util;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.kinview.config.DB;
import com.kinview.config.print;

public class EventMgr {
	private final String table_createSql = "create table if not exists event(eventTypeID int,eventTypeName text,mainTypeID int,mainTypeName text,subTypeID int,subTypeText text,reportStandard text,checkStandard text);";
	private final String table_name = "event";
	private DB db;
	private ArrayList<Event> itemList = new ArrayList<Event>();

	public EventMgr(Context context) {
		db = new DB(context, table_createSql);
		createTable(); // 检查表格是创建
	}

	private void createTable() {
		SQLiteDatabase sdb = db.mOpenHelper.getWritableDatabase();
		String sql = table_createSql;
		try {
			// String sql2 = "drop table " + table_name;
			// sdb.execSQL(sql2);
			sdb.execSQL(sql);
			// print.out("db success----数据表成功重建");
		} catch (SQLException e) {
			// print.out("de falil----数据表重建错误");
		}
		db.mOpenHelper.close();
	}

	private void insert(Event item) {
		// eventTypeID int,eventTypeName text,mainTypeID int,mainTypeName
		// text,subTypeID int
		// ,subTypeText text,reportStandard text,checkStandard text
		String sql = "insert into "
				+ table_name
				+ "(eventTypeID ,eventTypeName ,mainTypeID ,mainTypeName ,subTypeID "
				+ ",subTypeText ,reportStandard ,checkStandard)" + "values("
				+ "" + item.getEventTypeID() + "," + "'"
				+ item.getEventTypeName() + "'," + "" + item.getMainTypeID()
				+ "," + "'" + item.getMainTypeName() + "'," + ""
				+ item.getSubTypeID() + ","

				+ "'" + item.getSubTypeText() + "'," + "'"
				+ item.getReportStandard() + "'," + "'"
				+ item.getCheckStandard() + "');";
		// print.out("insert item="+sql);
		execSQL(sql);
	}

	public void clearDataBase() {
		String sql = "delete  from " + table_name + ";";
		execSQL(sql);
	}

	private void execSQL(String sql) {
		if (sql.equals(""))
			return;
		SQLiteDatabase sdb = db.mOpenHelper.getWritableDatabase();
		try {
			sdb.execSQL(sql);
		} catch (SQLException e) {
		} finally {
			db.mOpenHelper.close();
		}
	}

	public void save(ArrayList<Event> itemList) {
		for (int i = 0; i < itemList.size(); i++) {
			insert(itemList.get(i));
		}
	}

	public Event getEventById(int eventTypeID, int mainTypeID, int subTypeID) {
		Event item = null;
		SQLiteDatabase sdb = db.mOpenHelper.getReadableDatabase();
		String col[] = { "eventTypeID", "eventTypeName", "mainTypeID",
				"mainTypeName", "subTypeID", "subTypeText", "reportStandard",
				"checkStandard" };
		String selection = "eventTypeID=" + eventTypeID + " and mainTypeID="
				+ mainTypeID + " and subTypeID=" + subTypeID;
		
		print.out("Event getEventById sql="+selection);
		Cursor cur = sdb.query(table_name, col, selection, null, null, null,
				null);

		int num = cur.getCount();
		// 用完后关闭数据库表连接

		if (num > 0) {
			cur.moveToFirst();
			item = new Event();
			item.setEventTypeID(cur.getInt(0));
			item.setEventTypeName(cur.getString(1));
			item.setMainTypeID(cur.getInt(2));
			item.setMainTypeName(cur.getString(3));
			item.setSubTypeID(cur.getInt(4));
			item.setSubTypeText(cur.getString(5));
			item.setReportStandard(cur.getString(6));
			item.setCheckStandard(cur.getString(7));
		}
		cur.close();
		sdb.close();

		return item;
	}
	
	/*
	 * 防止未知小类读取时失败,报错
	 */
	public Event getEventForUnknowsubtypeId(int eventTypeID, int mainTypeID, int subTypeID) {
		Event item = null;
		SQLiteDatabase sdb = db.mOpenHelper.getReadableDatabase();
		String col[] = { "eventTypeID", "eventTypeName", "mainTypeID",
				"mainTypeName", "subTypeID", "subTypeText", "reportStandard",
				"checkStandard" };
		String selection = "eventTypeID=" + eventTypeID + " and mainTypeID="
				+ mainTypeID ;
		
		print.out("Event getEventById sql="+selection);
		Cursor cur = sdb.query(table_name, col, selection, null, null, null,
				null);

		int num = cur.getCount();
		// 用完后关闭数据库表连接

		if (num > 0) {
			print.out("num>0");
			cur.moveToFirst();
			item = new Event();
			item.setEventTypeID(cur.getInt(0));
			item.setEventTypeName(cur.getString(1));
			item.setMainTypeID(cur.getInt(2));
			item.setMainTypeName(cur.getString(3));
			item.setSubTypeID(cur.getInt(4));
			item.setSubTypeText(cur.getString(5));
			item.setReportStandard(cur.getString(6));
			item.setCheckStandard(cur.getString(7));
		}
		cur.close();
		sdb.close();
		
		item.setSubTypeID(subTypeID);
		item.setSubTypeText("未知小类");

		return item;
	}
	
	public Event getEventByName(String eventTypeName, String mainTypeName, String subTypeName) {
		Event item = null;
		SQLiteDatabase sdb = db.mOpenHelper.getReadableDatabase();
		String col[] = { "eventTypeID", "eventTypeName", "mainTypeID",
				"mainTypeName", "subTypeID", "subTypeText", "reportStandard",
				"checkStandard" };
		String selection = "eventTypeName='" + eventTypeName + "' and mainTypeName='"
				+ mainTypeName + "' and subTypeText='" + subTypeName +"'";
		print.out("selection="+selection);
		Cursor cur = sdb.query(table_name, col, selection, null, null, null,
				null);

		int num = cur.getCount();
		// 用完后关闭数据库表连接

		if (num > 0) {
			cur.moveToFirst();
			item = new Event();
			item.setEventTypeID(cur.getInt(0));
			item.setEventTypeName(cur.getString(1));
			item.setMainTypeID(cur.getInt(2));
			item.setMainTypeName(cur.getString(3));
			item.setSubTypeID(cur.getInt(4));
			item.setSubTypeText(cur.getString(5));
			item.setReportStandard(cur.getString(6));
			item.setCheckStandard(cur.getString(7));
		}
		cur.close();
		sdb.close();

		return item;
	}

	public ArrayList<Event> load() {
		ArrayList<Event> listEvent = new ArrayList<Event>();
		SQLiteDatabase sdb = db.mOpenHelper.getReadableDatabase();
		String col[] = { "eventTypeID", "eventTypeName", "mainTypeID",
				"mainTypeName", "subTypeID", "subTypeText", "reportStandard",
				"checkStandard" };
		// String selection = "cardID='" + Config.username + "'";
		Cursor cur = sdb.query(table_name, col, null, null, null, null, null);
		int count = cur.getCount();
		print.out("Event count=" + count);
		cur.moveToFirst();
		for (int i = 0; i < count; i++) {
			Event item = new Event();
			item.setEventTypeID(cur.getInt(0));
			item.setEventTypeName(cur.getString(1));
			item.setMainTypeID(cur.getInt(2));
			item.setMainTypeName(cur.getString(3));
			item.setSubTypeID(cur.getInt(4));
			item.setSubTypeText(cur.getString(5));
			item.setReportStandard(cur.getString(6));
			item.setCheckStandard(cur.getString(7));
			cur.moveToNext();
			listEvent.add(item);
		}
		cur.close();
		sdb.close();
		return listEvent;

	}

	public void update(String xml) {
		itemList.clear();
		xml = xml.replace("gb2312", "utf-8");

		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dombuilder = domfac.newDocumentBuilder();
			ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
			Document doc = dombuilder.parse(bais, "utf-8");
			// 获取根
			Element update = doc.getDocumentElement();
			NodeList updateList = update.getChildNodes();

			// 读数据
			if (updateList != null) {
				Node configUpdate = null;
				for (int i = 0; i < updateList.getLength(); i++) {
					Node tempNode = updateList.item(i);
					if (tempNode.getNodeName().equals("configUpdate")) {
						configUpdate = updateList.item(i);
						break;
					}
				}
				NodeList nodeList_configUpdate = configUpdate.getChildNodes();
				Node dataNote = null;
				for (int i = 0; i < nodeList_configUpdate.getLength(); i++) {
					Node tempNode = nodeList_configUpdate.item(i);
					if (tempNode.getNodeName().equals("data")) {
						dataNote = nodeList_configUpdate.item(i);
						break;
					}
				}
				NodeList nodeList_data = dataNote.getChildNodes();
				Node node = null;
				for (int i = 0; i < nodeList_data.getLength(); i++) {
					Node tempNode = nodeList_data.item(i);
					if (tempNode.getNodeName().equals("event")) {
						node = nodeList_data.item(i);
						setEvent(node);
					} else if (tempNode.getNodeName().equals("part")) {
						node = nodeList_data.item(i);
						setPart(node);
					} else if (tempNode.getNodeName().equals("version")) {
						node = nodeList_data.item(i);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 更新库
		if (itemList.size() > 0) {
			clearDataBase();
			save(itemList);
		}
	}
	
	private void setPart(Node root) {
		//部件
		int eventTypeID;
		String eventTypeName;
		int mainTypeID;
		String mainTypeName;

		int subTypeID;
		String subTypeText;
		String reportStandard = "";
		String checkStandard = "";

		eventTypeID = Integer.parseInt(root.getAttributes().getNamedItem("id")
				.getNodeValue());
		eventTypeName = root.getAttributes().getNamedItem("name")
				.getNodeValue();

		NodeList nodeList_mainType = root.getChildNodes();
		for (int i = 0; i < nodeList_mainType.getLength(); i++) {
			Node node_mainType = nodeList_mainType.item(i);
			//如果是节点
			if (node_mainType.getNodeType() == Node.ELEMENT_NODE) {

				mainTypeID = Integer.parseInt(node_mainType.getAttributes()
						.getNamedItem("id").getNodeValue());
				mainTypeName = node_mainType.getAttributes().getNamedItem(
						"name").getNodeValue();

				NodeList nodeList_subtype = node_mainType.getChildNodes();
				//添加不明部件节点
				if(nodeList_subtype.getLength()>0){
					Event event2 = new Event();
					event2.setEventTypeID(eventTypeID);
					//event2.setEventTypeID(eventTypeID);
					event2.setEventTypeName(eventTypeName);
					event2.setMainTypeID(mainTypeID);
					event2.setMainTypeName(mainTypeName);
					event2.setSubTypeID(0);
					event2.setSubTypeText("[无小类]");
					event2.setReportStandard("");
					event2.setCheckStandard("");
					itemList.add(event2);
				}
				//
				for (int j = 0; j < nodeList_subtype.getLength(); j++) {
					Node node_subType = nodeList_subtype.item(j);
					if (node_subType.getNodeType() == Node.ELEMENT_NODE) {

						subTypeID = Integer.parseInt(node_subType
								.getAttributes().getNamedItem("id")
								.getNodeValue());
						subTypeText = node_subType.getChildNodes().item(0)
								.getNodeValue();
						reportStandard = node_subType.getAttributes()
								.getNamedItem("reportStandard").getNodeValue();
						checkStandard = node_subType.getAttributes()
								.getNamedItem("checkStandard").getNodeValue();

						Event event = new Event();
						event.setEventTypeID(eventTypeID);
						event.setEventTypeName(eventTypeName);
						event.setMainTypeID(mainTypeID);
						event.setMainTypeName(mainTypeName);
						event.setSubTypeID(subTypeID);
						event.setSubTypeText(subTypeText);
						event.setReportStandard(reportStandard);
						event.setCheckStandard(checkStandard);

						itemList.add(event);
					}
				}
			}
		}
	}

	private void setEvent(Node root) {
		int eventTypeID;
		String eventTypeName;
		int mainTypeID;
		String mainTypeName;

		int subTypeID;
		String subTypeText;
		String reportStandard = "";
		String checkStandard = "";

		eventTypeID = Integer.parseInt(root.getAttributes().getNamedItem("id")
				.getNodeValue());
		eventTypeName = root.getAttributes().getNamedItem("name")
				.getNodeValue();

		NodeList nodeList_mainType = root.getChildNodes();
		for (int i = 0; i < nodeList_mainType.getLength(); i++) {
			Node node_mainType = nodeList_mainType.item(i);
			if (node_mainType.getNodeType() == Node.ELEMENT_NODE) {

				mainTypeID = Integer.parseInt(node_mainType.getAttributes()
						.getNamedItem("id").getNodeValue());
				mainTypeName = node_mainType.getAttributes().getNamedItem(
						"name").getNodeValue();

				NodeList nodeList_subtype = node_mainType.getChildNodes();
				for (int j = 0; j < nodeList_subtype.getLength(); j++) {
					Node node_subType = nodeList_subtype.item(j);
					if (node_subType.getNodeType() == Node.ELEMENT_NODE) {

						subTypeID = Integer.parseInt(node_subType
								.getAttributes().getNamedItem("id")
								.getNodeValue());
						subTypeText = node_subType.getChildNodes().item(0)
								.getNodeValue();
						reportStandard = node_subType.getAttributes()
								.getNamedItem("reportStandard").getNodeValue();
						checkStandard = node_subType.getAttributes()
								.getNamedItem("checkStandard").getNodeValue();

						Event event = new Event();
						event.setEventTypeID(eventTypeID);
						event.setEventTypeName(eventTypeName);
						event.setMainTypeID(mainTypeID);
						event.setMainTypeName(mainTypeName);
						event.setSubTypeID(subTypeID);
						event.setSubTypeText(subTypeText);
						event.setReportStandard(reportStandard);
						event.setCheckStandard(checkStandard);

						itemList.add(event);
					}
				}
			}
		}
	}

	public ArrayList<Event> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<Event> itemList) {
		this.itemList = itemList;
	}

}
