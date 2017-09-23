package finalProject;

import com.jogamp.opengl.GL2;

import com.jogamp.opengl.util.gl2.GLUT;


//This car looks terrible dont use for final project
public class HardCodeCar extends Car{
	
	public HardCodeCar(){
		speed = 1;
		handling  = 1;
		randSelectID = 0;
		carCenter = new Vertex(-1.5f, 3, 0);
		collisionRadius = 8;
	}
	
	void drawBody(GL2 gl2){
		GL2 gl = gl2.getGL().getGL2();
		gl.glPushMatrix();
		gl.glTranslatef(0,-.3f,0);
		gl.glColor3f(0,1,0);
		gl.glNormal3f(0,1,0);
		//top
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3f(-3,6,-2);
		gl.glVertex3f(-3,6,2);
		gl.glVertex3f(3,6,2);
		gl.glVertex3f(3,6,-2);
		gl.glEnd();
		//left window separator
		gl.glNormal3f(0,0,1);
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3f(-.1f,6,2f);
		gl.glVertex3f(.1f,6,2f);
		gl.glVertex3f(.1f, 3.8f, 2.2f);
		gl.glVertex3f(-.1f, 3.8f, 2.2f);
		gl.glEnd();
		//right window separator
		gl.glNormal3f(0,0,-1);
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3f(-.1f,6,-2f);
		gl.glVertex3f(.1f,6,-2f);
		gl.glVertex3f(.1f, 3.8f, -2.2f);
		gl.glVertex3f(-.1f, 3.8f, -2.2f);
		gl.glEnd();
		//front hood
		gl.glNormal3f(0,1,0);
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3f(-5,3.73f,-2.2f);
		gl.glVertex3f(-5,3.73f,2.2f);
		gl.glVertex3f(-10,3.7f,2.2f);
		gl.glVertex3f(-10,3.7f,-2.2f);
		gl.glEnd();
		//back hood
		gl.glNormal3f(0,1,0);
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3f(7,3.9f,-2.2f);
		gl.glVertex3f(7,3.9f,2.2f);
		gl.glVertex3f(4,3.85f,2.2f);
		gl.glVertex3f(4,3.85f,-2.2f);
		gl.glEnd();
		//back end
		gl.glNormal3f(1,0,0);
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3f(7,3.9f,-2.2f);
		gl.glVertex3f(7,3.9f,2.2f);
		gl.glVertex3f(7.1f,2.5f,2.2f);
		gl.glVertex3f(7.1f,2.5f,-2.2f);
		gl.glEnd();
		//front end
		gl.glNormal3f(-1,0,0);
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3f(-10,3.7f,-2.2f);
		gl.glVertex3f(-10,3.7f,2.2f);
		gl.glVertex3f(-10.2f,2.5f,2.2f);
		gl.glVertex3f(-10.2f,2.5f,-2.2f);
		gl.glEnd();
		//left side panel
		gl.glNormal3f(0,0,1);
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3f(-10,3.7f,2.2f);
		gl.glVertex3f(-10.2f,2.5f,2.2f);
		gl.glVertex3f(7.1f,2.5f,2.2f);
		gl.glVertex3f(7,3.9f,2.2f);
		gl.glEnd();
		//right side panel
		gl.glNormal3f(0,0,-1);
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3f(-10,3.7f,-2.2f);
		gl.glVertex3f(-10.2f,2.5f,-2.2f);
		gl.glVertex3f(7.1f,2.5f,-2.2f);
		gl.glVertex3f(7,3.9f,-2.2f);
		gl.glEnd();
		//FL window separator
		gl.glNormal3f(-.8f,.2f,0);
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3f(-3,6,1.8f);
		gl.glVertex3f(-3,6,2f);
		gl.glVertex3f(-5,3.73f,2.2f);
		gl.glVertex3f(-5,3.73f,2);
		gl.glEnd();
		//FR window separator
		gl.glNormal3f(-.8f,.2f,0);
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3f(-3,6,-1.8f);
		gl.glVertex3f(-3,6,-2f);
		gl.glVertex3f(-5,3.73f,-2.2f);
		gl.glVertex3f(-5,3.73f,-2);
		gl.glEnd();
		//BR window separator
		gl.glNormal3f(.8f,.2f,0);
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3f(3,6,-1.8f);
		gl.glVertex3f(3,6,-2f);
		gl.glVertex3f(4,3.85f,-2.2f);
		gl.glVertex3f(4,3.85f,-2);
		gl.glEnd();
		//BL window separator
		gl.glNormal3f(.8f,.2f,0);
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3f(3,6,1.8f);
		gl.glVertex3f(3,6,2f);
		gl.glVertex3f(4,3.85f,2.2f);
		gl.glVertex3f(4,3.85f,2);
		gl.glEnd();
		//Bottom
		gl.glColor3f(.2f,.2f,.2f);
		gl.glNormal3f(.8f,.2f,0);
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3f(-10.2f,2.5f,2.2f);
		gl.glVertex3f(-10.2f,2.5f,-2.2f);
		gl.glVertex3f(7.1f,2.5f,-2.2f);
		gl.glVertex3f(7.1f,2.5f,2.2f);
		gl.glEnd();
		
		gl.glPopMatrix();
	}
	
	void drawLFWheel(GL2 gl2){
		
		GL2 gl = gl2.getGL().getGL2();
		GLUT glut =  new GLUT();
		gl.glPushMatrix();
		gl.glColor3f(0,0,0);
		gl.glTranslatef(-7.5f,1.4f,1.8f);
		gl.glScalef(.5f,.5f,.5f);
		glut.glutSolidTorus(1, 2, 20, 20);
		gl.glPopMatrix();
	}
	
	void drawRFWheel(GL2 gl2){
		
		GL2 gl = gl2.getGL().getGL2();
		GLUT glut =  new GLUT();
		gl.glPushMatrix();
		gl.glColor3f(0,0,0);
		gl.glTranslatef(-7.5f,1.4f,-1.8f);
		gl.glScalef(.5f,.5f,.5f);
		glut.glutSolidTorus(1, 2, 20, 20);
		gl.glPopMatrix();
	}
	
	void drawBRWheel(GL2 gl2){
		
		GL2 gl = gl2.getGL().getGL2();
		GLUT glut =  new GLUT();
		gl.glPushMatrix();
		gl.glColor3f(0,0,0);
		gl.glTranslatef(4f,1.4f,-1.8f);
		gl.glScalef(.5f,.5f,.5f);
		glut.glutSolidTorus(1, 2, 20, 20);
		gl.glPopMatrix();
	}
	
	void drawBLWheel(GL2 gl2){
		
		GL2 gl = gl2.getGL().getGL2();
		GLUT glut =  new GLUT();
		gl.glPushMatrix();
		gl.glColor3f(0,0,0);
		gl.glTranslatef(4f,1.4f,1.8f);
		gl.glScalef(.5f,.5f,.5f);
		glut.glutSolidTorus(1, 2, 20, 20);
		gl.glPopMatrix();
	}
	
	void drawCar(GL2 gl2){
		GL2 gl = gl2.getGL().getGL2();
		this.drawBLWheel(gl);
		this.drawBRWheel(gl);
		this.drawLFWheel(gl);
		this.drawRFWheel(gl);
		this.drawBody(gl);
	}
	
	void drawCollision(GL2 gl2){
		GL2 gl = gl2.getGL2().getGL2();
		double y = this.carCenter.getY();
		
		gl.glPushMatrix();
		gl.glRotatef(90, 1, 0, 0);
		gl.glLineWidth(3);
		gl.glEnable(GL2.GL_BLEND);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBegin(GL2.GL_LINE_LOOP);
		gl.glColor3f(1,1,1);
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

}
