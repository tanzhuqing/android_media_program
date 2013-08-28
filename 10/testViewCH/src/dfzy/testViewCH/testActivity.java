package dfzy.testViewCH;

import dfzy.testViewCH.R;
import android.app.Activity;
import android.os.Bundle;

public class testActivity extends Activity {
	private testViewCH mTestview;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mTestview = (testViewCH) findViewById(R.id.testView);
        mTestview.initBitmap(320,240,0xcccccc);
    }
}