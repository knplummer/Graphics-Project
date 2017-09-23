package finalProject;

import com.jogamp.opengl.GL2;

public class Mercedes extends Car{
	public String bodyModelPath; 
	public String bodyTexturePath;
	public String wheelModelPath;
	public String wheelTexPath;
	public float wheelRotate;
	
	GLModel bodyModel = null;
	GLModel wheelModel = null;

	
	
	public Mercedes(){
		bodyModelPath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Mercedes/MercedesBody.obj";
		bodyTexturePath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Mercedes/MercedesBody.mtl";
		wheelModelPath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Mercedes/MercedesWheel.obj";
		wheelTexPath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Mercedes/MercedesWheel.mtl";
		
		speed = 3.5;
		handling = 2.9;
		carCenter = new Vertex	(0,2f,0);
		collisionRadius = 10;
		
	}
	
	public void drawBody(GL2 gl){
		gl.glPushMatrix();
		gl.glTranslatef(0,3.3f,0);
		gl.glScalef(5,5,5);
		gl.glRotatef(90,0,-1,0);
		//gl.glRotatef(90,-1,0,0);
		
		bodyModel.opengldraw(gl);
		
		gl.glPopMatrix();
		
	}
	
	public void drawLFwheel(GL2 gl, float r, float t){
		gl.glPushMatrix();
		gl.glTranslatef(-7.25f,1.6f,4.3f);
		gl.glRotatef(t, 0,1,0);
		gl.glRotatef(r, 0, 0, 1);
		gl.glScalef(5,5,5);
		gl.glRotatef(90,0,-1,0);
		//gl.glRotatef(90,-1,0,0);
		
		wheelModel.opengldraw(gl);
		
		gl.glPopMatrix();
		
	}
	
	public void drawFRwheel(GL2 gl, float r, float t){
		gl.glPushMatrix();
		gl.glTranslatef(-7.25f,1.6f,-4.3f);
		gl.glRotatef(t, 0,1,0);
		gl.glRotatef(r, 0, 0, 1);
		gl.glScalef(5,5,-5);
		gl.glRotatef(90,0,-1,0);
		//gl.glRotatef(90,-1,0,0);
		
		wheelModel.opengldraw(gl);
		
		gl.glPopMatrix();
		
	}
	
	public void drawBRwheel(GL2 gl, float r){
		gl.glPushMatrix();
		gl.glTranslatef(6.75f,1.6f,-4.3f);
		gl.glRotatef(r, 0, 0, 1);
		gl.glScalef(5,5,-5);
		gl.glRotatef(90,0,-1,0);
		//gl.glRotatef(90,-1,0,0);
		
		wheelModel.opengldraw(gl);
		
		gl.glPopMatrix();
		
	}
	
	public void drawBLwheel(GL2 gl, float r){
		gl.glPushMatrix();
		gl.glTranslatef(6.75f,1.6f,4.3f);
		gl.glRotatef(r, 0, 0, 1);
		gl.glScalef(5,5,5);
		gl.glRotatef(90,0,-1,0);
		//gl.glRotatef(90,-1,0,0);
		
		wheelModel.opengldraw(gl);
		
		gl.glPopMatrix();
		
	}
	
	public void drawCar(GL2 gl, float r, float t){
		gl.glPushMatrix();
		gl.glRotatef(90,1,0,0);
		drawBLwheel(gl,r);
		drawBRwheel(gl,r);
		drawLFwheel(gl,r,t);
		drawFRwheel(gl,r,t);
		drawBody(gl);
		gl.glPopMatrix();
		
	}
	
	
	void drawCollision(GL2 gl){
		double y = this.carCenter.getY();
		
		gl.glPushMatrix();
		gl.glRotatef(90, 1, 0, 0);
		gl.glLineWidth(3);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBegin(GL2.GL_LINE_LOOP);
		gl.glColor3f(1,0,0);
		for (int i = 0; i < 360; i += 360/30){
			double vertex = i * Math.PI/180;
			double x = Math.cos(vertex)*this.collisionRadius + this.carCenter.getX();
			double z = Math.sin(vertex)*this.collisionRadius + this.carCenter.getZ();
			gl.glVertex3d(x,z,-y);
		}
		gl.glEnd();
		gl.glFlush();
		gl.glPopMatrix();
		
	}
	public boolean loadModels(GL2 gl) {
		bodyModel = ModelLoaderOBJ.LoadModel(bodyModelPath, bodyTexturePath, gl);
		wheelModel = ModelLoaderOBJ.LoadModel(wheelModelPath, wheelTexPath, gl);
		if (bodyModel == null) {
			return false;
		}
		if (wheelModel == null) {
			return false;
		}
	
		return true;
	}
	
	public void setWheelRotate(float r){
		wheelRotate = r;
	}
}
