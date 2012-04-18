package fr.legbt.sections;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class CylinderScene extends Scene{
	private Cylinder cylinder;


	public CylinderScene(){
		super("cyl");
		cylinder = new Cylinder();
		this.h = -4.2f;
	}

	public void render(GL2 gl) {
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		int s = 2;
		gl.glOrtho(-s*1.6,s*1.6,-s*0.9,s*0.9,-s-1,s+1);
		this.cylinder.resetRotation();
		this.cylinder.zRotation((float)theta/2);
		this.cylinder.xRotation((float)phi/2);
		this.plan.resetRotation();
		this.plan.setH(h);
		this.plan.zRotation((float)theta/2);
		this.plan.xRotation((float)phi/2);
		this.dsection.resetRotation();
		this.dsection.setH(h);
		this.dsection.zRotation((float)theta/2);
		this.dsection.xRotation((float)phi/2);
		this.cylinder.sort();

		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glTranslatef(0.325f,0,0);


		this.cylinder.traceCube(gl);
		this.plan.tracePlan(gl);
		gl.glDisable(GL.GL_DEPTH_TEST);
		if((this.dsection.getH()<1)&&(this.dsection.getH()>-1)){
			this.dsection.traceVertexes(gl);
			this.dsection.drawBorders(gl);
		}
		this.h = 0;

		if(firstrotation){
			this.theta = 0;
			this.phi = 0;
			firstrotation = false;
		}
	}

}
