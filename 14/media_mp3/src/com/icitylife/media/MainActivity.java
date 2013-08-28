package com.icitylife.media;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity{
    private Button playButton;
    private Button stopButton;
    private MediaPlayer mediaPlayer;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        playButton=(Button)findViewById(R.id.playButton);
        stopButton=(Button)findViewById(R.id.stopButton);
        
        //播放MP3 
        playButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(playButton.getText().toString().equals("播放")){
					boolean createState=false;
					if(mediaPlayer==null){
						mediaPlayer=createLocalMp3();
						createState=true;
					}
					//当播放完音频资源时，会触发onCompletion事件，可以在该事件中释放音频资源，
					//以便其他应用程序可以使用该资源:
					mediaPlayer.setOnCompletionListener(new OnCompletionListener(){
						@Override
						public void onCompletion(MediaPlayer mp) {
							mp.release();//释放音频资源
							stopButton.setEnabled(false);
							setTitle("资源已经被释放了");
						}
					});
					try {
						//在播放音频资源之前，必须调用Prepare方法完成些准备工作
						if(createState) mediaPlayer.prepare();
						//开始播放音频
						mediaPlayer.start();
						playButton.setText("暂停");
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else if(playButton.getText().toString().equals("暂停")){
					if(mediaPlayer!=null){
						mediaPlayer.pause();//暂停
						playButton.setText("播放");
					}
				}
				stopButton.setEnabled(true);
			}
        });
        
        //停止
        stopButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(mediaPlayer!=null){
					mediaPlayer.stop();//停止播放
					mediaPlayer.release();//释放资源
					mediaPlayer=null;
					playButton.setText("播放");
					stopButton.setEnabled(false);
				}
			}
        });
        
    }
    /**
     * 创建网络mp3
     * @return
     */
    public MediaPlayer createNetMp3(){
    	String url="http://192.168.1.100:8080/media/beatit.mp3";
		MediaPlayer mp=new MediaPlayer();
		try {
			mp.setDataSource(url);
		} catch (IllegalArgumentException e) {
			return null;
		} catch (IllegalStateException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return mp;
    }
    /**
     * 创建本地MP3
     * @return
     */
    public MediaPlayer createLocalMp3(){
    	/**
		 * 创建音频文件的方法：
		 * 1、播放资源目录的文件：MediaPlayer.create(MainActivity.this,R.raw.beatit);//播放res\raw 资源目录下的MP3文件
		 * 2:播放sdcard卡的文件：mediaPlayer=new MediaPlayer();
		 *   mediaPlayer.setDataSource("/sdcard/beatit.mp3");//前提是sdcard卡要先导入音频文件
		 */
    	MediaPlayer mp=MediaPlayer.create(this,R.raw.beatit);
    	mp.stop();
    	return mp;
    }
}