package fr.legbt.sections;

public class Vect3{
	private float x;
	private float y;
	private float z;
	public Vect3(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vect3 rotation(Vect3 axe, float degree){
		axe = axe.normalise();
		double pradian = degree*Math.PI/180d;
		float radian = (float) pradian;
		double ps = Math.sin(radian);
		float s = (float) ps;
		double pc = Math.cos(radian);
		float c = (float) pc;
		Vect3 temp;
		temp = this.multScalaire(c);
		float scal = axe.produitScalaire(this);
		temp = temp.add(this.multScalaire((1-c)*scal));
		temp = temp.add(this.produitVectoriel(axe).multScalaire(s));
		//			FIXME debug
			System.out.println(temp.norme2());
		return temp;
	}

	public Vect3 produitVectoriel(Vect3 u){
		return new Vect3(
				this.getY()*u.getZ() - this.getZ()*u.getY(),
				this.getZ()*u.getX() - this.getX()*u.getZ(),
				this.getX()*u.getY() - this.getY()*u.getX()
				);
	}

	public Vect3 add(Vect3 u){
		return new Vect3(this.getX()+u.getX(),this.getY()+u.getY(),this.getZ()+u.getZ());

	}
	public Vect3 soustr(Vect3 u){
		return this.add(u.multScalaire(-1));
	}


	public float produitScalaire(Vect3 u){
		return scalaire(this,u);
	}

	public Vect3 multScalaire(float lambda){
		return new Vect3(this.getX()*lambda,this.getY()*lambda,this.getZ()*lambda);
	}

	static public float scalaire(Vect3 u, Vect3 v){
		return u.getX()*v.getX() + u.getY()*v.getY() + u.getZ()*v.getZ();
	}

	public Vect3 normalise(){
		double  pnorm =  1f/Math.sqrt(this.getX()*this.getX()+this.getY()*this.getY()+this.getZ()*this.getZ());
		float norm = (float) pnorm;
		return new Vect3(this.getX()/norm,this.getY()/norm,this.getZ()/norm);
	}

	public float norme2(){
		return this.getX()*this.getX()+this.getY()*this.getY()+this.getZ()*this.getZ();
	}

	public float getX(){return this.x;}
	public void setX(int x){this.x = x;}
	public float getY(){return this.y;}
	public void setY(int y){this.y = y;}
	public float getZ(){return this.z;}
	public void setZ(int z){this.z = z;}
}
