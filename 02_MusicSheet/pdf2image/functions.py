import cv2
import numpy as np


def threshold(image):
    image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    ret, image = cv2.threshold(image, 127, 255, cv2.THRESH_BINARY_INV | cv2.THRESH_OTSU)
    return image

def weighted(value):
    standard = 10
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