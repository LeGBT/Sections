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

public class AideUIItem{
	private Sections sect;
	private int height;
	private int width;
	private int textureid;
	private int textureid_invert;
	private float xtexcoo;
	private float ytexcoo;

	// posx et posx sont en px et height, width en proportion
	public AideUIItem(Sections sect,int width,int height,float xtexcoo, float ytexcoo,int textureid,int textureid_invert) {
		this.sect = sect;
		this.height = height;
		this.width = width;
		this.textureid = textureid;
		this.textureid_invert = textureid_invert;
		this.xtexcoo = xtexcoo;
		this.ytexcoo = ytexcoo;
	}

	public void traceUIItem(GL2 gl,int posx,int posy){
		float fposx = getFX(posx);
		float fposy = getFY(posy);
		float fheight = getFHeight(height);
		float fwidth = getFWidth(width);
		if (sect.isBonemode()) {
			sect.getTextures().bind(gl, textureid_invert);
		} else {
			sect.getTextures().bind(gl, textureid);
		}

		// L=240 l=90 pour une res de 1280*720
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4f(1f, 1f, 1f, 1f);
		gl.glTexCoord2f(0f, 0f);
		gl.glVertex3f(fposx, fposy, 0);

		gl.glTexCoord2f(xtexcoo, 0f);
		gl.glVertex3f(fposx + 2*fwidth, fposy, 0);

		gl.glTexCoord2f(xtexcoo, ytexcoo);
		gl.glVertex3f(fposx + 2*fwidth, fposy + 2*fheight, 0);

		gl.glTexCoord2f(0f, ytexcoo);
		gl.glVertex3f(fposx, fposy + 2*fheight, 0);

		gl.glEnd();
		sect.getTextures().unbind(gl);
	}

	public  float getFX(float posx){
		return (2*posx)/((float)sect.format*720f) - 1f;

	}
	public float getFY(float posy){
		return (2*posy)/(720f) - 1f;
	}
	public float getFHeight(int height){
		return height/720f;
	}
	public float getFWidth(int width){
		return width/1280f/(float)sect.format*16f/9f;
	}
	public void setTextureId(int id){
		this.textureid = id;
	}
	public void setTextureIdInvert(int id){
		this.textureid_invert = id;
	}

}
