package commandPack;

import com.google.gson.Gson;

/**
 * Class to describe the commands to be sent to the JSON File.<br><p>
 */
public class Commands {
	
	
	/**
	 * ID number of the command.
	 */
	private int commandID ;
	/**
	 * Actual string instruction of the command.
	 */
	private String commandInstruction ;
	
	/**
	 * Attributes associated with the command.
	 */
	private CommandAttribute commandAttribute ;
	
	/**
	 * To check from Java Code side to see if Node js used up the previous command or not.
	 */
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
