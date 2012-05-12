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

public class Matrice extends SimpleMatrix implements Matrice3f{

	public Matrice(){
		super(3,3);
	}

	public Matrice(SimpleMatrix mat){
		super(mat);
	}

	public Matrice(float m00, float m01, float m02, float m10, float m11, float m12,float m20, float m21, float m22){
		super(3,3,true,m00,m01,m02,m10,m11,m12,m20,m21,m22);
	}

	public void set(float m00, float m01, float m02, float m10, float m11, float m12,float m20, float m21, float m22){
		SimpleMatrix temp = new SimpleMatrix(3,3,true,m00,m01,m02,m10,m11,m12,m20,m21,m22);
		super.set(temp);
	}

	public void rotX(float angle){
		float   sinAngle, cosAngle;
		sinAngle = (float) Math.sin((double) angle);
		cosAngle = (float) Math.cos((double) angle);
		float m00 = (float) 1.0;
		float m01 = (float) 0.0;
		float m02 = (float) 0.0;
		float m10 = (float) 0.0;
		float m11 = cosAngle;
		float m12 = -sinAngle;
		float m20 = (float) 0.0;
		float m21 = sinAngle;
		float m22 = cosAngle;
		this.set(m00,m01,m02,m10,m11,m12,m20,m21,m22);
	}

	public void rotY(float angle){
		float   sinAngle, cosAngle;
		sinAngle = (float) Math.sin((double) angle);
		cosAngle = (float) Math.cos((double) angle);
		float m00 = cosAngle;
		float m01 = (float) 0.0;
		float m02 = sinAngle;
		float m10 = (float) 0.0;
		float m11 = (float) 1.0;
		float m12 = (float) 0.0;
		float m20 = -sinAngle;
		float m21 = (float) 0.0;
		float m22 = cosAngle;
		this.set(m00,m01,m02,m10,m11,m12,m20,m21,m22);
	}

	public void rotZ(float angle){
		float   sinAngle, cosAngle;
		sinAngle = (float) Math.sin((double) angle);
		cosAngle = (float) Math.cos((double) angle);
		float m00 = cosAngle;
		float m01 = -sinAngle;
		float m02 = (float) 0.0;
		float m10 = sinAngle;
		float m11 = cosAngle;
		float m12 = (float) 0.0;
		float m20 = (float) 0.0;
		float m21 = (float) 0.0;
		float m22 = (float) 1.0;
		this.set(m00,m01,m02,m10,m11,m12,m20,m21,m22);
	}

	public void transform(Vecteur3f vect) {
		((Vecteur)vect).set(this.mult((Vecteur)vect));
	}
}
