package userPack;

import java.util.HashSet;
import java.util.Set;

import dataPack.EyeCoordinate;
import dataPack.SmoothedEye;

public class UserEyeData {
	private EyeCoordinate eyeCoordinate ;
	private Set<SmoothedEye> smoothedEyePoints ;
	
	public UserEyeData() {
		smoothedEyePoints = new HashSet<SmoothedEye>();
	}
	
	public EyeCoordinate getEyeCoordinate() {
		return eyeCoordinate;
	}
	public void setEyeCoordinate(EyeCoordinate eyeCoordinate) {
		this.eyeCoordinate = eyeCoordinate;
	}
	public Set<SmoothedEye> getSmoothedEyePoints() {
		return smoothedEyePoints;
	}
	public void setSmoothedEyePoints(Set<SmoothedEye> smoothedEyePoints) {
		this.smoothedEyePoints = smoothedEyePoints;
	}
	
	public void addSmoothedEyeCoordinate(SmoothedEye smoothedEyeCoordinate)
	{
		this.smoothedEyePoints.add(smoothedEyeCoordinate) ;
	}

}
