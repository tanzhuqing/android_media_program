package irdc.example;

/* import���class */
import irdc.example.R;

import java.util.Calendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class example extends Activity
{
  /* ����������� */
  TextView setTime1;
  TextView setTime2;
  Button mButton1;
  Button mButton2;
  Button mButton3;
  Button mButton4;
  Calendar c=Calendar.getInstance();

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    /* ����main.xml Layout */
    setContentView(R.layout.main);

    /* ����Ϊֻ��һ�ε����ӵ����� */         
    setTime1=(TextView) findViewById(R.id.setTime1);
    /* ֻ��һ�ε����ӵ�����Button */
    mButton1=(Button)findViewById(R.id.mButton1);
    mButton1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        /* ȡ�õ����ťʱ��ʱ����ΪTimePickerDialog��Ĭ��ֵ */
        c.setTimeInMillis(System.currentTimeMillis());
        int mHour=c.get(Calendar.HOUR_OF_DAY);
        int mMinute=c.get(Calendar.MINUTE);
        
        /* ����TimePickerDialog������ʱ�� */
        new TimePickerDialog(example.this,
          new TimePickerDialog.OnTimeSetListener()
          {                
            public void onTimeSet(TimePicker view,int hourOfDay,
                                  int minute)
            {
              /* ȡ�����ú��ʱ�䣬���������Ϊ0 */
              c.setTimeInMillis(System.currentTimeMillis());
              c.set(Calendar.HOUR_OF_DAY,hourOfDay);
              c.set(Calendar.MINUTE,minute);
              c.set(Calendar.SECOND,0);
              c.set(Calendar.MILLISECOND,0);
              
              /* ָ����������ʱ�䵽ʱҪ����CallAlarm.class */
              Intent intent = new Intent(example.this, example_2.class);
              /* ����PendingIntent */
              PendingIntent sender=PendingIntent.getBroadcast(
                                     example.this,0, intent, 0);
              /* AlarmManager.RTC_WAKEUP���÷�����ϵͳ����ʱͬ�������� 
               * ��set()���õ�PendingIntentֻ������һ��
               * */
              AlarmManager am;
              am = (AlarmManager)getSystemService(ALARM_SERVICE);
              am.set(AlarmManager.RTC_WAKEUP,
                     c.getTimeInMillis(),
                     sender
                    );
              /* ������ʾ����������ʱ�� */
              String tmpS=format(hourOfDay)+"��"+format(minute);
              setTime1.setText(tmpS);
              /* ��Toast��ʾ��������� */
              Toast.makeText(example.this,"��������ʱ��Ϊ"+tmpS,
                Toast.LENGTH_SHORT)
                .show();
            }          
          },mHour,mMinute,true).show();
      }
    });

    /* ֻ��һ�ε����ӵ�ɾ��Button */
    mButton2=(Button) findViewById(R.id.mButton2);
    mButton2.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        Intent intent = new Intent(example.this, example_2.class);
        PendingIntent sender=PendingIntent.getBroadcast(
                               example.this,0, intent, 0);
        /* ��AlarmManager��ɾ�� */
        AlarmManager am;
        am =(AlarmManager)getSystemService(ALARM_SERVICE);
        am.cancel(sender);
        /* ��Toast��ʾ��ɾ�����ã���������ʾ������ʱ�� */
        Toast.makeText(example.this,"����ʱ����",
                       Toast.LENGTH_SHORT).show();
        setTime1.setText("Ŀǰ������");
      }
    });

    /* ����Ϊ�ظ���������ӵ����� */         
    setTime2=(TextView) findViewById(R.id.setTime2);
    /* create�ظ���������ӵ����û��� */  
    /* ����timeset.xmlΪLayout */
    LayoutInflater factory = LayoutInflater.from(this);
    final View setView = factory.inflate(R.layout.timeset,null);
    final TimePicker tPicker=(TimePicker)setView
                               .findViewById(R.id.tPicker);
    tPicker.setIs24HourView(true);

    /* create�ظ��������ӵ�����Dialog */   
    final AlertDialog di=new AlertDialog.Builder(example.this)
          .setIcon(R.drawable.clock)
          .setTitle("����")
          .setView(setView)
          .setPositiveButton("ȷ��",
            new DialogInterface.OnClickListener()
           {
             public void onClick(DialogInterface dialog, int which)
             {
               /* ȡ�����õļ������ */
               EditText ed=(EditText)setView.findViewById(R.id.mEdit);
               int times=Integer.parseInt(ed.getText().toString())
                          *1000;
               /* ȡ�����õĿ�ʼʱ�䣬�뼰������Ϊ0 */
               c.setTimeInMillis(System.currentTimeMillis());
               c.set(Calendar.HOUR_OF_DAY,tPicker.getCurrentHour());
               c.set(Calendar.MINUTE,tPicker.getCurrentMinute());
               c.set(Calendar.SECOND,0);
               c.set(Calendar.MILLISECOND,0);

               /* ָ����������ʱ�䵽ʱҪ����CallAlarm.class */
               Intent intent = new Intent(example.this,
                                          example_2.class);
               PendingIntent sender = PendingIntent.getBroadcast(
                                        example.this,1, intent, 0);
               /* setRepeating()���������ظ����� */
               AlarmManager am;
               am = (AlarmManager)getSystemService(ALARM_SERVICE);
               am.setRepeating(AlarmManager.RTC_WAKEUP,
                         c.getTimeInMillis(),times,sender);
               /* ������ʾ����������ʱ�� */    
               String tmpS=format(tPicker.getCurrentHour())+"��"+
                           format(tPicker.getCurrentMinute());
               setTime2.setText("��������ʱ��Ϊ"+tmpS+
                                "��ʼ���ظ����Ϊ"+times/1000+"��");
               /* ��Toast��ʾ��������� */
               Toast.makeText(example.this,"��������ʱ��Ϊ"+tmpS+
                              "��ʼ���ظ����Ϊ"+times/1000+"��",
                              Toast.LENGTH_SHORT).show();
             }
           })
          .setNegativeButton("ȡ��",
            new DialogInterface.OnClickListener()
           {
             public void onClick(DialogInterface dialog, int which)
             {
             }
           }).create();

    /* �ظ���������ӵ�����Button */
    mButton3=(Button) findViewById(R.id.mButton3);
    mButton3.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        /* ȡ�õ����ťʱ��ʱ����ΪtPicker��Ĭ��ֵ */
        c.setTimeInMillis(System.currentTimeMillis());
        tPicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
        tPicker.setCurrentMinute(c.get(Calendar.MINUTE));
        /* �������û���di */
        di.show();
      }
    });

    /* �ظ���������ӵ�ɾ��Button */
    mButton4=(Button) findViewById(R.id.mButton4);
    mButton4.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        Intent intent = new Intent(example.this, example_2.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                                 example.this,1, intent, 0);
        /* ��AlarmManager��ɾ�� */
        AlarmManager am;
        am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.cancel(sender);
        /* ��Toast��ʾ��ɾ�����ã���������ʾ������ʱ�� */
        Toast.makeText(example.this,"����ʱ����",
                       Toast.LENGTH_SHORT).show();
        setTime2.setText("Ŀǰ������");
      }
    });
  }

  /* ����ʱ����ʾ��λ���ķ��� */
  private String format(int x)
  {
    String s=""+x;
    if(s.length()==1) s="0"+s;
    return s;
  }
}
