package dfzy.suofang;
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
class MySurfaceView extends GLSurfaceView {
    private SceneRenderer mRenderer;//������Ⱦ��
    public float change;
	public MySurfaceView(Context context) {
        super(context);
        mRenderer = new SceneRenderer();	//����������Ⱦ��
        setRenderer(mRenderer);				//������Ⱦ��		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ   
    }
	private class SceneRenderer implements GLSurfaceView.Renderer 
    {   
    	suofang ball=new suofang(3);
    	
    	public SceneRenderer()
    	{   		
           new Thread()
            {
          	  public void run()
          	  {
          		
          		  while(true)
          		  {
          			ball.scaleX+=change;
      				ball.scaleY+=change;
          			 if(ball.scaleX>1.5)
          			 {
          				change=-0.02f;
          			 }
          			 if(ball.scaleX<0.5)
          			 {
          				 change+=0.02f;
          			 }
          			try
                    {
                  	  Thread.sleep(50);//��Ϣ50ms���ػ�
                    }
                    catch(Exception e)
                    {
                  	  e.printStackTrace();
                    } 
          		  } 
          	  }
            }.start();
    	} 	
        public void onDrawFrame(GL10 gl) {            
        	  	
        	//�����ɫ����
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        	//���õ�ǰ����Ϊģʽ����
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            //���õ�ǰ����Ϊ��λ����
            gl.glLoadIdentity();  
            gl.glTranslatef(0, 0, -5);
            ball.drawSelf(gl);
        }

        public void onSurfaceChanged(GL10 gl, int width, int height) {
            //�����Ӵ���С��λ�� 
        	gl.glViewport(0, 0, width, height);
        	//���õ�ǰ����ΪͶӰ����
            gl.glMatrixMode(GL10.GL_PROJECTION);
            //���õ�ǰ����Ϊ��λ����
            gl.glLoadIdentity();
            gl.glShadeModel(GL10.GL_SMOOTH);     
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
}

