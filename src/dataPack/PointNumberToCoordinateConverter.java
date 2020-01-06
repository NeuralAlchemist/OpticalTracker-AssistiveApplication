package dataPack;

import java.awt.Toolkit;

public class PointNumberToCoordinateConverter {
	
	private double xCenter, yCenter ;
	private int numberOfPoints ;
	
	private static double screenCenterX = Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2 ;
	private static double screenCenterY = Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2 ;
	private static double radius = Toolkit.getDefaultToolkit().getScreenSize().getWidth()/4;
	
	public PointNumberToCoordinateConverter(int numberOfPoints) {
		this.numberOfPoints = numberOfPoints ;
		System.out.println(screenCenterX+"  "+screenCenterY);
	}
	
	public double getXCenter(int pointNumber)
	{
		createXYCenters(pointNumber) ;
		return this.xCenter ;
	}
	
	private void createXYCenters(int pointNumber)
	{
		xCenter = screenCenterX + radius * Math.cos((90-360*pointNumber/numberOfPoints)*Math.PI/180);
		yCenter = screenCenterY - radius * Math.sin((90-360*pointNumber/numberOfPoints)*Math.PI/180);
		System.out.println("Point: "+pointNumber+" Angle: "+ (90-360*pointNumber/numberOfPoints) + "X: "+xCenter+"Y: "+yCenter);
	}
	
	public double getYCenter(int pointNumber)
	{
		createXYCenters(pointNumber) ;
		return this.yCenter ;
	}

}
