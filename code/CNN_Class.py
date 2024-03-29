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
TRAIN_DIR = './object/Text/'
TEST_DIR = './object/test'
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
    def process_test_data(self):
        try:
            testing_data = []
            for img in tqdm(os.listdir(TEST_DIR)):
                path = os.path.join(TEST_DIR, img)
                img_name = img.split('_')[0]
                img = cv2.imread(path, cv2.IMREAD_GRAYSCALE)
                img = cv2.resize(img, (IMG_SIZE, IMG_SIZE))
                testing_data.append([np.array(img), img_name])

            shuffle(testing_data)
            np.save('./npy/test_data.npy', testing_data)
            return testing_data
        except Exception:
            os.remove(path)
    def create_train_data(self):
        training_data = []
        try:
            for img in tqdm(os.listdir(TRAIN_DIR)):
                path = os.path.join(TRAIN_DIR, img)
                img_name = img.split('_')[0]
                img = cv2.imread(path, cv2.IMREAD_GRAYSCALE)
                img = cv2.resize(img, (IMG_SIZE, IMG_SIZE))
                if img_name == 'pci' :
                    label=[1,0]
                else :
                    label=[0,1]
                training_data.append([np.array(img), np.array(label)])
            shuffle(training_data)
            np.save('./npy/training_data.npy', training_data)
            return training_data
        except Exception:  ##file에 관련한 에러 발생시 해당 파일 삭제
            os.remove(path)
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
    def train(self,epoch):
        train_data = self.create_train_data()
        print(train_data)
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