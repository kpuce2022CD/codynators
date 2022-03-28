# objectProcessing.py
import cv2
import numpy as np
import functions as fs

#위 아래 겹치는 음표 인덱스 재배치
def object_classification(objects):
    axis, cnt = 0, -1
    index = 1
    i = 0
    while i < len(objects)-1:
        line_0, (x_0, y_0, w_0, h_0, area_0) = objects[i][0], objects[i][1] # 현재 value
        line_1, (x_1, y_1, w_1, h_1, area_1) = objects[i + 1][0], objects[i + 1][1] # 다음 value

        if line_0 > cnt:
            axis = y_0
            cnt += 1

        # (x축 비교) obj_0이 obj_1 안에 있거나 obj_1이 obj_0 안에 있을 때
        if x_0 <= x_1 + (w_1 / 2) <= x_0 + w_0 or x_1 <= x_0 + (w_0 / 2) <= x_1 + w_1:
            # 이어진 음표가 있을 경우 겹치는 음표 탐색 후 같은 인덱스 부여
            if w_0 > 70 and y_0 < axis:
                count = 0
                objects[i].append(index)
                for j in range(i + 1, len(objects)):
                    (x, y, w, h, area) = objects[j][1]
                    center = x + (w / 2)
                    if x_0 <= center <= x_0 + w_0:
                        objects[j].append(index)
                        count += 1
                    else:
                        break
                i += count
                index += 1

            # 이어진 음표가 없을 경우 같은 인덱스 부여
            else:
                # 하이햇 오픈 클로즈 객체 처리
                if i < len(objects) - 2:
                    line_2, (x_2, y_2, w_2, h_2, area_2) = objects[i + 2][0], objects[i + 2][1]
                    # 객체가 한 y축 직선상 세 개가 있는 경우
                    if x_1 <= x_2 + (w_2 / 2) <= x_1 + w_1 or x_2 <= x_1 + (w_1 / 2) <= x_2 + w_2:
                        objects[i].append(index)
                        objects[i + 1].append(index)
                        objects[i + 2].append(index)
                        index += 1
                        i += 2
                    else:
                        objects[i].append(index)
                        objects[i + 1].append(index)
                        index += 1
                        i += 1
                else:
                    objects[i].append(index)
                    objects[i + 1].append(index)
                    index += 1
                    i += 1
        else:
            objects[i].append(index)
            index += 1

        i += 1

    # 마지막 객체가 index가 안 들어갔을 경우 예외 처리
    if len(objects[-1]) == 3:
        objects[-1].append(index)
    # print('인덱스 재배열', objects)
    return objects



def object_detection(image, staves):
    mask = np.zeros(image.shape, np.uint8)
    lines = int(len(staves) / 5)  # 보표의 개수
    objects = []  # 구성요소 정보가 저장될 리스트

    # drum 글자 없애기
    cnt, labels, stats, centroids = cv2.connectedComponentsWithStats(image)  # 모든 객체 검출하기
    for i in range(1, cnt):
        (x, y, w, h, area) = stats[i]
        if w == 127 and h == 36:
            continue
        cv2.rectangle(mask, (x, y, w, h), (255, 0, 0), -1)

    image = cv2.bitwise_and(image, mask)
    closing_image = fs.closing(image)
    cnt, labels, stats, centroids = cv2.connectedComponentsWithStats(closing_image)

    for i in range(1, cnt):
        (x, y, w, h, area) = stats[i]
        if w >= fs.weighted(5) and h >= fs.weighted(5):  # 악보의 구성요소가 되기 위한 넓이, 높이 조건
            center = fs.get_center(y, h)
            for line in range(lines):
                #print(staves)
                area_top = staves[line * 5] - fs.weighted(150)  # 위치 조건 (상단)
                area_bot = staves[(line + 1) * 5 - 1] + fs.weighted(150)  # 위치 조건 (하단)
                #print("top: ", line, " pix: ", area_top, "      bot: ", line, " pix: ", area_bot, " center: ", center)
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
                    #print("line: ", line)
            # fs.put_text(image, (x, y - fs.weighted(20)))

    objects.sort()  # 보표 번호 → x 좌표 순으로 오름차순 정렬
    #for i, v in enumerate(objects):
    #    print(i,'번: ', v)

    for line in range(lines):
        area_top = staves[line * 5] - fs.weighted(150)  # 위치 조건 (상단)
        area_bot = staves[(line + 1) * 5 - 1] + fs.weighted(150)  # 위치 조건 (하단)
        width = (area_bot - area_top) / 2
    #    print("top: ", line, " pix: ", area_top, "      bot: ", line, " pix: ", area_bot)
    #    print("line: ", line, " / center: ", area_bot-width)
    return image, object_classification(objects)
