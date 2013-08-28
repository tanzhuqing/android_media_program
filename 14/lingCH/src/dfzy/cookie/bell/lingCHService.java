package dfzy.cookie.bell;

import java.util.Date;
import java.util.HashMap;

import dfzy.cookie.bell.R;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

public class lingCHService extends Service {
	//�����¼�
	public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
	//��������
	public static final int ONE_SMS = 1;
	public static final int TWO_SMS = 2;
	public static final int THREE_SMS = 3;
	public static final int FOUR_SMS = 4;
	public static final int FIVE_SMS = 5;
	
	private HashMap<Integer,Integer> bellMap;//����Map
	private Date lastSMSTime;//��������ʱ��
	private int currentBell;//��ǰӦ����������
	private boolean justStart=true;//�Ƿ��ǵ�һ������ �����״����������յ����ŵ����������ŵڶ������������
	private AudioManager am;
	private int currentMediaStatus;
	private int currentMediaMax;
	
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		IntentFilter filter = new IntentFilter();
		filter.addAction(SMS_RECEIVED_ACTION);
		Log.e("COOKIE", "Service start");
		//ע�����
		registerReceiver(messageReceiver, filter);
		// ��ʼ��Map ����֮��Ľ������滻���е�����
		bellMap = new HashMap<Integer,Integer>();
		bellMap.put(ONE_SMS, R.raw.holyshit);
		bellMap.put(TWO_SMS, R.raw.holydouble);
		bellMap.put(THREE_SMS, R.raw.holytriple);
		bellMap.put(FOUR_SMS, R.raw.holyultra);
		bellMap.put(FIVE_SMS, R.raw.holyrampage);
		//��ǰʱ��
		lastSMSTime=new Date(System.currentTimeMillis());
		//��ǰӦ�����ŵ����� ��ʼΪ1
		//֮����ݼ���ж� ��Ϊ5����֮�� ��+1
		//��������һ�γ���5���� ��������Ϊ1
		currentBell=1;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//ȡ������
		unregisterReceiver(messageReceiver);
		Log.e("COOKIE", "Service end");
	}

	// �趨�㲥
	private BroadcastReceiver messageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(SMS_RECEIVED_ACTION)) {
				playBell(context, 0);
			}
		}

	};
	//������Ч
	private void playBell(Context context, int num) {
		//Ϊ��ֹ�û���ǰģʽ�ر���media��Ч �Ƚ�media��
		am=(AudioManager)getSystemService(Context.AUDIO_SERVICE);//��ȡ��������
		currentMediaStatus=am.getStreamVolume(AudioManager.STREAM_MUSIC);
		currentMediaMax=am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		am.setStreamVolume(AudioManager.STREAM_MUSIC, currentMediaMax, 0);
		//����MediaPlayer ���в���
		MediaPlayer mp = MediaPlayer.create(context, getBellResource());
		mp.setOnCompletionListener(new musicCompletionListener());
		mp.start();
	}
	
	private class musicCompletionListener implements OnCompletionListener {
		@Override
		public void onCompletion(MediaPlayer mp) {
			//���Ž����ͷ�mp��Դ
			mp.release();
			//�ָ��û�֮ǰ��mediaģʽ
			am.setStreamVolume(AudioManager.STREAM_MUSIC, currentMediaStatus, 0);
		}
	}
	//��ȡ��ǰӦ�ò��ŵ�����
	private int getBellResource() {
		//�ж�ʱ���������룩
		int preferenceInterval;
		long interval;
		Date curTime = new Date(System.currentTimeMillis());
		interval=curTime.getTime()-lastSMSTime.getTime();
		lastSMSTime=curTime;
		preferenceInterval=getPreferenceInterval();
		if(interval<preferenceInterval*60*1000&&!justStart){
			currentBell++;
			if(currentBell>5){
				currentBell=5;
			}
		}else{
			currentBell=1;
		}
		justStart=false;
		return bellMap.get(currentBell);
	}
	//��ȡPreference����
	private int getPreferenceInterval(){
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		int interval=Integer.valueOf(settings.getString("interval_config", "5"));
//		Log.v("COOKIE", "interval: "+interval);
		return interval;
	}
}
