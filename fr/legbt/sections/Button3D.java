/* 
 *    This file is part of Sections.
 *    Copyright © 2012 Alban Avenant
 *
 *     Sections is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
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
import javax.media.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;


public class Button3D implements MouseListener{
	private float scale = 1.81f;
	private float h = scale*0.222f;
	private float l = 0.125f;
	private float le = scale*l;
	private int size = Math.round(1280*scale/16);
	private int bonus;
	private String[] textboutons = {"    Cube","Pavé droit","  Cylindre","  Pyramide","    Sphère"};
	private String[] textboutonscubepave = {"Face","Arrête"};
	private String[] textboutonscylindre = {"⟂","//"};
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
		traceBonus(gl,renderer,rendererbis,activeview);
	}

	private void traceBonus(GL2 gl,TextRenderer renderer,TextRenderer rendererbis, int activeview){
		getTextBonus();
		if (activeview==1){

		}


		if(activeview<4){
			gl.glBegin(GL2.GL_QUADS);
			gl.glColor4f(0.1f+0.5f*bonus,1f-bonus*0.6f,0.2f,0.9f);
			gl.glVertex3f(1,-1,0);

			gl.glColor4f(0.6f,0.7f,0.4f,0.9f);
			gl.glVertex3f(1-le,-1,0);
			gl.glColor4f(0.6f-0.5f*bonus,0.4f+bonus*0.6f,0.2f,0.9f);
			gl.glVertex3f(1-le,-1+h,0);
			gl.glColor4f(0.9f,0.9f,0.9f,0.2f);
			gl.glVertex3f(1,-1f+h,0);
			gl.glEnd();

			if(activeview!=3){
				renderer.beginRendering(1280,720);
				renderer.setColor(0.9f,0.9f,0.9f,0.45f+bonus/2f);
				renderer.draw(activebonus[0],1150,110);
				renderer.setColor(0.9f,0.9f,0.9f,0.95f-bonus/2f);
				renderer.draw(activebonus[1],1190,20);
				renderer.endRendering();
			}else{
				rendererbis.beginRendering(1280,720);
				rendererbis.setColor(1,1,1,0.5f+bonus/2f);
				rendererbis.draw(activebonus[0],1150,100);
				rendererbis.setColor(1f,1f,1f,1f-bonus/2f);
				rendererbis.draw(activebonus[1],1230,20);
				rendererbis.endRendering();
			}

			//			gl.glColor4f(0.9f,0.9f,0.9f,0.9f);

			//			gl.glRasterPos3f(1-le*3/6f,-1+h*1/6f,0f);
			//			glut.glutBitmapString(font,activebonus[1]);
		}
	}




	private void traceMe(GL2 gl,TextRenderer renderer, int n, int activeview){
		float c = 0;
		if (n==activeview){c = 0.1f;}
		float pos = n*h;

		//		gl.glColor4f(1f - pos/10f,1f-pos/13f,0.1f+pos/10f,0.8f);
		//gl.glColor4f(1f/n,c + n*0.2f,pos,0.8f);

		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4f(0.6f+4*c,0.6f+c,0.6f-5*c,0.9f-c);
		gl.glVertex3f(-1,1-pos+h,0);


		gl.glColor4f(0.9f-c*8,0.9f,0.9f-c*8,0.9f-c);
		gl.glVertex3f(-1+le,1-pos+h,0);

		gl.glColor4f(0.6f-c,0.6f+2*c,0.6f-5*c,0.9f-c);
		gl.glVertex3f(-1+le,1-pos,0);

		gl.glColor4f(0.4f-3*c,0.4f+3*c,0.4f-3*c,0.9f-c);
		gl.glVertex3f(-1,1f-pos,0);
		gl.glEnd();

		renderer.beginRendering(1280,720);
		renderer.setColor(1f,1f,1f,0.5f+5*c);
		renderer.draw(textboutons[n-1],15,780-(int)(pos*360));
		renderer.endRendering();

	}


	public void mouseClicked(MouseEvent me){
		int button = 0;
		if(me.getX()<size-3){
			button = me.getY()*5/710+1;
			this.instance.setActiveview(button);
		}
		if((me.getX()>1100)&&(me.getY()>560)){
			instance.changePlanType();
			instance.reset();
		}
	}

	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0){}
	public void mousePressed(MouseEvent arg0){}
	public void mouseReleased(MouseEvent arg0){}
}
