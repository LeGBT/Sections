package fr.legbt.sections;

import java.util.ArrayList;
import java.util.Collections;

import javax.media.opengl.GL2;
import javax.vecmath.Vector3f;

public class Pave {
	private Face top;
	private Face bottom;
	private Face left;
	private Face right;
	private Face front;
	private Face back;
	private ArrayList<Face> faces;

	public Pave(){
		Vector3f u1 = new Vector3f(1,0,0);
		Vector3f u11 = new Vector3f(1.5f,0,0);
		Vector3f u2 = new Vector3f(0,1,0);
		Vector3f u22 = new Vector3f(0,0.7f,0);
		Vector3f u3 = new Vector3f(0,0,1);
		Vector3f u4 = new Vector3f(-1,0,0);
		Vector3f u41 = new Vector3f(-1.5f,0,0);
		Vector3f u5 = new Vector3f(0,-1,0);
		Vector3f u52 = new Vector3f(0,-0.7f,0);
		Vector3f u6 = new Vector3f(0,0,-1);
		top = new Face(u11,u6,u22);
		bottom = new Face(u11,u3,u52);
		left = new Face(u22,u6,u41);
		right = new Face(u52,u6,u11);
		front = new Face(u11,u22,u3);
		back = new Face(u11,u52,u6);
		faces = new ArrayList<Face>();
		faces.add(top);
		faces.add(bottom);
		faces.add(left);
		faces.add(right);
		faces.add(front);
		faces.add(back);
		Collections.sort(faces);
	}

	public void resetRotation(){
		for(int i=0;i<faces.size();i++){
			faces.get(i).resetRotation();
		}
	}

	public void xRotation(float degree){
		for(int i=0;i<faces.size();i++){
			faces.get(i).xRotation(degree);
		}
	}
	public void yRotation(float degree){
		for(int i=0;i<faces.size();i++){
			faces.get(i).yRotation(degree);
		}
	}
	public void zRotation(float degree){
		for(int i=0;i<faces.size();i++){
			faces.get(i).zRotation(degree);
		}
	}

	
	public void sort(){
		Collections.sort(faces);
	}

	public void traceCube(GL2 gl){
		for(int i=0;i<faces.size();i++){
			faces.get(i).traceVertexes(gl);
		}
	}

}
