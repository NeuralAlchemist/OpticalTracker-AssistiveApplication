package commandPack;


public class ScrollDown extends CommandAttribute {

	@Override
	public String toString() {
		return "ScrollDown [pixelCount=" + pixelCount + "]";
	}

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


}
