package dataPack;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class QueueOfSmoothedEyeCoordinates {
	
	private BlockingQueue<SmoothedEye> queueOfSmoothedEyeCoordinates ;
	
	
	public QueueOfSmoothedEyeCoordinates(int sizeOfQueue)
	{
		super() ;
		this.queueOfSmoothedEyeCoordinates = new ArrayBlockingQueue<SmoothedEye>(sizeOfQueue) ;
	}
	
	
	public void addFIFOSmoothedEyeCoordinate(SmoothedEye smoothedEyeCoordinate) throws InterruptedException
	{
		this.queueOfSmoothedEyeCoordinates.put(smoothedEyeCoordinate);
	}



	public SmoothedEye getSmoothedEyeCoordinate() throws InterruptedException
	{
		return this.queueOfSmoothedEyeCoordinates.take() ;
	}
	
	

}
