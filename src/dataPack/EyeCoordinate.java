package dataPack;

public class EyeCoordinate {

	private double x ;
	private double y ;
	public EyeCoordinate()
	{
		
	}
	
	public EyeCoordinate(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}

	public double distanceBetweenEyeCoordinated(EyeCoordinate differentPoint)
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
