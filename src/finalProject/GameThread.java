package finalProject;

class GameThread implements Runnable {
   private Thread t;
   private String threadName;
   
   GameThread( String name){
       threadName = name;
       System.out.println("Creating Game Thread");
   }
   public void run() {
      System.out.println("Running Game Thread" );
      try {
            Thread.sleep(1000);
            Main m = new Main();
    		m.setVisible( true );
        
     } catch (InterruptedException e) {
         System.out.println("Game Interupted");
     }
     System.out.println("Game Thread exiting.");
   }
   
   public void start ()
   {
      System.out.println("Starting Game Thread" );
      if (t == null)
      {
         t = new Thread (this, threadName);
         t.start ();
      }
   }

}
