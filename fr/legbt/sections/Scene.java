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
	protected float theta = 10;
	protected float phi = -160;
	protected float h; 
	protected float angle;
	protected boolean firstrotation;
	protected Sections instance;
	static final Vecteur x = new Vecteur(1,0,0);
	static final Vecteur y = new Vecteur(0,1,0);
	static final Vecteur z = new Vecteur(0,0,1);
	static final Vecteur nul = new Vecteur(0,0,0);

	public Scene(float xscale, float yscale,Sections instance){
		if(!instance.isPlantype()){
			if(this instanceof CylinderScene){
				angle = 90f;
			}else{
				angle = 30f;
			}
			plan = new Plan();
			plan.angle = angle;
			section = new RotatingSection(1,xscale,yscale,instance);
			section.angle = angle;
		}else{
			plan = new Plan();
			section = new RotatingSection(1,xscale,yscale,instance);
		}
		firstrotation = true;
		this.instance = instance;
	}

	protected boolean getMode(){
		return instance.isBonemode();
	}


	public Scene(String type, Sections instance){
		this.instance = instance;
		plan = new Plan();
		if(type.equals("cyl")){
			if(!instance.isPlantype()){
				dsection = new Thales(z,x,y,new Vecteur(0,0,0.4f));
			}else{
				dsection = new Disc(x,y,new Vecteur(0,0,0.4f));
			}
			dsection.setBorder(true);
		}else if(type.equals("spl")){
			dsection = new Disc(x,y,new Vecteur(0,0,0.4f));
			dsection.setBorder(true);
			dsection.setSphere(true);
		}else if(type.equals("pyl")){
			psection = new Thales(new Vecteur(0.2f,0.1f,0.4f),new Vecteur(1.5f,0,0),y,new Vecteur(0.7f,0.35f,0.4f));
		}
		firstrotation = true;
	}

	public void reset(){
		firstrotation = true;
		if(!instance.isPlantype()){
			if(this instanceof CylinderScene){
				this.dsection = new Thales(new Vecteur(0.4f,0,0),y,z,new Vecteur(0.4f,0,0));
				((Thales)this.dsection).setCylinderthales(true);
				theta = 10;
				phi = -160;
				this.plan = new Plan();
				this.plan.reset(90);
				this.h = -4.2f;
				((CylinderScene)this).cylinder = new Cylinder();
				((CylinderScene)this).resetEdges();
				//	this.dsection.reset(90);
			}else{
				this.section.reset(30);
				this.plan.reset(30);
			}
		}else{
			this.plan.reset();
			if(this instanceof CylinderScene){
				this.dsection = new Disc(x,y,new Vecteur(0,0,0.4f));
				this.dsection.reset();
				this.dsection.setBorder(true);
				theta = 10;
				phi = -160;
				this.plan = new Plan();
				this.h = -4.2f;
				((CylinderScene)this).cylinder = new Cylinder();
				((CylinderScene)this).resetEdges();
			}else{
				this.section.reset();
			}
		}
	}


	public void released(){
		this.theta = 0;
		this.phi = 0;
		//		this.angle = 0;
	}

	public void sceneDragged(int xdelta,int ydelta){
		this.theta = xdelta;
		this.phi = ydelta;
	}

	public void sectionDragged(int xdelta,int ydelta){
		this.h = -ydelta/100f;
		this.angle = -xdelta/8f;
		if(instance!=null){
			if(!(this instanceof CylinderScene)){
				if(!instance.isPlantype()){
					plan.angle += this.angle;
					if((this instanceof CubeScene)||(this instanceof PaveScene)){
						section.angle += this.angle;
					}
				}
			}
		}
	}

	public abstract void render(GL2 gl);

	public float getAngle(){
		return this.angle;
	}
}
