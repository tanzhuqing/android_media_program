package dfzy.onoffCH;
import dfzy.onoffCH.R;

import android.app.Activity;					//������ذ�
import android.os.Bundle;					//������ذ�
import android.widget.CompoundButton;					//������ذ�
import android.widget.LinearLayout;					//������ذ�
import android.widget.ToggleButton;					//������ذ�
import android.widget.CompoundButton.OnCheckedChangeListener;					//������ذ�
public class MyActivity extends Activity {
    /** Called when the activity is first created. */
	MySurfaceView msv;
	ToggleButton tb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        msv=new MySurfaceView(this);				//ʵ����MySurfaceView
        setContentView(R.layout.main);				//����Acitivity������
        msv.requestFocus();							//��ȡ����
        msv.setFocusableInTouchMode(true);			//����Ϊ�ɴ���
        LinearLayout lla=(LinearLayout)findViewById(R.id.lla);
        lla.addView(msv);							//��SurfaceView����LinearLayout��
     tb=(ToggleButton)findViewById(R.id.ToggleButton01);//��Ӽ�����
        tb.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				msv.openLightFlag=!msv.openLightFlag;
		}});}
	@Override
	protected void onPause() {					//����һ��Acitvity�ڵ��ŵ�ǰ��Activityʱ����
		super.onPause();
		msv.onPause();
	}
	@Override
	protected void onResume() {					//��Acitvity����û�����ʱ����
		super.onResume();
		msv.onResume();
	}
}