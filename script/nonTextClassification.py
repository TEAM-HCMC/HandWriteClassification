#!/usr/bin/env python
# coding: utf-8
import numpy as np
import tflearn
from PIL import Image
import sys
import os
from tflearn.layers.conv import conv_2d, max_pool_2d
from tflearn.layers.core import input_data, dropout, fully_connected
from tflearn.layers.estimator import regression
import tensorflow as tf
IMG_SIZE = 100
import scipy.misc
cnt = 0
LR = 1e-3  #학습률
train_data = np.load('../destination/'+sys.argv[1]+'.npy')
MODEL_DIR = '/home/silmu/app/script/NonTextClassificationModel/model/'
MODEL_NAME ='isItText-{}-{}.model'.format(LR, '2conv-basic')
leng = len(train_data) 
refined_data =[]
tf.reset_default_graph() #커널 상의 값을 초기화
convnet = input_data(shape=[None, IMG_SIZE, IMG_SIZE, 1], name='input')

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
convnet = regression(convnet, optimizer='adam', learning_rate=LR, loss='categorical_crossentropy', name='targets')
model = tflearn.DNN(convnet, tensorboard_dir='log')
print(train_data)
if os.path.exists('{}.meta'.format(MODEL_DIR+MODEL_NAME)):
    model.load(MODEL_DIR+MODEL_NAME)
    print('classifing text nontext')
index=[]
for num,data in enumerate(train_data): 
    img_data = data[0]
    orig = np.asarray(img_data)
    try: 
        data = np.asarray(img_data).reshape(IMG_SIZE,IMG_SIZE,1)
    except Exception :
        index.append(num)
  
    try : model_out = model.predict([data])[0] #예측된 label 값
    except Exception : 
        index.append(num)
        
        print("error")
    if (np.argmax(model_out) ==  0):
        cnt = cnt + 1
        index.append(num)
    #scipy.misc.imsave('../destination/temp/temp_'+str(num)+'.jpg', img_data)
classif = train_data[index]
np.save('../destination/'+sys.argv[1]+'.npy',classif)
print(cnt)
