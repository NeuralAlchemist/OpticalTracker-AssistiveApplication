package userPack;

import dataPack.EyeCoordinate;

public class ErrorProfile {
	private int numberOfSamplesForProfile ;
	private EyeCoordinate meanDeviationCoordinate ;
	private EyeCoordinate varianceCoordinate ;
	
	
	public ErrorProfile(int numberOfSamplesForProfile, EyeCoordinate meanDeviationCoordinate,
			EyeCoordinate varianceCoordinate) {
		super();
		this.numberOfSamplesForProfile = numberOfSamplesForProfile;
		this.meanDeviationCoordinate = meanDeviationCoordinate;
		this.varianceCoordinate = varianceCoordinate;
	}


	public int getNumberOFSamplesForProfile() {
		return numberOfSamplesForProfile;
	}


	public void setNumberOFSamplesForProfile(int numberOfSamplesForProfile) {
		this.numberOfSamplesForProfile = numberOfSamplesForProfile;
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


	@Override
	public String toString() {
		return "ErrorProfile [numberOFSamplesForProfile=" + numberOfSamplesForProfile + ", meanDeviationCoordinate="
				+ meanDeviationCoordinate + ", varianceCoordinate=" + varianceCoordinate + "]";
	}

}
