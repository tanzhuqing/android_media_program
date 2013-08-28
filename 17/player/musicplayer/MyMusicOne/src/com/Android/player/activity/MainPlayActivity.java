package com.Android.player.activity;

import java.io.File;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.Android.player.common.LrcShow;
import com.Android.player.common.MusicAdapter;
import com.Android.player.common.MusicHelp;
import com.Android.player.common.StringHelper;
import com.Android.player.dao.DBProvider;
import com.Android.player.dao.SystemService;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import android.widget.Gallery.LayoutParams;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainPlayActivity extends MainPlayActivityRoot{
	
	int i;
	List<String> lrc_time = new ArrayList<String>();
	List<String> lrc_word = new ArrayList<String>();
	Cursor cursor;
	SystemService systemProvider;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		
		systemProvider=new SystemService(this);
		cursor=systemProvider.allSongs();

		SharedPreferences sp = getSharedPreferences("MUSIC",MODE_WORLD_READABLE);

		if (sp != null) {
			playingName = sp.getString("PLAYINGNAME", null);
			selectName = sp.getString("SELECTNAME", null);
			String s = sp.getString("MUSIC_LIST", null);
			if (s != null)
				music_List = StringHelper.spiltString(s);

		}

		init_Play_Rack();// �����ʼ��
		
		if (playingName != null) {//
			int time1 = mplayer.getDuration();
			int time2 = mplayer.getCurrentPosition();
			seekBar.setMax(time1);
			seekBar.setProgress(time2);
			currently_Time.setText(getFileTime(time2));
			end_Time.setText(getFileTime(time1));
			currently_Music.setText(playingName);
			
			
			handler.removeCallbacks(thread_One);
			handler.postDelayed(thread_One, 1000);
			lrc_time = new ArrayList<String>();
			lrc_word = new ArrayList<String>();
			showLrc(playingName);// �����ʾ
		}

		if (selectName != null) {// ����ѡ�еĸ���

			play_bt.setImageBitmap(musicAdapter.getSuspend_Icon());// Ĭ����ͣͼ��
			play_Music();
			lrc_time = new ArrayList<String>();
			lrc_word = new ArrayList<String>();
			showLrc(selectName);// �����ʾ

		}

		if (!(currently_Music.getText().toString()).equals("��")) {
			play_bt.setOnTouchListener(playListener);// ���ż�����
			seekBar.setOnSeekBarChangeListener(seekBarListener);// ���������
			stop_bt.setOnTouchListener(stopListener);// ֹͣ������
			move_Down.setOnTouchListener(downListener);// ��һ�׸���������
			move_Up.setOnTouchListener(upListener);// ��һ�׸���������
		}

		list_bt.setOnTouchListener(list_bt_listener);// �嵥������
		back_bt.setOnTouchListener(return_bt_listener);
		mplayer.setOnCompletionListener(playerListener);// ���������Ƿ񲥷���
		
		
		/**
		mSwitcher = (ImageSwitcher) findViewById(R.id.switcher);
        mSwitcher.setFactory(this);
        mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_in));
        mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
                android.R.anim.fade_out));
        mSwitcher.setImageResource(R.drawable.background);
        * 
		 */
		
        Gallery g = (Gallery) findViewById(R.id.gallery);
        g.setAdapter(new ImageAdapter(this));
        g.setSelection(200);
        g.setOnItemSelectedListener(this);

	}

	@Override
	protected void onPause() {

		super.onPause();
		SharedPreferences sp = getSharedPreferences("MUSIC",
				MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = sp.edit();
		playingName = currently_Music.getText().toString();
		if (!playingName.equals("��"))
			editor.putString("PLAYINGNAME", playingName);
		editor.putString("SELECTNAME", selectName);
		editor.putString("MUSIC_LIST", StringHelper.toStringAll(music_List));
		editor.commit();
		handler.removeCallbacks(thread_One);
	}

	
	@Override
	protected void onResume() {

		super.onResume();
		systemProvider=new SystemService(this);
		cursor=systemProvider.allSongs();

		SharedPreferences sp = getSharedPreferences("MUSIC",MODE_WORLD_READABLE);

		if (sp != null) {
			playingName = sp.getString("PLAYINGNAME", null);
			selectName = sp.getString("SELECTNAME", null);
			String s = sp.getString("MUSIC_LIST", null);
			if (s != null)
				music_List = StringHelper.spiltString(s);

		}
		if (mplayer.isPlaying()) {
			handler.removeCallbacks(thread_One);
			handler.postDelayed(thread_One, 1000);
		}
		else
			handler.removeCallbacks(thread_One);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.i("info", "onDestroy");
		new File("/data/data/com.Rain.music.activity/shared_prefs/MUSIC.xml")
				.delete();
		new File("/data/data/com.Rain.music.activity/shared_prefs/SET_MSG.xml")
				.delete();
		System.exit(0);
	};
	/**
	 *  �����ʼ��
	 */
	private void init_Play_Rack() {
		list_bt = (ImageButton) findViewById(R.id.listplay);
		back_bt = (ImageButton) findViewById(R.id.returnBt);
		stop_bt = (ImageButton) findViewById(R.id.btStop);
		play_bt = (ImageButton) findViewById(R.id.btStart);
		move_Up = (ImageButton) findViewById(R.id.before);
		move_Down = (ImageButton) findViewById(R.id.next);
		end_Time = (TextView) findViewById(R.id.end_Time_Text);
		//title_Music = (TextView) findViewById(R.id.title_music);
		currently_Time = (TextView) findViewById(R.id.current_time_text);
		currently_Music = (TextView) findViewById(R.id.current_music);
		seekBar = (SeekBar) findViewById(R.id.seekbar);

		mplayer = MusicHelp.getMediaPlayer();
		musicAdapter = new MusicAdapter(this, music_List);
		handler = MusicHelp.getHandler();
		currently_Music.setText("��");
		currently_Music.setTextColor(Color.WHITE);
		currently_Time.setTextColor(Color.WHITE);
		end_Time.setTextColor(Color.WHITE);
	//	title_Music.setTextColor(Color.WHITE);
		lrcTime = (TextView) findViewById(R.id.lrcText);

		SharedPreferences sp = getSharedPreferences("SET_MSG",
				MODE_WORLD_READABLE);

		if (sp != null) {
			if (sp.getString("sigle_Play", null) != null) {
				play_Mode = sp.getString("sigle_Play", null);
			}
			if (sp.getString("order_Play", null) != null) {
				play_Mode = sp.getString("order_Play", null);
			}
			if (sp.getString("random_Play", null) != null) {
				play_Mode = sp.getString("random_Play", null);
			}

			if (sp.getString("lyLrc", null) != null) {
				lrc_Show = sp.getString("lyLrc", null);
			}

			Log.i("info", "play_Mode=" + play_Mode);
			Log.i("info", "lrc_Show=" + lrc_Show);

		}

	}

	/**
	 *  ���������������
	 */
	OnCompletionListener playerListener = new OnCompletionListener() {

		@Override
		public void onCompletion(MediaPlayer mp) {

			play_Mode();// ����ģʽ
			lrc_time = new ArrayList<String>();
			lrc_word = new ArrayList<String>();
			showLrc(selectName);
		}
	};

	/**
	 * 
	OnClickListener return_bt_listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			finish();
			onDestroy();

		}
	};
	 */
	/**
	 * ����������
	 */
	OnTouchListener return_bt_listener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				back_bt.setImageResource(R.drawable.whitepress);

			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				back_bt.setImageResource(R.drawable.white);
				finish();
				onDestroy();
			//	android.os.Process.killProcess(android.os.Process.myPid());    //��ȡPID��Ŀǰ��ȡ�Լ���Ҳֻ�и�API�������/proc���Լ���ö���������̰ɣ�����Ҫ˵�����ǣ������������̲�һ����Ȩ�ޣ���Ȼ�������ˡ�
			//	System.exit(0); 
			}

			return false;

		}

	};
	

	/**
	 *  �嵥������
	 */
	OnTouchListener list_bt_listener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
			//	v.setBackgroundResource(R.drawable.share_pressed);
				list_bt.setImageResource(R.drawable.itunespress);
			

			//	startActivity(intent);
			//	Intent intent = new Intent();
			//	intent.setClass(MusicPlayer.this, MusicList.class);
				

			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				//v.setBackgroundResource(R.drawable.share);
				list_bt.setImageResource(R.drawable.itunes2);
				
				Intent intent = new Intent(MainPlayActivity.this,
						PlayListActivity.class);
				startActivityForResult(intent, 0);
			}

			return false;

		}

	};
	/**
	 *  ���������
	 */
	private OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener() {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			if (fromUser) {
				mplayer.seekTo(progress);
				currently_Time.setText(getFileTime(progress));
			}
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}
	};
	/**
	 *  
	// ���ż�����
	private OnClickListener playListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			if (mplayer.isPlaying()) {
				mplayer.pause();// ��ͣ
				currently_Time.setText(currently_Time.getText().toString());
				play_bt.setImageBitmap(musicAdapter.getPlay_Icon());
				handler.removeCallbacks(thread_One);

			}

			else {
				if (is_stopping) {
					play_Music();
					is_stopping = false;
					play_bt.setImageBitmap(musicAdapter.getSuspend_Icon());

				} else {
					mplayer.start();
					handler.postDelayed(thread_One, 1000);
					play_bt.setImageBitmap(musicAdapter.getSuspend_Icon());
				}
			}
		}
	};
	*/
	/**
	 *  ���ż�����
	 */
	OnTouchListener playListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				if (mplayer.isPlaying()) {
					play_bt.setImageResource(R.drawable.pausepress);
				}

				else {
					play_bt.setImageResource(R.drawable.playpress);
				}

			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				if (mplayer.isPlaying()) {
					mplayer.pause();// ��ͣ
					currently_Time.setText(currently_Time.getText().toString());
					play_bt.setImageBitmap(musicAdapter.getPlay_Icon());
					handler.removeCallbacks(thread_One);

				}

				else {
					if (is_stopping) {
						play_Music();
						is_stopping = false;
						play_bt.setImageBitmap(musicAdapter.getSuspend_Icon());

					} else {
						mplayer.start();
						handler.postDelayed(thread_One, 1000);
						play_bt.setImageBitmap(musicAdapter.getSuspend_Icon());
					}
				}
			}

			return false;

		}

	};
	/**
	 * 
	// ֹͣ������
	OnClickListener stopListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			mplayer.stop();
			currently_Time.setText("00:00");
			end_Time.setText("00:00");
			play_bt.setImageBitmap(musicAdapter.getPlay_Icon());
			handler.removeCallbacks(thread_One);
			seekBar.setProgress(1);
			is_stopping = true;
			lrcTime.setText("��");

		}
	};
	*/
	/**
	 * ֹͣ������
	 */
	OnTouchListener stopListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				stop_bt.setImageResource(R.drawable.stoppress);

			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				stop_bt.setImageResource(R.drawable.stop);
				mplayer.stop();
				currently_Time.setText("00:00");
				end_Time.setText("00:00");
				play_bt.setImageBitmap(musicAdapter.getPlay_Icon());
				handler.removeCallbacks(thread_One);
				seekBar.setProgress(1);
				is_stopping = true;
				lrcTime.setText("��");
			}

			return false;

		}

	};
	/**
	 * 
	// ��һ�׸���������
	OnClickListener downListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			move_Down(currently_Music.getText().toString());
			lrc_time = new ArrayList<String>();
			lrc_word = new ArrayList<String>();
			showLrc(currently_Music.getText().toString());// �����ʾ

		}
	};
	 */
	/**
	 * ��һ�׸���������
	 */
	OnTouchListener downListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				move_Down.setImageResource(R.drawable.forwardpress);

			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				move_Down.setImageResource(R.drawable.forward);
				move_Down(currently_Music.getText().toString());
				lrc_time = new ArrayList<String>();
				lrc_word = new ArrayList<String>();
				showLrc(currently_Music.getText().toString());// �����ʾ
			}

			return false;

		}

	};
	/**
	 * 
	
	// ��һ�׸���������
	OnClickListener upListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			move_Up(currently_Music.getText().toString());
			lrc_time = new ArrayList<String>();
			lrc_word = new ArrayList<String>();
			showLrc(currently_Music.getText().toString());// �����ʾ

		}
	};
	 */
	/**
	 *  ��һ�׸���������
	 */
	OnTouchListener upListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				move_Up.setImageResource(R.drawable.backwardpress);

			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				move_Up.setImageResource(R.drawable.backward);
				move_Up(currently_Music.getText().toString());
				lrc_time = new ArrayList<String>();
				lrc_word = new ArrayList<String>();
				showLrc(currently_Music.getText().toString());// �����ʾ
			}

			return false;

		}

	};
	
	/**
	 *  ��һ�׸���
	 * @param musicName
	 */
	private void move_Down(String musicName) {
		for (int i = 0; i < music_List.size(); i++) {

			if (musicName.equals(music_List.get(i))) {
				if ((i + 1) < music_List.size()) {
					selectName = music_List.get(i + 1);

					play_Music();
					return;
				} else {
					selectName = music_List.get(0);
					play_Music();
					return;
				}
			}

		}

	}

	/**
	 *  ��һ�׸���
	 * @param musicName
	 */
	private void move_Up(String musicName) {
		for (int i = 0; i < music_List.size(); i++) {

			if (musicName.equals(music_List.get(i))) {
				if ((i - 1) >= 0) {
					selectName = music_List.get(i - 1);// �ƶ�����һ�׸���
					play_Music();
					return;
				} else {
					selectName = music_List.get(music_List.size() - 1);
					play_Music();
					return;
				}
			}

		}

	}

	/**
	 *  ����ģʽ
	 */
	private void play_Mode() {
		if ("is_Sigle".equals(play_Mode)) {// ����ѭ��
			play_Music();
		}
		if ("is_Order".equals(play_Mode)) {// ˳�򲥷�
			move_Down(currently_Music.getText().toString());
		}
		if ("is_Random".equals(play_Mode)) {// �������
			Random r = new Random();
			int idx = r.nextInt(music_List.size());// ������� [0,music_List.size())
			// ��INTֵ
			selectName = music_List.get(idx);

			play_Music();
		}
	}

	/**
	 *  ���Ÿ���
	 */
	private void play_Music() {
		try {

			mplayer.reset();
			mplayer.setDataSource(query());// �ļ�����ѡ�����
			mplayer.prepare();
			mplayer.start();
			currently_Music.setText(selectName);
			/*	
			if(cursor.moveToFirst()){
				String title=systemProvider.getArtist();
				currently_Music.setText(title);
			}*/
			
			seekBar.setMax(mplayer.getDuration());// ��Ƶ�ļ�����ʱ��
			seekBar.setProgress(1);
			currently_Time.setText(getFileTime(mplayer.getCurrentPosition()));
		//	lrcTime.setText(systemProvider.getArtist());
			
			handler.removeCallbacks(thread_One);
			end_Time.setText(getFileTime(mplayer.getDuration()));
			handler.postDelayed(thread_One, 1000);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ��ѯ����·��
	 * @return ����·��
	 */
	public String query() {
		ContentResolver cr = getContentResolver();
		Uri uri = DBProvider.CONTENT_URI;
		String[] projection = { "path" };
		String selection = "fileName=?";
		String[] selectionArgs = { selectName };
		Cursor c = cr.query(uri, projection, selection, selectionArgs, null);
		if (c.moveToFirst()) {
			String path = c.getString(0);

			return path;
		}
		return null;

	}
	/**
	 * ��ȡ�����ļ��Ĳ��ų���ʱ�䳤�ĸ�ʽ���ַ���
	 * @param timeMs
	 * @return ��ʽ��ʱ���ַ���
	 */
	private String getFileTime(int timeMs) {
		int totalSeconds = timeMs / 1000;// ��ȡ�ļ��ж�����
		StringBuilder mFormatBuilder = new StringBuilder();
		Formatter mFormatter = new Formatter(mFormatBuilder, Locale
				.getDefault());
		int seconds = totalSeconds % 60;
		int minutes = (totalSeconds / 60) % 60;
		int hours = totalSeconds / 3600;
		mFormatBuilder.setLength(0);

		if (hours > 0) {
			return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds)
					.toString();// ��ʽ���ַ���
		} else {
			return mFormatter.format("%02d:%02d", minutes, seconds).toString();
		}
	}


	/**
	 * ��ʾ���
	 * @param musicName
	 */
	private void showLrc(String musicName) {
		if ("is_Show".equals(lrc_Show)) {
			String strTime;
			String strWord;
			String musicTitle;

			if (mplayer.isPlaying())
				LrcShow.showLrc(musicName, MainPlayActivity.this);
			SharedPreferences lrc_sp = getSharedPreferences("LRC_SHOW",
					MODE_WORLD_READABLE);
			if (lrc_sp != null) {
				strTime = lrc_sp.getString("LRC_TIME", null);
				strWord = lrc_sp.getString("LRC_WORD", null);
				musicTitle = lrc_sp.getString("MUSIC_TITLE", null);
				if ((strTime != null) && (strWord != null)
						&& musicTitle.equals(musicName)) {
					Log.i("info", musicTitle + "......." + musicName);
					lrc_time = StringHelper.spiltString(strTime);
					lrc_word = StringHelper.spiltString(strWord);
				}

			}
		}
	}
	/**
	 * ��ʾ��ǰ�������ŵ��ĸ��
	 * @param time ʱ�����鼯
	 * @param word �������鼯
	 */
	private void showLrcTwo(List<String> time, List<String> word) {
		if (time.size() > 0 && word.size() > 0)
			for (i = 0; i < time.size(); i++) {
				if (time.get(i).indexOf(currently_Time.getText().toString()) != -1) {
					lrcTime.setText(word.get(i));
				}

			}
		else
			lrcTime.setText("���޸��");
	}
	/**
	 * �߳�
	 */
	private Runnable thread_One = new Runnable() {
		public void run() {

			int currently_Progress = seekBar.getProgress() + 1000;// ��1��
			seekBar.setProgress(currently_Progress);
			currently_Time.setText(getFileTime(mplayer.getCurrentPosition()));// ÿ1000mˢ�¸�������
			showLrcTwo(lrc_time, lrc_word);
			handler.postDelayed(thread_One, 1000);
			Log.i("time", currently_Time.getText().toString());

		}
	};
	
	
	
	/**
	 * ͼƬ��ʾ
	 * @author Administrator
	 *
	 */
	public class ImageAdapter extends BaseAdapter {
        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView i = new ImageView(mContext);
            if(position<0){

                position =position+getCount();

              }

              i.setImageResource(mThumbIds[position% getCount()]);

            i.setAdjustViewBounds(true);
            i.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
       //     i.setBackgroundResource(R.drawable.background);
            return i;
        }

        private Context mContext;

    }
 
    private Integer[] mThumbIds = {
            R.drawable.speaker1, R.drawable.speaker2, R.drawable.speaker3,
            R.drawable.speaker4, R.drawable.speaker5, R.drawable.speaker6,
            R.drawable.speaker7, R.drawable.speaker8};
    @Override
    public void onItemSelected(AdapterView parent, View v, int position, long id) {
        // mSwitcher.setImageResource(mImageIds[position]);
     	//mSwitcher.setImageResource(R.drawable.backgroud);
      
     }
    @Override
     public void onNothingSelected(AdapterView parent) {
     }
    @Override
     public View makeView() {
         ImageView i = new ImageView(this);
         i.setBackgroundColor(0xFF000000);
         i.setScaleType(ImageView.ScaleType.FIT_CENTER);
         i.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT,
                 LayoutParams.FILL_PARENT));
         return i;
     }
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		try
		{
			super.onActivityResult(requestCode, resultCode, data);
			if(requestCode == 0)
			{
				if(resultCode == 0)
				{
					Bundle bundle = data.getExtras();
					if(bundle != null)
					{
						selectName=bundle.getString("selectName");
						if(selectName!=null){
							play_bt.setImageBitmap(musicAdapter.getSuspend_Icon());// Ĭ����ͣͼ��
							play_Music();
							lrc_time = new ArrayList<String>();
							lrc_word = new ArrayList<String>();
							showLrc(selectName);// �����ʾ
				    	}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}