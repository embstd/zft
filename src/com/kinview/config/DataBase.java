package com.kinview.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase {
    
	public DatabaseHelper mOpenHelper;
	
	private static final String DATABASE_NAME = Config.DATABASE_NAME;
	private static final int DATABASE_VERSION = 1;
    private static String table_createSql = "";
    
    public DataBase(Context context, String createSql) {
		table_createSql = createSql;
		mOpenHelper = new DatabaseHelper(context);
	}
    
    public static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(table_createSql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}

	public DatabaseHelper getmOpenHelper() {
		return mOpenHelper;
	}
    
}
