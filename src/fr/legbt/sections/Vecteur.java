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

public class Vecteur implements Vecteur3d{
	private double x;
	private double y;
	private double z;

	public Vecteur(){
		x=0;
		y=0;
		z=0;
	}

	public Vecteur(Vecteur3d vect){
		x=vect.X();
		y=vect.Y();
		z=vect.Z();
	}

	public Vecteur(double x, double y ,double z){
		this.x=x;
		this.y=y;
		this.z=z;
	}

	public void rotate(Quaternion h){
		h.rotate(this);
	}

	public double dot(Vecteur3d vect){
		return x*vect.X() + y*vect.Y() + z*vect.Z(); 
	}

	public void set(double tx, double ty, double tz){
		x = tx;
		y = ty;
		z = tz;
	}

	public void set(Vecteur vect){
		x = vect.X();
		y = vect.Y();
		z = vect.Z();
	}

	public double X(){
		return x;
	}

	public double Y(){
		return y;
	}

	public double Z(){
		return z;
	}

	public double norm(){
		return Math.sqrt(x*x + y*y + z*z);
	}

	public double length() {
		return this.norm();
	}

	public void add(Vecteur3d vect){
		x += vect.X();
		y += vect.Y();
		z += vect.Z();
	}

	public void sub(Vecteur3d vect){
		x -= vect.X();
		y -= vect.Y();
		z -= vect.Z();
	}

	public void scale(double k){
		x *= k; 
		y *= k; 
		z *= k; 
	}

	public void scaleAdd(double k, Vecteur3d v1, Vecteur3d v2){
		Vecteur temp = new Vecteur(v1);
		temp.scale(k);
		v2.add(temp);
	}

	public void normalize(){
		if(this.norm()!=0){
			this.scale(1./this.norm());
		}
	}
}
