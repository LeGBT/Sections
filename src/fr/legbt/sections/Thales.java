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

public class Thales extends Quad implements Piece,Bordered {
	private Vecteur direction;
	private double hr = 1.5f;
	private boolean cylinderthales;


	public Thales(Vecteur direction, Vecteur u, Vecteur v, Vecteur n){
		super(u,v,n);
		this.direction = direction ;
		this.cylinderthales = false;
	}

	public void setH(double ph){
		//this.np.scaleAdd(ph,this.direction,this.np);
		np.set(this.direction);
		np.scale(ph);
		np.add(np0);
		hr = 1.5f - ph;
		Vecteur temp = new Vecteur(u);
		if(cylinderthales){
			double dist = 0.5f - hr/5f;
			double rayon = (double) Math.sqrt(-dist*dist+0.25f);
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

	public void traceMe(GL2 gl){
		traceMe(gl,0.4f,0.4f,0.4f,0.4f);
	}

	public void traceMe(GL2 gl,double off){
		traceMe(gl,0.4f,0.4f,0.4f,0.4f);
	}


	public void traceMe(GL2 gl,double red,double blue,double green,double trans){
		/* *****************
		 * Tracé de la face
		 * ******************/
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4d(red,blue,green,trans);
		gl.glTexCoord2d(0,0);
		drawTopLeft(gl);
		gl.glTexCoord2d(1,0);
		drawBottomLeft(gl);
		gl.glTexCoord2d(1,1);
		drawBottomRight(gl);
		gl.glTexCoord2d(0,1);
		drawTopRight(gl);
		gl.glEnd();
		gl.glColor4f(0.9f,0.99f,0.9f,0.9f);
	}

	public void traceBorders(GL2 gl, double red){
		traceBorders(gl,red,0.005f);
	}

	public void traceBorders(GL2 gl, double red, double off){
		/* *******************
		 * Tracé des arrêtes
		 * ******************/
		gl.glColor4d(red,red,red,red);
		drawBorders(gl,off);
	}

	public int compareTo(Piece arg0) {
		return 0;
	}

	public double getProf() {
		return 0;
	}

	public void setBorder(boolean b){}
	public void setSphere(boolean b){}

	public boolean isCylinderthales(){
		return this.cylinderthales;
	}

	public void setCylinderthales(boolean cylinderthales){
		this.cylinderthales = cylinderthales;
	}
}
