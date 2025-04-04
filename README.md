

# 🔍 Oracle 인덱스 성능 비교 실험 프로젝트

Spring Boot 기반으로 Oracle DB에서 인덱스 유무에 따른 검색 속도 차이를 직접 체감해보기 위한 실험 프로젝트입니다.  
H2 DB에서는 큰 차이를 느끼지 못했던 인덱스 성능이, Oracle에서는 명확한 차이를 보여주었습니다.

---

## ✅ 프로젝트 정보

- **프로젝트명**: Index Test
- **기술스택**: Java 21, Spring Boot 3.4.4, Spring Web, Lombok, Spring Data JPA, Oracle (Docker), Swagger
- **실험 목적**:
  - 인덱스가 실제로 검색 성능에 얼마나 영향을 주는지 수치로 확인
  - DB 종류에 따라 인덱스 성능 효과가 다르게 나타나는지 비교
  - 실무에서 인덱스를 언제 적용할지 판단 기준 체감

---

## 📦 주요 의존성

```groovy
implementation 'org.springframework.boot:spring-boot-starter-web'
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
implementation 'org.springframework.boot:spring-boot-devtools'
implementation 'org.projectlombok:lombok'
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0'
runtimeOnly 'com.oracle.database.jdbc:ojdbc11'
```

---

## 🔧 실행 방법

```bash
# Oracle 실행 (Docker)
docker run -d -p 1521:1521 --name oracle \
-e ORACLE_PASSWORD=oracle \
gvenzl/oracle-free

# 프로젝트 실행
./gradlew bootRun
```

> 실행 후 Swagger 접속:  
> [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🧪 실험 API

| Method | Endpoint                    | 설명 |
|--------|------------------------------|------|
| GET    | `/benchmark/indexed`        | 인덱스가 설정된 테이블에서 조건 검색, 소요 시간 측정 |
| GET    | `/benchmark/non-indexed`    | 인덱스가 없는 테이블에서 동일 조건으로 검색, 소요 시간 측정 |

---

## 📊 실험 결과 비교

### 조건: `email = "user10000@example.com"`  
→ 10만 개 중 1만 번째 위치 (앞도 뒤도 아닌 중간 정도)

| DB 종류     | 인덱스 유무 | 소요 시간 |
|-------------|-------------|------------|
| H2 (메모리) | ❌ 없음     | 약 70ms     |
| H2          | ✅ 있음     | 약 67ms     |
| Oracle      | ❌ 없음     | 4040ms      |
| Oracle      | ✅ 있음     | 1092ms      |

- **H2 DB**에서는 성능 차이가 거의 없음
- **Oracle DB**에서는 인덱스 유무에 따라 **약 3.7배 차이**

---

## 🧠 느낀 점: "인덱스는 성능의 지름길."

- 10만 개 데이터면 "크게 차이 나겠어?" 싶었지만,  
  **Oracle에서는 인덱스 하나로 3~4배 차이가 발생**해서 꽤 놀라웠음.

- 반면 **H2 DB는 메모리 기반이라 워낙 빨라서 인덱스 유무가 의미 없었음.**

- 인덱스는 내부적으로는 별도의 테이블처럼 관리되며,  
  검색 시 먼저 해당 인덱스만 스캔해서 **결과 후보군을 좁혀주는 방식**이라 빠를 수밖에 없음.

- 하지만 삽입/수정/삭제 시엔 인덱스도 같이 갱신해야 하므로,  
  **무조건 많이 쓰는 건 오히려 성능에 독이 될 수 있음.**

- 결국 느낀 건,  
  **"언제 인덱스를 적용할 것인가"에 대한 판단 기준을 잡는 것이 중요하다**는 것.

- 실무에서는 데이터 규모와 DB 종류, 쿼리 패턴 등을 고려해 전략적으로 적용해야 함을 체감함.

---
