import cv2
import numpy as np
import matplotlib.pyplot as plt
import sys
from random import shuffle
import os


class contour:
    def __init__(self, basename, filename):
        self.basename = basename
        self.filename = filename
        self.image = cv2.imread("../originalSource/" + str(self.filename) + ".jpg", cv2.IMREAD_COLOR)
        self.img = cv2.imread("../originalSource/" + str(self.filename) + ".jpg", cv2.IMREAD_GRAYSCALE)
        self.img_name = filename.split('/')[1]
        self.data = []

    def imageSet(self):
        grayscaled = cv2.cvtColor(self.image, cv2.COLOR_BGR2GRAY)

        size = 10
        kernel = np.ones((size, size), np.float32)

        morph_Gradient_Gray = cv2.morphologyEx(grayscaled, cv2.MORPH_OPEN, kernel)

        adaptive_threshold = cv2.adaptiveThreshold(morph_Gradient_Gray, 255, cv2.ADAPTIVE_THRESH_MEAN_C,
                                                   cv2.THRESH_BINARY_INV, 5, 5)

        morph_Close_threshold = cv2.morphologyEx(adaptive_threshold, cv2.MORPH_CLOSE, kernel)

        dilate = cv2.dilate(morph_Close_threshold, kernel, iterations=2)
        edged = cv2.Canny(dilate, 50, 150)
        return edged

    def saveObject(self):
        t = 1
        contours, hierarchy = cv2.findContours(self.imageSet(), cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

        for contour in contours:
            (x, y, w, h) = cv2.boundingRect(contour)
            if (w > 10 and h > 10) and (w < 400 and h < 400):
                # cv2.imwrite("../destination/" + str(self.filename) + '_' + str(t) + ".jpg", self.img[y:y + h, x:x + w])
                self.data.append([np.array(cv2.resize(self.img[y:y + h, x:x + w], (100, 100)))])
                t = t + 1
        shuffle(self.data)
        np.save('../destination/' + self.basename + '.npy', self.data)

    def duplicateData(self):

        if os.path.exists('{}.npy'.format('../destination/' + self.basename)):
            self.data = np.load('../destination/' + self.basename + '.npy').tolist()
            # self.data.append(temp.tolist())
        self.saveObject()


a = contour(sys.argv[1], sys.argv[2])
a.duplicateData()
