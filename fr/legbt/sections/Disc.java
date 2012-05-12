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
	private float xrot;
	private float yrot;
	private float zrot;
	private float hr = 1.5f;
	private float angle;
	private boolean border;
	private boolean sphere;
	static final Vecteur x = new Vecteur(1,0,0);
	static final Vecteur y = new Vecteur(0,1,0);
	static final Vecteur z = new Vecteur(0,0,1);
	static final int res = 256;
	static final float radian = 6.28318531f/res;
	static final float PI = 3.1415926535898f;

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
		this.xrot = 0;
		this.yrot = 0;
		this.zrot = 0;
		this.border = false;
		this.sphere = false;
	}

	public void reset(){
		this.angle = 0;
	}

	public void reset(float angle){
		this.angle = angle;
	}

	public Disc(float c,Vecteur u,Vecteur v,Vecteur n){
		this(u,v,n);
	}
	public Disc(float c,float xscale,float yscale, Vecteur n){
		this(new Vecteur(xscale,0,0),new Vecteur(0,yscale,0),n);
	}

	public void traceMe(GL2 gl){
		traceMe(gl,0.3f);
	}

	public void traceMe(GL2 gl,float red){
		traceMe(gl,red,red,red,red);
	}

	public void traceMe(GL2 gl,float red,float green,float blue,float trans){
		Vecteur center = new Vecteur(this.nr);
		Vecteur tempx = new Vecteur(ur);
		Vecteur tempy = new Vecteur(vr);
		Vecteur first = new Vecteur(ur);
		first.add(this.nr);

		tempx.set(ur);
		tempy.set(vr);
		tempx.scale((float)Math.cos((double)radian));
		tempy.scale((float)Math.sin((double)radian));
		Vecteur second = new Vecteur();
		second.add(tempx);
		second.add(tempy);
		second.add(this.nr);

		gl.glBegin(GL2.GL_TRIANGLE_FAN);

		float para = 0;
		if(border){
		//	gl.glColor4f(red,red,red,red);
		//	gl.glColor4f(0.3f,0.2f,0.4f,0.6f);
			gl.glColor4f(red,green,blue,trans);
		}else{
			gl.glColor4f(para,0.8f,0.2f,0.7f);
		}

		vect3ToVertex(gl,center);
		vect3ToVertex(gl,first);


		for(int i=0;i<res;i++){
			if(border){
			}else{
				if(i<res/2){para = 2*i/(float)res;}else{para = 2*(1-i/(float)res);}
				gl.glColor4f(para,0.8f,0.2f,0.7f);
			}

			vect3ToVertex(gl,second);

			tempx.set(ur);
			tempy.set(vr);
			tempx.scale((float)Math.cos((double)radian*(i+2)));
			tempy.scale((float)Math.sin((double)radian*(i+2)));
			second = new Vecteur();
			second.add(tempx);
			second.add(tempy);
			second.add(this.nr);
		}
		gl.glEnd();

	}


	static private float radian(float degree){
		return degree*0.017453292519943295769236907684f;
	}

	private void rotation(Matrice matrix){
		matrix.transform(ur);
		matrix.transform(vr);
		matrix.transform(nr);
	}

	public void resetRotation(){
		this.ur = new Vecteur(this.up);
		this.vr = new Vecteur(this.vp);
		this.nr = new Vecteur(this.np);
	}

	public void xRotation(float degree){
		Matrice matrix = new Matrice();
		xrot += radian(degree);
		matrix.rotX(xrot);
		rotation(matrix);
	}
	public void yRotation(float degree){
		Matrice matrix = new Matrice();
		yrot += radian(degree);
		matrix.rotY(yrot+radian(angle));
		rotation(matrix);
	}
	public void zRotation(float degree){
		Matrice matrix = new Matrice();
		zrot += radian(degree);
		matrix.rotZ(zrot);
		rotation(matrix);
	}

	public void vect3ToVertex(GL2 gl, Vecteur b){
		float px = b.getX();
		float py = b.getY();
		float pz = b.getZ();
		gl.glVertex3f(px,py,pz);
	}

	public void setH(float ph){
		this.np.scaleAdd(ph,n,this.np);
		if(sphere){
			hr -= ph;
			Vecteur temp = new Vecteur(u);
			temp.scale((float)Math.sqrt(1/4f-(hr/5f-1f/2f)*(hr/5f-1f/2f))*2f);	
			up.set(temp);
			temp.set(v);
			temp.scale((float)Math.sqrt(1/4f-(hr/5f-1f/2f)*(hr/5f-1f/2f))*2f);	
			vp.set(temp);
		}
	}

	public float getH(){
		return this.np.length();
	}

	public float getProf(){
		return this.nr.getZ();
	}

	public void traceBorders(GL2 gl,float red){
		traceBorders(gl,red,0.005f);
	}

	public void traceBorders(GL2 gl,float red,float off){
		gl.glBegin(GL2.GL_LINE_STRIP);
		gl.glColor4f(red,red,red,red);
		Vecteur tempx = new Vecteur(ur);
		Vecteur tempy = new Vecteur(vr);
		Vecteur first = new Vecteur(ur);
		first.add(this.nr);
		first.scale(1+off);	

		tempx.set(ur);
		tempy.set(vr);
		tempx.scale((float)Math.cos((double)radian));
		tempy.scale((float)Math.sin((double)radian));
		Vecteur second = new Vecteur();
		second.add(tempx);
		second.add(tempy);
		second.add(this.nr);

		gl.glTexCoord1f(0f);
		vect3ToVertex(gl,first);

		for(int i=0;i<res;i++){
			gl.glTexCoord1f(105f*i/res);

			//test inflate
			second.scale(1+off);	

			vect3ToVertex(gl,second);
			tempx.set(ur);
			tempy.set(vr);
			tempx.scale((float)Math.cos((double)radian*(i+2)));
			tempy.scale((float)Math.sin((double)radian*(i+2)));
			second = new Vecteur();
			second.add(tempx);
			second.add(tempy);
			second.add(this.nr);
		}
		gl.glEnd();
	}

	public int compareTo(Piece d) {
		//le 100 évite un "threathold effect" à cause de la conversion en int
		float pc =	100*(this.getProf() - d.getProf());
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
