package sandbox;


import java.util.Timer;
import java.util.TimerTask;  

public class LogFile {
	public static void main(String[] args){
	boolean out;	
	while(true) {
		out = countDown(5);
		System.out.println(out);
	}		
		
		
	}
	
	public static boolean countDown(int count) {
		Timer timer = new Timer();
		timer.schedule(new Task(),count*1000);
	}
	
	class Task extends TimerTask{
		public void run() {
			System.out.println("Finished");
			timer.cancel();
		}
	}
}


