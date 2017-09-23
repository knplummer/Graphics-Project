package finalProject;

import com.jogamp.opengl.GL2;


public class Vertex {
	
	public float x;
	public float y;
	public float z;
	
	public Vertex(){
		x = 0;
		y = 0;
		z = 0;
	}
	
	public Vertex(float xPos, float yPos, float zPos){
		x = xPos;
		y = yPos;
		z = zPos;
	}
	
	public float getX(){
		return x;
	}
	
	public void setX(float newX){
		x = newX;
	}
	
	public float getY(){
		return y;
	}
	
	public void setY(float newY){
		z = newY;
	}
	
	public float getZ(){
		return z;
	}
	
	public void setZ(float newZ){
		z = newZ;
	}
	
	//draws the vertex on the screen 
	public void drawVertex(GL2 gl2){
		GL2 gl = gl2.getGL().getGL2();
		gl.glVertex3f(x,y,z);
	}
	
	//returns the distance between 2 vertices
	//possibly use for an improved collision detection but if so probably should change to exclude the y-demension
	public float distance(Vertex v2){
		float xDist = x - v2.getX();
		float yDist = y - v2.getY();
		float zDist = z - v2.getZ();
		float distance = 0;
		xDist = xDist*xDist;
		yDist = yDist*yDist;
		zDist = zDist*zDist;
		distance = (float) Math.sqrt(xDist+yDist+zDist);
		return distance;
	}

}
