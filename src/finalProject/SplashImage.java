package finalProject;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JWindow; 

class SplashImage extends JWindow implements Runnable {
   private Thread t;
   private String threadName;
   Image img =Toolkit.getDefaultToolkit().getImage("C:/Users/Kalen/Documents/Home/Code Projects/Final Project/src/Textures/SpashImageRename.png");
   ImageIcon icon = new ImageIcon(img);
   
   SplashImage( String name){
       threadName = name;
       System.out.println("Creating Splash Thread" );
   }
   
	public void paint(Graphics g){
		g.drawImage(img, 0, 0, this);
	}
	
	
   public void run() {
      System.out.println("Running Splash Thread");
      try {
    	  	setSize(1600,900);
			setLocationRelativeTo(null);
			setAlwaysOnTop(true);
			setVisible(true);
			Thread.sleep(5000);
			dispose();
         
     } catch (InterruptedException e) {
         System.out.println("Splash Interupted");
     }
     System.out.println("Splash Thread exiting.");
   }
   
   public void start ()
   {
      System.out.println("Starting Splash Thread"  );
      if (t == null)
      {
         t = new Thread (this, threadName);
         t.start ();
      }
   }

}
