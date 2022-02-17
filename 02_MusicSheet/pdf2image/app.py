from pdf2image import convert_from_path

file_name = "gooday.pdf"

pages = convert_from_path("./" + file_name)

for i, page in enumerate(pages):
    page.save("./source/"+file_name+str(i)+".jpg", "JPEG")