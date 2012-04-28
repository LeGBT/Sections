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
import javax.vecmath.Vector3f;

public class Thales extends Quad implements Piece,Bordered {
	private Vector3f direction;
	private float hr = 1.5f;
	private boolean cylinderthales;


	public Thales(Vector3f direction, Vector3f u, Vector3f v, Vector3f n){
		super(u,v,n);
		this.direction = direction ;
		this.cylinderthales = false;
	}

	public void setH(float ph){
		this.np.scaleAdd(ph,this.direction,this.np);
		hr -= ph;
		Vector3f temp = new Vector3f(u);
		if(cylinderthales){
		//	double dist = Math.abs(0.5f-hr/5f);
			double dist = 0.5f - hr/5f;
			float rayon = (float) Math.sqrt(-dist*dist+0.25f);
			temp.scale(rayon*2f);	
			up.set(temp);
		}else{
			temp.scale(hr/5);	
			up.set(temp);
			temp.set(v);
			temp.scale(hr/5);	
			vp.set(temp);
		}	
	}


	public void traceVertexes(GL2 gl){
		/* *****************
		 * Tracé de la face
		 * ******************/
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4f(0.3f,0.2f,0.4f,0.6f);
		drawTopLeft(gl);
		drawBottomLeft(gl);
		drawBottomRight(gl);
		drawTopRight(gl);
		gl.glEnd();
		gl.glColor4f(0.9f,0.99f,0.9f,0.9f);

		/* *******************
		 * Tracé des arrêtes
		 * ******************/
		gl.glColor4f(0.9f,0.99f,0.9f,0.9f);
		drawBorders(gl);
	}


	public int compareTo(Piece arg0) {
		return 0;
	}

	public float getProf() {
		return 0;
	}


	public void setBorder(boolean b){}
	public void setSphere(boolean b){}

	/**
	 * Determines if this instance is cylinderthales.
	 *
	 * @return The cylinderthales.
	 */
	public boolean isCylinderthales()
	{
		return this.cylinderthales;
	}

	/**
	 * Sets whether or not this instance is cylinderthales.
	 *
	 * @param cylinderthales The cylinderthales.
	 */
	public void setCylinderthales(boolean cylinderthales)
	{
		this.cylinderthales = cylinderthales;
	}
}
