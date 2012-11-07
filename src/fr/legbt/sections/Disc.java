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

public class Disc implements Piece,Bordered {
	protected Vecteur u;
	protected Vecteur v;
	protected Vecteur n;
	protected Vecteur ur;
	protected Vecteur vr;
	protected Vecteur nr;
	protected Vecteur up;
	protected Vecteur vp;
	protected Vecteur np;
	protected Vecteur np0;
	private double hr = 1.5f;
	private boolean border;
	private boolean sphere;
	static final Vecteur x = new Vecteur(1,0,0);
	static final Vecteur y = new Vecteur(0,1,0);
	static final Vecteur z = new Vecteur(0,0,1);
	static final int res = 256;
	static final double radian = 6.28318531f/res;
	static final double PI = 3.1415926535898f;

	public Disc(Vecteur u, Vecteur v, Vecteur n){
		this.u = new Vecteur(u);
		this.v = new Vecteur(v);
		this.n = new Vecteur(n);
		this.ur = new Vecteur(u);
		this.vr = new Vecteur(v);
		this.nr = new Vecteur(n);
		this.up = new Vecteur(u);
		this.vp = new Vecteur(v);
		this.np = new Vecteur(n);
		this.np0 = new Vecteur(n);
		this.border = false;
		this.sphere = false;
	}

	public void reset(){}
	public void reset(double angle){}

	public Disc(double c,Vecteur u,Vecteur v,Vecteur n){
		this(u,v,n);
	}
	public Disc(double c,double xscale,double yscale, Vecteur n){
		this(new Vecteur(xscale,0,0),new Vecteur(0,yscale,0),n);
	}

	public void traceMe(GL2 gl){
		traceMe(gl,0.3f);
	}

	public void traceMe(GL2 gl,double red){
		traceMe(gl,red,red,red,red);
	}

	public void traceMe(GL2 gl,double red,double green,double blue,double trans){
		Vecteur center = new Vecteur(this.nr);
		Vecteur tempx = new Vecteur(ur);
		Vecteur tempy = new Vecteur(vr);
		Vecteur first = new Vecteur(ur);
		first.add(this.nr);

		tempx.set(ur);
		tempy.set(vr);
		tempx.scale((double)Math.cos((double)radian));
		tempy.scale((double)Math.sin((double)radian));
		Vecteur second = new Vecteur();
		second.add(tempx);
		second.add(tempy);
		second.add(this.nr);

		gl.glBegin(GL2.GL_TRIANGLE_FAN);

		double para = 0;
		if(border){
			gl.glColor4d(red,green,blue,trans);
		}else{
			gl.glColor4d(para,0.8f,0.2f,0.7f);
		}

		vect3ToVertex(gl,center);
		vect3ToVertex(gl,first);

		for(int i=0;i<res;i++){
			if(border){
			}else{
				if(i<res/2){para = 2*i/(double)res;}else{para = 2*(1-i/(double)res);}
				gl.glColor4d(para,0.8,0.2,0.7);
			}

			vect3ToVertex(gl,second);

			tempx.set(ur);
			tempy.set(vr);
			tempx.scale((double)Math.cos((double)radian*(i+2)));
			tempy.scale((double)Math.sin((double)radian*(i+2)));
			second = new Vecteur();
			second.add(tempx);
			second.add(tempy);
			second.add(this.nr);
		}
		gl.glEnd();

	}



	public void rotation(Quaternion quat){
		ur.rotate(quat);	
		vr.rotate(quat);	
		nr.rotate(quat);	
	}

	public void resetRotation(){
		this.ur = new Vecteur(this.up);
		this.vr = new Vecteur(this.vp);
		this.nr = new Vecteur(this.np);
	}

	public void Rotation(Quaternion quat){
		rotation(quat);
	}

	public void vect3ToVertex(GL2 gl, Vecteur b){
		double px = b.X();
		double py = b.Y();
		double pz = b.Z();
		gl.glVertex3d(px,py,pz);
	}

	public void setH(double ph){
		np.set(n);
		np.scale(ph);
		np.add(np0);
		if(sphere){
			hr = 1.5f - ph;
			Vecteur temp = new Vecteur(u);
			temp.scale((double)Math.sqrt(1/4f-(hr/5f-1f/2f)*(hr/5f-1f/2f))*2f);	
			up.set(temp);
			temp.set(v);
			temp.scale((double)Math.sqrt(1/4f-(hr/5f-1f/2f)*(hr/5f-1f/2f))*2f);	
			vp.set(temp);
		}
	}

	public double getH(){
		return this.np.length();
	}

	public double getProf(){
		return this.nr.Z();
	}

	public void traceBorders(GL2 gl,double red){
		traceBorders(gl,red,0.005f);
	}

	public void traceBorders(GL2 gl,double red,double off){
		double a = 1f;

		gl.glBegin(GL2.GL_LINE_STRIP);
		gl.glColor4d(red,red,red,red);
		Vecteur tempx = new Vecteur(ur);
		Vecteur tempy = new Vecteur(vr);
		Vecteur first = new Vecteur(ur);
		first.add(this.nr);
		first.scale(1+off);	

		tempx.set(ur);
		tempy.set(vr);
		tempx.scale((double)Math.cos((double)radian));
		tempy.scale((double)Math.sin((double)radian));
		Vecteur second = new Vecteur();
		second.add(tempx);
		second.add(tempy);
		second.add(this.nr);

		gl.glTexCoord1f(0f);
		vect3ToVertex(gl,first);

		for(int i=0;i<res;i++){
			gl.glTexCoord1d(a*i/res);

			//test inflate
			second.scale(1+off);	

			vect3ToVertex(gl,second);
			tempx.set(ur);
			tempy.set(vr);
			tempx.scale((double)Math.cos((double)radian*(i+2)));
			tempy.scale((double)Math.sin((double)radian*(i+2)));
			second = new Vecteur();
			second.add(tempx);
			second.add(tempy);
			second.add(this.nr);
		}
		gl.glEnd();
	}

	public int compareTo(Piece d) {
		//le 100 évite un "threathold effect" à cause de la conversion en int
		double pc =	100*(this.getProf() - d.getProf());
		return (int) pc;
	}

	public boolean isBorder(){
		return this.border;
	}

	public void setBorder(boolean border){
		this.border = border;
	}

	public boolean isSphere(){
		return this.sphere;
	}

	public void setSphere(boolean sphere){
		this.sphere = sphere;
	}
}
