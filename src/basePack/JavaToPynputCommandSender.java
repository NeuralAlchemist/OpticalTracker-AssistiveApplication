package basePack;
import java.io.IOException;

public class JavaToPynputCommandSender {
	private final static String python3Path = "C:\\Users\\YashSeeta\\Anaconda3\\python.exe" ;
	//	private final static String PythonMouseExecutorPath = "/Users/sagarshubham/eclipse-java-workspace/"
	//			+ "CommandExecuter/PythonMouseExecutor/PythonMouseExecutor.py";
	private final static String PythonMouseExecutorPath = "PythonMouseExecutor/PythonMouseExecutor.py" ;
	private String command ;
	private Process proc ;

	public JavaToPynputCommandSender() {
		this.command = "" ;
		proc = null ;
	}

	public void scrollMouse(int xScroll, int yScroll)
	{
		this.command = 	python3Path + " " +
				PythonMouseExecutorPath + " " +
				"scrollMouse" + " "+ 
				xScroll + " " +
				yScroll ;
		System.out.println(this.command);

		try {
			this.proc = Runtime.getRuntime().exec(this.command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void moveMouse(int xPos, int yPos)
	{
		this.command = 	python3Path + " " +
				PythonMouseExecutorPath + " " +
				"moveMouse" + " "+ 
				xPos + " " +
				yPos ;
		System.out.println(this.command);	
		try {
			this.proc = Runtime.getRuntime().exec(this.command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
