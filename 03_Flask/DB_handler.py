import pyrebase
import json


class DBModule:
    # config 변수에 firebase config를 딕셔너리 형태로 
    def __init__(self) -> None:
        with open("./config/firebaseAuth.json") as f:
            config = json.load(f)

        self.firebase = pyrebase.initialize_app(config)

    def login(self):
        pass

    def signin(self):
        pass

    def write_post(self):
        pass

    def post_detail(self):
        pass
