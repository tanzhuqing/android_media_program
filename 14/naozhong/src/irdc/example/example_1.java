package irdc.example;

/* import相关class */
import irdc.example.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

/* 实际跳出闹铃Dialog的Activity */
public class example_1 extends Activity
{
  @Override
  protected void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    /* 跳出的闹铃警示  */
    new AlertDialog.Builder(example_1.this)
        .setIcon(R.drawable.clock)
        .setTitle("闹钟响了!!")
        .setMessage("赶快起床吧!!!")
        .setPositiveButton("关掉他",
         new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface dialog, int whichButton)
          {
            /* 关闭Activity */
            example_1.this.finish();
          }
        })
        .show();
  } 
}
