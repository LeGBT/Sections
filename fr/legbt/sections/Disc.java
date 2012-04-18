package fr.legbt.sections;

import javax.media.opengl.GL2;
import javax.media.opengl.GL;
import javax.vecmath.Vector3f;
import javax.vecmath.Matrix3f;

public class Disc implements Piece {
	protected Vector3f u;
	protected Vector3f v;
	protected Vector3f n;
	protected Vector3f ur;
	protected Vector3f vr;
	protected Vector3f nr;
	protected Vector3f np;
	private float xrot;
	private float yrot;
	private float zrot;
	private boolean border;
	static final Vector3f x = new Vector3f(1,0,0);
	static final Vector3f y = new Vector3f(0,1,0);
	static final Vector3f z = new Vector3f(0,0,1);
	static final int res = 256;
	static final float radian = 6.28318531f/res;


	public Disc(Vector3f u, Vector3f v, Vector3f n){
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
		this.border = false;
	}

	public Disc(float c,Vector3f u,Vector3f v,Vector3f n){
		this(u,v,n);
	}
	public Disc(float c,float xscale,float yscale, Vector3f n){
		this(new Vector3f(xscale,0,0),new Vector3f(0,yscale,0),n);
	}

	// main trace
	public void traceVertexes(GL2 gl){
		Vector3f center = new Vector3f(this.nr);
		Vector3f tempx = new Vector3f(ur);
		Vector3f tempy = new Vector3f(vr);
		Vector3f first = new Vector3f(ur);
		first.add(this.nr);

		tempx.set(ur);
		tempy.set(vr);
		tempx.scale((float)Math.cos((double)radian));
		tempy.scale((float)Math.sin((double)radian));
		Vector3f second = new Vector3f();
		second.add(tempx);
		second.add(tempy);
		second.add(this.nr);

		gl.glBegin(GL.GL_TRIANGLE_FAN);

		float para = 0;
		if(border){
			gl.glColor4f(0.3f,0.2f,0.4f,0.6f);
		}else{
			gl.glColor4f(para,0.8f,0.2f,0.7f);
		}

		vect3ToVertex(gl,center);
		vect3ToVertex(gl,first);


		for(int i=0;i<res;i++){
			if(border){
			}else{
			if(i<res/2){para = 2*i/(float)res;}else{para = 2*(1-i/(float)res);}
			//	para = 3f*(((float)i+1)/((float)res) - ((float)(i+1)*(i+1))/((float)(res*res)));
				gl.glColor4f(para,0.8f,0.2f,0.7f);
			}

			vect3ToVertex(gl,second);

			tempx.set(ur);
			tempy.set(vr);
			tempx.scale((float)Math.cos((double)radian*(i+2)));
			tempy.scale((float)Math.sin((double)radian*(i+2)));
			second = new Vector3f();
			second.add(tempx);
			second.add(tempy);
			second.add(this.nr);
		}
		gl.glEnd();

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

	public void setH(float ph){
		this.np.scaleAdd(ph,n,this.np);
	}

	public float getH(){
		return this.np.length();
	}

	/**
	 * Gets the normal vector for this face.
	 *
	 * @return The n.
	 */
	public float getProf()
	{
		return this.nr.dot(z);
	}

	protected void drawBorders(GL2 gl){
		gl.glBegin(GL2.GL_LINE_STRIP);
		gl.glColor4f(0.9f,0.8f,0.9f,0.99f);
		Vector3f tempx = new Vector3f(ur);
		Vector3f tempy = new Vector3f(vr);
		Vector3f first = new Vector3f(ur);
		first.add(this.nr);

		tempx.set(ur);
		tempy.set(vr);
		tempx.scale((float)Math.cos((double)radian));
		tempy.scale((float)Math.sin((double)radian));
		Vector3f second = new Vector3f();
		second.add(tempx);
		second.add(tempy);
		second.add(this.nr);


		vect3ToVertex(gl,first);


		for(int i=0;i<res;i++){
			vect3ToVertex(gl,second);
			tempx.set(ur);
			tempy.set(vr);
			tempx.scale((float)Math.cos((double)radian*(i+2)));
			tempy.scale((float)Math.sin((double)radian*(i+2)));
			second = new Vector3f();
			second.add(tempx);
			second.add(tempy);
			second.add(this.nr);
		}
		gl.glEnd();


		gl.glEnd();
	}

	public int compareTo(Piece d) {
		//le 100 évite un "threathold effect" à cause de la conversion en int
		float pc =	100*(this.getProf() - d.getProf());
		return (int) pc;
	}

	/**
	 * Determines if this instance is border.
	 *
	 * @return The border.
	 */
	public boolean isBorder()
	{
		return this.border;
	}

	/**
	 * Sets whether or not this instance is border.
	 *
	 * @param border The border.
	 */
	public void setBorder(boolean border)
	{
		this.border = border;
	}
}
