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


public class Cube {
	private Face top;
	private Face bottom;
	private Face left;
	private Face right;
	private Face front;
	private Face back;
	private ArrayList<Face> faces;

	public Cube(){
		Vecteur u1 = new Vecteur(1,0,0);
		Vecteur u2 = new Vecteur(0,1,0);
		Vecteur u3 = new Vecteur(0,0,1);
		Vecteur u4 = new Vecteur(-1,0,0);
		Vecteur u5 = new Vecteur(0,-1,0);
		Vecteur u6 = new Vecteur(0,0,-1);
		top = new Face(u1,u6,u2);
		bottom = new Face(u1,u3,u5);
		left = new Face(u2,u6,u4);
		right = new Face(u5,u6,u1);
		front = new Face(u1,u2,u3);
		back = new Face(u1,u5,u6);
		faces = new ArrayList<Face>();
		faces.add(top);
		faces.add(bottom);
		faces.add(left);
		faces.add(right);
		faces.add(front);
		faces.add(back);
		Collections.sort(faces);
	}

	public void resetRotation(){
		for(int i=0;i<faces.size();i++){
			faces.get(i).resetRotation();
		}
	}

	public void xRotation(float degree){
		for(int i=0;i<faces.size();i++){
			faces.get(i).xRotation(degree);
		}
	}
	public void yRotation(float degree){
		for(int i=0;i<faces.size();i++){
			faces.get(i).yRotation(degree);
		}
	}
	public void zRotation(float degree){
		for(int i=0;i<faces.size();i++){
			faces.get(i).zRotation(degree);
		}
	}

	
	public void sort(){
		Collections.sort(faces);
	}

	public void traceMe(GL2 gl){
		for(int i=0;i<faces.size();i++){
			faces.get(i).traceMe(gl);
		}
	}

	public void traceBorders(GL2 gl,float red,float green,float blue,float trans,float off){
		for(int i=0;i<faces.size();i++){
			faces.get(i).traceBorders(gl,red,green,blue,trans,off);
		}
	}

	public void traceBorders(GL2 gl,float red){
		for(int i=0;i<faces.size();i++){
			faces.get(i).traceBorders(gl,red);
		}
	}

}
