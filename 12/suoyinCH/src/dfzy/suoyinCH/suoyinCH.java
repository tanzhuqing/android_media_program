package dfzy.suoyinCH;
import java.nio.ByteBuffer;								//������ذ�
import java.nio.ByteOrder;								//������ذ�
import java.nio.IntBuffer;								//������ذ�
import javax.microedition.khronos.opengles.GL10;		//������ذ�
public class suoyinCH {
	private IntBuffer myVertexBuffer;			//�����������ݻ���
	private IntBuffer myColorBuffer;			//������ɫ���ݻ���
	private ByteBuffer myIndexBuffer;			//�����������ݻ���
	int vCount=0;								//��������
	int iCount=0;								//��������	
	float yAngle=0;								//��y����ת�ĽǶ�
	float zAngle=0;								//��z����ת�ĽǶ�
	public suoyinCH(){
		vCount=6;								//һ�������Σ�3������
		final int UNIT_SIZE=10000;				//���ű���
		int []vertices=new int[]{
				-8*UNIT_SIZE,10*UNIT_SIZE,0,
	        	-2*UNIT_SIZE,2*UNIT_SIZE,0,
	        	-8*UNIT_SIZE,2*UNIT_SIZE,0,
	        	8*UNIT_SIZE,2*UNIT_SIZE,0,
	        	8*UNIT_SIZE,10*UNIT_SIZE,0,
	        	2*UNIT_SIZE,10*UNIT_SIZE,0
	       };
		//���������������ݻ��棬���ڲ�ͬƽ̨�ֽ�˳��ͬ�����ݵ�Ԫ�����ֽڵģ�����������͵Ļ��棩��һ��Ҫ����ByteBufferת�����ؼ���ͨ��ByteOrder����nativeOrder()
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertices.length*4);//������ڴ��
		vbb.order(ByteOrder.nativeOrder());//���ñ���ƽ̨���ֽ�˳��
		myVertexBuffer=vbb.asIntBuffer();//ת��Ϊint�ͻ���
		myVertexBuffer.put(vertices);//�򻺳����з��붥����������
		myVertexBuffer.position(0);//���û���������ʼλ��
		final int one=65535;//֧��65535ɫɫ��ͨ��
		int []colors=new int[]{//������ɫֵ���飬ÿ������4��ɫ��ֵRGBA		
				one,one,one,0,
        		0,0,one,0,
        		0,0,one,0,
        		one,one,one,0,
        		one,0,0,0,
        		one,0,0,0 
		};
		ByteBuffer cbb=ByteBuffer.allocateDirect(colors.length*4);		//������ڴ��
		cbb.order(ByteOrder.nativeOrder());		//���ñ���ƽ̨���ֽ�˳��
		myColorBuffer=cbb.asIntBuffer();		//ת��Ϊint�ͻ���
		myColorBuffer.put(colors);				//�򻺳����з��붥����ɫ����
		myColorBuffer.position(0);				//���û���������ʼλ��
		//Ϊ�����ι����������ݳ�ʼ��
		iCount=6;
		byte []indices=new byte[]{
				0,1,2,
	        	3,4,5
            };
		//���������ι����������ݻ���
		myIndexBuffer=ByteBuffer.allocateDirect(indices.length);		//������ڴ��
		myIndexBuffer.put(indices);				//�򻺳����з��붥����������
		myIndexBuffer.position(0);				//���û���������ʼλ��
	}
	public void drawSelf(GL10 gl){
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//���ö�����������
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);//���ö�����ɫ����
		gl.glRotatef(yAngle,0,1,0);//����yAngle�ĽǶ�ֵ����y����תyAngle
		gl.glRotatef(zAngle,0,0,1);
		gl.glVertexPointer(			//Ϊ����ָ��������������
				3,					//ÿ���������������Ϊ3
				GL10.GL_FIXED,		//��������ֵ������ΪGL_FIXED,����
				0,					//����������������֮��ļ��
				myVertexBuffer		//������������
		);
		gl.glColorPointer(//Ϊ����ָ������ ��ɫ����
			4,
			GL10.GL_FIXED,
			0,
			myColorBuffer
		);
		gl.glDrawElements(//����ͼ��
			GL10.GL_TRIANGLES,				//���ģʽ���������������η�ʽ���
			iCount,							//
			GL10.GL_UNSIGNED_BYTE,			//��ʼ����
			myIndexBuffer					//��������
		);
	}
 }
