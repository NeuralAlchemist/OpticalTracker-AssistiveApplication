package basePack;

import com.theeyetribe.client.IGazeListener;
import com.theeyetribe.client.data.GazeData;

import dataPack.FixationSet;
import dataPack.ListOfFixationSets;
import dataPack.SmoothedEye;

public class SensorDataProducer implements IGazeListener {

	private FixationSet currentFixationSet ;
	private ListOfFixationSets listOfFixationSets ;
	boolean isNewDataReadyToPushToList ;

	public SensorDataProducer(ListOfFixationSets listOfFixationSets) {
		super();
		this.listOfFixationSets = listOfFixationSets;
		currentFixationSet = null ;
		isNewDataReadyToPushToList = false;
	}

	@Override
	public void onGazeUpdate(GazeData gazeData) {
		if(gazeData.isFixated)
		{
			if(currentFixationSet == null)
			{
				currentFixationSet = new FixationSet(gazeData.timeStamp) ;
			}
			SmoothedEye newSmootherEye = new SmoothedEye(gazeData.smoothedCoordinates.x,
					gazeData.smoothedCoordinates.y,
					gazeData.isFixated,
					gazeData.state,
					gazeData.timeStamp) ;
			currentFixationSet.addEyeCoordinatesSet(newSmootherEye);
		}
		else if (currentFixationSet != null)
		{
			try {
				listOfFixationSets.addFIFOFixationSet(currentFixationSet);
				currentFixationSet = null ;
			} catch (InterruptedException e) {
				System.out.println("Could Not add current fixation set to list!");
			}
		}
	}

}
