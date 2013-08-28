package com.Android.player.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class PlaySetting extends Activity {

	private TextView set_textView;
	private RadioButton sigle_Play, order_Play, random_Play;
	private ToggleButton lyLrc;
	private ImageButton set_bt;
	private ImageButton cancel_bt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);

		set_textView = (TextView) findViewById(R.id.setting);
		set_textView.setTextColor(Color.WHITE);
		sigle_Play = (RadioButton) findViewById(R.id.sigle_play);
		order_Play = (RadioButton) findViewById(R.id.order_play);
		random_Play = (RadioButton) findViewById(R.id.random_play);
		lyLrc = (ToggleButton) findViewById(R.id.ly_lrc);
		set_bt = (ImageButton) findViewById(R.id.make);
		cancel_bt = (ImageButton) findViewById(R.id.cancel);

		set_bt.setOnTouchListener(setting_bt_Listener);
		cancel_bt.setOnTouchListener(cancel_bt_Listener);

	}

	OnTouchListener setting_bt_Listener = new OnTouchListener() {// 设置确定按钮监听器
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				v.setBackgroundResource(R.drawable.share_pressed);

				SharedPreferences sp = getSharedPreferences("SET_MSG",
						MODE_WORLD_WRITEABLE);// 文件共享
				SharedPreferences.Editor editor = sp.edit();

				if (sigle_Play.isChecked()) {

					editor.putString("sigle_Play", "is_Sigle");
					editor.putString("order_Play", null);
					editor.putString("random_Play", null);
				}
				if (order_Play.isChecked()) {

					editor.putString("sigle_Play", null);
					editor.putString("order_Play", "is_Order");
					editor.putString("random_Play", null);
				}
				if (random_Play.isChecked()) {
					editor.putString("sigle_Play", null);
					editor.putString("order_Play", null);
					editor.putString("random_Play", "is_Random");
				}
				if (lyLrc.isChecked()) {
					editor.putString("lyLrc", "is_Show");
				}
				if (!lyLrc.isChecked()) {
					editor.putString("lyLrc", null);
				}
				editor.commit();// 提交
			//	Intent intent = new Intent(PlaySetting.this,
			//			MainPlayActivity.class);
			//	startActivity(intent);
				Intent intent = new Intent();
				setResult(4, intent);
		    	finish();

			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				v.setBackgroundResource(R.drawable.share);
			}

			return false;

		}

	};

	OnTouchListener cancel_bt_Listener = new OnTouchListener() {// 取消监听器

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				v.setBackgroundResource(R.drawable.back_pressed);

				//Intent intent = new Intent(PlaySetting.this,
				//		MainPlayActivity.class);
				//startActivity(intent);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				v.setBackgroundResource(R.drawable.back);
				Intent intent = new Intent();
				setResult(3, intent);
		    	finish();
			}

			return false;
		}
	};
	
	
}
