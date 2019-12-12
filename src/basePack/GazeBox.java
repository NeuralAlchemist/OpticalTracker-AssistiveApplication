package basePack;

import dataPack.FixationSet;

public class GazeBox {
	int[] tl;
	int[] br;
	long start_milli, cur_milli;
	long duration;
	boolean prev_in_box = false;
	
public GazeBox (int[] bt_tl, int[] bt_br, long sm, long d) {
	tl = bt_tl;
	br = bt_br;
	start_milli = sm; 
	duration = d;
}

public boolean CheckGazeInBox(int[] mean, FixationSet fixationSet) {
	boolean send = false;
	if (mean[0] > tl[0] && mean[0] < br[0]) {
		System.out.println("First if : check x ");
		if(mean[1] > tl[1] && mean[1] < br[1]) {
			System.out.println("Second if : Checked Y");
			if(prev_in_box) {
				System.out.println("Third if :previously in box ");
				cur_milli = fixationSet.getStopTimeStamp();
				
			} else {
				start_milli = fixationSet.getStartTimeStamp();
			}
			prev_in_box = true;
			if(cur_milli - start_milli > duration) {
				System.out.println("Send Command");
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
