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
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GL2GL3;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

public class Sections implements GLEventListener, KeyListener, MouseListener, MouseMotionListener{
	private double theta = 0;
	private double phi = 0;
	private float alpha = 0.6f;
	private int x = 0;
	private int y = 0;
	private Cube cube;
	private Plan plan;
	static GLU glu = new GLU();
	static GLCanvas canvas = new GLCanvas();
	static Frame frame = new Frame("test");
	static FPSAnimator animator = new FPSAnimator(canvas,60);


	public static void main(String[] args){
		Sections sect = new Sections();
		sect.cube = new Cube();
		sect.plan = new Plan();
		canvas.addGLEventListener(sect);
		canvas.addMouseListener(sect);
		canvas.addMouseMotionListener(sect);
		frame.add(canvas);
		frame.setSize(1240,720);
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				exit();	
			}
		});
		frame.setVisible(true);
		animator.start();
		canvas.requestFocus();
	}

	public void update(){
		//	theta +=1;
	}



	public void render(GLAutoDrawable drawable){
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		int s = 2;
		gl.glOrtho(-s*1.6,s*1.6,-s*0.9,s*0.9,-s-1,s+1);
		//gl.glRotatef((float)theta/2,0,1,0);
		//gl.glRotatef((float)phi/2,0,0,1);
		this.cube.yRotation((float)theta/2);
		this.cube.xRotation((float)phi/2);
	    this.cube.sort();
		this.plan.tracePlan(gl);
		this.cube.traceCube(gl);
	}

	public void drawUnitQuad(GL2 gl,float r, float v, float b){
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor4f(r,v,b,alpha);
		gl.glVertex3f(0,0,0f);
		gl.glVertex3f(1,0,0);

		//gl.glColor3f(r+v,v+b,b+r);

		gl.glVertex3f(1,1,0f);
		gl.glVertex3f(0,1,0);
		gl.glEnd();
	}


	public void display(GLAutoDrawable drawable) {
		update();	
		render(drawable);
	}

	public static void exit(){
		animator.stop();
		frame.dispose();
		System.exit(0);
	}	

	public void dispose(GLAutoDrawable arg0) {}

	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		//gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
		gl.glClearColor(0.0f,0.0f,0.0f,0.0f);
		gl.glClearDepth(1.0f);
	//	gl.glEnable(GL.GL_DEPTH_TEST);
		//gl.glDepthMask(false);
		//gl.glEnable(GL.GL_SAMPLE_ALPHA_TO_COVERAGE);
		//	gl.glEnable(GL.GL_CULL_FACE);
		gl.glEnable(GL2ES1.GL_FOG);
		gl.glEnable(GL.GL_BLEND);
		gl.glEnable(GL.GL_LINE_SMOOTH);
		gl.glHint(GL.GL_LINE_SMOOTH_HINT, GL.GL_NICEST);
		//gl.glBlendFunc(gl.GL_SRC_ALPHA_SATURATE,gl.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBlendFunc(gl.GL_SRC_ALPHA,gl.GL_ONE_MINUS_SRC_ALPHA);
		//gl.glAlphaFunc(GL.GL_GREATER,0.1f);
		gl.glDepthFunc(GL.GL_LEQUAL);
	//	gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
	//	gl.glHint(GL2GL3.GL_POLYGON_SMOOTH_HINT, GL.GL_NICEST);
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
		this.theta = 0;
		this.phi = 0;
	}

	public void mouseDragged(MouseEvent me) {
		if((this.x == 0)&&(this.y == 0)){
			this.x = me.getX();
			this.y = me.getX();
		}else{
			this.theta = me.getX() - this.x;	
			this.phi = me.getY() - this.y;	
		}
		this.x = me.getX();
		this.y = me.getY();
	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
}
