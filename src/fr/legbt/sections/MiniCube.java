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

public class MiniCube {
	private Sections sect;
	private float size;
	private float posx;
	private float posy;
	private float mode;

	public MiniCube(Sections sect,float posx,float posy,float size){
		this.sect = sect;
		this.size = size;
		this.posx = posx;
		this.posy = posy;
	}

	public void traceCube(GL2 gl,float angle){
		if (sect.isBonemode()) {
			mode = 0.6f;
			gl.glColor4f(0f, 0f, 0f, 1f);
		} else {
			mode = 0;
			gl.glColor4f(1f-mode, 1f-mode, 1f-mode, 1f-mode);
		}
		float h= 0.1f/(float)sect.format;
		float w= 0.1f;
		gl.glLoadIdentity();
		gl.glScalef(h,w,0.3f);
		gl.glTranslatef(posx*16f/9-1/h,posy-1/w,0);
		gl.glRotatef(-30,1f,0f,0);

		gl.glRotatef(-angle*2,0f,1f,0);
		traceFace(gl,size);

		gl.glRotatef(90f,0,1f,0);
		gl.glColor4f(0.8f-mode, 0.8f-mode, 0.8f-mode, 1f);
		traceFace(gl,size);

		gl.glRotatef(90f,0,1f,0);
		gl.glColor4f(1f-mode, 1f-mode, 1f-mode, 1f);
		traceFace(gl,size);

		gl.glRotatef(90f,0,1f,0);
		gl.glColor4f(0.8f-mode, 0.8f-mode, 0.8f-mode, 1f);
		traceFace(gl,size);

		gl.glRotatef(-90f,1f,0f,0f);
		gl.glColor4f(0.6f-mode, 0.6f-mode, 0.6f-mode, 1f);
		traceFace(gl,size);

		gl.glLoadIdentity();
	}

	private void traceFace(GL2 gl,float c){
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3f(c,c,c);
		gl.glVertex3f(c,-c,c);
		gl.glVertex3f(-c,-c,c);
		gl.glVertex3f(-c,c,c);
		gl.glEnd();
	}
}
