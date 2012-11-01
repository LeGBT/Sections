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


public class Pave extends Volume{
	private Face top;
	private Face bottom;
	private Face left;
	private Face right;
	private Face front;
	private Face back;

	public Pave(){
		top = new Face(u11,u6,u22);
		bottom = new Face(u11,u3,u52);
		left = new Face(u22,u6,u41);
		right = new Face(u52,u6,u11);
		front = new Face(u11,u22,u3);
		back = new Face(u11,u52,u6);
		pieces.add(top);
		pieces.add(bottom);
		pieces.add(left);
		pieces.add(right);
		pieces.add(front);
		pieces.add(back);
		sort();
	}


	public void traceBorders(GL2 gl,float red,float green,float blue,float trans,float off){
		for(int i=0;i<pieces.size();i++){
			((Face)pieces.get(i)).traceBorders(gl,red,green,blue,trans,off);
		}
	}

	public void traceBorders(GL2 gl,float red){
		for(int i=0;i<pieces.size();i++){
			((Face)pieces.get(i)).traceBorders(gl,red);
		}
	}

	public void traceBorders(GL2 gl, float red, float off) {}

}
