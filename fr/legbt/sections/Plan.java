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

public class Plan extends Quad{
	private static float h = 0.4f;
	private boolean border;

	public Plan(){
		super(2,x,y,new Vector3f(0,0,h/2));
		border = false;
	}

	public Plan(float s, float xscale, float yscale){
		super(s,xscale,yscale,new Vector3f(0,0,h/s));
		border = true;
	}


	public void setH(float ph){
		//	if(ph!=0){
		this.np.scaleAdd(ph,n,this.np);
		//	}
	}

	public void tracePlan(GL2 gl){
		gl.glBegin(GL2.GL_QUADS);

		if(border){
			gl.glColor4f(0.3f,0.2f,0.4f,0.6f);
		}else{
			gl.glColor4f(0f,0.2f,0.5f,0.8f);
		}
		drawTopLeft(gl);
		drawBottomLeft(gl);
		drawBottomRight(gl);
		drawTopRight(gl);
		gl.glEnd();
		if(border){
			gl.glColor4f(0.9f,0.99f,0.9f,0.9f);
			drawBorders(gl);
		}
	}
}
