import sys
import random
import matplotlib.pyplot as plt
import numpy as np
import CNN_Class
LR = 1e-3  # 학습률
MODEL_NAME = '../model/text-{}-{}.model'.format(LR, '2conv-basic')
TEST_DIR = '../destination/'
IMG_SIZE = 100

network=CNN_Class.cnn()
model = network.load_model()
#network.train(10)
test_data = np.load('../destination/'+sys.argv[1]+'.npy')
x=len(test_data)-1
#x=13
n = random.randrange(0,len(test_data)-x)
fig=plt.figure()
cnt_2 = 0
cnt_1=0
crtnumpy=[] #prediction값 저장
wronglist=[]
for num,data in enumerate(test_data[n:n+x]):    
    img_num = data[1]
    img_data = data[0]
    orig = img_data
    data = img_data.reshape(IMG_SIZE,IMG_SIZE,1)
    model_out = model.predict([data])[0] #예측된 label 값
    cnt_1 += model_out[0] 
    cnt_2 += model_out[1]
f = open("../output/output.txt",'w')
f.write('wrong rate : '+str(cnt_2/x))
f.write('correct rate : '+str(cnt_1/x))
f.close()

