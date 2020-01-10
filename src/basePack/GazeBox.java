package basePack;

import dataPack.FixationSet;

/**
 * This class uses the boundaries of a scroll button to check if 
 * the user intends to use a particular button.
 *
 */

public class GazeBox {
	int[] tl;
	int[] br;
	long start_milli, cur_milli;
	long duration;
	boolean prev_in_box = false;
	
	/**
	 * Constructor to initialize a scroll button and it's properties with the following parameters
	 * @param bt_tl The top left 2D coordinates of the button
	 * @param bt_br The bottom right 2D coordinates of the button
	 * @param sm	The time from which user's gaze is counted
	 * @param d Minimum amount of time in milliseconds that a user must gaze at the button to use it
	 */
	
	public GazeBox (int[] bt_tl, int[] bt_br, long sm, long d) {
		tl = bt_tl;
		br = bt_br;
		start_milli = sm; 
		duration = d;
}

/**
 * Method from class GazeBox to check if the user intends to use a button or not
 * @param mean	The mean of the eye coordinates from the fixationSet sent separately
 * @param fixationSet {@link dataPack.FixationSet}
 * @see Class FixationSet
 * @return a boolean that is true if the user intends to use the button. False, otherwise.
 */
	
public boolean CheckGazeInBox(int[] mean, FixationSet fixationSet) {
	boolean send = false;
	if (mean[0] > tl[0] && mean[0] < br[0]) {
		//System.out.println("First if : check x ");
		if(mean[1] > tl[1] && mean[1] < br[1]) {
		//	System.out.println("Second if : Checked Y");
			if(prev_in_box) {
				//System.out.println("Third if :previously in box ");
				cur_milli = fixationSet.getStopTimeStamp();
				
			} else {
				start_milli = fixationSet.getStartTimeStamp();
			}
			prev_in_box = true;
			if(cur_milli - start_milli > duration) {
				//System.out.println("Send Command");
				send = true;
			} 
		} else {
			prev_in_box = false;
			send = false;
		}
	} else {
		prev_in_box = false;
		send = false;
	}
	return send;
}

}
