# 설계 (UML)

[README로 돌아가기](../README.md)


## 클래스 다이어그램 (백엔드)

기능마다 Controller → Service → Mapper(MyBatis XML) 3계층으로 나뉩니다.

```mermaid
classDiagram
    direction LR

    class MemberController {
        +idCheck(userId)
        +join(MemberDto)
        +login(Map)
        +edit(MemberDto)
        +delete(userId)
    }
    class MemberService {
        <<interface>>
        +idCheck(userId) int
        +joinMember(MemberDto)
        +loginMember(Map) Map
        +getMember(userId) MemberDto
        +updateMember(MemberDto)
        +deleteMember(userId)
    }
    class MemberMapper {
        <<Mapper>>
        +loginMember(Map) MemberDto
    }
    class JWTUtil {
        +generateToken(MemberDto) String
        +validateToken(token)
    }
    class MemberDto {
        userId
        password
        name
        registerDate
    }
    MemberController --> MemberService
    MemberService <|.. MemberServiceImpl
    MemberServiceImpl --> MemberMapper
    MemberServiceImpl --> JWTUtil
    MemberMapper ..> MemberDto

    class BoardController {
        +writeArticle(BoardDto)
        +listArticle(Map)
        +getArticle(articleNo)
        +modifyArticle(BoardDto)
        +deleteArticle(articleNo)
    }
    class BoardService {
        <<interface>>
        +writeArticle(BoardDto)
        +listArticle(Map) BoardListDto
        +getArticle(articleNo) BoardDto
        +updateHit(articleNo)
        +modifyArticle(BoardDto)
        +deleteArticle(articleNo)
    }
    class BoardMapper {
        <<Mapper>>
        +listArticle(Map) List~BoardDto~
        +getTotalArticleCount(Map) int
    }
    class BoardDto {
        articleNo
        userId
        subject
        content
        hit
        registerTime
    }
    class BoardListDto {
        articles List~BoardDto~
        currentPage
        totalPageCount
    }
    BoardController --> BoardService
    BoardService <|.. BoardServiceImpl
    BoardServiceImpl --> BoardMapper
    BoardListDto o-- BoardDto

    class HappyHouseController {
        +listArticle(Map)
        +getAreaKeyword(word)
        +getAptInfoList(Map)
    }
    class HappyHouseService {
        <<interface>>
        +listArticle(Map) HouseSearchListDto
        +getAreaKeyword(word) List
        +getAptInfoList(Map) AptInfoListDto
    }
    class HouseMapper {
        <<Mapper>>
        +listArticle(Map) List
        +getAreaKeyword(Map) List
        +getAptInfoList(Map) List
    }
    HappyHouseController --> HappyHouseService
    HappyHouseService <|.. HappyHouseServiceImpl
    HappyHouseServiceImpl --> HouseMapper
```

## ERD

`board.user_id`와 `member.user_id`는 같은 값을 쓰지만 DB에 외래 키로 걸려 있지는 않습니다.
매물 테이블(`dongcode`, `houseinfo`, `housedeal`)은 주요 컬럼만 적었습니다.

```mermaid
erDiagram
    member ||--o{ board : "작성 (FK 없음)"
    dongcode ||--o{ houseinfo : dongCode
    houseinfo ||--o{ housedeal : aptCode

    member {
        varchar user_id PK
        varchar password
        varchar name
        timestamp register_date
    }
    board {
        int article_no PK
        varchar user_id
        varchar subject
        varchar content
        int hit
        timestamp register_time
    }
    dongcode {
        varchar dongCode PK
        varchar sidoName
        varchar gugunName
        varchar dongName
    }
    houseinfo {
        bigint aptCode PK
        varchar apartmentName
        int buildYear
        varchar roadName
        varchar dongCode FK
        varchar lat
        varchar lng
    }
    housedeal {
        bigint no PK
        bigint aptCode FK
        varchar dealAmount
        int dealYear
        int dealMonth
        int dealDay
        varchar area
        varchar floor
    }
```

## 시퀀스 다이어그램 (로그인)

```mermaid
sequenceDiagram
    actor U as 사용자
    participant V as LoginForm.vue / auth.js
    participant C as MemberController
    participant S as MemberServiceImpl
    participant M as MemberMapper (Member.xml)
    participant J as JWTUtil
    participant D as MySQL

    U->>V: 아이디, 비밀번호 입력
    V->>C: POST /api/user/login
    C->>S: loginMember(map)
    S->>M: loginMember(map)
    M->>D: SELECT ... FROM member WHERE user_id, password
    D-->>M: 회원 정보
    M-->>S: MemberDto
    S->>J: generateToken(member)
    J-->>S: JWT
    S-->>C: { token }
    C-->>V: 200 OK (실패 시 401)
    V->>V: jwtDecode로 이름 저장 (localStorage 유지)
```

## 시퀀스 다이어그램 (매물 검색)

```mermaid
sequenceDiagram
    actor U as 사용자
    participant V as SearchView.vue / house.js
    participant C as HappyHouseController
    participant S as HappyHouseServiceImpl
    participant M as HouseMapper (House.xml)
    participant D as MySQL
    participant K as Kakao (장소 검색, 지도)

    U->>V: 장소 키워드 입력
    V->>K: 키워드 검색 (로컬 API)
    K-->>V: 좌표
    V->>C: GET /api/house (지도 중심 좌표, 범위)
    C->>S: listArticle(map)
    S->>M: listArticle, getTotalArticleCount
    M->>D: houseinfo JOIN housedeal (위경도 거리 계산)
    D-->>M: 매물 목록
    M-->>S: List
    S-->>C: HouseSearchListDto (페이징)
    C-->>V: JSON
    V->>K: 좌표로 마커 표시
```
