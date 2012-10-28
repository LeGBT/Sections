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

import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.imageio.ImageIO;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.Screenshot;

public class Sections{ 
	private int activeview = 1;
	private boolean plantype;
	private boolean bonemode = false;
	private boolean shot = false;
	private TextureLib textures;
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
	private ActionListener listener;

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
		cs = new CubeScene(this);
		ps = new PaveScene(this);
		cys = new CylinderScene(this);
		pys = new PyramideScene(this);
		sps = new SphereScene(this);
		b = new Button3D(this);
		activescene = cs;
		listener = new ActionListener(this);

		listener.listen(canvas,b);
		textures = new TextureLib();
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
       listener.update();
	}


	public void render(GLAutoDrawable drawable){
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		int s = 2;
		gl.glOrtho(-s*1.6,s*1.6,-s*0.9,s*0.9,-3,3);

		this.activescene.render(gl);
		if(shot){
			BufferedImage tScreenshot = Screenshot.readToBufferedImage(150,0, 1050, 700, false); 
			File tScreenCaptureImageFile = new File("shot.png"); 
			try{
				ImageIO.write(tScreenshot, "png", tScreenCaptureImageFile); 
			}catch(Exception e){
				System.out.println(e);
			}
			shot = false;
		}
		b.drawButton(gl,this.activeview);
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

	public int getActiveview(){
		return this.activeview;
	}

	public void setActiveview(int activeview){
		this.activeview = activeview;
		if (activeview == 1){this.activescene = cs;
		}else if(activeview == 2){this.activescene = ps;
		}else if(activeview == 3){this.activescene = cys;
		}else if(activeview == 4){this.activescene = pys;
		}else if(activeview == 5){this.activescene = sps;}
	}

	public boolean isPlantype(){
		return this.plantype;
	}

	public boolean isBonemode(){
		return this.bonemode;
	}

	public void switchBonemode(){
		bonemode =  !bonemode;
	}

	public boolean isShot(){return this.shot;}
	public void setShot(boolean shot){this.shot = shot;}

	public TextureLib getTextures(){
		return this.textures;
	}

	public Scene getActivescene(){return this.activescene;}

	public void changePlanType(){
		this.plantype = !this.plantype;
	}
}
