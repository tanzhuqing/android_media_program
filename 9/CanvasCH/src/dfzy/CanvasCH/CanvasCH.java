package dfzy.CanvasCH;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class CanvasCH extends View implements Runnable
{
	/* ����Paint���� */
	private Paint mPaint	= null;

	public CanvasCH(Context context)
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
		
		/* ���û�������ɫ */
		canvas.drawColor(Color.BLACK);
		
		/* ����ȡ�����Ч�� */
		mPaint.setAntiAlias(true);
		
		/* ���òü����� */
		canvas.clipRect(10, 10, 280, 260);
		
		/* ���������� */
		canvas.save();
		/* ��ת���� */
		canvas.rotate(45.0f); 
		
		/* ������ɫ�����ƾ��� */
		mPaint.setColor(Color.RED);
		canvas.drawRect(new Rect(15,15,140,70), mPaint);
		
		/* ������������� */
		canvas.restore(); 
		
		/* ������ɫ��������һ������ */
		mPaint.setColor(Color.GREEN);
		canvas.drawRect(new Rect(150,75,260,120), mPaint);
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

