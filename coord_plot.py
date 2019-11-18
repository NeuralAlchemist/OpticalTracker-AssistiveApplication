####
#### Plot eye samples after running record_eye_tracker_log.py
####

import matplotlib.pyplot as plt

list_x = []
list_y = []

lines_seen = set() # holds lines already seen
outfile = open('test5_clean.txt', "w")
for line in open('test5.txt', "r"):
    if line not in lines_seen: # not a duplicate
        outfile.write(line)
        lines_seen.add(line)
outfile.close()

with open ('test5_clean.txt') as a:
    for line in a.readlines():
        tmp = line.split(",")
        tmp1 = float(tmp[0].replace("(",""))
        tmp2 = float(tmp[1].replace(")",""))       
        list_x.append(tmp1)
        list_y.append(tmp2)

for i in range(len(list_x)):
    plt.plot(list_x[i],list_y[i],'ro')

plt.show()