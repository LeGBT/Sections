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

public class RotatingSection extends Plan{
	private float hr = 1.5f;
	private Sections instance;
	private float xscale;
	private float yscale;

	public RotatingSection(float s, float xscale, float yscale, Sections instance){
		super(s,xscale,yscale);
		this.instance = instance;
		this.xscale = xscale;
		this.yscale = yscale;
	}

	@Override
		public void reset(){
			super.reset();
			vp.set(u);
		}


	@Override
		public void setH(float ph){
			this.np.scaleAdd(ph,n,this.np);
			if(!instance.isPlantype()){
				hr -= ph;
				Vector3f temp = new Vector3f(u);
				double ang = radian(angle);
				double len = Math.abs(hr-2.5f)/2.5f;
				double dx = len/Math.cos(ang)-Math.tan(ang)-1;
				double dy = len/Math.sin(ang)-1/Math.tan(ang)-1;
				//				FIXME debug
				System.out.println("dx="+dx+" dy="+dy);
				double lon = Math.sqrt(dx*dx+dy*dy)/2f;
				temp.scale((float)lon);
				up.set(temp);
			}
		}

	static private float radian(float degree){
		return degree*0.017453292519943295769236907684f;
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
		gl.glColor4f(0.99f,0.9f,0.6f,0.9f);
		drawBorders(gl);
	}

}
