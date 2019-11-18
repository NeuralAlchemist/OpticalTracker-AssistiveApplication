
#####
#####  Script to control the mouse pointer via eye-tracker using PyTribe
#####
#####
##### REFERENCES : Dalmaijer, E.S., Mathôt, S., & Van der Stigchel, S. (2013). 
##### PyGaze: an open-source, cross-platform toolbox for minimal-effort programming 
##### of eye tracking experiments. Behaviour Research Methods. doi:10.3758/s13428-013-0422-2
#####
#####
#####
##### In the folder containing this script include, 
##### - pytribe.py (script to communicate with the EyeTribe tracker,
##### from: https://github.com/esdalmaijer/PyTribe/blob/master/pytribe.py)

import os

import keyboard

from pytribe import EyeTribe

from pynput.mouse import Controller

mouse = Controller()


# files and paths
DIR = os.path.dirname(os.path.abspath(__file__))
# file to store the log from pytribe
LOGFILE = os.path.join(DIR, 'sandbox.txt')

# start communications with the EyeTribe tracker
tracker = EyeTribe(logfilename=LOGFILE)

# start recording gaze data
tracker.start_recording()
tracker.log_message("Tracker On")
cdtn = False
#file to store the frame data - only avgerage values
file = open("sb.txt","a+")
while not cdtn:
    frame = tracker._tracker.get_frame()
    x = frame['avgx']
    y = frame['avgy']
#    file.write("%f \n" %x)
    mouse.position = (x, y)
    cdtn = keyboard.is_pressed('p')           
tracker.log_message("Tracker Off")
tracker.stop_recording()


# # # # #
# CLOSE
# close connection to the tracker
tracker.close()