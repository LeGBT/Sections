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

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

public class HelpScene extends Scene implements MouseListener {
	private Sections sect;
	private AideUIItem faitourner;
	private AideUIItem translate;
	private AideUIItem rotate;
	private AideUIItem souris;
	private AideUIItem translateplan;
	private AideUIItem rotateplan;
	private AideUIItem fleche;
	private AideUIItem suivant;
	private MiniCube cube;
	private int t = 0;
	private double c = 0;
	private int i = 0;
	private int scene = 0;

	public HelpScene(Sections sect) {
		super(0, 0, sect);
		this.sect=sect;
		faitourner = new AideUIItem(sect, 310, 32, 0.625f, 1f, 223, 222);
		translate = new AideUIItem(sect, 206, 32, 0.805f, 1f, 235, 234);
		rotate = new AideUIItem(sect, 265, 32, 0.517f, 1f, 233, 232);
		suivant = new AideUIItem(sect, 132, 32, 1f, 1f, 231, 230);
		fleche = new AideUIItem(sect, 64, 32, 1f, 1f, 229, 228);
		souris = new AideUIItem(sect, 384, 279, 1, 0.726f, 225, 224);
		translateplan = new AideUIItem(sect,472,512,0.922f,1f, 241, 240);
		rotateplan = new AideUIItem(sect,472,491,0.961f,1f, 243, 242);
		cube = new MiniCube(sect, 9.8f, 14.5f, 1.2f);
	}

	public void render(GL2 gl) {
		i += 4;
		i %= 720;
		c = Math.cos(i / 360.0 * 3.1416);
		t = (int) Math.round(30 * c);

		preRender();
		gl.glLoadIdentity();
		if (instance.isBonemode()) {
			gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
			gl.glEnable(GL2.GL_FOG);
		} else {
			gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
			gl.glDisable(GL2.GL_FOG);
		}
		gl.glEnable(GL2.GL_FOG);
		gl.glEnable(GL2.GL_DEPTH_TEST);
		fleche.traceUIItem(gl, 700, 25);
		suivant.traceUIItem(gl, 550, 25);
		switch(scene){
			case 0:
				faitourner.traceUIItem(gl, 465, 620);
				translate.traceUIItem(gl,465,620);
				souris.traceUIItem(gl, 440 + t, 100);
				cube.traceCube(gl, t);break;
			case 1:
				translate.traceUIItem(gl,465,620);
				translateplan.traceUIItem(gl, 440, 100);break;
			case 2:
				rotate.traceUIItem(gl,465,620);
				rotateplan.traceUIItem(gl, 440, 100);break;
		}
	}

	public void mouseClicked(MouseEvent me) {
		float h = sect.height;	
		float w = 16/9f*sect.height;	
		if(me.getX()<0.2f*sect.height){
			sect.window.removeMouseListener(sect.hlp);
		}
		if((me.getX()>0.43*w)&&(me.getX()<0.6*w)&&(me.getY()>0.928f*h)&&(me.getY()<0.965f*h)){
			scene++;
			scene %= 3;
		}
	}
	public void mouseDragged(MouseEvent arg0){}
	public void mouseEntered(MouseEvent me) {
	}

	public void mouseExited(MouseEvent me){}
	public void mouseMoved(MouseEvent me){
		float h = sect.height;	
		float w = 16/9f*sect.height;	
		if((me.getX()>0.43*w)&&(me.getX()<0.6*w)&&(me.getY()>0.928f*h)&&(me.getY()<0.965f*h)){
			fleche.setTextureId(237);
			fleche.setTextureIdInvert(236);
			suivant.setTextureId(239);
			suivant.setTextureIdInvert(238);
		}else{
			fleche.setTextureId(229);
			fleche.setTextureIdInvert(228);
			suivant.setTextureId(231);
			suivant.setTextureIdInvert(230);
		}
	}
	public void mousePressed(MouseEvent arg0){}
	public void mouseReleased(MouseEvent arg0){}
	public void mouseWheelMoved(MouseEvent arg0){}

}
