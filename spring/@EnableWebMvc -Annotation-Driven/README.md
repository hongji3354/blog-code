Spring에서 MVC 자동설정시 `@EnableWebMvc` 또는 `<mvc:annotation-driven />`를 사용해서 설정을 진행하는 경우가 많습니다.

프로젝트 초기에는 해당 설정을 사용해도 문제가 없으나, 기존 프로젝트에서 사용시에는 유의할 점이 있습니다. 왜냐하면 자동설정으로 인해 **기존에 사용되던 Bean이 덮여쓰여질수 있는 문제**가 발생할 수 있기 때문입니다.

저 같은 경우 Jaxb2RootElementHttpMessageConverter를 bean으로 등록하기 위해 `<mvc:annotation-driven />`을 사용하였는데, 기존에 등록되어 있던 `DefaultAnnotationHandlerMapping`이 자동설정으로 인해 `RequestMappingHandlerMapping`에 덮여쓰여져서 기존에 사용하던 기능이 안되는 문제가 발생했습니다.

`@EnableWebMvc` 또는 `<mvc:annotation-driven />`으로 인해 자동으로 설정 되는 목록은 아래와 같습니다.

# 자동 설정 목록

* @RequestMapping, @ExceptionHandler가 사용된 Controller에서 request를 처리하기 위해 **RequestMappingHandlerAdapter, RequestMappingHandlerMapping, ExceptionResolver**를 bean으로 등록
* DataBinding에 사용되는 PropertyEditors 외에 **ConversionService instance를 사용한 데이터 바인딩**
* ConversionService를 통해 `@NumberFormat` 어노테이션을 사용해서 **Number Field formatting 지원**
* `@DateTimeFormat` 어노테이션을 사용해서 **Date, Calendar, Long, Joda Time, Field formatting 지원**
    > JDK 8 이상일시 LocalDate, LocalDateTime도 지원
* ClassPath에 JSR-303 Provider가 존재하면 **@Controller에서 입력을 검증**하기 위한 `@Valid` 지원
	> JSR-303은 Bean Validation 스펙입니다.   
* `@RequestBody` 메서드 매개 변수 및 `@ResponseBody`가 붙어있는 `@Requestmapping` 또는 `@ExceptionHandler` 메서드의 return 값에 **HttpMessageConverter 지원**
    * ByteArrayHttpMessageConverter
    * StringHttpMessageConverter
    * ResourceHttpMessageConverter
    * SourceHttpMessageConverter
    * FormHttpMessageConverter
    * Jaxb2RootElementHttpMessageConverter
        > classpath에 JAXB2가 존재 해야 Bean으로 등록
    * MappingJackson2HttpMessageConverter or MappingJacksonHttpMessageConverter
        > classpath에 Jackckson2 or Jackson가 존재해야 Bean으로 등록
    * AtomFeedHttpMessageConverter
        > classpath에 Rome가 존재 해야 Bean으로 등록
    * RssChannelHttpMessageConverte
        > classpath에 Rome가 존재 해야 Bean으로 등록

# 참고

* [17.15.1 Enabling the MVC Java Config or the MVC XML Namespace](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html#mvc-config-enable)
* [1.11.1. Enable MVC Configuration](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/web.html#mvc-config-enable)
* [1.1.2. Special Bean Types](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/web.html#mvc-servlet-special-bean-types)