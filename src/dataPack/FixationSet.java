package dataPack;

import java.util.HashSet;
import java.util.Set;

public class FixationSet {



	private Set<SmoothedEye> eyeCoordinatesSet ;
	private long startTimeStamp ;
	private long stopTimeStamp ;
	private EyeCoordinate meanEyeCoordinate ;
	private EyeCoordinate topLeftCornerOfFixation , bottomRightCornerOfFixation ;


	public FixationSet(long startTimeStamp) {
		super();
		this.startTimeStamp = startTimeStamp;
		this.eyeCoordinatesSet = new HashSet<SmoothedEye>() ;
		this.meanEyeCoordinate = null ;
	}

	public void addEyeCoordinatesSet(SmoothedEye eyeValue)
	{
		this.eyeCoordinatesSet.add(eyeValue) ;
		this.stopTimeStamp = eyeValue.getTimeStamp() ;
		updateMeanEyeCoordinate(eyeValue) ;
		updateTopLeftAndBottomRightEyeCoordinates(eyeValue) ;

	}
	

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

	public EyeCoordinate getTopLeftCornerOfFixation() {
		return topLeftCornerOfFixation;
	}

	public EyeCoordinate getBottomRightCornerOfFixation() {
		return bottomRightCornerOfFixation;
	}

	private void updateMeanEyeCoordinate(SmoothedEye eyeValue)
	{
		if (this.meanEyeCoordinate == null)
		{
			this.meanEyeCoordinate = new EyeCoordinate() ;
			this.meanEyeCoordinate.setX(eyeValue.getSmoothedEyeCoordinate().getX());
			this.meanEyeCoordinate.setY(eyeValue.getSmoothedEyeCoordinate().getY());
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

	public Set<SmoothedEye> getEyeCoordinatesSet() {
		return eyeCoordinatesSet;
	}


	public void setEyeCoordinatesSet(Set<SmoothedEye> eyeCoordinatesSet) {
		this.eyeCoordinatesSet = eyeCoordinatesSet;
	}



	public void setStartTimeStamp(long startTimeStamp) {
		this.startTimeStamp = startTimeStamp;
	}


	public void setStopTimeStamp(long stopTimeStamp) {
		this.stopTimeStamp = stopTimeStamp;
	}


	public long getStartTimeStamp() {
		return startTimeStamp;
	}


	public long getStopTimeStamp() {
		return stopTimeStamp;
	}



	public EyeCoordinate getMeanEyeCoordinate() {
		return meanEyeCoordinate;
	}


	public void setMeanEyeCoordinate(EyeCoordinate meanEyeCoordinate) {
		this.meanEyeCoordinate = meanEyeCoordinate;
	}





	@Override
	public String toString() {
		return "FixationSet [eyeCoordinatesSet=" + eyeCoordinatesSet.toString() + ", startTimeStamp=" + startTimeStamp
				+ ", stopTimeStamp=" + stopTimeStamp + ", meanEyeCoordinate=" + meanEyeCoordinate.toString() + "]";
	}


}
