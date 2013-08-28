package dfzy.testDrawableCH;

import dfzy.testDrawableCH.R;
import android.app.Activity;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class testDrawableCH extends Activity {
	LinearLayout mLinearLayout;

	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    ImageView spaceshipImage = (ImageView) findViewById(R.id.spaceshipImage);
	    Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.hyperspace_jump);
	    spaceshipImage.startAnimation(hyperspaceJumpAnimation);
	}
}