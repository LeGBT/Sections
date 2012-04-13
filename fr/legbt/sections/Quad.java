package fr.legbt.sections;

import javax.media.opengl.GL2;
import javax.vecmath.Vector3f;
import javax.vecmath.Matrix3f;

public class Quad{
	protected Vector3f u;
	protected Vector3f v;
	protected Vector3f n;
	private Vector3f topleft;
	private Vector3f topright;
	private Vector3f bottomleft;
	private Vector3f bottomright;
	static Vector3f x = new Vector3f(1,0,0);
	static Vector3f y = new Vector3f(0,1,0);
	static Vector3f z = new Vector3f(0,0,1);

	public Quad(Vector3f u, Vector3f v, Vector3f n){
		this.u = new Vector3f(u);
		this.v = new Vector3f(v);
		this.n = new Vector3f(n);
	}

	static private float radian(float degree){
		return degree*0.017453292519943295769236907684f;
	}

	private void rotation(Matrix3f matrix){
		matrix.transform(u);
		matrix.transform(v);
		matrix.transform(n);
	}

	public void xRotation(float degree){
		Matrix3f matrix = new Matrix3f();
		matrix.rotX(radian(degree));
		rotation(matrix);
	}
	public void yRotation(float degree){
		Matrix3f matrix = new Matrix3f();
		matrix.rotY(radian(degree));
		rotation(matrix);
	}
	public void zRotation(float degree){
		Matrix3f matrix = new Matrix3f();
		matrix.rotZ(radian(degree));
		rotation(matrix);
	}

	public void Vect3ToVertex(GL2 gl, Vector3f b){
		float px = b.dot(x);
		float py = b.dot(y);
		float pz = b.dot(z);
		gl.glVertex3f(px,py,pz);
	}

	/**
	 * Gets the normal vector for this face.
	 *
	 * @return The n.
	 */
	public Vector3f getN()
	{
		return this.n;
	}

	private void defineTopLeft(){
		topleft = new Vector3f(this.n);
		topleft.sub(this.u);
		topleft.add(this.v);
	}
	private void defineTopRight(){
		topright = new Vector3f(this.n);
		topright.add(this.u);
		topright.add(this.v);
	}
	private void defineBottomLeft(){
		bottomleft = new Vector3f(this.n);
		bottomleft.sub(this.u);
		bottomleft.sub(this.v);
	}
	private void defineBottomRight(){
		bottomright = new Vector3f(this.n);
		bottomright.add(this.u);
		bottomright.sub(this.v);
	}
	protected void drawTopLeft(GL2 gl){
		defineTopLeft();
		Vect3ToVertex(gl,topleft);
	}
	protected void drawTopRight(GL2 gl){
		defineTopRight();
		Vect3ToVertex(gl,topright);
	}
	protected void drawBottomLeft(GL2 gl){
		defineBottomLeft();
		Vect3ToVertex(gl,bottomleft);
	}
	protected void drawBottomRight(GL2 gl){
		defineBottomRight();
		Vect3ToVertex(gl,bottomright);
	}
	protected void drawBorders(GL2 gl){
		defineTopLeft();
		defineTopRight();
		defineBottomLeft();
		defineBottomRight();
		gl.glBegin(GL2.GL_LINES);
		Vect3ToVertex(gl,topleft);
		Vect3ToVertex(gl,bottomleft);
		gl.glEnd();
		gl.glBegin(GL2.GL_LINES);
		Vect3ToVertex(gl,bottomleft);
		Vect3ToVertex(gl,bottomright);
		gl.glEnd();
		gl.glBegin(GL2.GL_LINES);
		Vect3ToVertex(gl,bottomright);
		Vect3ToVertex(gl,topright);
		gl.glEnd();
		gl.glBegin(GL2.GL_LINES);
		Vect3ToVertex(gl,topright);
		Vect3ToVertex(gl,topleft);
		gl.glEnd();
	}

}
