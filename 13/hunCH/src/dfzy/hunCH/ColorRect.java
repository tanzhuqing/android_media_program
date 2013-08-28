package dfzy.hunCH;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

public class ColorRect {

	private IntBuffer   mVertexBuffer;//�����������ݻ���
    private IntBuffer   mColorBuffer;//������ɫ���ݻ���
    int vCount=0;//��������

    
    public ColorRect(int r,int g,int b,int alpha)
    {
    	//�����������ݵĳ�ʼ��================begin============================
        vCount=6;
        final int UNIT_SIZE=40000;
        int vertices[]=new int[]
        {
        	-1*UNIT_SIZE,1*UNIT_SIZE,0,
        	-1*UNIT_SIZE,-1*UNIT_SIZE,0,
        	1*UNIT_SIZE,1*UNIT_SIZE,0,
        	
        	-1*UNIT_SIZE,-1*UNIT_SIZE,0,
        	1*UNIT_SIZE,-1*UNIT_SIZE,0,
        	1*UNIT_SIZE,1*UNIT_SIZE,0
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
        
        //������ɫ���ݵĳ�ʼ��================begin============================
        int colors[]=new int[]//������ɫֵ���飬ÿ������4��ɫ��ֵRGBA
        {
        		r,g,b,alpha,
        		r,g,b,alpha,
        		r,g,b,alpha,
        		
        		r,g,b,alpha,
        		r,g,b,alpha,
        		r,g,b,alpha,
        };

        
        //����������ɫ���ݻ���
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
        cbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mColorBuffer = cbb.asIntBuffer();//ת��Ϊint�ͻ���
        mColorBuffer.put(colors);//�򻺳����з��붥����ɫ����
        mColorBuffer.position(0);//���û�������ʼλ��
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //������ɫ���ݵĳ�ʼ��================end============================
    }

    public void drawSelf(GL10 gl)
    {        
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//���ö�����������
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);//���ö�����ɫ����
        
		//Ϊ����ָ��������������
        gl.glVertexPointer
        (
        		3,				//ÿ���������������Ϊ3  xyz 
        		GL10.GL_FIXED,	//��������ֵ������Ϊ GL_FIXED
        		0, 				//����������������֮��ļ��
        		mVertexBuffer	//������������
        );
		
        //Ϊ����ָ��������ɫ����
        gl.glColorPointer
        (
        		4, 				//������ɫ����ɳɷ֣�����Ϊ4��RGBA
        		GL10.GL_FIXED, 	//������ɫֵ������Ϊ GL_FIXED
        		0, 				//����������ɫ����֮��ļ��
        		mColorBuffer	//������ɫ����
        );
		
        //����ͼ��
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//�������η�ʽ���
        		0,
        		vCount
        );
        
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);//���ö�����ɫ����
    }
}
