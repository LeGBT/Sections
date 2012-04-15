package fr.legbt.sections;

import javax.media.opengl.GL2;
import javax.vecmath.Vector3f;

public class Face extends Quad implements Comparable<Face>{

	public Face(Vector3f u, Vector3f v, Vector3f n){
		super(u,v,n);
	}

	public int compareTo(Face f){
		//le 100 évite un "threathold effect" à cause de la conversion en int
		float pc =	100*(this.getN().dot(z) - f.getN().dot(z));
		return (int) pc;
	}

	public void traceVertexes(GL2 gl){
		/* *****************
		 * Tracé de la face
		 * ******************/
		gl.glBegin(GL2.GL_QUADS);

		gl.glColor4f(1f,0.7f,0.1f,0.6f);
		drawTopLeft(gl);

		gl.glColor4f(0.1f,0.7f,0.1f,0.4f);
		drawBottomLeft(gl);
		drawBottomRight(gl);

		gl.glColor4f(0.5f,0.8f,0.1f,0.7f);
		drawTopRight(gl);

		gl.glEnd();

		/* *******************
		 * Tracé des arrêtes
		 * ******************/
		gl.glColor4f(0.99f,0.9f,0.6f,0.9f);
		drawBorders(gl);
	}
}
