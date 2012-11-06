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

public class Button3D implements MouseListener{
	private Sections sect;
	//	private int size;
	private int bonus;

	public  Button3D(Sections sect){
		this.sect = sect;
		getTextBonus();
		//		size = Math.round((int)(sect.height*sect.format/9));
	}


	public void getTextBonus(){
		bonus = (sect.isPlantype())? 1:0;
	}


	public void drawButton(GL2 gl, int activeview){
		gl.glLoadIdentity();
		for(int i=1;i<6;i++){
			traceMe(gl,i,activeview);
		}
		traceBonus(gl,activeview);
	}

	private void traceBonus(GL2 gl, int activeview){
		float tailleicone = (float) (0.1875f/sect.format*16/9);
		float marge = (float) (0.05f/16*9/sect.format);
		getTextBonus();
		if(activeview<4){
			if(sect.isBonemode()){
				if(bonus==1){
					sect.getTextures().bind(gl,204);
				}else{
					sect.getTextures().bind(gl,206);
				}
			}else{
				if(bonus==1){
					sect.getTextures().bind(gl,205);
				}else{
					sect.getTextures().bind(gl,207);
				}
			}
			// L=240 l=90 pour une res de 1280*720
			gl.glBegin(GL2.GL_QUADS);
			gl.glColor4f(1f,1f,1f,1f);
			gl.glTexCoord2f(0f,0f);
			gl.glVertex3f(1f-3*tailleicone-2*marge,0.875f,0);
			gl.glTexCoord2f(1f,0f);
			gl.glVertex3f(1f-2*tailleicone-2*marge,0.875f,0);
			gl.glTexCoord2f(1f,1f);
			gl.glVertex3f(1f-2*tailleicone-2*marge,1f,0);
			gl.glTexCoord2f(0f,1f);
			gl.glVertex3f(1-3*tailleicone-2*marge,1f,0);
			gl.glEnd();
			sect.getTextures().unbind(gl);
		}
		traceButtonMode(gl);
		traceButtonFullscreen(gl);
		traceButtonAide(gl);
	}

	private void traceButtonFullscreen(GL2 gl){
		float tailleicone = (float) (0.15f/sect.format);

		if(sect.isBonemode()){
			sect.getTextures().bind(gl,219);
		}else{
			sect.getTextures().bind(gl,218);
		}

		// L=240 l=90 pour une res de 1280*720
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4f(1f,1f,1f,1f);
		gl.glTexCoord2f(0f,0f);
		gl.glVertex3f(1f-tailleicone,-1f,0);
		gl.glTexCoord2f(1f,0f);
		gl.glVertex3f(1f,-1f,0);
		gl.glTexCoord2f(1f,1f);
		gl.glVertex3f(1f,-0.85f,0);
		gl.glTexCoord2f(0f,1f);
		gl.glVertex3f(1f-tailleicone,-0.85f,0);
		gl.glEnd();
		sect.getTextures().unbind(gl);
	}

	private void traceButtonAide(GL2 gl){
		float tailleicone = (float) (0.1875f/sect.format*16/9);
		float marge = (float) (0.05f/16*9/sect.format);

		if(sect.isBonemode()){
			sect.getTextures().bind(gl,202);
		}else{
			sect.getTextures().bind(gl,203);
		}

		// L=240 l=90 pour une res de 1280*720
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4f(1f,1f,1f,1f);
		gl.glTexCoord2f(0f,0f);
		gl.glVertex3f(1f-2*tailleicone-marge,0.875f,0);
		gl.glTexCoord2f(1f,0f);
		gl.glVertex3f(1f-tailleicone-marge,0.875f,0);
		gl.glTexCoord2f(1f,1f);
		gl.glVertex3f(1f-tailleicone-marge,1f,0);
		gl.glTexCoord2f(0f,1f);
		gl.glVertex3f(1-2*tailleicone-marge,1f,0);
		gl.glEnd();
		sect.getTextures().unbind(gl);
	}

	private void traceButtonMode(GL2 gl){
		float tailleicone = (float) (0.1875f/sect.format*16/9);
		if(sect.isBonemode()){
			sect.getTextures().bind(gl,220);
		}else{
			sect.getTextures().bind(gl,221);
		}

		// L=240 l=90 pour une res de 1280*720
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4f(1f,1f,1f,1f);
		gl.glTexCoord2f(0f,0f);
		gl.glVertex3f(1f-tailleicone,0.875f,0);
		gl.glTexCoord2f(1f,0f);
		gl.glVertex3f(1f,0.875f,0);
		gl.glTexCoord2f(1f,1f);
		gl.glVertex3f(1f,1f,0);
		gl.glTexCoord2f(0f,1f);
		gl.glVertex3f(1f-tailleicone,1f,0);
		gl.glEnd();
		sect.getTextures().unbind(gl);
	}


	private void traceMe(GL2 gl,int n, int activeview){
		float c = 0;
		//	float pos = n*h;
		float pos = 2*n/5.0f;
		//a donne la coordonnée de la texture (donc dépend de sa résolution)
		float a = 0.546875f;
		if(sect.isBonemode()){
			sect.getTextures().bind(gl,207 + 2*n );
			c = (n==activeview)?  1f:0.3f;
		}else{
			sect.getTextures().bind(gl,206 + 2*n);
			c = (n==activeview)?  1f:0.5f;
		}

		// Coin haut gauche
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4f(1f,1f,1f,c);
		gl.glTexCoord2f(0f,a);
		gl.glVertex3f(-1,1-pos+2/5f,0);


		// Coin haut droite
		gl.glColor4f(1f,1f,1f,c);
		gl.glTexCoord2f(a,a);
		gl.glVertex3f(-1+2/5f/(float)sect.format,1-pos+2/5f,0);

		// Coin bas droite
		gl.glColor4f(1f,1f,1f,c);
		gl.glTexCoord2f(a,0f);
		gl.glVertex3f(-1+2/5f/(float)sect.format,1-pos,0);

		// Coin bas gauche
		gl.glColor4f(1f,1f,1f,c);
		gl.glTexCoord2f(0f,0f);
		gl.glVertex3f(-1,1f-pos,0);
		gl.glEnd();

		sect.getTextures().unbind(gl);
	}


	public void mouseClicked(MouseEvent me){
		int button = 0;
		if(me.getX()<0.2f*sect.height){
			button = me.getY()*5/sect.height+1;
			this.sect.setActiveview(button);
		}
		float width = (float)(sect.height*sect.format);
		// .167~ 0.1875/2*16/9
		float buttonwidth = (float) (0.167*sect.height);
		float buttonfullscreenwidth = (float) (0.15/2*sect.height);
		float margin = (float) (0.014*sect.height);
		if((me.getX()>(width-3*buttonwidth-2*margin))&&(me.getX()<width-2*buttonwidth-2*margin)&&(me.getY()<0.0625f*sect.height)){
			sect.changePlanType();
			sect.reset();
		}
		if((me.getX()>(width-2*buttonwidth-margin))&&(me.getX()<width-buttonwidth-margin)&&(me.getY()<0.0625f*sect.height)){
			sect.switchBonemode();
		}
		if((me.getX()>width-buttonwidth)&&(me.getY()<0.0625f*sect.height)){
			sect.window.addMouseListener(sect.hlp);
			sect.setActiveview(6);
		}
		if((me.getX()>width-buttonfullscreenwidth)&&(me.getY()>sect.height-buttonfullscreenwidth)){
			sect.switchFullscreen();
		}
	}

	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0){}
	public void mousePressed(MouseEvent arg0){}
	public void mouseReleased(MouseEvent arg0){}

	public void mouseDragged(MouseEvent me){}

	public void mouseMoved(MouseEvent arg0){}
	public void mouseWheelMoved(MouseEvent arg0) {}

}
