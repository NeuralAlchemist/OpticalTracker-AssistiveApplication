package basePack;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

import com.google.gson.Gson;
import com.theeyetribe.client.IGazeListener;
import com.theeyetribe.client.data.GazeData;

import dataPack.FixationSet;
import dataPack.QueueOfFixationSets;
import dataPack.SmoothedEye;

/**
 * Class to receive data from the sensor.<br><p>
 * This class does the following things:<br>
 * 1. Gets data from the sensor asynchronously.<br>
 * 2. Uses the acquired data to determine if it belongs to a fixation or not.<br>
 * 3. Based on the decision from Part 2., decided whether to bundle the current data 
 * 		into a ongoing fixation set, or send the fixation set aquired from the above actions to the queue 
 * 		to be used.<br>
 * As the call is asynchronous, and the class is referenced from the default thread in {@link basePack.startPoint}, technically, 
 * this producer is not a separate thread from the default thread.
 */
public class SensorDataProducer implements IGazeListener {

	private final static Logger logger = Logger.getLogger(SensorDataProducer.class.getName()) ;
	private final static String LogFileLocation = "/Users/sagarshubham/eclipse-java-workspace/"
			+ "eyeTribe/LOGS/SensorDataProducerLogs.xml" ;
	/**
	 * Ongoing fixation set within which current data can be added.
	 */
	private FixationSet currentFixationSet ;
	/**
	 * Queue to which the most recently finished fixation set is to be added.
	 */
	private QueueOfFixationSets queueOfFixationSets ;

	/**
	 * Constructor to initialize the Producer by handing over the Queue.
	 * @param queueOfFixationSets Queue to which fixation sets would be added.
	 */
	public SensorDataProducer(QueueOfFixationSets queueOfFixationSets) {
		super();
		this.queueOfFixationSets = queueOfFixationSets;
		currentFixationSet = null ;
		SensorDataProducer.initializeLoggers();
	}

	private static void initializeLoggers()
	{
		SensorDataProducer.logger.setLevel(Level.ALL);
		ConsoleHandler consoleHandler = new ConsoleHandler() ;
		consoleHandler.setLevel(Level.INFO);
		SensorDataProducer.logger.addHandler(consoleHandler);

		try {
			Handler fileHandler = new FileHandler(LogFileLocation, true) ;
			fileHandler.setFormatter(new XMLFormatter());
			fileHandler.setLevel(Level.ALL);
			SensorDataProducer.logger.addHandler(fileHandler);
		} catch (SecurityException | IOException e) {
			logger.log(Level.SEVERE, "Cannot Create File Logger", e) ;
		}
	}

	/** 
	 * Overridden Method from IGazeListener to get data from the sensor asynchronously.<br><p>
	 * The Method, based on the {@link dataPack.SmoothedEye#isFixated}, 
	 * either adds the data to the {@link basePack.SensorDataProducer#currentFixationSet}, 
	 * or sends the {@link basePack.SensorDataProducer#currentFixationSet} to the Queue.<p><br>
	 * @see com.theeyetribe.client.IGazeListener#onGazeUpdate(com.theeyetribe.client.data.GazeData)
	 */
	@Override
	public void onGazeUpdate(GazeData gazeData) {
		String gazeDataString = new Gson().toJson(gazeData) ;
		logger.fine("Data Collected: "+ gazeDataString);
		int fixationSetID = 0 ;
		if(gazeData.isFixated)
		{
			if(currentFixationSet == null)
			{
				fixationSetID += 1 ;
				currentFixationSet = new FixationSet(gazeData.timeStamp, fixationSetID) ;
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
				currentFixationSet.setCurrentFixationSetFinished(true); 
				this.queueOfFixationSets.addFIFOFixationSet(currentFixationSet);
				currentFixationSet = null ;
			} catch (InterruptedException e) {
				logger.log(Level.WARNING,
						"Issue in sending current fixation set to Queue. ", e);
			}
		}
	}

}
