package dfzy.TweenCH;

import dfzy.TweenCH.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class TweenCH extends View
{
	/* ����Alpha���� */
	private Animation	mAnimationAlpha		= null;
	
	/* ����Scale���� */
	private Animation	mAnimationScale		= null;
	
	/* ����Translate���� */
	private Animation	mAnimationTranslate	= null;
	
	/* ����Rotate���� */
	private Animation	mAnimationRotate	= null;
	
	/* ����Bitmap���� */
	Bitmap				mBitQQ				= null;
	
	public TweenCH(Context context)
	{
		super(context);
		
		/* װ����Դ */
		mBitQQ = ((BitmapDrawable) getResources().getDrawable(R.drawable.qq)).getBitmap();
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		/* ����ͼƬ */
		canvas.drawBitmap(mBitQQ, 0, 0, null);
	}

	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		switch ( keyCode )
		{
		case KeyEvent.KEYCODE_DPAD_UP:
			/* ����Alpha���� */
			mAnimationAlpha = new AlphaAnimation(0.1f, 1.0f);
			/* ���ö�����ʱ�� */
			mAnimationAlpha.setDuration(3000);
			/* ��ʼ���Ŷ��� */
			this.startAnimation(mAnimationAlpha);
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			/* ����Scale���� */
			mAnimationScale =new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
												Animation.RELATIVE_TO_SELF, 0.5f, 
												Animation.RELATIVE_TO_SELF, 0.5f);
			/* ���ö�����ʱ�� */
			mAnimationScale.setDuration(500);
			/* ��ʼ���Ŷ��� */
			this.startAnimation(mAnimationScale);
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			/* ����Translate���� */
			mAnimationTranslate = new TranslateAnimation(10, 100,10, 100);
			/* ���ö�����ʱ�� */
			mAnimationTranslate.setDuration(1000);
			/* ��ʼ���Ŷ��� */
			this.startAnimation(mAnimationTranslate);
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			/* ����Rotate���� */
			mAnimationRotate=new RotateAnimation(0.0f, +360.0f,
												 Animation.RELATIVE_TO_SELF,0.5f,
												 Animation.RELATIVE_TO_SELF, 0.5f);
			/* ���ö�����ʱ�� */
			mAnimationRotate.setDuration(1000);
			/* ��ʼ���Ŷ��� */
			this.startAnimation(mAnimationRotate);
			break;
		}
		return true;
	}
}

