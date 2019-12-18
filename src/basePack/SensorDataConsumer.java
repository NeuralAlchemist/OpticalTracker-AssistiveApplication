package basePack;

import dataPack.FixationSet;
import dataPack.QueueOfFixationSets;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.google.gson.Gson;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.lang.Math;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

import dataPack.GazeBox;
import commandPack.Commands;

/**
 * Class to consume data from the sensor.<br><p>
 * The class takes in the fixation sets from the queue and uses them. Thus, it only need 
 * have the handle to the Queue and make use of it. This class will send commands to the 
 * webserver to control the scroll actions.
 */
public class SensorDataConsumer implements Runnable {


	/**
	 *  Set up file locations for storing screenshot image 
	 */
	private final static String dirname = "/Users/sagarshubham/eclipse-java-workspace/eyeTribe/IMAGES";
	private final static String image_path = dirname + "capture.jpg";
	private final static Logger logger = Logger.getLogger(SensorDataConsumer.class.getName()) ;
	private final static String LogFileLocation = "/Users/sagarshubham/eclipse-java-workspace/"
			+ "eyeTribe/LOGS/SensorDataConsumerLogs.xml" ;

	/**
	 * Handle to the queue {@link dataPack.QueueOfFixationSets}
	 */
	private QueueOfFixationSets queueOfFixationSets ;

	/**
	 * Constructor to assign the queue for use.
	 * @param queueOfFixationSets handle to the queue to be used for fixation sets.
	 */

	private static BufferedImage snip;
	private static File capture;
	private ArrayList<GazeBox> buttonsList ;

	public SensorDataConsumer(QueueOfFixationSets queueOfFixationSets) {
		super() ;
		this.queueOfFixationSets = queueOfFixationSets ;
		SensorDataConsumer.initializeLoggerAndSnips() ;
	}

	public SensorDataConsumer(QueueOfFixationSets queueOfFixationSets, ArrayList<GazeBox> buttonList) {
		super() ;
		this.queueOfFixationSets = queueOfFixationSets ;
		SensorDataConsumer.initializeLoggerAndSnips() ;
		this.buttonsList = buttonList ;
	}


	private static void initializeLoggerAndSnips() {
		SensorDataConsumer.logger.setLevel(Level.ALL);
		ConsoleHandler consoleHandler = new ConsoleHandler() ;
		consoleHandler.setLevel(Level.INFO);
		SensorDataConsumer.logger.addHandler(consoleHandler);

		try {
			Handler fileHandler = new FileHandler(LogFileLocation, true) ;
			fileHandler.setFormatter(new XMLFormatter());
			fileHandler.setLevel(Level.ALL);
			SensorDataConsumer.logger.addHandler(fileHandler);
		} catch (SecurityException | IOException e) {
			logger.log(Level.SEVERE, "Cannot Create File Logger", e) ;
		}

		/**
		 *  Get screenshot
		 */
		try {
			Rectangle screensnip = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			snip = new Robot().createScreenCapture(screensnip);
			capture = new File(image_path);

			//TODO: Check if this is necessary.
			TimeUnit.SECONDS.sleep(1);

		} catch (IllegalArgumentException | AWTException | NullPointerException | InterruptedException e) {
			logger.log(Level.SEVERE, "Cannot Snip Snap!", e) ;
		}
	}


	/**
	 *
	 */
	@Override
	public void run() {
		// Can change while loop condition to other parameters
		Gson gs = new Gson() ;

		while(true)
		{
			try 
			{
				FixationSet fixationSet = queueOfFixationSets.getFIFOFixationSet();
				logger.log(Level.FINE, fixationSet.getFixationSetID() + " "+ fixationSet.getMeanEyeCoordinate());
				createFixationBox(fixationSet) ;

				for(GazeBox gb : this.buttonsList)
				{
					if(gb.CheckGazeInBox(fixationSet.getMeanEyeCoordinate(), 
							fixationSet.getStartTimeStamp(), fixationSet.getStopTimeStamp()) ) 
					{
						Commands c = gb.getBoxCommand() ;
						String commandS = gs.toJson(c) ;
						logger.log(Level.INFO, "Sent Command: "+commandS);
					}
				}
			}
			catch (Exception e)
			{

			}
		}
	}


	private void createFixationBox(FixationSet fixationSet) {
		double topLeftX = Math.abs(fixationSet.getTopLeftCornerOfFixation().getX());
		double topLeftY = Math.abs(fixationSet.getTopLeftCornerOfFixation().getY());

		double bottomRightX =  Math.abs(fixationSet.getBottomRightCornerOfFixation().getX());
		double bottomRightY = Math.abs(fixationSet.getBottomRightCornerOfFixation().getY());

		double meanX = Math.abs(fixationSet.getMeanEyeCoordinate().getX());
		double meanY = Math.abs(fixationSet.getMeanEyeCoordinate().getY());


		int width = (int) Math.abs(topLeftX - bottomRightX);
		int height = (int) Math.abs(topLeftY - bottomRightY);


		int[] rgb_arr_width = colorArray(width);
		int[] rgb_arr_height = colorArray(height);
		int[] rgb_arr_mean = colorArray(100);


		try {
			snip.setRGB((int)meanX, (int)meanY, 10, 10, rgb_arr_mean, 0, 10);
			snip.setRGB((int)topLeftX, (int)topLeftY, width, 1, rgb_arr_width, 0, width); // top-line
			snip.setRGB((int)topLeftX, (int)bottomRightY, width, 1, rgb_arr_width, 0, width); // bottom-line
			snip.setRGB((int)topLeftX, (int)topLeftY, 1, height, rgb_arr_height, 0, 1); // left-line
			snip.setRGB((int)bottomRightX, (int)topLeftY, 1, height, rgb_arr_height, 0, 1); // right-line
			ImageIO.write(snip, "jpg", capture);
		} catch (ArrayIndexOutOfBoundsException | IOException e) {
			logger.log(Level.WARNING, "Out of bonds probably to draw box." 
					+ fixationSet.getTopLeftCornerOfFixation() + " "
					+ fixationSet.getBottomRightCornerOfFixation());

		}
	}



	private static int[] colorArray(int size) {
		int[] rgb_arr = new int[size];
		for(int i = 0; i < rgb_arr.length; i++) {
			rgb_arr[i] = Color.MAGENTA.getRGB();
		}
		return rgb_arr;
	}

}