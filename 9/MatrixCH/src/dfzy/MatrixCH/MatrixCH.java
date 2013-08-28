package dfzy.MatrixCH;

/* import相关class */
import dfzy.MatrixCH.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;

public class MatrixCH extends Activity
{
  /* 相关变量声明 */
  private ImageView mImageView;
  private Button mButton01;
  private Button mButton02;
  private AbsoluteLayout layout1;
  private Bitmap bmp;
  private int id=0;
  private int displayWidth;
  private int displayHeight;
  private float scaleWidth=1;
  private float scaleHeight=1;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    /* 载入main.xml Layout */
    setContentView(R.layout.main);
    
    /* 取得屏幕分辨率大小 */
    DisplayMetrics dm=new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(dm);
    displayWidth=dm.widthPixels;
    /* 屏幕高度须扣除下方Button高度 */
    displayHeight=dm.heightPixels-80;
    /* 初始化相关变量 */
    bmp=BitmapFactory.decodeResource(getResources(),
                                     R.drawable.suofang);
    mImageView = (ImageView)findViewById(R.id.myImageView);
    layout1 = (AbsoluteLayout)findViewById(R.id.layout1); 
    mButton01 = (Button)findViewById(R.id.myButton1);
    mButton02 = (Button)findViewById(R.id.myButton2);
    
    /* 缩小按钮onClickListener */
    mButton01.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        small();
      }
    });
    
    /* 放大按钮onClickListener */
    mButton02.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        big();
      }
    });
  }
  
  /* 图片缩小的method */
  private void small()
  {
    int bmpWidth=bmp.getWidth();
    int bmpHeight=bmp.getHeight();
    /* 设置图片缩小的比例 */
    double scale=0.8;  
    /* 计算出这次要缩小的比例 */
    scaleWidth=(float) (scaleWidth*scale);
    scaleHeight=(float) (scaleHeight*scale);
    
    /* 产生reSize后的Bitmap对象 */
    Matrix matrix = new Matrix();  
    matrix.postScale(scaleWidth, scaleHeight); 
    Bitmap resizeBmp = Bitmap.createBitmap(bmp,0,0,bmpWidth,
                                           bmpHeight,matrix,true);
     
    if(id==0)
    {
      /* 如果是第一次按，就删除原来默认的ImageView */
      layout1.removeView(mImageView);
    }
    else
    {
      /* 如果不是第一次按，就删除上次放大缩小所产生的ImageView */
      layout1.removeView((ImageView)findViewById(id));
    }
    /* 产生新的ImageView，放入reSize的Bitmap对象，再放入Layout中 */
    id++;
    ImageView imageView = new ImageView(MatrixCH.this);  
    imageView.setId(id);
    imageView.setImageBitmap(resizeBmp);
    layout1.addView(imageView); 
    setContentView(layout1);
    
    /* 因为图片放到最大时放大按钮会disable，所以在缩小时把他重设为enable */
    mButton02.setEnabled(true);
  }
  
  /* 图片放大的method */
  private void big()
  {   
    int bmpWidth=bmp.getWidth();
    int bmpHeight=bmp.getHeight();
    /* 设置图片放大的比例 */
    double scale=1.25;  
    /* 计算这次要放大的比例 */
    scaleWidth=(float)(scaleWidth*scale);
    scaleHeight=(float)(scaleHeight*scale);
    
    /* 产生reSize后的Bitmap对象 */
    Matrix matrix = new Matrix();  
    matrix.postScale(scaleWidth, scaleHeight); 
    Bitmap resizeBmp = Bitmap.createBitmap(bmp,0,0,bmpWidth,
                                           bmpHeight,matrix,true);
      
    if(id==0)
    {
      /* 如果是第一次按，就删除原来设置的ImageView */
      layout1.removeView(mImageView);
    }
    else
    {
      /* 如果不是第一次按，就删除上次放大缩小所产生的ImageView */
      layout1.removeView((ImageView)findViewById(id));
    }
    /* 产生新的ImageView，放入reSize的Bitmap对象，再放入Layout中 */
    id++; 
    ImageView imageView = new ImageView(MatrixCH.this);  
    imageView.setId(id);
    imageView.setImageBitmap(resizeBmp);
    layout1.addView(imageView); 
    setContentView(layout1); 
    
    /* 如果再放大会超过屏幕大小，就把Button disable */ 
    if(scaleWidth*scale*bmpWidth>displayWidth||
        scaleHeight*scale*bmpHeight>displayHeight)
    {
      mButton02.setEnabled(false);
    }
  }
}
