package basePack;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.theeyetribe.client.GazeManager;
import com.theeyetribe.client.GazeManager.ApiVersion;
import com.theeyetribe.client.GazeManager.ClientMode;
import dataPack.QueueOfFixationSets;;



/**
 * 
 * Starting Point in the code which is the default thread.
 * From here, we do the following:<p>
 * 1. Get {@link com.theeyetribe.client.GazeManager} handler.<br>
 * 2. Check for its connection status.<br>
 * 3. Get a {@link dataPack.QueueOfFixationSets} handle.<br>
 * 4. Get {@link basePack.SensorDataProducer} handle by passing it handle from Part 3.<br>
 * 5. Get {@link basePack.SensorDataConsumer} handle by passing it handle from Part 3.<br>
 * 6. Make thread from the handle in Part 5. <br>
 * 7. Get {@link }
 * 8. Add a shutdown hook for the handle in Part 1.<br>
 * @version 1.0
 * 
 */
public class startPoint {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		JPanel jp = new JPanel();
		frame.setLocation(1500, 200);
		JPanel rectangle = new JPanel();
		rectangle.setBackground( Color.RED );
		rectangle.setPreferredSize( new Dimension(50, 50) );
		jp.add( rectangle );
		frame.setVisible(true);
		
		
		JFrame frame1 = new JFrame();
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(300, 300);
		JPanel jp1 = new JPanel();
		frame1.setLocation(1500, 600);
		JPanel rectangle1 = new JPanel();
		rectangle1.setBackground( Color.RED );
		rectangle1.setPreferredSize( new Dimension(50, 50) );
		jp1.add( rectangle1 );
		frame1.setVisible(true);
		
		
		final GazeManager gm = GazeManager.getInstance();
        boolean success = gm.activate(ApiVersion.VERSION_1_0, ClientMode.PUSH);

		System.out.println("Hello, World! Activation: "+ success);
		
        
        QueueOfFixationSets queueOfFixationSets = new QueueOfFixationSets(25) ;
        

		final SensorDataProducer gazeProuducerListener = new SensorDataProducer(queueOfFixationSets) ;
        gm.addGazeListener(gazeProuducerListener);
        
        
        SensorDataConsumer sensorDataConsumer = new SensorDataConsumer(queueOfFixationSets) ;
        
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
