package xiaohang.zhimeng;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class Animation1 extends Activity implements View.OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animation_1);

		View loginButton = findViewById(R.id.login);
		loginButton.setOnClickListener(this);
	}

	public void onClick(View v) {
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		findViewById(R.id.pw).startAnimation(shake);
	}

}
