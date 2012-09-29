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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;


public class Button3D implements MouseListener,MouseMotionListener{
	private float scale = 1.81f;
	private float h = scale*0.222f;
	private float l = 0.125f;
	private float le = scale*l;
	private int size = Math.round(1280*scale/16);
	private int bonus;
	private String[] textboutons = {"    Cube"," Pavé droit","  Cylindre"," Pyramide","   Sphère"};
	private String[] textboutonscubepave = {"Face","   Arête"};
	private String[] textboutonscylindre = {"Base","Axe"};
	private String[] activebonus;
	private Sections instance;

	public  Button3D(Sections instance){
		this.instance = instance;
		getTextBonus();
	}


	public void getTextBonus(){
		if(instance.getActiveview() == 3){
			activebonus = textboutonscylindre;
		}else{
			activebonus = textboutonscubepave;
		}
		if(instance.isPlantype()){
			bonus = 1;
		}else{bonus = 0;}
	}


	public void drawButton(GL2 gl,TextRenderer renderer,TextRenderer rendererbis, int activeview){
		gl.glLoadIdentity();
		traceMe(gl,renderer,1,activeview);
		traceMe(gl,renderer,2,activeview);
		traceMe(gl,renderer,3,activeview);
		traceMe(gl,renderer,4,activeview);
		traceMe(gl,renderer,5,activeview);
		traceBonus(gl,activeview);
	}

	private void traceBonus(GL2 gl, int activeview){
		getTextBonus();
		float a = System.getProperty("os.name").equals("Mac OS X") ? 45f:1f;
		float b = System.getProperty("os.name").equals("Mac OS X") ? 120f:1f;
		if(activeview<4){
			if(instance.isBonemode()){
				if(bonus==1){
					instance.getTextures().bind(gl,204);
				}else{
					instance.getTextures().bind(gl,206);
				}
			}else{
				if(bonus==1){
					instance.getTextures().bind(gl,205);
				}else{
					instance.getTextures().bind(gl,207);
				}
			}
			gl.glBegin(GL2.GL_QUADS);
			gl.glColor4f(1f,1f,1f,1f);
			gl.glTexCoord2f(0f,a);
			gl.glVertex3f(0.575f,0.875f,0);
			gl.glTexCoord2f(b,a);
			gl.glVertex3f(0.7625f,0.875f,0);
			gl.glTexCoord2f(b,0f);
			gl.glVertex3f(0.7625f,1f,0);
			gl.glTexCoord2f(0f,0f);
			gl.glVertex3f(0.575f,1f,0);
			gl.glEnd();
			instance.getTextures().unbind(gl);
		}
		tracePrefs(gl);
	}


	private void tracePrefs(GL2 gl){
		//TODO gros refactoring à faire pour virer ça
		float a = System.getProperty("os.name").equals("Mac OS X") ? 45f:1f;
		float b = System.getProperty("os.name").equals("Mac OS X") ? 120f:1f;
		if(instance.isBonemode()){
			instance.getTextures().bind(gl,202);
		}else{
			instance.getTextures().bind(gl,203);
		}
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4f(1f,1f,1f,1f);
		gl.glTexCoord2f(0f,a);
		gl.glVertex3f(0.8125f,0.875f,0);
		gl.glTexCoord2f(b,a);
		gl.glVertex3f(1f,0.875f,0);
		gl.glTexCoord2f(b,0f);
		gl.glVertex3f(1f,1f,0);
		gl.glTexCoord2f(0f,0f);
		gl.glVertex3f(0.8125f,1f,0);
		gl.glEnd();
		instance.getTextures().unbind(gl);
	}


	private void traceMe(GL2 gl,TextRenderer renderer, int n, int activeview){
		float c = 0;
		float pos = n*h;
		//TODO gros refactoring à faire pour virer ça
		float a = System.getProperty("os.name").equals("Mac OS X") ? 140f:1f;
		if(instance.isBonemode()){
			instance.getTextures().bind(gl,207 + 2*n );
			c = (n==activeview)?  1f:0.3f;
		}else{
			instance.getTextures().bind(gl,206 + 2*n);
			c = (n==activeview)?  1f:0.5f;
		}


		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4f(1f,1f,1f,c);
		gl.glTexCoord2f(0f,0f);
		gl.glVertex3f(-1,1-pos+h,0);


		//gl.glColor4f(0.9f-c*8,0.9f,0.9f-c*8,0.9f-c);
		gl.glColor4f(1f,1f,1f,c);
		gl.glTexCoord2f(a,0f);
		gl.glVertex3f(-1+le,1-pos+h,0);

		//gl.glColor4f(0.6f-c,0.6f+2*c,0.6f-5*c,0.9f-c);
		gl.glColor4f(1f,1f,1f,c);
		gl.glTexCoord2f(a,a);
		gl.glVertex3f(-1+le,1-pos,0);

		//gl.glColor4f(0.4f-3*c,0.4f+3*c,0.4f-3*c,0.9f-c);
		gl.glColor4f(1f,1f,1f,c);
		gl.glTexCoord2f(0f,a);
		gl.glVertex3f(-1,1f-pos,0);
		gl.glEnd();

		instance.getTextures().unbind(gl);
		//renderer.beginRendering(1280,720);
		//renderer.setColor(1f,1f,1f,0.5f+5*c);
		//renderer.draw(textboutons[n-1],15,780-(int)(pos*360));
		//renderer.endRendering();

	}


	public void mouseClicked(MouseEvent me){
		int button = 0;
		if(me.getX()<size-3){
			button = me.getY()*5/710+1;
			this.instance.setActiveview(button);
		}
		if((me.getX()>976)&&(me.getX()<1096)&&(me.getY()<45)){
			instance.changePlanType();
			instance.reset();
		}
		if((me.getX()>1125)&&(me.getY()<45)){
			instance.switchBonemode();
		}
	}

	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0){}
	public void mousePressed(MouseEvent arg0){}
	public void mouseReleased(MouseEvent arg0){}

	public void mouseDragged(MouseEvent me) {
		int button = 0;
		if(me.getX()<size-3){
			button = me.getY()*5/710+1;
			this.instance.setActiveview(button);
		}
	}

	public void mouseMoved(MouseEvent arg0){}
}
