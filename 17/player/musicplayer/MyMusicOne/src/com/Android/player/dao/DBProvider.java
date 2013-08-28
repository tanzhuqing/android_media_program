package com.Android.player.dao;

import com.Android.player.common.FileColumn;

import android.content.ContentProvider;
import android.content.ContentValues;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.net.Uri;
import android.util.Log;

public class DBProvider extends ContentProvider {
	private DBHelper dbOpenHelper;
	public static final String AUTHORITY = "MUSIC";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
			+ "/" + FileColumn.TABLE);

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		
		try {
			db.delete(FileColumn.TABLE, arg1, arg2);
			Log.i("info", "delete");
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e("error", "delete");
		}
		return 1;
	}
	/**
	 * ��ʵ��
	 */
	@Override
	public String getType(Uri uri) {
		return null;
	}
	/**
	 * ����
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		long count = 0;
		try {
			count = db.insert(FileColumn.TABLE, null, values);
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e("error", "insert");
		}
		if (count > 0)
			return uri;
		else
			return null;

	}

	@Override
	public boolean onCreate() {
		dbOpenHelper = new DBHelper(getContext());

		return true;
	}
	/**
	 * ����������ѯ
	 * @return ���ݼ�
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		// ���β���Ϊ����������ѯ�ֶΣ�where���,ռλ���滻��group by(����)��having(��������),order by(����)
		Cursor cur = db.query(FileColumn.TABLE, projection, selection,
				selectionArgs, null, null, sortOrder);
		return cur;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		int i = 0;
		try {
			i = db.update(FileColumn.TABLE, values, selection, null);
			return i;
		} catch (Exception ex) {
			Log.e("error", "update");
		}
		return 0;
	}

}
