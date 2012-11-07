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


public abstract class  Scene{
	protected Plan plan;
	protected RotatingSection section;
	protected Bordered dsection;
	protected Thales psection;
	protected double theta = 10;
	protected double phi = -160;
	protected double phitot;
	protected double thetatot;
	protected Quaternion xquat;
	protected Quaternion yquat;
	protected Quaternion rquat;
	protected double h; 
	protected double htot; 
	protected double angle;
	protected double angletot;
	protected boolean firstrotation;
	protected Sections instance;
	static final Vecteur x = new Vecteur(1,0,0);
	static final Vecteur y = new Vecteur(0,1,0);
	static final Vecteur z = new Vecteur(0,0,1);
	static final Vecteur nul = new Vecteur(0,0,0);
	static final Vecteur sect = new Vecteur(0,0,0.4f);
	private Vecteur buffer;

	public Scene(float xscale, float yscale,Sections instance){
		plan = new Plan();
		section = new RotatingSection(1,xscale,yscale,instance);
		firstrotation = true;
		this.instance = instance;
		buffer = new Vecteur();
		xquat = new Quaternion();
		yquat = new Quaternion();
		rquat = new Quaternion();
	}



	public Scene(String type, Sections instance){
		this.instance = instance;
		plan = new Plan();
		firstrotation = true;
		buffer = new Vecteur();
		xquat = new Quaternion();
		yquat = new Quaternion();
		rquat = new Quaternion();
	}

	protected boolean getMode(){
		return instance.isBonemode();
	}

	public void reset(){
		firstrotation = true;
		if(!instance.isPlantype()){
			if(this instanceof CylinderScene){
				this.dsection = new Thales(new Vecteur(0.4f,0,0),y,z,new Vecteur(0.4f,0,0));
				((Thales)this.dsection).setCylinderthales(true);
				theta = 60;
				phi = 0;
				//	thetatot = 0;
				//	phitot = 30;
				yquat = new Quaternion(30f,y);
				this.plan = new Plan();
				this.plan.reset(90);
				this.h = -4.2f;
				this.htot = -4.2f;
				((CylinderScene)this).cylinder = new Cylinder();
				((CylinderScene)this).resetEdges();
			}else{
				this.section.reset(30);
				this.plan.reset(30);
				this.angletot = 30;
			}
		}else{
			this.plan.reset();
			if(this instanceof CylinderScene){
				this.dsection = new Disc(x,y,new Vecteur(0,0,0.4f));
				this.dsection.reset();
				this.dsection.setBorder(true);
				theta = 60;
				phi = 0;
				//	thetatot = 0;
				//	phitot = 30;
				yquat = new Quaternion(30f,y);
				this.plan = new Plan();
				this.h = -4.2f;
				this.htot = -4.2f;
				((CylinderScene)this).cylinder = new Cylinder();
				((CylinderScene)this).resetEdges();
			}else{
				this.section.reset();
			}
		}
	}

	protected void preRender(){

		buffer.set(0,1,0);
		yquat.identity();
		yquat.mult(new Quaternion(theta/2.,buffer));
		buffer.set(1,0,0);
		buffer.rotate(yquat);
		xquat.identity();
		xquat.mult(new Quaternion(phi/2.,buffer));

		// on multiplie rquat par x et y à GAUCHE ce qui revient 
		// à conjuger les rotations par la rotation commulée 
		// et donc à appliquer nos rotations celon les vecteurs 
		// correctement corrigés dans notre rotation		

		yquat.mult(xquat);
		yquat.mult(rquat);
		rquat.set(yquat);
	}


	public void released(){
		this.angle = 0;
		this.theta = 0;
		this.phi = 0;
	}

	public void sceneDragged(int xdelta,int ydelta){
		this.theta = xdelta;
		this.phi = ydelta;
	}

	public void sectionDragged(int xdelta,int ydelta){
		this.h =  -ydelta/100.0;
		this.angle = -xdelta/8.0;
		htot += h;
		angletot += this.angle;
		if(!(this instanceof CylinderScene)){
			if(!instance.isPlantype()){
				plan.angle = angletot;
				if((this instanceof CubeScene)||(this instanceof PaveScene)){
					section.angle = angletot;
				}
			}
		}
	}

	public abstract void render(GL2 gl);

}
