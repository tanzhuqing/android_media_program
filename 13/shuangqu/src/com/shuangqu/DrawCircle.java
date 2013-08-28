package com.shuangqu;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class DrawCircle
{
	//��Ա����
	private FloatBuffer myVertex;
	private FloatBuffer myTexture;
	int vcount;//��������
	
	float radius;//Բ�뾶
	float anglespan;//Բ������λ
	int textureid;//��������
	
	public float xAngle;//��ת�Ƕ�
	public float yAngle;
	public float zAngle;
	   
	//������
	public DrawCircle(float radius,float anglespan,int textureid){
		this.anglespan=anglespan;
		this.radius=radius;
		this.textureid=textureid;
		
		//����
		ArrayList<Float> val=new ArrayList<Float>();//���ڴ�Ŷ�������
		ArrayList<Float> tal=new ArrayList<Float>();//���ڴ����������

		for(float angle=0;angle<360;angle+=anglespan)
		{
			float x1=0f;
			float y1=0f;
			float z1=0f;
			float x2=(float) (radius*Math.cos(Math.toRadians(angle)));
			float y2=(float) (radius*Math.sin(Math.toRadians(angle)));
			float z2=0f;
			float x3=(float) (radius*Math.cos(Math.toRadians(angle+anglespan)));
			float y3=(float) (radius*Math.sin(Math.toRadians(angle+anglespan)));
			float z3=0f;
			val.add(x1);
			val.add(y1);
			val.add(z1);
			val.add(x2);
			val.add(y2);
			val.add(z2);
			val.add(x3);
			val.add(y3);
			val.add(z3);
			
			float tx1=(float) (0.5f*Math.cos(Math.toRadians(angle)));
			float ty1=(float) (0.5f*Math.sin(Math.toRadians(angle)));
			float tx2=(float) (0.5f*Math.cos(Math.toRadians(angle+anglespan)));
			float ty2=(float) (0.5f*Math.sin(Math.toRadians(angle+anglespan)));
			tal.add(0.5f);
			tal.add(0.5f);
			tal.add(tx1+0.5f);
			tal.add(ty1+0.5f);
			tal.add(tx2+0.5f);
			tal.add(ty2+0.5f);
		}
		vcount=val.size()/3;
		
		float[] vertexs=new float[val.size()];
		for(int i=0;i<val.size();i++)
		{
			vertexs[i]=val.get(i);
		}
		ByteBuffer vbb=ByteBuffer.allocateDirect(vertexs.length*4);
		vbb.order(ByteOrder.nativeOrder());
		myVertex=vbb.asFloatBuffer();
		myVertex.put(vertexs);
		myVertex.position(0);
	
		float[] textures=new float[tal.size()];
		for(int i=0;i<tal.size();i++)
		{
			textures[i]=tal.get(i);
		}
		ByteBuffer tbb=ByteBuffer.allocateDirect(textures.length*4);
		tbb.order(ByteOrder.nativeOrder());
		myTexture=tbb.asFloatBuffer();
		myTexture.put(textures);
		myTexture.position(0);
	}
	
	public void drawSelf(GL10 gl)
	{
		//gl.glRotatef(xAngle, 1, 0, 0);
		//gl.glRotatef(yAngle, 0, 1, 0);
		//gl.glRotatef(zAngle, 0, 0, 1);
		gl.glRotatef(180, 1, 0, 0);
		
		//����
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, myVertex);
		
		//����
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, myTexture);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureid);
		
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vcount);
        //�ر�����
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisable(GL10.GL_TEXTURE_2D);
	}
	
	
}