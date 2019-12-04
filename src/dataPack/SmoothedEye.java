package dataPack;

public class SmoothedEye {
	
	
	private EyeCoordinate eyeCoordinate ;
	private boolean isFixated ;
	private int state ;
	private long timeStamp ;
	public SmoothedEye(double x, double y, boolean isFixated, int state, long timeStamp) {
		super();
		this.eyeCoordinate = new EyeCoordinate(x, y) ;
		this.isFixated = isFixated;
		this.state = state;
		this.timeStamp = timeStamp;
	}
	
	
	public EyeCoordinate getSmoothedEyeCoordinate() {
		return this.eyeCoordinate ;
	}
	
	
	public boolean isFixated() {
		return isFixated;
	}
	public int getState() {
		return state;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	
	public double distanceBetweenSmoothedEyeCoordinated(SmoothedEye differentPoint)
	{
		return this.eyeCoordinate.distanceBetweenEyeCoordinated(differentPoint.eyeCoordinate) ;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eyeCoordinate == null) ? 0 : eyeCoordinate.hashCode());
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
		SmoothedEye other = (SmoothedEye) obj;
		if (eyeCoordinate == null) {
			if (other.eyeCoordinate != null)
				return false;
		} else if (!eyeCoordinate.equals(other.eyeCoordinate))
			return false;
		return true;
	}


}
