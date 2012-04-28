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

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class PyramideScene extends Scene{
	private Pyramide pyramide;

	public PyramideScene(Sections instance){
		super("pyl",instance);
		pyramide= new Pyramide();
		this.h = -4f;
	}

	public void reset(){}

	public void render(GL2 gl) {
		this.pyramide.resetRotation();
		this.pyramide.zRotation((float)theta/2);
		this.pyramide.xRotation((float)phi/2);
		this.plan.resetRotation();
		this.plan.setH(h);
		this.plan.zRotation((float)theta/2);
		this.plan.xRotation((float)phi/2);
		this.psection.resetRotation();
		this.psection.setH(h);
		this.psection.zRotation((float)theta/2);
		this.psection.xRotation((float)phi/2);
		this.pyramide.sort();

		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glTranslatef(0.325f,0,0);


		this.pyramide.traceCube(gl);
		this.plan.tracePlan(gl);
		gl.glDisable(GL.GL_DEPTH_TEST);
		if((this.plan.getH()<0.5f)){
			this.psection.traceVertexes(gl);
			this.psection.drawBorders(gl);
		}
		this.h = 0;

		if(firstrotation){
			this.theta = 0;
			this.phi = 0;
			firstrotation = false;
		}
	}

}
