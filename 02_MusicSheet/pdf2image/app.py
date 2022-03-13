from pdf2image import convert_from_path
import cv2
import preprocessing
import objectProcessing
import numpy as np
import matplotlib.pyplot as plt
import functions as fs
import os

file_name = "gotrip.pdf"

pages = convert_from_path("./musicsheet/" + file_name)

for i, page in enumerate(pages):
    file = "./source/" + file_name + str(i) + ".jpg"
    page.save(file, "JPEG")

    image = cv2.imread(file)

    masked_image = preprocessing.rmNoise(image)

    noStaves_img, staves = preprocessing.rmStaves(masked_image)

    normal_MS, staves = preprocessing.musicsheetNormalization(noStaves_img, staves, 150)
    cv2.imwrite("./source/" + "정규화" + str(i) + ".png", normal_MS)

    # 4. 객체 검출 과정
    findObject, objects = objectProcessing.object_detection(normal_MS, staves)

    cv2.imwrite("./source/" + "find" + str(i) + ".png", findObject)
    print("exit")


