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

	public HelpScene(Sections instance){
		super(0,0,instance);
	}

	public void render(GL2 gl){
		if(instance.isBonemode()){
			gl.glClearColor(1.0f,1.0f,1.0f,1.0f);
			gl.glEnable(GL2.GL_FOG);
		}else{
			gl.glClearColor(0.0f,0.0f,0.0f,0.0f);
			gl.glDisable(GL2.GL_FOG);
		}
	}

}
