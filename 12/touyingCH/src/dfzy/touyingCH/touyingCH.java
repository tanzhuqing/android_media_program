package dfzy.touyingCH;
import java.nio.ByteBuffer;					//������ذ�
import java.nio.ByteOrder;					//������ذ�
import java.nio.IntBuffer;					//������ذ�
import javax.microedition.khronos.opengles.GL10;
public class touyingCH {
	private IntBuffer   mVertexBuffer;		//�����������ݻ���
    private IntBuffer   mColorBuffer;		//������ɫ���ݻ���
    private ByteBuffer  mIndexBuffer;		//���㹹���������ݻ���
    int vCount=0;							//ͼ�ζ�������
    int iCount=0;							//������������
    public touyingCH(int zOffset){
    	//�����������ݵĳ�ʼ��
        vCount=7;
        final int UNIT_SIZE=10000;
        int vertices[]=new int[]{
        	0*UNIT_SIZE,0*UNIT_SIZE,zOffset*UNIT_SIZE,
        	2*UNIT_SIZE,3*UNIT_SIZE,zOffset*UNIT_SIZE,
        	4*UNIT_SIZE,0*UNIT_SIZE,zOffset*UNIT_SIZE,
        	2*UNIT_SIZE,-3*UNIT_SIZE,zOffset*UNIT_SIZE,
        	-2*UNIT_SIZE,-3*UNIT_SIZE,zOffset*UNIT_SIZE,
        	-4*UNIT_SIZE,0*UNIT_SIZE,zOffset*UNIT_SIZE,
        	-2*UNIT_SIZE,3*UNIT_SIZE,zOffset*UNIT_SIZE
        };
        //���������������ݻ���
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asIntBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        //������ɫ���ݵĳ�ʼ��
        final int one = 65535;
        int colors[]=new int[]//������ɫֵ���飬ÿ������4��ɫ��ֵRGBA
        {
        		0,0,one,0,
        		0,one,0,0,
        		0,one,one,0,
        		
        		one,0,0,0,
        		one,0,one,0,
        		one,one,0,0,
        		one,one,one,0
        };
        //����������ɫ���ݻ���
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mColorBuffer = cbb.asIntBuffer();//ת��Ϊint�ͻ���
        mColorBuffer.put(colors);//�򻺳����з��붥����ɫ����
        mColorBuffer.position(0);//���û�������ʼλ��
        //�����ι����������ݳ�ʼ��
        iCount=18;
        byte indices[]=new byte[]{
        	0,2,1,
        	0,3,2,
        	0,4,3,
        	0,5,4,
        	0,6,5,
        	0,1,6
        };
        //���������ι����������ݻ���
        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
        mIndexBuffer.put(indices);//�򻺳����з��������ι�����������
        mIndexBuffer.position(0);//���û�������ʼλ��
    }
    public void drawSelf(GL10 gl){        
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//���ö�����������
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);//���ö�����ɫ����
        gl.glVertexPointer(//Ϊ����ָ��������������     
        		3,				//ÿ���������������Ϊ3  xyz 
        		GL10.GL_FIXED,	//��������ֵ������Ϊ GL_FIXED
        		0, 				//����������������֮��ļ��
        		mVertexBuffer	//������������
        );
        gl.glColorPointer(//Ϊ����ָ��������ɫ����     
        		4, 				//������ɫ����ɳɷ֣�����Ϊ4��RGBA
        		GL10.GL_FIXED, 	//������ɫֵ������Ϊ GL_FIXED
        		0, 				//����������ɫ����֮��ļ��
        		mColorBuffer	//������ɫ����
        );
        gl.glDrawElements(//����������ͼ��
        		GL10.GL_TRIANGLES, 		//�������η�ʽ���
        		iCount, 			 	//һ��icount/3�������Σ�iCount������
        		GL10.GL_UNSIGNED_BYTE, 	//����ֵ�ĳߴ�
        		mIndexBuffer			//����ֵ����
        ); 
    }}

