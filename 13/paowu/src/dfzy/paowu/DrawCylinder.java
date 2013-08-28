package dfzy.paowu;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class DrawCylinder
{
	private FloatBuffer myVertexBuffer;//�������껺�� 
	private FloatBuffer myNormalBuffer;//����������
	private FloatBuffer myTexture;//������
	
	int textureId;
	
	int vCount;//��������
	
	float length;//Բ������
	float circle_radius;//Բ�ػ��뾶
	float degreespan;  //Բ�ػ�ÿһ�ݵĶ�����С
	int col;//Բ������
	
	public float mAngleX;
	public float mAngleY;
	public float mAngleZ;
	
	public DrawCylinder(float length,float circle_radius,float degreespan,int col,int textureId)
	{
		this.circle_radius=circle_radius;
		this.length=length;
		this.col=col;
		this.degreespan=degreespan;
		this.textureId=textureId;
		
		float collength=(float)length/col;//Բ��ÿ����ռ�ĳ���
		int spannum=(int)(360.0f/degreespan);
		
		ArrayList<Float> val=new ArrayList<Float>();//�������б�
		ArrayList<Float> ial=new ArrayList<Float>();//����������б�
		
		for(float circle_degree=360.0f;circle_degree>0.0f;circle_degree-=degreespan)//ѭ����
		{
			for(int j=0;j<col;j++)//ѭ����
			{
				float x1 =(float)(j*collength-length/2);
				float y1=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree)));
				float z1=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree)));
				
				float a1=0;
				float b1=y1;
				float c1=z1;
				float l1=getVectorLength(a1, b1, c1);//ģ��
				a1=a1/l1;//���������
				b1=b1/l1;
				c1=c1/l1;
				
				float x2 =(float)(j*collength-length/2);
				float y2=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree-degreespan)));
				float z2=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree-degreespan)));
				
				float a2=0;
				float b2=y2;
				float c2=z2;
				float l2=getVectorLength(a2, b2, c2);//ģ��
				a2=a2/l2;//���������
				b2=b2/l2;
				c2=c2/l2;
				
				float x3 =(float)((j+1)*collength-length/2);
				float y3=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree-degreespan)));
				float z3=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree-degreespan)));
				
				float a3=0;
				float b3=y3;
				float c3=z3;
				float l3=getVectorLength(a3, b3, c3);//ģ��
				a3=a3/l3;//���������
				b3=b3/l3;
				c3=c3/l3;
				
				float x4 =(float)((j+1)*collength-length/2);
				float y4=(float) (circle_radius*Math.sin(Math.toRadians(circle_degree)));
				float z4=(float) (circle_radius*Math.cos(Math.toRadians(circle_degree)));
				
				float a4=0;
				float b4=y4;
				float c4=z4;
				float l4=getVectorLength(a4, b4, c4);//ģ��
				a4=a4/l4;//���������
				b4=b4/l4;
				c4=c4/l4;
				
				val.add(x1);val.add(y1);val.add(z1);//���������Σ���6�����������
				val.add(x2);val.add(y2);val.add(z2);
				val.add(x4);val.add(y4);val.add(z4);
				
				val.add(x2);val.add(y2);val.add(z2);
				val.add(x3);val.add(y3);val.add(z3);
				val.add(x4);val.add(y4);val.add(z4);
				
				ial.add(a1);ial.add(b1);ial.add(c1);//�����Ӧ�ķ�����
				ial.add(a2);ial.add(b2);ial.add(c2);
				ial.add(a4);ial.add(b4);ial.add(c4);
				
				ial.add(a2);ial.add(b2);ial.add(c2);
				ial.add(a3);ial.add(b3);ial.add(c3);
				ial.add(a4);ial.add(b4);ial.add(c4);
			}
		}
		 
		vCount=val.size()/3;//ȷ����������
		Log.d("vCount", vCount+"");
		
		//����
		float[] vertexs=new float[vCount*3];
		for(int i=0;i<vCount*3;i++)
		{
			vertexs[i]=val.get(i);
		}
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertexs.length*4);
		vbb.order(ByteOrder.nativeOrder());
		myVertexBuffer=vbb.asFloatBuffer();
		myVertexBuffer.put(vertexs);
		myVertexBuffer.position(0);
		
		//������
		float[] normals=new float[vCount*3];
		for(int i=0;i<vCount*3;i++)
		{
			normals[i]=ial.get(i);
		}
		ByteBuffer ibb=ByteBuffer.allocateDirect(normals.length*4);
		ibb.order(ByteOrder.nativeOrder());
		myNormalBuffer=ibb.asFloatBuffer();
		myNormalBuffer.put(normals);
		myNormalBuffer.position(0);
		
		//����
		float[] textures=generateTexCoor(col,spannum);
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
		
		Log.d("aaaaaaaaa", "aaaaaaaaaa");
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);//�򿪶��㻺��
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertexBuffer);//ָ�����㻺��
		
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);//�򿪷���������
		gl.glNormalPointer(GL10.GL_FLOAT, 0, myNormalBuffer);//ָ������������
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, myTexture);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);//����ͼ��
		
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//�رջ���
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
	}
	
	//��������񻯣���ģ����
	public float getVectorLength(float x,float y,float z)
	{
		float pingfang=x*x+y*y+z*z;
		float length=(float) Math.sqrt(pingfang);
		return length;
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
    			   			
    			result[c++]=s;
    			result[c++]=t+sizeh;
    			
    			result[c++]=s+sizew;
    			result[c++]=t+sizeh;   
    			
    			result[c++]=s+sizew;
    			result[c++]=t;
    		}
    	}
    	return result;
    }
}