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
	//监听事件
	public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
	//铃声序列
	public static final int ONE_SMS = 1;
	public static final int TWO_SMS = 2;
	public static final int THREE_SMS = 3;
	public static final int FOUR_SMS = 4;
	public static final int FIVE_SMS = 5;
	
	private HashMap<Integer,Integer> bellMap;//铃声Map
	private Date lastSMSTime;//上条短信时间
	private int currentBell;//当前应当播放铃声
	private boolean justStart=true;//是否是第一次启动 避免首次启动马上收到短信导致立即播放第二条铃声的情况
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
		//注册监听
		registerReceiver(messageReceiver, filter);
		// 初始化Map 根据之后改进可以替换其中的铃声
		bellMap = new HashMap<Integer,Integer>();
		bellMap.put(ONE_SMS, R.raw.holyshit);
		bellMap.put(TWO_SMS, R.raw.holydouble);
		bellMap.put(THREE_SMS, R.raw.holytriple);
		bellMap.put(FOUR_SMS, R.raw.holyultra);
		bellMap.put(FIVE_SMS, R.raw.holyrampage);
		//当前时间
		lastSMSTime=new Date(System.currentTimeMillis());
		//当前应当播放的铃声 初始为1
		//之后根据间隔判断 若为5分钟之内 则+1
		//若举例上一次超过5分钟 则重新置为1
		currentBell=1;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//取消监听
		unregisterReceiver(messageReceiver);
		Log.e("COOKIE", "Service end");
	}

	// 设定广播
	private BroadcastReceiver messageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(SMS_RECEIVED_ACTION)) {
				playBell(context, 0);
			}
		}

	};
	//播放音效
	private void playBell(Context context, int num) {
		//为防止用户当前模式关闭了media音效 先将media打开
		am=(AudioManager)getSystemService(Context.AUDIO_SERVICE);//获取音量控制
		currentMediaStatus=am.getStreamVolume(AudioManager.STREAM_MUSIC);
		currentMediaMax=am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		am.setStreamVolume(AudioManager.STREAM_MUSIC, currentMediaMax, 0);
		//创建MediaPlayer 进行播放
		MediaPlayer mp = MediaPlayer.create(context, getBellResource());
		mp.setOnCompletionListener(new musicCompletionListener());
		mp.start();
	}
	
	private class musicCompletionListener implements OnCompletionListener {
		@Override
		public void onCompletion(MediaPlayer mp) {
			//播放结束释放mp资源
			mp.release();
			//恢复用户之前的media模式
			am.setStreamVolume(AudioManager.STREAM_MUSIC, currentMediaStatus, 0);
		}
	}
	//获取当前应该播放的铃声
	private int getBellResource() {
		//判断时间间隔（毫秒）
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
	//获取Preference设置
	private int getPreferenceInterval(){
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		int interval=Integer.valueOf(settings.getString("interval_config", "5"));
//		Log.v("COOKIE", "interval: "+interval);
		return interval;
	}
}
