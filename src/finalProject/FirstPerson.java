package finalProject;

import com.jogamp.opengl.GL2;

public class FirstPerson {
	public String wheelModelPath; 
	public String wheelTexturePath;
	public String carModelPath;
	public String carTexPath;
	
	GLModel wheel = null;
	GLModel car = null;

	
	
	public FirstPerson(){
		wheelModelPath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Textures/normalsWheel.obj";
		wheelTexturePath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Textures/normalsWheel.mtl";
		carModelPath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Textures/ThisBetterWork.obj";
		carTexPath = "C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Textures/ThisBetterWork.mtl";


		

		
	}
	
	public void drawWheel(GL2 gl, float r){
		
		gl.glPushMatrix();
		gl.glTranslatef(-1.8f, 2, -3.2f);
		gl.glRotatef(r,1,0,0);
		gl.glRotatef(90, -1, 0, 0);
		gl.glRotatef(90, 0, 1, 0);
		gl.glScalef(.15f,.15f,.15f);
		wheel.opengldraw(gl);
		
		gl.glPopMatrix();
	}
	
	public void drawCar(GL2 gl){
		
		gl.glPushMatrix();
		
		gl.glTranslatef(0, 0, -2.6f);
		gl.glRotatef(90,-1,0,0);
		gl.glScalef(4,4,4);
		car.opengldraw(gl);
		
		gl.glPopMatrix();
		
	}
	

	
	public void draw(GL2 gl, float wheelTurn){
		
		gl.glPushMatrix();
		gl.glRotatef(180,1,0,0);
	
		drawWheel(gl, wheelTurn);
		drawCar(gl);

		
		
		gl.glPopMatrix();
		
	}
	
	
	
	public boolean loadModels(GL2 gl) {
		wheel = ModelLoaderOBJ.LoadModel(wheelModelPath, wheelTexturePath, gl);
		car = ModelLoaderOBJ.LoadModel(carModelPath, carTexPath, gl);
		
		if (wheel == null) {
			return false;
		}
		if(car == null){
			return false;
		}
	
		
	
		return true;
	}
	

	

	

   
  

}

