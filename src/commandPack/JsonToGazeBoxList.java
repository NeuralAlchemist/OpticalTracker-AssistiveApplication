package commandPack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import dataPack.BoxCoordinates;
import dataPack.GazeBox;

public final class JsonToGazeBoxList {
	private static String ButtonsFilePath = "BUTTONS/BUTTONS.json" ;

	public static ArrayList<GazeBox> getGazeBoxList() throws IOException
	{
		ArrayList<GazeBox> gbList = new ArrayList<GazeBox>() ;
		GsonBuilder gsonBuilder = new GsonBuilder() ;
		JsonDeserializer<GazeBox> deserializer = new JsonDeserializer<GazeBox>() {

			@Override
			public GazeBox deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
				JsonObject jsonObject = arg0.getAsJsonObject() ;
				String gazeBoxName = jsonObject.get("gazeBoxName").getAsString() ;
				long duration = jsonObject.get("duration").getAsLong() ;
				double topLeftX = jsonObject.get("topLeftCoordinate").getAsJsonObject().get("x").getAsDouble() ;
				double topLeftY = jsonObject.get("topLeftCoordinate").getAsJsonObject().get("y").getAsDouble() ;
				double bottomRightX = jsonObject.get("bottomRightCoordinate").getAsJsonObject().get("x").getAsDouble() ;
				double bottomRightY = jsonObject.get("bottomRightCoordinate").getAsJsonObject().get("y").getAsDouble() ;
				String commandInstruction = jsonObject.get("boxCommand").getAsJsonObject().get("commandInstruction").getAsString();
				int commandID = jsonObject.get("boxCommand").getAsJsonObject().get("commandID").getAsInt();
				CommandAttribute ca ;
				if(commandInstruction.equalsIgnoreCase("Scroll Up"))
				{
					int pixelCount = jsonObject.get("boxCommand").getAsJsonObject().get("commandAttribute").getAsJsonObject().get("pixelCount").getAsInt();
					ca = new ScrollUp(pixelCount) ;
				}
				else if (commandInstruction.equalsIgnoreCase("Scroll Down"))
				{
					int pixelCount = jsonObject.get("boxCommand").getAsJsonObject().get("commandAttribute").getAsJsonObject().get("pixelCount").getAsInt();
					ca = new ScrollDown(pixelCount) ;
				}
				else if (commandInstruction.equalsIgnoreCase("Specific Command"))
				{
					double xPos = jsonObject.get("boxCommand").getAsJsonObject().get("commandAttribute").getAsJsonObject().get("xPos").getAsDouble();
					double yPos = jsonObject.get("boxCommand").getAsJsonObject().get("commandAttribute").getAsJsonObject().get("yPos").getAsDouble();
					boolean pressButton = jsonObject.get("boxCommand").getAsJsonObject().get("commandAttribute").getAsJsonObject().get("pressButton").getAsBoolean();
					ca = new SpecificCommand(xPos, yPos, pressButton) ;
				}
				else
				{
					ca = null ;
				}
				Commands c = new Commands(commandID, commandInstruction, ca) ;
				GazeBox gb  = new GazeBox(gazeBoxName, new BoxCoordinates(topLeftX, topLeftY),
						new BoxCoordinates(bottomRightX, bottomRightY), duration, c) ;
				return gb;
			}
		};
		gsonBuilder.registerTypeAdapter(GazeBox.class, deserializer) ;
		Gson customgs = gsonBuilder.create() ;
		BufferedReader br = new BufferedReader(new FileReader(ButtonsFilePath)) ;
		JsonElement jsonElements = JsonParser.parseReader(br) ;
		JsonArray jArray = jsonElements.getAsJsonArray() ;

		for (JsonElement e :  jArray)
		{
			GazeBox gb = customgs.fromJson(e, GazeBox.class) ;
			gbList.add(gb) ;
		}
		
		br.close();

		return gbList ;
	}

}
