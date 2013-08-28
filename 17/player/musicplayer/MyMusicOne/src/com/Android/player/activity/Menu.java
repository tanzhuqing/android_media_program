package com.Android.player.activity;

import java.util.ArrayList;
import java.util.List;

import com.Android.player.common.MusicAdapter;
import com.Android.player.dao.DBProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;


import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;

import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.AdapterView.OnItemClickListener;

public class Menu extends Activity {
	private ListView menuLV;
	private TextView select_Item;
	private String selectName;// 选中的名字
	private ImageButton back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		menuLV = (ListView) findViewById(R.id.menu);
		back=(ImageButton)findViewById(R.id.back);
		select_Item = (TextView) findViewById(R.id.select_item);
		select_Item.setTextColor(Color.WHITE);
		SharedPreferences sp = getSharedPreferences("MUSIC",
				MODE_WORLD_READABLE);
		if (sp != null) {
			
			selectName=sp.getString("SELECTNAME", null);
			
			
		}
		
		List<String> seclect_items = new ArrayList<String>();
		seclect_items.add("播放");
		seclect_items.add("详细");
		seclect_items.add("新增");
		seclect_items.add("移除");
		seclect_items.add("全部移除");
		seclect_items.add("设置");
		menuLV.setAdapter(new MusicAdapter(this, seclect_items));
		
		back.setOnTouchListener(backListener)
		;
		
		menuLV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				SharedPreferences sp=getSharedPreferences("MUSIC", MODE_WORLD_WRITEABLE);
				SharedPreferences.Editor editor=sp.edit();
				switch (arg2) {
				case 0:// 播放
					//Intent intent_play = new Intent(Menu.this,
					//		MainPlayActivity.class);
					//startActivity(intent_play);
					Bundle bundle = new Bundle();
			    	bundle.putInt("operate", 0);
			    	Intent intent = new Intent();
			    	intent.putExtras(bundle);
			    	setResult(2, intent);
			    	finish();
					break;
				case 2:
					Intent intent_add = new Intent(Menu.this,
							FileExplorerActivity.class);
					editor.putString("SELECTNAME", null);
					editor.commit();
				//	startActivity(intent_add);
					startActivityForResult(intent_add, 3);
					break;
				case 3:// 移除

					showDialog(selectName);
					break;
				case 4:// 全部移除
					showDialog("");
					break;
				case 5:// 设置

					Intent intent_set = new Intent(Menu.this, PlaySetting.class);
					editor.putString("SELECTNAME", null);
					editor.commit();
					
				//	startActivity(intent_set);
					startActivityForResult(intent_set, 3);
					break;
				default:
					break;
				}

			}
		});
	}

	
	OnTouchListener backListener=new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				v.setBackgroundResource(R.drawable.back_pressed);

			
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				v.setBackgroundResource(R.drawable.back);
				Intent intent = new Intent();
		    	setResult(6, intent);
		    	finish();
			}
			
			return false;
		}
	};
	private void showDialog(final String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if (msg.equals(""))
			builder.setMessage("全部移除?");
		else
			builder.setMessage("是否移除?");
		builder.setCancelable(false).setPositiveButton("是",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						if (msg.equals(""))
							del_All();
						else
							del_One(selectName);
						//Intent intent = new Intent(Menu.this,
						//		PlayListActivity.class);
						//startActivity(intent);
						Intent intent = new Intent();
				    	setResult(6, intent);
				    	finish();

					}
				}).setNegativeButton("否", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		AlertDialog alert = builder.create();
		alert.show();

	}
	/**
	 */
	private void del_One(String musicName) {// 删除单首歌曲

		ContentResolver cr = getContentResolver();
		Uri uri = DBProvider.CONTENT_URI;
		String where = "fileName=?";

		String[] selectionArgs = { musicName };
		cr.delete(uri, where, selectionArgs);
	}

	private void del_All() {// 删除全部歌曲

		ContentResolver cr = getContentResolver();
		Uri uri = DBProvider.CONTENT_URI;

		cr.delete(uri, null, null);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		try
		{
			super.onActivityResult(requestCode, resultCode, data);
			if(requestCode == 3)
			{
				if(resultCode == 3)
				{
					Intent intent = new Intent();
			    	setResult(5, intent);
			    	finish();
				}else if(resultCode == 6){
					Intent intent = new Intent();
			    	setResult(6, intent);
			    	finish();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
