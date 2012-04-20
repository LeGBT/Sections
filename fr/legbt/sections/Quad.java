/* 
 *    This file is part of Sections.
 *    Copyright © 2012 Alban Avenant
 *
 *     Sections is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
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
import javax.vecmath.Vector3f;
import javax.vecmath.Matrix3f;

public class Quad{
	protected Vector3f u;
	protected Vector3f v;
	protected Vector3f n;
	protected Vector3f ur;
	protected Vector3f vr;
	protected Vector3f nr;
	protected Vector3f up;
	protected Vector3f vp;
	protected Vector3f np;
	private Vector3f topleft;
	private Vector3f topright;
	private Vector3f bottomleft;
	private Vector3f bottomright;
	private float cote;
	private float xrot;
	private float yrot;
	private float zrot;
	static Vector3f x = new Vector3f(1,0,0);
	static Vector3f y = new Vector3f(0,1,0);
	static Vector3f z = new Vector3f(0,0,1);

	public Quad(Vector3f u, Vector3f v, Vector3f n){
		this.u = new Vector3f(u);
		this.v = new Vector3f(v);
		this.n = new Vector3f(n);
		this.ur = new Vector3f(u);
		this.vr = new Vector3f(v);
		this.nr = new Vector3f(n);
		this.up = new Vector3f(u);
		this.vp = new Vector3f(v);
		this.np = new Vector3f(n);
		this.xrot = 0;
		this.yrot = 0;
		this.zrot = 0;
		this.cote = 1;
	}

	public Quad(float c,Vector3f u, Vector3f v, Vector3f n){
		this(u,v,n);
		this.cote = c;
	}
	public Quad(float c,float xscale, float yscale, Vector3f n){
		this(new Vector3f(xscale,0,0),new Vector3f(0,yscale,0),n);
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
		this.ur = new Vector3f(this.up);
		this.vr = new Vector3f(this.vp);
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

	/**
	 * Gets the cote for this instance.
	 *
	 * @return The cote.
	 */
	public float getCote()
	{
		return this.cote;
	}

	/**
	 * Sets the cote for this instance.
	 *
	 * @param cote The cote.
	 */
	public void setCote(float cote)
	{
		this.cote = cote;
	}

	private void defineTopLeft(){
		topleft = new Vector3f(this.nr);
		topleft.sub(this.ur);
		topleft.add(this.vr);
		topleft.scale(cote);
	}
	private void defineTopRight(){
		topright = new Vector3f(this.nr);
		topright.add(this.ur);
		topright.add(this.vr);
		topright.scale(cote);
	}
	private void defineBottomLeft(){
		bottomleft = new Vector3f(this.nr);
		bottomleft.sub(this.ur);
		bottomleft.sub(this.vr);
		bottomleft.scale(cote);
	}
	private void defineBottomRight(){
		bottomright = new Vector3f(this.nr);
		bottomright.add(this.ur);
		bottomright.sub(this.vr);
		bottomright.scale(cote);
	}
	protected void drawTopLeft(GL2 gl){
		defineTopLeft();
		vect3ToVertex(gl,topleft);
	}
	protected void drawTopRight(GL2 gl){
		defineTopRight();
		vect3ToVertex(gl,topright);
	}
	protected void drawBottomLeft(GL2 gl){
		defineBottomLeft();
		vect3ToVertex(gl,bottomleft);
	}
	protected void drawBottomRight(GL2 gl){
		defineBottomRight();
		vect3ToVertex(gl,bottomright);
	}
	protected void drawBorders(GL2 gl){
		defineTopLeft();
		defineTopRight();
		defineBottomLeft();
		defineBottomRight();
		gl.glBegin(GL2.GL_LINES);
		vect3ToVertex(gl,topleft);
		vect3ToVertex(gl,bottomleft);
		gl.glEnd();
		gl.glBegin(GL2.GL_LINES);
		vect3ToVertex(gl,bottomleft);
		vect3ToVertex(gl,bottomright);
		gl.glEnd();
		gl.glBegin(GL2.GL_LINES);
		vect3ToVertex(gl,bottomright);
		vect3ToVertex(gl,topright);
		gl.glEnd();
		gl.glBegin(GL2.GL_LINES);
		vect3ToVertex(gl,topright);
		vect3ToVertex(gl,topleft);
		gl.glEnd();
	}
}
