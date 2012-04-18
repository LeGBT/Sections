package fr.legbt.sections;

import javax.media.opengl.GL2;
//import javax.vecmath.Vector3f;



public interface  Piece extends Comparable<Piece>{


	public  float getProf();
	public  void resetRotation();
	public  void xRotation(float degree);
	public  void yRotation(float degree);
	public  void zRotation(float degree);
	public  void traceVertexes(GL2 gl);




}
