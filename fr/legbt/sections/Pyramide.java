package fr.legbt.sections;

import java.util.ArrayList;
import java.util.Collections;

import javax.media.opengl.GL2;
import javax.vecmath.Vector3f;


public class Pyramide {
	private Face base;
	private Triangle front;
	private Triangle back;
	private Triangle left;
	private Triangle right;
	private ArrayList<Piece> pieces;
	private static final float tworoot = 1.4142136f;
	private static final float roottwo = 0.7071068f;

	public Pyramide(){
		Vector3f u1 = new Vector3f(1.5f,0,0);
		Vector3f u2 = new Vector3f(0,1,0);
		Vector3f u3 = new Vector3f(0,0,-1);
		Vector3f u4 = new Vector3f(0,0.75f,1f);
		Vector3f u5 = new Vector3f(0,-0.25f,0);
		Vector3f u6 = new Vector3f(-1.5f,0,0);
		Vector3f u7 = new Vector3f(0,-0.25f,1);
		Vector3f u8 = new Vector3f(0,0.75f,0);
		Vector3f u9 = new Vector3f(-0.25f,0,1);
		Vector3f u10 = new Vector3f(1.25f,0,0);
		Vector3f u11 = new Vector3f(1.25f,0,1);
		Vector3f u12 = new Vector3f(-0.25f,0,0);
		pieces = new ArrayList<Piece>();
		base = new Face(u1,u2,u3);
		front = new Triangle(0.66666667f,u1,u4,u5);
		back = new Triangle(0.66666667f,u1,u7,u8);
		left = new Triangle(0.5f,u2,u9,u10);
		right = new Triangle(0.5f,u2,u11,u12);
		pieces.add(base);
		pieces.add(front);
		pieces.add(back);
		pieces.add(left);
		pieces.add(right);
		Collections.sort(pieces);
	}

	public void resetRotation(){
		for(int i=0;i<pieces.size();i++){
			pieces.get(i).resetRotation();
		}
	}


	public void xRotation(float degree){
		for(int i=0;i<pieces.size();i++){
			pieces.get(i).xRotation(degree);
		}
	}

	public void yRotation(float degree){
		for(int i=0;i<pieces.size();i++){
			pieces.get(i).yRotation(degree);
		}
	}

	public void zRotation(float degree){
		for(int i=0;i<pieces.size();i++){
			pieces.get(i).zRotation(degree);
		}
	}

	public void sort(){
		Collections.sort(pieces);
	}

	public void traceCube(GL2 gl){
		for(int i=0;i<pieces.size();i++){
			pieces.get(i).traceVertexes(gl);
			if(pieces.get(i) instanceof Disc){
				Disc d = (Disc) pieces.get(i);
				d.drawBorders(gl);
			}
		}
	}

}
