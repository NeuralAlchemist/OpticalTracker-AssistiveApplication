package basePack;

import dataPack.FixationSet;
import dataPack.QueueOfFixationSets;
import dataPack.SmoothedEye;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
	
	private BufferedImage snip;
	private File capture;
	
	private int[] bt_topl, bt_topr, bt_botr, bt_botl;
	
	public SensorDataConsumer(QueueOfFixationSets queueOfFixationSets) {
		this.queueOfFixationSets = queueOfFixationSets ;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		int fixationSetNumber = 0 ;
		
		String filename = "fixations.txt";
		String dirname = "D:\\Google Drive\\UU\\M2S1\\ECP\\JavaWrk\\";
		String log_path = (dirname + filename);
		String image_path = dirname + "capture.jpg";
		
		Date date = new Date();
		long cur_milli = date.getTime();
		long start_milli = date.getTime();
		long duration = 500;
		
		bt_topl = new int[]{1460,160};
		bt_topr = new int[] {1740,160};
		bt_botr = new int[] {1740,420};
		//bt_botl = new int[] {1460,420};
		
		// Writing the x and y values into a file.
		initFile(log_path);
		
		// Get screenshot
		try {
			snip = getSnip(1);
			capture = new File(image_path);
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		//while((cur_milli - start_milli < duration))
		while(true)
		{
			Integer[] tl, br, mean;
			int width, height;
			Date cur_date = new Date();
			cur_milli = cur_date.getTime();
			//System.out.println("Current Time" + cur_milli);
			//System.out.println("Started at :" + start_milli);
			try {
				FixationSet fixationSet = queueOfFixationSets.getFIFOFixationSet();
				//System.out.println("FixationSet Number: "+ ++fixationSetNumber) ;
				//System.out.println("FixationPoints in Current Set: ");
				for(SmoothedEye fs : fixationSet.getEyeCoordinatesSet())
				{
					String content = "\n "+fs.getSmoothedEyeCoordinate().getX()+"\t "+fs.getSmoothedEyeCoordinate().getY();
					try {
						Path path = Paths.get(log_path);
						Files.write(path, content.getBytes(), StandardOpenOption.APPEND);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				String tl_text = fixationSet.getTopLeftCornerOfFixation().toString();
				String br_text = fixationSet.getBottomRightCornerOfFixation().toString();
				String mean_text = fixationSet.getMeanEyeCoordinate().toString();
				tl= getCoordinates(tl_text);
				br = getCoordinates(br_text);
				mean = getCoordinates(mean_text);
				
				width = Math.abs(tl[0] - br[0]);
				height = Math.abs(tl[1] - br[1]);
				
				
				int[] rgb_arr_width = colorArray(width);
				int[] rgb_arr_height = colorArray(height);
				int[] rgb_arr_mean = colorArray(100);
				
				snip.setRGB(mean[0], mean[1], 10, 10, rgb_arr_mean, 0, 10);
				
				snip.setRGB(tl[0], tl[1], width, 1, rgb_arr_width, 0, width); // top-line
				snip.setRGB(tl[0], br[1], width, 1, rgb_arr_width, 0, width); // bottom-line
				snip.setRGB(tl[0], tl[1], 1, height, rgb_arr_height, 0, 1); // left-line
				snip.setRGB(br[0], tl[1], 1, height, rgb_arr_height, 0, 1); // right-line
				
				// Draw rectangular fixation blocks 
				ImageIO.write(snip, "jpg", capture);
				
				//Check if inside the button
				if (mean[0] < bt_topl[0] && mean[0] < bt_topr[0]) {
					if(mean[1] > bt_topl[1] && mean[1] < bt_botr[1]) {
						cur_milli = cur_date.getTime();
						if(cur_milli - start_milli < duration) {
							System.out.println("Send command, scroll up");
						}
					}
					
				}
				else {
					start_milli = cur_date.getTime();
				}
				
				// Listen
				System.out.println("Top Left Position: " + tl[0] + "," + tl[1]);
				System.out.println("Bottom Right Position: "+ br[0] + "," + br[1]);

			} catch (InterruptedException e) {
				System.out.println("Could Not receive fixations set from the list!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Draw the image 
		//System.out.println("Stopping program");
		//System.exit(0);
	}
	
	
	// Supporting methods
	public static Integer[] getCoordinates(String s) {
		Integer[] result = new Integer[2];
		String[] split = s.split("\\[");
		String[] split2 = split[1].split("\\]");
		String[] split3 = split2[0].split(",");
		String[] x = split3[0].split("=");
		result[0] = (int) Math.ceil(Double.parseDouble(x[1]));
		String[] y = split3[1].split("=");
		result[1] = (int)Math.ceil(Double.parseDouble(y[1]));
		return result;
	}
	
	// Creates a file at specified path
	public static File initFile(String path_text) {
		File f = new File(path_text);
		try {
			if(f.createNewFile()) {
				System.out.println("File created at " + f.getPath());
				String content = "X \t\t Y";
				Files.write(Paths.get(path_text), content.getBytes(), StandardOpenOption.APPEND);
			} else if(f.delete()) {
				System.out.println("Deleted old file.");
				initFile(path_text);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return f;
		
	}
	
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
