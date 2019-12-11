package dataPack;

import java.util.HashSet;
import java.util.Set;


/**
 * Class to describe a set of fixation points. A fixation set is an unordered group of gaze points within some small
 * area, for some time duration for which a person's mind gathers information from the objects in view. Thus a fixation set can be
 * described by <br>
 * 1. A start time when the person starts to look at something.<br>
 * 2. A stop time when the person moves their gaze away from the spot.<br>
 * 3. A set of points which the person is/was looking at.<br>
 *For Mathematical convenience, we also add the following to the description of a fixation set<br>
 * a. Mean Eye Position. The centre or the mean position in the point set of the fixation where the eyes were looking at.<br>
 * b. Top Left Position in the fixation. If the fixation set of points is considered as a box containing all the points 
 * within that set, the top left corner position would be the point made from the least possible x coordinate and maximum
 * possible y coordinate from the points in the set.<br>
 * c. Bottom Right Position in the fixation. If the fixation set of points is considered as a box containing all the points 
 * within that set, the bottom right corner position would be the point made from the maximum possible x coordinate and the maximum
 * possible y coordinate from the points in the set.<br>
 * These point may or may not be in the set itself, but will be useful for determining, along with start and stop time, if two or more 
 * fixation sets could be merged into one bigger set or not.
 */
public class FixationSet {



	/**
	 * {@link java.util.Set} of {@link dataPack.SmoothedEye} values which lie within the fixation.
	 * Implemented as a {@link java.util.HashSet}
	 */
	private Set<SmoothedEye> eyeCoordinatesSet ;
	/**
	 * start time of the fixation set in milliseconds.
	 */
	private long startTimeStamp ;
	/**
	 * stop time of the fixation set in milliseconds.
	 */
	private long stopTimeStamp ;
	/**
	 * mean {@link dataPack.EyeCoordinate} of the fixation set.
	 */
	private EyeCoordinate meanEyeCoordinate ;
	/**
	 * top left and bottom right {@link dataPack.EyeCoordinate}s of the fixation set, respectively. 
	 */
	private EyeCoordinate topLeftCornerOfFixation , bottomRightCornerOfFixation ;


	/**
	 * Constructor to initialize a fixation set.<br>
	 * As the usage is such that a fixation is initialized with a start time,
	 * the argument of the same is used.
	 * @param startTimeStamp To initialize the {@link dataPack.FixationSet#startTimeStamp} value of the fixation set.
	 */
	public FixationSet(long startTimeStamp) {
		super();
		this.startTimeStamp = startTimeStamp;
		this.eyeCoordinatesSet = new HashSet<SmoothedEye>() ;
		this.meanEyeCoordinate = null ;
	}

	/**
	 * Method to add a new {@link dataPack.SmoothedEye} value to the fixation set. <p> 
	 * This method also<br>
	 * 1. Updates the current {@link dataPack.FixationSet#stopTimeStamp} to that of the added 
	 * {@link dataPack.SmoothedEye} value.<br>
	 * 2.Updates the Top Left and Bottom Right {@link dataPack.EyeCoordinate} of the fixation set.<br>
	 * @param eyeValue {@link dataPack.SmoothedEye} value to be added in the fixation set.
	 */
	public void addEyeCoordinatesSet(SmoothedEye eyeValue)
	{
		this.eyeCoordinatesSet.add(eyeValue) ;
		this.stopTimeStamp = eyeValue.getTimeStamp() ;
		updateMeanEyeCoordinate(eyeValue) ;
		updateTopLeftAndBottomRightEyeCoordinates(eyeValue) ;

	}
	

	/**
	 * Method to update top left and bottom right {@link dataPack.EyeCoordinate}s of the fixation set. 
	 * @param eyeValue {@link dataPack.SmoothedEye} From which the {@link dataPack.EyeCoordinate}'s {@link dataPack.EyeCoordinate#x} and 
	 * {@link dataPack.EyeCoordinate#y} are used.
	 */
	private void updateTopLeftAndBottomRightEyeCoordinates(SmoothedEye eyeValue) {
		if (this.topLeftCornerOfFixation == null)
		{
			this.topLeftCornerOfFixation = new EyeCoordinate(eyeValue.getSmoothedEyeCoordinate().getX(),
					eyeValue.getSmoothedEyeCoordinate().getY()) ;
		}
		else
		{
			if (this.topLeftCornerOfFixation.getX() > eyeValue.getSmoothedEyeCoordinate().getX())
			{
				this.topLeftCornerOfFixation.setX(eyeValue.getSmoothedEyeCoordinate().getX());
			}
			if (this.topLeftCornerOfFixation.getY() > eyeValue.getSmoothedEyeCoordinate().getY())
			{
				this.topLeftCornerOfFixation.setY(eyeValue.getSmoothedEyeCoordinate().getY());
			}
		}
		if (this.bottomRightCornerOfFixation == null)
		{
			this.bottomRightCornerOfFixation = new EyeCoordinate(eyeValue.getSmoothedEyeCoordinate().getX(),
					eyeValue.getSmoothedEyeCoordinate().getY()) ;
		}
		else
		{
			if (this.bottomRightCornerOfFixation.getX() < eyeValue.getSmoothedEyeCoordinate().getX())
			{
				this.bottomRightCornerOfFixation.setX(eyeValue.getSmoothedEyeCoordinate().getX());
			}
			if (this.bottomRightCornerOfFixation.getY() < eyeValue.getSmoothedEyeCoordinate().getY())
			{
				this.bottomRightCornerOfFixation.setY(eyeValue.getSmoothedEyeCoordinate().getY());
			}
		}	
	}

	/**
	 * Method to get the {@link dataPack.EyeCoordinate} {@link dataPack.FixationSet#topLeftCornerOfFixation}.
	 * @return {@link dataPack.FixationSet#topLeftCornerOfFixation}
	 */
	public EyeCoordinate getTopLeftCornerOfFixation() {
		return topLeftCornerOfFixation;
	}

	/**
	 * Method to get the {@link dataPack.EyeCoordinate} {@link dataPack.FixationSet#bottomRightCornerOfFixation}.
	 * @return {@link dataPack.FixationSet#bottomRightCornerOfFixation}
	 */
	public EyeCoordinate getBottomRightCornerOfFixation() {
		return bottomRightCornerOfFixation;
	}

	
	/**
	 * Method to update the {@link dataPack.FixationSet#meanEyeCoordinate}.
	 * @param eyeValue Current {@link dataPack.SmoothedEye} to be added to the fixation, from which the actual
	 * {@link dataPack.EyeCoordinate} would be used.
	 */
	private void updateMeanEyeCoordinate(SmoothedEye eyeValue)
	{
		if (this.meanEyeCoordinate == null)
		{
			this.meanEyeCoordinate = new EyeCoordinate(eyeValue.getSmoothedEyeCoordinate().getX(),
					eyeValue.getSmoothedEyeCoordinate().getY()) ;
		}
		else
		{
			double currentMeanX = this.meanEyeCoordinate.getX() ;
			double currentMeanY = this.meanEyeCoordinate.getY() ;
			double newXToAdd = eyeValue.getSmoothedEyeCoordinate().getX() ;
			double newYToAdd = eyeValue.getSmoothedEyeCoordinate().getY() ;
			int newSizeOfSet = this.eyeCoordinatesSet.size() ;
			double newMeanX = (currentMeanX*(newSizeOfSet-1) + newXToAdd)/newSizeOfSet ;
			double newMeanY = (currentMeanY*(newSizeOfSet-1) + newYToAdd)/newSizeOfSet ;
			this.meanEyeCoordinate.setX(newMeanX) ;
			this.meanEyeCoordinate.setY(newMeanY) ;
		}
	}

	/**
	 * Method to get the {@link dataPack.FixationSet#eyeCoordinatesSet}
	 * @return {@link dataPack.FixationSet#eyeCoordinatesSet}
	 */
	public Set<SmoothedEye> getEyeCoordinatesSet() {
		return eyeCoordinatesSet;
	}


	/**
	 * Method to set the {@link dataPack.FixationSet#eyeCoordinatesSet} of the fixation set.
	 * @param eyeCoordinatesSet new {@link java.util.Set} to be used. More restrictively, use {@link java.util.HashSet}.
	 */
	public void setEyeCoordinatesSet(Set<SmoothedEye> eyeCoordinatesSet) {
		this.eyeCoordinatesSet = eyeCoordinatesSet;
	}



	/**
	 * Method to set the {@link dataPack.FixationSet#startTimeStamp} of the fixation set.
	 * @param startTimeStamp start time in milliseconds.
	 */
	public void setStartTimeStamp(long startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
	}


	/**
	 * Method to set the {@link dataPack.FixationSet#stopTimeStamp} of the fixation set.
	 * @param stopTimeStamp stop time in milliseconds.
	 */
	public void setStopTimeStamp(long stopTimeStamp) {
		this.stopTimeStamp = stopTimeStamp;
	}


	/**
	 * Method to get the {@link dataPack.FixationSet#startTimeStamp} of the fixation set.
	 * @return {@link dataPack.FixationSet#startTimeStamp}
	 */
	public long getStartTimeStamp() {
		return startTimeStamp;
	}


	/**
	 * Method to get the {@link dataPack.FixationSet#stopTimeStamp} of the fixation set.
	 * @return {@link dataPack.FixationSet#stopTimeStamp}
	 */
	public long getStopTimeStamp() {
		return stopTimeStamp;
	}



	/**
	 * Method to get the {@link dataPack.FixationSet#meanEyeCoordinate} of the fixation set.
	 * @return {@link dataPack.FixationSet#meanEyeCoordinate}
	 */
	public EyeCoordinate getMeanEyeCoordinate() {
		return meanEyeCoordinate;
	}


	/**
	 * Method to set the {@link dataPack.FixationSet#meanEyeCoordinate} of the fixation set.
	 * @param meanEyeCoordinate {@link dataPack.EyeCoordinate} to be set as {@link dataPack.FixationSet#meanEyeCoordinate} 
	 */
	public void setMeanEyeCoordinate(EyeCoordinate meanEyeCoordinate) {
		this.meanEyeCoordinate = meanEyeCoordinate;
	}





	@Override
	public String toString() {
		return "FixationSet [eyeCoordinatesSet=" + eyeCoordinatesSet.toString() + ", startTimeStamp=" + startTimeStamp
				+ ", stopTimeStamp=" + stopTimeStamp + ", meanEyeCoordinate=" + meanEyeCoordinate.toString() + "]";
	}


}
