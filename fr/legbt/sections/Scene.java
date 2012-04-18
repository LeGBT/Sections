package fr.legbt.sections;

import javax.media.opengl.GL2;
import javax.vecmath.Vector3f;


public abstract class  Scene{
	protected Plan plan;
	protected Plan section;
	protected Disc dsection;
	protected Thales psection;
	protected float theta = 10;
	protected float phi = -160;
	protected float h; 
	protected boolean firstrotation;

	public Scene(float xscale, float yscale){
		plan = new Plan();
		section = new Plan(1,xscale,yscale);
		firstrotation = true;
	}

	public Scene(String type){
		plan = new Plan();
		if(type.equals("cyl")){
			dsection = new Disc(new Vector3f(1,0,0),new Vector3f(0,1,0),new Vector3f(0,0,0.4f));
			dsection.setBorder(true);
		}else if(type.equals("pyl")){
			psection = new Thales(new Vector3f(0.2f,0.1f,0.4f),new Vector3f(1.5f,0,0),new Vector3f(0,1,0),new Vector3f(0.7f,0.35f,0.4f));
		}
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
