## 기본 설정
### POM 설정
```
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <version>1.4.200</version>
    <scope>compile</scope>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
    <version>2.4.2</version>
</dependency>
```

### resource/application.yaml 설정
```
server:
  port: 8000

spring:
  h2:
    console:
      enabled: true
      path: /h2_db

  datasource:
    driver-class-name: org.h2.Driver
    url: jdbc:h2:file:./h2_db;AUTO_SERVER=TRUE
    username: sunny
    password: 1234
```

### H2 접속 확인
* http://localhost:8000/h2_db

## 엔티티 매핑

### primary key
* @Id @GeneratedValue

### 컬럼 명 변경
* @Column(name="ORDER_ID")

### enum
* @Enumerated(EnumType.STRING)

### Date 설정
1. date 타입일 경우 : @Temporal
2. java8 이후의 localDate, localDateTime 등등 일 경우
    * jpa 2.2 버전 이하
        - Conveter 작성
        ```java
            import java.time.LocalDateTime;
            import java.util.Date;

            import javax.persistence.AttributeConverter;
            import javax.persistence.Converter;

            import static java.time.Instant.ofEpochMilli;
            import static java.time.LocalDateTime.ofInstant;
            import static java.time.ZoneId.systemDefault;

            @Converter
            public class LocalDateTimePersistenceConverter implements AttributeConverter<LocalDateTime, Date> {

                @Override
                public Date convertToDatabaseColumn(LocalDateTime localDateTime) {
                    return Date.from(localDateTime.atZone(systemDefault()).toInstant());
                }

                @Override
                public LocalDateTime convertToEntityAttribute(Date date) {
                    return ofInstant(ofEpochMilli(date.getTime()), systemDefault());
                }
            }
                    ```
                    
        - @Convert(converter = LocalDateTimePersistenceConverter.class)

    * jpa 2.2 버전 이상
        - @Column(name = "ORDERDATE", columnDefinition = "TIMESTAMP")
        
## 연관관계 매핑

### 객체 연관관계 vs 테이블 연관관계
* 객체
    - 참조(주소)로 연관관계를 맺는다.
    - 단방
* 테이블
    - 외래 키로 연관관계를 맺는다.
    - 양방향

### 연관관계 매핑 어노테이션
* @JoinColumn
    - name
        * 매핑할 외래 키 이름
        * 기본값 : 필드명 + _ + 참조하는 테이블의 키본키 컬럼명
    - referencedColumnName
        * 외래 키가 참조하는 대상 테이블의 컬럼명
        * 기본값 : 참조하는 테이블의 기본 키 컬럼명
    - foreignKey(DDL)
        * 외래 키 제약조건을 직접 지정할 수 있음.
        * 테이블을 생성할 때만 사용
    - unique, nullable, insertable, updatable, columnDefinition, table
        * @Column의 속성과 같음.
    > @JoinColumn 생략하면 외래 키를 찾을 때 기본 전략을 사용

* @ManyToOne
    - optional
        * false 설정 시 연관된 엔티티가 항상 있어야 함
        * 기본값 : true
    - fatch
        * 글로벌 패치 전략 설정
    - cascade
        * 영속성 전이 기능 사용
    - targetEntity
        * 연관된 엔티티의 타입 정보 설정
        * 거의 사용하지 않음요
    > mapperBy 속성 사용 불가능
        
### mappedBy 속성
* 양방향 매핑일 때 사용함
* 반대쪽 매핑의 필드 이름을 값으로 주면 됨.
* 테이블은 외래 키 하나로 두 테이블의 연관관계를 관리. 
    - 두 객체 연관관계 중 하나를 정해서 테이블의 외래키를 관리해야 하는데 이것을 연관관계의 주인이라 함.
    - 연관관계의 주인만이 데이터베이스 연관관계와 매핑 됨
    - 외래 키를 관리(등록, 수정, 삭제) 할 수 있음
    - 주인이 아닌 쪽은 읽기만 가능
    - 주인은 mapperBy 속성 사용 불가

### 양방향 연관 관계
1. 연관 관계 매핑 어노테이션 설정
2. 연관관계 편의 메소드 추가
    * 기존 팀이 있으면 삭제를 하는 로직 추가 필
