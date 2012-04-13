package fr.legbt.sections;

import javax.media.opengl.GL2;
import javax.vecmath.Vector3f;

public class Plan extends Quad{
	private float h = 2;

	public Plan(){
		super(x,new Vector3f(0,0,-2),y);
	}

	public void Vect3ToVertex(GL2 gl, Vector3f b){
		float px = b.dot(x);
		float py = b.dot(y);
		float pz = b.dot(z);
		gl.glVertex3f(px,py,pz);
	}

	public void tracePlan(GL2 gl){
		gl.glBegin(GL2.GL_QUADS);

		gl.glColor4f(0f,0.2f,0.7f,0.8f);

		Vector3f topleft = new Vector3f(this.n);
		topleft.sub(this.u);
		topleft.add(this.v);
		Vect3ToVertex(gl,topleft);

		/* *******************
		 * Sommet bas gauche
		 * ******************/
		Vector3f bottomleft = new Vector3f(this.n);
		bottomleft.sub(this.u);
		bottomleft.sub(this.v);
		Vect3ToVertex(gl,bottomleft);

		/* *******************
		 * Sommet bas droite
		 * ******************/
		Vector3f bottomright = new Vector3f(this.n);
		bottomright.add(this.u);
		bottomright.sub(this.v);
		Vect3ToVertex(gl,bottomright);

		/* *******************
		 * Sommet haut droite
		 * ******************/
		Vector3f topright = new Vector3f(this.n);
		topright.add(this.v);
		topright.add(this.u);
		Vect3ToVertex(gl,topright);

		gl.glEnd();
	}
}
