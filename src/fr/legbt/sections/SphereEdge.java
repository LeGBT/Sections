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

public class SphereEdge implements Piece{
	protected Vecteur u;
	protected Vecteur v;
	protected Vecteur n;
	protected Vecteur ur;
	protected Vecteur vr;
	protected Vecteur nr;
	protected Vecteur up;
	protected Vecteur vp;
	protected Vecteur np;
	protected float angle;
	private Vecteur topleft;
	private Vecteur topright;
	private Vecteur bottomleft;
	private Vecteur bottomright;
	private float xrot;
	private float yrot;
	private float zrot;
	static final Vecteur x = new Vecteur(1,0,0);
	static final Vecteur y = new Vecteur(0,1,0);
	static final Vecteur z = new Vecteur(0,0,1);
	static final int res = 64; 

	public SphereEdge(Vecteur u, Vecteur v, Vecteur n){
		this.u = new Vecteur(u);
		this.v = new Vecteur(v);
		this.n = new Vecteur(n);
		this.ur = new Vecteur(u);
		this.vr = new Vecteur(v);
		this.nr = new Vecteur(n);
		this.up = new Vecteur(u);
		this.vp = new Vecteur(v);
		this.np = new Vecteur(n);
		this.xrot = 0;
		this.yrot = 0;
		this.zrot = 0;
	}

	public void reset(){
		this.angle = 0;
	}

	public void reset(float angle){
		this.angle = angle;
	}

	public float getH(){
		return this.np.length();
	}

	static private float radian(float degree){
		return degree*0.017453292519943295769236907684f;
	}

	public int compareTo(Piece arg0){
		return 0;
	}

	public float getProf() {
		return 0;
	}

	public void resetRotation(){
		this.ur = new Vecteur(this.up);
		this.vr = new Vecteur(this.vp);
		this.nr = new Vecteur(this.np);
	}

	public void rotation(Matrice matrix){
		matrix.transform(ur);
		matrix.transform(vr);
		matrix.transform(nr);
	}

	public void xRotation(float degree) {
		Matrice matrix = new Matrice();
		xrot += radian(degree);
		matrix.rotX(xrot);
		rotation(matrix);
	}

	public void yRotation(float degree) {
		Matrice matrix = new Matrice();
		yrot += radian(degree);
		matrix.rotY(yrot+radian(angle));
		rotation(matrix);
	}

	public void zRotation(float degree) {
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

	private void defineTopLeft(float off){
		topleft = new Vecteur(this.nr);
		topleft.sub(this.ur);
		topleft.add(this.vr);
		topleft.scale(1+off);
	}

	private void defineTopRight(float off){
		topright = new Vecteur(this.nr);
		topright.add(this.ur);
		topright.add(this.vr);
		topright.scale(1+off);
	}
	private void defineBottomLeft(float off){
		bottomleft = new Vecteur(this.nr);
		bottomleft.sub(this.ur);
		bottomleft.sub(this.vr);
		bottomleft.scale(1+off);
	}
	private void defineBottomRight(float off){
		bottomright = new Vecteur(this.nr);
		bottomright.add(this.ur);
		bottomright.sub(this.vr);
		bottomright.scale(1+off);
	}

	public void traceMe(GL2 gl) {
		traceMe(gl,0.9f,0.9f,0.9f,0.9f);
	}

	public void traceMe(GL2 gl,float off) {
		traceMe(gl,0.9f,0.9f,0.9f,0.9f);
	}

	public void traceMe(GL2 gl,float red,float green,float blue,float trans){
		gl.glBegin(GL2.GL_LINES);
		gl.glColor4f(red,green,blue,trans);
		gl.glTexCoord1f(0f);
		defineTopLeft(0.005f);
		vect3ToVertex(gl,topleft);
		gl.glTexCoord1f(1f);
		defineBottomLeft(0.005f);
		vect3ToVertex(gl,bottomleft);
		gl.glTexCoord1f(0f);
		defineTopRight(0.005f);
		vect3ToVertex(gl,topright);
		gl.glTexCoord1f(1f);
		defineBottomRight(0.005f);
		vect3ToVertex(gl,bottomright);
		gl.glEnd();
	}
}

