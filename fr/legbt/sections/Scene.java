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


public abstract class  Scene{
	protected Plan plan;
	protected Plan section;
	protected Disc dsection;
	protected Thales psection;
	protected float theta = 10;
	protected float phi = -160;
	protected float h; 
	protected float angle;
	protected boolean firstrotation;
	protected Sections instance;

	public Scene(float xscale, float yscale,Sections instance){
		if(instance.isPlantype()){
			angle = 0.5f;
			plan = new Plan(angle);
			section = new Plan(angle,1,xscale,yscale);
		}else{
			plan = new Plan();
			section = new Plan(1,xscale,yscale);
		}
		firstrotation = true;
		this.instance = instance;
	}

	public Scene(String type){
		plan = new Plan();
		if(type.equals("cyl")){
			dsection = new Disc(new Vector3f(1,0,0),new Vector3f(0,1,0),new Vector3f(0,0,0.4f));
			dsection.setBorder(true);
		}else if(type.equals("spl")){
			dsection = new Disc(new Vector3f(1,0,0),new Vector3f(0,1,0),new Vector3f(0,0,0.4f));
			dsection.setBorder(true);
			dsection.setSphere(true);
		}else if(type.equals("pyl")){
			psection = new Thales(new Vector3f(0.2f,0.1f,0.4f),new Vector3f(1.5f,0,0),new Vector3f(0,1,0),new Vector3f(0.7f,0.35f,0.4f));
		}
		firstrotation = true;
	}

	public void released(){
		this.theta = 0;
		this.phi = 0;
	}

	public void sceneDragged(int xdelta,int ydelta){
		this.theta = xdelta;
		this.phi = ydelta;
	}

	public void sectionDragged(int xdelta,int ydelta){
		this.h = -ydelta/100f;
	}

	public abstract void render(GL2 gl);

}
