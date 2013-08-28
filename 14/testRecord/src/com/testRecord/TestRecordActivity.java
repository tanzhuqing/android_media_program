package com.testRecord;    
    
import android.app.Activity;    
import android.media.AudioFormat;    
import android.media.AudioManager;    
import android.media.AudioRecord;    
import android.media.AudioTrack;    
import android.media.MediaRecorder;    
import android.os.Bundle;    
import android.view.View;    
import android.widget.Button;    
import android.widget.SeekBar;    
import android.widget.Toast;    
    
public class TestRecordActivity extends Activity {    
    /** Called when the activity is first created. */    
    Button btnRecord, btnStop, btnExit;    
    SeekBar skbVolume;//��������    
    boolean isRecording = false;//�Ƿ�¼�ŵı��    
    static final int frequency = 44100;    
    static final int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;    
    static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;    
    int recBufSize,playBufSize;    
    AudioRecord audioRecord;    
    AudioTrack audioTrack;    
    
    @Override    
    public void onCreate(Bundle savedInstanceState) {    
        super.onCreate(savedInstanceState);    
        setContentView(R.layout.main);    
        setTitle("������");    
        recBufSize = AudioRecord.getMinBufferSize(frequency,    
                channelConfiguration, audioEncoding);    
    
        playBufSize=AudioTrack.getMinBufferSize(frequency,    
                channelConfiguration, audioEncoding);    
        // -----------------------------------------    
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency,    
                channelConfiguration, audioEncoding, recBufSize);    
    
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, frequency,    
                channelConfiguration, audioEncoding,    
                playBufSize, AudioTrack.MODE_STREAM);    
        //------------------------------------------    
        btnRecord = (Button) this.findViewById(R.id.btnRecord);    
        btnRecord.setOnClickListener(new ClickEvent());    
        btnStop = (Button) this.findViewById(R.id.btnStop);    
        btnStop.setOnClickListener(new ClickEvent());    
        btnExit = (Button) this.findViewById(R.id.btnExit);    
        btnExit.setOnClickListener(new ClickEvent());    
        skbVolume=(SeekBar)this.findViewById(R.id.skbVolume);    
        skbVolume.setMax(100);//�������ڵļ���    
        skbVolume.setProgress(70);//����seekbar��λ��ֵ    
        audioTrack.setStereoVolume(0.7f, 0.7f);//���õ�ǰ������С    
        skbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {    
                
            @Override    
            public void onStopTrackingTouch(SeekBar seekBar) {    
                float vol=(float)(seekBar.getProgress())/(float)(seekBar.getMax());    
                audioTrack.setStereoVolume(vol, vol);//��������    
            }    
                
            @Override    
            public void onStartTrackingTouch(SeekBar seekBar) {    
                // TODO Auto-generated method stub    
            }    
                
            @Override    
            public void onProgressChanged(SeekBar seekBar, int progress,    
                    boolean fromUser) {    
                // TODO Auto-generated method stub    
            }    
        });    
    }    
    
    @Override    
    protected void onDestroy() {    
        super.onDestroy();    
        android.os.Process.killProcess(android.os.Process.myPid());    
    }    
    
    class ClickEvent implements View.OnClickListener {    
    
        @Override    
        public void onClick(View v) {    
            if (v == btnRecord) {    
                isRecording = true;    
                new RecordPlayThread().start();// ��һ���̱߳�¼�߷�    
            } else if (v == btnStop) {    
                isRecording = false;    
            } else if (v == btnExit) {    
                isRecording = false;    
                TestRecordActivity.this.finish();    
            }    
        }    
    }    
    
    class RecordPlayThread extends Thread {    
        public void run() {    
            try {    
                byte[] buffer = new byte[recBufSize];    
                audioRecord.startRecording();//��ʼ¼��    
                audioTrack.play();//��ʼ����    
                    
                while (isRecording) {    
                    //��MIC�������ݵ�������    
                    int bufferReadResult = audioRecord.read(buffer, 0,    
                            recBufSize);    
    
                    byte[] tmpBuf = new byte[bufferReadResult];    
                    System.arraycopy(buffer, 0, tmpBuf, 0, bufferReadResult);    
                    //д�����ݼ�����    
                    audioTrack.write(tmpBuf, 0, tmpBuf.length);    
                }    
                audioTrack.stop();    
                audioRecord.stop();    
            } catch (Throwable t) {    
                Toast.makeText(TestRecordActivity.this, t.getMessage(), 1000);    
            }    
        }    
    };    
}   
