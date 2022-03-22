import cv2
import numpy as np
import functions as fs
import recognition_modules as rs

def recognition(image, staves, objects):
    key = 0
    time_signature = False
    beats = []  # 박자 리스트
    pitches = []  # 음이름 리스트

    for i in range(1, len(objects)):
        obj = objects[i]
        line = obj[0]
        stats = obj[1]
        stems = obj[2]
        direction = obj[3]
        (x, y, w, h, area) = stats
        staff = staves[line * 5: (line + 1) * 5]
        if not time_signature:  # 조표가 완전히 탐색되지 않음 (아직 박자표를 찾지 못함)
            ts, temp_key = rs.recognize_key(image, staff, stats)
            time_signature = ts
            key += temp_key
        else:  # 조표가 완전히 탐색되었음
            rs.recognize_note(image, staff, stats, stems, direction)

        cv2.rectangle(image, (x, y, w, h), (255, 0, 0), 1)
        fs.put_text(image, i, (x, y - fs.weighted(20)))

    return image, key, beats, pitches