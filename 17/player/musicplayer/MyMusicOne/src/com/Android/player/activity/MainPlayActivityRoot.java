package com.Android.player.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.Android.player.common.MusicAdapter;

public class MainPlayActivityRoot  extends Activity implements
AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory{
	/**
	 * �嵥��ť
	 */
	protected ImageButton list_bt;
	/**
	 * ���ذ�ť
	 */
	protected ImageButton back_bt;
	/**
	 * ֹͣ��ť
	 */
	protected ImageButton stop_bt;
	/**
	 * ���Ű�ť
	 */
	protected ImageButton play_bt;
	/**
	 * ��һ�׸���
	 */
	protected ImageButton move_Up;
	/**
	 * ��һ�׸���
	 */
	protected ImageButton move_Down;
	/**
	 * ��������ʱ��
	 */
	protected TextView end_Time;
	/**
	 * ��ǰ����ʱ��
	 */
	protected TextView currently_Time;
	/**
	 * ��ǰ����ʱ��
	 */
	protected TextView currently_Music;
	/**
	 * ����
	 */
	protected SeekBar seekBar;
	/**
	 * �����ʾ
	 */
	protected TextView lrcTime;
	/**
	 * �������Ƿ�ֹͣ
	 */
	protected boolean is_stopping = false;
	/**
	 * ϵͳ�Դ��������ؼ�
	 */
	protected MediaPlayer mplayer;
	/**
	 * ѡ�еĸ���
	 */
	protected String selectName;
	/**
	 * ���ڲ��ŵĸ���
	 */
	protected String playingName;
	/**
	 *  ����ģʽ ��Ĭ��Ϊ�������ģʽ
	 */
	protected String play_Mode = "is_Random";
	/**
	 * �����ʾģʽ
	 */
	protected String lrc_Show;
	/**
	 * �����б�
	 */
	protected List<String> music_List = new ArrayList<String>();
	/**
	 * �߳�
	 */
	protected Handler handler;
	/**
	 * �������ֲ���
	 */
	protected MusicAdapter musicAdapter;
	public ImageButton getList_bt() {
		return list_bt;
	}
	public void setList_bt(ImageButton list_bt) {
		this.list_bt = list_bt;
	}
	public ImageButton getBack_bt() {
		return back_bt;
	}
	public void setBack_bt(ImageButton back_bt) {
		this.back_bt = back_bt;
	}
	public ImageButton getStop_bt() {
		return stop_bt;
	}
	public void setStop_bt(ImageButton stop_bt) {
		this.stop_bt = stop_bt;
	}
	public ImageButton getPlay_bt() {
		return play_bt;
	}
	public void setPlay_bt(ImageButton play_bt) {
		this.play_bt = play_bt;
	}
	public ImageButton getMove_Up() {
		return move_Up;
	}
	public void setMove_Up(ImageButton move_Up) {
		this.move_Up = move_Up;
	}
	public ImageButton getMove_Down() {
		return move_Down;
	}
	public void setMove_Down(ImageButton move_Down) {
		this.move_Down = move_Down;
	}
	public TextView getEnd_Time() {
		return end_Time;
	}
	public void setEnd_Time(TextView end_Time) {
		this.end_Time = end_Time;
	}
	public TextView getCurrently_Time() {
		return currently_Time;
	}
	public void setCurrently_Time(TextView currently_Time) {
		this.currently_Time = currently_Time;
	}
	public TextView getCurrently_Music() {
		return currently_Music;
	}
	public void setCurrently_Music(TextView currently_Music) {
		this.currently_Music = currently_Music;
	}
	public SeekBar getSeekBar() {
		return seekBar;
	}
	public void setSeekBar(SeekBar seekBar) {
		this.seekBar = seekBar;
	}
	public TextView getLrcTime() {
		return lrcTime;
	}
	public void setLrcTime(TextView lrcTime) {
		this.lrcTime = lrcTime;
	}
	public boolean isIs_stopping() {
		return is_stopping;
	}
	public void setIs_stopping(boolean is_stopping) {
		this.is_stopping = is_stopping;
	}
	public MediaPlayer getMplayer() {
		return mplayer;
	}
	public void setMplayer(MediaPlayer mplayer) {
		this.mplayer = mplayer;
	}
	public String getSelectName() {
		return selectName;
	}
	public void setSelectName(String selectName) {
		this.selectName = selectName;
	}
	public String getPlayingName() {
		return playingName;
	}
	public void setPlayingName(String playingName) {
		this.playingName = playingName;
	}
	public String getPlay_Mode() {
		return play_Mode;
	}
	public void setPlay_Mode(String play_Mode) {
		this.play_Mode = play_Mode;
	}
	public String getLrc_Show() {
		return lrc_Show;
	}
	public void setLrc_Show(String lrc_Show) {
		this.lrc_Show = lrc_Show;
	}
	public List<String> getMusic_List() {
		return music_List;
	}
	public void setMusic_List(List<String> music_List) {
		this.music_List = music_List;
	}
	public Handler getHandler() {
		return handler;
	}
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	public MusicAdapter getMusicAdapter() {
		return musicAdapter;
	}
	public void setMusicAdapter(MusicAdapter musicAdapter) {
		this.musicAdapter = musicAdapter;
	}
	@Override
	public View makeView() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
