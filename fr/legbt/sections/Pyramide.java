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

import java.util.ArrayList;
import java.util.Collections;

import javax.media.opengl.GL2;

public class Pyramide {
	private Face base;
	private Triangle front;
	private Triangle back;
	private Triangle left;
	private Triangle right;
	private ArrayList<Piece> pieces;

	public Pyramide(){
		Vecteur u1 = new Vecteur(1.5f,0,0);
		Vecteur u2 = new Vecteur(0,1,0);
		Vecteur u3 = new Vecteur(0,0,-1);
		Vecteur u4 = new Vecteur(0,0.75f,1f);
		Vecteur u5 = new Vecteur(0,-0.25f,0);
		Vecteur u7 = new Vecteur(0,-0.25f,1);
		Vecteur u8 = new Vecteur(0,0.75f,0);
		Vecteur u9 = new Vecteur(-0.25f,0,1);
		Vecteur u10 = new Vecteur(1.25f,0,0);
		Vecteur u11 = new Vecteur(1.25f,0,1);
		Vecteur u12 = new Vecteur(-0.25f,0,0);
		pieces = new ArrayList<Piece>();
		base = new Face(u1,u2,u3);
		front = new Triangle(0.66666667f,u1,u4,u5);
		back = new Triangle(0.66666667f,u1,u7,u8);
		left = new Triangle(0.5f,u2,u9,u10);
		right = new Triangle(0.5f,u2,u11,u12);
		pieces.add(base);
		pieces.add(front);
		pieces.add(back);
		pieces.add(left);
		pieces.add(right);
		Collections.sort(pieces);
	}

	public void resetRotation(){
		for(int i=0;i<pieces.size();i++){
			pieces.get(i).resetRotation();
		}
	}


	public void xRotation(float degree){
		for(int i=0;i<pieces.size();i++){
			pieces.get(i).xRotation(degree);
		}
	}

	public void yRotation(float degree){
		for(int i=0;i<pieces.size();i++){
			pieces.get(i).yRotation(degree);
		}
	}

	public void zRotation(float degree){
		for(int i=0;i<pieces.size();i++){
			pieces.get(i).zRotation(degree);
		}
	}

	public void sort(){
		Collections.sort(pieces);
	}

	public void traceBorders(GL2 gl, float red){
		traceBorders(gl,red,red,red,red,0);
	}

	public void traceBorders(GL2 gl, float red,float green, float blue,float trans,float off){
		for(int i=0;i<pieces.size();i++){
			if(pieces.get(i) instanceof Triangle){
				((Triangle)pieces.get(i)).traceBorders(gl,red,green,blue,trans,off);
			}else{
				//	((Face)pieces.get(i)).traceBorders(gl,red);
			}
		}
	}

	public void traceMe(GL2 gl){
		for(int i=0;i<pieces.size();i++){
			pieces.get(i).traceMe(gl);
		}
	}

}
