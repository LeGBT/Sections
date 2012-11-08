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

public class Face extends Quad implements Piece{

	public Face(Vecteur u, Vecteur v, Vecteur n){
		super(u,v,n);
	}

	public int compareTo(Piece f){
		//le 100 évite un "threathold effect" à cause de la conversion en int
		double pc =	100*(this.getProf() - f.getProf());
		return (int) pc;
	}

	public double getProf(){
		return this.getN().Z();
	}

	public void traceMe(GL2 gl,double off){
		traceMe(gl);
	}

	public void traceMe(GL2 gl,double a,double b,double c,double d){
		System.out.println("Warning: no color suport for face.java");
		traceMe(gl);
	}

	public void traceMe(GL2 gl){
		/* *****************
		 * Tracé de la face
		 * ******************/
		gl.glBegin(GL2.GL_QUADS);

		gl.glColor4f(1f,0.7f,0.1f,0.6f);
		drawTopLeft(gl);

		gl.glColor4f(0.1f,0.7f,0.1f,0.5f);
		drawBottomLeft(gl);
		drawBottomRight(gl);

		gl.glColor4f(0.5f,0.8f,0.1f,0.7f);
		drawTopRight(gl);

		gl.glEnd();
	}

	public void traceBorders(GL2 gl,double red,double green,double blue,double trans,double off){
		/* *******************
		 * Tracé des arrêtes
		 * ******************/
		gl.glColor4d(red,green,blue,trans);
		drawBorders(gl,off);

	}
	public void traceBorders(GL2 gl,double red){
		traceBorders(gl,red,red,red,red,0.005f);
	}
}
