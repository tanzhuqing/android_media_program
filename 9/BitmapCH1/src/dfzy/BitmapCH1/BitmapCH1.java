package dfzy.BitmapCH1;

import dfzy.BitmapCH1.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class BitmapCH1 extends View implements Runnable
{
    int BACKWIDTH;

    int BACKHEIGHT;

    short[] buf2;

    short[] buf1;

    int[] Bitmap2;

    int[] Bitmap1;
    
	public BitmapCH1(Context context)
	{
		super(context);
		
		/* װ��ͼƬ */
    	Bitmap 		image = BitmapFactory.decodeResource(this.getResources(),R.drawable.qq);
    	BACKWIDTH = image.getWidth();
    	BACKHEIGHT = image.getHeight();
    	
        buf2 = new short[BACKWIDTH * BACKHEIGHT];
        buf1 = new short[BACKWIDTH * BACKHEIGHT];
        Bitmap2 = new int[BACKWIDTH * BACKHEIGHT];
        Bitmap1 = new int[BACKWIDTH * BACKHEIGHT];

        /* ����ͼƬ�����ص������� */
        image.getPixels(Bitmap1, 0, BACKWIDTH, 0, 0, BACKWIDTH, BACKHEIGHT);
		
		new Thread(this).start();
	}
	
	
    void DropStone(int x,// x����
				   int y,// y����
				   int stonesize,// ��Դ�뾶
				   int stoneweight)// ��Դ����
	{
		for (int posx = x - stonesize; posx < x + stonesize; posx++)
			for (int posy = y - stonesize; posy < y + stonesize; posy++)
				if ((posx - x) * (posx - x) + (posy - y) * (posy - y) < stonesize * stonesize)
					buf1[BACKWIDTH * posy + posx] = (short) -stoneweight;
	}
    
    
    void RippleSpread()
	{
		for (int i = BACKWIDTH; i < BACKWIDTH * BACKHEIGHT - BACKWIDTH; i++)
		{
			// ������ɢ
			buf2[i] = (short) (((buf1[i - 1] + buf1[i + 1] + buf1[i - BACKWIDTH] + buf1[i + BACKWIDTH]) >> 1) - buf2[i]);
			// ����˥��
			buf2[i] -= buf2[i] >> 5;
		}

		// �����������ݻ�����
		short[] ptmp = buf1;
		buf1 = buf2;
		buf2 = ptmp;
	}

    /* ��Ⱦ��ˮ��Ч�� */
	void render()
	{
		int xoff, yoff;
		int k = BACKWIDTH;
		for (int i = 1; i < BACKHEIGHT - 1; i++)
		{
			for (int j = 0; j < BACKWIDTH; j++)
			{
				// ����ƫ����
				xoff = buf1[k - 1] - buf1[k + 1];
				yoff = buf1[k - BACKWIDTH] - buf1[k + BACKWIDTH];

				// �ж������Ƿ��ڴ��ڷ�Χ��
				if ((i + yoff) < 0)
				{
					k++;
					continue;
				}
				if ((i + yoff) > BACKHEIGHT)
				{
					k++;
					continue;
				}
				if ((j + xoff) < 0)
				{
					k++;
					continue;
				}
				if ((j + xoff) > BACKWIDTH)
				{
					k++;
					continue;
				}

				// �����ƫ�����غ�ԭʼ���ص��ڴ��ַƫ����
				int pos1, pos2;
				pos1 = BACKWIDTH * (i + yoff) + (j + xoff);
				pos2 = BACKWIDTH * i + j;
				Bitmap2[pos2++] = Bitmap1[pos1++];
				k++;
			}
		}
	}
    
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		
		/* ���ƾ��������ͼƬЧ�� */
		canvas.drawBitmap(Bitmap2, 0, BACKWIDTH, 0, 0, BACKWIDTH, BACKHEIGHT, false, null);
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
		DropStone(BACKWIDTH/2, BACKHEIGHT/2, 10, 30);
		return false;
	}


	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event)
	{
		return true;
	}
	
	/**
	 * �̴߳���
	 */
	public void run()
	{
		while (!Thread.currentThread().isInterrupted())
		{
			try
			{
				Thread.sleep(50);
			}
			catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
	        RippleSpread();
	        render();
			//ʹ��postInvalidate����ֱ�����߳��и��½���
			postInvalidate();
		}
	}
}

