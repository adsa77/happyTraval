# 여행한가득

> "여행한가득"은 다양한 숙소 옵션과 사용자 친화적인 예약 기능을 제공하는 숙박 중계 플랫폼입니다. 투숙객 리뷰와 평점을 반영한 신뢰성 높은 숙박 정보를 제공하며, 프로모션과 할인 혜택을 통해 여행 비용 절감을 돕습니다. 안전한 결제 시스템을 통해 개인정보 보호와 편리한 예약 환경을 보장합니다.
> 

**플로젝트명**   여행한가득

**주제**                숙박 예약 프로그램

**기간**                2024.09.01 ~ 2024.12.31

**ERDCloud**     [여행한가득_손지욱](https://www.erdcloud.com/d/mchdrpphGjdmSytsD)

**Git Hub**          https://github.com/adsa77/happyTraval

---

[기획의도](https://www.notion.so/10d23c81b09780b2b4b7f6e94940a6cc?pvs=21)

[구현기능](https://www.notion.so/10d23c81b0978081935ffc1275f4211f?pvs=21)

[개발 기획서(작성 중)](https://www.notion.so/13f23c81b0978083a80cc88ed9b76f14?pvs=21)

---

# 개발 환경

- **프로그래밍 언어**
    - **백엔드**: Java
    - **프론트엔드**: JavaScript, HTML5, jQuery, AJAX, JSON
- **개발 및 코드 관리 도구**
    - **통합 개발 환경 (IDE)**: IntelliJ IDEA
    - **코드 편집기**: Visual Studio Code
- **데이터베이스 및 데이터 관리 도구**
    - **데이터베이스**: MySQL
    - **DB 관리 도구**: MySQL Workbench
    - **데이터 모델링**: ERD Cloud
- **프레임워크 및 기술 스택**
    - **백엔드 프레임워크**: Spring Framework, Spring Boot
    - **보안 및 인증**: Spring Security, JWT
    - **영속성 관리**: JPA (Java Persistence API)
    - **웹 개발**: JSP, Servlet
- **디자인 및 UI/UX 도구**
    - **디자인 툴**: Figma
- **API 개발 및 테스트**
    - **API 테스트 및 문서화**: Postman, Swagger

---

# 아키텍처 설명

- **클라이언트-서버 아키텍처**: 사용자는 웹 또는 모바일 클라이언트에서 숙소 정보를 검색하고 예약하며, 서버는 이 요청을 처리하고 MySQL 데이터베이스에서 정보를 조회합니다.
- **REST API**: 클라이언트는 백엔드 서버와 통신할 때 REST API를 사용하여 데이터를 주고받습니다.
- **JWT 인증**: 로그인 및 회원가입 기능에서 JWT를 사용해 사용자 인증을 관리합니다.
- **JPA 사용**: 데이터베이스와의 상호작용을 관리하기 위해 JPA를 활용해 객체 관계 매핑을 처리합니다.
