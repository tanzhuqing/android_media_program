package com.Android.player.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


import android.content.Context;
import android.content.SharedPreferences;


public class LrcShow {

	@SuppressWarnings("static-access")
	public static void showLrc(String musicName, Context context) {

		BufferedReader file;
		String s = null;
		StringBuffer lrc_time = new StringBuffer();
		StringBuffer lrc_word = new StringBuffer();
		try {

			SharedPreferences sp = context.getSharedPreferences("LRC_SHOW",
					context.MODE_WORLD_WRITEABLE);

			SharedPreferences.Editor editor = sp.edit();
			file = new BufferedReader(new FileReader(new File("/sdcard/"
					+ musicName + ".lrc")));
			while ((s = file.readLine()) != null) {

				if (s.split("]").length == 2) {
					lrc_time.append(s.split("]")[0] + ";");
					lrc_word.append(s.split("]")[1] + ";");

				}

			}
			editor.putString("MUSIC_TITLE", musicName);
			editor.putString("LRC_TIME", lrc_time.toString());
			editor.putString("LRC_WORD", lrc_word.toString());
			editor.commit();

		} catch (IOException e) {

			e.printStackTrace();
			SharedPreferences sp = context.getSharedPreferences("LRC_SHOW",
					context.MODE_WORLD_WRITEABLE);

			SharedPreferences.Editor editor = sp.edit();
			editor.putString("MUSIC_TITLE", musicName);
			editor.putString("LRC_TIME", null);
			editor.putString("LRC_WORD", null);
			editor.commit();

		}

	}
}
