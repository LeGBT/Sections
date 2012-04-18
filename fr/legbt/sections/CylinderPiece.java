package fr.legbt.sections;

import javax.media.opengl.GL2;
//import javax.media.opengl.GL;
import javax.vecmath.Vector3f;
import javax.vecmath.Matrix3f;

public class CylinderPiece extends Piece{
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
	static final Vector3f x = new Vector3f(1,0,0);
	static final Vector3f y = new Vector3f(0,1,0);
	static final Vector3f z = new Vector3f(0,0,1);

	public CylinderPiece(Vector3f u, Vector3f v, Vector3f n){
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
	}

	public CylinderPiece(float c,Vector3f u,Vector3f v,Vector3f n){
		this(u,v,n);
	}
	public CylinderPiece(float c,float xscale,float yscale, Vector3f n){
		this(new Vector3f(xscale,0,0),new Vector3f(0,yscale,0),n);
	}

	// main trace
	public void traceVertexes(GL2 gl){
		final int res = 256; 
		float radian = 6.28318531f/res;
		//	float radian = 3.1415926536f/res;


		Vector3f tempx = new Vector3f(ur);
		Vector3f tempy = new Vector3f(vr);

		int fix = (int) Math.floor(nr.dot(y))*2 + 1;
		int fiy = (int) Math.floor(ur.dot(y))*2 + 1;
		int fiz = (int) Math.floor(nr.dot(z))*2 + 1;
		int firstposition = (int) Math.round(Math.acos(-fiz*fiy*ur.dot(x))/radian);
		// wtf bug !
		if(fix==3){fix=1;}


		tempx.set(ur);
		tempy.set(vr);
		tempx.scale((float)Math.cos((double)radian*firstposition));
		tempy.scale((float)Math.sin((double)radian*firstposition));
		Vector3f top = new Vector3f();
		Vector3f bottom = new Vector3f();
		top.add(tempx);
		top.add(tempy);
		top.add(this.nr);
		bottom.add(tempx);
		bottom.add(tempy);
		bottom.sub(this.nr);

		//gl.glColor4f(1,1,0.9f,0.9f);
		//gl.glBegin(GL2.GL_LINES);
		//vect3ToVertex(gl,new Vector3f(0,0,0));
		//vect3ToVertex(gl,ur);
		//gl.glEnd();

		//gl.glColor4f(0,1,0.9f,0.9f);
		//gl.glBegin(GL2.GL_LINES);
		//vect3ToVertex(gl,new Vector3f(0,0,0));
		//vect3ToVertex(gl,x);
		//gl.glEnd();


		gl.glBegin(GL2.GL_QUAD_STRIP);



		float para = 3f*(((float)firstposition)/((float)res) - ((float)(firstposition)*(firstposition))/((float)(res*res)));
		//		float para;
		//if(firstposition<res/2){para = firstposition/res;}else{para = (1-firstposition/res);}
		para = firstposition/res;
		gl.glColor4f(0,0.8f,0.2f,0.4f);
		vect3ToVertex(gl,top);
		vect3ToVertex(gl,bottom);
		int j;

		for(int i=0;i<res;i++){
			j = (-fix*fiy*fiz*i+firstposition);
			if(j>res){j-=res;}
			if(j<0){j+=res;}

			if(j<res/2){para = 2*j/(float)res;}else{para = 2*(1-j/(float)res);}
		//	para = 3f*(((float)j+1)/((float)res) - ((float)(j+1)*(j+1))/((float)(res*res)));
			gl.glColor4f(para,0.8f,0.2f,para/8f+0.4f);

			vect3ToVertex(gl,top);
			vect3ToVertex(gl,bottom);

			tempx.set(ur);
			tempy.set(vr);
			tempx.scale((float)Math.cos((double)radian*(j+2)));
			tempy.scale((float)Math.sin((double)radian*(j+2)));
			top.scale(0);
			bottom.scale(0);
			top.add(tempx);
			top.add(tempy);
			top.add(this.nr);
			bottom.add(tempx);
			bottom.add(tempy);
			bottom.sub(this.nr);
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
		//	Vector3f temp = new Vector3f();
		//	temp.add(ur);
		//	temp.add(vr);
		//	temp.scale(-0.5f);
		//	return temp.dot(z);
		return 2f;
	}

	protected void drawBorders(GL2 gl){
	}

	public int compareTo(Piece p) {
		//le 100 évite un "threathold effect" à cause de la conversion en int
		float pc =	1000*(this.getProf() - p.getProf());
		return (int) pc;
	}
}
