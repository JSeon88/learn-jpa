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

## 매핑

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
