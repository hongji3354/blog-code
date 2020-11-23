# MapStruct 기본 사용법

MapStruct는 **Object Mapping시 사용되는 라이브러리** 입니다.
MapStruct를 알기 전에는 modelmapper 라이브러리를 사용하였지만 MapStruct가 ModelMapper에 비해 아래와 같은 장점을 가지고 있어서 MapStruct를 사용하게 되었습니다.

1. Google Trands 검색시 **MapStruct의 검색비중이 높습니다.** - [MapStruct와 ModelMapper - Google Trand](https://trends.google.co.kr/trends/explore?q=mapstruct,modelmapper)
2. ModelMapper는 reflection을 사용하기 때문에 성능상 문제가 발생할 수 있으나, **MapStruct는 Compile시 mapping 정보를 가진 클래스를 생성하기 때문에 성능관련 이슈가 발생하지 않습니다.**
3. ModelMapper는 reflection을 사용하기 때문에 Runtime시 오류를 확인할 수 있으나 **MapStruct는 Compile시 오류를 확인**할 수 있습니다.

## 1. 환경
 - Java 11
 - Spring Boot 2.4.0
 - Gradle 6.6.1
 - MapStruct 1.4.1.Final
 - lombok 1.18.12


## 2. 의존성 추가

만약 lombok과 MapStruct를 함께 사용시에는 의존성을 아래과 같이 설정해 주셔야 합니다. 만약 lombok을 1.8.16 이상을 사용하신다면 `lombok-mapstruct-binding` 라이브러리를 추가해 주셔야 합니다.

혹시나 MapStruct나 lombok의 버전이 달라지면 문제가 해결될 수 있으니 세팅시 [Can I use MapStruct together with Project Lombok?](https://mapstruct.org/faq/#Can-I-use-MapStruct-together-with-Project-Lombok) 참고하시면 될 것 같습니다.

일단 해당 포스팅 에서는 MapStruct는 1.4.1.Final을 사용하며, lombok에서는 1.18.12를 사용하겠습니다.

```java
ext {
	mapstructVersion = "1.4.1.Final"
	lombokVersion = "1.18.12"
}

dependencies {
	implementation "org.mapstruct:mapstruct:${mapstructVersion}", "org.projectlombok:lombok:${lombokVersion}"
	annotationProcessor "org.mapstruct:mapstruct-processor:${mapstructVersion}", "org.projectlombok:lombok:${lombokVersion}"
}
```

## 3. 구현 코드

주로 Entity와 DTO를 매핑하므로 해당 코드를 예제로 작성하겠습니다.

### 3-1. Entity

```java
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member {

    @Id
    private String socialSecurityNumber;

    private String userName;

    private String password;

    private String name;

    private String address;

    @Builder
    public Member(String socialSecurityNumber, String userName, String password, String name, String address) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.address = address;
    }
}
```

### 3-2. DTO

위의 Entity의 field중 userName, name, address를 저장하기 위한 DTO 입니다.

```java
@Getter
@Setter
@ToString
public class MemberDto {

    private String userName;

    private String name;

    private String address;
}
```

### 3-3. Mapper Interface 

Mapping 정보를 정의하기 위한 Interface 입니다.

- `@Mapper`를 사용하면 build시 MapStruct code generator가 **해당 인터페이스의 구현체를 생성**합니다.  
- `@Mapper`에서 componentModel을 spring으로 지정하면 생성된 매퍼는 **싱글톤 scope인 Spring bean으로 생성** 됩니다.

MapStruct code generator는 memberToMemberDto 메서드의 정보를 토대로 매핑을 생성합니다.

```java
@Mapper(componentModel = "spring")
public interface MemberMapper {

    MemberDto memberToMemberDto(Member member);
    
}
```

생성된 매핑은 아래와 같습니다.

메서드의 파라미터로 받은 member Entity와 return Type인 MemberDto에 **이름이 일치하는 field에 대해서 자동으로 매핑**해주는 것을 알 수 있습니다.

```java
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-11-23T13:32:55+0900",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.6.1.jar, environment: Java 11.0.8 (Oracle Corporation)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public MemberDto memberToMemberDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDto memberDto = new MemberDto();

        memberDto.setUserName( member.getUserName() );
        memberDto.setName( member.getName() );
        memberDto.setAddress( member.getAddress() );

        return memberDto;
    }
}
```

생성된 매핑은 아래경로에서 확인할 수 있습니다.

![](image\1.PNG)

### 3-4. 테스트 코드

Mapper Interface에서 componentModel = "spring" 을 사용하였기 때문에 memberMapper의 구현체를 의존주입을 받을 수 있습니다.

```java
@SpringBootTest
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberMapper memberMapper;

    @Test
    void memberToMemberDtoTest(){
        Member member = Member.builder().socialSecurityNumber("123456-8901234")
                .userName("userName")
                .password("password")
                .name("홍길동")
                .address("서울특별시")
                .build();

        Member savedMember = memberRepository.save(member);

        MemberDto memberDto = memberMapper.memberToMemberDto(savedMember);
        assertThat(memberDto.getUserName(),is("userName"));
        assertThat(memberDto.getName(), is("홍길동"));
        assertThat(memberDto.getAddress(), is("서울특별시"));

    }
}
```

## 4. 필드명이 다를시

필드명시 다를시에는 `@Mapping` 어노테이션을 사용하여 일치시킬 필드명을 지정할 수 있습니다.

아래 코드에서 member에 socialSecurityNumber는 MemberDto의 userNumber과 매핑 됩니다.

```java
@Mapper(componentModel = "spring")
public interface MemberMapper {

    @Mappings(@Mapping(source = "socialSecurityNumber", target = "userNumber"))
    MemberDto memberToMemberDto(Member member);

}
```

생성된 매핑을 보면 위에서 정의한 대로 매핑되는 것을 알 수 있습니다.

```java
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-11-23T13:48:44+0900",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.6.1.jar, environment: Java 11.0.8 (Oracle Corporation)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public MemberDto memberToMemberDto(Member member) {
        if ( member == null ) {
            return null;
        }

        MemberDtoBuilder memberDto = MemberDto.builder();

        memberDto.userNumber( member.getSocialSecurityNumber() );
        memberDto.userName( member.getUserName() );
        memberDto.name( member.getName() );
        memberDto.address( member.getAddress() );

        return memberDto.build();
    }
}
```

## 5. componentModel = "spring" 생략하기

build.gradle에 추가설정을 해주면 `@Mapper`에 componentModel = "spring"을 생략할 수 있습니다.
> 다양한 Configuration options이 있으니 [Configuration options](https://mapstruct.org/documentation/stable/reference/html/#configuration-options)에서 필요한 options을 사용하시면 됩니다.

```java
compileJava {
    options.compilerArgs += [
            '-Amapstruct.defaultComponentModel=spring',
    ]
}
```

## 6. setter 대신 builder 사용하기

위에서 생성된 매핑을 보면 전부 setter를 사용해서 값을 넣어주고 있습니다. 하지만 setter를 사용하지 않고 빌더패턴을 사용해서 값을 세팅하는 객체도 있기 때문에 builder 사용해 보겠습니다.

아래 Entity는 builder를 통해서만 객체를 세팅할 수 있습니다.

```java
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member {

    @Id
    private String socialSecurityNumber;

    private String userName;

    private String password;

    private String name;

    private String address;

    @Builder
    public Member(String socialSecurityNumber, String userName, String password, String name, String address) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.address = address;
    }
}
```

memberDto를 Member으로 매핑하기 위한 인터페이스 입니다. 

```java
@Mapper
public interface MemberMapper {

    MemberDto memberToMemberDto(Member member);

    @Mappings(@Mapping(source = "userNumber", target = "socialSecurityNumber"))
    Member memberDtoToMember(MemberDto memberDto);

}
```

빌드 후 매핑 코드를 확인해 보면 setter가 아닌 빌더패턴을 사용해서 객체의 값을 세팅하는 것을 알 수 있습니다.

```java
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-11-23T14:12:59+0900",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.6.1.jar, environment: Java 11.0.8 (Oracle Corporation)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member memberDtoToMember(MemberDto memberDto) {
        if ( memberDto == null ) {
            return null;
        }

        MemberBuilder member = Member.builder();

        member.socialSecurityNumber( memberDto.getUserNumber() );
        member.userName( memberDto.getUserName() );
        member.name( memberDto.getName() );
        member.address( memberDto.getAddress() );

        return member.build();
    }
}
```

## 7. return type이 collection 일 시

MapStruct는 return type이 collection일시에도 자동으로 매핑을 해줍니다.

```java
@Mapper
public interface CollectionMapping {

    List<Integer> integerList(List<String> stringList);

}
```

아래 코드처럼 String만 담겨있는 List를 Integer만 담겨있는 List로 매핑해 줍니다.

```java
@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-11-23T14:19:47+0900",
    comments = "version: 1.4.1.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-6.6.1.jar, environment: Java 11.0.8 (Oracle Corporation)"
)
@Component
public class CollectionMappingImpl implements CollectionMapping {

    @Override
    public List<Integer> integerList(List<String> stringList) {
        if ( stringList == null ) {
            return null;
        }

        List<Integer> list = new ArrayList<Integer>( stringList.size() );
        for ( String string : stringList ) {
            list.add( Integer.parseInt( string ) );
        }

        return list;
    }
}
```

## 8. 매핑 메서드의 첫 번째 파라미터가 collection 일시 발생할 수 있는 문제

MapStruct 1.4.1.Final 까지는 매핑 메서드의 첫 번째 파라미터가 collection일 시 **return type은 무조건 iterable를 구현한 Collection이 되어야 합니다.** 

```java
@Mapper
public interface MemberMapper {

    Member memberDtoPasswordToMemberPassword(List<String> password);

}
```

위의 코드에서 예상한 것은 password라는 List 타입의 매개변수를 Member Entity의 password 변수에 넣는 코드인데 해당 코드는 컴파일시 iterable type을 non-iterable type에 매핑할 수 없다는 오류가 발생합니다.

![](image\2.PNG)

이 문제는 **메서드 매개변수 가장 앞에 임의의 mapping 과는 상관없는 임의의 변수**를 넣으면 해결 됩니다.
아래 코드에서는 `String dummy`를 추가해여 해당 문제를 회피하고 있습니다.

```java
@Mapper
public interface MemberMapper {

    Member memberDtoPasswordToMemberPassword(String dummy, List<String> password);
}
```

해당 문제는 1.5.0 버전에서 해결될 예정입니다. [Mapping Iterable<?> object to an object instead of collection](https://github.com/mapstruct/mapstruct/issues/607) 참고