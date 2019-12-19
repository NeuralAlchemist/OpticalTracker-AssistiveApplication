package userPack;


import guiPack.UserProfileMaker;
import userPack.User;

public final class MakeUserProfile {

	public static User makeUserProfile() {
		User user = null ;
		UserProfileMaker userProfileMakerGui = new UserProfileMaker() ;
		userProfileMakerGui.startUserProfileMakerGUI();
		
		
		
		
		return user ;
	}

}
