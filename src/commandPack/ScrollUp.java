package commandPack;

public class ScrollUp extends CommandAttribute{
	
	@Override
	public String toString() {
		return "ScrollUp [pixelCount=" + pixelCount + "]";
	}

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



}
