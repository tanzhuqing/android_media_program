package dfzy.testViewCH;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.view.View;

public class testViewCH extends View {
	private Bitmap  mbmpTest=null;
	private final Paint mPaint = new Paint();
	private final String mstrTitle="感受Android带给我们的新体验";
	public testViewCH(Context context, AttributeSet attrs, int defStyle)
	{
	     super(context, attrs, defStyle);
	     mPaint.setColor(Color.GREEN);
	}
	 public testViewCH(Context context, AttributeSet attrs) 
	 {
	     super(context, attrs);
	     mPaint.setColor(Color.GREEN);
	 }
	 
	public boolean initBitmap(int w,int h,int c)
	{
		mbmpTest = Bitmap.createBitmap(w,h, Config.ARGB_8888);
		Canvas canvas = new Canvas(mbmpTest);  
		canvas.drawColor(Color.WHITE);
		Paint p = new Paint();
		String familyName = "宋体";
		Typeface font = Typeface.create(familyName,Typeface.BOLD);
		p.setColor(Color.RED);
		p.setTypeface(font);
		p.setTextSize(22);  
		canvas.drawText(mstrTitle,0,100,p);
		return true;
	}

	@Override
    public void onDraw(Canvas canvas) 
	{
		 super.onDraw(canvas); 
		 /*if(mbmpTest!=null)
		 {
		     Rect rtSource = new Rect(0,0,320,240);
		     Rect rtDst = new Rect(0,0,320,240);
		     canvas.drawBitmap(mbmpTest, rtSource,rtDst, mPaint);
		 }*/
		 if(mbmpTest!=null)
		 {
		   Matrix matrix = new Matrix();
           //matrix.postScale(0.5f, 0.5f);
           matrix.setRotate(90,120,120);
           canvas.drawBitmap(mbmpTest, matrix, mPaint);
		 } 
    }
}
	
