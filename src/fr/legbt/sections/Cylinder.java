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


public class Cylinder extends Volume{
	private Disc top;
	private Disc bottom;
	private CylinderPiece cypiece1;

	public Cylinder(){
		top = new Disc(u1,u2,u3);
		bottom = new Disc(u4,u5,u6);
		cypiece1 = new CylinderPiece(u1,u2,u3);
		pieces.add(top);
		pieces.add(bottom);
		pieces.add(cypiece1);
		sort();
	}


	public void traceBorders(GL2 gl, float red,float off){
		for(int i=0;i<pieces.size();i++){
			if(pieces.get(i) instanceof Disc){
				Disc d = (Disc) pieces.get(i);
				d.traceBorders(gl,0.9f,off);
			}
		}
	}

	public void traceBorders(GL2 gl, float red){
		traceBorders(gl,red,0);
	}

	public void traceMe(GL2 gl, boolean black, boolean classic){
		cypiece1.traceMe(gl,black,classic);
	}
}
