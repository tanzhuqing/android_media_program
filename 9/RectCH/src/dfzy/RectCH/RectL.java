package dfzy.RectCH;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class RectL extends View implements Runnable
{
	/* ����Paint���� */
	private Paint mPaint = null;
	
	private RectL_1 mGameView2 = null;
	public RectL(Context context)
	{
		super(context);
		/* �������� */
		mPaint = new Paint();
		
		mGameView2 = new RectL_1(context);
		
		/* �����߳�  */
		new Thread(this).start();
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		/* ���û���Ϊ��ɫ���� */
		canvas.drawColor(Color.BLACK);
		/* ȡ����� */
		mPaint.setAntiAlias(true);
		
		mPaint.setStyle(Paint.Style.STROKE);
		
		{
			/* ������ζ��� */
			Rect rect1 = new Rect();
			/* ���þ��δ�С */
			rect1.left = 5;
			rect1.top = 5;
			rect1.bottom = 25;
			rect1.right = 45;
			
			mPaint.setColor(Color.BLUE);
			/* ���ƾ��� */
			canvas.drawRect(rect1, mPaint);
			
			mPaint.setColor(Color.RED);
			/* ���ƾ��� */
			canvas.drawRect(50, 5, 90, 25, mPaint);
			
			mPaint.setColor(Color.YELLOW);
			/* ����Բ��(Բ��x,Բ��y,�뾶r,p) */
			canvas.drawCircle(40, 70, 30, mPaint);
			
			/* ������Բ���� */
			RectF rectf1 = new RectF();
			/* ������Բ��С */
			rectf1.left = 80;
			rectf1.top = 30;
			rectf1.right = 120;
			rectf1.bottom = 70;
			
			mPaint.setColor(Color.LTGRAY);
			/* ������Բ */
			canvas.drawOval(rectf1, mPaint);
			
			/* ���ƶ���� */
			Path path1 = new Path();
			
			/*���ö���εĵ�*/
			path1.moveTo(150+5, 80-50);
			path1.lineTo(150+45, 80-50);
			path1.lineTo(150+30, 120-50);
			path1.lineTo(150+20, 120-50);
			/* ʹ��Щ�㹹�ɷ�յĶ���� */
			path1.close();
			
			mPaint.setColor(Color.GRAY);
			/* ������������ */
			canvas.drawPath(path1, mPaint);
			
			mPaint.setColor(Color.RED);
			mPaint.setStrokeWidth(3);
			/* ����ֱ�� */
			canvas.drawLine(5, 110, 315, 110, mPaint);
		}
		//
		//�������ʵ�ļ�����
		//
		mPaint.setStyle(Paint.Style.FILL);
		{
			/* ������ζ��� */
			Rect rect1 = new Rect();
			/* ���þ��δ�С */
			rect1.left = 5;
			rect1.top = 130+5;
			rect1.bottom = 130+25;
			rect1.right = 45;
			
			mPaint.setColor(Color.BLUE);
			/* ���ƾ��� */
			canvas.drawRect(rect1, mPaint);
			
			mPaint.setColor(Color.RED);
			/* ���ƾ��� */
			canvas.drawRect(50, 130+5, 90, 130+25, mPaint);
			
			mPaint.setColor(Color.YELLOW);
			/* ����Բ��(Բ��x,Բ��y,�뾶r,p) */
			canvas.drawCircle(40, 130+70, 30, mPaint);
			
			/* ������Բ���� */
			RectF rectf1 = new RectF();
			/* ������Բ��С */
			rectf1.left = 80;
			rectf1.top = 130+30;
			rectf1.right = 120;
			rectf1.bottom = 130+70;
			
			mPaint.setColor(Color.LTGRAY);
			/* ������Բ */
			canvas.drawOval(rectf1, mPaint);
			
			/* ���ƶ���� */
			Path path1 = new Path();
			
			/*���ö���εĵ�*/
			path1.moveTo(150+5, 130+80-50);
			path1.lineTo(150+45, 130+80-50);
			path1.lineTo(150+30, 130+120-50);
			path1.lineTo(150+20, 130+120-50);
			/* ʹ��Щ�㹹�ɷ�յĶ���� */
			path1.close();
			
			mPaint.setColor(Color.GRAY);
			/* ������������ */
			canvas.drawPath(path1, mPaint);
			
			mPaint.setColor(Color.RED);
			mPaint.setStrokeWidth(3);
			/* ����ֱ�� */
			canvas.drawLine(5, 130+110, 315, 130+110, mPaint);
		}
		
		/* ͨ��ShapeDrawable�����Ƽ���ͼ�� */
		mGameView2.DrawShape(canvas);
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
			//ʹ��postInvalidate����ֱ�����߳��и��½���
			postInvalidate();
		}
	}
}