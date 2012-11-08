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

public interface Vecteur3d{

	public double dot(Vecteur3d vect);
	public double X();
	public double Y();
	public double Z();
	public double norm();
	public double length();
	public void normalize();
	public void add(Vecteur3d vect);
	public void sub(Vecteur3d vect);
	public void scale(double k);
	public void scaleAdd(double k, Vecteur3d v1, Vecteur3d v2);

}

