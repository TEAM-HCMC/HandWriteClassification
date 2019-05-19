import sys
import random
import matplotlib.pyplot as plt
import numpy as np
import CNN_Class
import scipy.misc
LR = 1e-3  # 학습률
TEST_DIR = '../destination/'
IMG_SIZE = 100

network=CNN_Class.cnn(sys.argv[1])
model = network.load_model()
#network.train(10)

test_data = np.load('../destination/'+sys.argv[1]+'.npy')
x=len(test_data)-1
#print(test_data[1])
#x=13
n = random.randrange(0,len(test_data)-x)
#fig=plt.figure()
cnt_2 = 0
cnt_1=0
t=0
for num,data in enumerate(test_data[n:n+x]):    
    img_data = data[0]
    orig = data
    compare_data = orig.reshape(IMG_SIZE,IMG_SIZE,1)
    model_out = model.predict([compare_data])[0] #예측된 label 
    cnt_1 += round(model_out[0])
    cnt_2 += round(model_out[1])
    if model_out[1] >= 0.9 and t<20 :
        t = t + 1 
        scipy.misc.imsave('../output/'+sys.argv[1].split("/")[1]+str(t)+'.jpg',data[0])
print('correctness : ',cnt_1/x)
f = open("../output/"+sys.argv[1].split("/")[1]+"_output.txt",'w')
f.write('wrong_rate:'+str(cnt_2/x)+'\n')
f.write('correct_rate:'+str(cnt_1/x)+'\n')
f.close()
