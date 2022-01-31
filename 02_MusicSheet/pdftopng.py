from pdf2image import convert_from_path

images = convert_from_path('sample.pdf', fmt='jpg', output_folder='/Users/khsexk/Desktop/test/jpg', dpi =600, first_page=0, last_page=9)
images[0].save('first_page.jpg')

