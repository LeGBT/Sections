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

import java.io.InputStream;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;


public class TextureLib {
	private Texture tex11 = null;
	private Texture tex12 = null;
	private Texture tex21 = null;
	private int activetexture = 0;

	public  void dispose(GL2 gl){
		tex11.destroy(gl);
		tex12.destroy(gl);
		tex21.destroy(gl);
	}

	public void load(GL2 gl){
		try{
		InputStream input = this.getClass().getResourceAsStream("/resources/stipple.png");
			this.tex11 = TextureIO.newTexture(input,false,"png");
		input = this.getClass().getResourceAsStream("/resources/line.png");
			this.tex12 = TextureIO.newTexture(input,false,"png");
		input = this.getClass().getResourceAsStream("/resources/plan.png");
			this.tex21 = TextureIO.newTexture(input,false,"png");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void bind(GL2 gl,int i){
		gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
		this.activetexture = i;
		//	gl.glTexEnvi( GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_BLEND);
		if((i==11)||(i==12)){ 
			gl.glEnable(GL2.GL_TEXTURE_1D);       
			if(i==11){
				this.tex11.enable(gl);
				this.tex11.bind(gl);
			}else{
				this.tex12.enable(gl);
				this.tex12.bind(gl);
			}
		}else if(i==21){
			gl.glEnable(GL2.GL_TEXTURE_2D);       
			this.tex21.enable(gl);
			this.tex21.bind(gl);

		}
	}
	public void unbind(GL2 gl){
		if(activetexture<20){ 
			if(activetexture==11){
			this.tex11.disable(gl);
			}else{
			this.tex12.disable(gl);
			}
			gl.glDisable(GL2.GL_TEXTURE_1D);       
		}else{
			this.tex21.disable(gl);
			gl.glDisable(GL2.GL_TEXTURE_1D);       
		}
		this.activetexture = 0;
	}
}

