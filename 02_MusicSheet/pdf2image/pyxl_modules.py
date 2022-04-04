from openpyxl import Workbook

def init_excel(workbook): # 초기화 함수
    # workbook 생성
    sheet = workbook.active  # workbook의 활성화된 sheet

    # 열 설정
    sheet['A1'] = "Hihat"
    sheet['B1'] = "Snare"
    sheet['C1'] = "Crash"
    sheet['D1'] = "HighTom"
    sheet['E1'] = "MidTom"
    sheet['F1'] = "LowTom"
    sheet['G1'] = "Ride"
    sheet['H1'] = "Base"
    return sheet


# def processing_excel(objects, sheet):
#     for i, obj in enumerate(objects):
#         index = obj[3]
#         if index == 4:
#             if i == 4:
#                 sheet.append([])


#sheet.append([0, 0, 0, 0, 0, 0, 0, 0])
#workbook.save("./source/sample.xlsx")