package finalProject;



public class Clock {
	
	public long start;
	public long lap;
	
	public Clock(){
		start = System.nanoTime();
		lap = System.nanoTime();
	}
	public void lap(){
		start = lap;
	}
	public void update(){
		lap = System.nanoTime();
	}
	public double LapTime(){
		return (lap - start)/1000000000.0;
	}
	
}