package dfzy.hunCH;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import dfzy.hunCH.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

public class MySurfaceView extends GLSurfaceView{

private SceneRenderer mRenderer;//������Ⱦ��	
	
	public MySurfaceView(Context context) {
        super(context);
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
    }

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {
		final int one=65535;
		int baseTextureId;//��ײ���εĲ�͸�����������ID
		int topTextureId;//����͸�����������Id
		ColorRect c1;//��ɫ����1
		ColorRect c2;//��ɫ����2
		TextureRect t1;//�������1
		TextureRect t2;//�������2
		
        public void onDrawFrame(GL10 gl) {            
        	//����ƽ����ɫ
            gl.glShadeModel(GL10.GL_SMOOTH);
            
        	//�����ɫ��������Ȼ���
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        	//���õ�ǰ����Ϊģʽ����
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //���õ�ǰ����Ϊ��λ����
            gl.glLoadIdentity();    
            
            //���Ƶײ��������
            gl.glPushMatrix();
            gl.glTranslatef(0, 0f, -2f);  
            t1.drawSelf(gl);
            gl.glPopMatrix();
            
            //�����ϲ��������
            gl.glPushMatrix();
            gl.glTranslatef(-0.7f, -0.3f, -1.9f);  
            t2.drawSelf(gl);
            gl.glPopMatrix();
                   
            //�����ϲ���ɫ��͸������
            gl.glPushMatrix();
            gl.glTranslatef(0.7f, 0.4f, -1.8f);            
            c1.drawSelf(gl);
            gl.glPopMatrix();
            
            //�����ϲ���ɫ��͸������
            gl.glPushMatrix();
            gl.glTranslatef(-0.6f, 0.6f, -1.8f); 
            c2.drawSelf(gl);
            gl.glPopMatrix();
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����Ӵ���С��λ�� 
        	gl.glViewport(0, 0, width, height);
        	//���õ�ǰ����ΪͶӰ����
            gl.glMatrixMode(GL10.GL_PROJECTION);
            //���õ�ǰ����Ϊ��λ����
            gl.glLoadIdentity();
            //����͸��ͶӰ�ı���
            float ratio = (float) width / height;
            //���ô˷����������͸��ͶӰ����
            gl.glFrustumf(-ratio, ratio, -1, 1, 1, 100); 
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //�رտ����� 
        	gl.glDisable(GL10.GL_DITHER);
        	//�����ض�Hint��Ŀ��ģʽ������Ϊ����Ϊʹ�ÿ���ģʽ
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //������Ļ����ɫ��ɫRGBA
            gl.glClearColor(0,0,0,0);            
            //������Ȳ���
            gl.glEnable(GL10.GL_DEPTH_TEST); 
            //�������
            gl.glEnable(GL10.GL_BLEND);
//            gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_DST_ALPHA);
            gl.glBlendFunc(GL10.GL_SRC_COLOR, GL10.GL_DST_ALPHA);
             
            //��ʼ������
            baseTextureId=initTexture(gl,R.drawable.base);
            topTextureId=initTexture(gl,R.drawable.top);
            
            //��������
            c1=new ColorRect(one,0,0,one*3/4);
            c2=new ColorRect(0,one,0,one/2);
            t1=new TextureRect(baseTextureId);
            t2=new TextureRect(topTextureId);
        }
    }
	
	//��ʼ������
	public int initTexture(GL10 gl,int drawableId)//textureId
	{
		//��������ID
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);    
		int currTextureId=textures[0];    
		gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_MAG_FILTER,GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,GL10.GL_CLAMP_TO_EDGE);
        
        InputStream is = this.getResources().openRawResource(drawableId);
        Bitmap bitmapTmp; 
        try 
        {
        	bitmapTmp = BitmapFactory.decodeStream(is);
        } 
        finally 
        {
            try 
            {
                is.close();
            } 
            catch(IOException e) 
            {
                e.printStackTrace();
            }
        }
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);
        bitmapTmp.recycle(); 
        
        return currTextureId;
	}

}
