# price-search
상품 가격 검색 API

- 사용방법
  1. 어플리케이션 실행 
  2. 브라우저 주소창에 `http://localhost:8080/` 입력
  3. 종류선택 (선택한 종류에 해당하는 이름 목록 생성)
  4. 이름선택
  5. 찾기버튼 클릭
  6. 해당 상품의 가격 제공

- 패키지 구성
  - `com.test.pricesearch.config` 패키지
    - 설정정보 관련 클래스들로 구성
  - `com.test.pricesearch.domain` 패키지
  - `com.test.pricesearch.exception` 패키지
    - 예외처리를 위한 커스텀 예외 클래스로 구성
  - `com.test.pricesearch.handler` 패키지
    - http 요청 처리하는 핸들러 클래스로 구성
  - `com.test.pricesearch.router` 패키지
    - http 요청을 핸들러와 연결해주는 클래스로 구성
  - `com.test.pricesearch.service` 패키지
    - 가격 API 호출을 위한 서비스 클래스들로 구성
  - `com.test.pricesearch.util` 패키지
    - 암복호화를 위한 클래스 포함
  - `test` 폴더
    - 테스트 코드