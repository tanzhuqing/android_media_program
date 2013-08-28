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
        
        //����MP3 
        playButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(playButton.getText().toString().equals("����")){
					boolean createState=false;
					if(mediaPlayer==null){
						mediaPlayer=createLocalMp3();
						createState=true;
					}
					//����������Ƶ��Դʱ���ᴥ��onCompletion�¼��������ڸ��¼����ͷ���Ƶ��Դ��
					//�Ա�����Ӧ�ó������ʹ�ø���Դ:
					mediaPlayer.setOnCompletionListener(new OnCompletionListener(){
						@Override
						public void onCompletion(MediaPlayer mp) {
							mp.release();//�ͷ���Ƶ��Դ
							stopButton.setEnabled(false);
							setTitle("��Դ�Ѿ����ͷ���");
						}
					});
					try {
						//�ڲ�����Ƶ��Դ֮ǰ���������Prepare�������Щ׼������
						if(createState) mediaPlayer.prepare();
						//��ʼ������Ƶ
						mediaPlayer.start();
						playButton.setText("��ͣ");
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else if(playButton.getText().toString().equals("��ͣ")){
					if(mediaPlayer!=null){
						mediaPlayer.pause();//��ͣ
						playButton.setText("����");
					}
				}
				stopButton.setEnabled(true);
			}
        });
        
        //ֹͣ
        stopButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(mediaPlayer!=null){
					mediaPlayer.stop();//ֹͣ����
					mediaPlayer.release();//�ͷ���Դ
					mediaPlayer=null;
					playButton.setText("����");
					stopButton.setEnabled(false);
				}
			}
        });
        
    }
    /**
     * ��������mp3
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
     * ��������MP3
     * @return
     */
    public MediaPlayer createLocalMp3(){
    	/**
		 * ������Ƶ�ļ��ķ�����
		 * 1��������ԴĿ¼���ļ���MediaPlayer.create(MainActivity.this,R.raw.beatit);//����res\raw ��ԴĿ¼�µ�MP3�ļ�
		 * 2:����sdcard�����ļ���mediaPlayer=new MediaPlayer();
		 *   mediaPlayer.setDataSource("/sdcard/beatit.mp3");//ǰ����sdcard��Ҫ�ȵ�����Ƶ�ļ�
		 */
    	MediaPlayer mp=MediaPlayer.create(this,R.raw.beatit);
    	mp.stop();
    	return mp;
    }
}