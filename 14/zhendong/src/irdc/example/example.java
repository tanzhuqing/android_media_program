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
  /* 创建SensorManager对象 */
  private SensorManager mSensorManager01;
  private TextView mTextView01;
  
  /* 以私有类成员存储AudioManager模式 */
  private int strRingerMode;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    mTextView01 = (TextView)findViewById(R.id.myTextView1);
    
    /* 创建SensorManager对象，取得SENSOR_SERVICE服务 */
    mSensorManager01 = 
    (SensorManager)getSystemService(Context.SENSOR_SERVICE);
    
    /* 取得现在的AudioManager模式 */
    GetAudioManagerMode();
    
    /* 依据现在的AudioManager模式，显示于TextView当中 */
    switch(strRingerMode)
    {
      /* 正常模式 */
      case AudioManager.RINGER_MODE_NORMAL:
        mTextView01.setText(R.string.str_normal_mode);
        break;
      /* 静音模式 */
      case AudioManager.RINGER_MODE_SILENT:
        mTextView01.setText(R.string.str_silent_mode);
        break;
      /* 震动模式 */
      case AudioManager.RINGER_MODE_VIBRATE:
        mTextView01.setText(R.string.str_vibrate_mode);
        break;
    }
  }
  
  /* 创建SensorListener捕捉onSensorChanged事件 */
  private final SensorListener mSensorListener = 
                new SensorListener()
  {
    @Override
    public void onSensorChanged(int sensor, float[] values)
    {
      // TODO Auto-generated method stub
      
      //float fRollAngle = values[SensorManager.DATA_X];
      
      /* 取得Y平面左右倾斜的Pitch角度 */
      float fPitchAngle = values[SensorManager.DATA_Y];
      
      /* 正面向下(Y轴旋转)，经实验结果为小于-120为翻背面 */
      if(fPitchAngle<-120)
      {
        /* 先设置为静音模式 */
        ChangeToSilentMode();
        
        /* 再设置为震动模式 */
        ChangeToVibrateMode();
        
        /* 判断铃声模式 */
        switch(strRingerMode)
        {
          /* 正常模式 */
          case AudioManager.RINGER_MODE_NORMAL:
            mTextView01.setText(R.string.str_normal_mode);
            break;
          /* 静音模式 */
          case AudioManager.RINGER_MODE_SILENT:
            mTextView01.setText(R.string.str_silent_mode);
            break;
          /* 震动模式 */
          case AudioManager.RINGER_MODE_VIBRATE:
            mTextView01.setText(R.string.str_vibrate_mode);
            break;
        }
      }
      else
      {
        /* 正面向上(Y轴旋转)，经实验结果为大于-120为翻正面 */
        /* 更改为正常模式 */
        ChangeToNormalMode();
        
        /* 调用更改模式后，再一次确认手机的模式为何 */
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
  
  /* 取得当下的AudioManager模式 */
  private void GetAudioManagerMode()
  {
    try
    {
      /* 创建AudioManager对象，取得AUDIO_SERVICE */
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
  
  /* 更改为静音模式 */
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
  
  /* 更改为震动模式 */
  private void ChangeToVibrateMode()
  {
    try
    {
      AudioManager audioManager = 
      (AudioManager)getSystemService(Context.AUDIO_SERVICE);
      
      if (audioManager != null)
      {
        /* 调用setRingerMode方法，设置模式 */
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
  
  /* 更改为正常模式 */
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
    
    /* 注册一个SensorListener的Listener */ 
    /* 传入Sensor模式与rate */
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
    
    /* 于覆盖onPause事件，取消mSensorListener */
    mSensorManager01.unregisterListener(mSensorListener);
    super.onPause();
  }
}
