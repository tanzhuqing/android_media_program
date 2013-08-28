package com.Android.player.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.Android.player.common.FileColumn;
import com.Android.player.common.MusicAdapter;
import com.Android.player.dao.DBProvider;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FileExplorerActivity extends ListActivity {

	private List<String> items = null;
	private TextView store_Card;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.directory_list);
		store_Card = (TextView) findViewById(R.id.store_card);
		store_Card.setTextColor(Color.WHITE);
		fill(new File("/").listFiles());

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		int selectionRowID = (int) position;
		File file = new File(items.get(selectionRowID));
		if (selectionRowID == 0) {
			fillWithRoot();

		} else {

			if (file.isDirectory()) {
				fill(file.listFiles());
			} else {

				Intent intent = this.getIntent();
				intent.putExtra("filePath", file);
				FileExplorerActivity.this.setResult(0, intent);
				showDialog("加入播放列表?", file);
			}
		}
	}

	private void fillWithRoot() {
		fill(new File("/").listFiles());

	}

	private void fill(File[] files) {
		items = new ArrayList<String>();
		items.add(getString(R.string.to_top));
		for (File file : files) {
			if (file.isDirectory()) {
				if ((file.getPath().indexOf("/sdcard")) != -1
						|| (file.getPath().indexOf("/system")) != -1)
					items.add(file.getPath());
			}
			if ((file.getPath().indexOf(".mp3")) != -1) {
				items.add(file.getPath());
			}
		}
		setListAdapter(new MusicAdapter(this, items));

	}

	private void showDialog(String msg, final File file) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg).setCancelable(false).setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						String fileName = file.getName().substring(0,
								file.getName().indexOf("."));
						Log.i("info", fileName);
						if (query(fileName)) {
							insertMusic(file);// 添加音乐
						}

					}
				}).setNegativeButton("取消", null);
		AlertDialog alert = builder.create();
		alert.show();

	}

	// 添加音乐到播放列表
	private final void insertMusic(File file) {

		ContentResolver cr = getContentResolver();
		ContentValues values = new ContentValues();
		Uri uri = DBProvider.CONTENT_URI;
		String fileName = file.getName().substring(0,
				file.getName().indexOf("."));
		values.put(FileColumn.NAME, fileName);
		values.put(FileColumn.PATH, file.getAbsolutePath());
		values.put(FileColumn.TYPE, "Music");
		values.put(FileColumn.SORT, "popular");
		cr.insert(uri, values);
		Toast.makeText(FileExplorerActivity.this, "已加入", Toast.LENGTH_LONG)
				.show();
	//	Intent intent = new Intent(FileExplorerActivity.this,
	//			PlayListActivity.class);
	//	startActivity(intent);
		Intent intent = new Intent();
    	setResult(6, intent);
    	finish();

	}

	private boolean query(String name) {

		ContentResolver cr = getContentResolver();
		Uri uri = DBProvider.CONTENT_URI;
		String[] projection = { "filename" };
		Cursor c = cr.query(uri, projection, null, null, null);

		if (c.moveToFirst()) {
			for (int i = 0; i < c.getCount(); i++) {
				c.moveToPosition(i);
				String filename_DB = c.getString(0);
				if (name.equals(filename_DB)) {// 判断播放列表中是否存在该歌曲
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					builder.setMessage("文件已存在").setCancelable(false)
							.setPositiveButton("返回列表",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											Intent intent = new Intent(
													FileExplorerActivity.this,
													PlayListActivity.class);
											startActivity(intent);
										}
									}).setNegativeButton("重新添加", null);
					AlertDialog alert = builder.create();
					alert.show();
					return false;
				}

			}
		}
		return true;
	}
	
}