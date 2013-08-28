package dfzy.PaiPH;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import dfzy.PaiPH.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;

/* 引用Camera类 */
import android.hardware.Camera;

/* 引用PictureCallback作为取得拍照后的事件 */
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/* 使Activity实现SurfaceHolder.Callback */
public class example extends Activity
implements SurfaceHolder.Callback
{
  /* 创建私有Camera对象 */
  private Camera mCamera01;
  private Button mButton01, mButton02, mButton03;
  
  /* 作为review照下来的相片之用 */
  private ImageView mImageView01;
  private TextView mTextView01;
  private String TAG = "HIPPO";
  private SurfaceView mSurfaceView01;
  private SurfaceHolder mSurfaceHolder01;
  //private int intScreenX, intScreenY;
  
  /* 默认相机预览模式为false */
  private boolean bIfPreview = false;
  
  /* 将照下来的图片存储在此 */
  private String strCaptureFilePath = "/sdcard/camera_snap.jpg";
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    
    /* 使应用程序全屏幕运行，不使用title bar */
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.main);
    
    /* 判断存储卡是否存在 */
    if(!checkSDCard())
    {
      /* 提醒User未安装存储卡 */
      mMakeTextToast
      (
        getResources().getText(R.string.str_err_nosd).toString(),
        true
      );
    }
    
    /* 取得屏幕解析像素 */
    DisplayMetrics dm = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(dm);
    //intScreenX = dm.widthPixels;
    //intScreenY = dm.heightPixels;
    //Log.i(TAG, Integer.toString(intScreenX));
        
    mTextView01 = (TextView) findViewById(R.id.myTextView1);
    mImageView01 = (ImageView) findViewById(R.id.myImageView1);
    
    /* 以SurfaceView作为相机Preview之用 */
    mSurfaceView01 = (SurfaceView) findViewById(R.id.mSurfaceView1);
    
    /* 绑定SurfaceView，取得SurfaceHolder对象 */
    mSurfaceHolder01 = mSurfaceView01.getHolder();
    
    /* Activity必须实现SurfaceHolder.Callback */
    mSurfaceHolder01.addCallback(example.this);
    
    /* 额外的设置预览大小设置，在此不使用 */
    //mSurfaceHolder01.setFixedSize(320, 240);
    
    /*
     * 以SURFACE_TYPE_PUSH_BUFFERS(3)
     * 作为SurfaceHolder显示类型 
     * */
    mSurfaceHolder01.setType
    (SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    
    mButton01 = (Button)findViewById(R.id.myButton1);
    mButton02 = (Button)findViewById(R.id.myButton2);
    mButton03 = (Button)findViewById(R.id.myButton3);
    
    /* 打开相机及Preview */
    mButton01.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View arg0)
      {
        // TODO Auto-generated method stub
        
        /* 自定义初始化打开相机函数 */
        initCamera();
      }
    });
    
    /* 停止Preview及相机 */
    mButton02.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View arg0)
      {
        // TODO Auto-generated method stub
        
        /* 自定义重置相机，并关闭相机预览函数 */
        resetCamera();
      }
    });
    
    /* 拍照 */
    mButton03.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View arg0)
      {
        // TODO Auto-generated method stub
        
        /* 当存储卡存在才允许拍照，存储暂存图像文件 */
        if(checkSDCard())
        {
          /* 自定义拍照函数 */
          takePicture();
        }
        else 
        {
          /* 存储卡不存在显示提示 */
          mTextView01.setText
          (
            getResources().getText(R.string.str_err_nosd).toString()
          );
        }
      }
    });
  }
  
  /* 自定义初始相机函数 */
  private void initCamera()
  {
    if(!bIfPreview)
    {
      /* 若相机非在预览模式，则打开相机 */
      mCamera01 = Camera.open();
    }
    
    if (mCamera01 != null && !bIfPreview)
    {
      Log.i(TAG, "inside the camera");
      
      /* 创建Camera.Parameters对象 */
      Camera.Parameters parameters = mCamera01.getParameters();
      
      /* 设置相片格式为JPEG */
      parameters.setPictureFormat(PixelFormat.JPEG);
      
      /* 指定preview的屏幕大小 */
      parameters.setPreviewSize(320, 240);
      
      /* 设置图片分辨率大小 */
      parameters.setPictureSize(320, 240);
      
      /* 将Camera.Parameters设置予Camera */
      mCamera01.setParameters(parameters);
      
      /* setPreviewDisplay唯一的参数为SurfaceHolder */
      mCamera01.setPreviewDisplay(mSurfaceHolder01);
      
      /* 立即运行Preview */
      mCamera01.startPreview();
      bIfPreview = true;
    }
  }
  
  /* 拍照撷取图像 */ 
  private void takePicture()
  {
    if (mCamera01 != null && bIfPreview) 
    {
      /* 调用takePicture()方法拍照 */
      mCamera01.takePicture
      (shutterCallback, rawCallback, jpegCallback);
    }
  }
  
  /* 相机重置 */
  private void resetCamera()
  {
    if (mCamera01 != null && bIfPreview)
    {
      mCamera01.stopPreview();
      /* 扩展学习，释放Camera对象 */
      //mCamera01.release();
      mCamera01 = null;
      bIfPreview = false;
    }
  }
   
  private ShutterCallback shutterCallback = new ShutterCallback()
  { 
    public void onShutter() 
    { 
      // Shutter has closed 
    } 
  }; 
   
  private PictureCallback rawCallback = new PictureCallback() 
  { 
    public void onPictureTaken(byte[] _data, Camera _camera) 
    { 
      // TODO Handle RAW image data 
    } 
  }; 

  private PictureCallback jpegCallback = new PictureCallback() 
  {
    public void onPictureTaken(byte[] _data, Camera _camera)
    {
      // TODO Handle JPEG image data
      
      /* onPictureTaken传入的第一个参数即为相片的byte */
      Bitmap bm = BitmapFactory.decodeByteArray
                  (_data, 0, _data.length);
      
      /* 创建新文件 */
      File myCaptureFile = new File(strCaptureFilePath);
      try
      {
        BufferedOutputStream bos = new BufferedOutputStream
        (new FileOutputStream(myCaptureFile));
        
        /* 采用压缩转档方法 */
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        
        /* 调用flush()方法，更新BufferStream */
        bos.flush();
        
        /* 结束OutputStream */
        bos.close();
        
        /* 将拍照下来且存储完毕的图文件，显示出来 */ 
        mImageView01.setImageBitmap(bm);
        
        /* 显示完图文件，立即重置相机，并关闭预览 */
        resetCamera();
        
        /* 再重新启动相机继续预览 */
        initCamera();
      }
      catch (Exception e)
      {
        Log.e(TAG, e.getMessage());
      }
    }
  };
  
  /* 自定义删除文件函数 */
  private void delFile(String strFileName)
  {
    try
    {
      File myFile = new File(strFileName);
      if(myFile.exists())
      {
        myFile.delete();
      }
    }
    catch (Exception e)
    {
      Log.e(TAG, e.toString());
      e.printStackTrace();
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
  
  private boolean checkSDCard()
  {
    /* 判断存储卡是否存在 */
    if(android.os.Environment.getExternalStorageState().equals
    (android.os.Environment.MEDIA_MOUNTED))
    {
      return true;
    }
    else
    {
      return false;
    }
  }
  
  @Override
  public void surfaceChanged
  (SurfaceHolder surfaceholder, int format, int w, int h)
  {
    // TODO Auto-generated method stub
    Log.i(TAG, "Surface Changed");
  }
  
  @Override
  public void surfaceCreated(SurfaceHolder surfaceholder)
  {
    // TODO Auto-generated method stub
    Log.i(TAG, "Surface Changed");
  }
  
  @Override
  public void surfaceDestroyed(SurfaceHolder surfaceholder)
  {
    // TODO Auto-generated method stub
    /* 当Surface不存在，需要删除图片 */
    try
    {
      delFile(strCaptureFilePath);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    Log.i(TAG, "Surface Destroyed");
  }
}
