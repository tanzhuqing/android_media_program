package dfzy.dingweiCH;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class dingweiCH {
	private IntBuffer   mVertexBuffer;//�����������ݻ���
	private IntBuffer   mNormalBuffer;//���㷨�������ݻ���
    private ByteBuffer  mIndexBuffer;//���㹹���������ݻ���
    public float mAngleX;//��x����ת�Ƕ�
    public float mAngleY;//��y����ת�Ƕ� 
    public float mAngleZ;//��z����ת�Ƕ� 
    int vCount=0;
    int iCount=0;
    
    public dingweiCH(int scale)
    {
    	//�����������ݵĳ�ʼ��================begin============================
    	final int UNIT_SIZE=10000;
    	ArrayList<Integer> alVertix=new ArrayList<Integer>();//��Ŷ��������ArrayList
    	final int angleSpan=18;//������е�λ�зֵĽǶ�
        for(int vAngle=-90;vAngle<=90;vAngle=vAngle+angleSpan)//��ֱ����angleSpan��һ��
        {
        	for(int hAngle=0;hAngle<360;hAngle=hAngle+angleSpan)//ˮƽ����angleSpan��һ��
        	{//����������һ���ǶȺ�����Ӧ�Ĵ˵��������ϵ�����
        		double xozLength=scale*UNIT_SIZE*Math.cos(Math.toRadians(vAngle));
        		int x=(int)(xozLength*Math.cos(Math.toRadians(hAngle)));
        		int z=(int)(xozLength*Math.sin(Math.toRadians(hAngle)));
        		int y=(int)(scale*UNIT_SIZE*Math.sin(Math.toRadians(vAngle)));
        		//�����������XYZ��������Ŷ��������ArrayList
        		alVertix.add(x);alVertix.add(y);alVertix.add(z);
        	}
        } 	
        vCount=alVertix.size()/3;//���������Ϊ����ֵ������1/3����Ϊһ��������3������
    	
        //��alVertix�е�����ֵת�浽һ��int������
        int vertices[]=new int[vCount*3];
    	for(int i=0;i<alVertix.size();i++)
    	{
    		vertices[i]=alVertix.get(i);
    	}
		
        //���������������ݻ���
        //vertices.length*4����Ϊһ�������ĸ��ֽ�
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asIntBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��     
        
        
        //�������㷨�������ݻ���
        //vertices.length*4����Ϊһ��float�ĸ��ֽ�
        ByteBuffer nbb = ByteBuffer.allocateDirect(vertices.length*4);
        nbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mNormalBuffer = vbb.asIntBuffer();//ת��Ϊint�ͻ���
        mNormalBuffer.put(vertices);//�򻺳����з��붥����������
        mNormalBuffer.position(0);//���û�������ʼλ��
        
        
        //�ر���ʾ�����ڲ�ͬƽ̨�ֽ�˳��ͬ���ݵ�Ԫ�����ֽڵ�һ��Ҫ����ByteBuffer
        //ת�����ؼ���Ҫͨ��ByteOrder����nativeOrder()�������п��ܻ������
        //�����������ݵĳ�ʼ��================end============================
        
               
        //�����ι����������ݳ�ʼ��==========begin==========================
        ArrayList<Integer> alIndex=new ArrayList<Integer>();
        int row=(180/angleSpan)+1;//�����зֵ�����
        int col=360/angleSpan;//�����зֵ�����
        for(int i=0;i<row;i++)//��ÿһ��ѭ��
        {
        	if(i>0&&i<row-1)
        	{//�м���
        		for(int j=-1;j<col;j++)
				{//�м��е��������ڵ�����һ�еĶ�Ӧ�㹹��������
					int k=i*col+j;
					alIndex.add(k+col);
					alIndex.add(k+1);
					alIndex.add(k);		
				}
        		for(int j=0;j<col+1;j++)
				{//�м��е��������ڵ�����һ�еĶ�Ӧ�㹹��������				
					int k=i*col+j;
					alIndex.add(k-col);
					alIndex.add(k-1);
					alIndex.add(k);	
				}
        	}
        }
        iCount=alIndex.size();
        byte indices[]=new byte[alIndex.size()];
        for(int i=0;i<alIndex.size();i++)
        {
        	indices[i]=alIndex.get(i).byteValue();
        } 
        //���������ι����������ݻ���
        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
        mIndexBuffer.put(indices);//�򻺳����з��������ι�����������
        mIndexBuffer.position(0);//���û�������ʼλ��
      //�����ι����������ݳ�ʼ��==========end==============================
    }

    public void drawSelf(GL10 gl)
    {
    	gl.glRotatef(mAngleZ, 0, 0, 1);//��Z����ת
    	gl.glRotatef(mAngleX, 1, 0, 0);//��X����ת
        gl.glRotatef(mAngleY, 0, 1, 0);//��Y����ת
        
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        
		//Ϊ����ָ��������������
        gl.glVertexPointer
        (
        		3,				//ÿ���������������Ϊ3  xyz 
        		GL10.GL_FIXED,	//��������ֵ������Ϊ GL_FIXED
        		0, 				//����������������֮��ļ��
        		mVertexBuffer	//������������
        );
        
        //Ϊ����ָ�����㷨��������
        gl.glNormalPointer(GL10.GL_FIXED, 0, mNormalBuffer);
		
        //����ͼ��
        gl.glDrawElements
        (
        		GL10.GL_TRIANGLES, 		//�������η�ʽ���
        		iCount, 			 	//һ��icount/3�������Σ�iCount������
        		GL10.GL_UNSIGNED_BYTE, 	//����ֵ�ĳߴ�
        		mIndexBuffer			//����ֵ����
        ); 
    }

}
