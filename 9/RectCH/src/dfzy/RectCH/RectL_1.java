package dfzy.RectCH;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.view.View;

//ͨ��ShapeDrawable�����Ƽ���ͼ��
public class RectL_1 extends View
{
	/* ����ShapeDrawable���� */
	ShapeDrawable	mShapeDrawable	= null;


	public RectL_1(Context context)
	{
		super(context);
	}
	
	public void DrawShape(Canvas canvas)
	{
		/* ʵ����ShapeDrawable����˵���ǻ���һ������ */
		mShapeDrawable = new ShapeDrawable(new RectShape());
		
		//�õ�����paint������������ɫ
		mShapeDrawable.getPaint().setColor(Color.RED);
	
		Rect bounds = new Rect(5, 250, 55, 280);
		
		/* ����ͼ����ʾ������ */
		mShapeDrawable.setBounds(bounds);
		
		/* ����ͼ�� */
		mShapeDrawable.draw(canvas);
		/*=================================*/
		/* ʵ����ShapeDrawable����˵���ǻ���һ����Բ */
		mShapeDrawable = new ShapeDrawable(new OvalShape());
		
		//�õ�����paint������������ɫ
		mShapeDrawable.getPaint().setColor(Color.GREEN);
		
		/* ����ͼ����ʾ������ */
		mShapeDrawable.setBounds(70, 250, 150, 280);
		
		/* ����ͼ�� */
		mShapeDrawable.draw(canvas);
		
		Path path1 = new Path();
		/*���ö���εĵ�*/
		path1.moveTo(150+5, 80+80-50);
		path1.lineTo(150+45, 80+80-50);
		path1.lineTo(150+30, 80+120-50);
		path1.lineTo(150+20, 80+120-50);
		/* ʹ��Щ�㹹�ɷ�յĶ���� */
		path1.close();
		
		//PathShape�������������ֱ��ǿ�Ⱥ͸߶�
		mShapeDrawable = new ShapeDrawable(new PathShape(path1,150,150));
		
		//�õ�����paint������������ɫ
		mShapeDrawable.getPaint().setColor(Color.BLUE);
		
		/* ����ͼ����ʾ������ */
		mShapeDrawable.setBounds(100, 170, 200, 280);
		
		/* ����ͼ�� */
		mShapeDrawable.draw(canvas);
	}
}

