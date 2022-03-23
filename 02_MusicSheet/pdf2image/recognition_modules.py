import functions as fs
import cv2


def recognize_key(image, staves, stats):
    (x, y, w, h, area) = stats
    ts_conditions = (
        staves[0] + fs.weighted(5) >= y >= staves[0] - fs.weighted(5) and  # 상단 위치 조건
        staves[4] + fs.weighted(5) >= y + h >= staves[4] - fs.weighted(5) and  # 하단 위치 조건
        staves[2] + fs.weighted(5) >= fs.get_center(y, h) >= staves[2] - fs.weighted(5) and  # 중단 위치 조건
        fs.weighted(18) >= w >= fs.weighted(10) and  # 넓이 조건
        fs.weighted(45) >= h >= fs.weighted(35)  # 높이 조건
    )
    if ts_conditions:
        return True, 0
    else:  # 조표가 있을 경우 (다장조를 제외한 모든 조)
        stems = fs.stem_detection(image, stats, 20)
        if stems[0][0] - x >= fs.weighted(3):  # 직선이 나중에 발견되면
            key = int(10 * len(stems) / 2)  # 샾
        else:  # 직선이 일찍 발견되면
            key = 100 * len(stems)  # 플랫

    return False, key



def recognize_note(image, staff, stats, stems, direction):
    x, y, w, h, area = stats
    if len(stems):
        fs.put_text(image, w, (x, y + h + fs.weighted(30)))
        fs.put_text(image, h, (x, y + h + fs.weighted(60)))
        fs.put_text(image, fs.count_rect_pixels(image, (x, y, w, h)), (x, y + h + fs.weighted(90)))

    pass