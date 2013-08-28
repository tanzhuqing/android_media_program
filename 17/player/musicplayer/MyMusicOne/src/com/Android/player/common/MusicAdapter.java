package com.Android.player.common;

import java.io.File;
import java.util.List;

import com.Android.player.activity.R;
import com.Android.player.dao.DBProvider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MusicAdapter extends BaseAdapter {

	private Bitmap down_Icon;
	private Bitmap up_Icon;
	private Bitmap suspend_Icon;
	private Bitmap play_Icon;
	private Bitmap music_File;
	private Bitmap father_File;// 父目录
	private Bitmap folder_File;// 目录
	private List<String> list;
	Context context;
	LayoutInflater inflater;


	public Bitmap getDown_Icon() {
		return down_Icon;
	}

	public void setDown_Icon(Bitmap downIcon) {
		down_Icon = downIcon;
	}

	public Bitmap getUp_Icon() {
		return up_Icon;
	}

	public void setUp_Icon(Bitmap upIcon) {
		up_Icon = upIcon;
	}

	public Bitmap getSuspend_Icon() {
		return suspend_Icon;
	}

	public void setSuspend_Icon(Bitmap suspendIcon) {
		suspend_Icon = suspendIcon;
	}

	public Bitmap getPlay_Icon() {
		return play_Icon;
	}

	public void setPlay_Icon(Bitmap playIcon) {
		play_Icon = playIcon;
	}

	public MusicAdapter(Context context, List<String> list) {
		this.list = list;
		this.context = context;
		inflater = LayoutInflater.from(context);
		//位图工厂
		play_Icon = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.play);
		up_Icon = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.backward);
		suspend_Icon = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.pause);
		down_Icon = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.forward);
		music_File = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.itunes2);
		father_File = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.father_file);
		folder_File = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.folder_file);
		/*docm_File = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.doc_file);*/
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.file_row, null);
			holder.text = (TextView) convertView.findViewById(R.id.text);
			holder.icon = (ImageView) convertView.findViewById(R.id.image_Icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		//holder.text.setTextColor(Color.WHITE);
		//holder.text.setTextSize(22);

		if (list.get(position).toString().equals("[...]")) {// 如果是父目录
			holder.text.setText("[Back]");
			holder.icon.setImageBitmap(father_File);
		}
		if (list.get(position).toString().equals("/sdcard")
				|| list.get(position).toString().equals("/system")) {
			holder.text.setText(list.get(position));
			holder.icon.setImageBitmap(folder_File);
		}

		else {
			String path = null;
			if (!"[...]".equals(list.get(position).toString())) {
				path = query(list.get(position).toString());
			}
			

			if (path != null) {
				File f = new File(path);
				holder.text.setText(list.get(position));
				if (f.isDirectory()) {
					holder.icon.setImageBitmap(folder_File);
				} else {

					if (path.indexOf(".mp3") != -1) {
						holder.text.setText(list.get(position));
						holder.icon.setImageBitmap(music_File);
					}

				}

			} else {

				if ((list.get(position).indexOf(".mp3") != -1)) {
					holder.text.setText(list.get(position));
					holder.icon.setImageBitmap(music_File);
				} else {

					holder.text.setText(list.get(position));
					holder.icon.setImageBitmap(folder_File);
					if (list.get(position).equals("[...]")) {
						holder.icon.setImageBitmap(father_File);
					}
				}

			}
		}

		return convertView;
	}

	public String query(String fileName) {// 查询歌曲路径
		ContentResolver cr = context.getContentResolver();
		Uri uri = DBProvider.CONTENT_URI;
		String[] projection = { "path" };
		String selection = "fileName=?";
		String[] selectionArgs = { fileName };
		Cursor c = cr.query(uri, projection, selection, selectionArgs, null);
		if (c.moveToFirst()) {

			String path = c.getString(0);

			return path;
		}
		return null;

	}

	private class ViewHolder {

		private TextView text;
		private ImageView icon;
	}
}
