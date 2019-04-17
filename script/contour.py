import cv2
import numpy as np
import matplotlib.pyplot as plt
import sys


class contour:
    def __init__(self, filename):
        self.filename = filename
        self.image = cv2.imread("../originalSource/"+str(self.filename) + ".png", cv2.IMREAD_COLOR)
        self.img = cv2.imread("../originalSource/"+str(self.filename) + ".png", cv2.IMREAD_COLOR)

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

    def drawRect(self):
        t = 1
        contours, hierarchy = cv2.findContours(self.imageSet(), cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)

        for contour in contours:
            (x, y, w, h) = cv2.boundingRect(contour)
            if (w > 10 and h > 10) and (w < 400 and h < 400):
                cv2.imwrite("../destination/" + str(self.filename) + '_' + str(t) + ".jpg", self.img[y:y + h, x:x + w])
                t = t + 1
        plt.imshow(self.img)


a = contour(sys.argv[1])
a.drawRect()
