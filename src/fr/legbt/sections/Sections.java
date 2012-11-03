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

import java.awt.image.BufferedImage;
import java.io.File;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.imageio.ImageIO;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.Screenshot;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;

public class Sections{ 
	private int activeview = 1;
	public  int height = 720; 
	public  double format = 16/9.0; 
	private boolean plantype;
	private boolean bonemode = false;
	private boolean shot = false;
	private boolean fullscreen = false;
	private TextureLib textures;
	private Button3D b;
	private CubeScene cs;
	private PaveScene ps;
	private CylinderScene cys;
	private PyramideScene pys;
	private SphereScene sps;
	private HelpScene hlp;
	private Scene activescene;
	private GLProfile prof;
	private GLCapabilities caps;
	private GLWindow window;
	public FPSAnimator animator;
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

		window = GLWindow.create(caps);	
		animator = new FPSAnimator(window,60);
		plantype = true;
		cs = new CubeScene(this);
		ps = new PaveScene(this);
		cys = new CylinderScene(this);
		pys = new PyramideScene(this);
		sps = new SphereScene(this);
		hlp = new HelpScene(this);
		activescene = cs;
		listener = new ActionListener(this);

		b = new Button3D(this);
		listener.listen(window,b);
		textures = new TextureLib();
	}

	public void reset(){
		cs.reset();
		ps.reset();
		cys.reset();
	}

	public static void main(String[] args){
		final Sections sect = new Sections();
		sect.window.addWindowListener(new WindowAdapter(){
			public void windowDestroyNotify(WindowEvent e){
				new Thread(){
					public void run(){
						sect.exit();
					}
				}.start();
			};
		});

		sect.window.setSize((int)(sect.height*sect.format),sect.height);
		sect.window.setVisible(true);
		sect.window.setTitle("Sections");
		sect.animator.start();
		sect.window.requestFocus();
	}

	public void update(){
		listener.update();
	}


	public void render(GLAutoDrawable drawable){
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL2.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		double s = 1.8d;
		gl.glOrtho(-s*format,s*format,-s,s,-3,3);

		this.activescene.render(gl);
		if(shot){
			BufferedImage tScreenshot = Screenshot.readToBufferedImage(150,0, (int)(height*format*0.8468), (int)(height*0.9722), false); 
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
		render(drawable);
		update();	
	}

	public  void exit(){
		this.animator.stop();
		this.window.destroy();
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
		}else if(activeview == 5){this.activescene = sps;
		}else if(activeview == 6){this.activescene = hlp;}
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
	
	public boolean isFullscreen(){
		return fullscreen;
	}

	public void switchFullscreen(){
		fullscreen = !fullscreen;
		this.window.setFullscreen(fullscreen);
	}

	public Scene getActivescene(){return this.activescene;}
	public GLWindow getWindow(){return this.window;}
	public void changePlanType(){
		this.plantype = !this.plantype;
	}
}
