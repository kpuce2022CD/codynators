from pdf2image import convert_from_path
import matplotlib.pyplot as plt

pdf_path = './gooday.pdf'
image_list = convert_from_path(pdf_path)

page_no = 1
image = image_list[page_no - 1]

plt.figure(figsize=(16, 10))
plt.imshow(image)
plt.show()

