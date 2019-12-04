package basePack;

import dataPack.FixationSet;
import dataPack.QueueOfFixationSets;
import dataPack.SmoothedEye;

/**
 * Class to consume data from the sensor.<br><p>
 * The class takes in the fixation sets from the queue and uses them as needed. Thus, it only need 
 * have the handle to the Queue and make use of it.
 */
public class SensorDataConsumer implements Runnable {

	/**
	 * Handle to the queue {@link dataPack.QueueOfFixationSets}
	 */
	private QueueOfFixationSets queueOfFixationSets ;

	/**
	 * Constructor to assign the queue for use.
	 * @param queueOfFixationSets handle to the queue to be used for fixation sets.
	 */
	public SensorDataConsumer(QueueOfFixationSets queueOfFixationSets) {
		this.queueOfFixationSets = queueOfFixationSets ;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		int fixationSetNumber = 0 ;

		while(true)
		{
			try {
				FixationSet fixationSet = queueOfFixationSets.getFIFOFixationSet();
				System.out.println("FixationSet Number: "+ ++fixationSetNumber) ;
				System.out.println("FixationPoints in Current Set: ");
				for(SmoothedEye fs : fixationSet.getEyeCoordinatesSet())
				{
					System.out.println("\t\tX: "+fs.getSmoothedEyeCoordinate().getX()+
							"\tY: "+fs.getSmoothedEyeCoordinate().getY());
				}
				System.out.println("Mean Position: "+ fixationSet.getMeanEyeCoordinate().toString());
				System.out.println("Top Left Position: " + fixationSet.getTopLeftCornerOfFixation().toString());
				System.out.println("Bottom Right Position: "+ fixationSet.getBottomRightCornerOfFixation().toString());


			} catch (InterruptedException e) {
				System.out.println("Could Not receive fixations set from the list!");
			}
		}

	}

}
