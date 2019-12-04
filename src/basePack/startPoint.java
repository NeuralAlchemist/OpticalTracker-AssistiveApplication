package basePack;

import com.theeyetribe.client.GazeManager;
import com.theeyetribe.client.GazeManager.ApiVersion;
import com.theeyetribe.client.GazeManager.ClientMode;
import dataPack.ListOfFixationSets;



public class startPoint {

	public static void main(String[] args) {
		
		final GazeManager gm = GazeManager.getInstance();
        boolean success = gm.activate(ApiVersion.VERSION_1_0, ClientMode.PUSH);

		System.out.println("Hello, World! Activation: "+ success);
		
        
        ListOfFixationSets listOfFixationSets = new ListOfFixationSets(25) ;
        

		final SensorDataProducer gazeProuducerListener = new SensorDataProducer(listOfFixationSets) ;
        gm.addGazeListener(gazeProuducerListener);
        
        
        SensorDataConsumer sensorDataConsumer = new SensorDataConsumer(listOfFixationSets) ;
        
        Thread consumerThread = new Thread(sensorDataConsumer) ;
        consumerThread.start();
       
        
        
        
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                gm.removeGazeListener(gazeProuducerListener);
             gm.deactivate();
            }
        });
        
	}
}
