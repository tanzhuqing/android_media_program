package dfzy.dingdianCH;
import dfzy.dingdianCH.R;

import android.app.Activity;															//������ذ�
import android.os.Bundle;																//������ذ�
import android.widget.CompoundButton;													//������ذ�
import android.widget.LinearLayout;														//������ذ�
import android.widget.ToggleButton;														//������ذ�
import android.widget.CompoundButton.OnCheckedChangeListener;							//������ذ�
public class MyActivity extends Activity {
    /** Called when the activity is first created. */
	private MySurfaceView mSurfaceView;													//����MySurfaceView����
    @Override
    public void onCreate(Bundle savedInstanceState) {									
        super.onCreate(savedInstanceState);												//�̳и��෽��
        setContentView(R.layout.main);													//���ò����ļ�
        mSurfaceView=new MySurfaceView(this);											//����MySurfaceView����
        mSurfaceView.requestFocus();													//��ȡ����
        mSurfaceView.setFocusableInTouchMode(true);										//����Ϊ�ɴ���
        LinearLayout ll=(LinearLayout)this.findViewById(R.id.main_liner);				//������Բ��ֵ�����
        ll.addView(mSurfaceView);//
        ToggleButton tb01=(ToggleButton)this.findViewById(R.id.ToggleButton01);			//��õ�һ�����ذ�ť������
        tb01.setOnCheckedChangeListener(new FirstListener());							//Ϊ���ذ�ťע�������
        ToggleButton tb02=(ToggleButton)this.findViewById(R.id.ToggleButton02);			//��õڶ������ذ�ť������
        tb02.setOnCheckedChangeListener(new SecondListener());//
        ToggleButton tb03=(ToggleButton)this.findViewById(R.id.ToggleButton03);			//��õ��������ذ�ť������
        tb03.setOnCheckedChangeListener(new ThirdListener());//
    }
    class FirstListener implements OnCheckedChangeListener{								//������һ����ť�ļ�����
		@Override
		public void onCheckedChanged(CompoundButton buttonView,							//��д����
				boolean isChecked) {
			// TODO Auto-generated method stub
			mSurfaceView.setBackFlag(!mSurfaceView.isBackFlag());						//ʵ�ֹ���
		}  	
    }
    class SecondListener implements OnCheckedChangeListener{							//�����ڶ�����ť�ļ�����
		@Override
		public void onCheckedChanged(CompoundButton buttonView,							//��д����
				boolean isChecked) {
			// TODO Auto-generated method stub
			mSurfaceView.setSmoothFlag(!mSurfaceView.isSmoothFlag());					//ʵ�ֹ���
		}
    }
	class ThirdListener implements OnCheckedChangeListener{								//������������ť�ļ�����
		@Override
		public void onCheckedChanged(CompoundButton buttonView,							//��д����
				boolean isChecked) {
			// TODO Auto-generated method stub
			mSurfaceView.setSelfCulling(!mSurfaceView.isSelfCulling());					//ʵ�ֹ���
		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();																//�̳и����onPause()����
		mSurfaceView.onPause();															//����onPause()����
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();																//�̳и����onResume()����
		mSurfaceView.onResume();														//����onResume()����
	}  
}