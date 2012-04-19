package fr.legbt.sections;

import java.awt.Frame;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL2;
import javax.media.opengl.GL2GL3;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

public class Sections implements GLEventListener, KeyListener, MouseListener, MouseMotionListener{
	private int x = 0;
	private int y = 0;
	private int activeview = 5;
	private Button3D b;
	private CubeScene cs;
	private PaveScene ps;
	private CylinderScene cys;
	private PyramideScene pys;
	private SphereScene sps;
	private Scene activescene;
	private GLCanvas canvas;
	private Frame frame;
	private FPSAnimator animator;
	private GLUT glut;


	public Sections(){
		canvas = new GLCanvas();
		frame = new Frame("Sections");
		animator = new FPSAnimator(canvas,60);
		glut = new GLUT();
		cs = new CubeScene();
		ps = new PaveScene();
		cys = new CylinderScene();
		pys = new PyramideScene();
		sps = new SphereScene();
		b = new Button3D(this);
		activescene = sps;
		canvas.addGLEventListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseListener(b);
		canvas.addMouseMotionListener(this);
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

	public void update(){}


	public void render(GLAutoDrawable drawable){
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		int s = 2;
		gl.glOrtho(-s*1.6,s*1.6,-s*0.9,s*0.9,-s-1,s+1);

		this.activescene.render(gl);

		b.drawButton(gl,glut,this.activeview);
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
		//	gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glEnable(GL2ES1.GL_FOG);
		gl.glEnable(GL.GL_BLEND);
		gl.glEnable(GL.GL_LINE_SMOOTH);
		gl.glEnable(GL2GL3.GL_POLYGON_SMOOTH);
		gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);
		gl.glBlendFunc(GL.GL_SRC_ALPHA,GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT,GL.GL_NICEST);
		gl.glHint(GL2GL3.GL_POLYGON_SMOOTH_HINT, GL.GL_NICEST);
		((Component) drawable).addKeyListener(this);
	}


	public void keyPressed(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_ESCAPE){
			exit();
		}	
	}
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,int height) {}
	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0){}
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
			if(me.getButton()==3){
				activescene.sectionDragged(me.getX()-this.x,me.getY()-this.y);
			}else{
				activescene.sceneDragged(me.getX()-this.x,me.getY()-this.y);
			}
		}
		this.x = me.getX();
		this.y = me.getY();
	}

	public void mouseClicked(MouseEvent e) {}
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
}
