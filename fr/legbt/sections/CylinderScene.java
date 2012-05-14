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


public class CylinderScene extends Scene{
	static final Vecteur x = new Vecteur(1,0,0);
	static final Vecteur y = new Vecteur(0,1,0);
	static final Vecteur z = new Vecteur(0,0,1);
	static final Vecteur nul = new Vecteur(0,0,0);
	protected Cylinder cylinder;
	private CylinderEdge edge;

	public CylinderScene(Sections instance){
		super("cyl",instance);
		cylinder = new Cylinder();
		Vecteur xt = new Vecteur(x);
		xt.scale(-1);
		edge = new CylinderEdge(x,z,nul);
		this.h = -4.2f;
		this.instance = instance;
	}

	public void resetEdges(){
		edge = new CylinderEdge(x,z,nul);
	}

	private void renderFull(GL2 gl){
		gl.glClearColor(0.0f,0.0f,0.0f,0.0f);
		gl.glEnable(GL2.GL_FOG);
		this.cylinder.traceBorders(gl,0.9f);
		this.cylinder.traceMe(gl);
		this.plan.tracePlan(gl);
		gl.glDisable(GL.GL_DEPTH_TEST);
		if(instance.isPlantype()){
			if((this.dsection.getH()<1)&&(this.dsection.getH()>-1)){
				this.dsection.traceMe(gl,0.3f,0.2f,0.4f,0.6f);
				this.dsection.traceBorders(gl,0.9f,0);
			}
		}else{
			this.dsection.traceMe(gl,0.3f,0.2f,0.4f,0.6f);
			this.dsection.traceBorders(gl,0.9f,0);
		}
	}

	private void renderVoid(GL2 gl){
		// tracé à vide pour les tests
		gl.glClearColor(1.0f,1.0f,1.0f,1.0f);
		gl.glDisable(GL2.GL_FOG);
		gl.glDepthFunc(GL.GL_LESS);
		gl.glColorMask(false,false,false,false);
		this.cylinder.traceMe(gl);
		//		this.plan.tracePlan(gl);
		gl.glDepthFunc(GL.GL_GREATER);

		//tracé des pointillés
		gl.glColorMask(true,true,true,true);
		gl.glLineWidth(2f);
		instance.getTextures().bind(gl,11);
		if(instance.isPlantype()){
			if((this.dsection.getH()<1)&&(this.dsection.getH()>-1)){
				this.dsection.traceBorders(gl,0.99f,0.005f);
			}
		}else{
			this.dsection.traceBorders(gl,0.99f,0.005f);
		}
		this.cylinder.traceBorders(gl,0.99f,0.005f);
		instance.getTextures().unbind(gl);

		//tracé de la section
		gl.glDepthFunc(GL.GL_LESS);
		gl.glLineWidth(3f);
		gl.glDisable(GL2.GL_DEPTH_TEST);
		instance.getTextures().bind(gl,21);
		if(instance.isPlantype()){
			if((this.dsection.getH()<1)&&(this.dsection.getH()>-1)){
				this.dsection.traceMe(gl);
			}
		}else{
			this.dsection.traceMe(gl);
		}
		instance.getTextures().unbind(gl);

		//tracé du plan
		instance.getTextures().bind(gl,12);
		this.plan.tracePlan(gl,0.3f);
		gl.glEnable(GL2.GL_DEPTH_TEST);

		//tracé de bords visibles
		if(instance.isPlantype()){
			if((this.dsection.getH()<1)&&(this.dsection.getH()>-1)){
				this.dsection.traceBorders(gl,0.99f);
			}
		}else{
			this.dsection.traceBorders(gl,0.99f);
		}
		this.edge.traceMe(gl);
		this.cylinder.traceBorders(gl,0.9f,0.005f);
		instance.getTextures().unbind(gl);
	}

	public void render(GL2 gl) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		int s = 2;
		gl.glOrtho(-s*1.6,s*1.6,-s*0.9,s*0.9,-s-1,s+1);
		this.cylinder.resetRotation();
		this.cylinder.zRotation((float)theta/2);
		this.cylinder.xRotation((float)phi/2);
		this.edge.resetRotation();
		this.edge.xRotation((float)phi/2);
		this.plan.resetRotation();
		this.plan.setH(h);
		this.plan.yRotation(0);
		this.plan.zRotation((float)theta/2);
		this.plan.xRotation((float)phi/2);
		this.dsection.resetRotation();
		this.dsection.setH(h);
		this.dsection.yRotation(0);
		this.dsection.zRotation((float)theta/2);
		this.dsection.xRotation((float)phi/2);
		this.cylinder.sort();

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
