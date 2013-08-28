package dfzy.PaintCH;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class PaintCH extends View implements Runnable
{

	public final static String	TAG		= "example1";	// 
	/* ����Paint���� */
	private Paint				mPaint	= null;


	public PaintCH(Context context)
	{
		super(context);
		/* �������� */
		mPaint = new Paint();

		/* �����߳� */
		new Thread(this).start();
	}


	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		/* ����PaintΪ�޾�� */
		mPaint.setAntiAlias(true);

		/* ����Paint����ɫ */
		mPaint.setColor(Color.WHITE);
		mPaint.setColor(Color.BLUE);
		mPaint.setColor(Color.YELLOW);
		mPaint.setColor(Color.GREEN);
		/* ͬ����������ɫ */
		mPaint.setColor(Color.rgb(255, 0, 0));

		/* ��ȡ��ɫ */
		Color.red(0xcccccc);
		Color.green(0xcccccc);

		/* ����paint����ɫ��Alphaֵ(a,r,g,b) */
		mPaint.setARGB(255, 255, 0, 0);

		/* ����paint��Alphaֵ */
		mPaint.setAlpha(220);

		/* �����������Ϊ����һ��paint���� */
		// mPaint.set(new Paint());
		/* ��������ĳߴ� */
		mPaint.setTextSize(14);

		// ����paint�ķ��Ϊ�����ġ�.
		// ��ȻҲ��������Ϊ��ʵ�ġ�(Paint.Style.FILL)
		mPaint.setStyle(Paint.Style.STROKE);

		// ���á����ġ������Ŀ�ȡ�
		mPaint.setStrokeWidth(5);

		/* �õ�Paint��һЩ���� */
		Log.i(TAG, "paint����ɫ��" + mPaint.getColor());

		Log.i(TAG, "paint��Alpha��" + mPaint.getAlpha());

		Log.i(TAG, "paint�����Ŀ�ȣ�" + mPaint.getStrokeWidth());

		Log.i(TAG, "paint������ߴ磺" + mPaint.getTextSize());

		/* ����һ������ */
		// �϶���һ�����ĵľ���
		canvas.drawRect((320 - 80) / 2, 20, (320 - 80) / 2 + 80, 20 + 40, mPaint);

		/* ���÷��Ϊʵ�� */
		mPaint.setStyle(Paint.Style.FILL);

		mPaint.setColor(Color.GREEN);

		/* ������ɫʵ�ľ��� */
		canvas.drawRect(0, 20, 40, 20 + 40, mPaint);
	}


	// �����¼�
	public boolean onTouchEvent(MotionEvent event)
	{
		return true;
	}


	// ���������¼�
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return true;
	}


	// ���������¼�
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		return false;
	}


	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event)
	{
		return true;
	}


	public void run()
	{
		while (!Thread.currentThread().isInterrupted())
		{
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
			// ʹ��postInvalidate����ֱ�����߳��и��½���
			postInvalidate();
		}
	}
}
