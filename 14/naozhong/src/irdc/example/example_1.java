package irdc.example;

/* import���class */
import irdc.example.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

/* ʵ����������Dialog��Activity */
public class example_1 extends Activity
{
  @Override
  protected void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    /* ���������徯ʾ  */
    new AlertDialog.Builder(example_1.this)
        .setIcon(R.drawable.clock)
        .setTitle("��������!!")
        .setMessage("�Ͽ��𴲰�!!!")
        .setPositiveButton("�ص���",
         new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface dialog, int whichButton)
          {
            /* �ر�Activity */
            example_1.this.finish();
          }
        })
        .show();
  } 
}
