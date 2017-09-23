package finalProject;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;



//Press f for a free rotate otherwise it will only rotate around the z-axis 
//mostly the same as the basketball thing from hw 4 but at a set 60 fps

public class testCanvas implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	
	Double camZPos = 20.0;
	Double camXPos = -50.0;
	Double camYPos = 5.0;
	int lines = 2;
	float backrgb[] = new float[4]; 
	float rot;
	float rotY;
	float rotX; 
	float xOrig;
	float yOrig;
	boolean zoom;
	boolean rotate;
	boolean translate;
	char body = 's';
	float aimX = 0;
	float aimY = 0;
	float aimZ = 0;
	int anim = 0;
	float saveX = 0;
	float saveY = 0;
	Double saveZ = 0.0;
	float yRotate = 0;
	float camRot  = 0;
	int texID[]  = new int[4];
	ArrayList<Double> x = new ArrayList<Double>();
	ArrayList<Double> y = new ArrayList<Double>();
	ArrayList<Double> z = new ArrayList<Double>();
	int xVelocity = 0;
	int yVelocity = 0;
	int zVelocity = 0;
	int i = 0;
	int j = 0;
	int k = 0;
	char launch = 'n';
	double xLaunch = 0;
	double yLaunch = 0;
	double zLaunch = 0;
	boolean firstPerson = false;
	boolean thirdPerson = true;
	int light = 0;
	int light1 = 0;
	int light2 = 0;
	int light3 = 0;
	int perspective = 0;
	char initialized = 'n';
	float scaleFactor = 1;
	float transX = 0;
	float transY = 0;
	boolean lock = false;
	int xRotate = 0;
	int zRotate = 0;
	int ballRotate = 0;
	Vertex v1 = new Vertex(-5f, 0f, 0f);
	Vertex v2 = new Vertex(5f, 0f, 0f);
	Vertex v3 = new Vertex(0f, 5f, 0f);
	HardCodeCar HC = new HardCodeCar();
	Aventador Avent = new Aventador(2);
	//Aventador Avent2 = new Aventador(2);
	Navigator Nav = new Navigator(1);
	//FormulaOne f1 = new FormulaOne();
	float r = 0;
	//Mercedes sls = new Mercedes();
	float turn = 0;
	//ChryslerBuilding chrys = new ChryslerBuilding();
	//Building3 b3 = new Building3();
	WorldBuilder world = new WorldBuilder();
	//Building5 b4 = new Building5();

	
	
	/*
	 * Custom variables for mouse drag operations 
	 */
	int windowWidth, windowHeight;
	float orthoX=40;
	float tVal_x, tVal_y, rVal_x, rVal_y, rVal;
	double rtMat[] = new double[16];
	int mouseX0, mouseY0;
	int saveRTnow=0, mouseDragButton=0;
	

    	private GLU glu = new GLU();

	
	 public void displayChanged(GLAutoDrawable gLDrawable, 
	            boolean modeChanged, boolean deviceChanged) {
	    }

	    /** Called by the drawable immediately after the OpenGL context is
	     * initialized for the first time. Can be used to perform one-time OpenGL
	     * initialization such as setup of lights and display lists.
	     * @param gLDrawable The GLAutoDrawable object.
	     */
	    public void init(GLAutoDrawable gLDrawable) {
	        GL2 gl = gLDrawable.getGL().getGL2();
	        //gl.glShadeModel(GL.GL_LINE_SMOOTH);              // Enable Smooth Shading
	        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
	        //gl.glClearDepth(1.0f);                      // Depth Buffer Setup
	        gl.glEnable(GL.GL_DEPTH_TEST);              // Enables Depth Testing
	        //gl.glDepthFunc(GL.GL_LEQUAL);               // The Type Of Depth Testing To Do
	        // Really Nice Perspective Calculations
	        //gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
	        

	        gl.glMatrixMode(GL2.GL_MODELVIEW);
	        gl.glLoadIdentity();
	        gl.glGetDoublev(GL2.GL_MODELVIEW_MATRIX, rtMat, 0);
	        gl.glEnable(GL2.GL_NORMALIZE);
	        
	        //if (false == Avent.loadModels(gl)) {
				//System.out.println("Aventador not Loaded");
			//}
	        
	        //if (false == Nav.loadModels(gl)) {
				//System.out.println("Navigator not Loaded");
			//}
	        
	       // if (false == sls.loadModels(gl)) {
				//System.out.println("Mercedes not Loaded");
			//}
	        
	        
	        if(false == world.initializeWorld(gl)){
	        	System.out.println("World not Loaded");
	       }
	      //if (false == b4.loadModels(gl)) {
			//System.out.println("Aventador not Loaded");
		  //}
	     // if (false == Avent.loadModels(gl)) {
			//System.out.println("Aventador not Loaded");
	      //}
	        
	       
	        
	        // load an image;
	        
	        try {
	        	
	        	//ground
				//BufferedImage aImage = ImageIO.read(new File("C:\\Users\\Kalen Plummer\\workspace\\Final Project\\src\\background-asphalt.jpg"));
	        	BufferedImage aImage = ImageIO.read(new File("G:/GraphicsWorkspace/Final Project/src/Textures/track texture final.jpg"));
	        	
				ByteBuffer buf = convertImageData(aImage);
				
				gl.glGenTextures(4, texID, 0);
				gl.glBindTexture(GL.GL_TEXTURE_2D, texID[0]);
				
				gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
		        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);

				gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGB, aImage.getWidth(), 
	                    aImage.getHeight(), 0, GL2.GL_BGR, GL.GL_UNSIGNED_BYTE, buf);
				
				
				gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
				//gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
				

				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	        
	        
	        
	        
	    }
	    
	    private ByteBuffer convertImageData(BufferedImage bufferedImage) {
	        ByteBuffer imageBuffer;

	        
	        DataBuffer buf = bufferedImage.getRaster().getDataBuffer(); 
	      
	        
	        final byte[] data = ((DataBufferByte) buf).getData();

	        
	        return (ByteBuffer.wrap(data)); 
	    }


	    
	    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
	    	windowWidth = width;
	    	windowHeight = height;
	        final GL2 gl = gLDrawable.getGL().getGL2();

	        if (height <= 0) // avoid a divide by zero error!
	            height = 1;
	        final float h = (float) width / (float) height;
	        gl.glViewport(0, 0, width, height);
	        gl.glMatrixMode(GL2.GL_PROJECTION);
	        gl.glLoadIdentity();
	        //gl.glOrtho(-orthoX*0.5, orthoX*0.5, -orthoX*0.5*height/width, orthoX*0.5*height/width, -100, 100);
	        glu.gluPerspective(45.0f, h, 0.5, 1000.0);
	        
	        gl.glMatrixMode(GL2.GL_MODELVIEW);
	        
	    }

	    
	    
		@Override
		public void display(GLAutoDrawable gLDrawable) {
			// TODO Auto-generated method stub
			final GL2 gl = gLDrawable.getGL().getGL2();
			
			
			gl.glTranslatef(0,transY,transX);
			gl.glRotatef(rot,0,1,0);
			gl.glRotatef(rotX,1,0,0);
			gl.glScalef(scaleFactor,scaleFactor, scaleFactor);
			
			gl.glClearColor(.35f, .35f, .35f, 1);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
			
			float materialColor[] = {1.0f, 1.0f, 0.0f, 1.0f};
	         //The specular (shiny) component of the material
	         float  materialSpecular[] = {0,0,1,1};
	         //The color emitted by the material
	         float materialEmission[] = {1.0f,1.0f,0, 1.0f};

	         float shininess = 100;
	         
			 materialColor[0]= 0;
			 gl.glEnable(GL2.GL_COLOR_MATERIAL);
	         gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, materialColor, 0);
	         gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, materialSpecular, 0);
	         //gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_EMISSION, materialEmission, 0);
	         gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, shininess);
	         
	         gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, materialColor, 0);
	         gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, materialSpecular, 0);
	         
			
			
			 float[] light1Pos = { 1,10,1,0 };        // light position
			 float[] light2Pos = {-47,15,0,0};
			 float[] light3Pos = {47,15,0,0};
		     float[] noAmbient = { 0.5f, 0.2f, 0.2f, 1f };     // low ambient light
		     float[] diffuse = { 1f, 1f, 1f, 1f };        // full diffuse color
		        
		     
		     gl.glEnable(GL2.GL_LIGHTING);
		      
		        
		     //turn of light 1
		     if(light1 % 2 == 0){
		        gl.glEnable(GL2.GL_LIGHT0);
		     }
		     else{
		    	 gl.glDisable(GL2.GL_LIGHT0);
		     }
		     gl.glEnable(GL2.GL_LIGHT0);
		     gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, noAmbient, 0);
		     gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse, 0);
		     gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION,light1Pos, 0);
		        
		     //turn off light 2
		     if(light2 % 2 == 0){
		        gl.glEnable(GL2.GL_LIGHT1);
		     }
		     else{
		        gl.glDisable(GL2.GL_LIGHT1);
		     }
		     gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, noAmbient, 0);
		     gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, diffuse, 0);
		     gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION,light2Pos, 0);
		        
		     //turn off light 3
		     if(light3 % 2 == 0){
		        gl.glEnable(GL2.GL_LIGHT2);
		     }
		     else{
		        gl.glDisable(GL2.GL_LIGHT2);
		     }
		     gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_AMBIENT, noAmbient, 0);
		     gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_DIFFUSE, diffuse, 0);
		     gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_POSITION,light3Pos, 0);
		     
		     
		   //Draw coordinate axis
				//x-axis
				gl.glLineWidth(2);
				gl.glBegin(GL.GL_LINES);
				gl.glColor3f(1,0,0);
				gl.glVertex3f(0,0,0);
				gl.glVertex3f(1.5f,0,0);
				gl.glEnd();
				
				gl.glPushMatrix();
				gl.glTranslatef(1.5f, 0, 0);
				gl.glRotatef(-90,0,1,0);
				gl.glBegin(GL2.GL_TRIANGLE_FAN);
				gl.glColor3f(1, 0, 0);
				gl.glVertex3f(0,0,-.3f);
				for (int i = 0; i <= 360; i += 360/30){
					double vertex = i * Math.PI/180;
					double x = Math.cos(vertex)*.08;
					double y = Math.sin(vertex)*.08;
					gl.glVertex3d(x,y,0);
				}
				gl.glEnd();
				gl.glFlush();
				gl.glPopMatrix();
				
				gl.glLineWidth(2);
				gl.glBegin(GL.GL_LINES);
				gl.glColor3f(1,1,0);
				gl.glVertex3f(0,0,0);
				gl.glVertex3f(0,1.5f,0);
				gl.glEnd();
				
				//y-axis
				gl.glPushMatrix();
				gl.glTranslatef(0, 1.5f, 0);
				gl.glRotatef(90,1,0,0);
				gl.glBegin(GL2.GL_TRIANGLE_FAN);
				gl.glColor3f(1, 1, 0);
				gl.glVertex3f(0,0,-.3f);
				for (int i = 0; i <= 360; i += 360/30){
					double vertex = i * Math.PI/180;
					double x = Math.cos(vertex)*.08;
					double y = Math.sin(vertex)*.08;
					gl.glVertex3d(x,y,0);
				}
				gl.glEnd();
				gl.glFlush();
				gl.glPopMatrix();
				
				gl.glLineWidth(2);
				gl.glBegin(GL.GL_LINES);
				gl.glColor3f(0,0,1);
				gl.glVertex3f(0,0,0);
				gl.glVertex3f(0,0,1.5f);
				gl.glEnd();
				
				//z-axis
				gl.glPushMatrix();
				gl.glTranslatef(0, 0, 1.5f);
				gl.glRotatef(180,1,0,0);
				gl.glBegin(GL2.GL_TRIANGLE_FAN);
				gl.glColor3f(0, 0, 1);
				gl.glVertex3f(0,0,-.3f);
				for (int i = 0; i <= 360; i += 360/30){
					double vertex = i * Math.PI/180;
					double x = Math.cos(vertex)*.08;
					double y = Math.sin(vertex)*.08;
					gl.glVertex3d(x,y,0);
				}
				gl.glEnd();
				gl.glFlush();
				gl.glPopMatrix();
				

		        
		        //draws ground
				gl.glPushMatrix();
				gl.glTranslatef(0, 0, 0);
		        gl.glEnable(GL.GL_TEXTURE_2D);
		        gl.glBindTexture(GL.GL_TEXTURE_2D, texID[0]);
		        gl.glNormal3f(0,1,0);
				gl.glColor3f(1,1,1);
				gl.glBegin(GL2.GL_QUADS);
				gl.glTexCoord2f(0f,0f);
				gl.glVertex3f(-500f, 0, -500f);
				gl.glTexCoord2f(1f,0f);
				gl.glVertex3f(500f,0,-500f);
				gl.glTexCoord2f(1f,1f);
				gl.glVertex3f(500f,0,500f);
				gl.glTexCoord2f(0f,1f);
				gl.glVertex3f(-500, 0, 500);
				gl.glEnd();
				gl.glDisable(GL.GL_TEXTURE_2D);
				gl.glPopMatrix();
		        
				world.drawWorld(gl);
		     //Draw stuff here
		     /*
		     gl.glBegin(GL.GL_TRIANGLES);
		     gl.glColor3f(1,1,1);
		     gl.glNormal3f(0, 0, 1);
		     v1.drawVertex(gl);
		     v2.drawVertex(gl);
		     v3.drawVertex(gl);
		     gl.glEnd();
		     */
		     
		     //car1.drawCar(gl);
		     //car1.drawCollision(gl);
		    // world.drawWorld(gl);
		    // b4.draw(gl);
				
			gl.glPushMatrix();
			gl.glTranslatef(0,0,-10);
			//Nav.drawCar(gl,r,turn);
			gl.glPopMatrix();
			
			gl.glPushMatrix();
			gl.glTranslatef(0,0,-10);
			//Avent.drawCar(gl,r,turn);
			gl.glPopMatrix();
		     /*
		     gl.glPushMatrix();
		     gl.glTranslatef(0,0,7);
		     Avent.drawCar(gl,r,turn);
			 gl.glPopMatrix();
			 
			 
			 gl.glPushMatrix();
		     gl.glTranslatef(0,0,-7);
		     Avent2.drawCar(gl,r,turn);
			 gl.glPopMatrix();
		     */
		     
		    

	      
	        
	        
	        
	        if(perspective % 2 == 0){
	        	thirdPerson = true;
	        	firstPerson = false;
	        }
	        
	        if(perspective % 2 ==1){
	        	thirdPerson = false;
	        	firstPerson = true;
	        }
			
	        if(firstPerson){
	        	if(initialized == 'y'){
	        		camXPos = x.get(i);
	        		camYPos = y.get(j);
	        		camZPos = z.get(k);
	        		aimX = (float) (x.get(i)+xVelocity);
	        		aimY = (float) (camYPos+0);
	        		aimZ = (float)(z.get(k)+zVelocity);
	        		transX = 0;
	        		transY = 0;
	        		scaleFactor = 1;
	        		rot = 0;
	        		rotX = 0;
	        		
	        	}
	        	else{
	        		camXPos = 0.0;
	        		camYPos = 0.4;
	        		camZPos = 0.0;
	        		aimX = 1;
	        		aimY = 0.5f;
	        		aimZ = 0;
	        	}
	        	
	        }
	        
	        
	        if(thirdPerson){
	        	camXPos = -20.0;
	        	camYPos = 10.0;
	        	camZPos = 6.0;
	        	aimX = 0;
	        	aimY = 4;
	        	aimZ = 0;
	        }
	        
	        

	        gl.glLoadIdentity();
		    glu.gluLookAt(camXPos,camYPos,camZPos,aimX,aimY, aimZ, 0, 1, 0);
	      
	       
	        if(i < x.size()-1){
	        	i++;
	        }
	        if(j < y.size()-1){
	        	j++;
	        }
	        if(k < z.size()-1){
	        	k++;
	        }
	        
	        if(i == x.size()-2){
	        	launch = 'n';
	        	xLaunch = 0;
	        	yLaunch = 0;
	        	zLaunch = 0;
	        }
	        if(j == y.size()-2){
	        	launch = 'n';
	        	xLaunch = 0;
	        	yLaunch = 0;
	        	zLaunch = 0;
	        }
	        if(k == z.size()-2){
	        	launch = 'n';
	        	xLaunch = 0;
	        	yLaunch = 0;
	        	zLaunch = 0;
	        }
	        
	        r++;

		
	                             
		}

		@Override
		public void dispose(GLAutoDrawable arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		    char key= e.getKeyChar();
			System.out.printf("Key typed: %c\n", key); 
			if (key == KeyEvent.VK_ESCAPE ){
				System.exit(0);
			}
			if(key == 'r'){
				xVelocity = 0;
				yVelocity = 0;
				zVelocity = 0;
				xLaunch = 0;
				yLaunch = 0;
				zLaunch = 0;
				i = 0;
				j = 0;
				k = 0;
				x.clear();
				y.clear();
				z.clear();
			}
			
			if(key == 'X'){
				xVelocity--;
			}
			
			if(key == 'x'){
				xVelocity++;
			}
			
			if(key == 'Y'){
				if(yVelocity > 0){
					yVelocity--;
				}
				if(yVelocity <=0){
					yVelocity = 0;
				}
			}
			
			if(key == 'y'){
				yVelocity++;
			}
			
			if(key == 'Z'){
				zVelocity--;
			}
			
			if(key == 'z'){
				zVelocity++;
			}
			
			if(key == 's'){
				if(xVelocity != 0 || zVelocity != 0){
					//physicsEngine(xVelocity, yVelocity, zVelocity);
					launch = 'y';
					initialized = 'y';
					if(xVelocity > 0){
						zRotate = 1;
					}
					if(xVelocity < 0){
						zRotate = -1;
					}
					if(zVelocity > 0){
						xRotate = 1;
					}
					if(xVelocity < 0){
						zRotate = -1;
					}
				}
			}
			
			if(key == 't'){
				perspective++;
			}
			
			if(key == 'l'){
				light++;
			}
			
			if(key == '1'){
				light1++;
			}
			if(key == '2'){
				light2++;
			}
			if(key == '3'){
				light3++;
			}
			if(key == 'f'){
				lock = true;
			}
			
			if(key == 'a'){
				turn = 45;
			}
			if(key == 'd'){
				turn = -45;
			}
			
			
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			char key= e.getKeyChar();
			
		}
			

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			lock = false;
			System.out.println(lock);
			turn = 0;
		}

		@Override
		public void mouseDragged(MouseEvent m) {
			// TODO Auto-generated method stub
			//System.out.println("mouse dragged");
			
			float XX = (m.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(m.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			
			if(rotate){
				if(lock){
					rotX += -(m.getY()-mouseY0)*.5;
				}
				rot += -(m.getX()-mouseX0)*.5;
			}
			
			if(translate){
				transX += (m.getX()-mouseX0)*.1;
				transY += -(m.getY()-mouseY0)*.1;
			}
			
			if(zoom){
				if(scaleFactor <= 0){
					scaleFactor = .01f;
				}
				scaleFactor += -(m.getX()-mouseX0)*.01;
				scaleFactor += -(m.getY()-mouseY0)*.01;
			}
			
			mouseX0 = m.getX(); 
			mouseY0 = m.getY();
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			//System.out.println("click");

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			// TODO Auto-generated method stub
			/*
			 * Coordinates printout
			 */
			//System.out.println("mouse pressed");
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			System.out.printf("Point clicked: (%.3f, %.3f)\n", XX, YY);
			
			mouseX0 = (e.getX()/(windowWidth/2));
			mouseY0 = (e.getY()/(windowHeight/2));
			if(e.getButton()==MouseEvent.BUTTON1) {	// Left button
				System.out.println("left");
				rotate = true;
				xOrig = XX;
				yOrig = YY;
			}
			else if(e.getButton()==MouseEvent.BUTTON3) {	// Right button
				System.out.println("right");
				translate = true;
				xOrig = XX;
				yOrig = YY;
				
			}
			else if(e.getButton() == MouseEvent.BUTTON2){
				System.out.println("middle");
				zoom = true;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			// TODO Auto-generated method stu
			//System.out.println("mouse released");
			rotate = false;
			zoom = false;
			translate = false;
		
		}

		
		

	  /*  
	public void init(GLDrawable gLDrawable) {
		final GL gl = glDrawable.getGL();
        final GLU glu = glDrawable.getGLU();

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(-1.0f, 1.0f, -1.0f, 1.0f); // drawing square
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }*/
	
}

