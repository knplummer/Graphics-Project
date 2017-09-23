package finalProject;

import java.awt.Frame;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLCanvas;
import javax.swing.JFrame;

import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;

public class Main extends JFrame {
	static private FPSAnimator animator = new FPSAnimator(60);
	
	public Main() {
		super( "335 Racing" );
		
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setSize( 640, 480 );
		setVisible( true );
		
		setupJOGL();
	}
	
	public static void main( String[] args ) {
		
		GameThread T2 = new GameThread("Game Thread");
		T2.start();
		
		SplashImage T1 = new SplashImage("SplashImage");
		T1.start();
		
		
	}
	
	private void setupJOGL() {
		GLCapabilities caps = new GLCapabilities( null );
		caps.setDoubleBuffered( true );
		caps.setHardwareAccelerated( true );
		
		GLCanvas canvas = new GLCanvas( caps ); 
		add( canvas );
		
		JoglEventListener jgl = new JoglEventListener();
		canvas.addGLEventListener( jgl ); 
		canvas.addKeyListener( jgl ); 
		canvas.addMouseListener( jgl );
		canvas.addMouseMotionListener( jgl );
		
		animator.add( canvas );
		animator.start();
	}
	
}

class mainThread extends Thread{
	
	private Thread mainThread;
	
	mainThread(){
		System.out.println("Creating Main Thread");
		mainThread = new Thread();
	}

	@Override
	public void run() {
		System.out.println("Running Main Thread");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Main Thread Finished");
	}
	public void start(){
		System.out.println("Starting Main Thread");
		mainThread.start();
		
	}
	
}
