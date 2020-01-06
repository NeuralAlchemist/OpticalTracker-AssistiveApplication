package basePack;



import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.theeyetribe.client.GazeManager;
import com.theeyetribe.client.GazeManager.ApiVersion;
import com.theeyetribe.client.GazeManager.ClientMode;

import commandPack.JsonToGazeBoxList;
import dataPack.GazeBox;
import dataPack.QueueOfFixationSets;
import dataPack.QueueOfSmoothedEyeCoordinates;
import userPack.MakeUserProfile;
import userPack.User;



/**
 * 
 * Starting Point in the code which is the default thread.
 * From here, we do the following:<p>
 * 1. Get {@link com.theeyetribe.client.GazeManager} handler.<br>
 * 2. Check for its connection status.<br>
 * 3. Get a {@link dataPack.QueueOfFixationSets} handle.<br>
 * 4. Get {@link basePack.SensorDataProducer} handle by passing it handle from Part 3.<br>
 * 5. Get {@link basePack.SensorDataConsumer} handle by passing it handle from Part 3.<br>
 * 6. Make thread from the handle in Part 5. <br>
 * 7. Get {@link }
 * 8. Add a shutdown hook for the handle in Part 1.<br>
 * @version 1.0
 * 
 */
public class startPoint {

	private final static String LogFileLocation = "/Users/sagarshubham/eclipse-java-workspace/"
			+ "eyeTribe/LOGS/MainLogs.xml" ;
	private static Logger logger  ;
	private static int QueueOfFixationSetsDepth = 25 ;
	private static int QueueOfSmoothedEyeCoordinatesSize = 400 ;


	public static void main(String[] args) {

		//		JFrame frame = new JFrame();
		//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//		frame.setSize(300, 300);
		//		frame.setLocation(1000, 200);
		//		frame.setVisible(true);
		//		
		//		
		//		JFrame frame1 = new JFrame();
		//		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//		frame1.setSize(300, 300);
		//		frame1.setLocation(1000, 600);
		//		frame1.setVisible(true);
		//		
		createLoggers() ;
		
//		final GazeManager gm = GazeManager.getInstance();
//		boolean success = gm.activate(ApiVersion.VERSION_1_0, ClientMode.PUSH);
//
//		if (success)
//		{
//			logger.info("Successfully Connected to Eye Tribe Sensor.");
//		}
//		else
//		{
//			logger.severe("Could not connect to Eye Tribe Sensor!\n"
//					+ "Check Physical Connections And Restart Code.");
//			while(true) ;
//		}




//		QueueOfFixationSets queueOfFixationSets = new QueueOfFixationSets(QueueOfFixationSetsDepth) ;
//
//
//		final SensorDataProducer gazeProuducerListener = new SensorDataProducer(queueOfFixationSets) ;
//		gm.addGazeListener(gazeProuducerListener);
		
		QueueOfSmoothedEyeCoordinates queueOfSmoothedEyeCoordinates = 
				new QueueOfSmoothedEyeCoordinates(QueueOfSmoothedEyeCoordinatesSize) ;
		

		User user =null ;
		try 
		{
			MakeUserProfile mup = new MakeUserProfile(queueOfSmoothedEyeCoordinates) ;
			user =mup.makeUserProfile();
		} 
		catch (IOException e) {
			logger.log(Level.SEVERE, "We either can't find Json file to load button data, or can't close it after use.\n"
					+ "Place it in correct directory and restart the code. ",e);
			while(true) ;
		}
		catch (InterruptedException e)
		{
			logger.log(Level.SEVERE, "Sleeping Error in reading from GUI!. Halting process.", e);
			while(true) ;
		}
		catch (AWTException e)
		{
			logger.log(Level.SEVERE, "AWT Error in reading from GUI! due to Robot Class. Halting process.", e);
			while(true) ;

		}


		//		System.out.println(user);
		//
		//
		

		//
		//		ArrayList<GazeBox> gazeBoxList = null;
		//
		//
		//		try 
		//		{
		//			gazeBoxList = JsonToGazeBoxList.getGazeBoxList();
		//		} 
		//		catch (IOException e) 
		//		{
		//			logger.log(Level.SEVERE, "We either can't find Json file to load button data, or can't close it after use.\n"
		//					+ "Place it in correct directory and restart the code. ",e) ;
		//			while(true) ;
		//		}
		//
		//		SensorDataConsumer sensorDataConsumer = new SensorDataConsumer(queueOfFixationSets,gazeBoxList) ;
		//
		//		Thread consumerThread = new Thread(sensorDataConsumer) ;
		//		consumerThread.start();
		//
		//
		//		Runtime.getRuntime().addShutdownHook(new Thread()
		//		{
		//			@Override
		//			public void run()
		//			{
		//				gm.removeGazeListener(gazeProuducerListener);
		//				gm.deactivate();
		//			}
		//		});

	}

	private static void createLoggers()
	{
		LogManager logManager = LogManager.getLogManager() ;
		logManager.reset();
		logger = logManager.getLogger(Logger.GLOBAL_LOGGER_NAME) ;
		logger.setLevel(Level.ALL);

		ConsoleHandler consoleHandler = new ConsoleHandler() ;
		consoleHandler.setLevel(Level.INFO); // Only Info and Higher will be logged.
		logger.addHandler(consoleHandler);

		try {
			Handler fh = new FileHandler(LogFileLocation,true);
			fh.setLevel(Level.ALL);
			fh.setFormatter(new XMLFormatter());
			logger.addHandler(fh);
		} catch (SecurityException | IOException e) {
			logger.log(Level.SEVERE,"Cannot Create File Logger!",e) ;
		}
	}


}
