package dfzy.threeCH;
import dfzy.threeCH.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
public class MyActivity extends Activity {
    /** Called when the activity is first created. */
	private MySurfaceView mSurfaceView;//����MySurfaceView����
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);     
        mSurfaceView=new MySurfaceView(this);//����MySurfaceView����
        mSurfaceView.requestFocus();//��ȡ����
        mSurfaceView.setFocusableInTouchMode(true);//����Ϊ�ɴ���
        LinearLayout ll=(LinearLayout)this.findViewById(R.id.main_liner);//������Բ��ֵ�����
        ll.addView(mSurfaceView);
    }
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mSurfaceView.onPause();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mSurfaceView.onResume();
	}  
}