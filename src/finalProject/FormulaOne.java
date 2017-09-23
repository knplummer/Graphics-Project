package finalProject;

import com.jogamp.opengl.GL2;

public class FormulaOne extends Car{
	
	public String bodyModelPath; 
	public String bodyTexPath;
	public String wheelModelPath;
	public String wheelTexPath;
	
	
	public GLModel bodyModel = null;
	public GLModel wheelModel = null;
	
	public FormulaOne(){
		bodyModelPath = "C:/Users/Kalen Plummer/workspace/Final Project/src/FormulaOne/BBnormals.obj";
		bodyTexPath = "C:/Users/Kalen Plummer/workspace/Final Project/src/FormulaOne/BBnormals.mtl";
		wheelModelPath = "C:/Users/Kalen Plummer/workspace/Final Project/src/FormulaOne/wheelNormals.obj";
		wheelTexPath = "C:/Users/Kalen Plummer/workspace/Final Project/src/FormulaOne/wheelNormals.mtl";
		speed = 4;
		handling = 2;
		carCenter = new Vertex	(0,1.3f,0);
		collisionRadius = 10;
		
	}
	
	public void drawBody(GL2 gl){
		
		gl.glPushMatrix();
		gl.glTranslatef(0, 4.7f,0);
		gl.glScalef(2,2,2);
		gl.glRotatef(90, 0, 1, 0);
		bodyModel.opengldraw(gl);
		gl.glPopMatrix();
		
	}
	
	public void drawFLwheel(GL2 gl, float r, float t){
		
		gl.glPushMatrix();
		gl.glTranslatef(-10,2.5f,6);
		gl.glRotatef(t,0,1,0);
		gl.glRotatef(r,0,0,1);
		gl.glRotatef(90, 0, 1, 0);
		gl.glScalef(-2,2,2);
		wheelModel.opengldraw(gl);
		gl.glPopMatrix();
		
	}
	
	public void drawFRwheel(GL2 gl, float r, float t){
		
		gl.glPushMatrix();
		gl.glTranslatef(-10,2.5f,-6);
		gl.glRotatef(t,0,1,0);
		gl.glRotatef(r,0,0,1);
		gl.glRotatef(90, 0, 1, 0);
		gl.glScalef(2,2,2);
		wheelModel.opengldraw(gl);
		gl.glPopMatrix();
		
	}
	
	public void drawBRwheel(GL2 gl, float r){
		
		gl.glPushMatrix();
		gl.glTranslatef(10,2.5f,-6);
		gl.glRotatef(r,0,0,1);
		gl.glRotatef(90, 0, 1, 0);
		gl.glScalef(2,2,2.1f);
		wheelModel.opengldraw(gl);
		gl.glPopMatrix();
		
	}
	
	public void drawBLwheel(GL2 gl, float r){
		
		gl.glPushMatrix();
		gl.glTranslatef(14,2.5f,6);
		gl.glRotatef(r,0,0,1);
		gl.glRotatef(90, 0, 1, 0);
		gl.glScalef(-2,2,2.1f);
		wheelModel.opengldraw(gl);
		gl.glPopMatrix();
		
	}
	
	public void drawCar(GL2 gl, float r, float t){
		gl.glPushMatrix();
		gl.glRotatef(90,1,0,0);
		drawBody(gl);
		drawFRwheel(gl,r, t);
		drawFLwheel(gl,r,t);
		drawBLwheel(gl,r);
		drawBRwheel(gl,r);
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
		bodyModel = ModelLoaderOBJ.LoadModel(bodyModelPath, bodyTexPath, gl);
		wheelModel = ModelLoaderOBJ.LoadModel(wheelModelPath, wheelTexPath, gl);
		
		if (bodyModel == null) {
			return false;
		}
		if (wheelModel == null) {
			return false;
		}
		return true;
	}

}
