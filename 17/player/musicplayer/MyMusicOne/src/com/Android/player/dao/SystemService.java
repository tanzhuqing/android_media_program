package com.Android.player.dao;



import org.apache.http.util.EncodingUtils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.RemoteException;
import android.provider.MediaStore;

public class SystemService {

	private Context context;
	private Cursor cursor;

	public SystemService(Context context) {
		this.context = context;
	}
	
	public Cursor allSongs() {
		if (cursor != null)
			return cursor;
		ContentResolver resolver = context.getContentResolver();
		cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
		return cursor;

	}
	
	/**
	 * ��ȡ���ڲ��Ÿ�����������
	 * @return
	 */
	public String getArtist() {

		return cursor.getString(cursor
				.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
	}
	
	/**
	 * ��ȡ���ڲ��Ÿ�������
	 * @return ��������
	 */
	public String getTitle() {
		String title = cursor.getString(cursor
				.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
		try {
			title=EncodingUtils.getString(title.getBytes(), "UTF-8");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return title;
	}
	
	/**
	 *  ��ȡ���ڲ��Ÿ�����ר��
	 * @return ר����
	 * @throws RemoteException
	 */
	public String getAlbum() throws RemoteException {
		return cursor.getString(cursor
				.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
	}
	
	/*public int getDuration() throws RemoteException {
		// ��õ�ǰ������ʱ��
		return player.getDuration();
	}public int getTime() throws RemoteException {
		// ��õ�ǰ��ý��ʱ��
		return player.getCurrentPosition();
	}*/

}
