package dfzy.BoVideoView;

import dfzy.BoVideoView.R;
import android.app.Activity;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class example extends Activity
{
  private TextView mTextView01;
  private VideoView mVideoView01;
  private String strVideoPath = "";
  private Button mButton01, mButton02;
  private String TAG = "HIPPO_VIDEOVIEW";
  
  /* 默认判别是否安装存储卡flag为false */
  private boolean bIfSDExist = false;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    
    /* 全屏幕 */
    getWindow().setFormat(PixelFormat.TRANSLUCENT);
    setContentView(R.layout.main);
    
    /* 判断存储卡是否存在 */
    if(android.os.Environment.getExternalStorageState().equals
    (android.os.Environment.MEDIA_MOUNTED))
    {
      bIfSDExist = true;
    }
    else
    {
      bIfSDExist = false;
      mMakeTextToast
      (
        getResources().getText(R.string.str_err_nosd).toString(),
        true
      );
    }
    
    mTextView01 = (TextView)findViewById(R.id.myTextView1);
    mVideoView01 = (VideoView)findViewById(R.id.myVideoView1);
    
    mButton01 = (Button)findViewById(R.id.myButton1);
    mButton02 = (Button)findViewById(R.id.myButton2);
    
    mButton01.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View arg0)
      {
        // TODO Auto-generated method stub
        if(bIfSDExist)
        {
          /* 播放影片路径1 */
          strVideoPath = "file:///sdcard/hello.3gp";
          playVideo(strVideoPath);
        }
      }
    });
    
    mButton02.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View arg0)
      {
        // TODO Auto-generated method stub
        if(bIfSDExist)
        {
          /* 播放影片路径2 */
          strVideoPath = "file:///sdcard/test.3gp";
          playVideo(strVideoPath);
        }
      }
    });
  }
  
  /* 自定义以VideoView播放影片 */
  private void playVideo(String strPath)
  {
    if(strPath!="")
    {
      /* 调用VideoURI方法，指定解析路径 */
      mVideoView01.setVideoURI(Uri.parse(strPath));
      
      /* 设置控制Bar显示于此Context中 */
      mVideoView01.setMediaController
      (new MediaController(example.this));
      
      mVideoView01.requestFocus();
      
      /* 调用VideoView.start()自动播放 */
      mVideoView01.start();
      if(mVideoView01.isPlaying())
      {
        /* 下程序不会被运行，因start()后尚需要preparing() */
        mTextView01.setText("Now Playing:"+strPath);
        Log.i(TAG, strPath);
      }
    }
  }
  
  public void mMakeTextToast(String str, boolean isLong)
  {
    if(isLong==true)
    {
      Toast.makeText(example.this, str, Toast.LENGTH_LONG).show();
    }
    else
    {
      Toast.makeText(example.this, str, Toast.LENGTH_SHORT).show();
    }
  }
}
