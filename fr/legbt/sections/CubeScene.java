package fr.legbt.sections;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class CubeScene extends Scene{
	private Cube cube;

	public CubeScene(){
		super(1,1);
		cube = new Cube();
	}


	public void render(GL2 gl){
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		int s = 2;
		gl.glOrtho(-s*1.6,s*1.6,-s*0.9,s*0.9,-s-1,s+1);
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
		this.h = 0;

		if(firstrotation){
			this.theta = 0;
			this.phi = 0;
			firstrotation = false;
		}
	}
}
