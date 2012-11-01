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
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.event.KeyEvent;

public class ActionListener implements GLEventListener, KeyListener, MouseListener{
	private int x = 0;
	private int y = 0;
	private boolean shift;
	private Sections section;
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;

	public ActionListener(Sections section){
		this.section = section;
		this.up = false;
		this.down = false;
		this.left = false;
		this.right = false;
	}

	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(0.0f,0.0f,0.0f,0.0f);
		gl.glClearDepth(1.0f);
		if(!section.isBonemode()){
			gl.glEnable(GL2.GL_FOG);
		}
		gl.glEnable(GL2.GL_BLEND);
		gl.glLineWidth(2f);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA,GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glDepthFunc(GL2.GL_LESS);
		section.getTextures().load(gl);
	}

	public void update(){
		if(up){section.getActivescene().sectionDragged(0,-2);}
		if(down){section.getActivescene().sectionDragged(0,2);}
		if(left){section.getActivescene().sectionDragged(-2,0);}
		if(right){section.getActivescene().sectionDragged(2,0);}
	}

	public void listen(GLWindow window, Button3D b){
		window.addGLEventListener(this);
		window.addKeyListener(this);
		window.addMouseListener(this);
		window.addMouseListener(b);
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width,int height) {
		section.height = height;
		section.format = width/(height*1.0);
	}
	public void mouseMoved(MouseEvent arg0){}

	public void mouseReleased(MouseEvent me){
		this.x = 0;
		this.y = 0;
		section.getActivescene().released();
	}

	public void mouseDragged(MouseEvent me) {
		if((this.x == 0)&&(this.y == 0)){
			this.x = me.getX();
			this.y = me.getX();
		}else{
			if((me.getButton()==3)||(shift)){
				section.getActivescene().sectionDragged(me.getX()-this.x,me.getY()-this.y);
			}else{
				section.getActivescene().sceneDragged(me.getX()-this.x,me.getY()-this.y);
			}
		}
		this.x = me.getX();
		this.y = me.getY();
	}

	public void keyPressed(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_F){
			section.switchFullscreen();
		}	
		if(key.getKeyCode() == KeyEvent.VK_S){
			section.setShot(true);
		}	
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE){
			section.exit();
		}	
		if(key.getKeyCode() == KeyEvent.VK_UP){
			up = true;
		}
		if(key.getKeyCode() == KeyEvent.VK_DOWN){
			down = true;
		}
		if(key.getKeyCode() == KeyEvent.VK_SHIFT){
			shift = true;
		}
		if(key.getKeyCode() == KeyEvent.VK_LEFT){
			left = true;
		}
		if(key.getKeyCode() == KeyEvent.VK_RIGHT){
			right = true;
		}
	}

	public void keyReleased(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_UP){
			up = false;
		}
		if(key.getKeyCode() == KeyEvent.VK_DOWN){
			down = false;
		}
		if(key.getKeyCode() == KeyEvent.VK_SHIFT){
			shift = false;
		}
		if(key.getKeyCode() == KeyEvent.VK_LEFT){
			left = false;
		}
		if(key.getKeyCode() == KeyEvent.VK_RIGHT){
			right = false;
		}
	}

	public void display(GLAutoDrawable drawable) {
		section.update();	
		section.render(drawable);
	}

	public void dispose(GLAutoDrawable drawable){
		GL2 gl = drawable.getGL().getGL2();	
		section.getTextures().dispose(gl);
	}

	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseWheelMoved(MouseEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}
}
