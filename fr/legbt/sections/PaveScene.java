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

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class PaveScene extends Scene{
	private Pave pave;

	public PaveScene(Sections instance){
		super(1.5f,0.7f,instance);
		pave = new Pave();
	}

	public void render(GL2 gl) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		int s = 2;
		gl.glOrtho(-s*1.6,s*1.6,-s*0.9,s*0.9,-s-1,s+1);
		this.pave.resetRotation();
		this.pave.zRotation((float)theta/2);
		this.pave.xRotation((float)phi/2);
		this.plan.resetRotation();
		this.plan.setH(h);
		this.plan.yRotation(0);
		this.plan.zRotation((float)theta/2);
		this.plan.xRotation((float)phi/2);
		this.section.resetRotation();
		this.section.setH(h);
		this.section.yRotation(0);
		this.section.zRotation((float)theta/2);
		this.section.xRotation((float)phi/2);
		this.pave.sort();

		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glTranslatef(0.325f,0,0);


		this.pave.traceCube(gl);
		this.plan.tracePlan(gl);
		gl.glDisable(GL.GL_DEPTH_TEST);
		if((this.section.getH()<1)&&(this.section.getH()>-1)){
			this.section.tracePlan(gl);
		}
		this.h = 0;

		if(firstrotation){
			this.theta = 0;
			this.phi = 0;
			firstrotation = false;
		}
	}

}
