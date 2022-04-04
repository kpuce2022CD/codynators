import cv2
import numpy as np
import functions as fs
import recognition_modules as rs

def recognition(image, staves, objects):
    key = 0
    time_signature = False
    beats = []  # 박자 리스트
    pitches = []  # 음이름 리스트

    for i in range(0, len(objects)):
        obj = objects[i]
        line = obj[0]
        stats = obj[1]
        index = obj[3]
        stems = obj[4]
        direction = obj[5]
        (x, y, w, h, area) = stats
        staff = staves[line * 5: (line + 1) * 5]
        rs.recognize_note(image, staff, stats, stems, direction)
        if not time_signature:  # 조표가 완전히 탐색되지 않음 (아직 박자표를 찾지 못함)
            ts, temp_key = rs.recognize_key(image, staff, stats)
            time_signature = ts
            key += temp_key
            if time_signature:
                fs.put_text(image, key, (x, y + h + fs.weighted(20)))
        else:  # 조표가 완전히 탐색되었음
            rs.recognize_note(image, staff, stats, stems, direction)

        cv2.rectangle(image, (x, y, w, h), (255, 0, 0), 1)
        fs.put_text(image, index, (x, y - fs.weighted(30)))
    return image, key, beats, pitches



# 3/28 -> stems 인식 문제
def object_analysis(image, objects):
    for obj in objects:
        stats = obj[1]
        stems = fs.stem_detection(image, stats, 67)  # 객체 내의 모든 직선들을 검출함
        direction = None
        if len(stems) > 0:  # 직선이 1개 이상 존재함
            if stems[0][0] - stats[0] >= fs.weighted(5):  # 직선이 나중에 발견되면
                direction = True  # 정 방향 음표
            else:  # 직선이 일찍 발견되면
                direction = False  # 역 방향 음표
        obj.append(stems)  # 객체 리스트에 직선 리스트를 추가
        obj.append(direction)  # 객체 리스트에 음표 방향을 추가

    return image, objects