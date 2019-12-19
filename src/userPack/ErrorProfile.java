package userPack;

import dataPack.EyeCoordinate;

public class ErrorProfile {
	private int numberOFSamplesForProfile ;
	private EyeCoordinate meanDeviationCoordinate ;
	private EyeCoordinate varianceCoordinate ;
	
	
	public ErrorProfile(int numberOFSamplesForProfile, EyeCoordinate meanDeviationCoordinate,
			EyeCoordinate varianceCoordinate) {
		super();
		this.numberOFSamplesForProfile = numberOFSamplesForProfile;
		this.meanDeviationCoordinate = meanDeviationCoordinate;
		this.varianceCoordinate = varianceCoordinate;
	}


	public int getNumberOFSamplesForProfile() {
		return numberOFSamplesForProfile;
	}


	public void setNumberOFSamplesForProfile(int numberOFSamplesForProfile) {
		this.numberOFSamplesForProfile = numberOFSamplesForProfile;
	}


	public EyeCoordinate getMeanDeviationCoordinate() {
		return meanDeviationCoordinate;
	}


	public void setMeanDeviationCoordinate(EyeCoordinate meanDeviationCoordinate) {
		this.meanDeviationCoordinate = meanDeviationCoordinate;
	}


	public EyeCoordinate getVarianceCoordinate() {
		return varianceCoordinate;
	}


	public void setVarianceCoordinate(EyeCoordinate varianceCoordinate) {
		this.varianceCoordinate = varianceCoordinate;
	}

}
