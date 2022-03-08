from pdf2image import convert_from_path
import cv2
import preprocessing
import numpy as np
import matplotlib.pyplot as plt
import functions as fs
import os

file_name = "gotrip.pdf"

pages = convert_from_path("./musicsheet/" + file_name)

for i, page in enumerate(pages):
    file = "./source/" + file_name + str(i) + ".jpg"
    page.save(file, "JPEG")

    #file2 = "./source/sample.png"

    image = cv2.imread(file)

    masked_image = preprocessing.rmNoise(image)

    #noStaves_img, staves = preprocessing.rmStaves(masked_image)

    #normal_MS, staves = preprocessing.musicsheetNormalization(noStaves_img, staves, 30)

    #cv2.imshow('image', normal_MS)
    cv2.imwrite("./source/" + "masktest" + str(i) + ".png", masked_image)
    print("exit")


