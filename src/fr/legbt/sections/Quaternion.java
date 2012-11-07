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


public class Quaternion  {
	private double r;
	private double i;
	private double j;
	private double k;

	public  Quaternion(double r,double i,double j,double k , boolean classic){
			this.r = r;
			this.i = i;
			this.j = j;
			this.k = k;
	}

	public Quaternion(){
		this(0,0,0,0,true);
	}

	public Quaternion(double alpha,double i,double j,double k){
			this(
					Math.cos(Math.PI*alpha/360.),
					i,
					j,
					k,
					true);
		}

	public float getAngle(){
		return (float)(Math.acos(r)/Math.PI*180);
	}

	private double imNorm(){return this.im().norm();}

	public float getrotI(){
		return (float)(i/this.imNorm());
	}
	public float getrotJ(){
		return (float)(i/this.imNorm());
	}
	public float getrotK(){
		return (float)(this.K()/this.imNorm());
	}

	private Quaternion im(){
		return new Quaternion(
				0,
				this.I(),
				this.J(),
				this.K(),
				true);
	}

	public Quaternion conj(){
		return  new Quaternion(
				this.R(),
				-this.I(),
				-this.J(),
				-this.K(),
				true);
	}

	public Quaternion add(Quaternion b){
		return new Quaternion(
				this.R()+b.R(),
				this.I()+b.I(),
				this.J()+b.J(),
				this.K()+b.K(),
				true);
	}

	public Quaternion mult(Quaternion b){
		return new Quaternion(
				r*b.R()-i*b.I()-j*b.J()-k*b.K(),	
				r*b.I()+i*b.R()+j*b.K()-k*b.J(),	
				r*b.J()-i*b.K()+j*b.R()+k*b.I(),
				r*b.K()+i*b.J()-j*b.I()+k*b.R(),
				true);
	}

	public double norm(){
		return 
			Math.sqrt(
					r*r +
					i*i +
					j*j +
					k*k ); 
	}


	public boolean equals(Quaternion b){
		return (  r == b.R()
				&& i == b.I()
				&& j == b.J()
				&& k == b.K() );
	}

	public Quaternion scale(double s){
		return new Quaternion(
				r*s,
				i*s,
				j*s,
				k*s,
				true);
	}

	/**
	 * @return the r
	 */
	public double R(){return r;}

	/**
	 * @return the i
	 */
	public double I(){return i;}

	/**
	 * @return the j
	 */
	public double J(){return j;}

	/**
	 * @return the k
	 */
	public double K(){return k;}
}
