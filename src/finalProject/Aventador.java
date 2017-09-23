package finalProject;

import com.jogamp.opengl.GL2;

public class Aventador extends Car{
	public String bodyModelPath; 
	public String bodyTexturePath;
	public String backWheelPath;
	public String backWheeltex;
	public String frontWheelPath;
	public String frontWheeltex;
	public float wheelRotate;
	
	GLModel body = null;
	GLModel BWheel = null;
	GLModel FWheel = null;
	
	
	public Aventador(int type){
		bodyModelPath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Aventador/GreenBlack.obj";
		
		
		backWheelPath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Aventador/BlackBackWheel.obj";
		backWheeltex = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Aventador/BlackBackWheel.mtl";
		frontWheelPath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Aventador/BlackFrontWheel.obj";
		frontWheeltex = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Aventador/BlackFrontWheel.mtl";
		
		if(type == 1){
			bodyTexturePath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Aventador/BlackRedobj.mtl";
		}
		else{
			bodyTexturePath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Aventador/GreenBlack.mtl";
		}
		
		speed = 5;
		handling = 1.8;
		carCenter = new Vertex	(0,2f,0);
		collisionRadius = 10;
		
	}
	
	public void drawBRwheel(GL2 gl, float r){
		
		gl.glPushMatrix();
		gl.glTranslatef(6.5f,1.5f,-6.55f);
		gl.glRotatef(r,0,0,1);
		gl.glTranslatef(-4.2f,-1.6f,1.6f);
		gl.glScalef(4,4,-4);
		
		BWheel.opengldraw(gl);
		
		gl.glPopMatrix();
	}
	
	public void drawBLwheel(GL2 gl, float r){
		
		gl.glPushMatrix();
		gl.glTranslatef(6.5f,1.5f,3.4f);
		gl.glRotatef(r,0,0,1);
		gl.glTranslatef(-4.2f,-1.6f,1.6f);
		gl.glScalef(4,4,4);
		
		BWheel.opengldraw(gl);
		
		gl.glPopMatrix();
		
	}
	
	public void drawFLwheel(GL2 gl, float r, float t){
		
		gl.glPushMatrix();
		gl.glTranslatef(-5.7f,1.46f,3.8f);
		gl.glRotatef(t,0,1,0);
		gl.glRotatef(r,0,0,1);
		gl.glTranslatef(4.27f,1.6f,0);
		gl.glScalef(4,4,4);
		
		FWheel.opengldraw(gl);
		
		gl.glPopMatrix();
		
	}
	
	public void drawFRwheel(GL2 gl, float r, float t){
		
		gl.glPushMatrix();
		gl.glTranslatef(-5.7f,1.46f,-3.8f);
		gl.glRotatef(t,0,1,0);
		gl.glRotatef(r,0,0,1);
		gl.glTranslatef(4.27f,1.6f,0);
		gl.glScalef(4,4,-4);
		
		FWheel.opengldraw(gl);
		
		gl.glPopMatrix();
		
	}
	
	public void drawBody(GL2 gl){
		
		gl.glPushMatrix();
		
		gl.glTranslatef(-0,2.6f,0);
		gl.glScalef(4,4,4);
		
		body.opengldraw(gl);
		
		gl.glPopMatrix();
		
	}
	
	public void drawCar(GL2 gl, float wheelRotate, float wheelTurn){
		
		gl.glPushMatrix();
		gl.glRotatef(90,1,0,0);
		drawBody(gl);
		drawFLwheel(gl, wheelRotate, wheelTurn);
		drawFRwheel(gl, wheelRotate, wheelTurn);
		drawBLwheel(gl, wheelRotate);
		drawBRwheel(gl, wheelRotate);
		
		
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
		body = ModelLoaderOBJ.LoadModel(bodyModelPath, bodyTexturePath, gl);
		FWheel = ModelLoaderOBJ.LoadModel(frontWheelPath, frontWheeltex, gl);
		BWheel = ModelLoaderOBJ.LoadModel(backWheelPath, backWheeltex, gl);
		if (body == null) {
			return false;
		}
		if(FWheel == null){
			return false;
		}
		if(BWheel == null){
			return false;
		}
		
	
		return true;
	}
	
	public void setWheelRotate(float r){
		wheelRotate = r;
	}
	

	

   
  

}
