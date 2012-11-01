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

public class RotatingSection extends Plan{
	private float hr = 1.5f;
	private Sections instance;
	private float xscale;
	private boolean tracer = true;
	private double xa;
	private double ya;
	private double yb;
	private double ang;
	private Vecteur temptrans;
	private Vecteur temptransold = new Vecteur(u);
	private Vecteur temp;

	public RotatingSection(float s, float xscale, float yscale, Sections instance){
		super(s,xscale,yscale);
		this.instance = instance;
		this.xscale = xscale;
	}

	@Override
		public void reset(){
			super.reset();
			up.set(u);
			vp.set(v);
			uoff = new Vecteur();
		}

	@Override
		public void setH(float ph){
			this.np.scaleAdd(ph,n,this.np);
			hr -= ph;
			temp = new Vecteur(u);
			temptrans = new Vecteur(u);
			temp.scale(1/xscale);
			temptrans.scale(1/xscale);
			ang = radian(angle);
			float casangsupapisur2 = 1;
			float thr = 1;
			if(hr>2.5){
				thr =-1;
			}
			while(ang>Math.PI){
				ang -= Math.PI;
			}
			while(ang<0){
				ang += Math.PI;
			}
			if(ang>Math.PI/2f){
				casangsupapisur2 = -1;
			}
			double len = Math.abs(hr-2.5f)/2.5f;
			ya = casangsupapisur2/2f;
			yb = (len/Math.cos(ang)-xscale*Math.tan(ang))/2;
			if(ang==0){
				xa = -xscale;
			}else{
				xa = (len/Math.sin(ang)-casangsupapisur2/Math.tan(ang))/2;
			}

			double xb = xscale/2f;

			double dx = xb-xa;
			double dy = yb-ya;

			double xo = len*Math.sin(ang)/2f-dx/2f;
			double yo = (len*Math.cos(ang))/2f-dy/2f;

			double lon = Math.sqrt(dx*dx+dy*dy);

			double y2;
			double x2;
			double haut = (hr-2.5f)/2.5f;
			boolean coins = true;
			int vertical = 0;
			this.tracer = true;
			if(xa>xscale/2f-0.001f){
				this.tracer = false;
			}
			if(xa<-xscale/2){
				coins = false;
				ya = -casangsupapisur2*haut/Math.cos(ang)/2f-xscale*Math.tan(ang)/2f;
				y2 = -casangsupapisur2*haut/Math.cos(ang)/2f+xscale*Math.tan(ang)/2f;
				xa = xscale/2f;
				dx = xscale;
				if(ang==0){dy=0;}else{dy = (ya-y2);}
				lon = Math.sqrt(dx*dx+dy*dy);
				xo = -casangsupapisur2*haut*Math.sin(ang)/2f+dx/2f;
				yo = (-casangsupapisur2*haut*Math.cos(ang)/2f+dy/2f);
			}
			if(yb>0.5f){
				coins = false;
				vertical = 1;
				xa = haut/Math.sin(ang)/2f-1/Math.tan(ang)/2f;
				x2 = haut/Math.sin(ang)/2f+1/Math.tan(ang)/2f;
				ya = 1/2f;
				dy = 1f;
				if(ang==Math.PI/2f){dx=0;}else{dx = (xa-x2);}
				lon = Math.sqrt(dx*dx+dy*dy);
				yo = haut*Math.cos(ang)/2f+dy/2f;
				xo = haut*Math.sin(ang)/2f+dx/2f;
			}
			if(yb<-0.5f){
				coins = false;
				vertical = -1;
				xa = haut/Math.sin(ang)/2f-1/Math.tan(ang)/2f;
				x2 = haut/Math.sin(ang)/2f+1/Math.tan(ang)/2f;
				ya = 1/2f;
				dy = 1f;
				if(ang==Math.PI/2f){dx=0;}else{dx = (xa-x2);}
				lon = Math.sqrt(dx*dx+dy*dy);
				yo = haut*Math.cos(ang)/2f+dy/2f;
				xo = haut*Math.sin(ang)/2f+dx/2f;
			}

			float test = -1;
			if(yo>0.5f){
				test = 1;
			}else if((yo>-0.5)&&(yo<0)){
				test = 1;
			}

			double dox = (xo-xa);
			double doy = (yo-ya);
			if(coins){
				temptrans.scale(2*(test)*thr*(float)(Math.sqrt(dox*dox+doy*doy)));
			}else{
				if(vertical ==0){
					temptrans.scale(2f*(float)(-casangsupapisur2*thr*Math.sqrt(dox*dox+doy*doy)));
				}else{
					temptrans.scale(2f*(float)(-vertical*thr*Math.sqrt(dox*dox+doy*doy)));
				}
			}
			if(lon>1.81f){
				lon = 0;
			}

			temp.scale((float)lon);

			if(!instance.isPlantype()){
				uoff.set(temptransold);
				up.set(temp);
			}else if((yb>0.5f)||(yb<-0.5f)){
				tracer = false;
			}
			this.temptransold = new Vecteur(temptrans);
		}

	static private double radian(double degree){
		return degree*Math.PI/180;
	}

	public void tracePlan(GL2 gl){
	float a = 1f;

		if(tracer){
			/* *****************
			 * Tracé de la face
			 * ******************/
			gl.glBegin(GL2.GL_QUADS);
			if(instance.isBonemode()){
				gl.glColor4f(0.4f,0.4f,0.4f,0.3f);
			}else{
				gl.glColor4f(0.3f,0.2f,0.4f,0.6f);
			}
			gl.glTexCoord2f(0f,0f);
			drawTopLeft(gl);
			gl.glTexCoord2f(a,0f);
			drawBottomLeft(gl);
			gl.glTexCoord2f(a,a);
			drawBottomRight(gl);
			gl.glTexCoord2f(0f,a);
			drawTopRight(gl);
			gl.glEnd();
			gl.glColor4f(0.1f,0.1f,0.1f,0.999f);
		}
	}
	public void traceBorders(GL2 gl,float red,float green,float blue,float trans,float off){
		if(tracer){
			/* *******************
			 * Tracé des arrêtes
			 * ******************/
			gl.glColor4f(red,green,blue,trans);
			drawBorders(gl,off);
		}
	}
	public void traceBorders(GL2 gl,float red){
		traceBorders(gl,red,red,red,red,0.005f);
	}
}
