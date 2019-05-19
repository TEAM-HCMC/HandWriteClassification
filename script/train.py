import sys
import CNN_Class
network=CNN_Class.cnn(sys.argv[1])
network.train(10)
