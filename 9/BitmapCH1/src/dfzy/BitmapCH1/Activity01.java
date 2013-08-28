package dfzy.BitmapCH1;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

public class Activity01 extends Activity
{
	private BitmapCH1 mGameView = null;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		mGameView = new BitmapCH1(this);
		
		setContentView(mGameView);
	}
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		return mGameView.onKeyUp(keyCode,event);
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if ( mGameView == null )
		{
			return false;
		}
		if ( keyCode ==  KeyEvent.KEYCODE_BACK)
		{
			this.finish();
			return true;
		}
		return false;
	}
}