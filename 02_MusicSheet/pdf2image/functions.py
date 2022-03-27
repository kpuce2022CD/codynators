import cv2
import numpy as np

VERTICAL = True
HORIZONTAL = False

def threshold(image):
    image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    ret, image = cv2.threshold(image, 127, 255, cv2.THRESH_BINARY_INV | cv2.THRESH_OTSU)
    return image

def weighted(value):
    standard = 150
    return int(value * (standard / 150))

# 닫힘 연산: 객체부분을 넓혔다가 다시 원래대로 돌림으로써 객체안에 존재하는 노이즈를 제거하는 연산
def closing(image):
    kernel = np.ones((weighted(5), weighted(5)), np.uint8)
    image = cv2.morphologyEx(image, cv2.MORPH_CLOSE, kernel)
    return image

# 크기별 숫자 넣기
def put_text(image, text, loc):
    font = cv2.FONT_HERSHEY_SIMPLEX
    cv2.putText(image, str(text), loc, font, 0.6, (255, 0, 0), 2)

def get_center(y, h):
    return (y + y + h) / 2

# 같은 객체임에도 픽셀이 끊어지거나 다른 객체로 검출될 수 있기 때문에 함수 선언
def closing(image):
    kernel = np.ones((weighted(5), weighted(5)), np.uint8)
    image = cv2.morphologyEx(image, cv2.MORPH_CLOSE, kernel)
    return image

# 사각형 형태의 구역에 픽셀이 몇 개 존재하는지 카운트해주는 함수
def count_pixels(image, rect):
    x, y, w, h = rect
    pixels = 0
    for row in range(y, y + h):
        for col in range(x, x + w):
            if image[row][col] == 255:
                pixels += 1
    return pixels

# 객체에 직선 성분이 존재하는지 체크 -> 존재한다면 음표로 가정하고 인식에 쓰일 특징점을 추출
# image = 이미지, axis = 축, axis_value = 고정된 축의 값, start, end = 선을 검출할 범위, length = 선의 최소 길이 조건
def get_line(image, axis, axis_value, start, end, length):
    if axis:
        points = [(i, axis_value) for i in range(start, end)]  # 수직 탐색
    else:
        points = [(axis_value, i) for i in range(start, end)]  # 수평 탐색
    pixels = 0
    for i in range(len(points)):
        (y, x) = points[i]
        pixels += (image[y][x] == 255)  # 흰색 픽셀의 개수를 셈
        next_point = image[y + 1][x] if axis else image[y][x + 1]  # 다음 탐색할 지점
        if next_point == 0 or i == len(points) - 1:  # 선이 끊기거나 마지막 탐색임
            if pixels >= weighted(length):
                break  # 찾는 길이의 직선을 찾았으므로 탐색을 중지함
            else:
                pixels = 0  # 찾는 길이에 도달하기 전에 선이 끊김 (남은 범위 다시 탐색)
    return y if axis else x, pixels

# axis true 세로로 된 직선 검출 false 가로로 된 직선 검출
# 3/28 stem_detection에서 stems 인식 문제
def stem_detection(image, stats, length):
    (x, y, w, h, area) = stats
    stems = []  # 기둥 정보 (x, y, w, h)
    for col in range(x, x + w):
        end, pixels = get_line(image, VERTICAL, col, y, y + h, length)  # getline함수를 통해 직선 검출
        if pixels: # pixels 값 존재한다면
            if len(stems) == 0 or abs(stems[-1][0] + stems[-1][2] - col) >= 1:
                (x, y, w, h) = col, end - pixels + 1, 1, pixels
                stems.append([x, y, w, h])
            else:
                stems[-1][2] += 1
    print('stem_detection', stems)
    return stems