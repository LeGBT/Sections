package fr.legbt.sections;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.media.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;


public class Button3D implements MouseListener{
	private float scale = 1.81f;
	private float h = scale*0.222f;
	private float l = 0.125f;
	private float le = scale*l;
	private int size = Math.round(1280*scale/16);
	private int font = GLUT.BITMAP_HELVETICA_18;
	private String[] textboutons = {"Cube","Pave droit","Cylindre","Pyramide","Sphere"};
	private Sections instance;

	public  Button3D(Sections instance){
		this.instance = instance;
	}


	public void drawButton(GL2 gl,GLUT glut, int activeview){
		gl.glLoadIdentity();
		traceMe(gl,glut,1,activeview);
		traceMe(gl,glut,2,activeview);
		traceMe(gl,glut,3,activeview);
		traceMe(gl,glut,4,activeview);
		traceMe(gl,glut,5,activeview);
	}


	private void traceMe(GL2 gl,GLUT glut, int n, int activeview){
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

		gl.glColor4f(0.9f,0.9f,0.9f,0.9f);

		gl.glRasterPos3f(-1+le/4f,1-pos+h/2,0f);
		glut.glutBitmapString(font,textboutons[n-1]);

	}


	public void mouseClicked(MouseEvent me){
		int button = 0;
		if(me.getX()<size-3){
			button = me.getY()*5/710+1;
			this.instance.setActiveview(button);
		}

	}


	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0){}
	public void mousePressed(MouseEvent arg0){}
	public void mouseReleased(MouseEvent arg0){}
}
