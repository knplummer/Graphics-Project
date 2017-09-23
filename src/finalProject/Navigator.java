package finalProject;



import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;

public class Navigator extends Car{
	public String modelPath; 
	public String texturePath;
	public String wheelPath; 
	public String wheelTex;
	public GLModel body = null;
	public GLModel wheel = null;
	GLAutoDrawable gLDrawable;
	
	
	public Navigator(int tex){
		modelPath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Navigator/BlendTex.obj";
		wheelPath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Navigator/NavWheel.obj";
		wheelTex = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Navigator/BlendTex.mtl";
		
		
		if(tex == 1){
			texturePath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Navigator/BlendTex.mtl";
		}
		if(tex == 2){
			texturePath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Navigator/Orange.mtl";
		}
		if(tex == 3){
			texturePath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Navigator/White.mtl";
		}
		speed = 2;
		handling = 1.2;
		carCenter = new Vertex	(0,3f,0);
		collisionRadius = 7;
	
		
	}
	
	public void drawBRwheel(GL2 gl, float r){
	
		gl.glPushMatrix();
		gl.glTranslatef(-3.55f, 1.7f, -6.05f);
		gl.glRotatef(r, 1,0,0);
		gl.glScalef(-1.5f,1.5f,1.5f);
		wheel.opengldraw(gl);
		gl.glPopMatrix();
	}
	
	public void drawBLwheel(GL2 gl, float r){
		gl.glPushMatrix();
		gl.glTranslatef(3.55f, 1.7f, -6.05f);
		gl.glRotatef(r, 1,0,0);
		gl.glScalef(1.5f,1.5f,-1.5f);
		wheel.opengldraw(gl);
		gl.glPopMatrix();
		
	}
	
	public void drawFLwheel(GL2 gl, float r, float t){
		
		gl.glPushMatrix();
		gl.glTranslatef(3.5f, 1.7f, 6.8f);
		gl.glRotatef(t,0,1,0);
		gl.glRotatef(r, 1,0,0);
		gl.glScalef(1.5f,1.5f,-1.5f);
		wheel.opengldraw(gl);
		
		gl.glPopMatrix();

	}
	
	public void drawFRwheel(GL2 gl, float r, float t){
		gl.glPushMatrix();
		gl.glTranslatef(-3.5f, 1.7f, 6.8f);
		gl.glRotatef(t,0,1,0);
		gl.glRotatef(r, 1,0,0);
		gl.glScalef(-1.5f,1.5f,1.5f);
		wheel.opengldraw(gl);
		
		gl.glPopMatrix();
		
	}
	
	public void drawBody(GL2 gl){
		gl.glPushMatrix();
		gl.glScalef(1.5f,1.5f, 1.5f);
	    gl.glTranslatef(0,3.1f,0);
	    body.opengldraw(gl);
		gl.glPopMatrix();
	}
	
	public void drawCar(GL2 gl, float rotate, float turn){
		gl.glPushMatrix();
		gl.glRotatef(90, 1, 0, 0);
		gl.glRotatef(90, 0,-1,0);
		drawFLwheel(gl, rotate, turn);
		drawFRwheel(gl, rotate, turn);
		drawBLwheel(gl,rotate);
		drawBRwheel(gl, rotate);
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
		body = ModelLoaderOBJ.LoadModel(modelPath,
				texturePath, gl);
		wheel = ModelLoaderOBJ.LoadModel(wheelPath, wheelTex, gl);
		if (body == null) {
			return false;
		}
		if(wheel == null){
			return false;
		}
		return true;
	}
	

   
  

}
