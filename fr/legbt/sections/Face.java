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

public class Face extends Quad implements Piece{

	public Face(Vector3f u, Vector3f v, Vector3f n){
		super(u,v,n);
	}

	public int compareTo(Piece f){
		//le 100 évite un "threathold effect" à cause de la conversion en int
		float pc =	100*(this.getProf() - f.getProf());
		return (int) pc;
	}

	public float getProf(){
		return this.getN().dot(z);
	}

	public void traceVertexes(GL2 gl){
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

		/* *******************
		 * Tracé des arrêtes
		 * ******************/
		gl.glColor4f(0.99f,0.9f,0.6f,0.9f);
		drawBorders(gl);
	}
}
