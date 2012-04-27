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

import java.awt.Frame;
import java.awt.Font;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL2;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.TextRenderer;

public class Sections implements GLEventListener, KeyListener, MouseListener, MouseMotionListener{
	private int x = 0;
	private int y = 0;
	private int activeview = 1;
	private boolean plantype;
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private boolean shift;
	private TextRenderer renderer;
	private TextRenderer rendererbis;
	private Button3D b;
	private CubeScene cs;
	private PaveScene ps;
	private CylinderScene cys;
	private PyramideScene pys;
	private SphereScene sps;
	private Scene activescene;
	private GLProfile prof;
	private GLCapabilities caps;
	private GLCanvas canvas;
	private Frame frame;
	private FPSAnimator animator;


	public Sections(){
		prof = GLProfile.get(GLProfile.GL2);
		caps = new GLCapabilities(prof);
		caps.setDoubleBuffered(true);
		caps.setHardwareAccelerated(true);
		caps.setAccumBlueBits(8);
		caps.setAccumGreenBits(8);
		caps.setAccumRedBits(8);
		caps.setAccumAlphaBits(8);
		caps.setStencilBits(8);
		caps.setDepthBits(16);
		caps.setSampleBuffers(true);
		caps.setNumSamples(8);

		canvas = new GLCanvas(caps);
		frame = new Frame("Sections");
		animator = new FPSAnimator(canvas,60);
		plantype = true;
		up = false;
		down = false;
		left = false;
		right = false;
		cs = new CubeScene(this);
		ps = new PaveScene(this);
		cys = new CylinderScene(this);
		pys = new PyramideScene();
		sps = new SphereScene();
		b = new Button3D(this);
		activescene = cs;
		canvas.addGLEventListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseListener(b);
		canvas.addMouseMotionListener(this);
		renderer = new TextRenderer(new Font("Times",Font.BOLD,24),true,true);
		rendererbis = new TextRenderer(new Font("Times",Font.PLAIN,50),true,true);
	}

	public void reset(){
		cs.reset();
		ps.reset();
		cys.reset();
	}

	public static void main(String[] args){
		final Sections sect = new Sections();
		sect.frame.add(sect.canvas);
		sect.frame.setSize(1240,720);
		sect.frame.setLocation(100,100);
		sect.frame.setResizable(false);
		sect.frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				sect.exit();	
			}
		});
		sect.frame.setVisible(true);
		sect.animator.start();
		sect.canvas.requestFocus();
	}

	public void update(){
		if(up){activescene.sectionDragged(0,-2);}
		if(down){activescene.sectionDragged(0,2);}
		if(left){activescene.sectionDragged(-2,0);}
		if(right){activescene.sectionDragged(2,0);}
	}


	public void render(GLAutoDrawable drawable){
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		int s = 2;
		gl.glOrtho(-s*1.6,s*1.6,-s*0.9,s*0.9,-3,2);

		this.activescene.render(gl);
		b.drawButton(gl,this.renderer,this.rendererbis,this.activeview);
	}


	public void display(GLAutoDrawable drawable) {
		update();	
		render(drawable);
	}

	public  void exit(){
		this.animator.stop();
		this.frame.dispose();
		System.exit(0);
	}	

	public void dispose(GLAutoDrawable arg0) {}

	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(0.0f,0.0f,0.0f,0.0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL2.GL_FOG);
		gl.glEnable(GL.GL_BLEND);
		//      gl.glEnable(GL.GL_MULTISAMPLE);
		//		gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);
		//		gl.glHint(GL2GL3.GL_POLYGON_SMOOTH_HINT, GL.GL_NICEST);
		//		gl.glEnable(GL2GL3.GL_POLYGON_SMOOTH);
		//		gl.glEnable(GL.GL_LINE_SMOOTH);
		gl.glLineWidth(2f);
		gl.glBlendFunc(GL.GL_SRC_ALPHA,GL.GL_ONE_MINUS_SRC_ALPHA);
		//		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glDepthFunc(GL.GL_LESS);
		//		gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT,GL.GL_NICEST);
		((Component) drawable).addKeyListener(this);
	}


	public void keyPressed(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE){
			exit();
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

	public void reshape(GLAutoDrawable drawable, int x, int y, int width,int height) {}
	public void keyTyped(KeyEvent k){}
	public void mouseMoved(MouseEvent arg0){}

	public void mouseReleased(MouseEvent me){
		this.x = 0;
		this.y = 0;
		activescene.released();
	}

	public void mouseDragged(MouseEvent me) {
		if((this.x == 0)&&(this.y == 0)){
			this.x = me.getX();
			this.y = me.getX();
		}else{
			if((me.getButton()==3)||(shift)){
				activescene.sectionDragged(me.getX()-this.x,me.getY()-this.y);
			}else{
				activescene.sceneDragged(me.getX()-this.x,me.getY()-this.y);
			}
		}
		this.x = me.getX();
		this.y = me.getY();
	}

	public void mouseClicked(MouseEvent me) {}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}


	/**
	 * Gets the activeview for this instance.
	 *
	 * @return The activeview.
	 */
	public int getActiveview()
	{
		return this.activeview;
	}

	/**
	 * Sets the activeview for this instance.
	 *
	 * @param activeview The activeview.
	 */
	public void setActiveview(int activeview)
	{
		this.activeview = activeview;
		if (activeview == 1){this.activescene = cs;
		}else if(activeview == 2){this.activescene = ps;
		}else if(activeview == 3){this.activescene = cys;
		}else if(activeview == 4){this.activescene = pys;
		}else if(activeview == 5){this.activescene = sps;}
	}


	/**
	 * Determines if this instance is plantype.
	 *
	 * @return The plantype.
	 */
	public boolean isPlantype()
	{
		return this.plantype;
	}


	/**
	 * Sets whether or not this instance is plantype.
	 *
	 * @param plantype The plantype.
	 */
	public void changePlanType()
	{
		this.plantype = !this.plantype;
	}
}
