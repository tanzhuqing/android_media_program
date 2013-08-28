package dfzy.paowu;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class Drawpaowu
{
	private FloatBuffer myVertexBuffer;//�������껺�� 
//	private FloatBuffer myNormalBuffer;//����������
	private FloatBuffer myTexture;//������
	
	int textureId;
	int vCount;//��������
	
	float height;//������߶�
	float a;//������Բ�����
	float b;
	float col;//������߶��и����
	float angleSpan;//�����зֽǶ�
	
	public float mAngleX;
	public float mAngleY;
	public float mAngleZ;
	
	public Drawpaowu(float height,float a,float b,int col,float angleSpan,int textureId)
	{
		this.height=height;
		this.a=a;
		this.b=b;
		this.col=col;
		this.angleSpan=angleSpan;
		this.textureId=textureId;
		
		float heightSpan=height/col;//������ÿ����ռ�ĸ߶�
		int spannum=(int)(360.0f/angleSpan);//����Ƕ��зַ���
		
		ArrayList<Float> val=new ArrayList<Float>();//�������б�
		//ArrayList<Float> ial=new ArrayList<Float>();//����������б�
		
		for(float h=height;h>0;h-=heightSpan)//ѭ����
		{
			for(float angle=360;angle>0;angle-=angleSpan)//ѭ����
			{
				float x1=(float) (a*Math.sqrt(2*h)*Math.cos(Math.toRadians(angle)));
				float y1=h;
				float z1=(float) (b*Math.sqrt(2*h)*Math.sin(Math.toRadians(angle)));
				
				float x2=(float) (a*Math.sqrt(2*(h-heightSpan))*Math.cos(Math.toRadians(angle)));
				float y2=h-heightSpan;
				float z2=(float) (b*Math.sqrt(2*(h-heightSpan))*Math.sin(Math.toRadians(angle)));
				
				float x3=(float) (a*Math.sqrt(2*(h-heightSpan))*Math.cos(Math.toRadians(angle-angleSpan)));
				float y3=h-heightSpan;
				float z3=(float) (b*Math.sqrt(2*(h-heightSpan))*Math.sin(Math.toRadians(angle-angleSpan)));
				
				float x4=(float) (a*Math.sqrt(2*h)*Math.cos(Math.toRadians(angle-angleSpan)));
				float y4=h;
				float z4=(float) (b*Math.sqrt(2*h)*Math.sin(Math.toRadians(angle-angleSpan)));
				
				val.add(x1);val.add(y1);val.add(z1);//���������Σ���6�����������
				val.add(x2);val.add(y2);val.add(z2);
				val.add(x4);val.add(y4);val.add(z4);
				
				val.add(x2);val.add(y2);val.add(z2);
				val.add(x3);val.add(y3);val.add(z3);
				val.add(x4);val.add(y4);val.add(z4);
				
//				float a1=0;
//				float b1=y1;
//				float c1=z1;
//				float l1=getVectorLength(a1, b1, c1);//ģ��
//				a1=a1/l1;//���������
//				b1=b1/l1;
//				c1=c1/l1;
//				
//				float a2=0;
//				float b2=y2;
//				float c2=z2;
//				float l2=getVectorLength(a2, b2, c2);//ģ��
//				a2=a2/l2;//���������
//				b2=b2/l2;
//				c2=c2/l2;
//				
//				float a3=0;
//				float b3=y3;
//				float c3=z3;
//				float l3=getVectorLength(a3, b3, c3);//ģ��
//				a3=a3/l3;//���������
//				b3=b3/l3;
//				c3=c3/l3;
//				
//				float a4=0;
//				float b4=y4;
//				float c4=z4;
//				float l4=getVectorLength(a4, b4, c4);//ģ��
//				a4=a4/l4;//���������
//				b4=b4/l4;
//				c4=c4/l4;
//				
//				ial.add(a1);ial.add(b1);ial.add(c1);//�����Ӧ�ķ�����
//				ial.add(a2);ial.add(b2);ial.add(c2);
//				ial.add(a4);ial.add(b4);ial.add(c4);
//				
//				ial.add(a2);ial.add(b2);ial.add(c2);
//				ial.add(a3);ial.add(b3);ial.add(c3);
//				ial.add(a4);ial.add(b4);ial.add(c4);
			}
		}
		 
		vCount=val.size()/3;//ȷ����������
		
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
		
//		//������
//		float[] normals=new float[vCount*3];
//		for(int i=0;i<vCount*3;i++)
//		{
//			normals[i]=ial.get(i);
//		}
//		ByteBuffer ibb=ByteBuffer.allocateDirect(normals.length*4);
//		ibb.order(ByteOrder.nativeOrder());
//		myNormalBuffer=ibb.asFloatBuffer();
//		myNormalBuffer.put(normals);
//		myNormalBuffer.position(0);
//		
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
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertexBuffer);
		
//		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);//�򿪷���������
//		gl.glNormalPointer(GL10.GL_FLOAT, 0, myNormalBuffer);//ָ������������
//		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, myTexture);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);
		
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);//�رջ���
		gl.glEnable(GL10.GL_TEXTURE_2D);
//		gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}
	
	//��������񻯣���ģ����
	public float getVectorLength(float x,float y,float z)
	{
		float pingfang=x*x+y*y+z*z;
		float length=(float) Math.sqrt(pingfang);
		return length;
	}
	
    //�Զ��з����������������ķ���
    public float[] generateTexCoor(int hang,int lie)
    {
    	float[] result=new float[hang*lie*6*2]; 
    	float sizeh=1.0f/hang;//�д�С��λ
    	float sizel=1.0f/lie;//�д�С��λ
    	int c=0;
    	for(int i=0;i<hang;i++)
    	{
    		for(int j=0;j<lie;j++)
    		{
    			//ÿ����һ�����Σ������������ι��ɣ��������㣬12����������
    			float h=i*sizeh;
    			float l=j*sizel;
    			 
    			result[c++]=l;//1
    			result[c++]=h; 
    		
    			result[c++]=l;//2
    			result[c++]=h+sizeh; 
    			
    			result[c++]=l+sizel;//4
    			result[c++]=h;
    			
    			result[c++]=l;//2
    			result[c++]=h+sizeh;
    			
    			result[c++]=l+sizel;//3
    			result[c++]=h+sizeh; 
    			
    			result[c++]=l+sizel;//4
    			result[c++]=h;    			
    		}
    	}
    	return result;
    }
}