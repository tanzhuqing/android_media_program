package com.GIFL;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.GIFL.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

public class GameView extends View implements Runnable
{
	Context		mContext	= null;

	/* ����GifFrame���� */
	GifFrame	mGifFrame	= null;

	public GameView(Context context)
	{
		super(context);
		
		mContext = context;
		/* ����GIF���� */
		mGifFrame=GifFrame.CreateGifImage(fileConnect(this.getResources().openRawResource(R.drawable.gif1)));
		/* �����߳� */
		new Thread(this).start();
	}
	
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		/* ��һ֡ */
		mGifFrame.nextFrame();
		/* �õ���ǰ֡��ͼƬ */
		Bitmap b=mGifFrame.getImage();
		
		/* ���Ƶ�ǰ֡��ͼƬ */
		if(b!=null)
			canvas.drawBitmap(b,10,10,null);
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
	
	/* ��ȡ�ļ� */
	public byte[] fileConnect(InputStream is)
	{
		try
		{					    
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int ch = 0;
			while( (ch = is.read()) != -1) 
			{
				baos.write(ch);
			}			      
			byte[] datas = baos.toByteArray();
			baos.close(); 
			baos = null;
			is.close();
			is = null;
			return datas;
		}
		catch(Exception e)
		{
			return null;
		}
	}
}

