package dfzy.yuanhuan;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class yuanhuan
{
	private FloatBuffer myVertex;//���㻺��
	private FloatBuffer myTexture;//������
	private FloatBuffer myNormalBuffer;//����������
	
	int vcount;
	int textureid;
	
	float ring_Span;
	float circle_Span;
	
	float ring_Radius;
	float circle_Radius;
	
	public float mAngleX;
	public float mAngleY;
	public float mAngleZ;
	
	public yuanhuan(float ring_Span,float circle_Span,float ring_Radius,float circle_Radius,int textureid)
	{     
		//ring_Span��ʾ��ÿһ�ݶ��ٶȣ�circle_Span��ʾԲ�ػ�ÿһ�ݶ��ٶ�;ring_Radius��ʾ���뾶��circle_RadiusԲ����뾶��
		this.ring_Span=ring_Span;
		this.circle_Span=circle_Span;
		this.circle_Radius=circle_Radius;
		this.ring_Radius=ring_Radius;
		this.textureid=textureid; 
		
		ArrayList<Float> val=new ArrayList<Float>();
		ArrayList<Float> ial=new ArrayList<Float>();//����������б�
		
		for(float circle_Degree=50f;circle_Degree<130f;circle_Degree+=circle_Span)
		{
			for(float ring_Degree=-90f;ring_Degree<0f;ring_Degree+=ring_Span)
			{
				float x1=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree)))*Math.cos(Math.toRadians(ring_Degree)));
				float y1=(float) (circle_Radius*Math.sin(Math.toRadians(circle_Degree)));
				float z1=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree)))*Math.sin(Math.toRadians(ring_Degree)));
				
				float x2=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree)))*Math.cos(Math.toRadians(ring_Degree+ring_Span)));
				float y2=(float) (circle_Radius*Math.sin(Math.toRadians(circle_Degree)));
				float z2=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree)))*Math.sin(Math.toRadians(ring_Degree+ring_Span)));
				
				float x3=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree+circle_Span)))*Math.cos(Math.toRadians(ring_Degree+ring_Span)));
				float y3=(float) (circle_Radius*Math.sin(Math.toRadians(circle_Degree+circle_Span)));
				float z3=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree+circle_Span)))*Math.sin(Math.toRadians(ring_Degree+ring_Span)));
				
				float x4=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree+circle_Span)))*Math.cos(Math.toRadians(ring_Degree)));
				float y4=(float) (circle_Radius*Math.sin(Math.toRadians(circle_Degree+circle_Span)));
				float z4=(float) ((ring_Radius+circle_Radius*Math.cos(Math.toRadians(circle_Degree+circle_Span)))*Math.sin(Math.toRadians(ring_Degree)));
				
				val.add(x1);val.add(y1);val.add(z1);
				val.add(x4);val.add(y4);val.add(z4);
				val.add(x2);val.add(y2);val.add(z2);
							
				val.add(x2);val.add(y2);val.add(z2);
				val.add(x4);val.add(y4);val.add(z4);
				val.add(x3);val.add(y3);val.add(z3); 
				
				//��������Բ�������ĵ���ɵ�Բ���ϵĵ������
				
				float a1=(float) (x1-(ring_Radius*Math.cos(Math.toRadians(ring_Degree))));
				float b1=y1-0;
				float c1=(float) (z1-(ring_Radius*Math.sin(Math.toRadians(ring_Degree))));
				float l1=getVectorLength(a1, b1, c1);//ģ��
				a1=a1/l1;//���������
				b1=b1/l1;
				c1=c1/l1;
				
				float a2=(float) (x2-(ring_Radius*Math.cos(Math.toRadians(ring_Degree+ring_Span))));
				float b2=y1-0;
				float c2=(float) (z2-(ring_Radius*Math.sin(Math.toRadians(ring_Degree+ring_Span))));
				float l2=getVectorLength(a2, b2, c2);//ģ��
				a2=a2/l2;//���������
				b2=b2/l2;
				c2=c2/l2;
				
				float a3=(float) (x3-(ring_Radius*Math.cos(Math.toRadians(ring_Degree+ring_Span))));
				float b3=y1-0;
				float c3=(float) (z3-(ring_Radius*Math.sin(Math.toRadians(ring_Degree+ring_Span))));
				float l3=getVectorLength(a3, b3, c3);//ģ��
				a3=a3/l3;//���������
				b3=b3/l3;
				c3=c3/l3;
				
				float a4=(float) (x4-(ring_Radius*Math.cos(Math.toRadians(ring_Degree))));
				float b4=y1-0;
				float c4=(float) (z4-(ring_Radius*Math.sin(Math.toRadians(ring_Degree))));
				float l4=getVectorLength(a4, b4, c4);//ģ��
				a4=a4/l4;//���������
				b4=b4/l4;
				c4=c4/l4;
				
				ial.add(a1);ial.add(b1);ial.add(c1);//�����Ӧ�ķ�����
				ial.add(a2);ial.add(b2);ial.add(c2);
				ial.add(a4);ial.add(b4);ial.add(c4);
				
				ial.add(a2);ial.add(b2);ial.add(c2);
				ial.add(a3);ial.add(b3);ial.add(c3);
				ial.add(a4);ial.add(b4);ial.add(c4);
			}
		}
		vcount=val.size()/3;
		float[] vertexs=new float[vcount*3];
		for(int i=0;i<vcount*3;i++)
		{
			vertexs[i]=val.get(i);
		}
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertexs.length*4);
		vbb.order(ByteOrder.nativeOrder());
		myVertex=vbb.asFloatBuffer();
		myVertex.put(vertexs);
		myVertex.position(0);
		
		//������
		float[] normals=new float[vcount*3];
		for(int i=0;i<vcount*3;i++)
		{
			normals[i]=ial.get(i);
		}
		ByteBuffer ibb=ByteBuffer.allocateDirect(normals.length*4);
		ibb.order(ByteOrder.nativeOrder());
		myNormalBuffer=ibb.asFloatBuffer();
		myNormalBuffer.put(normals);
		myNormalBuffer.position(0);
		
		//����
		
		int row=(int) (360.0f/circle_Span);
		int col=(int) (360.0f/ring_Span);
		float[] textures=generateTexCoor(row,col);
		
		ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
		tbb.order(ByteOrder.nativeOrder());
		myTexture=tbb.asFloatBuffer();
		myTexture.put(textures);
		myTexture.position(0);
	}
	
	public void drawSelf(GL10 gl)
	{	
		gl.glRotatef(mAngleX, 1, 0, 0);//��ת
		gl.glRotatef(mAngleY, 0, 1, 0);
		gl.glRotatef(mAngleZ, 0, 0, 1);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertex);
		
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);//�򿪷���������
		gl.glNormalPointer(GL10.GL_FLOAT, 0, myNormalBuffer);//ָ������������
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, myTexture);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureid);
		
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vcount);
		
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//�رջ���
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
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
    
	//��������񻯣���ģ����
	public float getVectorLength(float x,float y,float z)
	{
		float pingfang=x*x+y*y+z*z;
		float length=(float) Math.sqrt(pingfang);
		return length;
	}
    
}
