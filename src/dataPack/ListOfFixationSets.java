package dataPack;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ListOfFixationSets {
	
	private BlockingQueue<FixationSet> listOfFixationSets ;

	
	
	public void addFIFOFixationSet(FixationSet fixationSet) throws InterruptedException
	{
		listOfFixationSets.put(fixationSet);
	}
	
	public ListOfFixationSets(int sizeOfList) {
		super();
		this.listOfFixationSets = new ArrayBlockingQueue<FixationSet>(sizeOfList) ;
	}

	public FixationSet getFIFOFixationSet() throws InterruptedException
	{
		return listOfFixationSets.take() ;
	}

}
