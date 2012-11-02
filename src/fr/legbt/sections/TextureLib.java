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
	static int tex2size = 19;
	private Texture tex101 = null;
	private Texture tex102 = null;
	private Texture[] tex2 = new Texture[tex2size+1];
	private int activetexture = 0;

	public  void dispose(GL2 gl){
		tex101.destroy(gl);
		tex102.destroy(gl);
		for(int i=1;i<tex2size+1;i++){
			tex2[i].destroy(gl);
		}
	}

	public void load(GL2 gl){
		try{
			InputStream input = this.getClass().getResourceAsStream("/resources/stipple.png");
			this.tex101 = TextureIO.newTexture(input,false,"png");
			input = this.getClass().getResourceAsStream("/resources/line.png");
			this.tex102 = TextureIO.newTexture(input,false,"png");
			input = this.getClass().getResourceAsStream("/resources/plan.png");
			this.tex2[1] = TextureIO.newTexture(input,false,"png");
			input = this.getClass().getResourceAsStream("/resources/button_mode.png");
			this.tex2[2] = TextureIO.newTexture(input,false,"png");
			input = this.getClass().getResourceAsStream("/resources/button_mode_revert.png");
			this.tex2[3] = TextureIO.newTexture(input,false,"png");
			input = this.getClass().getResourceAsStream("/resources/button_face.png");
			this.tex2[4] = TextureIO.newTexture(input,false,"png");
			input = this.getClass().getResourceAsStream("/resources/button_face_invert.png");
			this.tex2[5] = TextureIO.newTexture(input,false,"png");
			input = this.getClass().getResourceAsStream("/resources/button_arete.png");
			this.tex2[6] = TextureIO.newTexture(input,false,"png");
			input = this.getClass().getResourceAsStream("/resources/button_arete_invert.png");
			this.tex2[7] = TextureIO.newTexture(input,false,"png");
			input = this.getClass().getResourceAsStream("/resources/cube.png");
			this.tex2[8] = TextureIO.newTexture(input,false,"png");
			input = this.getClass().getResourceAsStream("/resources/cube_invert.png");
			this.tex2[9] = TextureIO.newTexture(input,false,"png");
			input = this.getClass().getResourceAsStream("/resources/pave.png");
			this.tex2[10] = TextureIO.newTexture(input,false,"png");
			input = this.getClass().getResourceAsStream("/resources/pave_invert.png");
			this.tex2[11] = TextureIO.newTexture(input,false,"png");
			input = this.getClass().getResourceAsStream("/resources/cylindre.png");
			this.tex2[12] = TextureIO.newTexture(input,false,"png");
			input = this.getClass().getResourceAsStream("/resources/cylindre_invert.png");
			this.tex2[13] = TextureIO.newTexture(input,false,"png");
			input = this.getClass().getResourceAsStream("/resources/pyramide.png");
			this.tex2[14] = TextureIO.newTexture(input,false,"png");
			input = this.getClass().getResourceAsStream("/resources/pyramide_invert.png");
			this.tex2[15] = TextureIO.newTexture(input,false,"png");
			input = this.getClass().getResourceAsStream("/resources/sphere.png");
			this.tex2[16] = TextureIO.newTexture(input,false,"png");
			input = this.getClass().getResourceAsStream("/resources/sphere_invert.png");
			this.tex2[17] = TextureIO.newTexture(input,false,"png");
			input = this.getClass().getResourceAsStream("/resources/fullscreen.png");
			this.tex2[18] = TextureIO.newTexture(input,false,"png");
			input = this.getClass().getResourceAsStream("/resources/fullscreen_invert.png");
			this.tex2[19] = TextureIO.newTexture(input,false,"png");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void bind(GL2 gl,int i){
		gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
		this.activetexture = i;
		//	gl.glTexEnvi( GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_BLEND);
		if(i<200){ 
			gl.glEnable(GL2.GL_TEXTURE_1D);       
			if(i==101){
				this.tex101.enable(gl);
				this.tex101.bind(gl);
			}else{
				this.tex102.enable(gl);
				this.tex102.bind(gl);
			}
		}else{
			gl.glEnable(GL2.GL_TEXTURE_2D);       
				this.tex2[i-200].enable(gl);
				this.tex2[i-200].bind(gl);
			}
		}
		public void unbind(GL2 gl){
			if(activetexture<200){ 
				if(activetexture==101){
					this.tex101.disable(gl);
				}else{
					this.tex102.disable(gl);
				}
				gl.glDisable(GL2.GL_TEXTURE_1D);       
			}else{
				this.tex2[activetexture-200].disable(gl);
				gl.glDisable(GL2.GL_TEXTURE_1D);       
			}
			this.activetexture = 0;
		}
	}

