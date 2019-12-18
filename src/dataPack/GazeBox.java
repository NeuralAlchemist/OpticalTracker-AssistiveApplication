package dataPack;

import commandPack.Commands;
import dataPack.BoxCoordinates;
import dataPack.EyeCoordinate;

/**
 * This class uses the boundaries of a scroll button to check if 
 * the user intends to use a particular button.
 *
 */

public class GazeBox {
	@Override
	public String toString() {
		return "GazeBox [gazeBoxName=" + gazeBoxName + ", topLeftCoordinate=" + topLeftCoordinate
				+ ", bottomRightCoordinate=" + bottomRightCoordinate + ", start_milli=" + start_milli + ", cur_milli="
				+ cur_milli + ", duration=" + duration + ", prev_in_box=" + prev_in_box + ", boxCommand=" + boxCommand
				+ "]";
	}



	private String gazeBoxName ;
	private BoxCoordinates topLeftCoordinate ;
	private BoxCoordinates bottomRightCoordinate ;
	private long start_milli;
	private long cur_milli;
	private long duration;
	boolean prev_in_box = false;
	private Commands boxCommand ;



	/**
	 * @param topLeftCoordinate Coordinate of the top left corner of the button.
	 * @param bottomRightCoordinate Coordinate of the bottom right corner of the button.
	 * @param duration Minimum amount of time in milliseconds that a user must gaze at the button to use it.
	 * @param boxCommand Command associated with the button.
	 */
	public GazeBox(String gazeBoxName ,
			BoxCoordinates topLeftCoordinate, BoxCoordinates bottomRightCoordinate, long duration,
			Commands boxCommand) {
		super();
		this.gazeBoxName = gazeBoxName ;
		this.topLeftCoordinate = topLeftCoordinate;
		this.bottomRightCoordinate = bottomRightCoordinate;
		this.duration = duration;
		this.boxCommand = boxCommand;
	}



	/**
	 * Method from class GazeBox to check if the user intends to use a button or not
	 * @param mean	The mean of the eye coordinates from the fixationSet sent separately
	 * @param fixationSet {@link dataPack.FixationSet}
	 * @see Class FixationSet
	 * @return a boolean that is true if the user intends to use the button. False, otherwise.
	 */

	public boolean CheckGazeInBox(EyeCoordinate meanEyeCoordinate, long fixationStartTime, long fixationStopTime) {
		boolean inBoxStatus = false;
		if (	meanEyeCoordinate.getX() > this.topLeftCoordinate.getX() &&
				meanEyeCoordinate.getX() < this.bottomRightCoordinate.getX() &&
				meanEyeCoordinate.getY() >  this.topLeftCoordinate.getY() &&
				meanEyeCoordinate.getY() < this.bottomRightCoordinate.getY()	) 
		{
			if(!this.prev_in_box)
			{
				this.prev_in_box = true ;
				this.start_milli = fixationStartTime ;
			}
			else
			{
				cur_milli = fixationStopTime ;
			}
			
			if(this.cur_milli - this.start_milli > this.duration)
			{
				inBoxStatus = true ;
			}
		}
		else
		{
			this.prev_in_box = false; 
		}
		return inBoxStatus ;


	}



	public BoxCoordinates getTopLeftCoordinate() {
		return topLeftCoordinate;
	}



	public void setTopLeftCoordinate(BoxCoordinates topLeftCoordinate) {
		this.topLeftCoordinate = topLeftCoordinate;
	}



	public BoxCoordinates getBottomRightCoordinate() {
		return bottomRightCoordinate;
	}



	public void setBottomRightCoordinate(BoxCoordinates bottomRightCoordinate) {
		this.bottomRightCoordinate = bottomRightCoordinate;
	}



	public long getDuration() {
		return duration;
	}



	public void setDuration(long duration) {
		this.duration = duration;
	}



	public boolean isPrev_in_box() {
		return prev_in_box;
	}



	public void setPrev_in_box(boolean prev_in_box) {
		this.prev_in_box = prev_in_box;
	}



	public Commands getBoxCommand() {
		return boxCommand;
	}



	public void setBoxCommand(Commands boxCommand) {
		this.boxCommand = boxCommand;
	}



	public String getGazeBoxName() {
		return gazeBoxName;
	}



	public void setGazeBoxName(String gazeBoxName) {
		this.gazeBoxName = gazeBoxName;
	}
}