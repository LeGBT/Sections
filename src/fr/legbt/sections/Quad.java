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

public class Quad{
	protected Vecteur u;
	protected Vecteur v;
	protected Vecteur n;
	protected Vecteur u0;
	protected Vecteur v0;
	protected Vecteur n0;
	protected Vecteur ur;
	protected Vecteur vr;
	protected Vecteur nr;
	protected Vecteur up;
	protected Vecteur vp;
	protected Vecteur np;
	protected Vecteur np0;
	protected Vecteur uoff;
	protected float angle;
	protected Vecteur topleft;
	protected Vecteur topright;
	protected Vecteur bottomleft;
	protected Vecteur bottomright;
	private float cote;
	protected double xrot;
	protected double yrot;
	protected double zrot;
	static Vecteur x = new Vecteur(1,0,0);
	static Vecteur y = new Vecteur(0,1,0);
	static Vecteur z = new Vecteur(0,0,1);

	public Quad(Vecteur u, Vecteur v, Vecteur n){
		this.u = new Vecteur(u);
		this.v = new Vecteur(v);
		this.n = new Vecteur(n);
		this.u0 = new Vecteur(u);
		this.v0 = new Vecteur(v);
		this.n0 = new Vecteur(n);
		this.np0 = new Vecteur(n);
		this.ur = new Vecteur(u);
		this.vr = new Vecteur(v);
		this.nr = new Vecteur(n);
		this.up = new Vecteur(u);
		this.vp = new Vecteur(v);
		this.np = new Vecteur(n);
		this.uoff = new Vecteur();
		this.xrot = 0;
		this.yrot = 0;
		this.zrot = 0;
		this.cote = 1;
		this.angle = 0;
	}

	public Quad(float c,Vecteur u, Vecteur v, Vecteur n){
		this(u,v,n);
		this.cote = c;
	}
	public Quad(float c,float xscale, float yscale, Vecteur n){
		this(new Vecteur(xscale,0,0),new Vecteur(0,yscale,0),n);
		this.cote = c;
	}


	static private double radian(double degree){
		return degree*0.017453292519943295769236907684f;
	}

	private void rotation(Matrice matrix){
		matrix.transform(uoff);
		matrix.transform(ur);
		matrix.transform(vr);
		matrix.transform(nr);
	}


	public void reset(){
		this.angle = 0;
	}

	public void reset(float angle){
		this.angle = angle;
	}


	public void resetRotation(){
		this.ur = new Vecteur(this.up);
		this.vr = new Vecteur(this.vp);
		this.nr = new Vecteur(this.np);
	}

	public void xRotation(float rad){
		Matrice matrix = new Matrice();
		xrot = rad;
		matrix.rotX((float)xrot);
		rotation(matrix);
	}
	public void yRotation(float rad){
		Matrice matrix = new Matrice();
		yrot = rad;
		matrix.rotY((float)(yrot+radian(angle)));
		rotation(matrix);
	}
	public void zRotation(float rad){
		Matrice matrix = new Matrice();
		zrot = rad;
		matrix.rotZ((float)zrot);
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

	public Vecteur getN(){return this.nr;}
	public Vecteur getUoff(){return this.uoff;}
	public void setUoff(Vecteur uoff){this.uoff = uoff;}
	public float getCote(){return this.cote;}
	public void setCote(float cote){this.cote = cote;}

	protected void defineTopLeft(float off){
		topleft = new Vecteur(this.nr);
		topleft.sub(this.ur);
		topleft.add(this.uoff);
		topleft.add(this.vr);
		topleft.scale(cote);
		topleft.scale(1+off);
	}
	protected void defineTopRight(float off){
		topright = new Vecteur(this.nr);
		topright.add(this.ur);
		topright.add(this.uoff);
		topright.add(this.vr);
		topright.scale(cote);
		topright.scale(1+off);
	}
	protected void defineBottomLeft(float off){
		bottomleft = new Vecteur(this.nr);
		bottomleft.sub(this.ur);
		bottomleft.add(this.uoff);
		bottomleft.sub(this.vr);
		bottomleft.scale(cote);
		bottomleft.scale(1+off);
	}
	protected void defineBottomRight(float off){
		bottomright = new Vecteur(this.nr);
		bottomright.add(this.ur);
		bottomright.add(this.uoff);
		bottomright.sub(this.vr);
		bottomright.scale(cote);
		bottomright.scale(1+off);
	}
	protected void drawTopLeft(GL2 gl){
		defineTopLeft(0);
		vect3ToVertex(gl,topleft);
	}
	protected void drawTopRight(GL2 gl){
		defineTopRight(0);
		vect3ToVertex(gl,topright);
	}
	protected void drawBottomLeft(GL2 gl){
		defineBottomLeft(0);
		vect3ToVertex(gl,bottomleft);
	}
	protected void drawBottomRight(GL2 gl){
		defineBottomRight(0);
		vect3ToVertex(gl,bottomright);
	}

	public void drawBorders(GL2 gl,float off){
	 float a = 1f;

		defineTopLeft(off);
		defineTopRight(off);
		defineBottomLeft(off);
		defineBottomRight(off);
		gl.glBegin(GL2.GL_LINE_STRIP);
		gl.glTexCoord1f(0f);
		vect3ToVertex(gl,topleft);
		gl.glTexCoord1f(a);
		vect3ToVertex(gl,bottomleft);
		gl.glTexCoord1f(0f);
		vect3ToVertex(gl,bottomright);
		gl.glTexCoord1f(a);
		vect3ToVertex(gl,topright);
		gl.glTexCoord1f(0f);
		vect3ToVertex(gl,topleft);
		gl.glEnd();
	}
}
