package com.Android.player.activity;

import java.util.ArrayList;
import java.util.List;

import com.Android.player.common.MusicAdapter;
import com.Android.player.common.StringHelper;
import com.Android.player.dao.DBProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;

import android.database.Cursor;

import android.graphics.Color;

import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ImageButton;

import android.widget.TextView;

import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class PlayListActivity extends Activity {
	private ListView playlist;
	private ContentResolver cr;
	private List<String> list = new ArrayList<String>();// 存放歌曲的List
	private Uri uri;
	private TextView music_List;
	private String selectName;
	private ImageButton back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.play_list);
		Log.v("this in onCreate", ".................");
		playlist = (ListView) findViewById(R.id.show_play_list);
		music_List = (TextView) findViewById(R.id.music_list);
		music_List.setTextColor(Color.WHITE);
		back=(ImageButton)findViewById(R.id.back);
		query();
		Log.v("this in onCreate after", ".................");
		setListener();
	}
	
	private void setListener(){
		playlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(PlayListActivity.this, Menu.class);
				selectName = playlist.getItemAtPosition(arg2).toString();
			//	startActivity(intent);
				startActivityForResult(intent, 2);
			}

			
		});
		back.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					v.setBackgroundResource(R.drawable.back_pressed);

				
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					v.setBackgroundResource(R.drawable.back);
					Intent intent = new Intent(); 
					intent.setClass(PlayListActivity.this, MainPlayActivity.class);
				//	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //注意本行的FLAG设置
				//	startActivity(intent);
			    	setResult(0, intent);
			    	finish();
				}
				
				return false;
			}
		});
	}
	
	@Override
	protected void onPause() {
		
		super.onPause();
		Log.v("log", "playListActivity is in pause state");
		SharedPreferences sp=getSharedPreferences("MUSIC", MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor=sp.edit();
		
		
		editor.putString("SELECTNAME", selectName);
		String str=StringHelper.toStringAll(list);
		editor.putString("MUSIC_LIST", str);
		
		editor.commit();
	//	finish();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		try
		{
			super.onActivityResult(requestCode, resultCode, data);
			if(requestCode == 2)
			{
				if(resultCode == 2)
				{
					Bundle bundle = data.getExtras();
					if(bundle != null)
					{
						int operate=bundle.getInt("operate");
						if(operate==0){
							Intent intent = new Intent();
							bundle.putString("selectName", selectName);
					    	intent.putExtras(bundle);
					    	setResult(0, intent);
					    	finish();
				    	}
					}
				}else if(resultCode == 5){
					Intent intent = new Intent();
					setResult(0, intent);
			    	finish();
				}else if(resultCode == 6){
					query();
					setListener();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public String[] query() {// 查询数据库

		cr = getContentResolver();
		uri = DBProvider.CONTENT_URI;
		list.clear();
		String[] projection = { "filename", "path" };
		Cursor c = cr.query(uri, projection, null, null, null);
		if (c.getCount() == 0) {
			showDialog("播放列表为空");
		}
		String[] music = new String[c.getCount()];
		if (c.moveToFirst()) {
			for (int i = 0; i < c.getCount(); i++) {
				c.moveToPosition(i);
				String filename = c.getString(0);

				music[i] = filename;
				list.add(filename);

			}
		}
		if (music.length > 0) {
			playlist.setAdapter(new MusicAdapter(this, list));

		}
		return music;

	}

	private void showDialog(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(msg).setCancelable(false).setPositiveButton("从SD卡",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						Intent intent = new Intent(PlayListActivity.this,
								FileExplorerActivity.class);
					//	startActivity(intent);
						startActivityForResult(intent, 2);
					}
				}).setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			//	Intent intent = new Intent(PlayListActivity.this,
			//			MainPlayActivity.class);
			//	startActivity(intent);
				Intent intent = new Intent();
		    	setResult(0, intent);
		    	finish();

			}
		});
		AlertDialog alert = builder.create();
		alert.show();

	}


}
