package dfzy.dingweiCH;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MySurfaceView extends GLSurfaceView{

    private final float TOUCH_SCALE_FACTOR = 180.0f/320;//�Ƕ����ű���
    private SceneRenderer mRenderer;//������Ⱦ��
    private float mPreviousY;//�ϴεĴ���λ��Y����
    private float mPreviousX;//�ϴεĴ���λ��Y����
    public float light0PositionX=0;//0�ŵ�x����λ��
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
            mRenderer.ball.mAngleX += dy * TOUCH_SCALE_FACTOR;//������x����ת�Ƕ�
            mRenderer.ball.mAngleZ += dx * TOUCH_SCALE_FACTOR;//������z����ת�Ƕ�
            requestRender();//�ػ滭��
        }
        mPreviousY = y;//��¼���ر�λ��
        mPreviousX = x;//��¼���ر�λ��
        return true;
    }
	private class SceneRenderer implements GLSurfaceView.Renderer {   
    	dingweiCH ball=new dingweiCH(3);
    	public SceneRenderer(){
    	}
        public void onDrawFrame(GL10 gl){   
        	gl.glShadeModel(GL10.GL_SMOOTH);
        	gl.glEnable(GL10.GL_LIGHTING);//�������  
        	initMaterialWhite(gl);//��ʼ������Ϊ��ɫ
        	gl.glDisable(GL10.GL_LIGHT0);	//ÿ�λ���ǰ��ȡ���ѿ����ĵƹ�Ч��
        	initLight0(gl);//��ʼ��0�ŵ�
        	float[] positionParams0={0,0,light0PositionX,1};//����1��ʾ�Ƕ�λ�⣬��Ϊ0�ŵ�λ�ò�����
            gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParams0,0); //0�ŵ�λ��
        	//�����ɫ����
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        	//���õ�ǰ����Ϊģʽ����
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //���õ�ǰ����Ϊ��λ����
            gl.glLoadIdentity();     
            
            gl.glTranslatef(-1, 0f, -1.8f);  	//ƽ��
            ball.drawSelf(gl);//����
            gl.glLoadIdentity();//�ָ�����
            gl.glTranslatef(1, 0f, -1.8f);//ƽ��
            ball.drawSelf(gl);//����
            
            gl.glLoadIdentity();//�ָ�����
            gl.glTranslatef(0, 0f, -2.8f);//ƽ��
            ball.drawSelf(gl);//����
       
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
            gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
        }
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            //�رտ����� 
        	gl.glDisable(GL10.GL_DITHER);
        	//�����ض�Hint��Ŀ��ģʽ������Ϊ����Ϊʹ�ÿ���ģʽ
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);
            //������Ļ����ɫ��ɫRGBA
            gl.glClearColor(0,0,0,0);
            //������ɫģ��Ϊƽ����ɫ   
            gl.glShadeModel(GL10.GL_SMOOTH);//GL10.GL_SMOOTH  GL10.GL_FLAT
            //������Ȳ���
            gl.glEnable(GL10.GL_DEPTH_TEST);
        }
    }
	private void initLight0(GL10 gl)
	{
        gl.glEnable(GL10.GL_LIGHT0);//��0�ŵ�  ,��ɫ
        //����������
        float[] ambientParams={0.2f,0.03f,0.03f,1.0f};//����� RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams,0);            
        //ɢ�������
        float[] diffuseParams={0.5f,0.1f,0.1f,1.0f};//����� RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams,0); 
        //���������
        float[] specularParams={1.0f,0.1f,0.1f,1.0f};//����� RGBA
        gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams,0);     
	}
	private void initMaterialWhite(GL10 gl)
	{//����Ϊ��ɫʱʲô��ɫ�Ĺ���������ͽ����ֳ�ʲô��ɫ
        //������Ϊ��ɫ����
        float ambientMaterial[] = {0.4f, 0.4f, 0.4f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, ambientMaterial,0);
        //ɢ���Ϊ��ɫ����
        float diffuseMaterial[] = {0.8f, 0.8f, 0.8f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, diffuseMaterial,0);
        //�߹����Ϊ��ɫ
        float specularMaterial[] = {1.0f, 1.0f, 1.0f, 1.0f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, specularMaterial,0);
        //�߹ⷴ������,��Խ���������ԽСԽ��
        float shininessMaterial[] = {1.5f};
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, shininessMaterial,0);
	}
}