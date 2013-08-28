package dfzy.threeCH;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import javax.microedition.khronos.opengles.GL10;
public class threeCH {
	private IntBuffer myVertexBuffer;//�����������ݻ���
	private IntBuffer myColorBuffer;//������ɫ���ݻ���
	private ByteBuffer myIndexBuffer;//���㹹�����������ݻ���
	int vCount=0;//��������
	int iCount=0;//��������
	float yAngle=0;//��y����ת�ĽǶ�
	float zAngle=0;//��z����ת�ĽǶ�
	public threeCH(){
		vCount=3;//һ�������Σ�3������
		final int UNIT_SIZE=10000;//���ű���
		int []vertices=new int[]
	       {
				-8*UNIT_SIZE,6*UNIT_SIZE,0,
				-8*UNIT_SIZE,-6*UNIT_SIZE,0,
				8*UNIT_SIZE,-6*UNIT_SIZE,0
	       };
		//���������������ݻ��棬���ڲ�ͬƽ̨�ֽ�˳��ͬ�����ݵ�Ԫ�����ֽڵģ�����������͵Ļ��棩��һ��Ҫ����ByteBufferת�����ؼ���ͨ��ByteOrder����nativeOrder()
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);//һ�������ĸ��ֽڣ��������·�����ڴ��������һ��������ֽڻ���
		vbb.order(ByteOrder.nativeOrder());//��������ֽڻ�����ֽ�˳��Ϊ����ƽ̨���ֽ�˳��
		myVertexBuffer=vbb.asIntBuffer();//ת��Ϊint���͵Ļ���
		myVertexBuffer.put(vertices);//�򻺳����з��붥����������
		myVertexBuffer.position(0);//���û���������ʼλ��
		final int one=65535;//֧��65535ɫɫ��ͨ��
		int []colors=new int[]//������ɫֵ���飬ÿ������4��ɫ��ֵRGBA
		{
			one,one,one,0,
			one,one,one,0,
			one,one,one,0
		};
		ByteBuffer cbb=ByteBuffer.allocateDirect(colors.length*4);
		cbb.order(ByteOrder.nativeOrder());
		myColorBuffer=cbb.asIntBuffer();
		myColorBuffer.put(colors);
		myColorBuffer.position(0);
		//Ϊ�����ι����������ݳ�ʼ��
		iCount=3;
		byte []indices=new byte[]
            {
				0,1,2
            };
		//���������ι����������ݻ���
		myIndexBuffer=ByteBuffer.allocateDirect(indices.length);
		myIndexBuffer.put(indices);
		myIndexBuffer.position(0);
	}
	public void drawSelf(GL10 gl)//GL10��ʵ�ֽӿ�GL��һ�����ӿڣ�������һϵ�г����ͳ��󷽷�
	{
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//���ö�����������
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);//���ö�����ɫ����
		
		gl.glRotatef(yAngle,0,1,0);//����yAngle�ĽǶ�ֵ����y����תyAngle
		gl.glRotatef(zAngle,0,0,1);
		
		gl.glVertexPointer//Ϊ����ָ��������������
		(
				3,					//ÿ���������������Ϊ3
				GL10.GL_FIXED,		//��������ֵ������ΪGL_FIXED,����
				0,					//����������������֮��ļ��
				myVertexBuffer		//������������
		);
		gl.glColorPointer//Ϊ����ָ������ ��ɫ����
		(
			6,
			GL10.GL_FIXED,
			0,
			myColorBuffer
		);
		gl.glDrawElements//����ͼ��
		(
			GL10.GL_TRIANGLES,		//���ģʽ���������������η�ʽ���
			iCount,					//��������
			GL10.GL_UNSIGNED_BYTE,	//����ֵ������
			myIndexBuffer			//����ֵ����
		);
	}}
