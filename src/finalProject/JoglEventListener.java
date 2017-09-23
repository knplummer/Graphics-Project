package finalProject;

import java.awt.Font;
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
import java.util.*;

import javax.imageio.ImageIO;
import com.jogamp.opengl.*;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;

import com.jogamp.opengl.util.awt.TextRenderer;

//import finalProject.Navigator;

public class JoglEventListener implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	private int windowWidth, windowHeight;
	
	private TextureLoader texture_loader = null;
	private Skybox current_skybox = null;
	private final float skybox_size = 3000.0f;
	private boolean skybox_follow_camera = true;
	private final String[] skybox_names = {
		"ThickCloudsWater", "DarkStormy",
		"FullMoon", "SunSet",
		"CloudyLightRays", "TropicalSunnyDay"
	};
	// Making this larger will allocate more skybox textures to start, giving a
	// super slow startup, but allowing you to switch between them quickly.
	// Best to use a value of 1 for production code.
	private final int skybox_max_textures = 1;
	private Skybox[] skyboxes = new Skybox[ skybox_names.length ];
	
	
	private float scene_eye_x = -25f;
	private float scene_eye_y = 105f;
	private float scene_eye_z = 7.0f;
	private float scene_look_x = 1.0f;
	private float scene_look_y = 0.0f;
	private float scene_look_z = 0.0f;
	int texID[]  = new int[15];
	int RotX = 0;
	int RotZ = 0;
	TGABuffer TreeBuffer;
	
	
	boolean ControllingCar = false;
	boolean FPR = false;
	
	int startingposition[][] = {{-30, -340}, {-30,-310}, {-60,-325}, {-60,-295}};
	
	private int mouse_x0 = 0;
	private int mouse_y0 = 0;
	FirstPerson FP = new FirstPerson();
	Navigator Nav = new Navigator(2);
	Driver AI1 = new Driver(Nav, startingposition[1][0], startingposition[1][1],  true, FP);
	Aventador Mer = new Aventador(0);
	Driver Player1 = new Driver(Mer, startingposition[0][0], startingposition[0][1],  !ControllingCar, FP);
	Aventador Avent2 = new Aventador(1);
	Driver AI2 = new Driver(Avent2, startingposition[2][0], startingposition[2][1],  true, FP);
	WorldBuilder world = new WorldBuilder();
	Mercedes Mer2 = new Mercedes();
	Driver AI3 = new Driver(Mer2, startingposition[3][0], startingposition[3][1],  true, FP);
	
	TextRenderer rend = new TextRenderer(new Font("SansSerif", Font.BOLD, 124));
	int light = 0;
	int light1 = 0;
	int light2 = 0;
	int light3 = 0;
	
	Clock lapClock;
	
	private int mouse_mode = 0;
	
	private boolean following = true;
	
	
	private final int MOUSE_MODE_NONE = 0;
	private final int MOUSE_MODE_ROTATE = 1;
	
	private boolean[] keys = new boolean[256];

	
	private GLU glu = new GLU();
	
	public void displayChanged( GLAutoDrawable gLDrawable, boolean modeChanged,
			boolean deviceChanged) { }

	@Override
	public void init( GLAutoDrawable gLDrawable ) {
		GL2 gl = gLDrawable.getGL().getGL2();
		
		
		
		try {
            // Debug ..
            gl = gl.getContext().setGL( GLPipelineFactory.create("javax.media.opengl.Debug", null, gl, null) ).getGL2();
        } catch (Exception e) {e.printStackTrace();}
		
		gl.glClearColor( 0.0f, 0.0f, 0.0f, 1.0f );
		gl.glColor3f( 1.0f, 1.0f, 1.0f );
		gl.glClearDepth( 1.0f );
		gl.glEnable( GL.GL_DEPTH_TEST );
		gl.glDepthFunc( GL.GL_LEQUAL );
		gl.glEnable( GL.GL_TEXTURE_2D );
		
		// Initialize the texture loader and skybox.
		texture_loader = new TextureLoader( gl );
		
		for ( int i = 0; i < skybox_max_textures; ++i )
			skyboxes[ i ] = new Skybox( texture_loader, skybox_names[ i ] );
		
		current_skybox = skyboxes[ 0 ];
		
		// Initialize the keys.
		for ( int i = 0; i < keys.length; ++i )
			keys[i] = false;
		
		gl.glMatrixMode( GLMatrixFunc.GL_MODELVIEW );
		gl.glLoadIdentity();
		
		
		if (false == Nav.loadModels(gl)) {
			System.out.println("Mercedes not Loaded");
		}
		if (false == Mer2.loadModels(gl)) {
			System.out.println("Mercedes not Loaded");
		}
		
		if (false == Mer.loadModels(gl)) {
			System.out.println("Mercedes not Loaded");
		}
		if (false == Avent2.loadModels(gl)) {
			System.out.println("Mercedes not Loaded");
		}
		if (false == FP.loadModels(gl)) {
			System.out.println("Mercedes not Loaded");
		}
    
    
		if(false == world.initializeWorld(gl)){
			System.out.println("World not Loaded");
		}
	
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_NORMALIZE);
		
		
		gl.glGenTextures(15, texID, 0);
		try {
        	
        	//ground
			BufferedImage aImage = ImageIO.read(new File("C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Textures/track texture resize.jpg"));
			
			ByteBuffer buf = convertImageData(aImage);
			
			
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
		
		TreeBuffer = TGABufferMaker.make("src/Textures/Great Deku Tree.tga");
		
		gl.glBindTexture(GL.GL_TEXTURE_2D, texID[1]);
		
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);

        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, TreeBuffer.getWidth(), TreeBuffer.getHeight(), 
				0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, TreeBuffer.getBuffer());
		
		
		gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
		
		String[] foldNames = new String[10];
		foldNames[0]="C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/ClockNumbers/0.tga";
		foldNames[1]="C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/ClockNumbers/1.tga";
		foldNames[2]="C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/ClockNumbers/2.tga";
		foldNames[3]="C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/ClockNumbers/3.tga";
		foldNames[4]="C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/ClockNumbers/4.tga";
		foldNames[5]="C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/ClockNumbers/5.tga";
		foldNames[6]="C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/ClockNumbers/6.tga";
		foldNames[7]="C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/ClockNumbers/7.tga";
		foldNames[8]="C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/ClockNumbers/8.tga";
		foldNames[9]="C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/ClockNumbers/9.tga";
		
		
		
		
		
		for(int i = 0; i < foldNames.length; i ++){
			TreeBuffer = TGABufferMaker.make(foldNames[i]);
			
			gl.glBindTexture(GL.GL_TEXTURE_2D, texID[i+2]);
			
			gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
	        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);

	        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, TreeBuffer.getWidth(), TreeBuffer.getHeight(), 
					0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, TreeBuffer.getBuffer());
			
			
			gl.glTexEnvf(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
		
		}
		
		try {
        	
        	//ground
			BufferedImage aImage = ImageIO.read(new File("C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/ClockNumbers/Walls.jpg"));
			
			ByteBuffer buf = convertImageData(aImage);
			
			
			gl.glBindTexture(GL.GL_TEXTURE_2D, texID[12]);
			
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
		
		
		lapClock = new Clock();
		//System.out.println("Exiting init");
	}
	
	@Override
	public void reshape( GLAutoDrawable gLDrawable, int x, int y, int width,
			int height ) {
		//System.out.println("Entering reshape0");
		windowWidth = width;
		//System.out.println("Entering reshape1");
		windowHeight = height > 0 ? height : 1;
		//System.out.println("Entering reshape2");
		final GL2 gl = gLDrawable.getGL().getGL2();
		//System.out.println("Entering reshape3");
		gl.glViewport( 0, 0, width, height );
		//System.out.println("Entering reshape4");
		gl.glMatrixMode( GLMatrixFunc.GL_PROJECTION );
		//System.out.println("Entering reshape5");
		gl.glLoadIdentity();
		//System.out.println("Entering reshape6");
		glu.gluPerspective( 60.0f, (float) windowWidth / windowHeight, 0.1f, skybox_size * (float) Math.sqrt( 3.0 ) / 2.0f );
		//System.out.println("Exiting reshape");
		
	}

	public void KeepOutSquare(Driver D, float centerX, float centerY, float sideLength){
		float corner[][] = new float[2][2];
		corner[0][0] = centerX + sideLength/2.0f;
		corner[0][1] = centerY + sideLength/2.0f;
		
		corner[1][0] = centerX - sideLength/2.0f;
		corner[1][1] = centerY - sideLength/2.0f;
		
		
		if(D.position[0] < corner[0][0] && D.position[0] > corner[1][0] && D.position[1] < corner[0][1] && D.position[1] > corner[1][1]){
			int XminOne = (int)Math.abs(D.position[0] - corner[0][0]);
			int XminTwo = (int)Math.abs(D.position[0] - corner[1][0]);
			
			int YminOne = (int)Math.abs(D.position[1] - corner[0][1]);
			int YminTwo = (int)Math.abs(D.position[1] - corner[1][1]);
			
			int minX = Math.min(XminOne,XminTwo);
			int minY = Math.min(YminOne,YminTwo);
			
			int minTot = Math.min(minX, minY);
			
			
			
			
			if(minTot == XminOne){
				D.position[0] = corner[0][0];
			}
			else if (minTot == XminTwo){
				D.position[0] = corner[1][0];
			}
			else if (minTot == YminOne){
				D.position[1] = corner[0][1];
			}
			else if (minTot == YminTwo){
				D.position[1] = corner[1][1];
			}
			else{
			
			}
			
			
		}
		
		
		
		
	}
	
	public void checkPlayerBounds(Driver D){
		float outsideWall = 358;
		if(D.position[0] < -outsideWall)
			D.position[0] = -outsideWall;
		if(D.position[0] > outsideWall)
			D.position[0] = outsideWall;
		if(D.position[1] < -outsideWall)
			D.position[1] = -outsideWall;
		if(D.position[1] > outsideWall)
			D.position[1] = outsideWall;
		
		float distanceBetweenPlots = 222.0f;
		float startingPlotY = distanceBetweenPlots;
		float plotSize = 146.0f;
		for( int i =0; i < 3; i++){
			KeepOutSquare(D, -distanceBetweenPlots, startingPlotY, plotSize);
			KeepOutSquare(D, 0, startingPlotY, plotSize);
			KeepOutSquare(D, distanceBetweenPlots, startingPlotY, plotSize);
			startingPlotY -= distanceBetweenPlots;
		}
		if(D.position[0] != AI1.position[0] && D.position[1] != AI1.position[1])
			KeepOutSquare(D, AI1.position[0], AI1.position[1], 50);
		if(D.position[0] != AI2.position[0] && D.position[1] != AI2.position[1])
			KeepOutSquare(D, AI2.position[0], AI2.position[1], 50);
		if(D.position[0] != AI3.position[0] && D.position[1] != AI3.position[1])
			KeepOutSquare(D, AI3.position[0], AI3.position[1], 50);
		if(D.position[0] != Player1.position[0] && D.position[1] != Player1.position[1])
			KeepOutSquare(D, Player1.position[0], Player1.position[1], 50);
		
		
		
	}
	
	public void drawTree(GL2 gl){
		float pos[]=new float[3],right[]=new float[3],up[]=new float[3];
		
		Vertex v4 = new Vertex(216.6f,0,433.2f);
		Vertex v7 = new Vertex(0,0,216.6f);
		Vertex v10 = new Vertex(-433.2f,0,0);
		Vertex v12 = new Vertex(216.6f,0,0);
		Vertex v13 = new Vertex(433.2f,0,0);
		Vertex v15 = new Vertex(-216.6f,0,-216.6f);
		Vertex v17 = new Vertex(216.6f,0,-216.6f);
		Vertex v19 = new Vertex(-433.2f,0,-433.2f);
		Vertex v23 = new Vertex(433.2f,0,-433.2f);
		
		ArrayList<Vertex> locations = new ArrayList<Vertex>();
		locations.add(v4);
		locations.add(v7);
		locations.add(v10);
		locations.add(v12);
		locations.add(v13);
		locations.add(v15);
		locations.add(v17);
		locations.add(v19);
		locations.add(v23);
		
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL2.GL_ALPHA_TEST);
		//gl.glTexEnvi(GL2.GL_TEXTURE_ENV,GL2.GL_TEXTURE_ENV_MODE,GL2.GL_REPLACE);
		gl.glAlphaFunc(GL.GL_GREATER, 0);
		gl.glEnable(GL.GL_TEXTURE_2D);
		
		gl.glBindTexture(GL.GL_TEXTURE_2D,texID[1]);
		gl.glPushMatrix();
		

			
				


		pos[0] = (float)(5+10.0); pos[1] = 0; pos[2] = (float)(5+10.0);
	
		//l3dBillboardCheatSphericalBegin();
		float modelview[]=new float[16];
		int i1,j1;

		// save the current modelview matrix
		gl.glPushMatrix();
		
		// get the current modelview matrixs
		gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX , modelview,0);

		// undo all rotations
		// beware all scaling is lost as well 
		for( i1=0; i1<3; i1++ ) 
			for( j1=0; j1<3; j1++ ) {
				if ( i1==j1 )
					modelview[i1*4+j1] = 1.0f;
				else
					modelview[i1*4+j1] = 0.0f;
			}

		// set the modelview with no rotations
		gl.glLoadMatrixf(modelview,0);
		
		
		
		gl.glTranslatef(0,-20,0);
		
		
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f(0,0);gl.glVertex3f(-50, 0.0f, 0.0f);
		gl.glTexCoord2f(1,0);gl.glVertex3f(50, 0.0f, 0.0f);
		gl.glTexCoord2f(1,1);gl.glVertex3f(50, 90.0f, 0.0f);
		gl.glTexCoord2f(0,1);gl.glVertex3f(-50, 90.0f,  0.0f);
		gl.glEnd();
		
		
		gl.glPopMatrix();
			//l3dBillboardEnd();
		gl.glPopMatrix();
		
		
		int dispTime = (int)lapClock.LapTime();
		int one = dispTime %10;
		int two = dispTime / 10;
		gl.glPushMatrix();
		

		gl.glBindTexture(GL.GL_TEXTURE_2D,texID[two+2]);
		
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f(0,0);gl.glVertex3f(0.0f, -305.0f, 10.0f);
		gl.glTexCoord2f(1,0);gl.glVertex3f(0.0f, -310.0f, 10.0f);
		gl.glTexCoord2f(1,1);gl.glVertex3f(0.0f, -310.0f, 15.0f);
		gl.glTexCoord2f(0,1);gl.glVertex3f(0.0f, -305.0f, 15.0f);
		gl.glEnd();
		
		
		
		gl.glBindTexture(GL.GL_TEXTURE_2D,texID[one+2]);
		
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f(0,0);gl.glVertex3f(0.0f, -310.0f, 10.0f);
		gl.glTexCoord2f(1,0);gl.glVertex3f(0.0f, -315.0f, 10.0f);
		gl.glTexCoord2f(1,1);gl.glVertex3f(0.0f, -315.0f, 15.0f);
		gl.glTexCoord2f(0,1);gl.glVertex3f(0.0f, -310.0f, 15.0f);
		gl.glEnd();
		
		
		gl.glPopMatrix();
		gl.glPushMatrix();
		gl.glBindTexture(GL.GL_TEXTURE_2D,texID[12]);
		
        gl.glNormal3f(0,1,0);
		gl.glColor3f(1,1,1);
		gl.glBegin(GL2.GL_QUADS);
		
		gl.glTexCoord2f(1f,1f);
		gl.glVertex3f(-500, -500, -3);
		gl.glTexCoord2f(0,1);
		gl.glVertex3f(500,-500,-3);
		gl.glTexCoord2f(0,0);
		gl.glVertex3f(500,-500,17);
		gl.glTexCoord2f(1,0);
		gl.glVertex3f(-500, -500, 17);
		
		gl.glEnd();
		
		gl.glBegin(GL2.GL_QUADS);
		
		gl.glTexCoord2f(1f,1f);
		gl.glVertex3f(500, 500, -3);
		gl.glTexCoord2f(0,1);
		gl.glVertex3f(500,-500,-3);
		gl.glTexCoord2f(0,0);
		gl.glVertex3f(500,-500,17);
		gl.glTexCoord2f(1,0);
		gl.glVertex3f(500, 500, 17);
		
		gl.glEnd();
		
		gl.glBegin(GL2.GL_QUADS);
		
		gl.glTexCoord2f(1f,1f);
		gl.glVertex3f(500, 500, -3);
		gl.glTexCoord2f(0,1);
		gl.glVertex3f(-500,500,-3);
		gl.glTexCoord2f(0,0);
		gl.glVertex3f(-500, 500,17);
		gl.glTexCoord2f(1,0);
		gl.glVertex3f(500, 500, 17);
		
		gl.glEnd();
		
		gl.glTranslatef(-500, 0, 7);
		gl.glRotatef(180, 0, 0, 1);
		gl.glTranslatef(500, 0, -7);
		gl.glBegin(GL2.GL_QUADS);
		
		gl.glTexCoord2f(1f,1f);
		gl.glVertex3f(-500, -500, -3);
		gl.glTexCoord2f(0,1);
		gl.glVertex3f(-500, 500,-3);
		gl.glTexCoord2f(0,0);
		gl.glVertex3f(-500,500,17);
		gl.glTexCoord2f(1,0);
		gl.glVertex3f(-500, -500, 17);
	
		gl.glEnd();
		
		
		
		gl.glPopMatrix();
		
			
		//gl.glBindTexture(GL.GL_TEXTURE_2D,0);
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glDisable(GL.GL_BLEND);
		gl.glDisable(GL2.GL_ALPHA_TEST);
		
		
	}
	
	
	public void turn(){
		if ( keys[KeyEvent.VK_A] || keys[KeyEvent.VK_D] ) {
			float multiplier = Player1.inertia > 0 ? -1.0f : 1.0f;
			/*
			float theta = (float) Math.atan2( scene_look_y, scene_look_x );
			float phi = (float) Math.acos( scene_look_z );
			*/
			if ( keys[KeyEvent.VK_A] ){
				Player1.theta +=0.01*Player1.myCar.handling*multiplier;
				Player1.turn = 45;
			}
			else if ( keys[KeyEvent.VK_D] ){
				Player1.theta -=0.01*Player1.myCar.handling*multiplier;
				Player1.turn = -45;
			}
		}
		else
			Player1.turn = 0;
		
	}
	
	public void drawLapTime(GL2 gl){
		
		
	}
	
	@Override
	public void display( GLAutoDrawable gLDrawable ) {
		final GL2 gl = gLDrawable.getGL().getGL2();
		
		
		gl.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
		
		gl.glMatrixMode( GLMatrixFunc.GL_MODELVIEW );
		String LapTime = Objects.toString(lapClock.LapTime());
		if(Player1.passGo()){
			lapClock.lap();
			//System.out.println(LapTime);
		}
		drawLapTime(gl);
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
         
		
		
		 float[] light1Pos = { 0,0,0,0};        // light position
		 float[] light2Pos = {0,0,500,0};
		 float[] light3Pos = {0,0,-500,0};
		 
		 float[] light4Pos = { 0,0,0,0};        // light position
		 float[] light5Pos = {0,0,500,0};
		 float[] light6Pos = {0,0,-500,0};
		 
	     float[] noAmbient = { 0.5f, 0.2f, 0.2f, 1f };     // low ambient light
	     float[] diffuse = { 1f, 1f, 1f, 1f };        // full diffuse color
	        
	     lapClock.update();
		
		gl.glPushMatrix();
		
		gl.glRotatef(RotX, 1, 0, 0);
		gl.glRotatef(RotZ, 0, 0, 1);
		
		 gl.glEnable(GL2.GL_LIGHT0);
	     gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, noAmbient, 0);
	     gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse, 0);
	     gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION,light1Pos, 0);
	        
	    
	     
	     gl.glEnable(GL2.GL_LIGHT1);
	     gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, noAmbient, 0);
	     gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_DIFFUSE, diffuse, 0);
	     gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION,light2Pos, 0);
	        
	     
	     gl.glEnable(GL2.GL_LIGHT2);
	     gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_AMBIENT, noAmbient, 0);
	     gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_DIFFUSE, diffuse, 0);
	     gl.glLightfv(GL2.GL_LIGHT2, GL2.GL_POSITION,light3Pos, 0);
	     
		
		float throttle_pan = 100.0f;
		
		if(keys[KeyEvent.VK_X]){
			RotX ++;
		}
		if(keys[KeyEvent.VK_Z]){
			RotZ ++;
		}
		
		// Update the camera state.
		if(ControllingCar){
			RotX=0;
			RotZ=0;
			double DEG2RAD = Math.PI/180;
			if ( keys[KeyEvent.VK_W] || keys[KeyEvent.VK_S] ) {
				float multiplier = keys[KeyEvent.VK_S] ? 1.0f : -1.0f;
				Player1.position[0] += (float)Math.cos(Player1.theta) * (float)Player1.myCar.speed * multiplier/350;
				Player1.position[1] += (float)Math.sin(Player1.theta) * (float)Player1.myCar.speed * multiplier/350;
				Player1.inertia += 0.3*multiplier;
				
				turn();
			}
			else if(Player1.inertia != 0)
				turn();
			if(!FPR){
				scene_eye_x = Player1.position[0] + (float) Math.cos(Player1.theta)*35 + Player1.inertia * (float) Math.cos(Player1.theta);
				scene_eye_y = Player1.position[1] + (float) Math.sin(Player1.theta)*35 + Player1.inertia * (float) Math.sin(Player1.theta);
				scene_eye_z = 15;
				
			
				scene_look_x = Player1.position[0] - scene_eye_x;
				scene_look_y = Player1.position[1] - scene_eye_y;
				scene_look_z = -6;
			}
			else{
				
				scene_eye_x = Player1.position[0] - (float) Math.cos(Player1.theta + Math.PI/4)*0.25f + Player1.inertia * (float) Math.cos(Player1.theta);
				scene_eye_y = Player1.position[1] - (float) Math.sin(Player1.theta + Math.PI/4)*0.25f + Player1.inertia * (float) Math.sin(Player1.theta);
				scene_eye_z = 1.1f;
				
			
				scene_look_x = scene_eye_x - Player1.position[0];
				scene_look_y = scene_eye_y - Player1.position[1];
				scene_look_z = 0;
				
			}
			
			
		}
		
		
		else{
			if(scene_look_z == -6){
				scene_look_z = 0;
			}
			throttle_pan = 5.0f;
			if ( keys[KeyEvent.VK_W] || keys[KeyEvent.VK_S] ) {
				float normxy = (float) Math.sqrt( scene_look_x * scene_look_x + scene_look_y * scene_look_y );
				float multiplier = keys[KeyEvent.VK_W] ? 1.0f : -1.0f;
				scene_eye_x += scene_look_x / normxy * throttle_pan * multiplier;
				scene_eye_y += scene_look_y / normxy * throttle_pan * multiplier;
			}
			
			if ( keys[KeyEvent.VK_R] ) {
				scene_eye_z += throttle_pan;
			} else if ( keys[KeyEvent.VK_F] ) {
				scene_eye_z -= throttle_pan;
			}
		
		
			if ( keys[KeyEvent.VK_A] || keys[KeyEvent.VK_D] ) {
				float theta = (float) Math.atan2( scene_look_y, scene_look_x );
				float phi = (float) Math.acos( scene_look_z );
				
				if ( keys[KeyEvent.VK_A] )
					theta += Math.PI / 2.0;
				else if ( keys[KeyEvent.VK_D] )
					theta -= Math.PI / 2.0;
				
				float strafe_x = (float)( Math.cos( theta ) * Math.sin( phi ) );
				float strafe_y = (float)( Math.sin( theta ) * Math.sin( phi ) );
				float normxy = (float) Math.sqrt( strafe_x * strafe_x + strafe_y * strafe_y );
				
				scene_eye_x += strafe_x / normxy * throttle_pan;
				scene_eye_y += strafe_y / normxy * throttle_pan;
			}
		}
		checkPlayerBounds(Player1);
		glu.gluLookAt( scene_eye_x, scene_eye_y, scene_eye_z,
				scene_eye_x + scene_look_x, scene_eye_y + scene_look_y, scene_eye_z + scene_look_z,
				0.0f, 0.0f, 1.0f );
		
		gl.glPushMatrix();
		
		if ( skybox_follow_camera )
			gl.glTranslatef( scene_eye_x, scene_eye_y, scene_eye_z );
		
		current_skybox.draw( gl, skybox_size );
		gl.glPopMatrix();
		
		
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, -3);
		gl.glRotatef(90, 1, 0, 0);
		gl.glScalef(.5f,.5f,.5f);
		gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glBindTexture(GL.GL_TEXTURE_2D, texID[0]);
        gl.glNormal3f(0,1,0);
		gl.glColor3f(1,1,1);
		gl.glBegin(GL2.GL_QUADS);
		gl.glTexCoord2f(0f,0f);
		gl.glVertex3f(-1000, 0, -1000);
		gl.glTexCoord2f(1,0);
		gl.glVertex3f(1000,0,-1000);
		gl.glTexCoord2f(1,1);
		gl.glVertex3f(1000,0,1000);
		gl.glTexCoord2f(0,1);
		gl.glVertex3f(-1000, 0, 1000);
		gl.glEnd();
		gl.glDisable(GL.GL_TEXTURE_2D);
		gl.glPopMatrix();
		
		world.drawWorld(gl);
		
		drawTree(gl);
		
	    //Nav.drawCar(gl,rotate, turn);
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, -3);
		if(FPR && ControllingCar)
			Player1.DrawFP(gl);
		else
			Player1.DrawDriver(gl);
		AI1.DrawDriver(gl);
		checkPlayerBounds(AI1);
		AI2.DrawDriver(gl);
		checkPlayerBounds(AI2);
		AI3.DrawDriver(gl);
		checkPlayerBounds(AI3);
		gl.glPopMatrix();
		/*
		gl.glPushMatrix();
	    gl.glTranslatef(startingposition[1][0],startingposition[1][1],0);
	    Nav.drawCar(gl,0, 0);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
	    gl.glTranslatef(startingposition[2][0],startingposition[2][1], 0);
	    Nav2.drawCar(gl,0, 0);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
	    gl.glTranslatef(startingposition[3][0],startingposition[3][1],0);
	    Avent2.drawCar(gl,0, 0);
		gl.glPopMatrix();
		
		*/
		/*
		gl.glPushMatrix();
	    gl.glTranslatef(-7,0,0);
	    Nav2.draw(gl);
		gl.glPopMatrix();
		*/
		gl.glColor3f(1, 1, 1);
		gl.glEnable(GL.GL_TEXTURE_2D);
		

		
		
		
		
		
		
		gl.glPopMatrix();
	}
	
	void drawPlane( GL2 gl, float size ) {
		final float d = size / 2.0f;
		
		gl.glDisable( GL2.GL_TEXTURE_2D );
		gl.glBegin( GL2.GL_QUADS );
		
		gl.glVertex3f( d, d, 0.0f );
		gl.glVertex3f( -d, d, 0.0f );
		gl.glVertex3f( -d, -d, 0.0f );
		gl.glVertex3f( d, -d, 0.0f );
		
		gl.glEnd();
		gl.glEnable( GL2.GL_TEXTURE_2D );
	}
	
	@Override
	public void dispose( GLAutoDrawable arg0 ) {
	}

	@Override
	public void keyTyped( KeyEvent e ) {
		char key = e.getKeyChar();
		
		
		
		switch ( key ) {
			
			
			case KeyEvent.VK_F:
				skybox_follow_camera = ! skybox_follow_camera;
				break;
			
			
		}
		
		// Change the skybox dynamically.
		if ( key >= '1' && key <= '1' + Math.min( skybox_names.length, skybox_max_textures ) - 1 )
			current_skybox = skyboxes[ key - 0x30 - 1 ];
	}

	@Override
	public void keyPressed( KeyEvent e ) {
		keys[ e.getKeyCode() ] = true;
		
		if ( keys[KeyEvent.VK_E]){
			ControllingCar = !ControllingCar;
			Player1.AI = !Player1.AI; 
		}
		if ( keys[KeyEvent.VK_Q])
			FPR = !FPR;
		
	}
	

	@Override
	public void keyReleased( KeyEvent e ) {
		keys[ e.getKeyCode() ] = false;
		
	}

	@Override
	public void mouseDragged( MouseEvent e ) {

		int x = e.getX();
		int y = e.getY();
		
		final float throttle_rot = 128.0f;
		
		float dx = ( x - mouse_x0 );
		float dy = ( y - mouse_y0 );
		
		if ( MOUSE_MODE_ROTATE == mouse_mode) {
			float phi = (float) Math.acos( scene_look_z );
			float theta = (float) Math.atan2( scene_look_y, scene_look_x );
			
			theta -= dx / throttle_rot;
			phi += dy / throttle_rot;
			
			if ( theta >= Math.PI * 2.0 )
				theta -= Math.PI * 2.0;
			else if ( theta < 0 )
				theta += Math.PI * 2.0;
			
			if ( phi > Math.PI - 0.1 )
				phi = (float)( Math.PI - 0.1 );
			else if ( phi < 0.1f )
				phi = 0.1f;
			
			scene_look_x = (float)( Math.cos( theta ) * Math.sin( phi ) );
			scene_look_y = (float)( Math.sin( theta ) * Math.sin( phi ) );
			scene_look_z = (float)( Math.cos( phi ) );
		}
		
		mouse_x0 = x;
		mouse_y0 = y;
	}
	
	@Override
	public void mouseMoved( MouseEvent e ) {
	}

	@Override
	public void mouseClicked( MouseEvent e ) {
	}

	@Override
	public void mousePressed( MouseEvent e ) {
		mouse_x0 = e.getX();
		mouse_y0 = e.getY();
		
		if ( MouseEvent.BUTTON1 == e.getButton() ) {
			
			mouse_mode = MOUSE_MODE_ROTATE;
		} else {
			mouse_mode = MOUSE_MODE_NONE;
		}
		
	}

	@Override
	public void mouseReleased( MouseEvent e ) {
	}

	@Override
	public void mouseEntered( MouseEvent e ) {
	}

	@Override
	public void mouseExited( MouseEvent e ) {
	}
	private ByteBuffer convertImageData(BufferedImage bufferedImage) {
        ByteBuffer imageBuffer;

        
        DataBuffer buf = bufferedImage.getRaster().getDataBuffer(); 
      
        
        final byte[] data = ((DataBufferByte) buf).getData();

        
        return (ByteBuffer.wrap(data)); 
    }
}