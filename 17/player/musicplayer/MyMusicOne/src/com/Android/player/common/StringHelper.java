package com.Android.player.common;

import java.util.ArrayList;
import java.util.List;


/**
 * 字符串操作类
 * @author Administrator
 *
 */
public class StringHelper {
	/**
	 * 字符串分段为数组
	 * @param str
	 * @return 字符串分段后数组
	 */
	public static List<String> spiltString(String str) {
		List<String> music_List = new ArrayList<String>();
		
		String[] musics = str.split(";");
		for (int i = 0; i < musics.length; i++) {
			music_List.add(musics[i]);
		}
		return music_List;
	}

	/**
	 * 数组转字符串
	 * @param music_List
	 * @return 字符串
	 */
	public static String toStringAll(List<String> music_List) {
		StringBuffer buffer = new StringBuffer();
		if (music_List.size() > 0) {
			for (int i = 0; i < music_List.size(); i++) {
				buffer.append(music_List.get(i));
				if (i < music_List.size() - 1)
					buffer.append(";");
			}
		}

		return buffer.toString();
	}
}
