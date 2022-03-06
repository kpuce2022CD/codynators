from pdf2image import convert_from_path
import cv2
import numpy as np
import matplotlib.pyplot as plt

file_name = "gotrip.pdf"


pages = convert_from_path("./musicsheet/" + file_name)

for i, page in enumerate(pages):
    page.save("./source/" + file_name + str(i) + ".jpg", "JPEG")
    image = cv2.imread("./source/" + file_name + str(i) + ".jpg")

    thresh = 0
    maxValue = 255

    th, dst = cv2.threshold(image, thresh, maxValue, cv2.THRESH_BINARY)

    plt.figure(figsize=(14, 5))
    plt.subplot(121), plt.imshow(image, cmap='gray'), plt.title('original image', fontsize=15)
    plt.xticks([]), plt.yticks([])

    plt.subplot(122), plt.imshow(dst, cmap='gray'), plt.title('filtered image', fontsize=15)
    plt.xticks([]), plt.yticks([])
    plt.show()

