# slackBot
코로나, 날씨, 미세먼지 API 활용하여 정보 제공 및 점심 및 저녁 추천.


### API 출처
* 코로나 API : [공공데이터활용지원센터_보건복지부 코로나19 감염 현황](https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15043376)


* 날씨 API : [기상청_동네예보 조회서비스](https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15057682)


* 미세먼지 API : [한국환경공단_대기오염정보](https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15073861)
  
### 동작화면

1. 코로나 알림

![image](https://user-images.githubusercontent.com/59398492/120690567-5902b480-c4e0-11eb-9292-b03a6a879d34.png)
```
당일 날짜 기준으로 신규확진자수와, 총 확진자수, 총 사망자수 정보를 보내줍니다.
```
2. 날씨 및 미세먼지 알림

![image](https://user-images.githubusercontent.com/59398492/120690678-7afc3700-c4e0-11eb-8f44-07c2506fc27c.png)

```
기상청에서 오전 5시에 발표하는 일일예보를 기준으로 정보를 보내줍니다. 
강수 확률과 미세먼지 농도를 기준으로 특정 임계치를 초과하였다면,
안내메세지를 별도로 보내줍니다.
(시연 목적으로 임계치를 둘다 낮게 설정하였습니다.)
```
3. 식사 추천

![image](https://user-images.githubusercontent.com/59398492/120690848-b139b680-c4e0-11eb-9b90-d0407d4d82f9.png)

![image](https://user-images.githubusercontent.com/59398492/120691026-e940f980-c4e0-11eb-8260-d681e3d5d337.png)

```
점심시간과 저녁시간이 되면 저장된 메뉴 중 전날메뉴를 
(저녁일 경우 당일 추천 받은 점심메뉴까지) 제외하고 추천해줍니다.
또한 당일 추천받은 음식 두가지를 텍스트파일에 담아 파일전송해줍니다. 
```

