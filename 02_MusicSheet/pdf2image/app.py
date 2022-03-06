from pdf2image import convert_from_path
import cv2
import numpy as np
import matplotlib.pyplot as plt
import functions as fs
import os

file_name = "gotrip.pdf"

pages = convert_from_path("./musicsheet/" + file_name)

for i, page in enumerate(pages):
    file = "./source/" + file_name + str(i) + ".jpg"
    #page.save(file, "JPEG")

    image = cv2.imread(file)
    image = fs.threshold(image)

    cv2.imshow('image', image)
    cv2.imwrite("./source/" + "bnw" + str(i) + ".png", image)


