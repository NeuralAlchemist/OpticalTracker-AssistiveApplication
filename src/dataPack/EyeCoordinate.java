package dataPack;


/**
 * Class to describe a basic 2D Point in the screen a person is looking at. This can be described as a simple<br>
 * 1. X coordinate.<br>
 * 2. Y coordinate.<br>
 */
public class EyeCoordinate {

	/**
	 * To store the x coordinate of the point being looked at.
	 */
	private double x ;
	/**
	 * To store the y coordinate of the point being looked at.
	 */
	private double y ;
	/**
	 * Constructor to initialize the coordinates of the point the eye is looking at.
	 * @param x x coordinate of the point being looked at.
 	 * @param y y coordinate of the point being looked at.
	 */
	public EyeCoordinate(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	/**
	 * Method to get the {@link dataPack.EyeCoordinate#x}.
	 * @return {@link dataPack.EyeCoordinate#x}
	 */
	public double getX() {
		return x;
	}
	/**
	 * Method to set the {@link dataPack.EyeCoordinate#x}
	 * @param x Value for the x coordinate.
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * Method to get the {@link dataPack.EyeCoordinate#y}.
	 * @return {@link dataPack.EyeCoordinate#y}
	 */
	public double getY() {
		return y;
	}
	/**
	 * Method to set the {@link dataPack.EyeCoordinate#y}
	 * @param y Value for the y coordinate.
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Method to find the distance between the current coordinate and another coordinate.
	 * It uses the 2D Euclidean formula for finding the distance.
	 * @param differentPoint A different {@link dataPack.EyeCoordinate}
	 * @return Euclidean distance between the coordinates.
	 */
	public double getDistanceBetweenEyeCoordinated(EyeCoordinate differentPoint)
	{
		double x0 = x ;
		double y0 = y ;
		double x1 = differentPoint.getX() ;
		double y1 = differentPoint.getY() ;

		return  Math.sqrt((x1-x0)*(x1-x0) + (y1-y0)*(y1-y0)) ;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EyeCoordinate other = (EyeCoordinate) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EyeCoordinate [x=" + x + ", y=" + y + "]";
	}

	

}
