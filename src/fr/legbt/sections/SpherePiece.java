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

public class SpherePiece implements Piece{
	protected Vecteur u;
	protected Vecteur v;
	protected Vecteur n;
	protected Vecteur ur;
	protected Vecteur vr;
	protected Vecteur nr;
	protected Vecteur np;
	private float xrot;
	private float yrot;
	private float zrot;
	static final Vecteur x = new Vecteur(1,0,0);
	static final Vecteur y = new Vecteur(0,1,0);
	static final Vecteur z = new Vecteur(0,0,1);
	static final int res = 64; 

	public SpherePiece(Vecteur u, Vecteur v, Vecteur n){
		this.u = new Vecteur(u);
		this.v = new Vecteur(v);
		this.n = new Vecteur(n);
		this.ur = new Vecteur(u);
		this.vr = new Vecteur(v);
		this.nr = new Vecteur(n);
		this.np = new Vecteur(n);
		this.xrot = 0;
		this.yrot = 0;
		this.zrot = 0;
	}

	public SpherePiece(float c,Vecteur u,Vecteur v,Vecteur n){
		this(u,v,n);
	}
	public SpherePiece(float c,float xscale,float yscale, Vecteur n){
		this(new Vecteur(xscale,0,0),new Vecteur(0,yscale,0),n);
	}

	public void traceMe(GL2 gl,float a,float b,float c,float d){
		System.out.println("Warning: no color support for spherepiece.java");
	}

	public void traceMe(GL2 gl,float off){
		System.out.println("Warning: inflate support for spherepiece.java");
	}

	public void traceMe(GL2 gl){
		float radian = 6.28318531f/res;

		Vecteur tempx = new Vecteur(ur);
		Vecteur tempy = new Vecteur(vr);
		Vecteur tempz = new Vecteur(nr);

		int fix = (int) Math.floor(nr.getY())*2 + 1;
		int fiy = (int) Math.floor(ur.getY())*2 + 1;
		int fiz = (int) Math.floor(nr.getZ())*2 + 1;
		int firstposition = (int) Math.round(Math.acos(-fiz*fiy*ur.getX())/radian);

		// wtf case !
		if(fix==3){fix=1;}

		tempx.set(ur);
		tempy.set(vr);
		tempz.set(nr);
		tempx.scale((float)Math.sin((double)radian)*(float)Math.cos((double)radian*firstposition));
		tempy.scale((float)Math.sin((double)radian)*(float)Math.sin((double)radian*firstposition));
		tempz.scale((float)Math.cos((double)radian));
		Vecteur top = new Vecteur();
		Vecteur bottom = new Vecteur();
		top.add(tempx);
		top.add(tempy);
		top.add(tempz);
		tempz.set(nr);
		tempz.scale((float)Math.cos((double)radian*2));
		bottom.add(tempx);
		bottom.add(tempy);
		bottom.add(tempz);

		int k;

		for(int l=1;l<res/2+1;l++){
			if(fiz == 3){fiz=1;}
			k = (-fiz*l)+(fiz-1)/2;
			if(k>res/2){k-=res/2;}
			if(k<0){k+=res/2;}


			float para = 3f*(((float)firstposition)/((float)res) - ((float)(firstposition)*(firstposition))/((float)(res*res)));
			para = firstposition/res;
			gl.glColor4f(0,0.8f,0.2f,0.4f);
			int j;

			gl.glBegin(GL2.GL_QUAD_STRIP);

			for(int i=0;i<res+1;i++){
				j = (-fix*fiy*fiz*i+firstposition);
				if(j>res){j-=res;}
				if(j<0){j+=res;}

				if(j<res/2){para = 2*j/(float)res;}else{para = 2*(1-j/(float)res);}

				tempx.set(ur);
				tempy.set(vr);
				tempz.set(nr);
				tempx.scale((float)Math.sin((double)radian*(k))*(float)Math.cos((double)radian*(j)));
				tempy.scale((float)Math.sin((double)radian*(k))*(float)Math.sin((double)radian*(j)));
				tempz.scale((float)Math.cos((double)radian*(k)));
				top.scale(0);
				bottom.scale(0);
				top.add(tempx);
				top.add(tempy);
				top.add(tempz);
				tempx.set(ur);
				tempy.set(vr);
				tempz.set(nr);
				tempx.scale((float)Math.sin((double)radian*(k+1))*(float)Math.cos((double)radian*(j+fix*fiy*fiz)));
				tempy.scale((float)Math.sin((double)radian*(k+1))*(float)Math.sin((double)radian*(j+fix*fiy*fiz)));
				tempz.scale((float)Math.cos((double)radian*(k+1)));
				bottom.add(tempx);
				bottom.add(tempy);
				bottom.add(tempz);

				gl.glColor4f(para,0.8f,0.2f,para/8f+0.4f);

				vect3ToVertex(gl,top);
				vect3ToVertex(gl,bottom);
			}
			gl.glEnd();
		}

	}

	private void rotation(Matrice matrix){
		matrix.transform(ur);
		matrix.transform(vr);
		matrix.transform(nr);
	}

	public void resetRotation(){
		this.ur = new Vecteur(this.u);
		this.vr = new Vecteur(this.v);
		this.nr = new Vecteur(this.np);
	}

	public void xRotation(float rad){
		Matrice matrix = new Matrice();
		xrot = rad;
		matrix.rotX(xrot);
		rotation(matrix);
	}
	public void yRotation(float rad){
		Matrice matrix = new Matrice();
		yrot = rad;
		matrix.rotY(yrot);
		rotation(matrix);
	}
	public void zRotation(float rad){
		Matrice matrix = new Matrice();
		zrot = rad;
		matrix.rotZ(zrot);
		rotation(matrix);
	}

	public void vect3ToVertex(GL2 gl, Vecteur b){
		float px = b.getX();
		float py = b.getY();
		float pz = b.getZ();
		gl.glVertex3f(px,py,pz);
	}


	public float getH(){
		return this.np.length();
	}

	public float getProf(){
		return 2f;
	}

	protected void drawBorders(GL2 gl){
	}

	public int compareTo(Piece p) {
		//le 100 évite un "threathold effect" à cause de la conversion en int
		float pc =	1000*(this.getProf() - p.getProf());
		return (int) pc;
	}
}
