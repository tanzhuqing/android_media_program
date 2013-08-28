package dfzy.myActionAnimationCH;

import dfzy.myActionAnimationCH.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class myActionAnimationCH extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	private Button button_alpha;
	private Button button_scale;
	private Button button_translate;
	private Button button_rotate;
	private Animation myAnimation_Alpha;
	private Animation myAnimation_Scale;
	private Animation myAnimation_Translate;
	private Animation myAnimation_Rotate;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
			
		button_alpha = (Button) findViewById(R.id.button_Alpha);
		button_alpha.setOnClickListener(this);

		button_scale = (Button) findViewById(R.id.button_Scale);
		button_scale.setOnClickListener(this);

		button_translate = (Button) findViewById(R.id.button_Translate);
		button_translate.setOnClickListener(this);

		button_rotate = (Button) findViewById(R.id.button_Rotate);
		button_rotate.setOnClickListener(this);
	}
	public void onClick(View button) {
		// TODO Auto-generated method stub
		switch (button.getId()) {
		case R.id.button_Alpha: {
			myAnimation_Alpha = AnimationUtils.loadAnimation(this,R.anim.my_alpha_action);
			button_alpha.startAnimation(myAnimation_Alpha);
		}
			break;
		case R.id.button_Scale: {
			myAnimation_Scale= AnimationUtils.loadAnimation(this,R.anim.my_scale_action);
			button_scale.startAnimation(myAnimation_Scale);
		}
			break;
		case R.id.button_Translate: {
			myAnimation_Translate= AnimationUtils.loadAnimation(this,R.anim.my_translate_action);
			button_translate.startAnimation(myAnimation_Translate);
		}
			break;
		case R.id.button_Rotate: {
			myAnimation_Rotate= AnimationUtils.loadAnimation(this,R.anim.my_rotate_action);
			button_rotate.startAnimation(myAnimation_Rotate);
		}
			break;

		default:
			break;
		}
	}
}