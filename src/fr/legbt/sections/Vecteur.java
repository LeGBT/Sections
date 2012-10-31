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

import org.ejml.simple.SimpleMatrix;


public class Vecteur extends SimpleMatrix implements Vecteur3f{

	public Vecteur(){
		super(3,1);
	}

	public Vecteur(Vecteur vect){
		super(vect);
	}

	public Vecteur(SimpleMatrix vect){
		super(vect);
	}

	public Vecteur(float x, float y ,float z){
		super(3,1,true,x,y,z);
	}

	public float dot(Vecteur3f vect){
		return (float) this.dot(vect);	
	}

	public void set(Vecteur vect){
		((SimpleMatrix)this).set(vect);
	}

	public float getX(){
		return (float) this.get(0,0);
	}

	public float getY(){
		return (float) this.get(1,0);
	}

	public float getZ(){
		return (float) this.get(2,0);
	}

	public float length() {
		return (float) this.normF();
	}

	public void set(SimpleMatrix v){
		super.set(v);
	}

	public void add(Vecteur3f vect){
		this.set(this.plus((Vecteur)vect));
	}

	public void sub(Vecteur3f vect){
		this.set(this.minus((Vecteur)vect));
	}

	public void scale(float k){
		this.set(((SimpleMatrix)this).scale(k));
	}

	public void scaleAdd(float k, Vecteur3f v1, Vecteur3f v2){
		Vecteur temp = new Vecteur(((SimpleMatrix)v1).scale(k));
		v2.add(temp);
	}
}

