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

	public Quaternion(Quaternion h){
		this(
				h.R(),
				h.I(),
				h.J(),
				h.K(),
				true
			);
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

	public void mult(Vecteur v){




	}


	public void normalize(){
		double norm2 = r*r + i*i + j*j + k*k;
		if (norm2 != 0.0 && Math.abs(norm2 - 1)>0.00000001){
			double norm = Math.sqrt(norm2);
			r /= norm;
			i /= norm;
			j /= norm;
			k /= norm;
		}
	}

	public double getAngle(){
		return (Math.acos(r)/Math.PI*180.);
	}

	public void conj(){
		i*=-1.;
		j*=-1.;
		k*=-1.;
	}

	public void add(Quaternion b){
		r += b.R();
		i += b.I();
		j += b.J();
		k += b.K();
	}

	public void mult(Quaternion b){
		double tr,ti,tj,tk;
		tr = r;
		ti = i;
		tj = j;
		tk = k;
		r = tr*b.R() - ti*b.I() - tj*b.J() - tk*b.K();	
		i = tr*b.I() + ti*b.R() + tj*b.K() - tk*b.J();	
		j = tr*b.J() - ti*b.K() + tj*b.R() + tk*b.I();
		k = tr*b.K() + ti*b.J() - tj*b.I() + tk*b.R();
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
		return ( 
				Math.abs(r-b.R())<0.00000001
				&& Math.abs(i-b.I())<0.00000001
				&& Math.abs(j-b.J())<0.00000001
				&& Math.abs(k-b.K())<0.00000001
			   );
	}

	public void scale(double s){
		r*=s;
		i*=s;
		j*=s;
		k*=s;
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
