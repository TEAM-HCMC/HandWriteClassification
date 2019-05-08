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
MODEL_NAME = 'text-{}-{}.model'.format(LR, '2conv-basic')
tf.reset_default_graph()  # 커널 상의 값을 초기화
# noinspection PyBroadException
class cnn:
    def __init__(self):
        convnet = input_data(shape=[None, IMG_SIZE, IMG_SIZE, 1], name='input')
        convnet = self.conv(convnet)
        convnet = regression(convnet, optimizer='adam', learning_rate=LR, loss='categorical_crossentropy',
                             name='targets')
        model = tflearn.DNN(convnet, tensorboard_dir='log')
        self.model = model
        pass
    @staticmethod
    def conv(convnet):
        convnet = conv_2d(convnet, 32, 5, activation='relu')
        convnet = max_pool_2d(convnet, 5)

        convnet = conv_2d(convnet, 64, 5, activation='relu')
        convnet = max_pool_2d(convnet, 5)

        convnet = conv_2d(convnet, 128, 5, activation='relu')
        convnet = max_pool_2d(convnet, 5)

        convnet = conv_2d(convnet, 64, 5, activation='relu')
        convnet = max_pool_2d(convnet, 5)

        convnet = conv_2d(convnet, 32, 5, activation='relu')
        convnet = max_pool_2d(convnet, 5)

        convnet = fully_connected(convnet, 1024, activation='relu')
        convnet = dropout(convnet, 0.8)

        convnet = fully_connected(convnet, 2, activation='softmax')
        return convnet
    def load_model(self):
        if os.path.exists('{}.meta'.format(MODEL_DIR+MODEL_NAME)):
            self.model.load(MODEL_DIR+MODEL_NAME)
            print('model loaded!')
            return self.model
    def train(self,epoch,train_data):
        train_data = np.load(TRAIN_DIR+train_data+'.npy') 
        leng = len(train_data)
        train = train_data[:-round(leng * 0.3)]  # 70% <- 트레이닝 데이터
        test = train_data[-round(leng * 0.3):]  # 30% <- 테스트 데이터
        Y = [i[1] for i in train]  # training label
        X = np.array([i[0] for i in train]).reshape(-1, IMG_SIZE, IMG_SIZE, 1)  # training data
        # print(len(self.train_data), "  ", len(train), "  ", len(test))
        test_x = np.array([i[0] for i in test]).reshape(-1, IMG_SIZE, IMG_SIZE, 1)  # test data
        test_y = [i[1] for i in test]  # test label
        self.model.fit({'input': X}, {'targets': Y}, n_epoch=epoch,
                       validation_set=({'input': test_x}, {'targets': test_y}),
                       snapshot_step=500, show_metric=True, run_id=MODEL_NAME)
        self.model.save(MODEL_DIR+MODEL_NAME)
