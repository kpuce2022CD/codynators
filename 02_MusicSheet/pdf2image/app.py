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
    findObject, objects = objectProcessing.object_detection(normal_MS, staves)
    # cv2.connectedComponentsWithStats(src, label, stats, centroids)
    # src 입력 이미지, labels: 레이블 맵 행렬, stats: connected components를 감싸는 직사각형 및 픽셀 정보를 담고 있음 centroids: 각 connected components의 무게 중심 위치
    # cv2.rectangle(img, pt1, pt2, color, thickness)
    # img : 이미지 파일, pt1: 시작점 좌표, pt2: 종료점 좌표 color: 색상 , thickness : 선 두께

    # 5. 객체 분석 과정
    for obj in objects:
        stats = obj[1]
        stems = fs.stem_detection(normal_MS, stats, 30)  # 객체 내의 모든 직선들을 검출함
        direction = None
        if len(stems) > 0:  # 직선이 1개 이상 존재함
            if stems[0][0] - stats[0] >= fs.weighted(5):  # 직선이 나중에 발견되면
                direction = True  # 정 방향 음표
            else:  # 직선이 일찍 발견되면
                direction = False  # 역 방향 음표
        obj.append(stems)  # 객체 리스트에 직선 리스트를 추가
        obj.append(direction)  # 객체 리스트에 음표 방향을 추가

    for obj in objects:
        (x, y, w, h, area) = obj[1]
        if len(obj[2]):
            fs.put_text(normal_MS, len(obj[2]), (x, y + h + 20))

    cv2.imwrite("./source/" + "검출" + str(i) + ".png", normal_MS)
    print("exit")


