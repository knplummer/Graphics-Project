package finalProject;

import java.awt.BorderLayout;
import java.awt.Frame;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLCanvas; 

import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;





public class testWindow extends Frame implements Runnable{

	static Animator anim = null;
	static FPSAnimator animator = new FPSAnimator(60);
	private void setupJOGL(){
	    GLCapabilities caps = new GLCapabilities(null);
	    caps.setDoubleBuffered(true);
	    caps.setHardwareAccelerated(true);
	    
	    GLCanvas canvas = new GLCanvas(caps); 
        add(canvas);

        testCanvas jgl = new testCanvas();
        canvas.addGLEventListener(jgl); 
        canvas.addKeyListener(jgl); 
        canvas.addMouseListener(jgl);
        canvas.addMouseMotionListener(jgl);

        //anim = new Animator(canvas);
       //anim.start();
        animator.add(canvas);
        animator.start();

	}
	
    public testWindow() {
        super("Final Project Test Window 60fps");

        setLayout(new BorderLayout());
        
        
        
     
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
        setExtendedState(Frame.MAXIMIZED_BOTH); //maximize window
        setSize(600, 600);
        setLocation(40, 40);

        setVisible(true);

        setupJOGL();
    }

    public static void main(String[] args) {
    	
    	
    	
    	
 
        testWindow demo = new testWindow();
        

        demo.setVisible(true);
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}


class MyWin extends WindowAdapter {
	 public void windowClosing(WindowEvent e)
   {
       System.exit(0);
   }
	 
}


