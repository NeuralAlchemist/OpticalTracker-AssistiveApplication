package guiPack;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

import dataPack.PointNumberToCoordinateConverter;




public class EyeProfileGuiPanel extends JPanel implements  ActionListener,KeyListener{

	public double getxCenter() {
		return xCenter;
	}


	public double getyCenter() {
		return yCenter;
	}


	private double xCenter, yCenter;
	private int currentPointCount ;
	private PointNumberToCoordinateConverter pnc ;
	private static double diam = 50 ;
	private static double scaleFactor = 4 ;
	private boolean currentPointStatus = false ;



	public EyeProfileGuiPanel(int numberOfPoints ) {

		this.currentPointCount = 0 ;
		this.pnc = new PointNumberToCoordinateConverter(numberOfPoints) ;
		
		addKeyListener(this);
		this.xCenter = pnc.getXCenter(currentPointCount);
		this.yCenter = pnc.getYCenter(currentPointCount);
		System.out.println("Done Init."+isFocusable());
	}


	public boolean isCurrentPointStatus() {
		return currentPointStatus;
	}


	public void setCurrentPointStatus(boolean currentPointStatus) {
		this.currentPointStatus = currentPointStatus;
	}


	public void setFocii()
	{
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
		System.out.println("Done Focii."+isFocusable()+" "+requestFocusInWindow());

	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d =(Graphics2D) g ;
		System.out.println("repainting! started");
	
		g2d.setColor(Color.BLACK);


		double xPos = xCenter - diam/2 ;
		double yPos = yCenter - diam/2 ;

		g2d.fill(new Ellipse2D.Double(xPos, yPos, diam, diam)) ;



		xPos = xCenter - diam/2/scaleFactor ;
		yPos = yCenter - diam/2/scaleFactor ;
		int offSet = 1 ; // required because otherwise the circles are not concentric.
		g2d.setColor(Color.RED);


		g2d.fillOval((int)xPos+offSet,
				(int)yPos+offSet,
				(int)(diam/scaleFactor), (int)(diam/scaleFactor));
	}





	public int getCurrentPointCount() {
		return currentPointCount;
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Caught Key Press!");
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			xCenter = pnc.getXCenter(currentPointCount) ;
			yCenter = pnc.getYCenter(currentPointCount) ;	
			++currentPointCount ;
			currentPointStatus = true ;
			repaint();
		}

	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
