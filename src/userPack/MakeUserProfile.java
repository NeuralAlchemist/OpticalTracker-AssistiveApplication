package userPack;


import java.util.ArrayList;

import guiPack.UserProfileMaker;
import userPack.User;

public final class MakeUserProfile {
	
	private String userProfilesFileLocation = "USER_PROFILES/USER_PROFILES.json" ;
	public static User makeUserProfile() {
		ArrayList<User> userList = getUserList() ;
		User user = userList.get(0) ;// First Value
		UserProfileMaker userProfileMakerGui = new UserProfileMaker(userList) ;
		
		while(!userProfileMakerGui.isUserDataReady())
		{
		} ;
		user = userProfileMakerGui.getUserData() ;
		
		
		
		
		return user ;
	}
	private static ArrayList<User> getUserList() {
		return null;
	}

}
