package basePack;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

public class ReadDetector {
	private static String path_text = "ReadTextFolder/Read.txt";
	private static String image_text = "ReadCaptureJPGFolder/ReadCapture.jpg";
	private BufferedImage snip;
	private File capture;
	private int[] rgb_arr_read;
	private int[] rgb_arr_not_read;
	
	private int count = 0;
	private final int countMax = 3;
	
	private double prev_x = 0;
	private double prev_y = 0;
	private double x;
	private double y;
	
	
	private Integer points = 0;
	
	private final int shortRightX = 10;
	private final int shortLeftX  = -10;
	private final int mediumRightX = 5;
	private final int mediumLeftX  = -5;
	private final int shortUpY = -5;
	private final int shortDownY = 0;
	private final int mediumDownY = -5;
	
	private final int paddingDist = 5;
	private final int shortDist = 125;
	private final int mediumDist = shortDist * 2;
	//private final int longDist = shortDist * 4;
	
	/* Medium Up, Long right and left = reset */
	private final int readStateSize = 10;
	private final int coordinateSize = 10;
	private final double thresholdRatio = 0.5;
	private final int thresholdCoordY = 700;
	private final int thresholdTime = 5000;
	private int[] rgb_arr_scroll;
	private boolean[] readStates = new boolean[readStateSize];
	private double[] coordinates = new double[coordinateSize];
	private int stateCount = 0;
	private int coordIndex = 0;
	
	private long lastScrollTime = 0;
	
	
	private double ScreenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight() ;
	private double scrollFactor = 5.5 ;
	private JavaToPynputCommandSender jpcs ;
	
	public ReadDetector() {	
		this.initFile();
		//System.out.println("Initializing");
		try {
			snip = getSnip(1);
			capture = new File(image_text);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		rgb_arr_read = colorArray(100, Color.BLUE.getRGB());
		rgb_arr_not_read = colorArray(100, Color.RED.getRGB());
		rgb_arr_scroll = colorArray(100, Color.GREEN.getRGB());
		this.jpcs = new JavaToPynputCommandSender() ;
	}
	public Integer getPoints() {
		return points;
	} 
	
	public boolean update(double xIn, double yIn){
		count  += 1;
		x += xIn;
		y += yIn;
		
		//System.out.println(count);
		if(count == countMax) {
			x = x/countMax;
			y = y/countMax;
			updatePoints();
			count = 0;
			
			
			if(points >= 30) {
				snip.setRGB((int)x, (int)y, 10, 10, rgb_arr_read, 0, 10);
				try {
					ImageIO.write(snip, "jpg", capture);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				readStates[stateCount] = true;
			}
			else {
				snip.setRGB((int)x, (int)y, 10, 10, rgb_arr_not_read, 0, 10);
				try {
					ImageIO.write(snip, "jpg", capture);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				readStates[stateCount] = false;
			}
			coordinates[coordIndex] = y;
			thresholdScroll();
			x = 0;
			y = 0;
			stateCount = (stateCount + 1)  % readStateSize;	
			coordIndex = (coordIndex + 1) % coordinateSize;
		}
		
		if(points >= 30) {
			return true;
		}
		return false;
	}
	
	private void updatePoints()  {
		
		double diffX = x - prev_x;
		double diffY = y - prev_y;
		
		if(diffX < -paddingDist) {
			
			if(diffX > -shortDist){
				points += shortLeftX;				
			}
			else if(diffX > -mediumDist){
				points += mediumLeftX;				
			}
			else {
				points = 0;				
			}
			
		}else if(diffX > paddingDist) {
			if(diffX < shortDist){
				points += shortRightX;				
			}
			else if(diffX < mediumDist){
				points += mediumRightX;				
			}
			else {
				points = 0;				
			}			
		}
		
		if(diffY < - paddingDist) {
			if(diffY > -shortDist) {
				points += shortUpY;
			}else {
				points = 0;
			}
		}else if(diffY > paddingDist) {
			if(diffY < shortDist) {
				points += shortDownY;
			}
			else if(diffY < mediumDist) {
				points += mediumDownY;
			}
			else {
				points = 0;
			}
		}
		
		writeFile((int)diffX, (int)diffY);
		prev_x = x;
		prev_y = y;
		
	}
	
	private void thresholdScroll()  {
		
		double ratio;
		if(isCoordBelow() && (java.lang.System.currentTimeMillis() - lastScrollTime > thresholdTime)) {
			System.out.println("Inside threshold region");
			ratio = getRatio();
			if(ratio >= thresholdRatio) {
				snip.setRGB((int)x, (int)y, 10, 10, rgb_arr_scroll, 0, 10);
				try {
					ImageIO.write(snip, "jpg", capture);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				lastScrollTime = java.lang.System.currentTimeMillis();
				System.out.print(ratio);
				System.out.println("Scrolling Down");
				
				jpcs.scrollMouse(0, -(int) (this.ScreenHeight/scrollFactor));
				
			}
		}
		
		
	}
	
	private double getRatio() {
		int index;
		double ratio = 0;
		for(index = 0; index < readStateSize; index++ ) {
			if(readStates[index])
				ratio += 1;
		}
		return ratio /= readStateSize;
	}
	
	private boolean isCoordBelow() {
		for(int index = 0;  index < coordinateSize; index++) {
			if(coordinates[index] < thresholdCoordY) {
				return false;
			}
		}
		return true;
	}
	
	public void initFile() {
		File f = new File(path_text);
		try {
			if(f.createNewFile()) {
				System.out.println("File created at " + f.getPath());
				String content = "X \t\t Y";
				Files.write(Paths.get(path_text), content.getBytes(), StandardOpenOption.APPEND);
			} else if(f.delete()) {
				System.out.println("Deleted old file.");
				initFile();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeFile(double x, double y) {
		String content = "\n " + x + "\t" + y + "\t" + points;
		Path path = Paths.get(path_text);
		try {
			Files.write(path, content.getBytes(), StandardOpenOption.APPEND );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
	
	public static int[] colorArray(int size, int color) {
		int[] rgb_arr = new int[size];
		for(int i = 0; i < rgb_arr.length; i++) {
			rgb_arr[i] = color;
		}
		return rgb_arr;
	}
	}
