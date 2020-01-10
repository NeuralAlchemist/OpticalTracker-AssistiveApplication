package dataPack;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 
 * Queue of different fixation sets.<br><p>
 * Succinctly, a {@link java.util.concurrent.ArrayBlockingQueue} of {@link dataPack.FixationSet}. <br>
 * We use this to have thread safety for the data from the sensor to the actual usage of it.<br>
 * As the queue is filled by the producer {@link basePack.SensorDataProducer}, 
 * the {@link basePack.SensorDataConsumer} thread consumes the fixation sets and uses them as desired.
 */
public class QueueOfFixationSets {

	private BlockingQueue<FixationSet> queueOfFixationSets ;

	/**
	 * Constructor to initialize the queue to a specific size.
	 * @param sizeOfQueue size of the queue.
	 */
	public QueueOfFixationSets(int sizeOfQueue) {
		super();
		this.queueOfFixationSets = new ArrayBlockingQueue<FixationSet>(sizeOfQueue) ;
	}


	/**
	 * @param fixationSet {@link dataPack.FixationSet} which are received from the producer and added to 
	 * {@link dataPack.QueueOfFixationSets#queueOfFixationSets}
	 * @throws InterruptedException Due to use of {@link java.util.concurrent.ArrayBlockingQueue}, this is necessary.
	 */
	public void addFIFOFixationSet(FixationSet fixationSet) throws InterruptedException
	{
		queueOfFixationSets.put(fixationSet);
	}



	/**
	 * @return {@link dataPack.FixationSet} at the head of the queue.
	 * @throws InterruptedException Due to use of {@link java.util.concurrent.ArrayBlockingQueue}, this is necessary.
	 */
	public FixationSet getFIFOFixationSet() throws InterruptedException
	{
		return queueOfFixationSets.take() ;
	}


}
