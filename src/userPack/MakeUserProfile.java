package userPack;


import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import dataPack.EyeCoordinate;
import dataPack.QueueOfFixationSets;
import dataPack.QueueOfSmoothedEyeCoordinates;
import guiPack.UserProfileMaker;
import userPack.User;

public class MakeUserProfile {

	private static String userProfilesFilePath = "USER_PROFILES/USER_PROFILES.json" ;
	private QueueOfSmoothedEyeCoordinates queueOfSmoothedEyeCoordinates;
	
	public MakeUserProfile(QueueOfSmoothedEyeCoordinates queueOfSmoothedEyeCoordinates) {
		this.queueOfSmoothedEyeCoordinates = queueOfSmoothedEyeCoordinates ;
	}
	
	public User makeUserProfile() throws IOException, InterruptedException, AWTException {
		ArrayList<User> userList = getUserList() ;
		User user = userList.get(0) ; // First Value as default.
		UserProfileMaker userProfileMakerGui = new UserProfileMaker(userList, this.queueOfSmoothedEyeCoordinates) ;

		System.out.println("Here!");
		while(!userProfileMakerGui.isUserDataReady())
		{
			TimeUnit.MILLISECONDS.sleep(25);
		} ;
		user = userProfileMakerGui.getUserData() ;
		
		System.out.println("is New User: "+userProfileMakerGui.isUserDataNew()+"\n"+user);
		if(userProfileMakerGui.isUserDataNew())
		{
			addUserDataToFile(userList, user);
		}




		return user ;
	}
	
	
	
	private static void addUserDataToFile(ArrayList<User> userList, User user) throws IOException {
		userList.add(user) ;
		Gson gs = new Gson() ;
		String userListString = gs.toJson(userList)  ;
		System.out.println(userListString);
		BufferedWriter bw = new BufferedWriter(new FileWriter(userProfilesFilePath)) ;
		bw.write(userListString);
		bw.close();
	}



	private static ArrayList<User> getUserList() throws IOException {
		ArrayList<User> userList = new ArrayList<User>() ;
		GsonBuilder gsonBuilder = new GsonBuilder() ;
		JsonDeserializer<User> jsonDeserializer = new JsonDeserializer<User>() {

			@Override
			public User deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
				JsonObject jsonObject = arg0.getAsJsonObject() ;
				String name = jsonObject.get("name").getAsString() ;
				int age = jsonObject.get("age").getAsInt() ;
				JsonObject errorProfileJsonObject = jsonObject.get("userErrorProfile").getAsJsonObject() ;
				int numberOfSamplesForProfile = errorProfileJsonObject.get("numberOfSamplesForProfile").getAsInt() ;
				double meanDeviationCoordinateX = errorProfileJsonObject.get("meanDeviationCoordinate").getAsJsonObject().get("x").getAsDouble() ;
				double meanDeviationCoordinateY = errorProfileJsonObject.get("meanDeviationCoordinate").getAsJsonObject().get("y").getAsDouble() ;
				double varianceCoordinateX = errorProfileJsonObject.get("varianceCoordinate").getAsJsonObject().get("x").getAsDouble() ;
				double varianceCoordinateY = errorProfileJsonObject.get("varianceCoordinate").getAsJsonObject().get("y").getAsDouble() ;

				ErrorProfile ep = new ErrorProfile(numberOfSamplesForProfile, 
						new EyeCoordinate(meanDeviationCoordinateX, meanDeviationCoordinateY),
						new EyeCoordinate(varianceCoordinateX, varianceCoordinateY)) ;
				User user = new User(name, age, ep) ;
				return user ;
			}

		};
		gsonBuilder.registerTypeAdapter(User.class, jsonDeserializer) ;
		Gson customGson = gsonBuilder.create() ;
		BufferedReader br = new BufferedReader(new FileReader(userProfilesFilePath)) ;
		JsonElement jsonElements = JsonParser.parseReader(br) ;
		JsonArray jArray = jsonElements.getAsJsonArray() ;

		for (JsonElement e :  jArray)
		{
			User user = customGson.fromJson(e, User.class) ;
			userList.add(user) ;
		}



		br.close();
		return userList;
	}

}
