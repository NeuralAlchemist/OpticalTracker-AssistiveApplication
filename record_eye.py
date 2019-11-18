#####  Record data from EyeTribe untill the key "p" is pressed.
#####  There are two output logs: pytribe's log is difficult to parse
#####  Other log takes only current frame's average values -- its samples are duplicates 
#####  IMPROVE : Try to make the samples better
#####
##### REFERENCES : Dalmaijer, E.S., Mathôt, S., & Van der Stigchel, S. (2013). 
##### PyGaze: an open-source, cross-platform toolbox for minimal-effort programming 
##### of eye tracking experiments. Behaviour Research Methods. doi:10.3758/s13428-013-0422-2
#####
#####
#####
##### In the folder containing this script include, 
##### -  pygazeanalyzer from 
#####    from: https://github.com/esdalmaijer/PyGazeAnalyser/tree/master/pygazeanalyser)
##### - pytribe.py (script to communicate with the EyeTribe tracker,
##### from: https://github.com/esdalmaijer/PyTribe/blob/master/pytribe.py)


import os

from pytribe import EyeTribe

import keyboard

# files and paths
DIR = os.path.dirname(os.path.abspath(__file__))
LOGFILE = os.path.join(DIR, 'example_data_of_range.txt')

# start communications with the EyeTribe tracker
tracker = EyeTribe(logfilename=LOGFILE)

# start recording gaze data
tracker.start_recording()
tracker.log_message("Tracker On")
file = open("test5.txt","a+")
cdtn = False
while (not cdtn):
    tup = tracker.sample()
    print(tup)
    file.write("%s \n" % str(tup))
    cdtn = keyboard.is_pressed('p')

tracker.log_message("Tracker Off")
tracker.stop_recording()


# # # # #
# CLOSE
# close connection to the tracker
tracker.close()

