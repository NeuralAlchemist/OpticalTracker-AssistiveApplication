from pynput.mouse import Button,Controller
import sys

mouseController = Controller()


argumentString = "" #Place holder for mouse action.
numberOfArgs = len(sys.argv)
# print(numberOfArgs)
if numberOfArgs == 1:
	argumentString = "" #No Arguments passed.
elif numberOfArgs == 2:
	argumentString = "" #Basic Argument such as close window/change tab
	#might require more work.
		
elif numberOfArgs == 3:
	argumentString = "" #One Argument Command.
elif numberOfArgs == 4:
	command = sys.argv[1]
	if(command == "scrollMouse"):
		xScroll = int(sys.argv[2])
		yScroll = int(sys.argv[3])
		mouseController.scroll(xScroll,yScroll)
	elif (command == "moveMouse"):
		xPos = int(sys.argv[2])
		yPos = int(sys.argv[3])
		mouseController.position = (xPos,yPos)
