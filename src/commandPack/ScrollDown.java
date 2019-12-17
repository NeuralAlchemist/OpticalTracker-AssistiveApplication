package commandPack;

import com.google.gson.Gson;

public class ScrollDown extends CommandAttribute {

	private int pixelCount ;
	
	public int getPixelCount() {
		return pixelCount;
	}

	public void setPixelCount(int pixelCount) {
		this.pixelCount = pixelCount;
	}

	public ScrollDown(int pixelCount) {
		super();
		this.pixelCount = pixelCount;
	}

	@Override
	public String toJSON() {
		
		return new Gson().toJson(this) ;
	}

	

}
