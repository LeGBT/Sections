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

public interface Bordered {

	public void setBorder(boolean b);
	public void setSphere(boolean b);
	public void reset(float angle);
	public void reset();
	public void resetRotation();
	public void setH(float h);
	public float getH();
	public void xRotation(float angle);
	public void yRotation(float angle);
	public void zRotation(float angle);
	public void traceMe(GL2 gl);
	public void traceMe(GL2 gl,float red,float green,float blue,float trans);
	public void traceBorders(GL2 gl, float red);
	public void traceBorders(GL2 gl, float red, float off);

}
