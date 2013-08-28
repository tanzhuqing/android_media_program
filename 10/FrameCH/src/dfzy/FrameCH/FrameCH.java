package dfzy.FrameCH;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.View;

public class FrameCH extends View
{
	/* ����AnimationDrawable���� */
	private AnimationDrawable	frameAnimation	= null;
	Context						mContext		= null;
	
	/* ����һ��Drawable���� */
	Drawable				mBitAnimation				= null;
	public FrameCH(Context context)
	{
		super(context);
		
		mContext = context;
		
		/* ʵ����AnimationDrawable���� */
		frameAnimation = new AnimationDrawable();
		
		/* װ����Դ */
		//������һ��ѭ����װ�������������Ƶ���Դ
		//�硰a1.......15.png����ͼƬ
		//��������ô��ǳ���
		for (int i = 1; i <= 15; i++)
		{
			int id = getResources().getIdentifier("a" + i, "drawable", mContext.getPackageName());
			mBitAnimation = getResources().getDrawable(id);
			/* Ϊ�������һ֡ */
			//����mBitAnimation�Ǹ�֡��ͼƬ
			//����500�Ǹ�֡��ʾ��ʱ��,���������
			frameAnimation.addFrame(mBitAnimation, 500);
		}
		
		/* ���ò���ģʽ�Ƿ�ѭ��false��ʾѭ����true��ʾ��ѭ�� */
		frameAnimation.setOneShot( false );  
		
		/* ���ñ��ཫҪ��ʾ������� */
		this.setBackgroundDrawable(frameAnimation);
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
	}
	
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		switch ( keyCode )
		{
		case KeyEvent.KEYCODE_DPAD_UP:		
			/* ��ʼ���Ŷ��� */
			frameAnimation.start();
			break;
		}
		return true;
	}
}

