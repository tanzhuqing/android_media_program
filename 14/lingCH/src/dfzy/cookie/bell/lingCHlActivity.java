package dfzy.cookie.bell;

import dfzy.cookie.bell.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class lingCHlActivity extends Activity {
	//Variable
	private Button startButton;
	private Button endButton;
	private Button configButton;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //findViews
        startButton=(Button)findViewById(R.id.startButton);
        endButton=(Button)findViewById(R.id.endButton);
        configButton=(Button)findViewById(R.id.configButton);
        //setListeners
        startButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
//				Toast.makeText(DotaBellActivity.this, "start", Toast.LENGTH_SHORT).show();
				Intent serviceIntent=new Intent();
				serviceIntent.setClass(lingCHlActivity.this, lingCHService.class);
				startService(serviceIntent);
			}
		});
        endButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
//				Toast.makeText(DotaBellActivity.this, "end", Toast.LENGTH_SHORT).show();
				//Í£Ö¹·þÎñ
				Intent serviceIntent=new Intent();
				serviceIntent.setClass(lingCHlActivity.this, lingCHService.class);
				stopService(serviceIntent);
			}
		});
        configButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
//				Toast.makeText(DotaBellActivity.this, "config", Toast.LENGTH_SHORT).show();
				Intent preferenceIntent=new Intent();
				preferenceIntent.setClass(lingCHlActivity.this, lingCHConfigPreference.class);
				startActivity(preferenceIntent);
			}
		});
    }
	@Override
	protected void onDestroy() {
		super.onDestroy();
		android.os.Process.killProcess(android.os.Process.myPid()); 
	}
    
    
}