package dataPack;

import java.util.HashSet;
import java.util.Set;

public class FixationSet {



	private Set<SmoothedEye> eyeCoordinatesSet ;
	private long startTimeStamp ;
	private long stopTimeStamp ;
	private EyeCoordinate meanEyeCoordinate ;
	
	
	public FixationSet(long startTimeStamp) {
		super();
		this.startTimeStamp = startTimeStamp;
		this.eyeCoordinatesSet = new HashSet<SmoothedEye>() ;
		this.meanEyeCoordinate = null ;
	}

	public void addEyeCoordinatesSet(SmoothedEye eyeValues)
	{
		this.eyeCoordinatesSet.add(eyeValues) ;
		this.stopTimeStamp = eyeValues.getTimeStamp() ;
		if (this.meanEyeCoordinate == null)
		{
			this.meanEyeCoordinate = new EyeCoordinate() ;
			this.meanEyeCoordinate.setX(eyeValues.getSmoothedEyeCoordinate().getX());
			this.meanEyeCoordinate.setY(eyeValues.getSmoothedEyeCoordinate().getY());
		}
		else
		{
			double currentMeanX = this.meanEyeCoordinate.getX() ;
			double currentMeanY = this.meanEyeCoordinate.getY() ;
			double newXToAdd = eyeValues.getSmoothedEyeCoordinate().getX() ;
			double newYToAdd = eyeValues.getSmoothedEyeCoordinate().getY() ;
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
