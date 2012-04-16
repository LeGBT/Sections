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
import javax.media.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

public class Sections implements GLEventListener, KeyListener, MouseListener, MouseMotionListener{
	private double theta = 10;
	private double phi = -160;
	private float h = 1.9f;
	private boolean firstrotation = true;
	private int x = 0;
	private int y = 0;
	private int activeview = 1;
	private Cube cube;
	private Plan plan;
	private Plan section;
	private Button3D b;
	static GLU glu = new GLU();
	//static GLCanvas canvas = new GLCanvas();
	static GLCanvas canvas = new GLCanvas();
	static Frame frame = new Frame("Sections");
	static FPSAnimator animator = new FPSAnimator(canvas,60);
	static GLUT glut = new GLUT();


	public static void main(String[] args){
		Sections sect = new Sections();
		sect.cube = new Cube();
		sect.plan = new Plan();
		sect.section = new Plan(1);
		sect.b = new Button3D(sect);
		canvas.addGLEventListener(sect);
		canvas.addMouseListener(sect);
		canvas.addMouseListener(sect.b);
		canvas.addMouseMotionListener(sect);
		frame.add(canvas);
		frame.setSize(1240,720);
		frame.setLocation(100,100);
		frame.setResizable(false);
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
		this.cube.resetRotation();
		this.cube.zRotation((float)theta/2);
		this.cube.xRotation((float)phi/2);
		this.plan.resetRotation();
		this.plan.setH(h);
		this.plan.zRotation((float)theta/2);
		this.plan.xRotation((float)phi/2);
		this.section.resetRotation();
		this.section.setH(h);
		this.section.zRotation((float)theta/2);
		this.section.xRotation((float)phi/2);
		this.cube.sort();

		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glTranslatef(0.325f,0,0);


		this.cube.traceCube(gl);
		this.plan.tracePlan(gl);
		gl.glDisable(GL.GL_DEPTH_TEST);
		if((this.section.getH()<1)&&(this.section.getH()>-1)){
			this.section.tracePlan(gl);
		}

		b.drawButton(gl,glut,this.activeview);

		this.h = 0;
	}


	public void display(GLAutoDrawable drawable) {
		update();	
		render(drawable);
		if(firstrotation){
			this.theta = 0;
			this.phi = 0;
			firstrotation = false;
		}
	}

	public static void exit(){
		animator.stop();
		frame.dispose();
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
		this.theta = 0;
		this.phi = 0;
	}

	public void mouseDragged(MouseEvent me) {
		if((this.x == 0)&&(this.y == 0)){
			this.x = me.getX();
			this.y = me.getX();
		}else{
			if(me.getButton()==3){
				this.h = -(me.getY() - this.y)/100f;
			}else{
				this.theta = me.getX() - this.x;	
				this.phi = me.getY() - this.y;	
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
	 * Gets the h for this instance.
	 *
	 * @return The h.
	 */
	public float getH()
	{
		return this.h;
	}

	/**
	 * Sets the h for this instance.
	 *
	 * @param h The h.
	 */
	public void setH(float h)
	{
		this.h = h;
	}

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
	}
}
