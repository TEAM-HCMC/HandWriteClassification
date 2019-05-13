import sys
import CNN_Class
network=CNN_Class.cnn()
network.train(10, sys.argv[1])
