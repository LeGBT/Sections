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

public class CylinderEdge extends Quad implements Piece{
	protected double angle;
	private Vecteur topleft;
	private Vecteur topright;
	private Vecteur bottomleft;
	private Vecteur bottomright;

	public CylinderEdge(Vecteur u, Vecteur v, Vecteur n){
		super(u,v,n);
	}

	public void reset(){
		this.angle = 0;
	}

	public void reset(double angle){
		this.angle = angle;
	}

	public double getH(){
		return this.np.length();
	}

	public int compareTo(Piece arg0){
		return 0;
	}

	public double getProf() {
		return 0;
	}


	protected void defineTopLeft(double off){
		topleft = new Vecteur(this.nr);
		topleft.sub(this.ur);
		topleft.add(this.vr);
		topleft.scale(1+off);
	}

	protected void defineTopRight(double off){
		topright = new Vecteur(this.nr);
		topright.add(this.ur);
		topright.add(this.vr);
		topright.scale(1+off);
	}
	protected void defineBottomLeft(double off){
		bottomleft = new Vecteur(this.nr);
		bottomleft.sub(this.ur);
		bottomleft.sub(this.vr);
		bottomleft.scale(1+off);
	}
	protected void defineBottomRight(double off){
		bottomright = new Vecteur(this.nr);
		bottomright.add(this.ur);
		bottomright.sub(this.vr);
		bottomright.scale(1+off);
	}

	public void traceMe(GL2 gl) {
		traceMe(gl,0.9f,0.9f,0.9f,0.9f);
	}

	public void traceMe(GL2 gl,double off){
		traceMe(gl,0.9f,0.9f,0.9f,0.9f);
	}

	public void traceMe(GL2 gl,double red,double green,double blue,double trans) {
		gl.glBegin(GL2.GL_LINES);
		gl.glColor4d(red,green,blue,trans);
		gl.glTexCoord1f(0f);
		defineTopLeft(0.005f);
		vect3ToVertex(gl,topleft);
		gl.glTexCoord1d(1);
		defineBottomLeft(0.005f);
		vect3ToVertex(gl,bottomleft);
		gl.glTexCoord1d(0);
		defineTopRight(0.005f);
		vect3ToVertex(gl,topright);
		gl.glTexCoord1d(1);
		defineBottomRight(0.005f);
		vect3ToVertex(gl,bottomright);
		gl.glEnd();
	}
}

