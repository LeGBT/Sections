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


public class PaveScene extends Scene{
	private Pave pave;

	public PaveScene(Sections instance){
		super(1.5f,0.7f,instance);
		pave = new Pave();
	}

	private void renderFull(GL2 gl){
		gl.glClearColor(0.0f,0.0f,0.0f,0.0f);
		gl.glEnable(GL2.GL_FOG);
		this.pave.traceBorders(gl,0.99f,0.9f,0.6f,0.9f,0);
		this.pave.traceMe(gl);
		this.plan.tracePlan(gl);
		gl.glDisable(GL.GL_DEPTH_TEST);
		this.section.tracePlan(gl);
		this.section.traceBorders(gl,0.9f,0.99f,0.9f,0.9f,0);
	}

	private void renderVoid(GL2 gl){
		gl.glClearColor(1.0f,1.0f,1.0f,1.0f);
		gl.glDisable(GL2.GL_FOG);
		gl.glDepthFunc(GL.GL_LESS);
		gl.glColorMask(false,false,false,false);
		this.pave.traceMe(gl);
		gl.glDepthFunc(GL.GL_GREATER);

		gl.glColorMask(true,true,true,true);
		gl.glLineWidth(2f);
		instance.getTextures().bind(gl,101);
		this.section.traceBorders(gl,0.99f);
		this.pave.traceBorders(gl,0.99f);
		instance.getTextures().unbind(gl);

		gl.glDepthFunc(GL.GL_LESS);
		gl.glLineWidth(3f);

		gl.glDisable(GL2.GL_DEPTH_TEST);
		instance.getTextures().bind(gl,201);
		this.section.tracePlan(gl);
		instance.getTextures().unbind(gl);

		instance.getTextures().bind(gl,102);
		this.plan.tracePlan(gl,0.3f);
		gl.glEnable(GL2.GL_DEPTH_TEST);

		this.section.traceBorders(gl,0.99f);
		this.pave.traceBorders(gl,0.9f);
		instance.getTextures().unbind(gl);
	}

	public void render(GL2 gl) {
		preRender();

		this.pave.resetRotation();
		this.plan.resetRotation();
		this.section.resetRotation();
		this.plan.setH(htot);
		this.section.setH(htot);

		this.pave.rotation(rquat);
		this.plan.rotation(rquat);
		this.section.rotation(rquat);

		this.pave.sort();

		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glTranslatef(0.325f,0,0);

		if(instance.isBonemode()){
			renderVoid(gl);
		}else{
			renderFull(gl);
		}
		this.h = 0;

		if(firstrotation){
			this.theta = 0;
			this.phi = 0;
			firstrotation = false;
		}
	}
}
