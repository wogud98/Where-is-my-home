# Where-is-my-home
SSAFY 10th 1학기 파이널 프로젝트

## 환경 변수

키는 커밋하지 않고 각 폴더의 `.env`에 넣습니다. `.env`는 git에서 무시됩니다.

`backend/.env`
```
DB_USERNAME=
DB_PASSWORD=
JWT_SECRET=   # base64, 32바이트 이상
```

`frontend/.env`
```
VITE_KAKAO_MAP_SERVICE_KEY=    # Kakao JavaScript 키
VITE_KAKAO_REST_SERVICE_KEY=   # Kakao REST API 키
VITE_VUE_API_URL=http://localhost:80
VITE_ARTICLE_LIST_SIZE=20
VITE_ARTICLE_NAVIGATION_SIZE=5
```
