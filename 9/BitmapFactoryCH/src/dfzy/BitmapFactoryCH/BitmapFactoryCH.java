package dfzy.BitmapFactoryCH;

import dfzy.BitmapFactoryCH.R;
import android.app.Activity; 
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle; 
import android.view.ContextMenu; 
import android.view.Menu; 
import android.view.MenuItem; 
import android.view.View; 
import android.view.ContextMenu.ContextMenuInfo; 
import android.widget.ImageView;
import android.widget.ListView; 
import android.widget.TextView; 

public class BitmapFactoryCH extends Activity 
{ 
  /*����һ�� TextVie������һ��ImageView����*/
  private TextView mTextView01; 
  private ImageView mImageView01;
  /*����Context Menu��ѡ���*/
  protected static final int CONTEXT_ITEM1 = Menu.FIRST;  
  protected static final int CONTEXT_ITEM2 = Menu.FIRST+1;
  protected static final int CONTEXT_ITEM3 = Menu.FIRST+2;
   
  /** Called when the activity is first created. */ 
  @Override 
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.main); 
     
    /*ͨ��findViewById����������TextView��ImageView����*/
    mTextView01 = (TextView)findViewById(R.id.myTextView1); 
    mImageView01= (ImageView)findViewById(R.id.myImageView1);
    /*��Drawable�е�ͼƬm123.png�����Զ����ImageView��*/
    mImageView01.setImageDrawable(getResources().
                 getDrawable(R.drawable.m123));
    
    /*����OnCreateContextMenuListener��TextView
     * ��ͼƬ�Ͽ���ʹ��ContextMenu*/
    mImageView01.setOnCreateContextMenuListener 
    (new ListView.OnCreateContextMenuListener() 
    {  
      @Override 
      /*����OnCreateContextMenu������ContextMenu��ѡ��*/
      public void onCreateContextMenu 
      (ContextMenu menu, View v, ContextMenuInfo menuInfo) 
      { 
        // TODO Auto-generated method stub 
        menu.add(Menu.NONE, CONTEXT_ITEM1, 0, R.string.str_context1); 
        menu.add(Menu.NONE, CONTEXT_ITEM2, 0, R.string.str_context2); 
        menu.add(Menu.NONE, CONTEXT_ITEM3, 0, R.string.str_context3);
      }  
    }); 
  } 

  @Override 
  /*����OnContextItemSelected�������û����menu��Ķ���*/
  public boolean onContextItemSelected(MenuItem item) 
  { 
    // TODO Auto-generated method stub 
    /*�Զ���Bitmap����ͨ��BitmapFactory.decodeResourceȡ��
     *Ԥ��Import��Drawable��m123.pngͼ��*/
    Bitmap myBmp = BitmapFactory.decodeResource 
      (getResources(), R.drawable.m123); 
     /*ͨ��Bitmap�����getHight��getWidth��ȡ��ͼƬ����*/
     int intHeight = myBmp.getHeight();
     int intWidth = myBmp.getWidth();
    
    try
    {
      /*�˵�ѡ���붯��*/
      switch(item.getItemId()) 
      { 
        /*��ͼƬ������ʾ��TextView��*/
        case CONTEXT_ITEM1:
          String strOpt =
          getResources().getString(R.string.str_width)
          +"="+Integer.toString(intWidth);
          mTextView01.setText(strOpt);
          break;
        /*��ͼƬ�߶���ʾ��TextView��*/
        case CONTEXT_ITEM2:
          String strOpt2 =
          getResources().getString(R.string.str_height)
          +"="+Integer.toString(intHeight); 
          mTextView01.setText(strOpt2); 
          break;
        /*��ͼƬ������ʾ��TextView��*/
        case CONTEXT_ITEM3:
          String strOpt3 =
          getResources().getString(R.string.str_width)
          +"="+Integer.toString(intWidth)+"\n" 
          +getResources().getString(R.string.str_height) 
          +"="+Integer.toString(intHeight);  
          mTextView01.setText(strOpt3);  
          break;  
      }
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    return super.onContextItemSelected(item);
  }
}
