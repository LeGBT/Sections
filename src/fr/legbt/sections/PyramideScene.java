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
		psection = new Thales(new Vecteur(0.2f,0.1f,0.4f),new Vecteur(1.5f,0,0),y,new Vecteur(0.7f,0.35f,0.4f));
		pyramide= new Pyramide();
		this.h = -4f;
	}

	public void reset(){}

	private void renderFull(GL2 gl){
		gl.glClearColor(0.0f,0.0f,0.0f,0.0f);
		gl.glEnable(GL2.GL_FOG);
		this.pyramide.traceBorders(gl,0.99f,0.9f,0.6f,0.9f,0f);
		this.pyramide.traceMe(gl);
		this.plan.tracePlan(gl);
		gl.glDisable(GL.GL_DEPTH_TEST);
		if((this.plan.getH()<0.5f)){
			this.psection.traceMe(gl,0.3f,0.2f,0.4f,0.6f);
			this.psection.drawBorders(gl,0);
		}
	}

	private void renderVoid(GL2 gl){
		// tracé à vide pour les tests
		gl.glClearColor(1.0f,1.0f,1.0f,1.0f);
		gl.glDisable(GL2.GL_FOG);
		gl.glDepthFunc(GL.GL_LESS);
		gl.glColorMask(false,false,false,false);
		this.pyramide.traceMe(gl);
		gl.glDepthFunc(GL.GL_GREATER);

		//tracé des pointillés
		gl.glColorMask(true,true,true,true);
		gl.glLineWidth(2f);
		instance.getTextures().bind(gl,101);
		if((this.plan.getH()<0.5f)){
			this.psection.traceBorders(gl,0.99f,0.013f);
		}
		this.pyramide.traceBorders(gl,0.99f,0.99f,0.99f,0.99f,0.009f);
		instance.getTextures().unbind(gl);

		//tracé de la section
		gl.glDepthFunc(GL.GL_LESS);
		gl.glLineWidth(3f);
		gl.glDisable(GL2.GL_DEPTH_TEST);
		instance.getTextures().bind(gl,201);
		if((this.plan.getH()<0.5f)){
			this.psection.traceMe(gl);
		}
		instance.getTextures().unbind(gl);

		//tracé du plan
		instance.getTextures().bind(gl,102);
		this.plan.tracePlan(gl,0.3f);
		gl.glEnable(GL2.GL_DEPTH_TEST);

		//tracé de bords visibles
		if((this.plan.getH()<0.5f)){
			this.psection.traceBorders(gl,0.99f,0.013f);
		}
		this.pyramide.traceBorders(gl,0.9f,0.9f,0.9f,0.9f,0.009f);
		instance.getTextures().unbind(gl);
	}

	public void render(GL2 gl) {
		preRender();

		this.pyramide.resetRotation();
		this.pyramide.zRotation((float)thetatot);
		this.pyramide.xRotation((float)phitot);
		this.plan.resetRotation();
		this.plan.setH(h);
		this.plan.zRotation((float)thetatot);
		this.plan.xRotation((float)phitot);
		this.psection.resetRotation();
		this.psection.setH(h);
		this.psection.zRotation((float)thetatot);
		this.psection.xRotation((float)phitot);
		this.pyramide.sort();

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
