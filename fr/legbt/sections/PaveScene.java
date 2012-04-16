package fr.legbt.sections;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class PaveScene extends Scene{
	private Pave pave;

	public PaveScene(){
		super(1.5f,0.7f);
		pave = new Pave();
	}

	public void render(GL2 gl) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		int s = 2;
		gl.glOrtho(-s*1.6,s*1.6,-s*0.9,s*0.9,-s-1,s+1);
		this.pave.resetRotation();
		this.pave.zRotation((float)theta/2);
		this.pave.xRotation((float)phi/2);
		this.plan.resetRotation();
		this.plan.setH(h);
		this.plan.zRotation((float)theta/2);
		this.plan.xRotation((float)phi/2);
		this.section.resetRotation();
		this.section.setH(h);
		this.section.zRotation((float)theta/2);
		this.section.xRotation((float)phi/2);
		this.pave.sort();

		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glTranslatef(0.325f,0,0);


		this.pave.traceCube(gl);
		this.plan.tracePlan(gl);
		gl.glDisable(GL.GL_DEPTH_TEST);
		if((this.section.getH()<1)&&(this.section.getH()>-1)){
			this.section.tracePlan(gl);
		}
		this.h = 0;

		if(firstrotation){
			this.theta = 0;
			this.phi = 0;
			firstrotation = false;
		}
	}

}
