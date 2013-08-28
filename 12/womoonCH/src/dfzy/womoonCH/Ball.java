package dfzy.womoonCH;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class Ball {

	private IntBuffer   mVertexBuffer;//�����������ݻ���
	private IntBuffer   mNormalBuffer;//���㷨�������ݻ���
    private FloatBuffer mTextureBuffer;//�����������ݻ���
    public float mAngleX;//��x����ת�Ƕ�
    public float mAngleY;//��y����ת�Ƕ� 
    public float mAngleZ;//��z����ת�Ƕ� 
    int vCount=0;//��������
    int texId;//����ID
    
    public Ball(int scale,int texId) 
    {
    	this.texId=texId;
    	final int UNIT_SIZE=10000;//��λ�ߴ�
    	final float angleSpan=11.25f;//������е�λ�зֵĽǶ�
    	
    	//��ȡ�з���ͼ����������
    	float[] texCoorArray= 
         generateTexCoor
    	 (
    			 (int)(360/angleSpan), //����ͼ�зֵ�����
    			 (int)(180/angleSpan)  //����ͼ�зֵ�����
    	);
        int tc=0;//�������������
        int ts=texCoorArray.length;//�������鳤��
    	
    	ArrayList<Integer> alVertix=new ArrayList<Integer>();//��Ŷ��������ArrayList
    	ArrayList<Float> alTexture=new ArrayList<Float>();//������������ArrayList
    	
        for(float vAngle=90;vAngle>-90;vAngle=vAngle-angleSpan)//��ֱ����angleSpan��һ��
        {
        	for(float hAngle=360;hAngle>0;hAngle=hAngle-angleSpan)//ˮƽ����angleSpan��һ��
        	{
        		//����������һ���ǶȺ�����Ӧ�Ĵ˵��������ϵ��ı��ζ�������
        		//��������������ı��ε�������
        		
        		double xozLength=scale*UNIT_SIZE*Math.cos(Math.toRadians(vAngle));
        		int x1=(int)(xozLength*Math.cos(Math.toRadians(hAngle)));
        		int z1=(int)(xozLength*Math.sin(Math.toRadians(hAngle)));
        		int y1=(int)(scale*UNIT_SIZE*Math.sin(Math.toRadians(vAngle)));
        		
        		xozLength=scale*UNIT_SIZE*Math.cos(Math.toRadians(vAngle-angleSpan));
        		int x2=(int)(xozLength*Math.cos(Math.toRadians(hAngle)));
        		int z2=(int)(xozLength*Math.sin(Math.toRadians(hAngle)));
        		int y2=(int)(scale*UNIT_SIZE*Math.sin(Math.toRadians(vAngle-angleSpan)));
        		
        		xozLength=scale*UNIT_SIZE*Math.cos(Math.toRadians(vAngle-angleSpan));
        		int x3=(int)(xozLength*Math.cos(Math.toRadians(hAngle-angleSpan)));
        		int z3=(int)(xozLength*Math.sin(Math.toRadians(hAngle-angleSpan)));
        		int y3=(int)(scale*UNIT_SIZE*Math.sin(Math.toRadians(vAngle-angleSpan)));
        		
        		xozLength=scale*UNIT_SIZE*Math.cos(Math.toRadians(vAngle));
        		int x4=(int)(xozLength*Math.cos(Math.toRadians(hAngle-angleSpan)));
        		int z4=(int)(xozLength*Math.sin(Math.toRadians(hAngle-angleSpan)));
        		int y4=(int)(scale*UNIT_SIZE*Math.sin(Math.toRadians(vAngle)));   
        		
        		//������һ������
        		alVertix.add(x1);alVertix.add(y1);alVertix.add(z1);
        		alVertix.add(x2);alVertix.add(y2);alVertix.add(z2);
        		alVertix.add(x4);alVertix.add(y4);alVertix.add(z4);        		
        		//�����ڶ�������
        		alVertix.add(x4);alVertix.add(y4);alVertix.add(z4);
        		alVertix.add(x2);alVertix.add(y2);alVertix.add(z2);
        		alVertix.add(x3);alVertix.add(y3);alVertix.add(z3); 
        		
        		//��һ������3�������6����������
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);        		
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);
        		//�ڶ�������3�������6����������
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);        		
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);
        		alTexture.add(texCoorArray[tc++%ts]);       		
        	}
        } 	
        
        
        
        vCount=alVertix.size()/3;//���������Ϊ����ֵ������1/3����Ϊһ��������3������
    	
        //��alVertix�е�����ֵת�浽һ��int������
        int vertices[]=new int[vCount*3];
    	for(int i=0;i<alVertix.size();i++)
    	{
    		vertices[i]=alVertix.get(i);
    	}
        
        //�������ƶ������ݻ���
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mVertexBuffer = vbb.asIntBuffer();//ת��Ϊint�ͻ���
        mVertexBuffer.put(vertices);//�򻺳����з��붥����������
        mVertexBuffer.position(0);//���û�������ʼλ��     
        
        //�������㷨�������ݻ���
        ByteBuffer nbb = ByteBuffer.allocateDirect(vertices.length*4);
        nbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mNormalBuffer = vbb.asIntBuffer();//ת��Ϊint�ͻ���
        mNormalBuffer.put(vertices);//�򻺳����з��붥�㷨��������
        mNormalBuffer.position(0);//���û�������ʼλ��
        
        //�����������껺��
        float textureCoors[]=new float[alTexture.size()];//��������ֵ����
        for(int i=0;i<alTexture.size();i++) 
        {
        	textureCoors[i]=alTexture.get(i);
        }
        
        ByteBuffer tbb = ByteBuffer.allocateDirect(textureCoors.length*4);
        tbb.order(ByteOrder.nativeOrder());//�����ֽ�˳��
        mTextureBuffer = tbb.asFloatBuffer();//ת��Ϊfloat�ͻ���
        mTextureBuffer.put(textureCoors);//�򻺳����з��붥����������
        mTextureBuffer.position(0);//���û�������ʼλ��
    }

    public void drawSelf(GL10 gl)
    {
    	gl.glRotatef(mAngleZ, 0, 0, 1);//��Z����ת
    	gl.glRotatef(mAngleX, 1, 0, 0);//��X����ת
        gl.glRotatef(mAngleY, 0, 1, 0);//��Y����ת
        
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
        
        //����ʹ�÷���������
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        //Ϊ����ָ�����㷨��������
        gl.glNormalPointer(GL10.GL_FIXED, 0, mNormalBuffer); 
		
        //��������
        gl.glEnable(GL10.GL_TEXTURE_2D);   
        //����ʹ������ST���껺��
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        //Ϊ����ָ������ST���껺��
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
        //�󶨵�ǰ����
        gl.glBindTexture(GL10.GL_TEXTURE_2D, texId);
        
        //����ͼ��
        gl.glDrawArrays
        (
        		GL10.GL_TRIANGLES, 		//�������η�ʽ���
        		0, 			 			//��ʼ����
        		vCount					//��������
        );
    }
    
    //�Զ��з����������������ķ���
    public float[] generateTexCoor(int bw,int bh)
    {
    	float[] result=new float[bw*bh*6*2]; 
    	float sizew=1.0f/bw;//����
    	float sizeh=1.0f/bh;//����
    	int c=0;
    	for(int i=0;i<bh;i++)
    	{
    		for(int j=0;j<bw;j++)
    		{
    			//ÿ����һ�����Σ������������ι��ɣ��������㣬12����������
    			float s=j*sizew;
    			float t=i*sizeh;
    			
    			result[c++]=s;
    			result[c++]=t;
    			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    			
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t+sizeh;    			
    		}
    	}
    	return result;
    }
}
