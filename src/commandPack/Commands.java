package commandPack;

import com.google.gson.Gson;

/**
 * Class to describe the commands to be sent to the JSON File.<br><p>
 */
public class Commands {
	
	
	@Override
	public String toString() {
		return "Commands [commandID=" + commandID + ", commandInstruction=" + commandInstruction + ", commandAttribute="
				+ commandAttribute + ", hasCommandBeenExecutedByServer=" + hasCommandBeenExecutedByServer + "]";
	}


	/**
	 * ID number of the command.
	 */
	@SuppressWarnings("unused")
	private int commandID ;
	/**
	 * Actual string instruction of the command.
	 */
	@SuppressWarnings("unused")
	private String commandInstruction ;
	
	/**
	 * Attributes associated with the command.
	 */
	@SuppressWarnings("unused")
	private CommandAttribute commandAttribute ;
	
	/**
	 * To check from Java Code side to see if Node js used up the previous command or not.
	 */
	@SuppressWarnings("unused")
	private boolean hasCommandBeenExecutedByServer ;

	public Commands(String commandInstruction, CommandAttribute commandAttribute) {
		super();
		this.commandInstruction = commandInstruction;
		this.commandAttribute = commandAttribute;
		this.hasCommandBeenExecutedByServer = false ;
	}
	
	public Commands(int commandID, String commandInstruction, CommandAttribute commandAttribute) {
		super();
		this.commandID = commandID ;
		this.commandInstruction = commandInstruction;
		this.commandAttribute = commandAttribute;
		this.hasCommandBeenExecutedByServer = false ;
	}
	
	
	public String toJSONString()
	{
		return new Gson().toJson(this) ;
	}
	
}
