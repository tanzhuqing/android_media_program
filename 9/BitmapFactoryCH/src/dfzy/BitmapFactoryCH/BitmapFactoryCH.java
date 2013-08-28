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
  /*声明一个 TextVie变量与一个ImageView变量*/
  private TextView mTextView01; 
  private ImageView mImageView01;
  /*声明Context Menu的选项常数*/
  protected static final int CONTEXT_ITEM1 = Menu.FIRST;  
  protected static final int CONTEXT_ITEM2 = Menu.FIRST+1;
  protected static final int CONTEXT_ITEM3 = Menu.FIRST+2;
   
  /** Called when the activity is first created. */ 
  @Override 
  public void onCreate(Bundle savedInstanceState) 
  { 
    super.onCreate(savedInstanceState); 
    setContentView(R.layout.main); 
     
    /*通过findViewById构造器创建TextView与ImageView对象*/
    mTextView01 = (TextView)findViewById(R.id.myTextView1); 
    mImageView01= (ImageView)findViewById(R.id.myImageView1);
    /*将Drawable中的图片m123.png放入自定义的ImageView中*/
    mImageView01.setImageDrawable(getResources().
                 getDrawable(R.drawable.m123));
    
    /*设置OnCreateContextMenuListener给TextView
     * 让图片上可以使用ContextMenu*/
    mImageView01.setOnCreateContextMenuListener 
    (new ListView.OnCreateContextMenuListener() 
    {  
      @Override 
      /*覆盖OnCreateContextMenu来创建ContextMenu的选项*/
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
  /*覆盖OnContextItemSelected来定义用户点击menu后的动作*/
  public boolean onContextItemSelected(MenuItem item) 
  { 
    // TODO Auto-generated method stub 
    /*自定义Bitmap对象并通过BitmapFactory.decodeResource取得
     *预先Import至Drawable的m123.png图档*/
    Bitmap myBmp = BitmapFactory.decodeResource 
      (getResources(), R.drawable.m123); 
     /*通过Bitmap对象的getHight与getWidth来取得图片宽高*/
     int intHeight = myBmp.getHeight();
     int intWidth = myBmp.getWidth();
    
    try
    {
      /*菜单选项与动作*/
      switch(item.getItemId()) 
      { 
        /*将图片宽度显示在TextView中*/
        case CONTEXT_ITEM1:
          String strOpt =
          getResources().getString(R.string.str_width)
          +"="+Integer.toString(intWidth);
          mTextView01.setText(strOpt);
          break;
        /*将图片高度显示在TextView中*/
        case CONTEXT_ITEM2:
          String strOpt2 =
          getResources().getString(R.string.str_height)
          +"="+Integer.toString(intHeight); 
          mTextView01.setText(strOpt2); 
          break;
        /*将图片宽高显示在TextView中*/
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

