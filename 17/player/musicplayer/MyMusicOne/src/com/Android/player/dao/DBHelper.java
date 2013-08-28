package com.Android.player.dao;



import com.Android.player.common.FileColumn;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Administrator
 *
 */
public class DBHelper extends SQLiteOpenHelper{
    /**
     * ���ݿ����Ƴ���
     */
    private static final String DATABASE_NAME = "MyMusic.db";
    /**
     *  ���ݿ�汾����
     */
    private static final int DATABASE_VERSION = 1;
    /**
     *  �����Ƴ���
     */
    public static final String TABLES_TABLE_NAME = "File_Table";
    private static final String DATABASE_CREATE = "CREATE TABLE " + FileColumn.TABLE +" ("
	+ FileColumn.ID+" integer primary key autoincrement,"
	+ FileColumn.NAME+" text,"
	+ FileColumn.PATH+" text,"
	+ FileColumn.SORT+" integer,"
	+ FileColumn.TYPE+" text)";
	/**
	 *  ���췽��
	 * @param context
	 */
	public DBHelper(Context context) {
		// �������ݿ�
		super(context, DATABASE_NAME,null, DATABASE_VERSION);
	}

	
	/**
	 *  ����ʱ����
	 */
	public void onCreate(SQLiteDatabase db) {
		/*Locale l = new Locale("zh", "CN");
		db.setLocale(l);*/
        db.execSQL(DATABASE_CREATE);
        
      
	}

	/**
	 *  �汾����ʱ����
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// ɾ����
		db.execSQL("DROP TABLE IF EXISTS File_Table");
        onCreate(db);
	}

}
