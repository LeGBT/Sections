/* 
 *    This file is part of Sections.
 *    Copyright © 2012 Alban Avenant
 *
 *     Sections is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     any later version.
 *     
 *     Sections is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *     
 *     You should have received a copy of the GNU General Public License
 *     along with Sections.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package fr.legbt.sections;

import javax.media.opengl.GL2;


public class Triangle implements Piece{
	protected Vecteur u;
	protected Vecteur v;
	protected Vecteur n;
	protected Vecteur ur;
	protected Vecteur vr;
	protected Vecteur nr;
	protected Vecteur np;
	private Vecteur left;
	private Vecteur right;
	private Vecteur top;
	private float cote;
	private float xrot;
	private float yrot;
	private float zrot;
	private float sommet;
	static Vecteur x = new Vecteur(1,0,0);
	static Vecteur y = new Vecteur(0,1,0);
	static Vecteur z = new Vecteur(0,0,1);

	public Triangle(float sommet, Vecteur u, Vecteur v, Vecteur n){
		this.u = new Vecteur(u);
		this.v = new Vecteur(v);
		this.n = new Vecteur(n);
		this.ur = new Vecteur(u);
		this.vr = new Vecteur(v);
		this.nr = new Vecteur(n);
		this.np = new Vecteur(n);
		this.xrot = 0;
		this.yrot = 0;
		this.zrot = 0;
		this.cote = 1;
		this.sommet = sommet;
	}

	public Triangle(float sommet, float c,Vecteur u, Vecteur v, Vecteur n){
		this(sommet,u,v,n);
		this.cote = c;
	}
	public Triangle(float sommet, float c,float xscale, float yscale, Vecteur n){
		this(sommet,new Vecteur(xscale,0,0),new Vecteur(0,yscale,0),n);
		this.cote = c;
	}

	static private float radian(float degree){
		return degree*0.017453292519943295769236907684f;
	}

	private void rotation(Matrice matrix){
		matrix.transform(ur);
		matrix.transform(vr);
		matrix.transform(nr);
	}

	public void resetRotation(){
		this.ur = new Vecteur(this.u);
		this.vr = new Vecteur(this.v);
		this.nr = new Vecteur(this.np);
	}

	public void xRotation(float degree){
		Matrice matrix = new Matrice();
		xrot += radian(degree);
		matrix.rotX(xrot);
		rotation(matrix);
	}
	public void yRotation(float degree){
		Matrice matrix = new Matrice();
		yrot += radian(degree);
		matrix.rotY(yrot);
		rotation(matrix);
	}
	public void zRotation(float degree){
		Matrice matrix = new Matrice();
		zrot += radian(degree);
		matrix.rotZ(zrot);
		rotation(matrix);
	}

	public void vect3ToVertex(GL2 gl, Vecteur b){
		float px = b.getX();
		float py = b.getY();
		float pz = b.getZ();
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
	public Vecteur getN()
	{
		return this.nr;
	}

	private void defineLeft(float off){
		left = new Vecteur(this.nr);
		left.sub(this.ur);
		left.sub(this.vr);
		left.scale(cote+off);
	}
	private void defineRight(float off){
		right = new Vecteur(this.nr);
		right.add(this.ur);
		right.sub(this.vr);
		right.scale(cote+off);
	}
	private void defineTop(float off){
		top = new Vecteur(this.ur);
		top.scale(sommet);
		top.add(this.nr);
		top.add(this.vr);
		top.scale(cote+off);
	}
	protected void drawLeft(GL2 gl,float off){
		defineLeft(off);
		vect3ToVertex(gl,left);
	}
	protected void drawRight(GL2 gl,float off){
		defineRight(off);
		vect3ToVertex(gl,right);
	}
	protected void drawTop(GL2 gl,float off){
		defineTop(off);
		vect3ToVertex(gl,top);
	}
	protected void traceBorders(GL2 gl,float off){
		defineTop(off);
		defineLeft(off);
		defineRight(off);
		gl.glBegin(GL2.GL_LINE_STRIP);
		gl.glTexCoord1f(0f);
		vect3ToVertex(gl,left);
		gl.glTexCoord1f(1f);
		vect3ToVertex(gl,right);
		gl.glTexCoord1f(0f);
		vect3ToVertex(gl,top);
		gl.glTexCoord1f(1f);
		vect3ToVertex(gl,left);
		gl.glEnd();
	}

	public int compareTo(Piece o) {
		return  Math.round(100*(this.getProf()-o.getProf()));
	}

	public float getProf() {
		return this.getN().getZ();
	}

	public void traceBorders(GL2 gl, float red,float green,float blue,float trans,float off){
		gl.glColor4f(red,green,blue,trans);
		traceBorders(gl,off);
	}

	public void traceMe(GL2 gl,float a,float b,float c,float d){
		System.out.println("Warning: no color suport for face.java");
		traceMe(gl);
	}

	public void traceMe(GL2 gl){
		traceMe(gl,0f);
	}

	public void traceMe(GL2 gl,float off) {
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glColor4f(1f,0.7f,0.1f,0.6f);
		drawLeft(gl,off);
		gl.glColor4f(0.1f,0.7f,0.1f,0.5f);
		drawRight(gl,off);
		gl.glColor4f(0.5f,0.8f,0.1f,0.7f);
		drawTop(gl,off);
		gl.glEnd();
	}
}
