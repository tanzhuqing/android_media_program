package dfzy.touyingCH;

import dfzy.touyingCH.MySurfaceView;
import dfzy.touyingCH.R;

import android.app.Activity;				//������ذ�
import android.os.Bundle;					//������ذ�
import android.widget.CompoundButton;		//������ذ�
import android.widget.LinearLayout;			//������ذ�
import android.widget.ToggleButton;			//������ذ�
import android.widget.CompoundButton.OnCheckedChangeListener;	//������ذ�
public class MyActivity extends Activity {
	private MySurfaceView mSurfaceView;			//����MySurfaceView����
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);       
        setContentView(R.layout.main);			//���ò���
        mSurfaceView = new MySurfaceView(this);	//����
        mSurfaceView.requestFocus();//��ȡ����	//MySurfaceView����
        mSurfaceView.setFocusableInTouchMode(true);//����Ϊ�ɴ���  
        LinearLayout ll=(LinearLayout)findViewById(R.id.main_liner); //��ò�������
        ll.addView(mSurfaceView);//�ڲ��������MySurfaceView����
        //�����Ƿ�򿪱�����õ�ToggleButton
        ToggleButton tb=(ToggleButton)this.findViewById(R.id.ToggleButton01);//��ð�ť����
        tb.setOnCheckedChangeListener(new MyListener());        //Ϊ��ť���ü�����
    }
    class MyListener implements OnCheckedChangeListener{
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
				mSurfaceView.isPerspective=!mSurfaceView.isPerspective;//������ͶӰ��͸��ͶӰ֮���л�
				mSurfaceView.requestRender();//���»���
		}
    }
    @Override
    protected void onResume() {	//
        super.onResume();		//
        mSurfaceView.onResume();//
    }
    @Override
    protected void onPause() {	//
        super.onPause();		//
        mSurfaceView.onPause();	//
    }    
}