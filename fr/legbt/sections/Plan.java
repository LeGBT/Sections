package fr.legbt.sections;

import javax.media.opengl.GL2;
import javax.vecmath.Vector3f;

public class Plan extends Quad{
	private static float h = 0.4f;
	private boolean border;
	private final static int SECTION_PLAN = 1;
	private final static int SECTION_PAVE = 2;

	public Plan(){
		super(2,x,y,new Vector3f(0,0,h/2));
		border = false;
	}

	public Plan(float s, float xscale, float yscale){
		super(s,xscale,yscale,new Vector3f(0,0,h/s));
		border = true;
	}

	public void setH(float ph){
	//	if(ph!=0){
			this.np.scaleAdd(ph,n,this.np);
	//	}
	}

	public void tracePlan(GL2 gl){
		gl.glBegin(GL2.GL_QUADS);

		if(border){
			gl.glColor4f(0.3f,0.2f,0.4f,0.6f);
		}else{
			gl.glColor4f(0f,0.2f,0.5f,0.8f);
		}
		drawTopLeft(gl);
		drawBottomLeft(gl);
		drawBottomRight(gl);
		drawTopRight(gl);
		gl.glEnd();
		if(border){
			gl.glColor4f(0.9f,0.99f,0.9f,0.9f);
			drawBorders(gl);
		}
	}
}
