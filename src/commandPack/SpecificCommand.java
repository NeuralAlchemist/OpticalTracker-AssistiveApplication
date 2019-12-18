package commandPack;


public class SpecificCommand extends CommandAttribute {

	private double xPos ;
	private double yPos ;
	private boolean pressButton ;

	
	@Override
	public String toString() {
		return "SpecificCommand [xPos=" + xPos + ", yPos=" + yPos + ", pressButton=" + pressButton + "]";
	}


	public SpecificCommand(double xPos, double yPos, boolean pressButton) {
		super();
		this.xPos = xPos;
		this.yPos = yPos;
		this.pressButton = pressButton;
	}

}
