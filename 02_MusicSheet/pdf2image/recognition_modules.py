import functions as fs


def recognize_note(image, staff, stats, stems, direction):
    x, y, w, h, area = stats
    if len(stems):
        fs.put_text(image, w, (x, y + h + fs.weighted(30)))
        fs.put_text(image, h, (x, y + h + fs.weighted(60)))
        fs.put_text(image, fs.count_rect_pixels(image, (x, y, w, h)), (x, y + h + fs.weighted(90)))

    pass