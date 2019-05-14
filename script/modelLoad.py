import sys
import random
import matplotlib.pyplot as plt
import numpy as np
import CNN_Class
import scipy.misc
LR = 1e-3  # 학습률
TEST_DIR = '../destination/'
IMG_SIZE = 100

network=CNN_Class.cnn()
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
crtnumpy=[] #prediction값 저장
wronglist=[]
for num,data in enumerate(test_data[n:n+x]):    
    img_data = data[0]
    orig = data
    compare_data = orig.reshape(IMG_SIZE,IMG_SIZE,1)
    model_out = model.predict([compare_data])[0] #예측된 label 
    cnt_1 += round(model_out[0])
    cnt_2 += round(model_out[1])
    if model_out[0] >= 0.9999 :
        scipy.misc.imsave('../output/highPercent'+str(num)+'.jpg',data[0])
print('correctness : ',cnt_1/x)
f = open("../output/output.txt",'w')
f.write('wrong rate : '+str(cnt_2/x)+'\n')
f.write('correct rate : '+str(cnt_1/x)+'\n')
f.close()
