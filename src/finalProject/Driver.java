package finalProject;

import com.jogamp.opengl.GL2;

public class Driver {
	public Car myCar;
	public boolean AI;
	public float[] position = new float[2];
	public float theta, turn, rotation, look_x, look_y;
	public float inertia = 0;
	public int aiState = 0;
	public float aiForward;
	public FirstPerson FP;
	
	public Driver(Car givenCar, float x, float y, boolean isAI, FirstPerson P){
		AI = isAI;
		myCar = givenCar;
		look_x = x + (float)Math.cos(theta);
		look_y = y + (float)Math.sin(theta);
		position[0] = x;
		position[1] = y;
		turn = 0;
		rotation = 0;
		theta = (float)Math.PI;
		aiForward = theta;
		FP = P;
		
		
		
	}
	
	public boolean passGo(){
		return position[0] < 5 && position[0] > -5 && position[1] < -280;
	}
	
	public void DrawFP(GL2 gl){
		if(inertia>0)
			rotation-=5;
		else if(inertia <0)
			rotation+=5;
		
		float inertiaLimit = -2.0f - (float)myCar.speed/4;
		
		
		if(inertia > -inertiaLimit/3){
			inertia = -inertiaLimit/3;
		}
		else if(inertia > 0.3)
			inertia -=0.01*(float)myCar.speed/6;
		else if(inertia < inertiaLimit){
			inertia = inertiaLimit;
		}
		else if(inertia < -0.3)
			inertia +=0.01*(float)myCar.speed/6;
		else
			inertia = 0;
		
		//System.out.println(inertia);
		position[0] += (float)Math.cos(theta) * inertia;
		position[1] += (float)Math.sin(theta) * inertia;
		
		float RAD2DEG = 180.0f/(float)Math.PI;
		gl.glPushMatrix();
		look_x = position[0] + (float)Math.cos(theta);
		look_y = position[1] + (float)Math.sin(theta);
		gl.glTranslatef(position[0],position[1], 0);
		gl.glRotatef(theta*RAD2DEG, 0, 0, 1);
		FP.draw(gl, turn);
		gl.glPopMatrix();
		
	}
	
	public void DrawDriver(GL2 gl){
		
		if(AI)
			ControlAI();
		
		if(inertia>0)
			rotation-=5;
		else if(inertia <0)
			rotation+=5;
		
		float inertiaLimit = -2.0f - (float)myCar.speed/4;
		
		
		if(inertia > -inertiaLimit/3){
			inertia = -inertiaLimit/3;
		}
		else if(inertia > 0.3)
			inertia -=0.01*(float)myCar.speed/6;
		else if(inertia < inertiaLimit){
			inertia = inertiaLimit;
		}
		else if(inertia < -0.3)
			inertia +=0.01*(float)myCar.speed/6;
		else
			inertia = 0;
		
		//System.out.println(inertia);
		position[0] += (float)Math.cos(theta) * inertia;
		position[1] += (float)Math.sin(theta) * inertia;
		
		float RAD2DEG = 180.0f/(float)Math.PI;
		gl.glPushMatrix();
		look_x = position[0] + (float)Math.cos(theta);
		look_y = position[1] + (float)Math.sin(theta);
		gl.glTranslatef(position[0],position[1], 0);
		gl.glRotatef(theta*RAD2DEG, 0, 0, 1);
		
		myCar.drawCar(gl, rotation, turn);
		gl.glPopMatrix();
		
	}
	
	public void driveForward(){
		position[0] += (float)Math.cos(theta) * (float)myCar.speed*myCar.speed * -1/350;
		position[1] += (float)Math.sin(theta) * (float)myCar.speed*myCar.speed * -1/350;
		inertia += 0.3*-1;
	}
	
	public void  turnLeft(){
		if(theta < aiForward + Math.PI/2 - .05f)
			theta +=0.01*myCar.handling;
		turn = 45;
		
	}
	
	public void ControlAI(){
		int straightAwayLen = 150 + (int)(myCar.handling*myCar.handling*myCar.handling*myCar.handling);
		switch(aiState){
		case 0:
			//System.out.println("Made it to state 1");
			driveForward();
			if(theta < aiForward){
				theta +=0.01*myCar.handling;
				//System.out.println("Theta:" + theta + "   aiForward: "+ aiForward);
			}
			else if (theta > aiForward)
				theta -=0.01*myCar.handling;
			if(position[0] > straightAwayLen) 
				aiState ++;
			
			break;
		case 1:
			//System.out.println("Made it to state 2");
			turnLeft();
			driveForward();
			if(position[1] > - straightAwayLen){
				turn = 0;
				aiState ++;
				aiForward += Math.PI/2;
			}
			break;
		case 2:
			//System.out.println("Made it to state 3");
			driveForward();
			if(theta < aiForward){
				theta +=0.01*myCar.handling;
				//System.out.println("Theta:" + theta + "   aiForward: "+ aiForward);
			}
			else if (theta > aiForward)
				theta -=0.01*myCar.handling;
			if(position[1] > straightAwayLen)
				aiState ++;
			break;
		case 3:
			//System.out.println("Made it to state 4");
			turnLeft();
			driveForward();
			if(position[0] < straightAwayLen){
				turn = 0;
				aiState ++;
				aiForward += Math.PI/2;
			}
			break;
		case 4:
			//System.out.println("Made it to state 5");
			driveForward();
			if(theta < aiForward){
				theta +=0.01*myCar.handling;
				//System.out.println("Theta:" + theta + "   aiForward: "+ aiForward);
			}
			else if (theta > aiForward)
				theta -=0.01*myCar.handling;
			if(position[0] < -straightAwayLen)
				aiState ++;
			
			break;
		case 5:
			//System.out.println("Made it to state 6");
			turnLeft();
			driveForward();
			if(position[1] < straightAwayLen){
				turn = 0;
				aiState ++;
				aiForward += Math.PI/2;
			}
			break;
		case 6:
			//System.out.println("Made it to state 7");
			driveForward();
			if(theta < aiForward){
				theta +=0.01*myCar.handling;
				//System.out.println("Theta:" + theta + "   aiForward: "+ aiForward);
			}
			else if (theta > aiForward)
				theta -=0.01*myCar.handling;
			if(position[1] < -straightAwayLen)
				aiState ++;
			break;
		case 7:
			//System.out.println("Made it to state 8");
			turnLeft();
			driveForward();
			if(position[0] > -straightAwayLen){
				turn = 0;
				aiState= 0;
				aiForward += Math.PI/2;
			}
			break;
		
		
		}
		
		
	}
	
	
}
