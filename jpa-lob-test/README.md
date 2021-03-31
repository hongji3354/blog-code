# @Lob 사용시 DB별 Data Type

> 코드는 [Github]()에 있습니다.

## 1. 환경

- JDk 11
- Spring Boot 2.4.2
- Oracle-xe-11g
- MySQL 8.0.23
- MariaDB 10.5.8
- PostgreSQL 13.1

## 2. @Lob

일반적으로 VARCHAR보다 큰 데이터를 저장하고자 할 시 @Lob를 사용하는데, @Lob사용시 DB별 Data Type은 아래와 같습니다.

> Java에서 데이터 타입은 String에서만 테스트 했습니다.

```java
@Getter
@setter
@Entity
public class Foo {

    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private String lob;
}
```

위의 Entity를 기반으로 생성되는 테이블 구조를 확인해 보면 아래와 같은 Data Type을 확인할 수 있습니다.

|DB|Data Type|
|:---:|:---:|
|Oracle-xe-11g|CLOB|
|MySQL 8.0.23|longtext|
|MariaDB 10.5.8|longtext|
|PostgreSQL 13.1|text|