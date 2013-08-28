package dfzy.yan;

import dfzy.yan.R;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

public class yan extends Activity
{
  private TextView mTextView01;
  private TextView mTextView02;
  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    mTextView01 = (TextView) findViewById(R.id.myTextView01);
    mTextView01.setText("使用的是Drawable背景色文本。");
    
    Resources resources = getBaseContext().getResources();
    Drawable HippoDrawable = resources.getDrawable(R.drawable.white);
    mTextView01.setBackgroundDrawable(HippoDrawable);
    
    mTextView02 = (TextView) findViewById(R.id.myTextView02);
    mTextView02.setTextColor(Color.MAGENTA);
  }
}