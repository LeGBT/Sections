package fr.legbt.sections;

import javax.media.opengl.GL2;
import javax.vecmath.Vector3f;
import javax.vecmath.Matrix3f;

public class Triangle implements Piece{
	protected Vector3f u;
	protected Vector3f v;
	protected Vector3f n;
	protected Vector3f ur;
	protected Vector3f vr;
	protected Vector3f nr;
	protected Vector3f np;
	private Vector3f left;
	private Vector3f right;
	private Vector3f top;
	private float cote;
	private float xrot;
	private float yrot;
	private float zrot;
	private float sommet;
	static Vector3f x = new Vector3f(1,0,0);
	static Vector3f y = new Vector3f(0,1,0);
	static Vector3f z = new Vector3f(0,0,1);

	public Triangle(float sommet, Vector3f u, Vector3f v, Vector3f n){
		this.u = new Vector3f(u);
		this.v = new Vector3f(v);
		this.n = new Vector3f(n);
		this.ur = new Vector3f(u);
		this.vr = new Vector3f(v);
		this.nr = new Vector3f(n);
		this.np = new Vector3f(n);
		this.xrot = 0;
		this.yrot = 0;
		this.zrot = 0;
		this.cote = 1;
		this.sommet = sommet;
	}

	public Triangle(float sommet, float c,Vector3f u, Vector3f v, Vector3f n){
		this(sommet,u,v,n);
		this.cote = c;
	}
	public Triangle(float sommet, float c,float xscale, float yscale, Vector3f n){
		this(sommet,new Vector3f(xscale,0,0),new Vector3f(0,yscale,0),n);
		this.cote = c;
	}

	static private float radian(float degree){
		return degree*0.017453292519943295769236907684f;
	}

	private void rotation(Matrix3f matrix){
		matrix.transform(ur);
		matrix.transform(vr);
		matrix.transform(nr);
	}

	public void resetRotation(){
		this.ur = new Vector3f(this.u);
		this.vr = new Vector3f(this.v);
		this.nr = new Vector3f(this.np);
	}

	public void xRotation(float degree){
		Matrix3f matrix = new Matrix3f();
		xrot += radian(degree);
		matrix.rotX(xrot);
		rotation(matrix);
	}
	public void yRotation(float degree){
		Matrix3f matrix = new Matrix3f();
		yrot += radian(degree);
		matrix.rotY(yrot);
		rotation(matrix);
	}
	public void zRotation(float degree){
		Matrix3f matrix = new Matrix3f();
		zrot += radian(degree);
		matrix.rotZ(zrot);
		rotation(matrix);
	}

	public void vect3ToVertex(GL2 gl, Vector3f b){
		float px = b.dot(x);
		float py = b.dot(y);
		float pz = b.dot(z);
		gl.glVertex3f(px,py,pz);
	}


	public float getH(){
		return this.np.length();
	}

	/**
	 * Gets the normal vector for this face.
	 *
	 * @return The n.
	 */
	public Vector3f getN()
	{
		return this.nr;
	}

	private void defineLeft(){
		left = new Vector3f(this.nr);
		left.sub(this.ur);
		left.sub(this.vr);
		left.scale(cote);
	}
	private void defineRight(){
		right = new Vector3f(this.nr);
		right.add(this.ur);
		right.sub(this.vr);
		right.scale(cote);
	}
	private void defineTop(){
		top = new Vector3f(this.ur);
		top.scale(sommet);
		top.add(this.nr);
		top.add(this.vr);
		top.scale(cote);
	}
	protected void drawLeft(GL2 gl){
		defineLeft();
		vect3ToVertex(gl,left);
	}
	protected void drawRight(GL2 gl){
		defineRight();
		vect3ToVertex(gl,right);
	}
	protected void drawTop(GL2 gl){
		defineTop();
		vect3ToVertex(gl,top);
	}
	protected void drawBorders(GL2 gl){
		defineTop();
		defineLeft();
		defineRight();
		gl.glBegin(GL2.GL_LINE_STRIP);
		vect3ToVertex(gl,left);
		vect3ToVertex(gl,right);
		vect3ToVertex(gl,top);
		vect3ToVertex(gl,left);
		gl.glEnd();
	}

	public int compareTo(Piece o) {
		return  Math.round(100*(this.getProf()-o.getProf()));
	}

	public float getProf() {
		return this.getN().dot(z);
	}

	public void traceVertexes(GL2 gl) {
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glColor4f(1f,0.7f,0.1f,0.6f);
		drawLeft(gl);
		gl.glColor4f(0.1f,0.7f,0.1f,0.5f);
		drawRight(gl);
		gl.glColor4f(0.5f,0.8f,0.1f,0.7f);
		drawTop(gl);
		gl.glEnd();
		gl.glColor4f(0.99f,0.9f,0.6f,0.9f);
		drawBorders(gl);
	}
}
