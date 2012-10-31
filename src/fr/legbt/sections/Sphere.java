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

public class Sphere {
	private SpherePiece sp;
	private Disc border;
	private ArrayList<Piece> pieces;

	public Sphere(){
		Vecteur u1 = new Vecteur(1,0,0);
		Vecteur u2 = new Vecteur(0,1,0);
		Vecteur u3 = new Vecteur(0,0,1);
		Vecteur nul = new Vecteur(0,0,0);
		pieces = new ArrayList<Piece>();
		sp = new SpherePiece(u1,u2,u3);
		border = new Disc(u2,u1,nul);
		pieces.add(sp);
		pieces.add(border);
		Collections.sort(pieces);
	}


	public void resetRotation(){
		for(int i=0;i<pieces.size();i++){
			if (!(pieces.get(i) instanceof Disc)){
				pieces.get(i).resetRotation();
			}
		}
	}


	public void xRotation(float degree){
		for(int i=0;i<pieces.size();i++){
			if (!(pieces.get(i) instanceof Disc)){
				pieces.get(i).xRotation(degree);
			}
		}
	}

	public void yRotation(float degree){
		for(int i=0;i<pieces.size();i++){
			if (!(pieces.get(i) instanceof Disc)){
				pieces.get(i).yRotation(degree);
			}
		}
	}

	public void zRotation(float degree){
		for(int i=0;i<pieces.size();i++){
			if (!(pieces.get(i) instanceof Disc)){
				pieces.get(i).zRotation(degree);
			}
		}
	}

	public void sort(){
		Collections.sort(pieces);
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

}
