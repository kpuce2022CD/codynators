# objectProcessing.py
import cv2
import numpy as np
import functions as fs


def object_detection(image, staves):
    lines = int(len(staves) / 5)  # 보표의 개수
    objects = []  # 구성요소 정보가 저장될 리스트

    closing_image = fs.closing(image)
    cnt, labels, stats, centroids = cv2.connectedComponentsWithStats(closing_image)  # 모든 객체 검출하기
    for i in range(1, cnt):
        (x, y, w, h, area) = stats[i]
        if w >= fs.weighted(5) and h >= fs.weighted(5):  # 악보의 구성요소가 되기 위한 넓이, 높이 조건
            center = fs.get_center(y, h)
            for line in range(lines):
                print(staves)
                area_top = staves[line * 5] - fs.weighted(150)  # 위치 조건 (상단)
                area_bot = staves[(line + 1) * 5 - 1] + fs.weighted(150)  # 위치 조건 (하단)
                print("top: ", line, " pix: ", area_top, "      bot: ", line, " pix: ", area_bot, " center: ", center)
                aver = ((area_bot - center) + (center - area_top)) / 2

                if area_top <= center <= area_bot:
                    # 1. (x,y,w,h,area)를 가지고 있는 객체가 없다면
                    flag = False
                    for index, obj in enumerate(objects):
                        if (x, y, w, h, area) == obj[1]:
                            flag = True
                            if obj[2] > aver:
                                objects[index] = [line, (x, y, w, h, area), aver]
                                break

                    if flag is False:
                        objects.append([line, (x, y, w, h, area), aver])  # 객체 리스트에 보표 번호와 객체의 정보(위치, 크기)를 추가
                    # 2. 있다면 aver 비교 (작은 값을 넣어준다)
                    print("line: ", line)
        # fs.put_text(image, i, (x, y - fs.weighted(20)))

    objects.sort()  # 보표 번호 → x 좌표 순으로 오름차순 정렬
    print(objects)

    # for obj in objects:
    #     l, (x, y, w, h, area), aver = obj
    #     fs.put_text(image, l, (x, y - fs.weighted(20)))

    return image, objects
