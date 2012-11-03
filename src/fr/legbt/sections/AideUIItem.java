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

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import javax.media.opengl.GL2;

public class AideUIItem implements MouseListener {
	private Sections sect;
	private int posx;
	private int posy;
	private float height;
	private float width;
	private int textureid;
	private int textureid_invert;
	private float fposx;
	private float fposy;

	// posx et posx sont en px et height, width en proportion
	public AideUIItem(Sections sect,int posx,int posy,float width,float height,int textureid,int textureid_invert) {
		this.sect = sect;
		this.posx = posx;
		this.posy = posy;
		this.height = height;
		this.width = width;
		this.textureid = textureid;
		this.textureid_invert = textureid_invert;
		this.fposx = getFX();
		this.fposy = getFY();
		//		FIXME debug
		//System.out.println("fposx="+fposx);
		//System.out.println("fposy="+fposy);
	}

	public void traceUIItem(GL2 gl){
		if (sect.isBonemode()) {
			sect.getTextures().bind(gl, textureid_invert);
		} else {
			sect.getTextures().bind(gl, textureid);
		}

		// L=240 l=90 pour une res de 1280*720
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4f(1f, 1f, 1f, 1f);
		gl.glTexCoord2f(0f, 0f);
		//	gl.glVertex3f(fposx - width, fposy, 0);
		gl.glVertex3f(1f - width, fposy, 0);
		gl.glTexCoord2f(1f, 0f);
		//gl.glVertex3f(fposx, fposy, 0);
		gl.glVertex3f(1, fposy, 0);
		gl.glTexCoord2f(1f, 1f);
		//gl.glVertex3f(fposx, fposy - height, 0);
		gl.glVertex3f(1, fposy - height, 0);
		gl.glTexCoord2f(0f, 1f);
		//	gl.glVertex3f(fposx - width, fposy - height, 0);
		gl.glVertex3f(1 - width, fposy - height, 0);
		gl.glEnd();
		sect.getTextures().unbind(gl);
	}

	protected float getFX(){
		return (2*posx)/((float)sect.format*sect.height) - 1f;

	}
	protected float getFY(){
		return (2*posy)/(sect.height) - 1f;
	}


	private void traceButtonFullscreen(GL2 gl) {
		float tailleicone = (float) (0.15f / sect.format);

		if (sect.isBonemode()) {
			sect.getTextures().bind(gl, 219);
		} else {
			sect.getTextures().bind(gl, 218);
		}

		// L=240 l=90 pour une res de 1280*720
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4f(1f, 1f, 1f, 1f);
		gl.glTexCoord2f(0f, 0f);
		gl.glVertex3f(1f - tailleicone, -1f, 0);
		gl.glTexCoord2f(1f, 0f);
		gl.glVertex3f(1f, -1f, 0);
		gl.glTexCoord2f(1f, 1f);
		gl.glVertex3f(1f, -0.85f, 0);
		gl.glTexCoord2f(0f, 1f);
		gl.glVertex3f(1f - tailleicone, -0.85f, 0);
		gl.glEnd();
		sect.getTextures().unbind(gl);
	}


	public void mouseClicked(MouseEvent me) {
		int button = 0;
		if (me.getX() < 0.2f * sect.height) {
			button = me.getY() * 5 / sect.height + 1;
			this.sect.setActiveview(button);
		}
		float width = (float) (sect.height * sect.format);
		// .167~ 0.1875/2*16/9
		float buttonwidth = (float) (0.167 * sect.height);
		float buttonfullscreenwidth = (float) (0.15 / 2 * sect.height);
		float margin = (float) (0.014 * sect.height);
		if ((me.getX() > (width - 3 * buttonwidth - 2 * margin))
				&& (me.getX() < width - 2 * buttonwidth - 2 * margin)
				&& (me.getY() < 0.0625f * sect.height)) {
			sect.changePlanType();
			sect.reset();
				}
		if ((me.getX() > (width - 2 * buttonwidth - margin))
				&& (me.getX() < width - buttonwidth - margin)
				&& (me.getY() < 0.0625f * sect.height)) {
			sect.switchBonemode();
				}
		if ((me.getX() > width - buttonwidth)
				&& (me.getY() < 0.0625f * sect.height)) {
			sect.setActiveview(6);
				}
		if ((me.getX() > width - buttonfullscreenwidth)
				&& (me.getY() > sect.height - buttonfullscreenwidth)) {
			sect.switchFullscreen();
				}
	}

	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0){}
	public void mousePressed(MouseEvent arg0){}
	public void mouseReleased(MouseEvent arg0){}
	public void mouseDragged(MouseEvent me){}
	public void mouseMoved(MouseEvent arg0){}
	public void mouseWheelMoved(MouseEvent arg0){}

}
