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

public class Pyramide extends Volume{
	private Face base;
	private Triangle front;
	private Triangle back;
	private Triangle left;
	private Triangle right;

	public Pyramide(){
		base = new Face(u11,u2,u6);
		front = new Triangle(0.66666667f,u11,u40,u50);
		back = new Triangle(0.66666667f,u11,u70,u8);
		left = new Triangle(0.5f,u2,u9,u10);
		right = new Triangle(0.5f,u2,u101,u12);
		pieces.add(base);
		pieces.add(front);
		pieces.add(back);
		pieces.add(left);
		pieces.add(right);
		sort();
	}
	public void traceBorders(GL2 gl, float red){
		traceBorders(gl,red,red,red,red,0);
	}

	public void traceBorders(GL2 gl, float red,float green, float blue,float trans,float off){
		for(int i=0;i<pieces.size();i++){
			if(pieces.get(i) instanceof Triangle){
				((Triangle)pieces.get(i)).traceBorders(gl,red,green,blue,trans,off);
			}
		}
	}


	public void traceBorders(GL2 gl, float red, float off) {}

}
