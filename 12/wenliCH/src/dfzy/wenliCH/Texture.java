package dfzy.wenliCH;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Texture {

	private IntBuffer   mVertexBuffer;//�����������ݻ���
    private FloatBuffer   mTextureBuffer;//�����������ݻ���
    public float mAngleY;//��y����ת�Ƕ� 
    public float mAngleZ;//��z����ת�Ƕ� 
    int vCount=0;//��������     
    int textureId;
    public Texture(int textureId)
    {
    	this.textureId=textureId;
    	//�����������ݵĳ�ʼ��================begin============================
    	final int UNIT_SIZE=30000;    	
        vCount=3;//���������    
        int vertices[]=new int[]//����������������
        {
        	2*UNIT_SIZE,0,0,
        	-2*UNIT_SIZE,0,0,
        	0,4*UNIT_SIZE,0
        };
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asIntBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //�����������ݵĳ�ʼ��================end============================
        
        //�����������ݵĳ�ʼ��================begin============================
        float textureCoors[]=new float[]//��������S��T����ֵ����
	    {
        	0,1,
        	1,1,
        	0.5f,0
	    };        
        
        //���������������ݻ���
        //textureCoors.length��4����Ϊһ��float�������ĸ��ֽ�
        ByteBuffer cbb = ByteBuffer.allocateDirect(textureCoors.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTextureBuffer = cbb.asFloatBuffer();//ת��Ϊint�ͻ���
        mTextureBuffer.put(textureCoors);//�򻺳����з��붥����ɫ����
        mTextureBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //�����������ݵĳ�ʼ��================end============================
    }

    public void drawSelf(GL10 gl)
    {    	
    	gl.glRotatef(mAngleZ, 0, 0, 1);//��Z����ת    	
        gl.glRotatef(mAngleY, 0, 1, 0);//��Y����ת
        
        //��������==========begin=============================================
        //����ʹ�ö�������
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//Ϊ����ָ��������������
        gl.glVertexPointer
        (
        		3,				//ÿ���������������Ϊ3  xyz 
        		GL10.GL_FIXED,	//��������ֵ������Ϊ GL_FIXED
        		0, 				//����������������֮��ļ��
        		mVertexBuffer	//������������
        );
        //��������==========end===============================================
        
        //����===========begin================================================
        //��������
        gl.glEnable(GL10.GL_TEXTURE_2D);   
        //����ʹ����������
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        //Ϊ����ָ������uv��������
        gl.glTexCoordPointer
        (
        		2, 					//ÿ���������������������� S��T
        		GL10.GL_FLOAT, 		//��������
        		0, 					//����������������֮��ļ��
        		mTextureBuffer		//������������
        );
        //Ϊ���ʰ�ָ������ID����		
        gl.glBindTexture(GL10.GL_TEXTURE_2D,textureId);   
        //����===========end==================================================
        
        //����ͼ��
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 
        		0, 
        		vCount
        );
        gl.glDisable(GL10.GL_TEXTURE_2D);//�ر�����
    }

}
