package basePack;

import dataPack.FixationSet;
import dataPack.ListOfFixationSets;
import dataPack.SmoothedEye;

public class SensorDataConsumer implements Runnable {

	private ListOfFixationSets listOfFixationSets ;

	public SensorDataConsumer(ListOfFixationSets listOfFixationSets) {
		this.listOfFixationSets = listOfFixationSets ;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		int fixationSetNumber = 0 ;

		while(true)
		{
			try {
				FixationSet fixationSet = listOfFixationSets.getFIFOFixationSet();
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
