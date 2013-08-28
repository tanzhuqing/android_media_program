package dfzy.womoonCH;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import dfzy.womoonCH.R;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.view.MotionEvent;

public class MySurfaceView extends GLSurfaceView{

	private final float TOUCH_SCALE_FACTOR = 180.0f/320;//�Ƕ����ű���
    private SceneRenderer mRenderer;//������Ⱦ��
    private float mPreviousY;//�ϴεĴ���λ��Y����
    private float mPreviousX;//�ϴεĴ���λ��Y����
	
	public int earthTextureId;//��������ID 
	public int moonTextureId;//��������ID
	
	public MySurfaceView(Context context) {
        super(context);
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
    }

    //�����¼��ص�����
    @Override public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        float x = e.getX();
        switch (e.getAction()) {
        case MotionEvent.ACTION_MOVE:
            float dy = y - mPreviousY;//���㴥�ر�Yλ��
            float dx = x - mPreviousX;//���㴥�ر�Yλ��
            mRenderer.earth.mAngleX += dy * TOUCH_SCALE_FACTOR;//������x����ת�Ƕ�
            mRenderer.earth.mAngleZ += dx * TOUCH_SCALE_FACTOR;//������z����ת�Ƕ�
            requestRender();//�ػ滭��
        }
        mPreviousY = y;//��¼���ر�λ��
        mPreviousX = x;//��¼���ر�λ��
        return true;
    }   

	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
		Ball earth;
		Ball moon;
    	Celestial celestialSmall;//С�����ǿհ���
    	Celestial celestialBig;//�������ǿհ���
    	
            public void onDrawFrame(GL10 gl) {  
        	//�����ɫ����
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        	//���õ�ǰ����Ϊģʽ����
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //���õ�ǰ����Ϊ��λ����
            gl.glLoadIdentity();     
            
            gl.glTranslatef(0, 0f, -3.6f);  
            
            gl.glEnable(GL10.GL_LIGHTING);//�������      
            gl.glPushMatrix();//�����任�����ֳ�
            gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 3.5f);
            earth.drawSelf(gl);//���Ƶ���
            gl.glTranslatef(0, 0f, 1.5f);
            gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 1.0f);
            moon.drawSelf(gl);//��������
            gl.glPopMatrix();//�ָ��任�����ֳ�           
            gl.glDisable(GL10.GL_LIGHTING);//���������      
            
            //�����ǿ�
            gl.glPushMatrix();//�����任�����ֳ�
            gl.glTranslatef(0, -8.0f, 0.0f);  
            celestialSmall.drawSelf(gl);
            celestialBig.drawSelf(gl);
            gl.glPopMatrix();//�ָ��任�����ֳ�
            
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
            gl.glFrustumf(-ratio*0.5f, ratio*0.5f, -0.5f, 0.5f, 1, 100);
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //�رտ����� 
        	gl.glDisable(GL10.GL_DITHER);
        	//�����ض�Hint��Ŀ��ģʽ������Ϊ����Ϊʹ�ÿ���ģʽ
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //������Ļ����ɫ��ɫRGBA
            gl.glClearColor(0,0,0,0);
            //������ɫģ��Ϊƽ����ɫ   
            gl.glShadeModel(GL10.GL_SMOOTH);
            //������Ȳ���
            gl.glEnable(GL10.GL_DEPTH_TEST);
            //����Ϊ�򿪱������
    		gl.glEnable(GL10.GL_CULL_FACE);
    		
            gl.glEnable(GL10.GL_LIGHTING);//�������            
            initSunLight(gl);//��ʼ��̫����Դ
            initMaterial(gl);//��ʼ������
            
            //��ʼ������
            earthTextureId=initTexture(gl,R.drawable.di); 
            moonTextureId=initTexture(gl,R.drawable.yue);
            
            earth=new Ball(6,earthTextureId);
            moon=new Ball(2,moonTextureId);
             
            //�����ǿ�
            celestialSmall=new Celestial(0,0,1,0,750);
            celestialBig=new Celestial(0,0,2,0,200);
            
           //����һ���߳��Զ���ת����������
            new Thread()
            {
          	  public void run()
          	  {
          		  while(true)
          		  {
          			mRenderer.earth.mAngleY+=2*TOUCH_SCALE_FACTOR;//����Y��ת��
          			mRenderer.moon.mAngleY+=2*TOUCH_SCALE_FACTOR;//����Y��ת��
                    requestRender();//�ػ滭��
                    try
                    {
                  	  Thread.sleep(50);//��Ϣ10ms���ػ�
                    }
                    catch(Exception e)
                    {
                  	  e.printStackTrace();
                    }        			  
          		  }
          	  }
            }.start();
            
            new Thread()
            {//��ʱת���ǿյ��߳�
           	 public void run()
           	 {
           		 while(true)
           		 {
           			celestialSmall.yAngle+=0.5;
           			if(celestialSmall.yAngle>=360)
           			{
           				celestialSmall.yAngle=0;
           			}
           			celestialBig.yAngle+=0.5;
          			if(celestialBig.yAngle>=360)
          			{
          				celestialBig.yAngle=0;
          			}
           			 try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
           		 }
           	 }
            }.start();
        }
    }
	
	//��ʼ��̫����Դ
	private void initSunLight(GL10 gl)
	{
        gl.glEnable(GL10.GL_LIGHT0);//��0�ŵ�  
        
        //����������
        float[] ambientParams={0.05f,0.05f,0.025f,1.0f};//����� RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams,0);            
        
        //ɢ�������
        float[] diffuseParams={1f,1f,0.5f,1.0f};//����� RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams,0); 
        
        //���������
        float[] specularParams={1f,1f,0.5f,1.0f};//����� RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams,0);  
        
        //�趨��Դ��λ��
    	float[] positionParamsGreen={-14.14f,8.28f,6f,0};//����0��ʾʹ�ö����
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParamsGreen,0); 
	}
	
	
	//��ʼ������
	private void initMaterial(GL10 gl)
	{//����Ϊ��ɫʱʲô��ɫ�Ĺ���������ͽ����ֳ�ʲô��ɫ
        //������Ϊ��ɫ����
        float ambientMaterial[] = {0.7f, 0.7f, 0.7f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambientMaterial,0);
        //ɢ���Ϊ��ɫ����
        float diffuseMaterial[] = {1.0f, 1.0f, 1.0f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuseMaterial,0);
        //�߹����Ϊ��ɫ
        float specularMaterial[] = {1f, 1f, 1f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specularMaterial,0);        
	}
	
	//��ʼ������
	public int initTexture(GL10 gl,int drawableId)
	{
		//��������ID
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);    
		int textureId=textures[0];    
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
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
        
        return textureId;
	}
}
