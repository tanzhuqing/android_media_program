package dfzy.tiaoCH;

/* import���class */
import dfzy.tiaoCH.R;
import android.app.Activity; 
import android.content.Context; 
import android.media.AudioManager;
import android.os.Bundle; 
import android.view.View; 
import android.widget.Button; 
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class example extends Activity 
{ 
  /* �������� */
  private ImageView myImage;
  private ImageButton downButton;
  private ImageButton upButton;
  private ImageButton normalButton;
  private ImageButton muteButton;
  private ImageButton vibrateButton;
  private ProgressBar myProgress;
  private AudioManager audioMa;
  private int volume=0;

  @Override 
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.main);

    /* �����ʼ�� */
    audioMa = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
    myImage = (ImageView)findViewById(R.id.myImage); 
    myProgress = (ProgressBar)findViewById(R.id.myProgress); 
    downButton = (ImageButton)findViewById(R.id.downButton); 
    upButton = (ImageButton)findViewById(R.id.upButton); 
    normalButton = (ImageButton)findViewById(R.id.normalButton); 
    muteButton = (ImageButton)findViewById(R.id.muteButton); 
    vibrateButton = (ImageButton)findViewById(R.id.vibrateButton); 

    /* ���ó�ʼ���ֻ����� */
    volume=audioMa.getStreamVolume(AudioManager.STREAM_RING); 
    myProgress.setProgress(volume);
    /* ���ó�ʼ������ģʽ */
    int mode=audioMa.getRingerMode();
    if(mode==AudioManager.RINGER_MODE_NORMAL)
    {
      myImage.setImageDrawable(getResources()
                               .getDrawable(R.drawable.normal));
    }
    else if(mode==AudioManager.RINGER_MODE_SILENT)
    {
      myImage.setImageDrawable(getResources()
                               .getDrawable(R.drawable.mute));
    }
    else if(mode==AudioManager.RINGER_MODE_VIBRATE)
    {
      myImage.setImageDrawable(getResources()
                               .getDrawable(R.drawable.vibrate));
    }

    /* ������С����Button */
    downButton.setOnClickListener(new Button.OnClickListener() 
    { 
      @Override 
      public void onClick(View arg0) 
      {
        /* ����������С��һ�� */
        audioMa.adjustVolume(AudioManager.ADJUST_LOWER, 0); 
        volume=audioMa.getStreamVolume(AudioManager.STREAM_RING);
        myProgress.setProgress(volume);
        /* ���õ���������ģʽ */
        int mode=audioMa.getRingerMode();
        if(mode==AudioManager.RINGER_MODE_NORMAL)
        {
          myImage.setImageDrawable(getResources()
                                   .getDrawable(R.drawable.normal));
        }
        else if(mode==AudioManager.RINGER_MODE_SILENT)
        {
          myImage.setImageDrawable(getResources()
                                   .getDrawable(R.drawable.mute));
        }
        else if(mode==AudioManager.RINGER_MODE_VIBRATE)
        {
          myImage.setImageDrawable(getResources()
                                  .getDrawable(R.drawable.vibrate));
        }
      }
    }); 

    /* ������������Button */
    upButton.setOnClickListener(new Button.OnClickListener()
    {
      @Override 
      public void onClick(View arg0)
      {
        /* ��������������һ�� */
        audioMa.adjustVolume(AudioManager.ADJUST_RAISE, 0);
        volume=audioMa.getStreamVolume(AudioManager.STREAM_RING);
        myProgress.setProgress(volume);
        /* ���õ����������ģʽ */
        int mode=audioMa.getRingerMode();
        if(mode==AudioManager.RINGER_MODE_NORMAL)
        {
          myImage.setImageDrawable(getResources()
                                   .getDrawable(R.drawable.normal));
        }
        else if(mode==AudioManager.RINGER_MODE_SILENT)
        {
          myImage.setImageDrawable(getResources()
                                   .getDrawable(R.drawable.mute));
        }
        else if(mode==AudioManager.RINGER_MODE_VIBRATE)
        {
          myImage.setImageDrawable(getResources()
                                  .getDrawable(R.drawable.vibrate));
        }
      } 
    }); 

    /* ��������ģʽΪ����ģʽ��Button */ 
    normalButton.setOnClickListener(new Button.OnClickListener() 
    { 
      @Override 
      public void onClick(View arg0) 
      {
        /* ��������ģʽΪNORMAL */
        audioMa.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        /* ��������������ģʽ */
        volume=audioMa.getStreamVolume(AudioManager.STREAM_RING); 
        myProgress.setProgress(volume);
        myImage.setImageDrawable(getResources()
                                 .getDrawable(R.drawable.normal));
      } 
    });

    /* ��������ģʽΪ����ģʽ��Button */ 
    muteButton.setOnClickListener(new Button.OnClickListener() 
    { 
      @Override 
      public void onClick(View arg0) 
      { 
        /* ��������ģʽΪSILENT */
        audioMa.setRingerMode(AudioManager.RINGER_MODE_SILENT); 
        /* ��������������״̬ */
        volume=audioMa.getStreamVolume(AudioManager.STREAM_RING);
        myProgress.setProgress(volume);
        myImage.setImageDrawable(getResources()
                                 .getDrawable(R.drawable.mute)); 
      } 
    }); 

    /* ��������ģʽΪ��ģʽ��Button */ 
    vibrateButton.setOnClickListener(new Button.OnClickListener()
    {
      @Override 
      public void onClick(View arg0) 
      { 
        /* ��������ģʽΪVIBRATE */
        audioMa.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        /* ��������������״̬ */
        volume=audioMa.getStreamVolume(AudioManager.STREAM_RING); 
        myProgress.setProgress(volume);
        myImage.setImageDrawable(getResources()
                                 .getDrawable(R.drawable.vibrate));
      }
    });
  }
}
