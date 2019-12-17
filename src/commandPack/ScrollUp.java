package commandPack;

import com.google.gson.Gson;

public class ScrollUp extends CommandAttribute{
	
	private int pixelCount ;

	public ScrollUp(int pixelCount) {
		super();
		this.pixelCount = pixelCount;
	}

	public int getPixelCount() {
		return pixelCount;
	}

	public void setPixelCount(int pixelCount) {
		this.pixelCount = pixelCount;
	}

	@Override
	public String toJSON() {
		
		return new Gson().toJson(this) ;
	}

}
