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

public class HelpScene extends Scene{
	private AideUIItem faitourner;
	private AideUIItem souris;
	private MiniCube cube;
	private int t=0;
	private double c=0;
	private int i = 0;

	public HelpScene(Sections instance){
		super(0,0,instance);
		faitourner = new AideUIItem(instance,310,32,0.625f,1f,223,222);
		souris = new AideUIItem(instance,384,279,1,0.726f,225,224);
		cube = new MiniCube(instance,9.8f,14.5f,1.2f);
	}

	public void render(GL2 gl){
		i+=4;
		i%=720;
		c =	Math.cos(i/360.0*3.1416);
		t = (int)Math.round(30*c);

		preRender();
		gl.glLoadIdentity();
		if(instance.isBonemode()){
			gl.glClearColor(1.0f,1.0f,1.0f,1.0f);
			gl.glEnable(GL2.GL_FOG);
		}else{
			gl.glClearColor(0.0f,0.0f,0.0f,0.0f);
			gl.glDisable(GL2.GL_FOG);
		}
		gl.glEnable(GL2.GL_FOG);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		faitourner.traceUIItem(gl,465,620);
		souris.traceUIItem(gl,440+t,100);
		cube.traceCube(gl,t);
		
	}

}
