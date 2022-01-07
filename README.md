<p align="right"><img src="https://user-images.githubusercontent.com/56003992/138816338-49083cd9-569a-4f34-b001-ba6584ae2a2e.png"  width="192" height="133"></p>

> **Come Up With Ideas💡** 

2020년 시즌1에 이어 2021년 시즌2까지 <슬기로운 의사생활>이라는 드라마(이하 '슬의생')가 국내에서 인기를 끌었다. <슬의생>에서 나오는 주연 의사들은 취미생활로 밴드 활동을 하는데, 드라마가 흥행한 만큼 <슬의생> 배우들의 COVER한 밴드 음악들도 인기를 끌었고, 밴드 또한 주목을 받았다. 밴드하면 우리는 기타를 먼저 떠올리지만, 드럼은 기타만큼이나 밴드에서 중요한 포지션을 맡고 있다. 하지만 주변에서 드러머(: Drummer)를 찾기는 힘들 것이다. 드럼은 초보자가 시작하기에 가격이 비싸고, 이러한 문제점의 솔루션으로 비교적 값 싼 전자드럼이 출시됐지만, 드럼 연주의 난이도와 흥미 등의 문제로 접근성이 낮기 때문이다. 
 
 이런 드럼과 드럼 연주에 대한 단점을 보완하고, 드럼에 대한 접근성을 높이기 위해 AR기술을 이용하여 드럼 연주를 어시스트 해주는 소프트웨어를 개발하자는 아이디어가 도출되었다.

* * *

> **SW Design🪡🧵** 

<p align="center"><img src="https://user-images.githubusercontent.com/56003992/148493823-8cff6056-839a-49a4-87a1-8d28eab45083.png"  width="600" height="280"></p>

 **MODEL**: Business Logic을 처리하는 부분으로, 기본적으로 Android Application이 담당하지만, Unity와 Flask, 그리고 DB가 연동돼있다.
 
 **VIEW**: 화면을 보여주는 부분으로, Android Application이 담당한다.
 
 **CONTROLLER**: 화면을 흐름 제어를 담당하는 부분으로, Android Application에서 메뉴(버튼, 리스트 등)를 통해 이루어진다.


* * *

> **Scenario🎞** 

<p align="center"><img src="https://user-images.githubusercontent.com/56003992/148495180-d9b5c27a-7e70-49ca-b892-b42326893352.png"  width="600" height="280"></p>

 <div align="center"> ☑️ 스마트폰으로 Android Application을 실행
  
 ☑️ HMD 사용 시, HMD를 통해 Android Application 실행 </div>
 
<p align="center"><img src="https://user-images.githubusercontent.com/56003992/148495216-fe4eaa01-6085-4467-84e6-d8e9aaa866ba.png"  width="600" height="280"></p>

<div align="center"> ☑️ Android Application 실행 후 카메라를 통해 드럼을 인식 후 플레이 </div>
 
<p align="center"><img src="https://user-images.githubusercontent.com/56003992/148495234-fc42bbbd-7257-4b0d-9303-344d27c85b3f.png"  width="600" height="280"></p>

<div align="center"> ☑️ 악보를 선택하면 Web Server에서 데이터로 변환 후 다시 Android Application으로 전송
  
☑️ 전자드럼 위에 AR로 박자가 출력 </div>

* * *

> **Skill Stack 🛠**

- **FRAMWORK**
 
![Android Badge](https://img.shields.io/badge/-Android-3DDC84?style=flat&logo=Android&logoColor=FFFFFF) ![Unity Badge](https://img.shields.io/badge/-Unity-000000?style=flat&logo=Unity&logoColor=FFFFFF) ![Flask Badge](https://img.shields.io/badge/-Flask-FFFFFF?style=flat&logo=Flask&logoColor=000000) 

- **LANGUAGE**
 
![Kotlin Badge](https://img.shields.io/badge/-Kotlin-0095D5?style=flat&logo=Kotlin&logoColor=FFFFFF) ![Python Badge](https://img.shields.io/badge/-Python-D3D3D3?style=flat&logo=Python) ![C# Badge](https://img.shields.io/badge/-C%23-000000?style=flat&logo=Csharp) 

- **ETC**
 
![AWS Badge](https://img.shields.io/badge/-AWS-FFD400?style=flat&logo=AmazonAWS&logoColor=232F3E) ![MySQL Badge](https://img.shields.io/badge/-MySQL-D3D3D3?style=flat&logo=MySQL) 

* * *

> **Role 👨‍👨‍👦‍👦**
- **[고현석](https://github.com/khsexk)(팀장)**
- [김태용](https://github.com/YongsHub)(팀원)
- [김현수](https://github.com/KimHacne)(팀원)
- [박승찬](https://github.com/eemdeeks)(팀원)
