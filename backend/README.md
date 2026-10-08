# 부동산 검색 사이트

무엇을 하는 사이트인가요?

 사용자가 원하는 정보를 바탕으로 부동산 정보를 조회하고,
상세조회 시, 타 부동산 사이트들에서 해당 상세조회 매물을 조회한 사이트 링크 제공 

*Spring Boot, MyBatis, REST 

## 요구사항 정리

1. 주택 실거래가 정보 수집
 - 국토교통부 : 실거래가 정보 활용
 - 데이터 가공 후 DB 저장

2. 주택 실거래가 검색
 - 검색 방법
   - 지역(필수선택, select)
   - 동별(선택, select)
   - 키워드(필수입력)
     - 아파트명 

 - 검색결과
   - 매물이름, 가격, 면적, 거래일자, 지역정보
 houseinfo.apartmentName, housedeal.dealAmount, housedeal.area, (housedeal.dealYear+housedeal.dealMonth+housedeal.dealDay), 도로명주소/지번

  - 도로명주소
dongcode.sidoName, dongcode.gugunName,houseinfo.roadName, houseinfo.roadNameBonbun

  - 지번
dongcode.sidoName, dongcode.gugunName, dongcode.dongName, houseinfo.jibun


3. 회원관리
 - member.userid, member.username, member.userpass, member.joinDate, member.address, member.tel
회원정보 등록 : 회원가입
회원정보 수정 : 회원정보 수정
회원정보 삭제 : 회원탈퇴
회원정보 검색 : 마이페이지에서 내 정보 조회

4. 로그인 관리
로그인, 로그아웃, 내 아이디 저장

---

## 화면 구상 정리
 - 메인페이지 : 검색창 이동, 매물추천
   
 - 검색페이지 : 검색 및 결과 출력

 - 상세조회 페이지 : 상세조회, 타 사이트 해당 매물정보 링크 제공
 
 - 로그인페이지 : 사용자 로그인

 - 회원가입페이지 : 사용자 등록
 
 - 마이페이지 : 사용자 정보 조회, 수정, 삭제
  

---

## REST 설계
 
### 부동산 관리
  - (GET) 지역/동 선택, 키워드 매물 조회
    http://localhost:0000/ws/homes/param1/{dongcode.sidoName}/param2/{dongcode.dongName}/param3/{keyword}
  - (GET) 지역 선택, 키워드 매물 조회
    http://localhost:0000/ws/homes2/{dongcode.sidoName}/param2/{keyword}
  - (GET) 상세 조회
    http://localhost:0000/ws/home/{AptCode}

### 회원관리
 - (GET) 회원정보 조회
    http://localhost:0000/ws/user
 - (GET) 로그인
    http://localhost:0000/ws/user/login
 - (POST) 회원정보 등록
    http://localhost:0000/ws/user
 - (DEL) 회원정보 삭제
    http://localhost:0000/ws/user/{userid}
 - (PUT) 회원정보 수정
    http://localhost:0000/ws/user/{userid}


