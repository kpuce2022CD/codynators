from flask import Flask, redirect, render_template, url_for
from DB_handler import DBModule

app = Flask(__name__)
DB = DBModule()
storage = DB.firebase.storage()

username = "khsexk"
filename = "여행을 떠나요" + ".txt"


@app.route("/")
def index():
    path_on_cloud = f"{username}/{filename}"
    path_local = "sample.txt"
    storage.child(path_on_cloud).put(path_local)
    return "Home"


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=8080, debug=True)
