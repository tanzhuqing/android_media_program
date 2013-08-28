package dfzy.touyingCH;
import android.opengl.GLSurfaceView;						//������ذ�
import android.view.MotionEvent;							//������ذ�
import javax.microedition.khronos.egl.EGLConfig;			//������ذ�
import javax.microedition.khronos.opengles.GL10;			//������ذ�
import android.content.Context;								//������ذ�
class MySurfaceView extends GLSurfaceView {
	private final float TOUCH_SCALE_FACTOR = 180.0f/320;	//�Ƕ����ű���
    private SceneRenderer mRenderer;						//������Ⱦ��
	public boolean isPerspective=false;						//ͶӰ��־λ
	private float mPreviousY;								//�ϴεĴ���λ��Y����
    public float xAngle=0;									//������x����ת�ĽǶ�  
	public MySurfaceView(Context context) {
        super(context);
        mRenderer = new SceneRenderer();					//����������Ⱦ��
        setRenderer(mRenderer);								//������Ⱦ��		
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������Ⱦ   
    }
	//�����¼��ص�����
    @Override 
    public boolean onTouchEvent(MotionEvent e) {
        float y = e.getY();
        switch (e.getAction()) {							//��ȡ����
        case MotionEvent.ACTION_MOVE:						//�ж��Ƿ��ǻ���
            float dy = y - mPreviousY;						//���㴥�ر�Yλ��
            xAngle+= dy * TOUCH_SCALE_FACTOR;				//������x����ת�Ƕ�
            requestRender();								//�ػ滭��
        }
        mPreviousY = y;										//��Ϊ��һ�δ����Y����
        return true;
    }
	private class SceneRenderer implements GLSurfaceView.Renderer { 
		touyingCH[] ha=new touyingCH[]{							//����������
			new touyingCH(0),
			new touyingCH(-2),
			new touyingCH(-4),
			new touyingCH(-6),
			new touyingCH(-8),
			new touyingCH(-10),
			new touyingCH(-12),
	    };
    	public SceneRenderer(){}						//��Ⱦ��������
    	@Override
        public void onDrawFrame(GL10 gl) {  
            gl.glMatrixMode(GL10.GL_PROJECTION);		//���õ�ǰ����ΪͶӰ����
            gl.glLoadIdentity(); 						//���õ�ǰ����Ϊ��λ����        
            float ratio = (float) 320/480;				//����͸��ͶӰ�ı���
            if(isPerspective){
                gl.glFrustumf(-ratio, ratio, -1, 1, 1f, 10);//���ô˷����������͸��ͶӰ����
            }
            else{
            	gl.glOrthof(-ratio, ratio, -1, 1, 1, 10);//���ô˷��������������ͶӰ����
            }
			gl.glEnable(GL10.GL_CULL_FACE);				//����Ϊ�򿪱������	
	        gl.glShadeModel(GL10.GL_SMOOTH);    		//������ɫģ��Ϊƽ����ɫ       	
        	gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);//�������
            gl.glMatrixMode(GL10.GL_MODELVIEW);			//���õ�ǰ����Ϊģʽ����
            gl.glLoadIdentity();    			 		//���õ�ǰ����Ϊ��λ����
            
            gl.glTranslatef(0, 0f, -1.4f); 				//��z����Զ����
            gl.glRotatef(xAngle, 1, 0, 0);				//��x����ת�ƶ��Ƕ�
            
            for(touyingCH th:ha){
            	th.drawSelf(gl);				//ѭ�����������������е�ÿ��������
            }
        }
    	@Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
        	gl.glViewport(0, 0, width, height); //�����Ӵ���С��λ��              
        }
    	@Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {    
        	gl.glDisable(GL10.GL_DITHER);								//�رտ����� 
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);//����Hintģʽ
            gl.glClearColor(0,0,0,0);									//������Ļ����ɫ��ɫ            
            gl.glEnable(GL10.GL_DEPTH_TEST);							//������Ȳ���
        }}}
