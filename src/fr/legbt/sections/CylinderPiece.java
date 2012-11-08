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

public class CylinderPiece implements Piece{
	protected Vecteur u;
	protected Vecteur v;
	protected Vecteur n;
	protected Vecteur ur;
	protected Vecteur vr;
	protected Vecteur nr;
	protected Vecteur np;
	static final Vecteur x = new Vecteur(1,0,0);
	static final Vecteur y = new Vecteur(0,1,0);
	static final Vecteur z = new Vecteur(0,0,1);

	public CylinderPiece(Vecteur u, Vecteur v, Vecteur n){
		this.u = new Vecteur(u);
		this.v = new Vecteur(v);
		this.n = new Vecteur(n);
		this.ur = new Vecteur(u);
		this.vr = new Vecteur(v);
		this.nr = new Vecteur(n);
		this.np = new Vecteur(n);
	}

	public CylinderPiece(double c,Vecteur u,Vecteur v,Vecteur n){
		this(u,v,n);
	}
	public CylinderPiece(double c,double xscale,double yscale, Vecteur n){
		this(new Vecteur(xscale,0,0),new Vecteur(0,yscale,0),n);
	}

	public void traceMe(GL2 gl,double a,double b,double c,double d){
		System.out.println("color cylinder not supported");
	}

	public void traceMe(GL2 gl,double off){
		System.out.println("Warning: no inflate suport for face.java");
		traceMe(gl);
	}

	public void traceMe(GL2 gl){
		traceMe(gl,false,true);
	}

	public void traceMe(GL2 gl,boolean black,boolean classic){
		final int res = 256; 
		double radian = 6.28318531f/res;
		//	double radian = 3.1415926536f/res;

		double off = ((!classic)&&black)? 0.008:0.;
		ur.scale(1+off);
		vr.scale(1+off);


		Vecteur tempx = new Vecteur(ur);
		Vecteur tempy = new Vecteur(vr);

		//int fix = (int) Math.floor(nr.Y())*2 + 1;
		//int fiy = (int) Math.floor(ur.Y())*2 + 1;
		//int fiz = (int) Math.floor(nr.Z())*2 + 1;
		//



		//gram-schmidt  sur nr pour garder trace de la rotation selon Z
		Vecteur gsn = new Vecteur(x);
		Vecteur gstemp = new Vecteur(y);
		gsn.scale(nr.X());
		gstemp.scale(nr.Y());
		gsn.add(gstemp);
		gsn.normalize();
		// gsn ok	
		Vecteur gsx = new Vecteur(x);	
		gstemp.set(gsn);
		gstemp.scale(gsn.dot(x));
		gsx.sub(gstemp);
		gsx.normalize();
		//gsx ok
		Vecteur gsy = new Vecteur(gsx);
		gsy.vect(gsn);
		//gsy ok

		int fiz = (int) Math.floor(vr.dot(gsx))*2 + 1;
		int fiy = (int) Math.floor(nr.Y())*2 + 1;

		// wtf bug !
		if(fiy==3){fiy=1;}
		if(fiy==-3){fiy=-1;}
		if(fiz==3){fiz=1;}
		if(fiz==-3){fiz=-1;}






		int firstposition = (int) Math.round(Math.acos(  fiz*ur.dot(gsx)  )  /radian);


		tempx.set(ur);
		tempy.set(vr);
		tempx.scale((double)Math.cos((double)radian*firstposition));
		tempy.scale((double)Math.sin((double)radian*firstposition));
		Vecteur top = new Vecteur();
		Vecteur bottom = new Vecteur();
		top.add(tempx);
		top.add(tempy);
		top.add(this.nr);
		bottom.add(tempx);
		bottom.add(tempy);
		bottom.sub(this.nr);

		gl.glBegin(GL2.GL_QUAD_STRIP);

		double para = 3f*(((double)firstposition)/((double)res) - ((double)(firstposition)*(firstposition))/((double)(res*res)));
		para = firstposition/res;
		if(black){
			gl.glColor4f(0,0,0,1);
		}else{
			gl.glColor4f(0,0.8f,0.2f,0.4f);
		}
		vect3ToVertex(gl,top);
		vect3ToVertex(gl,bottom);
		int j;

		for(int i=0;i<res;i++){
			j = (fiy*fiz*i+firstposition);
			if(j>res){j-=res;}
			if(j<0){j+=res;}

			if(j<res/2){para = 2*j/(double)res;}else{para = 2*(1-j/(double)res);}
			if(black){
				if(i>res/1.89 || firstposition == 0){
					gl.glColor4f(1,1,1,0);
				}else{
					gl.glColor4f(0,0,0,1);
				}
			}else{
				gl.glColor4d(para,0.8,0.2,para/8+0.4);
			}

			vect3ToVertex(gl,top);
			vect3ToVertex(gl,bottom);

			tempx.set(ur);
			tempy.set(vr);
			tempx.scale((double)Math.cos((double)radian*(j+2)));
			tempy.scale((double)Math.sin((double)radian*(j+2)));
			top.scale(0);
			bottom.scale(0);
			top.add(tempx);
			top.add(tempy);
			top.add(this.nr);
			bottom.add(tempx);
			bottom.add(tempy);
			bottom.sub(this.nr);
		}
		gl.glEnd();
	}


	public void rotation(Quaternion quat){
		ur.rotate(quat);
		vr.rotate(quat);
		nr.rotate(quat);
	}

	public void resetRotation(){
		this.ur = new Vecteur(this.u);
		this.vr = new Vecteur(this.v);
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


	public double getH(){
		return this.np.length();
	}

	public double getProf(){
		return 2f;
	}

	protected void drawBorders(GL2 gl){
	}

	public int compareTo(Piece p) {
		//le 100 évite un "threathold effect" à cause de la conversion en int
		double pc =	1000*(this.getProf() - p.getProf());
		return (int) pc;
	}
}
