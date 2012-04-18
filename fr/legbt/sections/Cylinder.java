package fr.legbt.sections;

import java.util.ArrayList;
import java.util.Collections;

import javax.media.opengl.GL2;
import javax.vecmath.Vector3f;

public class Cylinder {
	private Disc top;
	private Disc bottom;
	private CylinderPiece cypiece1;
	//private CylinderPiece cypiece2;
	private ArrayList<Piece> pieces;


	public Cylinder(){
		Vector3f u1 = new Vector3f(1,0,0);
		Vector3f u2 = new Vector3f(0,1,0);
		Vector3f u3 = new Vector3f(0,0,1);
		Vector3f u4 = new Vector3f(-1,0,0);
		Vector3f u5 = new Vector3f(0,-1,0);
		Vector3f u6 = new Vector3f(0,0,-1);
		pieces = new ArrayList<Piece>();
		top = new Disc(u1,u2,u3);
		bottom = new Disc(u4,u5,u6);
		cypiece1 = new CylinderPiece(u1,u2,u3);
		//cypiece2 = new CylinderPiece(u4,u5,u3);
		pieces.add(top);
		pieces.add(bottom);
		pieces.add(cypiece1);
	//	pieces.add(cypiece2);
		Collections.sort(pieces);
	}

	public void resetRotation(){
		for(int i=0;i<pieces.size();i++){
			pieces.get(i).resetRotation();
		}
	}


	public void xRotation(float degree){
		for(int i=0;i<pieces.size();i++){
			pieces.get(i).xRotation(degree);
		}
	}

	public void yRotation(float degree){
		for(int i=0;i<pieces.size();i++){
			pieces.get(i).yRotation(degree);
		}
	}

	public void zRotation(float degree){
		for(int i=0;i<pieces.size();i++){
			pieces.get(i).zRotation(degree);
		}
	}

	public void sort(){
		Collections.sort(pieces);
	}

	public void traceCube(GL2 gl){
		for(int i=0;i<pieces.size();i++){
			pieces.get(i).traceVertexes(gl);
			if(pieces.get(i) instanceof Disc){
				Disc d = (Disc) pieces.get(i);
				d.drawBorders(gl);
			}
		}
	}

}
