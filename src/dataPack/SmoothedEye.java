package dataPack;

/**
 * Class to depict a smooth eye values from the sensor. <br><p>
 * A smooth eye value consists of the following value: <br>
 * 1. The actual x and y coordinates of the point where the eye is currently looking at.<br>
 * 2. If this current look by the user a part of a fixation or not.<br>
 * 3. The time at which the sensor recorded the current data.<br>
 * 4. The state of the view (sensor specific and can be ignored for now).<br>
 *
 */
public class SmoothedEye {
	
	
	/**
	 * {@link dataPack.EyeCoordinate} of the smoothed eye coordinate.
	 */
	private EyeCoordinate eyeCoordinate ;
	/**
	 * Is the current coordinate a part of a {@link dataPack.FixationSet} or not.
	 */
	private boolean isFixated ;
	/**
	 * State of the gaze. Not much information is available for this in the sensor documentations too.
	 */
	private int state ;
	/**
	 * The time at which the sensor recorded the current data in milliseconds.
	 */
	private long timeStamp ;
	/**
	 * @param x x coordinate of the {@link dataPack.EyeCoordinate} of the smoothed eye coordinate.
	 * @param y y coordinate of the {@link dataPack.EyeCoordinate} of the smoothed eye coordinate.
	 * @param isFixated True if these coordinates belong to a fixation set.
	 * @param state state of the data set.
	 * @param timeStamp time at which the sensor recorded the gaze in milliseconds.
	 */
	public SmoothedEye(double x, double y, boolean isFixated, int state, long timeStamp) {
		super();
		this.eyeCoordinate = new EyeCoordinate(x, y) ;
		this.isFixated = isFixated;
		this.state = state;
		this.timeStamp = timeStamp;
	}
	
	
	/**
	 * Method to get the {@link dataPack.SmoothedEye#eyeCoordinate}.
	 * @return {@link dataPack.SmoothedEye#eyeCoordinate}
	 */
	public EyeCoordinate getSmoothedEyeCoordinate() {
		return this.eyeCoordinate ;
	}
	
	
	/**
	 * Method to get the {@link dataPack.SmoothedEye#isFixated}.
	 * @return {@link dataPack.SmoothedEye#isFixated}
	 */
	public boolean getIsFixated() {
		return isFixated;
	}
	/**
	 * Method to get the {@link dataPack.SmoothedEye#state}.
	 * @return {@link dataPack.SmoothedEye#state}
	 */
	public int getState() {
		return state;
	}
	/**
	 * Method to get the {@link dataPack.SmoothedEye#timeStamp}.
	 * @return {@link dataPack.SmoothedEye#timeStamp}
	 */
	public long getTimeStamp() {
		return timeStamp;
	}
	
	/**
	 * Method to get distance between this smoothed eye coordinate and another {@link dataPack.SmoothedEye}.
	 * Uses the underlying {@link dataPack.EyeCoordinate}'s {@link dataPack.EyeCoordinate#getDistanceBetweenEyeCoordinated(EyeCoordinate)} method.
	 * @param differentPoint Another {@link dataPack.SmoothedEye}
	 * @return distance between the two smoothed eye coordinates.
	 */
	public double getDistanceBetweenSmoothedEyeCoordinated(SmoothedEye differentPoint)
	{
		return this.eyeCoordinate.getDistanceBetweenEyeCoordinated(differentPoint.eyeCoordinate) ;
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
