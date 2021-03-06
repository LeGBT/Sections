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
	private static final int TEX2SIZE = 2*( 23 ) +1;
	private Texture tex101 = null;
	private Texture tex102 = null;
	private Texture[] tex2 = new Texture[TEX2SIZE+1];
	private int activetexture = 0;

	public  void dispose(GL2 gl){
		tex101.destroy(gl);
		tex102.destroy(gl);
		for(int i=1;i<TEX2SIZE+1;i++){
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
			setT(1,"button_mode");
			setT(2,"button_face");
			setT(3,"button_arete");
			setT(4,"cube");
			setT(5,"pave");
			setT(6,"cylindre");
			setT(7,"pyramide");
			setT(8,"sphere");
			setT(9,"fullscreen");
			setT(10,"button_aide");
			setT(11,"tourner_figure");
			setT(12,"souris");
			setT(13,"cube_souris");
			setT(14,"fleche");
			setT(15,"suivant");
			setT(16,"tourner_plan");
			setT(17,"deplacer_plan");
			setT(18,"fleche_ro");
			setT(19,"suivant_ro");
			setT(20,"translation");
			setT(21,"rotation");
			setT(22,"imprim_ecran");
			setT(23,"shot");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void bind(GL2 gl,int i){
		//gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
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

	private void setT(int i,String name) throws Exception{
		InputStream input = this.getClass().getResourceAsStream("/resources/"+name+".png");
		this.tex2[2*i] = TextureIO.newTexture(input,false,"png");
		input = this.getClass().getResourceAsStream("/resources/"+name+"_invert.png");
		this.tex2[2*i+1] = TextureIO.newTexture(input,false,"png");
	}

}
