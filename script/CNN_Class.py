import cv2
import numpy as np
import os
from random import shuffle
from tflearn import DNN
from tqdm import tqdm
import tflearn
from tflearn.layers.conv import conv_2d, max_pool_2d
from tflearn.layers.core import input_data, dropout, fully_connected
from tflearn.layers.estimator import regression
import tensorflow as tf
TRAIN_DIR = '../destination/'
TEST_DIR = '../destination/'
IMG_SIZE = 100
LR = 1e-3  # 학습률
MODEL_DIR = '../model/'
MODEL_NAME = '-{}-{}.model'.format(LR, '2conv-basic')
tf.reset_default_graph()  # 커널 상의 값을 초기화
# noinspection PyBroadException
class cnn:
    def __init__(self, name):
        convnet = input_data(shape=[None, IMG_SIZE, IMG_SIZE, 1], name='input')
        convnet = self.conv(convnet)
        convnet = regression(convnet, optimizer='adam', learning_rate=LR, loss='categorical_crossentropy',
                             name='targets')
        model = tflearn.DNN(convnet, tensorboard_dir='log')
        self.model = model
        self.name =  name
        pass
    @staticmethod
    def conv(convnet):
        convnet = conv_2d(convnet, 32, 10, activation='relu')
        convnet = max_pool_2d(convnet, 10)

        convnet = conv_2d(convnet, 64, 10, activation='relu')
        convnet = max_pool_2d(convnet, 10)

        convnet = conv_2d(convnet, 128,10, activation='relu')
        convnet = max_pool_2d(convnet, 10)

        convnet = conv_2d(convnet, 256, 10, activation='relu')
        convnet = max_pool_2d(convnet, 10)

        convnet = conv_2d(convnet, 128, 10, activation='relu')
        convnet = max_pool_2d(convnet, 10)

        convnet = conv_2d(convnet, 64, 10, activation='relu')
        convnet = max_pool_2d(convnet, 10)

        convnet = conv_2d(convnet, 32, 10, activation='relu')
        convnet = max_pool_2d(convnet, 10)

        convnet = fully_connected(convnet, 512, activation='relu')
        convnet = dropout(convnet, 0.8)
        
        convnet = fully_connected(convnet, 512, activation='relu')
        convnet = dropout(convnet, 0.8)
        convnet = fully_connected(convnet, 2, activation='softmax')
        return convnet
    def load_model(self):
        if os.path.exists('{}.meta'.format(MODEL_DIR+self.name.split("/")[1]+MODEL_NAME)):
            self.model.load(MODEL_DIR+self.name.split("/")[1]+MODEL_NAME)
            print('model loaded!')
            return self.model
    def train_data_set(self):
        data_train = []
        true_data = np.load(TRAIN_DIR +self.name+'.npy')
        true_list = true_data.tolist()
        #print(true_data)
        f=0
        false_data = np.zeros((1,100,100,1))
        for npy in tqdm(os.listdir(TRAIN_DIR + 'train/')):
            path = os.path.join(TRAIN_DIR+'train/',npy)
            if path != TRAIN_DIR+self.name+'.npy' :
                if f==0 :
                    false_data = np.load(TRAIN_DIR+'train/'+npy)
                    f = 1
                else :
                    temp = np.load(TRAIN_DIR+'train/'+npy)
                    false_data=np.vstack((false_data,temp))
                #print('temp : ', temp[1])

                #print('false_data : ',false_data[1]) 
        shuffle(false_data)
        #false_data = false_data[:length]
        #print('true_data : ',true_data[0])
        #print('false_data : ', false_data[0])
        false_list = false_data.tolist()
        for i in range(len(true_list)):
            true_list[i].append([1,0])
            data_train.append(np.asarray(true_list[i]))
        #print(data_train)
        for i in range(len(false_data)):
            false_list[i].append([0,1])
            data_train.append(np.asarray(false_list[i]))
        shuffle(data_train)
        #print(data_train[:10])
        return data_train
            
    def train(self,epoch):
        data = self.train_data_set()
        leng = len(data)
        train = data[:-round(leng * 0.3)]  # 70% <- 트레이닝 데이터
        test = data[-round(leng * 0.3):]  # 30% <- 테스트 데이터
        cnt=0;
        try: 
            Y =[i[1] for i in train]
            #print('Y : \n', Y)
            X = np.array([i[0] for i in train]).reshape(-1, IMG_SIZE, IMG_SIZE, 1)  # training data
        except Exception as err:
            print('0')
            #train.remove(i) 
        print(len(data), "  ", len(train), "  ", len(test))
        try:
            test_x = np.array([i[0] for i in test]).reshape(-1, IMG_SIZE, IMG_SIZE, 1) # test data
            test_y = [i[1] for i in test]  # test label
        except Exception as err:
            print('0')
            #test.remove(i)
        print(len(X))
        print(len(Y))
        self.model.fit({'input': X}, {'targets': Y}, n_epoch=epoch,
                       validation_set=({'input': test_x}, {'targets': test_y}),
                       snapshot_step=100,show_metric=True, run_id=MODEL_NAME)
        self.model.save(MODEL_DIR+self.name.split("/")[1]+MODEL_NAME)
