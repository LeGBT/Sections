package fr.legbt.sections;

import javax.media.opengl.GL2;
import javax.vecmath.Vector3f;

public class Thales extends Quad {
	private Vector3f direction;
	private float hr = 1.5f;


	public Thales(Vector3f direction, Vector3f u, Vector3f v, Vector3f n){
		super(u,v,n);
		this.direction = direction ;

	}

	public void setH(float ph){
		this.np.scaleAdd(ph,this.direction,this.np);
		hr -= ph;
		Vector3f temp = new Vector3f(u);
		temp.scale(hr/5);	
		up.set(temp);
		temp.set(v);
		temp.scale(hr/5);	
		vp.set(temp);
	}


	public void traceVertexes(GL2 gl){
		/* *****************
		 * Tracé de la face
		 * ******************/
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4f(0.3f,0.2f,0.4f,0.6f);
		drawTopLeft(gl);
		drawBottomLeft(gl);
		drawBottomRight(gl);
		drawTopRight(gl);
		gl.glEnd();
		gl.glColor4f(0.9f,0.99f,0.9f,0.9f);

		/* *******************
		 * Tracé des arrêtes
		 * ******************/
		gl.glColor4f(0.99f,0.9f,0.6f,0.9f);
		drawBorders(gl);
	}
}
