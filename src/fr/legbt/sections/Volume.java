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


public abstract class Volume {
	protected ArrayList<Piece> pieces;
	protected Vecteur nul = new Vecteur(0,0,0);
	protected Vecteur u1 = new Vecteur(1,0,0);
	protected Vecteur u2 = new Vecteur(0,1,0);
	protected Vecteur u3 = new Vecteur(0,0,1);
	protected Vecteur u4 = new Vecteur(-1,0,0);
	protected Vecteur u5 = new Vecteur(0,-1,0);
	protected Vecteur u6 = new Vecteur(0,0,-1);
	protected Vecteur u8 = new Vecteur(0,0.75f,0);
	protected Vecteur u9 = new Vecteur(-0.25f,0,1);
	protected Vecteur u10 = new Vecteur(1.25f,0,0);
	protected Vecteur u11 = new Vecteur(1.5f,0,0);
	protected Vecteur u22 = new Vecteur(0,0.7f,0);
	protected Vecteur u41 = new Vecteur(-1.5f,0,0);
	protected Vecteur u52 = new Vecteur(0,-0.7f,0);
	protected Vecteur u12 = new Vecteur(-0.25f,0,0);
	protected Vecteur u101 = new Vecteur(1.25f,0,1);
	protected Vecteur u50 = new Vecteur(0,-0.25f,0);
	protected Vecteur u70 = new Vecteur(0,-0.25f,1);
	protected Vecteur u40 = new Vecteur(0,0.75f,1f);

	public Volume(){
		pieces = new ArrayList<Piece>();	
	}

	public void resetRotation(){
		for(int i=0;i<pieces.size();i++){
			pieces.get(i).resetRotation();
		}
	}

	public void xRotation(double degree){
		for(int i=0;i<pieces.size();i++){
			pieces.get(i).xRotation((float)degree);
		}
	}
	public void yRotation(double degree){
		for(int i=0;i<pieces.size();i++){
			pieces.get(i).yRotation((float)degree);
		}
	}
	public void zRotation(double degree){
		for(int i=0;i<pieces.size();i++){
			pieces.get(i).zRotation((float)degree);
		}
	}


	public void sort(){
		Collections.sort(pieces);
	}

	public void traceMe(GL2 gl){
		for(int i=0;i<pieces.size();i++){
			pieces.get(i).traceMe(gl);
		}
	}

	public abstract void traceBorders(GL2 gl,float red,float off);
	public abstract void traceBorders(GL2 gl,float red);

}
