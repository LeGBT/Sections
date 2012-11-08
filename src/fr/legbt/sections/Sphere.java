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

public class Sphere extends Volume{
	private SpherePiece sp;
	private Disc border;

	public Sphere(){
		sp = new SpherePiece(u1,u2,u3);
	//	border = new Disc(u2,u1,nul);
		border = new Disc(u2,u1,nul);
		pieces.add(sp);
		pieces.add(border);
		super.sort();
	}

	public void traceBorders(GL2 gl, float red){
		for(int i=0;i<pieces.size();i++){
			if(pieces.get(i) instanceof Disc){
				Disc d = (Disc) pieces.get(i);
				d.traceBorders(gl,0.7f);
			}
		}
	}

	public void traceMe(GL2 gl){
		for(int i=0;i<pieces.size();i++){
			pieces.get(i).traceMe(gl);
		}
	}

	public void traceBorders(GL2 gl, float red, float off) {}

}
