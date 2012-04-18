package fr.legbt.sections;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class PyramideScene extends Scene{
	private Pyramide pyramide;

	public PyramideScene(){
		super("pyl");
		pyramide= new Pyramide();
		this.h = -4f;
	}

	public void render(GL2 gl) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		int s = 2;
		gl.glOrtho(-s*1.6,s*1.6,-s*0.9,s*0.9,-s-1,s+1);
		this.pyramide.resetRotation();
		this.pyramide.zRotation((float)theta/2);
		this.pyramide.xRotation((float)phi/2);
		this.plan.resetRotation();
		this.plan.setH(h);
		this.plan.zRotation((float)theta/2);
		this.plan.xRotation((float)phi/2);
		this.psection.resetRotation();
		this.psection.setH(h);
		this.psection.zRotation((float)theta/2);
		this.psection.xRotation((float)phi/2);
		this.pyramide.sort();

		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glTranslatef(0.325f,0,0);


		this.pyramide.traceCube(gl);
		this.plan.tracePlan(gl);
		gl.glDisable(GL.GL_DEPTH_TEST);
		if((this.plan.getH()<0.5f)){
			this.psection.traceVertexes(gl);
			this.psection.drawBorders(gl);
		}
		this.h = 0;

		if(firstrotation){
			this.theta = 0;
			this.phi = 0;
			firstrotation = false;
		}
	}

}
