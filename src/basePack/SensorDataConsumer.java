package basePack;

import dataPack.FixationSet;
import dataPack.QueueOfFixationSets;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.lang.Math;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import basePack.GazeBox;

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
	
	private BufferedImage snip;
	private File capture;	
	private int[] up_topl, up_botr, down_topl, down_botr;

	
	/**
	 *
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		/**
		 *  Set up file locations for storing screenshot image 
		 */
		String dirname = "D:\\Google Drive\\UU\\M2S1\\ECP\\JavaWrk\\";
		String image_path = dirname + "capture.jpg";
		
		/**
		 *  Variables for checking gaze duration within button areas
		 */
		Date date = new Date();
		long cur_milli = date.getTime();
		long start_milli = date.getTime();
		long duration = 500;
		boolean prev_in_box = false;
		
		/**
		 * Prototype button corners
		 */
		up_topl = new int[]{1500,200};
		up_botr = new int[] {1800,500};
		
		down_topl = new int[] {1500,600};
		down_botr = new int[] {1800, 900};
		
		GazeBox ScrollUp = new GazeBox(up_topl, up_botr, start_milli, duration);
		GazeBox ScrollDown = new GazeBox(down_topl, down_botr, start_milli, duration);
		/**
		 *  Get screenshot
		 */
		try {
			snip = getSnip(1);
			capture = new File(image_path);
		
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	   // Can change while loop condition to other parameters
		
		while(true)
		{
			int [] tl = new int[2];
			int [] br = new int[2];
			int [] mean = new int[2];
			boolean up,down = false;
			int width, height;
			Date cur_date = new Date();
			cur_milli = cur_date.getTime();
			try {
				FixationSet fixationSet = queueOfFixationSets.getFIFOFixationSet();
				
				tl[0] = (int) Math.abs(fixationSet.getTopLeftCornerOfFixation().getX());
				tl[1] = (int)Math.abs(fixationSet.getTopLeftCornerOfFixation().getY());
				
				br[0] = (int)Math.abs(fixationSet.getBottomRightCornerOfFixation().getX());
				br[1] = (int)Math.abs(fixationSet.getBottomRightCornerOfFixation().getY());
				
				mean[0] = (int)Math.abs(fixationSet.getMeanEyeCoordinate().getX());
				mean[1] = (int)Math.abs(fixationSet.getMeanEyeCoordinate().getY());
				
				
				width = Math.abs(tl[0] - br[0]);
				height = Math.abs(tl[1] - br[1]);
				
				
				int[] rgb_arr_width = colorArray(width);
				int[] rgb_arr_height = colorArray(height);
				int[] rgb_arr_mean = colorArray(100);
				
				
				try {
					snip.setRGB(mean[0], mean[1], 10, 10, rgb_arr_mean, 0, 10);
					
					snip.setRGB(tl[0], tl[1], width, 1, rgb_arr_width, 0, width); // top-line
					snip.setRGB(tl[0], br[1], width, 1, rgb_arr_width, 0, width); // bottom-line
					snip.setRGB(tl[0], tl[1], 1, height, rgb_arr_height, 0, 1); // left-line
					snip.setRGB(br[0], tl[1], 1, height, rgb_arr_height, 0, 1); // right-line
				} catch (Exception e) {
					System.out.println("out of bonds probably");
	
				}
				
				// Draw rectangular fixation blocks 
				ImageIO.write(snip, "jpg", capture);
				up = ScrollUp.CheckGazeInBox(mean, fixationSet);
				down = ScrollDown.CheckGazeInBox(mean, fixationSet);
				if (up) {
					System.out.println("Scrolling up");
				}
				if(down) {
					System.out.println("Scrolling Down");
				}
			} catch (InterruptedException e) {
				System.out.println("Could Not receive fixations set from the list!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

		
	// Supporting methods
	
	
	// Capture a screenshot after X seconds
	public static BufferedImage getSnip(long timeout) throws AWTException {
		Rectangle screensnip = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage snip = new Robot().createScreenCapture(screensnip);
		
		try {
			TimeUnit.SECONDS.sleep(timeout);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return snip;
	}
	
	public static int[] colorArray(int size) {
		int[] rgb_arr = new int[size];
		for(int i = 0; i < rgb_arr.length; i++) {
			rgb_arr[i] = Color.MAGENTA.getRGB();
		}
		return rgb_arr;
	}
	
}
