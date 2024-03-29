import random
import matplotlib.pyplot as plt
import numpy as np
import CNN_Class
LR = 1e-3  # 학습률
MODEL_NAME = 'text-{}-{}.model'.format(LR, '2conv-basic')
TEST_DIR = './object/test'
IMG_SIZE = 100

network=CNN_Class.cnn()
model = network.load_model()
#network.train(10)
test_data = network.process_test_data()
test_data = np.load('./npy/test_data.npy')
x=len(test_data)-1
#x=13
n = random.randrange(0,len(test_data)-x)
fig=plt.figure()
cnt=0
crtnumpy=[] #prediction값 저장
wronglist=[]
for num,data in enumerate(test_data[n:n+x]):    
    img_num = data[1]
    img_data = data[0]
    orig = img_data
    data = img_data.reshape(IMG_SIZE,IMG_SIZE,1)
    model_out = model.predict([data])[0] #예측된 label 값
    if np.argmax(model_out) == 1: str_label='not'
    elif np.argmax(model_out) == 0 : str_label='pci'
    else : str_label='jhw'
    if str_label == test_data[n+num][1]: #맞은 경우
        cnt = cnt+1
        #plt.plot(model_out[0],model_out[1],'o', color='red')
        if np.argmax(model_out) == 0: #맞은 경우 + pci인 경우
            crtnumpy.append(max(model_out))
    else : 
        #plt.plot(model_out[0],model_out[1],'o', color='blue')

        #----------------visualize-----------------------------#

        if np.argmax(model_out) == 0:                     #맞은 경우 + pci인 경우
            wronglist.append(max(model_out))
        print("Wrong!!", model_out)
    y = fig.add_subplot(x/10 + x%10,10,num+1)
    y.imshow(orig,cmap='gray')
    plt.title(str_label + ":" +test_data[n+num][1])
    y.axes.get_xaxis().set_visible(False)
    y.axes.get_yaxis().set_visible(False)
        #------------------------------------------------------#
    
#plt.axis([0,1,0,1])
f = open("output.txt",'w')
f.write('Accuracy : '+str(cnt/x))
f.close()
# print("박찬인인것을 맞춘 녀석 중 가장 낮은 확률로 맞춘 녀셕의 확률 : ",min(crtnumpy))
# print("박찬인인것을 맞춘 녀셕의 평균 확률 : ",sum(crtnumpy)/len(crtnumpy))
# try : print("틀린 녀석 중 가장 높은 확률로 틀린 녀셕의 확률 : ",max(wronglist))
# except Exception as e: print("")
#print("prediction : actual")
#plt.show()

