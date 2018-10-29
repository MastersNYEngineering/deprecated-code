import os

dir = "./img/"
imgs = os.listdir(dir)

for f in range(len(imgs)):
	name = dir + imgs[f]
	newname = f
	os.rename(name, newname)
	print("Renamed " + name + " to " + newname)