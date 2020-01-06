package basePack;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

import com.theeyetribe.client.IGazeListener;
import com.theeyetribe.client.data.GazeData;

import dataPack.QueueOfSmoothedEyeCoordinates;
import dataPack.SmoothedEye;

public class SensorEyeCoordinatesProducer implements IGazeListener {

	private final static Logger logger = Logger.getLogger(SensorEyeCoordinatesProducer.class.getName()) ;
	private final static String LogFileLocation = "/Users/sagarshubham/eclipse-java-workspace/"
			+ "eyeTribe/LOGS/SensorEyeCoordinatesProducerLogs.xml" ;

	private QueueOfSmoothedEyeCoordinates queueOfSmoothedEyeCoordinates ;

	public SensorEyeCoordinatesProducer(QueueOfSmoothedEyeCoordinates queueOfSmoothedEyeCoordinates) {
		this.queueOfSmoothedEyeCoordinates = queueOfSmoothedEyeCoordinates ;
		SensorEyeCoordinatesProducer.initializeLoggers();
	}


	private static void initializeLoggers()
	{
		SensorEyeCoordinatesProducer.logger.setLevel(Level.ALL);
		ConsoleHandler consoleHandler = new ConsoleHandler() ;
		consoleHandler.setLevel(Level.INFO);
		SensorEyeCoordinatesProducer.logger.addHandler(consoleHandler);

		try {
			Handler fileHandler = new FileHandler(LogFileLocation, true) ;
			fileHandler.setFormatter(new XMLFormatter());
			fileHandler.setLevel(Level.ALL);
			SensorEyeCoordinatesProducer.logger.addHandler(fileHandler);
		} catch (SecurityException | IOException e) {
			logger.log(Level.SEVERE, "Cannot Create File Logger", e) ;
		}
	}



	@Override
	public void onGazeUpdate(GazeData gazeData) {
		SmoothedEye smoothedEyeCoordinate = new SmoothedEye(gazeData.smoothedCoordinates.x,
				gazeData.smoothedCoordinates.y,
				gazeData.isFixated,
				gazeData.state,
				gazeData.timeStamp) ;
		try {
			this.queueOfSmoothedEyeCoordinates.addFIFOSmoothedEyeCoordinate(smoothedEyeCoordinate);
		} catch (InterruptedException e) {
			logger.log(Level.WARNING,
					"Issue in sending current fixation set to Queue. ", e);
		}

	}

}
