package fr.legbt.sections;

import javax.media.opengl.GL2;


public abstract class  Scene{
	protected Plan plan;
	protected Plan section;
	protected float theta = 10;
	protected float phi = -160;
	protected float h; 
	protected boolean firstrotation;

	public Scene(float xscale, float yscale){
		plan = new Plan();
		section = new Plan(1,xscale,yscale);
		firstrotation = true;
	}


	public void released(){
		this.theta = 0;
		this.phi = 0;
	}

	public void sceneDragged(int xdelta,int ydelta){
		this.theta = xdelta;
		this.phi = ydelta;
	}

	public void sectionDragged(int xdelta,int ydelta){
		this.h = -ydelta/100f;
	}

	public abstract void render(GL2 gl);

}
