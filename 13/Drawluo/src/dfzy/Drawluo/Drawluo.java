package dfzy.Drawluo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class Drawluo
{
	private FloatBuffer myVertex;//���㻺��
	private FloatBuffer myTexture;//������
	private FloatBuffer myNormalBuffer;//����������
	
	int vcount;
	int textureid;
	
	float circle_small_R;		//�����ʼ�뾶
	float Hedicoid_small_R;		//��������ʼ�뾶
	
	public float mAngleX;
	public float mAngleY;
	public float mAngleZ;
	
	final static float MAX_ANGLE=360*3f;//�����Ƕ�
	final static float HEDICOID_ANGLE_SPAN=10f;//�����Ƕȱ仯��
	final static float CIRCLE_ANGLE_SPAN=180f;//����Ƕȱ仯��
	final static float CIRCLE_R_SPAN=0f;//����뾶�仯��0.01f
	final static float HEDICOID_R_SPAN=0f;//������뾶�仯��0.2f
	final static float LENGTH_SPAN=0.3f;//�����߶ȱ仯
	final static float CIECLE_ANGLE_BEGIN=0f;//���濪ʼ���ƽǶ�
	final static float CIECLE_ANGLE_OVER=180f;//����������ƽǶ�
	 
	public Drawluo
			(		float circle_small_R,	//����뾶
					float Hedicoid_small_R,	//������뾶
					int textureid			//������Ƕȱ仯��λ������
			)
	{     
		this.circle_small_R=circle_small_R;
		this.Hedicoid_small_R=Hedicoid_small_R;
		this.textureid=textureid;
		
		ArrayList<Float> val=new ArrayList<Float>();//�����б�
		ArrayList<Float> ial=new ArrayList<Float>();//����������б�
		
		for(float h_angle=0,c_r=circle_small_R,h_r=Hedicoid_small_R,length=0;h_angle<MAX_ANGLE;h_angle+=HEDICOID_ANGLE_SPAN,c_r+=CIRCLE_R_SPAN,h_r+=HEDICOID_R_SPAN,length+=LENGTH_SPAN)
		{
			for(float c_angle=CIECLE_ANGLE_BEGIN;c_angle<CIECLE_ANGLE_OVER;c_angle+=CIRCLE_ANGLE_SPAN)
			{
				float x1=(float) ((h_r+c_r*Math.cos(Math.toRadians(c_angle)))*Math.cos(Math.toRadians(h_angle)));
				float y1=(float) (c_r*Math.sin(Math.toRadians(c_angle))+length);
				float z1=(float) ((h_r+c_r*Math.cos(Math.toRadians(c_angle)))*Math.sin(Math.toRadians(h_angle)));
				
				float x2=(float) ((h_r+c_r*Math.cos(Math.toRadians(c_angle+CIRCLE_ANGLE_SPAN)))*Math.cos(Math.toRadians(h_angle)));
				float y2=(float) (c_r*Math.sin(Math.toRadians(c_angle+CIRCLE_ANGLE_SPAN))+length);
				float z2=(float) ((h_r+c_r*Math.cos(Math.toRadians(c_angle+CIRCLE_ANGLE_SPAN)))*Math.sin(Math.toRadians(h_angle)));
				
				float x3=(float) (((h_r+HEDICOID_R_SPAN)+(c_r+CIRCLE_R_SPAN)*Math.cos(Math.toRadians(c_angle+CIRCLE_ANGLE_SPAN)))*Math.cos(Math.toRadians(h_angle+HEDICOID_ANGLE_SPAN)));
				float y3=(float) ((c_r+CIRCLE_R_SPAN)*Math.sin(Math.toRadians(c_angle+CIRCLE_ANGLE_SPAN))+(length+LENGTH_SPAN));
				float z3=(float) (((h_r+HEDICOID_R_SPAN)+(c_r+CIRCLE_R_SPAN)*Math.cos(Math.toRadians(c_angle+CIRCLE_ANGLE_SPAN)))*Math.sin(Math.toRadians(h_angle+HEDICOID_ANGLE_SPAN)));
				
				float x4=(float) (((h_r+HEDICOID_R_SPAN)+(c_r+CIRCLE_R_SPAN)*Math.cos(Math.toRadians(c_angle)))*Math.cos(Math.toRadians(h_angle+HEDICOID_ANGLE_SPAN)));
				float y4=(float) ((c_r+CIRCLE_R_SPAN)*Math.sin(Math.toRadians(c_angle))+(length+LENGTH_SPAN));
				float z4=(float) (((h_r+HEDICOID_R_SPAN)+(c_r+CIRCLE_R_SPAN)*Math.cos(Math.toRadians(c_angle)))*Math.sin(Math.toRadians(h_angle+HEDICOID_ANGLE_SPAN)));
				
				val.add(x1);val.add(y1);val.add(z1);
				val.add(x2);val.add(y2);val.add(z2);
				val.add(x4);val.add(y4);val.add(z4);
							
				val.add(x2);val.add(y2);val.add(z2);
				val.add(x3);val.add(y3);val.add(z3); 
				val.add(x4);val.add(y4);val.add(z4); 
				
				//��������Բ�������ĵ���ɵ�Բ���ϵĵ������
				
				float a1=(float) (x1-(h_r*Math.cos(Math.toRadians(h_angle))));
				float b1=y1-length;
				float c1=(float) (z1-(h_r*Math.sin(Math.toRadians(h_angle))));
				float l1=getVectorLength(a1, b1, c1);//ģ��
				a1=a1/l1;//���������
				b1=b1/l1;
				c1=c1/l1;
				
				float a2=(float) (x2-(h_r*Math.cos(Math.toRadians(h_angle))));
				float b2=y1-length;
				float c2=(float) (z2-(h_r*Math.sin(Math.toRadians(h_angle))));
				float l2=getVectorLength(a2, b2, c2);//ģ��
				a2=a2/l2;//���������
				b2=b2/l2;
				c2=c2/l2;
				
				float a3=(float) (x3-(h_r*Math.cos(Math.toRadians(h_angle+HEDICOID_ANGLE_SPAN))));
				float b3=y1-(length+LENGTH_SPAN);
				float c3=(float) (z3-(h_r*Math.sin(Math.toRadians(h_angle+HEDICOID_ANGLE_SPAN))));
				float l3=getVectorLength(a3, b3, c3);//ģ��
				a3=a3/l3;//���������
				b3=b3/l3;
				c3=c3/l3;
				
				float a4=(float) (x4-(h_r*Math.cos(Math.toRadians(h_angle+HEDICOID_ANGLE_SPAN))));
				float b4=y1-(length+LENGTH_SPAN);
				float c4=(float) (z4-(h_r*Math.sin(Math.toRadians(h_angle+HEDICOID_ANGLE_SPAN))));
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
		
		int col=(int) (MAX_ANGLE/HEDICOID_ANGLE_SPAN);
		int row=(int) ((CIECLE_ANGLE_OVER-CIECLE_ANGLE_BEGIN)/CIRCLE_ANGLE_SPAN);
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
		gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
    //�Զ��з����������������ķ���
    public float[] generateTexCoor(int hang,int lie)
    {
    	float[] result=new float[hang*lie*6*2]; 
    	float sizeh=1.0f/hang;//�д�С��λ
    	float sizel=1.0f/lie;//�д�С��λ
    	int c=0;
    	for(int i=0;i<lie;i++)
    	{
    		for(int j=0;j<hang;j++)
    		{
    			//ÿ����һ�����Σ������������ι��ɣ��������㣬12����������
    			float h=j*sizeh;
    			float l=i*sizel;
    			
    			result[c++]=h;//1
    			result[c++]=l;
    		
    			result[c++]=h+sizeh;//2
    			result[c++]=l;
    			
    			result[c++]=h;//4
    			result[c++]=l+sizel;
    			
    			result[c++]=h+sizeh;//2
    			result[c++]=l;
    			
    			result[c++]=h+sizeh;//3
    			result[c++]=l+sizel; 
    			
    			result[c++]=h;//4
    			result[c++]=l+sizel;    			
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
