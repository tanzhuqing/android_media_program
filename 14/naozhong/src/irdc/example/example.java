package irdc.example;

/* import相关class */
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
  /* 声明对象变量 */
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
    /* 载入main.xml Layout */
    setContentView(R.layout.main);

    /* 以下为只响一次的闹钟的设置 */         
    setTime1=(TextView) findViewById(R.id.setTime1);
    /* 只响一次的闹钟的设置Button */
    mButton1=(Button)findViewById(R.id.mButton1);
    mButton1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        /* 取得点击按钮时的时间作为TimePickerDialog的默认值 */
        c.setTimeInMillis(System.currentTimeMillis());
        int mHour=c.get(Calendar.HOUR_OF_DAY);
        int mMinute=c.get(Calendar.MINUTE);
        
        /* 跳出TimePickerDialog来设置时间 */
        new TimePickerDialog(example.this,
          new TimePickerDialog.OnTimeSetListener()
          {                
            public void onTimeSet(TimePicker view,int hourOfDay,
                                  int minute)
            {
              /* 取得设置后的时间，秒跟毫秒设为0 */
              c.setTimeInMillis(System.currentTimeMillis());
              c.set(Calendar.HOUR_OF_DAY,hourOfDay);
              c.set(Calendar.MINUTE,minute);
              c.set(Calendar.SECOND,0);
              c.set(Calendar.MILLISECOND,0);
              
              /* 指定闹钟设置时间到时要运行CallAlarm.class */
              Intent intent = new Intent(example.this, example_2.class);
              /* 创建PendingIntent */
              PendingIntent sender=PendingIntent.getBroadcast(
                                     example.this,0, intent, 0);
              /* AlarmManager.RTC_WAKEUP设置服务在系统休眠时同样会运行 
               * 以set()设置的PendingIntent只会运行一次
               * */
              AlarmManager am;
              am = (AlarmManager)getSystemService(ALARM_SERVICE);
              am.set(AlarmManager.RTC_WAKEUP,
                     c.getTimeInMillis(),
                     sender
                    );
              /* 更新显示的设置闹钟时间 */
              String tmpS=format(hourOfDay)+"："+format(minute);
              setTime1.setText(tmpS);
              /* 以Toast提示设置已完成 */
              Toast.makeText(example.this,"设置闹钟时间为"+tmpS,
                Toast.LENGTH_SHORT)
                .show();
            }          
          },mHour,mMinute,true).show();
      }
    });

    /* 只响一次的闹钟的删除Button */
    mButton2=(Button) findViewById(R.id.mButton2);
    mButton2.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        Intent intent = new Intent(example.this, example_2.class);
        PendingIntent sender=PendingIntent.getBroadcast(
                               example.this,0, intent, 0);
        /* 由AlarmManager中删除 */
        AlarmManager am;
        am =(AlarmManager)getSystemService(ALARM_SERVICE);
        am.cancel(sender);
        /* 以Toast提示已删除设置，并更新显示的闹钟时间 */
        Toast.makeText(example.this,"闹钟时间解除",
                       Toast.LENGTH_SHORT).show();
        setTime1.setText("目前无设置");
      }
    });

    /* 以下为重复响起的闹钟的设置 */         
    setTime2=(TextView) findViewById(R.id.setTime2);
    /* create重复响起的闹钟的设置画面 */  
    /* 引用timeset.xml为Layout */
    LayoutInflater factory = LayoutInflater.from(this);
    final View setView = factory.inflate(R.layout.timeset,null);
    final TimePicker tPicker=(TimePicker)setView
                               .findViewById(R.id.tPicker);
    tPicker.setIs24HourView(true);

    /* create重复响起闹钟的设置Dialog */   
    final AlertDialog di=new AlertDialog.Builder(example.this)
          .setIcon(R.drawable.clock)
          .setTitle("设置")
          .setView(setView)
          .setPositiveButton("确定",
            new DialogInterface.OnClickListener()
           {
             public void onClick(DialogInterface dialog, int which)
             {
               /* 取得设置的间隔秒数 */
               EditText ed=(EditText)setView.findViewById(R.id.mEdit);
               int times=Integer.parseInt(ed.getText().toString())
                          *1000;
               /* 取得设置的开始时间，秒及毫秒设为0 */
               c.setTimeInMillis(System.currentTimeMillis());
               c.set(Calendar.HOUR_OF_DAY,tPicker.getCurrentHour());
               c.set(Calendar.MINUTE,tPicker.getCurrentMinute());
               c.set(Calendar.SECOND,0);
               c.set(Calendar.MILLISECOND,0);

               /* 指定闹钟设置时间到时要运行CallAlarm.class */
               Intent intent = new Intent(example.this,
                                          example_2.class);
               PendingIntent sender = PendingIntent.getBroadcast(
                                        example.this,1, intent, 0);
               /* setRepeating()可让闹钟重复运行 */
               AlarmManager am;
               am = (AlarmManager)getSystemService(ALARM_SERVICE);
               am.setRepeating(AlarmManager.RTC_WAKEUP,
                         c.getTimeInMillis(),times,sender);
               /* 更新显示的设置闹钟时间 */    
               String tmpS=format(tPicker.getCurrentHour())+"："+
                           format(tPicker.getCurrentMinute());
               setTime2.setText("设置闹钟时间为"+tmpS+
                                "开始，重复间隔为"+times/1000+"秒");
               /* 以Toast提示设置已完成 */
               Toast.makeText(example.this,"设置闹钟时间为"+tmpS+
                              "开始，重复间隔为"+times/1000+"秒",
                              Toast.LENGTH_SHORT).show();
             }
           })
          .setNegativeButton("取消",
            new DialogInterface.OnClickListener()
           {
             public void onClick(DialogInterface dialog, int which)
             {
             }
           }).create();

    /* 重复响起的闹钟的设置Button */
    mButton3=(Button) findViewById(R.id.mButton3);
    mButton3.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        /* 取得点击按钮时的时间作为tPicker的默认值 */
        c.setTimeInMillis(System.currentTimeMillis());
        tPicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
        tPicker.setCurrentMinute(c.get(Calendar.MINUTE));
        /* 跳出设置画面di */
        di.show();
      }
    });

    /* 重复响起的闹钟的删除Button */
    mButton4=(Button) findViewById(R.id.mButton4);
    mButton4.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View v)
      {
        Intent intent = new Intent(example.this, example_2.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                                 example.this,1, intent, 0);
        /* 由AlarmManager中删除 */
        AlarmManager am;
        am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.cancel(sender);
        /* 以Toast提示已删除设置，并更新显示的闹钟时间 */
        Toast.makeText(example.this,"闹钟时间解除",
                       Toast.LENGTH_SHORT).show();
        setTime2.setText("目前无设置");
      }
    });
  }

  /* 日期时间显示两位数的方法 */
  private String format(int x)
  {
    String s=""+x;
    if(s.length()==1) s="0"+s;
    return s;
  }
}
