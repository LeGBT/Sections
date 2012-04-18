package fr.legbt.sections;

import javax.media.opengl.GL2;
//import javax.vecmath.Vector3f;



public abstract class Piece implements Comparable<Piece>{


	public abstract float getProf();
	public abstract void resetRotation();
	public abstract void xRotation(float degree);
	public abstract void yRotation(float degree);
	public abstract void zRotation(float degree);
	public abstract void traceVertexes(GL2 gl);




}
