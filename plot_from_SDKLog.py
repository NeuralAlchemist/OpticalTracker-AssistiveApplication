
###### 
###### Cleaned up ani's code from : LINK 
######

import json
import matplotlib.pyplot as plt
data = [] #empty array to hold JSON object in each element

# Open the log file created from EyeTribe SDK
with open('D:\\Google Drive\\UU\\M2S1\\ECP\\PyTribe\\example\\record_1_Manu_Winograd.txt') as f:     
    for frame_data in f:
        data.append(json.loads(frame_data))
        
for info in data: #retrieve average x and y coordinates    
    if info['category'] != "heartbeat":
        plt.plot(info["values"]["frame"]["avg"]["x"], info["values"]["frame"]["avg"]["y"], 'ro') #add (x,y) to be plotted
        plt.axis([-20, 1920, -20, 1080]) #your screen resolution 
        plt.gca().invert_yaxis() #invert x-axis as origin is top-left
plt.show() #display the plot