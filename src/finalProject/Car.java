package finalProject;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;


//creates a type of car whose vertices are hard coded and not read from a file
public class Car {
	
	// use as pos/neg Z cord translate multiplier so cars with higher speed move faster?
	public double speed;
	
	// use as pos/neg X multiplier cars with higher handling are more sensitive to direction keys
	public double handling;  
	
	// an int between 0 and the number of cars a player can choose from so the map is populated randomly by cars that are not the same as theirs
	public int randSelectID;
	
	
	public Vertex carCenter;
	
	//the radius of the circular bumper surrounding the car for collision detection
	public int collisionRadius;
	
	//a list of all of the vertices used to draw the car
	public ArrayList<Vertex> pointCloud = new ArrayList<Vertex>();
	
	public Car(){
		speed = 1;
		handling = 1;
		randSelectID = 0;
		carCenter = new Vertex(0, 0, 0);
		collisionRadius = 0;
	}
	
	public void drawCar(GL2 gl, float wheelRotate, float wheelTurn){
		//TODO
	};
	
	double getSpeed(){
		return speed;
	}
	
	void setSpeed(int s){
		speed = s;
	}
	
	double getHandling(){
		return handling;
	}
	
	void setHandling(int h){
		handling = h;
	}
	
	int getRandSelectID(){
		return randSelectID;
	}
	
	void setRandSelectID(int id){
		randSelectID = id;
	}
	
	Vertex getCenter(){
		return carCenter;
	}
	
	void setCenter(int x, int y, int z){
		carCenter.setX(x);
		carCenter.setY(y);
		carCenter.setZ(z);
	}
	
	ArrayList<Vertex> getPoints(){
		return pointCloud;
	}
	

}
