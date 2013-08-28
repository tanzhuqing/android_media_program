package irdc.example;

import irdc.example.R;
import android.app.Activity;
import android.content.Context;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.TextView;

public class example extends Activity
{
  /* ����SensorManager���� */
  private SensorManager mSensorManager01;
  private TextView mTextView01;
  
  /* ��˽�����Ա�洢AudioManagerģʽ */
  private int strRingerMode;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    mTextView01 = (TextView)findViewById(R.id.myTextView1);
    
    /* ����SensorManager����ȡ��SENSOR_SERVICE���� */
    mSensorManager01 = 
    (SensorManager)getSystemService(Context.SENSOR_SERVICE);
    
    /* ȡ�����ڵ�AudioManagerģʽ */
    GetAudioManagerMode();
    
    /* �������ڵ�AudioManagerģʽ����ʾ��TextView���� */
    switch(strRingerMode)
    {
      /* ����ģʽ */
      case AudioManager.RINGER_MODE_NORMAL:
        mTextView01.setText(R.string.str_normal_mode);
        break;
      /* ����ģʽ */
      case AudioManager.RINGER_MODE_SILENT:
        mTextView01.setText(R.string.str_silent_mode);
        break;
      /* ��ģʽ */
      case AudioManager.RINGER_MODE_VIBRATE:
        mTextView01.setText(R.string.str_vibrate_mode);
        break;
    }
  }
  
  /* ����SensorListener��׽onSensorChanged�¼� */
  private final SensorListener mSensorListener = 
                new SensorListener()
  {
    @Override
    public void onSensorChanged(int sensor, float[] values)
    {
      // TODO Auto-generated method stub
      
      //float fRollAngle = values[SensorManager.DATA_X];
      
      /* ȡ��Yƽ��������б��Pitch�Ƕ� */
      float fPitchAngle = values[SensorManager.DATA_Y];
      
      /* ��������(Y����ת)����ʵ����ΪС��-120Ϊ������ */
      if(fPitchAngle<-120)
      {
        /* ������Ϊ����ģʽ */
        ChangeToSilentMode();
        
        /* ������Ϊ��ģʽ */
        ChangeToVibrateMode();
        
        /* �ж�����ģʽ */
        switch(strRingerMode)
        {
          /* ����ģʽ */
          case AudioManager.RINGER_MODE_NORMAL:
            mTextView01.setText(R.string.str_normal_mode);
            break;
          /* ����ģʽ */
          case AudioManager.RINGER_MODE_SILENT:
            mTextView01.setText(R.string.str_silent_mode);
            break;
          /* ��ģʽ */
          case AudioManager.RINGER_MODE_VIBRATE:
            mTextView01.setText(R.string.str_vibrate_mode);
            break;
        }
      }
      else
      {
        /* ��������(Y����ת)����ʵ����Ϊ����-120Ϊ������ */
        /* ����Ϊ����ģʽ */
        ChangeToNormalMode();
        
        /* ���ø���ģʽ����һ��ȷ���ֻ���ģʽΪ�� */
        switch(strRingerMode)
        {
          case AudioManager.RINGER_MODE_NORMAL:
            mTextView01.setText(R.string.str_normal_mode);
            break;
          case AudioManager.RINGER_MODE_SILENT:
            mTextView01.setText(R.string.str_silent_mode);
            break;
          case AudioManager.RINGER_MODE_VIBRATE:
            mTextView01.setText(R.string.str_vibrate_mode);
            break;
        }
      }
    }
    
    @Override
    public void onAccuracyChanged(int sensor, int values)
    {
      // TODO Auto-generated method stub
      
    }
  };
  
  /* ȡ�õ��µ�AudioManagerģʽ */
  private void GetAudioManagerMode()
  {
    try
    {
      /* ����AudioManager����ȡ��AUDIO_SERVICE */
      AudioManager audioManager = 
      (AudioManager)getSystemService(Context.AUDIO_SERVICE);
      
      if (audioManager != null)
      {
        /* RINGER_MODE_NORMAL | 
           RINGER_MODE_SILENT | 
           RINGER_MODE_VIBRATE */
           
        strRingerMode = audioManager.getRingerMode();
      }
    }
    catch(Exception e)
    {
      mTextView01.setText(e.toString());
      e.printStackTrace();
    }
  }
  
  /* ����Ϊ����ģʽ */
  private void ChangeToSilentMode()
  {
    try
    {
      AudioManager audioManager =
      (AudioManager)getSystemService(Context.AUDIO_SERVICE);
      
      if (audioManager != null)
      {
        /* RINGER_MODE_NORMAL | 
           RINGER_MODE_SILENT | 
           RINGER_MODE_VIBRATE */
           
        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        strRingerMode = audioManager.getRingerMode();
      }
    }
    catch(Exception e)
    {
      mTextView01.setText(e.toString());
      e.printStackTrace();
    }
  }
  
  /* ����Ϊ��ģʽ */
  private void ChangeToVibrateMode()
  {
    try
    {
      AudioManager audioManager = 
      (AudioManager)getSystemService(Context.AUDIO_SERVICE);
      
      if (audioManager != null)
      {
        /* ����setRingerMode����������ģʽ */
        audioManager.setRingerMode
        (
          AudioManager.RINGER_MODE_VIBRATE
        );
        /* RINGER_MODE_NORMAL | 
           RINGER_MODE_SILENT | 
           RINGER_MODE_VIBRATE */
        strRingerMode = audioManager.getRingerMode();
      }
    }
    catch(Exception e)
    {
      mTextView01.setText(e.toString());
      e.printStackTrace();
    }
  }
  
  /* ����Ϊ����ģʽ */
  private void ChangeToNormalMode()
  {
    try
    {
      AudioManager audioManager = 
      (AudioManager)getSystemService(Context.AUDIO_SERVICE);
      
      if (audioManager != null)
      {
        /* RINGER_MODE_NORMAL | 
           RINGER_MODE_SILENT | 
           RINGER_MODE_VIBRATE */
        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        strRingerMode = audioManager.getRingerMode();
      }
    }
    catch(Exception e)
    {
      mTextView01.setText(e.toString());
      e.printStackTrace();
    }
  }
  
  @Override
  protected void onResume()
  {
    // TODO Auto-generated method stub
    
    /* ע��һ��SensorListener��Listener */ 
    /* ����Sensorģʽ��rate */
    mSensorManager01.registerListener
    (
      mSensorListener,
      SensorManager.SENSOR_ORIENTATION,
      SensorManager.SENSOR_DELAY_NORMAL
    );
    super.onResume();
  }
  
  @Override
  protected void onPause()
  {
    // TODO Auto-generated method stub
    
    /* �ڸ���onPause�¼���ȡ��mSensorListener */
    mSensorManager01.unregisterListener(mSensorListener);
    super.onPause();
  }
}
