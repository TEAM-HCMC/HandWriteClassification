import sys
import os
import numpy as np
import scipy.misc
train_data = np.load('../destination/'+sys.argv[1]+'.npy')
for num,data in enumerate(train_data):
    scipy.misc.imsave('../destination/temp/temp_'+str(num)+'.jpg',data[0])
