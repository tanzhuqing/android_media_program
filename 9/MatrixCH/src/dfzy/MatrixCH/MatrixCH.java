package dfzy.MatrixCH;

/* import���class */
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
  /* ��ر������� */
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
    /* ����main.xml Layout */
    setContentView(R.layout.main);
    
    /* ȡ����Ļ�ֱ��ʴ�С */
    DisplayMetrics dm=new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(dm);
    displayWidth=dm.widthPixels;
    /* ��Ļ�߶���۳��·�Button�߶� */
    displayHeight=dm.heightPixels-80;
    /* ��ʼ����ر��� */
    bmp=BitmapFactory.decodeResource(getResources(),
                                     R.drawable.suofang);
    mImageView = (ImageView)findViewById(R.id.myImageView);
    layout1 = (AbsoluteLayout)findViewById(R.id.layout1); 
    mButton01 = (Button)findViewById(R.id.myButton1);
    mButton02 = (Button)findViewById(R.id.myButton2);
    
    /* ��С��ťonClickListener */
    mButton01.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        small();
      }
    });
    
    /* �Ŵ�ťonClickListener */
    mButton02.setOnClickListener(new Button.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        big();
      }
    });
  }
  
  /* ͼƬ��С��method */
  private void small()
  {
    int bmpWidth=bmp.getWidth();
    int bmpHeight=bmp.getHeight();
    /* ����ͼƬ��С�ı��� */
    double scale=0.8;  
    /* ��������Ҫ��С�ı��� */
    scaleWidth=(float) (scaleWidth*scale);
    scaleHeight=(float) (scaleHeight*scale);
    
    /* ����reSize���Bitmap���� */
    Matrix matrix = new Matrix();  
    matrix.postScale(scaleWidth, scaleHeight); 
    Bitmap resizeBmp = Bitmap.createBitmap(bmp,0,0,bmpWidth,
                                           bmpHeight,matrix,true);
     
    if(id==0)
    {
      /* ����ǵ�һ�ΰ�����ɾ��ԭ��Ĭ�ϵ�ImageView */
      layout1.removeView(mImageView);
    }
    else
    {
      /* ������ǵ�һ�ΰ�����ɾ���ϴηŴ���С��������ImageView */
      layout1.removeView((ImageView)findViewById(id));
    }
    /* �����µ�ImageView������reSize��Bitmap�����ٷ���Layout�� */
    id++;
    ImageView imageView = new ImageView(MatrixCH.this);  
    imageView.setId(id);
    imageView.setImageBitmap(resizeBmp);
    layout1.addView(imageView); 
    setContentView(layout1);
    
    /* ��ΪͼƬ�ŵ����ʱ�Ŵ�ť��disable����������Сʱ��������Ϊenable */
    mButton02.setEnabled(true);
  }
  
  /* ͼƬ�Ŵ��method */
  private void big()
  {   
    int bmpWidth=bmp.getWidth();
    int bmpHeight=bmp.getHeight();
    /* ����ͼƬ�Ŵ�ı��� */
    double scale=1.25;  
    /* �������Ҫ�Ŵ�ı��� */
    scaleWidth=(float)(scaleWidth*scale);
    scaleHeight=(float)(scaleHeight*scale);
    
    /* ����reSize���Bitmap���� */
    Matrix matrix = new Matrix();  
    matrix.postScale(scaleWidth, scaleHeight); 
    Bitmap resizeBmp = Bitmap.createBitmap(bmp,0,0,bmpWidth,
                                           bmpHeight,matrix,true);
      
    if(id==0)
    {
      /* ����ǵ�һ�ΰ�����ɾ��ԭ�����õ�ImageView */
      layout1.removeView(mImageView);
    }
    else
    {
      /* ������ǵ�һ�ΰ�����ɾ���ϴηŴ���С��������ImageView */
      layout1.removeView((ImageView)findViewById(id));
    }
    /* �����µ�ImageView������reSize��Bitmap�����ٷ���Layout�� */
    id++; 
    ImageView imageView = new ImageView(MatrixCH.this);  
    imageView.setId(id);
    imageView.setImageBitmap(resizeBmp);
    layout1.addView(imageView); 
    setContentView(layout1); 
    
    /* ����ٷŴ�ᳬ����Ļ��С���Ͱ�Button disable */ 
    if(scaleWidth*scale*bmpWidth>displayWidth||
        scaleHeight*scale*bmpHeight>displayHeight)
    {
      mButton02.setEnabled(false);
    }
  }
}
