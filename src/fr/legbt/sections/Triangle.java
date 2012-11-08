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
	private double cote;
	private double xrot;
	private double yrot;
	private double zrot;
	private double sommet;
	static Vecteur x = new Vecteur(1,0,0);
	static Vecteur y = new Vecteur(0,1,0);
	static Vecteur z = new Vecteur(0,0,1);

	public Triangle(double sommet, Vecteur u, Vecteur v, Vecteur n){
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

	public Triangle(double sommet, double c,Vecteur u, Vecteur v, Vecteur n){
		this(sommet,u,v,n);
		this.cote = c;
	}
	public Triangle(double sommet, double c,double xscale, double yscale, Vecteur n){
		this(sommet,new Vecteur(xscale,0,0),new Vecteur(0,yscale,0),n);
		this.cote = c;
	}

	public void rotation(Quaternion quat){
		ur.rotate(quat);	
		vr.rotate(quat);	
		nr.rotate(quat);	
	}

	public void resetRotation(){
		this.ur = new Vecteur(this.u);
		this.vr = new Vecteur(this.v);
		this.nr = new Vecteur(this.np);
	}

	public void Rotation(Quaternion quat){
		rotation(quat);
	}

	public void vect3ToVertex(GL2 gl, Vecteur b){
		double px = b.X();
		double py = b.Y();
		double pz = b.Z();
		gl.glVertex3d(px,py,pz);
	}


	public double getH(){
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

	private void defineLeft(double off){
		left = new Vecteur(this.nr);
		left.sub(this.ur);
		left.sub(this.vr);
		left.scale(cote+off);
	}
	private void defineRight(double off){
		right = new Vecteur(this.nr);
		right.add(this.ur);
		right.sub(this.vr);
		right.scale(cote+off);
	}
	private void defineTop(double off){
		top = new Vecteur(this.ur);
		top.scale(sommet);
		top.add(this.nr);
		top.add(this.vr);
		top.scale(cote+off);
	}
	protected void drawLeft(GL2 gl,double off){
		defineLeft(off);
		vect3ToVertex(gl,left);
	}
	protected void drawRight(GL2 gl,double off){
		defineRight(off);
		vect3ToVertex(gl,right);
	}
	protected void drawTop(GL2 gl,double off){
		defineTop(off);
		vect3ToVertex(gl,top);
	}
	protected void traceBorders(GL2 gl,double off){
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
		return  (int)Math.round(100*(this.getProf()-o.getProf()));
	}

	public double getProf() {
		return this.getN().Z();
	}

	public void traceBorders(GL2 gl, double red,double green,double blue,double trans,double off){
		gl.glColor4d(red,green,blue,trans);
		traceBorders(gl,off);
	}

	public void traceMe(GL2 gl,double a,double b,double c,double d){
		System.out.println("Warning: no color suport for face.java");
		traceMe(gl);
	}

	public void traceMe(GL2 gl){
		traceMe(gl,0f);
	}

	public void traceMe(GL2 gl,double off) {
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
