from pdf2image import convert_from_path
import cv2
import numpy as np
import matplotlib.pyplot as plt

file_name = "gotrip.pdf"

thresh = 0
maxValue = 255

pages = convert_from_path("./musicsheet/" + file_name)

for i, page in enumerate(pages):
    image = cv2.imread('threshold.png')
    page.save("./source/"+file_name+str(i)+".jpg", "JPEG")