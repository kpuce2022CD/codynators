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
    # 4. 객체 검출 과정
    normal_MS = fs.closing(normal_MS)
    #findObject, objects = objectProcessing.object_detection(normal_MS, staves)
    cnt, labels, stats, centroids = cv2.connectedComponentsWithStats(normal_MS)
    # cv2.connectedComponentsWithStats(src, label, stats, centroids)
    # src 입력 이미지, labels: 레이블 맵 행렬, stats: connected components를 감싸는 직사각형 및 픽셀 정보를 담고 있음 centroids: 각 connected components의 무게 중심 위치
    for i in range(1, cnt):
        (x, y, w, h, area) = stats[i]
        cv2.rectangle(normal_MS, (x, y, w, h), (255, 0, 0), 1)
        # cv2.rectangle(img, pt1, pt2, color, thickness)
        # img : 이미지 파일, pt1: 시작점 좌표, pt2: 종료점 좌표 color: 색상 , thickness : 선 두께

    cv2.imwrite("./source/" + "검출" + str(i) + ".png", normal_MS)
    print("exit")


