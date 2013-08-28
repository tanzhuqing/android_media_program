package dfzy.threeCH;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
public class MySurfaceView extends GLSurfaceView {
	private final float TOUCH_SCALE_FACTOR=180.0f/320;//�Ƕ����ű���������Ļ��320������Ļ��һ�˻�����һ�ˣ�x���ϵĲ���Ӧ��Ӧ����Ҫ��ת�ĽǶ�
	private SceneRenderer myRenderer;//������Ⱦ��
	private float myPreviousY;//�ϴ���Ļ�ϵĴ���λ�õ�Y����
	private float myPreviousX;//�ϴ���Ļ�ϵĴ���λ�õ�X����
	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		myRenderer=new SceneRenderer();//����������Ⱦ��
		this.setRenderer(myRenderer);//������Ⱦ��
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);//������ȾģʽΪ������Ⱦ
	}
	@Override//�����¼��ص�����
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float y=event.getY();//��õ�ǰ�����Y����
		float x=event.getX();//��õ�ǰ�����X����
		switch(event.getAction()){
		case MotionEvent.ACTION_MOVE:
			float dy=y-myPreviousY;//����������y�᷽���ϵĴ�ֱ����
			float dx=x-myPreviousX;//�������x�᷽���ϵĴ�ֱ����
			myRenderer.tr.yAngle+=dx*TOUCH_SCALE_FACTOR;//������y����ת�Ƕ�
			myRenderer.tr.zAngle+=dy*TOUCH_SCALE_FACTOR;//������z����ת�Ƕ�
			requestRender();//��Ⱦ����
		}
		myPreviousY=y;
		myPreviousX=x;
		return true;
	}
	private class SceneRenderer  implements GLSurfaceView.Renderer{//�ڲ��࣬ʵ��Renderer�ӿڣ���Ⱦ��	
		threeCH tr=new threeCH();
		public SceneRenderer(){		
		}
		@Override
		public void onDrawFrame(GL10 gl) {
			// TODO Auto-generated method stub		
			gl.glEnable(GL10.GL_CULL_FACE);//����Ϊ�򿪱������	
			gl.glShadeModel(GL10.GL_SMOOTH);//������ɫģ��Ϊƽ����ɫ
			gl.glFrontFace(GL10.GL_CCW);//�����Զ������˳��Ϊ��ʱ��Ϊ����
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);//�����ɫ�������Ȼ���
			gl.glMatrixMode(GL10.GL_MODELVIEW);//���õ�ǰ����Ϊģʽ����
			gl.glLoadIdentity();//���õ�ǰ����Ϊ��λ����	
			gl.glTranslatef(0, 0, -2.0f);//
			tr.drawSelf(gl);//
		}
		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			// TODO Auto-generated method stub
			 gl.glViewport(0, 0, width, height);
			 gl.glMatrixMode(GL10.GL_PROJECTION);
			 gl.glLoadIdentity();
			 float ratio=(float)width/height;
			 gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
		}
		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			// TODO Auto-generated method stub
			gl.glDisable(GL10.GL_DITHER);//�رտ�����
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_FASTEST);//�����ض�Hint��Ŀ��ģʽ������Ϊ����ʹ�ÿ���ģʽ
			gl.glClearColor(0, 0, 0, 0);//������Ļ����ɫΪ��ɫ
			gl.glEnable(GL10.GL_DEPTH_TEST);//������ȼ��	
		}}}
