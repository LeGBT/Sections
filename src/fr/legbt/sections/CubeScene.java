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

public class CubeScene extends Scene{
	private Cube cube;

	public CubeScene(Sections instance){
		super(1,1,instance);
		cube = new Cube();
	}

	private void renderFull(GL2 gl){
		gl.glClearColor(0.0f,0.0f,0.0f,0.0f);
		gl.glEnable(GL2.GL_FOG);
		this.cube.traceBorders(gl,0.99f,0.9f,0.6f,0.9f,0);
		this.cube.traceMe(gl);
		this.plan.tracePlan(gl);
		gl.glDisable(GL2.GL_DEPTH_TEST);
		this.section.tracePlan(gl);
		this.section.traceBorders(gl,0.9f,0.99f,0.9f,0.9f,0);
	}	

	private void renderVoid(GL2 gl){
		// tracé à vide pour les tests
		gl.glClearColor(1.0f,1.0f,1.0f,1.0f);
		gl.glDisable(GL2.GL_FOG);
		gl.glDepthFunc(GL2.GL_LESS);
		gl.glColorMask(false,false,false,false);
		this.cube.traceMe(gl);
		gl.glDepthFunc(GL2.GL_GREATER);

		//tracé des pointillés
		gl.glColorMask(true,true,true,true);
		gl.glLineWidth(2f);
		instance.getTextures().bind(gl,101);
		this.section.traceBorders(gl,0.99f);
		this.cube.traceBorders(gl,0.99f);
		instance.getTextures().unbind(gl);

		//tracé de la section
		gl.glDepthFunc(GL2.GL_LESS);
		gl.glLineWidth(3f);
		gl.glDisable(GL2.GL_DEPTH_TEST);
		instance.getTextures().bind(gl,201);
		this.section.tracePlan(gl);
		instance.getTextures().unbind(gl);

		//tracé du plan
		instance.getTextures().bind(gl,102);
		this.plan.tracePlan(gl,0.3f);
		gl.glEnable(GL2.GL_DEPTH_TEST);

		//tracé de bords visibles
		this.section.traceBorders(gl,0.99f);
		this.cube.traceBorders(gl,0.9f);
		instance.getTextures().unbind(gl);
	}


	public void render(GL2 gl){
		preRender();

		this.cube.resetRotation();
		this.plan.resetRotation();
		this.section.resetRotation();
		this.plan.setH(h);
		this.section.setH(h);

		this.plan.yRotation(0);
		this.section.yRotation(0);

		this.cube.zRotation((float)thetatot);
		this.plan.zRotation((float)thetatot);
		this.section.zRotation((float)thetatot);

		this.cube.xRotation(phitot);
		this.plan.xRotation((float)phitot);
		this.section.xRotation((float)phitot);

		this.cube.sort();

		gl.glEnable(GL2.GL_DEPTH_TEST);
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
			this.angle = 0;
			firstrotation = false;
		}
	}
}
